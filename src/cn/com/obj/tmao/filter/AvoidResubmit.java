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
 * ���Զ����ǩ-----> <mytag:token/>����ʹ�õĹ����������Ա���ӵ�и��Զ����ǩ�ı��ظ��ύ
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
		
		// ===============�������ı�׼׼�����������������Ӧת��ΪHTTPЭ��汾==============
		HttpServletRequest   request =null;
		HttpServletResponse  response  = null;
		try{
			request =  (HttpServletRequest)req;
			response =  (HttpServletResponse)resp;
		}catch(Exception e)
		{
			throw new RuntimeException("Filter->AvoidResubmit->Э�����Ͳ���Http");
		}
		
		// ======================��ʼ�������¶�========================
		
		// ����������session�ͱ��е�token��
		String  sessiontoken  =  (String) request.getSession().getAttribute("token");
		String jsptoken  =  (String) request.getParameter("token");
		if(null!=jsptoken)
		{
			// token���ƴ���
			if(jsptoken.equals(sessiontoken))
			{
				// ����ƥ�䣬���в�ɾ��
				request.getSession().removeAttribute("token");
				chain.doFilter(request, response);
				return;
			}
			else
			{
				// *****����ʧЧ���ж�Ϊ���ظ��ύ������ͨ��*****
				response.setHeader("refresh", "1;url="+request.getContextPath()+"/pages/main.jsp");
				response.getWriter().write("<script type='text/javascript'>alert('ע��ɹ�')</script>");
				return;
			}
		}
		else
		{
			// ���Ʋ����ڣ�˵����������ӵ��MyTagLibrary�ඨ���Զ����ǩ<mytag:token/>�ı�ҳ��
			chain.doFilter(request, response);
			return;
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
