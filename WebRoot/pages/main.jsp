<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--如果在这里使用下面的语句引入外部脚本文件，就是使整个站点的解析速度变得非常缓慢 --%>
<%--<script type="text/javascript"  src="${pageContext.request.contextPath}/js/try.js"></script> --%>
<title>这里是首页</title>
</head>
<%@ include file="/pages/head.jsp" %>
	
<h1  style="text-align: center;">我去年买了个表书城</h1>
<div style="margin-left: 25%"> 
	<table>
		<c:forEach   items="${list}"   varStatus="state">
			<c:if test="${state.index%3==0}">
				<tr>
					<c:forEach  items="${list}"   var="book"  varStatus="innerState">
						<c:if test="${innerState.index==state.index || innerState.index==(state.index+1) || innerState.index==(state.index+2)}">
							<td>
								<img alt="图片缺失" src="${pageContext.request.contextPath}/${book.path}/${book.newImageName}"><br>
								书名：${book.name}<br>
								作者：${book.author}
								售价：${book.money}
								<form action="${pageContext.request.contextPath}/servlet/center">
									<input type="hidden"  name="op"   value="add2car">
									<input  type="hidden"  name="bookid"   value="${book.id}">
									<input type="text"  name="number"   value="1"  style="width: 10%;">个
									<input  type="submit"   value="加入购物车" >
								</form>							
							</td>
						</c:if>
					</c:forEach>
				</tr>
			</c:if>
		</c:forEach>		
	</table>
</div>

</body>
</html>