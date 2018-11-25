<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/introduce/introduce-view.css" />
<script src="${pageContext.request.contextPath}/resources/js/introduce/introduce-view.js"></script>
</head>

<body>
<div class="wrapper">
	<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
	
	<div class="wrap-myinfo">
		<div class="introduce">
			<div class="introduce-bg" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view00.jpg)"></div>
			<div class="introduce-text">
				<div class="introduce-head">CHANGOO LEE</div>
				<div class="introduce-desc">
					<c:out value="${intro001}" escapeXml="false"></c:out>
				</div>
			</div>
		</div>
		
		<div class="spec">
			<div class="spec-text">
				<div class="spec-head">Spec.</div>
				<div class="spec-desc">
					<c:out value="${intro002}" escapeXml="false"></c:out>
				</div>
				<div style="display: flex; justify-content: space-between;">
					<div style="flex:1;"></div>
					<!-- <div class="download-resume black-button" onclick="doDownloadResume()">RESUME</div> -->
				</div>
			</div>
			<div class="spec-image" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view01.jpg)"></div>
			
		</div>
		
		<div class="biography">
			<div class="biography-image" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view02.jpg)"></div>
			<div class="biography-text">
				<div class="biography-head">Biography.</div>
				<div class="biography-desc">
					<c:out value="${intro003}" escapeXml="false"></c:out>
				</div>
			</div>
		</div>
		
		<div class="contact">
			<div class="contact-head">Leave Message</div>
			<div class="contact-input-message">
				<form>
					<textarea class="input-message"></textarea>
					<div style="display: flex; justify-content: space-between;">
						<div style="flex:1;"></div>
						<div class="submit-message black-button" onclick="doSendMessage()">SEND</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


