<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-list.css" /> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-project-list.css" /> 
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="main">
		<div class="projects-title font-lora">
			Projects
		</div>
		<%@ include file="/WEB-INF/views/included/included_project_list.jsp" %>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


