var page 		= 1;
var limit 		= 5;
var category 	= '';
var totalCount	= '';
var searchValue = '';
var studyTemp;
var doingPaging = false;

$(document).ready(function(){
	doMenuOn(menu.STUDY);
	
	studyTemp = $(".study-list-item").clone();
	$(".study-list-item").remove();
	
	
	totalCount = $("#totalCount").val();
	category = $("#category").val();
	searchValue = $("#searchValue").val();
	
	/* Scroll Page event */
	$(window).scroll(function(){
		var scrollBottom = $(this).scrollTop()  + $(window).height();
		var studies  = $(".study-list-item");
		var lastStudy = studies.eq(studies.length - limit - 1);
		
		if(studies.length && scrollBottom >= (lastStudy.offset().top) && page * limit < totalCount && !doingPaging){
			page = page + 1;
			pageMove(page);
		}
	});
	
	/* Add Class 'on' to Category*/
	var items = $(".study-categories .study-categories-item");
	for(var i = 0; i < items.length; i++){
		if($(items[i]).text() == category){
			$(items[i]).addClass("on");
			break;
		}
	}

	setTimeout(function(){
		$(window).trigger('scroll');
	},100)
	
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
	
	var param = {};
	param["category"] = category;
	window.location.href = getContextPath() + "/studies/" + seq + encodeURIParam(param);
}

/* 검색 했을때 */
function search(){
	searchValue =  $("#searchValue").val();
	
	var param = {};
	param["category"] = category;
	param["title"] = searchValue;
	window.location.href = getContextPath() + "/studies" + encodeURIParam(param);
}

/* 카테고리를 선택했을 때 */
function selectCategory(tg){
	var tg = $(tg);
	
	if(tg.hasClass("on")){
		category = '';
	} else{
		category  = tg.text();
	}
	
	var param = {};
	param["category"] = category;
	param["title"] = searchValue;
	window.location.href = getContextPath() + "/studies" + encodeURIParam(param);
}

/* Paging */
function pageMove(pg){
	$.ajax({
		type	: "GET",
		url		: getContextPath() + "/studies/records",
		data	: {
			'offset'	: (pg - 1) * limit,
			'limit' 	: limit,
			'category' 	: category,
			'title'		: searchValue
		},
		dataType: 'JSON',
		beforeSend : function(){
			doingPaging = true;
		},
		success : function(data) {
			page = pg;
			drawStudy(data);
		},
		complete: function(){
			doingPaging = false;
		},
		error : function(e) {
			console.log(e);
		}
	});
}

/* draw Study list */
function drawStudy(records){
	var studyList 	= $(".study-list");
	var length		= records.length;
	var item		= undefined;
	
	if(!records.length){
		study = records[i];
		item = $("<div>", {'class' : 'study-list-item', onclick : "$('.search-value').val(''); search();"});
		$("<div>",{ 'class' : 'study-item-nosearch', text: "조회된 글이 없습니다.(목록으로)" }).appendTo(item);
		item.appendTo(studyList);
	}
	
	for (var i = 0; i < length; i++){
		study = records[i];
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