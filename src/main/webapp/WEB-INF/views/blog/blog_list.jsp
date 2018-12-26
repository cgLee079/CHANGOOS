<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/blog/blog-list.css" />
<script src="${pageContext.request.contextPath}/resources/js/blog/blog-list.js"></script>

<c:set var="imageDir"><spring:eval expression="@location['blog.image.dir.url']"/></c:set>
<c:set var="thumbDir"><spring:eval expression="@location['blog.thumb.dir.url']"/></c:set>
<script>
var imageDir = '<c:out value="${imageDir}"/>';
var thumbDir = '<c:out value="${thumbDir}"/>';
</script>

</head>

<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<input type="hidden" id="allRowCnt" value="<c:out value='${count}'/>"/>
	
	<div class="wrap-blog">
		<div class="blog-first-item">
			<div class="blog-first-item-fg"></div>
			<div class="blog-first-item-snapsht"></div>
			<div class="blog-first-item-desc">
				<div class="prefix">최신글</div>
				<div class="title"></div>
				<div class="content"></div>
			</div>
		</div>		
		
		<div class="wrap-blog-list">
			<div class="blog-item-list">
				<div class="blog-item">
					<div class="blog-item-snapsht">
						<div class="blog-item-snapsht-fg"></div>
					</div>
					<div class="blog-item-desc">
						<div class="blog-item-title"></div>
						<div class="blog-item-content"></div>
						<div class="blog-item-empty"></div>
						<div class="blog-item-sub">
							<div class="blog-item-tag"></div>
							<div class="blog-item-comt">
							</div>
						</div>
					</div>
				</div>		
			</div>
			<div class="blog-tags">
				<c:forEach var="tag" items="${tags}">
					<a class="tag" onclick="doSearchTag(this)"><c:out value="${tag}"/></a>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>

</body>
</html>



