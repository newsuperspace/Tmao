package cn.com.obj.tmao.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnyName extends HttpServlet {

	/**
	 * ����get����ʽ�ύ��������
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * ����<form	 action="${pageContext.request.contextPath}/servlet/AnyName"  method="post"></form>�ı��ύ
	 * ���������ݣ��ɴ˿�ֻ֪�б�Ϊpost��ʱ��Ż���Ӧ���doPost�ķ����������κ�ʱ����ʹ�õ�Get����ʽ��
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);   // ���ڶ���ʱ����Get����ʽ�����Ϊ�˷�������ֱ�ӵ���Get�Ĺ��ܼ���
	}

}
