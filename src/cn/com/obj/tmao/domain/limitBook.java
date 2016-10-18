package cn.com.obj.tmao.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class limitBook implements Serializable {

	private int  currentPageNumber;   // 当前显示的页码（必须外部注入）*
	private int  nextPageNumber;    // 下一页的页码
	private int previousPageNumber;   // 上一页的页码
	
	private int  bigPageNumber;
	private  int  smallPageNumber;
	
	private int totalPages;   // 总页码数目
	private int totalItems; // 总条目数
	
	private int currentPageIndex;  // 当前页起始条目在数据库中的索引(可以自行算出)*
	private int everyPageItemNumber;   // 每页显示的条目数量(必须外部注入)*
	
	
	private String path;   // 存放处理分页servlet的虚拟路径和请求参数的虚拟路径信息
	private  List  list;   // 存放当前页条目的容器

	
	
	
	
	
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
	
	public limitBook(int currentPageNumber, int everyPageItemNumbers)
	{
		this.currentPageNumber = currentPageNumber;
		this.everyPageItemNumber =  everyPageItemNumbers;
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
