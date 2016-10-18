<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib   uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   <%@ taglib  uri="http://www.NB.com/Tmao"   prefix="mytag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>

<script type="text/javascript">
	function pay(formid,money)
	{
		var  form  =  document.getElementById(formid);
		var   choice  =  window.confirm("您将要花费"+money+"元，是否确认支付？");
		if(choice)
		{
			// 确认支付
			form.submit();
		}
		else{
			// 不支付，什么也不做
		}
	}
</script>


</head>
<body>
<%@  include file="/pages/head.jsp" %>

<h1  style="text-align: center;">我的订单</h1>
<div style="margin-left: 30%"> 
	<table>
		<tr>
			<th>订单号</th>
			<th>总花费</th>
			<th>订单项数</th>
			<th>订单状态</th>
			<th>操作(删除 付款)</th>
		</tr>
		<c:forEach  items="${orders}"  var="order"  varStatus="state" >
			<form  id="a${state.count}"   action="${pageContext.request.contextPath}/servlet/center">
				<input  type="hidden"   name="op"  value="pay4it" >
				<input  type="hidden"   name="ordersnum"   value="${order.ordersnum}" >
				<mytag:token/>
				<tr>
					<td>${order.ordersnum}</td>
					<td>${order.money}</td>
					<td>${order.num}</td>
					<td>
						<c:if  test="${order.status==0}"  >
							<font style="font-size: 30sp;color: red;" >未支付</font>
						</c:if>
						<c:if  test="${order.status==1}"  >
							<font style="font-size: 30sp;color: green;" >已支付</font>
						</c:if>
						<c:if  test="${order.status==2}"  >
							<font style="font-size: 30sp;color: black;" >已完成</font>
						</c:if>
					</td>
					<c:choose>
						<c:when test="${order.status==0}">
							<td>
								<a   href="${pageContext.request.contextPath}/servlet/center?op=deleteOrders&ordersnum=${order.ordersnum}">删除</a>
								<a  href="javascript:pay('a${state.count}','${order.money}')">支付</a>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								无法操作
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</form>
		</c:forEach>			
	</table>		
</div>
</body>
</html>