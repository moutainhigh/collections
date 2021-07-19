//列表内部打开新列表
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	beforeclick : function(event){
		event.cancelBubble = true;
	},
	afterclick : function(event){
		event.cancelBubble = true;
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CELL,
	beforeclick : function(event){
		event.cancelBubble = true;
	},
	afterclick : function(event){
		event.cancelBubble = true;
	}
});
$MsgCenter.on({
	sid : 'sys_allcmsobjs_cancel',
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		event.cancelBubble = true;
	}
});
PageContext.m_CurrPage = $MsgCenter.$currPage();
//下拉提示
Ext.ns('Event');
Ext.applyIf(Event, {
	KEY_BACKSPACE:	8,
	KEY_TAB:		9,
	KEY_RETURN:		13,
	KEY_ESC:		27,
	KEY_LEFT:		37,
	KEY_UP:			38,
	KEY_RIGHT:		39,
	KEY_DOWN:		40,
	KEY_DELETE:		46,
	KEY_PGUP:		33,
	KEY_PGDN:		34,
	KEY_HOME:		36,
	KEY_END:		35
});
Ext.ns("wcm.Suggestion");
wcm.Suggestion = function(config){
	Ext.apply(this, config || {});
};
Ext.apply(wcm.Suggestion.prototype, {
	autoComplete : true,
	requestOnFocus : true,
	init : function(config){
		if(this.inited) return;
		this.inited = true;
		config = Ext.apply({"list-min-height" : '80px', "list-max-height" : '140px'}, config);
		if(!config['el']){
			alert("the [el] parameter must be set! ");
			return;
		}
		this.inputEl = $(config['el']);
		Ext.apply(this, config);
		this.initBoxEl();
		this.initEvents();
	},
	initBoxEl : function(){
		var doc = this.inputEl.ownerDocument;
		var dom = doc.createElement("div");
		dom.style.display = 'none';
		dom.setAttribute("unselectable", "on");
		Element.addClassName(dom, "sg-box");
		doc.body.appendChild(dom);
		this.boxEl = dom;
	},
	initEvents : function(){
		this.keyUpEventBind = this.keyUpEvent.bind(this);
		this.blurEventBind = this.blurEvent.bind(this);
		Event.observe(this.inputEl, 'keyup', this.keyUpEventBind);
		Event.observe(this.inputEl, 'blur', this.blurEventBind);
		if(this.requestOnFocus){
			this.focusEventBind = this.focusEvent.bind(this);
			Event.observe(this.inputEl, 'focus', this.focusEventBind);
		}
		Event.observe(this.boxEl, 'mousemove', this.mouseMoveEvent.bind(this));
		Event.observe(this.boxEl, 'click', this.clickEvent.bind(this));
		Event.observe(this.boxEl, 'mouseover', this.mouseOverEvent.bind(this));
		Event.observe(this.boxEl, 'mouseout', this.mouseOutEvent.bind(this));
		Event.observe(this.boxEl, 'selectstart', function(){return false;});
	},
	keyUpEvent : function(event){
		event = event || window.event;
		var keyCode = event.keyCode;
		switch(keyCode){
			case Event.KEY_RETURN:
				this.keyEnter(event);
				break;
			case Event.KEY_UP:
				this.keyUp(event);
				break;
			case Event.KEY_DOWN:
				this.keyDown(event);
				break;
			default:
				this.keyCommon(event);
		}
	},
	mouseOverEvent : function(event){
		this.inBoxEl = true;
	},
	mouseOutEvent : function(event){
		this.inBoxEl = false;
	},
	blurEvent : function(event){
		if(this.inBoxEl) return;
		this.hideList();
	},
	focusEvent : function(){
		this.request(this.getInputValue());
	},
	keyCommon : function(event){
		var keyCode = event.keyCode;
		if(this.getInputValue() == "" && !this.requestOnFocus){
			this.hideList();
			return;
		}
		if(keyCode == Event.KEY_LEFT || keyCode == Event.KEY_RIGHT) return;
		this.request(this.getInputValue());
		this.showList();
	},
	request : function(){
		//TODO request for get something and the addItem to this suggestion.
	},
	keyEnter : function(){
		this.hideList();
		this.execute();
	},
	keyUp : function(){
		if(!this.boxEl || !Element.visible(this.boxEl)) return;
		var item = null;
		if(this.selectedItem){
			item = Element.previous(this.selectedItem);
			var height = this.selectedItem.offsetHeight;
			var scrollTop = this.boxEl.scrollTop;
			this.boxEl.scrollTop = scrollTop - height;
		}else{
			item = Element.last(this.boxEl);
			this.boxEl.scrollTop = this.boxEl.scrollHeight;
		}
		if(!item) return;
		this.setSelectedItem(item, true);
	},
	keyDown : function(){
		if(!this.boxEl || !Element.visible(this.boxEl)) return;
		var item = null;
		if(this.selectedItem){
			item = Element.next(this.selectedItem);
			var height = this.selectedItem.offsetHeight;
			var scrollTop = this.boxEl.scrollTop;
			this.boxEl.scrollTop = scrollTop + height;
		}else{
			item = Element.first(this.boxEl);
			this.boxEl.scrollTop = 0;
		}
		if(!item) return;
		this.setSelectedItem(item, true);
	},
	mouseMoveEvent : function(event){
		event = event || window.event;
		var element = Event.element(event);
		var item = this.findItem(element);
		if(item == this.selectedItem) return;
		this.setSelectedItem(item);
	},
	findItem : function(dom){
		while(dom && dom.tagName != "BODY"){
			if(Element.hasClassName(dom, "item")){
				return dom;
			}
			dom = dom.parentNode;
		}
		return null;
	},
	clickEvent : function(event){
		event = event || window.event;
		var element = Event.element(event);
		var item = this.findItem(element);
		if(!item) return;	
		this.setSelectedItem(item, true);
		this.hideList();
		this.execute();
	},
	showList : function(){
		//set height.
		var nodes = this.boxEl.childNodes;
		if(nodes.length < 4) this.boxEl.style.height = this["list-min-height"];
		if(nodes.length > 8) this.boxEl.style.height = this["list-max-height"];
		if(Element.visible(this.boxEl)) return;
		if(!this.initPosition){
			this.initPosition = true;
			Position.clone(this.inputEl, this.boxEl, {setHeight:false, offsetTop:this.inputEl.offsetHeight});
		}else{
			Position.clone(this.inputEl, this.boxEl, {setLeft : false, setHeight:false, offsetTop:this.inputEl.offsetHeight});
		}
		Element.show(this.boxEl);
	},
	hideList : function(){
		Element.hide(this.boxEl);
	},
	setSelectedItem : function(item, bSynInputValue){
		if(this.selectedItem) Element.removeClassName(this.selectedItem, "selected");		
		if(item) Element.addClassName(item, "selected");		
		this.selectedItem = item;
		if(bSynInputValue && this.autoComplete) this.setInputValue(this.getListValue());
	},
	getList : function(){
		return this.boxEl;
	},
	getListValue : function(item){
		item = item || this.selectedItem;
		if(!item) return this.getInputValue();
		return item.getAttribute("_value") || "";
	},
	getInput : function(){
		return this.inputEl;
	},
	getInputValue : function(){
		return this.inputEl.value;
	},
	setInputValue : function(sValue){
		this.inputEl.value = sValue;
	},
	execute : function(){
		//TODO override the method.
		//alert(this.getInputValue());
	},
	setItems : function(items){
		delete this.selectedItem;
		this.setItemsHtml(this.html(items));			
		this.fix4Ie();
	},
	setItemsHtml : function(sHtml){
		delete this.selectedItem;
		Element.update(this.boxEl, sHtml);
		this.showList();
		this.fix4Ie();
	},
	addItems : function(items){
		var sHtml = this.html(items);
		new Insertion.Bottom(this.boxEl, sHtml);
		this.showList();
		this.fix4Ie();
	},
	addItem : function(item){
		var sHtml = this.html(item);
		new Insertion.Bottom(this.boxEl, sHtml);
		this.showList();
		this.fix4Ie();
	},
	isIE : function(){
		var ua = navigator.userAgent.toLowerCase();
		var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
		return isIE;
	}(),
	fix4Ie : function(){
		return; //be afraid of performance.
		if(!this.isIE) return;
		var doms = this.boxEl.getElementsByTagName("*");
		for (var i = 0; i < doms.length; i++){
			doms.setAttribute("unselectable", "on");
		}
	},
	html : function(item){
		item = Ext.Json.toUpperCase(item);
		if(Array.isArray(item)){
			var aHtml = [];
			for (var i = 0; i < item.length; i++){
				aHtml.push(this.html(item[i]));
			}
			return aHtml.join("");
		}
		var sValue = (""+item["VALUE"]).replace(/\'/g,"&#39;");
		var sLable = (""+item["LABEL"]).replace(/\'/g,"&#39;");
		return [
			"<div unselectable='on' class='item' _value='", sValue, "'>", sLable, "</div>" 
		].join("");
	},
	destroy : function(){
		delete this.inited;
		Event.stopObserving(this.inputEl, 'keyup', this.keyUpEventBind);
		delete this.keyUpEventBind;
		Event.stopObserving(this.inputEl, 'blur', this.blurEventBind);
		delete this.blurEventBind;
		if(this.requestOnFocus){
			Event.stopObserving(this.inputEl, 'focus', this.focusEventBind);
			delete this.focusEventBind;
		}
		delete this.inputEl;
		if(this.boxEl1){
			Event.stopObserving(this.boxEl);
			Element.remove(this.boxEl);
		}
		delete this.boxEl;
		delete this.inBoxEl;
	}
});
Ext.ns("wcm.ListQuery", "wcm.ListQuery.Checker");

(function(){
	var sContent = [
		'<div class="querybox">',
			'<div class="qbr">',
				'<table border=0 cellspacing=0 cellpadding=0 class="qbc">',
					'<tr>',
						'<td class="elebox">',
							'<input type="text" name="queryValue" id="queryValue" onfocus="wcm.ListQuery.focusQueryValue();" onkeydown="wcm.ListQuery.keydownQueryValue(event);">',
							'<select name="queryType" id="queryType" onchange="wcm.ListQuery.changeQueryType();">{0}</select>',
						'</td>',
						'<td class="search" onclick="wcm.ListQuery.doQuery();"><div>&nbsp;</div></td>',
					'</tr>',
				'</table>',
			'</div>',
		'</div>'
	].join("");

	var allFlag = "-1";

	Ext.apply(wcm.ListQuery, {
		/**
		 * @cfg {String} container
		 * the container of query box to render to.
		 */
		/**
		 * @cfg {Boolean} appendQueryAll
		 * whether append the query all item or not, default to false.
		 */
		/**
		 * @cfg {Boolean} autoLoad
		 * whether the query box auto loads itself or not, default to true.
		 */
		/**
		*@cfg {String} maxStrLen
		*the max length of string value. default to 100
		*/
		/**
		 * @cfg {Object} items
		 * the query items of query box.
		 *eg. {name : 'id', desc : '站点', type : 'string'}
		 */
		/**
		 * @cfg {Function} callback
		 * the callback when user clicks the search button.
		 */
		config : null,
		register : function(_config){
			var config = {maxStrLen : 100, appendQueryAll : false, autoLoad : true};
			Ext.apply(config, _config);
			if(config["appendQueryAll"]){
				config["items"].unshift({name: allFlag, desc: WCMLANG["LIST_QUERY_ALL_DESC"] || "全部", type: 'string'});
			}
			this.config = config;
			if(config["autoLoad"]){
				if(document.body){
					this.render();
				}else{
					Event.observe(window, 'load', this.render.bind(this), false);
				}
			}
			return this;
		},
		render : function(){
			var sOptHTML = "";
			var items = this.config.items;
			for (var i = 0; i < items.length; i++){
				sOptHTML += "<option value='" + items[i].name + "' title='"+ items[i].desc + "'>" +  items[i].desc + "</option>";
			}
			Element.update(this.config.container, String.format(sContent, sOptHTML));
			$('queryValue').value = this.getDefaultValue();
		},
		changeQueryType : function(){
			var eQVal = $('queryValue');
			if(eQVal.value.indexOf(WCMLANG["LIST_QUERY_INPUT_DESC"]||"..输入") >= 0) {
				eQVal.value = this.getDefaultValue();
				eQVal.style.color = 'gray';
			}
			eQVal.select();
			eQVal.focus();
		},
		keydownQueryValue : function(event){
			event = window.event || event;
			if(event.keyCode == 13){
				Event.stop(event);
				this.doQuery();
			}
		},
		focusQueryValue : function(){
			var eQVal = $('queryValue');
			eQVal.style.color = '#414141';
			eQVal.select();
		},
		getDefaultValue : function(){
			var nIndex = $('queryType').selectedIndex;
			if(nIndex < 0) return "";
			var oItem =  this.getItem(nIndex);
			return (WCMLANG["LIST_QUERY_INPUT_DESC"]||"..输入") + (oItem["name"] == allFlag ? (WCMLANG["LIST_QUERY_JSC_DESC"]||"检索词") : oItem["desc"]);
		},
		getItem : function(_index){
			return this.config["items"][_index];
		},
		getParams : function(){
			var params = {};
			var sQType = $F("queryType");
			var sQValue= $F("queryValue");
			if(this.getDefaultValue() == sQValue){
				sQValue = "";
			}
			if(sQType == allFlag){
				params["isor"] = true;
				var items = this.config["items"];
				for (var i = 0; i < items.length; i++){
					var item = items[i];
					if(item["name"] == allFlag) continue;
					if(this.valid(item).isFault) continue;
					params[item["name"]] = sQValue;
				}
			}else{
				params["isor"] = false;
				params[sQType] = sQValue;
			}
			return params;
		},
		valid : function(item){
			var sQValue = $F("queryValue").trim();
			var sType = item["type"] || '';
			sType = sType.toLowerCase();
			var checker = wcm.ListQuery.Checker;
			var result = (checker[sType]||checker['default'])(sQValue, item);
			return {isFault : !!result, msg : result}
		},
		clearLastParams : function(){
			if(!window.PageContext || !PageContext.params) return;
			var params = PageContext.params;
			var items = this.config["items"];
			for (var i = 0; i < items.length; i++){
				var item = items[i];
				delete params[item["name"]];
				delete params[item["name"].toUpperCase()];
			}
			delete params["SelectIds"];
		},
		doQuery : function(){
			//check the valid.
			var validInfo = this.valid(this.getItem($('queryType').selectedIndex));
			if(validInfo.isFault) {
				Ext.Msg.$alert(validInfo["msg"]);
				return;
			}
			//exec the callback.
			if(this.config.callback){
				this.clearLastParams();
				this.config.callback(this.getParams());
			}
		}
	});

	//wcm.ListQuery.Checker
	Ext.apply(wcm.ListQuery.Checker, {
		'default' : function(){
			return false;
		},
		"int" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(wcm.ListQuery.getDefaultValue() == sValue) return false;
			var nIntVal = parseInt(sValue, 10);
			if(!(/^-?[0-9]+\d*$/).test(sValue)) {
				return WCMLANG["LIST_QUERY_INT_MIN"] || "要求为整数！";
			}else if(nIntVal > 2147483647){
				return WCMLANG["LIST_QUERY_INT_MAX"] || '要求在-2147483648~2147483647(-2^31~2^31-1)之间的数字！';
			}
			return false;
		},
		"float" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(sValue.match(/^-?[0-9]+(.[0-9]*)?$/) == null){
				return WCMLANG["LIST_QUERY_FLOAT"] || "要求为小数！";
			}
			return false;
		},
		"double" : function(sValue){
			if(sValue.trim().length == 0) return false;
			if(sValue.match(/^-?[0-9]+(.[0-9]*)?$/) == null){
				return WCMLANG["LIST_QUERY_FLOAT"] || "要求为小数！";
			}
			return false;
		},
		"string" : function(sValue, item){
			var nDefMaxLen = wcm.ListQuery.config["maxStrLen"];
			var nItemMaxLen = parseInt(item["maxLength"], 10) || nDefMaxLen;
			var nMaxLen = Math.min(nDefMaxLen, nItemMaxLen);
			if(sValue.length > nMaxLen){
				return '<span style="width:180px;overflow-y:auto;">'+String.format("当前检索字段限制为[<b>{0}</b>]个字符长度，当前为[<b>{1}</b>]！<br><br><b>提示：</b>每个汉字长度为2。", nMaxLen, sValue.length)+'</span>';
			}
			return false;
		},
		"date" : function(sValue, item){
			var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
			if(sValue && !reg.test(sValue)){
				return '<span style="width:180px;overflow-y:auto;">当前检索字段限制为日期类型！<br><br><b>提示：</b>如yyyy-MM-dd。</span>';
			}
			return false;
		}
	});
})();
PageContext.setFilters0 = PageContext.setFilters;
PageContext.setFilters = function(filters, info){
	var info = PageContext.getCustomizeFilterInfo({displayNum : 6, filterType : 0})
	this.setFilters0(filters, info);
};
Ext.apply(PageContext, {
	contextMenuEnable : true,
	gridDraggable : true && !getParameter("doSearch"),
	getContext : function(){
		var context0 = this.getContext0();
		var bIsSearch = !!PageContext.getParameter("IsSearch");
		var bIsChannel = !!PageContext.getParameter("ChannelId");
		if(bIsSearch){
			var context = Ext.applyIf({
				isChannel : false,
				host : {
					objType : 'docSearchContext',
					objId : bIsChannel ? PageContext.getParameter("ChannelId")
										: PageContext.getParameter("SiteId"),
					right : PageContext.getParameter("RightValue"),
					isVirtual : PageContext.getParameter("IsVirtual"),
					detail : location.search.substring(1)
				}
			}, context0);
			return context;
		}
		var context = Ext.applyIf({
			isChannel : bIsChannel,
			host : {
				right : PageContext.getParameter("RightValue"),
				isVirtual : PageContext.getParameter("IsVirtual"),
				objType : bIsChannel ? WCMConstants.OBJ_TYPE_CHANNEL
										: WCMConstants.OBJ_TYPE_WEBSITE,
				objId : bIsChannel ? PageContext.getParameter("ChannelId")
										: PageContext.getParameter("SiteId")
			}
		}, context0);
		context['CanSort'] = this.CanSort;
		return context;
	},
	getCustomizeFilterInfo : function(def){
		if(!parent.m_CustomizeInfo) return def;
		var displayNum = parent.m_CustomizeInfo.listFilterSize.paramValue;
		var filterType = parent.m_CustomizeInfo.documentDefaultShow.paramValue;
		if(filterType == "normal"){
			filterType = 0;
		}
		return {displayNum : displayNum, filterType : filterType};
	},
	getParameter : function(paramName){
		paramName = paramName.toLowerCase();
		if(['channelid', 'siteid'].include(paramName)){
			return getParameter(paramName) || getParameter(paramName + 's') || '';
		}
		return getParameter.apply(null, arguments);
	},
	initDraggable : function(){
		var docGridDragDrop = new wcm.dd.GridDragDrop({
			id : 'wcm_table_grid', 
			rootId : 'grid_body',
			captureEnable:false
		});
		docGridDragDrop.addListener('dispose', function(){
			top.DragAcross = null;
			delete this.hintInSelf;
			delete this.hintInTree;
		});
		Ext.apply(docGridDragDrop, {
			_getHint : function(row){
				if(this.hintInSelf) return this.hintInSelf;
				if(!top.DragAcross){
					top.DragAcross = {};
				}
				var sCurrId = row.getObjId();
				var aSelectedIds = wcm.Grid.getRowIds(true);
				if(!aSelectedIds.include(sCurrId)) aSelectedIds.push(sCurrId);			
				Object.extend(top.DragAcross,{
					ObjectType : 605 ,
					FolderId :  row.getAttribute("channelid"),
					ObjectId : sCurrId,
					ObjectIds : aSelectedIds
				});
				if(!PageContext.CanSort){
					return wcm.LANG.DOCUMENT_PROCESS_197 || "当前文档列表不支持排序";
				}
				if(PageContext.params["ORDERBY"]){
					return wcm.LANG.DOCUMENT_PROCESS_198 || "自动排序列表不支持手动排序";
				}
				if(!this.sortable){
					return wcm.LANG.DOCUMENT_PROCESS_199 || "当前文档没有权限排序";
				}
				return String.format("[文档RecID-{0}]",sCurrId);
			},
			_isSortable : function(row){
				if(!PageContext.CanSort || PageContext.params["ORDERBY"]) return false;
				var sRight = row.dom.getAttribute("right", 2);
				//排序的权限调整为用户在当前栏目下是否有编辑文档权限
				if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 62)){
					return false;
				}
				return true;
			},
			_move : function(srcRow, iPosition, dstRow, eTargetMore){
				//iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
				if(!PageContext.CanSort&&PageContext.params["ORDERBY"]) return false;
				if(!srcRow) return;
				var bCurrTopped = srcRow.getAttribute("isTopped")=='true';
				var bTargetTopped = dstRow.getAttribute("isTopped")=='true';
				var docid = srcRow.getAttribute('docid');
				var rowId = srcRow.getAttribute('rowId');
				var targetDocId = dstRow.getAttribute('docid');
				if(bCurrTopped!=bTargetTopped){//当前行与目标行,一行是置顶,一行是非置顶
					var bFixedUp = true;
					if(iPosition==0){//有前一行,插入到目标行之后的情况
						if(!bCurrTopped&&eTargetMore!=null){//非置顶行判断前一行和后一行
							//若下一行为非置顶行,则不交叉
							//反之,则交叉
							var bTargetMoreTopped = eTargetMore.getAttribute("isTopped")=='true';
							if(!bTargetMoreTopped){
								//用后一行的数据,表示插入到它之前
								targetDocId = eTargetMore.getAttribute('docid');
								iPosition = (iPosition==0)?1:0;
								bFixedUp = false;
							}
						}
						else if(!bCurrTopped&&eTargetMore==null){//非置顶行上一行为置顶行,下一行无
							//相当于至少有n-1个置顶行,而被拖动的那行是非置顶行
							//所以非置顶行本身未移动,此种情况其实不需要考虑
							//考虑的话不计为交叉
							bFixedUp = false;
						}
						else if(bCurrTopped){//置顶行的上一行为非置顶行,必然交叉
							bFixedUp = true;
						}
					}
					else{//无前一行,但有后一行,插入到目标行之前的情况
						if(!bCurrTopped){//当前行非置顶,插在置顶行之前必然交叉
							bFixedUp = true;
						}
						else{//当前行置顶,插在非置顶行之前必然不交叉
							//但此种情况可以不考虑
							//当前置顶行拖动后前无置顶行(在第一行),后无置顶行
							//可以知道当前只有一个置顶行,且没有交换位置
							//不应该到这里来
							bFixedUp = false;
							//若来到这里就不能按置顶交换顺序的方式处理了
							bCurrTopped = false;
						}
					}
					if(bFixedUp){
						Ext.Msg.$timeAlert(wcm.LANG.DOCUMENT_PROCESS_201 || '置顶文档与非置顶文档间不能交叉排序.',5);
						return false;
					}
				}
				if(!confirm(wcm.LANG.DOCUMENT_PROCESS_202 || '您确定要调整文档的顺序?')) return false;
				if(bCurrTopped){
					var oPostData = {
						"TopFlag" : 3,/*表示不改变置顶设置*/
						"ChannelId" : PageContext.getParameter("channelid"),
						"DocumentId" : docid,
						"Position" : iPosition,
						"TargetDocumentId" : targetDocId
					}
					BasicDataHelper.call('wcm6_viewdocument', 'setTopDocument', oPostData, true, function(){
						PageContext.updateCurrRows(rowId);
					});
				}
				else{
					var oPostData = {
						FromDocId:docid,
						ToDocId:targetDocId,
						position:iPosition,
						channelid: PageContext.getParameter("channelid")
					};
					wcm.domain.ChnlDocMgr['saveorder'](oPostData, {
						onSuccess : function(){
							PageContext.updateCurrRows(rowId);
						},
						onFailure : function(trans,json){
							wcm.FaultDialog.show({
								code		: $v(json,'fault.code'),
								message		: $v(json,'fault.message'),
								detail		: $v(json,'fault.detail'),
								suggestion  : $v(json,'fault.suggestion')
							}, wcm.LANG.DOCUMENT_PROCESS_51 || '与服务器交互时出现错误' , function(){
								PageContext.updateCurrRows();
							});
						}
					});
				}
				return true;
			}
		});

		var accrossDragger = new wcm.dd.AccrossFrameDragDrop(docGridDragDrop);
		Ext.apply(accrossDragger, {
			getWinInfos : function(){
				if(!top.$('nav_tree'))return [];
				return [
					{win : top}, 
					{
						win : top.$('nav_tree').contentWindow,
						enterMe : function(event, target, opt){
							if(!this.dd.hintInTree){
								if(top.DragAcross.ObjectIds.length>1){
									this.dd.hintInTree = String.format("[引用多篇文档:{0}]",top.DragAcross.ObjectIds);
								}
								else{
									this.dd.hintInTree = String.format("[文档RecID-{0}]",top.DragAcross.ObjectId);
								}
							}
							this.dd.dragEl.innerHTML = this.dd.hintInTree;
						},
						leaveMe : function(event, target, opt){
							this.dd.dragEl.innerHTML = this.dd._getHint(this.dd.row);
						},
						endDrag : function(event, target, opt){
							if(!top.DragAcross || !top.DragAcross.TargetFolderId) return;
							var objId = top.DragAcross.ObjectId;
							BasicDataHelper.call('wcm6_viewdocument', 'quote', {
								"ObjectIds" : top.DragAcross.ObjectIds,
								"ToChannelIds" : top.DragAcross.TargetFolderId
							}, true, function(_transport,_json){
									Ext.Msg.report(_json, wcm.LANG.DOCUMENT_PROCESS_47 || '文档引用结果', function(){
										var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CHNLDOC};
										CMSObj.createFrom(info, PageContext.getContext()).afteredit();
									});
								},
								function(_transport,_json){
									$render500Err(_transport,_json);
								}
							);
						}
				}];	
			}
		});
	}
});


