/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.plugins.add( 'imageUpload',{
	requires : [ 'dialog' ],
//初始化函数
	init: function(editor){

//创建一个对话窗口
		CKEDITOR.dialog.add("imageUploadDialog", this.path + "dialogs/imageUpload.js")
//创建一个命令
		var command = editor.addCommand('imageUpload', new CKEDITOR.dialogCommand('imageUploadDialog'));

		//editor.getCommand('imageUpload').setState(CKEDITOR.TRISTATE_ON);	
//~定义命令的执行环境
		command.modes = { wysiwyg:1 };
//~添加按钮
		editor.ui.addButton('imageUpload',{
			lable:'imageUploadIcon',
			command:'imageUpload',
			icon: this.path + 'imageUpload.png'//icon路径,CKEDITOR.plugins.getPath('imageUpload')==this.path
		})	
	}
})