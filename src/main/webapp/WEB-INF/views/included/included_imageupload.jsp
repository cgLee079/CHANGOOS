<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var='maxWidth'><spring:eval expression="@constant['image.max.width']" /></c:set>

<script>
imageUploader.maxWidth = '<c:out value="${maxWidth}"/>'
imageUploader.status = {
	'BE' 	: '<c:out value="${imageStatus.BE}"/>',
	'NEW'	: '<c:out value="${imageStatus.NEW}"/>',
	'UNNEW' : '<c:out value="${imageStatus.UNNEW}"/>',
	'REMOVE': '<c:out value="${imageStatus.REMOVE}"/>'
}
</script>

<div>
	<input type="hidden" id="imageValues" name="imageValues">
	<div class="image-upload" onclick="openImageUploadPopup('${param.editor}')">사진올리기</div>
	<div class="image-list">
		<div class="wrap-image temp">
			<input type="hidden" class="seq"> 
			<input type="hidden" class="filename"> 
			<input type="hidden" class="pathname"> 
			<input type="hidden" class="status">
			<input type="hidden" class="editorID">
			<img class="image" width='100%' height='100%' />
			<div class="btn-remove" onclick="imageUploader.removeImage(this)" >삭제</div>
		</div>
		
	 	<c:if test="${!empty images}">
			<c:forEach var="image" items="${images}">
				<div class="wrap-image">
					<input type="hidden" class="seq" value="${image.seq}">
					<input type="hidden" class="editorID" value="${image.editorID}">
					<input type="hidden" class="pathname" value="${image.pathname}"> 
					<input type="hidden" class="filename" value="${image.filename}"> 
					<input type="hidden" class="status" value="${imageStatus.BE}">
					<img class="image" width='100%' height='100%' src="${pageContext.request.contextPath}${param.dir}${image.pathname}"/>
					<div class="btn-remove" onclick="imageUploader.removeImage(this)">삭제</div>
				</div>
			</c:forEach>
		</c:if>
		
	</div>
</div>
