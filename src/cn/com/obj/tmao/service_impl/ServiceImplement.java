package cn.com.obj.tmao.service_impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.com.obj.tmao.dao_interface.DAOBookInterface;
import cn.com.obj.tmao.dao_interface.DAOCategoryInterface;
import cn.com.obj.tmao.dao_interface.DAOCustomerInterface;
import cn.com.obj.tmao.dao_interface.DAOOrderItemInterface;
import cn.com.obj.tmao.dao_interface.DAOOrdersInterface;
import cn.com.obj.tmao.domain.Book;
import cn.com.obj.tmao.domain.Category;
import cn.com.obj.tmao.domain.Customer;
import cn.com.obj.tmao.domain.OrderItem;
import cn.com.obj.tmao.domain.Orders;
import cn.com.obj.tmao.domain.formBook;
import cn.com.obj.tmao.domain.formCustomer;
import cn.com.obj.tmao.exception.FormBeanFormatException;
import cn.com.obj.tmao.factory.DaoFactory;
import cn.com.obj.tmao.service_interface.ServiceInterface;
import cn.com.obj.tmao.utils.MyBeanUtils;

public class ServiceImplement implements ServiceInterface {

	private DaoFactory   daoFactory = null;
	{
		daoFactory =  DaoFactory.getDAOFactory();  
	}
	
	// ����DAOFactory��service����DAO��֮��Ľ��Ԫ������ȡDAOʵ��
	// ������ǰ��service��Ͳ���DAO֮������ˣ�����������˽���
	private  DAOBookInterface   daobook =  daoFactory.createBookDAO();
	private DAOCategoryInterface daocategory =  daoFactory.createCategoryDAO();
	private  DAOCustomerInterface  daocustomer = daoFactory.createCustomerDAO();
	private DAOOrdersInterface   daoorders  =  daoFactory.createOrdersDAO();
	private DAOOrderItemInterface  daoorderitem  =  daoFactory.createOrderItemDAO();
	
	// =============================����Book��ҵ���߼�====================================
	@Override
	public void saveBook(formBook formbook) throws FormBeanFormatException {
		
		Book  book  =  null;
		
		boolean  b  =  checkFormBook(formbook);
		if(b)
		{
			// ��ʽ���ͨ��
			book =  new Book();
			MyBeanUtils.formBean2DAObean(book, formbook);
			daobook.save(book);
		}
		else
		{
			// ��ʽ����ȷ���׳��쳣
			// ���쳣���ᱻservlet�㲶��
			// ���ڵ�ǰ������������formBook���Bean�������servlet����������з�װ�ģ�����֮��ʱ
			// service������У������е����formBean��servlet����ͬ������
			// ������serviceУ����д�뵽���bean�еĴ�����ʾ��Ϣ��servlet��Ҳ�ܵõ�������
			// servlet�Ὣ���formBeanֱ�ӷ��뵽request����ת�������У�Ȼ������ת����ԭ��ҳ��
			// ��ҳ���ϵ�EL���ʽ�ͻ��Զ���request�еõ����formBeanȻ�����е����ݻָ���������Ϊ���ݻ���
			// ���ҰѴ�����ʾ��ϢҲ��ʾ������
			throw new FormBeanFormatException("saveBook()������formBook���ֶθ�ʽ������Ҫ��");
		}
	}

