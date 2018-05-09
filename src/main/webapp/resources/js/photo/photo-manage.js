function photoDelete(seq){
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "삭제된 사진은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(willDelete => {
		  if (willDelete) {
			  window.location.href = getContextPath() + "/admin/photo/delete.do?seq=" + seq;
		  } 
		});
}

function photoModify(seq){
	window.location.href = getContextPath() + "/admin/photo/upload?seq=" + seq;		
}