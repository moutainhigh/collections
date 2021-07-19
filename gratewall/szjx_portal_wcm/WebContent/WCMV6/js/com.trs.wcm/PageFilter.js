$package('com.trs.wcm');
var _PAGEFILTER_ = com.trs.wcm.PageFilter = Class.create("wcm.PageFilter");
Object.extend(_PAGEFILTER_,{
	CSS_PAGEFILTER : 'pagefilter',
	CSS_PAGEFILTER_MORE : 'pagefilter_more_container',
	CSS_PAGEFILTER_ACTIVE : 'pagefilter_active',
	CSS_PAGEFILTER_DEACTIVE : 'pagefilter_deactive',
	ATT_PAGEFILTER_TYPE : 'pagefilter_type',
	ID_MORE_CONTAINER : 'more_pagefilter',
	ID_MORE_BTN : 'pagefilter_more_btn'
});
com.trs.wcm.PageFilter.prototype = {
	initialize: function(_sPageFilterId){
		this.pageFilterId = _sPageFilterId;
	},
	_loadData : function(_sFilterType){
	},
	handleDisplay : function(_eFilters,_iNum){
		var eFilters = _eFilters;
		if(!isNaN(_iNum)&&eFilters.length>_iNum){
			var eDiv = $(_PAGEFILTER_['ID_MORE_CONTAINER']);
			if(!eDiv){
				eDiv = document.createElement("DIV");
				eDiv.id = _PAGEFILTER_['ID_MORE_CONTAINER'];
				eDiv.className =  _PAGEFILTER_['CSS_PAGEFILTER_MORE'];
				document.body.appendChild(eDiv);
			}
			Element.hide(eDiv);
			$removeChilds(eDiv);
			for(var i=_iNum;i<eFilters.length;i++){
				eDiv.appendChild(eFilters[i]);
			}
			var bubblePanel = new com.trs.wcm.BubblePanel(eDiv);
			this.arrEvOb.push($(_PAGEFILTER_['ID_MORE_BTN']));
			Event.observe($(_PAGEFILTER_['ID_MORE_BTN']),'click',function(length, iNum, event){
				event = window.event || event;
				var x = Event.pointerX(event)+4;
				var y = Event.pointerY(event)+4;
				bubblePanel.bubble([x,y],function(_Point){
					if(length <= iNum*2){
						return [_Point[0]-this.offsetWidth,_Point[1]];
					}
					return [_Point[0],_Point[1]];
				});
			}.bind(this, eFilters.length, _iNum));
		}
		else{
			Element.hide($(_PAGEFILTER_['ID_MORE_BTN']));
		}
		delete _eFilters;
	},
	_QuickGetFilters : function(){
		var aFilters = [];
		if($('pagefilter_container')){
			var eTmpRows = $('pagefilter_container').childNodes;
			for(var i=0;i<eTmpRows.length;i++){
				if(eTmpRows[i].tagName&&Element.hasClassName(eTmpRows[i],_PAGEFILTER_['CSS_PAGEFILTER'])){
					aFilters.push(eTmpRows[i]);
				}
			}
		}
		if(aFilters.length==0){
			aFilters = document.getElementsByClassName(_PAGEFILTER_['CSS_PAGEFILTER'],$(this.pageFilterId));
		}
		return aFilters;
	},
	bindEvents : function(_iNum){
		var elPageFilter = $(this.pageFilterId);
		if(!elPageFilter){
			alert('指定的PageFilter[id="'+this.pageFilterId+'"]不存在');
			return;
		}
		if(this.arrEvOb){
			this.arrEvOb.each(function(element){Event.stopAllObserving(element);});
		}
		this.arrEvOb = [];
		var eFilters = this._QuickGetFilters();
		this.handleDisplay(eFilters,_iNum);
		$(this.pageFilterId).style.display = '';
		for(var i=0;i<eFilters.length;i++){
			var element = eFilters[i];
			if(Element.hasClassName(element,_PAGEFILTER_['CSS_PAGEFILTER_ACTIVE'])){
				this.activeFilter = element;
			}
			Event.observe(element,'click',function(_element){
				this.selectFilter(_element);
				this._loadData(this.getCurrFilterType());
				delete _element;
			}.bind(this,element));
			this.arrEvOb.push(element);
		}
		delete eFilters;
	},
	filterClass : function(_bDefault){
		if(_bDefault=='true'||_bDefault==true){
			return _PAGEFILTER_['CSS_PAGEFILTER_ACTIVE'];
		}
		return _PAGEFILTER_['CSS_PAGEFILTER_DEACTIVE'];
	},
	getCurrFilterType : function(){
		if(this.activeFilter)return this.activeFilter.getAttribute(_PAGEFILTER_['ATT_PAGEFILTER_TYPE']);
	},
	selectFilter : function(_eFilter){
		if(this.activeFilter==_eFilter){
			return;
		}
		if(_eFilter){
			if(this.activeFilter){
				Element.removeClassName(this.activeFilter,_PAGEFILTER_['CSS_PAGEFILTER_ACTIVE']);
				Element.addClassName(this.activeFilter,_PAGEFILTER_['CSS_PAGEFILTER_DEACTIVE']);
			}
			var sFilterType = _eFilter.getAttribute(_PAGEFILTER_['ATT_PAGEFILTER_TYPE']);
			Element.addClassName(_eFilter,_PAGEFILTER_['CSS_PAGEFILTER_ACTIVE']);
			Element.removeClassName(_eFilter,_PAGEFILTER_['CSS_PAGEFILTER_DEACTIVE']);
			this.activeFilter = _eFilter;
			delete _eFilter;
		}
	},
	selectFilterByType : function(_nFilterType){
		var eFilters = this._QuickGetFilters();
		if(Element.visible(_PAGEFILTER_['ID_MORE_BTN'])){
			var eMoreFilters = document.getElementsByClassName(_PAGEFILTER_['CSS_PAGEFILTER'],_PAGEFILTER_['ID_MORE_CONTAINER']);
			eFilters = eFilters.concat(eMoreFilters);
		}
		for(var i=0;i<eFilters.length;i++){
			var eFilter = eFilters[i];
			var sFilterType = eFilter.getAttribute(_PAGEFILTER_['ATT_PAGEFILTER_TYPE']);
			if(_nFilterType==sFilterType){
				this.selectFilter(eFilter);
				return;
			}
		}
		if(eFilters.length>0){
			this.selectFilter(eFilters[0])
		}
	}
};
