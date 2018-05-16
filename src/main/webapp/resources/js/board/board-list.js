const perPgLine = 10;
var searchType 	= "";
var searchValue = "";
var page 		= '';
var sect 		= 'ALL';
var allRowCnt	= undefined;

$(document).ready(function(){
	/* Get Hash */
	allRowCnt = $("#allRowCnt").val();
	sect = window.location.hash.substring(1).split("&")[0];
	page = parseInt(window.location.hash.substring(1).split("&")[1]);

	/* Add Class 'on' to Sect*/
	if(!sect){ sect = 'ALL'; }
	var items = $(".board-sects .board-sects-item");
	for(var i = 0; i < items.length; i++){
		if($(items[i]).text() == sect){
			$(items[i]).addClass("on");
			break;
		}
	}

	/* Page to 1 */
	if(!page){
		page = 1;
	}
	pageMove(page);
	
	/* touch wipe event - Paging */
	$(".wrap-board").touchwipe({
	     wipeLeft: function() {
	    	 pageMove(page + 1);
	     },
	     wipeRight: function() {
	    	 if(!(page <= 1)){
	    		 pageMove(page - 1);
	    	 }
	     },
	     min_move_x: 30,
	     min_move_y: 20,
	     preventDefaultEvents: true
	});
});

/* when board click */
function boardView(seq){
	Progress.start();
	window.location.href = getContextPath() + "/board/view?seq=" + seq + "&sect=" + sect + "&page=" + page;		
}

/* when Search button click */
function search(){
	searchType	= $(".search-type").val();
	searchValue = $(".search-value").val();
	
	pageMove(1);
}

/* when Sect click */
function selectSect(tg){
	var tg = $(tg);
	
	var items = $(".board-sects .board-sects-item");
	items.removeClass("on");
	tg.addClass("on");
	sect = tg.text();
	
	pageMove(1);
}

/* Paging */
function pageMove(pg){
	if(sect == "ALL"){
		sect = '';
	}
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/board/board_paging.do",
		data	: {
			'page'			: pg,
			'perPgLine' 	: perPgLine,
			'sect' 			: sect,
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
				window.location.hash = sect + "&" + page;
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

/* draw Page number */
function updatePaging(callFunc, page, allRowCnt, perPgLine, pgGrpCnt){
	var boardPager	= $('.board-pager');
	var	pager		= drawPager(callFunc, page, allRowCnt, perPgLine, pgGrpCnt);
		
	boardPager.empty();
	boardPager.append(pager);
}

/* draw Board list */
function updateBoard(data){
	var boardList 	= $(".board-list");
	var length		= data.length;
	var noticeLen	= boardList.find(".board-list-item.board-notice").length;
	
	boardList.find(".board-list-item").remove();
	
	if(!data.length){
		board = data[i];
		item = $("<div>", {'class' : 'board-list-item', onclick : "$('.search-value').val(''); search();"});
		$("<div>",{ 'class' : 'board-item-nosearch', text: "조회된 글이 없습니다.(목록으로)" }).appendTo(item);
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
		$("<span>", {"class" : "board-item-overlay", text : "SHOW"}).appendTo(item);
		$("<div>", {"class" : "board-item-fg"}).appendTo(item);
		$("<div>",{"class" : 'board-item-title', text : board.title}).appendTo(item);

		itemInfo = $("<div>",{ 'class' : 'board-item-info'}).appendTo(item);
		itemInfoL = $("<div>",{ 'class' : 'board-item-info-l row-center'}).appendTo(itemInfo);
		itemInfoR = $("<div>",{ 'class' : 'board-item-info-r'}).appendTo(itemInfo);
		
		$("<div>",{ 'class' : 'board-item-sect', text : board.sect}).appendTo(itemInfoL);
		$("<div>",{ 'class' : 'colum-border'}).appendTo(itemInfoL);
		
		if(board.codeLang){
			$("<div>",{ 'class' : 'board-item-codeLang', text : board.codeLang}).appendTo(itemInfoL);
			$("<div>",{ 'class' : 'colum-border'}).appendTo(itemInfoL);
		}
		
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
	
	boardList.removeAttr("style");
	if(boardList.height() < deviceHeight){
		boardList.css("height", deviceHeight);
	} 
}