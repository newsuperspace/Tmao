package cn.com.obj.tmao.utils;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCPUtils {

	/**
	 * ͨ����Դ����Դ����DBCP����ȡ���ݿ����ӣ����Զ��������ӳ�
	 * @return
	 */
	public  static Connection  getConnection()
	{
		Connection  result= null;
		try {
			DataSource ds = BasicDataSourceFactory.createDataSource(propertiesUtils.getDBCPConfig());
			result = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("��ȡ���ݿ����Ӷ���ʧ��");
		}
		
		return result;
	}
	
}
