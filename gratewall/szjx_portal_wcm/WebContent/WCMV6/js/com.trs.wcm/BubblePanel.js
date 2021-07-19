$package('com.trs.wcm');
var BubblePanel = com.trs.wcm.BubblePanel = Class.create('wcm.BubblePanel');
com.trs.wcm.BubblePanel.prototype = {
	initialize : function(_element){
		if(Element.getStyle(_element,'position')!='absolute'&&!_element.tabIndex){
			alert('需要设置"'+_element.id+'"的position为absolute或者tabIndex为>0的数.');
		}
		var caller = this;
		Event.observe(_element,'click',function(event){
			Element.hide(_element);
			(caller.doAfterHide||Prototype.emptyFunction)(event || window.event);
		});
		Event.observe(_element,'blur',function(event){
			event = window.event || event;
			if(!Position.within(_element,Event.pointerX(event),Event.pointerY(event))){
				Element.hide(_element);
				(caller.doAfterHide||Prototype.emptyFunction)(event || window.event);
			}else{
				_element.focus();
			}
		});
		this.element = _element;
	},
	bubble : function(_Point,_Mapping, referTo){
		Element.show(this.element);
		if(_Mapping){
			_Point = _Mapping.apply(this.element,[_Point]);
		}
		if(_Point){
			if(referTo){
				var offset = Position.cumulativeOffset(referTo);
				if((this.element.offsetHeight + _Point[1]) > (referTo.offsetHeight + offset[1])){
					_Point[1] -= this.element.offsetHeight;
					if(_Point[1] < 0){
						_Point[1] = 10;
					}
				}
			}
		
			this.element.style.left = _Point[0] + 'px';
			this.element.style.top = _Point[1] + 'px';
		}
		this.element.focus();
	},
	destroy:function(){
		Event.stopAllObserving(this.element);
		this.doAfterHide = null;
		this.element = null;
	},
	hide : function(event){
		if(this.element == null) {
			return;
		}
		//else
		Element.hide(this.element);
		(this.doAfterHide || Prototype.emptyFunction)(event || window.event);		
	}
}