//窗口
var Ext = window.Ext || {};
var wcm = window.wcm || {};
Ext.apply = function(o, c, defaults){
    if(defaults){
        Ext.apply(o, defaults);
    }
    if(o && c && typeof c == 'object'){
        for(var p in c){
            o[p] = c[p];
        }
    }
    return o;
};
(function(){
	var ua = navigator.userAgent.toLowerCase();
	var isOpera = ua.indexOf("opera") > -1;
	Ext.apply(Ext, {
		isOpera : isOpera,
		isSafari : (/webkit|khtml/).test(ua),
		isIE : !isOpera && ua.indexOf("msie") > -1
	});
})();
if(Ext.isIE){
	try{
		document.execCommand("BackgroundImageCache", false, true);
	}catch(e){}
}
String.format = function(format){
	var args = Array.prototype.slice.call(arguments, 1);
	return format.replace(/\{(\d+)\}/g, function(m, i){
		return args[i];
	});
}
if(!$){
	var $ = function(id){
		return document.getElementById(id);
	};
}

/**
 * @class wcm.CrashBoard
*/
var m_btnTemplate = '<button onclick="" class="cbd-btn">{0}</button>'; 
var m_frmTemplate = '<iframe src="{0}" id="frm-{1}" style="height:100%;width:100%;" frameborder="0" onload="wcm.CrashBoard.contentLoaded(\'{1}\', this);"></iframe>';
var m_template = [
	'<div class="wcm-cbd" id="{0}">',
		'<div class="header l" id="header-{0}"><div class="r"><div class="c">',
			'<div class="spt"></div>',
			'<div class="title" id="dialogTitle-{0}">{1}</div>',
			'<div class="tools" id="tools-{0}">',
				'<a class="close" href="#" id="close-{0}"></a>',
			'</div>',
		'</div></div></div>',
		'<div class="body l"><div class="r"><div class="c">',
			'<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
				'<tr><td id="content-{0}">{3}</td></tr>',
				'<tr><td class="buttons" id="buttons-{0}"></td></tr>',
			'</table>',
		'</div></div></div>',
		'<div class="footer l"><div class="r"><div class="c"></div></div></div>',
	'</div>',
].join("");

wcm.CrashBoard = function(config){
	var config = Ext.apply({
		id : 'cb-' + new Date().getTime(),
		title : wcm.LANG.crashborad_2011 || '系统提示框',
		appendParamsToUrl : true
	}, config);
	Ext.apply(this, config);
	wcm.CrashBoard.register(this.id, this);
};

Ext.apply(wcm.CrashBoard, {
	cache : {},
	register : function(id, cb){
		this.cache[id] = cb;
	},
	remove : function(id){
		delete this.cache[id];
	},
	get : function(id){
		return this.cache[id];
	},
	contentLoaded : function(id, frm){
		var cb = this.get(id);
		if(!cb) return;
		var win = null;
		try{
			win = frm.contentWindow;
			if(win.m_cbCfg){
				cb.clearBtns();
				cb.addBtns(win.m_cbCfg);
			}
		}catch(error){
			//alert(error.message);
		}
		try{
			if(win.init){
				win.init(cb.params);
			}
		}catch(error){
			//alert(error.message);
		}
		cb.inited = true;
	}
});

