<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/introduce/introduce-view.css" />
<script src="${pageContext.request.contextPath}/resources/js/introduce/introduce-view.js"></script>
</head>

<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />

		<div class="wrap-myinfo">
			<div class="introduce">
				<div class="introduce-bg"
					style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view05.jpg)"></div>
				<div class="introduce-text">
					<div class="introduce-head">LEE CHANGOO</div>
					<div class="introduce-desc">
						<c:out value="${intro001}" escapeXml="false"></c:out>
					</div>
				</div>
			</div>

			<div class="detail">
				<div class="contents editor-contents">
					<c:out value="${intro002}" escapeXml="false"></c:out>
				</div>
				
				<div class="download">
					<a href="${pageContext.request.contextPath}/introduce/resume">이력서 다운로드</a>
				</div>
			</div>
			
			
		
		</div>
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
	</div>
</body>
</html>


