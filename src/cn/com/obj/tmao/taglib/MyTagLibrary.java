package cn.com.obj.tmao.taglib;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;

public class MyTagLibrary implements SimpleTag {

	private  PageContext  page  =  null;
	
	@Override
	public void setJspContext(JspContext arg0) {
		// PageContext继承自JspContext,对JspContext进行了拓展
		page =  (PageContext) arg0;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		/**
		 * 通常请情况下JSP页面上的JspWriter类型的out输出对象可以等同的看成是通过response.getWriter()
		 * 获得PrintWriter类型对象，但从类型上我们就可以看出两个输出流并不相同。
		 *JspWriter实际上是作为PrintWriter的缓冲输出流存在的，也就是说，在JSP页面上的字符流输出会首先
		 *输出到JspWriter中，等整个JSP页面的字符全都输出完毕后在统一输出到真正的浏览器输出流PrintWriter
		 *中去。因此这里一定要注意，如果我们直接将令牌的hidden节点输出到printWrite，则
		 *这段输出就会先于JspWriter的输出出现在整个HTML文档的最前面，这不是我们想看到的插入到HTML文档
		 *指定位置的结果。
		 */
		JspWriter out = page.getOut();
		
		String  token = UUID.randomUUID().toString();
		String content =  "<input  type=\"hidden\"   name=\"token\"   value=\""+token+"\" />";
		out.write(content);
		
		HttpSession session = page.getSession();
		session.setAttribute("token", token);
		
	}

	@Override
	public JspTag getParent() {
		return null;
	}

	@Override
	public void setJspBody(JspFragment arg0) {

	}

	@Override
	public void setParent(JspTag arg0) {

	}

}
