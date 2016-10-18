package cn.com.obj.tmao.factory;

import java.util.Properties;

import cn.com.obj.tmao.dao_interface.DAOBookInterface;
import cn.com.obj.tmao.dao_interface.DAOCategoryInterface;
import cn.com.obj.tmao.dao_interface.DAOCustomerInterface;
import cn.com.obj.tmao.dao_interface.DAOOrderItemInterface;
import cn.com.obj.tmao.dao_interface.DAOOrdersInterface;
import cn.com.obj.tmao.utils.propertiesUtils;

public class DaoFactory {
	
	// 单例中唯一的实例
	private static  DaoFactory  factory  =  new  DaoFactory();
	private static  Properties  p  =  propertiesUtils.getDAOConfig();
	
	private DaoFactory()
	{
		// 私有化工厂类构造器
	}
	
	public  static DaoFactory  getDAOFactory()
	{
		return factory;
	}
	
	public  DAOBookInterface createBookDAO()
	{
		DAOBookInterface  dao  =  null;
		try {
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
