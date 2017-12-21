<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board-view-basic.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-board">
			<div class="board-submenu">
				<a class="btn" href="${pageContext.request.contextPath}/board">목록</a>
				<a class="btn">이전글</a>
				<a class="btn">다음글</a>
			</div>
			<div class="board-detail">
				<div class="board-head">
					<div class="board-title">${board.title}</div>
					<div style="height : 1px; background: #CCC; margin: 0.5rem 0rem" ></div>
					<div class="board-info">
						<a>${board.sect}</a>
						<a>${board.date}</a>
						<a>조회수 ${board.hits}</a>
					</div>
				</div>

				<div class="board-contents editor-contents">${board.contents}</div>
			</div>
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