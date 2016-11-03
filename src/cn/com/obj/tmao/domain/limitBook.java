package cn.com.obj.tmao.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class limitBook implements Serializable {

	// ====================处理分页查询的特殊JavaBean的特别属性=====================
	
	private int  currentPageNumber;   // 当前显示的页码（必须外部注入）*
	private int  nextPageNumber;    // “下一页”的页码
	private int previousPageNumber;   // “上一页”的页码
	
	private int  bigPageNumber;
	private  int  smallPageNumber;
	
	private int totalPages;   // 总页码数目
	private int totalItems; // 总条目数
	
	private int currentPageIndex;  // 当前页起始条目在数据库中的索引(可以自行算出)*
	private int everyPageItemNumber;   // 每页显示的条目数量(必须外部注入)*
	
	 // 存放处理分页servlet的虚拟路径和请求参数的虚拟路径信息
	// 以便当用户点击了JSP页面上的索引工具条上的连接的时候，工具条知道应该将请求
	// 发送给哪个servlet进行处理
	private String path;  
	
	// 存放当前页面所要显示的条目的数据的Bean
	private  List  list;   

	
	// =========================数据属性的set/get方法==========================
	public int getNextPageNumber() {
		return nextPageNumber;
	}
	public void setNextPageNumber(int nextPageNumber) {
		this.nextPageNumber = nextPageNumber;
	}


	public int getEveryPageItemNumber() {
		return everyPageItemNumber;
	}
	public void setEveryPageItemNumber(int everyPageItemNumber) {
		this.everyPageItemNumber = everyPageItemNumber;
	}


	public int getBigPageNumber() {
		return bigPageNumber;
	}
	public void setBigPageNumber(int bigPageNumber) {
		this.bigPageNumber = bigPageNumber;
	}


	public int getSmallPageNumber() {
		return smallPageNumber;
	}
	public void setSmallPageNumber(int smallPageNumber) {
		this.smallPageNumber = smallPageNumber;
	}
	
	// 构造器，必须将必须传入的 “当前显示页码”和“每页显示条目数”的信息传递进来
	public limitBook(int currentPageNumber, int everyPageItemNumbers)
	{
		// 当前要显示的页码
		this.currentPageNumber = currentPageNumber;    
		// 每页显示的条目数
 		this.everyPageItemNumber =  everyPageItemNumbers;   
 		// 当前页所显示的第一个数据条目，在数据库中的索引值（从0开始到count（*）-1）
		this.currentPageIndex = (currentPageNumber-1)*everyPageItemNumbers;    
	}


	public int getCurrentPageNumber() {
		return currentPageNumber;
	}
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}


	public int getPreviousPageNumber() {
		return previousPageNumber;
	}
	public void setPreviousPageNumber(int previousPageNumber) {
		this.previousPageNumber = previousPageNumber;
	}


	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}


	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}


	public int getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}


	public int getEveryPageItemNumbers() {
		return everyPageItemNumber;
	}
	public void setEveryPageItemNumbers(int everyPageItemNumbers) {
		this.everyPageItemNumber = everyPageItemNumbers;
	}
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}


	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
