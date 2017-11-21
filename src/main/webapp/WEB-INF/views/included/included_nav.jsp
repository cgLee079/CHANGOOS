<style>

.nav{
	position: fixed;
	left: 2%;
	right: 2%;
	top : 20px;
	z-index : 1000;
	display: flex;
	justify-content : space-between;
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

.menu.myinfo-icon{
	margin-right: 10px;
}

.wrap-myinfo {
	position : fixed;
	overflow : hidden;
	background : #FFF;
	top : 50%;
	left : 50%;
	bottom : 50%;
	right : 50%;
	opacity : 0.5;
	z-index: 100;
}

.myinfo-views{
	position : relative;
	margin-top: 70px;
	width: 100%;
	height: 500px;
}

.myinfo-view {
	display : flex;
	flex-flow: row wrap;
	position: absolute;
	width : 100%;
	height: 100%;
}

.myinfo-view00 {left: 0%; background: #FFF;}
.myinfo-view01 {left: 100%; background: #333;}
.myinfo-view02 {left: 100%; background: #666;}
.myinfo-view03 {left: 100%; background: #999;}

.content-picture{
	flex : 1 50%;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: cover;
}

.content-text{
	flex : 1 40%;
	padding : 30px;
}

.btns-view{
	width : 100%;
	text-align: center;
	margin-top : 30px;
}

.btn-view{
	display : inline-block;
	width : 20px;
	height: 20px;
	border-radius : 10px;
	background : #CCC;
	margin : 20px 10px 0px 10px;
	cursor : pointer;
}

.btn-view.on{
	background : #000;
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
	
	.myinfo-views{
		margin-top: 20%;
		height: 1000px;
	}
	
	.myinfo-view{
		flex-flow: column wrap;
	}
	
	.btns-view{
		margin-top : 100px;
	}
}

</style>

<script>
var setIntervalId;
var openAni;
function openMyInfo(){
	var wrap	= $(".myinfo-views");
	var views	= $(".myinfo-views > .myinfo-view");
	var btns 	= $(".btns-view > .btn-view");
	var current = 0 ;
	
	views.css({left : "-100%"});
	views.eq(0).css({left : "0"});
	btns.removeClass("on");
	btns.eq(0).addClass("on");
	
	btns.on("click", function(){
		var tg = $(this);
		var index = tg.index();
		
		btns.removeClass("on");
		tg.addClass("on");
		
		move(index);
	});
	
	function move(index){
		var currentEl =  views.eq(current);
		var nextEl = views.eq(index);
		
		currentEl.css({left : "0"}).stop().animate({left : "-100%"});
		nextEl.css({left : "100%"}).stop().animate({left : "0%"});
		
		current = index;
	}
	
	function timer(){
		setIntervalId = setInterval(function(){
			var n = current + 1;
			if(n === views.length){
				n = 0;
			}
			btns.eq(n).trigger("click");
		}, 7000);
	}
	timer();
	
	openAni = anime({
		  targets: '.wrap-myinfo',
		  easing : 'easeInOutQuad',
		  bottom : 0,
		  left   : 0,
		  top 	 : 0,
		  right  : 0,
		  opacity : 1,
		  duration : 500
	});
}

function closeMyInfo(){
	clearInterval(setIntervalId);
	openAni.play();
	openAni.reverse();
}
</script>
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
		<span class="menu git-icon"> 
			<a href="${pageContext.request.contextPath}"> 
				<img src="${pageContext.request.contextPath}/resources/image/icon_home.png" />
			</a>
		</span> 
	</div>

	<div class="sub-menus">
		<span class="menu myinfo-icon">
			<a> 
				<img src="${pageContext.request.contextPath}/resources/image/icon_myinfo.png" />
			</a>
		</span> 
		<script>
			(function(){
				$(".myinfo-icon").on("click", function(){
					var tg = $(this);
					tg.toggleClass("open");
					if(tg.hasClass("open")){
						openMyInfo();
					} else{
						closeMyInfo();
					}
				});
			})();
		</script>
		
		<span class="menu git-icon"> 
			<a target="_blank" href="https://github.com/cgLee079"> 
				<img src="${pageContext.request.contextPath}/resources/image/icon_github.png" />
			</a>
		</span> 
		
		<span class="menu insta-icon">
			<a target="_blank" href="https://www.instagram.com/cglee079"> 
				<img src="${pageContext.request.contextPath}/resources/image/icon_insta.png" />
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
			       	show	: "fadeIn",
				};
				
				tg.attr("title", title);
				tg.tooltip(option);
			});
		})();
	</script>
</div>

<div class="wrap-myinfo slider closed">
	<div class="myinfo-views">
		<div class="myinfo-view myinfo-view00">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_sample1.jpeg)"></div>
			<div class="content-text">
				<h1 class="ml6">
					<span class="text-wrapper">
					<span class="letters">Who am I?</span>
					</span>
				</h1>
			</div>
		</div>
		<div class="myinfo-view myinfo-view01"></div>
		<div class="myinfo-view myinfo-view02"></div>
		<div class="myinfo-view myinfo-view03"></div>
	</div>
	
	<div class="btns-view">
		<div class="btn-view btn-view00 on"></div>
		<div class="btn-view btn-view01"></div>
		<div class="btn-view btn-view02"></div>
		<div class="btn-view btn-view03"></div>
	</div>
</div>