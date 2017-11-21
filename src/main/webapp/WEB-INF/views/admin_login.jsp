<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="wrapper">
		<form id="form-admin-login" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
			<input type="text" name="username" />
			<input type="password" name="password"/>
			<input type="submit"/>
		</form>
	</div>
</body>
</html>