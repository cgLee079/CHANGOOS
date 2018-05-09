<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE>
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
			<a href="${pageContext.request.contextPath}/photo" class="btn">List</a>
			<a href="${pageContext.request.contextPath}/admin/photo/upload" class="btn">Upload</a>
		</div>
		
		<div class="project-list">
			<div class="list-photo list-photo-header">
				<div class="photo-seq">SEQ</div>
				<div class="photo-title">TITLE</div>
				<div class="photo-img">IMG</div>
				<div class="photo-device">DEVICE</div>
				<div class="photo-loc">LOC</div>
				<div class="photo-date">DATE</div>
				<div class="photo-time">TIME</div>
				<div class="photo-modify">수정</div>
				<div class="photo-delete">삭제</div>
			</div>
		
			<c:forEach var="photo" items="${photos}">
				<div class="list-photo">
					<div class="photo-seq"><c:out value="${photo.seq}"/></div>
					<div class="photo-title"><c:out value="${photo.name}"/></div>
					<div class="photo-img"><img src="${pageContext.request.contextPath}${photo.snapsht}"/></div>
					<div class="photo-device"><c:out value="${photo.device}"/></div>
					<div class="photo-loc"><c:out value="${photo.location}"/></div>
					<div class="photo-date"><c:out value="${photo.date}"/></div>
					<div class="photo-time"><c:out value="${photo.time}"/></div>
					<div class="btn photo-modify"><a href="javascript:void(0)" onclick="photoModify('${photo.seq}')">수정</a></div>
					<div class="btn photo-delete"><a href="javascript:void(0)" onclick="photoDelete('${photo.seq}')">삭제</a></div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>