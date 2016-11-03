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
//		response.setCharacterEncoding("utf-8");
		/**
		 * ��仰�ǳ���Ҫ��
		 * 1������֪������ӷ��������ݹ�������Ӧ���ݵ�MIME����ΪHTML�ı�
		 * ����������Ͳ��������صķ�ʽ������η�������Ӧ�����������ˡ������ָ��response��MIME����
		 * Ϊtext/html�������Ĭ�ϻ������صķ�ʽ�������������Ӧ��������һHTMLҳ�����ʽ�������ǳ���Ҫ��
		 * 2������֪���������Ժ����ַ�����UTF-8��������Ӧ���ֵ��ַ���
		 * Ҳ����servletͨ��response.getWriter().write()��response.getOutputStream().write()
		 * д�뵽response����Ļ����������ݣ���Щ���ݻᱻ����Ϊ�����ƣ�������Ƕ����ƵľͲ��ñ��ˣ�
		 * Ȼ�������Ϊ����HTTPЭ�����Ӧ���ֵ�ʵ�����ݣ��������������о��������������
		 * ����ͨ������charset=utf-8����response.setCharacterEncoding('utf-8')��ָ���ġ�
		 * 3��������֪��������Ժ����ַ�������ӷ������˴�����HTTPЭ���ʵ�����ݣ������ƣ�����
		 * 4���������ֻҪл��response.setContextType("text/html;charset=utf-8");�˾Ͳ�����дresponse.setCharacterEncoding("utf-8");��
		 */
		response.setContentType("text/html;charset=utf-8");   // ǧ��Ҫ����MIME���Ͳ��֣���Ȼ������ͻ᳢�����ض����ǳ��Խ���HTML�ı�ҳ��,���Ѿ��Թ����ٴ��������
		
		// ���GET����ʽ���������⡪������Ҫ������װ���ģʽ��ʵ�ֵ���������request������а�װ���Ӷ�������getParameter()����Ϊ
//		MyHttpServletRequest  myRequest =  new  MyHttpServletRequest(request);   // Ŀǰ�°汾��Tomcat�����Ѿ�����Ҫ��ô���ˣ�ʹ��request��setCharacterEncoding����ͬʱ���post��get��������
//		chain.doFilter(myRequest, response);
		
		// ���һ��׼�����������а�
		chain.doFilter(request, response);
	}

	/**
	 * ����һ��˽�е��ڲ��࣬�̳���Request�İ�װ���������͡���Wrapper
	 * Ȼ�������������Ҫ�ķ�������Ϊ
	 * @author Administrator
	 *
	 *���������ģʽ = ��װ���ģʽ + �̳и���
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

		/**
		 * ������Ҫ�޸ĵķ����ǻ�ȡ��������ķ�������Ϊ request.setCharacterEncoding()�ķ�ʽֻ��POST�ύ��Ч
		 * ����������ʽΪGET�Ļ�������������������ԣ�name=AAA& ��������ʽ׷�ӵ�hurlĩβ�����Ķ�����������������
		 * ���ͨ�����ñ���ķ�ʽû������������⣨��ʵ����ͨ��ʵ�������������°汾��TomCat����ֻ��Ҫͨ��request.setCharacterEncoding()����һ��
		 * ����ͬʱ���POST��GET�����ύ��ʽ�����ˣ����������������ķ�ʽ���ܲ�����Ҫ�ˣ�
		 */
		@Override
		public String getParameter(String name) {
			// �ȵ���ԭʼHttpServletRequest�����getParameter������ȡ����������������ֵ
			//����Ϊ���ж��������������������˺�Ĭ�϶�������ASCII�����н��룬��������ͻ�������U8����Ķ�������ȴ��ASCII������Ȼ�ͻ�������룩
			String value = super.getParameter(name);
			
			if(null==value)
			{
				return null;  // ����ַ����Ƿ�Ϊnull��Ϊnull˵��û�н�������ֵ���������������ֱ�ӷ���null����ʾ�Ҳ���������ֵ��������
			}
			
			/**
			 * ���������Ǻܾ���ش����ַ�������ķ�ʽ
			 * ��Ϊ����֪������value�Ƿ�����ȱʡ��ASCII�����������ɵ��ַ���������Ϊ�˻�ȡ���������������ԭʼ����������
			 * ���Ǿ���Ҫʹ��String���͵�getBytes����ָ�������ַ���ΪISO8859-1����ַ������б��룬�õ��ַ����飬Ҳ�����ֽڻ���
			 * Ȼ���½�һ��String���ͣ�ʹ��һ���������������Ĺ��������ֱ��� �ֽ����顢������� 
			 */
			try {
				byte[]  buffer =  value.getBytes("iso8859-1");
				// �����½��������ַ���������˴�ASCII��ת��ΪUTF-8����Ĺ�����
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
