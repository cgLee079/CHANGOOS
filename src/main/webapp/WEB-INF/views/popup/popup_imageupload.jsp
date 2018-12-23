<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp"%>
<script type="text/javascript">
var wrapImageTemp;

$(document).ready(function(){
	wrapImageTemp = $(".wrap-image").clone();
	$(".wrap-image").remove();
})


function onImageChange() {
    files = $('#images').prop('files');
    for(var i = 0; i < files.length; i++){
    	var file = files[i];
    	
    	var fileReader = new FileReader();
		fileReader.onload = function (file){
			return function (evt) {
				var base64 = evt.target.result;
	 	      	$.ajax({
	 	      		type	: "post",
	 	      		url 	: getContextPath() + "/img/upload.do",
	 	      		dataType: "JSON",
	 	      		async 	: false,
	 	      		data 	: {
	 	      			"base64" : base64,
	 	      			"filename" : file.name
	 	      		},
	 	      		success : function(result) {
	 	      			var path = result.path;
	 	      			var pathname = result.pathname;
	 	      			var filename = result.filename;
	 	      			var wrapImage = wrapImageTemp.clone();
	 	      			wrapImage.appendTo($(".image-list"));
	 	      			wrapImage.find(".image").attr("src", path + pathname);
	 	      			wrapImage.find(".path").val(path);
	 	      			wrapImage.find(".filename").val(filename);
	 	      			wrapImage.find(".pathname").val(pathname);
	 	      		}
	 	      	})
    	  }
		}(file);
		
		fileReader.readAsDataURL(files[i]);
    }
}

	
function removeImage(tg){
	$(tg).parent(".wrap-image").remove();
}

function doUpload(){
	var wrapImages = $(".wrap-image");
	var editorID =  $("#editor").val();
	var width = $("#width").val();
	
	for(var i = 0; i < wrapImages.length; i++){
		//Editor에 이미지 첨부
		var wrapImage = $(wrapImages[i]);
		var editorImage = $("<image>");
		var path = wrapImage.find(".path").val();
		var pathname = wrapImage.find(".pathname").val();
		var filename = wrapImage.find(".filename").val();
		opener.imageUploader.insertCKEditor(editorID, path, pathname, filename, width);
		opener.imageUploader.insertThumbnail(editorID, path, pathname, filename);
	}
	

	window.close();
}

</script>
</head>
<style>
#images{
	display: none;
}

.upload-form{
	display : flex;
	padding : 15px 30px;
	align-items: center;
	justify-content: space-between;	
	border-bottom: 0.7px solid #DCDCDC;
}

.upload-set{
	display: flex;
}

.image-width{
 	-webkit-appearance: none;
 	width: 70;
    padding: 7px 10px;
    border : 1px solid #CCC;
    text-align: center;
    margin-right: 10px;
 }

.btn-image-upload{
	padding: 5px 10px 5px 10px;
	background: #695353;
	text-align: center;
	cursor: pointer;
	color: #FFF;
}

.image-list{
	display: flex;
	flex-flow : row wrap;
	padding : 20px 30px;
	
}	

.wrap-image{
	width : 20vw;
	height : 20vw;
	min-width : 100px;
	min-height: 100px;
	margin : 5px;
	
	position: relative;
	border : 1px solid #DDD;
}

.wrap-image .btn-remove{
	cursor : pointer;
	padding : 5px 7px;
	position: absolute;
	right : 0;
	top : 0;
	background: #FF0000;
	opacity: 0.8;
	color: #FFF;
}


</style>
<body>
	<input type="hidden" id="editor" value='<c:out value="${editor}"/>'>
	
	<div class="upload-form">
		<input id="images" type="file" multiple="multiple" accept="image/gif, image/jpeg,image/png" onchange="onImageChange()">
		<div class="btn-image-upload" onclick="$(this).siblings('#images').click();">파일선택</div>
		<div class="upload-set">
			<input id="width" class="image-width" type="number" value="720">
			<div class="btn-image-upload" onclick="doUpload()">올리기</div>
		</div>
	</div>
	
	<div class="image-list">
		<div class="wrap-image">
			<input type="hidden" class="path">
			<input type="hidden" class="pathname">
			<input type="hidden" class="filename">
			<input type="hidden" class="status">
			<img class="image" width='100%' height='100%'/>
			<div class="btn-remove" onclick="removeImage(this)">삭제</div>
		</div>
	</div>
</body>
</html>