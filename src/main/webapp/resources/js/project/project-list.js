var currentView = 0;
var tops 	= [];
var pass 	= [];

$(document).ready(function(){
	doMenuOn(menu.PROJECT);
	
	var projectViews = $(".project-view");
	projectViews.each(function(){
		$(this).bind("click", function(){
			var seq = $(this).find("#project-seq").val();
			projectView(seq);
		});
	});	
	
});

/* when project click */
function projectView(seq){
	Progress.start();
	window.location.href = getContextPath() + "/projects/" + seq;
}
