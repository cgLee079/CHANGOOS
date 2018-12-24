var wrapImageTemp;

$(document).ready(function() {
	wrapImageTemp = $(".wrap-image").clone();
	$(".wrap-image").remove();
})

function sendImage(base64, filename) {
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "post",
			url : getContextPath() + "/mgnt/image/upload.do",
			dataType : "JSON",
			async : false,
			data : {
				"base64" : base64,
				"filename" : filename
			},
			success : function(result) {
				resolve(result);
			}
		})
	});
}

function onImageChange() {
	files = $('#images').prop('files');
	for (var i = 0; i < files.length; i++) {
		var file = files[i];
		var fileReader = new FileReader();
		fileReader.onload = function(file) {
			return function(evt) {
				var base64 = evt.target.result;
				sendImage(base64, file.name).then(function(result) {
					var path = result.path;
					var pathname = result.pathname;
					var filename = result.filename;
					var wrapImage = wrapImageTemp.clone();
					wrapImage.appendTo($(".image-list"));
					wrapImage.find(".image").attr("src", path + pathname);
					wrapImage.find(".path").val(path);
					wrapImage.find(".filename").val(filename);
					wrapImage.find(".pathname").val(pathname);
				})
			}
		}(file);

		fileReader.readAsDataURL(files[i]);
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
			"path" : wrapImage.find(".path").val(),
			"pathname" : wrapImage.find(".pathname").val(),
			"filename" : wrapImage.find(".filename").val(),
			"status" : "NEW",
		}
		var path = wrapImage.find(".path").val();
		var pathname = wrapImage.find(".pathname").val();
		var filename = wrapImage.find(".filename").val();
		opener.imageUploader.insertCKEditor(image, maxWidth);
		opener.imageUploader.insertImageInfo(image);
	}

	window.close();
}
