package cn.com.obj.tmao.factory;

import java.util.Properties;

import cn.com.obj.tmao.service_impl.ServiceImplement;
import cn.com.obj.tmao.utils.propertiesUtils;

public class ServiceFactory {

	private  static  ServiceFactory  factory = new ServiceFactory();
	
	private  ServiceFactory(){
		
	}
	
	public static   ServiceFactory   getServiceFactory(){
		return factory;
	}
	
	public  ServiceImplement  createService()
	{
		Properties config = propertiesUtils.getServiceConfig();
		String classname = config.getProperty("serviceclass");
		
		Object result = null;
		try {
			result = Class.forName(classname).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return (ServiceImplement)result;
	}
	
}
