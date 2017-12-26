<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/item-view.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/editor-contents-resizer.js"></script>
<script>
window.onload = function(){
	var lineDrawing = anime({
		targets: ".item-line",
		easing: "easeInQuad",
		width : [0, "100%"],
		duration: 400,
	});
	
	if(isMobile){
		$(".btn-item-before, .btn-item-next").addClass("display-none");
	}
	
	$(".btn-item-next").on("click", function(){
		var afterparentSeq = '${afterItem.seq}';
		
		if(afterparentSeq){
			Progress.start();
			window.location.href = getContextPath() + "/item/view?seq=" + afterparentSeq;
		} 
	});
	
	$(".btn-item-before").on("click", function(){
		var beforeparentSeq= '${beforeItem.seq}';
		if(beforeparentSeq){
			Progress.start();
			window.location.href = getContextPath() + "/item/view?seq=" + beforeparentSeq;
		} 
	});
	
	/*
	$(".wrapper").touchwipe({
	     wipeLeft: function() {
	    	 $(".btn-item-next").trigger("click");
	     },		     
	     wipeRight: function() {
	    	 $(".btn-item-before").trigger("click");
	     },		     
	     min_move_x: 30,
	     min_move_y: 20,
	     preventDefaultEvents: true
	});
	*/
}


</script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="item-detail">
			<div class="item-head">
				<h1 class="item-name">${item.name}</h1>
				<div class="item-subinfo">
					<c:if test="${!empty item.date}"> 
						<a class="item-date">DATE : ${item.date}</a> <br/>
					</c:if>
					<a class="item-sect">SECT : ${item.sect}</a>
					,&nbsp
					<a class="item-hits">HITS : ${item.hits}</a>
				</div>
			</div>

			<div class="item-line"></div>
				   			
			<div class="item-subinfo2">
				<c:if test="${!empty item.developer}"> 
					<a class="item-developer">by ${item.developer}</a>
				</c:if>
				<c:if test="${!empty item.sourcecode}">
					<a class="btn row-center item-source" target="_black" href="${item.sourcecode}">
						<img src="${pageContext.request.contextPath}/resources/image/btn_itemview_source.png" style="width:0.8rem; height:0.8rem; margin-right:0.1rem">
						SOURCE
					</a>
				</c:if>
			</div>
			
			<div class="item-content editor-contents">
				<c:if test="${item.video eq 'N'}">
					<img class="item-snapsht" src="${pageContext.request.contextPath}${item.snapsht}" >
				</c:if>
				${item.content}
			</div>
		
			<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
			   <c:param name = "perPgLine" value = "10" />
			   <c:param name = "boardType" value = "ITEM" />
			   <c:param name = "boardSeq" value = "${item.seq}" />
			   <c:param name = "comtCnt" value = "${comtCnt}" />
			</c:import>
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