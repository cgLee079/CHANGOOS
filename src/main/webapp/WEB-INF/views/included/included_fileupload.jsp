<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
.file-infos .file-info{
	display : flex;
	align-items : center;
	justify-content : space-between;
	border: 1px solid #CCC;
	border-radius: 5px;
	padding : 0.5rem;
}

.file-infos .file-info:NOT(:FIRST-CHILD){
	margin-top: 0.5rem;
}

.file-info-name {
	flex : 1;
}

.btn-file-upload, .btn-file-remove {
	max-width: 100px;
}
</style>

<div class="file-infos">
	<div class="file-info">
		<input type="file" id="itemFile" class="item-file" name="file"
			onchange="fileChange(this)" style="display: none;"/>
		<div class="btn-file-upload btn-gray-text" onclick="$(this).siblings('.item-file').click();">UPLOAD</div>
		<div class="btn-file-remove btn-red-text" onclick="fileRemove(this)" style="display: none;">REMOVE</div>
		<input type="hidden" class="file-seq"/>
	</div>
</div>

<script>
var form = $(".file-info").clone(true);
var boardType = "${param.boardType}";
var path = getContextPath() + "/admin" + "/" + boardType;
	
function fileRemove(tg){
	var fileInfo = $(tg).parents(".file-info");
	var seq = fileInfo.find(".file-seq").val();
	if(seq){
		var result = confirm("정말로 파일을 삭제하시겠습니까?\n확인 시 서버에서 파일이 삭제됩니다!!!");
		if(result){
			$.ajax({
				type	: "POST",
				url 	: path + "/deleteFile.do",
				dataType: "JSON",
				data 	: {
					"seq" : seq
				},
				success : function(data) {
					if(data.result === true){
						alert("서버에서 파일이 삭제되었습니다.");
						fileInfo.remove();
					} else{
						alet("파일삭제 실패!");
					}
				}	
			});
		}
	} else{
		fileInfo.remove();
	}
}

function fileChange(tg){
	var file = tg.files[0];
	var fileInfo = $(tg).parents(".file-info");
	var fileInfos= fileInfo.parents(".file-infos");
	
	if($(tg).val()){
		fileInfo.find(".btn-file-remove").css("display", "");
		fileInfo.find(".btn-file-upload").css("display", "none");
		
		$("<div>", {
			"class" : "file-info-name",
			text : "(" + (file.size/(1024 * 1024)).toFixed(2) + " MB) " + file.name 
		}).prependTo(fileInfo);
		
		fileInfos.append(form.clone(true));
	} 
}
</script>
<c:if test="${!empty files}">
	<c:forEach var="file" items="${files}">
		<script>
			(function(){
				var fileInfos= $(".file-infos");										
				var fileInfo = form.clone(true);
				
				fileInfo.find(".btn-file-remove").css("display", "");
				fileInfo.find(".btn-file-upload").css("display", "none");
				fileInfo.find(".file-seq").val("${file.seq}");
				
				console.log("${file}");
				
				$("<div>", {
					"class" : "file-info-name",
					text : "(" + ("${file.size}"/(1024 * 1024)).toFixed(2) + " MB) " + "${file.realNm}" 
				}).prependTo(fileInfo);
				
				fileInfos.prepend(fileInfo);
			})();
		</script>							
	</c:forEach>
</c:if>