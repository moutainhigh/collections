var myPageContext = {
	init : function(){
		this.bpAdvSetting = new wcm.BubblePanel('dvAdvSetting', 'bubble-panel');
		this.bpTitleSetting = new wcm.BubblePanel('titleSystemMenu', 'bubble-panel');
	},
	toggleAdvance : function(ev, tg){
		this.bpAdvSetting.bubble(ev.pointer, null, function(p){
			this.style.right = '23px';
			this.style.top = '32px';
		});
	},
	serialcode : function(ev, tg){
		var oArgs = {
			XMLString    : $('frmAction').ObjectXML.value,
			HasDocSerial  : $("HasDocSerial").value || "0",
			SerialPattern : $("SerialPattern").value || "",
			SerialPeriod  : $("SerialPeriod").value || "-1",
			SerialField   : $("SerialField").value || ""
		};
		var cb = wcm.CrashBoard.get({
			id : 'SerialCodeDialog',
			title: wcm.LANG['INFOVIEW_DOC_51'] || '编号设置',
			url : 'infoview_serial_code.html',
			params : oArgs,
			callback : function(_args){
				$("HasDocSerial").value = _args.HasDocSerial || "0";
				$("SerialPattern").value = _args.SerialPattern || "";
				$("SerialPeriod").value = _args.SerialPeriod || "";
				$("SerialField").value = _args.SerialField || "";
			},
			width:'700px',
			height:'550px',
			appendParamsToUrl : false
		});
		cb.show();
	},
	titlesystemmenu : function(ev, tg){
		this.bpTitleSetting.bubble(ev.pointer, function(p){
			return [p[0] - 2, 32];
		});
	},
	titlesetting : function(ev, tg){
		appendTitlePattern(tg.getAttribute('_pattern', 2));
	},
	searchsetting : function(ev, tg){
		var cb = wcm.CrashBoard.get({
			id : 'SearchFieldsSetting',
			title: wcm.LANG['INFOVIEW_DOC_52'] || '检索字段设置',
			url : 'infoview_mycrashboard.html',
			params : {
				Jsp : 'infoview_advance_setting.jsp',
				InfoViewId : m_nInfoViewId,
				SettingMode : 2,
				_Window : window,
				appendParamsToUrl : false
			},
			width:'670px',
			height:'400px',
			btns : false
		});
		cb.show();
	},
	ordersetting : function(ev, tg){
		var cb = wcm.CrashBoard.get({
			id : 'OrderFieldSetting',
			title: '排序字段设置',
			url : 'infoview_mycrashboard.html',
			params : {
				Jsp : 'infoview_advance_setting.jsp',
				InfoViewId : m_nInfoViewId,
				SettingMode : 4,
				_Window : window,
				appendParamsToUrl : false
			},
			width:'320px',
			height:'400px',
			btns : false
		});
		cb.show();
	},
	viewsetting : function(ev, tg){
		var cb = wcm.CrashBoard.get({
			id : 'CustomViewSetting',
			title: wcm.LANG['INFOVIEW_DOC_53'] || '缺省视图设置',
			url : 'infoview_mycrashboard.html',
			params : {
				Jsp : 'infoview_advance_setting.jsp',
				InfoViewId : m_nInfoViewId,
				SettingMode : 1,
				_Window : window,
				appendParamsToUrl : false
			},
			width:'670px',
			height:'400px',
			btns : false
		});
		cb.show();
	},
	publishable : function(exEv, tg){
		this._saveView(exEv, tg, 'PublicFill', 'trs_item_public_fill');
	},
	isdefault : function(exEv, tg){
		var able = tg.getAttribute('_power', 2)=='1';
		if(able)return;
		this._saveView(exEv, tg, 'DefaultView', 'trs_item_default_view');
	},
	_saveView : function(exEv, tg, prop, attr){
		var able = tg.getAttribute('_power', 2)=='1';
		var trsTmpid = tg.getAttribute('_tgid', 2);
		prop = prop.toUpperCase();
		var flag = able ? 0 : 1;
		var defView = (prop=='DEFAULTVIEW' && !able) ? 1 : 0;
		var objXml = [
			"<WCMOBJ>", "<PROPERTIES>",
			"<IVVIEWID>", trsTmpid, "</IVVIEWID>",
			"<", prop, ">", flag, "</", prop, ">",
			"</PROPERTIES>", "</WCMOBJ>"
		].join('');
		new ajaxRequest({
			url : '../infoview/infoview_save_view.jsp',
			method : 'post',
			parameters : "IVViewId=" + trsTmpid + "&DefaultView=" + defView + "&ObjectXML=" + objXml,
			onSuccess : function(trans){
				var mv0 = wcm.MultiView.mv0();
				var tab = mv0.getNavTab(trsTmpid);
				tab.setAttribute(attr, flag);
				var sIds = trans.responseText;
				if(!sIds)return;
				var arr = sIds.split(',');
				for(var i=0;i<arr.length;i++){
					tab = mv0.getNavTab(arr[0]);
					if(!tab)continue;
					tab.setAttribute('trs_item_default_view', 0);
				}
			}
		});
	}
};
function appendTitlePattern(tp){
	var elTitle = $("TitlePattern");
	var stp;
	if(typeof tp == 'string')
		stp = tp;
	else{
		stp = tp.getAttribute('name');
		stp = '${' + stp + '}';
	}
	elTitle.value += stp;
}
function regMenuItemEvent(id){
	var lstMenuItem = null;
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
	myPageContext.init();
	function fireAction(exEv, tg){
		var actionItem = findItem(tg, false, '_action');
		if(!actionItem)return;
		var action = actionItem.getAttribute('_action', 2);
		var fn = myPageContext[action];
		if(!fn)return;
		fn.apply(myPageContext, [exEv, tg]);
	}
	Ext.get(document.body).on('click', fireAction);
	regMenuItemEvent('dvAdvSetting');
	regMenuItemEvent('titleSystemMenu');
	regMenuItemEvent('bubble-panel');
	doMultiView();

	window.pageInited = true;
	$('fields_tree').contentWindow.initPage();
});
function doMultiView(){
	function flyBtnFx(ev, el, undo){
		var fb = $('fly-button');
		if(undo){
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
		fb.style.display = '';
		fb.cfg = {
			id : eid,
			el : el
		};
	}
	function buildViewMenu(items, tg){
		var rst = [];
		var str = '<DIV class="menuitem" unselectable="on" _action="{1}" _tgid="{0}" _power="{4}">{2}<DIV class="{3}{1}"></DIV></DIV>';
		for(var i=0, n=items.length; i<n; i++){
			var t = items[i], p;
			if(t.action=='publishable')
				p = tg.getAttribute("trs_item_public_fill", 2) == "1";
			else if(t.action=='isdefault')
				p = tg.getAttribute("trs_item_default_view", 2) == "1";
			rst.push(String.format(str, tg.getAttribute('trs_temp_id', 2), t.action, t.name,
				p?'':'un', p?1:0));
		}
		return rst.join('');
	}
	var viewItems = [{
			action : 'publishable',
			name : wcm.LANG['INFOVIEW_DOC_19'] ||  '允许发布'
		}, {
			action : 'isdefault',
			name : wcm.LANG['INFOVIEW_DOC_20'] || '缺省显示页'
		}
	];
	var bubblePanel = new wcm.BubblePanel('bubble-panel');
	function showContextMenu(ev, tg){
		var fb = $('fly-button');
		if(!fb.cfg)return true;
		$('bubble-panel').innerHTML = buildViewMenu(viewItems, fb.cfg.el);
		bubblePanel.bubble(ev.pointer, function(p){
			return [p[0], p[1]-60];
		});
		ev.stop();
		return false;
	}
	Ext.get('fly-button').on('click', showContextMenu);
	var lastMsm = null;
	function mouseoverFx(ev, tg){
		var temp = findItem(tg, false, '_fxType');
		if(temp!=null)return;
		tg = findItem(tg, 'iv_ng');
		if(lastMsm!=null && lastMsm.el!=tg){
			flyBtnFx(null, lastMsm.el, true);
			lastMsm = null;
		}
		if(tg==null)return;
		flyBtnFx(ev, tg);
		lastMsm = {ev:ev.browserEvent, el:tg};
	}
	wcm.MultiView.doAfterMvRender = function(mv){
		var extTd = Ext.get(mv.getNavTd());
		Ext.get(document.body).on('mousemove', mouseoverFx);
		extTd.on('contextmenu', function(ev, tg){
			var el = findItem(tg, 'iv_ng');
			if(!el)return true;
			$('bubble-panel').innerHTML = buildViewMenu(viewItems, el);
			bubblePanel.bubble(ev.pointer, function(p){
				return [p[0], p[1]-60];
			});
			ev.stop();
			return false;
		});
	}
	wcm.MultiView.draw('infoview');
}
var FieldsTree = {
	appendtitle : function(_oInfo){
		appendTitlePattern('${' + _oInfo.name + '}');
	},
	editattr : function(_oInfo, _oElement){
		openEditAttrs(_oInfo.xpath);
	},
	public_fill : function(_oInfo, _oElement){
		openEditGroup(_oInfo.xpath);
	}
}
var m_FieldHelper = {
	treeWin : function(){
		var frm = $('fields_tree');
		if(frm && frm.contentWindow)return frm.contentWindow;
	},
	_GetEle : function(el, trsTmpId, bWin, getMultiEl){
		if(el && typeof el != 'string')return el;
		var cnt = $('infoview'), oWin;
		var ifrms = cnt.getElementsByTagName('IFRAME');
		for(var i=0,n=ifrms.length;i<n;i++){
			try{
				oWin = ifrms[i].contentWindow;
				el = getMultiEl ? oWin.document.getElementsByName(trsTmpId) : (oWin.$(el) || oWin.$(trsTmpId));
				if(el)return getMultiEl ? {el:el, win:oWin} : (!bWin ? el : {el:el, win:oWin});
			}catch(err){}
		}
		return null;
	},
	xPath2Name : function(xp){
		if(xp==null)return'';
		var axp = xp.split('/');
		for(var i=0,n=axp.length;i<n;i++){
			axp[i] = axp[i].replace(/^[^:]*?:/,'');
		}
		return axp.join('_');
	},
	isPublishable : function(el, trsTmpId){
		el = this._GetEle(el, trsTmpId);
		if(!el)return;
		return el.getAttribute("trs_obj_publish",2)!=0;
	},
	isWriteable : function(el, trsTmpId){
		el = this._GetEle(el, trsTmpId);
		return el.getAttribute("trs_readonly_field", 2)!=1 && el.getAttribute("trs_backreadonly_field", 2)!=1;
	},
	isBackWriteable : function(el, trsTmpId){
		el = this._GetEle(el, trsTmpId);
		return el.getAttribute("trs_backreadonly_field", 2)!=1;	
	},
	isFrontWriteable : function(el, trsTmpId){
		el = this._GetEle(el, trsTmpId);
		return el.getAttribute("trs_readonly_field", 2)!=1;	
	},
	setWriteable : function(trsTmpId, enable){
		new ajaxRequest({
			url : './infoview_field_set_readonly.jsp',
			parameters : "InfoViewId=" + m_nInfoViewId + "&IVFieldName="
						+ encodeURIComponent(this.xPath2Name(trsTmpId))
						+ "&ReadOnly=" + (enable?0:1),
			onSuccess : function(trans){
				m_FieldHelper.renderPower(trsTmpId, 'write', enable);
			}
		});
	},
	setPublishable : function(trsTmpId, enable){
		var el = this._GetEle(null, trsTmpId);
		var objXml = [
			"<WCMOBJ>", "<PROPERTIES>",
			"<PUBLICFILL>", enable?1:0, "</PUBLICFILL>",
			"</PROPERTIES>", "</WCMOBJ>"
		].join('');
		var gid = el.getAttribute("trs_obj_id", 2);
		new ajaxRequest({
			url : '../infoview/infoview_save_group.jsp',
			method : 'post',
			parameters : "IVGroupId=" + gid + "&ObjectXML=" + objXml,
			onSuccess : function(trans){
				m_FieldHelper.renderPower(trsTmpId, 'publish', enable);
			}
		});
	},
	renderPower : function(trsTmpId, type, enable){
		this.renderFrmPower(trsTmpId, type, enable);
		this.renderTreePower(trsTmpId, type, enable);
	},
	renderFrmAllPower : function(){
		var cnt = $('infoview'), oWin;
		var ifrms = cnt.getElementsByTagName('IFRAME');
		for(var i=0,n=ifrms.length;i<n;i++){
			try{
				oWin = ifrms[i].contentWindow;
				oWin.location.reload();
			}catch(err){}
		}
	},
	renderFrmPower : function(trsTmpId, type, enable){
		if(type=='frontwrite' || type=='backwrite' || type=='write'){
			var o = this._GetEle(null, trsTmpId, true, true);
			var el = o.el;
			for(index=0; index < el.length; index++){
				if(type=='write'){
					el[index].setAttribute("trs_readonly_field", enable?0:1);
					el[index].setAttribute("trs_backreadonly_field", enable?0:1);
				}else el[index].setAttribute((type=='frontwrite' ?  "trs_readonly_field" : "trs_backreadonly_field"), enable?0:1);
				o.win._TransRule_.initPower(el[index]);
			}
		}
		else if(type=='publish'){
			var o = this._GetEle(null, trsTmpId, true);
			if(o == null) return;
			var el = o.el;
			el.setAttribute("trs_obj_publish", enable?1:0);
			o.win._TransRule_.initPower(el);
		}
	},
	renderTreePower : function(trsTmpId, type, enable){
		var oTreeWin = this.treeWin();
		if(!oTreeWin || !oTreeWin.PowerHelper)return;
		oTreeWin.PowerHelper.renderPower(trsTmpId, 0, type, enable);
	},
	setFieldNillable : function(trsTmpId, _bNillable){
		var o = this._GetEle(null, trsTmpId, true);
		var el = o.el;
		el.setAttribute('not_null', _bNillable ? 0 : 1);
		var oStyle = {};
		if(el.tagName !='SELECT'){
			oStyle = {marginLeft:'-15px'};
		}
		o.win._TransRule_.NotifyMustFill(el, oStyle);
	},
	setFieldDefault : function(trsTmpId, _sDefaultValue){
		var o = this._GetEle(null, trsTmpId, true);
		var el = o.el;
		el.setAttribute("default_value", _sDefaultValue);
		o.win._TransRule_.initDefaultValue(el);
	},
	PublicFillByView2 : function(trsTmpId, _bReadable){
		var o = this._GetEle(null, trsTmpId, true);
		var el = o.el;
		this.renderTreePower(trsTmpId, 'publish', _bReadable);
		this.renderFrmPower(trsTmpId,  'publish', _bReadable);
	},
	dblclick : function(field, el, _bGroup){
		if(!_bGroup){
			return openEditAttrs(field);
		}
		return openEditGroup(field);
	},
	focus : function(field, el){
		var oTreeWin = this.treeWin();
		if(!oTreeWin || !oTreeWin.XMLTree)return;
		var oFunc = oTreeWin.XMLTree['focusNode'];
		if(!oFunc)return;
		(oFunc)(field);
		return false;
	},
	click : function(field, el){
		return this.focus(field, el);
	}
};
function openEditAttrs(xpt){
	if(xpt==null){
		alert(wcm.LANG['INFOVIEW_DOC_21'] || '字段名称为空!');
		return;
	}
	var arrXpt = xpt.split('/');
	for(var i=0,n=arrXpt.length;i<n;i++){
		arrXpt[i] = arrXpt[i].replace(/^[^:]*?:/,'');
	}
	var cb = wcm.CrashBoard.get({
		id : 'InfoViewFieldAttrs',
		title: wcm.LANG['INFOVIEW_DOC_22'] || '属性设置',
		url : 'infoview_mycrashboard.html',
		params : {
			InfoViewId : m_nInfoViewId,
			IVFieldName : arrXpt.join('_'),
			_Window : window,
			XPath : xpt,
			appendParamsToUrl : false
			
		},
		callback : function(_args){
			m_FieldHelper.renderTreePower(_args['XPath'], 'write', (_args.FrontReadOnly || _args.BackReadOnly));
			m_FieldHelper.renderFrmPower(_args['XPath'], 'frontwrite', _args.FrontReadOnly);
			m_FieldHelper.renderFrmPower(_args['XPath'], 'backwrite', _args.BackReadOnly);
			m_FieldHelper.setFieldNillable(_args['XPath'], _args.Nillable);
			m_FieldHelper.setFieldDefault(_args['XPath'],  _args.Default);
		},
		width:'505px',
		height:'580px',
		btns : false
	});
	cb.show();
}
function openEditGroup(xpt){
	if(xpt==null){
		alert(wcm.LANG['INFOVIEW_DOC_21'] || '字段名称为空!');
		return;
	}
	var o = m_FieldHelper._GetEle(null, xpt, true);
	if(o == null) return;
	var arrXpt = xpt.split('/');
	for(var i=0,n=arrXpt.length;i<n;i++){
		arrXpt[i] = arrXpt[i].replace(/^[^:]*?:/,'');
	}
	var cb = wcm.CrashBoard.get({
		id : 'InfoViewPublishFill',
		title: wcm.LANG['INFOVIEW_DOC_25'] || '发布设置',
		url : 'infoview_mycrashboard.html',
		params : {
			Jsp : 'infoview_publicfill_setting.jsp',
			InfoViewId : m_nInfoViewId,
			IVGroupName : arrXpt.join('_'),
			_Window : window,
			XPath : xpt,
			appendParamsToUrl : false
		},
		callback : function(_args){
			 m_FieldHelper.PublicFillByView2(_args['XPath'], _args['PublicFillable']);
		},
		width:'500px',
		height:'280px',
		btns : false
	});
	cb.show();
}