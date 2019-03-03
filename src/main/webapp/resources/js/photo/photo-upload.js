$(document).ready(() => {
	doMenuOn(menu.MGNT_PHOTO);

	//Init CKEditor
	const editor = CKEDITOR.replace("desc", {
		on : {
			instanceReady : function(ev) {
				// Output paragraphs as <p>Text</p>.
				this.dataProcessor.writer.setRules('p', {
					indent : false,
					breakBeforeOpen : true,
					breakAfterOpen : false,
					breakBeforeClose : false,
					breakAfterClose : true
				});
			}
		},
		toolbar : 'Basic'
	});

})

const onPhotoChange = function(tg) {
	const file = tg.files[0];
	const formData = new FormData(); 	
	formData.append("image", file);
	
	$.ajax({
		type : "POST",
		url : getContextPath() + "/photos/post/image",
		dataType : "JSON",
		async : true,
		contentType: false,
		processData: false,
		data : formData,
		beforeSend : function(){
			Progress.start();
		},
		success : function(photo) {
			$("#filename").val(photo.filename);
			$("#pathname").val(photo.pathname);
			$("#thumbnail").val(photo.thumbnail);
			$("#date").val(photo.date);
			$("#time").val(photo.time);
			$("#device").val(photo.device);
			$("#snapshot").attr("src",  getContextPath()  + loc.temp.dir + photo.thumbnail);
		},
		complete : function(){
			Progress.stop();
		},
	})
}
