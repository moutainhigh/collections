var PgC = {
	names : {
		1 : 'input', 2 : 'textarea', 3 : 'repeatsection', 4 : 'repeattable',
		5 : 'fileattachment', 6 : 'trsobject', 7 : 'richtext', 8 : 'select',
		9 : 'checkbox', 10 : 'optionbutton', 11 : 'dtpicker_dttext', 12 : 'viewnavtab',
		13 : 'section', 101 : 'expression'
	}
};
var m_sRootPath = './infoview/'
Object.extend(PgC, {
	flys : (function(){
		var rst = {};
		var basic = [{
				action : 'appendtitle',
				name : wcm.LANG['INFOVIEW_DOC_26'] || '添加到标题'
			}, {
				action : 'writeable',
				name : wcm.LANG['INFOVIEW_DOC_27'] ||  '允许编辑'
			}, {
				action : 'fieldsetting',
				name : wcm.LANG['INFOVIEW_DOC_22'] || '属性设置'
			}

		];
		for(var type in PgC.names){
			rst[type] = basic;
		}
		rst['4'] = [{
				action : 'writeable',
				name : wcm.LANG['INFOVIEW_DOC_27'] ||  '允许编辑'
			}, {
				action : 'fieldsetting',
				name : wcm.LANG['INFOVIEW_DOC_22'] || '属性设置'
			}

		]
		rst['12'] = rst['13'] = rst['3'] = [{
				action : 'publishable',
				name : wcm.LANG['INFOVIEW_DOC_19'] || '允许发布'
			}, {
				action : 'fieldsetting',
				name : wcm.LANG['INFOVIEW_DOC_25'] || '发布设置'
			}
		]
		return rst;
	})(),
	flyItem : '<DIV class="menuitem" unselectable="on" _action="{1}" _tgid="{0}" _power="{4}">{2}<DIV class="{3}" unselectable="on"></DIV></DIV>',
	buildMenu : function(items, tg){
		var rst = [];
		for(var i=0, n=items.length; i<n; i++){
			var t = items[i];
			rst.push(String.format(PgC.flyItem, tg.id, t.action, t.name,
				this.getCls(t.action, tg), this.getPower(t.action, tg)));
		}
		return rst.join('');
	},
	publishable : function(exEv, tg){
		var able = tg.getAttribute('_power', 2)=='1';
		var trsTmpid = tg.getAttribute('_tgid', 2);
		parent.m_FieldHelper.setPublishable(trsTmpid, !able);
	},
	writeable : function(exEv, tg){
		var able = tg.getAttribute('_power', 2)=='1';
		var trsTmpid = tg.getAttribute('_tgid', 2);
		parent.m_FieldHelper.setWriteable(trsTmpid, !able);
	},
	fieldsetting : function(exEv, tg){
		var trsTmpid = tg.getAttribute('_tgid', 2);
		var el = $(trsTmpid)
		parent.m_FieldHelper.dblclick(trsTmpid, tg, PgC.isBlockEle(el));
	},
	getCls : function(action, tg){
		if(this.getPower(action, tg)==1)return action;
		return 'un' + action;
	},
	getPower : function(action, tg){
		if(action=='publishable'){
			return parent.m_FieldHelper.isPublishable(tg)?1:0;
		}
		if(action=='writeable'){
			return parent.m_FieldHelper.isWriteable(tg)?1:0;
		}
		return 0;
	},
	appendtitle : function(action, tg){
		var trsTmpid = tg.getAttribute('_tgid', 2);
		var el = $(trsTmpid); 
		parent.appendTitlePattern(el);
	},
	borderFx : function(el, undo){
		var nElType = el.getAttribute('element-type', 2);
		if(nElType==11){
			el = el.parentNode;
		}
		Element[undo?'removeClassName':'addClassName'](el, 'xct-hover');
		el._borderColor = undo ? '' : el.style.borderColor;
		el.style.borderColor = undo ? el._borderColor : '#FF6600';
	},
	flyBtnFx : function(ev, el, undo){
		var fb = $('fly-button');
		if(undo){
			ShieldMgr.hideShield(fb);
			fb.style.display = 'none';
			fb.cfg = '';
			return;
		}
		var eid = el.getAttribute('trs_temp_id', 2);
		if(fb.cfg && fb.cfg.id == eid 
			&& fb.style.display!='none')return;
		var p = ev.pointer;
		Position.clone(el, fb, {
			setWidth:   false,
			setHeight:  false,
			offsetTop:  2,
			offsetLeft: 2
		});
		ShieldMgr.showShield(fb);
		var nElType = el.getAttribute('element-type', 2);
		fb.style.display = '';
		fb.cfg = {
			id : eid,
			elType : nElType,
			el : el
		};
	},
	isBlockEle : function(el){
		var nElType = el.getAttribute('element-type', 2);
		return nElType==3 || (nElType>=12 && nElType<100);
	}
});
function regMenuItemEvent(id){
	var lstMenuItem = null;
	Ext.get(id).on('click', function(exEv, tg){
		var actionItem = findItem(tg, false, '_action');
		if(!actionItem)return;
		var action = actionItem.getAttribute('_action', 2);
		var fn = PgC[action];
		if(!fn)return;
		fn.apply(PgC, [exEv, actionItem]);
	});
	Ext.get(id).on('mousemove', function(exEv, tg){
		var menuitem = findItem(tg, 'menuitem');
		if(!menuitem)return;
		if(lstMenuItem){
			Element.removeClassName(lstMenuItem, 'menuitem_active');
		}
		Element.addClassName(menuitem, 'menuitem_active');
		lstMenuItem = menuitem;
	});
	Ext.get(id).on('mouseout', function(exEv, tg){
		if(lstMenuItem){
			Element.removeClassName(lstMenuItem, 'menuitem_active');
		}
		lstMenuItem = null;
	});
}
Event.observe(window, 'load', function(){
	var eles = document.getElementsByTagName("*");
	for(var i=0; i<eles.length; i++) {
		var el = eles[i];
		_TransRule_.doTrans(el, el.getAttribute('trs_readonly_field', 2)=='1');
	}
	//bubble-panel
	var divBp = document.createElement('DIV');
	divBp.id = 'bubble-panel';
	divBp.setAttribute('_fxType', 'bubble-panel');
	divBp.style.display = 'none';
	divBp.style.position = "absolute";
	divBp.style.zIndex = 999;
	document.body.appendChild(divBp);
	regMenuItemEvent('bubble-panel');
	//fly-button
	var flyBtn = document.createElement('DIV');
	flyBtn.style.display = 'none';
	flyBtn.id = 'fly-button';
	flyBtn.setAttribute('_fxType', 'fly-button');
	flyBtn.style.zIndex = 99;
	flyBtn.style.cursor = 'pointer';
	flyBtn.style.position = "absolute";
	flyBtn.style.border = 0;
	flyBtn.setAttribute("xd:xctname", "FlyButton");
	flyBtn.innerHTML = "<img src='" + m_sRootPath + "DownButton.gif' border='0'>";
	document.body.appendChild(flyBtn);
	var bubblePanel = new wcm.BubblePanel('bubble-panel');
	function showContextMenu(ev, tg){
		var fb = $('fly-button');
		if(!fb.cfg)return true;
		var items = PgC.flys[fb.cfg.elType];
		if(!items)return true;
		$('bubble-panel').innerHTML = PgC.buildMenu(items, fb.cfg.el);
		bubblePanel.bubble(ev.pointer, function(p){
			return [p[0], p[1]+5];
		});
		ev.stop();
		return false;
	}
	Ext.get(flyBtn).on('click', showContextMenu);
	var lastMsm = null;
	function mouseoverFx(ev, tg){
		var temp = findItem(tg, false, '_fxType');
		if(temp!=null)return;
		tg = findItem(tg, false, 'element-type');
		if(lastMsm!=null && lastMsm.el!=tg){
			PgC.borderFx(lastMsm.el, true);
			PgC.flyBtnFx(null, lastMsm.el, true);
			lastMsm = null;
		}
		if(tg==null)return;
		var nElType = tg.getAttribute('element-type', 2);
		var sElName = PgC.names[nElType];
		if(!sElName)return;
		PgC.borderFx(tg);
		PgC.flyBtnFx(ev, tg);
		lastMsm = {ev:ev.browserEvent, el:tg};
		if(!PgC[sElName])return;
		PgC[sElName](ev.browserEvent, tg);
		return true;
	}
	var extBody = Ext.get(document.body);
	extBody.on('contextmenu', function(ev, tg){
		var el = findItem(tg, false, 'element-type');
		if(!el)return true;
		mouseoverFx(ev, tg);
		var eid = el.getAttribute('trs_temp_id', 2);
		var nElType = el.getAttribute('element-type', 2);
		var items = PgC.flys[nElType];
		if(!items)return true;
		$('bubble-panel').innerHTML = PgC.buildMenu(items, el);
		bubblePanel.bubble(ev.pointer, function(p){
			return [p[0], p[1]+5];
		});
		ev.stop();
		return false;
	});
	extBody.on('mousemove', function(ev, tg){
		if(Element.visible('bubble-panel'))return;
		mouseoverFx(ev, tg);
	});
	extBody.on('dblclick', function(ev, tg){
		tg = findItem(tg, false, 'element-type');
		if(tg==null)return;
		var nElType = tg.getAttribute('element-type', 2);
		var nElId = tg.getAttribute('trs_temp_id', 2);
		parent.m_FieldHelper.dblclick(nElId, tg, PgC.isBlockEle(tg));
	});
	extBody.on('click', function(ev, tg){
		tg = findItem(tg, false, 'element-type');
		if(tg==null)return;
		var nElId = tg.getAttribute('trs_temp_id', 2);
		parent.m_FieldHelper.click(nElId, tg);
	});
	extBody.on('focus', function(ev, tg){
		tg = findItem(tg, false, 'element-type');
		if(tg==null)return;
		var nElId = tg.getAttribute('trs_temp_id', 2);
		parent.m_FieldHelper.focus(nElId, tg);
	});
});
var _TransRule_ = {
	xct : 'xd:xctname',
	Rules : {},
	save : function(tag, xct, m) {
		tag = tag.toLowerCase();
		var l = this.Rules[tag] || {};
		this.Rules[tag] = l;
		l[xct.toLowerCase()] = m;
	},
	doTrans : function(el, args) {
		var tag = el.tagName;
		if(!tag)return false;
		var xct = el.getAttribute(this.xct, 2);
		if(!xct)return false;
		xct = xct.toLowerCase();
		var l = this.Rules[tag.toLowerCase()];
		if(!l || !l[xct])return;
		el = l[xct](el, args) || el;
		if(!el.id)el.id = el.getAttribute('trs_temp_id', 2);
		this.initPower(el);
		this.initDefaultValue(el);
	},
	initPower : function(nd){
		var block = PgC.isBlockEle(nd), backable, frontable, pubable;
		if(block){
			pubable = parent.m_FieldHelper.isPublishable(nd);
			return Element[pubable?'removeClassName':'addClassName'](nd, 'unpublish');
		}
		frontable = parent.m_FieldHelper.isFrontWriteable(nd);
		backable = parent.m_FieldHelper.isBackWriteable(nd);
		if(nd.getAttribute('xd:xctname', 2) == 'FileAttachment' || nd.getAttribute('xd:xctname', 2) == 'InlineImage'){
			if(!frontable && !backable)nd.style.backgroundColor = '#DFDFDF';
			else if(!frontable)nd.style.backgroundColor = '#ADD69B';
			else if(!backable)nd.style.backgroundColor = '#D2A499';
			else nd.style.backgroundColor = '';
			return;
		}
		if(!backable && !frontable){
			Element['addClassName'](nd, 'unwrite'); 
			Element['removeClassName'](nd, 'unfrontwrite'); 
			Element['removeClassName'](nd, 'unbackwrite');
			return;
		}else{
			Element[frontable?'removeClassName':'addClassName'](nd, 'unfrontwrite');
			Element[backable?'removeClassName':'addClassName'](nd, 'unbackwrite');
			Element['removeClassName'](nd, 'unwrite');
		}
	},
	initDefaultValue : function(nd){
		var nElType = nd.getAttribute('element-type', 2);
		var v = nd.getAttribute('default_value', 2) || '';
		if(nElType==1 || nElType==2 || nElType==7 || nElType==11){
			nd.innerHTML = v;
		}
		else if(nElType==8){
			nd.value = v;
		}
		else if(nElType==10){
			if(nd.getAttribute("xd:onValue")==v)nd.checked = true;
		}
		else if(nElType==9){
			var sv = ',' + nd.getAttribute("xd:onValue") + ',';
			if((','+v+',').indexOf(sv)!=-1)nd.checked = true;
		}
	},
	mustDoCaches : [],
	pushToMustDoCaches : function(item){
		this.mustDoCaches.push(item);
	},
	removeMustDoCaches : function(){
		this.mustDoCaches = [];
	},
	getMustDoCaches : function(){
		return this.mustDoCaches;
	},
	_MustDoSpan : function(){
		var sp = document.createElement("SPAN");
		sp.className = 'must_fill';
		sp.innerHTML = '*';
		return sp;
	},
	NotifyMustFill : function(nd, os){
		var spid = nd.getAttribute('trs_obj_id', 2) + "_mustfill";
		if(nd.getAttribute("not_null", 2) == "1"){
			if($(spid)){
				Element.show(spid);
				return;
			}
			var sp = this._MustDoSpan();
			this.pushToMustDoCaches([nd, sp]);
			sp.id = spid;
			document.body.appendChild(sp);
			Position.clone(nd, sp, {
				setWidth : false,
				setHeight : false,
				offsetLeft : nd.offsetWidth,
				offsetTop : 5
			});
			for(var n in os){
				sp.style[n] = os[n];
			}
		}
		else if($(spid)){
			Element.hide(spid);
		}
	}
};
_TransRule_.save("span", "PlainText", function(nd) {
	var nHeight = parseInt(nd.style.height, 10);
	if(isNaN(nHeight))nHeight = 0;
	var isTextArea = nHeight > 30;
	nd.setAttribute("element-type", isTextArea?2:1);
	if(!nd.getAttribute("default_value", 2)){
		nd.setAttribute("default_value", nd.innerHTML);
	}
	if(isTextArea)
		 nd.className = nd.className + ' ' + 'xdAreaBox'; 
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px'});
});
_TransRule_.save("span", "RichText", function(nd) {
	nd.style.overflow = "auto";
	nd.setAttribute("element-type", "7");
	if(!nd.getAttribute("default_value", 2)){
		nd.setAttribute("default_value", nd.innerHTML);
	}
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px'});
});
_TransRule_.save("select", "DropDown", function(nd) {
	nd.setAttribute("element-type", "8");
	_TransRule_.NotifyMustFill(nd);
});
_TransRule_.save("select", "ListBox", function(nd) {
	nd.setAttribute("element-type", "8");
	_TransRule_.NotifyMustFill(nd);
});
_TransRule_.save("input", "CheckBox", function(nd) {
	nd.setAttribute("element-type", "9");
});
_TransRule_.save("input", "OptionButton", function(nd) {
	nd.setAttribute("element-type", "10");
});
_TransRule_.save("div", "Section", function(nd) {
	nd.setAttribute("element-type", "13");
	nd.style.border = '1px dotted gray';
});
_TransRule_.save("div", "RepeatingSection", function(nd) {
	nd.setAttribute("element-type", "3");
	nd.style.border = '1px dotted gray';
});
_TransRule_.save("tbody", "RepeatingTable", function(nd) {
	nd.setAttribute("element-type", "4");
});
_TransRule_.save("span", "ListItem_Plain", function(nd) {
	nd.setAttribute("element-type", "1");
});
_TransRule_.save("span", "DTPicker_DTText", function(nd) {
	nd.setAttribute("element-type", "11");
	//nd.style.width = '50px';
	//nd.style.height = '25px';
	//nd.style.display = 'inline-block';
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px'});
});
_TransRule_.save("span", "FileAttachment", function(nd) {
	nd.setAttribute("element-type", "5");
	nd.style.padding = '4px';
	nd.style.cursor = 'pointer';
	var img = document.createElement("img");
	img.src = m_sRootPath + "FileAttachment.gif";
	img.style.width = "14px";
	img.style.height = "13px";
	img.align = 'absmiddle';
	nd.appendChild(img);
	var txt = document.createElement("span");
	txt.setAttribute("text_body", "1");
	txt.style.position = "relative";
	txt.style.paddingLeft = '1px';
	txt.innerHTML = wcm.LANG['INFOVIEW_DOC_54'] || "点击此处以添加附件";
	nd.appendChild(txt);
	_TransRule_.NotifyMustFill(nd, {marginLeft:'-15px', marginTop:'7px'});
});
function copyAttrs(src, tg){
	var attrs = src.attributes;
	for(var i=0; i<attrs.length; i++) {
		if(!attrs[i].specified)continue;
		if(attrs[i].name == "style"){
			tg.style.cssText = src.style.cssText;
			continue;
		}
		tg.setAttribute(attrs[i].name, attrs[i].value);
	}
}
var GetImageAttachmentRule = function(_sXctNameFlag){
	return function(nd) {
		var sp = document.createElement('SPAN');
		sp.setAttribute("element-type", "5");
		copyAttrs(nd, sp);
		sp.style.cursor = "pointer";
		nd.parentNode.insertBefore(sp, nd.nextSibling);
		var w = nd.offsetWidth, h = nd.offsetHeight;
		nd.parentNode.removeChild(nd);
		var img = document.createElement('IMG');
		img.src = m_sRootPath + 'spacer.gif';
		img.style.width = w + 'px';
		img.style.height = h + 'px';
		img.style.background = "url(" + m_sRootPath + "ImageAttachment.gif) no-repeat center center";
		sp.appendChild(img);
		var txt = document.createElement("span");
		txt.setAttribute("text_body", "1");
		txt.innerHTML = '&nbsp;' + wcm.LANG['INFOVIEW_DOC_93'] || '点击此处以添加图片';
		txt.style.marginLeft = (-w)  + 'px';
		txt.style.marginTop = '-22px';
		txt.style.height = '16px';
		txt.style.lineHeight = '16px';
		sp.appendChild(txt);
		return sp;
	};
}
_TransRule_.save("img", "InlineImage", GetImageAttachmentRule('trs_is_inline_image'));
_TransRule_.save("img", "LinkedImage", GetImageAttachmentRule('trs_is_linked_image'));
_TransRule_.save("span", "ExpressionBox", function(nd) {
	nd.setAttribute("element-type", "101");
});
Event.observe(window, 'resize', function(){
	var caches = _TransRule_.getMustDoCaches();
	for(var i=0, n=caches.length; i<n; i=i+1){
		Position.clone(caches[i][0], caches[i][1], {
			setWidth : false,
			setHeight : false,
			offsetLeft : caches[i][0].offsetWidth,
			offsetTop : 5
		});
	}
});
Event.observe(window, 'unload', function(){
	_TransRule_.removeMustDoCaches();
});

var ShieldMgr = {
	initShield : function(el){
		if(!Ext.isIE6) return;
		if($(this.id + '-shld')) return;
		var dom = document.createElement('iframe');
		dom.src = Ext.blankUrl;
		dom.style.display = 'none';
		dom.style.position = 'absolute';
		dom.style.zIndex = el.style.zIndex - 1;
		dom.id = this.id + '-shld';
		document.body.appendChild(dom);
	},
	showShield : function(el){
		if(!Ext.isIE6) return;
		this.initShield(el);
		var dom = el;
		var oStyle = $(this.id + '-shld').style;
		oStyle.left = (parseInt(dom.style.left, 10) + 1)+"px";
		oStyle.top = (parseInt(dom.style.top, 10) + 1)+"px";
		oStyle.width = "14px";
		oStyle.height = "12px";
		oStyle.display = '';
	},
	hideShield : function(){
		if(!Ext.isIE6) return;
		$(this.id + '-shld').style.display = 'none';
	},
	destroyShield : function(){
		if(!Ext.isIE6) return;
		var dom = $(this.id + '-shld');
		if(!dom) return;
		dom.parentNode.removeChild(dom);
	}
}

