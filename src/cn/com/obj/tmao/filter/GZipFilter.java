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
		// =============================过滤器的标准步骤============================
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) resp;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"GZipFilter->doFilter()->非HTTP协议");
		}

		// ==============================下面开始正经事儿============================
		// TODO GZIP编码的具体细节遗忘了，先放一放
		MyHttpServletResponse   myResponse=  new  MyHttpServletResponse(response);
		
		// 可以放行
		chain.doFilter(request, myResponse);
		
		System.out.println(request.getServletPath());
		System.out.println("压缩前数据大小为："+myResponse.getBuffer().length);
		// 最后的压缩处理
		ByteArrayOutputStream   gos  =  new ByteArrayOutputStream();
		GZIPOutputStream  gzip  =  new  GZIPOutputStream(gos);
		gzip.write(myResponse.getBuffer());
		gzip.finish();
		System.out.println("压缩后数据大小为："+gos.toByteArray().length);
		
		// 告诉浏览器，数据是经过GZIP压缩的，这样浏览器才知道在展示数据前需要先进行解压缩
		response.setHeader("Content-Encoding","gzip");
		// 最后将已经压缩完成字节流数据写入到真正用作想浏览器输出的response对象的输出流中去
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
