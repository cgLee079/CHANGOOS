var wrapImageTemp;

$(document).ready(function() {
	wrapImageTemp = $(".wrap-image").clone();
	$(".wrap-image").remove();
})

function sendImage(file) {
	return new Promise(function(resolve, reject) {
		var formData = new FormData(); 	
		formData.append("image", file);
		
		$.ajax({
			type : "POST",
			url : getContextPath() + "/mgnt/board/post/image",
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

function onImageChange() {
	files = $('#images').prop('files');
	for (var i = 0; i < files.length; i++) {
		var file = files[i];
		(function(file) {
			sendImage(file).then(function(result) {
				var path = result.path;
				var pathname = result.pathname;
				var wrapImage = wrapImageTemp.clone();
				wrapImage.appendTo($(".image-list"));
				wrapImage.find(".image").attr("src", tempDirURL + pathname);
				wrapImage.find(".filename").val(file.name);
				wrapImage.find(".pathname").val(pathname);
			})
		})(file);
	}
}

function removeImage(tg) {
	$(tg).parent(".wrap-image").remove();
}

function doUpload() {
	var wrapImages = $(".wrap-image");
	var editorID = $("#editor").val();
	var maxWidth = $("#width").val();

	for (var i = 0; i < wrapImages.length; i++) {
		//Editor에 이미지 첨부
		var wrapImage = $(wrapImages[i]);
		var image = {
			"editorID" : editorID,
			"seq" : undefined,
			"pathname" : wrapImage.find(".pathname").val(),
			"filename" : wrapImage.find(".filename").val()
		}
		opener.imageUploader.insertCKEditor(image, maxWidth);
		opener.imageUploader.insertImageInfo(image);
	}

	window.close();
}
