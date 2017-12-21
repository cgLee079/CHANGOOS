<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board-view-basic.css" />
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<div class="wrap-board">
			<div class="board-submenu">
				<a class="btn" href="${pageContext.request.contextPath}/board">목록</a>
				<a class="btn">이전글</a>
				<a class="btn">다음글</a>
			</div>
			<div class="board-detail">
				<div class="board-head">
					<div class="board-title">${board.title}</div>
					<div style="height : 1px; background: #CCC; margin: 0.5rem 0rem" ></div>
					<div class="board-info">
						<a>${board.sect}</a>
						<a>${board.date}</a>
						<a>조회수 ${board.hits}</a>
					</div>
				</div>
				
				<div class="board-contents">	
						<h2>OCP, Open-Closed Principle (개방-폐쇄 원칙)</h2>
					
						<p>소프트웨어 개체(클래스, 모듈, 함수 등등)는 확장에 대해 열려 있어야 하고,<br/>
						수정에 대해서는 닫혀 있어야 한다<br/>
						<br/>
						기존의 코드를 변경하지 않으면서, 기능을 추가 할 수 있도록 설계한다.</p>
						
						<p><br/>
						&nbsp;</p>
						
						<h3>#Before Branch</h3>
						
						<p><img alt="" src="/resources/image/item/contents/content_171205_132752_OCP before.jpg" style="height:300px; width:470px"/><br/>
						<br/>
						AreaCalculator 클래스는 shape들의 넓이의 합을 계산하고.<br/>
						ConsolePrinter 클래스는 결과를 출력한다.<br/>
						<br/>
						이 프로그램은 OCP원칙이 위배됨을 보여준다</p>
						
						<p>&nbsp;</p>
						
						<p>&nbsp;</p>
						
						<p><strong>AreaCaclualotr.java</strong></p>
				</div>
			</div>
		</div>	
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
		<c:if test="${!empty beforeItem}">
			<div class="btn btn-item-before h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
		
		<c:if test="${!empty afterItem}">
			<div class="btn btn-item-next" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
	</div>
</body>
</html>