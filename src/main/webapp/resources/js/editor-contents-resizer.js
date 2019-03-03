const initImgSize = [];

const contentImgResize = function(){
	var imgs = $(".editor-contents img");
	imgs.each(function(){
		const parentWidth = parseInt($(".editor-contents").width());
		const width 	= parseInt($(this).css("width"));
		const height 	= parseInt($(this).css("height"));
		const ratio	= width / height;
		const index 	= imgs.index(this);
		
		$(this).css("width", "");
		$(this).css("height", "");
		
		if(width > parentWidth){
			$(this).css("width", "100%");
		} else if (width <= parentWidth){
			$(this).css("width", initImgSize[index]);
			width = parseInt($(this).css("width"));
			if(width > parentWidth){
				$(this).css("width", "100%");
			}
		} 
		
	});
}

const contentYoutubeResize = function(){
	const parentWidth = parseInt($(".editor-contents").width());
	const videos = $(".editor-contents iframe");
	videos.each(function(){
		if(parentWidth >= 640){
			$(this).attr("width", "640");
			$(this).attr("height", "360");
		} else{
			const width = parentWidth;
			const ratio = parseFloat($(this).attr("width") /$(this).attr("height"));
			$(this).attr("width", width);
			$(this).attr("height", width / ratio );
		} 
	});
}


const resizedw = function(){
	contentImgResize();
	contentYoutubeResize();
}

let doit;
$(window).resize(() => {
  clearTimeout(doit);
  doit = setTimeout(resizedw, 100);
});

window.addEventListener("load", () => {
	const imgs = $(".editor-contents img");
	for(let i = 0; i < imgs.length; i++){
		initImgSize.push($(imgs[i]).css("width"));
	}
	
	contentImgResize();
	contentYoutubeResize();
});