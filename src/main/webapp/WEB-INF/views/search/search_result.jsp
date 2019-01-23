<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp"%>
</head>

<style>

.wrap-sch-result {
	margin: 50px 0 70px 0;
	min-height: calc(100% - var(--nav-height));
}

.board{
    padding: 30px 20px;
    cursor: pointer;
    border-bottom: 0.5px solid #F1F1F1
}

.board:first-child{
	margin-top : 0;
	padding-top: 0;
}

.board .board-title {
	font-size: 1.4rem;
	font-weight: bold;
	margin-bottom: 0.4rem;
	transition	: margin .3s cubic-bezier(0.215, 0.61, 0.355, 1);
}

.board:hover .board-title {
	margin-left : 10px;
}

.board .board-desc {
	display: -webkit-box;
	overflow: hidden;
	-webkit-box-orient: vertical;
	white-space: normal;
	-webkit-line-clamp: 4;
	max-height: 5.6rem;
	word-break: break-word;
}

.board-desc sch-val {
	color : #00F;
	font-weight: bold
}

.noresult{
    text-align: center;
}

.noresult .noresult-title {
	text-align: center;
	font-size: 1.4rem;
	font-weight: bold;
	margin-bottom: 0.7rem;
}

.noresult .noresult-desc {
	text-align: center;
	color : #00F;
}


</style>
<script type="text/javascript">
	function boardView(type, seq) {
		switch (type) {
		case "PROJECT":
			window.location.href = getContextPath() + "/projects/" + seq;
			break;
		case "STUDY":
			window.location.href = getContextPath() + "/studies/" + seq;
			break;
		case "BLOG":
			window.location.href = getContextPath() + "/blogs/" + seq;
			break;
		}
	}
</script>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />

		<div class="wrap-sch-result">
	
			<c:choose>
				<c:when test="${fn:length(results) == 0}" >
					<div class="noresult" onclick="boardView('${result.type}', '${result.seq}')">
						<div class="noresult-title">검색된 결과가 없습니다</div>
						<div class="noresult-desc"><a href="javascript:history.back();" >뒤로가기</a></div>
					</div>
				</c:when>
				
				<c:otherwise>
					<c:forEach var="result" items="${results}">
						<c:if test="${result.enabled eq true}">
							<div class="board" onclick="boardView('${result.type}', '${result.seq}')">
								<div class="board-title">${result.title}</div>
								<div class="board-desc">${result.highlight}</div>
							</div>
						</c:if>
					</c:forEach>
				</c:otherwise>
		
			</c:choose>
		</div>

		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>