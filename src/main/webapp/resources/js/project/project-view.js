function downloadFile(pathNm){
	window.location.assign(getContextPath()	+ "/project/download.do?filename="+ pathNm);
}

$(document).ready(function(){
	var lineDrawing = anime({
		targets: ".project-line",
		easing: "easeInQuad",
		width : [0, "100%"],
		duration: 400,
	});
	
	if(isMobile){
		$(".btn-project-before, .btn-project-next").addClass("display-none");
	}
	
	$(".btn-project-next").on("click", function(){
		var afterparentSeq = '${afterProject.seq}';
		
		if(afterparentSeq){
			Progress.start();
			window.location.href = getContextPath() + "/project/view?seq=" + afterparentSeq;
		} 
	});
	
	$(".btn-project-before").on("click", function(){
		var beforeparentSeq= '${beforeProject.seq}';
		if(beforeparentSeq){
			Progress.start();
			window.location.href = getContextPath() + "/project/view?seq=" + beforeparentSeq;
		} 
	});
	
	/*
	$(".wrapper").touchwipe({
	     wipeLeft: function() {
	    	 $(".btn-project-next").trigger("click");
	     },		     
	     wipeRight: function() {
	    	 $(".btn-project-before").trigger("click");
	     },		     
	     min_move_x: 30,
	     min_move_y: 20,
	     preventDefaultEvents: true
	});
	*/
})