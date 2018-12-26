<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/photo/photo-manage.css" />
<script src="${pageContext.request.contextPath}/resources/js/photo/photo-manage.js"></script>

<c:set var="thumbDir"><spring:eval expression="@location['photo.thumb.dir.url']"/></c:set>
<script>
var thumbDir = '<c:out value="${thumbDir}"/>';
</script>


</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="wrap-photo-list">
		<div class="menu-manage">
			<a href="${pageContext.request.contextPath}/photo" class="btn">List</a>
			<a href="${pageContext.request.contextPath}/mgnt/photo/upload" class="btn">Upload</a>
		</div>
		
		<div class="photo-list">
			<table id="dg" style="width: 100%; height:100%;"></table>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>