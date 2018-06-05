<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-list.css" /> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-project-list.css"/>
<script src="${pageContext.request.contextPath}/resources/js/included/included-project-list.js"></script> 
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />

	<div class="wrap-project-list">
		<div class="projects-title">Projects</div>
		<%@ include file="/WEB-INF/views/included/included_project_list.jsp" %>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


