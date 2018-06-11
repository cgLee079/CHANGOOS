<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study/study-view.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-comment.css" />
<script src="${pageContext.request.contextPath}/resources/js/study/study-view.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-comment.js"></script>

</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<input type="hidden" id="section" value="<c:out value='${section}'/>"/>
		<input type="hidden" id="studyPage" value="<c:out value='${page}'/>"/>
		
		<div class="wrap-study">
			<div class="study">
				<div class="study-head">
					<div class="study-sect"><c:out value="${study.sect}"/></div>
					<div class="study-title"><c:out value="${study.title}"/></div>
					<div class="study-info">
						<c:if test="${not empty study.codeLang }">
							<div><c:out value="${study.codeLang}"/></div>
							<div class="colum-border"></div>
						</c:if>
						<div><c:out value="${study.date}"/></div>
						<div class="colum-border"></div>
						<div>조회수 <c:out value="${study.hits}"/></div>
					</div>
					
					<div class="study-submenu">
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<a class="btn" onclick="studyModify('${study.seq}')">수정</a>
							<a class="btn" onclick="studyDelete('${study.seq}')">삭제</a>
						</sec:authorize>
						
						<a class="btn" onclick="studyList()">목록</a>
						
						<c:choose>
							<c:when test='${not empty afterStudy}'><c:set value="${afterStudy.title}" var="afterStudyTooltip" /></c:when>
							<c:otherwise><c:set value="더 이상 글이 없습니다." var="afterStudyTooltip" /></c:otherwise>
						</c:choose>
						<a class="btn btn-study-before" title="<c:out value='${afterStudyTooltip}'/>" onclick="studyView('${afterStudy.seq}')">이전글</a>
						
						<c:choose>
							<c:when test='${not empty beforeStudy}'><c:set value="${beforeStudy.title}" var="boforeStudyTooltip" /></c:when>
							<c:otherwise><c:set value="더 이상 글이 없습니다." var="boforeStudyTooltip" /></c:otherwise>
						</c:choose>
						<a class="btn btn-study-next" title="<c:out value='${boforeStudyTooltip}'/>"onclick="studyView('${beforeStudy.seq}')">다음글</a>
					</div>
				</div>

				<c:if test="${!empty files}">
					<div class="study-files"">
						<div class="study-file-button">
							<div class ="icon" style= "background-image: url('${pageContext.request.contextPath}/resources/image/btn-study-savefile.svg');"></div>
							<div>첨부파일</div>
						</div>
						<div class="file-list">
							<c:forEach var="file" items="${files}">
								<fmt:formatNumber var="filesize" value="${file.size/(1024*1024)}" pattern="0.00"/>
								<div class="file-item">
									 <a onclick="downloadFile('${file.pathNm}')">
									 	<c:out value="${file.realNm}"/>(<c:out value="${filesize}"/> MB)
									 </a>
								</div>												
							</c:forEach>
						</div>
					</div>
				</c:if>
				
				<div class="study-contents editor-contents">
					<c:out value="${study.contents}" escapeXml="false"/>
				</div>
			</div>
			
			<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
			   <c:param name = "perPgLine" value = "10" />
			   <c:param name = "boardType" value = "study" />
			   <c:param name = "boardSeq" value = "${study.seq}" />
			   <c:param name = "comtCnt" value = "${study.comtCnt}" />
			</c:import>
		</div>	
	
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
	</div>
</body>
</html>