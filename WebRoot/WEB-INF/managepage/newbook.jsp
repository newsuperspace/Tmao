<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://www.NB.com/Tmao" prefix="mytag"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css">
<title>书籍管理系统</title>
</head>
<form action="${pageContext.request.contextPath}/servlet/center"
	method="post" enctype="multipart/form-data">
	<input type="hidden" name="op" value="addbook"> <input
		type="hidden" name="id" value="${book.id}">
	<mytag:token />
	<table border="3sp">
		<tr>
			<th align="center" colspan="3">添加新书籍
			<th />
		</tr>
		<tr>
			<td>名称</td>
			<td><input type="text" name="name" value="${book.name}"></td>
			<td><font>${book.message.name}</font></td>
		</tr>
		<tr>
			<td>作者</td>
			<td><input type="text" name="author" value="${book.author}"></td>
			<td><font>${book.message.author}</font></td>
		</tr>
		<tr>
			<td>售价</td>
			<td><input type="text" name="money" value="${book.money}"></td>
			<td><font>${book.message.money}</font></td>
		</tr>
		<tr>
			<td>描述</td>
			<td><input type="text" name="description"
				value="${book.description}"></td>
			<td><font>${book.message.description}</font></td>
		</tr>
		<tr>
			<td>上传图片</td>
			<td><c:choose>
					<c:when test="${book.path== null}">
						<input type="file" name="image">
					</c:when>
					<c:otherwise>
						<img src="${book.path}/${book.newImageName}">
					</c:otherwise>
				</c:choose></td>
			<td><font>${book.message.path}</font></td>
		</tr>
		<tr>
			<td>门类</td>
			<td><select name="categoryId">
					<c:forEach items="${category}" var="category" varStatus="state">
						<c:choose>
							<c:when test="${book!=null}">
								<c:if test="${category.id==book.categoryId}">
									<option value="${category.id}" selected="selected">${category.name}</option>
								</c:if>
								<c:if test="${category.id!=book.categoryId}">
									<option value="${category.id}">${category.name}</option>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${state.count==1}">
									<option value="${category.id}" selected="selected">${category.name}</option>
								</c:if>
								<c:if test="${state.count!=1}">
									<option value="${category.id}">${category.name}</option>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="3" align="center"><input type="submit" value="提交"></td>
		</tr>
	</table>
</form>
</body>
</html>