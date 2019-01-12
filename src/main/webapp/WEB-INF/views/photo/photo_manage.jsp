<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/photo/photo-manage.css" />
<script src="${pageContext.request.contextPath}/resources/js/photo/photo-manage.js"></script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="wrap-photo-list">
		<div class="menu-manage">
			<a href="${pageContext.request.contextPath}/photos" class="btn">List</a>
			<a href="${pageContext.request.contextPath}/photos/post" class="btn">Upload</a>
		</div>
		
		<div class="photo-list">
			<table id="dg" style="width: 100%; height:100%;"></table>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>