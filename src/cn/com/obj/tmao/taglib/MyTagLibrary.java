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
		 *�����JspWriter�У�������JSPҳ����ַ�ȫ�������Ϻ���ͳһ�����������������������������PrintWriter
		 *��ȥ���������һ��Ҫע�⣬�������ֱ�ӽ����Ƶ�hidden�ڵ������printWrite����
		 *�������ͻ�����JspWriter���������������HTML�ĵ�����ǰ�棬�ⲻ�������뿴���Ĳ��뵽HTML�ĵ�
		 *ָ��λ�õĽ����
		 */
		JspWriter out = page.getOut();
		
		// ���Ƚ���UUID�������һ����
		String  token = UUID.randomUUID().toString();
		// ��jspҳ���ϲ���һ��hidden���ֶΣ��ֶ����־ͽ�token�������֣����ֶ�ֵ����������ɵ�token��
		String content =  "<input  type=\"hidden\"   name=\"token\"   value=\""+token+"\" />";
		// ��������֯�õı�ǩ�ַ���д�뵽JspWriter���ַ��������ȥ�����������ͬ�⽫����д�뵽��֯�õ�HTMLҳ��Ķ�Ӧλ���ϣ��������赣��
		out.write(content);
		// ������ǻ�ȡsession���󣬲���session�����һ��ֵ��Ҳ��ͬ����token��
		HttpSession session = page.getSession();
		session.setAttribute("token", token);
		
		/**
		 * ����������token��ǩ�ı����ύ��ʱ��UUID��ͻ�һͬ�ύ��������
		 * ����������session�л�ȡͬΪtoken���ֵ�UUID����бȶԣ��ȶ�һ�¾�˵����α��ύ���״��ύ���������remove��
		 * session�е�token�룬Ȼ��ִ�б��ύ���߼�����������ظ��ύ�����ı�������ȻҲ��token��ǩ��UUID�뵫������
		 * ��һ���ύ��ʱ����������Ѿ���session���Ƴ��˶�Ӧ��UUID���ԣ������ظ����ύ�Ͳ���ͨ���������ıȶԶ�����Ϊ���ظ��ύ
		 * ���������ظ��ύ����ͽ���ˡ�
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
