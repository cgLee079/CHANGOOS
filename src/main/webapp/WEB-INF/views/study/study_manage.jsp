<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study/study-manage.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datagrid-common.css" />
<script src="${pageContext.request.contextPath}/resources/js/datagrid-common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/study/study-manage.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-detailview.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-groupview.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-cellediting.js"></script>

</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="wrap-study-list">
		<div class="menu-manage">
			<a href="${pageContext.request.contextPath}/studies">List</a>
			<a href="${pageContext.request.contextPath}/studies/post">Upload</a>
		</div>
		
		<div class="study-list">
			<div id="tb" style="padding:10px 5px; text-align: right;">
			    <span>TITLE : </span>
			    <input id="title" style="padding :2px 3px; border:1px solid #CCC" onkeyup="javascript:if(event.keyCode==13){doSearch();}" >
			    <a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">검색</a>
			</div>
		
			<table id="dg" style="width: 100%; height:100%;">
			</table>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>