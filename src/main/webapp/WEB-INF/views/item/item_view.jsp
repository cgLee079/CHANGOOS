<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
.item-detail {
	width : 80%;
	margin : 0px auto;
	margin-top: 100px;
}

.item-head {
	position: relative;
}

.item-name{
	display: inline-block;
	margin-right: 10px;
}

.item-subinfo{
	position: absolute;
	right : 0px;
	bottom : 0px;
	
	color: #888;
	font-size: 12px;
	text-align: right;
}

.item-content {
	margin-top: 50px;
}

@media (max-width: 800px){
	.item-detail {
		margin-top: 50px;
	}
	
	.item-content{
		margin-top: 10px;
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
			delay: function(el, i){return i * 250 },
			loop: false
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8">
		</c:import>

		<div class="item-detail">
			<div class="item-head">
				<h1 class="item-name">${item.name}</h1>
				<c:if test="${!empty item.developer}">
					<a class="item-developer">with ${item.developer}</a>
				</c:if>
				
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

			<div class="item-content">${item.content}</div>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
	</div>
</body>
</html>