var section;
var studyPage;

$(document).ready(function(){
	doMenuOn(".menu-study");
	
	section = $("#section").val();
	studyPage = $("#studyPage").val();
	
	$(".btn-study-before, .btn-study-next").tooltip({
    	position: 'top',
    	show : null,
    	hide : null,
    });
})

/* when '목록' click */
function studyList(){
	if(!studyPage){
		studyPage = 1;
	}
	window.location.href = getContextPath() + "/study#" + section + "&" + studyPage;
}

/* when '삭제' click, only for Admin */
function studyDelete(seq){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "한번 삭제된 글은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete) {
			if(willDelete) {
				window.location.href = getContextPath() + "/mgnt/study/delete.do?seq=" + seq;  
			} 
		});
}

/* when '수정' click, only for Admin */
function studyModify(seq){
	window.location.href = getContextPath() + "/mgnt/study/upload?seq=" + seq;		
}

/* when '이전글', '다음글' click */
function studyView(seq){
	if (seq){
		window.location.href = getContextPath() + "/study/view?seq=" + seq + "&section=" + section + "&page=" + studyPage;
	} else {
		if(section){
			swal( section + " 영역에 글이 더 이상 없습니다.");
		} else{
			swal("글이 더 이상 없습니다.");
		}
	}
}

/* Download File */
function downloadFile(pathNm){
	window.location.href = getContextPath() + "/study/download.do?filename=" + pathNm;
}