<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"%>
    <%@ taglib  uri="http://www.NB.com/Tmao"  prefix="mytag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/login.css"  rel="stylesheet"  type="text/css">
<title>登录页面</title>
</head>
<body>
		<form action="${pageContext.request.contextPath}/servlet/center"  method="get">
			<input type="hidden"  name="op"  value="login2service">
			<mytag:token/>
			<table>
				<tr><th align="center" colspan="3">用户登录<th/></tr>
				<tr>
					<td>用户名</td>
					<td><input type="text"  name="username"></td>
					<td></td>
				</tr>
				<tr>
					<td>密码</td>
					<td><input type="password" name="password"></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<div style="color: red;">${msg}</div>
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center"><input type="submit" value="登录"></td>
				</tr>
			</table>			
		</form>
</body>
</html>