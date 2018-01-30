<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board/board-list.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
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
	var boardDesc 	= undefined;
	
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
		
		boardDesc = $("<div>",{ 'class' : 'list-item-desc'}).appendTo(item);
		$("<span>",{ 'class' : 'list-item-title', text : board.title}).appendTo(boardDesc);
		if(board.comtCnt > 0){
		$("<span>",{ 'class' : 'list-item-comt', text : "(" + board.comtCnt + ")" }).appendTo(boardDesc);
		}
		
		$("<div>",{ 'class' : 'list-item-date', text : board.date}).appendTo(item);
		item.appendTo(boardList);			
	}
}

function resizedw(){
	var wrapParent = $(".wrap-board-list");
	var parent = $(".board-list")
	var items = $(".board-list-item");
	var pHeight = undefined;
	var cHeight = undefined;
	var noticeLen = undefined;
	
	wrapParent.css("height", deviceHeight - 200)
	
	pHeight = parseInt(parent.height());
	cHeight = parseInt(items.eq(1).outerHeight());
	noticeLen = parent.find(".board-list-item.board-notice").length;
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
	
	$(".wrap-board-list").touchwipe({
	     wipeLeft: function() {
	    	 pageMove(page + 1);
	     },
	     
	     wipeRight: function() {
	    	 if(!(page <= 1)){
	    		 pageMove(page - 1);
	    	 }
	     },
	     
	     min_move_x: 20,
	     min_move_y: 20,
	     preventDefaultEvents: true
	});
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
				<div class="list-item-desc">TITLE</div>
				<div class="list-item-date">DATE</div>
			</div>
			<c:forEach var="notice" items="${notices}">
				<div class="board-list-item board-notice">
					<div class="list-item-index">★★★</div>
					<div class="list-item-sect">NOTICE</div>
					<div class="list-item-desc">${notice.title}</div>
					<div class="list-item-date">${notice.date}</div>
				</div>
			</c:forEach>
		</div>
		
		<div class="board-search row-center">
			<select class="search-type" style="font-size: 0.5rem">
				<option>SECT</option>
				<option>TITLE</option>
			</select>
			<input type="text"onkeydown="javascript:if(event.keyCode==13){search();}" style="font-size: 0.5rem" class="search-value" />
			<div class="search-submit col-center" onclick="search()">검색</div>
		</div>
		
		<div class="board-pager"></div>
	</div>
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>


</body>
</html>



