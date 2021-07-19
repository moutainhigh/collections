var $ = window.$ || {};
(function(host){
var 
guid = 0,
cache = {},
isSecure = location.href.toLowerCase().indexOf("https") === 0,
blankUrl = isSecure ? "javascript:false" : '',
isStrict = document.compatMode == "CSS1Compat",
ua = navigator.userAgent.toLowerCase(),
isIE6 = ua.indexOf("opera") == -1 && ua.indexOf("msie 6") > -1,
btnTemplate = [
	'<div class="wcm-btn wcm-btn-left {2}" id="{0}" btn="{0}">',
		'<div class="wcm-btn-right">',
			'<div class="wcm-btn-center">',
				'<a class="wcm-btn-text" href="#" onfocus="this.blur();">{1}</a>',
			'</div>',
		'</div>',
	'</div>'
].join(""),
template = [
	'<div class="wcm-cbd" id="{0}" style="visibility:hidden;">',
		'<div class="header l" id="header-{0}"><div class="r"><div class="c">',
			'<div class="spt"></div>',
			'<div class="title" id="dialogTitle-{0}">{1}</div>',
			'<div class="tools" id="tools-{0}">',
				'<a class="close" href="#" id="close-{0}"></a>',
			'</div>',
		'</div></div></div>',
		'<div class="body l"><div class="r"><div class="c">',
			'<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
				'<tr><td id="content-{0}">',
				'<iframe src="{2}" id="frm-{0}" style="height:100%;width:100%;"',
				' frameborder="0" onload="__CrashBoardContentLoaded__(\'{0}\', this);"></iframe>',
				'</td></tr>',
				'<tr style="display:{5}">',
					'<td class="buttons" id="buttons-{0}" style="text-align:center;"></td>',
				'</tr>',
			'</table>',
		'</div></div></div>',
		'<div class="footer l"><div class="r"><div class="c"></div></div></div>',
	'</div>'
].join("");

function $cb(cfg){
	cfg = apply({
		id : 'cb-' + (++guid),
		title : '系统提示框'
	}, cfg);
	apply(this, cfg);
	cache[cfg.id] = this;
}
$cb.prototype = {
	getEl : function(id){
		return $(id || this.id);
	},
	show : function(){
		var t = this;
		if(!t.rendered){
			t.rendered = true;

			//append html
			var sHtml = format(template, t.id, t.title, t.url);
			var div = document.createElement('DIV');
			document.body.appendChild(div);
			div.innerHTML = sHtml;
			var cbEle = t.getEl();

			//set bounds
			if(t.width) cbEle.style.width = t.width;
			if(t.height) t.getEl('content-' + t.id).style.height = t.height;
			var docEle = isStrict ? document.documentElement : document.body;
			var left = (docEle.clientWidth - cbEle.offsetWidth) / 2 + docEle.scrollLeft + 'px';
			var top = (docEle.clientHeight - cbEle.offsetHeight) / 2 + docEle.scrollTop + 'px';
			cbEle.style.left = t.left || left;
			cbEle.style.top = t.top || top;
			cbEle.style.zIndex = 999 + guid;
			cbEle.style.visibility = 'visible';
			cbEle.style.display = '';

			//bind events
			if(this.draggable !== false) drag(cbEle, t.getEl('header-' + t.id), this.maskable);
			t.getEl('close-' + t.id).onclick = function(){t.close.apply(t, arguments)};
			t.getEl('buttons-' + t.id).onclick = function(){t.onClick.apply(t, arguments)};
		}
		this.getEl().style.display = '';
		this.showShield();
	},
	renderBtns : function(btns){
		this.btns = btns = btns || [];
		var aHtml = [];
		for(var i = 0; i < btns.length; i++){
			var btn = btns[i]; btn.id = btn.id || ('btn-'+(++guid)); btns[btn.id] = btn;
			aHtml.push(format(btnTemplate, btn.id, btn.text, btn.extraCls||""));
		}
		this.getEl('buttons-' + this.id).innerHTML = aHtml.join("");
	},
	hide : function(){
		this.getEl().style.display = 'none';
		this.hideShield();
	},
	close : function(){
		try{
			var t = this;
			t.hide();
			t.getEl("frm-" + t.id).src = blankUrl;
			t.getEl("content-" + t.id).innerHTML = '';
			t.getEl("close-" + t.id).onclick = null;
			t.getEl("buttons-" + t.id).onclick = null;
			t.destroyShield();
			delete cache[t.id];
			dom = t.getEl();
			dom.parentNode.parentNode.removeChild(dom.parentNode);
			dom = null;
		}catch(err){}
	},
	onClick : function(event){
		var t = this;
		event = window.event || event;
		var dom = event.srcElement || event.target;
		var btnId, btnsId='buttons-' + t.id;
		while(dom && dom.id != btnsId){
			btnId = dom.getAttribute("btn");
			if(btnId)break;
			dom = dom.parentNode;
		}
		if(t.btns[btnId] && t.btns[btnId].cmd){
			if(t.btns[btnId].cmd.call(t, t.btns[btnId]) !== false){
				t.close();
			}
		}
	},
	initShield : function(){
		if(!this.maskable && !isIE6) return;
		if($(this.id + '-shld')) return;
		var dom = document.createElement('iframe');
		dom.src = blankUrl;
		dom.style.display = 'none';
		dom.style.border = 0;
		dom.frameBorder = 0;
		dom.className = 'wcm-panel-shield';
		dom.style.zIndex = this.getEl().style.zIndex - 1;
		dom.id = this.id + '-shld';
		document.body.appendChild(dom);
	},
	showShield : function(){
		if(!this.maskable && !isIE6) return;
		this.initShield();
		var dom = this.getEl();
		var oStyle = $(this.id + '-shld').style;
		if(!this.maskable){
			oStyle.left = (parseInt(dom.style.left, 10) )+"px";
			oStyle.top = (parseInt(dom.style.top, 10) + 1)+"px";
			oStyle.width = (dom.offsetWidth - 4)+"px";
			oStyle.height = (dom.offsetHeight - 4)+"px";
		}	
		oStyle.display = '';
	},
	hideShield : function(){
		if(!this.maskable && !isIE6) return;
		$(this.id + '-shld').style.display = 'none';
	},
	destroyShield : function(){
		if(!this.maskable && !isIE6) return;
		var dom = $(this.id + '-shld');
		if(!dom) return;
		dom.parentNode.removeChild(dom);
	}
};
function $(id){
	return document.getElementById(id);
}
function apply(o, c){
	if(o && c && typeof c == 'object'){
		for(var p in c){
			o[p] = c[p];
		}
	}
	return o;
};
function format(format){
	var args = Array.prototype.slice.call(arguments, 1);
	return format.replace(/\{(\d+)\}/g, function(m, i){
		return args[i]||"";
	});
}
function $toQueryStr(params){
	var rst = [];
	for(var nm in params){
		var v = params[nm], type = typeof v;
		if(type!='string' && type!='number' && type!='boolean')continue;
		rst.push(nm, '=', encodeURIComponent(params[nm]), '&');
	}
	return rst.join('');
}
var getStyle = function(){
	return window.getComputedStyle ? function(el, style){
		var cs = window.getComputedStyle(el, "");
		return cs ? cs[style] : null;
	} : function(el, style){
		return el.style[style] || el.currentStyle[style];
	}
}();
function drag(o, p, maskable){
	var id = o.id;
	p.onmousedown=function(a){
		var frm = $('frm-' + id);
		if(frm) frm.style.visibility = 'hidden';
		var sld = $(id + '-shld');
		var d=document;if(!a)a=window.event;
		var l=parseInt(getStyle(o,'left'),10),t=parseInt(getStyle(o,'top'),10);
		var x=a.pageX?a.pageX:a.clientX,y=a.pageY?a.pageY:a.clientY;
		if(p.setCapture)p.setCapture();
		else if(window.captureEvents)window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
		d.onmousemove=function(a){
			if(!a)a=window.event;
			if(!a.pageX)a.pageX=a.clientX;
			if(!a.pageY)a.pageY=a.clientY;
			var tx=a.pageX-x+l,ty=a.pageY-y+t;
			o.style.left=tx+"px";
			o.style.top=ty+"px";
			if(!maskable && sld){
				sld.style.left=(tx)+"px";
				sld.style.top=(ty+1)+"px";
			}
		}
		d.onmouseup=function(){
			if(frm) frm.style.visibility = 'visible';
			if(p.releaseCapture)p.releaseCapture();
			else if(window.releaseEvents)window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			d.onmousemove=null;
			d.onmouseup=null;
		}
    }
}
__CrashBoardContentLoaded__ = function(id, frm){
	var cb = cache[id];
	if(!cb) return;
	try{
		var win = frm.contentWindow;
		cb.renderBtns((win.m_cbCfg || {}).btns);
		if(!win.init)return;
	}catch(err){
		return;
	}
	win.init(cb.params, cb);
}
//expose the global host context
host.CrashBoard = function(cfg){
	cfg = apply({appendParamsToUrl : true}, cfg);
	var cjoin = cfg.url.indexOf('?')==-1 ? '?' : '&';
	if(cfg.appendParamsToUrl){
		cfg.url = cfg.url + cjoin + $toQueryStr(cfg.params);
	}
	return cache[cfg.id] || (new $cb(cfg));
}
})($);