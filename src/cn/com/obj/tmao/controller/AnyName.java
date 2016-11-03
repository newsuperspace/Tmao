package cn.com.obj.tmao.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnyName extends HttpServlet {

	/**
	 * 处理get请求方式提交来的请求
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * 处理<form	 action="${pageContext.request.contextPath}/servlet/AnyName"  method="post"></form>的表单提交
	 * 过来的数据，由此可知只有表单为post的时候才会响应这个doPost的方法，其他任何时候都是使用的Get请求方式。
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);   // 由于多数时候都是Get请求方式，因此为了方便这里直接调用Get的功能即可
	}

}
