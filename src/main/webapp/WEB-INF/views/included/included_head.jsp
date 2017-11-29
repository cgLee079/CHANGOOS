<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<title>Lee Changoo</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" >

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/global-basic.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/global-responsive.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included-nav-basic.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included-nav-responsive.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/letter-motion.css" />

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.touchwipe.1.1.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/anime2.2.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/letter-motion.js"></script>

<script>
/*
var isMobile = {
	    Android: function() {
	        return navigator.userAgent.match(/Android/i);
	    },
	    BlackBerry: function() {
	        return navigator.userAgent.match(/BlackBerry/i);
	    },
	    iOS: function() {
	        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
	    },
	    Opera: function() {
	        return navigator.userAgent.match(/Opera Mini/i);
	    },
	    Windows: function() {
	        return navigator.userAgent.match(/IEMobile/i);
	    },
	    any: function() {
	        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
	    }
	};

if( isMobile.any() ) { console.log("isMobile")}
else {console.log("is Not Mobile");}
*/
var isMobile = false;
var deviceWidth = undefined;
var deviceHeight = undefined;
function checkDevice(){
	deviceWidth = Math.min(window.innerWidth || Infinity, screen.width);
	deviceHeight = Math.min(window.innerHeight || Infinity, screen.height);
	if(deviceWidth <= 440){
		isMobile = true;
	}
}
checkDevice();

$(window).resize(function(){
	checkDevice();
})

if(!isMobile){
	var link = document.createElement("link");
	link.rel = "stylesheet";
	link.href = "${pageContext.request.contextPath}/resources/css/font-nanumgothic.css";
	document.head.appendChild(link);
	$("*").css("font-family", "Nanum Gothic");
}



</script>