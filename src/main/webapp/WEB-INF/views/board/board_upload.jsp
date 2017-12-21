<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<style>
.board-upload-form{
	font-size: 0.7rem;
	margin-top : 50px;
	background: #FFF;
	border: 1px solid #DDD;
	padding : 1rem 5rem;
}

.board-upload-item{
	display: flex;
	flex-flow : row nowrap;
	algin-items : center;
	margin-bottom : 0.5rem;
}

.board-upload-item .item-name{
	width : 150px;
}

.board-upload-item .item-input{
	flex : 1;
}

.board-sect{
	padding-left : 0.5rem;
	width : 50%;
}

.board-title{
	padding-left : 0.5rem;
	width : 50%;
}

.board-contents{
	height: 450px;
}

.board-submit{
	text-align: right;
}


</style>

</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8"/>
		<div class="board-upload-form col-center">
			<form action="" method="post">
				<div class="board-upload-item">
					<div class="item-name">TITLE</div>
					<div class="item-input"><input class="board-title" type="text"></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">SECT</div>
					<div class="item-input"><input class="board-sect" type="text"></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">CONTENTS</div>
					<div class="item-input"><textarea id="board-contents" name="board-contents" class="board-contents"></textarea></div>
				</div>
				<script>
					var editor = CKEDITOR.replace("board-contents", {
						height: '450px',
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
					});
					
					CKEDITOR.on( 'instanceReady', function( ev ) {
						        // Ends self closing tags the HTML4 way, like <br>.
						        ev.editor.dataProcessor.writer.selfClosingEnd = '/>';
				    });
				</script>
				<div class="board-upload-item">
					<div class="item-name"></div>
					<div class="item-input board-submit"><a class="btn">저장</a></div>
				</div>	
				
			</form>
		</div>
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
		
	</div>
</body>
</html>