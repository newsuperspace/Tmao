package cn.com.obj.tmao.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;

/**
 * 这个类使用了开源框架 BeanUtils，实现了
 * 
 * 1、两个JavaBean之间的秒杀拷贝
 * 2、从Map容器中拷贝到指定JavaBean
 * 
 * @author Administrator
 *
 */
public class MyBeanUtils {

	/**
	 * 实现从formBean到DAOBean的拷贝
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
	 * 实现从DAOBean到formBean的拷贝
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
	 * 实现包含请求参数的map容器秒杀拷贝到指定formBean的操作
	 */
	public  static  <T> void parameterMap2formBean(T bean, Map<String,String[]> map)
	{
		ConvertUtils.register(new FloatConverter(), Float.class);
		ConvertUtils.register(new IntegerConverter(), Integer.class);
		// formBook 中有一个字段money是float类型的，请求参数值都是String类型存在类型转换问题
		
		
		try {
			BeanUtils.populate(bean, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
