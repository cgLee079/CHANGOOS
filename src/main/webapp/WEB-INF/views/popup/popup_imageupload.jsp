<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/popup/popup-imageupload.css" />
<script src="${pageContext.request.contextPath}/resources/js/popup/popup-imageupload.js"></script>

<c:set var="tempDir"><spring:eval expression="@location['temp.dir.url']"/></c:set>
<spring:eval expression="@constant['image.max.width']" var='maxWidth' />
<script>
var tempDir = '${tempDir}';
</script>

</head>
<body>
	<input type="hidden" id="editor" value='<c:out value="${editor}"/>'>
	
	<div class="upload-form">
		<input id="images" type="file" multiple="multiple" accept="image/gif,image/jpeg,image/png" onchange="onImageChange()">
		<div class="btn-image-upload" onclick="$(this).siblings('#images').click();">파일선택</div>
		<div class="upload-set">
			<input id="width" class="image-width" type="number" value="<c:out value="${maxWidth}" />">
			<div class="btn-image-upload" onclick="doUpload()">올리기</div>
		</div>
	</div>
	
	<div class="image-list">
		<div class="wrap-image">
			<input type="hidden" class="pathname">
			<input type="hidden" class="filename">
			<input type="hidden" class="status">
			<img class="image" src='${pageContext.request.contextPath}${tempDir}' width='100%' height='100%'/>
			<div class="btn-remove" onclick="removeImage(this)">삭제</div>
		</div>
	</div>
</body>
</html>