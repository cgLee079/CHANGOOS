<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/project/project-list.css" /> 
<script src="${pageContext.request.contextPath}/resources/js/project/project-list.js"></script> 

</head>
<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />

	<div class="wrap-project-list">
		<div class="project-list">
			<c:forEach var="project" items="${projects}">
				<div onclick="" class="project-view">
					<div onclick="projectView(${project.seq})" class="project-snapsht">
						<div class="project-snapsht-img" style="background-image: url('${pageContext.request.contextPath}${projectThumbDir}${project.thumbnail}')"></div>
						<span class="project-snapsht-overlay"><c:out value="${project.subtitle}"/></span>
						<div class="project-snapsht-fg"></div>
					</div>
					<div class="project-info">
						<div class="project-title"><c:out value="${project.title}"/></div>
						<div class="project-desc"><c:out value="${project.desc}" escapeXml="false"/></div>
					</div>
					
					<div class="project-subinfo">
						<div>By <c:out value="${project.developer}"/></div>
						<div class="colum-border"></div>
						<div>조회수 <c:out value="${project.hits}"/></div>
						<div class="colum-border"></div>
						<div>댓글 <c:out value="${project.comtCnt}"/></div>
					</div>
					<div class="bottom-border"></div>
					<input type="hidden" id="project-seq" class="project-seq" value="${project.seq}">
				</div>
			</c:forEach>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


