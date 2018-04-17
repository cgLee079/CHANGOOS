<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board/board-view.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-comment.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/editor-contents-resizer.js"></script>

<script>
var boardPage = "${page}";

function boardList(){
	if(!boardPage){
		boardPage = 1;
	}
	window.location.href = getContextPath() + "/board#" + boardPage;
}

function boardDelete(seq){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "한번 삭제된 글은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then((willDelete) => {
			if(willDelete) {
				window.location.href = getContextPath() + "/admin/board/delete.do?seq=" + seq;  
			} 
		});
}

function boardModify(seq){
	window.location.href = getContextPath() + "/admin/board/upload?seq=" + seq;		
}

function boardView(seq){
	if (seq){
		window.location.href = getContextPath() + "/board/view?seq=" + seq +"&page=" + boardPage;
	} else {
		swal("글이 더 이상 없습니다.");
	}
}

function downloadFile(pathNm){
	window.location.assign(getContextPath()	+ "/board/download.do?filename="+ pathNm);
}
</script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-board">
			<div class="board-submenu">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<a class="btn" onclick="boardModify('${board.seq}')">수정</a>
					<a class="btn" onclick="boardDelete('${board.seq}')">삭제</a>
				</sec:authorize>
				
				<a class="btn" onclick="boardList()">목록</a>
				<a class="btn" onclick="boardView('${afterBoard.seq}')">이전글</a>
				<a class="btn" onclick="boardView('${beforeBoard.seq}')">다음글</a>
			</div>
			<div class="board-detail">
				<div class="board-head">
					<div class="board-title">${board.title}</div>
					<div class="board-info">
						<div>${board.sect}</div>
						<div class="colum-border"></div>
						<div>${board.date}</div>
						<div class="colum-border"></div>
						<div>조회수 ${board.hits}</div>
					</div>
				</div>

				<c:if test="${!empty files}">
					<div class="board-files">
						<c:forEach var="file" items="${files}">
							<fmt:formatNumber var="filesize" value="${file.size/(1024*1024)}" pattern="0.00"/>
							<div class="board-file">
								 <a onclick="downloadFile('${file.pathNm}')">
								 	${file.realNm} (${filesize}MB)
								 </a>
							</div>												
						</c:forEach>
					</div>
				</c:if>
				<div class="board-contents editor-contents">${board.contents}</div>
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