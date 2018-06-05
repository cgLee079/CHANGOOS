var section;
var boardPage;

$(document).ready(function(){
	section = $("#section").val();
	boardPage = $("#boardPage").val();
	
	$(".btn-board-before, .btn-board-next").tooltip({
    	position: 'top',
    	show : null,
    	hide : null,
    });
})

/* when '목록' click */
function boardList(){
	if(!boardPage){
		boardPage = 1;
	}
	window.location.href = getContextPath() + "/board#" + section + "&" + boardPage;
}

/* when '삭제' click, only for Admin */
function boardDelete(seq){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "한번 삭제된 글은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete) {
			if(willDelete) {
				window.location.href = getContextPath() + "/admin/board/delete.do?seq=" + seq;  
			} 
		});
}

/* when '수정' click, only for Admin */
function boardModify(seq){
	window.location.href = getContextPath() + "/admin/board/upload?seq=" + seq;		
}

/* when '이전글', '다음글' click */
function boardView(seq){
	if (seq){
		window.location.href = getContextPath() + "/board/view?seq=" + seq + "&section=" + section + "&page=" + boardPage;
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
	var form = $("<form>");
	form.attr("method", "post");
	form.attr("action", getContextPath() + "/board/download.do");
	form.appendTo($("body"));
	form.append($("<input>", {"type": "hidden", "name" : "filename" , "value" : pathNm}));
	form.submit();
	form.remove();
}