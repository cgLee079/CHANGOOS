<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-fileupload.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board/board-upload.css"/>
<script src="${pageContext.request.contextPath}/resources/js/included/included-fileupload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/board/board-upload.js"></script>

<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8"/>
		<div class="board-upload-form col-center">
			<form id="uploadForm" action="${pageContext.request.contextPath}/admin/board/upload.do" method="post" enctype="multipart/form-data" class="upload-form">
				<c:set var="sort" value="99999"/>
				<c:if test="${not empty board}">
					<input type="hidden" name="seq" value="<c:out value='${board.seq}'/>"/>
					<input type="hidden" name="date" value="<c:out value='${board.date}'/>"/>
					<input type="hidden" name="hits" value="<c:out value='${board.hits}'/>"/>
					<c:set var="sort" value="${board.sort}"/>
				</c:if>
				
				<div class="board-upload-item">
					<div class="item-name">SORT</div>
					<div class="item-input">
					<input type="text" id="sort" name="sort"  value="<c:out value='${sort}'/>" class="board-sort"/>
					</div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">SECT</div>
					<div class="item-input"><input type="text" id="sect" name="sect"  value="<c:out value='${board.sect}'/>" class="board-sect" ></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">CODE LANGUAGE</div>
					<div class="item-input"><input type="text" id="codeLang" name="codeLang"  value="<c:out value='${board.codeLang}'/>" class="board-codelang"></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">TITLE</div>
					<div class="item-input"><input type="text" id="title" name="title"  value="<c:out value='${board.title}'/>" class="board-title"></div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">CONTENTS</div>
					<div class="item-input">
						<textarea id="board-contents" name="contents" class="board-contents">
							<c:out value="${board.contents}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name">File</div>
					<div class="item-input">
						<c:import url="../included/included_fileupload.jsp" charEncoding="UTF-8">
							<c:param name="boardType" value="board"/>
						</c:import>
					</div>
				</div>
				
				<div class="board-upload-item">
					<div class="item-name"></div>
					<div class="item-input board-submit">
						<a class="btn" onclick="Progress.start(); history.back();">취소</a>
						<a class="btn" onclick="Progress.start(); $('#uploadForm').submit()">저장</a>
					</div>
				</div>	
				
			</form>
		</div>
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>