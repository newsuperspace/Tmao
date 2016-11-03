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

/**
 * 当前这个servlet是整个工程的唯一一个servlet用于控制整个Web应用的流转
 * 当然你也可以建立多个servlet但是servlet与web.xml配置文件的繁简是负相关的
 * 如果servlet复杂了势必web.xml就简单（只需要配置一个servlet），相反如果web.xml配置多个servlet
 * 则每个servlet逻辑结构也就没这么复杂了。孰是孰非自己决定。
 * 
 * 当然如果使用Struts2技术就避免这个两难的抉择了，因为struts2借助struts2.xml配置文件+其中的通配符映射机制 +
 * 外部访问url匹配（匹配工作由struts2容器负责） 后就可以跳转到任何Action类中的任何公有方法中去了。这样做的好处
 * 就是struts2将过去JavaWeb中servlet的只能分担给了Struts2配置文件，这看似与web.xml的功能相同但区别有二。
 * 其一，struts2.xml可以借助include标签将文档内容分割为多个配置文件，这样就提升的文档的可读性
 * 其二，在struts2中以及彻底找不到servlet了，表面上看去就如同外部直接靠url就可以访问到指定Action类的指定方法（Action相当于service层）
 * 			这样就彻底做到了service层与servlet容器的解耦（servlet都没了自然就解耦了）。
 * 
 * @author Administrator
 *
 */
@WebServlet
@MultipartConfig
public class center extends HttpServlet {

	private static ServiceImplement service = ServiceFactory
			.getServiceFactory().createService();

	// TODO 万恶之源
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取op请求参数，其中包含着页面告知servlet的用以servlet执行流程控制的参数
		String operation = request.getParameter("op");

