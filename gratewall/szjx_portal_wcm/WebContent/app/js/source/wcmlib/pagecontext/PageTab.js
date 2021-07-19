Ext.ns('wcm.PageTab');
//页面标签
(function(){
	var myTemplate = {
		outer : [
			'<table cellspacing=0 cellpadding=0 border=0 id="pagetab" class="pagetab">',
				'<tbody>',
					'<tr valign=middle align=center id="pagetab_container">',
						'{0}',
						'<td class="tab_item_more {1}" id="pagetab_more">&nbsp;&nbsp;</td>',
					'</tr>',
				'</tbody>',
			'</table>'
		].join(''),
		inner : [
			'<td class="tab_item {0}" id="tab_{1}" _type="{1}" _url={2}><span title="{3}">{3}</span></td>'
		].join('')
	};
	function Tab(tab, tabs){
		Ext.apply(this, tab);
		this.tabs = tabs;
		this.setOrder = function(order){
			tab.order = order;
			items = this.tabs.items;
			var currItem = null;
			for (var i=0,n=items.length; i<n; i++){
				if(items[i].type.equalsI(this.type)){
					currItem = items[i];
					items[i] = null;
					break;
				}
			}
			if(currItem == null)return this;
			items = this.tabs.items = items.compact();
			items.splice(order-1, 0, currItem);
			return this;
		}
		this.hide = function(fn){
			if(!Ext.isFunction(fn)){
				fn = function(){
					return false;
				}
			}
			if(!Ext.isFunction(tab.isVisible)){
				this.isVisible = tab.isVisible = fn.bind(this);
			}
			this.isVisible = tab.isVisible = tab.isVisible.createInterceptor(fn, this);
		}
	}
	function Tabs(tabs){
		Ext.apply(this, tabs);
		this.register = this.addTab = function(infos){
			var tabs = this;
			if(Ext.isArray(infos)){
				infos.each(function(info){
					tabs.addTab(info);
				});
				return tabs;
			}
			if(tabs.hostType.toLowerCase()!=infos.hostType.toLowerCase())
				return tabs;
			var items = infos.items || {};
			if(Ext.isArray(items)){
				items.each(function(item, index){
					item = Ext.applyIf(item, infos);
					item.order = item.order || (tabs.items.length + index + 1);
					delete item.items;
					tabs.items.splice(item.order-1, 0, item);
				});
			}else{
				items = Ext.applyIf(items, infos);
				items.order = items.order || (tabs.items.length+1);
				delete items.items;
				tabs.items.splice(items.order-1, 0, items);
			}
			tabs.items = tabs.items.compact();
			return tabs;
		}
		this.get = this.getTab = function(type){
			var items = this.items;
			for (var i=0,n=items.length; i<n; i++){
				if(items[i].type.equalsI(type))
					return new Tab(items[i], this);
			}
			return new Tab({}, this);
		}
	}
	Ext.apply(wcm.PageTab, {
		tabs : {},
		init : function(info){
			if(!info.enable){
				wcm.Layout.collapseByChild('south', 'south_tabs');
				return;
			}
			info.displayNum = this._tabDisplayNums[info.hostType];
			this.info = info;
			var extInfo = Ext.apply({}, info);
			//通过记忆确定出是否全部展现标签
			extInfo.displayNum = wcm.TabManager.calDisplayNum(this._tabDisplayNums[info.hostType]);
			extInfo.tabs = (extInfo.tabs)?extInfo.tabs.compact():[];
			var sValue = this.getTabsHtml(extInfo);
			Element.update($('south_tabs'), sValue);
			this.disable(getParameter("disableTab"));
			this.bindEvents(extInfo);
		},
		disable : function(disabled){
			var sMethod = disabled ? 'addClassName' : 'removeClassName';
			Element[sMethod]('pagetab', 'disabled');
		},
		findTabItem : function(srcElement, parent){
			while(srcElement!=null && srcElement!=$(parent || 'pagetab_container')){
				if(Element.hasClassName(srcElement, 'tab_item'))return srcElement;
				srcElement = srcElement.parentNode;
			}
			return null;
		},
		findTabItemMore : function(srcElement, parent){
			while(srcElement!=null && srcElement!=$(parent || 'pagetab_container')){
				if(Element.hasClassName(srcElement, 'tab_item_more'))return srcElement;
				srcElement = srcElement.parentNode;
			}
			return null;
		},
		bindEvents : function(info){
			var inited = !!this.extInfo;
			this.extInfo = info;
			if(inited) return;
			var tabCnt = Ext.get('south_tabs');
			tabCnt.on('click', function(event, origTarget){
				if(Element.hasClassName('pagetab', 'disabled')) return;
				var target = this.findTabItem(origTarget);
				if(target){
					if(Ext.fly(target).hasClass('tab_item5_disabled') 
						|| Ext.fly(target).hasClass('tab_item4_disabled')
						|| Ext.fly(target).hasClass('tab_item_disabled'))return;
					var tabType = target.getAttribute("_type");
					var tabItem = this.extInfo.getTab(tabType);
					wcm.TabManager.exec(tabItem);
					return;
				}
				var target = this.findTabItemMore(origTarget);
				if(target){
					this.moreAction(target, event);
				}
			}, this);
		},
		moreAction : function(target, event){
			//记忆是否全部展现标签
			wcm.TabManager.rememberShowAll(!Element.hasClassName(target, 'tab_item_more_open'));
			var extTarget = Ext.fly(target);
			var bIsOpen = extTarget.hasClass('tab_item_more_open');
			var extendInfo = bIsOpen ? {} : {displayNum : wcm.TabManager.maxDisplayNum};
			var sValue = this.getTabsHtml(Ext.applyIf(extendInfo, this.info));
			extTarget.removeClass(bIsOpen?'tab_item_more_open':'tab_item_more_close');
			extTarget.addClass(!bIsOpen?'tab_item_more_open':'tab_item_more_close');
			Element.update($('south_tabs'), sValue);
		},
		getTabs : function(info){
			var tabs = wcm.TabManager.getTabs(info.hostType, true, PageContext.getContext0());
			return new Tabs(Ext.apply(info, {
				items : tabs.items
			}));
		},
		getTabsHtml : function(info){
			return String.format(myTemplate.outer, this._getInnerHtml(info),
				this._getMoreClass(info.displayNum, info.num));
		},
		_getInnerHtml : function(info){
			var result = [];
			var caller = this;
			var context = PageContext.getContext();
			var oCounter = {num : 0};
			info.items.each(function(tab, index){
				var tabDesc = Ext.kaku(tab.desc, null, context) || tab.type;
				var url = wcm.TabManager.getTabUrl(tab);
				result.push(String.format(myTemplate.inner,
						caller._getTabClass(tab, info, context, oCounter),
						tab.type,
						url,
						tabDesc));
			});
			info.num = oCounter.num;
			return result.length>0 ? result.join('') : '';
		},
		_getMoreClass : function(displayNum, num){
			if((displayNum > num && !wcm.TabManager.showAll()) || this.info.displayNum > num) return 'more_display_none';
			if(displayNum>=wcm.TabManager.maxDisplayNum)return 'tab_item_more_open';
			return 'tab_item_more_close';
		},
		_getTabClass : function(tab, info, context, oCounter){
			var rightIndex = tab.rightIndex;
			var currTabType = info.activeTabType;
			var len = Ext.kaku(tab.desc, null, context).length;
			var identify = len < 4 ? "" : (len >= 5 ? "5" : "4");
			var extraTabCls = tab.extraCls;
			if(tab.type.equalsI(currTabType)){
				oCounter.num++;
				return 'tab_item' + identify + '_active' + (extraTabCls ? " " + extraTabCls + '_active' : "");
			}
			if(oCounter.num>=info.displayNum){
				oCounter.num++;
				return 'display_none';
			}
			if(Ext.isFunction(tab.isVisible) && !tab.isVisible(context)){
				return 'display_none';
			}
			if(!wcm.AuthServer.checkRight(wcm.AuthServer.getRightValue(), rightIndex)){
				return 'tab_item' + identify + '_disabled' + (extraTabCls ? " " + extraTabCls + '_disabled' : "");
			}
			oCounter.num++;
			return 'tab_item' + identify + '_deactive' + (extraTabCls ? " " + extraTabCls + '_deactive' : "");
		}
	});
})();

