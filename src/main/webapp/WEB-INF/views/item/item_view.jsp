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
	margin-top: 50px;
}

.item-git-link{
	position : absolute;
    right : 0px;
    top : 0px;
}

.item-git-link img{
	width : 50px;
	height: 50px;
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

			<div class="item-content">
				<c:if test="${item.git eq true}">
					<div class="btn item-git-link">
						 <a target="_black" href="${item.gitURL}">
						 	<img src="${pageContext.request.contextPath}/resources/image/btn_item_git.png" />
						 </a>
					</div>
				</c:if>
				
				${item.content}
			</div>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
	</div>
</body>
</html>