/*   操作面板需要执行相关函数 */
Ext.apply(PageContext, {
	//下面这个函数没用了应该
	filterDocSources : function(event, valueDom, wcmEvent){
		var oHelper = BasicDataHelper;
		var oPostData = {
			k : this.value,
			r : new Date().getTime()
		};
		var elSuggestion = $('suggestion_cnt');
		if(!elSuggestion){
			elSuggestion = document.createElement('DIV');
			elSuggestion.style.position = 'absolute';
			elSuggestion.id = 'suggestion_cnt';
			elSuggestion.style.zIndex = 1000;
			document.body.appendChild(elSuggestion);
			Element.hide(elSuggestion);
		}
		oHelper.JspRequest(WCMConstants.WCM6_PATH + 'system/filter_docsource.jsp', oPostData,
			true, function(_trans){
				var extEvent = new Ext.EventObjectImpl(event);
				var point = extEvent.getPoint();
				var x = point.x + 4;
				var y = point.y + 4;
				var elSuggestion = $('suggestion_cnt');
				var oBubbler = new wcm.BubblePanel(elSuggestion);
				Element.update('suggestion_cnt', _trans.responseText);	
				oBubbler.bubble([x,y], function(_Point){
					return [_Point[0]-this.offsetWidth,_Point[1]];
				});
				Element.show(elSuggestion);
			}
		);
	},
	//在属性面板中,构造属性保存时,需要提交的参数
	_buildParams : function(wcmEvent, actionType , valueDom){
		if(wcmEvent.length() <= 0) return; 
		if(wcmEvent.getObjs().getType() != WCMConstants.OBJ_TYPE_CHNLDOC) return;
		var obj = wcmEvent.getObjs().getAt(0);
		if(actionType=='save'){
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getDocId() : 0
				},
				ChannelId : !!PageContext.getParameter("IsSearch") ? valueDom.getAttribute("channelId", 2): PageContext.getParameter("ChannelId") || 0,
				SiteId : !!PageContext.getParameter("IsSearch") ? 0:PageContext.getParameter("SiteId") || 0
			}
		}else if(actionType=='changestatus'){
			return {
				objectIds : obj.getId(),
				StatusId : valueDom.getAttribute("_fieldValue", 2)
			};
		}
	}
});

