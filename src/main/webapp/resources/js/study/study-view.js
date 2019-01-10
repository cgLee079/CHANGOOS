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
	var param = {};
	param["category"] = $("#category").val();
	window.location.href = getContextPath() + "/studies" + encodeURIParam(param);
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
				$.ajax({
					method: 'DELETE',
				    url: getContextPath() + '/mgnt/studies/post/' + seq,
				    contentType: 'application/json',
				    success: function(result) {
				    },
				    error: function(request,msg,error) {
				    }
				});
			} 
		});
}

/* when '수정' click, only for Admin */
function studyModify(seq){
	window.location.href = getContextPath() + "/mgnt/studies/post/" + seq;		
}

/* when '이전글', '다음글' click */
function studyView(seq){
	if (seq){
		var param = {};
		param["category"] = category;
		window.location.href = getContextPath() + "/studies/" + seq + encodeURIParam(param);
	} else {
		if(category){ 
			swal( category + " 카테고리에 글이 더 이상 없습니다.");
		} else{
			swal("글이 더 이상 없습니다.");
		}
	}
}
