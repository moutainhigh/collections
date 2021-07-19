(function(factory){
	if(typeof define == 'function' && define.amd){
		define(['jquery'],factory);
	}else{
		factory(jQuery);
	}
}(function($){
	var CountDown = function(element,options){
		this.element = element;
		this._init();
	}
	CountDown.defaults = {
		count : 60,      //倒计时60s
		text : '获取验证码',      //button显示的默认文字
		countingText : '重新获取验证码',      //计数是显示的文字
		
	}
	CountDown.prototype = {
		_init : function(){
			
		}
	}
}));