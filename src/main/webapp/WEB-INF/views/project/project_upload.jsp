<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-upload.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-fileupload.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-imageupload.css"/>
<script src="${pageContext.request.contextPath}/resources/js/project/project-upload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-fileupload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-imageupload.js"></script>
</head>

<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
				
		<div class="wrap-upload-form">
			<div class="upload-title">프로젝트 업로드</div>
			
			<form id="uploadForm" action="${pageContext.request.contextPath}/mgnt/project/upload.do" 
				method="post" enctype="multipart/form-data">
				
				<c:if test="${not empty project}">
					<input type="hidden" name="seq" value="<c:out value='${project.seq}'/>"/>
					<input type="hidden" name="snapsht" value="<c:out value='${project.snapsht}'/>"/>
					<input type="hidden" name="hits" value="<c:out value='${project.hits}'/>"/>
				</c:if>
				
				<div class="upload-project">
					<div class="upload-project-name">공개여부</div>
					<div class="upload-project-input">
						<select id="enabled" name="enabled" class="project-enabled" >
					<option value='true' <c:if test='${not empty project and project.enabled}'>selected</c:if>>공개 </option>
							<option value='false' <c:if test='${not empty project and !project.enabled}'>selected</c:if>>비공개 </option>
						</select>
						
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">스냅샷</div>
					<div class="upload-project-input">
						<input type="file" id="snapshtFile" name="snapshtFile" class="project-snapshot"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">소스코드</div>
					<div class="upload-project-input">
						<input type="text" id="sourcecode" name="sourcecode" class="project-sourcecode" value="<c:out value='${project.sourcecode}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">개발자</div>
					<div class="upload-project-input">
						<input type="text" id="developer" name="developer" class="project-developer" value="<c:out value='${project.developer}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">부제목</div>
					<div class="upload-project-input">
						<input type="text" id="subtitle" name="subtitle" class="project-subtitle" value="<c:out value='${project.subtitle}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">제목</div>
					<div class="upload-project-input">
						<input type="text" id="title" name='title' class="project-name" value="<c:out value='${project.title}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">개요</div>
					<div class="upload-project-input">
						<textarea id="desc" name="desc" class="project-desc">
							<c:out value="${project.desc}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">내용</div>
					<div class="upload-project-input">
						<textarea name="contents" id="contents" class="ckeditor-autosave">
							<c:out value="${project.contents}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">이미지</div>
					<div class="upload-project-input">
						<c:import url="../included/included_imageupload.jsp" charEncoding="UTF-8">
							<c:param name="editor" value="contents"/>
						</c:import>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">첨부파일</div>
					<div class="upload-project-input">
						<c:import url="../included/included_fileupload.jsp" charEncoding="UTF-8">
							<c:param name="boardType" value="project"/>
						</c:import>
					</div>
				</div>
				
				
				<div class="upload-project">
					<div class="upload-project-name"></div>
					<div class="upload-project-input project-submit">
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