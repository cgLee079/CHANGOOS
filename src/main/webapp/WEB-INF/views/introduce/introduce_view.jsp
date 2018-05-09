<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<div class="myinfo-views">
			<div class="myinfo-view myinfo-view00">
				<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view00.jpg)"></div>
				<div class="content-text font-lora">
					<h1 class="content-head">Who am I?</h1>
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
			
			<div class="myinfo-view myinfo-view01">
				<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view01.jpg)"></div>
				<div class="content-text font-lora">
					 <h1 class="content-head">History.</h1>
					 <div class="row-border"></div>
					 <p>
						 <strong>2011.03</strong> &nbsp&nbsp Admissions C.E at Hansug University. <br/>
						 <strong>2012.05</strong> &nbsp&nbsp Military Service (21 Months). <br/>
						 <strong>2017.02</strong> &nbsp&nbsp Victory Graduate Competition <br/>
						 <strong>2017.07</strong> &nbsp&nbsp Intern at Nexgen Associate (Web, SI) <br/>
						 <strong>2018.02</strong> &nbsp&nbsp Graduated from university <br/>
					 </p>
				</div>
				
			</div>
			
			<div class="myinfo-view myinfo-view02">
				<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view02.jpg)"></div>
				<div class="content-text font-lora">
					<h1 class="content-head">Resume.</h1>
					<div class="row-border"></div>
					Coumputer Enginnering 
					Bachelor's degree at Hausng University. <br/>
					Grade &nbsp&nbsp<strong>4.22 / 4.5</strong> <br/>
					Toeic &nbsp&nbsp&nbsp<strong>845</strong> <br/>
					Data Processing Engineer, <br/>
					Craftsman Information Equipment Operation,<br/>
					MS master, GTQ
				</div>
			</div>
			
			<div class="myinfo-view myinfo-view03">
			<div class="content-picture" style="background-image: url(${pageContext.request.contextPath}/resources/image/introduce/bg_introduce_view03.jpg)"></div>
				<div class="content-text font-lora">
					<h1 class="content-head">CONTACT.</h1>
					<div class="row-border"></div>
					<strong>Name.</strong> 이찬구(Changoo Lee) <br/>
					<strong>Email.</strong> cgLee079@gmail.com <br/>
					<strong>Tel.</strong> 010 - 2062 - 2979 <br/>
					<br/>
					<h3>Send Message</h3>
					<div class="message-form">
						<form>
							<textarea class="input-message"></textarea>
							<div class="submit-message"><a class="submit-btn">SEND</a></div>
						</form>
					</div>
				</div>
			</div>
			
		</div>
		
		<div class="btn-views">
			<div class="btn-view btn-view00 on"></div>
			<div class="btn-view btn-view01"></div>
			<div class="btn-view btn-view02"></div>
			<div class="btn-view btn-view03"></div>
		</div>
	</div>
	
	<c:import url="../included/included_footer.jsp" charEncoding="UTF-8" />
</div>
</body>
</html>


