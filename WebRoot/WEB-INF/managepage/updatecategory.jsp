<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://www.NB.com/Tmao" prefix="mytag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/register.css"
	rel="stylesheet" type="text/css">
<title>书籍门类管理系统</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/servlet/center">
		<input type="hidden" name="op" value="updatecategory">
		<input type="hidden" name="id"  value="${param.id}">
		<mytag:token />
		<table>
			<tr>
				<th align="center" colspan="3">更新门类信息
				<th />
			</tr>
			<tr>
				<td>名称</td>
				<td><input type="text" name="name"  value="${category.name}"></td>
				<td></td>
			</tr>
			<tr>
				<td>描述</td>
				<td><input type="text" name="description"  value="${category.description}"></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<div style="color: red;">${msg}</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="提交"></td>
			</tr>
		</table>
	</form>
</body>
</html>