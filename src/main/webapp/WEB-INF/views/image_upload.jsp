<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp"%>
<script type="text/javascript">
function popup(){
	var windowOpner = window.open(getContextPath()  + "/img/popup", "_blank " , {width : 300, height : 300});
}
</script>
</head>
<body>
	<div onclick="popup()">이미지 첨부</div>
	<div id="image-list"></div>
</body>
</html>