	/**
	 * ����ʽformBook�з�װ���ֶ��Ƿ���ϸ�ʽҪ��
	 * @param book
	 * @return  �ϸ񷵻�true�����򷵻�false
	 */
	private boolean checkFormBook(formBook book) {

		boolean  result  =  true;
		
		if(null==book.getId()||"".equals(book.getId()))
		{
			// �û�ID����Ϊ��
			book.getMessage().put("id", "id����Ϊ��");
			result = false;
		}
		
		if(null==book.getName()||"".equals(book.getName()))
		{
			// �û�name����Ϊ��
			book.getMessage().put("name", "name����Ϊ��");
			result = false;
		}
		
		if(null==book.getAuthor()||"".equals(book.getAuthor()))
		{
			// �û�author����Ϊ��
			book.getMessage().put("author", "����Ϊ��");
			result = false;
		}
		
		if(book.getMoney()<=0)
		{
			// �û�money�ļ۸���ȷ
			book.getMessage().put("money", "�۸���ȷ");
			result = false;
		}
		if(null==book.getDescription()||"".equals(book.getDescription()))
		{
			// �û�description����Ϊ��
			book.getMessage().put("description", "�鼮��������Ϊ��");
			result = false;
		}
		if(null==book.getPath()||"".equals(book.getPath()))
		{
			// �û�path����Ϊ��
			book.getMessage().put("path", "���ϴ�һ���鼮ͼƬ");
			result = false;
		}
		if(null==book.getOldImageName()||"".equals(book.getOldImageName()))
		{
			// �û�oldImageName����Ϊ��
			book.getMessage().put("oldImageName", " ԭͼƬ���Ʋ���Ϊ��");
			result = false;
		}
		if(null==book.getNewImageName()||"".equals(book.getNewImageName()))
		{
			// �û�newImageName����Ϊ��
			book.getMessage().put("newImageName", "�¶���ͼƬ���Ʋ���Ϊ��");
			result = false;
		}
		if(null==book.getCategoryId()||"".equals(book.getCategoryId()))
		{
			// �û�categoryId����Ϊ��
			book.getMessage().put("categoryId", "�鼮�������಻��Ϊ��");
			result = false;
		}
		return result;
	}

	@Override
	public void updateBook(formBook formbook) throws FormBeanFormatException {
		if(checkFormBook(formbook))
		{
			Book  book  =  new  Book();
			MyBeanUtils.formBean2DAObean(book,formbook);
			daobook.update(book);
		}
		else{
			throw new FormBeanFormatException("updateBook()������formBook���ֶθ�ʽ������Ҫ��");
		}
	}

	@Override
	public void deleteBook(String id) {
		daobook.delete(id);
	}

	@Override
	public Book getBookByID(String id) {
		return daobook.getBookByID(id);
	}
	
	@Override
	public List<Book>  getAllBooks()
	{
		return daobook.getAllBooks();
	}
	
	@Override
	public List<Book> getBooksBylimit(int currentPageIndex,
			int everyPageItemNumbers) {
		return daobook.getBooksBylimit(currentPageIndex, everyPageItemNumbers);
	}

	@Override
	public long getTotalBookItems() {
		return daobook.getTotalBookItems();
	}

	@Override
	public long getTotalBookPages(int everyPageItemNumbers) {
		return daobook.getTotalBookPages(everyPageItemNumbers);
	}

	
	
	//===============================����Category��ҵ���߼�===============================
	// =========�ϸ���˵Ӧ��Ҳ��Book�����Դӱ��ύ������formBook����������Ч�Լ�⣬������ʡ����===========
	@Override
	public void saveCategory(Category category) {
		daocategory.save(category);
	}

	@Override
	public void deleteCategory(String id) {
		daocategory.delete(id);
	}

	@Override
	public void update(Category category) {
		daocategory.update(category);
	}

	@Override
	public List<Category> getAllCategory() {
		return daocategory.getAllCategory();
	}

	@Override
	public Category getCategoryById(String id) {
		return daocategory.getCategoryById(id);
	}

	
	// =============================customer===============================
	// ====�ϸ���˵Ӧ��Ҳ��Book�����Դӱ��ύ������formBook����������Ч�Լ�⣬������ʡ����=====
	
	@Override
	public void saveCustomer(formCustomer formcustomer) throws FormBeanFormatException {
		if(checkFormCustomer(formcustomer))
		{
			Customer  customer = new Customer();
			MyBeanUtils.formBean2DAObean(customer, formcustomer);
			daocustomer.save(customer);
		}
		else
		{
			throw new FormBeanFormatException("saveCustomer()������formcustomer�����ݸ�ʽ����ȷ");
		}
	}

