<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home-basic.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home-responsive.css" />

<script>
function itemView(seq){
	var contextPath = "${pageContext.request.contextPath}";
	window.location.href = contextPath + "/item/view?seq=" + seq;		
}

function scrollToItems(){
	var top = $(".main-items-title").offset().top;
	$("html, body").animate({ scrollTop: (top - 50) });
}

</script>
</head>
<body>
	
<div class="wrapper">
	<c:import url="included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="main">
		<div class="main-introduce">
			<div class="me-icon" style="background-image : url(resources/image/icon_me.png);">
			</div>
			
			<div class="main-introduce-name">
				<h1 class="ml9">
				  <span class="text-wrapper">
				    <span class="letters">Toyo dim</span>
				  </span>
				</h1>
			</div>
			
			<div class="main-introduce-desc">
				Hello! Thank you for visiting my portfolio site. <br/>
				I specialized Computer Engineering at Hansung University.<br/>
				and interested in Android, Web development. <br/>
				If you want to see my projects, show below.<br/>
				<br/>
				<a href="#items" onclick="scrollToItems()">going on</a>
			</div>
		</div>
		
		<div class="main-items-title">
			<h1>Projects</h1>
		</div>
		<div class="main-items">
			<c:forEach var="item" items="${items}">
				<div onclick="itemView(${item.seq})" class="item-view">
					<div class="item-snapsht" style="background-image: url('${pageContext.request.contextPath}${item.snapsht}')">
						<div class="item-info display-none">
						<h3>${item.name}</h3>
						<a class="item-desc">${item.desc}</a>
						</div>
					</div>
				</div>
			</c:forEach>
			
			<script>
			(function(){
				function itemResize(){
					$(".item-view").each(function(){
						var width = parseInt($(this).width());
						console.log(width);
						$(this).css("height", width *(2/3));
					});
				}
				itemResize();
				
				$(window).resize(function(){
					itemResize();	
				})
			})();
			</script>
		</div>
	</div>
	
	<c:import url="included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


