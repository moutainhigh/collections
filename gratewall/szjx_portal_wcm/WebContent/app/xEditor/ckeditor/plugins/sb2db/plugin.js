CKEDITOR.plugins.add('sb2db',
{
	lang:['zh-cn','en'],//多语言处理
	init : function( editor )
	{
		var pluginName = 'sb2db';
		editor.addCommand(pluginName,
		{
			exec : function(editor){
				
			}
		});
		editor.ui.addButton(pluginName, 
		{
			label: editor.lang.test.btnName,
			command: pluginName,
			icon : this.path + ' sb2db.jpg'
		});
	}
});

function xx(){
	alert('test');
}