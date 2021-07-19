/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.plugins.add( 'wcmstyle',
{
	lang : ['zh-cn','en'],
	requires : [ 'dialog' ],
	init : function( editor )
	{		
		var command = editor.addCommand( 'wcmstyle', {
			exec : function( editor )
			{
				var featureViewIds = editor.window.$.parent.getParameter("featureViewIds");
				var featureChnlIds = editor.window.$.parent.getParameter("featureChnlIds");
				var url = top.WCMConstants.WCM6_PATH + 'document/product_features.html?'+"featureViewIds=" + featureViewIds + "&featureChnlIds=" + featureChnlIds;

				top.FloatPanel.open({
					src : url,
					title : 'Features Management',
					callback : function(info){
						editor.insertHtml(info);
					},
					dialogArguments : {}
				});
			}
		});

		command.modes = { wysiwyg:1 };

		editor.ui.addButton( 'wcmstyle', {
				label : editor.lang.wcmstyle.featuretip,
				icon : CKEDITOR.plugins.getPath('wcmstyle') + 'wcmstyle.gif',
				command : 'wcmstyle'
		});
	}
});
