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
		// TODO GZIP����ľ���ϸ�������ˣ��ȷ�һ��
		MyHttpServletResponse   myResponse=  new  MyHttpServletResponse(response);
		
		// ���Է���
		chain.doFilter(request, myResponse);
		
		System.out.println(request.getServletPath());
		System.out.println("ѹ��ǰ���ݴ�СΪ��"+myResponse.getBuffer().length);
		// ����ѹ������
		ByteArrayOutputStream   gos  =  new ByteArrayOutputStream();
		GZIPOutputStream  gzip  =  new  GZIPOutputStream(gos);
		gzip.write(myResponse.getBuffer());
		gzip.finish();
		System.out.println("ѹ�������ݴ�СΪ��"+gos.toByteArray().length);
		
		// ����������������Ǿ���GZIPѹ���ģ������������֪����չʾ����ǰ��Ҫ�Ƚ��н�ѹ��
		response.setHeader("Content-Encoding","gzip");
		// ����Ѿ�ѹ������ֽ�������д�뵽��������������������response������������ȥ
		response.getOutputStream().write(gos.toByteArray());
	}

	class MyHttpServletResponse  extends HttpServletResponseWrapper
	{

		ByteArrayOutputStream  baos  = null;
		PrintWriter   pw  =  null;
		
		public  byte[]   getBuffer()
		{
			if(null!=pw)
			{
				pw.flush();
			}
			try {
				baos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return baos.toByteArray();
		}
		
		public MyHttpServletResponse(HttpServletResponse response) {
			super(response);
			baos = new ByteArrayOutputStream();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new MyServletOutputStream();
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			if(null!=pw)
			{
				return pw;
				
			}else
			{
				pw = new PrintWriter(new OutputStreamWriter(baos, super.getCharacterEncoding()));
				return pw;
			}
		}
		
		
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
