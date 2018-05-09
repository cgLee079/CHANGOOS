<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/photo/photo-view.css" />
<script src="${pageContext.request.contextPath}/resources/js/photo/photo-view.js"></script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8">
	</c:import>
	<div class="wrap-photo-view col-center">
		<div class="photo-view">
			<div class="photo-img"></div>
			<div class="photo-detail">
				<div class="photo-info">
					<div class="photo-name"></div>
					<div class="photo-date-loc">
					</div>
				</div>
				
				<div class="photo-tag"></div>
				<div class="photo-desc editor-contents"></div>
				<div class="photo-index display-none"></div>
			</div>
		</div>
		
		<div class="wrap-photo-list">
			<div class="btn btn-left-list h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_photo_arrow.png)">
			</div>
			
			<div class="photo-list">
				<c:forEach items="${photos}" var="photo" varStatus="status" >
					<div class="btn photo-item" onclick="showPhoto('${photo.seq}', '${status.index}')"  
						style="background-image: url('${pageContext.request.contextPath}${photo.snapsht}')">
					</div>
				</c:forEach>
			</div>
			
			<div class="btn btn-right-list"  style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_photo_arrow.png)">
			</div>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
	</c:import>
	
</div>

</body>
</html>