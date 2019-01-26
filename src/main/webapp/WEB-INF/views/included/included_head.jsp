<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<title>CHANGOO'S</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" >

<!-- favicon -->
<link rel="apple-touch-icon" sizes="57x57"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180"  href="${pageContext.request.contextPath}/resources/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"   href="${pageContext.request.contextPath}/resources/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32"  href="${pageContext.request.contextPath}/resources/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96"  href="${pageContext.request.contextPath}/resources/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16"  href="${pageContext.request.contextPath}/resources/favicon/favicon-16x16.png">

<!-- font -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font/font-flex.css" />
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-nanumsquare.css'/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/font/font-nanumgothic.css'/>
 
<!-- lib-css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ckeditor/plugins/prism/lib/prism/prism_patched.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/themes/material/easyui.css"/>

<!--  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-nav.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/included/included-footer.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/editor-contents.css" />


<!-- Javascript 라이브러리-->
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/jquery-ui-1.12.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/sweetalert.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/cg-autocomplete.js"></script>
<script src="${pageContext.request.contextPath}/resources/ckeditor/plugins/prism/lib/prism/prism_patched.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery-easyui-1.5.5/jquery.easyui.min.js"></script>

<!--  -->
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pager-1.0.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/editor-contents-resizer.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/included/included-nav.js"></script>

<!--  Property -->
<c:set var="projectThumbDir"><spring:eval expression="@location['project.thumb.dir.url']"/></c:set>
<c:set var="projectImageDir"><spring:eval expression="@location['project.image.dir.url']"/></c:set>
<c:set var="projectFileDir"><spring:eval expression="@location['project.file.dir.url']"/></c:set>
<c:set var="studyImageDir"><spring:eval expression="@location['study.image.dir.url']"/></c:set>
<c:set var="studyFileDir"><spring:eval expression="@location['study.file.dir.url']"/></c:set>
<c:set var="blogThumbDir"><spring:eval expression="@location['blog.thumb.dir.url']"/></c:set>
<c:set var="blogImageDir"><spring:eval expression="@location['blog.image.dir.url']"/></c:set>
<c:set var="blogFileDir"><spring:eval expression="@location['blog.file.dir.url']"/></c:set>
<c:set var="photoThumbDir"><spring:eval expression="@location['photo.thumb.dir.url']"/></c:set>
<c:set var="photoOriginDir"><spring:eval expression="@location['photo.origin.dir.url']"/></c:set>
<c:set var="tempDir" ><spring:eval expression="@location['temp.dir.url']"/><c:out value="${sessionScope.tempDirId}"/></c:set>

<script>
var loc = {
	project : {
		thumbDir: '<c:out value="${projectThumbDir}"/>',
		imageDir: '<c:out value="${projectImageDir}"/>',
		fileDir	: '<c:out value="${projectFileDir}"/>'
	},
	study:{
		imageDir: '<c:out value="${studyImageDir}"/>',
		fileDir	: '<c:out value="${studyFileDir}"/>'
	},
	blog :{
		thumbDir: '<c:out value="${blogThumbDir}"/>',
		imageDir: '<c:out value="${blogImageDir}"/>',
		fileDir	: '<c:out value="${blogFileDir}"/>'
	}, 
	photo :{
		thumbDir: '<c:out value="${photoThumbDir}"/>',
		originDir: '<c:out value="${photoOriginDir}"/>',
	}, 
	temp:{
		dir : '<c:out value="${tempDir}"/>'
	}
}


</script>

<script>
/* context Path */
var contextPath = "${pageContext.request.contextPath}";
function getContextPath(){ return contextPath; }
</script>