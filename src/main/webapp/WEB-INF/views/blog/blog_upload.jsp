<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<%@ include file="/WEB-INF/views/blog/blog_common.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/blog/blog-upload.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-fileupload.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-imageupload.css"/>
<script src="${pageContext.request.contextPath}/resources/js/blog/blog-upload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-fileupload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-imageupload.js"></script>

<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8"/>
		<div class="wrap-upload-form">
			<div class="upload-title">블로그 업로드</div>
		
			<form id="uploadForm" action="${pageContext.request.contextPath}/mgnt/blogs/post/${blog.seq}" 
					method="POST" class="upload-form">
				<c:if test="${not empty blog}">
				 	<input type="hidden" name="_method" value="PUT"/> 
					<input type="hidden" id = "seq" name="seq" value="<c:out value='${blog.seq}'/>"/>
					<input type="hidden" id = "date" name="date" value="<c:out value='${blog.date}'/>"/>
					<input type="hidden" id = "hits" name="hits" value="<c:out value='${blog.hits}'/>"/>
				</c:if>
				
				<div class="blog-upload-item">
					<div class="item-name">공개여부</div>
					<div class="item-input">
						<select id="enabled" name="enabled" class="blog-enabled" >
							<option value='true' <c:if test='${not empty blog and blog.enabled}'>selected</c:if>>공개 </option>
							<option value='false' <c:if test='${not empty blog and !blog.enabled}'>selected</c:if>>비공개 </option>
						</select>
					</div>
				</div>
				
				<div class="blog-upload-item">
					<div class="item-name">썸네일</div>
					<div class="item-input">
						<img id="thumbnail-img" class="blog-thumbnail-img" onclick="$(this).siblings('#thumbnailFile').click();" src="<c:out value='${thumbDir}${blog.thumbnail}'/>" height="150">
						<input type="hidden" id="thumbnail" name="thumbnail" value="<c:out value='${blog.thumbnail}'/>" />
						<input type="file" id="thumbnailFile" name="thumbnailFile" class="blog-thumbnailfile" onchange="onThumbnailChange(this)"/>
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
						<textarea id="blog-contents" name="contents" class="ckeditor-autosave blog-contents">
							<c:out value="${blog.contents}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="blog-upload-item">
					<div class="item-name">내용</div>
					<div class="item-input">
						<c:import url="../included/included_imageupload.jsp" charEncoding="UTF-8">
							<c:param name="dir" value="${imageDir}"/>
							<c:param name="editor" value="blog-contents"/>
						</c:import>
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
						<a class="btn" onclick="Progress.start(); $('#uploadForm').submit();">저장</a>
					</div>
				</div>	
				
			</form>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>