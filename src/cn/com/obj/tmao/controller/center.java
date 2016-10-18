package cn.com.obj.tmao.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import cn.com.obj.function.MyFunction;
import cn.com.obj.tmao.domain.Book;
import cn.com.obj.tmao.domain.Category;
import cn.com.obj.tmao.domain.Customer;
import cn.com.obj.tmao.domain.OrderItem;
import cn.com.obj.tmao.domain.Orders;
import cn.com.obj.tmao.domain.formBook;
import cn.com.obj.tmao.domain.formCustomer;
import cn.com.obj.tmao.domain.limitBook;
import cn.com.obj.tmao.exception.FormBeanFormatException;
import cn.com.obj.tmao.factory.ServiceFactory;
import cn.com.obj.tmao.service_impl.ServiceImplement;
import cn.com.obj.tmao.utils.MyBeanUtils;

@WebServlet
@MultipartConfig
public class center extends HttpServlet {

	private static ServiceImplement service = ServiceFactory
			.getServiceFactory().createService();

	// TODO 万恶之源
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String operation = request.getParameter("op");

		if (null == operation) {
			
			List<Book> list = service.getAllBooks();
			request.setAttribute("list", list);
			request.getRequestDispatcher("/pages/main.jsp").forward(request,
					response);
			return;
		}

