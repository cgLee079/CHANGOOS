<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
.item-detail {
	width : 80%;
	margin : 0px auto;
	margin-top: 50px;
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
	margin-top: 70px;
	font-size: 0.8rem;
}

.item-subinfo2 {
	display : flex;
	justify-content: space-between;
	font-size: 0.7rem;
}

@media (max-width: 720px){
	.item-detail {
		margin-top: 50px;
	}
	
	.item-content{
		margin-top: 10px;
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
	</div>
</body>
</html>