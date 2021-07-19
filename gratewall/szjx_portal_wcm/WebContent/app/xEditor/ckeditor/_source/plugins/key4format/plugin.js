CKEDITOR.plugins.add('key4format',
{
	//lang:['zh-cn','en'],//多语言处理
	init : function( editor )
	{
		editor.addCommand('key4formatCommand',
		{
			exec : function(editor){
				var elDiv = document.getElementById("cke_contents_editor").childNodes;
				var doc = elDiv[0].contentWindow.document.body;
				//doc = cke_contents_ckeditor
				var args = {
					"doc"			:doc
					//GetHtml		:function(){},
					//SetHtml		:function(sHtml){},
					//"html"		:document.getElementById("mytext").innerHTML
				};
				html=window.showModalDialog("ckeditor/_source/plugins/key4format/dialogs.html",args,"dialogHeight=240px;dialogWidth=710px");
				//中途终止操作时，没有返回值，不会将传回的空值传递给页面
				if(html) {
					setTimeout(function(){
						editor.setData(html);
					}, 0);
				}
			}
		});
		editor.ui.addButton('key4format', 
		{
			label: '一键排版',//editor.lang.test.btnName,
			command: 'key4formatCommand'
			//icon : this.path + 'testButton.jpg'
		});
	}
});