/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';

	config.language = 'ko';
	config.toolbar_Full = [
			{
				name : 'document',
				items : [ 'Source', 'Maximize', 'ShowBlocks' ]
			},
			{
				name : 'links',
				items : [ 'Link', 'Unlink', ]
			},
			{
				name : 'insert',
				items : [ 'Youtube', 'CodeSnippet', 'Table', 'SpecialChar', 'Emojione', 'Image', 'HorizontalRule']
			},
			{
				name : 'paragraph',
				items : [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent' ]
			},
			'/',
			{
				name : 'styles',
				items : [ 'Format', 'FontSize' ]
			},
			{
				name : 'colors',
				items : [ 'TextColor', 'BGColor']
			},
			{
				name : 'basicstyles',
				items : [ 'Bold', 'Italic', 'Underline', 'Strike', '-','RemoveFormat','Styles','-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-']
			},

	];

	config.toolbar_Basic = [ [ 'Bold', 'Italic', 'Underline', 'Strike', '-','RemoveFormat', 'Emojione'] ];

	config.extraPlugins = 'youtube,prism,emojione,autosave,pastebase64toserver,stylescombo';
	config.bodyClass 	= 'editor-contents';
	config.contentsCss 	= '/resources/css/editor-contents.css';
	
	config.autosave = {
		// Auto save Key - The Default autosavekey can be overridden from the config ...
		Savekey : 'autosave_' + window.location + "_" + $('.ckeditor-autosave').attr('name'),

		// Ignore Content older then X
		//The Default Minutes (Default is 1440 which is one day) after the auto saved content is ignored can be overidden from the config ...
		NotOlderThen : 1440,

		// Save Content on Destroy - Setting to Save content on editor destroy (Default is false) ...
		saveOnDestroy : false,

		// Setting to set the Save button to inform the plugin when the content is saved by the user and doesn't need to be stored temporary ...
		saveDetectionSelectors : "a[class*='submit']",

		// Notification Type - Setting to set the if you want to show the "Auto Saved" message, and if yes you can show as Notification or as Message in the Status bar (Default is "notification")
		//messageType : "notification",

		// Show in the Status Bar
		messageType : "statusbar",

		// Show no Message
		//messageType : "no",

		// Delay
		delay : 60,

		// The Default Diff Type for the Compare Dialog, you can choose between "sideBySide" or "inline". Default is "sideBySide"
		diffType : "inline",

		// autoLoad when enabled it directly loads the saved content
		autoLoad : false
	};
};
