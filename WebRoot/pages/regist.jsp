<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.NB.com/Tmao"  prefix="mytag" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${pageContext.request.contextPath}/css/register.css"  rel="stylesheet"  type="text/css">

<title>注册新用户</title>
</head>
<body>
	<div style="text-align: center;font-size: 20sp;">请填写注册信息</div>
		<form action="${pageContext.request.contextPath}/servlet/center"
			method="get">
			<mytag:token/>
			<input type="hidden" name="op" value="register2service">
			<table>
				<tr>
					<td>用户名</td>
					<td><input type="text" name="username"
						value="${formcustomer.username}"></td>
					<td>${formcustomer.message.username}</td>
				</tr>
				<tr>
					<td>密码</td>
					<td><input type="password" name="password""></td>
					<td>${formcustomer.message.password}</td>
				</tr>
				<tr>
					<td>电话</td>
					<td><input type="text" name="phone"
						value="${formcustomer.phone}"></td>
					<td>${formcustomer.message.username}</td>
				</tr>
				<tr>
					<td>住址</td>
					<td><input type="text" name="address"
						value="${formcustomer.address}"></td>
					<td>${formcustomer.message.address}</td>
				</tr>
				<tr>
					<td>邮件</td>
					<td><input type="text" name="email"
						value="${formcustomer.email}"></td>
					<td>${formcustomer.message.email}</td>
				</tr>
				<tr>
					<td>级别</td>
					<td>普通用户<input type="radio" name="level" value="normal"
						checked="checked">&nbsp;&nbsp; 特殊用户<input type="radio"
						name="level" value="vip">
					</td>
					<td>${formcustomer.message.username}</td>
				</tr>
				<tr>
					<td colspan="3"><input type="submit" value="提交"   style="margin-left: 45%;width: 300sp;"/></td>
				</tr>
			</table>
		</form>
</body>
</html>