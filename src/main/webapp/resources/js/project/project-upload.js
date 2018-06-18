$(document).ready(function() {
	doMenuOn(".menu-mgnt-project");
	
	var videoVal = $("#videoVal").val();
	if(videoVal){
		$("#video").val(videoVal);
	}
	
	initDescCKEditor();
	initContentCKEditor();
});

function initDescCKEditor() {
	var editor = CKEDITOR.replace("desc", {
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

	CKEDITOR.on('instanceReady', function(ev) {
		// Ends self closing tags the HTML4 way, like <br>.
		ev.editor.dataProcessor.writer.selfClosingEnd = '/>';
	});
}

function initContentCKEditor() {
	var editor = CKEDITOR.replace("contents", {
		height : '400px',
		filebrowserUploadUrl : getContextPath() + "/mgnt/project/imgUpload.do",
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
		}
	});

	CKEDITOR.on('dialogDefinition', function(ev) {
		var dialogName = ev.data.name;
		var dialog = ev.data.definition.dialog;
		var dialogDefinition = ev.data.definition;

		if (dialogName == 'image') {
			dialogDefinition.removeContents('Link'); //링크 탭 제거
			dialogDefinition.removeContents('advanced'); //상세정보 탭 제거
		}
	});

	CKEDITOR.on('instanceReady', function(ev) {
		// Ends self closing tags the HTML4 way, like <br>.
		ev.editor.dataProcessor.writer.selfClosingEnd = '/>';
	});
}