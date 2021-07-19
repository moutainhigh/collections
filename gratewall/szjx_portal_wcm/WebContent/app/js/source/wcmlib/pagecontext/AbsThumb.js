Ext.ns('wcm.ThumbList', 'wcm.ThumbItem', 'wcm.ThumbItemMgr');
//缩略图抽象
var m_ThumbItemConst = {
	thumb_item_cls : 'thumb_item',
	thumb_item_active_cls : 'thumb_item_active',
	thumb_item_hover_cls : 'thumb_item_hover',
	identify_attr : 'itemId',
	thumb_item_id_prefix : 'thumb_item_',
	thumb_id_prefix : 'thumb_',
	thumb_attrs_id_prefix : 'thumb_attrs_',
	thumb_edit_id_prefix : 'thumb_edit_',
	cbx_id_prefix : 'cbx_',
	desc_id_prefix : 'desc_',
	thumb_selectAll : 'selectAll'
};
wcm.ThumbItemMgr = function(){
	var type = {};
	return {
		registerType : function(_sType, _cls){
			type[_sType.toUpperCase()] = _cls;
		},
		get : function(_sType, _sId){
			if(_sId && !String.isString(_sId)){
				_sId = _sId.getAttribute(m_ThumbItemConst["identify_attr"]);
			}
			var item = new type[_sType.toUpperCase()](_sId);
			return item.wrapper ? item.wrapper() : item;
		},
		isThumbItem : function(_oDom){
			if(!_oDom) return false;
			var sThumbCls = m_ThumbItemConst["thumb_item_cls"];
			return Element.hasClassName(_oDom, sThumbCls);
		},
		createListeners : function(node, fWrapper){
			if(!node || !fWrapper)return;
			var oldWrapper = node.prototype.wrapper;
			node.prototype.wrapper = oldWrapper ? function(){
				oldWrapper.apply(this, []);
				fWrapper.apply(this, []);
				return this;
			} : function(){
				fWrapper.apply(this, []);
				return this;
			};
		}
	};
}();

wcm.ThumbItem = function(_sId){
	this.id = _sId;
	wcm.ThumbItem.superclass.constructor.apply(this, arguments);
	var events = ['beforeclick', 'click', 'beforehover', 'hover', 'beforeunhover', 'unhover', 'beforedblclick', 'dblclick', 'beforeedit', 'edit', 'beforesave', 'save'];
	this.addEvents.apply(this, events);
};

