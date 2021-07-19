Ext.ns('wcm.PageFilter', 'wcm.PageFilters');
//过滤器
(function(){
	var myTemplate = {
		outer : [
			'<table height="28" border="0" cellpadding="0" cellspacing="0">',
				'<tr height="28">', 
					'<td align="left" valign="center">',
						'<div height="28" class="pagefilter_container" id="pagefilter_container">',
							'{0}',
							'<span class="wcm_pointer pagefilter_more_btn" id="pagefilter_more_btn"></span>',
						'</div>',
					'</td>',
				'</tr>',
			'</table>',
			'<div id="more_pagefilter" class="pagefilter_more_container" style="display:none">{1}</div>'
		].join(''),
		inner : [
			'<span class="pagefilter {2}" pagefilter_type="{0}">',
				'<table border="0" cellpadding="0" cellspacing="0">',
					'<tr height="23">',
						'<td class="left" width="7"></td>',
						'<td class="middle" nowrap="nowrap" valign="middle">',
							'<a href="#" onclick="return false">{1}</a>',
						'</td>',
						'<td class="right" width="7"></td>',
					'</tr>',
				'</table>',
			'</span>'
		].join('')
	};
	Ext.apply(wcm.PageFilter, {
		init : function(info){
			if(!info.enable || info.items.length==0)return;
			if(!$('pageinfo'))return;
			this.filters = info;
			var minDisplayNum = Math.min(3, info.displayNum);
			info.displayNum = window.screen.width <= 1024 ? minDisplayNum : info.displayNum;
			var sValue = this._getHtml(info);
			$('pageinfo').style.display = 'none';
			Element.update($('pageinfo'), sValue);
			$('pageinfo').style.display = '';
			this.bindEvents(info);
			wcm.PageFilter.selectFilterByType(info.filterType);
			return info;
		},
		_getHtml : function(info){
			var html1 = [], html2 = [];
			var filters = info.items;
			var num = filters.length;
			for(var i=0;i<num && i<info.displayNum;i++){
				var filter = filters[i];
				html1.push(String.format(myTemplate.inner, filter.type, 
					filter.desc, this.filterClass(filter)));
			}
			for(var i=info.displayNum;i<num;i++){
				var filter = filters[i];
				html2.push(String.format(myTemplate.inner, filter.type, 
					filter.desc, this.filterClass(filter)));
			}
			return String.format(myTemplate.outer, html1.join(''), html2.join(''));
		},
		_QuickGetFilters : function(){
			var aFilters = [];
			var eTmpRows = $('pagefilter_container').childNodes;
			for(var i=0;i<eTmpRows.length;i++){
				if(eTmpRows[i].tagName && Element.hasClassName(eTmpRows[i], 'pagefilter')){
					aFilters.push(eTmpRows[i]);
				}
			}
			eTmpRows = $('more_pagefilter').childNodes;
			for(var i=0;i<eTmpRows.length;i++){
				if(eTmpRows[i].tagName && Element.hasClassName(eTmpRows[i], 'pagefilter')){
					aFilters.push(eTmpRows[i]);
				}
			}
			return aFilters;
		},
		findFilter : function(srcElement, parent){
			while(srcElement!=null && srcElement!=$(parent || 'pageinfo')){
				if(Element.hasClassName(srcElement, 'pagefilter'))return srcElement;
				srcElement = srcElement.parentNode;
			}
			return null;
		},
		fireEvent : function(){
			if(!this.filters.enable)return;
			var nFilterType = this.getCurrFilterType();
			var oPageFilter = this.filters.get(nFilterType);
			if(oPageFilter==null)return;
			if(oPageFilter.fn){
				(oPageFilter.fn)();
				return;
			}
			PageContext.loadList(Ext.apply(PageContext.params, {
				filterType : nFilterType
			}));
		},
		bindEvents : function(info){
			var elPageFilter = $('pageinfo');
			Ext.get('pageinfo').on('click', function(event, target){
				target = this.findFilter(target);
				if(!target)return;
				this.selectFilter(target);
				this.fireEvent();
			}, this);
			var filters = info.items;
			var num = filters.length;
			if(num > info.displayNum){
				Ext.get('pagefilter_more_btn').on('click', function(length, iNum, event){
					var p = event.getPoint();
					var x = p.x + 4;
					var y = p.y + 4;
					var bubblePanel = new wcm.BubblePanel($('more_pagefilter'));
					bubblePanel.bubble([x,y], function(_Point){
						if(length <= iNum*2){
							return [_Point[0]-this.offsetWidth, _Point[1]];
						}
						return [_Point[0], _Point[1]];
					});
				}.bind(this, num, info.displayNum));
				document.body.appendChild($('more_pagefilter'));
				Ext.get('more_pagefilter').on('click', function(event, target){
					target = this.findFilter(target);
					if(!target)return;
					this.selectFilter(target, 'more_pagefilter');
					this.fireEvent();
				}, this);
			}
			else{
				Element.hide($('pagefilter_more_btn'));
			}
		},
		getCurrFilterType : function(){
			if(this.activeFilter)
				return this.activeFilter.getAttribute('pagefilter_type');
		},
		selectFilter : function(_eFilter){
			if(this.activeFilter==_eFilter)
				return;
			if(_eFilter){
				if(this.activeFilter){
					Element.removeClassName(this.activeFilter, 'pagefilter_active');
					Element.addClassName(this.activeFilter, 'pagefilter_deactive');
				}
				Element.addClassName(_eFilter, 'pagefilter_active');
				Element.removeClassName(_eFilter, 'pagefilter_deactive');
				this.activeFilter = _eFilter;
			}
		},
		selectFilterByType : function(_nFilterType){
			var eFilters = this._QuickGetFilters();
			for(var i=0;i<eFilters.length;i++){
				var eFilter = eFilters[i];
				var sFilterType = eFilter.getAttribute('pagefilter_type');
				if(_nFilterType==sFilterType){
					this.selectFilter(eFilter);
					return;
				}
			}
			if(eFilters.length>0){
				this.selectFilter(eFilters[0]);
			}
		}
	});
	function Filter(filter, filters){
		filter.type = filter.type + '';
		Ext.apply(this, filter);
		this.filters = filters;
		this.setOrder = function(order){
			this.order = order;
			items = this.filters.items;
			var currItem = null;
			for (var i=0,n=items.length; i<n; i++){
				items[i].type = items[i].type + '';
				if(items[i].type.equalsI(this.type)){
					currItem = items[i];
					items[i] = null;
					break;
				}
			}
			if(currItem==null)return this;
			items = this.filters.items = items.compact();
			items.splice(order-1, 0, currItem);
			return this;
		}
	}
	function Filters(info){
		Ext.apply(this, info);
		this.items = [];
		this.register = this.addFilter = function(items){
			if(!items)return;
			var filters = this;
			if(Ext.isArray(items)){
				items.each(function(item, index){
					filters.register(item);
				});
				return filters;
			}
			items.type = items.type==null ? ('ft_'+$MsgCenter.genId()) : items.type;
			items.order = items.order || (filters.items.length + 1);
			filters.items.splice(items.order-1, 0, items);
			return filters;
		}
		this.get = this.getFilter = function(type){
			var items = this.items;
			type = type + '';
			for (var i=0,n=items.length; i<n; i++){
				items[i].type = items[i].type + '';
				if(items[i].type.equalsI(type))
					return new Filter(items[i], this);
			}
			return new Filter({}, this);
		}
	}
	Ext.apply(wcm.PageFilter, {
		filterClass : function(filter){
			if(PageContext.pageFilters.filterType==filter.type)
				return 'pagefilter_active';
			return 'pagefilter_deactive';
		}
	});
	wcm.PageFilters = Filters;
})();
