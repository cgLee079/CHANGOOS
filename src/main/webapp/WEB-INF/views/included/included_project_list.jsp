<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="project-list">
	<c:forEach var="project" items="${projects}">
		<div onclick="" class="project-view">
			<div onclick="projectView(${project.seq})" class="project-snapsht" style="background-image: url('${pageContext.request.contextPath}${project.snapsht}')">
				<span class="project-snapsht-overlay">SHOW</span>
				<div class="project-snapsht-fg"></div>
			</div>
			<div class="project-info">
				<div class="project-title">[<c:out value="${project.sect}"/>]<c:out value="${project.title}"/></div>
				<div class="row-border"></div>
				<div class="project-desc editor-contents"><c:out value="${project.desc}" escapeXml="false"/></div>
				<div class="project-menu">
					<div class="btn-slideup btn-project-show">
						<a onclick="projectView(${project.seq})">SHOW</a>	
					</div>
				</div>
			</div>
			<input type="hidden" id="project-seq" class="project-seq" value="${project.seq}">
		</div>
	</c:forEach>
</div>
