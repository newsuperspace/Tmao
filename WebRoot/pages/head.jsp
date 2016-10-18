<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
	<script type="text/javascript">
		var logout = function(path) {
			var result = window.confirm("是否确认注销？");
			if (result) {
				// 确认注销
				window.location.href = path;
			}
		}
	</script>
	<div style="text-align: right;font-size: 10sp;">
		<c:choose>
			<c:when test="${sessionScope.customer != null}">
				欢迎你,${customer.username}&nbsp;
				<c:if test="${'vip'==sessionScope.customer.level}">
					管理员权限&nbsp;
					<a
						href="javascript:logout('${pageContext.request.contextPath}/servlet/center?op=logout')">注销</a>&nbsp;
					<a
						href="${pageContext.request.contextPath}/servlet/center?op=categorymanager">门类管理</a>&nbsp;
					<a
						href="${pageContext.request.contextPath}/servlet/center?op=bookmanager">书籍管理</a>
				</c:if>
				<c:if test="${'normal'==sessionScope.customer.level }">
					<c:if test="${customer.actived }">
						普通用户[<font style="color: blue;">已激活</font>]
					</c:if>
					<c:if test="${!customer.actived }">
						普通用户[<font style="color: red;">未激活</font>]
					</c:if>
					<a
						href="javascript:logout('${pageContext.request.contextPath}/servlet/center?op=logout')">注销</a>&nbsp;
					<a
						href="${pageContext.request.contextPath}/servlet/center?op=myorders">我的订单</a>&nbsp;
					<a
						href="${pageContext.request.contextPath}/servlet/center?op=mycar">购物车</a>&nbsp;
				</c:if>
			</c:when>
			<c:otherwise>
					欢迎你，游客&nbsp;
					<a
					href="${pageContext.request.contextPath}/servlet/center?op=login">登录</a>&nbsp;
					<a
					href="${pageContext.request.contextPath}/servlet/center?op=register">注册</a>&nbsp;
					<a
					href="${pageContext.request.contextPath}/servlet/center?op=mycar">购物车</a>&nbsp;
			</c:otherwise>
		</c:choose>
	</div>