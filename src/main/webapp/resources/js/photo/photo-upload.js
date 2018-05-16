$(document).ready(function(){
	initDescCKEditor();
	
	/* CKEditor initialize */
	function initDescCKEditor(){
		var editor = CKEDITOR.replace("desc", {
			on : {
				instanceReady : function( ev ){
				    // Output paragraphs as <p>Text</p>.
				    this.dataProcessor.writer.setRules( 'p',
				        {
				            indent : false,
				            breakBeforeOpen : true,
				            breakAfterOpen : false,
				            breakBeforeClose : false,
				            breakAfterClose : true
				        });
				}
			},
			toolbar : "Basic"
		});

		CKEDITOR.on( 'instanceReady', function( ev ) {
			  // Ends self closing tags the HTML4 way, like <br>.
			  ev.editor.dataProcessor.writer.selfClosingEnd = '/>';
	    });
		
	}
})

