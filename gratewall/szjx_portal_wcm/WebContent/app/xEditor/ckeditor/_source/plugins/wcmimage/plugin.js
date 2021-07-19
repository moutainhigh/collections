/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.plugins.add( 'wcmimage',
{
	lang : ['zh-cn','en'],

	requires : ['image'],

	init : function( editor )
	{
		var pluginName = 'wcmimage';

		// Register the dialog.
		CKEDITOR.dialog.add( pluginName, this.path + 'dialogs/wcmimage.js' );

		// Register the command.		
		var command = editor.addCommand( pluginName, new CKEDITOR.dialogCommand( pluginName ));

		// Register the toolbar button.
		editor.ui.addButton( pluginName,
			{
				label : '插入图片',
				icon : this.path + 'wcmimage.png',
				command : pluginName
			});

		editor.on( 'doubleclick', function( evt )
			{
				var element = evt.data.element;

				if ( element.is( 'img' ) && !element.data( 'cke-realelement' ) && !element.isReadOnly() ){
					//evt.data.dialog = 'image';
					evt.data.dialog = 'imageSet';
				}
			});

		// If the "menu" plugin is loaded, register the menu items.
		if ( editor.addMenuItems )
		{
			editor.addMenuItems(
				{
					image :
					{
						label : editor.lang.image.menu,
						command : 'imageSet',
						group : 'image'
					}
				});
		}
	}
} );


