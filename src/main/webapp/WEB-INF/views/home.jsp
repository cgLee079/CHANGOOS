<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<title>Home</title>
<link href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" rel="stylesheet"></link>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/anime2.2.0.js"></script>

<style>
@keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}

@keyframes fadeout {
    from { opacity: 1; }
    to   { opacity: 0; }
}
</style>
<style>
*{
	margin	: 0px;
	padding	: 0px;
}

a, a:VISITED{
	text-decoration: none;
}

.header{
	position: fixed;
	top: 0px;
	left: 0px;
	width: 100%;
	z-index: 10;
}

.nav{
	margin : 20px 2% 10px 2%;
	display: flex;
	justify-content : space-between;
}

.main{
	width: 80%;
	margin: 0px auto;
}

.main-introduce{
	max-width: 700px;
	margin : 0px auto;
	margin-top: 100px; 
	text-align: center;
} 

.me-icon{
	width : 50%;
	height: 300px;
	margin: 20px auto;
	
	background-image	: url(resources/image/icon_me.png);
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: contain;
    
}

.ml9 {
  position: relative;
  font-weight: 200;
  font-size: 4em;
}

.ml9 .text-wrapper {
  position: relative;
  display: inline-block;
  padding-top: 0.2em;
  padding-right: 0.05em;
  padding-bottom: 0.1em;
  overflow: hidden;
}

.ml9 .letter {
  transform-origin: 50% 100%;
  display: inline-block;
  line-height: 1em;
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

.main-items{
	margin-top: 200px;
	display: flex;
	flex-flow: row wrap;
	align-items: flex-start;
} 

.item{
	flex : 1 33%;
	height : 300px;
	margin : 1px;
	position: relative;
	cursor: pointer;
}

.item-desc{
	position: relative;
	height: 100%;
	width : 100%;
	opacity: 0;
}

.item-desc.fade-in{
	animation: fadein 1s 1;
	animation-fill-mode: forwards;
}

.item-desc.fade-out{
	animation: fadeout 0.5s 1;
	animation-fill-mode: forwards;
}

.item-desc a{
	position: absolute;
	bottom: 0px;
	padding : 20px;
	background: rgba(0, 0, 0, 1);
	color: #EEE;
	font-size: 10px;
}

.item-bg{
    background-size: cover;
	position: absolute;
	top 	: 0px;
	bottom 	: 0px;
	left 	: 0px;
	right 	: 0px;
	opacity : 0.6;
}


@media (max-width: 1000px){
	.item{
		flex : 1 100%;
	}
	
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

.display-show{
	display: inherit;
}

.display-none{
	display: none;
}

.footer{
	width : 100%;
	height: 300px;
}
</style>

<script>
const AT_UNSHOW_INTRO = 600; 

$(document).ready(function(){
	$('.nav-icon').click(function(){
		$(this).toggleClass('open');
	});
	
	$('.nav-icon').hover(function(){
		$(this).find('span').css("background","#000");
	}, function(){
		$(this).find('span').css("background","#999");
	});
	
});

</script>
</head>
<body>
<div class="wrapper">
	<div class="header">
		<div class="nav">
				
			<div class="nav-icon">
			  <span></span>
			  <span></span>
			  <span></span>
			  <span></span>
			</div>
			
			<div class="sub-menus">
				<span class="sub-menu git-icon">
					<a target="_blank" href="https://github.com/cgLee079">
						<img src="${pageContext.request.contextPath}/resources/image/icon_github.png" />
					</a>
				</span>
				<span class="sub-menu insta-icon">
					<a target="_blank" href="https://www.instagram.com/cglee079">
						<img src="${pageContext.request.contextPath}/resources/image/icon_insta.png" />
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
		 
	</div>
		
	<div class="main">
		<div class="main-introduce">
			<div class="me-icon">
			</div>
		
			<h1 class="ml9">
			  <span class="text-wrapper">
			    <span class="letters">Toyo dim</span>
			  </span>
			</h1>
			<script>
			(function(){
				$('.ml9 .letters').each(function(){
					  $(this).html($(this).text().replace(/([^\x00-\x80]|\w)/g, "<span class='letter'>$&</span>"));
				});
				
				anime.timeline({loop: true})
				  .add({
				    targets: '.ml9 .letter',
				    scale: [0, 1],
				    duration: 1500,
				    elasticity: 600,
				    delay: function(el, i) {
				      return 45 * (i+1)
				    }
				  }).add({
				    targets: '.ml9',
				    opacity: 0,
				    duration: 1000,
				    easing: "easeOutExpo",
				    delay: 1000
				  });
			})();
			</script>
			<a>
				Hello! Thank you for visiting my portfolio site. <br>
				I specialized computer engineering at Hansung University  <br>
				and interested in Android App, Spring framework. <br>
 				<br>
				below  <br>

			</a>
		</div>
		
		<div class="main-items">
			<c:forEach var="item" items="${items}">
				<div class="item">
					<div class="item-bg" style="background-image: url('${item.snapsht}')"></div>
					<div class="item-desc">
					
					<a>
						${item.name}</br>
						${item.desc}
					</a>
					</div>
				</div>
			</c:forEach>
			
			<script>
			(function(){
				$(".item").bind("mouseover", function(){
					var tg = $(this);
					tg.css("opacity","1");
					tg.find(".item-desc").addClass("fade-in");
					tg.find(".item-desc").removeClass("fade-out");
				});
				
				$(".item").bind("mouseout", function(){
					var tg = $(this);
					tg.css("opacity","0.5");
					tg.find(".item-desc").removeClass("fade-in");
					tg.find(".item-desc").addClass("fade-out");
				});
			})();
			</script>
		</div>
		
	</div>
	
	<div class="footer">
		
	</div>
</div>
</body>
</html>
