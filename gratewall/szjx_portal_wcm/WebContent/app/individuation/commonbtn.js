var Button = {
	okBtn		: 'okBtn',
	cancelBtn	: 'cancelBtn',

	initButtonEvent : function(){
		var okBtn = $(Button.okBtn);
		var cancelBtn = $(Button.cancelBtn);

		Event.observe(okBtn, 'mouseenter', function(){
			Element.addClassName(okBtn, 'btnHover');
		});
		Event.observe(okBtn, 'mouseleave', function(){
			Element.removeClassName(okBtn, 'btnHover');
			Element.removeClassName(okBtn, 'btnDown');
		});
		Event.observe(okBtn, 'mousedown', function(){
			Element.removeClassName(okBtn, 'btnHover');
			Element.addClassName(okBtn, 'btnDown');
		});
		Event.observe(okBtn, 'mouseup', function(){
			Element.removeClassName(okBtn, 'btnDown');
		});  
		Event.observe(cancelBtn, 'mouseenter', function(){
			Element.addClassName(cancelBtn, 'btnHover');
		});   
		Event.observe(cancelBtn, 'mouseleave', function(){
			Element.removeClassName(cancelBtn, 'btnHover');
			Element.removeClassName(cancelBtn, 'btnDown');
		});
		Event.observe(cancelBtn, 'mousedown', function(){
			Element.removeClassName(cancelBtn, 'btnHover');
			Element.addClassName(cancelBtn, 'btnDown');
		}); 
		Event.observe(cancelBtn, 'mouseup', function(){ 
			Element.removeClassName(cancelBtn, 'btnDown');
		}); 		
	},

	initButton : function(){
		this.initButtonEvent();
	}
};

Event.observe(window, 'load', Button.initButton.bind(Button));