Ext.apply(wcm.CrashBoard.prototype, {
	getWin : function(){
		return window;
	},
	getEl : function(id){
		id = id || this.id;
		var win = this.getWin();
		return (win.$ || win.document.getElementById)(id);
	},
	notify : function(){
		if(this.callback){
			this.callback.apply(this, arguments);
		}
	},
	show : function(){
		this.render();
		this.getEl().style.display = '';
		this.setSize(this.width, this.height);
		var position = this.getCenterXY();
		this.left = this.left || position['x'];
		this.top = this.top || position['y'];
		this.setPosition(this.left, this.top);
	},
	getCenterXY : function(){
		var doc = this.getWin().document;
		var container = Ext.isIE ? doc.body : doc.documentElement;
		var dom = this.getEl();
		var l = (parseInt(container.clientWidth, 10) - parseInt(dom.offsetWidth, 10)) / 2 + parseInt(container.scrollLeft, 10);
		var left = l + "px";
		var t = (parseInt(container.clientHeight, 10) - parseInt(dom.offsetHeight, 10)) / 2 + parseInt(container.scrollTop, 10);
		var top = t + "px";
		return {x : left, y : top};
	},
	setSize : function(width, height){
		if(width) this.getEl().style.width = width;
		if(height) {			
			var dom = this.getEl('content-' + this.id);
			dom.style.height = height;
			dom.style.height = (parseInt(dom.offsetHeight, 10) - 65) + 'px';
		}
	},
	setPosition : function(left, top){
		var dom = this.getEl();
		if(left) dom.style.left = left;
		if(top) dom.style.top = top;
	},
	render : function(){
		if(this.rendered) return;
		this.rendered = true;
		var sHtml = String.format(m_template, this.id, this.title);
		var dom = this.getWin().document.body;
		insertBeforeEnd(dom, sHtml);
		this.renderContent();
		var caller = this;
		this.getEl("close-" + this.id).onclick = function(){caller.close()};
		drag(this.getEl(), this.getEl('header-' + this.id));
		var btns = this.btns || [];
		this.btns = [];
		this.addBtns(btns);
		this.getEl("buttons-" + this.id).onclick = function(event){caller.btnClick(event)};
	},	
	renderContent : function(){
		if(this.src){
			var sParams = "";
			if(this.params && this.appendParamsToUrl){
				sParams = $toQueryStr(this.params);
				sParams = (this.src.indexOf("?") > 0 ? "&" : "?") + sParams;
			}
			this.html = String.format(m_frmTemplate, this.src + sParams, this.id);
		}
		if(this.el){
			this.el = $(this.el);
			this.html = this.el.innerHTML;
			this.el.innerHTML = "";
		}
		if(!this.html) return;
		this.getEl("content-" + this.id).innerHTML = this.html;
	},
	clearBtns : function(){
		this.btns = [];
		var dom = this.getEl('buttons-' + this.id);
		dom.innerHTML = "";
	},
	addBtns : function(btns){
		var aHtml = [];
		for (var i = 0; i < btns.length; i++){
			var btn = btns[i];
			this.btns.push(btn);
			aHtml.push(String.format(m_btnTemplate, btn['text']));
		}
		var dom = this.getEl('buttons-' + this.id);
		insertBeforeEnd(dom, aHtml.join(""));
	},
	btnClick : function(event){
		event = window.event || event;
		var dom = event.srcElement || event.target;
		if(dom.tagName != 'BUTTON') return;
		var value = dom.innerHTML;
		for (var i = 0, length = this.btns.length; i < length; i++){
			if(this.btns[i].text == value){
				var cmd = this.btns[i].cmd || this.close;
				if(cmd.call(this) !== false){
					this.close();
				}
				break;
			}
		}
	},
	hide : function(){
		this.getEl().style.display = 'none';
	},
	close : function(){
		this.hide();
		var dom = this.getEl("content-" + this.id);
		if(this.el){
			this.el.innerHTML= dom.innerHTML;
			delete this.el;
		}
		dom.innerHTML = "";
		this.getEl("close-" + this.id).onclick = null;	
		this.getEl("buttons-" + this.id).onclick = null;
		wcm.CrashBoard.remove(this.id);
		return false;
	}
});
var getStyle = function(){
	return window.getComputedStyle ? function(el, style){
		var cs = window.getComputedStyle(el, "");
		return cs ? cs[style] : null;
	} : function(el, style){
		return el.style[style] || el.currentStyle[style];
	}
}();
function drag(o, p){
	var id = o.id;
	var frm = document.getElementById('frm-' + id);
    p.onmousedown=function(a){
		if(frm) frm.style.visibility = 'hidden';
		var d=document;if(!a)a=window.event;
		var l=parseInt(getStyle(o,'left'),10),t=parseInt(getStyle(o,'top'),10);
		var x=a.pageX?a.pageX:a.clientX,y=a.pageY?a.pageY:a.clientY;
		if(p.setCapture)
			p.setCapture();
		else if(window.captureEvents)
			window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
		d.onmousemove=function(a){
			if(!a)a=window.event;
			if(!a.pageX)a.pageX=a.clientX;
			if(!a.pageY)a.pageY=a.clientY;
			var tx=a.pageX-x+l,ty=a.pageY-y+t;
			o.style.left=tx+"px";
			o.style.top=ty+"px";
		};
		d.onmouseup=function(){
			if(frm) frm.style.visibility = 'visible';
			if(p.releaseCapture)
			p.releaseCapture();
			else if(window.releaseEvents)
			window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			d.onmousemove=null;
			d.onmouseup=null;
		};
    };
}
function insertBeforeEnd(el, html){
	if(el.insertAdjacentHTML){
		el.insertAdjacentHTML('BeforeEnd', html);
		return el.lastChild;
	}
	var range = el.ownerDocument.createRange();
	var frag;
	if(el.lastChild){
		range.setStartAfter(el.lastChild);
		frag = range.createContextualFragment(html);
		el.appendChild(frag);
		return el.lastChild;
	}else{
		el.innerHTML = html;
		return el.lastChild;
	}
}