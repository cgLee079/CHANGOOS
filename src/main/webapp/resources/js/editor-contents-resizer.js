function contentImgResize(){
	var imgs = $(".editor-contents img");
	imgs.each(function(){
		var parentWidth = parseInt($(".editor-contents").width());
		var width 	= parseInt($(this).css("width"));
		var height 	= parseInt($(this).css("height"));
		var ratio	= width / height;
		
		$(this).css("width", "");
		$(this).css("height", "");
		
		if(width > parentWidth){
			$(this).css("width", "100%");
		} else if (width <= parentWidth){
			$(this).css("width", "");
			width 	= parseInt($(this).css("width"));
			if(width > parentWidth){
				$(this).css("width", "100%");
			}
		} 
		
	});
}

function contentYoutubeResize(){
	var parentWidth = parseInt($(".editor-contents").width());
	var videos = $("iframe");
	videos.each(function(){
		if(parentWidth >= 640){
			$(this).attr("width", "640");
			$(this).attr("height", "360");
		} else{
			var width = parentWidth;
			var ratio = parseFloat($(this).attr("width") /$(this).attr("height"));
			$(this).attr("width", width);
			$(this).attr("height", width / ratio );
		} 
	});
}


function resizedw(){
	contentImgResize();
	contentYoutubeResize();
}

var doit;
$(window).resize(function(){
  clearTimeout(doit);
  doit = setTimeout(resizedw, 100);
});