CKEDITOR.plugins.add('test',
{
	lang:['zh-cn','en'],//多语言处理
	init : function( editor )
	{
		editor.addCommand('testCommand',
		{
			exec : function(editor){
				
			}
		});
		editor.ui.addButton('testButton', 
			{
				label: 'testButton',
				command: 'testCommand',
				icon : this.path + ' testButton.jpg'
			});

		//CKEDITOR.dialog.add('testDialog', this.path + 'dialogs/testDialog.js');                        
		//editor.addCommand('testCommand', new CKEDITOR.dialogCommand('testDialog'));
	}
});