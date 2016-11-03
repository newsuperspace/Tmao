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
//		response.setCharacterEncoding("utf-8");
		/**
		 * 这句话非常重要，
		 * 1、它告知浏览器从服务器传递过来的响应数据的MIME类型为HTML文本
		 * 这样浏览器就不会用下载的方式处理这段服务器响应回来的数据了。如果不指定response的MIME类型
		 * 为text/html则浏览器默认会以下载的方式处理服务器的响应，而不是一HTML页面的形式处理，这点非常重要！
		 * 2、它告知服务器端以何种字符集（UTF-8）编码响应部分的字符，
		 * 也就是servlet通过response.getWriter().write()或response.getOutputStream().write()
		 * 写入到response对象的缓冲区的数据，这些数据会被编码为二进制（本身就是二进制的就不用编了）
		 * 然后组成作为真正HTTP协议的响应部分的实体内容，而这个编码过程中究竟是用哪种码表
		 * 就是通过这里charset=utf-8或者response.setCharacterEncoding('utf-8')来指定的。
		 * 3、它还告知了浏览器以何种字符集解码从服务器端传递来HTTP协议的实体内容（二进制）数据
		 * 4、因此这里只要谢了response.setContextType("text/html;charset=utf-8");了就不用再写response.setCharacterEncoding("utf-8");了
		 */
		response.setContentType("text/html;charset=utf-8");   // 千万不要忽视MIME类型部分，不然浏览器就会尝试下载而不是尝试解析HTML文本页面,我已经吃过不少次这个亏了
		
		// 解决GET请求方式的乱码问题———需要借助包装设计模式所实现的适配器对request对象进行包装，从而更改其getParameter()的行为
//		MyHttpServletRequest  myRequest =  new  MyHttpServletRequest(request);   // 目前新版本的Tomcat可能已经不需要这么做了，使用request。setCharacterEncoding就能同时解决post和get乱码问题
//		chain.doFilter(myRequest, response);
		
		// 最后一切准备就绪，放行把
		chain.doFilter(request, response);
	}

	/**
	 * 定义一个私有的内部类，继承自Request的包装适配器类型——Wrapper
	 * 然后更改我们所需要的方法的行为
	 * @author Administrator
	 *
	 *适配器设计模式 = 包装设计模式 + 继承覆盖
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

		/**
		 * 我们想要修改的方法是获取请求参数的方法，因为 request.setCharacterEncoding()的方式只对POST提交有效
		 * 而对于请求方式为GET的话，由于请求参数都是以？name=AAA& 这样的形式追加到hurl末尾过来的而不是在请求正文中
		 * 因此通过设置编码的方式没法解决乱码问题（可实际上通过实践操作，现在新版本的TomCat好像只需要通过request.setCharacterEncoding()设置一次
		 * 就能同时解决POST和GET两种提交方式乱码了，所以这种适配器的方式可能不再需要了）
		 */
		@Override
		public String getParameter(String name) {
			// 先调用原始HttpServletRequest对象的getParameter方法获取有乱码的请求参数的值
			//（因为所有二进制数据来到服务器端后默认都是先以ASCII码表进行解码，所以如果客户端是用U8编码的而服务器却用ASCII解码自然就会产生乱码）
			String value = super.getParameter(name);
			
			if(null==value)
			{
				return null;  // 检测字符串是否为null，为null说明没有叫这个名字的请求参数，则可以直接返回null，表示找不到这个名字的请求参数
			}
			
			/**
			 * 接下来就是很经典地处理字符串乱码的方式
			 * 因为我们知道现在value是服务器缺省用ASCII码表解码所生成的字符串，所以为了获取从浏览器穿过来的原始二进制数据
			 * 我们就需要使用String类型的getBytes方法指定编码字符集为ISO8859-1后对字符串进行编码，得到字符数组，也就是字节缓冲
			 * 然后新建一个String类型，使用一个包含两个参数的构造器，分别是 字节数组、解码码表、 
			 */
			try {
				byte[]  buffer =  value.getBytes("iso8859-1");
				// 这样新建出来的字符处就完成了从ASCII码转变为UTF-8编码的过程了
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
