package cn.com.obj.tmao.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class limitBook implements Serializable {

	// ====================�����ҳ��ѯ������JavaBean���ر�����=====================
	
	private int  currentPageNumber;   // ��ǰ��ʾ��ҳ�루�����ⲿע�룩*
	private int  nextPageNumber;    // ����һҳ����ҳ��
	private int previousPageNumber;   // ����һҳ����ҳ��
	
	private int  bigPageNumber;
	private  int  smallPageNumber;
	
	private int totalPages;   // ��ҳ����Ŀ
	private int totalItems; // ����Ŀ��
	
	private int currentPageIndex;  // ��ǰҳ��ʼ��Ŀ�����ݿ��е�����(�����������)*
	private int everyPageItemNumber;   // ÿҳ��ʾ����Ŀ����(�����ⲿע��)*
	
	 // ��Ŵ����ҳservlet������·�����������������·����Ϣ
	// �Ա㵱�û������JSPҳ���ϵ������������ϵ����ӵ�ʱ�򣬹�����֪��Ӧ�ý�����
	// ���͸��ĸ�servlet���д���
	private String path;  
	
	// ��ŵ�ǰҳ����Ҫ��ʾ����Ŀ�����ݵ�Bean
	private  List  list;   

	
	// =========================�������Ե�set/get����==========================
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
	
	// �����������뽫���봫��� ����ǰ��ʾҳ�롱�͡�ÿҳ��ʾ��Ŀ��������Ϣ���ݽ���
	public limitBook(int currentPageNumber, int everyPageItemNumbers)
	{
		// ��ǰҪ��ʾ��ҳ��
		this.currentPageNumber = currentPageNumber;    
		// ÿҳ��ʾ����Ŀ��
 		this.everyPageItemNumber =  everyPageItemNumbers;   
 		// ��ǰҳ����ʾ�ĵ�һ��������Ŀ�������ݿ��е�����ֵ����0��ʼ��count��*��-1��
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
