CKEDITOR.plugins.add('testuidialog',
{
	init:function(editor){
		var pluginName = 'testuidialog';
		editor.ui.addButton('testuiDialog',
			{
				label: 'ui窗口',
				command: pluginName,
				icon : this.path + 'testDialog.jpg' 
			});
		CKEDITOR.dialog.add(pluginName, this.path + 'dialogs/testuidialog.js');
		editor.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));

	}
});