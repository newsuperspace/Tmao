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
	 * �洢�鼮
	 * @param book
	 * @throws FormBeanFormatException 
	 */
	public  void saveBook(formBook formbook) throws FormBeanFormatException;
	
	/**
	 * �޸�ͼ����Ϣ
	 * @param book
	 * @throws FormBeanFormatException 
	 */
	public  void  updateBook(formBook formbook) throws FormBeanFormatException;
	
	/**
	 * ����ͼ��IDɾ��ͼ��
	 * @param id
	 */
	public  void deleteBook(String id);
	
	
	/**
	 * ͨ���鼮��ID��ȡָ����һ����
	 * @param id
	 */
	public  Book getBookByID(String  id);
	
	/**
	 * ʹ�÷�ҳ���ƻ�ȡָ�����䷶Χ�ڵ��鼮����
	 * @param currentPageNumber
	 * @return
	 */
	public  List<Book>  getBooksBylimit(int  currentPageIndex,int everyPageItemNumbers); 
	
	/**
	 * ��ȡ�������ݵ�����
	 * @return
	 */
	public  long getTotalBookItems();
	
	/**
	 * ��ȡ��ҳ����ҳ��
	 * @return
	 */
	public  long  getTotalBookPages(int everyPageItemNumbers);
	
	
	
	/**
	 * ������������
	 * @param category
	 */
	public void saveCategory(Category  category);
	
	/**
	 * ��������idɾ��������
	 * @param id
	 */
	public  void  deleteCategory(String id);
	
	/**
	 * ִ�и��²���s
	 * @param category
	 */
	public  void  update(Category  category);
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public  List<Category>  getAllCategory();
	
	/**
	 * ����ָ��ID��������
	 * @param id
	 * @return
	 */
	public Category   getCategoryById(String id);
	
	
	
	/**
	 * �����¿ͻ�
	 * @param customer
	 * @throws FormBeanFormatException 
	 */
	public void saveCustomer(formCustomer formcustomer) throws FormBeanFormatException;
	
	/**
	 * �����û�IDִ��ɾ������
	 * @param id
	 */
	public  void deleteCustomer(String id);
	
	/**
	 * ִ�и����û�����
	 * @param customer
	 * @throws FormBeanFormatException 
	 */
	public  void updateCustomer(formCustomer formcustomer) throws FormBeanFormatException;
	
	/**
	 * �����û�IDִ�в���
	 * @param id
	 * @return
	 */
	public  Customer getCustomerById(String  id);
	
	
	/**
	 * �����¶������������ݿ�
	 * @param orders
	 */
	public  void saveOrders(Orders orders);
	
	/**
	 * ���¶���
	 * @param orders
	 */
	public  void updateOrders(Orders orders);
	
	/**
	 *  ���ݶ�����ţ�ɾ������
	 * @param ordersnum
	 */
	public  void deleteOrders(String ordersnum);
	
	/**
	 * ��ȡȫ������
	 * @return
	 */
	public  List<Orders>  getAllOrders();
	
	
	/**
	 * �����û�ID��ȡ���û���ȫ������
	 * @param customerid
	 * @return
	 */
	public  List<Orders>  getOrdersByCustomerID(String customerid);
	
	
	/**
	 *	���涩������ݿ� 
	 * @param item
	 */
	public void saveOrderItem(OrderItem  item);
	/**
	 * ���¶�����
	 * @param item
	 */
	public  void  updateOrderItem(OrderItem item);
	/**
	 * ���ݶ�����IDɾ��������
	 * @param id
	 */
	public  void deleteOrderItem(String id);
	/**
	 * ���ݶ�����Ż�ȡ�ö����µ����ж�����Ŀ
	 * @param ordernum
	 */
	public List<OrderItem> getOrderItemsByOrdersNum(String ordersnum);
	/**
	 * �����鼮ID��ȡ����鼮�йص����ж�����
	 * @param id
	 */
	public  List<OrderItem>  getOrderItemsByBookId(String id);

	List<Book> getAllBooks();
}
