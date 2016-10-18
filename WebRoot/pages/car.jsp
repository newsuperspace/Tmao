<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@  taglib  uri="http://www.NB.com/myFunction"  prefix="myfun" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的购物车</title>

<script type="text/javascript">
	function changeNumber(num)
	{
		// 参数num是表单从0开始的索引下标号
		window.document.forms[num].submit();
	}
</script>

</head>
<body>
<%@ include file="/pages/head.jsp" %>
	<h1  style="text-align: center;">我的购物车</h1>
<div style="margin-left: 30%"> 
	<table>
		<tr>
			<th>图片</th>
			<th>商品名</th>
			<th>数量</th>
			<th>单价</th>
			<th>总价</th>
			<th>操作</th>
		</tr>
		<c:forEach  items="${orderItemList}"   var="orderitem"   varStatus="state">
			<tr>
				<c:forEach  items="${booklist}"   var="book">
					<c:if test="${book.id==orderitem.bookid}">
						<form  action="${pageContext.request.contextPath}/servlet/center">
							<input   type="hidden"    name="op"   value="updateCar" >
							<input   type="hidden"    name="orderitemID"   value="${orderitem.id}">
							<td><img alt="图片丢失" src="${pageContext.request.contextPath}/${book.path}/${book.newImageName}"></td>
							<td>${book.name}</td>
							<td><input type="text"  name="number"  value="${orderitem.num}"  onblur="changeNumber(${state.index});" /></td>
							<td>${book.money}</td>
							<td>${orderitem.money}</td>
							<td><a  href="${pageContext.request.contextPath}/servlet/center?op=deleteOrderItem4car&orderItemID=${orderitem.id}">删除</a></td>
						</form>
					</c:if>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>		
</div>
<div style="margin-left: 50%">
	<font  style="font-size: 50sp;color: #ff6600;border: 10sp;">合计：${myfun:totalMoney(orderItemList)}</font> &nbsp;&nbsp;
	<a  href="${pageContext.request.contextPath}/servlet/center?op=formOrders"><font  style="font-size: 50sp;border: 10sp;">提交订单</font></a>
</div>
</body>
</html>