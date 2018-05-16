<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="file-infos">
	<input type="hidden" id="boardType" value="<c:out value='${param.boardType}'/>"/>
	
	<c:if test="${!empty files}">
		<c:forEach var="file" items="${files}">
			<div class="file-info">
				<input type="hidden" class="file-seq" value="<c:out value='${file.seq}'/>"/>
				<div class="file-info-name">( <fmt:formatNumber maxFractionDigits="2" value="${file.size/(1024 * 1024)}" /> MB) <c:out value='${file.realNm}'/></div>
				<div class="btn-file-remove btn-red-text" onclick="doFileRemove(this)">REMOVE(SERVER)</div>
			</div>
		</c:forEach>
	</c:if>

	<div class="file-info temp">
		<input type="hidden" class="file-seq"/>
		<div class="file-info-name"></div>
		<div class="btn-file-remove btn-red-text" onclick="doFileRemove(this)">REMOVE</div>
	</div>
		
	<div class="file-update">
		<input type="file" id="itemFile" class="file-real" name="file" onchange="onFileChange(this)" style="display: none;"/>
		<div style="flex: 1"></div>
		<div class="btn-file-upload btn-gray-text" onclick="$(this).siblings('.file-real').click();">UPLOAD</div>
	</div>
</div>