(function(){
	Ext.extend(wcm.ThumbItem, wcm.util.Observable, {
		getId : function(){
			return this.id;
		},
		getDom : function(){
			return $(this["thumb_item_id_prefix"] + this.getId());
		},
		isActive : function(){
			return Element.hasClassName(this.getDom(), this["thumb_item_active_cls"]);
		},
		isDisabled : function(){
			var cbx = $(this["cbx_id_prefix"] + this.getId());
			if(cbx){
				var disabled = cbx.getAttribute('disabled', 2);
				return disabled != null && disabled != false;
			}
			return false;
		},
		active : function(){
			if(this.isActive()) return;
			var XThumbItem = wcm.XThumbItem.get(this);
			if(!XThumbItem.select()) return;
			this.onActive();
			XThumbItem.afterselect();
		},
		//private
		onActive : function(){
			Element.addClassName(this.getDom(), this["thumb_item_active_cls"]);
			var cbx = $(this["cbx_id_prefix"] + this.getId());
			if(cbx){
				cbx.checked = true;
				cbx.defaultChecked = true;
			}
			this.list.onSelect(this);
		},
		deactive : function(){
			if(!this.isActive()) return;
			var XThumbItem = wcm.XThumbItem.get(this);
			if(!XThumbItem.unselect()) return;
			this.onDeactive();
			XThumbItem.afterunselect();
		},
		//private
		onDeactive : function(){
			Element.removeClassName(this.getDom(), this["thumb_item_active_cls"]);
			var cbx = $(this["cbx_id_prefix"] + this.getId());
			if(cbx){
				cbx.checked = false;
				cbx.defaultChecked = false;
			}
			this.list.onUnselect(this);
		},
		isHover : function(){
			return Element.hasClassName(this.getDom(), this["thumb_item_hover_cls"]);
		},
		hover : function(event){
			if(this.isHover()) return;
			if(this.fireEvent('beforehover', event) === false) return;
			this.onHover();
			this.fireEvent('hover', event);
		},
		//private
		onHover : function(){
			Element.addClassName(this.getDom(), this["thumb_item_hover_cls"]);
		},
		unhover : function(event){
			if(!this.isHover()) return;
			if(this.fireEvent('beforeunhover', event) === false) return;
			this.onUnhover();
			this.fireEvent('unhover', event);
		},
		//private
		onUnhover : function(){
			Element.removeClassName(this.getDom(), this["thumb_item_hover_cls"]);
		},
		click : function(event){
			if(this.fireEvent('beforeclick', event) === false) return;
			this.fireEvent('click', event);
		},
		dblclick : function(event){
			if(this.fireEvent('beforedblclick', event) === false) return;
			this.fireEvent('dblclick', event);
		},
		edit : function(){
			if(this.fireEvent('beforeedit') === false) return;
			this.fireEvent('edit');
		},
		beforeSave : function(inputEl, descEl){
			this.fireEvent('beforesave', inputEl, descEl);
		},
		save : function(inputEl, descEl){
			this.fireEvent('save', inputEl, descEl);
		},
		afterSave : function(inputEl, descEl){
			this.fireEvent('aftersave', inputEl, descEl);
		},
		isToggle : function(event){
			if(event.ctrlKey) return true;
			var oCbx = Event.element(event);
			return oCbx.tagName == "INPUT" && oCbx.type.toUpperCase() == "CHECKBOX";
		},
		toggle : function(){
			this[this.isActive() ? "deactive" : 'active']();
		},
		getCmdTarget : function(dom){
			while(dom && dom.tagName != "BODY"){
				if(dom.getAttribute("cmd")) return dom;
				if(Element.hasClassName(dom, this["thumb_item_cls"])) return null;
				dom = dom.parentNode;
			}
			return null;
		},
		getItemInfo : function(_oInfo){
			_oInfo = _oInfo || this.itemInfo;
			var result = {};
			var dom = this.getDom();
			if(!dom){
				return {
					objType : this.itemType(dom)
				};
			}
			for(var sKey in _oInfo){
				if(!_oInfo[sKey])continue;
				result[sKey] = dom.getAttribute(sKey, 2);
			}
			result.objId = dom.getAttribute(this["identify_attr"], 2);
			result.right = dom.getAttribute('right', 2);
			result.objType = this.itemType(dom);
			return result;
		}
	});
})();

