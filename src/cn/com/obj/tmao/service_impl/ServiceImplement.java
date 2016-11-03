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
	
	// 利用DAOFactory（service层与DAO层之间的解耦单元）来获取DAO实例
	// 这样当前的service层就不与DAO之间相关了，这样就完成了解耦
	private  DAOBookInterface   daobook =  daoFactory.createBookDAO();
	private DAOCategoryInterface daocategory =  daoFactory.createCategoryDAO();
	private  DAOCustomerInterface  daocustomer = daoFactory.createCustomerDAO();
	private DAOOrdersInterface   daoorders  =  daoFactory.createOrdersDAO();
	private DAOOrderItemInterface  daoorderitem  =  daoFactory.createOrderItemDAO();
	
	// =============================关于Book的业务逻辑====================================
	@Override
	public void saveBook(formBook formbook) throws FormBeanFormatException {
		
		Book  book  =  null;
		
		boolean  b  =  checkFormBook(formbook);
		if(b)
		{
			// 格式检测通过
			book =  new Book();
			MyBeanUtils.formBean2DAObean(book, formbook);
			daobook.save(book);
		}
		else
		{
			// 格式不正确，抛出异常
			// 该异常将会被servlet层捕获
			// 由于当前对象所操作的formBook这个Bean对象就是servlet从请求参数中封装的，换言之此时
			// service层数据校验过程中的这个formBean与servlet是相同的引用
			// 这样在service校验中写入到这个bean中的错误提示信息在servlet层也能得到，进而
			// servlet会将这个formBean直接放入到request请求转发链域中，然后请求转发到原表单页面
			// 该页面上的EL表达式就会自动从request中得到这个formBean然后将其中的数据恢复到表单中作为数据回显
			// 并且把错误提示信息也显示出来。
			throw new FormBeanFormatException("saveBook()过程中formBook中字段格式不符合要求");
		}
	}

	/**
	 * 检测格式formBook中封装的字段是否符合格式要求
	 * @param book
	 * @return  合格返回true，否则返回false
	 */
	private boolean checkFormBook(formBook book) {

		boolean  result  =  true;
		
		if(null==book.getId()||"".equals(book.getId()))
		{
			// 用户ID不能为空
			book.getMessage().put("id", "id不能为空");
			result = false;
		}
		
		if(null==book.getName()||"".equals(book.getName()))
		{
			// 用户name不能为空
			book.getMessage().put("name", "name不能为空");
			result = false;
		}
		
		if(null==book.getAuthor()||"".equals(book.getAuthor()))
		{
			// 用户author不能为空
			book.getMessage().put("author", "不能为空");
			result = false;
		}
		
		if(book.getMoney()<=0)
		{
			// 用户money的价格不正确
			book.getMessage().put("money", "价格不正确");
			result = false;
		}
		if(null==book.getDescription()||"".equals(book.getDescription()))
		{
			// 用户description不能为空
			book.getMessage().put("description", "书籍描述不能为空");
			result = false;
		}
		if(null==book.getPath()||"".equals(book.getPath()))
		{
			// 用户path不能为空
			book.getMessage().put("path", "请上传一张书籍图片");
			result = false;
		}
		if(null==book.getOldImageName()||"".equals(book.getOldImageName()))
		{
			// 用户oldImageName不能为空
			book.getMessage().put("oldImageName", " 原图片名称不能为空");
			result = false;
		}
		if(null==book.getNewImageName()||"".equals(book.getNewImageName()))
		{
			// 用户newImageName不能为空
			book.getMessage().put("newImageName", "新定义图片名称不能为空");
			result = false;
		}
		if(null==book.getCategoryId()||"".equals(book.getCategoryId()))
		{
			// 用户categoryId不能为空
			book.getMessage().put("categoryId", "书籍所属门类不能为空");
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
			throw new FormBeanFormatException("updateBook()过程中formBook中字段格式不符合要求");
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

	
	
	//===============================关于Category的业务逻辑===============================
	// =========严格来说应该也像Book那样对从表单提交过来的formBook进行数据有效性检测，但这里省略了===========
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
	// ====严格来说应该也像Book那样对从表单提交过来的formBook进行数据有效性检测，但这里省略了=====
	
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
			throw new FormBeanFormatException("saveCustomer()过程中formcustomer中数据格式不正确");
		}
	}

	/**
	 * 校验从前端表单提交过来并在servlet层封装进formCustomer的数据是否
	 * 符合保存进数据库的标准
	 * @param formcustomer
	 * @return   true 符合标准，false不符合标准
	 */
	private boolean checkFormCustomer(formCustomer formcustomer)
	{
		boolean  correctness = true;
		
		if(null==formcustomer.getId()||"".equals(formcustomer.getId()))
		{
			// id为空
			formcustomer.getMessage().put("id", "id不能为空");
			correctness = false;
		}
		if(null==formcustomer.getUsername()||"".equals(formcustomer.getUsername()))
		{
			// username为空
			formcustomer.getMessage().put("username", "username不能为空");
			correctness = false;
		}
		if(null==formcustomer.getPassword()||"".equals(formcustomer.getPassword()))
		{
			// password为空
			formcustomer.getMessage().put("password", "password不能为空");
			correctness = false;
		}
		if(null==formcustomer.getPhone()||"".equals(formcustomer.getPhone()))
		{
			// phone为空
			formcustomer.getMessage().put("phone", "phone不能为空");
			correctness = false;
		}
		if(null==formcustomer.getAddress()||"".equals(formcustomer.getAddress()))
		{
			// address为空
			formcustomer.getMessage().put("address", "address不能为空");
			correctness = false;
		}
		if(null==formcustomer.getEmail()||"".equals(formcustomer.getEmail()))
		{
			// email为空
			formcustomer.getMessage().put("email", "email不能为空");
			correctness = false;
		}
		if(null==formcustomer.getCode()||"".equals(formcustomer.getCode()))
		{
			// code为空
			formcustomer.getMessage().put("code", "code不能为空");
			correctness = false;
		}
		
		Set<String>  set =  new HashSet<String>();
		set.add("vip");
		set.add("normal");
		if(null==formcustomer.getLevel()||"".equals(formcustomer.getLevel()))
		{
			// level为空
			formcustomer.getMessage().put("level", "level不能为空");
			correctness = false;
		}
		else if(!set.contains(formcustomer.getLevel()))
		{
			// 用户类别不属于vip或normal中的任何一种
			formcustomer.getMessage().put("level", "level类型不匹配");
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
			throw new FormBeanFormatException("updateCustomer()过程中formcustomer中数据格式不正确");
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
	// ====严格来说应该也像Book那样对从表单提交过来的formBook进行数据有效性检测，但这里省略了===
	
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
	// =====严格来说应该也像Book那样对从表单提交过来的formBook进行数据有效性检测，但这里省略了========
	
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