		// 当用户是第一次访问网站的时候，没有op请求参数，则操作值必定为null，这是默认执行的跳转到main.jsp页面
		if (null == operation) {
			
			List<Book> list = service.getAllBooks();
			request.setAttribute("list", list);
			// main.jsp页面通过是由包括main本身在内两个JSP页面通过 include处理指令实现的静态包含组成的，这三个页面的功能分别是
			// head 用来显示登录信息、注销、注册等功能的标题栏，类似于百度右上角的那一行，是应该存在于当前站点所有页面中的
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
			
		case "myorders": // 跳转到订单页面（需要检测用户是否已经登录）
			MyOrders(request,response);
			break;

		case "pay4it":   // 支付订单
			Pay4It(request,response);
			break;
			
		case "deleteOrders":  // 删除订单
			DeleteOrders(request,response);
			break;
			
		case "logout": // 执行登出逻辑——从session中删除customer属性即可
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
			
			// 分页查询所必须的设置
			int currentPageNumber  = 1;   // 肯定是从第一页开始的
			int  everyPageItemNumber  =  10;   // 默认每页显示10个项目
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
			// 得到分页查询出的条目容器list
			List<Book> list = service.getBooksBylimit(limitbook.getCurrentPageIndex(), everyPageItemNumber);   
			// 将list保存到limitBean中的list中去
			limitbook.setList(list);
			// 数据库中总共有多少条目（用来计算有多少页）
			int totalBookItems = (int) service.getTotalBookItems();
			// 总共有多少页
			int totalBookPages = (int) service.getTotalBookPages(everyPageItemNumber);
			limitbook.setTotalItems(totalBookItems);
			limitbook.setTotalPages(totalBookPages);
			
			// 设置处理分页查询的servlet虚拟路径
			String path  =  request.getContextPath()+"/servlet/center?op=bookmanager";
			limitbook.setPath(path);
			
			// 设置 “上一页”和“下一页”的页码
			int nextPageNumber =  currentPageNumber+1;
			int previousPageNumber = currentPageNumber-1;
			// 并判断页码不能大于最大值 、小于1
			if(nextPageNumber > totalBookPages)
			{
				nextPageNumber = -1;   // JSP页面会检测，如果小于0则会将连接设置为不可点击
			}
			if(previousPageNumber == 0)
			{
				previousPageNumber = -1;    // JSP页面会检测，如果小于0则会将连接设置为不可点击
			}
			limitbook.setPreviousPageNumber(previousPageNumber);
			limitbook.setNextPageNumber(nextPageNumber);
			
			// 设置索引页码的上限和下线，也就是在导航条中显示当前页以上高出5个的连接索引，以及小于5个的连接缩影
			int  bigPageNumber =  currentPageNumber+5;
			int smallPageNumber =  currentPageNumber -5;
			// 保证这些单页跳转索引连接不超出最大页数和不小1
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
			
			// 把容器转发给jsp页面，在页面上使用JSTL的core核心标签对容器进行遍历，并最值JSP为HTML
			request.setAttribute("limit", limitbook);  
			
			request.getRequestDispatcher("/WEB-INF/managepage/booker.jsp").forward(request, response);
			break;

		case "newbook":    // 跳转到添加新书籍页面
			List<Category> category2 = service.getAllCategory();
			request.setAttribute("category", category2);
			request.getRequestDispatcher("/WEB-INF/managepage/newbook.jsp").forward(request, response);
			break;
			
		case "addbook":    // 处理从添加新书籍页面传来的表单数据————涉及使用servlet3.0技術實現的文件上傳邏輯
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

	
	// ==============================以下都是由上面的servlet分支調用的邏輯封裝===============================
	/**
	 * 处理激活邮件的业务逻辑
	 * @param request
	 * @param response
	 */
	private void Activate(HttpServletRequest request,
			HttpServletResponse response) {
		// http://localhost/Tmao/servlet/center?op=activate&id=61b4488c-3356-471d-94f2-05fe9c4287d3&code=32072096749639
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
		
		/**
		 * 主从从主6666666666  具有多表映射关系的数据如何删除
		 * 由于订单和订单项两个数据库表存在“一对多”的外键映射
		 * 因此要想删除主表（订单表）中的某一个数据，需要先删除在从表中引用主表中某一个属性为外键的全部数据后
		 * 才能删除主表中的数据，因此主从从主的意思就是
		 * 添加数据的顺序——先加主表，再加从表 （要不然从表哪儿去引外键呢？）
		 * 删除数据的顺序（是添加顺序的逆向）——先删除从表，再删除主表 
		 */
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
		String  ordersnum =  System.currentTimeMillis()+"";   // 这里是以系统时间来做诶订单号的
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

	/**
	 * 购物车逻辑
	 * 因为购物车是通过session来暂时保存的数据，这也就是为什么
	 * 当我们多次重新访问站点仍能保证数据不丢失
	 * @param request
	 * @param response
	 */
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

	/**
	 * 向数据库添加新图书
	 * @param request
	 * @param response
	 */
	private void AddBook(HttpServletRequest request,
			HttpServletResponse response) {
		
		formBook   formbook  =  new  formBook();
		MyBeanUtils.parameterMap2formBean(formbook, request.getParameterMap());
		formbook.setId(UUID.randomUUID().toString());
		
		// 用於處理文件上的Part
		Part part = null;
		String oldImageName = null;
		String  newImageName = null;
		String path  = null;
		String realpath = null;
		try {
			part = request.getPart("image");  // 這是servlet3.0 才有的API，用來獲取“multipart/form”的多部分結構對象，這裏的這個對象就是上傳圖片的結構對象
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
			newImageName =  System.currentTimeMillis()+oldImageName;  // 注意oldImageName里包含文件的后缀，所以不能在其后追加字符串，只能再其前面追加
			int hashCode = newImageName.hashCode();
			int a  =   hashCode&0xf;   	// 只保留十六位的二进制哈希码的后四位后的十进制数值 （可能值0 到 15）
			int b = hashCode&0xf0>>4; // 这回保留哈希码的十六位的二进制的 0000 0000 1111 0000 位置的数值其他都归0，然后右移四位变为
														// 0000 0000 0000 1111的形式在转为十进制书（可能值0到15）
			
			path  =  "image"+"/"+a+"/"+b;   // 组建图片存放在服务器硬盘上的路径
			
			realpath =  request.getServletContext().getRealPath(path);  // 获取到当前web应用真正安装运行在web服务器上时候的磁盘真实路径
																						// 这样 realpath+"/" +newImageName 就是图片保存的真实路径了
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		// 下面这些东西都是要保存到数据库中去的，方便以后重新从服务器端下载
		formbook.setNewImageName(newImageName);
		formbook.setOldImageName(oldImageName);
		formbook.setPath(path);
		try {
			// 将新建图书的数据保存到数据库中去，当然再此之前需要先通过service层的表单验证逻辑的检验
			// 如果发现数据格式不符合要求，就会将错误信息封装到formbook中然后抛出自定义异常FormBeanFormatException
			// try块中这条代码之后的逻辑不在执行，而是跳转到下方的catch块中执行逻辑
			// 正常情况下catch块中逻辑执行完毕后还会继续执行整个try{}catch{}之外的代码，但考虑到这里catch中的逻辑是执行请求转发
			// 到前端表单中，并在表单中完成数据回显和错误信息提示帮助用户重新创创建书籍，因此也就用不到tyr...catch块之后的代码了，
			// 所以直接用return结束函数调用————return的作用有两个(1)返回值(2)终止函数调用切记   66666666
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
			return;
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
	 * 注册
	 * @param request
	 * @param response
	 */
	private void Register2Service(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String[]> map = request.getParameterMap();
		final formCustomer bean = new formCustomer();
		bean.setId(UUID.randomUUID().toString());   // 用户ID的创建就使用UUID随机数
		bean.setActived(false);    // 默认用户没有被激活
		bean.setCode(String.valueOf(System.nanoTime()));    // 这里设置的激活码就是系统时间纳秒值
		MyBeanUtils.parameterMap2formBean(bean, map);    // 使用Bean工具快速将Map中数据封入JavaBean——formCustomer中

		try {
			service.saveCustomer(bean);   // 将注册数据保存到数据到数据库（前提是通过了service中的表单验证）
			
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
		// 创建properties配置文件
		Properties props  = new  Properties();
		props.setProperty("mail.host","smtp.126.com");   // 发件邮箱的stmp服务器
		props.setProperty("mail.transport.protocol","smtp"); 
		props.setProperty("mail.smtp.auth","true");
		// 这里的Session不是HttpSession而是JavaMail的 javax.mail.Session
		Session session  =  Session.getInstance(props);
		// 邮件的组织是使用Mime协议（多部分文件上传也使用的是Mime协议）
		MimeMessage   message =  new MimeMessage(session);		
		try {
			// 设置发件邮箱地址
			message.setFrom(new  InternetAddress("huixinshegong@126.com"));
			// 邮件类型 抄送、密送、发送之一，以及目标收件地址
			message.setRecipients(Message.RecipientType.TO, customer.getEmail());
			// 邮件标题
			message.setSubject("来自最近买了个表书城的激活邮件");
			// 邮件内容（可以输入HTML内容以便组织邮件表现形式）
			message.setContent("<a href=\" http://localhost/Tmao/servlet/center?op=activate&id="+
							customer.getId()+"&code="+customer.getCode()+"\">点击这里完成注册</a>", "text/html;charset=utf-8");
			// 内容写完要保存一下
			message.saveChanges();
			// 创建邮件传输器对象
			Transport   trans =  session.getTransport();
			// 设置好发送邮箱的登录用户名和密码
			trans.connect("huixinshegong", "xxoo123321");
//			trans.send(message, message.getAllRecipients());  不要使用这个函数发送邮件，容易发送失败
			// 使用sendMessage()发送邮件到指定地址中去
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