(function(){
	//private
	var select = function(_sItemId){
		if(arguments.length > 1){
			for (var i = 0; i < arguments.length; i++){
				select.call(this, arguments[i]);
			}
			return;
		}
		if(!_sItemId) return;
		this.get(_sItemId).active();
	};

	//private
	var unselect = function(_sItemId){
		if(arguments.length > 1){
			for (var i = 0; i < arguments.length; i++){
				unselect.call(this, arguments[i]);
			}
			return;
		}
		if(!_sItemId) return;
		this.get(_sItemId).deactive();
	};

	//private
	var selectAll = function(){
		var box = $(this["container"]);
		var dom = Element.first(box);
		while(dom){
			if(wcm.ThumbItemMgr.isThumbItem(dom)){
				select.call(this, dom);
			}
			dom = Element.next(dom);
		}
	};

	//private
	//for improve the performance, then use selectSuspended.
	var unselectAll = function(){
		this.selectSuspended = true;
		var cache = this.cache;
		for(var index = 0, len = cache.length; index < len; index++){
			unselect.call(this, cache[index]);
		}
		this.cache = [];
		delete this.selectSuspended;
	};

	//private
	/*set the length of thumb item in one row.*/
	var setLengthPreRow = function(){
		this["length_pre_row"] = 0;
		var r = $(this.container);
		var p = Element.first(r);
		if(!p){
			return;
		}
		this["length_pre_row"] = 1;
		var q = Element.next(p);
		while(q){
			if(p.offsetTop != q.offsetTop){
				break;
			}
			this["length_pre_row"]++;
			p = q;
			q = Element.next(p);
		}
	};

	/**
	*@class	wcm.ThumbList
	*/
	wcm.ThumbList = function(sThumbType, sContainer){
		this["thumb_type"] = sThumbType;
		this["container"] = sContainer || this["container"];
		this.cache = [];
	};

	Ext.apply(wcm.ThumbList.prototype, {
		container : 'wcm_table_grid',
		thumb_type : null,
		length_pre_row : 0,
		cache : null
	});

	//cmd operators
	Ext.apply(wcm.ThumbList.prototype, {
		cmds : {},
		addCmds : function(configs){
			for(var sKey in configs){
				this.cmds[sKey] = configs[sKey];
			}
		},
		getCmd : function(sKey){
			return this.cmds[sKey];
		}
	});

	Ext.apply(wcm.ThumbList.prototype, {
		init : function(info){
			this.cache = [];
			this.info = info || {};
			if(info){
				this.validCache(info["SelectedIds"]);
				if(info["RecordNum"] == 0){
					var dom = $('thumb_NoObjectFound');
					if(dom){
						Element.update('wcm_table_grid', Element.first(dom).outerHTML);
					}
				}
			}
			this.initEvents();
			this.notify(true);
		},
		validCache : function(selectIds){
			if(!selectIds) return;
			var cache = [];
			var aIds = selectIds.split(",");
			for (var i = 0; i < aIds.length; i++){
				if(this.getThumbItemDom(aIds[i])){
					cache.push(aIds[i]);
				}
			}
			this.cache = cache;
		},
		getIds : function(bArray){
			if(bArray) return this.cache;
			return this.cache.join(",");
		},
		initEvents : function(){
			if(this.eventsInited) return;
			this.eventsInited = true;
			var box = this["container"];
			Event.observe(box, 'click', this.clickEvent.bind(this));
			Event.observe(box, 'dblclick', this.dblclickEvent.bind(this));
			Event.observe(box, 'mousemove', this.mouseMoveEvent.bind(this));
			Event.observe(box, 'mouseout', this.mouseOutEvent.bind(this));
			Event.observe(box, 'resize', setLengthPreRow.bind(this));
			Event.observe(box, 'selectstart', function(event){
				var dom = Event.element(event);
				if(dom.tagName == 'INPUT' && dom.type.toUpperCase() == 'TEXT') return true;
				Event.stop(event);
			});
			//contextmenu
			if(PageContext.contextMenuEnable){
				Ext.fly('wcm_table_grid').on('contextmenu', function(event, target){
					var extra = {extEvent : event, targetElement : target};
					event = window.event || event;
					var srcElement = Event.element(event);
					var thumbItem = this.find(srcElement);
					var XThumbItem = wcm.XThumbItem.get(thumbItem, extra);
					XThumbItem.contextmenu();
					Event.stop(event);
					return false;
				}, this);
			}			
		},
		clickEvent : function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var thumbItem = this.find(srcElement);
			if(!thumbItem) return;
			thumbItem.click(event);
			this.notify();
		},
		//private, invoked by ThumbItem instance
		onClick : function(thumbItem, extra){
			if(extra && extra["type"] == 'toggle') return;
			unselectAll.call(this);
		},
		dblclickEvent : function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var thumbItem = this.find(srcElement);
			if(!thumbItem) return;
			thumbItem.dblclick(event);
		},
		mouseMoveEvent : function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var thumbItem = this.find(srcElement);
			if(thumbItem == this.lastThumbItem) return;
			if(thumbItem && this.lastThumbItem
					&& thumbItem.getId() == this.lastThumbItem.getId()){
				return;
			}
			if(this.lastThumbItem) this.lastThumbItem.unhover(event);
			if(thumbItem) thumbItem.hover(event);
			this.lastThumbItem = thumbItem;
		},
		mouseOutEvent : function(event){
			if(this.lastThumbItem){
				this.lastThumbItem.unhover();
				delete this.lastThumbItem;
			}
		},
		get : function(_sId){
			var thumbItem = wcm.ThumbItemMgr.get(this["thumb_type"], _sId);
			thumbItem.list = this;//relate the item to list
			return thumbItem;
		},
		find : function(_oDom){
			var box = $(this["container"]);
			while(_oDom && _oDom != box){
				if(wcm.ThumbItemMgr.isThumbItem(_oDom)){
					return this.get(_oDom);
				}
				_oDom = _oDom.parentNode;
			}
			return null;
		},

		/*
		*ThumbList don't know the dom detail of thumb item.
		*so, wo get the info by the ThumbItem object.
		*if the method has some problem in performance,
		*then we should use the quick method to get the id.
		*/
		getThumbItemId : function(_itemDom){
			var thumbItem = this.get(_itemDom);
			return thumbItem.getId();
		},
		getThumbItemDom : function(_itemId){
			var thumbItem = this.get(_itemId);
			return thumbItem.getDom();
		},
		contain : function(_itemId){
			return this.getThumbItemDom(_itemId) != null;
		},
		getFirstId : function(){
			var box = $(this["container"]);
			var itemDom = Element.first(box);
			return this.getThumbItemId(itemDom);
		},
		getLastId : function(){
			var box = $(this["container"]);
			var itemDom = Element.last(box);
			return this.getThumbItemId(itemDom);
		},
		getId : function(_sItemId, _sDirection, _nCount){
			if(!_sItemId) return null;
			var domItem = this.getThumbItemDom(_sItemId);
			if(!domItem) return null;
			domItem = this.getDom(domItem, _sDirection, _nCount);
			if(domItem){
				return this.getThumbItemId(domItem);
			}
			return null;
		},
		getDom : function(_oDomItem, _sDirection, _nCount){
			while(_oDomItem && _nCount > 0){
				_oDomItem = Element[_sDirection](_oDomItem)
				_nCount--;
			}
			return _oDomItem;
		},
		editThumbItem : function(event){
			if(this.cache.length != 1) return;
			this.get(this.cache.last()).edit(event);
		},
		enterThumbItem : function(event){
			if(this.cache.length != 1) return;
			this.get(this.cache.last()).dblclick(event);
		},
		selectBefore : function(){
			var itemId = this.getId(this.cache.last(), "previous", 1);
			itemId = itemId || this.getLastId();
			if(!itemId) return;
			unselectAll.apply(this, arguments);
			this.select(itemId);
		},
		selectAfter : function(){
			var itemId = this.getId(this.cache.last(), "next", 1);
			itemId = itemId || this.getFirstId();
			if(!itemId) return;
			unselectAll.apply(this, arguments);
			this.select(itemId);
		},
		selectAbove : function(){
			var count = this.getLengthPreRow();
			var itemId = this.getId(this.cache.last(), "previous", count);
			if(!itemId) return;
			unselectAll.apply(this, arguments);
			this.select(itemId);
		},
		selectBelow : function(){
			var count = this.getLengthPreRow();
			var itemId = this.getId(this.cache.last(), "next", count);
			if(!itemId) return;
			unselectAll.apply(this, arguments);
			this.select(itemId);
		},
		select : function(_sItemId){
			this.selectedChange = false;
			select.apply(this, arguments);
			this.notify();
		},
		//private
		onSelect : function(thumbItem){
			this.selectedChange = true;
			if(this.selectSuspended) return;
			this.cache.push(thumbItem.getId());
		},
		unselect : function(_sItemId){
			this.selectedChange = false;
			unselect.apply(this, arguments);
			this.notify();
		},
		//private
		onUnselect : function(thumbItem){
			this.selectedChange = true;
			if(this.selectSuspended) return;
			this.cache.remove(thumbItem.getId());
		},
		toggle : function(_sItemId){
			if(!_sItemId) return;
			var thumbItem = this.get(_sItemId);
			this[thumbItem.isActive() ? "unselect" : "select"]();
		},
		selectAll : function(){
			this.selectedChange = false;
			selectAll.apply(this, arguments);
			this.notify();
		},
		unselectAll : function(){
			this.selectedChange = false;
			unselectAll.apply(this, arguments)
			this.notify();
		},
		toggleAll : function(){
			var bSelectAll = false;
			var box = $(this["container"]);
			var dom = Element.first(box);
			while(dom){
				if(wcm.ThumbItemMgr.isThumbItem(dom)){
					var thumbItem = this.get(dom);
					if(!thumbItem.isDisabled() && !thumbItem.isActive()){
						bSelectAll = true;
						break;
					}
				}
				dom = Element.next(dom);
			}
			this[bSelectAll ? "selectAll" : "unselectAll"]();
		},
		//private
		notify : function(bForce){
			if(bForce || this.selectedChange){
				this.selectedChange = false;
				wcm.XThumbList.get(this).selectedchange();
			}
		},
		/*get the length of thumb item in one row.*/
		getLengthPreRow : function(){
			if(this["length_pre_row"]) return this["length_pre_row"];
			setLengthPreRow.call(this);
			return this["length_pre_row"];
		},
		initEditedItems : function(ids){
			if(!ids || ids.length <= 0) return;
			var dom = this.getThumbItemDom(ids[0]);
			if(!dom) return;
			this.firstEditIds = true;
			this.editIds = ids;
			Element.addClassName(dom, "thumb-edit-item");
		},
		clearEditedItems : function(){
			if(this.firstEditIds){
				delete this.firstEditIds;
				return;
			}
			var ids = this.editIds;
			if(!ids || ids.length <= 0) return;
			delete this.editIds;
			var dom = this.getThumbItemDom(ids[0]);
			if(!dom) return;
			Element.removeClassName(dom, "thumb-edit-item");
		},
		getItemInfos : function(_oInfo){
			var arrInfos = [];
			var cache = this.cache;
			for(var index = 0, len = cache.length; index < len; index++){
				var thumbItem = this.get(cache[index]);
				if(!thumbItem) continue;
				arrInfos.push(thumbItem.getItemInfo(_oInfo));
			}
			return arrInfos;
		}
	});
})();

wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
	this.on('beforedblclick', function(event){
		var extra = {event : event};
		var dom = Event.element(event);
		if(dom.tagName == 'INPUT' && dom.type.toUpperCase() == 'TEXT') return false;
		var dom = this.getCmdTarget(dom);
		if(!dom)return true;
		var cmd = dom.getAttribute("cmd");
		if(cmd) return false;
		return true;
	});
	this.on('beforeclick', function(event){
		var extra = {event : event};
		var dom = this.getCmdTarget(Event.element(event));
		if(!dom)return true;
		var cmd = dom.getAttribute("cmd");
		if(cmd) extra["cmd"] = cmd;
		var oXThumbCell = wcm.XThumbCell.get(this, extra);
		if(oXThumbCell.click())
			oXThumbCell.afterclick();
		return false;
	});
	this.on('beforeclick', function(event){
		if(!this.isActive()) return true;
		var dom = Event.element(event);
		if(dom.tagName == "INPUT"
				&& dom.type.toUpperCase() == "TEXT")
			return false;
		var bindEl = dom.getAttribute("bind");
		if(!bindEl) return true;
		this.edit();
		return false;
	});
	this.on('click', function(event){
		this.getDom().focus();
		if(this.isToggle(event)){
			this.list.onClick(this, {type : 'toggle'});
			this.toggle();
		}else{
			var sMethod = this.isActive() ? "deactive" : "active";
			this.list.onClick(this);
			this[sMethod]();
		}
	});
});

