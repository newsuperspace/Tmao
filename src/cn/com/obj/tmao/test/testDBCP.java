package cn.com.obj.tmao.test;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import cn.com.obj.tmao.utils.propertiesUtils;

public class testDBCP {

	@Test
	public void test() {
		
		// DBCP数据源的配置测试通过可以正常使用
		try {
			DataSource  ds = null;
			ds   = BasicDataSourceFactory.createDataSource(propertiesUtils.getDBCPConfig());
			Connection connection = ds.getConnection();
			
			/*
			 * 基础JDBC操作
			 */
			PreparedStatement PS = connection.prepareStatement("INSERT INTO category VALUES(?,?,?)");
			PS.setString(1, UUID.randomUUID().toString());
			PS.setString(2, "计算机科学");
			PS.setString(3, "描述");
			PS.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
