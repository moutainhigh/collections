CKEDITOR.plugins.add('imageSet',
{
	//lang:['zh-cn','en'],//多语言处理
	init : function( editor )
	{
		CKEDITOR.dialog.add('imageSet', this.path + 'dialogs/imageSet.js');
		editor.addCommand( 'imageSet', new CKEDITOR.dialogCommand('imageSet'));
	}
});