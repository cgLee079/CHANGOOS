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
					안녕하십니까? 개발자  <strong>이찬구</strong>입니다.<br/>
					개발자로서의 저의 목표는 누군가에 편리함을 제공해주는 것입니다. <br/>
					이런 목표는 웹, 모바일 등 다양한 플랫폼에서 가능하다고 생각합니다. <br/>
					한 예로 가상화폐 가격 정보 알림 서비스의 필요성을 느꼈습니다. <br/>
					어떠한 이득이 있지는 않지만, 누군가에게 편리함을 제공해주겠다는 기쁨으로 서비스를 개발하였습니다.<br/>
					현재는 누적 사용자 수는 2000명 정도이며, 일별 약 1000개의 메시지를 받고 있습니다.<br/>
					그 결과 수많은 사용자에게 감사 인사를 받았고, 비록 소액이지만 후원금을 보내주신 사용자도 있었습니다.<br/>
					이제 저는 개인적 목표에 한발을 디뎠습니다. 앞으로의 기나긴 여정 또한 목표를 향해 달려갈 것입니다<br/>
				</div>
			</div>
		</div>
		
		<div class="spec">
			<div class="spec-text">
				<div class="spec-head">Spec.</div>
				<div class="spec-desc">
					<strong>한성대학교 컴퓨터공학부 학사</strong><br/>
					학점 &nbsp&nbsp<strong>4.22/4.5</strong>&nbsp&nbsp(전공학점 <strong>4.39/4.5</strong>)<br/>
					토익 &nbsp&nbsp<strong>845</strong> <br/>
					<br/>
					<strong>자격증.</strong> <br/>
					2015.03.15 &nbsp&nbsp 정보기기운용기능사<br/>
					2015.09.11 &nbsp&nbsp 국가정보기술자격증 GTQ 1급<br/>
					2016.12.16 &nbsp&nbsp MOS 마스터<br/>
					2017.03.16 &nbsp&nbsp 정보처리기사 <br/>
					<br/>
					
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
					 <strong>2011.03</strong> &nbsp&nbsp 한성대학교 컴퓨터공학부 입학 <br/>
					 <strong>2012.05</strong> &nbsp&nbsp 병역 <br/>
					 <strong>2017.02</strong> &nbsp&nbsp 졸업프로젝트 대상 <br/>
					 <strong>2017.07</strong> &nbsp&nbsp (주)넥스젠어쏘시에이트 인턴 (Web, SI) <br/>
					 <strong>2018.02</strong> &nbsp&nbsp 한성대학교 컴퓨터공학부 수석 졸업 <br/>
					 <strong>2018.03</strong> &nbsp&nbsp 가상화폐 알리미 개발 및 릴리즈<br/>
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


