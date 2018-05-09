var form; 
var boardType;
var path;
	
$(document).ready(function(){
	boardType = $("#boardType").val();
	form = $(".file-info").clone(true);
	path = getContextPath() + "/admin" + "/" + boardType;	
});

function fileRemove(tg){
	var fileInfo = $(tg).parents(".file-info");
	var seq = fileInfo.find(".file-seq").val();
	if(seq){
		swal({
			  title: "정말로 삭제 하시겠습니까?",
			  text: "한번 삭제된 파일은 복구 할 수 없습니다.",
			  icon: "warning",
			  buttons: ["취소", "삭제"],
			  dangerMode: true,
			})
			.then((willDelete) => {
				if(willDelete) {
					$.ajax({
						type	: "POST",
						url 	: path + "/deleteFile.do",
						dataType: "JSON",
						data 	: {
							"seq" : seq
						},
						success : function(data) {
							if(data.result){
								swal({ text : "서버에서 파일이 삭제되었습니다.", icon : "success" });
								fileInfo.remove();
							} else{
								swal({ text : "파일 삭제 실패하였습니다.", icon : "error" });
							}
						}	
					});  
				} 
			});
	} else{
		fileInfo.remove();
	}
}

function fileChange(tg){
	var file = tg.files[0];
	var fileInfo = $(tg).parents(".file-info");
	var fileInfos= fileInfo.parents(".file-infos");
	
	if($(tg).val()){
		fileInfo.find(".btn-file-remove").css("display", "");
		fileInfo.find(".btn-file-upload").css("display", "none");
		
		$("<div>", {
			"class" : "file-info-name",
			text : "(" + (file.size/(1024 * 1024)).toFixed(2) + " MB) " + file.name 
		}).prependTo(fileInfo);
		
		fileInfos.append(form.clone(true));
	} 
}