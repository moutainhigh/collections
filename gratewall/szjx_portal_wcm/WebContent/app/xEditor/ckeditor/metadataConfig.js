/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/
/*
在这里设定参数
*/
CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	config.uiColor = 'gray';
	config.resize_enabled = false;
	config.toolbarCanCollapse = true;
	config.baseFloatZIndex = 10000;
	config.extraPlugins = "photolib,flashlib,wcmstyle,wcmimage,officeactivex";
	config.toolbar = "full";
	config.fontSize_sizes ='小五/9pt;五号/10.5pt;小四/12pt;四号/14pt;小三/15pt;三号/16pt;小二/18pt;二号/22pt;小一/24pt;一号/26pt;小初/36pt;初号/42pt';
	if(window.$G_LOCALE){
		config.fontSize_sizes ='9pt/9pt;10.5pt/10.5pt;12pt/12pt;14pt/14pt;15pt/15pt;16pt/16pt;18pt/18pt;22pt/22pt;24pt/24pt;26pt/26pt;36pt/36pt;42pt/42pt';
	}
	config.contentsCss = ['./ckeditor/cssforhaier/features.css'];	
	config.toolbar_full = [
		['Source','-','Templates'],
		['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
		['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
		['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
		'/',
		['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
		['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['Link','Unlink','Anchor'],
		['wcmimage','photolib','flashlib','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],		
		'/',
		['Styles','Format','Font','FontSize'],
		['TextColor','BGColor','wcmstyle']	
	];
};
