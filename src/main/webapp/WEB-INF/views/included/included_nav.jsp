<style>
.nav{
	position: absolute;
	left: 2%;
	right: 2%;
	top : 20px;
	display: flex;
	justify-content : space-between;
}

/* Icon 3 */
.nav-icon{
	width: 20px;
	height: 10px;
	position: relative;
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
  height: 3px;
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

.nav-icon span:nth-child(1) {
  top: 0px;
}

.nav-icon span:nth-child(2),.nav-icon span:nth-child(3) {
  top: 6px;
}

.nav-icon span:nth-child(4) {
  top: 12px;
}

.nav-icon.open span:nth-child(1) {
  top: 10px;
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

.sub-menus{
}

.sub-menu {
	cursor: pointer;
}

.sub-menu img{
	width : 25px;
	height: 25px;
	opacity: 0.5;
}

.sub-menu:hover img, .sub-menu.hover img{
	opacity: 1;
}

.sub-menu-tooltip{
	background: #000;
	color : #EEE;
	box-shadow: 0 0 0px black;
}

.sub-menu-tooltip.hover{
	display: inherit;
	animation : fadein 2s;
}

.sub-menu.git-icon{
	margin-right: 10px;
}

@media (max-width: 1000px){
	.nav-icon{
		width: 30px;
		height: 30px;
	}	
	
	.nav-icon span{
	  height: 6px;
	  width: 100%;
	}
	
	.nav-icon span:nth-child(1) {
	  top: 0px;
	}
	
	.nav-icon span:nth-child(2),.nav-icon span:nth-child(3) {
	  top: 10px;
	}
	
	.nav-icon span:nth-child(4) {
	  top: 20px;
	}
	
	.sub-menu img{
		width : 40px;
		height: 40px;
	}
}


</style>
<div class="nav">

	<div class="nav-icon">
		<span></span> <span></span> <span></span> <span></span>
	</div>
	<script>
	(function(){
		$('.nav-icon').click(function(){
			$(this).toggleClass('open');
		});
		
		$('.nav-icon').hover(function(){
			$(this).find('span').css("background","#000");
		}, function(){
			$(this).find('span').css("background","#999");
		});
		
	})();
	</script>
	

	<div class="sub-menus">
		<span class="sub-menu git-icon"> <a target="_blank"
			href="https://github.com/cgLee079"> <img
				src="${pageContext.request.contextPath}/resources/image/icon_github.png" />
		</a>
		</span> <span class="sub-menu insta-icon"> <a target="_blank"
			href="https://www.instagram.com/cglee079"> <img
				src="${pageContext.request.contextPath}/resources/image/icon_insta.png" />
		</a>
		</span>
	</div>
	<script>
		(function(){
			var subMenus = $(".sub-menu");
			
			subMenus.bind("touchstart", function(){
				$(this).addClass("hover");
			});
			
			subMenus.bind("touchend", function(){
				$(this).removeClass("hover");
			});
			
			subMenus.each(function(){
				var tg 		= $(this);
				var title 	= tg.find("a").attr("href");
				var option	= {
					tooltipClass: "sub-menu-tooltip",
			       	show	: "fadeIn",
				};
				tg.attr("title", title);
				
				tg.tooltip(option);
				tg.bind("tocuchstart", function(){
					$(this).tooltip(option);
					$(this).tooltip("open");
				});
				tg.bind("touchend", function(){
					$(this).tooltip("close");
				})
			});
		})();
	
	</script>
</div>