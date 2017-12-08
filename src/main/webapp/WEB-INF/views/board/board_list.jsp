<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script>
	var allRowCnt = '${count}';
	var perPgLine = 10; 
	
	function boardView(seq){
		window.location.href = getContextPath() + "/item/view?seq=" + seq;		
	}
	
	function pageMove(page){
		$.ajax({
			type	: "POST",
			url		: getContextPath() + "/board/board_paging.do",
			data	: {
				'page'		: page,
				'perPgLine' : perPgLine
			},
			dataType: 'JSON',
			success : function(data) {
				updateBoard(data);
				updatePaging("pageMove", page, allRowCnt, perPgLine, 3);
			},
			error : function(e) {
				console.log(e);
			}
		});
	}
	
	function updatePaging(callFunc, page, allRowCnt, perPgLine, pgGrpCnt){
		var boardPager	= $('.board-pager');
		var	pager		= drawPager(callFunc, page, allRowCnt, perPgLine, pgGrpCnt);
			
		boardPager.empty();
		boardPager.append(pager);
	}
	
	function updateBoard(data){
		var boardList 	= $(".board-list");
		var board 		= undefined;
		var item 		= undefined;
		var length		= data.length;
		
		boardList.find('.board-list-item:gt(0)').remove();
		
		for (var i = 0; i < length; i++){
			board = data[i];
			item = $("<div>", {'class' : 'board-list-item'});
			$("<div>",{ 'class' : 'list-item-index', text : board.seq}).appendTo(item);
			$("<div>",{ 'class' : 'list-item-sect', text : board.sect}).appendTo(item);
			$("<div>",{ 'class' : 'list-item-title', text : board.title}).appendTo(item);
			$("<div>",{ 'class' : 'list-item-date', text : board.date}).appendTo(item);
			item.appendTo(boardList);			
		}
	}
	
	/* 페이지가 로드됨과 동시에 계정 리스트의 첫 번째 페이지를 출력 */
	$(document).ready(function(){
		pageMove(1);
	});
</script> 
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	<style>
		.wrap-board-list{
			height : 500px;
			background: #FFF;
			border: 1px solid #DDD;
			margin-top: 4rem;
			padding: 2rem;
		}
		
		.board-list {
			height: 95%;
		}
		
		.board-list .board-list-item{
			display: flex;
			flex-flow: row nowrap;
			justify-content: space-between;
			color : #666;
			padding : 0.3rem 0rem;
			font-size: 0.6rem;
		}
		
		.board-list .board-list-item:FIRST-CHILD{
			font-weight: bold;
			background: #777;
			color: #FFF;
			border-top : 1px solid #333;
			border-bottom : 1px solid #333;
			margin-bottom: 0.5rem;
		}
		
		.board-list .board-list-item:NOT(:FIRST-CHILD):NTH-CHILD(odd){
			background: #F7F7F7;
			border-top: #F0F0F0;
			border-bottom: #F0F0F0;
		}
		
		
		.board-list .board-list-item div{
			padding : 0px 0.5rem;
		}
		
		.board-list-item .list-item-index{ width : 2rem; text-align: center;}
		.board-list-item .list-item-sect{ width : 4rem;  text-align: center;}
		.board-list-item .list-item-title{ flex : 1; text-align: left; overflow: hidden;}
		.board-list-item:NOT(:FIRST-CHILD) .list-item-title{ color: #00F;}
		.board-list-item .list-item-date{ width : 7rem; text-align: center;}
		
		.board-pager {
			text-align: center;
		}
		
		@media (max-width: 420px){
			.wrap-board-list{
				margin-top: 6rem;
			}
		}
		
	</style>
	<div class="wrap-board-list">
		<div class="board-list">
			<div class="board-list-item">
				<div class="list-item-index">INDEX</div>
				<div class="list-item-sect">SECT</div>
				<div class="list-item-title">TITLE</div>
				<div class="list-item-date">DATE</div>
			</div>
			
		</div>
		
		<div class="board-pager"></div>
	</div>
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>


</body>
</html>



