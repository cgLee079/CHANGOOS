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
			<c:forEach var="sect" items="${sects}" varStatus="status">
				<div class="board-sects-item" onclick="selectSect(this)"><c:out value="${sect}" /></div>
			</c:forEach>
		</div>
		
		<div class="board-menu">
			<div class="board-pager"></div>
			<div class="board-search row-center">
				<select class="search-type" style="font-size: 0.5rem">
					<option>TITLE</option>
				</select>
				<input type="text"onkeydown="javascript:if(event.keyCode==13){search();}" style="font-size: 0.5rem" class="search-value" />
				<div class="search-submit col-center" onclick="search()">검색</div>
			</div>
		</div>
		<div class="board-list"></div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>

</body>
</html>



