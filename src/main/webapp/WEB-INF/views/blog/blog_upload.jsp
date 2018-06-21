<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-fileupload.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/blog/blog-upload.css"/>
<script src="${pageContext.request.contextPath}/resources/js/included/included-fileupload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/blog/blog-upload.js"></script>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8"/>
		<div class="wrap-upload-form">
			<div class="upload-title">블로그 업로드</div>
		
			<form id="uploadForm" action="${pageContext.request.contextPath}/mgnt/blog/upload.do" method="post" enctype="multipart/form-data" class="upload-form">
				<c:if test="${not empty blog}">
					<input type="hidden" name="seq" value="<c:out value='${blog.seq}'/>"/>
					<input type="hidden" name="snapsht" value="<c:out value='${blog.snapsht}'/>"/>
					<input type="hidden" name="date" value="<c:out value='${blog.date}'/>"/>
					<input type="hidden" name="hits" value="<c:out value='${blog.hits}'/>"/>
				</c:if>
				
				<div class="blog-upload-item">
					<div class="item-name">스냅샷</div>
					<div class="item-input">
						<input type="file" id="snapshtFile" name="snapshtFile" class="blog-snapshot"/>
					</div>
				</div>
				
				<div class="blog-upload-item">
					<div class="item-name">태그</div>
					<div class="item-input">
						<input type="text" id="tag" name="tag"  value="<c:out value='${blog.tag}'/>" class="blog-tag"/>
					</div>
				</div>
				
				<div class="blog-upload-item">
					<div class="item-name">제목</div>
					<div class="item-input"><input type="text" id="title" name="title"  value="<c:out value='${blog.title}'/>" class="blog-title"></div>
				</div>
				
				<div class="blog-upload-item">
					<div class="item-name">내용</div>
					<div class="item-input">
						<textarea id="blog-contents" name="contents" class="blog-contents">
							<c:out value="${blog.contents}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="blog-upload-item">
					<div class="item-name">첨부파일</div>
					<div class="item-input">
						<c:import url="../included/included_fileupload.jsp" charEncoding="UTF-8">
							<c:param name="boardType" value="blog"/>
						</c:import>
					</div>
				</div>
				
				<div class="blog-upload-item">
					<div class="item-name"></div>
					<div class="item-input blog-submit">
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