package cn.com.obj.tmao.domain;

import java.io.Serializable;

public class Orders implements Serializable {
	
	private String ordersnum;   // �����ţ�ʹ��System.curretnMillionTime()���ɵ�ϵͳʱ�������ֵ��
	private float money;        // �������ܷ��ã�������ļ�ֵ���ܺͣ�
	private int num;              // �����������Ķ��������� ����������Ʒ�������ۺϣ�
	private int status;           // ����״̬ 0=δ֧��  1=��֧��  2=��ɽ���  
	private String customerid;     // ���������û���ID   
	
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
