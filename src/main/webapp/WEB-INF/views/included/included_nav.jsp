<style>
.nav-occupy{
	position: fixed;
	top : 0px;
	width : 100%;
	height : 2.5rem;
	z-index : 2000;
	display: -ms-flexbox;
	display: -webkit-flex;
	display: flex;
	-ms-flex-align: center;
	-webkit-align-items: center;
	-webkit-box-align: center;
	align-items: center;
	justify-content : space-between;
	background: #FFF;
	border-bottom: 1px solid #ddd;
}

.nav-icon{
	width: 25px;
	height: 15px;
	position: fixed;
	top : 1rem;
	left: 1rem;
	z-index :10000;
	-webkit-transform: rotate(0deg);
	-moz-transform: rotate(0deg);
	-o-transform: rotate(0deg);
	transform: rotate(0deg);
	-webkit-transition: .5s ease-in-out;
	-moz-transition: .5s ease-in-out;
	-o-transition: .5s ease-in-out;
	transition: .5s ease-in-out;
	cursor: pointer;
}

.nav-icon span{
  display: block;
  position: absolute;
  height: 0.1rem;
  width: 100%;
  background: #999;
  border-radius: 9px;
  opacity: 1;
  left: 0;
  -webkit-transform: rotate(0deg);
  -moz-transform: rotate(0deg);
  -o-transform: rotate(0deg);
  transform: rotate(0deg);
  -webkit-transition: .25s ease-in-out;
  -moz-transition: .25s ease-in-out;
  -o-transition: .25s ease-in-out;
  transition: .25s ease-in-out;
}

.nav-icon:hover span{
	background : #000;
}

.nav-icon span:nth-child(1) {
  top: 0px;
}

.nav-icon span:nth-child(2),.nav-icon span:nth-child(3) {
  top: 0.3rem;
}

.nav-icon span:nth-child(4) {
  top: 0.6rem;
}

.nav-icon.open span:nth-child(1) {
  top: 0.5rem;
  width: 0%;
  left: 50%;
}

.nav-icon.open span:nth-child(2) {
  -webkit-transform: rotate(45deg);
  -moz-transform: rotate(45deg);
  -o-transform: rotate(45deg);
  transform: rotate(45deg);
}

.nav-icon.open span:nth-child(3) {
  -webkit-transform: rotate(-45deg);
  -moz-transform: rotate(-45deg);
  -o-transform: rotate(-45deg);
  transform: rotate(-45deg);
}

.nav-icon.open span:nth-child(4) {
  top: 10px;
  width: 0%;
  left: 50%;
}

.nav-background{
	position: fixed;
	width : 100%;
	height : 100%;
	z-index: 3000;
	pointer-events: none;
}

.nav-menu{
	position: fixed;
	width : 100%;
	height: 100%;
	top : 0;
	z-index: 3000;
	text-align: center;
	opacity: 0;
}

.nav-menu.unvalid{
	pointer-events: none;	
}

.menu-header{
	margin:  0.5rem;
	margin-top : 5rem;
}

.menu-option{
	font-size: 1.6rem;
	line-height: 2.5rem;
}

.menu-option li{
	list-style-type: none;
}

.scroll-no{
	overflow: hidden;
}

@media (max-width: 420px){
	.nav-occupy{
		height : 4rem;
	}
	
	.nav-icon{
		width: 20px;
		height: 15px;
		top : 1.5rem;
		left: 1.5rem;
	}	
	
	.nav-icon span{
	  height: 0.1rem;
	  width: 100%;
	}
	
	.nav-icon span:nth-child(1) {
	  top: 0;
	}
	
	.nav-icon span:nth-child(2),.nav-icon span:nth-child(3) {
	  top: 0.6rem;
	}
	
	.nav-icon span:nth-child(4) {
	  top: 1.2rem;
	}
	
	.menu-header{
		margin-top:  11rem;
	}
	
}
</style>
<div class="nav-occupy"></div>
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
		
		console.log(width +" x " + height + " => " + radius);
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
				<span class="line line1"></span>
				<span class="letters">Menu</span>
				<span class="line line2"></span>
			</span>
		</h1>
	</div>
	<ul class="menu-option">
		<li class="btn"><a href="${pageContext.request.contextPath}">Home</a></li>
		<li class="btn"><a href="${pageContext.request.contextPath}/myinfo">Introduce</a></li>
		<li class="btn"><a href="${pageContext.request.contextPath}/photo">Photo</a></li>
		<li class="btn"><a target="_blank" href="https://github.com/cgLee079">Github</a></li>
		<li class="btn"><a target="_blank" href="https://www.instagram.com/cglee079">Instagram</a></li>
	</ul>
</div>
