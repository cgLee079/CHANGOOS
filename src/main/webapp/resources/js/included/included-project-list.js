var currentView = 0;
var tops 	= [];
var pass 	= [];

/* when project click */
function projectView(seq){
	Progress.start();
	window.location.href = getContextPath() + "/project/view?seq=" + seq;		
}

$(document).ready(function(){
	/* save each project top position */ 
	var projectViews = $(".project-view");
	projectViews.each(function(){
		tops.push($(this).offset().top);
		pass.push(false);
	});
	
	
	/* mobile, add click event */
	if(isMobile){
		projectViews.each(function(){
			$(this).bind("click", function(){
				var seq = $(this).find("#project-seq").val();
				projectView(seq);
			});
		});	
	}
	
	/* when scroll, show project */
	$(window).scroll(onScroll);
	
	var index = 0;
	var scrollTop = $(window).scrollTop();
	while(tops[index] <= scrollTop){
		onScroll();
		index++;
	}
	
	function onScroll(){
		var scrollTop = $(window).scrollTop();
		console.log("##");
		if(tops[currentView] <= (scrollTop + 550) && pass[currentView] == false){
			console.log("dddd");
			pass[currentView] = true;
			var target = document.querySelector(".project-view:nth-child(" + (currentView + 1)+ ")");
			anime.timeline()
				.add({
					targets: target,
					opacity : [0, 1],
					duration: 650,
					easing : "easeInQuad"
				});
			currentView += 1;
		}
	}
	
});