$package('com.trs.wcm');
if(!window.PopTip){
	var PopTip = {
		aElement : [],
		aElementEventCache : {},
		popWin	 : null,
		register : function(aElement){
			this.aElement = aElement;
		},
		registerAtOnce : function(aElement){
			this.bindEvent(aElement);
		},
		unRegisterAtOnce : function(aElement){
			for (var i = 0; i < aElement.length; i++){
				var element = $(aElement[i]);
				if(!element) continue;
				var uniqueId = element.uniqueID;
				if(!this.aElementEventCache[uniqueId]){
					continue;
				}
				Event.stopObserving(element, 'mouseover', this.aElementEventCache[uniqueId]['show']);
				Event.stopObserving(element, 'mouseout', this.aElementEventCache[uniqueId]['hide']);
				Event.stopObserving(element, 'focus', this.aElementEventCache[uniqueId]['show']);
				Event.stopObserving(element, 'blur', this.aElementEventCache[uniqueId]['hide']);
				delete this.aElementEventCache[uniqueId]['show'];
				delete this.aElementEventCache[uniqueId]['hide'];
				delete this.aElementEventCache[uniqueId];
			}
		},
		bindEvent : function(aElement){
			if(aElement.length <= 0) return;
			for (var i = 0; i < aElement.length; i++){
				var element = $(aElement[i]);
				if(!element) continue;
				var uniqueId = element.uniqueID;
				if(!this.aElementEventCache[uniqueId]){
					this.aElementEventCache[uniqueId] = {};
				}
				this.aElementEventCache[uniqueId]['show'] = this.show.bind(this, element);
				this.aElementEventCache[uniqueId]['hide'] = this.hide.bind(this, element);
				Event.observe(element, 'mouseover', this.aElementEventCache[uniqueId]['show']);
				Event.observe(element, 'mouseout', this.aElementEventCache[uniqueId]['hide']);
				Event.observe(element, 'focus', this.aElementEventCache[uniqueId]['show']);
				Event.observe(element, 'blur', this.aElementEventCache[uniqueId]['hide']);
			}
			this.popWin = window.createPopup();
			with(this.popWin.document.body.style){
				backgroundColor = "lightyellow";
				border = "solid black 1px";
				overflow = "hidden";
				fontSize = "12px";
				fontFamily = "arial";
				fontWeight = 'normal';
				margin = "0px";
				padding = "3px";
			}
		},
		getDimensions : function(sInnerHTML){
			var tempNode = document.createElement("span");
			with(tempNode.style){
				margin = "0px";
				padding = "3px";
				border = "solid black 1px";
				fontSize = "12px";
				fontFamily = "arial";
				overflow = "visible";
				fontWeight = 'normal';
				display = 'none';
				position = 'absolute';
			}
			tempNode.innerHTML = sInnerHTML;
			document.body.appendChild(tempNode);
			var offsets = Element.getDimensions(tempNode);
			Element.remove(tempNode);
			return offsets;
		},
		show : function(element){
			if(!this.popWin || !element.getAttribute("_title")) return;
			var sInnerHTML = element.getAttribute("_title");
			var offsets = this.getDimensions(sInnerHTML);
			var width = offsets["width"] > 350 ? 350 : offsets["width"];
			var height = offsets["height"] > 300 ? 300 : offsets["height"];
			this.popWin.document.body.innerHTML = sInnerHTML;
			this.popWin.show(element.offsetWidth, element.offsetHeight, width, height, element);
		},
		hide : function(element){
			this.popWin.hide();
		},
		destroy : function(){
			this.popWin = null;
			for (var i = 0; i < this.aElement.length; i++){
				delete this.aElement[i];
			}
			for (var uniqueId in this.aElementEventCache){
				try{
					delete this.aElementEventCache[uniqueId]['show'];
					delete this.aElementEventCache[uniqueId]['hide'];
					delete this.aElementEventCache[uniqueId];
				}catch(error){
				}
			}
		}
	}
}
Event.observe(window, 'load', function(){
	PopTip.bindEvent(PopTip.aElement);
});
Event.observe(window, 'unload', PopTip.destroy.bind(PopTip));