package cn.com.obj.tmao.dao_impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.com.obj.tmao.dao_interface.DAOOrdersInterface;
import cn.com.obj.tmao.domain.Orders;
import cn.com.obj.tmao.manager.TransactionManager;

public class DAOOrdersImplement implements DAOOrdersInterface {

	private QueryRunner  runner =  new  QueryRunner();
	
	@Override
	public void save(Orders orders) {
		try {
			runner.update(TransactionManager.getConnection(), "INSERT INTO orders VALUES(?,?,?,?,?)",
					orders.getOrdersnum(),
					orders.getMoney(),
					orders.getNum(),
					orders.getStatus(),
					orders.getCustomerid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrdersImplement");
		}
	}

	@Override
	public void update(Orders orders) {
		try {
			runner.update(TransactionManager.getConnection(), "UPDATE orders SET money=?,num=?,status=? WHERE ordersnum = ? AND customerid = ?", 
					orders.getMoney(),
					orders.getNum(),
					orders.getStatus(),
					orders.getOrdersnum(),
					orders.getCustomerid()
					);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrdersImplement");
		}
	}

	@Override
	public void delete(String ordersnum) {
		try {
			runner.update(TransactionManager.getConnection(), "DELETE FROM orders WHERE ordersnum=?", 
					ordersnum);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrdersImplement");
		}
	}

	@Override
	public List<Orders> getAllOrders() {
		
		List<Orders>  list = null;
		
		try {
			list = runner.query(TransactionManager.getConnection(), "SELECT * FROM orders", new BeanListHandler<Orders>(Orders.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrdersImplement");
		}
		
		return list;
	}

	@Override
	public List<Orders> getOrdersByCustomerID(String customerid) {
		
		List<Orders> list = null;
		try {
			list = runner.query(TransactionManager.getConnection(), "SELECT * FROM orders WHERE customerid=?", 
					new BeanListHandler<Orders>(Orders.class), customerid );
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrdersImplement");
		}
		return list;
	}

}
