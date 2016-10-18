package cn.com.obj.tmao.dao_interface;

import cn.com.obj.tmao.domain.Customer;

public interface DAOCustomerInterface {

	Customer getCustomerById(String id);

	void delete(String id);

	void update(Customer customer);

	void save(Customer customer);

	Customer getgetCustomerByNameANDpwd(String username, String pwd);

	
}
