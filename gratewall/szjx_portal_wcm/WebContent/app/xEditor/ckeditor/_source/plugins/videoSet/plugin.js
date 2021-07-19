CKEDITOR.plugins.add('videoSet',
{
	//lang:['zh-cn','en'],//多语言处理
	init : function( editor )
	{
		CKEDITOR.dialog.add('videoSet', this.path + 'dialogs/videoSet.js');
		editor.addCommand( 'videoSet', new CKEDITOR.dialogCommand('videoSet'));
	}
});