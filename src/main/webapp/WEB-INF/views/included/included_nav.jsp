<div class="nav-top">
	<div class="wrap-home-logo">
		<div onclick="window.location.href = '${pageContext.request.contextPath}/'">
		CHANGOO'S
		</div>
		<div class="logo-line"></div>
	</div>
</div>
<div class="nav-occupy"></div>

<script>
function navOccupyReHeight(){
	var navTop = $(".nav-top");
	var height = navTop.outerHeight();
	$(".nav-occupy").height(height);
}
navOccupyReHeight();
$(window).resize(function(){
	navOccupyReHeight();
});

(function(){
	var drawingLine = undefined;
	$(".wrap-home-logo").hover(function(){
		drawingLine = anime({
			targets : ".logo-line",
			scale : [0, 1],
			easing : "easeInQuad",
			duration : 300
		})
	}, function(){
		drawingLine.play();
		drawingLine.reverse();
	});
		
})();
</script>
<div class="nav-icon">
	<span></span>
	<span></span>
	<span></span>
	<span></span>
</div>
<script>
(function(){
	function navBgResize(){
		$(".nav-background").css("width", "100%");
		$(".nav-background").css("height", "100%");
		
		var width = $(".nav-background").width();
		var height = $(".nav-background").height();
		var radius = undefined;
		
		if(width >= height) { radius = deviceWidth; }
		else { radius = deviceHeight; }
		
		$(".nav-background").css("width", radius);
		$(".nav-background").css("height", radius);
		$(".nav-background").css("top", -radius);
		$(".nav-background").css("left", -radius);
	}
	
	$(window).resize(function(){
		navBgResize();
	});
	
	$(".nav-icon").click(function(){
		$(this).toggleClass("open");
		
		navBgResize();
		
		if($(this).hasClass("open")){
			$(".nav-menu").removeClass("unvalid");
			$("html, body").addClass("scroll-no");
			$("html, body").on("scroll touchmove mousewheel", function(event) {
			   event.preventDefault();
			   event.stopPropagation();
			   return false;
			});
			
			var ani = anime.timeline({loop : false})
				.add({
					targets	: ".nav-background",
					easing: "easeInQuad",
					duration: 300,
					scale : [1, 3],
					borderRadius : ["100%", 0],
					background : ["#EDECED", "#FEFDFE"],
				}).add({
					targets : ".nav-menu",
					opacity : [0, 1],
					duration: 1,
					easing: "easeOutExpo"
				}).add({
					targets: ".ml1 .letter",
					scale: [0.3,1],
					opacity: [0,1],
					translateZ: 0,
					easing: "easeOutExpo",
					duration: 500,
					delay: function(el, i) {
					  return 50 * (i)
					}
				}).add({
					targets: ".menu-option li",
					scale: [0,1],
					easing: "easeOutExpo",
					duration: 300,
					delay: function(el, i) {
					  return 50 * (i);
					}
				})
		} else{
			var ani = anime.timeline({loop : false})
				.add({
					targets : ".nav-menu",
					opacity : [1, 0],
					duration: 100,
					easing: "easeOutExpo"
				}).add({
					targets	: ".nav-background",
					easing: "easeInQuad",
					duration: 500,
					scale : [3, 1],
					borderRadius : [0, "100%"],
					background : ["#FEFDFE", "#EDECED"],
				})
			
			$(".nav-menu").addClass("unvalid");
			$("html, body").off("scroll touchmove mousewheel");
			$("html, body").removeClass("scroll-no");
		}
	});
})();
</script>
<div class="nav-background"></div>
<div class="nav-menu unvalid">
	<div class="menu-header">
		<h1 class="ml1">
			<span class="text-wrapper">
				<span class="letters">Menu</span>
			</span>
		</h1>
	</div>
	<ul class="menu-option">
		<li class="btn"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/">Home</a></li>
		<li class="btn"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">Introduce</a></li>
		<li class="btn"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/item">Projects</a></li>
		<!--<li class="btn"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/board">Board</a></li>-->
		<li class="btn"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">Photo</a></li>
		<li class="btn"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">Github</a></li>
		<li class="btn"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">Instagram</a></li>
	</ul>
</div>
<div class="progress-bar display-none">
	<div class="spinner">
	  <div class="double-bounce1"></div>
	  <div class="double-bounce2"></div>
	</div>
</div>
<script>
var Progress ={
	start : function (){
		$(".progress-bar").removeClass("display-none");
	},
	stop : function(){
		$(".progress-bar").addClass("display-none");
	}
}
</script>
