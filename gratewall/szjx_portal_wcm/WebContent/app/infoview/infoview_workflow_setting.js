var m_oWorkFlowSetting = {};
var m_oWorkFlowConfig = null;
var myPageContext = {
	init : function(){
		try{
			var oDialogInfo = window.top.getDialogArguments() || {};
			m_oWorkFlowConfig = oDialogInfo['config'] || {};
			m_oWorkFlowSetting = WorkFlowHelper.prepare(oDialogInfo['data']);
		}catch(err){
			m_oWorkFlowConfig = {};
			m_oWorkFlowSetting = {};
		}
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
	var stp = tp.name;
	if(stp != null){
		stp = '${' + stp + '}';
	}else{
		stp = tp;
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
	function fireAction(exEv, tg){
		var actionItem = findItem(tg, false, '_action');
		if(!actionItem)return;
		var action = actionItem.getAttribute('_action', 2);
		var fn = myPageContext[action];
		if(!fn)return;
		fn.apply(myPageContext, [exEv, tg]);
	}
	myPageContext.init();
	Ext.get(document.body).on('click', fireAction);
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
		var str = '<DIV class="menuitem" unselectable="on" _action="{1}" _tgid="{0}" _power="{4}">{2}<DIV class="{3}{1}" unselectable="on"></DIV></DIV>';
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
			name : wcm.LANG['INFOVIEW_DOC_19'] || '允许发布'
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
	workflow : function(_oInfo, _oElement){
		openEditAttrs(_oInfo.xpath);
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
		return this.isReadable(el, trsTmpId);
	},
	isWriteable : function(el, trsTmpId){
		trsTmpId = trsTmpId || el.id;
		var mySet = m_oWorkFlowSetting[trsTmpId];
		return !mySet || mySet['writefield'] != 0;
	},
	isReadable : function(el, trsTmpId){
		trsTmpId = trsTmpId || el.id;
		var mySet = m_oWorkFlowSetting[trsTmpId];
		return !mySet || mySet['readfield'] != 0;
	},
	setWriteable : function(trsTmpId, enable){
		var mySet = m_oWorkFlowSetting[trsTmpId];
		if(!mySet){
			m_oWorkFlowSetting[trsTmpId] = mySet = {};
		}
		mySet['writefield'] = enable ? 1 : 0;
		mySet['fieldtype'] = 0;
		WorkFlowHelper._EffectSetting(trsTmpId, mySet);
	},
	setPublishable : function(trsTmpId, enable){
		var mySet = m_oWorkFlowSetting[trsTmpId];
		if(!mySet){
			m_oWorkFlowSetting[trsTmpId] = mySet = {};
		}
		mySet['readfield'] = enable ? 1 : 0;
		mySet['fieldtype'] = 1;
		WorkFlowHelper._EffectSetting(trsTmpId, mySet);
	},
	setReadable : function(trsTmpId, enable){
		var mySet = m_oWorkFlowSetting[trsTmpId];
		if(!mySet){
			m_oWorkFlowSetting[trsTmpId] = mySet = {};
		}
		mySet['readfield'] = enable ? 1 : 0;
		mySet['fieldtype'] = 0;
		WorkFlowHelper._EffectSetting(trsTmpId, mySet);
	},
	renderPower : function(trsTmpId, type, enable){
		this.renderFrmPower.apply(this, arguments);
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
	renderFrmPower : function(trsTmpId, type, enable, info){
		var o = this._GetEle(null, trsTmpId, true, true);
		var els = o.el;
		for(index=0; index<els.length;index++){
			var el  = els[index];
			o.win._TransRule_.flowSetting(el, Object.extend({
				type : type,
				power : enable
			}, info));
		}
	},
	renderTreePower : function(trsTmpId, type, enable){
		var oTreeWin = this.treeWin();
		if(!oTreeWin || !oTreeWin.PowerHelper)return;
		oTreeWin.PowerHelper.renderPower(trsTmpId, 0, type, enable);
	},
	PublicFillByView2 : function(trsTmpId, _bReadable){
		var o = this._GetEle(null, trsTmpId, true);
		var el = o.el;
		var type = el.getAttribute("element-type");
		el.setAttribute("trs_obj_publish", _bReadable?1:0);
		el.style.backgroundColor = !_bReadable?'#FFECEC':'';
		this.renderTreePower(trsTmpId, type, _bReadable);
	},
	dblclick : function(field, el, _bGroup){
		if(!_bGroup){
			return openEditAttrs(field, 0);
		}
		return openEditAttrs(field, 1);
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
function openEditAttrs(xpt, fieldType){
	if(xpt==null){
		alert(wcm.LANG['INFOVIEW_DOC_21'] || '字段名称为空!');
		return;
	}
	var arrXpt = xpt.split('/');
	for(var i=0,n=arrXpt.length;i<n;i++){
		arrXpt[i] = arrXpt[i].replace(/^[^:]*?:/,'');
	}
	var o = m_FieldHelper._GetEle(null, xpt, true);
	var el = o.el;
	var sDefault = null;
	var _nFieldType = el.getAttribute('element-type');
	var _sDataType = el.getAttribute('data_type');
	if(_nFieldType == 1 || _nFieldType == 8){
		sDefault = el.value;
	}
	var params = {
		XPath : xpt,
		_Window : window,
		IVFieldName : arrXpt.join('_'),
		CurrInfo : m_oWorkFlowSetting[xpt],
		Config : m_oWorkFlowConfig,
		FieldType : fieldType,
		InitValue : sDefault,
		DataType : _sDataType
	};
	var cb = wcm.CrashBoard.get({
		id : 'WorkFlowField',
		title: wcm.LANG['INFOVIEW_DOC_22'] || '属性设置',
		url : 'infoview_workflow_setting.html',
		params : params,
		callback : function(_args){
			var sXPath = _args.XPath;
			WorkFlowHelper.advanceSetting(sXPath, _args);
			WorkFlowHelper._EffectSetting(sXPath, _args);
		},
		width:'500px',
		height:'280px',
		btns : false
	});
	cb.show();
}
var WorkFlowHelper = {
	prepare : function(_oSettingData){
		if(_oSettingData==null)return {};
		var oResult = {};
		for(var sXpath in _oSettingData){
			oResult[sXpath] = _oSettingData[sXpath];
		}
		return oResult;
	},
	isReadable : function(_sXPath){
		if(_sXPath==null)return false;
		
		var oInfo = m_oWorkFlowSetting[_sXPath];
		if(!oInfo)return true;
		return oInfo['readfield'] != 0;
	},
	isWriteable : function(_sXPath){
		if(_sXPath==null)return false;
		var oInfo = m_oWorkFlowSetting[_sXPath];
		if(!oInfo)return true;
		return oInfo['writefield'] != 0;
	},
	toggleReadable : function(_sXPath, _nFieldType){
		if(_sXPath==null)return false;
		
		var oInfo = m_oWorkFlowSetting[_sXPath];
		if(!oInfo){
			oInfo = m_oWorkFlowSetting[_sXPath] = {};
		}
		oInfo['readfield'] = (oInfo['readfield']==0)?1:0;
		oInfo['fieldtype'] = _nFieldType || 0;
		return true;
	},
	toggleWriteable : function(_sXPath, _nFieldType){
		if(_sXPath==null)return false;
		
		var oInfo = m_oWorkFlowSetting[_sXPath];
		if(!oInfo){
			oInfo = m_oWorkFlowSetting[_sXPath] = {};
		}
		oInfo['writefield'] = (oInfo['writefield']==0)?1:0;
		oInfo['fieldtype'] = _nFieldType || 0;
		return true;
	},
	_EffectPowerByTree : function(_sXPath, type, _nFieldType){
		var o = m_FieldHelper._GetEle(null, _sXPath, true);
		try{
			var el = o.el;
			if(type=='read')
				o.win._TransRule_.flowSetting(el, {type: type, power : m_FieldHelper.isReadable(null, _sXPath)});
			else if(type=='write')
				o.win._TransRule_.flowSetting(el, {type: type, power : m_FieldHelper.isWriteable(null, _sXPath)});
		}catch(error){
			//
		}
	},
	_EffectSetting : function(xp, info){
		if(info['fieldtype']==1){
			m_FieldHelper.renderPower(xp, 'publish', m_FieldHelper.isReadable(null, xp));
			return;
		}
		m_FieldHelper.renderPower(xp, 'read', m_FieldHelper.isReadable(null, xp), info);
		m_FieldHelper.renderPower(xp, 'write', m_FieldHelper.isWriteable(null, xp), info);
	},
	advanceSetting : function(xp, info){
		if(!info) return false;
		if(xp==null)return false;
		var mySet = m_oWorkFlowSetting[xp];
		if(!mySet){
			mySet = m_oWorkFlowSetting[xp] = {};
			mySet['fieldtype'] = 0;
		}
		for(var sName in info){
			if(sName=='XPath')continue;
			mySet[sName] = info[sName];
		}
		return true;
	},
	forcePower : function(_sXPath, _nFieldType, _sPowerType, _bPowerEnable){
		if(_sXPath==null)return false;
		
		var oInfo = m_oWorkFlowSetting[_sXPath];
		if(!oInfo){
			oInfo = m_oWorkFlowSetting[_sXPath] = {};
		}
		oInfo['fieldtype'] = _nFieldType || 0;
		if(_sPowerType=='write'){
			oInfo['writefield'] = _bPowerEnable?1:0;
		}
		else if(_sPowerType=='read'){
			oInfo['readfield'] = _bPowerEnable?1:0;
		}
		return true;
	},
	_FetchInitValues : function(_oWindow){
		var oDocument = _oWindow.document;
		var aElement = null;
		//Input text
		var arrInputs = oDocument.getElementsByTagName('INPUT');
		for(var i=0,n=arrInputs.length;i<n;i++){
			aElement = arrInputs[i];
			//FIXME radio,checkbox
			if(aElement.type!='text')continue;
			var sXPath = aElement.getAttribute('trs_temp_id', 2);
			if(!sXPath||aElement.value.trim()=='')continue;
			var sDefaultValue = aElement.getAttribute('default_value', 2);
			if(aElement.value!=sDefaultValue){
				this.advanceSetting(sXPath, {'initvalue':aElement.value});
			}
		}
		var arrSelects = oDocument.getElementsByTagName('Select');
		for(var i=0,n=arrSelects.length;i<n;i++){
			aElement = arrSelects[i];
			//FIXME multi-select
			var sXPath = aElement.getAttribute('trs_temp_id', 2);
			if(!sXPath||aElement.value.trim()=='')continue;
			var sDefaultValue = aElement.getAttribute('default_value', 2);
			if(aElement.value!=sDefaultValue){
				this.advanceSetting(sXPath, {'initvalue':aElement.value});
			}
		}
	},
	fetchInitValues : function(){
		var arrIframes = document.getElementsByTagName("iframe");
		for(var i=0; i<arrIframes.length; i++) {
			if(!arrIframes[i].contentWindow._XMLDATA_)continue;
			this._FetchInitValues(arrIframes[i].contentWindow);
		}
	},
	render : function(){
		this.fetchInitValues();
		//exclude sth.
		for(var sXPath in m_oWorkFlowSetting){
			var oInfo = m_oWorkFlowSetting[sXPath];
			oInfo['fieldname'] = sXPath;
			if(oInfo['readfield'] !='0' &&oInfo['writefield'] != '0'
				&&(oInfo['initvalue'] == null || oInfo['initvalue'] == '')
				&&(oInfo['initvaluecreator'] == null || oInfo['initvaluecreator'] == ''))
			delete m_oWorkFlowSetting[sXPath];
		}
		return m_oWorkFlowSetting;
	}
};