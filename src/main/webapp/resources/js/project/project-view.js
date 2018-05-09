$(document).ready(function(){
	initBeforeProjectTooltip();
	initAfterProjectTooltip();
	
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
		var afterparentSeq = $("#afterparentSeq").val();
		if(afterparentSeq){
			Progress.start();
			window.location.href = getContextPath() + "/project/view?seq=" + afterparentSeq;
		} 
	});
	
	$(".btn-project-before").on("click", function(){
		var beforeparentSeq= $("#beforeparentSeq").val();
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
});

function downloadFile(pathNm){
	window.location.assign(getContextPath()	+ "/project/download.do?filename="+ pathNm);
}

function initBeforeProjectTooltip(){
    $(".btn-project-before").tooltip({
    	 tooltipClass: "btn-project-tooltip",
    	 show : null,
    	 hide : null,
    });
}

function initAfterProjectTooltip(){
	$(".btn-project-next").tooltip({
		 tooltipClass: "btn-project-tooltip",
		 show : null,
		 hide : null,
	});
}
