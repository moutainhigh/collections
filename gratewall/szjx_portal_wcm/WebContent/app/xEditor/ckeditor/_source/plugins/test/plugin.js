CKEDITOR.plugins.add('test',
{
	//lang:['zh-cn','en'],//多语言处理
	init : function( editor )
	{
		//editor.on( 'editingBlockReady', function(){alert("11")});
		//editor.on( 'readOnly', function(){alert("12")});
		//editor.on( 'click', function(){alert("13")});

		/*
		var myObject = { message : 'Example' };
		CKEDITOR.event.implementOn( myObject );
		myObject.on( 'testEvent', function(){
			alert( this.message );  // "Example"
		});
		myObject.fire( 'testEvent' );
		*/
		//var html = '<p>This is <b>an example</b>.</p>'; 
		//var data = editor.dataProcessor.toDataFormat( html ); 
		//var data = editor.dataProcessor.toDataFormat( html, '<p>'); 
		//alert(data);

		editor.addCommand('testCommand',
		{
			exec : function(editor){
				CKEDITOR.instances.editor.setData("输入文字");
				var h = editor.config.height;
				alert(h);
				//editor.config.height = '600px';
			}
		});
		editor.ui.addButton('test', 
		{
			label: 'test',//editor.lang.test.btnName,
			command: 'testCommand',
			icon : this.path + 'testButton.jpg'
		});
	}
});