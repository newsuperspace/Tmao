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

		//  =============================�������ı�׼����============================
		HttpServletRequest   request = null;
		HttpServletResponse  response = null;
		try{
			request = (HttpServletRequest)req;
			response = (HttpServletResponse)resp;
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("CharacterEncodingFilter->doFilter()->��HTTPЭ��");
		}
		
		
		// ==============================���濪ʼ�����¶�============================
		 
		// ���POST����ʽ����������
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		/**
		 * ��仰�ǳ���Ҫ��
		 * 1������֪������ӷ��������ݹ�������Ӧ���ݵ�MIME����ΪHTML�ı�
		 * ����������Ͳ��������صķ�ʽ������������ˡ�
		 * 2������֪���������Ժ����ַ�����UTF-8��������Ӧ���ֵ��ַ�
		 * 3��������֪��������Ժ����ַ�������ӷ������˴�����������
		 */
		response.setContentType("text/html;charset=utf-8");   // ǧ��Ҫ����MIME���Ͳ��֣���Ȼ������ͻ᳢�����ض����ǳ��Խ���HTML�ı�ҳ��,���Ѿ��Թ����ٴ��������
		
		// ���GET����ʽ���������⡪������Ҫ������װ���ģʽ��ʵ�ֵ���������request������а�װ���Ӷ�������getParameter()����Ϊ
		
//		MyHttpServletRequest  myRequest =  new  MyHttpServletRequest(request);
		
		// ���һ��׼�����������а�
		chain.doFilter(request, response);
	}

	/**
	 * ����һ��˽�е��ڲ��࣬�̳���Request�İ�װ���������͡���Wrapper
	 * Ȼ�������������Ҫ�ķ�������Ϊ
	 * @author Administrator
	 *
	 */
	private  class MyHttpServletRequest  extends HttpServletRequestWrapper
	{
		/**
		 * ����������ǰ�װ�������ĺ��ģ�������Ҫ���ĳ�Ա������Ϊ�ı���װ����ע�뵽���������ģʽʵ�ֵİ�װ��������ȥ
		 * ��������������������ǵ�ǰ��ĸ��࣬����������������뱻��װ����ʵ������ȫ��ͬ�Ľӿڣ�����ÿ����ͬ�����ڲ�
		 * ԭ�ⲻ����ԭ�����ñ���װ�Ķ����ͬ�����������������Լ�������ֻ��Ҫ����(override)������Ҫ������Ϊ�ķ������ɣ���������
		 * ����Ҫ������Ϊ�ķ����ͻ���ø����ڲ��ģ��������ֻ��Զ����ñ���װ��ġ�
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
				throw  new  RuntimeException("CharacterEncodingFilter->MyHttpServletRequest->getParameter()->���������쳣");
			}
			return value;
		}
		
	}
	
	
	
	
	
	
	@Override
	public void init(FilterConfig config) throws ServletException {}

}
