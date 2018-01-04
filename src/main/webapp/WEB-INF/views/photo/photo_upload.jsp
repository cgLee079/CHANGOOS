<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<style>
.wrapper{
	width: 100%;
	margin: 0px auto;
}

.wrap-upload-form{
	width : 80%;
	margin: 0px auto;
	margin-top : 20px;
}

.upload-item{
	display : flex;
	flex-flow : row nowrap;
	margin: 1rem 0;
}

.upload-item-name{
	width : 7rem;
}

.upload-item-input{
	flex  : 1;
}

.upload-item-input input{
	width : 100%;
}

</style>

<c:if test="${!empty photo}">
	<script>
		$(document).ready(function(){
			$("#sort").val("${photo.sort}");
			$("#name").val("${photo.name}");
			$("#location").val("${photo.location}");
			$("#date").val("${photo.date}");
			$("#time").val("${photo.time}");
			$("#tag").val("${photo.tag}");
			
			$("<input>", { type : "hidden", name : "seq", value: "${photo.seq}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "image", value: "${photo.image}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "snapsht", value: "${photo.snapsht}"}).appendTo($("#upload-form"));
		});
	</script>
</c:if>

</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-upload-form">
			<form id="upload-form" action="${pageContext.request.contextPath}/admin/photo/upload.do" 
				method="post"  enctype="multipart/form-data">
				
				<div class="upload-item">
					<div class="upload-item-name">sort</div>
					<div class="upload-item-input">
						<input type="text" id="sort" name="sort" class="photo-sort" value="99999"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">image</div>
					<div class="upload-item-input">
						<input type="file" id="imageFile" name="imageFile" class="photo-image" accept="image/*"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">location</div>
					<div class="upload-item-input">
						<input type="text" id="location" name="location" class="photo-location"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">date</div>
					<div class="upload-item-input">
						<input type="text" id="date" name="date" class="photo-date"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">time</div>
					<div class="upload-item-input">
						<input type="text" id="time" name="time" class="photo-date"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">tag</div>
					<div class="upload-item-input">
						<input type="text" id="tag" name="tag" class="photo-tag"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">name</div>
					<div class="upload-item-input">
						<input type="text" id="name" name="name" class="photo-name"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">desc</div>
					<div class="upload-item-input">
						<textarea id="desc" name="desc" class="photo-desc">
							<c:if test="${!empty photo.desc}">${photo.desc}</c:if>
						</textarea>
						<script>
							var editor = CKEDITOR.replace("desc", {
								on : {
									instanceReady : function( ev ){
									    // Output paragraphs as <p>Text</p>.
									    this.dataProcessor.writer.setRules( 'p',
									        {
									            indent : false,
									            breakBeforeOpen : true,
									            breakAfterOpen : false,
									            breakBeforeClose : false,
									            breakAfterClose : true
									        });
									}
								},
								toolbar : "Basic"
							});
		
							CKEDITOR.on( 'instanceReady', function( ev ) {
								  // Ends self closing tags the HTML4 way, like <br>.
								  ev.editor.dataProcessor.writer.selfClosingEnd = '/>';
						    });
							
						</script>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name"></div>
					<div class="upload-item-input">
						<input type="submit" class="photo-submit">
					</div>
				</div>
			</form>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>