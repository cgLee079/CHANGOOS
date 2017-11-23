<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
.wrapper{
	width: 70%;
	margin: 0px auto;
}

.item-detail {
	margin-top: 100px;
}

.item-head {
	
	position: relative;
}

.item-hits{
	position: absolute;
	bottom: 0px;
	right : 120px;
	color: #888;
	font-size: 12px;
}

.item-date{
	position: absolute;
	bottom: 0px;
	right : 0px;
	color: #888;
	font-size: 12px;
}

.item-content {
	margin-top: 50px;
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
				<h1>${item.name}</h1>
				
				<a class="item-hits">hits : ${item.hits}</a>
				<a class="item-date">date : ${item.date}</a>
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