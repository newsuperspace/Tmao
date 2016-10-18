package cn.com.obj.tmao.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 与自定义标签-----> <mytag:token/>配套使用的过滤器，用以避免拥有该自定义标签的表单重复提交
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
		HttpServletRequest   request =null;
		HttpServletResponse  response  = null;
		try{
			request =  (HttpServletRequest)req;
			response =  (HttpServletResponse)resp;
		}catch(Exception e)
		{
			throw new RuntimeException("Filter->AvoidResubmit->协议类型并非Http");
		}
		
		// ======================开始干正经事儿========================
		
		// 令牌来自于session和表单中的token项
		String  sessiontoken  =  (String) request.getSession().getAttribute("token");
		String jsptoken  =  (String) request.getParameter("token");
		if(null!=jsptoken)
		{
			// token令牌存在
			if(jsptoken.equals(sessiontoken))
			{
				// 令牌匹配，放行并删除
				request.getSession().removeAttribute("token");
				chain.doFilter(request, response);
				return;
			}
			else
			{
				// *****令牌失效，判定为表单重复提交，不予通过*****
				response.setHeader("refresh", "1;url="+request.getContextPath()+"/pages/main.jsp");
				response.getWriter().write("<script type='text/javascript'>alert('注册成功')</script>");
				return;
			}
		}
		else
		{
			// 令牌不存在，说明不是来自拥有MyTagLibrary类定义自定义标签<mytag:token/>的表单页面
			chain.doFilter(request, response);
			return;
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
