package cn.com.obj.tmao.exception;

/**
 * �������ServiceImplement��->saveBook() || updateBook() -> checkFormBook()
 * ���صĽ��Ϊfalse����ʾ�û�������ֶ����ݲ����Ϲ涨��ʽ������service����
 * servlet�׳������쳣��������ʽ������ȷ��
 * ���servlet���Ծ�����������������ת����ԭ��ҳ��������ݺ����û��������룬����
 * ��ת��ĳ������ָ��ҳ�棩
 * @author Administrator
 *
 */
public class FormBeanFormatException  extends Exception{

	public FormBeanFormatException() {
		super();
	}

	public FormBeanFormatException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FormBeanFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FormBeanFormatException(String arg0) {
		super(arg0);
	}

	public FormBeanFormatException(Throwable arg0) {
		super(arg0);
	}
	
}
