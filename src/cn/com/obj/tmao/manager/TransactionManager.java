package cn.com.obj.tmao.manager;

import java.sql.Connection;
import java.sql.SQLException;

import cn.com.obj.tmao.utils.DBCPUtils;

/**
 * ����TLS�����ֲ߳̾��洢����ʵ�������"service��----dao��-----JDBC------DBCP-----���ݿ������"�Ĺ���
 * @author Administrator
 *
 */
public class TransactionManager {
	// �ֲ߳̾��洢�����ĺ��ĵ㣬�����ھ�̬��Ψһ���ֲ߳̾��洢�������������Ը����̵߳Ĳ�ͬ����������Ѱ�ҳ�ֻ���ڸ��̵߳�Ԫ�س���
	private static   ThreadLocal<Connection>  tls  =  new ThreadLocal<>();
	
	
	//===============��DAO����ʹ�õ�DBUtils�е�QueryRunner���update()��query()��������ʱʹ��=========================
	
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
	
	
	
	//========================= ��service������������===========================

	public static  void start()
	{
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->start()->��������ʧ��");
		}
	}
	
	
	public  static  void commit()
	{
		try {
			getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->commit()->�ύ����ʧ��");
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
			throw new RuntimeException("TransactionManager->commit()->�ع�����ʧ��");
		}finally
		{
			deleteConnection();
		}
	}
	
	
	
	// ===============================�ڲ�ʹ��=================================
	
	/**
	 * ˽�з���������
	 */
	private   static  void  deleteConnection()
	{
		Connection connection =  getConnection();
		tls.remove();
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->deleteConnection()->Connection����ر�ʧ��");
		}finally
		{
			connection = null;
		}
	}
}
