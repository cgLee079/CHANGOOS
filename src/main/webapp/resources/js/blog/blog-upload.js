$(document).ready(() => {
	doMenuOn(menu.MGNT_BLOG);
	
	const editor = CKEDITOR.replace("contents", {
		height : '400px',
		pasteImageUrl : getContextPath() + "/board/post/image",
		codeSnippet_theme : 'github',
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
		toolbar : "Full"
	});

	CKEDITOR.on('dialogDefinition', function(ev) {
		const dialogName = ev.data.name;
		const dialog = ev.data.definition.dialog;
		const dialogDefinition = ev.data.definition;

		if (dialogName == 'image') {
			dialogDefinition.removeContents('Link'); //링크 탭 제거
			dialogDefinition.removeContents('advanced'); //상세정보 탭 제거
		}
	});
});

var submit = function(){
	form = $('#uploadForm');
	seq = $('#seq').val();
	
	if(!seq){
		form.attr("action", getContextPath() + "/blogs/post/");
		form.attr("method", "post");
	} else{
		form.attr("action", getContextPath() + "/blogs/post/" + seq);
		form.attr("method", "put");
	}
	
	form.submit();	
}

var onThumbnailChange = function(tg){
	var files = tg.files;
	var file = files[0];
	
	var formData = new FormData(); 	
	formData.append("thumbnailFile", file);
	
	$.ajax({
		type : "POST",
		url : getContextPath() + "/blogs/post/thumbnail",
		dataType : "JSON",
		async : false,
		contentType: false,
		processData: false,
		data : formData,
		success : function(result) {
			$("#thumbnail").val(result["pathname"]);
			$("#thumbnail-img").attr("src", getContextPath() + loc.temp.dir + result["pathname"]);
		},
	})
}
