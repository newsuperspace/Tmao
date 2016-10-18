package cn.com.obj.tmao.dao_interface;

import java.util.List;

import cn.com.obj.tmao.domain.Orders;

public interface DAOOrdersInterface {

	void save(Orders orders);

	void update(Orders orders);

	void delete(String ordersnum);

	List<Orders> getAllOrders();

	List<Orders>  getOrdersByCustomerID(String customerid);
}
