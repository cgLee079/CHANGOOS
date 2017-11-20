<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/global.css" />
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/anime2.2.0.js"></script>
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
	right : 100px;
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
		<c:import url="included/included_nav.jsp" charEncoding="UTF-8">
		</c:import>
		
		<div class="item-detail">
			<div class="item-head">
				<h1>${item.name}</h1>
				
				<a class="item-hits">hits : ${item.hits}</a>
				<a class="item-date">date : ${item.wrDate}</a>
			</div>

			<svg xmlns="http://www.w3.org/2000/svg" width="100%" height="5px">
				<path class="path" fill="none" stroke="#999" d="M0 0 L1000 0" />
	   		</svg>

			<div class="item-content">${item.content}</div>
		</div>
		
		<c:import url="included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
	</div>
</body>
</html>