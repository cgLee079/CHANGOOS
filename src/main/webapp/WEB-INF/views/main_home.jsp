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
			<div class="me-icon" style="background-image : url(resources/image/home_icon_me.png);">
			</div>
			
			<div class="introduce">
				<div class="introduce-name">
					<div class="ml9">
					  <span class="text-wrapper">
					    <span class="letters">Changoo Lee</span>
					  </span>
					</div>
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


