<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study/study-view.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-comment.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-export.css" />
<script src="${pageContext.request.contextPath}/resources/js/study/study-view.js"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-comment.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-export.js"></script>

</head>
<body>
	<div class="wrapper">
		<input type="hidden" id="category" value="<c:out value='${category}'/>"/>
		
		<c:if test="${fn:length(study.images) > 0}" >
			<c:set var="thumbnail" value="${studyImageDir}${study.images[0].pathname}"/>
		</c:if>
		
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		<c:import url="../included/included_export.jsp" charEncoding="UTF-8">
			<c:param name = "thumbnail" value = "${thumbnail}" />
			<c:param name = "title" value = "[스터디] ${study.title}" />
			<c:param name = "hits" value = "${study.hits}" />
			<c:param name = "comtCnt" value = "${study.comtCnt}" />
		</c:import>
		
		<div class="wrap-study">
			<div class="study">
				<div class="study-head">
					<div class="study-category" onclick="$('#category').val('${study.category}'); studyList();">
						<c:out value="${study.category}"/>
					</div>
					<div class="study-title"><c:out value="${study.title}"/></div>
					<div class="study-info">
						<c:if test="${not empty study.codeLang}">
							<div><c:out value="${study.codeLang}"/></div>
							<div class="colum-border"></div>
						</c:if>
						<div><c:out value="${study.date}"/></div>
						<div class="colum-border"></div>
						<div>조회수 <c:out value="${study.hits}"/></div>
					</div>
				</div>
				
				<div class="study-sub">
					<c:if test="${!empty files}">
						<div class="study-files">
							<c:forEach var="file" items="${files}">
								<fmt:formatNumber var="filesize" value="${file.size/(1024*1024)}" pattern="0.00"/>
								<div class="file-item" onclick="downloadFile('${studyFileDir}', '${file.pathname}', '${file.filename}')">
									 <div class="name"><c:out value="${file.filename}"/> </div>
									 <div class="size">(<c:out value="${filesize}"/> MB)</div>
								</div>												
							</c:forEach>
						</div>
					</c:if>
					
					<div></div>
			
					<div class="study-submenus">
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<div class="submenu" onclick="studyModify('${study.seq}')">수정</div>
							<div class="submenu" onclick="studyDelete('${study.seq}')">삭제</div>
						</sec:authorize>
						<div class="submenu" onclick="drawExportView()">공유하기</div>
						<div class="submenu" onclick="studyList()">목록</div>
						
						<c:choose>
							<c:when test='${not empty beforeStudy}'><c:set value="${beforeStudy.title}" var="beforeStudyTooltip" /></c:when>
							<c:otherwise><c:set value="더 이상 글이 없습니다." var="beforeStudyTooltip" /></c:otherwise>
						</c:choose>
						<div class="submenu study-before" title="<c:out value='${beforeStudyTooltip}'/>" onclick="studyView('${beforeStudy.seq}')">이전글</div>
						
						<c:choose>
							<c:when test='${not empty afterStudy}'><c:set value="${afterStudy.title}" var="afterStudyTooltip" /></c:when>
							<c:otherwise><c:set value="더 이상 글이 없습니다." var="afterStudyTooltip" /></c:otherwise>
						</c:choose>
						<div class="submenu study-next" title="<c:out value='${afterStudyTooltip}'/>"onclick="studyView('${afterStudy.seq}')">다음글</div>
					</div>
				</div>

				<div class="study-contents editor-contents">
					<c:out value="${study.contents}" escapeXml="false"/>
				</div>
			</div>
			
			<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
				<c:param name = "perPgLine" value = "10" />
				<c:param name = "boardType" value = "${boardType.STUDY.val}" />
				<c:param name = "boardSeq" value = "${study.seq}" />
				<c:param name = "comtCnt" value = "${study.comtCnt}" />
			</c:import>
		</div>	
	
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
	</div>
</body>
</html>