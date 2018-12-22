$(document).ready(function() {
	doMenuOn(".menu-blog");
});

function blogList(){
	window.location.href = getContextPath() + "/blog";
}

/* download file */
function downloadFile(pathNm){
	window.location.href = getContextPath() + "/blog/file/download.do?filename=" + pathNm;
}
