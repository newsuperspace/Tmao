package cn.com.obj.tmao.utils;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCPUtils {

	/**
	 * 通过开源数据源——DBCP，获取数据库连接，并自动管理连接池
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
			throw new RuntimeException("获取数据库连接对象失败");
		}
		
		return result;
	}
	
}
