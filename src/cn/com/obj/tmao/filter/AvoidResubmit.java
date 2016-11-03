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
 * ���Զ����ǩ-----> <mytag:token/>����ʹ�õĹ����������Ա���ӵ�и��Զ����ǩ�ı��ظ��ύ
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
		// ===============�������ı�׼׼�����������������Ӧת��ΪHTTPЭ��汾==============
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) resp;
		} catch (Exception e) {
			throw new RuntimeException("Filter->AvoidResubmit->Э�����Ͳ���Http");
		}
		// ======================��ʼ�������¶�========================
		// ����������session�ͱ��е�token��
		String sessiontoken = (String) request.getSession().getAttribute(
				"token");
		String jsptoken = (String) request.getParameter("token");
		if (null != jsptoken) {
			// token���ƴ���
			if (jsptoken.equals(sessiontoken)) {
				// ����ƥ�䣬���в�ɾ��
				request.getSession().removeAttribute("token");
				chain.doFilter(request, response);
				return;
			} else {
				// *****����ʧЧ���ж�Ϊ���ظ��ύ������ͨ��*****
				response.setHeader("refresh",
						"1;url=" + request.getContextPath() + "/pages/main.jsp"); // �Զ���ת
				// ���ֱ����ҳ���<script></script>�в���һ���������κ�function��JS���룬��ҳ�������Ϻ�ͻ��Զ�������Щ��������
				// ������ǵ�ҳ�������Ϻ� ����һ����ʾ��
				response.getWriter()
						.write("<script type='text/javascript'>alert('ע��ɹ�')</script>");
				
				/**
				 * �ڹ������У����ʹ��chain.doFilter() �ͻὫ������ת����һ��������������еĻ����������������һ�����������Żύ����������������Ҫ
				 * ���ʵ�servlet��ȥ��
				 * ������һ����������ֱ��ʹ��return��ִֹ�У���˵����η��ʴ������⣬�Ѿ����������������������servlet�����ˡ�
				 * ����Ҫ��Ϊ�������д��response���֣�Ȼ�����return�ж����󣬷��������һ��û���κ���Ӧ�������û���������ǿհ���Ҳ��֪��������ʲô��
				 * ��ȷ������������return�ж�����ǰ�Ƚ�response��������Ϣ����ҳ����ȥ�����û��˽������
				 */
				return;   
			}
		} else {
			// ���Ʋ����ڣ�˵����������ӵ��MyTagLibrary�ඨ���Զ����ǩ<mytag:token/>�ı�ҳ��
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
