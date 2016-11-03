package cn.com.obj.tmao.dao_impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transaction;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.com.obj.tmao.dao_interface.DAOBookInterface;
import cn.com.obj.tmao.domain.Book;
import cn.com.obj.tmao.manager.TransactionManager;

public class DAOBookImplement implements DAOBookInterface {

	private  QueryRunner  runner = null;
	{
		// 之所以使用初始化代码块来完成runner的实例化工作
		// 是因为这些DAO操作的实例是通过反射机制获取的
		// 我不确定反射机制在创建类实例的时候是否会调用构造函数
		// 因此为了万无一失这里使用了初始化代码块
		runner =  new QueryRunner();      // 借助DBUtils的开源框架
	}
	
	@Override
	public void save(Book book) {
		try {
			runner.update(TransactionManager.getConnection(),   // 通过事务管理器中维护的TLS获取只服务于当前线程的数据库连接对象
					// 运用标准的sql语句+？号占位符 来构筑准备执行的sql语句主体，这一点与直接使用J2EE的preparedStatement是一样的
					"INSERT INTO book (id,name,author,money,description,path,oldImageName,newImageName,categoryId) VALUES(?,?,?,?,?,?,?,?,?)", 
					// 按照占位符的对应顺序依次填入对应的value
					book.getId(),
					book.getName(),
					book.getAuthor(),
					book.getMoney(),
					book.getDescription(),
					book.getPath(),
					book.getOldImageName(),
					book.getNewImageName(),
					book.getCategoryId()
					);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOBookImplement->save()->添加新书籍出现错误");
		}
	}

	@Override
	public void update(Book book) {
		try {
			runner.update(TransactionManager.getConnection(),
					"UPDATE  book SET name=?,author=?,money=?,description=?,path=?,oldImageName=?,newImageName=?,categoryId=? WHERE id=?", 
					book.getName(),
					book.getAuthor(),
					book.getMoney(),
					book.getDescription(),
					book.getPath(),
					book.getOldImageName(),
					book.getNewImageName(),
					book.getCategoryId(),
					book.getId()
					);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOBookImplement->update()->更新书籍出现错误");
		}

	}

	@Override
	public void delete(String id) {
		
		try {
			runner.update(TransactionManager.getConnection(),"DELETE FROM book WHERE id = ?", id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOBookImplement->delete()->删除书籍出现错误");
		}

	}

	@Override
	public Book getBookByID(String id) {
		Book book  =  new Book();
		try {
			book  =  runner.query(TransactionManager.getConnection(), "SELECT * FROM book WHERE id = ?", 
					// 使用DBUtils提供的各种适配器
					new BeanHandler<Book>(Book.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOBookImplement->getBookByID()->获取书籍出现错误");
		}
		return book;
	}


	/**
	 * 对大结果集的数据库查询结果，为了节约处理成本，可使用分页查询LIMIT关键字
	 * currentPageIndex 所要查询的第一个数据在数据库索引位置
	 * everyPageItemNumbers 要查询出多少个数据
	 */
	@Override
	public List<Book> getBooksBylimit(int currentPageIndex,
			int everyPageItemNumbers) {
		List<Book>  list =  new ArrayList<Book>();
		try {
			list = runner.query(TransactionManager.getConnection(), "SELECT * FROM book LIMIT ?,?", 		
					new BeanListHandler<Book>(Book.class), currentPageIndex,everyPageItemNumbers);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOBookImplement->getBooksBylimit()->获取书籍出现错误");
		}
		return list;
	}

	@Override
	public long getTotalBookItems() {
		
		long  number = 0;
		try {
			number  =  (long) runner.query(TransactionManager.getConnection(), "SELECT COUNT(*) FROM book", new ScalarHandler(1));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("DAOBookImplement->getTotalBookItems()->获取书籍总数目出现错误");
		}
		
		return number;
	}

	@Override
	public long getTotalBookPages(int everyPageItemNumbers) {

		long  number = 0;
		
		number = getTotalBookItems()%everyPageItemNumbers == 0 ? getTotalBookItems()/everyPageItemNumbers : getTotalBookItems()/everyPageItemNumbers + 1;
		
		return number;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book>   list =  null;
		try {
			list  =  runner.query(TransactionManager.getConnection(), "SELECT * FROM book", new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
