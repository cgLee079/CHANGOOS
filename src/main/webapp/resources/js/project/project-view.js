$(document).ready(function(){
	doMenuOn(".menu-project");
	
//	$(".btn-project-before, .btn-project-next").tooltip({
//    	position: 'top',
//    	show : null,
//    	hide : null,
//    });

});

/* when '목록' click */
function projectList(){
	window.location.href = getContextPath() + "/project";
}

/* when '이전글', '다음글' click */
function projectView(seq){
	if (seq){
		window.location.href = getContextPath() + "/project/view?seq=" + seq;
	} else {
		swal("글이 더 이상 없습니다.");
	}
}


/* download file */
function downloadFile(pathNm){
	window.location.href = getContextPath() + "/project/download.do?filename=" + pathNm;
}
