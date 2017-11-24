<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
.wrapper{
	width: 80%;
	margin: 0px auto;
}

.header{
	text-align: center;
}

input[class*="photo"] {margin-bottom: 20px;}
.photo-image{ width : 100%; }
.photo-name{ width : 100%; }
.photo-location{ width: 100%; }
.photo-date{ width: 100%; }
.photo-people{ 	width: 100%; }
.photo-tag{ width: 100%; }
.photo-submit{ width: 100%; }
.photo-desc{
	width: 100%;
	height: 100px;
}

</style>

<c:if test="${!empty photo}">
	<script>
		$(document).ready(function(){
			$("#name").val("${photo.name}");
			$("#desc").val("${photo.desc}");
			$("#location").val("${photo.location}");
			$("#date").val("${photo.date}");
			$("#tag").val("${photo.tag}");
			
			$("<input>", { type : "hidden", name : "seq", value: "${photo.seq}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "image", value: "${photo.image}"}).appendTo($("#upload-form"));
		});
	</script>
</c:if>

</head>
<body>
	<div class="wrapper">
		<div class="header">
			<h1>Photo-upload</h1>
		</div>
		
		<div class="wrap-upload-form">
			<form id="upload-form" action="${pageContext.request.contextPath}/admin/photo/upload.do" 
				method="post"  enctype="multipart/form-data">
				
				<h5>image</h5>
				<input type="file" id="imageFile" name="imageFile" class="photo-image"/>
				
				<h5>name</h5>
				<input type="text" id="name" name="name" class="photo-name"/>
				
				<h5>desc</h5>
				<textarea id="desc" name="desc" class="photo-desc"></textarea>
				
				<h5>location</h5>
				<input type="text" id="location" name="location" class="photo-location"/>
				
				<h5>date</h5>
				<input type="text" id="date" name="date" class="photo-date"/>
				
				<h5>tag</h5>
				<input type="text" id="tag" name="tag" class="photo-tag"/>
				
				<hr>
				<input type="submit" class="photo-submit">
				
			</form>
		</div>
	</div>
</body>
</html>