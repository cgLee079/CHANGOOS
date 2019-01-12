var imageUploader = new Object();
window.imageUloader = imageUploader;

var wrapImageTemp;

$(document).ready(function() {
	wrapImageTemp = $(".wrap-image.temp").clone();
	$(".wrap-image.temp").remove();
	
	imageUploader.updateInputValue();
});

imageUploader.insertCKEditor = function(image, maxWidth) {
	var img = new Image();
	img.src =  loc.temp.dir + image.pathname;
	
	img.onload = function() {
		var width = this.width;
		var height = this.height;
		
		if(width > maxWidth){
			height = height / (width/maxWidth);
			width = maxWidth;
		}
		
		var editor = CKEDITOR.instances[image.editorID];
		var p = editor.document.createElement('p');
		var e = editor.document.createElement('img', {
			attributes : {
				"src" : loc.temp.dir + image.pathname,
				"pathname" : image.pathname,
				"alt" : image.filename,
				"title" : image.filename,
				"width" : width,
				"height" : height
			}
		});
		
		$(p.$).append($(e.$));
		editor.insertElement(p);
	}
	
}

imageUploader.insertImageInfo = function(image) {
	var imageList = $(".image-list");
	var wrapImage = wrapImageTemp.clone();
	wrapImageTemp.removeClass("temp");
	
	wrapImage.appendTo(imageList);
	wrapImage.find(".editorID").val(image.editorID);
	wrapImage.find(".pathname").val(image.pathname);
	wrapImage.find(".filename").val(image.filename);
	wrapImage.find(".status").val(imageUploader.status.NEW);
	wrapImage.find(".image").attr("src", loc.temp.dir + image.pathname);
	
	imageUploader.updateInputValue();
}

imageUploader.removeImage = function(tg) {
	var wrapImage 	= $(tg).parent(".wrap-image");
	var status 		= wrapImage.find(".status").val();
	var editorID	= wrapImage.find(".editorID").val();
	var pathname 	= wrapImage.find(".pathname").val(); 
	var editor 		= CKEDITOR.instances[editorID];
	var images 		= $(editor.document.$.body).find("img");
	
	for(var i = 0; i < images.length; i++){
		var image = $(images[i]);
		if(image.attr("pathname") == pathname){
			image.remove();
		}
	}
	
	if(status == imageUploader.status.BE){
		wrapImage.addClass("remove");
		status.val(imageUploader.status.REMOVE);
	} else if(status == imageUploader.status.NEW){
		wrapImage.addClass("remove");
		status.val(imageUploader.status.UNNEW);
	} 
	
	imageUploader.updateInputValue();
}

imageUploader.image2JSON = function(){
	var images = new Array();
	var image;
	var wrapImages = $(".wrap-image");
	var wrapImage;
	for(var i = 0; i < wrapImages.length; i++){
		wrapImage = $(wrapImages[i]);
		
		image = new Object();
		image["seq"] 		= wrapImage.find(".seq").val();
		image["editorID"] 	= wrapImage.find(".editorID").val();
		image["filename"] 	= wrapImage.find(".filename").val();
		image["pathname"] 	= wrapImage.find(".pathname").val();
		image["status"] 	= wrapImage.find(".status").val();
		images.push(image);
	}
	
	return images;
}

imageUploader.updateInputValue = function(){
	var images = imageUploader.image2JSON();
	var input = $("#imageValues");
	input.val(JSON.stringify(images));
}

function openImageUploadPopup(editor) {
	var popup = window.open(getContextPath() + "/board/post/image?editor=" + editor, "_blank", 'width=600, height=800');
}