let tags	= new Array();
const paging = {
	limit : 5,
	page : 0,
	totalCount : undefined,
	doing : false,
	item : undefined,
	getCount(){
		return this.limit * this.page;
	},
	clear(){
		$(".blog-item-list").empty();
	},
	next(){
		const param = {
			'offset': this.page * this.limit,
			'limit' : this.limit,
			'tags' 	: tags
		};
		
		$.ajax({
			type	: "GET",
			url		: getContextPath() + "/blogs/records" + encodeURIParam(param),
			dataType: 'JSON',
			context : this,
			beforeSend : function(){
				this.doing = true;
			},
			success : function(data) {
				if(data.length){
					this.page += 1;
					this.draw(data);
				}
			},
			complete : function(){
				this.doing = false;
			},
			error : function(e) {
				console.log(e);
			}
		});
	},
	draw(data){
		const blogList 	= $(".blog-item-list");
		const length	= data.length;
		let blog		= undefined;
		
		if(this.page === 1){
			blog = data[0];
			
			const blogFirstItem = $(".blog-first-item");
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

		for (let i = 0; i < length; i++){
			blog = data[i];
			
			const blogItem = this.item.clone();
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
			
		
			const tags = blog.tag.split(" ");
			for(let j = 0; j < tags.length; j++){
				$("<a>", {"class" : "tag", text : tags[j], onclick : "doSearchTag(this)" }).appendTo(blogItem.find(".blog-item-tag"));
			}
			blogItem.find(".blog-item-comt").text("댓글 " + blog.comtCnt);
			
			blogItem.appendTo($(".blog-item-list"));
		}
		
		// Draw tags.
		const allTag = $(".tag");
		allTag.removeClass("on");
		
		for(let i = 0; i < tags.length; i++){
			allTag.each( function(){
				if($(this).text() === tags[i]){
					$(this).addClass("on");
				}
			})
		}
	},
	
};

$(document).ready( () => {
	doMenuOn(menu.BLOG);
	
	paging.item = $(".blog-item").clone();
	$(".blog-item").remove();
	
	paging.totalCount = $("#totalCount").val();
	
	// Tags 공백 제거.
	if($("#tags").val()){
		tags = $("#tags").val().replace(/ /gi,'').split(",");
	}
	
	/* Scroll Paging */
	$(window).scroll(function(){
		const scrollBottom = $(this).scrollTop()  + $(window).height();
		const blogs  = $(".blog-item");
		const lastBlog = blogs.eq(blogs.length - paging.limit - 1);
		if(blogs.length && scrollBottom >= (lastBlog.offset().top) && paging.getCount() < paging.totalCount && !paging.doing){
			paging.next();
		}
	});
	
	setTimeout(function(){
		$(window).trigger('scroll');
	},100)
	
	
	paging.clear();
	paging.next();
});


const searchByTag = function(target){
	const tg = $(target);
	const tag = tg.text();
	
	tg.toggleClass("on");
	if(tg.hasClass("on")){
		tags.push(tag);		
	} else{
		const index = tags.indexOf(tag);
		if(index != -1){
			tags.splice(index, 1);
		}
	}
	
	window.location.href = getContextPath() + "/blogs" + encodeURIParam({"tags" : tags});
}

const doBlogView = function(seq){
	window.location.href = getContextPath() + "/blogs/" + seq;
}