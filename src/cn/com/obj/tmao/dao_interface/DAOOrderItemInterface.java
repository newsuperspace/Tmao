package cn.com.obj.tmao.dao_interface;

import java.util.List;

import cn.com.obj.tmao.domain.OrderItem;

public interface DAOOrderItemInterface {

	void save(OrderItem item);

	void update(OrderItem item);

	void delete(String id);

	List<OrderItem> getOrderItemsByOrdersNum(String ordersnum);

	List<OrderItem> getOrderItemsByBookId(String bookid);

}
