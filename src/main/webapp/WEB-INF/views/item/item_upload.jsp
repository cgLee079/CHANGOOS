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

<c:if test="${!empty item}">
	<script>
		$(document).ready(function(){
			$("#sort").val("${item.sort}");
			$("#sect").val("${item.sect}");
			$("#name").val("${item.name}");
			$("#sourcecode").val("${item.sourcecode}");
			$("#date").val("${item.date}");
			$("#developer").val("${item.developer}");
			$("#video").val("${item.video}");
			
			$("<input>", { type : "hidden", name : "seq", value: "${item.seq}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "snapsht", value: "${item.snapsht}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "hits", value: "${item.hits}"}).appendTo($("#upload-form"));
		});
	</script>
</c:if>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-upload-form">
			<form id="upload-form" action="${pageContext.request.contextPath}/admin/item/upload.do" 
				method="post" enctype="multipart/form-data">
				
				<div class="upload-item">
					<div class="upload-item-name">sort</div>
					<div class="upload-item-input">
						<input type="text" id="sort" name="sort" class="item-sort" value="99999"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">name</div>
					<div class="upload-item-input">
						<input type="text" id="name" name='name' class="item-name"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">sect</div>
					<div class="upload-item-input">
						<input type="text" id="sect" name="sect" class="item-sect"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">sourcecode</div>
					<div class="upload-item-input">
						<input type="text" id="sourcecode" name="sourcecode" class="item-sourcecode"/>
					</div>
				</div>
				
					<div class="upload-item">
					<div class="upload-item-name">date</div>
					<div class="upload-item-input">
						<input type="text" id="date" name="date" class="item-date"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">developer</div>
					<div class="upload-item-input">
						<input type="text" id="developer" name="developer" class="item-developer"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">snapshot</div>
					<div class="upload-item-input">
							<input type="file" id="snapshtFile" name="snapshtFile" class="item-snapshot"/>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">video</div>
					<div class="upload-item-input">
						<select id="video" name="video" class="item-video">
							<option>Y</option>
							<option selected="selected">N</option>
						</select>
					</div>
				</div>
				
				
				<div class="upload-item">
					<div class="upload-item-name">desc</div>
					<div class="upload-item-input">
						<textarea id="desc" name="desc" class="item-desc">
							<c:if test="${!empty item.desc }">
								${item.desc }
							</c:if>
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
								toolbar : 'Basic'
							});
							
		
							CKEDITOR.on( 'instanceReady', function( ev ) {
								        // Ends self closing tags the HTML4 way, like <br>.
								        ev.editor.dataProcessor.writer.selfClosingEnd = '/>';
						    });
							
						</script>
				
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">contents</div>
					<div class="upload-item-input">
						<textarea name="content" id="content">
							<c:if test="${!empty item.content }">
								${item.content}
							</c:if>
						</textarea>
						<script>
							var editor = CKEDITOR.replace("content", {
								filebrowserUploadUrl : '<%=request.getContextPath()%>' + "/admin/item/imgUpload.do",
								codeSnippet_theme : 'github',
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
								}
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
							
							CKEDITOR.on( 'instanceReady', function( ev ) {
								        // Ends self closing tags the HTML4 way, like <br>.
								        ev.editor.dataProcessor.writer.selfClosingEnd = '/>';
						    });
							
						</script>
					</div>
				</div>
				
				<div class="upload-item">
					<div class="upload-item-name">file</div>
					<div class="upload-item-input">
						<c:import url="../included/included_fileupload.jsp" charEncoding="UTF-8">
							<c:param name="boardType" value="item"/>
						</c:import>
					</div>
				</div>
				
				
				<div class="upload-item">
					<div class="upload-item-name"></div>
					<div class="upload-item-input">
						<input type="submit" class="item-submit">
					</div>
				</div>
			</form>
			
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>