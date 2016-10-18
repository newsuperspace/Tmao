package cn.com.obj.tmao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class propertiesUtils {

	
	/**
	 * ��ȡDBCP����Ҫ��������Ϣ
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
				throw  new  RuntimeException("propertiesUtils--->����DBCP�����ļ�����");
			}
			return  config;
	}
	
	/**
	 * ΪServlet����Service��֮��Ľ��Ԫ����ServiceFactory������ʹ�õĹ��ڵ�ǰ�����ṩҵ��֧�ֵ�ҵ�����ȫ·��������Ϣ
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
			throw  new  RuntimeException("propertiesUtils--->����service�����ļ�����");
		}
		return  config;
	}
	
	/**
	 * ΪService���DAO��֮��Ľ��Ԫ��������DAOFactory������ʹ�õĹ��ڵ�ǩ�����ṩDAO���ݷ���ҵ������ȫ·��������Ϣ
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
			throw  new  RuntimeException("propertiesUtils--->����DAO�����ļ�����");
		}
		return  config;
	}
	
	
	/**
	 * ��ȡ���ֱ������õ�������Ϣ
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
			throw  new  RuntimeException("propertiesUtils--->����charset�����ļ�����");
		}
		return  config;
	}
	
	
	
	
}
