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
		
		// DBCP����Դ�����ò���ͨ����������ʹ��
		try {
			DataSource  ds = null;
			ds   = BasicDataSourceFactory.createDataSource(propertiesUtils.getDBCPConfig());
			Connection connection = ds.getConnection();
			
			/*
			 * ����JDBC����
			 */
			PreparedStatement PS = connection.prepareStatement("INSERT INTO category VALUES(?,?,?)");
			PS.setString(1, UUID.randomUUID().toString());
			PS.setString(2, "�������ѧ");
			PS.setString(3, "����");
			PS.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
