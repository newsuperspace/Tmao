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
			result = DBCPUtils.getConnection();   // DBUtils��ʹ��DBCP�����API����ȡ��ָ�����ݿ�������ϵ�ָ�������������Ӷ���
			tls.set(result);   									// Ȼ�����Ӷ�����뵽tls �ֲ��洢�����У�������ͬ�߳��ϵ��߼����κεط�ͨ�������
																	// getConnnection()������ȡ�������Ӷ�����ͬһ�����Ӷ�����
		}
		return result;
	}
	
	//========================= ��service�������õķ��������Խ����������===========================
	/**
	 * ��������
	 */
	public static  void start()
	{
		try {
			getConnection().setAutoCommit(false);    // �������ݿ����Ӷ���������Զ��ύ����ʧЧ����ͬ�ڿ������������
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->start()->��������ʧ��");
		}
	}
	
	/**
	 * �ύ��������������
	 */
	public  static  void commit()
	{
		try {
			getConnection().commit();      				// �ύ�����ϵ��������ݿ����������������
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("TransactionManager->commit()->�ύ����ʧ��");
		}finally
		{
			deleteConnection();
		}
	}
	
	/**
	 * �ع���������������
	 */
	public  static  void rollback()
	{
		try {
			getConnection().rollback();						// ����DAO���ϵ��������ݿ����
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
	 * ˽�з����������������ʹ��
	 * ����Ӧ��ǰ�̵߳����Ӷ����TLS��ɾ��
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
