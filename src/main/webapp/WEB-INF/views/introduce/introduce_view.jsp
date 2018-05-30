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
	
	<div class="wrap-myinfo col-center">
		<div class="myinfo-view myinfo-view00">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view00.jpg)"></div>
			<div class="content-text font-lora">
				<h1 class="content-head">Developer CHANGOO LEE</h1>
				<div class="row-border"></div>
				<p>
				Hi. My name is <strong>Changoo Lee</strong>.<br/> 
				I am
					<jsp:useBean id="date" class="java.util.Date" />
					<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
					<c:out value="${currentYear - 1992 + 1}" />
				old and specialized Computer Engineering at Hansung University.<br/>
				When i'm 9 old, i got my first computer as present. 
				after that so many time in my life were spended to control computer.
				as a result, I love and very well use computer than other people.<br/>
				In 19 old i had to decide my major in university. but I didn't have to think about what major.
				Naturally, choiced Computer Enginnering.<br/>
				Of course, I know that many people very well use computer.
				i am not arrogant. so now i study, study, study to become one of best people my area.<br/>
				<br/>
				Thank you for reading. 
				</p>
			</div>
		</div>
		
			<div class="circle-border row-center">
			<div class="circle"></div>
			<div class="circle"></div>
			<div class="circle"></div>
		</div>
		
		<div class="myinfo-view myinfo-view02">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view02.jpg)"></div>
			<div class="content-text font-lora">
				<h1 class="content-head">Resume.</h1>
				<div class="row-border"></div>
				Coumputer Enginnering 
				Bachelor's degree at Hausng University. <br/>
				Grade &nbsp&nbsp<strong>4.22/4.5</strong>&nbsp&nbsp(Major <strong>4.4/4.5</strong>)<br/>
				Toeic &nbsp&nbsp&nbsp<strong>845</strong> <br/>
				<br/>
				<strong>Certificate</strong> <br/>
				2015.03.15 &nbsp&nbsp Craftsman Information Equipment Operation<br/>
				2015.09.11 &nbsp&nbsp GTQ<br/>
				2016.12.16 &nbsp&nbsp MOS master<br/>
				2017.03.16 &nbsp&nbsp Data Processing Engineer <br/>
				<br/>
				<strong>Current</strong><br/>
				Master of CoinNoticer Service
				
			</div>
		</div>
		
		<div class="circle-border row-center">
			<div class="circle"></div>
			<div class="circle"></div>
			<div class="circle"></div>
		</div>
		
		<div class="myinfo-view myinfo-view01">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view01.jpg)"></div>
			<div class="content-text font-lora">
				 <h1 class="content-head">My Biography.</h1>
				 <div class="row-border"></div>
				 <p>
					 <strong>2011.03</strong> &nbsp&nbsp Admissions C.E at Hansug University. <br/>
					 <strong>2012.05</strong> &nbsp&nbsp Military Service (21 Months). <br/>
					 <strong>2017.02</strong> &nbsp&nbsp Victory Graduate Competition <br/>
					 <strong>2017.07</strong> &nbsp&nbsp Intern at Nexgen Associate (Web, SI) <br/>
					 <strong>2018.02</strong> &nbsp&nbsp Graduated with honor from University <br/>
					 <strong>2018.03</strong> &nbsp&nbsp Develop CoinNoticer Service<br/>
				 </p>
			</div>
			
		</div>
		
		<div class="circle-border row-center">
			<div class="circle"></div>
			<div class="circle"></div>
			<div class="circle"></div>
		</div>
		
		<div class="myinfo-view myinfo-view03">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view03.jpg)"></div>
			<div class="content-text font-lora">
				<h1 class="content-head">Contact Me.</h1>
				<div class="row-border"></div>
				<strong>Name.</strong> 이찬구(Changoo Lee) <br/>
				<strong>Email.</strong> cgLee079@gmail.com <br/>
				<strong>Tel.</strong> 010 - 2062 - 2979 <br/>
				<br/>
				<h3>Please leave a guestbook.</h3>
				<div class="message-form">
					<form>
						<textarea class="input-message"></textarea>
						<div class="submit-message"><a class="submit-btn" onclick="doSendMessage()">SEND</a></div>
					</form>
				</div>
			</div>
		</div>
		
	</div>
	
	<div class="btn-views display-none">
		<div class="btn-view btn-view00 on"></div>
		<div class="btn-view btn-view01"></div>
		<div class="btn-view btn-view02"></div>
		<div class="btn-view btn-view03"></div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


