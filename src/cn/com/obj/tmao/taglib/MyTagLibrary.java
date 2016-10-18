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
		// PageContext�̳���JspContext,��JspContext��������չ
		page =  (PageContext) arg0;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		/**
		 * ͨ���������JSPҳ���ϵ�JspWriter���͵�out���������Ե�ͬ�Ŀ�����ͨ��response.getWriter()
		 * ���PrintWriter���Ͷ��󣬵������������ǾͿ��Կ������������������ͬ��
		 *JspWriterʵ��������ΪPrintWriter�Ļ�����������ڵģ�Ҳ����˵����JSPҳ���ϵ��ַ������������
		 *�����JspWriter�У�������JSPҳ����ַ�ȫ�������Ϻ���ͳһ���������������������PrintWriter
		 *��ȥ���������һ��Ҫע�⣬�������ֱ�ӽ����Ƶ�hidden�ڵ������printWrite����
		 *�������ͻ�����JspWriter���������������HTML�ĵ�����ǰ�棬�ⲻ�������뿴���Ĳ��뵽HTML�ĵ�
		 *ָ��λ�õĽ����
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
