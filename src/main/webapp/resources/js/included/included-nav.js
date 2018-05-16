$(document).ready(function(){
	
	/* logo mouseover event */
	var drawingLine = undefined;
	$(".wrap-home-logo .logo").hover(function() {
		drawingLine = anime({
			targets : ".wrap-home-logo .logo-line",
			scale : [ 0, 1 ],
			easing : "easeInQuad",
			duration : 300
		})
	}, function() {
		drawingLine.play();
		drawingLine.reverse();
	});
	
	/* when navigation click, draw Menu */
	$(".nav-icon").click(function() {
		$(this).toggleClass("open");

		if ($(this).hasClass("open")) {
			$(".nav-menu").removeClass("unvalid");
			$("html, body").css("overflow", "hidden");
			$("html, body").on("scroll touchmove mousewheel", function(event) {
				event.preventDefault();
				event.stopPropagation();
				return false;
			});

			var ani = anime.timeline({
				loop : false
			}).add({
				targets : ".nav-background",
				easing: "easeInCubic",
				duration : 400,
				scale: [0.2, 3],
				opacity: [0.2,1],
			}).add({
				targets : ".nav-menu",
				opacity : [ 0, 1 ],
				duration : 1,
				easing : "easeOutExpo"
			}).add({
				targets : ".ml1 .letter",
				scale : [ 0.3, 1 ],
				opacity : [ 0, 1 ],
				translateZ : 0,
				easing : "easeOutExpo",
				duration : 500,
				delay : function(el, i) {
					return 50 * (i)
				}
			}).add({
				targets : ".nav-menu div",
				scale : [ 0, 1 ],
				easing : "easeOutExpo",
				duration : 500,
				delay : function(el, i) {
					return 50 * (i);
				}
			})
		} else {
			var ani = anime.timeline({
				loop : false
			}).add({
				targets : ".nav-menu",
				opacity : [ 1, 0 ],
				duration : 100,
				easing : "easeOutExpo"
			}).add({
				targets : ".nav-background",
				easing: "easeInCubic",
				duration : 500,
				scale: [3,0],
				opacity: [1,0],
			})

			$(".nav-menu").addClass("unvalid");
			$("html, body").off("scroll touchmove mousewheel");
			$("html, body").css("overflow", "");
		}
	});
	
	/* when 'TOP' click */
	$(".btn-scroll-top").click(function() {
		$("html, body").animate({ scrollTop : 0 });
	})
});

/* when window scroll, draw 'top' button */
$(window).scroll(function(event) {
	var scroll = $(window).scrollTop();
	if (scroll > 100) { $(".btn-scroll-top").removeClass("off"); } 
	else { $(".btn-scroll-top").addClass("off"); }
});

/* Progress bar */
var Progress = {
	start 	: function() { $(".progress-bar").removeClass("off"); },
	stop 	: function() { $(".progress-bar").addClass("off"); }
}