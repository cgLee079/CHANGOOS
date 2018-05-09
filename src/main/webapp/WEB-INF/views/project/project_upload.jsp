<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-upload.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-fileupload.css"/>
<script src="${pageContext.request.contextPath}/resources/js/project/project-upload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-fileupload.js"></script>
</head>

<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
				
		<div class="wrap-upload-form">
			<form id="upload-form" action="${pageContext.request.contextPath}/admin/project/upload.do" 
				method="post" enctype="multipart/form-data">
				
				<c:set var="sort" value="99999"/>
				<c:if test="${not empty project}">
					<input type="hidden" name="seq" value="<c:out value='${project.seq}'/>"/>
					<input type="hidden" name="snapsht" value="<c:out value='${project.snapsht}'/>"/>
					<input type="hidden" name="hits" value="<c:out value='${project.hits}'/>"/>
					<input type="hidden" id="videoVal" value="<c:out value='${project.video}'/>"/>
					<c:set var="sort" value="${project.sort}"/>
				</c:if>
				
				<div class="upload-project">
					<div class="upload-project-name">SORT</div>
					<div class="upload-project-input">
						<input type="text" id="sort" name="sort" class="project-sort" value="<c:out value='${sort}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">TITLE</div>
					<div class="upload-project-input">
						<input type="text" id="title" name='title' class="project-name" value="<c:out value='${project.title}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">SECT</div>
					<div class="upload-project-input">
						<input type="text" id="sect" name="sect" class="project-sect" value="<c:out value='${project.sect}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">SOURCECODE</div>
					<div class="upload-project-input">
						<input type="text" id="sourcecode" name="sourcecode" class="project-sourcecode" value="<c:out value='${project.sourcecode}'/>"/>
					</div>
				</div>
				
					<div class="upload-project">
					<div class="upload-project-name">DATE</div>
					<div class="upload-project-input">
						<input type="text" id="date" name="date" class="project-date" value="<c:out value='${project.date}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">DEVELOPER</div>
					<div class="upload-project-input">
						<input type="text" id="developer" name="developer" class="project-developer" value="<c:out value='${project.developer}'/>"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">SNAPSHT</div>
					<div class="upload-project-input">
						<input type="file" id="snapshtFile" name="snapshtFile" class="project-snapshot"/>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">VIDEO</div>
					<div class="upload-project-input">
						<select id="video" name="video" class="project-video">
							<option>Y</option>
							<option selected="selected">N</option>
						</select>
					</div>
				</div>
				
				
				<div class="upload-project">
					<div class="upload-project-name">DESC</div>
					<div class="upload-project-input">
						<textarea id="desc" name="desc" class="project-desc">
							<c:out value="${project.desc}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">CONTENTS</div>
					<div class="upload-project-input">
						<textarea name="contents" id="contents">
							<c:out value="${project.contents}" escapeXml="false"/>
						</textarea>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name">FILE</div>
					<div class="upload-project-input">
						<c:import url="../included/included_fileupload.jsp" charEncoding="UTF-8">
							<c:param name="boardType" value="project"/>
						</c:import>
					</div>
				</div>
				
				<div class="upload-project">
					<div class="upload-project-name"></div>
					<div class="upload-project-input">
						<input type="submit" class="project-submit" onclick="Progress.start()">
					</div>
				</div>
			</form>
			
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>