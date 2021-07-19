/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.plugins.add( 'photolib',
{
	init : function( editor )
	{		
		var command = editor.addCommand( 'photolib', {
			exec : function( editor )
			{
				var url = CKEDITOR.plugins.getPath('photolib') + 'photolib.jsp';
				
				window.showModalDialog(url, {CKEDITOR : CKEDITOR, editor : editor}, "dialogWidth:950px;dialogHeight:660px;");
			}
		});

		command.modes = { wysiwyg:1 };

		editor.ui.addButton( 'photolib', {
				label : 'Photo Library',
				icon : CKEDITOR.plugins.getPath('photolib') + 'photolib.gif',
				command : 'photolib'
		});
	}
});
