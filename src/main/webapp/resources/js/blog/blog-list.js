var perPgLine 	= 10;
var searchType 	= "";
var searchValue = "";
var page 		= "";
var allRowCnt	= undefined;
var blogItemTemp= undefined;
var tags		=  new Array();

$(document).ready(function(){
	doMenuOn(".menu-blog");
	
	blogItemTemp = $(".blog-item").clone();
	$(".blog-item").remove();
	
	/* Page to 1 */
	if(!page){
		page = 1;
	}
	
	/* Add Scroll Page event */
	$(window).scroll(function(){
		var scrollPosition = $(this).scrollTop() + $(this).outerHeight();
		var docHeight = $(this).height();
		if(scrollPosition >= (docHeight/2) - 10 && page * perPgLine < allRowCnt){
			page = page + 1;
			pageMove(page);
		}
	});
	
	clearBlogItems();
	pageMove(page);
	
});


/* Paging */
function pageMove(pg){
	var validTags = new Array();
	
	if(!tags.length){
		var allTag = $(".blog-tags .tag");
		allTag.each(function(){ validTags.push($(this).text()); });
	} else{
		validTags = tags;
	}
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/blog/paging",
		data	: {
			'page'			: pg,
			'perPgLine' 	: perPgLine,
			'tags' 			: JSON.stringify(validTags)
		},
		dataType: 'JSON',
		async	: false,
		success : function(result) {
			var count = result['count'];
			var data = result['data'];
			
			allRowCnt = count;
			
			if(data.length){
				page = pg;
				drawBlog(data);
				drawTags(tags);
			}
			

		},
		error : function(e) {
			console.log(e);
		}
	});
}

function clearBlogItems(){
	$(".blog-item-list").empty();
}

/* draw Study list */
function drawBlog(data){
	var blogList 	= $(".blog-item-list");
	var length		= data.length;
	var blog		= undefined;
	
	if(page == 1){
		blog = data[0];
		
		var blogFirstItem = $(".blog-first-item");
		if(blog.snapsht){
			blogFirstItem.find(".blog-first-item-snapsht").css("background-image", "url('" + getContextPath() + blog.snapsht + "')");
		} else{
			blogFirstItem.find(".blog-item-snapsht").css("background", "#000");
		}
		
		blogFirstItem.find(".blog-first-item-desc").attr("onclick", "doBlogView('" + blog.seq + "')");
		blogFirstItem.find(".blog-first-item-desc .title").html(blog.title);
		blogFirstItem.find(".blog-first-item-desc .content").html(blog.contents);
	} 
	
	for (var i = 0; i < length; i++){
		blog = data[i];
		
		var blogItem = blogItemTemp.clone();
		if(blog.snapsht){
			blogItem.find(".blog-item-snapsht").css("background-image", "url('" + getContextPath() + blog.snapsht + "')");
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
}

function drawTags(tags){
	var allTag = $(".tag");
	allTag.removeClass("on");
	for(var i = 0; i < tags.length; i++){
		allTag.each(function(){
			if($(this).text() === tags[i]){
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
	
	clearBlogItems();
	pageMove(1);
}

function doBlogView(seq){
	window.location.href = getContextPath() + "/blog/view?seq=" + seq;
}