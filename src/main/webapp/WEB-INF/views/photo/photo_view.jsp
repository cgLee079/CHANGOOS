<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
.wrapper{
	width : 100%;
}

.wrap-photo-view{
	width : 100%;
	margin : 0px auto;
	margin-top: 2rem;
}

.photo-view{
	width: 100%;
	padding : 20px 0px;
	height: 400px;
	border: 1px solid #eee;
	background: #FFF;
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
	width: 80%;
	margin: 0px auto;
	margin-top: 10px;
	height : 20%;
}

.photo-view > .photo-detail > .photo-info{
	display: flex;
	justify-content: space-between;
}

.photo-view > .photo-detail > .photo-info > .photo-name{
	color: #000;
	font-weight: bold;
	font-size: 0.8rem;
}

.photo-view > .photo-detail > .photo-info > .photo-date-loc{
	color: #555;
	font-size: 0.5rem;
}

.photo-view > .photo-detail > .photo-tag{
	margin-top: 5px;
	color : #33F;
	font-size: 0.5rem;
}

.photo-view > .photo-detail > .photo-desc{
	margin-top: 5px;
	width : 100%;
	height: 80%;
	overflow : hidden;
	color : #555;
	font-size: 0.6rem;
	word-break : break-all;
}


.wrap-photo-list{
	display : flex;
	height: 100px;
	width : 90%;
	margin: 0px auto;
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

@media (max-width: 720px){
	.wrap-photo-view{
		margin-top: 3rem;
	}
	
	.wrap-photo-list{
		margin-top: 3rem;
		height: 50px;
	}
	
	.photo-view{
		width : 100%;
		height: 400px;
	}
	
	.photo-view > .photo-detail {
		margin-top: 5px;
	}
	
	.btn-left-list{
		width: 10px;
		height: 10px;
	}
	
	.btn-right-list{
		width: 10px;
		height: 10px;
	}
}

</style>
<script>
	$(document).ready(function(){
		var items 		= $(".photo-item");
		items.eq(0).trigger("click");
		 
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
					tg.stop().animate({"left" : (width * i) + (5 * i) });
				} else {				
					tg.css({"left" : left}).stop().animate({"left" : toLeft});
				}
			}
		});
		
		$(".photo-view").touchwipe({
		     wipeLeft: function() {
		    	 var tg = $(".photo-view");
		    	 var index 		= parseInt(tg.find(".photo-index").html());
		    	 var toIndex 	= index + 1;
		    	 var items 		= $(".photo-item");
		    	 var itemLength = items.length;
		    	 if(toIndex < itemLength){
					items.eq(toIndex).trigger("click");
		    	 } else{
		    		alert("더 이상 사진이 없습니다.");
		    	 } 
		     },
		     
		     wipeRight: function() {
		    	 var tg = $(".photo-view");
		    	 var index 		= parseInt(tg.find(".photo-index").html());
		    	 var toIndex 	= index - 1;
		    	 var items 		= $(".photo-item");
		    	 if(toIndex >= 0){
		    		 items.eq(toIndex).trigger("click");
		    	 } else{
		    		 alert("더 이상 사진이 없습니다.");
		    	 }
		     },
		     
		     min_move_x: 20,
		     min_move_y: 20,
		     preventDefaultEvents: true
		});
		
		$(".photo-list").touchwipe({
		     wipeLeft: function() {
		    	 $(".btn-right-list").trigger("click");
		     },
		     
		     wipeRight: function() {
		    	 $(".btn-left-list").trigger("click");
		     },
		     
		     min_move_x: 20,
		     min_move_y: 20,
		     preventDefaultEvents: true
		});
		
		$(window).resize(function(){
			photoSnapshtResize();
		});
		
		
	});
	
	
	function photoSnapshtResize(){
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
	}
	
	function showPhoto(seq, index){
		var photoItems = $(".photo-list > .photo-item");
		var tg = photoItems.eq(index);
		photoItems.removeClass("on");
		tg.addClass("on");
		
		var tgLeft = parseInt(tg.css("left"));
		console.log("left");
		photoItems.each(function(){
			var left = parseInt($(this).css("left"));
			var toLeft = left - tgLeft;
			$(this).css({"left" : left}).stop().animate({"left" : toLeft});
		})
		
		$.ajax({
			type	: "POST",
			url 	: "${pageContext.request.contextPath}" + "/photo/view.do",
			dataType: "JSON",
			data 	: {
				"seq" : seq
			},
			success : function(photo) {
			 	$(".photo-view").css("opacity", 0);
				var begin = anime({
					  targets	: ".photo-view",
					  duration	: 500,
					  opacity	: 1,
					  easing	: 'easeInOutSine',
				});
				
				$(".photo-img")
					.css("background-image", "url('${pageContext.request.contextPath}" + photo.image +"')");
				
				$(".photo-name").html(photo.name);
				$(".photo-date-loc").html(photo.date + "  " + photo.location);
				$(".photo-desc").html(photo.desc);
				$(".photo-tag").html(photo.tag);
				$(".photo-index").html(index);
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
				
				<div class="photo-tag"></div>
				<div class="photo-desc"></div>
				<div class="photo-index display-none"></div>
			</div>
		</div>
		<div class="wrap-photo-list">
			<div class="btn btn-left-list h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_arrow.png)">
			</div>
			
			<div class="photo-list">
				<c:forEach items="${photos}" var="photo" varStatus="status" >
				<div class="btn photo-item" onclick="showPhoto('${photo.seq}', '${status.index}')"  
					style="background-image: url('${pageContext.request.contextPath}${photo.snapsht}')">
				</div>
			</c:forEach>
				
			</div>
			<script>
				photoSnapshtResize();
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