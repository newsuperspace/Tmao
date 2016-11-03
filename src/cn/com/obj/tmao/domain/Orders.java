package cn.com.obj.tmao.domain;

import java.io.Serializable;

public class Orders implements Serializable {
	
	private String ordersnum;   // 订单号（使用System.curretnMillionTime()生成的系统时间毫秒数值）
	private float money;        // 订单的总费用（订单项的价值的总和）
	private int num;              // 订单所包含的订单项数量 （订单项商品数量的综合）
	private int status;           // 订单状态 0=未支付  1=已支付  2=完成交易  
	private String customerid;     // 订单所属用户的ID   
	
	@Override
	public String toString() {
		return "Orders [ordersnum=" + ordersnum + ", money=" + money + ", num="
				+ num + ", status=" + status + ", customerid=" + customerid
				+ "]";
	}
	public String getOrdersnum() {
		return ordersnum;
	}
	public void setOrdersnum(String ordersnum) {
		this.ordersnum = ordersnum;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
}	
