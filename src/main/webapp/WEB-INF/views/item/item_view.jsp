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
	text-align: right;
}

.item-content {
	position : relative;
	margin-top: 2rem;
	font-size: 0.8rem;
}

.item-subinfo2 {
	display : flex;
	justify-content: space-between;
	font-size: 0.7rem;
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
		margin-top: 7rem;
	}
	
	.item-content{
		margin-top: 1rem;
	}
	
	.item-git-link img{
		width : 20px;
    	height : 20px;
	}
	
}

</style>
<script>
	$(document).ready(function(){
		var lineDrawing = anime({
			targets: '.path',
			strokeDashoffset: [anime.setDashoffset, 0],
			easing: 'easeInOutSine',
			duration: 1000,
			delay : 300,
			loop: false
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
		
		if(isMobile){
			$("img").css("width", "100%");
		}
		
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
					<a class="item-sect">sect : ${item.sect}</a>
					<br>
					<a class="item-date">date : ${item.date}</a>
					<br>
					<a class="item-hits">hits : ${item.hits}</a>
					
				</div>
			</div>

			<svg xmlns="http://www.w3.org/2000/svg" width="100%" height="5px">
				<path class="path" fill="none" stroke="#999" d="M0 0 L2000 0" />
	   		</svg>
	   			
			<div class="item-subinfo2">
				<c:if test="${!empty item.developer}"> 
					<a class="item-developer">by ${item.developer}</a>
				</c:if>
				<c:if test="${!empty item.sourcecode}">
					<a class="item-soruce" target="_black" href="${item.sourcecode}"> source</a>
				</c:if>
			</div>
			
			<div class="item-content">
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