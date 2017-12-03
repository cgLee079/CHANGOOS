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
	overflow: hidden;
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
	padding : 3.5rem;
	line-height: 2rem;
}

.content-text > .content-head{
	margin-bottom: 1rem;
}

.btns-view{
	width : 100%;
	text-align: center;
	margin-top : 2rem;
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
	}
	
	.btn-view{
		width : 10px;
		height: 10px;
	}
}
</style>
<script>
	$(document).ready(function(){
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
		var setIntervalId;
		var openAni;
		
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
		
		if(!isMobile){
			timer();	
		}
		
		
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
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_myinfo_view00.jpg)"></div>
			<div class="content-text">
				<h1 class="content-head">Who am I?</h1>
				
				<p>
				Hello? My name is <strong>changoo Lee</strong>.<br/> 
				I live in south korea and
					<strong>
					<jsp:useBean id="date" class="java.util.Date" />
					<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
					<c:out value="${currentYear - 1992 + 1}" />
					</strong>
				old. <br/>
				I specialized Computer Engineering at Hansung University. <br/>
				
				</p>
			</div>
		</div>
		
		<div class="myinfo-view myinfo-view01">
			<div class="content-text">
				 <h1 class="content-head">My History.</h1>
				 <p>
					 <strong>2011.03</strong> &nbsp&nbsp Admissions C.E at Hansug University. <br/>
					 <strong>2012.05</strong> &nbsp&nbsp Join to army. <br/>
					 <strong>2017.02</strong> &nbsp&nbsp Victory Graduate Competition <br/>
					 <strong>2017.07</strong> &nbsp&nbsp Intern at Nexgen Associate (Web, SI) <br/>
					 <strong>2018.02</strong> &nbsp&nbsp Graduated from university <br/>
				 </p>
			</div>
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_myinfo_view01.jpg)"></div>
		</div>
		
		<div class="myinfo-view myinfo-view02">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_myinfo_view02.jpg)"></div>
			<div class="content-text">
				<h1 class="content-head">My Resume.</h1>
				Bachelor's degree at Hausng University. <br/>
				Grade &nbsp&nbsp<strong>4.22 / 4.5</strong> <br/>
				Toeic &nbsp&nbsp&nbsp<strong>855</strong> <br/>
				Data Processing Engineer, <br/>
				Craftsman Information Equipment Operation,<br/>
				MS master, GTQ
			</div>
		</div>
		
		<div class="myinfo-view myinfo-view03">
			<div class="content-text">
				<h1 class="content-head">CONTACT.</h1>
				<strong>Name.</strong> 이찬구(Changoo Lee) <br/>
				<strong>Email.</strong> cgLee079@gmail.com <br/>
				<strong>Tel.</strong> 010 - 2062 - 2979 <br/>
				<br/>
				<h3>Contact Me!!</h3>
			</div>
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/bg_myinfo_view03.jpg)"></div>
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


