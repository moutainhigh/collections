CKEDITOR.plugins.add('replaceNbsps',
{
	init : function( editor )
	{
		editor.addCommand('replaceNbspsCommand',
		{
			exec : function(editor){
				var data = editor.getData();
				data = data.replace(/&nbsp;&nbsp;|&nbsp; |  | &nbsp;/ig, '　');
				setTimeout(function(){
					editor.setData(data, null, false); 
				}, 0);
			}
		});
		editor.ui.addButton('replaceNbsps', 
			{
				label: '转换全角空格',
				command: 'replaceNbspsCommand',
			});
	}
});
