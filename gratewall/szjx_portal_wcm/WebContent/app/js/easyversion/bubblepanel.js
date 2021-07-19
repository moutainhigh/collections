//冒泡框
Ext.ns('wcm.BubblePanel');
function defBubblePanel(){
	var contains = function(t, p){
		while(p && p.tagName!='BODY'){
			if(t==p)return true;
			p = p.parentNode;
		}
		return false;
	}

	var ua = navigator.userAgent.toLowerCase();
	var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
	var hide = function(ev){
		if(ev.type=='blur' && contains(this.extEl.dom, ev.blurTarget))return;
		var el = this.extEl.dom;
		BpShieldMgr.hideShield(el);
		el.style.display = 'none';
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
		BpShieldMgr.showShield(el);
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

var BpShieldMgr = {
	initShield : function(el){
		if(!Ext.isIE6) return;
		if($(el.id + '-shldbp')) return;
		var dom = document.createElement('iframe');
		dom.src = Ext.blankUrl;
		dom.style.display = 'none';
		dom.style.position = 'absolute';
		dom.style.zIndex = el.style.zIndex - 1;
		dom.id = el.id + '-shldbp';
		document.body.appendChild(dom);
	},
	showShield : function(el){
		if(!Ext.isIE6) return;
		this.initShield(el);
		var oStyle = $(el.id + '-shldbp').style;
		if(el.style.left)
			oStyle.left = (parseInt(el.style.left, 10))+"px";
		if(el.style.right)
			oStyle.right = (parseInt(el.style.right, 10))+"px";
		oStyle.top = (parseInt(el.style.top, 10))+"px";
		oStyle.width = (el.offsetWidth)+"px";
		oStyle.height = (el.offsetHeight)+"px";
		oStyle.display = '';
	},
	hideShield : function(el){
		if(!Ext.isIE6) return;
		$(el.id + '-shldbp').style.display = 'none';
	},
	destroyShield : function(){
		if(!Ext.isIE6) return;
		var dom = $(this.id + '-shldbp');
		if(!dom) return;
		dom.parentNode.removeChild(dom);
	}
}
