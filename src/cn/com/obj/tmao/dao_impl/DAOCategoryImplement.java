package cn.com.obj.tmao.dao_impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.com.obj.tmao.dao_interface.DAOCategoryInterface;
import cn.com.obj.tmao.domain.Category;
import cn.com.obj.tmao.manager.TransactionManager;
import cn.com.obj.tmao.utils.DBCPUtils;

public class DAOCategoryImplement implements DAOCategoryInterface {

	private  QueryRunner  runner =  new QueryRunner();
	
	@Override
	public void save(Category category) {
		try {
			runner.update(TransactionManager.getConnection(),
					"INSERT INTO category (id,name,description) VALUES(?,?,?)", 
					category.getId(),category.getName(),category.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCategoryImplement->save()->�����쳣");
		}
	}

	@Override
	public void update(Category category) {
		try {
			runner.update(TransactionManager.getConnection(),
					"UPDATE category SET name=?,description=? WHERE id=?", 
					category.getName(),category.getDescription(),category.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCategoryImplement->update()->�����쳣");
		}
	}

	@Override
	public List<Category> getAllCategory() {

		List<Category>  list  =  null;
		try {
			list = new ArrayList<Category>();
			list = runner.query(TransactionManager.getConnection(), 
					"SELECT * FROM category",
					new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCategoryImplement->getAllCategory()->�����쳣");
		}
		return list;
	}

	@Override
	public Category getCategoryById(String id) {
		Category  category  = null;
		try {
			category  = runner.query(TransactionManager.getConnection(), 
					"SELECT * FROM category WHERE id=?",
					new BeanHandler<Category>(Category.class),
					id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCategoryImplement->getCategoryById()->�����쳣");
		}
		return category;
	}

	@Override
	public void delete(String id) {
		
		try {
			runner.update(TransactionManager.getConnection(), "DELETE FROM category WHERE id=?", id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOCategoryImplement->delete()->�����쳣");
		}
		
	}

}
