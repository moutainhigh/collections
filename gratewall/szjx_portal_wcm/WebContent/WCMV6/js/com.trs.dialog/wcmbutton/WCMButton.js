$package('com.trs.dialog.wcmbutton');
$importCSS('com.trs.dialog.wcmbutton.resource.WCMButton');


/**
*按钮的类型,当为自定义类型时，一般需要自己指定样式
*/
var $ButtonType = com.trs.dialog.ButtonType = {
	DEFAULT	: {
		ButtonClassName : 'btn_default',
		ButtonClassNameA: 'btn_default_a'
	},
	OK	: {
		ButtonClassName : 'btn_ok',
		ButtonClassNameA: 'btn_ok_a'
	},
	CANCEL	: {
		ButtonClassName : 'btn_cancel',
		ButtonClassNameA: 'btn_cancel_a'
	},
	CLOSE	: {
		ButtonClassName : 'btn_close',
		ButtonClassNameA: 'btn_close_a'
	},
	REFRESH	: {
		ButtonClassName : 'btn_refresh',
		ButtonClassNameA: 'btn_refresh_a'
	},
	MESSAGESEND	: {
		ButtonClassName : 'btn_message_send',
		ButtonClassNameA: 'btn_message_send_a'
	},
	SAVE	: {
		ButtonClassName : 'btn_save',
		ButtonClassNameA: 'btn_save_a'
	},
	CUSTOM	: {
		ButtonClassName : '',
		ButtonClassNameA: ''
	}
};
var $WCMButton = com.trs.dialog.WCMButton = Class.create();
$WCMButton.nextButtonId = 1;
Object.extend(com.trs.dialog.WCMButton.prototype, {	
	/**
	*@param	_oButtonRelated 包含一些关于按钮的相关信息
	*						如：ButtonId,ButtonType,Container,
	*							Action,ButtonText,ButtonClassName,
	*							ButtonClassNameA,ButtonClassNameD等
	*/
	initialize : function(_oButtonRelated){
		if(!_oButtonRelated){
			throw new Error("没有传入有关Button信息");
		}
		this.ButtonId = _oButtonRelated.ButtonId || ("_"+($WCMButton.nextButtonId++)+"_");
		this.ButtonType = _oButtonRelated.ButtonType || $ButtonType.DEFAULT;
		this.ButtonText = _oButtonRelated.ButtonText || '';
		this.ButtonClassName = _oButtonRelated.ButtonClassName || this.ButtonType.ButtonClassName;
		this.ButtonClassNameA = _oButtonRelated.ButtonClassNameA || this.ButtonType.ButtonClassNameA || this.ButtonClassName+"_a";
		this.ButtonClassNameD = _oButtonRelated.ButtonClassNameD || this.ButtonType.ButtonClassNameD || this.ButtonClassName+"_d";
		if(!this.ButtonClassName){
			throw new Error("没有设置按钮的样式");
		}
		this.Container = _oButtonRelated.Container;
		if(!_oButtonRelated.Action){
			this.Action = Prototype.emptyFunction;
		}else if(typeof _oButtonRelated.Action == 'string'){
			var caller = this;
			this.Action = function(){
				var oBtn = $(caller.ButtonId);
				if(oBtn.getAttribute("_disabled_")) return;
				eval(_oButtonRelated.Action);
			};
		}else if(typeof _oButtonRelated.Action == 'function'){
			var caller = this;
			this.Action = function(){
				var oBtn = $(caller.ButtonId);
				if(oBtn.getAttribute("_disabled_")) return;
				_oButtonRelated.Action;
			};
		}else{
			throw new Error("非法的Button执行命令，期望函数或字符串");
		}
	},
	loadButton : function(container){
		this.Container = $(container) || this.Container;
		if(!this.Container){
			throw new Error("没有指定button["+this.ButtonId+"]的容器");
		}
		var btnHTML = this.getButtonHTML();
		new Insertion.Bottom(this.Container, btnHTML);
		this.bindEvents();

		return this;
	},
	disable : function(){
		var oBtn = $(this.ButtonId);
		oBtn.setAttribute("_disabled_", true);
		this.toggleStyle("", this.ButtonClassNameD);
	},
	enable : function(){
		var oBtn = $(this.ButtonId);
		oBtn.setAttribute("_disabled_", false);
		this.toggleStyle(this.ButtonClassNameD, this.ButtonClassName);
	},
	toggleStyle : function(oldClassName, newClassName){		
		if(this.ButtonType == $ButtonType["CUSTOM"]){
			var targetId = this.ButtonId;
		}else{
			var targetId = this.ButtonId + "_middle";
		}
		if(oldClassName){
			Element.removeClassName(targetId, oldClassName);
		}else{
			Element.removeClassName(targetId, this.ButtonClassName);
			Element.removeClassName(targetId, this.ButtonClassNameA);		
		}
		Element.addClassName(targetId, newClassName);
	},
	bindEvents : function(){
		try{
			Event.observe(this.ButtonId, 'click', this.Action);
			Event.observe(this.ButtonId, 'mouseover', this.toggleStyle.bind(this, this.ButtonClassName, this.ButtonClassNameA));
			Event.observe(this.ButtonId, 'mouseout', this.toggleStyle.bind(this, this.ButtonClassNameA, this.ButtonClassName));
		}catch(error){
			throw new Error("button["+this.ButtonId+"]尚未加入dom树.\n"+error.message);
		}
	},
	getButtonHTML : function(){
		if(this.ButtonType == $ButtonType["CUSTOM"]){
			return '<span id="'+this.ButtonId+'" class="wcmbutton '+this.ButtonClassName+'" unselectable="on"></span>';
		}else{
			return '\
					<span id="'+this.ButtonId+'" class="wcmbutton">\
						<div class="btn_left"></div>\
						<div class="'+this.ButtonClassName+'" id="'+this.ButtonId+'_middle" unselectable="on">'+this.ButtonText+'</div>\
						<div class="btn_right"></div>\
					</span>';							
		}
	}
});