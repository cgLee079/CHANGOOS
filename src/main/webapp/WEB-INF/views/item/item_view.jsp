<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
.item-detail {
	width : 80%;
	margin : 0px auto;
	margin-top: 5rem;
}

.item-head {
	position: relative;
}

.item-name{
	display: inline-block;
	margin-right: 5px;
}

.item-subinfo{
	position: absolute;
	right : 0px;
	bottom : 0px;
	color: #666;
	font-size: 0.5rem;
	line-height : 1rem;
	text-align: right;
}

.item-line{
	background: #DDD;
	height : 1px;
	margin: 0.5rem 0;
}

.item-snapsht-crop{
    width: 360px;
    height:	200px;
    overflow: hidden;
	margin-bottom: 2rem;    
}

.item-content {
	position : relative;
	margin-top: 1.5rem;
	font-size: 0.8rem;
	padding : 1rem;
	background: #FFF;
	border: 1px solid #DDD;
	min-height: 100%;
}

.item-content h1, h2, h3{
	margin: 0.5rem 0px;
}

.item-content p{
	margin : 0.3rem 0;
	line-height: 1.3rem;
}

.item-content code {
	overflow-x: auto;
	margin: 1rem 0.1rem;
	border: 1px solid #DDD;
}

.item-subinfo2 {
	display : flex;
	justify-content: space-between;
	font-size: 0.7rem;
}

.item-source{
	font-weight: bold;
}

.btn-item-before{
	position : fixed;
	top : 50%;
	left : 1.5rem;
	width: 3%;
	height: 3%;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: contain;
}

.btn-item-next{
	position : fixed;
	top : 50%;
	right : 1.5rem;
	width: 3%;
	height: 3%;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: contain;
}

@media (max-width: 420px){
	.item-detail {
		width : 90%;
		margin-top: 7rem;
	}
}

</style>
<script>
	function snapshtResize(){
		var parentWidth = parseInt($(".item-content").width());
		var crop 	= $(".item-snapsht-crop");
		var img 	= $(".item-snapsht");
		var width 	= parseInt(crop.width());
		var cropHeight;
		var imgHeight;
		
		if(width > parentWidth){
			crop.css("width", "100%");
			img.attr("width", "100%");
		} else{
			crop.css("width", "");
			img.attr("width", "360px");
		}
		
		cropHeight = crop.height();
		imgHeight = img.height();
		if(imgHeight > cropHeight){
			img.css("margin-top", (cropHeight- imgHeight) / 2);
		} else{
			crop.css("height", imgHeight);
		}
		
	}
	
	function contentImgResize(){
		var parentWidth = parseInt($(".item-content").width());
		var imgs = $(".item-content img");
		imgs.each(function(){
			var width = parseInt($(this).width());
			if(width > parentWidth){
				$(this).css("width", "100%");
			}else{
				$(this).css("width", "");
			}
		});
	}
	
	function contentYoutubeResize(){
		var parentWidth = parseInt($(".item-content").width());
		var videos = $("iframe");
		videos.each(function(){
			if(parentWidth >= 640){
				$(this).attr("width", "640");
				$(this).attr("height", "360");
			} else{
				var width = parentWidth;
				var ratio = parseFloat($(this).attr("width") /$(this).attr("height"));
				$(this).attr("width", width);
				$(this).attr("height", width * (1/ratio));
			} 
		});
	}

	$(document).ready(function(){
		var lineDrawing = anime({
			targets: ".item-line",
			easing: "easeInQuad",
			width : [0, "100%"],
			duration: 400,
		});
		
		if(isMobile){
			$(".btn-item-before, .btn-item-next").addClass("display-none");
		}
		
		$(".btn-item-next").on("click", function(){
			var afterItemSeq = '${afterItem.seq}';
			var contextPath = "${pageContext.request.contextPath}";
			if(afterItemSeq){
				window.location.href = contextPath + "/item/view?seq=" + afterItemSeq;
			} 
		});
		
		$(".btn-item-before").on("click", function(){
			var beforeItemSeq= '${beforeItem.seq}';
			var contextPath = "${pageContext.request.contextPath}";
			if(beforeItemSeq){
				window.location.href = contextPath + "/item/view?seq=" + beforeItemSeq;
			} 
		});
		
		/*
		$(".wrapper").touchwipe({
		     wipeLeft: function() {
		    	 $(".btn-item-next").trigger("click");
		     },		     
		     wipeRight: function() {
		    	 $(".btn-item-before").trigger("click");
		     },		     
		     min_move_x: 50,
		     min_move_y: 20,
		     preventDefaultEvents: true
		});
		*/
		snapshtResize();
		contentImgResize();
		contentYoutubeResize();
		$(window).resize(function(){
			snapshtResize();
			contentImgResize();
			contentYoutubeResize();
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />

		<div class="item-detail">
			<div class="item-head">
				<h1 class="item-name">${item.name}</h1>
				<div class="item-subinfo">
					<c:if test="${!empty item.date}"> 
						<a class="item-date">DATE : ${item.date}</a> <br/>
					</c:if>
					<a class="item-sect">SECT : ${item.sect}</a>
					,&nbsp
					<a class="item-hits">HITS : ${item.hits}</a>
				</div>
			</div>

			<div class="item-line"></div>
				   			
			<div class="item-subinfo2">
				<c:if test="${!empty item.developer}"> 
					<a class="item-developer">by ${item.developer}</a>
				</c:if>
				<c:if test="${!empty item.sourcecode}">
					<a class="btn item-source" target="_black" href="${item.sourcecode}"> source</a>
				</c:if>
			</div>
			
			
			<div class="item-content">
				<div class="item-snapsht-crop">
					<img class="item-snapsht" src="${pageContext.request.contextPath}${item.snapsht}" width="400px">
				</div>
				${item.content}
			</div>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
		<c:if test="${!empty beforeItem}">
			<div class="btn btn-item-before h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
		
		<c:if test="${!empty afterItem}">
			<div class="btn btn-item-next" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
	</div>
</body>
</html>