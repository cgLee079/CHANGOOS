<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<style>
.board-upload-form{
	margin-top : 30px;
	background: #FFF;
	border: 1px solid #DDD;
	padding : 1rem 5rem;
	font-size: 0.7rem;
}

.board-upload-item{
	display: flex;
	flex-flow : row nowrap;
	margin-bottom : 0.5rem;
}

.board-upload-item .item-name{
	width : 5rem;
}

.board-upload-item .item-input{
	flex : 1;
}

.board-upload-item .item-input input[type='text']{
	padding : 0.1rem;
	width : 50%;
}

.board-type {
	padding: 0.1rem;
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
			<form id="upload-form" action="${pageContext.request.contextPath}/board/board_upload.do" method="post">
				<div class="board-upload-item">
					<div class="item-name">TYPE</div>
					<div class="item-input">
					<select class="board-type" name="type">
						<option selected="selected">기본</option>
						<option>공지사항</option>
					</select></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">SORT</div>
					<div class="item-input"><input type="text" name="sort"  value="99999" class="board-sort"></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">SECT</div>
					<div class="item-input"><input type="text" name="sect" class="board-sect" ></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">TITLE</div>
					<div class="item-input"><input type="text" name="title" class="board-title"> </div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">CONTENTS</div>
					<div class="item-input"><textarea id="board-contents" name="contents" class="board-contents"></textarea></div>
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
					<div class="item-input board-submit"><a class="btn" onclick="$('#upload-form').submit()">저장</a></div>
				</div>	
				
			</form>
		</div>
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
		
	</div>
</body>
</html>