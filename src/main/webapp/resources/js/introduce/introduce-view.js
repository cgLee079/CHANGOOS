$(document).ready(function(){
	var wrap	= $(".myinfo-views");
	var views	= $(".myinfo-views > .myinfo-view");
	var btns 	= $(".btn-views > .btn-view");
	var current = 0 ;
	var setIntervalId;
	var openAni;
	
	views.css({left : "-100%"});
	views.eq(0).css({left : "0"});
	btns.removeClass("on");
	btns.eq(0).addClass("on");
	
	btns.on("click", function(){
		var tg = $(this);
		var index = tg.index();
		
		btns.removeClass("on");
		tg.addClass("on");
		
		move(index);
	});
	
	function move(index){
		var currentEl =  views.eq(current);
		var nextEl = views.eq(index);
		
		currentEl.css({left : "0"}).stop().animate({left : "-100%"});
		nextEl.css({left : "100%"}).stop().animate({left : "0%"});
		
		current = index;
	}
	
	function timer(){
		setIntervalId = setInterval(function(){
			var n = current + 1;
			if(n === views.length){
				n = 0;
			}
			btns.eq(n).trigger("click");
		}, 30000);
	}
	
	if(!isMobile){
		//timer();	
	}
	
	function toLeft(){
		var tg = $(".myinfo-views");
		var index 		= current;
		var toIndex 	= index + 1;
		var items 		= $(".myinfo-view");
		var itemLength 	= items.length;
		var btns 		= $(".btn-views > .btn-view");
		var currentEl	= undefined;
		var nextEl		= undefined;
		
		if(toIndex >= itemLength){ // 4 to 0
			toIndex = 0;
		}
		
		currentEl 	= items.eq(index);
		nextEl 		= items.eq(toIndex);
		  	 
		currentEl.css({left : "0"}).stop().animate({left : "-100%"});
	  	nextEl.css({left : "100%"}).stop().animate({left : "0%"});	
	  	
	  	btns.removeClass("on");
	  	btns.eq(toIndex).addClass("on");
	  	current = toIndex;
	}
	
	function toRight(){
		var tg = $(".myinfo-views");
		var index 		= current;
		var toIndex 	= index - 1;
		var items 		= $(".myinfo-view");
		var btns 		= $(".btn-views > .btn-view");
		var currentEl	= undefined;
		var nextEl		= undefined;
		if(toIndex < 0){ //  -1 to 3 
			toIndex = 3;
		} 
		
		currentEl 	= items.eq(index);
		nextEl 		= items.eq(toIndex);
		
		currentEl.css({left : "0"}).stop().animate({left : "100%"});
	  	nextEl.css({left : "-100%"}).stop().animate({left : "0%"});	
	  	
	  	btns.removeClass("on");
	  	btns.eq(toIndex).addClass("on");
		current = toIndex;
	}
	
	/*
	$(".myinfo-views").on("wheel", function(e){
		e.preventDefault();
        var delta = e.originalEvent.deltaY;
        if(delta > 0 ) {toLeft(); }
        else  {toRight(); }
	})
	*/
	
	$(".myinfo-views").touchwipe({
		wipeLeft	: function() { toLeft();},
		wipeRight	: function() { toRight();},
		min_move_x	: 20,
		min_move_y	: 20,
		preventDefaultEvents: true
	});
});

function doSendMessage(){
	var message = $(".input-message").val();
	
	$.ajax({
		type	: "POST",
		url		: getContextPath() + "/introduce/remain_message.do",
		data	: {
			'contents' : message	
		},
		dataType: 'JSON',
		beforeSend : function(){
			Progress.start();
		},
		success : function(re) {
			if(re.result){
				swal({text : "방명록이 등록되었습니다", icon : "success"});
				$(".input-message").val("");
			} else{
				swal({text : "방명록 등록 실패하였습니다.", icon : "error"});
			}
		},
		complete: function(){
			Progress.stop();
		},
		error : function(e) {
			console.log(e);
			swal({text : "방명록 등록 실패하였습니다.", icon : "error"});
			Progress.stop();
		}
	});							
}