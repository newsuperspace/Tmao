package cn.com.obj.tmao.dao_interface;

import java.util.List;

import cn.com.obj.tmao.domain.Book;

public interface DAOBookInterface {

	/**
	 * ����鼮
	 * @param book
	 */
	public void save(Book book);

	/**
	 * �����鼮
	 * @param book
	 */
	public void update(Book book);
	
	/**
	 * ɾ���鼮
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * ͨ���鼮ID��ȡ�鼮
	 * @param id
	 * @return
	 */
	public Book getBookByID(String id);
	
	/**
	 * �鼮��ҳ��ѯ
	 * @param currentPageNumber
	 * @param everyPageItemNumbers
	 * @return
	 */
	public List<Book>  getBooksBylimit(int currentPageIndex,int everyPageItemNumbers);
	
	/**
	 * �������ݿ����ܹ��ж����鼮
	 * @return
	 */
	public long getTotalBookItems();
	
	/**
	 * ��ÿҳָ���������ֶܷ���ҳ
	 * @param everyPageItemNumbers
	 * @return
	 */
	public long getTotalBookPages(int everyPageItemNumbers);

	public List<Book> getAllBooks();
}
