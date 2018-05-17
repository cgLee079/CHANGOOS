<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board/board-view.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-comment.css" />
<script src="${pageContext.request.contextPath}/resources/js/board/board-view.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-comment.js"></script>

</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<input type="hidden" id="sect" value="<c:out value='${sect}'/>"/>
		<input type="hidden" id="boardPage" value="<c:out value='${page}'/>"/>
		
		<div class="wrap-board">
			<div class="board-detail">
				<div class="board-head">
					<div class="board-sect font-lora"><c:out value="${board.sect}"/></div>
					<div class="board-title"><c:out value="${board.title}"/></div>
					<div class="board-info">
						<c:if test="${not empty board.codeLang }">
							<div><c:out value="${board.codeLang}"/></div>
							<div class="colum-border"></div>
						</c:if>
						<div><c:out value="${board.date}"/></div>
						<div class="colum-border"></div>
						<div>조회수 <c:out value="${board.hits}"/></div>
					</div>
				</div>

				<c:if test="${!empty files}">
					<div class="board-files">
						<c:forEach var="file" items="${files}">
							<fmt:formatNumber var="filesize" value="${file.size/(1024*1024)}" pattern="0.00"/>
							<div class="board-file">
								 <a onclick="downloadFile('${file.pathNm}')">
								 	<c:out value="${file.realNm}"/>(<c:out value="${filesize}"/> MB)
								 </a>
							</div>												
						</c:forEach>
					</div>
				</c:if>
				<div class="board-contents editor-contents">
					<c:out value="${board.contents}" escapeXml="false"/>
				</div>
			</div>
			
			<div class="board-submenu">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<a class="btn" onclick="boardModify('${board.seq}')">수정</a>
					<a class="btn" onclick="boardDelete('${board.seq}')">삭제</a>
				</sec:authorize>
				
				<a class="btn" onclick="boardList()">목록</a>
				
				<c:choose>
					<c:when test='${not empty afterBoard}'><c:set value="${afterBoard.title}" var="afterBoardTooltip" /></c:when>
					<c:otherwise><c:set value="더 이상 글이 없습니다." var="afterBoardTooltip" /></c:otherwise>
				</c:choose>
				<a class="btn btn-board-before" title="<c:out value='${afterBoardTooltip}'/>" onclick="boardView('${afterBoard.seq}')">이전글</a>
				
				<c:choose>
					<c:when test='${not empty beforeBoard}'><c:set value="${beforeBoard.title}" var="boforeBoardTooltip" /></c:when>
					<c:otherwise><c:set value="더 이상 글이 없습니다." var="boforeBoardTooltip" /></c:otherwise>
				</c:choose>
				<a class="btn btn-board-next" title="<c:out value='${boforeBoardTooltip}'/>"onclick="boardView('${beforeBoard.seq}')">다음글</a>
			</div>
			
			<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
			   <c:param name = "perPgLine" value = "10" />
			   <c:param name = "boardType" value = "board" />
			   <c:param name = "boardSeq" value = "${board.seq}" />
			   <c:param name = "comtCnt" value = "${board.comtCnt}" />
			</c:import>
		</div>	
	
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
	</div>
</body>
</html>