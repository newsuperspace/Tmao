package cn.com.obj.tmao.dao_impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.com.obj.tmao.dao_interface.DAOCustomerInterface;
import cn.com.obj.tmao.domain.Customer;
import cn.com.obj.tmao.manager.TransactionManager;

public class DAOCustomerImplement implements DAOCustomerInterface {

	private QueryRunner  runner =  new QueryRunner();
	
	@Override
	public Customer getCustomerById(String id) {
		Customer  customer = null;
		try {
			customer = runner.query(TransactionManager.getConnection(), "SELECT * FROM customer WHERE id=?", new BeanHandler<Customer>(Customer.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public void delete(String id) {
		try {
			runner.update(TransactionManager.getConnection(), "DELETE FROM customer WHERE id=?", id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCustomerImplement");
		}
	}

	@Override
	public void update(Customer customer) {
		try {
			runner.update(TransactionManager.getConnection(), "UPDATE customer SET username=?,password=?,phone=?,address=?,email=?,actived=?,code=?,level=? WHERE id=?", 
					customer.getUsername(),
					customer.getPassword(),
					customer.getPhone(),
					customer.getAddress(),
					customer.getEmail(),
					customer.isActived(),
					customer.getCode(),
					customer.getLevel(),
					customer.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCustomerImplement");
		}
	}

	@Override
	public void save(Customer customer) {
		try {
			runner.update(TransactionManager.getConnection(), "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)", 
					customer.getId(),
					customer.getUsername(),
					customer.getPassword(),
					customer.getPhone(),
					customer.getAddress(),
					customer.getEmail(),
					customer.isActived(),
					customer.getCode(),
					customer.getLevel());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCustomerImplement");
		}
	}

	@Override
	public Customer getgetCustomerByNameANDpwd(String username, String pwd) {
		Customer result = null;
		try {
			result = runner.query(TransactionManager.getConnection(), "SELECT * FROM customer WHERE username=? AND password=?", 
					new BeanHandler<Customer>(Customer.class), username,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCustomerImplement");
		}
		return result;
	}

	
	
}
