<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"  src="${pageContext.request.contextPath}/js/selector.js"></script>
<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/login.css" >
<title>书籍管理系统</title>
</head>
<%@ include file="/pages/head.jsp"%>
<h1 style="margin-left: 40%">书籍门类管理系统</h1>
	<form action="${pageContext.request.contextPath}/servlet/center">
		<input   type="hidden"  name="op"    value="deletebook">   
		<table border="3">
			<tr>
				<th>
					<a href="javascript:selectAll()">全选</a>&nbsp;
					<a href="javascript:inverse()">反选</a>&nbsp;
					<a href="javascript:deleteAll()">删除</a>
					<a href="${pageContext.request.contextPath}/servlet/center?op=newbook">新建</a>
				</th>
				<th>书名</th>
				<th>作者</th>
				<th>售价</th>
				<th>描述</th>
				<th>图片</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${limit.list}"  var="book">
				<tr>
					<td>
						<input type="checkbox"  name="id"  value="${book.id}">
					</td>
					<td>
						${book.name}
					</td>
					<td>
						${book.author}
					</td>
					<td>
						${book.money}
					</td>
					<td>
						${book.description}
					</td>
					<td>
						<!-- 展示图片 -->
						<img  src="${pageContext.request.contextPath}/${book.path}/${book.newImageName}" />
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/servlet/center?op=updatecategory&id=${item.id}">修改</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
<%@ include file="/pages/foot.jsp" %>
</body>
</html>