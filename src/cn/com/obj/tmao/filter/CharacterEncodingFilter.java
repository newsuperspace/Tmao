package cn.com.obj.tmao.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import cn.com.obj.tmao.utils.propertiesUtils;

public class CharacterEncodingFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		//  =============================过滤器的标准步骤============================
		HttpServletRequest   request = null;
		HttpServletResponse  response = null;
		try{
			request = (HttpServletRequest)req;
			response = (HttpServletResponse)resp;
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("CharacterEncodingFilter->doFilter()->非HTTP协议");
		}
		
		
		// ==============================下面开始正经事儿============================
		 
		// 解决POST请求方式的乱码问题
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		/**
		 * 这句话非常重要，
		 * 1、它告知浏览器从服务器传递过来的响应数据的MIME类型为HTML文本
		 * 这样浏览器就不会用下载的方式处理这段数据了。
		 * 2、它告知服务器端以何种字符集（UTF-8）编码响应部分的字符
		 * 3、它还告知了浏览器以何种字符集解码从服务器端传递来的数据
		 */
		response.setContentType("text/html;charset=utf-8");   // 千万不要忽视MIME类型部分，不然浏览器就会尝试下载而不是尝试解析HTML文本页面,我已经吃过不少次这个亏了
		
		// 解决GET请求方式的乱码问题———需要借助包装设计模式所实现的适配器对request对象进行包装，从而更改其getParameter()的行为
		
//		MyHttpServletRequest  myRequest =  new  MyHttpServletRequest(request);
		
		// 最后一切准备就绪，放行把
		chain.doFilter(request, response);
	}

	/**
	 * 定义一个私有的内部类，继承自Request的包装适配器类型——Wrapper
	 * 然后更改我们所需要的方法的行为
	 * @author Administrator
	 *
	 */
	private  class MyHttpServletRequest  extends HttpServletRequestWrapper
	{
		/**
		 * 这个构造器是包装适配器的核心，它将需要更改成员方法行为的被包装对象注入到以修饰设计模式实现的包装适配器中去
		 * 这个适配器类型又是我们当前类的父类，在这个适配器类型与被包装类型实现了完全相同的接口，并在每个相同方法内部
		 * 原封不动地原样调用被包装的对象的同名方法。而我们在自己的类中只需要覆盖(override)我们需要更改行为的方法即可，至于其他
		 * 不需要更改行为的方法就会调用父类内部的，而父类又会自动调用被包装类的。
		 * @param request
		 */
		public MyHttpServletRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			String value = super.getParameter(name);
			
			if(null==value)
			{
				return null;
			}
			
			try {
				byte[]  buffer =  value.getBytes("iso8859-1");
				value =  new String(buffer,propertiesUtils.getCharsetConfig().getProperty("page"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw  new  RuntimeException("CharacterEncodingFilter->MyHttpServletRequest->getParameter()->编码类型异常");
			}
			return value;
		}
		
	}
	
	
	
	
	
	
	@Override
	public void init(FilterConfig config) throws ServletException {}

}
