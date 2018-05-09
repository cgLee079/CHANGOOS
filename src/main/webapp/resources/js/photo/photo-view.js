const moveDuration = 1000;
	
function wrapPhotoViewSetHeight(){
	var wrapPhotoView = $(".wrap-photo-view");
	wrapPhotoView.css("height", deviceHeight - 100);
}

function photoSnapshtResize(){
	var photoItems = $(".photo-item");
	var length = photoItems.length;
	
	photoItems.each(function(){
		var tg = $(this);
		var height 	= parseInt(tg.css("height"));
		var width	= height * (4/3);
		tg.css("width", width);
	});
	
	for(var i = 0; i < length; i++){
		var tg = photoItems.eq(i);
		var width = parseInt(tg.css("width"));
		tg.stop().css("left", (width * i) + (5 * i));
	}
}

function showPhoto(seq, index){
	var photoItems = $(".photo-list > .photo-item");
	var tg = photoItems.eq(index);
	photoItems.removeClass("on");
	tg.addClass("on");
	
	var tgLeft = parseInt(tg.css("left"));
	photoItems.each(function(){
		var left = parseInt($(this).css("left"));
		var toLeft = left - tgLeft;
		$(this).css({"left" : left}).stop().animate({"left" : toLeft}, moveDuration);
	})
	
	$.ajax({
		type	: "POST",
		url 	: getContextPath() + "/photo/view.do",
		dataType: "JSON",
		data 	: {
			"seq" : seq
		},
		success : function(photo) {
		 	$(".photo-view").css("opacity", 0);
			anime({
				  targets	: ".photo-view",
				  duration	: 500,
				  opacity	: 1,
				  easing	: 'easeInOutSine',
			});

			if(photo.device === null){
				photo.device = "";
			}
			$(".photo-img")	.css("background-image", "url('" + getContextPath() + photo.image +"')");
			$(".photo-name").html(photo.name);
			$(".photo-date-loc").html(photo.date + " " + photo.location);
			$(".photo-desc").html(photo.desc);
			$(".photo-tag").html(photo.tag);
			$(".photo-index").html(index);
		}	
	});
}

$(document).ready(function(){
	var items 		= $(".photo-item");
	items.eq(0).trigger("click");
	 
	$(".btn-right-list").on("click", function(){
		var photoList = $(".photo-list");
		var photoItems = $(".photo-list > .photo-item");
		var wrapWidth = parseInt(photoList.css("width"));
		var length = photoItems.length;
		
		var lastTg 		= photoItems.eq(length - 1);
		var lastLeft 	= parseInt(lastTg.css("left"));
		var lastToLeft 	= lastLeft - wrapWidth;
		var lastToRight = lastLeft + parseInt(lastTg.css("width"));
		
		if(!(lastToRight <= wrapWidth)){
			for(var i = 0; i < length; i++){
				var tg = photoItems.eq(i);
				var width 	= parseInt(tg.css("width"));
				var left 	= parseInt(tg.css("left"));
				var toLeft 	= left - wrapWidth;
				tg.css({"left" : left}).stop().animate({"left" : toLeft}, moveDuration);
			}
		}
	});
	
	$(".btn-left-list").on("click", function(){
		var photoList = $(".photo-list");
		var photoItems = $(".photo-list > .photo-item");
		var wrapWidth = parseInt(photoList.css("width"));
		var length = photoItems.length;
		
		var overLeft = false;
		var firstTg 	= photoItems.eq(0);
		var firstLeft 	= parseInt(firstTg.css("left"));
		var firstToLeft = firstLeft + wrapWidth;
		if(firstToLeft > 0){
			overLeft = true;
		}
		
		for(var i = 0; i < length; i++){
			var tg = photoItems.eq(i);
			var width 	= parseInt(tg.css("width"));
			var left 	= parseInt(tg.css("left"));
			var toLeft 	= left + wrapWidth;
			
			if(overLeft){
				tg.stop().animate({"left" : (width * i) + (5 * i)}, moveDuration);
			} else {				
				tg.css({"left" : left}).stop().animate({"left" : toLeft}, moveDuration);
			}
		}
	});
	
	$(".wrap-photo-list").on("wheel", function(e){
		e.preventDefault();
        var delta = e.originalEvent.deltaY;
        if(delta > 0 ) {$(".btn-right-list").trigger("click"); }
        else  {$(".btn-left-list").trigger("click"); }
	})
	
	$(".photo-view").touchwipe({
	     wipeLeft: function() {
	    	 var tg = $(".photo-view");
	    	 var index 		= parseInt(tg.find(".photo-index").html());
	    	 var toIndex 	= index + 1;
	    	 var items 		= $(".photo-item");
	    	 var itemLength = items.length;
	    	 if(toIndex < itemLength){
				items.eq(toIndex).trigger("click");
	    	 } else{
	    		swal("더이상 사진이 없습니다."); 
	    	 } 
	     },
	     
	     wipeRight: function() {
	    	 var tg = $(".photo-view");
	    	 var index 		= parseInt(tg.find(".photo-index").html());
	    	 var toIndex 	= index - 1;
	    	 var items 		= $(".photo-item");
	    	 if(toIndex >= 0){
	    		 items.eq(toIndex).trigger("click");
	    	 } else{
	    		 swal("더이상 사진이 없습니다."); 
	    	 }
	     },
	     
	     min_move_x: 20,
	     min_move_y: 20,
	     preventDefaultEvents: true
	});
	
	$(".photo-list").touchwipe({
	     wipeLeft: function() {
	    	 $(".btn-right-list").trigger("click");
	     },
	     
	     wipeRight: function() {
	    	 $(".btn-left-list").trigger("click");
	     },
	     
	     min_move_x: 20,
	     min_move_y: 20,
	     preventDefaultEvents: true
	});
	
	wrapPhotoViewSetHeight();
	photoSnapshtResize();
	$(".photo-img").css("height", parseInt($(".photo-view").height()) * 0.7);
	
});

$(window).resize(function(){
	wrapPhotoViewSetHeight();
	photoSnapshtResize();
	$(".photo-img").css("height", parseInt($(".photo-view").height()) * 0.7);
});