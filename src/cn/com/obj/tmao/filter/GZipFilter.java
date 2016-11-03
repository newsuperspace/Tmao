package cn.com.obj.tmao.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class GZipFilter implements Filter {

	@Override
	public void destroy() {}

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// =============================�������ı�׼����============================
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) resp;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"GZipFilter->doFilter()->��HTTPЭ��");
		}

		// ==============================���濪ʼ�����¶�============================
		// TODO GZIP�Ѿ����
		// ������������������װ���ģʽ+�̳�˼�룩��������һ�����ǿ��Ը��û�ȡд�뵽reponse���������ݵ�MyHttpServletResponse�����װԭresponse����
		// Ȼ��ͨ��chain.doFilter()���и������filterֱ��servlet������֮���κ���response��������д������ݶ��ᱻ�������ػ񡣶����ǽػ���Щ���ݵ�Ŀ����
		// �Ƚ�����Gzipѹ�����ڽ��Ѿ�ѹ����ϵ�����д�뵽ԭʼresponse��ȥ��
		MyHttpServletResponse   myResponse=  new  MyHttpServletResponse(response);
		
		// ԭresponse�Ѿ���װ��ϣ����Է���
		chain.doFilter(request, myResponse);
		
		System.out.println(request.getServletPath());
		System.out.println("ѹ��ǰ���ݴ�СΪ��"+myResponse.getBuffer().length);
		// ������Gzipѹ���������߼�
		ByteArrayOutputStream   gos  =  new ByteArrayOutputStream();  // ���ByteArrayOutputStream�������outPutStream�ܹ������׵Ļ�ȡ�ֽ����飨���壩
		GZIPOutputStream  gzip  =  new  GZIPOutputStream(gos);   // �½�һ��Gzipר����������󣬲�����Ҫ�н�ѹ���������������Ϊ�������ݽ�ȥ
		gzip.write(myResponse.getBuffer());        // ����Ҫѹ�������������ֽ����飨���壩�ύ��gzip��write�������ѹ��
		gzip.finish();   // ����ѹ������
		System.out.println("ѹ�������ݴ�СΪ��"+gos.toByteArray().length);    // �����Ѿ�ѹ���õ����ݶ������gos�������
		
		// ����������������Ǿ���GZIPѹ���ģ������������֪����չʾ����ǰ��Ҫ�Ƚ��н�ѹ��
		response.setHeader("Content-Encoding","gzip");
		// ����Ѿ�ѹ������ֽ�������д�뵽��������������������response������������ȥ
		response.getOutputStream().write(gos.toByteArray()); 
	}

	/**
	 * ��һ���ڲ���
	 * ���ڸ���response���࣬�̳��Դ�ͳ��HttpServletResponseWrapper��װ�࣬��ͨ�����ǹ��ڻ�ȡresponse�������ķ�����
	 * ������д�����뵽���Ƕ���õ�����������һ���̾ͽ������������̡�
	 * @author Administrator
	 *
	 */
	class MyHttpServletResponse  extends HttpServletResponseWrapper
	{

		ByteArrayOutputStream  baos  = null;    	// ��Ӧ reponse.getOutputStream().write() ��д�뵽�� �ֽ������
		PrintWriter   pw  =  null;							// ��Ӧ repsonse.getWriter().write() ��д�뵽�� �ַ������
		
		/**
		 * ��ȡresponse���ֽ����飨�ֽڻ������������������������д�뵽response�����������������֯HttpЭ����Ӧ���ĵ�����
		 * @return
		 */
		public  byte[]   getBuffer()
		{
			if(null!=pw)
			{
				pw.flush();  // �ڻ�ȡ���������֮ǰ��ȷ��pw�е������Ѿ���ȫ���͵��� baos����
			}
			try {
				baos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return baos.toByteArray();
		}
		
		/**
		 *  Constructor
		 * @param response
		 */
		public MyHttpServletResponse(HttpServletResponse response) {
			// ��ԭʼresponse���󽻸�����HttpServletResponseWrapper��ȥ����������Ҫ�����ǵ�response�ķ����ͻ�ͨ�������ͬ����������ԭʼresponse�Լ���
			super(response);  
			// ��ʼ�������Լ������滻ԭresponse����������������
			baos = new ByteArrayOutputStream();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			/*
			 * �������Ѿ�������ԭʼServletOutputStream��write������������󴫵ݳ�ȥ������
			 * �ⲿͨ����������write����д��������������������׼���õ�baos��������ˡ�
			 */
			return new MyServletOutputStream();    
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if(null!=pw)
			{
				return pw;
				
			}else
			{
				/*
				 * ������Ҫ������response���ֽ������ַ����Ĺ�ϵ����ʵ�������������д�뵽response�����������ݵ������ֻ��һ���ֽ������
				 * ����ν��PrintWriter�ַ�������������Ҳ����ݹ���PrintWriterʱ����ָ�����ַ���(������response.setContentType("Content-Type;charset=utf-8")��ż���õ�
				 * �����Ϊʲô��ͨ��response.getWriter֮ǰ��Ҫ���ַ�����ԭ��)���б�����뵽�ֽ�����ȥ�ġ�
				 */
				pw = new PrintWriter(new OutputStreamWriter(baos, super.getCharacterEncoding()));
				return pw;
			}
		}
		
		/**
		 * �ڶ����ڲ���
		 * ͨ���̳еķ�ʽ�����滻response.getOutputStream()���õ����������write������
		 * ���佫����д�뵽����ָ����baos����ȥ
		 * @author Administrator
		 *
		 */
		class MyServletOutputStream extends ServletOutputStream
		{
			@Override
			public void write(int arg0) throws IOException {
				baos.write(arg0);
			}
		}
	}
	
	
	@Override
	public void init(FilterConfig config) throws ServletException {	}

}
