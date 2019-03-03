let wrapImageTemp;

$(document).ready(function() {
	wrapImageTemp = $(".wrap-image").clone();
	$(".wrap-image").remove();
})

const sendImage = function(file) {
	return new Promise(function(resolve, reject) {
		var formData = new FormData(); 	
		formData.append("image", file);
		
		$.ajax({
			type : "POST",
			url : getContextPath() + "/board/post/image",
			dataType : "JSON",
			async : false,
			contentType: false,
			processData: false,
			data : formData,
			beforeSend : function(){
				Progress.start();
			},
			success : function(result) {
				resolve(result);
			},
			complete : function(){
				Progress.stop();
			}
		})
	});
}

const onImageChange = function() {
	files = $('#images').prop('files');
	
	let file;
	for (let i = 0; i < files.length; i++) {
		file = files[i];
		(function(file) {
			sendImage(file).then(function(result) {
				const wrapImage = wrapImageTemp.clone();
				wrapImage.appendTo($(".image-list"));
				wrapImage.find(".filename").val(file.name);
				wrapImage.find(".pathname").val(result.pathname);
				wrapImage.find(".image").attr("src", getContextPath() +  loc.temp.dir + result.pathname);
			})
		})(file);
	}
}

const removeImage = function(tg) {
	$(tg).parent(".wrap-image").remove();
}

const doUpload = function() {
	const wrapImages = $(".wrap-image");
	const editorID = $("#editor").val();
	const maxWidth = $("#width").val();

	for (let i = 0; i < wrapImages.length; i++) {
		//Editor에 이미지 첨부
		const wrapImage = $(wrapImages[i]);
		const image = {
			"editorID" : editorID,
			"seq" : undefined,
			"pathname" : wrapImage.find(".pathname").val(),
			"filename" : wrapImage.find(".filename").val(),
		}
		opener.imageUploader.insertCKEditor(image, maxWidth);
		opener.imageUploader.insertImageInfo(image);
	}

	window.close();
}
