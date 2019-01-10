$(document).ready(function(){
	doMenuOn(".menu-mgnt-study");
	initContentCKEditor();
});

function submit(){
	var form = $("#uploadForm");
	form.submit();
}

/* CKEditor Initialize */
function initContentCKEditor(){
	var editor = CKEDITOR.replace("study-contents", {
		height : '400px',
		pasteImageUrl : getContextPath() + "/mgnt/board/post/image",
		on : {
			instanceReady : function( ev ){
			    // Output paragraphs as <p>Text</p>.
			    this.dataProcessor.writer.setRules( 'p', {
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
		var dialogName = ev.data.name;
		var dialog = ev.data.definition.dialog;
		var dialogDefinition = ev.data.definition;

		if (dialogName == 'image') {
			dialogDefinition.removeContents('Link'); //링크 탭 제거
			dialogDefinition.removeContents('advanced'); //상세정보 탭 제거
		}
	});
	
	CKEDITOR.on( 'instanceReady', function( ev ) {
        // Ends self closing tags the HTML4 way, like <br>.
        ev.editor.dataProcessor.writer.selfClosingEnd = '/>';
    });
}