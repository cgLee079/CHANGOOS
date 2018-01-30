<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/item/item-list.css" /> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-item-list.css" /> 
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="main">
		<div class="items-title font-lora">
			Projects
		</div>
		<%@ include file="/WEB-INF/views/included/included_item_list.jsp" %>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


