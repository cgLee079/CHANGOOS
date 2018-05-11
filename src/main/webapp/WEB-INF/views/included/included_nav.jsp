<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Header -->
<div class="header-occupy"></div>
<div class="header row-center">
	<div class="wrap-home-logo col-center">
		<div class="logo" onclick="window.location.href = '${pageContext.request.contextPath}/'">CHANGOO'S</div>
		<div class="logo-line"></div>
	</div>
	
	<sec:authorize access="isAnonymous()">
		<div class="header-admin-menu row-center">
			<a href="${pageContext.request.contextPath}/login" class="btn btn-admin-login">관리자</a>
		</div>
	</sec:authorize>
	 
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	  	<div class="header-admin-menu row-center">
	  		<a href="${pageContext.request.contextPath}/j_spring_security_logout" class="btn btn-admin-login">로그아웃 </a>
	  		<a href="${pageContext.request.contextPath}/admin/project/manage" class="btn btn-admin-login">프로젝트관리 </a>
	  		<a href="${pageContext.request.contextPath}/admin/photo/manage" class="btn btn-admin-login">사진관리 </a>
	  		<a href="${pageContext.request.contextPath}/admin/board/manage" class="btn btn-admin-login">게시판관리 </a>
	  	</div>    
	</sec:authorize>
</div>

<!-- Navigation -->
<div class="wrap-nav-icon row-center">
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
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/">Home</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">Introduce</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/project">Projects</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/board">Board</a></div>
	<div class="btn-slideup"><a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">Photo</a></div>
	<div class="btn-slideup"><a target="_blank" href="https://github.com/cglee079">Github</a></div>
	<div class="btn-slideup"><a target="_blank" href="https://www.instagram.com/cglee079">Instagram</a></div>
</div>

<!--  Top btn. -->
<div class="btn btn-scroll-top col-center off">
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
