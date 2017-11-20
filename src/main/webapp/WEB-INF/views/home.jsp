<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/global.css" />
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
.wrapper{
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


.main-items-title {
	margin-top: 200px;
}

.main-items-title h1{
	font-size: 50px;
}

.main-items{
	display: flex;
	flex-flow: row wrap;
	align-items: flex-start;
}   

.item{
	flex : 1 33%;
	height : 300px;
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
	left : 0px;
	right : 0px;
	word-break : break-all;
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

function itemView(seq){
	var contextPath = "${pageContext.request.contextPath}";
	window.location.href = contextPath + "/item/view?seq=" + seq;		
}

function scrollToItems(){
	scroll(0, 700);
}

</script>
</head>
<body>

	
<div class="wrapper">
	<c:import url="included/included_nav.jsp" charEncoding="UTF-8">
	</c:import>

	<!--  -->
	<style>
		.wrap-my-info{
			position: fixed;
			top: 0px;
			bottom: 0px;
			right : 0px;
			left : 0px;
		}
		
		.bg-cover{
			position : absolute;
			width : 100%;
			height: 100%;
			z-index: 20;
			background: rgba(0, 0, 0, 0.5);
		}
		
		.bg-my-info{
			display: flex;
			flex-flow: column wrap;
		}
		
		.bg{
			flex : 1 33%;
			z-index: 10;
			background-position	: center;
   	 		background-repeat	: no-repeat;
   			background-size		: cover;
		}
		
		.bg-01{background-image: url('${pageContext.request.contextPath}/resources/image/bg_sample1.jpg')}
		.bg-02{background-image: url('${pageContext.request.contextPath}/resources/image/bg_sample2.jpg')}
		.bg-03{background-image: url('${pageContext.request.contextPath}/resources/image/bg_sample3.jpg')}
		
		.my-info{
			position : absolute;
			top : 50%;
			z-index : 30;
			color: #FFF;
			left : 50%;
		}
	</style>
	<div class="wrap-my-info">
		<div class="bg-cover"></div>
		<div class="bg-my-info">
			<div class="bg bg-01"></div>
			<div class="bg bg-02"></div>
			<div class="bg bg-03"></div>
		</div>
		<div class="my-info">
			<br/>
			2011.03	한성대 멀티미디어공학과 입학 <br/>
			2016.07	넥스젠어쏘시에이트(주)입사 <br/>
			2017.02	한성대 컴퓨터공학부 졸업  <br/>
		</div>
	</div>
	<!--  -->
	
	<div class="main display-none">
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
			
			Hello! Thank you for visiting my portfolio site. <br/>
			I specialized Computer Engineering at Hansung University.<br/>
			and interested in Android, Web development. <br/>
			If you want to see my projects, show below.<br/>
			<br/>
			<a href="#items" onclick="scrollToItems()">going on</a>
		</div>
		
		<div class="main-items-title">
			<h1>Projects</h1>
		</div>
		<div class="main-items">
			<c:forEach var="item" items="${items}">
				<div onclick="itemView(${item.seq})" class="item">
					<div class="item-bg" style="background-image: url('${pageContext.request.contextPath}${item.snapsht}')"></div>
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
	
	<c:import url="included/included_footer.jsp" charEncoding="UTF-8">
	</c:import>
</div>
</body>
</html>
