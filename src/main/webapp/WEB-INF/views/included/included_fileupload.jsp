<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="file-infos">
	<input type="hidden" id="boardType" value="<c:out value='${params.boardType}'/>"/>
	<div class="file-info">
		<input type="file" id="itemFile" class="item-file" name="file" onchange="fileChange(this)" style="display: none;"/>
		<div class="btn-file-upload btn-gray-text" onclick="$(this).siblings('.item-file').click();">UPLOAD</div>
		<div class="btn-file-remove btn-red-text" onclick="fileRemove(this)" style="display: none;">REMOVE</div>
		<input type="hidden" class="file-seq"/>
	</div>
</div>

<c:if test="${!empty files}">
	<c:forEach var="file" items="${files}">
		<script>
			(function(){
				var fileInfos= $(".file-infos");										
				var fileInfo = form.clone(true);
				
				fileInfo.find(".btn-file-remove").css("display", "");
				fileInfo.find(".btn-file-upload").css("display", "none");
				fileInfo.find(".file-seq").val("${file.seq}");
				
				$("<div>", {
					"class" : "file-info-name",
					text : "(" + ("${file.size}"/(1024 * 1024)).toFixed(2) + " MB) " + "${file.realNm}" 
				}).prependTo(fileInfo);
				
				fileInfos.prepend(fileInfo);
			})();
		</script>							
	</c:forEach>
</c:if>