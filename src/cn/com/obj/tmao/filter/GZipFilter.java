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
		// TODO GZIP已经完成
		// 利用适配器技术（包装设计模式+继承思想）重新生成一个我们可以更好获取写入到reponse缓冲区数据的MyHttpServletResponse对象包装原response对象
		// 然后通过chain.doFilter()放行给下面的filter直到servlet，这样之后任何向response缓冲区中写入的数据都会被我们所截获。而我们截获这些数据的目的是
		// 先将数据Gzip压缩后在将已经压缩完毕的数据写入到原始response中去。
		MyHttpServletResponse   myResponse=  new  MyHttpServletResponse(response);
		
		// 原response已经包装完毕，可以放行
		chain.doFilter(request, myResponse);
		
		System.out.println(request.getServletPath());
		System.out.println("压缩前数据大小为："+myResponse.getBuffer().length);
		// 真正的Gzip压缩处理处理逻辑
		ByteArrayOutputStream   gos  =  new ByteArrayOutputStream();  // 这个ByteArrayOutputStream相比其他outPutStream能够更容易的获取字节数组（缓冲）
		GZIPOutputStream  gzip  =  new  GZIPOutputStream(gos);   // 新建一个Gzip专用输出流对象，并将需要承接压缩后输出流的流作为参数传递进去
		gzip.write(myResponse.getBuffer());        // 将需要压缩的数据流的字节数组（缓冲）提交给gzip的write方法完成压缩
		gzip.finish();   // 结束压缩过程
		System.out.println("压缩后数据大小为："+gos.toByteArray().length);    // 至此已经压缩好的数据都存放在gos输出流中
		
		// 告诉浏览器，数据是经过GZIP压缩的，这样浏览器才知道在展示数据前需要先进行解压缩
		response.setHeader("Content-Encoding","gzip");
		// 最后将已经压缩完成字节流数据写入到真正用作想浏览器输出的response对象的输出流中去
		response.getOutputStream().write(gos.toByteArray()); 
	}

	/**
	 * 第一层内部类
	 * 用于改造response的类，继承自传统的HttpServletResponseWrapper包装类，并通过覆盖关于获取response缓冲区的方法，
	 * 将数据写入引入到我们定义好的流中区，这一过程就叫做适配器过程。
	 * @author Administrator
	 *
	 */
	class MyHttpServletResponse  extends HttpServletResponseWrapper
	{

		ByteArrayOutputStream  baos  = null;    	// 对应 reponse.getOutputStream().write() 所写入到的 字节输出流
		PrintWriter   pw  =  null;							// 对应 repsonse.getWriter().write() 所写入到的 字符输出流
		
		/**
		 * 获取response的字节数组（字节缓冲区）对象，里面包含着所有写入到response缓冲区并与服务器组织Http协议响应正文的数据
		 * @return
		 */
		public  byte[]   getBuffer()
		{
			if(null!=pw)
			{
				pw.flush();  // 在获取输出缓冲区之前先确保pw中的数据已经完全输送到了 baos中了
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
			// 将原始response对象交给父类HttpServletResponseWrapper中去，这样不需要被覆盖的response的方法就会通过父类的同名方法调用原始response自己的
			super(response);  
			// 初始化我们自己用来替换原response输出流的输出流对象
			baos = new ByteArrayOutputStream();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			/*
			 * 将我们已经覆盖了原始ServletOutputStream的write方法的子类对象传递出去，这样
			 * 外部通过这个对象的write方法写入的数据流都会进入我们准备好的baos输出流中了。
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
				 * 这里需要解释下response的字节流和字符流的关系，其实本质上用来存放写入到response缓冲区的数据的输出流只有一个字节输出流
				 * 而所谓的PrintWriter字符输出流数据最后也会根据构建PrintWriter时候所指定的字符集(就是在response.setContentType("Content-Type;charset=utf-8")是偶设置的
				 * 这就是为什么在通过response.getWriter之前需要先字符集的原因)进行编码后传入到字节流中去的。
				 */
				pw = new PrintWriter(new OutputStreamWriter(baos, super.getCharacterEncoding()));
				return pw;
			}
		}
		
		/**
		 * 第二层内部类
		 * 通过继承的方式用来替换response.getOutputStream()所得到的输出流的write方法，
		 * 让其将数据写入到我们指定的baos流中去
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
