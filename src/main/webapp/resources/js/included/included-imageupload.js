imageUploader = new Object();
window.imageUloader = imageUploader;

var STATUS_NEW = "NEW";
var STATUS_REMOVE = "REMOVE";

var wrapThumbnailTemp;

$(document).ready(function() {
	wrapThumbnailTemp = $(".wrap-thumbnail.temp").clone();
	$(".wrap-thumbnail.temp").remove();
	
	updateInputValue();
});

imageUploader.insertCKEditor = function(image, maxWidth) {
	var img = new Image();
	img.src =  tempDirURL + image.pathname;
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
				"src" : tempDirURL + image.pathname,
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
	var thumbnailList = $(".thumbnail-list");
	var wrapThumbnail = wrapThumbnailTemp.clone();
	wrapThumbnailTemp.removeClass("temp");
	
	wrapThumbnail.appendTo(thumbnailList);
	wrapThumbnail.find(".editorID").val(image.editorID);
	wrapThumbnail.find(".pathname").val(image.pathname);
	wrapThumbnail.find(".filename").val(image.filename);
	wrapThumbnail.find(".status").val(image.status);
	wrapThumbnail.find(".thumbnail").attr("src", tempDirURL + image.pathname);
	wrapThumbnail.find(".btn-remove").attr("onclick", "removeImage(this, '" + image.editorID + "', '" + image.pathname + "')");
	
	updateInputValue();
}

function removeImage(tg, editorID, pathname) {
	var editor = CKEDITOR.instances[editorID];
	var images = $(editor.document.$.body).find("img");
	
	for(var i = 0; i < images.length; i++){
		var image = $(images[i]);
		if(image.attr("pathname") == pathname){
			image.remove();
		}
	}
	
	var wrapThumbnail = $(tg).parent(".wrap-thumbnail");
	wrapThumbnail.addClass("remove");
	
	var status =wrapThumbnail.find(".status");
	if(status.val() == STATUS_NEW){
		wrapThumbnail.remove();
	} else{
		wrapThumbnail.addClass("remove");
		status.val(STATUS_REMOVE);
	}
	
	
	updateInputValue();
}

imageUploader.image2JSON = function(){
	var images = new Array();
	var image;
	var wrapImages = $(".wrap-thumbnail");
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

function updateInputValue(){
	var images = imageUploader.image2JSON();
	var input = $("#imageValues");
	input.val(JSON.stringify(images));
}

function openImageUploadPopup(editor) {
	var popup = window.open(getContextPath() + "/mgnt/image/upload?editor=" + editor, "_blank", 'width=600, height=800');
}