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
		 *输出到JspWriter中，等整个JSP页面的字符全都输出完毕后在统一输出到真正的用于输出到浏览器的流PrintWriter
		 *中去。因此这里一定要注意，如果我们直接将令牌的hidden节点输出到printWrite，则
		 *这段输出就会先于JspWriter的输出出现在整个HTML文档的最前面，这不是我们想看到的插入到HTML文档
		 *指定位置的结果。
		 */
		JspWriter out = page.getOut();
		
		// 首先借用UUID随机生成一个码
		String  token = UUID.randomUUID().toString();
		// 在jsp页面上插入一个hidden表单字段，字段名字就叫token（好区分），字段值就是随机生成的token码
		String content =  "<input  type=\"hidden\"   name=\"token\"   value=\""+token+"\" />";
		// 将上面组织好的标签字符串写入到JspWriter的字符输出流中去，该流在最后同意将内容写入到组织好的HTML页面的对应位置上，我们无需担心
		out.write(content);
		// 最后我们获取session对象，并在session中添加一个值，也是同样的token码
		HttpSession session = page.getSession();
		session.setAttribute("token", token);
		
		/**
		 * 这样包含有token标签的表单被提交的时候UUID码就会一同提交到服务器
		 * 服务器会在session中获取同为token名字的UUID码进行比对，比对一致就说明这次表单提交是首次提交，随后立刻remove掉
		 * session中的token码，然后执行表单提交的逻辑。这样随后重复提交过来的表单请求虽然也有token标签的UUID码但是由于
		 * 第一次提交的时候服务器就已经从session中移除了对应的UUID属性，这样重复的提交就不能通过过滤器的比对而被认为是重复提交
		 * 这样表单的重复提交问题就解决了。
		 */
		
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
