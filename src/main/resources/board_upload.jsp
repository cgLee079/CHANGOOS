<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board/board-upload.css" />

<c:if test="${!empty board}">
	<script>
		$(document).ready(function(){
			$("#type").val("${board.type}");
			$("#sort").val("${board.sort}");
			$("#sect").val("${board.sect}");
			$("#title").val("${board.title}");
			
			$("<input>", { type : "hidden", name : "seq", value: "${board.seq}"}).appendTo($("#uploadForm"));
			$("<input>", { type : "hidden", name : "date", value: "${board.date}"}).appendTo($("#uploadForm"));
			$("<input>", { type : "hidden", name : "hits", value: "${board.hits}"}).appendTo($("#uploadForm"));
		});
	</script>
</c:if>

</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8"/>
		<div class="board-upload-form col-center">
			<form id="uploadForm" action="${pageContext.request.contextPath}/admin/board/upload.do" method="post" enctype="multipart/form-data" class="upload-form">
				<div class="board-upload-item">
					<div class="item-name">TYPE</div>
					<div class="item-input">
					<select id="type" class="board-type" name="type">
						<option selected="selected">BASIC</option>
						<option>NOTICE</option>
					</select></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">SORT</div>
					<div class="item-input"><input type="text" id="sort" name="sort"  value="99999" class="board-sort"></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">SECT</div>
					<div class="item-input"><input type="text" id="sect" name="sect" class="board-sect" ></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">TITLE</div>
					<div class="item-input"><input type="text" id="title" name="title" class="board-title"> </div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">CONTENTS</div>
					<div class="item-input">
						<textarea id="board-contents" name="contents" class="board-contents">
							<c:if test="${!empty board.contents }">
								${board.contents}
							</c:if>
						</textarea>
					</div>
				</div>
				<script>
				var editor = CKEDITOR.replace("board-contents", {
					filebrowserUploadUrl : '<%=request.getContextPath()%>' + "/admin/board/imgUpload.do",
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
				
				<div class="board-upload-item">
					<div class="item-name">File</div>
					<div class="item-input">
						<c:import url="../included/included_fileupload.jsp" charEncoding="UTF-8">
							<c:param name="boardType" value="board"/>
						</c:import>
					</div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name"></div>
					<div class="item-input board-submit">
						<a class="btn" href="${pageContext.request.contextPath}/board">취소</a>
						<a class="btn" onclick="Progress.start(); $('#uploadForm').submit()">저장</a>
					</div>
				</div>	
				
			</form>
		</div>
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>