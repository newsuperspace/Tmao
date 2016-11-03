package cn.com.obj.function;

import java.util.List;

import cn.com.obj.tmao.domain.OrderItem;

/**
 * JSP自定义函数
 * 与JSP自定义标签类似，需要在/WEB-INF/taglib.tld 文件中进行注册，然后方能在JSP页面中使用EL表达式来调用我们自定义
 * 的函数实现逻辑。
 * 
 * 我们可以认为：
 * JSP自定义标签+JSP自定义函数 ==  JSP脚本片断   (替代)
 * EL表达式  ==  JSP表达式
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
