CKEDITOR.plugins.add('clearWordStyle',
{
	init : function( editor )
	{
		editor.addCommand('clearWordStyleCommand',
		{
			exec : function(editor){
				alert(editor.getData());

			}
		});
		editor.ui.addButton('clearWordStyle', 
			{
				label: 'WORD清理',
				command: 'clearWordStyleCommand',
			});
	}
});
