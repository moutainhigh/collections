/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.dialog.add( 'wcmimage', function( editor )
{
	var lang = editor.lang.image;

	return {
		title : '插入/编辑图片',
		minWidth : 580,
		minHeight :380,
		contents : [
			{
				id : 'tab0',
				label : '本地上传图片',
				title : '',
				expand : true,
				padding : 0,
				elements :
				[
					{
						type : 'html',
						html : '<iframe src="'+CKEDITOR.plugins.getPath( 'wcmimage' ) + 'form_Browse.html' + '" style="width:100%;height:100%;background-color:white;" frameborder="0"></iframe>'
					}
				]
			},
			{
				id : 'tab1',
				label : '引用网络图片',
				title : '',
				expand : true,
				padding : 0,
				elements :
				[
					{
						type : 'html',
						html : '<iframe src="'+CKEDITOR.plugins.getPath( 'wcmimage' ) + 'form_Text.html' + '" style="width:100%;height:100%;background-color:white" frameborder="0"></iframe>'
					}
				]
			},
			{
				id : 'tab2',
				label : '图片库图片',
				title : '',
				expand : true,
				padding : 0,
				elements :
				[
					{
						type : 'html',
						html : '<iframe src="'+CKEDITOR.plugins.getPath( 'wcmimage' ) + 'photo.jsp' + '" style="width:100%;height:100%;background-color:white" frameborder="0"></iframe>'
					}
				]
			}
		],
		onOk : function(){
			var dom = this.getElement().$.getElementsByTagName('iframe')[0];
			if(dom && dom.contentWindow && dom.contentWindow.onOk){
				return dom.contentWindow.onOk();
			}
		},
		buttons : []/*[ CKEDITOR.dialog.okButton, CKEDITOR.dialog.cancelButton ]*/
	};
} );
