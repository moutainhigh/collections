/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.dialog.add( 'wcmimage', function( editor )
{
	var lang = editor.lang.image;

	return {
		title : lang.title,
		minWidth : 500,
		minHeight : 210,
		contents : [
			{
				id : 'tab1',
				label : '',
				title : '',
				expand : true,
				padding : 0,
				elements :
				[
					{
						type : 'html',
						html : '<iframe src="'+CKEDITOR.plugins.getPath( 'wcmimage' ) + 'page.html' + '" style="width:100%;height:100%;" frameborder="0"></iframe>'
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
		buttons : [ CKEDITOR.dialog.okButton, CKEDITOR.dialog.cancelButton ]
	};
} );
