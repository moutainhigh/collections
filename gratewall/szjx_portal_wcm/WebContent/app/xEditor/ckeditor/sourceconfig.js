/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/
/*
在这里设定参数
*/
CKEDITOR.editorConfig = function( config )
{
	config.pasteFromWordPromptCleanup = true;
	config.uiColor = '#cfd9e6';//config.uiColor = '#C4C4C4';
	config.resize_enabled = false;
	config.toolbarCanCollapse = true;
	config.baseFloatZIndex = 10000;
	config.extraPlugins = "key4format,wcmimage,wcmvideo,imageSet,videoSet,officeactivex,bottombutton"
	config.toolbar = "full";
	config.contentsCss = "ckeditor/contents.css";
	//config.fontSize_sizes ='小五/9pt;五号/10.5pt;小四/12pt;四号/14pt;小三/15pt;三号/16pt;小二/18pt;二号/22pt;小一/24pt;一号/26pt;小初/36pt;初号/42pt';
	//if(window.$G_LOCALE){
		//config.fontSize_sizes ='9pt/9pt;10.5pt/10.5pt;12pt/12pt;14pt/14pt;15pt/15pt;16pt/16pt;18pt/18pt;22pt/22pt;24pt/24pt;26pt/26pt;36pt/36pt;42pt/42pt';
	//}
	config.toolbar_full = [
		['Cut','Copy','Paste','-',
		'Undo','Redo','-','Find',],['Font','FontSize'],
		['TextColor','BGColor','-',
		'Bold','Italic','Underline'],
		['NumberedList','BulletedList','-','JustifyLeft','JustifyCenter','JustifyRight'],
		['wcmimage','wcmvideo','Link','Unlink','Anchor']
	];
	config.fullPage = true;
};
	