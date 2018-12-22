var category;

$(document).ready(function(){
	doMenuOn(".menu-study");
	
	category = $("#category").val();
	
	$(".btn-study-before, .btn-study-next").tooltip({
    	position: 'top',
    	show : null,
    	hide : null,
    });
})

/* when '목록' click */
function studyList(){
	window.location.href = getContextPath() + "/study#" + category;
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
				$.post("/mgnt/study/delete.do", {"seq" : seq});
				//TODO 삭제시 페이지 이동 처리
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
		window.location.href = getContextPath() + "/study/view?seq=" + seq + "&category=" + category;
	} else {
		if(category){
			swal( category + " 카테고리에 글이 더 이상 없습니다.");
		} else{
			swal("글이 더 이상 없습니다.");
		}
	}
}

/* Download File */
function downloadFile(pathNm){
	window.location.href = getContextPath() + "/study/file/download.do?filename=" + pathNm;
}