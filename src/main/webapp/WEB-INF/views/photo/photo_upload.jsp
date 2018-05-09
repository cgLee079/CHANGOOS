<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/photo/photo-upload.css" />
<script src="${pageContext.request.contextPath}/resources/js/photo/photo-upload.js"></script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-upload-form">
			<form id="upload-form" action="${pageContext.request.contextPath}/admin/photo/upload.do" 
				method="post" enctype="multipart/form-data">
				
				<c:set var="sort" value="99999"/>
				<c:if test="${not empty photo}">
					<input type="hidden" name="seq" value="<c:out value='${photo.seq}'/>"/>
					<input type="hidden" name="image" value="<c:out value='${photo.image}'/>"/>
					<input type="hidden" name="snapsht" value="<c:out value='${photo.snapsht}'/>"/>
					<c:set var="sort" value="${photo.sort}"/>
				</c:if>
				
				
				<div class="upload-item">
					<div class="upload-item-name">SORT</div>
					<div class="upload-item-input">
						<input type="text" id="sort" name="sort" class="photo-sort" value="<c:out value='${sort}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">IMAGE</div>
					<div class="upload-item-input">
						<input type="file" id="imageFile" name="imageFile" class="photo-image" accept="image/*"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">LOCATION</div>
					<div class="upload-item-input">
						<input type="text" id="location" name="location" class="photo-location" value="<c:out value='${photo.location}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">DATE</div>
					<div class="upload-item-input">
						<input type="text" id="date" name="date" class="photo-date" value="<c:out value='${photo.date}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">TIME</div>
					<div class="upload-item-input">
						<input type="text" id="time" name="time" class="photo-date" value="<c:out value='${photo.time}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">TAG</div>
					<div class="upload-item-input">
						<input type="text" id="tag" name="tag" class="photo-tag" value="<c:out value='${photo.tag}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">NAME</div>
					<div class="upload-item-input">
						<input type="text" id="name" name="name" class="photo-name" value="<c:out value='${photo.name}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">DESC</div>
					<div class="upload-item-input">
						<textarea id="desc" name="desc" class="photo-desc">
							<c:out value="${photo.desc}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name"></div>
					<div class="upload-item-input">
						<input type="submit" class="photo-submit">
					</div>
				</div>
			</form>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>