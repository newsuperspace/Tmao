package cn.com.obj.tmao.factory;

import java.util.Properties;

import cn.com.obj.tmao.dao_interface.DAOBookInterface;
import cn.com.obj.tmao.dao_interface.DAOCategoryInterface;
import cn.com.obj.tmao.dao_interface.DAOCustomerInterface;
import cn.com.obj.tmao.dao_interface.DAOOrderItemInterface;
import cn.com.obj.tmao.dao_interface.DAOOrdersInterface;
import cn.com.obj.tmao.utils.propertiesUtils;

public class DaoFactory {
	
	// ======================�������ģʽ�����е�����========================
	// ������Ψһ��ʵ��
	private static  DaoFactory  factory  =  new  DaoFactory();
	private static  Properties  p  =  propertiesUtils.getDAOConfig();    // ͨ�������ļ��õ���Ӧ��ͬDAO����ȫ��
	
	private DaoFactory()
	{
		// ˽�л������๹����
	}
	
	public  static DaoFactory  getDAOFactory()
	{
		return factory;
	}
	
	// =================DAO�����е����з���ÿ���������ڻ�ȡһ��DAO���͵�ʵ������==================
	public  DAOBookInterface createBookDAO()
	{
		DAOBookInterface  dao  =  null;
		try {
			// ������ƣ� ����������Class��forName�������ƶ���ȫ���������Ͷ���class���󣩼��ص��ڴ棨���������������һ����
			// Ȼ�������ü��ص�������class�����newInstance������̬����һ��ʵ������
			// ����ǹ������ģʽ�ĺ�������
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
