var sect;
var boardPage;

$(document).ready(function(){
	sect = $("#sect").val();
	boardPage = $("#boardPage").val();
})

function boardList(){
	if(!boardPage){
		boardPage = 1;
	}
	window.location.href = getContextPath() + "/board#" + sect + "&" + boardPage;
}

function boardDelete(seq){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "한번 삭제된 글은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then((willDelete) => {
			if(willDelete) {
				window.location.href = getContextPath() + "/admin/board/delete.do?seq=" + seq;  
			} 
		});
}

function boardModify(seq){
	window.location.href = getContextPath() + "/admin/board/upload?seq=" + seq;		
}

function boardView(seq){
	if (seq){
		window.location.href = getContextPath() + "/board/view?seq=" + seq + "&sect=" + sect + "&page=" + boardPage;
	} else {
		if(sect){
			swal( sect + " 부분 글이 더 이상 없습니다.");
		} else{
			swal("글이 더 이상 없습니다.");
		}
	}
}

function downloadFile(pathNm){
	window.location.assign(getContextPath()	+ "/board/download.do?filename="+ pathNm);
}