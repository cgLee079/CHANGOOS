<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<style>

.wrapper{
	width: 80%;
	margin: 0px auto;
}

.header{
	text-align: center;
}

.wrap-upload-form h5 {margin-top: 20px;}
.item-name{ width : 100%;}
.item-sect{ width : 100%;}
.item-sourcecode{ width : 100%;}
.item-date{ width : 100%;}
.item-developer{ width : 100%;}
.item-submit {width : 100%;}

.item-desc{
	width : 100%;
	height : 100px;
}

.item-tools{
	width : 100%;
	height : 100px;
}

</style>

<c:if test="${!empty item}">
	<script>
		$(document).ready(function(){
			$("#name").val("${item.name}");
			$("#desc").val("${item.desc}");
			$("#sect").val("${item.sect}");
			$("#content").val("${item.content}");
			$("#sourcecode").val("${item.sourcecode}");
			$("#date").val("${item.date}");
			$("#developer").val("${item.developer}");
			$("#tools").val("${item.tools}");
			
			$("<input>", { type : "hidden", name : "seq", value: "${item.seq}"}).appendTo($("#upload-form"));
			$("<input>", { type : "hidden", name : "snapsht", value: "${item.snapsht}"}).appendTo($("#upload-form"));
		});
	</script>
</c:if>
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<h1>Item-upload</h1>
		</div>
		<div class="wrap-upload-form">
			<form id="upload-form" action="${pageContext.request.contextPath}/admin/item/upload.do" 
				method="post"  enctype="multipart/form-data">
				<h5>name</h5>
				<input type='text' id="name" name='name' class="item-name"/>
				
				<h5>sect</h5>
				<input id="sect" name="sect" class="item-sect"/>
				
				<h5>desc</h5>
				<textarea id="desc" name="desc" class="item-desc"></textarea>
				
				<h5>sourcecode</h5>
				<input type="text" id="sourcecode" name="sourcecode" class="item-sourcecode"/>
				
				<h5>contents</h5>
				<textarea name="content" id="content"></textarea>
				<script>
					var editor = CKEDITOR.replace("content", {
						filebrowserUploadUrl : '<%=request.getContextPath()%>' + "/item/imgUpload.do",
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
				
				<h5>date</h5>
				<input type="text" id="date" name="date" class="item-date"/>
				
				<h5>developer</h5>
				<input type="text" id="developer" name="developer" class="item-developer"/>
				
				<h5>tools</h5>
				<textarea id="tools" name="tools" class="item-tools"></textarea>
				
				<h5>snapshot</h5>
				<input type="file" id="snapshtFile" name="snapshtFile" class="item-snapshot"/>

				<hr>
				<input type="submit" class="item-submit">
				
			</form>
		</div>
	</div>
</body>
</html>