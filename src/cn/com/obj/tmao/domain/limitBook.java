package cn.com.obj.tmao.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class limitBook implements Serializable {

	private int  currentPageNumber;   // ��ǰ��ʾ��ҳ�루�����ⲿע�룩*
	private int  nextPageNumber;    // ��һҳ��ҳ��
	private int previousPageNumber;   // ��һҳ��ҳ��
	
	private int  bigPageNumber;
	private  int  smallPageNumber;
	
	private int totalPages;   // ��ҳ����Ŀ
	private int totalItems; // ����Ŀ��
	
	private int currentPageIndex;  // ��ǰҳ��ʼ��Ŀ�����ݿ��е�����(�����������)*
	private int everyPageItemNumber;   // ÿҳ��ʾ����Ŀ����(�����ⲿע��)*
	
	
	private String path;   // ��Ŵ����ҳservlet������·�����������������·����Ϣ
	private  List  list;   // ��ŵ�ǰҳ��Ŀ������

	
	
	
	
	
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
