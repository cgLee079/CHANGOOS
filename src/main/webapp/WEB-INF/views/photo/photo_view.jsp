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
	height: 500px;
	margin: 0px auto;
}

.photo-view > .photo-img{
	width : 100%;
	height: 75%;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: contain;
}

.photo-view > .photo-detail{
	padding : 10px;
	width: 100%;
	height : 20%;
}

.photo-view > .photo-detail > .photo-info{
	display: flex;
	justify-content: space-between;
}

.photo-view > .photo-detail > .photo-info > .photo-name{
	color: #000;
	font-weight: bold;
	font-style: oblique;
}

.photo-view > .photo-detail > .photo-info > .photo-date-loc{
	color: #555;
	font-size: 12px;
}

.photo-view > .photo-detail > .photo-desc{
	margin-top: 5px;
	width : 100%;
	height: 80%;
	overflow : hidden;
	color : #555;
	font-size: 13px;
	word-break : break-all;
}

.photo-view > .photo-detail > .photo-people-tag{
	margin-top: 5px;
	color : #33F;
	font-size: 10px;
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
	position: relative;
	overflow: hidden;
	margin : 0px auto;
}

.btn-left-list{
	flex : 1;
	width: 20px;
	height: 20px;
	
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: contain;
}

.btn-right-list{
	flex : 1;
	width: 20px;
	height: 20px;
	
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: contain;
}

.btn{
	cursor: pointer;
	opacity: 0.5;
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
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: contain;
}

@media (max-width: 800px){
	.wrap-photo-view{
		margin-top: 300px;
	}
	
	.wrap-photo-list{
		margin-top: 100px;
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
	
	function showPhoto(seq){
		$.ajax({
			type	: "POST",
			url 	: "${pageContext.request.contextPath}" + "/photo/view.do",
			dataType: "JSON",
			data 	: {
				"seq" : seq
			},
			success : function(photo) {
				$(".photo-img").css("background-image", 
						"url('${pageContext.request.contextPath}" + photo.image +"')");
				
				var begin = anime({
					  targets	: ".photo-img",
					  duration	: 1000,
					  opacity	: 1,
					  begin: function(anim) {
					   	var tg = $(".photo-img");
					   	tg.css("opacity", 0);
					  }
				});
				
				$(".photo-name").html(photo.name);
				$(".photo-date-loc").html(photo.date + "  " + photo.location);
				$(".photo-desc").html(photo.desc);
				$(".photo-people-tag").html(photo.people + "  " + photo.tag);
			}
			
		});
	}
	
</script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8">
	</c:import>
	<div class="wrap-photo-view">
		<div class="photo-view">
			<div class="photo-img"></div>
			<div class="photo-detail">
				<div class="photo-info">
					<div class="photo-name"></div>
					<div class="photo-date-loc"></div>
				</div>
				
				<div class="photo-people-tag"></div>
				<div class="photo-desc"></div>
				
			</div>
		</div>
		<div class="wrap-photo-list">
			<div class="btn btn-left-list h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_arrow.png)">
			</div>
			
			<div class="photo-list">
				<c:forEach var="photo" items="${photos}">
				<div class="btn photo-item" onclick="showPhoto('${photo.seq}')"  
					style="background-image: url('${pageContext.request.contextPath}${photo.image}')">
				</div>
			</c:forEach>
				
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
			<div class="btn btn-right-list"  style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_arrow.png)">
			</div>
	
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
	</c:import>
	
</div>

</body>
</html>