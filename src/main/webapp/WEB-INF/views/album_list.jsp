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
.wrapper{
	width: 80%;
	margin : 0px auto;
}

.wrap-photo-view{
	width : 100%;
	margin-top: 70px;
}

.photo-view{
	width: 80%;
	height: 400px;
	margin: 0px auto;
	background: #EEE;
}

.wrap-photo-list{
	display : flex;
	height: 100px;
	width : 100%;
	margin-top: 30px;
	
	-ms-flex-align: center;
	-webkit-align-items: center;
	-webkit-box-align: center;

	align-items: center;
}

.photo-list{
	flex : 8;
	width: 80%;
	height: 100%;
	background: #EEE;
	position: relative;
	overflow: hidden;
	margin : 0px auto;
}

.btn-left-list{
	width: 20px;
	height: 20px;
	flex : 1;
}

.btn-right-list{
	width: 20px;
	height: 20px;
	flex : 1;
}

.btn{
	cursor: pointer;
	opacity: 0.5;
	
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: contain;
}

.btn:hover{
	opacity: 1;
}

.h-reverse{
	-moz-transform: scaleX(-1);
	-o-transform: scaleX(-1);
	-webkit-transform: scaleX(-1);
	transform: scaleX(-1);
	filter: FlipH;
	-ms-filter: "FlipH";
}

.photo-item{
	position : absolute;
	height : 100%;
	background : #AAA;
}

@media (max-width: 800px){
	.wrap-photo-view{
		margin-top: 400px;
	}
}
</style>
<script>
	$(document).ready(function(){
		$(".btn-right-list").on("click", function(){
			var photoList = $(".photo-list");
			var photoItems = $(".photo-list > .photo-item");
			var wrapWidth = parseInt(photoList.css("width"));
			var length = photoItems.length;
			
			var lastTg 		= photoItems.eq(length - 1);
			var lastLeft 	= parseInt(lastTg.css("left"));
			var lastToLeft 	= lastLeft - wrapWidth;
			var lastToRight = lastLeft + parseInt(lastTg.css("width"));
			
			if(!(lastToRight <= wrapWidth)){
				for(var i = 0; i < length; i++){
					var tg = photoItems.eq(i);
					var width 	= parseInt(tg.css("width"));
					var left 	= parseInt(tg.css("left"));
					var toLeft 	= left - wrapWidth;
					tg.css({"left" : left}).stop().animate({"left" : toLeft});
				}
			}
			
		});
		
		$(".btn-left-list").on("click", function(){
			var photoList = $(".photo-list");
			var photoItems = $(".photo-list > .photo-item");
			var wrapWidth = parseInt(photoList.css("width"));
			var length = photoItems.length;
			
			var overLeft = false;
			var firstTg 	= photoItems.eq(0);
			var firstLeft 	= parseInt(firstTg.css("left"));
			var firstToLeft = firstLeft + wrapWidth;
			if(firstToLeft > 0){
				overLeft = true;
			}
			
			for(var i = 0; i < length; i++){
				var tg = photoItems.eq(i);
				var width 	= parseInt(tg.css("width"));
				var left 	= parseInt(tg.css("left"));
				var toLeft 	= left + wrapWidth;
				
				if(overLeft){
					tg.css("left", (width * i) + (5 * i));
				} else {				
					tg.css({"left" : left}).stop().animate({"left" : toLeft});
				}
			}
		});

	});
</script>
</head>
<body>
<div class="wrapper">
	<c:import url="included/included_nav.jsp" charEncoding="UTF-8">
	</c:import>
	ddddddddddddddddddd
	<div class="wrap-photo-view">
		<div class="photo-view"></div>
		<div class="wrap-photo-list">
			<div class="btn btn-left-list h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_arrow.png)"></div>
			<div class="photo-list">
				<div class="btn photo-item">0</div>
				<div class="btn photo-item">1</div>
				<div class="btn photo-item">2</div>
				<div class="btn photo-item">3</div>
				<div class="btn photo-item">4</div>
				<div class="btn photo-item">5</div>
				<div class="btn photo-item">6</div>
				<div class="btn photo-item">7</div>
				<div class="btn photo-item">8</div>
				<div class="btn photo-item">9</div>
				<div class="btn photo-item">10</div>
				<div class="btn photo-item">11</div>
				<div class="btn photo-item">12</div>
				<div class="btn photo-item">13</div>
			</div>
			<script>
			(function(){
				var photoItems = $(".photo-item");
				var length = photoItems.length;
				
				photoItems.each(function(){
					var tg = $(this);
					var height 	= parseInt(tg.css("height"));
					var width	= height * (4/3);
					tg.css("width", width);
				});
				
				for(var i = 0; i < length; i++){
					var tg = photoItems.eq(i);
					var width = parseInt(tg.css("width"));
					tg.css("left", (width * i) + (5 * i));
				}
				
			})();
			</script>
			<div class="btn btn-right-list" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_arrow.png)"></div>
	
		</div>
	</div>
	
	<c:import url="included/included_footer.jsp" charEncoding="UTF-8">
	</c:import>
	
</div>

</body>
</html>