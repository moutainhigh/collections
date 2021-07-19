function $H(s){
	var a = s.split(',');
	var r = {};
	for(var i=0;i<a.length;i++){
		if(a[i].trim()=='')continue;
		r[a[i]] = true;
	}
	return r;
}
m_oAllFields = $H(m_strAllFields);
m_oUnReadableFields = $H(m_strUnReadableFields);
m_oUnWriteableFields = $H(m_strUnWriteableFields);
m_oUnReadableGroups = $H(m_strUnReadableGroups);
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
var m_oCurrCfg = null;

Event.observe(window, 'load', function(){
	window.pageInited = true;
	initPage();
});


function initPage(){
	if(!window.pageInited || !parent.pageInited) return;
	var sXMLString = $("TemplateXML").value;
	oTree = XMLTree.createInstance(sXMLString, null, null);
	$("FieldsTree").innerHTML = oTree.toString();
	function fireAction(exEv, tg){
		var actionItem = findItem(tg, false, '_action');
		if(!actionItem){
			var nodeName = tg.getAttribute("_nodeName", 2);
			if(nodeName == undefined) return;
			XMLTree.focusNode(nodeName);
			PowerHelper.focusParentNode(exEv, nodeName);
		}else{
			var action = actionItem.getAttribute('_action', 2);
			var oFunc = (parent.FieldsTree)?parent.FieldsTree[action]:null;
			if(oFunc){
				return (oFunc)(m_oCurrCfg, actionItem);
			}
			oFunc = window.PowerHelper[action];
			if(!oFunc)return;
			oFunc.call(PowerHelper, m_oCurrCfg.xpath, m_oCurrCfg.fieldtype);
		}
	}
	Ext.get(document.body).on('click', fireAction);
	regMenuItemEvent('bubble-panel');
	init();
};

