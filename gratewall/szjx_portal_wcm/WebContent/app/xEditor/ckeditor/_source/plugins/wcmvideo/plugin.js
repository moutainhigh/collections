/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

function createFakeElement( editor, realElement )
	{
		var fakeElement = editor.createFakeParserElement( realElement, 'cke_flash', 'flash', true ),
			fakeStyle = fakeElement.attributes.style || '';

		var width = realElement.attributes.width,
			height = realElement.attributes.height;

		/*if ( typeof width != 'undefined' )
			fakeStyle = fakeElement.attributes.style = fakeStyle + 'width:' + cssifyLength( width ) + ';';

		if ( typeof height != 'undefined' )
			fakeStyle = fakeElement.attributes.style = fakeStyle + 'height:' + cssifyLength( height ) + ';';
*/
		return fakeElement;
	}


CKEDITOR.plugins.add( 'wcmvideo',
{
	//lang : ['zh-cn','en'],
	init : function( editor )
	{
		var pluginName = 'wcmvideo';

		// Register the dialog.
		CKEDITOR.dialog.add( pluginName, this.path + 'dialogs/wcmvideo.js' );

		// Register the command.		
		var command = editor.addCommand( pluginName, new CKEDITOR.dialogCommand( pluginName ));

		// Register the toolbar button.
		editor.ui.addButton( pluginName,
			{
				label : "插入视频",//editor.lang.common.image,
				icon : this.path + 'wcmvideo.png',
				command : pluginName
			});

		/*editor.addCss(
			'img.cke_flash' +
			'{' +
				'background-image: url(' + CKEDITOR.getUrl( this.path + 'images/placeholder.png' ) + ');' +
				'background-position: center center;' +
				'background-repeat: no-repeat;' +
				'border: 1px solid #a9a9a9;' +
				//'width: 80px;' +
				//'height: 80px;' +
			'}'
		);*/


		editor.on( 'doubleclick', function( evt )
			{
				var element = evt.data.element;
				if ( element.is( 'img' ) && element.data( 'cke-real-element-type' ) == 'flash' && !element.isReadOnly() )
					evt.data.dialog = 'videoSet';
			});

		// If the "menu" plugin is loaded, register the menu items.
		if ( editor.addMenuItems )
		{
			editor.addMenuItems(
				{
					image :
					{
						label : editor.lang.image.menu,
						command : 'flash',
						group : 'video'
					}
				});
		}
	},
	afterInit : function( editor )
	{
		var dataProcessor = editor.dataProcessor,
			dataFilter = dataProcessor && dataProcessor.dataFilter;

		if ( dataFilter )
		{
			dataFilter.addRules(
				{
					elements :
					{
						'cke:embed' : function( element )
						{
							//if(element && element.attributes['mediatype']){
							
								return createFakeElement( editor, element );
							//}
							//return null;
							
						}
					}
				},
				5);
		}
	},

	requires : [ 'fakeobjects' ]

} );
