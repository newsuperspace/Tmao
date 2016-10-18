package cn.com.obj.tmao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class propertiesUtils {

	
	/**
	 * 获取DBCP所需要的配置信息
	 * @return
	 */
	public  static  Properties   getDBCPConfig()
	{
			Properties  config  =  new Properties();
		
			InputStream is = propertiesUtils.class.getClassLoader().getResourceAsStream("config/03dbcpconfig.properties");
			try {
				config.load(is);
			} catch (IOException e) {
				e.printStackTrace();
				throw  new  RuntimeException("propertiesUtils--->载入DBCP配置文件出错");
			}
			return  config;
	}
	
	/**
	 * 为Servlet层与Service层之间的解耦单元——ServiceFactory工厂所使用的关于当前正在提供业务支持的业务类的全路径配置信息
	 * @return
	 */
	public static Properties getServiceConfig()
	{
		Properties  config  =  new Properties();
		
		InputStream is = propertiesUtils.class.getClassLoader().getResourceAsStream("config/service.properties");
		try {
			config.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw  new  RuntimeException("propertiesUtils--->载入service配置文件出错");
		}
		return  config;
	}
	
	/**
	 * 为Service层和DAO曾之间的解耦单元——各种DAOFactory工厂所使用的关于单签正在提供DAO数据访问业务的类的全路径配置信息
	 * @return
	 */
	public static Properties getDAOConfig()
	{
		Properties  config  =  new Properties();
		
		InputStream is = propertiesUtils.class.getClassLoader().getResourceAsStream("config/dao.properties");
		try {
			config.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw  new  RuntimeException("propertiesUtils--->载入DAO配置文件出错");
		}
		return  config;
	}
	
	
	/**
	 * 获取各种编码配置的配置信息
	 * @return
	 */
	public  static  Properties getCharsetConfig()
	{
		Properties   config  =  new  Properties();
		
		InputStream is = propertiesUtils.class.getClassLoader().getResourceAsStream("config/charset.properties");
		try {
			config.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw  new  RuntimeException("propertiesUtils--->载入charset配置文件出错");
		}
		return  config;
	}
	
	
	
	
}