var m_MenuItems = {
	root : [{
			action : 'allwriteable',
			name : wcm.LANG['INFOVIEW_DOC_23'] || '全部可编'
		}
	],
	group : [{
			action : 'readable',
			name : wcm.LANG['INFOVIEW_DOC_24'] || '允许发布'
		},{
			action : 'public_fill',
			name : wcm.LANG['INFOVIEW_DOC_25'] || '发布设置'
		}
	],
	field : [{
			action : 'appendtitle',
			name : wcm.LANG['INFOVIEW_DOC_26'] || '添加到标题'
		},{
			action : 'writeable',
			name : wcm.LANG['INFOVIEW_DOC_27'] || '允许编辑'
		},{
			action : 'editattr',
			name : wcm.LANG['INFOVIEW_DOC_22'] || '属性设置'
		}
	],
	root_flow : [{
			action : 'allreadable',
			name : wcm.LANG['INFOVIEW_DOC_28'] || '全部可查'
		},{
			action : 'allwriteable',
			name : wcm.LANG['INFOVIEW_DOC_23'] || '全部可编'
		}
	],
	group_flow : [{
			action : 'readable',
			name : wcm.LANG['INFOVIEW_DOC_29'] || '允许查看'
		},{
			action : 'workflow',
			name : wcm.LANG['INFOVIEW_DOC_30'] || '高级设置'
		}
	],
	field_flow : [{
			action : 'readable',
			name : wcm.LANG['INFOVIEW_DOC_29'] || '允许查看'
		},{
			action : 'writeable',
			name : wcm.LANG['INFOVIEW_DOC_27'] || '允许编辑'
		},{
			action : 'workflow',
			name : wcm.LANG['INFOVIEW_DOC_30'] || '高级设置'
		}
	]
}
function getViewItems(cfg){
	var type = m_sCurrMode=='normal' ? cfg.type : (cfg.type + '_flow');
	return m_MenuItems[type];
}
function isEnable(t, tg){
	var id = tg.getAttribute('_nodeXPath', 2);
	var isEntry = tg.getAttribute('_isEntry', 2)=='true'?1:0;
	switch(t.action.toLowerCase()){
		case 'readable':
			return PowerHelper.isReadAble(id, isEntry);
		case 'writeable':
			return PowerHelper.isWriteAble(id, isEntry);
		case 'allwriteable':
			return PowerHelper.isAllWriteAble();
		case 'allreadable':
			return PowerHelper.isAllReadAble();
	}
	return true;
}
function init(){
	function buildCfg(el){
		var eid = el.getAttribute('_nodeXPath', 2);
		var isEntry = el.getAttribute('_isEntry', 2)=='true';
		var parentId = el.getAttribute('_parentId', 2);
		m_oCurrCfg = {
			id : eid,
			isEntry : isEntry,
			parentId : parentId,
			type : (parentId=='-1')?'root':(isEntry?'group':'field'),
			name : el.getAttribute('_nodeName', 2),
			xpath : eid,
			el : el,
			fieldtype : isEntry ? 1 : 0
		};
		return m_oCurrCfg;
	}
	function findNodeXPathChild(el){
		if(!el)return;
		var t = el.getElementsByTagName('SPAN')[0];
		if(t && t.getAttribute('_nodeXPath', 2)!=null)return t;
	}
	function flyBtnFx(ev, el, undo){
		var fb = $('fly-button');
		if(undo){
			fb.style.display = 'none';
			fb.cfg = '';
			return;
		}
		var eid = el.getAttribute('_nodeXPath', 2);
		if(fb.cfg && fb.cfg.id == eid 
			&& fb.style.display!='none')return;
		var p = ev.pointer;
		Position.clone(el, fb, {
			setWidth:   false,
			setHeight:  false,
			offsetTop:  2
		});
		fb.style.left = '';
		fb.style.right = '20px';
		fb.style.display = '';
		fb.cfg = buildCfg(el);
	}
	function buildViewMenu(items, tg){
		var rst = [];
		var str = '<DIV class="menuitem" unselectable="on" _action="{1}" _tgid="{0}" _power="{4}">{2}<DIV class="{3}{1}" unselectable="on"></DIV></DIV>';
		for(var i=0, n=items.length; i<n; i++){
			var t = items[i];
			var p = isEnable(t, tg);
			rst.push(String.format(str, tg.getAttribute('trs_temp_id', 2), t.action.toLowerCase(),
				t.name, p?'':'un', p?1:0));
		}
		return rst.join('');
	}
	var bubblePanel = new wcm.BubblePanel('bubble-panel');
	function showContextMenu(ev, tg){
		var fb = $('fly-button');
		if(!fb.cfg)return true;
		//if(fb.cfg.type == 'group' && fb.cfg.parentId.trim() == '')return;
		var viewItems = getViewItems(fb.cfg);
		$('bubble-panel').innerHTML = buildViewMenu(viewItems, fb.cfg.el);
		bubblePanel.bubble(ev.pointer, function(p){
			return [p[0]-120, p[1]+2];
		});
		ev.stop();
		return false;
	}
	Ext.get('fly-button').on('click', showContextMenu);
	var lastMsm = null;
	function mouseoverFx(ev, tg){
		var temp = findItem(tg, false, '_fxType');
		if(temp!=null)return;
		var divNode = findItem(tg, 'dTreeNode');
		tg = findItem(tg, false, '_nodeXPath') || findNodeXPathChild(divNode);
		if(lastMsm!=null && lastMsm.el!=tg){
			flyBtnFx(null, lastMsm.el, true);
			if(lastMsm.div)lastMsm.div.style.backgroundColor = '';
			lastMsm = null;
		}
		if(tg==null)return;
		flyBtnFx(ev, tg);
		divNode.style.backgroundColor = '#ECECEC';
		lastMsm = {ev:ev.browserEvent, el:tg, div:divNode};
	}
	var extBody = Ext.get(document.body);
	extBody.on('mousemove', function(ev, tg){
		if(Element.visible('bubble-panel'))return;
		mouseoverFx(ev, tg);
	});
	extBody.on('contextmenu', function(ev, tg){
		mouseoverFx(ev, tg);
		showContextMenu(ev, tg);
	});
	var m_oLastFocusNode = null;
	XMLTree.focusNode = function(nm){
		if(!nm)return;
		var item = XMLTree._findNodeByPath(nm, $('FieldsTree'));
		if(!item)return;
		oTree.openTo(nm);
		item.scrollIntoView(true);
		if(m_oLastFocusNode){
			Element.removeClassName(m_oLastFocusNode, 'treenode_focus');
		}
		Element.addClassName(item, 'treenode_focus');
		m_oLastFocusNode = item;
	}
	XMLTree.dblclickNode = function(el, ev) {
		if(el.getAttribute('_isEntry', 2)=='true')return;
		var oFunc = (parent.FieldsTree)?parent.FieldsTree['editattr']:null;
		if(oFunc){
			(oFunc)(buildCfg(el), null);
		}
		return false;
	}
}
var PowerHelper = {
	renderAllFieldsPower : function(stype, enable){
		if(stype=='read'){
			m_oUnReadableFields = enable ? {} : m_oAllFields;
		}
		else if(stype=='write'){
			m_oUnWriteableFields = enable ? {} : m_oAllFields;
		}
		for(var xp in m_oAllFields){
			this._effectNode(xp, 0);
		}
	},
	renderPower : function(xp, ftype, stype, enable){
		if(xp==null) return;
		if(ftype==1 || stype=='publish'){
			m_oUnReadableGroups[xp] = !enable;
			ftype = 1;
		}
		else if(ftype==0){
			if(stype=='read'){
				if(m_oUnReadableFields==null )return;
				m_oUnReadableFields[xp] = !enable;
			}
			else if(stype=='write'){
				if(m_oUnWriteableFields==null )return;
				m_oUnWriteableFields[xp] = !enable;
			}
		}
		this._effectNode(xp, ftype);
	},
	isReadAble : function(xp, ftype){
		if(parent.WorkFlowHelper){
			return parent.WorkFlowHelper.isReadable(xp);
		}
		var json = ftype==0 ? m_oUnReadableFields : m_oUnReadableGroups;
		return !json || !xp || !json[xp];
	},
	isWriteAble : function(xp, ftype){
		if(parent.WorkFlowHelper){
			return parent.WorkFlowHelper.isWriteable(xp);
		}
		var json = ftype==0 ? m_oUnWriteableFields : null;
		return !json || !xp || !json[xp];
	},
	isAllReadAble : function(){
		for(var xp in m_oAllFields){
			if(!this.isReadAble(xp, 0))
				return false;
		}
		return true;
	},
	isAllWriteAble : function(){
		for(var xp in m_oAllFields){
			if(!this.isWriteAble(xp, 0))
				return false;
		}
		return true;
	},
	allreadable : function(){
		var able = this.isAllReadAble();
		if(!confirm(!able? String.format('您确定要设置所有的字段为允许查看?'):String.format('您确定要设置所有的字段为不允许查看?')))
			return;
		if(parent.WorkFlowHelper){
			for(var xp in m_oAllFields){
				this.forcePower(xp, 0, 'read', !able);
			}
		}
	},
	allwriteable : function(){
		var able = this.isAllWriteAble();
		if(!confirm(able? String.format('您确定要设置所有的字段为只读?'):String.format('您确定要设置所有的字段为可编?')))
			return;
		if(parent.WorkFlowHelper){
			for(var xp in m_oAllFields){
				this.forcePower(xp, 0, 'write', !able);
			}
		}
		else{
			this._InfoviewAllFieldsReadOnly(able);
		}
	},
	forcePower : function(xp, ftype, stype, enable){
		if(parent.WorkFlowHelper){
			if(parent.WorkFlowHelper.forcePower)
				parent.WorkFlowHelper.forcePower(xp, ftype, stype, enable);
			this._effectNode(xp, ftype);
			if(parent.WorkFlowHelper._EffectPowerByTree)
				parent.WorkFlowHelper._EffectPowerByTree(xp, stype);
		}
	},
	focusParentNode : function(ev, xp){
		if(parent.m_FieldHelper){
			if(parent.m_FieldHelper._GetEle){
				el = parent.m_FieldHelper._GetEle(null, xp);
				if(el != null){
					el.focus(); 
				}
			}
		}
	},
	readable : function(xp, ftype){
		if(parent.WorkFlowHelper){
			var isWriteable = parent.WorkFlowHelper.toggleReadable(xp, ftype);
			this._effectNode(xp, ftype);
			parent.WorkFlowHelper._EffectPowerByTree(xp, 'read', ftype);
			return;
		}
		if(ftype==0){
			m_oUnReadableFields[xp] = !m_oUnReadableFields[xp];
		}
		else if(ftype==1){
			this._InfoviewGroupReadable(xp);
		}
	},
	writeable : function(xp, ftype){
		if(parent.WorkFlowHelper){
			parent.WorkFlowHelper.toggleWriteable(xp, ftype);
			this._effectNode(xp, ftype);
			parent.WorkFlowHelper._EffectPowerByTree(xp, 'write', ftype);
			return;
		}
		if(ftype==0){
			this._InfoviewFieldReadOnly(xp);
		}
	},
	_XPath2Name : function(xp){
		if(xp==null)return'';
		var axp = xp.split('/');
		for(var i=0,n=axp.length;i<n;i++){
			axp[i] = axp[i].replace(/^[^:]*?:/,'');
		}
		return axp.join('_');
	},
	_XPath2GroupName : function(xp){
		if(xp==null)return'';
		var axp = xp.split('/');
		return axp.pop().replace(/^[^:]*?:/,'');
	},
	_ToggleWriteableVars : function(xp, ftype){
		m_oUnWriteableFields[xp] = !m_oUnWriteableFields[xp];
		this._effectNode(xp, ftype);
	},
	_ToggleReadableVars : function(xp, ftype){
		m_oUnReadableFields[xp] = !m_oUnReadableFields[xp];
		this._effectNode(xp, ftype);
	},
	_ToggleReadableGroupVars : function(xp){
		m_oUnReadableGroups[xp] = !m_oUnReadableGroups[xp];
		this._effectNode(xp, 1);
	},
	_InfoviewGroupReadable : function(xp){
		var flag = m_oUnReadableGroups[xp]?1:0;
		var url = "./infoview_group_simple_save.jsp?InfoViewId=" + m_nInfoViewId + "&IVGroupName="
			+ encodeURIComponent(this._XPath2GroupName(xp)) +"&PublicFill="+flag;
		new ajaxRequest({
			url : url,
			onSuccess : function(_transport){
				PowerHelper._ToggleReadableGroupVars(xp);
				if(parent.m_FieldHelper && parent.m_FieldHelper.renderFrmPower){
					parent.m_FieldHelper.renderFrmPower(xp, 'publish', flag==1);
				}
			}
		});
	},
	_InfoviewFieldReadOnly : function (xp){
		var flag = m_oUnWriteableFields[xp]?0:1;
		var url = "./infoview_field_set_readonly.jsp?InfoViewId=" + m_nInfoViewId + "&IVFieldName="
			+ encodeURIComponent(this._XPath2Name(xp)) +"&ReadOnly="+flag;
		new ajaxRequest({
			url : url,
			onSuccess : function(_transport){
				PowerHelper._ToggleWriteableVars(xp, 0);
				if(parent.m_FieldHelper && parent.m_FieldHelper.renderFrmPower){
					parent.m_FieldHelper.renderFrmPower(xp, 'write', flag!=1);
				}
			}
		});
	},
	_InfoviewAllFieldsReadOnly : function(_bReadonly){
		var nReadonlyFlag = _bReadonly?1:0;
		var url = "./infoview_field_set_readonly.jsp?InfoViewId=" + m_nInfoViewId + "&ReadOnly="+nReadonlyFlag;
		new ajaxRequest({
			url : url,
			onSuccess : function(_transport){
				PowerHelper.renderAllFieldsPower('write', !_bReadonly);
				if(parent.m_FieldHelper && parent.m_FieldHelper.renderFrmPower){
					parent.m_FieldHelper.renderFrmAllPower();
				}
			}
		});
	},
	_effectNode : function(xp, ftype){
		var oSpan = XMLTree._findNodeByPath(xp, $('FieldsTree'));
		if(oSpan){
			oSpan.className = this.getNodeClass(xp, ftype);
		}
	},
	getNodeClass : function(xp, ftype){
		if(!this.isReadAble(xp, ftype)){
			return 'node_unread';
		}
		if(!this.isWriteAble(xp, ftype)){
			return 'node_unwrite';
		}
		return '';
	}
}
