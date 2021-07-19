/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

(function(){
	function createFakeElement( editor, realElement )
	{
		var fakeElement = editor.createFakeParserElement( realElement, 'cke_flash', 'flash', true ),
			fakeStyle = fakeElement.attributes.style || '';

		var width = realElement.attributes.width,
			height = realElement.attributes.height;

		fakeElement.attributes.title = realElement.attributes.title;
		fakeElement.attributes.src = realElement.attributes.imgurl;

		if ( typeof width != 'undefined' )
			fakeStyle = fakeElement.attributes.style = fakeStyle + 'width:' + cssifyLength( width ) + ';';

		if ( typeof height != 'undefined' )
			fakeStyle = fakeElement.attributes.style = fakeStyle + 'height:' + cssifyLength( height ) + ';';

		return fakeElement;
	}

	var cssifyLength = CKEDITOR.tools.cssLength;

CKEDITOR.plugins.add( 'flashlib',
{
	init : function( editor )
	{		
		var command = editor.addCommand( 'flashlib', {
			exec : function( editor )
			{
				var url =  top.WCMConstants.WCM6_PATH + 'video/fck_videolib.jsp';
				
				window.showModalDialog(url, {CKEDITOR : CKEDITOR, editor : editor}, "dialogWidth:950px;dialogHeight:660px;");
			}
		});

		command.modes = { wysiwyg:1 };

		editor.ui.addButton( 'flashlib', {
				label : 'Video Library',
				icon : CKEDITOR.plugins.getPath('flashlib') + 'photolib.gif',
				command : 'flashlib'
		});
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
						'div' : function( element )
						{
							if(element && element.attributes['__fckvideo']){
							
								return createFakeElement( editor, element );
							}
							return null;
							
						}
					}
				},
				5);
		}
	},

	requires : [ 'fakeobjects' ]

});
})();