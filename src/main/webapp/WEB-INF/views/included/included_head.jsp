<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<title>CHANGOO'S</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" >

<!-- font -->
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-lora.css'/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-nanumgothic.css'/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-nanumsquare.css'/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-misaeng.css'/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-bm.css'/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-kcc.css'/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-apple-sd.css'/>

<!-- lib-css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/lib/letter-motion.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ckeditor/plugins/prism/lib/prism/prism_patched.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/themes/material/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/themes/color.css" />

<!--  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-nav.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-footer.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/global-basic.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/global-responsive.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/editor-contents.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dg-common.css" />

<!-- lib-js -->
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery.touchwipe.1.1.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/anime-2.2.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/letter-motion.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/sweetalert.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/vue.js"></script>
<script src="${pageContext.request.contextPath}/resources/ckeditor/plugins/prism/lib/prism/prism_patched.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-detailview.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-groupview.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/datagrid-cellediting.js"></script>

<!--  -->
<script src="${pageContext.request.contextPath}/resources/js/dg-common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/editor-contents-resizer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-nav.js"></script>

<script>

/* context Path */
var contextPath = "${pageContext.request.contextPath}";
function getContextPath(){ return contextPath; }

/* check, is mobile?*/
var isMobile = false;
var deviceWidth = undefined;
var deviceHeight = undefined;

function checkDevice(){
	deviceWidth 	= Math.min(window.innerWidth || Infinity, screen.width);
	deviceHeight	= Math.min(window.innerHeight || Infinity, screen.height);
	if(deviceWidth <= 720){
		isMobile = true;
	}
	
}

checkDevice();
$(window).resize(function(){
	checkDevice();
})

$(document).ready(function(){
	//$("*").css("font-family", "Nanum Gothic");
	$("html, body").css("font-family", "Nanum Square, Nanum Gothic");
	//$("*").css("font-family", "MBKCorporateACon");

})


</script>