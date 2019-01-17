<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp"%>
</head>

<style>
sch-val {
	color: #00F;
	font-weight: bold;
}

.wrap-sch-result {
	margin: 50px 0 70px 0;
	min-height: calc(100% - 60px);
}

.board {
    margin: 20px 20px;
    border-bottom: 0.7px solid #D3D3D3;
    padding: 25px 0;
    cursor: pointer;
}

.board .board-title {
	color: #ec5621;
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

			<c:forEach var="result" items="${results}">
				<c:if test="${result.enabled eq true}">
					<div class="board"
						onclick="boardView('${result.type}', '${result.seq}')">
						<div class="board-title">${result.title}</div>
						<div class="board-desc">${result.highlight}</div>
					</div>
				</c:if>
			</c:forEach>

		</div>

		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>