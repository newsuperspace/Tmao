package cn.com.obj.tmao.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.com.obj.tmao.domain.Book;
import cn.com.obj.tmao.domain.Category;
import cn.com.obj.tmao.domain.Customer;
import cn.com.obj.tmao.domain.OrderItem;
import cn.com.obj.tmao.domain.Orders;
import cn.com.obj.tmao.domain.formBook;
import cn.com.obj.tmao.domain.formCustomer;
import cn.com.obj.tmao.exception.FormBeanFormatException;
import cn.com.obj.tmao.factory.ServiceFactory;
import cn.com.obj.tmao.manager.TransactionManager;
import cn.com.obj.tmao.service_interface.ServiceInterface;
import cn.com.obj.tmao.utils.MyBeanUtils;

public class testService {

	private static ServiceInterface   service  =  null;
	
	@BeforeClass
	public static void  init()
	{
		service =  ServiceFactory.getServiceFactory().createService();
	}
	
	
	// ==================测试Book====================
	@Test//(expected=FormBeanFormatException.class)
	public void testSaveBook() throws FormBeanFormatException {
		formBook  book  =  new formBook();
		book.setId(UUID.randomUUID().toString());
		book.setName("test");
		book.setAuthor("test");
		book.setMoney(100);
		book.setDescription("这是一个测试行为");
		book.setPath("c:/xxxx/cccc/fvvvv");
		book.setOldImageName("test");
		book.setNewImageName("newTest");
		book.setCategoryId("32652920-d2c5-438f-bfd3-79f517c153da");
		service.saveBook(book);
	}
	
	
	@Test//(expected=FormBeanFormatException.class)
	public void testUpdateBook() throws FormBeanFormatException {
		String id  = "d9b72621-d4c5-4571-915c-d65895bbee89";
		Book book = service.getBookByID(id);
		book.setDescription("超能勇士");
		
		formBook  dest =  new formBook();
		MyBeanUtils.DAObean2formBean(dest, book);
		
		service.updateBook(dest);
		service.deleteBook("d9b72621-d4c5-4571-915c-d65895bbee89");
		
	}
	
	@Test
	public void testGetBooksBylimit()
	{
		List<Book> list = service.getBooksBylimit(0, 5);
		for(Book b:list)
		{
			System.out.println(b.toString());
		}
	}
	
	
	@Test
	public void testTotalBook()
	{
		System.out.println(service.getTotalBookItems());
		System.out.println(service.getTotalBookPages(5));
	}

	
	@Test
	public void testCategory()
	{
		Category category =  new Category();
		category.setId("111");
		category.setName("测试类目");
		category.setDescription("这是一个单元测试所创建的数目类目");
		
		service.saveCategory(category);
		
		category.setDescription("更改描述结果");
		service.update(category);
		
		List<Category> list = service.getAllCategory();
		for(Category  c:list)
		{
			System.out.println(c.toString());
		}
		
		category  = service.getCategoryById("111");
		System.out.println(category);
		
	}
	
	@Test
	public void testDeleteCategory()
	{
		service.deleteCategory("111");
	}
	
	
	@Test
	public void testSaveCustomer()
	{
		formCustomer  customer =  new formCustomer();
		customer.setId("111");
		customer.setUsername("测试用户");
		customer.setPassword("123456");
		customer.setPhone("13718805500");
		customer.setEmail("123@qq.com");
		customer.setAddress("M78星云");
		customer.setActived(false);
		customer.setCode("测试账号的测试码");
		customer.setLevel("vip");
		
		try {
			service.saveCustomer(customer);
		} catch (FormBeanFormatException e) {
			e.printStackTrace();
			
			Map<String, String> message = customer.getMessage();
			for(Map.Entry<String, String> entry :message.entrySet())
			{
				System.out.println(entry.getKey()+":"+entry.getValue());
			}
		}
	}
	
	
	@Test
	public void testCustomer() throws FormBeanFormatException
	{
		Customer customer = service.getCustomerById("111");
		formCustomer  dest  =  new  formCustomer();
		
		MyBeanUtils.DAObean2formBean(dest, customer);
		dest.setLevel("normal");
		
		service.updateCustomer(dest);
		
		System.out.println(dest.toString());
	}
	
	
	@Test
	public void testDeleteCustomer()
	{
		service.deleteCustomer("111");
	}
	
	
	
	
	
	
	@Test
	public void testOrders()
	{
		Orders  orders =  new  Orders();
		orders.setOrdersnum(System.nanoTime()+"");
		orders.setMoney(1000);
		orders.setNum(3);
		orders.setStatus(0);
		orders.setCustomerid("111");
		
		service.saveOrders(orders);
		
		List<Orders> list = service.getAllOrders();
		orders = list.get(0);
		
		orders.setNum(5);
		service.updateOrders(orders);
		
		System.out.println(orders);
		
		service.deleteOrders(orders.getOrdersnum());
	}
	
	
	
	
	
	@Test
	public void testOrderItem()
	{
		OrderItem  item  =  new OrderItem();
		item.setId("111");
		item.setNum(1);
		item.setMoney(1111);
		item.setBookid("059c09d5-4a99-460c-b622-be418e096f74");
		item.setOrdersnum("111");
		
		try{
			
			service.saveOrderItem(item);
			
			TransactionManager.start();
			
			List<OrderItem> list = service.getOrderItemsByBookId("059c09d5-4a99-460c-b622-be418e096f74");
			item  = list.get(0);
			item.setMoney(2222);
			service.updateOrderItem(item);
			
			list = service.getOrderItemsByOrdersNum("111");
			item  = list.get(0);
			
			System.out.println(item.toString());
			
			int a = 1/0;
			
			service.deleteOrderItem("111");
			TransactionManager.commit();
		}catch(Exception e)
		{
			e.printStackTrace();  // 打印了调用栈轨迹
			TransactionManager.rollback();
		}
		
	}
	
	
	
	
	@Test
	public void testParameterMap2formBean()
	{
		Map<String,String>  map  =  new HashMap<String,String>();
		
		map.put("id", "111");
		map.put("num", "123456");
		map.put("money", "1000.0");
		map.put("bookid", "xjisne");
		map.put("ordersnum", "josdijfo123");
		
		OrderItem  item  =  new OrderItem();
		
	 //	MyBeanUtils.parameterMap2formBean(item, map);
		
		System.out.println(item.toString());
	}
	
	
	
	
	
	@Test
	public void test()
	{
		
	}
	
	
	
	
	
	
	
	
}
