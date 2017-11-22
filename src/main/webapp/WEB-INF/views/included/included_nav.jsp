<style>

.nav{
	position: fixed;
	top : 0px;
	left: 0;
	right: 0;
	padding : 1% 2%;
	z-index : 1000;
	display: flex;
	justify-content : space-between;
	background: #FFF;
	border-bottom: 1px solid #EEE;
}

/*
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

*/
.menu {
	cursor: pointer;
}

.menu img{
	width : 25px;
	height: 25px;
	opacity: 0.5;
}

.menu:hover img, .menu.hover img{
	opacity: 1;
}

.menu-tooltip{
	background: #000;
	color : #EEE;
	box-shadow: 0 0 0px black;
}

.menu-tooltip.hover{
	display: inherit;
	animation : fadein 2s;
}

.menu.git-icon{
	margin-right: 10px;
}

.menu.home-icon{
	margin-right: 10px;
}

@media (max-width: 800px){
	/*
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
	*/
	.menu img{
		width : 40px;
		height: 40px;
	}
}

</style>
<div class="nav">
	
	<!--
	<div class="nav-icon">
		<span></span>
		<span></span>
		<span></span>
		<span></span>
	</div>
	<script>
	(function(){
		$('.nav-icon').click(function(){
			$(this).toggleClass("open");
		});
		
		$('.nav-icon').hover(function(){
			$(this).find('span').css("background","#000");
		}, function(){
			$(this).find('span').css("background","#999");
		});
		
	})();
	</script>
	 -->
	<div class="main-menus">
		<span class="menu home-icon"> 
			<a href="${pageContext.request.contextPath}"> 
				<img src="${pageContext.request.contextPath}/resources/image/btn_home.png" />
			</a>
		</span>
		
		<span class="menu myinfo-icon">
			<a href="${pageContext.request.contextPath}/myinfo"> 
				<img src="${pageContext.request.contextPath}/resources/image/btn_myinfo.png" />
			</a>
		</span>  
	</div>

	<div class="sub-menus">
		<span class="menu git-icon"> 
			<a target="_blank" href="https://github.com/cgLee079"> 
				<img src="${pageContext.request.contextPath}/resources/image/btn_github.png" />
			</a>
		</span> 
		
		<span class="menu insta-icon">
			<a target="_blank" href="https://www.instagram.com/cglee079"> 
				<img src="${pageContext.request.contextPath}/resources/image/btn_insta.png" />
			</a>
		</span>
	</div>
	<script>
		(function(){
			var subMenus = $(".sub-menus .menu");
			
			subMenus.each(function(){
				var tg 		= $(this);
				var title 	= tg.find("a").attr("href");
				var option	= {
					tooltipClass: "menu-tooltip",
					delay: 0,
					show : false,
					hide : false,
					position: { 
						 my: "right+20 top",
						 at: "center bottom+10",
						 collision : "none"
					}
				};
				
				tg.attr("title", title);
				tg.tooltip(option);
			});
		})();
	</script>
</div>

