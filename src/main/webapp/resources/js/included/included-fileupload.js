var fileUploader = new Object();

var fileInfoTemp;

$(document).ready(function(){
	fileInfoTemp = $(".file-info.temp").clone();
	fileInfoTemp.removeClass("temp");
	$(".file-info.temp").remove();
	
	updateFileValues();
});

fileUploader.file2JSON = function(){
	var files = new Array();
	var file;
	var fileInfos = $(".file-info");
	var fileInfo;
	
	for(var i = 0; i < fileInfos.length; i++){
		fileInfo = $(fileInfos[i]);
		file = new Object();
		file["seq"] 		= fileInfo.find(".file-seq").val();
		file["pathname"] 	= fileInfo.find(".file-pathname").val();
		file["filename"] 	= fileInfo.find(".file-filename").val();
		file["size"] 		= fileInfo.find(".file-size").val();
		file["status"] 		= fileInfo.find(".file-status").val();
		files.push(file);
	}
	
	return files;
}

fileUploader.insertFileInfo = function(file) {
	var fileInfos= $(".file-infos");
	var fileInfo = fileInfoTemp.clone();
	
	fileInfo.find(".file-seq").val(file.seq);
	fileInfo.find(".file-pathname").val(file.pathname);
	fileInfo.find(".file-filename").val(file.filename);
	fileInfo.find(".file-size").val(file.size);
	fileInfo.find(".file-status").val(file.status);
	
	fileInfo.find(".file-info-name").text("[" + (file.size/(1024 * 1024)).toFixed(2) + " MB] " + file.filename);
	fileInfos.append(fileInfo);
	
	updateFileValues();
	
	updateInputValue();
}

function doFileRemove(tg){
	var fileInfo = $(tg).parents(".file-info");
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "한번 삭제된 파일은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete) {
			if(willDelete) {
				var status = fileInfo.find(".file-status")
				if(status.val() == FILE_STATUS_BE){
					fileInfo.addClass("remove");
					status.val(FILE_STATUS_REMOVE);
				} else if(status.val() == FILE_STATUS_NEW){
					fileInfo.addClass("remove");
					status.val(FILE_STATUS_UNNEW);
				}
				
				updateFileValues();
			} 
			
		})
	
	
}


function sendFile(file){
	return new Promise(function(resolve, reject) {
		var formData = new FormData(); 	
		formData.append("file", file);
		
		$.ajax({
			type : "POST",
			url : getContextPath() + "/mgnt/board/post/file",
			dataType : "JSON",
			async : false,
			contentType: false,
			processData: false,
			data : formData,
			success : function(result) {
				resolve(result);
			},
		})
	});
}


function onFileChange(tg){
	var files = tg.files;
	
	Progress.start();
		
	setTimeout(function(){
		for(var i = 0; i < files.length; i++){
			var file = files[i];
			
			(function(file){
				sendFile(file).then(function(result) {
					var fileInfos= $(".file-infos");
					var fileInfo = fileInfoTemp.clone();
					
					fileInfo.find(".file-pathname").val(result.pathname);
					fileInfo.find(".file-filename").val(file.name);
					fileInfo.find(".file-size").val(file.size);
					fileInfo.find(".file-status").val(FILE_STATUS_NEW);
					
					fileInfo.find(".file-info-name").text("[" + (file.size/(1024 * 1024)).toFixed(2) + " MB] " + file.name);
					fileInfos.append(fileInfo);
					
					updateFileValues();
				})
			})(file);
		}
		
		Progress.stop();
	}, 0)
	
	
}

function updateFileValues(){
	var files = fileUploader.file2JSON();
	var input = $("#fileValues");
	input.val(JSON.stringify(files));
}