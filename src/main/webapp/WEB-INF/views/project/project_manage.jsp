<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-manage.css" />
<script src="${pageContext.request.contextPath}/resources/js/project/project-manage.js"></script>

</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	<div class="wrap-project-list">
		<div class="menu-manage">
			<a href="${pageContext.request.contextPath}/project" class="btn">List</a>
			<a href="${pageContext.request.contextPath}/admin/project/upload" class="btn">Upload</a>
		</div>
		
		<div class="project-list">
			<div class="list-item list-item-header">
				<div class="item-seq">SEQ</div>
				<div class="item-sort">SORT</div>
				<div class="item-title">TITLE</div>
				<div class="item-snapsht">SNAPSHT</div>
				<div class="item-detail-view">보기</div>
				<div class="item-modify">수정</div>
				<div class="item-delete">삭제</div>
			</div>
		
			<c:forEach var="project" items="${projects}">
				<div class="list-item">
					<div class="item-seq"><c:out value="${project.seq}"/></div>
					<div class="item-sort"><c:out value="${project.sort}"/></div>
					<div class="item-title"><c:out value="${project.title}"/></div>
					<div class="item-snapsht"><img src="${pageContext.request.contextPath}${project.snapsht}"/></div>
					<div class="btn item-detail-view"><a href="javascript:void(0)" onclick="projectView('${project.seq}')">보기</a></div>
					<div class="btn item-modify"><a href="javascript:void(0)" onclick="projectModify('${project.seq}')">수정</a></div>
					<div class="btn item-delete"><a href="javascript:void(0)" onclick="projectDelete('${project.seq}')">삭제</a></div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>