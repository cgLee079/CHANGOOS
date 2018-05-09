<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-upload.css" />

<c:if test="${!empty project}">
	<script>
		$(document).ready(function(){
			$("#sort").val("${project.sort}");
			$("#sect").val("${project.sect}");
			$("#title").val("${project.title}");
			$("#sourcecode").val("${project.sourcecode}");
			$("#date").val("${project.date}");
			$("#developer").val("${project.developer}");
			$("#video").val("${project.video}");
			
			$("<input>", { type : "hidden", name : "seq", value: "${project.seq}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "snapsht", value: "${project.snapsht}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "hits", value: "${project.hits}"}).appendTo($("#upload-form"));
		});
	</script>
</c:if>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-upload-form">
			<form id="upload-form" action="${pageContext.request.contextPath}/admin/project/upload.do" 
				method="post" enctype="multipart/form-data">
				
				<div class="upload-project">
					<div class="upload-project-name">SORT</div>
					<div class="upload-project-input">
						<input type="text" id="sort" name="sort" class="project-sort" value="99999"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">TITLE</div>
					<div class="upload-project-input">
						<input type="text" id="title" name='title' class="project-name"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">SECT</div>
					<div class="upload-project-input">
						<input type="text" id="sect" name="sect" class="project-sect"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">SOURCECODE</div>
					<div class="upload-project-input">
						<input type="text" id="sourcecode" name="sourcecode" class="project-sourcecode"/>
					</div>
				</div>
				
					<div class="upload-project">
					<div class="upload-project-name">DATE</div>
					<div class="upload-project-input">
						<input type="text" id="date" name="date" class="project-date"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">DEVELOPER</div>
					<div class="upload-project-input">
						<input type="text" id="developer" name="developer" class="project-developer"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">SNAPSHT</div>
					<div class="upload-project-input">
							<input type="file" id="snapshtFile" name="snapshtFile" class="project-snapshot"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">VIDEO</div>
					<div class="upload-project-input">
						<select id="video" name="video" class="project-video">
							<option>Y</option>
							<option selected="selected">N</option>
						</select>
					</div>
				</div>
				
				
				<div class="upload-project">
					<div class="upload-project-name">DESC</div>
					<div class="upload-project-input">
						<textarea id="desc" name="desc" class="project-desc">
							<c:if test="${!empty project.desc }">
								<c:out value="${project.desc}" escapeXml="false"/>
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
				
				<div class="upload-project">
					<div class="upload-project-name">CONTENTS</div>
					<div class="upload-project-input">
						<textarea name="contents" id="contents">
							<c:if test="${!empty project.contents }">
								<c:out value="${project.contents}" escapeXml="false"/>
							</c:if>
						</textarea>
						<script>
							var editor = CKEDITOR.replace("contents", {
								filebrowserUploadUrl : '<%=request.getContextPath()%>' + "/admin/project/imgUpload.do",
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
				
				<div class="upload-project">
					<div class="upload-project-name">FILE</div>
					<div class="upload-project-input">
						<c:import url="../included/included_fileupload.jsp" charEncoding="UTF-8">
							<c:param name="boardType" value="project"/>
						</c:import>
					</div>
				</div>
				
				
				<div class="upload-project">
					<div class="upload-project-name"></div>
					<div class="upload-project-input">
						<input type="submit" class="project-submit" onclick="Progress.start()">
					</div>
				</div>
			</form>
			
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>