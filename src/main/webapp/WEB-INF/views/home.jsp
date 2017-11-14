<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<title>Home</title>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/anime2.2.0.js"></script>


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
	padding : 10px 0px;
	background : #FFF;
	top: 0px;
	left: 0px;
	width: 100%;
}

.nav{
	margin : 3%;
}
.main{
	width: 80%;
	margin: 0px auto;
}

.main-introduce{
	max-width: 700px;
	margin : 0px auto;
	margin-top: 200px; 
	text-align: center;
} 

.main-items{
	margin-top: 200px;
	display: flex;
	flex-flow: row wrap;
} 

.item{
	flex : 1 20%;
	background: rgba(100, 100, 100, 0.1);
	height: 300px;
	margin: 1px;
}

@media (max-width: 800px){
	.item{
		flex : 1 100%;
	}
}


.item-desc{
	position: relative;
	height: 100%;
	width : 100%;
	opacity: 0;
}

.item-desc.fade-in{
	display : inherit;
	animation: fadein 1s;
}

.item-desc.fade-out{
	display : inherit;
	animation: fadeout 0.5s;
}

@keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}

@keyframes fadeout {
    from { opacity: 1; }
    to   { opacity: 0; }
}

.item-desc a{
	position: absolute;
	bottom: 0px;
	padding : 20px;
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
	float: left;
	width: 30px;
	height: 20px;
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
  height: 6px;
  width: 100%;
  background: #DDD;
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
  top: 10px;
}

.nav-icon span:nth-child(4) {
  top: 20px;
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

.sub-icons{
	float : right;
}

.icon {
	cursor: pointer;
}

.icon img{
	width : 30px;
	height: 30px;
	opacity: 0.5;
}

.icon:HOVER img{
	opacity: 1;
}

.git-icon{
	margin-right: 10px;
}
</style>

<script>
$(document).ready(function(){
	$('.nav-icon').click(function(){
		$(this).toggleClass('open');
	});
	
	$('.nav-icon').hover(function(){
		$(this).find('span').css("background","#000");
	}, function(){
		$(this).find('span').css("background","#DDD");
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
			
			<div class="sub-icons">
				<span class="icon git-icon">
					<a target="_blank" href="https://github.com/cgLee079">
						<img src="${pageContext.request.contextPath}/resources/image/icon_github.png" />
					</a>
				</span>
				<span class="icon insta-icon">
					<a href="https://github.com/cgLee079">
						<img src="${pageContext.request.contextPath}/resources/image/icon_insta.png" />
					</a>
				</span>
			</div>
		</div>
	</div>
	<div class="main">
		<div class="main-introduce">
			<h1 class="ml9">
			  <span class="text-wrapper">
			    <span class="letters">Changoo Lee</span>
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
				Hello! Thank you for visiting my photofolio site.<br>
				My name is changoo and specialized computer engineering in Hansung Univ.<br>
				I'm interested in Android development.<br>
			</a>
		</div>
		<div class="main-items">
			<div class="item">
				<div class="item-desc">
				<a>
				is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text e
				</a>
				</div>
			</div>
			<div class="item">
				<div class="item-desc">
				<a>
				is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text e
				</a>
				</div>
			</div>
			<div class="item">
				<div class="item-desc">
				<a>
				is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text e
				</a>
				</div>
			</div>
			<div class="item">
				<div class="item-desc">
				<a>
				is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text e
				</a>
				</div>
			</div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			<div class="item"></div>
			
			
			<script>
			(function(){
				$(".item").bind("mouseover", function(){
					var tg = $(this);
					tg.find(".item-desc").addClass("fade-in");
					tg.find(".item-desc").removeClass("fade-out");
				});
				
				$(".item").bind("mouseout", function(){
					var tg = $(this);
					tg.find(".item-desc").removeClass("fade-in");
					tg.find(".item-desc").addClass("fade-out");
				});
					
			})();
			</script>
		</div>
		
	</div>
</div>
</body>
</html>