	/**
	 * У���ǰ�˱��ύ��������servlet���װ��formCustomer�������Ƿ�
	 * ���ϱ�������ݿ�ı�׼
	 * @param formcustomer
	 * @return   true ���ϱ�׼��false�����ϱ�׼
	 */
	private boolean checkFormCustomer(formCustomer formcustomer)
	{
		boolean  correctness = true;
		
		if(null==formcustomer.getId()||"".equals(formcustomer.getId()))
		{
			// idΪ��
			formcustomer.getMessage().put("id", "id����Ϊ��");
			correctness = false;
		}
		if(null==formcustomer.getUsername()||"".equals(formcustomer.getUsername()))
		{
			// usernameΪ��
			formcustomer.getMessage().put("username", "username����Ϊ��");
			correctness = false;
		}
		if(null==formcustomer.getPassword()||"".equals(formcustomer.getPassword()))
		{
			// passwordΪ��
			formcustomer.getMessage().put("password", "password����Ϊ��");
			correctness = false;
		}
		if(null==formcustomer.getPhone()||"".equals(formcustomer.getPhone()))
		{
			// phoneΪ��
			formcustomer.getMessage().put("phone", "phone����Ϊ��");
			correctness = false;
		}
		if(null==formcustomer.getAddress()||"".equals(formcustomer.getAddress()))
		{
			// addressΪ��
			formcustomer.getMessage().put("address", "address����Ϊ��");
			correctness = false;
		}
		if(null==formcustomer.getEmail()||"".equals(formcustomer.getEmail()))
		{
			// emailΪ��
			formcustomer.getMessage().put("email", "email����Ϊ��");
			correctness = false;
		}
		if(null==formcustomer.getCode()||"".equals(formcustomer.getCode()))
		{
			// codeΪ��
			formcustomer.getMessage().put("code", "code����Ϊ��");
			correctness = false;
		}
		
		Set<String>  set =  new HashSet<String>();
		set.add("vip");
		set.add("normal");
		if(null==formcustomer.getLevel()||"".equals(formcustomer.getLevel()))
		{
			// levelΪ��
			formcustomer.getMessage().put("level", "level����Ϊ��");
			correctness = false;
		}
		else if(!set.contains(formcustomer.getLevel()))
		{
			// �û��������vip��normal�е��κ�һ��
			formcustomer.getMessage().put("level", "level���Ͳ�ƥ��");
			correctness = false;
		}
			
			
		return correctness;
	}
	
	
	@Override
	public void deleteCustomer(String id) {
		daocustomer.delete(id);
	}

	@Override
	public void updateCustomer(formCustomer formcustomer) throws FormBeanFormatException {
		if(checkFormCustomer(formcustomer))
		{
			Customer  customer = new Customer();
			MyBeanUtils.formBean2DAObean(customer, formcustomer);
			daocustomer.update(customer);
		}
		else
		{
			throw new FormBeanFormatException("updateCustomer()������formcustomer�����ݸ�ʽ����ȷ");
		}
	}

	@Override
	public Customer getCustomerById(String id) {
		return daocustomer.getCustomerById(id);
	}

	public  Customer getCustomerByNameANDpwd(String username,String pwd)
	{
		return daocustomer.getgetCustomerByNameANDpwd(username, pwd);
	}
	
	
	//==============================orders==============================
	// ====�ϸ���˵Ӧ��Ҳ��Book�����Դӱ��ύ������formBook����������Ч�Լ�⣬������ʡ����===
	
	@Override
	public void saveOrders(Orders orders) {
		daoorders.save(orders);
	}

	@Override
	public void updateOrders(Orders orders) {
		daoorders.update(orders);
	}

	@Override
	public void deleteOrders(String ordersnum) {
		daoorders.delete(ordersnum);
	}

	@Override
	public List<Orders> getAllOrders() {
		return daoorders.getAllOrders();
	}

	public  List<Orders>  getOrdersByCustomerID(String customerid)
	{
		return  daoorders.getOrdersByCustomerID(customerid);
	}
	
	
	// ===============================orderitem================================
	// =====�ϸ���˵Ӧ��Ҳ��Book�����Դӱ��ύ������formBook����������Ч�Լ�⣬������ʡ����========
	
	@Override
	public void saveOrderItem(OrderItem item) {
		daoorderitem.save(item);
	}

	@Override
	public void updateOrderItem(OrderItem item) {
		daoorderitem.update(item);
	}

	@Override
	public void deleteOrderItem(String id) {
		daoorderitem.delete(id);
	}

	@Override
	public List<OrderItem> getOrderItemsByOrdersNum(String ordersnum) {
		return daoorderitem.getOrderItemsByOrdersNum(ordersnum);
	}

	@Override
	public List<OrderItem> getOrderItemsByBookId(String id) {
		return daoorderitem.getOrderItemsByBookId(id);
	}

}
