CKEDITOR.plugins.add('test',
{
	//lang:['zh-cn','en'],//多语言处理
	init : function( editor )
	{	
		editor.ui.addButton('testButton', 
			{
				label: '按钮',
				command: 'testButton',
				icon : this.path + 'testButton.jpg'
			});
		editor.addCommand('testButton',
			{
				exec : function(editor){
					alert("孟岩：！@#￥%……");
					alert("宋健：恩");
				}
			});
	}
});