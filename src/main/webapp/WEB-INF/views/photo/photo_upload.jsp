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
			<div class="upload-title">사진 업로드</div>
			
			<form id="uploadForm" action="${pageContext.request.contextPath}/mgnt/photo/upload.do" 
				method="post" enctype="multipart/form-data">
				
				<c:if test="${not empty photo}">
					<input type="hidden" id="seq" name="seq" value="<c:out value='${photo.seq}'/>"/>
				</c:if>
				
				<input type="hidden" id="filename" name="filename" value="<c:out value='${photo.filename}'/>"/>
				<input type="hidden" id="photoPath" name="photoPath" value="<c:out value='${photo.photoPath}'/>"/>
				<input type="hidden" id="photoPathname" name="photoPathname" value="<c:out value='${photo.photoPathname}'/>"/>
				<input type="hidden" id="snapshotPath" name="snapshotPath" value="<c:out value='${photo.snapshotPath}'/>"/>
				<input type="hidden" id="snapshotPathname" name="snapshotPathname" value="<c:out value='${photo.snapshotPathname}'/>"/>
				
				<div class="upload-item">
					<div class="upload-item-name">사진</div>
					<div class="upload-item-input">
						<img id="snapshot" onclick="$(this).siblings('#imageFile').click();" src="<c:out value='${photo.snapshotPath}${photo.snapshotPathname}'/>" height="150">
						<input type="file" id="imageFile" name="imageFile" class="photo-image" accept="image/*" onchange="onPhotoChnage(this)"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">촬영기기</div>
					<div class="upload-item-input">
						<input type="text" id="device" name="device" class="photo-device" value="<c:out value='${photo.device}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">촬영일</div>
					<div class="upload-item-input">
						<input type="text" id="date" name="date" class="photo-date" value="<c:out value='${photo.date}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">촬영시간</div>
					<div class="upload-item-input">
						<input type="text" id="time" name="time" class="photo-time" value="<c:out value='${photo.time}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">촬영위치</div>
					<div class="upload-item-input">
						<input type="text" id="location" name="location" class="photo-location" value="<c:out value='${photo.location}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">태그</div>
					<div class="upload-item-input">
						<input type="text" id="tag" name="tag" class="photo-tag" value="<c:out value='${photo.tag}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">제목</div>
					<div class="upload-item-input">
						<input type="text" id="name" name="name" class="photo-name" value="<c:out value='${photo.name}'/>"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">내용</div>
					<div class="upload-item-input">
						<textarea id="desc" name="desc" class="ckeditor-autosave photo-desc">
							<c:out value="${photo.desc}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name"></div>
					<div class="upload-item-input photo-submit">
						<a class="btn" onclick="Progress.start(); history.back();">취소</a>
						<a class="btn" onclick="Progress.start(); $('#uploadForm').submit()">저장</a>
					</div>
				</div>	
				
			</form>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>