package cn.com.obj.tmao.manager;

import java.sql.Connection;
import java.sql.SQLException;

import cn.com.obj.tmao.utils.DBCPUtils;

/**
 * 利用TLS——线程局部存储技术实现事务跨"service层----dao层-----JDBC------DBCP-----数据库服务器"的管理
 * @author Administrator
 *
 */
public class TransactionManager {
	// 线程局部存储技术的核心点，就在于静态的唯一的线程局部存储容器对象，他可以根据线程的不同而从自身中寻找出只属于该线程的元素出来
	private static   ThreadLocal<Connection>  tls  =  new ThreadLocal<>();
	
	
	//===============供DAO层所使用的DBUtils中的QueryRunner类的update()和query()方法调用时使用=========================
	
	public  static  Connection  getConnection(){
		Connection  result = null;
		
		result = tls.get();
		if(null==result)
		{
			result = DBCPUtils.getConnection();
			tls.set(result);
		}
		
		return result;
	}
	
	
	
	//========================= 供service层进行事务管理===========================

	public static  void start()
	{
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->start()->开启事务失败");
		}
	}
	
	
	public  static  void commit()
	{
		try {
			getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->commit()->提交事务失败");
		}finally
		{
			deleteConnection();
		}
	}
	
	public  static  void rollback()
	{
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->commit()->回滚事务失败");
		}finally
		{
			deleteConnection();
		}
	}
	
	
	
	// ===============================内部使用=================================
	
	/**
	 * 私有方法，用以
	 */
	private   static  void  deleteConnection()
	{
		Connection connection =  getConnection();
		tls.remove();
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->deleteConnection()->Connection对象关闭失败");
		}finally
		{
			connection = null;
		}
	}
}
