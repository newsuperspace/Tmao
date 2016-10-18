<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   isELIgnored="false"%>
   <%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"  src="${pageContext.request.contextPath}/js/selector.js"></script>
<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/login.css" >
<title>书籍门类管理系统</title>
</head>
<%@ include file="/pages/head.jsp"%>
<h1 style="margin-left: 35%">书籍门类管理系统--添加新书籍</h1>
	<form action="${pageContext.request.contextPath}/servlet/center">
		<input   type="hidden"  name="op"    value="deletecategory">   
		<table border="3">
			<tr>
				<th>
					<a href="javascript:selectAll()">全选</a>&nbsp;
					<a href="javascript:inverse()">反选</a>&nbsp;
					<a href="javascript:deleteAll()">删除</a>
					<a href="${pageContext.request.contextPath}/servlet/center?op=newcategory">新建</a>
				</th>
				<th>书类名</th>
				<th>书类描述</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${categories}"  var="item">
				<tr>
					<td>
						<input type="checkbox"  name="id"  value="${item.id}">
					</td>
					<td>
						${item.name}
					</td>
					<td>
						${item.description}
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/servlet/center?op=updatecategory&id=${item.id}">修改</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	
	</form>




</body>
</html>