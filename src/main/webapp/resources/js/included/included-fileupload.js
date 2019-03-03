const fileUploader = {};

$(document).ready(() => {
	fileUploader.item = $(".file-info.temp").clone();
	fileUploader.item.removeClass("temp");
	$(".file-info.temp").remove();
	
	fileUploader.updateFileValues();
});

fileUploader.file2JSON = function(){
	const files = [];
	const fileInfos = $(".file-info");
	let file;
	let fileInfo;
	
	for(let i = 0; i < fileInfos.length; i++){
		fileInfo = $(fileInfos[i]);
		file = {
			seq 		: fileInfo.find(".file-seq").val(),
			pathname 	: fileInfo.find(".file-pathname").val(),
			filename  	: fileInfo.find(".file-filename").val(),
			size 		: fileInfo.find(".file-size").val(),
			status 		: fileInfo.find(".file-status").val(),
		};
		files.push(file);
	}
	
	return files;
}

fileUploader.insertFileInfo = function(file) {
	const fileInfos= $(".file-infos");
	const fileInfo = this.item.clone();
	
	fileInfo.find(".file-seq").val(file.seq);
	fileInfo.find(".file-pathname").val(file.pathname);
	fileInfo.find(".file-filename").val(file.filename);
	fileInfo.find(".file-size").val(file.size);
	fileInfo.find(".file-status").val(file.status);
	fileInfo.find(".file-info-name").text("[" + (file.size/(1024 * 1024)).toFixed(2) + " MB] " + file.filename);
	fileInfos.append(fileInfo);
	
	fileUploader.updateFileValues();
}

fileUploader.doFileRemove = function(tg){
	const fileInfo = $(tg).parents(".file-info");
	swal({
		  title: "정말로 삭제 하시겠습니까?",
		  text: "한번 삭제된 파일은 복구 할 수 없습니다.",
		  icon: "warning",
		  buttons: ["취소", "삭제"],
		  dangerMode: true,
		})
		.then(function(willDelete) {
			if(willDelete) {
				const status = fileInfo.find(".file-status")
				if(status.val() === fileUploader.status.BE){
					fileInfo.addClass("remove");
					status.val(fileUploader.status.REMOVE);
				} else if(status.val() === fileUploader.status.NEW){
					fileInfo.addClass("remove");
					status.val(fileUploader.status.UNNEW);
				}
				
				fileUploader.updateFileValues();
			} 
			
		})
	
	
}


fileUploader.sendFile = function(file){
	return new Promise(function(resolve, reject) {
		const formData = new FormData(); 	
		formData.append("file", file);
		
		$.ajax({
			type : "POST",
			url : getContextPath() + "/board/post/file",
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


fileUploader.onFileChange = function(tg){
	const files = tg.files;
	
	Progress.start();
		
	setTimeout(() => {
		for(let i = 0; i < files.length; i++){
			const file = files[i];
			
			(function(file){
				fileUploader.sendFile(file).then(function(result) {
					const fileInfos= $(".file-infos");
					const fileInfo = fileUploader.item.clone();
					
					fileInfo.find(".file-pathname").val(result.pathname);
					fileInfo.find(".file-filename").val(file.name);
					fileInfo.find(".file-size").val(file.size);
					fileInfo.find(".file-status").val(fileUploader.status.NEW);
					
					fileInfo.find(".file-info-name").text("[" + (file.size/(1024 * 1024)).toFixed(2) + " MB] " + file.name);
					fileInfos.append(fileInfo);
					
					fileUploader.updateFileValues();
				})
			})(file);
		}
		
		Progress.stop();
	}, 0)
	
	
}

fileUploader.updateFileValues = function(){
	const files = fileUploader.file2JSON();
	const input = $("#fileValues");
	input.val(JSON.stringify(files));
}