//edit
(function(){
	var ensureInput = function(descEl, bindEl){
		var editable = $(this["thumb_edit_id_prefix"] + this.getId())
		var input = editable.getElementsByTagName("input")[0];
		if(!input){
			editable.innerHTML = bindEl.innerHTML;
			input = editable.getElementsByTagName("input")[0];
		}
		input.value = descEl.innerHTML.unescapeHTML();
		input.onblur = this.beforeSave.bind(this, input, descEl);
		input.onkeydown = function(event){
			event = window.event || event;
			if(event.keyCode != Event.KEY_RETURN) return;
			this.blur();
		};
		try{
			input.focus();
			input.select();
		}catch(error){
			//just skip it.
		}
	};

	wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
		this.on('beforeedit', function(event){
			//get the desc element.
			var descEl = $(this["desc_id_prefix"] + this.getId());
			if(!descEl) return false;

			//get the bind element.
			var bindEl = descEl.getAttribute("bind");
			if(!bindEl) return false;
		});

		this.on('edit', function(event){
			var sId = this.getId();

			//hide the attrs desc.
			Element.hide(this["thumb_attrs_id_prefix"] + sId);

			//show the editable desc.
			Element.show(this["thumb_edit_id_prefix"] + sId);

			//ensure the existance of editable input.
			var descEl = $(this["desc_id_prefix"] + sId);
			var bindEl = $(descEl.getAttribute("bind"));
			ensureInput.call(this, descEl, bindEl);
		});
	});
})();

