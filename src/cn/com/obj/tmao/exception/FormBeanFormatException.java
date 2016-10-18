package cn.com.obj.tmao.exception;

/**
 * 如果经过ServiceImplement类->saveBook() || updateBook() -> checkFormBook()
 * 返回的结果为false，表示用户输入的字段数据不符合规定格式，则会从service层向
 * servlet抛出此类异常，表明格式不在正确。
 * 随后servlet可以决定程序走向（是请求转发到原表单页面回填数据后让用户重新输入，还是
 * 跳转回某个其他指定页面）
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
