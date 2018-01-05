<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-nav.css" />
 
<sec:authorize access="isAnonymous()">
	<div class="admin-menu">
		<a href="${pageContext.request.contextPath}/login" class="btn btn-admin-login">로그인</a>
	</div>
</sec:authorize>
 
<sec:authorize access="hasRole('ROLE_ADMIN')">
  	<div class="admin-menu">
  		<a href="${pageContext.request.contextPath}/j_spring_security_logout" class="btn btn-admin-login">로그아웃</a>
  		<a href="${pageContext.request.contextPath}/admin/item/manage" class="btn btn-admin-login">프로젝트관리</a>
  		<a href="${pageContext.request.contextPath}/admin/photo/manage" class="btn btn-admin-login">사진관리</a>
  		<a href="${pageContext.request.contextPath}/admin/board/upload" class="btn btn-admin-login">게시판글쓰기</a>
  	</div>    
</sec:authorize>


<div class="nav-top row-center">
	<div class="wrap-home-logo col-center" onclick="window.location.href = '${pageContext.request.contextPath}/'">
		<div>CHANGOO'S</div>
		<div class="logo-line"></div>
	</div>
</div>
<div class="nav-occupy"></div>

<script>
function navOccupyReHeight(){
	var navTop = $(".nav-top");
	var height = navTop.outerHeight();
	$(".nav-occupy").height(height);
}
navOccupyReHeight();
$(window).resize(function(){
	navOccupyReHeight();
});

(function(){
	var drawingLine = undefined;
	$(".wrap-home-logo").hover(function(){
		drawingLine = anime({
			targets : ".logo-line",
			scale : [0, 1],
			easing : "easeInQuad",
			duration : 300
		})
	}, function(){
		drawingLine.play();
		drawingLine.reverse();
	});
		
})();
</script>
<div class="nav-icon">
	<span></span>
	<span></span>
	<span></span>
	<span></span>
</div>
<script>
(function(){
	function navBgResize(){
		$(".nav-background").css("width", "100%");
		$(".nav-background").css("height", "100%");
		
		var width = $(".nav-background").width();
		var height = $(".nav-background").height();
		var radius = undefined;
		
		if(width >= height) { radius = deviceWidth; }
		else { radius = deviceHeight; }
		
		$(".nav-background").css("width", radius);
		$(".nav-background").css("height", radius);
		$(".nav-background").css("top", -radius);
		$(".nav-background").css("left", -radius);
	}
	
	$(window).resize(function(){
		navBgResize();
	});
	
	$(".nav-icon").click(function(){
		$(this).toggleClass("open");
		
		navBgResize();
		
		if($(this).hasClass("open")){
			$(".nav-menu").removeClass("unvalid");
			$("html, body").addClass("scroll-no");
			$("html, body").on("scroll touchmove mousewheel", function(event) {
			   event.preventDefault();
			   event.stopPropagation();
			   return false;
			});
			
			var ani = anime.timeline({loop : false})
				.add({
					targets	: ".nav-background",
					easing: "easeInQuad",
					duration: 400,
					scale : [1, 3.5],
					borderRadius : ["100%", 0],
					background : ["#EDECED", "#FEFDFE"],
				}).add({
					targets : ".nav-menu",
					opacity : [0, 1],
					duration: 1,
					easing: "easeOutExpo"
				}).add({
					targets: ".ml1 .letter",
					scale: [0.3,1],
					opacity: [0,1],
					translateZ: 0,
					easing: "easeOutExpo",
					duration: 500,
					delay: function(el, i) {
					  return 50 * (i)
					}
				}).add({
					targets: ".nav-menu div",
					scale: [0,1],
					easing: "easeOutExpo",
					duration: 500,
					delay: function(el, i) {
					  return 50 * (i);
					},
					complete : function(){
						//$(".nav-menu div").addClass("btn-slideup");
					}
				})
		} else{
			var ani = anime.timeline({loop : false})
				.add({
					targets : ".nav-menu",
					opacity : [1, 0],
					duration: 100,
					easing: "easeOutExpo"
				}).add({
					targets	: ".nav-background",
					easing: "easeInQuad",
					duration: 500,
					scale : [3, 1],
					borderRadius : [0, "100%"],
					background : ["#FEFDFE", "#EDECED"]
				})
			
			$(".nav-menu div").removeClass("btn-slideup");
			$(".nav-menu").addClass("unvalid");
			$("html, body").off("scroll touchmove mousewheel");
			$("html, body").removeClass("scroll-no");
		}
	});
})();
</script>
<div class="nav-background"></div>
<div class="nav-menu unvalid col-center">
	<div class="btn-slideup">
		<a onclick="Progress.start()" href="${pageContext.request.contextPath}/">Home</a>
	</div>
	
	<div class="btn-slideup">
		<a onclick="Progress.start()" href="${pageContext.request.contextPath}/introduce">Introduce</a>
	</div>
	
	<div class="btn-slideup">
		<a onclick="Progress.start()" href="${pageContext.request.contextPath}/item">Projects</a>
	</div>
	
	<div class="btn-slideup">
		<a onclick="Progress.start()" href="${pageContext.request.contextPath}/photo">Photo</a>
	</div>
	
	<div class="btn-slideup">
		<a onclick="Progress.start()" href="${pageContext.request.contextPath}/board">Board</a>
	</div>
	
	<div class="btn-slideup">
		<a target="_blank" href="https://github.com/cglee079">Github</a>
	</div>
	
	<div class="btn-slideup">
		<a target="_blank" href="https://www.instagram.com/cglee079">Instagram</a>
	</div>
</div>
<div class="progress-bar display-none">
	<div class="spinner">
	  <div class="double-bounce1"></div>
	  <div class="double-bounce2"></div>
	</div>
</div>
<script>
var Progress ={
	start : function (){
		$(".progress-bar").removeClass("display-none");
	},
	stop : function(){
		$(".progress-bar").addClass("display-none");
	}
}
</script>
