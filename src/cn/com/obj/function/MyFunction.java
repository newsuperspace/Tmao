package cn.com.obj.function;

import java.util.List;

import cn.com.obj.tmao.domain.OrderItem;

/**
 * JSP�Զ��庯��
 * ��JSP�Զ����ǩ���ƣ���Ҫ��/WEB-INF/taglib.tld �ļ��н���ע�ᣬȻ������JSPҳ����ʹ��EL���ʽ�����������Զ���
 * �ĺ���ʵ���߼���
 * 
 * ���ǿ�����Ϊ��
 * JSP�Զ����ǩ+JSP�Զ��庯�� ==  JSP�ű�Ƭ��   (���)
 * EL���ʽ  ==  JSP���ʽ
 * @author Administrator
 *
 */
public class MyFunction {

	public  static  String  totalMoney(List  list)
	{
		
		String  result = null;
		
		float   total =  0;
		for(Object  obj : list)
		{
			OrderItem  item =  (OrderItem)obj;
			total += item.getMoney();
		}
		
		result =  String.valueOf(total);
		return result;
	}
	
}
