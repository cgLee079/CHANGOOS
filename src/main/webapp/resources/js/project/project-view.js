$(document).ready(() => {
	doMenuOn(menu.PROJECT);
});

/* when '목록' click */
const projectList = function(){
	window.location.href = getContextPath() + "/projects";
}

/* when '이전글', '다음글' click */
const projectView = function(seq){
	if (seq){
		window.location.href = getContextPath() + "/projects/" + seq;
	} else {
		swal("글이 더 이상 없습니다.");
	}
}
