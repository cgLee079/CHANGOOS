var perPgLine 	= 100000;
var searchType 	= "";
var searchValue = "";
var page 		= '';
var sect 		= 'ALL';
var allRowCnt	= undefined;

$(document).ready(function(){
	doMenuOn(".menu-study");
	
	if(isMobile){
		perPgLine = 100000;
	}
	
	/* Get Hash */
	allRowCnt = $("#allRowCnt").val();
	sect = window.location.hash.substring(1).split("&")[0];
	page = parseInt(window.location.hash.substring(1).split("&")[1]);

	/* Add Class 'on' to Sect*/
	if(!sect){ sect = 'ALL'; }
	var items = $(".study-sects .study-sects-item");
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
	/*
	$(".wrap-study").touchwipe({
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
	*/
});

/* when study click */
function studyView(seq){
	Progress.start();
	window.location.href = getContextPath() + "/study/view?seq=" + seq + "&section=" + sect + "&page=" + page;		
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
	
	var items = $(".study-sects .study-sects-item");
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
		url		: getContextPath() + "/study/study_paging.do",
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
				updateStudy(data);
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
	var studyPager	= $('.study-pager');
	var	pager		= drawPager(callFunc, page, allRowCnt, perPgLine, pgGrpCnt);
		
	studyPager.empty();
	studyPager.append(pager);
}

/* draw Study list */
function updateStudy(data){
	var studyList 	= $(".study-list");
	var length		= data.length;
	var noticeLen	= studyList.find(".study-list-item.study-notice").length;
	
	studyList.find(".study-list-item").remove();
	
	if(!data.length){
		study = data[i];
		item = $("<div>", {'class' : 'study-list-item', onclick : "$('.search-value').val(''); search();"});
		$("<div>",{ 'class' : 'study-item-nosearch', text: "조회된 글이 없습니다.(목록으로)" }).appendTo(item);
		item.appendTo(studyList);
	}
	
	var study 		= undefined;
	var item 		= undefined;
	var itemInfo	= undefined;
	var itemInfoL 	= undefined;
	var itemInfoR 	= undefined;
	for (var i = 0; i < length; i++){
		study = data[i];
		item = $("<div>", {'class' : 'study-list-item', onclick : "studyView(" + study.seq + ")"});
		$("<span>", {"class" : "study-item-overlay", text : "SHOW"}).appendTo(item);
		$("<div>", {"class" : "study-item-fg"}).appendTo(item);
		$("<div>",{"class" : 'study-item-title', text : study.title}).appendTo(item);

		itemInfo = $("<div>",{ 'class' : 'study-item-info'}).appendTo(item);
		itemInfoL = $("<div>",{ 'class' : 'study-item-info-l row-center'}).appendTo(itemInfo);
		itemInfoR = $("<div>",{ 'class' : 'study-item-info-r'}).appendTo(itemInfo);
		
		$("<div>",{ 'class' : 'study-item-sect', text : study.sect}).appendTo(itemInfoL);
		$("<div>",{ 'class' : 'colum-border'}).appendTo(itemInfoL);
		
		if(study.codeLang){
			$("<div>",{ 'class' : 'study-item-codeLang', text : study.codeLang}).appendTo(itemInfoL);
			$("<div>",{ 'class' : 'colum-border'}).appendTo(itemInfoL);
		}
		
		$("<div>",{ 'class' : 'study-item-date', text : study.date}).appendTo(itemInfoL);
		$("<div>",{ 'class' : 'colum-border'}).appendTo(itemInfoL);
		$("<div>",{ 'class' : 'study-item-hits', text : "조회수 " + study.hits}).appendTo(itemInfoL);
		
		$("<div>",{ 'class' : 'study-item-comtcnt', text : "댓글 " + study.comtCnt}).appendTo(itemInfoR);
		
		if(isMobile){
			item.click(function(){
				$(this).find(".study-item-title").trigger("click");
			});
		} 		
		item.appendTo(studyList);			
	}
	
}