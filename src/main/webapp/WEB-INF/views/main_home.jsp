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
				<div onclick="itemView(${item.seq})" class="item">
					<div class="item-bg" style="background-image: url('${pageContext.request.contextPath}${item.snapsht}')"></div>
					<div class="item-desc">
					
					<a>
						${item.name}</br>
						${item.desc}
					</a>
					</div>
				</div>
			</c:forEach>
			
			<script>
			(function(){
				$(".item").bind("mouseover", function(){
					var tg = $(this);
					tg.css("opacity","1");
					tg.find(".item-desc").addClass("fade-in");
					tg.find(".item-desc").removeClass("fade-out");
				});
				
				$(".item").bind("mouseout", function(){
					var tg = $(this);
					tg.css("opacity","0.5");
					tg.find(".item-desc").removeClass("fade-in");
					tg.find(".item-desc").addClass("fade-out");
				});
			})();
			</script>
		</div>
	</div>
	
	<c:import url="included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


