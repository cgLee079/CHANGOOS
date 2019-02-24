var limit 		= 5;
var page 		= 1;
var totalCount	= undefined;
var blogItemTemp= undefined;
var tags		= new Array();
var doingPaging	= false;

$(document).ready(function(){
	doMenuOn(menu.BLOG);
	
	blogItemTemp = $(".blog-item").clone();
	$(".blog-item").remove();
	
	totalCount = $("#totalCount").val();
	
	// Tags 공백 제거.
	if($("#tags").val()){
		tags = $("#tags").val().replace(/ /gi,'').split(",");
	}
	
	/* Scroll Paging */
	$(window).scroll(function(){
		var scrollBottom = $(this).scrollTop()  + $(window).height();
		var blogs  = $(".blog-item");
		var lastBlog = blogs.eq(blogs.length - limit - 1);
		if(blogs.length && scrollBottom >= (lastBlog.offset().top) && page * limit < totalCount && !doingPaging){
			page = page + 1;
			pageMove(page);
		}
	});
	
	setTimeout(function(){
		$(window).trigger('scroll');
	},100)
	
	
	clearBlogItems();
	pageMove(page);
});


/* Paging */
function pageMove(pg){
	var param = {
		'offset': (pg - 1) * limit,
		'limit' : limit,
		'tags' 	: tags
	};
	
	$.ajax({
		type	: "GET",
		url		: getContextPath() + "/blogs/records" + encodeURIParam(param),
		dataType: 'JSON',
		beforeSend : function(){
			doingPaging = true;
		},
		success : function(data) {
			if(data.length){
				page = pg;
				drawBlog(data);
			}
		},
		complete : function(){
			doingPaging = false;
		},
		error : function(e) {
			console.log(e);
		}
	});
}

function clearBlogItems(){
	$(".blog-item-list").empty();
}

/* draw Blog list */
function drawBlog(data){
	var blogList 	= $(".blog-item-list");
	var length		= data.length;
	var blog		= undefined;
	
	if(page == 1){
		blog = data[0];
		
		var blogFirstItem = $(".blog-first-item");
		if(blog.thumbnail){
			blogFirstItem.find(".blog-first-item-snapsht").css("background-image", "url('" + getContextPath() + loc.blog.thumbDir + blog.thumbnail + "')");
		}  else if(blog.images.length > 0){
			blogFirstItem.find(".blog-first-item-snapsht").css("background-image", "url('" + getContextPath() + loc.blog.imageDir + blog.images[0].pathname + "')");
		} else{
			blogFirstItem.find(".blog-first-item-snapsht").css("background", "#000");
		}
		
		blogFirstItem.find(".blog-first-item-desc").attr("onclick", "doBlogView('" + blog.seq + "')");
		blogFirstItem.find(".blog-first-item-desc .title").html(blog.title);
		blogFirstItem.find(".blog-first-item-desc .content").html(blog.contents);
	}

	for (var i = 0; i < length; i++){
		blog = data[i];
		
		var blogItem = blogItemTemp.clone();
		if(blog.thumbnail){
			blogItem.find(".blog-item-snapsht").css("background-image", "url('" + getContextPath() + loc.blog.thumbDir + blog.thumbnail + "')");
		} else if(blog.images.length > 0){
			blogItem.find(".blog-item-snapsht").css("background-image", "url('" + getContextPath() + loc.blog.imageDir + blog.images[0].pathname + "')");
		} else{
			blogItem.find(".blog-item-snapsht").css("background", "#000");
		}
		
		blogItem.find(".blog-item-title").html(blog.title);
		blogItem.find(".blog-item-content").html(blog.contents);
		
		blogItem.find(".blog-item-snapsht").attr("onclick", "doBlogView('" + blog.seq + "')");
		blogItem.find(".blog-item-title").attr("onclick", "doBlogView('" + blog.seq + "')");
		blogItem.find(".blog-item-content").attr("onclick", "doBlogView('" + blog.seq + "')");
		blogItem.find(".blog-item-empty").attr("onclick", "doBlogView('" + blog.seq + "')");
		
	
		var tags = blog.tag.split(" ");
		for(var j = 0; j < tags.length; j++){
			$("<a>", {"class" : "tag", text : tags[j], onclick : "doSearchTag(this)" }).appendTo(blogItem.find(".blog-item-tag"));
		}
		blogItem.find(".blog-item-comt").text("댓글 " + blog.comtCnt);
		
		blogItem.appendTo($(".blog-item-list"));
	}
	
	drawTags();
}

function drawTags(){
	var allTag = $(".tag");
	allTag.removeClass("on");
	
	for(var i = 0; i < tags.length; i++){
		allTag.each(function(){
			if($(this).text() == tags[i]){
				$(this).addClass("on");
			}
		})
	}
}

function doSearchTag(tg){
	var tg = $(tg);
	var tag = tg.text();
	
	tg.toggleClass("on");
	if(tg.hasClass("on")){
		tags.push(tag);		
	} else{
		var index = tags.indexOf(tag);
		if(index != -1){
			tags.splice(index, 1);
		}
	}
	
	window.location.href = getContextPath() + "/blogs" + encodeURIParam({"tags" : tags});
}

function doBlogView(seq){
	window.location.href = getContextPath() + "/blogs/" + seq;
}