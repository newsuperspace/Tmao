package cn.com.obj.tmao.service_interface;

import java.util.List;

import cn.com.obj.tmao.domain.Book;
import cn.com.obj.tmao.domain.Category;
import cn.com.obj.tmao.domain.Customer;
import cn.com.obj.tmao.domain.OrderItem;
import cn.com.obj.tmao.domain.Orders;
import cn.com.obj.tmao.domain.formBook;
import cn.com.obj.tmao.domain.formCustomer;
import cn.com.obj.tmao.exception.FormBeanFormatException;

public interface ServiceInterface {

	
	/**
	 * 存储书籍
	 * @param book
	 * @throws FormBeanFormatException 
	 */
	public  void saveBook(formBook formbook) throws FormBeanFormatException;
	
	/**
	 * 修改图书信息
	 * @param book
	 * @throws FormBeanFormatException 
	 */
	public  void  updateBook(formBook formbook) throws FormBeanFormatException;
	
	/**
	 * 根据图书ID删除图书
	 * @param id
	 */
	public  void deleteBook(String id);
	
	
	/**
	 * 通过书籍的ID获取指定的一本书
	 * @param id
	 */
	public  Book getBookByID(String  id);
	
	/**
	 * 使用分页机制获取指定区间范围内的书籍数据
	 * @param currentPageNumber
	 * @return
	 */
	public  List<Book>  getBooksBylimit(int  currentPageIndex,int everyPageItemNumbers); 
	
	/**
	 * 获取所有数据的总数
	 * @return
	 */
	public  long getTotalBookItems();
	
	/**
	 * 获取分页的总页数
	 * @return
	 */
	public  long  getTotalBookPages(int everyPageItemNumbers);
	
	
	
	/**
	 * 增加新书类型
	 * @param category
	 */
	public void saveCategory(Category  category);
	
	/**
	 * 根据类型id删除书类型
	 * @param id
	 */
	public  void  deleteCategory(String id);
	
	/**
	 * 执行更新操作s
	 * @param category
	 */
	public  void  update(Category  category);
	
	/**
	 * 获取所有类型
	 * @return
	 */
	public  List<Category>  getAllCategory();
	
	/**
	 * 返回指定ID的书类型
	 * @param id
	 * @return
	 */
	public Category   getCategoryById(String id);
	
	
	
	/**
	 * 增加新客户
	 * @param customer
	 * @throws FormBeanFormatException 
	 */
	public void saveCustomer(formCustomer formcustomer) throws FormBeanFormatException;
	
	/**
	 * 根据用户ID执行删除功能
	 * @param id
	 */
	public  void deleteCustomer(String id);
	
	/**
	 * 执行更新用户操作
	 * @param customer
	 * @throws FormBeanFormatException 
	 */
	public  void updateCustomer(formCustomer formcustomer) throws FormBeanFormatException;
	
	/**
	 * 根据用户ID执行查找
	 * @param id
	 * @return
	 */
	public  Customer getCustomerById(String  id);
	
	
	/**
	 * 保存新订单到订单数据库
	 * @param orders
	 */
	public  void saveOrders(Orders orders);
	
	/**
	 * 更新订单
	 * @param orders
	 */
	public  void updateOrders(Orders orders);
	
	/**
	 *  根据订单编号，删除订单
	 * @param ordersnum
	 */
	public  void deleteOrders(String ordersnum);
	
	/**
	 * 获取全部订单
	 * @return
	 */
	public  List<Orders>  getAllOrders();
	
	
	/**
	 * 根据用户ID获取该用户的全部订单
	 * @param customerid
	 * @return
	 */
	public  List<Orders>  getOrdersByCustomerID(String customerid);
	
	
	/**
	 *	保存订单项到数据库 
	 * @param item
	 */
	public void saveOrderItem(OrderItem  item);
	/**
	 * 更新订单项
	 * @param item
	 */
	public  void  updateOrderItem(OrderItem item);
	/**
	 * 根据订单项ID删除订单项
	 * @param id
	 */
	public  void deleteOrderItem(String id);
	/**
	 * 根据订单编号获取该订单下的所有订单项目
	 * @param ordernum
	 */
	public List<OrderItem> getOrderItemsByOrdersNum(String ordersnum);
	/**
	 * 根据书籍ID获取与该书籍有关的所有订单项
	 * @param id
	 */
	public  List<OrderItem>  getOrderItemsByBookId(String id);

	List<Book> getAllBooks();
}
