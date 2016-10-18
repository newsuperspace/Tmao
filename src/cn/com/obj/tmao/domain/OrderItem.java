package cn.com.obj.tmao.domain;

import java.io.Serializable;

public class OrderItem implements Serializable {

	private String id;   // ������Ŀ�ڶ�����Ŀ���е�id
	private int num;   // ������Ʒ������
	private float money;  // ��Ʒ���ܼ۸�
	private String bookid;   // ��Ʒ���ĸ�ͼ��
	private String ordersnum; // ��Ʒ��������
	
	
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", num=" + num + ", money=" + money
				+ ", bookid=" + bookid + ", ordersnum=" + ordersnum + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public String getOrdersnum() {
		return ordersnum;
	}
	public void setOrdersnum(String ordersnum) {
		this.ordersnum = ordersnum;
	}
	
	
	
}
