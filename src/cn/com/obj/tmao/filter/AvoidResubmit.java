package cn.com.obj.tmao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 与自定义标签-----> <mytag:token/>配套使用的过滤器，用以避免拥有该自定义标签的表单重复提交
 * 
 * @author Administrator
 * 
 */
public class AvoidResubmit implements Filter {
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// ===============过滤器的标准准备工作，将请求和响应转化为HTTP协议版本==============
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) resp;
		} catch (Exception e) {
			throw new RuntimeException("Filter->AvoidResubmit->协议类型并非Http");
		}
		// ======================开始干正经事儿========================
		// 令牌来自于session和表单中的token项
		String sessiontoken = (String) request.getSession().getAttribute(
				"token");
		String jsptoken = (String) request.getParameter("token");
		if (null != jsptoken) {
			// token令牌存在
			if (jsptoken.equals(sessiontoken)) {
				// 令牌匹配，放行并删除
				request.getSession().removeAttribute("token");
				chain.doFilter(request, response);
				return;
			} else {
				// *****令牌失效，判定为表单重复提交，不予通过*****
				response.setHeader("refresh",
						"1;url=" + request.getContextPath() + "/pages/main.jsp"); // 自动跳转
				// 如果直接在页面的<script></script>中插入一条不属于任何function的JS代码，则当页面加载完毕后就会自动调用这些无主代码
				// 这里就是当页面加载完毕后 弹出一个提示框
				response.getWriter()
						.write("<script type='text/javascript'>alert('注册成功')</script>");
				
				/**
				 * 在过滤器中，如果使用chain.doFilter() 就会将流程流转给下一个拦截器（如果有的话），如果这就是最后一个过滤器，才会交给本次请求真正想要
				 * 访问的servlet中去。
				 * 因此如果一个拦截器中直接使用return终止执行，则说明这次访问存在问题，已经无需其他过滤器处理或者servlet处理了。
				 * 但你要先为这次请求写好response部分，然后才能return中断请求，否则这就是一个没有任何响应的请求，用户浏览器会是空白他也不知道发生了什么。
				 * 正确的做法就是在return中断请求前先将response引导到信息反馈页面上去，让用户了解情况。
				 */
				return;   
			}
		} else {
			// 令牌不存在，说明不是来自拥有MyTagLibrary类定义自定义标签<mytag:token/>的表单页面
			chain.doFilter(request, response);
			return;
		}
	}
	
	private ServletContext sc = null;
	@Override
	public void init(FilterConfig config) throws ServletException {
		sc = config.getServletContext();
	}
}
