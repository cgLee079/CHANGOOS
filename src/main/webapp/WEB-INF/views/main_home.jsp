<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home.css" />
</head>
<body>
	
<div class="wrapper">
	<c:import url="included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="main">
		<div class="wrap-introduce">
			<img class="me-icon" src="${pageContext.request.contextPath}resources/image/home_icon_me.png" />
			
			<div class="introduce">
				<div class="introduce-link">
					<a class="btn-link" href="https://github.com/cglee079" target="_blank" > <img src="${pageContext.request.contextPath}/resources/image/btn-main-git.svg"/></a>
					<a class="btn-link" href="https://www.instagram.com/cglee079" target="_blank" > <img src="${pageContext.request.contextPath}/resources/image/btn-main-insta.svg"/></a>
					<a class="btn-link"  href="mailto:cglee079@gmail.com"> <img src="${pageContext.request.contextPath}/resources/image/btn-main-email.svg"/></a>
				</div>
				
				<div class="introduce-desc">
					<c:out value="${home001}" escapeXml="false"></c:out>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>


