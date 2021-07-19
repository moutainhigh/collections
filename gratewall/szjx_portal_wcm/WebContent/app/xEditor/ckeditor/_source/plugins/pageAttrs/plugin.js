CKEDITOR.plugins.add('pageAttrs',
{
	lang:['zh-cn','en'],
	init : function( editor )
	{
		var pluginName = 'pageAttrs';
		CKEDITOR.dialog.add(pluginName, this.path + 'dialogs/pageAttrs.js');        
		editor.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));
		editor.ui.addButton(pluginName, 
		{
			label: editor.lang.pageAttrs.btnName,
			command: pluginName
			//icon : this.path + 'testButton.jpg'
		});
	}
});