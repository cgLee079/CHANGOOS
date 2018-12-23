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

window.imageUploader.insertThumbnail = function(editorID, path, pathname, filename) {
	var thumbnailList = $(".thumbnail-list");
	var wrapThumbnail = wrapThumbnailTemp.clone();
	wrapThumbnailTemp.removeClass("temp");
	
	wrapThumbnail.appendTo(thumbnailList);
	wrapThumbnail.find(".editorID").val(editorID);
	wrapThumbnail.find(".path").val(path);
	wrapThumbnail.find(".pathname").val(pathname);
	wrapThumbnail.find(".filename").val(filename);
	wrapThumbnail.find(".status").val(STATUS_NEW);
	wrapThumbnail.find(".thumbnail").attr("src", path + pathname);
	wrapThumbnail.find(".btn-remove").attr("onclick", "removeImage(this, '" + editorID + "', '" + pathname + "')");
	
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

function updateInputValue(){
	var images = new Array();
	var image;
	var wrapImages = $(".wrap-thumbnail");
	var wrapImage;
	for(var i = 0; i < wrapImages.length; i++){
		wrapImage = $(wrapImages[i]);
		
		image = new Object();
		image["seq"] = wrapImage.find(".seq").val();
		image["editorID"] = wrapImage.find(".editorID").val();
		image["path"] = wrapImage.find(".path").val();
		image["filename"] = wrapImage.find(".filename").val();
		image["pathname"] = wrapImage.find(".pathname").val();
		image["status"] = wrapImage.find(".status").val();
		images.push(image);
	}
	
	var input = $("#contentImages");
	input.val(JSON.stringify(images));
}
function openImageUploadPopup(editor) {
	var popup = window.open(getContextPath() + "/mgnt/image/upload?editor=" + editor, "_blank", 'width=600, height=800');
}