<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<style>

.header{
	text-align: center;
}

.wrap-upload-form{
	width: 70%;
	margin: 0px auto;
}

.item-name{
	width : 100%;
}
.item-desc{
	width: 100%;
	height: 100px;
}

.item-snapsht{
	width: 100%;
}

.item-dirtURL{
	width: 100%;
}

.submit{
	width: 100%;
}
</style>

<c:if test="${!empty item}">
	<script>
		$(document).ready(function(){
			$("#name").val("${item.name}");
			$("#desc").val("${item.desc}");
			$("#dirtURL").val("${item.dirtURL}");
			$("#content").val("${item.content}");
			$("<input>", { type : "hidden", name : "seq", value: "${item.seq}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "snapsht", value: "${item.snapsht}"}).appendTo($("#upload-form"));
		});
	</script>
</c:if>

</head>
<body>
	<div class="header">
		<h1>test</h1>
	</div>
	<div class="main">
		<div class="wrap-upload-form">
			<form id="upload-form" action="${pageContext.request.contextPath}/item/upload.do" 
				method="post"  enctype="multipart/form-data">
				<h5>name</h5>
				<input type='text' id="name" name='name' class="item-name"/>
				
				<h5>desc</h5>
				<textarea id="desc" name="desc" class="item-desc"></textarea>
				
				<h5>dirtURL</h5>
				<input type="text" id="dirtURL" name="dirtURL" class="item-dirtURL"/>
				
				<h5>contents</h5>
				<textarea name="content" id="content"></textarea>
				<script>
					var editor = CKEDITOR.replace("content", {
						filebrowserUploadUrl : '<%=request.getContextPath()%>' + "/item/imgUpload.do"
					});

					CKEDITOR.on('dialogDefinition', function(ev) {
						var dialogName = ev.data.name;
						var dialog = ev.data.definition.dialog;
						var dialogDefinition = ev.data.definition;

						if (dialogName == 'image') {
							dialogDefinition.removeContents('Link'); //링크 탭 제거
							dialogDefinition.removeContents('advanced'); //상세정보 탭 제거
						}
					});
				</script>
				
				<h5>snapshot</h5>
				<input type="file" id="snapshtFile" name="snapshtFile"/>
				
				<hr>
				<input type="submit" class="submit">
				
			</form>
		</div>
	</div>
</body>
</html>