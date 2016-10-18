package cn.com.obj.tmao.dao_impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.com.obj.tmao.dao_interface.DAOOrderItemInterface;
import cn.com.obj.tmao.domain.OrderItem;
import cn.com.obj.tmao.manager.TransactionManager;

public class DAOOrderItemImplement implements DAOOrderItemInterface {

	private QueryRunner  runner =  new QueryRunner();
	
	@Override
	public void save(OrderItem item) {
		try {
			runner.update(TransactionManager.getConnection(), "INSERT INTO orderitem VALUES(?,?,?,?,?)", 
					item.getId(),
					item.getNum(),
					item.getMoney(),
					item.getBookid(),
					item.getOrdersnum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrderItemImplement");
		}
	}

	@Override
	public void update(OrderItem item) {

		try {
			runner.update(TransactionManager.getConnection(), "UPDATE orderitem SET num=?,money=? WHERE bookid=? AND ordersnum=?", 
					item.getNum(),
					item.getMoney(),
					item.getBookid(),
					item.getOrdersnum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrderItemImplement");
		}
		
	}

	@Override
	public void delete(String id) {

		try {
			runner.update(TransactionManager.getConnection(),"DELETE FROM orderitem WHERE id=?",
					id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrderItemImplement");
		}
	}

	@Override
	public List<OrderItem> getOrderItemsByOrdersNum(String ordersnum) {

		List<OrderItem>  list = null;
		try {
			list = runner.query(TransactionManager.getConnection(), "SELECT * FROM orderitem WHERE ordersnum=?", new BeanListHandler<OrderItem>(OrderItem.class),ordersnum);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrderItemImplement");
		}
		return list;
	}

	@Override
	public List<OrderItem> getOrderItemsByBookId(String bookid) {
		
		List<OrderItem>  list = null;
		try {
			list = runner.query(TransactionManager.getConnection(), "SELECT * FROM orderitem WHERE bookid=?", new BeanListHandler<OrderItem>(OrderItem.class),bookid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOOrderItemImplement");
		}
		return list;
	}

}
