package cn.com.obj.tmao.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;

/**
 * �����ʹ���˿�Դ��� BeanUtils��ʵ����
 * 
 * 1������JavaBean֮�����ɱ����
 * 2����Map�����п�����ָ��JavaBean
 * 
 * @author Administrator
 *
 */
public class MyBeanUtils {

	/**
	 * ʵ�ִ�formBean��DAOBean�Ŀ���
	 * @param dest
	 * @param orig
	 */
	public  static <F,D>  void  formBean2DAObean(D dest, F orig)
	{
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ʵ�ִ�DAOBean��formBean�Ŀ���
	 * @param dest
	 * @param orig
	 */
	public  static <F,D>  void  DAObean2formBean(F dest, D orig)
	{
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ʵ�ְ������������map������ɱ������ָ��formBean�Ĳ���
	 */
	public  static  <T> void parameterMap2formBean(T bean, Map<String,String[]> map)
	{
		ConvertUtils.register(new FloatConverter(), Float.class);
		ConvertUtils.register(new IntegerConverter(), Integer.class);
		// formBook ����һ���ֶ�money��float���͵ģ��������ֵ����String���ʹ�������ת������
		
		
		try {
			BeanUtils.populate(bean, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