//save
(function(){
	wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
		this.on('beforesave', function(inputEl, descEl){
			var sValue = inputEl.value.trim();
			//judge changed whether or not.
			if(sValue.escapeHTML() == descEl.innerHTML){//no change.
				this.afterSave(inputEl, descEl);
				return;
			}
			//valid here.
			ValidatorHelper.asynValid(inputEl, {
				success : this.save.bind(this, inputEl),
				fail : function(warning){//invalid case.
					Ext.Msg.warn(warning, function(){
						try{
							inputEl.value = sValue || descEl.innerHTML.unescapeHTML() || "";
							inputEl.focus();
							inputEl.select();
						}catch(error){
							//just skip it.
						}
					});
				}
			});
		});
		this.on('save', function(inputEl){
			var sServiceId = inputEl.getAttribute("serviceId") || PageContext.serviceId;
			var sMethodName = inputEl.getAttribute("methodName") || "save";
			var oPostData = {objectId : this.getId()};
			oPostData[inputEl.name] = inputEl.value.trim();
			BasicDataHelper.call(sServiceId, sMethodName, oPostData, true, function(){
				var context = this.getItemInfo(this.itemInfo);
				CMSObj.createFrom(context).afteredit();
			}.bind(this));
		});
		this.on('aftersave', function(inputEl, descEl){
			inputEl.style.border = '1px solid silver';
			descEl.innerHTML = inputEl.value.escapeHTML();
			var sId = this.getId();
			Element.hide(this["thumb_edit_id_prefix"] + sId);
			Element.show(this["thumb_attrs_id_prefix"] + sId);
			inputEl.onblur = null;
			inputEl.onkeydown = null;
		});
	});
})();

wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
	Ext.apply(this, m_ThumbItemConst);
});

Ext.ns('wcm.XThumbList', 'wcm.XThumbItem', 'wcm.XThumbCell');

