let category 	= '';
let searchValue = '';
const paging = {
	page : 0,
	limit : 5,
	totalCount : undefined,
	item : undefined,
	doing : false,
	getCount(){
		return this.page * this.limit;
	},
	clear(){
		$(".study-list-item").remove();
		$("html, body").scrollTop(0);
	},
	next(){
		const param = {
			'offset': this.page * this.limit,
			'limit' : this.limit,
			'category' 	: category,
			'title'		: searchValue
		};
		
		$.ajax({
			type	: "GET",
			url		: getContextPath() + "/studies/records",
			data	: param,
			context : this,
			dataType: 'JSON',
			beforeSend : function(){
				this.doing = true;
			},
			success : function(data) {
				this.page += 1;
				this.draw(data);
			},
			complete: function(){
				this.doing = false;
			},
			error : function(e) {
				console.log(e);
			}
		});
	},
	
	draw(data){
		const studyList 	= $(".study-list");
		const length		= data.length;
		let item		= undefined;
		
		if(!data.length){
			item = $("<div>", {'class' : 'study-list-item', onclick : "$('.search-value').val(''); searchByValue();"});
			$("<div>",{ 'class' : 'study-item-nosearch', text: "조회된 글이 없습니다.(목록으로)" }).appendTo(item);
			item.appendTo(studyList);
		}
		
		for (let i = 0; i < length; i++){
			study = data[i];
			item = paging.item.clone();
			
			item.attr("onclick", "doStudyView(" + study.seq + ")");
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
	
}


$(document).ready(() => {
	doMenuOn(menu.STUDY);
	
	category = $("#category").val();
	searchValue = $("#searchValue").val();
	
	paging.totalCount = $("#totalCount").val();
	paging.item = $(".study-list-item").clone();
	$(".study-list-item").remove();
	
	/* Scroll Page event */
	$(window).scroll(function(){
		const scrollBottom = $(this).scrollTop()  + $(window).height();
		const studies  = $(".study-list-item");
		const lastStudy = studies.eq(studies.length - paging.limit - 1);
		
		if(studies.length && scrollBottom >= (lastStudy.offset().top) && paging.getCount() < paging.totalCount && !paging.doing){
			paging.next();
		}
	});
	
	/* Add Class 'on' to Category*/
	const items = $(".study-categories .study-categories-item");
	for(let i = 0; i < items.length; i++){
		if($(items[i]).text() === category){
			$(items[i]).addClass("on");
			break;
		}
	}

	setTimeout(function(){
		$(window).trigger('scroll');
	},100)
	
	paging.clear();
	paging.next();
});

/* when study click */
const doStudyView =  function (seq){
	Progress.start();
	
	const param = {
		'category' : category,	
	};
	window.location.href = getContextPath() + "/studies/" + seq + encodeURIParam(param);
}

/* 검색 했을때 */
const searchByValue = function(){
	searchValue =  $("#searchValue").val();
	
	const param = {
		'category' : category,
		'title'	: searchValue
	};
	window.location.href = getContextPath() + "/studies" + encodeURIParam(param);
}

/* 카테고리를 선택했을 때 */
const serachByCategory = function(target){
	let tg = $(target);
	
	if(tg.hasClass("on")){
		category = '';
	} else{
		category  = tg.text();
	}
	
	const param = {
		'category' : category,
		'title'	: searchValue
	};
	window.location.href = getContextPath() + "/studies" + encodeURIParam(param);
}

