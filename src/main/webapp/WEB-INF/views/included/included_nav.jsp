<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Header -->
<div class="header-occupy"></div>
<div class="header">
	<div class="wrap-home-logo col-center">
		<div class="logo" onclick="window.location.href = '${pageContext.request.contextPath}/'">CHANGOO'S</div>
	</div>
	<div class="web-menus">
		<sec:authorize access="hasRole('ROLE_ADMIN')">
	  		<div class="btn-web-menu"><a href="${pageContext.request.contextPath}/j_spring_security_logout" class="btn btn-admin-login">로그아웃 </a></div>
	  		<div class="btn-web-menu"><a href="${pageContext.request.contextPath}/mgnt/project" class="btn btn-admin-login">프로젝트관리 </a></div>
	  		<div class="btn-web-menu"><a href="${pageContext.request.contextPath}/mgnt/photo" class="btn btn-admin-login">사진관리 </a></div>
	  		<div class="btn-web-menu"><a href="${pageContext.request.contextPath}/mgnt/study" class="btn btn-admin-login">공부관리 </a></div>
	  		<div class="btn-web-menu"><a href="${pageContext.request.contextPath}/mgnt/blog" class="btn btn-admin-login">블로그 관리 </a></div>
		</sec:authorize>
	
		<div class="btn-web-menu menu-about"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">ABOUT</a></div>
		<div class="btn-web-menu menu-project"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/project">PROJECT</a></div>
		<div class="btn-web-menu menu-study"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/study">STUDY</a></div>
		<div class="btn-web-menu menu-blog"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/blog">BLOG</a></div>
		<div class="btn-web-menu menu-photo"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">PHOTO</a></div>
		
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
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">ABOUT</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/project">PROJECT</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/study">STUDY</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/blog">PHOTO</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">BLOG</a></div>
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
