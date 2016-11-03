package cn.com.obj.tmao.factory;

import java.util.Properties;

import cn.com.obj.tmao.dao_interface.DAOBookInterface;
import cn.com.obj.tmao.dao_interface.DAOCategoryInterface;
import cn.com.obj.tmao.dao_interface.DAOCustomerInterface;
import cn.com.obj.tmao.dao_interface.DAOOrderItemInterface;
import cn.com.obj.tmao.dao_interface.DAOOrdersInterface;
import cn.com.obj.tmao.utils.propertiesUtils;

public class DaoFactory {
	
	// ======================工厂设计模式必须有的特征========================
	// 单例中唯一的实例
	private static  DaoFactory  factory  =  new  DaoFactory();
	private static  Properties  p  =  propertiesUtils.getDAOConfig();    // 通过配置文件得到对应不同DAO的类全名
	
	private DaoFactory()
	{
		// 私有化工厂类构造器
	}
	
	public  static DaoFactory  getDAOFactory()
	{
		return factory;
	}
	
	// =================DAO工厂中的下列方法每个方法用于获取一种DAO类型的实例对象==================
	public  DAOBookInterface createBookDAO()
	{
		DAOBookInterface  dao  =  null;
		try {
			// 反射机制： 运用类类型Class的forName方法将制定类全名的类类型对象（class对象）加载到内存（与类加载器的作用一样）
			// 然后在利用加载的这个类的class对象的newInstance方法动态创建一个实例对象。
			// 这就是工厂设计模式的核心所在
			dao  = (DAOBookInterface) Class.forName(p.getProperty("daobook")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dao;
	}
	
	public DAOCategoryInterface  createCategoryDAO()
	{
		DAOCategoryInterface  dao  =  null;
		try {
			dao  = (DAOCategoryInterface) Class.forName(p.getProperty("daocategory")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dao;
	}
	
	public DAOCustomerInterface createCustomerDAO()
	{
		DAOCustomerInterface  dao  =  null;
		try {
			dao  = (DAOCustomerInterface) Class.forName(p.getProperty("daocustomer")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dao;
	}
	
	public  DAOOrdersInterface  createOrdersDAO()
	{
		DAOOrdersInterface  dao  =  null;
		try {
			dao  = (DAOOrdersInterface) Class.forName(p.getProperty("daoorders")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dao;
	}
	
	public  DAOOrderItemInterface	createOrderItemDAO()
	{
		DAOOrderItemInterface  dao  =  null;
		try {
			dao  = (DAOOrderItemInterface) Class.forName(p.getProperty("daoorderitem")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dao;
	}
}
