<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Header -->
<div class="header-occupy"></div>
<div class="header">
	<div class="wrap-home-logo col-center">
		<img src="${pageContext.request.contextPath}/resources/image/icon-logo.svg"  class="logo" onclick="window.location.href = '${pageContext.request.contextPath}/'" ></img>
	</div>
	<div class="web-menus">
		<sec:authorize access="hasRole('ROLE_ADMIN')">
	  		<div class="btn-web-menu menu-logout"><a class="btn" href="${pageContext.request.contextPath}/j_spring_security_logout">로그아웃 </a></div>
	  		<div class="btn-web-menu menu-mgnt-project"><a class="btn" href="${pageContext.request.contextPath}/mgnt/project">프로젝트관리 </a></div>
	  		<div class="btn-web-menu menu-mgnt-study"><a class="btn" href="${pageContext.request.contextPath}/mgnt/study">스터디관리 </a></div>
	  		<div class="btn-web-menu menu-mgnt-blog"><a class="btn" href="${pageContext.request.contextPath}/mgnt/blog">블로그관리 </a></div>
	  		<div class="btn-web-menu menu-mgnt-photo"><a class="btn" href="${pageContext.request.contextPath}/mgnt/photo">사진관리 </a></div>
		</sec:authorize>
	
		<div class="btn-web-menu menu-about"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">이력</a></div>
		<div class="btn-web-menu menu-project"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/project">프로젝트</a></div>
		<div class="btn-web-menu menu-study"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/study">스터디</a></div>
		<div class="btn-web-menu menu-blog"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/blog">블로그</a></div>
		<div class="btn-web-menu menu-photo"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">사진</a></div>
		
	</div>
	
	
</div>

<!-- Navigation -->
<div class="wrap-nav-icon">
	<div class="nav-icon">
		<span></span>
		<span></span>
		<span></span>
		<span></span>
	</div>
</div>
	
<div class="nav-background-wrapper">
	<div class="nav-background"></div>
</div>
<div class="nav-menu unvalid col-center">
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">이력</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/project">프로젝트</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/study">스터디</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/blog">블로그</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">사진</a></div>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/mgnt/project">프로젝트관리 </a></div>
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/mgnt/study">스터디관리 </a></div>
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/mgnt/blog">블로그관리 </a></div>
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/mgnt/photo">사진관리 </a></div>
 		<div class="btn-slideup"><a href="${pageContext.request.contextPath}/j_spring_security_logout">로그아웃 </a></div>
	</sec:authorize>
</div>

<!--  Top btn. -->
<div class="btn-scroll-top col-center off">
	<a>TOP</a>
</div>

<!-- Progress-bar. -->
<div class="progress-bar off">
	<div class="spinner">
		<div class="double-bounce1"></div>
		<div class="double-bounce2"></div>
	</div>
</div>


<!--  main 	-->
