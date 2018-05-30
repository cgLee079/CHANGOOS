<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-view.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-comment.css" />
<script src="${pageContext.request.contextPath}/resources/js/project/project-view.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-comment.js"></script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
		<div class="project">
			<div class="project-head">
				<div class="project-sect font-lora"><c:out value="${project.sect}"/></div>
				<div class="project-title"><c:out value="${project.title}"/></div>
				<div class="project-subinfo">
					<c:if test="${!empty project.date}">
						<a class="project-date">DATE : <c:out value="${project.date}"/></a> <br/>
						<div class="colum-border"></div>
					</c:if>
					<c:if test="${!empty project.developer}"> 
						<div class="project-developer">by ${project.developer}</div>
						<div class="colum-border"></div>
					</c:if>
					
					<div>조회수 <c:out value="${project.hits}"/></div>
					
					
					<c:if test="${!empty project.sourcecode}">
						<div class="colum-border"></div>
						<div class="btn row-center project-source">
							<div class="git-logo" style="background-image: url('${pageContext.request.contextPath}/resources/image/btn_projectview_source.png')"></div>
							<a target="_blank" href="${project.sourcecode}">SOURCE</a>
						</div>
					</c:if>
					
				</div>
				
				<div class="project-submenu">
					<a class="btn" onclick="projectList()">목록</a>
					<c:choose>
						<c:when test='${not empty beforeProject}'><c:set value="[${beforeProject.sect}] ${beforeProject.title}" var="beforeProejctTooltip" /></c:when>
						<c:otherwise><c:set value="더 이상 글이 없습니다." var="beforeProejctTooltip" /></c:otherwise>
					</c:choose>
					<a class="btn btn-project-before" title="<c:out value='${beforeProejctTooltip}'/>" onclick="projectView('${beforeProject.seq}')">이전글</a>

					<c:choose>
						<c:when test='${not empty afterProject}'><c:set value="[${afterProject.sect}] ${afterProject.title}" var="afterProjectTooltip" /></c:when>
						<c:otherwise><c:set value="더 이상 글이 없습니다." var="afterProjectTooltip" /></c:otherwise>
					</c:choose>
					<a class="btn btn-project-next" title="<c:out value='${afterProjectTooltip}'/>"onclick="projectView('${afterProject.seq}')">다음글</a>
				</div>
			</div>

			<div class="project-line"></div>
				   			
			<div class="project-content editor-contents">
				<c:if test="${project.video eq 'N'}">
					<img class="project-snapsht" src="${pageContext.request.contextPath}${project.snapsht}" >
				</c:if>
				<c:out value="${project.contents}" escapeXml="false"/>
				
				<c:if test="${!empty files}">
					<h3>첨부파일</h3>
					<div class="project-files">
						<c:forEach var="file" items="${files}">
							<fmt:formatNumber var="filesize" value="${file.size/(1024*1024)}" pattern="0.00"/>
							<div class="project-file">
								 <a onclick="downloadFile('${file.pathNm}')"> <c:out value="${file.realNm}"/> (<c:out value="${filesize}"/> MB)</a>
							</div>												
						</c:forEach>
					</div>
				</c:if>
			</div>
		
			<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
			   <c:param name = "perPgLine" value = "10" />
			   <c:param name = "boardType" value = "project" />
			   <c:param name = "boardSeq" value = "${project.seq}" />
			   <c:param name = "comtCnt" value = "${project.comtCnt}" />
			</c:import>
		</div>
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
	
</body>
</html>