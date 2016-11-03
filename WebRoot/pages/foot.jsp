<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script
		type="text/javascript">
	function change() {
		var former = document.getElementById("selecter");
		former.submit();
	}
</script>
<span style="margin-left: 40%;"> 
	<form id="selecter"
		action="${limit.path}">
		<input   type="hidden"   name="currentPageNumber"   value="${limit.currentPageNumber}">
		
		<a
			href="${limit.path}&currentPageNumber=1&everyPageItemNumber=${limit.everyPageItemNumber}">首页</a>&nbsp;
		<a
			href="${limit.path}&currentPageNumber=${limit.previousPageNumber}&everyPageItemNumber=${limit.everyPageItemNumber}">上一页</a>&nbsp;


		<c:forEach begin="${limit.smallPageNumber}"
			end="${limit.bigPageNumber}" step="1" var="num">
			<a
				href="${limit.path}&currentPageNumber=${num}&everyPageItemNumber=${limit.everyPageItemNumber}">[${num}]</a>&nbsp;	
		</c:forEach>
		<a
			href="${limit.path}&currentPageNumber=${limit.nextPageNumber}&everyPageItemNumber=${limit.everyPageItemNumber}">下一页</a>&nbsp;
		<a
			href="${limit.path}&currentPageNumber=${limit.totalPages}&everyPageItemNumber=${limit.everyPageItemNumber}">尾页</a>&nbsp;

		每页 <select onchange="change();" name="everyPageItemNumber">
			<c:choose>
				<c:when test="${limit.everyPageItemNumber == 10}">
					<option value="10"  selected="selected">10</option>
				</c:when>
				<c:otherwise>
					<option value="10"  >10</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${limit.everyPageItemNumber == 20}">
					<option value="20"  selected="selected">20</option>
				</c:when>
				<c:otherwise>
					<option value="20"  >20</option>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${limit.everyPageItemNumber == 30}">
					<option value="30"  selected="selected">30</option>
				</c:when>
				<c:otherwise>
					<option value="30"  >30</option>
				</c:otherwise>
			</c:choose>
		</select> 行&nbsp;
	</form>
</span>
