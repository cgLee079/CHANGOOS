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
	
	function relocateItemTitle(){
		var itemTitle = $(".main-items-title");
		var offset = itemTitle.offset();
		var top = offset.top;
		
		if(top < deviceHeight){
			var marginTop = parseInt(itemTitle.css("margin-top"));
			itemTitle.css("margin-top", marginTop + (deviceHeight - top));
		}
	}	
	
	$(document).ready(function(){
		relocateItemTitle();
		
		$(window).resize(function(){
			relocateItemTitle();
		})
	})
</script>
</head>
<body>
	
<div class="wrapper">
	<c:import url="included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="main">
		<div class="main-introduce">
			<div class="me-icon" style="background-image : url(resources/image/home_icon_me.png);">
			</div>
			
			<div class="main-introduce-name">
				<h1 class="ml9">
				  <span class="text-wrapper">
				    <span class="letters">Changoo Lee</span>
				  </span>
				</h1>
			</div>
			
			<div class="main-introduce-desc">
				Hello! Thank you for visiting my site. <br/>
				I specialized Computer Engineering at Hansung University.<br/>
				and interested in Android, Web development.<br/>
				If you want to see my projects, show below.<br/>
				<br/>
				<a href="javascript:void(0)" onclick="scrollToItems()" class ="btn btn-show-items" >going on</a>
			</div>
		</div>
		
		<div class="main-items-title">
			<a href="${pageContext.request.contextPath}/item"><h1>Projects</h1></a>
		</div>
		<%@ include file="included/included_item_list.jsp" %> 
	</div>
	
	<c:import url="included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


