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
	 	  				drawImage(result.path, result.filename);
	 	      		}
	 	      	})
    	  }
		}(file);
		
		fileReader.readAsDataURL(files[i]);
    }
}

function drawImage(path, filename){
	var wrapImage = wrapImageTemp.clone();
	wrapImage.appendTo($(".image-list"));
	wrapImage.find(".thumbnail").attr("src", path + filename);
	wrapImage.find(".thumbnail").attr("filename", filename);
}
	

function doUpload(){
	var images = $("img");
	for(var i = 0; i <= images.length; i++){
		$(".image-list", opener.document).append(images[i]);	
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
}

.btn-image-upload{
	padding: 5px 10px 5px 10px;
	background: #FCC;
	text-align: center;
	cursor: pointer;
}

.image-list{
	display: flex;
	flex-flow : row wrap;
	justify-content: center;
	
}	

.wrap-image{
	width : 90px;
	height : 90px;
	margin : 5px;
	
	position: relative;
	border : 1px solid #DDD;
}

.wrap-image .thumbnail{
	position: absolute;
	left : 0;
	right : 0;
	top : 0;
	bottom : 0;
}

.wrap-image .btn-remove{
	cursor : pointer;
	padding : 7px 10px;
	position: absolute;
	right : 0;
	top : 0;
	background: #FF0000;
	opacity: 0.8;
	color: #FFF;
}
</style>
<body>
	<input type="hidden" id="textArea">
	
	<div class="upload-form">
		<input id="images" type="file" multiple="multiple" accept="image/gif, image/jpeg,image/png" onchange="onImageChange()">
		<div class="btn-image-upload" onclick="$(this).siblings('#images').click();">파일선택</div>
		<div class="btn-image-upload" onclick="doUpload()">올리기</div>
	</div>
	
	<div class="image-list">
		<div class="wrap-image">
			<img class="thumbnail" width='100%' height='100%'/>
			<div class="btn-remove">삭제</div>
		</div>
	
	</div>
</body>
</html>