WCMConstants.OBJ_TYPE_XTHUMBLIST = "XThumbList";
wcm.XThumbList = function(list, extra){
	var context = (list) ? this.buildContext(list) : null;
	this.objType = WCMConstants.OBJ_TYPE_XTHUMBLIST;
	wcm.XThumbList.superclass.constructor.call(this, null, Ext.applyIf(context, extra));
	this.addEvents(['selectedchange']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_XTHUMBLIST, 'wcm.XThumbList');
Ext.apply(wcm.XThumbList, {
	get : function(list, extra){//for cache
		return new wcm.XThumbList(list, extra);
	}
});
Ext.extend(wcm.XThumbList, wcm.CMSObj, {
	isCMSObjType : function(){
		return false;
	},
	buildContext : function(list){
		if(list == null) return null;
		var context = (this._buildContext) ? this._buildContext(list) : null;
		var info = Ext.applyIf(list.get().getItemInfo(), {list : list});
		return Ext.applyIf(info, context);
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBLIST,
	selectedchange : function(event){
		var list = event.getContext().list;
		var elements = list.getItemInfos(list.get().itemInfo);
		var context = {sysOpers : wcm.SysOpers, list : list};
		Ext.apply(context, list.info);
		Ext.apply(context, PageContext.getContext());
		var oCmsObjs = CMSObj.createEnumsFrom({
			objType : list.get().itemType()
		}, context);
		oCmsObjs.addElement(elements);
		oCmsObjs.afterselect();
	}
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBLIST,
	selectedchange : function(event){
		var list = event.getContext().list;
		var bSelectAll = false;
		var box = $(list["container"]);
		var dom = Element.first(box);
		var sel = $(m_ThumbItemConst.thumb_selectAll);
		if(dom.id == "" && sel) {
			sel.checked = false;
			return;
		}
		while(dom){

			if(wcm.ThumbItemMgr.isThumbItem(dom)){
				var thumbItem = list.get(dom);
				if(!thumbItem.isDisabled() && !thumbItem.isActive()){
					bSelectAll = true;
					break;
				}
			}
			dom = Element.next(dom);
		}
		if(sel) sel.checked = bSelectAll ? false : true;
	}
});
WCMConstants.OBJ_TYPE_XTHUMBITEM = "XThumbItem";
wcm.XThumbItem = function(item, extra){
	var context = (item) ? this.buildContext(item) : null;
	this.objType = WCMConstants.OBJ_TYPE_XTHUMBITEM;
	wcm.XThumbItem.superclass.constructor.call(this, null, Ext.applyIf(context || {}, extra));
	this.addEvents(['select', 'afterselect', 'unselect', 'afterunselect', 'contextmenu']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_XTHUMBITEM, 'wcm.XThumbItem');
Ext.apply(wcm.XThumbItem, {
	get : function(item, extra){//for cache
		return new wcm.XThumbItem(item, extra);
	}
});

Ext.extend(wcm.XThumbItem, wcm.CMSObj, {
	isCMSObjType : function(){
		return false;
	},
	buildContext : function(item){
		if(item == null) return null;
		var context = (this._buildContext) ? this._buildContext(item) : PageContext.getContext();
		var info = Ext.applyIf(item.getItemInfo(), {item : item});
		return Ext.applyIf(info, context);
	}
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBITEM,
	contextmenu : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		wcm.ThumbList.ContextMenuHelper.dispatch(event);
		return false;
	}
});


WCMConstants.OBJ_TYPE_XTHUMBCELL = "XThumbCell";
wcm.XThumbCell = function(item, extra){
	var context = (item) ? this.buildContext(item) : null;
	this.objType = WCMConstants.OBJ_TYPE_XTHUMBCELL;
	wcm.XThumbCell.superclass.constructor.call(this, null, Ext.applyIf(context, extra));
	this.addEvents(['click', 'afterclick']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_XTHUMBCELL, 'wcm.XThumbCell');
Ext.apply(wcm.XThumbCell, {
	get : function(item, extra){//for cache
		return new wcm.XThumbCell(item, extra);
	}
});
Ext.extend(wcm.XThumbCell, wcm.CMSObj, {
	isCMSObjType : function(){
		return false;
	},
	buildContext : function(item){
		if(item == null) return null;
		var context = (this._buildContext) ? this._buildContext(item) : PageContext.getContext();
		var info = Ext.applyIf(item.getItemInfo(), {item : item});
		return Ext.applyIf(info, context);
	}
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_XTHUMBCELL,
	afterclick : function(event){
		var context = event.getContext();
		var thumbItem = context.item;
		var thumbList = thumbItem.list;
		var oCmsObjs = CMSObj.createEnumsFrom({
			objType : context.objType
		}, context);
		oCmsObjs.addElement(CMSObj.createFrom(context));
		event.objs = oCmsObjs;
		var cmd = thumbList.getCmd(context.cmd);
		if(cmd) cmd(event);
	}
});

$MsgCenter.on({
	sid : 'sys_allcmsobjs',
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
//		if(!event.isCurrWin(window)) return;
		//如果caller存在，表示来自本页面的调用，否则如果是其它页面的调用，则直接退出
		if(!arguments.callee.caller)return;
		var list = event.getContext().list;
		if(list) list.clearEditedItems();
		PageContext.event = event;
		var objs = event.getObjs();
		if(wcm.PageOper)wcm.PageOper.render(event);
	}
});

//init thumb list key provider.
Event.observe(window, 'load', function(){
	if(!PageContext.keyProvider)return;
	var myThumbList = (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;
	if(!myThumbList) return;
	Ext.apply(PageContext.keyProvider, {
		keyA : function(event){
			myThumbList.toggleAll();
			try{
				window.focus();
			}catch(err){}
		}
	});
	Event.observe(document, 'keydown', function(event){
		var event = event || window.event;
		var eTarget = Event.element(event);
		var bIsTextInput = true;
		if(eTarget != null){
			bIsTextInput = (eTarget.nodeName.toUpperCase() == 'INPUT' && eTarget.type != 'checkbox')
				||(eTarget.nodeName.toUpperCase() == 'TEXTAREA')
				||(eTarget.nodeName.toUpperCase() == 'SELECT');
		}
		if(bIsTextInput)return;
		if(event.ctrlKey || event.altKey)return;
		switch(event.keyCode){
			case Event.KEY_UP:
				return myThumbList.selectAbove();
			case Event.KEY_DOWN:
				return myThumbList.selectBelow();
			case Event.KEY_LEFT:
				return myThumbList.selectBefore();
			case Event.KEY_RIGHT:
				return myThumbList.selectAfter();
			case Event.KEY_RETURN:
				return myThumbList.enterThumbItem(event);
			case Event.KEY_F2:
				return myThumbList.editThumbItem(event);
		}
	});
});

if(Ext.isIE6){
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afterselect : function(event){
			if(event.from()!=wcm.getMyFlag())return;
			if(!arguments.callee.caller)return;
			var dom = $('wcm_table_grid');
			if(!dom) return;
			Element.addClassName(dom, 'fix-ie6-redraw');
			Element.removeClassName(dom, 'fix-ie6-redraw');
		}
	});
}

//contextmenu
wcm.ThumbList.ContextMenuHelper = {
	findCMEl : function(dom){
		while(dom && dom.tagName != 'BODY'){
			if(dom.getAttribute("contextmenu")) return dom;
			dom = dom.parentNode;
		}
		return null;
	},
	init : function(event){
		var context = event.getContext();
		this.obj = event.getObj();
		this.extEvent = context.get('extEvent');
		this.browserEvent = this.extEvent.browserEvent;
		this.targetElement = context.get('targetElement');
		this.cmEl = this.findCMEl(this.targetElement);
		this.item = context.get('item');
		if(this.item){
			var list = this.item.list;
			this.items = list.getItemInfos(list.get().itemInfo);
		}
	},
	destroy : function(){
		delete this.obj;
		delete this.extEvent;
		delete this.browserEvent;
		delete this.targetElement;
		delete this.cmE1;
		delete this.item;
		delete this.items;
	},
	dispatch : function(event){
		this.init(event);
		if(this.browserEvent.ctrlKey && this.items.length > 0){
			this.more(event);
		}else if(this.cmEl == null){
			this.none(event);
		}else{
			this.one(event);
		}
		this.destroy();
	},
	none : function(event){//when click the blank area.
		var pcEvent = PageContext.event;
		var context = pcEvent.getContext();
		var relateType = PageContext.relateType || context.relateType;
		var host = context.getHost();
		var info = {
			type : relateType.toLowerCase(),
			right : host.right,
			objs : host,
			frompanel : true
		};
		var arrOpers = wcm.SysOpers.getOpersByInfo(info, pcEvent);		
		var wcmEvent = CMSObj.createEvent({objType : relateType}, PageContext.getContext());
		var context = Ext.applyIf({
			extEvent : this.extEvent,
			event : this.browserEvent,
			targetElement : this.targetElement,
			opers : arrOpers,
			wcmEvent : wcmEvent
		}, PageContext.getContext());
		$MsgCenter.$main(context).contextmenu();
	},
	one : function(event){//when click the item.
		var itemInfo = this.item.getItemInfo();
		var wcmEvent = CMSObj.createEvent(itemInfo, PageContext.getContext());
		var context = Ext.applyIf({
			extEvent : this.extEvent,
			event : this.browserEvent,
			targetElement : this.targetElement,
			wcmEvent : wcmEvent
		}, PageContext.getContext());
		$MsgCenter.$main(context).contextmenu();
	},
	more : function(event){//when ctrlkey press.
		var context = Ext.applyIf({
			extEvent : this.extEvent,
			event : this.browserEvent,
			targetElement : this.targetElement,
			wcmEvent : PageContext.event
		}, PageContext.getContext());
		$MsgCenter.$main(context).contextmenu();
	}
};
