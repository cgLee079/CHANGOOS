<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="tempDir">
<spring:eval expression="@location['image.temp.dir.url']"/>
</c:set>
<script>
var tempDirURL = '<c:out value="${tempDir}" />';
</script>


<div>
	<input type="hidden" id="imageValues" name="imageValues">
	<div class="image-upload" onclick="openImageUploadPopup('${param.editor}')">사진올리기</div>
	<div class="thumbnail-list">
		<div class="wrap-thumbnail temp">
			<input type="hidden" class="seq"> 
			<input type="hidden" class="filename"> 
			<input type="hidden" class="pathname"> 
			<input type="hidden" class="status">
			<input type="hidden" class="editorID">
			<img class="thumbnail" width='100%' height='100%' />
			<div class="btn-remove">삭제</div>
		</div>
		
	 	<c:if test="${!empty images}">
			<c:forEach var="image" items="${images}">
				<div class="wrap-thumbnail">
					<input type="hidden" class="seq" value="${image.seq}">
					<input type="hidden" class="editorID" value="${image.editorID}">
					<input type="hidden" class="pathname" value="${image.pathname}"> 
					<input type="hidden" class="filename" value="${image.filename}"> 
					<input type="hidden" class="status" value="${image.status}">
					<img class="thumbnail" width='100%' height='100%' src="${param.dir}${image.pathname}"/>
					<div class="btn-remove" onclick="removeImage(this, '${image.editorID}','${image.pathname}')">삭제</div>
				</div>
			</c:forEach>
		</c:if>
		
	</div>
</div>
