package cn.com.obj.tmao.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class formBook implements Serializable {

	
	private String id;
	private String name;
	private String author;
	private float money;
	private String description;
	private String path;
	private String oldImageName;
	private String newImageName;
	private String categoryId;
	
	// 用来存放与 属性相关的错误或提示信息，用于显示到JSP页面上提示用户
	// 键名与上方属性名相同，值就是错误提示信息
	private  Map<String,String>  message = new HashMap<String,String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void stMoney(float f)
	{
		money = f;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOldImageName() {
		return oldImageName;
	}

	public void setOldImageName(String oldImageName) {
		this.oldImageName = oldImageName;
	}

	public String getNewImageName() {
		return newImageName;
	}

	public void setNewImageName(String newImageName) {
		this.newImageName = newImageName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Map<String, String> getMessage() {
		return message;
	}
}
