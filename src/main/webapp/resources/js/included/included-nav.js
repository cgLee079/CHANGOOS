function navBgResize() {
	$(".nav-background").css("width", "100%");
	$(".nav-background").css("height", "100%");

	var width = $(".nav-background").width();
	var height = $(".nav-background").height();
	var radius = undefined;

	if (width >= height) { radius = deviceWidth; } 
	else { radius = deviceHeight; }

	$(".nav-background").css("width", radius);
	$(".nav-background").css("height", radius);
	$(".nav-background").css("top", -radius);
	$(".nav-background").css("left", -radius);
}

$(document).ready(function(){
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
	
	$(".nav-icon").click(function() {
		$(this).toggleClass("open");

		navBgResize();

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
				easing : "easeInQuad",
				duration : 400,
				scale : [ 1, 3.5 ],
				borderRadius : [ "100%", 0 ],
				background : [ "#000", "#FFF" ],
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
				easing : "easeInQuad",
				duration : 500,
				scale : [ 3, 1 ],
				borderRadius : [ 0, "100%" ],
				background : [ "#FEFDFE", "#EDECED" ]
			})

			$(".nav-menu").addClass("unvalid");
			$("html, body").off("scroll touchmove mousewheel");
			$("html, body").css("overflow", "");
		}
	});
	
	$(".btn-scroll-top").click(function() {
		$("html, body").animate({ scrollTop : 0 });
	})
});

var Progress = {
	start 	: function() { $(".progress-bar").removeClass("off"); },
	stop 	: function() { $(".progress-bar").addClass("off"); }
}

$(window).resize(function() {
	navBgResize();
});

$(window).scroll(function(event) {
	var scroll = $(window).scrollTop();
	if (scroll > 100) { $(".btn-scroll-top").removeClass("off"); } 
	else { $(".btn-scroll-top").addClass("off"); }
});
