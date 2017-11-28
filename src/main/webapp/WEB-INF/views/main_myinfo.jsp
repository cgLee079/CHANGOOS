<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
.myinfo-views{
	position : relative;
	margin-top: 4rem;
	width: 100%;
	height: 500px;
}

.myinfo-view {
	display : flex;
	background: #FFF;
	flex-direction : row;
	position: absolute;
	width : 100%;
	height: 100%;
	
	border-top : 1px solid #EEE;
	border-bottom : 1px solid #EEE;
}

.myinfo-view00 {left: 0%;}
.myinfo-view01 {left: 100%; }
.myinfo-view02 {left: 100%; }
.myinfo-view03 {left: 100%; }

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

@media (max-width: 420px){
	.myinfo-views{
		margin-top: 8rem;
		height: 450px;
	}
	
	.myinfo-view{
		flex-direction : column;
		height: 100%;
	}
	
	.content-picture{
		width : 100%;
	}
	
	.content-text{
		flex : 1 30%;
		line-height: 150%;
	}
	
	.btns-view{
		margin-top : 30px;
	}
	
	.btn-view{
		width : 10px;
		height: 10px;
	}
}
</style>
<script>
var setIntervalId;
var openAni;
$(document).ready(function(){
	//var viewport = $("#viewport");
	//var content = viewport.attr("content");
	//viewport.attr("content", content + ",  user-scalable=no");
	setSlideMyInfo();
	
	if(isMobile){
		$(".myinfo-view01 .content-text").insertAfter(".myinfo-view01 .content-picture");
		$(".myinfo-view03 .content-text").insertAfter(".myinfo-view03 .content-picture");
	}
});

function setSlideMyInfo(){
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
	
	$(".myinfo-views").touchwipe({
		wipeLeft: function() {
			var tg = $(".myinfo-views");
			var index 		= current;
			var toIndex 	= index + 1;
			var items 		= $(".myinfo-view");
			var itemLength 	= items.length;
			var btns 		= $(".btns-view > .btn-view");
			var currentEl	= undefined;
			var nextEl		= undefined;
			
			if(toIndex >= itemLength){ // 4 to 0
				toIndex = 0;
			}
			
			currentEl 	= items.eq(index);
			nextEl 		= items.eq(toIndex);
			  	 
			currentEl.css({left : "0"}).stop().animate({left : "-100%"});
		  	nextEl.css({left : "100%"}).stop().animate({left : "0%"});	
		  	
		  	btns.removeClass("on");
		  	btns.eq(toIndex).addClass("on");
		  	current = toIndex;
		},
		   
		wipeRight: function() {
			var tg = $(".myinfo-views");
			var index 		= current;
			var toIndex 	= index - 1;
			var items 		= $(".myinfo-view");
			var btns 		= $(".btns-view > .btn-view");
			var currentEl	= undefined;
			var nextEl		= undefined;
			if(toIndex < 0){ //  -1 to 3 
				toIndex = 3;
			} 
			
			currentEl 	= items.eq(index);
			nextEl 		= items.eq(toIndex);
			
			currentEl.css({left : "0"}).stop().animate({left : "100%"});
		  	nextEl.css({left : "-100%"}).stop().animate({left : "0%"});	
		  	
		  	btns.removeClass("on");
		  	btns.eq(toIndex).addClass("on");
			current = toIndex;
		},
		   
		min_move_x: 20,
		min_move_y: 20,
		preventDefaultEvents: true
	});
}
</script>
</head>

<body>
<div class="wrapper">
	<c:import url="included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="myinfo-views">
		<div class="myinfo-view myinfo-view00">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_sample1.jpeg)"></div>
			<div class="content-text">
				<h1 class="ml6">
					<span class="text-wrapper">
					<span class="letters">Who am I?</span>
					</span>
				</h1>
				<p>
				Hello? My name is <strong>changoo Lee</strong>.<br/> 
				I live in south korea and
					<jsp:useBean id="date" class="java.util.Date" />
					<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
					<c:out value="${currentYear - 1992 + 1}" />
				old. <br/>
				I specialized Computer Engineering at Hansung University. <br/>
				
				</p>
			</div>
		</div>
		
		<div class="myinfo-view myinfo-view01">
			<div class="content-text">
				<h1 class="ml6">
					<span class="text-wrapper">
					<span class="letters">Who am I?</span>
					</span>
				</h1>
			</div>
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_sample1.jpeg)"></div>
		</div>
		
		<div class="myinfo-view myinfo-view02">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_sample1.jpeg)"></div>
			<div class="content-text">
				<h1 class="ml6">
					<span class="text-wrapper">
					<span class="letters">Who am I?</span>
					</span>
				</h1>
			</div>
		</div>
		
		<div class="myinfo-view myinfo-view03">
			<div class="content-text">
				<h1 class="ml6">
					<span class="text-wrapper">
					<span class="letters">Who am I?</span>
					</span>
				</h1>
			</div>
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_sample1.jpeg)"></div>
		</div>
		
	</div>
	
	<div class="btns-view">
		<div class="btn-view btn-view00 on"></div>
		<div class="btn-view btn-view01"></div>
		<div class="btn-view btn-view02"></div>
		<div class="btn-view btn-view03"></div>
	</div>
	
	<c:import url="included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


