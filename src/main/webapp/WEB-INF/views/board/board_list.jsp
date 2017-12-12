<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>

<script>
	var allRowCnt = '${count}';
	var perPgLine = 1; 
	var page = 1;
	
	function boardView(seq){
		window.location.href = getContextPath() + "/board/board_view?seq=" + seq;		
	}
	
	function pageMove(pg){
		$.ajax({
			type	: "POST",
			url		: getContextPath() + "/board/board_paging.do",
			data	: {
				'page'		: pg,
				'perPgLine' : perPgLine
			},
			dataType: 'JSON',
			async	: false,
			success : function(data) {
				page = pg;
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
			item = $("<div>", {'class' : 'board-list-item', onclick : "boardView(" + board.seq + ")"});
			$("<div>",{ 'class' : 'list-item-index', text : (page - 1 ) * perPgLine + 1 + i}).appendTo(item);
			$("<div>",{ 'class' : 'list-item-sect', text : board.sect}).appendTo(item);
			$("<div>",{ 'class' : 'list-item-title', text : board.title}).appendTo(item);
			$("<div>",{ 'class' : 'list-item-date', text : board.date}).appendTo(item);
			item.appendTo(boardList);			
		}
	}
	
	function resizedw(){
		var parent = $(".board-list")
		var items = $(".board-list-item");
		var pHeight = parseInt(parent.height())
		var cHeight = parseInt(items.eq(1).outerHeight());
		perPgLine = parseInt(pHeight / cHeight) - 5;
		pageMove(page);
	}

	var doit;
	$(window).resize(function(){
	  clearTimeout(doit);
	  doit = setTimeout(resizedw, 100);
	});
	
	/* 페이지가 로드됨과 동시에 계정 리스트의 첫 번째 페이지를 출력 */
	$(document).ready(function(){
		pageMove(1);
		resizedw();
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
			margin-top: 1.5rem;
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
			font-size: 0.7rem;
		}
		
		.board-list .board-list-item:FIRST-CHILD{
			font-weight: bold;
			margin-bottom: 0.5rem;
			border-bottom: 2px solid #EEE;
		}
		
		.board-list .board-list-item:NOT(:FIRST-CHILD){
			opacity: 0.8;
			border-bottom: 1px soild #FAFAFA;
			cursor: pointer;
		}
			
		.board-list .board-list-item:NOT(:FIRST-CHILD):HOVER{
			opacity: 1;
			font-weight: bold;
			background: #FCFCFC;
		}
		
		.board-list .board-list-item div{
			padding : 0px 0.5rem;
		}
		
		.board-list-item .list-item-index{ width : 2rem; text-align: center;}
		.board-list-item .list-item-sect{ width : 4rem;  text-align: center;}
		.board-list-item .list-item-title{ flex : 1; text-align: left; overflow: hidden;}
		.board-list-item:NOT(:FIRST-CHILD) .list-item-title{ color: #00C;}
		.board-list-item .list-item-date{ width : 7rem; text-align: center;}
		
		.board-pager {
			text-align: center;
		}
		
		@media (max-width: 420px){
			.wrap-board-list{
				margin-top: 3rem;
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



