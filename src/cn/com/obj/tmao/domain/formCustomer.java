package cn.com.obj.tmao.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class formCustomer implements Serializable {

	
	private String id;   // servlet层随机生成
	
	private String username;   // 用户填写
	private String password;	// 用户填写
	private String phone;	// 用户填写
	private String address;	// 用户填写
	private String email;	// 用户填写
	
	// *JAVA中的布尔类型对应MySQL中的BIT类型，因此可以在QueryRunner中直接使用布尔类型为sql语句中的占位符赋值
	private boolean actived;  // 初次注册默认为0 （0未激活  1已激活 2封禁）
	
	private String code;    // servlet层随机生成
	private String level;    // 用户选择
	
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