		//---------------------------------开始流程控制--------------------------------
		switch (operation) {
		case "activate":   // 响应激活账户的操作
			Activate(request,response);
			break;
		
		
		
		case "add2car":   // 将书城中用户选择的书籍添加到购物车中
			Add2Car(request, response);
			response.setHeader("refresh", "1;url="+request.getContextPath());
			response.getWriter().write("<script>alert(\"  商品已添加成功！ \");</script>");
			break;
		
		case "formOrders":   // 将购物车中保存的数据形成订单写入到数据库orders表和orderitem表中
			FormOrder(request,response);
			break;
			
		case "myorders": // 跳转到订单页面，需要检测用户是否已经登录了
			MyOrders(request,response);
			break;

		case "pay4it":
			Pay4It(request,response);
			break;
			
		case "deleteOrders":
			DeleteOrders(request,response);
			break;
		case "logout": // 执行登出逻辑
			logout(request, response);
			break;

		case "login": // 进入登录页
			request.getRequestDispatcher("/pages/login.jsp").forward(request,
					response);
			break;

		case "login2service":     // 处理来自登录页面传递来的登录表单数据，完成用户登录的业务逻辑
			Login2Service(request, response);
			break;

		case "register": // 进入注册页
			request.getRequestDispatcher("/pages/regist.jsp").forward(request,
					response);
			break;

		case "register2service": // 处理注册页表单所提交的数据
			Register2Service(request, response);
			break;

		case "mycar": // 进入购物车页面
			request.getRequestDispatcher("/pages/car.jsp").forward(request, response);
			break;
			
		case "deleteOrderItem4car":     // 从购物车中删除某一项商品
			DeleteOrderItem4car(request,response);
			break;
			
		case "updateCar":    // 更新购物车中某一项商品的数量
			UpdateCar(request, response);
			break;
			
			
			
			
		case "bookmanager": // 跳转到图书管理页面
			
			int currentPageNumber  = 1;
			int  everyPageItemNumber  =  10;
			if(null== request.getParameter("currentPageNumber") || "".equals( request.getParameter("currentPageNumber")))
			{
				// 属性中没有附带页码请求参数，判定为初次访问
				currentPageNumber  =  1;
				everyPageItemNumber = 10;
			}
			else
			{
				// 不是初次访问
				currentPageNumber = Integer.valueOf( request.getParameter("currentPageNumber").trim());
				everyPageItemNumber =  Integer.valueOf(request.getParameter("everyPageItemNumber").trim());
			}
			limitBook  limitbook  =  new  limitBook(currentPageNumber, everyPageItemNumber);
			
			List<Book> list = service.getBooksBylimit(limitbook.getCurrentPageIndex(), everyPageItemNumber);   // 得到装满门类对象的容器
			limitbook.setList(list);
			
			int totalBookItems = (int) service.getTotalBookItems();
			int totalBookPages = (int) service.getTotalBookPages(everyPageItemNumber);
			limitbook.setTotalItems(totalBookItems);
			limitbook.setTotalPages(totalBookPages);
			
			String path  =  request.getContextPath()+"/servlet/center?op=bookmanager";
			limitbook.setPath(path);
			
			int nextPageNumber =  currentPageNumber+1;
			int previousPageNumber = currentPageNumber-1;
			if(nextPageNumber > totalBookPages)
			{
				nextPageNumber = -1;
			}
			if(previousPageNumber == 0)
			{
				previousPageNumber = -1;
			}
			limitbook.setPreviousPageNumber(previousPageNumber);
			limitbook.setNextPageNumber(nextPageNumber);
			
			
			int  bigPageNumber =  currentPageNumber+5;
			int smallPageNumber =  currentPageNumber -5;
			if(bigPageNumber > totalBookPages)
			{
				bigPageNumber = totalBookPages;
			}
			if(smallPageNumber <=0)
			{
				smallPageNumber = 1;
			}
			limitbook.setBigPageNumber(bigPageNumber);
			limitbook.setSmallPageNumber(smallPageNumber);
			
			request.setAttribute("limit", limitbook);  // 把容器转发给jsp页面，在页面上使用JSTL的core核心标签对容器进行遍历
			
			request.getRequestDispatcher("/WEB-INF/managepage/booker.jsp").forward(request, response);
			break;

		case "newbook":    // 跳转到添加新书籍页面
			List<Category> category2 = service.getAllCategory();
			request.setAttribute("category", category2);
			request.getRequestDispatcher("/WEB-INF/managepage/newbook.jsp").forward(request, response);
			break;
			
		case "addbook":    // 处理从添加新书籍页面传来的表单数据
		  	AddBook(request,response);
			break;

		case "deletebook":   // 删除图书
			deletebook(request,response);
			break;
			
		case "updatebook":   // 跳转到更新图书页面
			UpdateBook(request,response);
			break;
			
			
			
			
			
			
			
			
			
			
		case "categorymanager": // 跳转到图书类别管理页面
			List<Category> list11 = service.getAllCategory();   // 得到装满门类对象的容器
			request.setAttribute("categories", list11);  // 把容器转发给jsp页面，在页面上使用JSTL的core核心标签对容器进行遍历
			request.getRequestDispatcher("/WEB-INF/managepage/categoryer.jsp").forward(request, response);
			break;
		
		case "newcategory":    // 跳转到添加新门类的页面
			request.getRequestDispatcher("/WEB-INF/managepage/newcategory.jsp").forward(request, response);
			break;
		
		case "addcategory":    // 处理从添加新门类页面的表单提交来的数据
			AddCategory(request,response);
			break;
		
		case "deletecategory":   // 删除指定门类
			DeleteCategory(request,response);
			break;
			
		case "updatecategory":
			if(request.getParameter("name")==null)
			{
				Category category = service.getCategoryById(request.getParameter("id"));
				request.setAttribute("category", category);
				request.getRequestDispatcher("/WEB-INF/managepage/updatecategory.jsp?id="+request.getParameter("id")).forward(request, response);
			}
			else
			{
				updatecategory(request,response);
				request.getRequestDispatcher("/servlet/center?op=categorymanager").forward(request, response);
			}
			break;
		}
	} 

	/**
	 * 处理激活邮件的业务逻辑
	 * @param request
	 * @param response
	 */
	private void Activate(HttpServletRequest request,
			HttpServletResponse response) {
		//http://localhost/Tmao/servlet/center?op=activate&id=61b4488c-3356-471d-94f2-05fe9c4287d3&code=32072096749639
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		
		Customer customer = service.getCustomerById(id);
		if(code.equals(customer.getCode()))
		{
			// 激活成功
			customer.setActived(true);
			formCustomer  formcustomer =  new  formCustomer();

			MyBeanUtils.DAObean2formBean(formcustomer, customer);

			try {
				service.updateCustomer(formcustomer);
			} catch (FormBeanFormatException e) {
				e.printStackTrace();
			}
			
			response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center");
			try {
				response.getWriter().write("<script>alert(\" 账户激活成功 \");</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			// 激活失败 
			response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center");
			try {
				response.getWriter().write("<script>alert(\" 账户激活失败，请联系管理员 \");</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 支付订单
	 * @param request
	 * @param response
	 */
	private void Pay4It(HttpServletRequest request, HttpServletResponse response) {

		// TODO这里应该使用第三方支付平台，我这里就做一个简单的模拟了
		String ordersnum = request.getParameter("ordersnum");
		List<Orders> list = service.getAllOrders();
		Orders   myorders =  null;
		for(Orders orders : list)
		{
			if(orders.getOrdersnum().equals(ordersnum))
			{
				myorders =  orders;
			}
		}
		
		myorders.setStatus(1);
		service.updateOrders(myorders);
		response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center?op=myorders");
		try {
			response.getWriter().write("<script>alert(\"  订单支付成功 \");</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void DeleteOrders(HttpServletRequest request,
			HttpServletResponse response) {

		String ordersnum = request.getParameter("ordersnum");
		
		// 主从从主
		List<OrderItem> list = service.getOrderItemsByOrdersNum(ordersnum);
		for(int i =0; i<list.size(); i++)
		{
			String  id  =  list.get(i).getId();
			service.deleteOrderItem(id);
		}
		service.deleteOrders(ordersnum);
		
		response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center?op=myorders");
		try {
			response.getWriter().write("<script>alert(\"  订单删除成功 \");</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从数据库中提取订单数据，然后放入到请求转发链域中交于order.jsp页面
	 * @param request
	 * @param response
	 */
	private void MyOrders(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		Customer  customer =  (Customer)session.getAttribute("customer");
		String customerid =  customer.getId();
		
		List<Orders> list = service.getOrdersByCustomerID(customerid);
		request.setAttribute("orders", list);
		try {
			request.getRequestDispatcher("/pages/order.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 抽取session中保存的订单项数据，形成订单后向数据库的orders表和orderitem表中写入数据 
	 * @param request
	 * @param response
	 */
	private void FormOrder(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		Customer  customer =  (Customer) session.getAttribute("customer");
		if(null==customer)
		{
			// 用户为登录显示提示信息后，跳转到登录页面
			response.setHeader("refresh", "1;url="+request.getContextPath()+"/pages/login.jsp");
			try {
				response.getWriter().write("<script>alert('您还没有登录，请先登录');</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		// 用户已经登录，可以开始形成订单的逻辑了
		List<OrderItem>   list  =  (List<OrderItem>) session.getAttribute("orderItemList");
		List<Book>   booklist =  (List<Book>) session.getAttribute("booklist");
		
		Orders  orders =  new Orders();
		
		String  ordersnum =  System.currentTimeMillis()+"";
		orders.setOrdersnum(ordersnum);
		orders.setMoney(Float.valueOf(MyFunction.totalMoney(list)));
		orders.setNum(list.size());
		orders.setStatus(0);
		orders.setCustomerid(customer.getId());
		
		
		
		// 开始调用业务逻辑将订单和订单项数据写入到数据库中
		
		/*
		 * 下面这个像服务器中写入数据是有先后顺讯的，由于orderitem表中的ordersnum是引用表orders中的ordersnum的外键
		 * 因此在创建orderitem数据的时候一定要确保从表orderitem中所使用的外键值已经先一步存在于主表orders中了
		 */
		service.saveOrders(orders);
		for(OrderItem  item : list)
		{
			item.setOrdersnum(ordersnum);
			service.saveOrderItem(item);
		}
	
		// 清空购物车
		session.removeAttribute("orderItemList");
		session.removeAttribute("booklist");
		
		// 一切都结束了,提示订单提交成功，然后跳转回主页
		response.setHeader("refresh", "1;url="+request.getContextPath());
		try {
			response.getWriter().write("<script>alert(' 订单提交成功');</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	private void UpdateCar(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		List<OrderItem>   list  =  (List<OrderItem>) session.getAttribute("orderItemList");
		
		String orderitemID = request.getParameter("orderitemID");
		int  newnumber = Integer.valueOf(request.getParameter("number"));
		
		for(OrderItem item: list)
		{
			if(item.getId().equals(orderitemID))
			{
				item.setNum(newnumber);
				item.setMoney(service.getBookByID(item.getBookid()).getMoney()*newnumber);
			}
		}
		session.setAttribute("orderItemList", list);
		
		response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center?op=mycar");
		try {
			response.getWriter().write("<script>alert(\"  数量更改成功 \");</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void DeleteOrderItem4car(HttpServletRequest request,
			HttpServletResponse response) {
		
		String orderItemID = request.getParameter("orderItemID");
		HttpSession session = request.getSession();
		
		List<OrderItem>   list  =  (List<OrderItem>) session.getAttribute("orderItemList");
		List<Book>   booklist =  (List<Book>) session.getAttribute("booklist");
	
		//for(OrderItem  item: list)  增强for只能用来遍历容器，如果需要在遍历过程中修改容器还是使用传统方法吧
		for(int i = 0;i<list.size();i++)
		{
			OrderItem item  =  list.get(i);
			if(item.getId().equals(orderItemID))
			{
//				System.out.println("list之前"+list.size());
				list.remove(item);
//				System.out.println("list之后"+list.size());
				
				// for(Book book: booklist)  增强for只能浏览不能修改容器
				for(int j = 0; j<booklist.size();j++)
				{
					Book book = booklist.get(j);
					if(book.getId().equals(item.getBookid()))
					{
//						System.out.println("booklist之前"+booklist.size());
						booklist.remove(book);
//						System.out.println("booklist之后"+booklist.size());
						break;
					}
				}
				break;
			}
		}
		
		response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center?op=mycar");
		try {
			response.getWriter().write("<script>alert(\"  删除成功 \");</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void Add2Car(HttpServletRequest request,
			HttpServletResponse response) {
		
		String bookid = request.getParameter("bookid");  //  所选图书ID
		int  number =  Integer.valueOf(request.getParameter("number"));   // 所选图书个数
		float  money  =  service.getBookByID(bookid).getMoney()*number;   // 总价格
		
		HttpSession session = request.getSession();
		List<OrderItem>   list  =  (List<OrderItem>) session.getAttribute("orderItemList");
		List<Book>   booklist =  (List<Book>) session.getAttribute("booklist");
		if(null==list)
		{
			// 初次选购商品
			list =  new ArrayList<OrderItem>();
			booklist = new ArrayList<Book>();
			
			OrderItem  orderitem =  new  OrderItem();
			orderitem.setId(UUID.randomUUID().toString());
			orderitem.setNum(number);
			orderitem.setMoney(money);
			orderitem.setBookid(bookid);
			
			list.add(orderitem);
			booklist.add(service.getBookByID(bookid));
		}
		else
		{
			// 不是初次选购商品
			for(OrderItem  item: list)
			{
				if(item.getBookid().equals(bookid))
				{
					// 之前已经添加过该类商品了,只增加数字就好
					item.setNum(item.getNum()+number);
					item.setMoney(item.getMoney()+money);
					number = 0;
				}
			}
			
			if(number!=0)
			{
				// 说明是新加入购物车的商品
				OrderItem  orderitem =  new  OrderItem();
				orderitem.setId(UUID.randomUUID().toString());
				orderitem.setNum(number);
				orderitem.setMoney(money);
				orderitem.setBookid(bookid);
				
				list.add(orderitem);
				booklist.add(service.getBookByID(bookid));
			}
		}
		
		session.setAttribute("orderItemList", list);
		session.setAttribute("booklist", booklist);
//		System.out.println(session.getAttribute("orderItemList").toString());
	}

	private void UpdateBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO UpdateBook 暂时不写了，因为与用户管理相同
	}

	private void AddBook(HttpServletRequest request,
			HttpServletResponse response) {
		
		formBook   formbook  =  new  formBook();
		MyBeanUtils.parameterMap2formBean(formbook, request.getParameterMap());
		formbook.setId(UUID.randomUUID().toString());
		
		Part part = null;
		String oldImageName = null;
		String  newImageName = null;
		String path  = null;
		String realpath = null;
		try {
			part = request.getPart("image");
			String header = part.getHeader("Content-Disposition");
			if(header.contains("\\"))
			{
				// 从浏览器传递过来的是图片文件的全路径
				oldImageName  = header.substring(header.lastIndexOf("\\")+2, header.length()-1);
			}
			else
			{
				// 只有图片名称
				int lastIndexOf = header.lastIndexOf("=");
				oldImageName =  header.substring(lastIndexOf+2, header.length()-1);
			}
			newImageName =  System.currentTimeMillis()+oldImageName;
			int hashCode = newImageName.hashCode();
			int a  =   hashCode&0xf;
			int b = hashCode&0xf0>>4;
			
			path  =  "image"+"/"+a+"/"+b;
			
			realpath =  request.getServletContext().getRealPath(path);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		formbook.setNewImageName(newImageName);
		formbook.setOldImageName(oldImageName);
		formbook.setPath(path);
		try {
			service.saveBook(formbook);
			//  如果保存新图书没有抛出异常，就表示通过了业务逻辑的审核流程，用户提交的表单数据符合标准，这之后我们再存储图片到服务器磁盘上
			File file  =  new  File(realpath);
			if(!file.exists())
			{
				// 如果路径不存在，就逐层一级一级目录的创建
				file.mkdirs();
			}
			try {
				// 正式保存图片文件到服务器端磁盘上，完成图片上传的工作
				part.write(realpath+"/"+newImageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			response.setHeader("refresh", "1;url="+request.getContextPath()+"/"+"servlet/center?op=bookmanager");
			
			try {
				response.getWriter().write("<script>alert(\" 新书籍添加成功 \");</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FormBeanFormatException e) {
			try {
				request.setAttribute("book", formbook);
				request.getRequestDispatcher("/WEB-INF/managepage/newbook.jsp").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	private void deletebook(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, String[]> map = request.getParameterMap();
		String[] strings = map.get("id");
		if(null!=strings)
		{
			for(String s:strings)
			{
				service.deleteBook(s);
			}
		}
		try {
			request.getRequestDispatcher("/servlet/center?op=bookmanager").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updatecategory(HttpServletRequest request,
			HttpServletResponse response) {
		Category  category  =  new  Category();
		MyBeanUtils.parameterMap2formBean(category, request.getParameterMap());
		service.update(category);
	}

	private void DeleteCategory(HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, String[]> map = request.getParameterMap();
		String[] strings = map.get("id");
		if(null!=strings)
		{
			for(String s:strings)
			{
				service.deleteCategory(s);
			}
		}
		try {
			request.getRequestDispatcher("/servlet/center?op=categorymanager").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void AddCategory(HttpServletRequest request,
			HttpServletResponse response) {
		
		String  name  =  request.getParameter("name");
		String  description  =  request.getParameter("description");
		
		if(name!=null&&!"".equals(name)&&description!=null&&!"".equals(description))
		{
			// 粗略的检测数据格式，通过 
			Category  category  =  new  Category();
			category.setId(UUID.randomUUID().toString());
			category.setName(name);
			category.setDescription(description);
			service.saveCategory(category);
			
			response.setHeader("refresh", "1;url=" + request.getContextPath()
					+ "/servlet/center?op=categorymanager");
			try {
				response.getWriter()
				.write("<script type='text/javascript'>alert('新建门类成功')</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			// 粗略的格式检测没有通过，打回重新填写
			request.setAttribute("msg", "数据不能为空");
			request.getRequestDispatcher("/servlet/center?op=newcategory");
		}
		
	}

	private void Login2Service(HttpServletRequest request,
			HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// Map<String, String[]> map = request.getParameterMap();
		// if (map.isEmpty()) {
		// // 登录失败
		// request.setAttribute("msg", "用户名或密码不正确");
		// try {
		// request.getRequestDispatcher("/pages/login.jsp").forward(
		// request, response);
		// } catch (ServletException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// String username = map.get("username")[0];
		// String password = map.get("password")[0];

		Customer customer = service.getCustomerByNameANDpwd(username, password);
		if (null != customer) {
			// 登录成功
			request.getSession().setAttribute("customer", customer);
			response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center");
			try {
				response.getWriter()
						.write("<script type='text/javascript'>alert('登录成功')</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 登录失败
			request.setAttribute("msg", "用户名或密码不正确");
			try {
				request.getRequestDispatcher("/pages/login.jsp").forward(
						request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("customer");
		response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center");
		try {
			response.getWriter().write(
					"<script type='text/javascript'>alert('登出成功')</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 */
	private void Register2Service(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String[]> map = request.getParameterMap();
		final formCustomer bean = new formCustomer();
		bean.setId(UUID.randomUUID().toString());
		bean.setActived(false);
		bean.setCode(String.valueOf(System.nanoTime()));
		MyBeanUtils.parameterMap2formBean(bean, map);

		try {
			service.saveCustomer(bean);
			
			// 下面发生的事情都是在注册没出现异常，也就是注册成功后
			
			/*
			 * 单独开启一个线程，用以发送激活邮件
			 */
			new Thread(){

				@Override
				public void run() {
					SendActivateMail(bean);
				}
			}.start();
			
			try {
				response.setHeader("refresh", "1;url="+request.getContextPath()+"/servlet/center");
				response.getWriter()
						.write("<script type='text/javascript'>alert('注册成功,激活邮件已经发送，请注意查收')</script>");
				
			} catch (IOException e) {
				try {
					request.getRequestDispatcher("/servlet/center").forward(
							request, response);
				} catch (ServletException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (FormBeanFormatException e) {
			request.setAttribute("formcustomer", bean);
			try {
				request.getRequestDispatcher("/pages/regist.jsp").forward(
						request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 发送验证激活邮件
	 * @param customer
	 */
	private void  SendActivateMail(formCustomer  customer)
	{
		Properties props  = new  Properties();
		props.setProperty("mail.host","smtp.126.com");
		props.setProperty("mail.transport.protocol","smtp");
		props.setProperty("mail.smtp.auth","true");
		
		Session session  =  Session.getInstance(props);
		MimeMessage   message =  new MimeMessage(session);
		
		try {

			message.setFrom(new  InternetAddress("huixinshegong@126.com"));
			message.setRecipients(Message.RecipientType.TO, customer.getEmail());
			message.setSubject("来自最近买了个表书城的激活邮件");
			message.setContent("<a href=\" http://localhost/Tmao/servlet/center?op=activate&id="+customer.getId()+"&code="+customer.getCode()+"\">点击这里完成注册</a>", "text/html;charset=utf-8");
			message.saveChanges();
			Transport   trans =  session.getTransport();
			trans.connect("huixinshegong", "xxoo123321");
//			trans.send(message, message.getAllRecipients());
			trans.sendMessage(message, message.getAllRecipients());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
