CKEDITOR.plugins.add('testdialog',
{
	//lang:['zh-cn','en'],//多语言处理
	init : function( editor )
	{
		editor.ui.addButton('testDialog', 
			{
				label: '窗口',
				command: 'testDialog',
				icon : this.path + 'testDialog.jpg'
			});
		CKEDITOR.dialog.add('testDialog', this.path + 'dialogs/testDialog.js');
		editor.addCommand( 'testDialog', new CKEDITOR.dialogCommand('testDialog'));
	}
});