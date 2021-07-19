CKEDITOR.plugins.add( 'testbutton',
{
	requires : ['bottombutton'],
	init:function(editor){
		/*
		*插件底部按钮的初始化：定义按钮参数，调用BtnHandler来添加按钮
		*/
		var ButtonArray1 = [{
			title	: "粘贴",
			key		: "paste",
			order	: 3,
			cmd		: function(){alert("粘贴");}
		},{
			title	: "复制",
			key		: "copy",
			order	: 4,
			cmd		: function(){alert("复制");}
			
		}];
		BtnHandler.addBtns(ButtonArray1);
/********************************************************************/
		var ButtonArray2 = [{
			title	: "粘贴",
			key		: "pastes",
			order	: 3,
			cmd		: function(){alert("粘贴");}
		},{
			title	: "复制",
			key		: "copys",
			order	: 4,
			cmd		: function(){alert("复制");}
			
		}];
		BtnHandler.addBtns(ButtonArray2);

	}
});
