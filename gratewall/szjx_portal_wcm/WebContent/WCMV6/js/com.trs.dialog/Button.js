$package("com.trs.dialog");
com.trs.dialog.Button = {
	instance : function(id,type,name,dialog,func){
		var button = $(id);
		if(!button){
			button = document.createElement("input");
			button.type = type;
			button.id = id;
			button.style.border = 0;
		}
		if(type=="image"){
			button.src = name;
		}
		else{
			button.style.width = 62;
			button.style.height = 22;
			button.style.background = "url("+com.trs.dialog.imgPath+"bg.gif)";
			button.style.lineHeight = "20px";
			button.value = name;
		}
//		document.body.appendChild(button);
		button.dialog = dialog;
		button.func = func;
		button.onclick = function(){
			if(!this.func&&this.dialog){
				this.dialog.hide();
			}
			else{
				if(typeof(this.func)=='string'){
					eval(this.func);
				}
				else{
					this.func.apply(this);
				}
			}
		};
		button.destroy = function(){
			com.trs.dialog.Button.destroy(this);
		}
		// 按钮之间分开一些距离
		button.style.marginLeft = '10';
		button.style.marginRight = '10';
		return button;
	},
	OK : function(id,dialog,func){
		return this.instance('OK_'+id,'button','确定',dialog,func);
	},
	CLOSE : function(id,dialog,func){
		return this.instance('Close_'+id,'image',com.trs.dialog.imgPath+'close.gif',dialog,func);
	},
	CANCEL : function(id,dialog,func){
		return this.instance('Cancel_'+id,'button','取消',dialog,func);
	},
	destroy : function(_oButton){
		_oButton.func = null;
		_oButton.dialog = null;
		_oButton.onclick = null;
		_oButton.destroy = null;
		if(_oButton.parentNode){
			_oButton.parentNode.removeChild(_oButton);
		}
	}
}
