<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<style>
	.main{
		width : 90%;
		margin: 0px auto;
		margin-top : 3rem;	
	}
	
	.items-title{
		letter-spacing : 1.5rem;
		font-size: 3.5rem;
		font-weight : bold;
		text-align: center;
		margin-bottom: 3rem;
		-webkit-transition: trsnform 0.3s;
    	transition: transform 0.3s;
    	cursor: default;
	}
	
	.items-title:HOVER {
		transform : scale(1.1);
	}
}
</style>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="main">
		<div class="items-title font-lora">
			Projects
		</div>
		<%@ include file="/WEB-INF/views/included/included_item_list.jsp" %>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


