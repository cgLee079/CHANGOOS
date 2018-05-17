$(document).ready(function(){
	/* draw line animation */
	var lineDrawing = anime({
		targets: ".project-line",
		easing: "easeInQuad",
		width : [0, "100%"],
		duration: 400,
	});
	
	/* before, next Project button initialize */
	initBeforeProjectBtn();
	initNextProjectBtn();
	
	function initBeforeProjectBtn(){
	    $(".btn-project-before").tooltip({
	    	position: 'right',
	    	show : null,
	    	hide : null,
	    });
	    
		if(isMobile){
			$(".btn-project-before").addClass("display-none");
		}
		
		$(".btn-project-next").on("click", function(){
			var afterparentSeq = $("#afterparentSeq").val();
			if(afterparentSeq){
				Progress.start();
				window.location.href = getContextPath() + "/project/view?seq=" + afterparentSeq;
			} 
		});
	}

	function initNextProjectBtn(){
		$(".btn-project-next").tooltip({
			position: 'left',
			show : null,
			hide : null,
		});
		
		if(isMobile){
			$(".btn-project-next").addClass("display-none");
		}
		
		$(".btn-project-before").on("click", function(){
			var beforeparentSeq= $("#beforeparentSeq").val();
			if(beforeparentSeq){
				Progress.start();
				window.location.href = getContextPath() + "/project/view?seq=" + beforeparentSeq;
			} 
		});
	}

	
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

/* download file */
function downloadFile(pathNm){
	window.location.assign(getContextPath()	+ "/project/download.do?filename="+ pathNm);
}
