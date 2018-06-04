<%@ page pageEncoding="UTF-8" %>
<html>
<head>

<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board/board-list.css" />
<script src="${pageContext.request.contextPath}/resources/js/board/board-list.js"></script>
</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<input type="hidden" id="allRowCnt" value="<c:out value='${count}'/>"/>
	
	<div class="wrap-board">
		<div class="board-sects">
			<div class="board-sects-item" onclick="selectSect(this)">ALL</div>
			<c:forEach var="sect" items="${sects}" varStatus="status">
				<div class="board-sects-item" onclick="selectSect(this)"><c:out value="${sect}" /></div>
			</c:forEach>
		</div>
		
		<div class="board-search">
			<select class="search-type" style="font-size: 0.75rem">
				<option>TITLE</option>
			</select>
			<input type="text"onkeydown="javascript:if(event.keyCode==13){search();}" style="font-size: 0.75rem" class="search-value" />
			<div class="search-submit " style="background-image: url('${pageContext.request.contextPath}/resources/image/btn-board-search.svg')" onclick="search()"></div>
		</div>
		
		<div class="board-list"></div>
		
		<div class="board-pager"></div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>

</body>
</html>



