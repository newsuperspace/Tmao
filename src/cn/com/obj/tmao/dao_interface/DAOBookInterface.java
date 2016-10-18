package cn.com.obj.tmao.dao_interface;

import java.util.List;

import cn.com.obj.tmao.domain.Book;

public interface DAOBookInterface {

	/**
	 * 添加书籍
	 * @param book
	 */
	public void save(Book book);

	/**
	 * 更新书籍
	 * @param book
	 */
	public void update(Book book);
	
	/**
	 * 删除书籍
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 通过书籍ID获取书籍
	 * @param id
	 * @return
	 */
	public Book getBookByID(String id);
	
	/**
	 * 书籍分页查询
	 * @param currentPageNumber
	 * @param everyPageItemNumbers
	 * @return
	 */
	public List<Book>  getBooksBylimit(int currentPageIndex,int everyPageItemNumbers);
	
	/**
	 * 返回数据库中总共有多少书籍
	 * @return
	 */
	public long getTotalBookItems();
	
	/**
	 * 以每页指定数量，能分多少页
	 * @param everyPageItemNumbers
	 * @return
	 */
	public long getTotalBookPages(int everyPageItemNumbers);

	public List<Book> getAllBooks();
}
