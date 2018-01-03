<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>

<style>
	.wrap-board-list{
		height : 500px;
		background: #FFF;
		border: 1px solid #DDD;
		margin-top: 1.5rem;
		padding: 2rem 3rem;
	}
	
	.board-list {
		height: 90%;
	}
	
	.board-list .board-list-item{
		display: flex;
		flex-flow: row nowrap;
		justify-content: space-between;
		color : #666;
		padding : 0.3rem 0rem;
		font-size: 0.7rem;
	}
	
	.board-list .board-list-item.board-header{
		font-weight: bold;
		border-bottom: 1px solid #EEE;
		margin-bottom: 0.5rem;
		color : #333;
	}
	
	.board-list .board-list-item.board-notice{
		color : #444;
	}
	
	.board-list .board-list-item.board-basic{
		opacity: 1;
		border-bottom: 1px soild #FAFAFA;
		margin-top : 0.1rem;
		cursor: pointer;
	}
	
	.board-list .board-list-item.board-basic:HOVER{
		background: #FAFAFA;
	}
	
	.board-list-item div{ padding : 0px 0.5rem; }
	.board-list-item .list-item-index{ width : 2rem; text-align: center;}
	.board-list-item .list-item-sect{ width : 5rem;  text-align: center;}
	.board-list-item .list-item-title{ flex : 1; text-align: left; overflow: hidden; white-space: nowrap;}
	.board-list-item.board-basic .list-item-title{ color: #00C;}
	.board-list-item .list-item-date{ width : 7rem; text-align: center;}
	.board-list-item .list-item-none{ width : 100%; color : #F00; text-align: center;}
	
	.board-menu{
		text-align: right;
		font-size: 0.7rem;
	}

	.board-search{
		height : 1rem;
		font-size: 0.5rem;
	}
	
	.search-type{
		height: 100%;
	}
	
	.search-value{
		height: 100%;
		padding : .1rem;
		width : 7rem;
	}
	
	.search-submit{
		color: #FFF;
		width: 2rem;
		background: #666;
		cursor: pointer;
		height: 100%;
	}
		
	.board-pager {
		margin-top : 10px;
		text-align: center;
	}
	
	@media (max-width: 420px){
		.wrap-board-list{
			margin-top: 3rem;
		}
	}
	
</style>
	
<script>
	var allRowCnt = '${count}';
	var perPgLine = 1;
	var searchType = "";
	var searchValue = "";
	
	var page = window.location.hash.substring(1);
	if(!page){
		page = 1;
	}
	
	function boardView(seq){
		Progress.start();
		window.location.href = getContextPath() + "/board/view?seq=" + seq + "&page=" + page;		
	}
	
	function search(){
		searchType	= $(".search-type").val();
		searchValue = $(".search-value").val();
		
		pageMove(1);
	}
	
	function pageMove(pg){
		$.ajax({
			type	: "POST",
			url		: getContextPath() + "/board/board_paging.do",
			data	: {
				'page'			: pg,
				'perPgLine' 	: perPgLine,
				'searchType'	: searchType,
				'searchValue'	: searchValue
			},
			dataType: 'JSON',
			async	: false,
			success : function(result) {
				var count = result['count'];
				var data = result['data'];
				allRowCnt = count;
				
				if(!data.length && pg != 1){
					pageMove(pg - 1);
				} else{
					page = pg;
					window.location.hash = page;
					updateBoard(data);
					updatePaging("pageMove", page, allRowCnt, perPgLine, 3);
				}
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
		var noticeLen	= boardList.find(".board-list-item.board-notice").length;
		
		boardList.find(".board-list-item:gt(" + noticeLen + ")").remove();
		
		if(!data.length){
			board = data[i];
			item = $("<div>", {'class' : 'board-list-item board-basic', onclick : "$('.search-value').val(''); search();"});
			$("<div>",{ 'class' : 'list-item-none', text: "조회된 글이 없습니다.(목록으로)" }).appendTo(item);
			item.appendTo(boardList);
		}
		
		for (var i = 0; i < length; i++){
			board = data[i];
			item = $("<div>", {'class' : 'board-list-item board-basic', onclick : "boardView(" + board.seq + ")"});
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
		var noticeLen = parent.find(".board-list-item.board-notice").length;
		perPgLine = parseInt(pHeight / cHeight) - noticeLen - 4;
		pageMove(page);
	}

	var doit;
	$(window).resize(function(){
	  clearTimeout(doit);
	  doit = setTimeout(resizedw, 100);
	});
	
	/* 페이지가 로드됨과 동시에 계정 리스트의 첫 번째 페이지를 출력 */
	$(document).ready(function(){
		pageMove(page);
		resizedw();
	});
	
</script> 
</head>

<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />

	<div class="wrap-board-list">
		<div class="board-list">
			<div class="board-list-item board-header">
				<div class="list-item-index">INDEX</div>
				<div class="list-item-sect">SECT</div>
				<div class="list-item-title">TITLE</div>
				<div class="list-item-date">DATE</div>
			</div>
			<c:forEach var="notice" items="${notices}">
				<div class="board-list-item board-notice">
					<div class="list-item-index">★★★</div>
					<div class="list-item-sect">NOTICE</div>
					<div class="list-item-title">${notice.title}</div>
					<div class="list-item-date">${notice.date}</div>
				</div>
			</c:forEach>
		</div>
		
		<div class="board-search row-center">
			<select class="search-type">
				<option>SECT</option>
				<option>TITLE</option>
			</select>
			<input type="text" class="search-value" onkeydown="javascript:if(event.keyCode==13){search();}"/>
			<div class="search-submit col-center" onclick="search()">검색</div>
		</div>
		
		<div class="board-menu">
		 	<a href="${pageContext.request.contextPath}/admin/board/upload" class="btn">글쓰기</a>
		</div>
		
		<div class="board-pager"></div>
	</div>
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>


</body>
</html>