Ext.apply(wcm.PageTab, {
	_tabDisplayNums : (function(){
		var result = {}, num = parent.m_CustomizeInfo ? parent.m_CustomizeInfo.sheetCount.paramValue : 7;
		result[WCMConstants.TAB_HOST_TYPE_CHANNEL] = num;
		result[WCMConstants.TAB_HOST_TYPE_WEBSITE] = num;
		result[WCMConstants.TAB_HOST_TYPE_WEBSITEROOT] = num;
		result[WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST] = num;
		result[WCMConstants.TAB_HOST_TYPE_MYMSGLIST] = num;
		result[WCMConstants.TAB_HOST_TYPE_CLASSINFO] = num;
		return result;
	})()
});

//small resolution
(function(){
	//if(screen.width > 1280 && wcm.LANG.LOCALE != 'en' ) return;
	var tabTemplate = [
		'<div class="more-tab {0}" _type="{1}" _url="{2}"><a href="#" onclick="return false">{3}</a></div>',
			'<table border="0" cellpadding="0" cellspacing="0">',
				'<tr height="23">',
					'<td class="left" width="7"></td>',
					'<td class="middle" nowrap="nowrap" valign="middle">',
						'<a href="#">{3}</a>',
					'</td>',
					'<td class="right" width="7"></td>',
				'</tr>',
			'</table>',
		'</span>'
	].join('');
	var tabTemplate = [
		'<div class="more-tab {0}" _type="{1}" _url="{2}">',
			'<a href="#" onclick="return false;">{3}</a>',
		'</div>'
	].join('');

	function findTabItem(dom){
		while(dom && dom.tagName != 'BODY'){
			if(dom.getAttribute('_type')) return dom;
			dom = dom.parentNode;
		}
		return null;
	}

	Ext.apply(wcm.PageTab, {
		initTabsMore : function(){
			if(this.oBubbler) return;			
			var dom = document.createElement('div');
			dom.id = 'more-tabs';
			Element.addClassName(dom, "more-tabs");
			document.body.appendChild(dom);
			dom.innerHTML = this.getTabsMoreHtml();
			this.initTabsMoreEvents(dom);
			this.oBubbler = new wcm.BubblePanel(dom);
		},
		initTabsMoreEvents : function(dom){
			Ext.get(dom).on('click', function(event, origTarget){
				if(Element.hasClassName('pagetab', 'disabled')) return;
				var target = findTabItem(origTarget);
				if(!target) return;
				var tabType = target.getAttribute("_type");
				var tabItem = this.extInfo.getTab(tabType);
				wcm.TabManager.exec(tabItem);
			}, this);
		},
		getTabsMoreClass : function(tab, info, context, oCounter){
			var rightIndex = tab.rightIndex;
			var currTabType = info.activeTabType;
			if(Ext.isFunction(tab.isVisible) && !tab.isVisible(context)){
				return 'display_none';
			}
			if(!wcm.AuthServer.checkRight(wcm.AuthServer.getRightValue(), rightIndex)){
				return 'tab_item_disabled';
			}
			oCounter.num++;
			if(tab.type.equalsI(currTabType)){
				return 'display_none';
			}
			return 'more-tab';
		},
		getTabsMoreHtml : function(){
			var result = [];
			var caller = this;
			var context = PageContext.getContext();
			var oCounter = {num : 0};
			var info = this.info;
			info.items.each(function(tab, index){
				var cls = caller.getTabsMoreClass(tab,  info, context, oCounter);
				if(oCounter.num <= info.displayNum) return;				
				var tabDesc = Ext.kaku(tab.desc, null, context) || tab.type;
				var url = wcm.TabManager.getTabUrl(tab);
				result.push(String.format(tabTemplate, cls, tab.type, url, tabDesc));
			});
			return result.length>0 ? result.join('') : '';
		},
		moreAction : function(target, extEvent){
			this.initTabsMore();
			var dom = document.getElementById('more-tabs');
			if(dom.innerHTML == '') return;
			this.oBubbler.bubble(null, null, function(){
				this.style.left = extEvent.getPageX() + "px";
				this.style.bottom = '30px';
			});
		}
	});
})();