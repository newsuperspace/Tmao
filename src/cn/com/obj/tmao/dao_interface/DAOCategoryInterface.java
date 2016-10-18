package cn.com.obj.tmao.dao_interface;

import java.util.List;

import cn.com.obj.tmao.domain.Category;

public interface DAOCategoryInterface {
	
	public  void  save(Category category);
	public  void  delete(String id);
	public  void update(Category category);
	public List<Category>  getAllCategory();
	public  Category getCategoryById(String id);
	
	
	
	
}
