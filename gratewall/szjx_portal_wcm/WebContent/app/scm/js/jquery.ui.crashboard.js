/**
*CrashBoard
*/
(function(host){
var 
guid = 0,
cache = {},
isSecure = location.href.toLowerCase().indexOf("https") === 0,
blankUrl = isSecure ? "javascript:false" : '',
ua = navigator.userAgent.toLowerCase(),
isIE6 = ua.indexOf("opera") == -1 && ua.indexOf("msie 6") > -1,
isStrict = document.compatMode == "CSS1Compat",
btnTemplate = [
	'<div class="wcm-btn wcm-btn-left {2}" id="{0}" btn="{0}">',
		'<div class="wcm-btn-right">',
			'<div class="wcm-btn-center">',
				'<a class="wcm-btn-text"  href="#" id="{0}_a" onfocus="this.blur();">{1}</a>',
			'</div>',
		'</div>',
	'</div>'
].join(""),
btnTemplate1 = [
'<div class="wcm-btn wcm-btn-left1 {2}" style="border:1px solid #dedede;" id="{0}" btn="{0}">',
	'<div class="wcm-btn-right1">',
		'<div class="wcm-btn-center1">',
			'<a class="wcm-btn-text1" href="#" style="color:#666666" id="{0}_a" onfocus="this.blur();">{1}</a>',
		'</div>',
	'</div>',
'</div>'
].join(""),
linkBtnTemplate = [
	'<a class="wcm-btn-link" href="#" id="{0}_a" btn="{0}" onfocus="this.blur();">{1}</a>'
].join(""),
templatePart1 = [
	'<div class="wcm-cbd" id="{0}" style="visibility:hidden;" >',
		'<div class="header l" id="header-{0}"><div class="r"><div class="c">',
			'<div class="spt"></div>',
			'<div class="title" id="dialogTitle-{0}">{1}</div>',
			'<div class="tools" id="tools-{0}">',
				'<a class="close" href="#" id="close-{0}"></a>',
			'</div>',
		'</div></div></div>',
		'<div class="body l"><div id="line_hr"></div><div class="r"><div class="c">',
			'<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
				'<tr><td id="content-{0}">'
].join("");
templatePart2 = [
	'<iframe src="{2}" id="frm-{0}" style="height:100%;width:100%;"',
	' frameborder="0" onload="__CrashBoardContentLoaded__(\'{0}\', this);"></iframe>'
].join("");
templatePart3 = [
				'</td></tr>',
				'<tr style="display:{5}">',
					'<td class="buttons" id="buttons-{0}"></td>',
				'</tr>',
			'</table>',
		'</div></div></div>',
		'<div class="footer l"><div class="r"><div class="c"></div></div></div>',
	'</div>'
].join("");
function getDocEleSLeft(){
	if(isStrict){ 
		return getMyWindowTop().document.documentElement.scrollLeft == 0 ? getMyWindowTop().document.body.scrollLeft : getMyWindowTop().document.documentElement.scrollLeft; 
	}else {
		return getMyWindowTop().document.body.scrollLeft; 
	}
}
function getDocEleSTop(){
	if(isStrict){ 
		return getMyWindowTop().document.documentElement.scrollTop == 0 ? getMyWindowTop().document.body.scrollTop : getMyWindowTop().document.documentElement.scrollTop; 
	}else {
		return getMyWindowTop().document.body.scrollTop; 
	}
}

function $cb(cfg){
	cfg = apply({
		id : 'cb-' + (++guid),
		title : 'ekp窗口'
	}, cfg);
	apply(this, cfg);
	cache[cfg.id] = this;
}
$cb.prototype	= {
	getTemplate : function(){
		return templatePart1 + (this.isContentHTML() ? "" : templatePart2) + templatePart3;
	},
	getEl : function(id){
		return $(id || this.id);
	},
	getZindex : function(){
		return host(this.getEl()).css("zIndex");
	},
	setZindex : function(_nNewZindex){
		//解决从缓存中弹出的cb可能会显示在别的cb的下面
		host(this.getEl()).css({
			zIndex:_nNewZindex
		});
	},
	_showAnimate : function(){
		var t = this;
		var nCBZindex = getNextZindex();
		var cbEle = t.getEl();
		var docEle = isStrict ? getMyWindowTop().document.documentElement : getMyWindowTop().document.body;
		host(cbEle).find("iframe").hide();
		host(cbEle).show().css({
			visibility:"visible",
			opacity:0,
			zIndex: nCBZindex,//999 + guid
			width:0,
			height:"auto",
			left:(docEle.clientWidth)/2 + getDocEleSLeft(),
			top:(docEle.clientHeight)/2 + getDocEleSTop()
		}).animate({
			opacity:1,
			width:t.width || 500,
			left:(docEle.clientWidth - (t.width && t.width.match(/^\d+/) || 500))/2 + getDocEleSLeft(),
			top:(docEle.clientHeight-(t.height && t.height.match(/^\d+/) || 200)-100)/2 + getDocEleSTop()
		},function(){
			host(cbEle).find("iframe").show();
		});
		host('#content-' + t.id).css({
			height:0
		}).animate({
			height:t.height || 200
		});
	},
	_hideAnimate : function(){
		var docEle = isStrict ? getMyWindowTop().document.documentElement : getMyWindowTop().document.body,
			dom = host(this.getEl()),
			offset = dom.offset(),
			width = dom.width(),
			height = dom.height();
		host(this.getEl()).find(".body").animate({
			height:0,
			width:0
		});
		host(this.getEl()).animate({
			width:0,
			height:0,
			top:offset.top+height/2,
			left:offset.left + width/2
		},function(){
			host(this).parent().remove();
		});
	},
	notify : function(params){
		if(this.callback){
			this.callback(params);
		}
	},
	isContentHTML : function(){
		return (this.contentHTML && !this.src);
	},
	show : function(){
		var t = this;
		if(!t.rendered){
			var nCBZindex = getNextZindex();
			t.rendered = true;
			//append html
			var sHtml = format(t.getTemplate(), t.id, t.title, t.url);
			var div = getMyWindowTop().document.createElement('DIV');
			getMyWindowTop().document.body.appendChild(div);
			
			div.innerHTML = sHtml;
			var cbEle = t.getEl();
			if(!isIE6 && this.animate){
				this._showAnimate()
			}else{
				//set bounds
				if(t.width) cbEle.style.width = t.width;
				if(t.height) t.getEl('content-' + t.id).style.height = t.height;
				var docEle = isStrict ? getMyWindowTop().document.documentElement : getMyWindowTop().document.body;
				var left = (docEle.clientWidth - cbEle.offsetWidth) / 2 + getDocEleSLeft() + 'px';
				var top = (docEle.clientHeight - cbEle.offsetHeight) / 2 + getDocEleSTop() + 'px';
				cbEle.style.left = t.left || left;
				cbEle.style.top = t.top || top;
				cbEle.style.zIndex = nCBZindex;//999 + guid
				cbEle.style.visibility = 'visible';
				cbEle.style.display = '';
			}
			//set content html
			if(t.isContentHTML()){
				var contentTd = $("content-" + t.id);
				if(contentTd){
					contentTd.innerHTML = t.contentHTML;
					host(contentTd).css("border","1px #ddd solid");
				}
			}

			//bind events
			if(this.draggable !== false) drag(cbEle, t.getEl('header-' + t.id), this.maskable);
			t.getEl('close-' + t.id).onclick = function(){t._close.apply(t, arguments);return false;};
			t.getEl('buttons-' + t.id).onclick = function(){t.onClick.apply(t, arguments);return false;};
		}
		this.getEl().style.display = '';
		this.renderBtns(t.btns);
		this.showShield();
		try{
			host(document).keydown(function(e){
				_cb_keydown_(t.id,e);
			});
			$(t.id).focus();
		}catch(ex){};
	},
	renderBtns : function(btns){
		this.btns = btns = btns || [];
		var aHtml = [];
		for(var i = 0; i < btns.length; i++){
			var btn = btns[i]; btn.id = btn.id || ('btn-'+(++guid)); btns[btn.id] = btn;
			if(btn.link){
				aHtml.push(format(linkBtnTemplate, btn.id, btn.text));
				continue;
			}
			if (i==1){
				aHtml.push(format(btnTemplate1, btn.id, btn.text, btn.extraCls||""));
			}else{
				aHtml.push(format(btnTemplate, btn.id, btn.text, btn.extraCls||""));
			}
		}
		this.getEl('buttons-' + this.id).innerHTML = aHtml.join("");
	},
	hide : function(){
		if(!isIE6 && this.animate){
			this._hideAnimate();
		}else{
			this.getEl().style.display = 'none';
		}
		this.hideShield();
	},
	_close : function(params){
		if(this.closeCallback && !this.hasClosed){
			this.hasClosed = true;
			this.closeCallback(params);
		};
		//关闭对象
		this.doClose();

	},
	close : function(params){
		if(params==null || params.triggerClose != 1){
			this.doClose();
		}else{
			this._close(params);
		}
	},
	doClose : function(){
		try{
			var t = this;
			t.hide();
			var frm = $("frm-" + t.id);
			if(frm)frm.src = blankUrl;
			t.getEl("content-" + t.id).innerHTML = '';
			t.getEl("close-" + t.id).onclick = null;
			t.getEl("buttons-" + t.id).onclick = null;
			t.destroyShield();
			delete cache[t.id];
			if(isIE6 || !this.animate){
				dom = t.getEl();
				dom.parentNode.parentNode.removeChild(dom.parentNode);
				dom = null;
			}
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
			var btnElementA = $(btnId + "_a");
			if(!btnElementA || (btnElementA.currDisabled==1))return ;
			if(t.btns[btnId].cmd.call(t, t.btns[btnId]) !== false){
				t.close();
			}
		}
	},
	getButtonAByText : function(_btnText){
		var t = this;
		if(!_btnText || _btnText=="")return;
		if(!t.btns || t.btns.length==0)return;
		for(var i=0;i<t.btns.length;i++){
			var aBtn = t.btns[i];
			if(!aBtn || !aBtn.text)continue;
			if(aBtn.text!=_btnText) continue;
			var btnElementA = $(aBtn.id + "_a");
			if(!btnElementA) continue;
			return btnElementA;
		}
		return null;
	},
	disabledButton : function(_btnText){
		var btnElementA = this.getButtonAByText(_btnText);
		if(btnElementA==null)return;
		btnElementA.style.color = "#aca899";
		btnElementA.currDisabled=1;//1表示按钮被禁用
	},
	unDisabledButton : function(_btnText){
		var btnElementA = this.getButtonAByText(_btnText);
		if(btnElementA==null)return;
		btnElementA.style.color = "black";
		btnElementA.currDisabled=0;//0表示未被禁用
	},
	initShield : function(){
		if(!this.maskable && !isIE6) return;
		if($(this.id + '-shld')) return;
		var dom = getMyWindowTop().document.createElement('iframe');
		dom.src = blankUrl;
		dom.style.display = 'none';
		dom.style.border = 0;
		dom.frameBorder = 0;
		dom.className = 'wcm-panel-shield';
		dom.style.zIndex = this.getEl().style.zIndex - 1;
		dom.id = this.id + '-shld';
		getMyWindowTop().document.body.appendChild(dom);
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
		}else{
			//显示灰色遮布
			try{
				var win = $(this.id + '-shld').contentWindow;
				setTimeout(function(){
					win.document.body.bgColor = "#b3b3b3";
				},10);
			}catch(err){}
			oStyle.width = "100%";
			oStyle.height = (getMyWindowTop().window.parent.document.body.scrollHeight+50)+"px";
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
	},
	onResize : function(width,height){
		var t = this;
		var cbEle = t.getEl();
		var docEle = isStrict ? getMyWindowTop().document.documentElement : getMyWindowTop().document.body;
		
		if(width) cbEle.style.width = width;
		if(height) t.getEl('content-' + t.id).style.height = height;

		var left = (docEle.clientWidth - cbEle.offsetWidth) / 2 + getDocEleSLeft() + 'px';
		var top = (docEle.clientHeight - cbEle.offsetHeight) / 2 + getDocEleSTop() + 'px';
		cbEle.style.left = left || t.left;
		cbEle.style.top = top || t.top;
	}
};
function getMyWindowTop(){
	if(window.getActualTop){
		return window.getActualTop();
	}
	return window;
}
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
		//if(frm) frm.style.visibility = 'hidden';
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
			adjustPosition();

			//if(frm) frm.style.visibility = 'visible';
			if(p.releaseCapture)p.releaseCapture();
			else if(window.releaseEvents)window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			d.onmousemove=null;
			d.onmouseup=null;
		}
		
		var adjustPosition = function(){
			//判断组件是否超出了视线
			var newLeft = parseInt(getStyle(o,'left'),10);
			var newTop = parseInt(getStyle(o,'top'),10);
			if(newLeft>0 && newTop >0){
				return false;
			}
			if(newLeft<0){
				newLeft = "3";
			}
			if(newTop<0){
				newTop = "3";
			}
			o.style.left=newLeft+"px";
			o.style.top=newTop+"px";
			if(!maskable && sld){
				sld.style.left=(newLeft)+"px";
				sld.style.top=(newTop+1)+"px";
			}
		}
    }
}
__CrashBoardContentLoaded__ = function(id, frm){
	var cb = cache[id];
	if(!cb) return;
	try{
		var win = frm.contentWindow;
		if(win.m_cbCfg)
			cb.renderBtns((win.m_cbCfg || {}).btns);
		if(!win.g_init)return;
	}catch(err){
		return;
	}
	win.g_init(cb.params, cb);
}
_cb_keydown_=function(id,event){
	var event = event || window.event;
	var srcElement = event.target? event.target : event.srcElement;
	if(srcElement.tagName=="INPUT"){
		return ;
	}
	var nKeyCode = event.keyCode || event.which;
	if(event.keyCode==27){
		var cb = cache[id];
		if(!cb) return;
		cb.close();
	}
}
getNextZindex=function(){
	var nZIndex=_getMaxZIndex();
	if(nZIndex<=999){
		nZIndex = 999;
	}
	return nZIndex + 5;
}
_getMaxZIndex=function(){
	var nMaxZIndex=0;
	for(var aCB in cache){
		var nTempZIndex=cache[aCB].getZindex() || 0;
		nMaxZIndex = (nMaxZIndex>nTempZIndex)? nMaxZIndex : nTempZIndex;
	}
	return nMaxZIndex;
}
_adjustZIndex=function(_cbId){
	//在缓存中的话，需要重新设置下cb的zIndex，保证每次都是在最上方,zIndex至少是999起，因为CB定义就是这样
	var nMaxZindex = _getMaxZIndex();
	if(nMaxZindex<=999){
		alert("获取CrashBoard的最大的zindex属性失败");
		return;
	}
	var currCB = cache[_cbId];
	if(currCB.getZindex()>=nMaxZindex){
		return;
	}

	currCB.setZindex(nMaxZindex + 5);//比当前最大的zIndex多5
	cache[_cbId] = currCB;

	//设置遮布
	host("#"+_cbId+"-shld").css({
		zIndex:nMaxZindex+4//比当前最大zIndex多4
	});
}
//expose the global host context
host.CrashBoard = function(cfg){
	cfg = apply({appendParamsToUrl : true}, cfg);
	if(cache[cfg.id]){
		//_adjustZIndex(cfg.id);
		//return cache[cfg.id];
		var _currCache = cache[cfg.id];
		_currCache.doClose();
		delete _currCache;
		cache[cfg.id]=null;
	}
	if(cfg.url && cfg.appendParamsToUrl){
		var cjoin = cfg.url.indexOf('?')==-1 ? '?' : '&';
		cfg.url = cfg.url + cjoin + $toQueryStr(cfg.params);
	}
	//return cache[cfg.id] || (new $cb(cfg));
	return  (new $cb(cfg));
}
host.get=function(cfg){
	return cache[cfg.id];
}
})(jQuery);

//外围调用接口==================
var CrashBoard=$crashboard={
	show : function(_crashBoarderInfo){
		$.CrashBoard(_crashBoarderInfo).show();
	},
	close : function(id){
		$.CrashBoard({id:id}).close();
	},
	resetSize : function(id,width, height){
		$.CrashBoard({
			id:id
		}).onResize(width, height);
	},
	disabledButton : function(id,_btnText){
		$.CrashBoard({
			id:id
		}).disabledButton(_btnText);
	},
	unDisabledButton : function(id,_btnText){
		$.CrashBoard({
			id:id
		}).unDisabledButton(_btnText);
	},
	get : function(_cbId){
		return $.get({id:_cbId});
	}
}
