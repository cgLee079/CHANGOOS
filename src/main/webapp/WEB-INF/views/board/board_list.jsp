<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board/board-list.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script>
var allRowCnt = '${count}';
var perPgLine = 10;
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
		complete: function(){
			$("html, body").scrollTop(0);
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
	var length		= data.length;
	var noticeLen	= boardList.find(".board-list-item.board-notice").length;
	
	boardList.find(".board-list-item").remove();
	
	if(!data.length){
		board = data[i];
		item = $("<div>", {'class' : 'board-list-item board-basic', onclick : "$('.search-value').val(''); search();"});
		$("<div>",{ 'class' : 'list-item-none', text: "조회된 글이 없습니다.(목록으로)" }).appendTo(item);
		item.appendTo(boardList);
	}
	
	
	var board 		= undefined;
	var item 		= undefined;
	var itemInfo	= undefined;
	var itemInfoL 	= undefined;
	var itemInfoR 	= undefined;
	for (var i = 0; i < length; i++){
		board = data[i];
		item = $("<div>", {'class' : 'board-list-item', onclick : "boardView(" + board.seq + ")"});
		$("<span>", {"class" : "board-item-overlay", text : "Show"}).appendTo(item);
		//$("<div>", {"class" : "board-item-fg"}).appendTo(item);
		$("<div>",{ 'class' : 'board-item-title', text : board.title}).appendTo(item);
		$("<div>",{ 'class' : 'board-item-desc', text : board.contents}).appendTo(item);

		itemInfo = $("<div>",{ 'class' : 'board-item-info'}).appendTo(item);
		itemInfoL = $("<div>",{ 'class' : 'board-item-info-l row-center'}).appendTo(itemInfo);
		itemInfoR = $("<div>",{ 'class' : 'board-item-info-r'}).appendTo(itemInfo);
		
		$("<div>",{ 'class' : 'board-item-sect', text : board.sect}).appendTo(itemInfoL);
		$("<div>",{ 'class' : 'colum-border'}).appendTo(itemInfoL);
		$("<div>",{ 'class' : 'board-item-date', text : board.date}).appendTo(itemInfoL);
		$("<div>",{ 'class' : 'colum-border'}).appendTo(itemInfoL);
		$("<div>",{ 'class' : 'board-item-hits', text : "조회수 " + board.hits}).appendTo(itemInfoL);
		
		$("<div>",{ 'class' : 'board-item-comtcnt', text : "댓글 " + board.comtCnt}).appendTo(itemInfoR);
		
		if(isMobile){
			item.click(function(){
				$(this).find(".board-item-title").trigger("click");
			});
		} 		
		item.appendTo(boardList);			
	}
	
}

/* 페이지가 로드됨과 동시에 계정 리스트의 첫 번째 페이지를 출력 */
$(document).ready(function(){
	pageMove(page);
	
	$(".wrap-board").touchwipe({
	     wipeLeft: function() {
	    	 pageMove(page + 1);
	     },
	     
	     wipeRight: function() {
	    	 if(!(page <= 1)){
	    		 pageMove(page - 1);
	    	 }
	     },
	     
	     min_move_x: 60,
	     min_move_y: 20,
	     preventDefaultEvents: true
	});
});
</script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />

	<div class="wrap-board">
		<div class="board-menu">
			<div class="board-pager"></div>
			<div class="board-search row-center">
				<select class="search-type" style="font-size: 0.5rem">
					<option>TITLE</option>
					<option>SECT</option>
				</select>
				<input type="text"onkeydown="javascript:if(event.keyCode==13){search();}" style="font-size: 0.5rem" class="search-value" />
				<div class="search-submit col-center" onclick="search()">검색</div>
			</div>
		</div>
		<div class="board-list">
			<!-- 
			<c:forEach var="notice" items="${notices}">
				<div class="board-list-item board-notice">
					<div class="list-item-index">★</div>
					<div class="list-item-sect">NOTICE</div>
					<div class="list-item-desc">${notice.title}</div>
					<div class="list-item-date">${notice.date}</div>
				</div>
			</c:forEach>
			 -->
		</div>
		
	</div>
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>


</body>
</html>



