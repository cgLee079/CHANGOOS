var STATUS_NEW = "NEW";
var STATUS_UNNEW = "UNNEW";
var STATUS_REMOVE = "REMOVE";

var wrapThumbnailTemp;

$(document).ready(function() {
	wrapThumbnailTemp = $(".wrap-thumbnail.temp").clone();
	$(".wrap-thumbnail.temp").remove();
	
	updateInputValue();
});

window.imageUploader = new Object();

window.imageUploader.insertCKEditor = function(editorID, path, pathname, filename, width) {
	var editor = CKEDITOR.instances[editorID];
	var element = editor.document.createElement('img', {
		attributes : {
			"src" : path + pathname,
			"pathname" : pathname,
			"alt" : filename,
			"title" : filename,
			"width" : width
		}
	});

	editor.insertElement(element);
}

window.imageUploader.insertThumbnail = function(image) {
	console.log(image);
	
	var thumbnailList = $(".thumbnail-list");
	var wrapThumbnail = wrapThumbnailTemp.clone();
	wrapThumbnailTemp.removeClass("temp");
	
	wrapThumbnail.appendTo(thumbnailList);
	wrapThumbnail.find(".editorID").val(image.editorID);
	wrapThumbnail.find(".path").val(image.path);
	wrapThumbnail.find(".pathname").val(image.pathname);
	wrapThumbnail.find(".filename").val(image.filename);
	wrapThumbnail.find(".status").val(image.status);
	wrapThumbnail.find(".thumbnail").attr("src", image.path + image.pathname);
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
	
	$(tg).parent(".wrap-thumbnail").addClass("remove");
	
	var status =$(tg).parent(".wrap-thumbnail").find(".status");
	if(status.val() == STATUS_NEW){
		status.val(STATUS_UNNEW);
	} else{
		status.val(STATUS_REMOVE);	
	}
	
	updateInputValue();
}

window.imageUploader.image2JSON = function(){
	var images = new Array();
	var image;
	var wrapImages = $(".wrap-thumbnail");
	var wrapImage;
	for(var i = 0; i < wrapImages.length; i++){
		wrapImage = $(wrapImages[i]);
		
		image = new Object();
		image["seq"] 		= wrapImage.find(".seq").val();
		image["editorID"] 	= wrapImage.find(".editorID").val();
		image["path"] 		= wrapImage.find(".path").val();
		image["filename"] 	= wrapImage.find(".filename").val();
		image["pathname"] 	= wrapImage.find(".pathname").val();
		image["status"] 	= wrapImage.find(".status").val();
		images.push(image);
	}
	
	return images;
}

function updateInputValue(){
	var images = window.imageUploader.image2JSON();
	var input = $("#contentImages");
	input.val(JSON.stringify(images));
}

function openImageUploadPopup(editor) {
	var popup = window.open(getContextPath() + "/mgnt/image/upload?editor=" + editor, "_blank", 'width=600, height=800');
}