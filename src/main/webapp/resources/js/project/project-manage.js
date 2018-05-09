function projectView(seq){
	window.location.href = getContextPath() + "/project/view?seq=" + seq;		
}

function projectDelete(seq){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 프로젝트는 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(willDelete => {
		  if (willDelete) {
			  window.location.href = getContextPath() + "/admin/project/delete.do?seq=" + seq;	
		  } 
		});
}

function projectModify(seq){
	window.location.href = getContextPath() + "/admin/project/upload?seq=" + seq;		
}