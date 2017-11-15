<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
</head>
<body>
	<section class='section'>
		<article class='article'>
			<div class='uploadForm'>
				<form action="${pageContext.request.contextPath}/board/upload" method="post" id='uploadForm'>
					<input type='text' name='board_title' class='full margin_top margin_bottom'>
					<textarea name='board_cont' class='full margin_top margin_bottom' id='ckeditor'>
					</textarea>
					<script>
						var editor = CKEDITOR.replace('ckeditor', {
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

					<div class='right margin_top margin_bottom'>
						<a href="javascript:void(0)" onclick="$('#uploadForm').submit()">저장</a>
					</div>
				</form>
			</div>
		</article>
	</section>
</body>
</html>