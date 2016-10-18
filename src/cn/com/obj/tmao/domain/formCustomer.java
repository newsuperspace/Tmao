package cn.com.obj.tmao.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class formCustomer implements Serializable {

	
	private String id;   // servlet���������
	
	private String username;   // �û���д
	private String password;	// �û���д
	private String phone;	// �û���д
	private String address;	// �û���д
	private String email;	// �û���д
	
	// *JAVA�еĲ������Ͷ�ӦMySQL�е�BIT���ͣ���˿�����QueryRunner��ֱ��ʹ�ò�������Ϊsql����е�ռλ����ֵ
	private boolean actived;  // ����ע��Ĭ��Ϊ0 ��0δ����  1�Ѽ��� 2�����
	
	private String code;    // servlet���������
	private String level;    // �û�ѡ��
	
	private Map<String,String>  message = new HashMap<String,String>();
	
	
	
	@Override
	public String toString() {
		return "formCustomer [id=" + id + ", username=" + username
				+ ", password=" + password + ", phone=" + phone + ", address="
				+ address + ", email=" + email + ", actived=" + actived
				+ ", code=" + code + ", level=" + level + ", message="
				+ message + "]";
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, String> getMessage() {
		return message;
	}

}
