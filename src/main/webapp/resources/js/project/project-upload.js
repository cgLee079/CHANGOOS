$(document).ready(() => {
	doMenuOn(menu.MGNT_PROJECT);
	
	initDescCKEditor();
	initContentCKEditor();
});

const onThumbnailChange = function(tg){
	const files = tg.files;
	const file = files[0];
	const formData = new FormData(); 	
	formData.append("thumbnailFile", file);
	
	$.ajax({
		type : "POST",
		url : getContextPath() + "/projects/post/thumbnail",
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

const initDescCKEditor = function() {
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
}

const initContentCKEditor = function() {
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
		toolbar : 'Full'
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
}