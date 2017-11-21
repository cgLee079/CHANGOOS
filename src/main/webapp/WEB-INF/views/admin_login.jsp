<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.wrap-form{
		display: flex;
		justify-content: center;
	}
</style>

<c:if test="${param.result eq false}">
	<script>
		alert("로그인 실패!");
	</script>
</c:if>
</head>

<body>
	<div class="wrapper">
		<div class="wrap-form">
			<form id="form-admin-login" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
				<table>
				<tr>
				<td>USERNAME</td>
				<td><input type="text" name="username" /></td>
				</tr>
				<tr>
				<td>PASSWORD</td>
				<td><input type="password" name="password"/></td>
				</tr>
				<tr>
				<td></td>
				<td><input type="submit"/></td>
				</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>