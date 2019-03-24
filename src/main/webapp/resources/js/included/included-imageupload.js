imageUploader = {};

$(document).ready(function() {
	imageUploader.item = $(".wrap-image.temp").clone();
	$(".wrap-image.temp").remove();
	
	imageUploader.updateInputValue();
});

imageUploader.insertCKEditor = function(image, maxWidth) {
	const img = new Image();
	img.src =  loc.temp.dir + image.pathname;
	
	img.onload = function() {
		const width = this.width;
		const height = this.height;
		
		if(width > maxWidth){
			height = height / (width/maxWidth);
			width = maxWidth;
		}
		
		const editor = CKEDITOR.instances[image.editorID];
		const p = editor.document.createElement('p');
		const e = editor.document.createElement('img', {
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
	var wrapImage = this.item.clone();
	wrapImage.removeClass("temp");
	
	wrapImage.appendTo(imageList);
	wrapImage.find(".editorID").val(image.editorID);
	wrapImage.find(".pathname").val(image.pathname);
	wrapImage.find(".filename").val(image.filename);
	wrapImage.find(".status").val(imageUploader.status.NEW);
	wrapImage.find(".image").attr("src", loc.temp.dir + image.pathname);
	
	imageUploader.updateInputValue();
}

imageUploader.removeImage = function(tg) {
	const wrapImage 	= $(tg).parent(".wrap-image");
	const status 		= wrapImage.find(".status");
	const editorID		= wrapImage.find(".editorID").val();
	const pathname 		= wrapImage.find(".pathname").val(); 
	const editor 		= CKEDITOR.instances[editorID];
	const images 		= $(editor.document.$.body).find("img");
	
	for(var i = 0; i < images.length; i++){
		var image = $(images[i]);
		if(image.attr("pathname") == pathname){
			image.remove();
		}
	}
	
	if(status.val() === imageUploader.status.BE){
		wrapImage.addClass("remove");
		status.val(imageUploader.status.REMOVE);
	} else if(status.val() === imageUploader.status.NEW){
		wrapImage.addClass("remove");
		status.val(imageUploader.status.UNNEW);
	} 
	
	imageUploader.updateInputValue();
}

imageUploader.image2JSON = function(){
	const images = [];
	const wrapImages = $(".wrap-image");
	let image;
	let wrapImage;
	for(let i = 0; i < wrapImages.length; i++){
		wrapImage = $(wrapImages[i]);
		
		image = {};
		image.seq 		= wrapImage.find(".seq").val();
		image.editorID 	= wrapImage.find(".editorID").val();
		image.filename 	= wrapImage.find(".filename").val();
		image.pathname 	= wrapImage.find(".pathname").val();
		image.status 	= wrapImage.find(".status").val();
		images.push(image);
	}
	
	return images;
}

imageUploader.updateInputValue = function(){
	const images = imageUploader.image2JSON();
	const input = $("#imageValues");
	input.val(JSON.stringify(images));
}

const openImageUploadPopup = function(editor) {
	window.open(getContextPath() + "/board/post/image?editor=" + editor, "_blank", 'width=600, height=800');
}