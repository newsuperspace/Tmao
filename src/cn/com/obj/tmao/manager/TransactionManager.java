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
			result = DBCPUtils.getConnection();   // DBUtils会使用DBCP的相关API来获取到指定数据库服务器上的指定服务器的连接对象
			tls.set(result);   									// 然后将连接对象放入到tls 局部存储对象中，这样相同线程上的逻辑在任何地方通过本类的
																	// getConnnection()方法获取到的连接对象都是同一个连接对象了
		}
		return result;
	}
	
	//========================= 供service层所调用的方法，用以进行事务管理===========================
	/**
	 * 开启事务
	 */
	public static  void start()
	{
		try {
			getConnection().setAutoCommit(false);    // 设置数据库连接对象的事务自动提交功能失效（等同于开启了事务管理）
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->start()->开启事务失败");
		}
	}
	
	/**
	 * 提交事务（完成事务管理）
	 */
	public  static  void commit()
	{
		try {
			getConnection().commit();      				// 提交连接上的所有数据库操作，完成事务管理
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->commit()->提交事务失败");
		}finally
		{
			deleteConnection();
		}
	}
	
	/**
	 * 回滚事务（完成事务管理）
	 */
	public  static  void rollback()
	{
		try {
			getConnection().rollback();						// 撤销DAO层上的所有数据库操作
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
	 * 私有方法，供给事务管理使用
	 * 将对应当前线程的连接对象从TLS中删除
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
