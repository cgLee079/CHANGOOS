<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="statusBe"><spring:eval expression="@constant['file.status.id.be']"/></c:set>
<c:set var="statusNew"><spring:eval expression="@constant['file.status.id.new']"/></c:set>
<c:set var="statusUnnew"><spring:eval expression="@constant['file.status.id.unnew']"/></c:set>
<c:set var="statusRemove"><spring:eval expression="@constant['file.status.id.remove']"/></c:set>
<script>
var FILE_STATUS_BE 		= '<c:out value="${statusBe}"/>';
var FILE_STATUS_NEW 	= '<c:out value="${statusNew}"/>';
var FILE_STATUS_UNNEW 	= '<c:out value="${statusUnnew}"/>';
var FILE_STATUS_REMOVE 	= '<c:out value="${statusRemove}"/>';
</script>

<div class="file-infos">
	<input type="hidden" name="fileValues" id="fileValues"/>
	
	<div class="file-upload">
		<div></div>
		<input type="file" class="real-file" name="file" onchange="onFileChange(this)" multiple="multiple"/>
		<div class="btn-file-upload btn-gray-text" onclick="$(this).siblings('.real-file').click();">UPLOAD</div>
	</div>
	
 	<c:if test="${!empty files}">
		<c:forEach var="file" items="${files}">
			<div class="file-info">
				<input type="hidden" class="file-seq" value="<c:out value='${file.seq}'/>"/>
				<input type="hidden" class="file-pathname" value="<c:out value='${file.pathname}'/>"/>
				<input type="hidden" class="file-filename" value="<c:out value='${file.filename}'/>"/>
				<input type="hidden" class="file-size" value="<c:out value='${file.size}'/>"/>
				<input type="hidden" class="file-status" value="<c:out value='${statusBe}'/>"/>
				<div class="file-info-name">[<fmt:formatNumber maxFractionDigits="2" value="${file.size/(1024 * 1024)}" /> MB]&nbsp;<c:out value='${file.filename}'/></div>
				<div class="btn-file-remove btn-red-text" onclick="doFileRemove(this)">REMOVE</div>
			</div>
		</c:forEach>
	</c:if>

	<div class="file-info temp">
		<input type="hidden" class="file-seq"/>
		<input type="hidden" class="file-pathname"/>
		<input type="hidden" class="file-filename"/>
		<input type="hidden" class="file-size"/>
		<input type="hidden" class="file-status"/>
		<div class="file-info-name"></div>
		<div class="btn-file-remove btn-red-text" onclick="doFileRemove(this)">REMOVE</div>
	</div>
		
</div>
