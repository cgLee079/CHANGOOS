var currentView = 0;
var tops 	= [];
var pass 	= [];

var projectViews;

function projectView(seq){
	Progress.start();
	window.location.href = getContextPath() + "/project/view?seq=" + seq;		
}

$(document).ready(function(){
	projectViews = $(".project-view");
	projectViews.each(function(){
		tops.push($(this).offset().top);
		pass.push(false);
	});
	
	if(isMobile){
		projectViews.each(function(){
			$(this).bind("click", function(){
				var seq = $(this).find("#project-seq").val();
				projectView(seq);
			});
		});	
	}
	
	$(window).scroll(function(){
		var scrollTop = $(window).scrollTop();
		if(tops[currentView] <= (scrollTop + 550) && pass[currentView] == false){
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
		
	});
	
	$(window).trigger("scroll");
});