//冒泡框
Ext.ns('wcm.BubblePanel');
function defBubblePanel(){
	var ua = navigator.userAgent.toLowerCase();
	var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
	var hide = function(ev){
		if(ev.type=='blur' && this.extEl.contains(ev.blurTarget))return;
		var el = this.extEl.dom;
		setTimeout(function(){
			el.style.display = 'none';
		}, 100);
	}
	var bubble = function(p, map, render){
		var el = this.extEl.dom;
		if(map)p = map.apply(el, [p]);
		var ost = el.style;
		if(render){
			render.apply(el, [p]);
		}else if(p){
			ost.left = p[0] + 'px';
			ost.top = p[1] + 'px';
		}
		ost.display = '';
		el.focus();
	};
	wcm.BubblePanel = function(el, fid){
		el = $(el);
		var extEl = this.extEl = Ext.get(el);
		if(isIE){
			var arr = el.getElementsByTagName("*");
			for(var i=0;i<arr.length;i++)
				arr[i].setAttribute('unselectable', 'on');
		}
		else extEl.dom.tabIndex = 100;
		extEl.on('click', hide, this);
		extEl.on('blur', hide, this);
		this.bubble = bubble;
	}
};
defBubblePanel();
