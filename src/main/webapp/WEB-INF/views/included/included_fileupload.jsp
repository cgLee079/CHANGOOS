<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="file-infos">
	<input type="hidden" id="boardType" value="<c:out value='${param.boardType}'/>"/>
	
 	<c:if test="${!empty files}">
		<c:forEach var="file" items="${files}">
			<div class="file-info">
				<input type="hidden" class="file-seq" value="<c:out value='${file.seq}'/>"/>
				<div class="file-info-name">[<fmt:formatNumber maxFractionDigits="2" value="${file.size/(1024 * 1024)}" /> MB]&nbsp;<c:out value='${file.realNm}'/></div>
				<div class="btn-file-remove btn-red-text" onclick="doFileRemove(this)">REMOVE(SERVER)</div>
			</div>
		</c:forEach>
	</c:if>

	<div class="file-info upload">
		<input type="hidden" class="file-seq"/>
		<input type="file" class="real-file" name="file" onchange="onFileChange(this)"/>
		<div class="file-info-name"></div>
		<div class="btn-file-remove btn-red-text" onclick="doFileRemove(this)">REMOVE</div>
		<div class="btn-file-upload btn-gray-text" onclick="$(this).siblings('.real-file').click();">UPLOAD</div>
	</div>
</div>
