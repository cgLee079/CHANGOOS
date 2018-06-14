<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/blog/blog-view.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-comment.css" />
<script src="${pageContext.request.contextPath}/resources/js/blog/blog-view.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-comment.js"></script>

</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-blog">
			<div class="blog-head">
				<c:choose>
					<c:when test="${not empty blog.snapsht}" >
					<div class="blog-head-bg" style="background-image: url('${blog.snapsht}')"></div>
					</c:when>
					<c:otherwise>
					<div class="blog-head-bg" style="background: #000"></div>
					</c:otherwise>
				</c:choose>
				<div class="blog-head-fg"></div>
				<div class="blog-head-desc">
					<div class="tag"><c:out value="${blog.tag}"/></div>
					<div class="title"><c:out value="${blog.title}"/></div>
					<div class="subinfo">
						<div><c:out value="${blog.date}"/></div>
						<div class="colum-border"></div>
						<div>조회수 <c:out value="${blog.hits}"/></div>
					</div>
				</div>
				<div class="blog-head-submenu">
					<a class="btn-list" href="${pageContext.request.contextPath}/blog">목록</a>
				</div>
			</div>
			
			<div class="blog-main">
				<div class="blog-contents editor-contents">
					<c:out value="${blog.contents}" escapeXml="false"/>
				</div>
				
				<c:import url="../included/included_comment.jsp" charEncoding="UTF-8">
				   <c:param name = "perPgLine" value = "10" />
				   <c:param name = "boardType" value = "blog" />
				   <c:param name = "boardSeq" value = "${blog.seq}" />
				   <c:param name = "comtCnt" value = "${blog.comtCnt}" />
				</c:import>
			</div>
			
			
		</div>	
	
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
	</div>
</body>
</html>