//详细信息面板中文档来源的suggestion,利用消息机制绑定元素
var sg1 = null;
function showSuggestion(){
	setTimeout(function(){
		if(!$('DocSourceName'))return;
		sg1 = new wcm.Suggestion();
		sg1.init({
			el : 'DocSourceName',
			request : function(sValue){
				var all = [];
				BasicDataHelper.JspRequest(
				WCMConstants.WCM6_PATH + "nav_tree/source_create.jsp?SourceName=",
				{SiteId : PageContext.event.getObj().getPropertyAsInt('siteId', 0) || 0},  true,
				function(transport, json){
					var result = eval(transport.responseText.trim());
					for (var i = 0; i < result.length; i++){
						var sGroup = {};
						sGroup.value = result[i].title;
						sGroup.label = result[i].desc;
						all.push(sGroup);
					}
					var items = [];
					for (var i = 0; i < all.length; i++){
						if(all[i].label.toUpperCase().indexOf(sValue.toUpperCase()) >= 0) items.push(all[i]);
					}
					sg1.setItems(items);
				});
			}
		});
	},1000);
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CHNLDOC,
	afterselect : function(event){
		if(sg1) {
			sg1.destroy();
			sg1 = null;
		}
		if(event.length() != 1) return;
		showSuggestion();
	}
});
