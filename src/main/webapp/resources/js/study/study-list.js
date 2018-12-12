var perPgLine 	= 10;
var searchType 	= "";
var searchValue = "";
var page 		= 1;
var category 	= 'ALL';
var allRowCnt	= undefined;
var studyTemp	= undefined;

$(document).ready(function(){
	doMenuOn(".menu-study");
	
	studyTemp = $(".study-list-item").clone();
	$(".study-list-item").remove();
	
	/* Add Scroll Page event */
	$(window).scroll(function(){
		var scrollPosition = $(this).scrollTop() + $(this).outerHeight();
		var docHeight = $(this).height();
		if(scrollPosition >= (docHeight/2) - 10 && page * perPgLine < allRowCnt){
			page = page + 1;
			pageMove(page);
		}
	});
	
	/* Get Hash */
	allRowCnt = $("#allRowCnt").val();
	category = decodeURI(window.location.hash.substring(1));
	
	/* Add Class 'on' to Category*/
	if(!category){ category = 'ALL'; }
	var items = $(".study-categories .study-categories-item");
	for(var i = 0; i < items.length; i++){
		if($(items[i]).text() == category){
			$(items[i]).addClass("on");
			break;
		}
	}

	clearStudyItems();
	pageMove(page);
});

function clearStudyItems(){
	$(".study-list-item").remove();
	$("html, body").scrollTop(0);
}

/* when study click */
function studyView(seq){
	Progress.start();
	window.location.href = getContextPath() + "/study/view?seq=" + seq + "&category=" + category;
}

/* when Search button click */
function search(){
	searchType	= $(".search-type").val();
	searchValue = $(".search-value").val();
	
	clearStudyItems();
	pageMove(1);
}

/* when Category click */
function selectCategory(tg){
	var tg = $(tg);
	
	var items = $(".study-categories .study-categories-item");
	items.removeClass("on");
	tg.addClass("on");
	category = tg.text();
	window.location.hash = category;
	
	clearStudyItems();
	pageMove(1);
}

/* Paging */
function pageMove(pg){
	if(category == "ALL"){
		category = '';
	}
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/study/paging.do",
		data	: {
			'page'			: pg,
			'perPgLine' 	: perPgLine,
			'category' 		: category,
			'searchType'	: searchType,
			'searchValue'	: searchValue
		},
		dataType: 'JSON',
		async	: false,
		success : function(result) {
			var count = result['count'];
			var data = result['data'];
			allRowCnt = count;
			
			if(data.length){
				page = pg;
				drawStudy(data);
			}
		},
		complete: function(){
		},
		error : function(e) {
			console.log(e);
		}
	});
}

/* draw Study list */
function drawStudy(data){
	var studyList 	= $(".study-list");
	var length		= data.length;
	var item		= undefined;
	
	if(!data.length){
		study = data[i];
		item = $("<div>", {'class' : 'study-list-item', onclick : "$('.search-value').val(''); search();"});
		$("<div>",{ 'class' : 'study-item-nosearch', text: "조회된 글이 없습니다.(목록으로)" }).appendTo(item);
		item.appendTo(studyList);
	}
	
	for (var i = 0; i < length; i++){
		study = data[i];
		item = studyTemp.clone();
		
		item.attr("onclick", "studyView(" + study.seq + ")");
		item.find('.study-item-title').text(study.title);
		item.find('.study-item-content').text(study.contents);
		item.find('.study-item-category').text(study.category);
		item.find('.study-item-lang').text(study.codeLang);
		item.find('.study-item-date').text(study.date);
		item.find('.study-item-hits').text("조회수 " + study.hits);
		item.find('.study-item-comtcnt').text("댓글 " + study.comtCnt);
		
		if(!study.codeLang){
			item.find('.study-item-lang').css("display", "none");
		}
		
		item.appendTo(studyList);			
	}
	
}