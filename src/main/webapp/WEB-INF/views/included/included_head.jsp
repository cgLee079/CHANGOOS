<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<title>CHANGOO'S</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" >

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/global-basic.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/global-responsive.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/letter-motion.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ckeditor/plugins/prism/lib/prism/prism_patched.min.css" />
<link href='https://fonts.googleapis.com/css?family=Lora:400italic' rel='stylesheet' type='text/css'>


<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.touchwipe.1.1.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/anime-2.2.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/letter-motion.js"></script>
<script src="${pageContext.request.contextPath}/resources/ckeditor/plugins/prism/lib/prism/prism_patched.min.js"></script>
<script>

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

var contextPath = "${pageContext.request.contextPath}";
function getContextPath(){
	return contextPath;
}

function getAttr(attr){
	return eval('('+ attr +')');
}
</script>