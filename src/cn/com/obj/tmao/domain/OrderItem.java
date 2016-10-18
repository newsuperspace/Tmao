package cn.com.obj.tmao.domain;

import java.io.Serializable;

public class OrderItem implements Serializable {

	private String id;   // 订单项目在订单项目表中的id
	private int num;   // 该类商品的数量
	private float money;  // 商品的总价格
	private String bookid;   // 商品是哪个图书
	private String ordersnum; // 商品所属订单
	
	
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
