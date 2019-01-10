$(document).ready(function(){
	doMenuOn(".menu-project");
});

/* when '목록' click */
function projectList(){
	window.location.href = getContextPath() + "/projects";
}

/* when '이전글', '다음글' click */
function projectView(seq){
	if (seq){
		window.location.href = getContextPath() + "/projects/" + seq;
	} else {
		swal("글이 더 이상 없습니다.");
	}
}
