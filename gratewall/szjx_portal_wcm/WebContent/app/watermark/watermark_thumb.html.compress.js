Ext.ns("wcm.ListQuery", "wcm.ListQuery.Checker", "wcm.ListOrder");

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
				sOptHTML += "<option value='" + items[i].name + "' title='" + items[i].desc + "'>" + items[i].desc + "</option>";
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
			if(event.keyCode == 13) {
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
				return '<span style="width:180px;overflow-y:auto;">'+String.format("当前检索字段限制为[<b>{0}</b>]个字符长度，当前为[<b>{1}</b>]！\<br><br><b>提示：</b>每个汉字长度为2。", nMaxLen, sValue.length)+'</span>';
			}
			return false;
		}
	});
})();


(function(){
	var headerTemplate = [
		'<div class="orderbox-header">',
			'<div class="left" id="{0}-header">',
				'<div class="right">',
					'<div class="center">',
						'<div class="text" id="{0}-text">' + (WCMLANG['LIST_QUERY_DEFAULTORDER'] || '默认排序') + '</div>',
					'</div>',
				'</div>',
			'</div>',
		'</div>'
	].join("");
	var bodyTemplate = '<div class="orderbox-body" id="{0}-body" style="display:none;"></div>';
	var itemTemplate = '<div class="item"><a href="#" id="{0}-item" field="{0}">{1}</a></div>';
	var tipTemplate = '<div class="tip">{0}</div>';

	Ext.apply(wcm.ListOrder, {
		activeKey : null,
		config : null,
		register : function(_config){
			var config = {appendTip : true, autoLoad : true, id : 'list-order-' + (new Date().getTime())};
			Ext.apply(config, _config);
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
			var activeKey = null;
			var config = this.config;
			Element.update(config.container, String.format(headerTemplate, config.id));
			new Insertion.Bottom(document.body, String.format(bodyTemplate, config.id));
			var aHtml = [];
			var items = config.items;
			for (var i = 0, length = items.length; i < length; i++){
				var item = items[i];
				items[item['name']] = item;
				if(item["isActive"]){
					activeKey = item["name"];
				}
				aHtml.push(String.format(itemTemplate, item['name'], item['desc']));
			}
			if(config.appendTip){
				var appendTip = Ext.isBoolean(config.appendTip) ? (WCMLANG['LIST_QUERY_NOTDEFAULT'] || '非默认排序时不保存拖动排序结果') : config.appendTip;
				aHtml.push(String.format(tipTemplate, appendTip));
			}
			Element.update(config.id + "-body", aHtml.join(""));
			if(!activeKey){
				activeKey = items[0]["name"];
			}
			this.setActive(activeKey);
			this.bindEvents();
		},
		bindEvents : function(){
			var id = this.config.id;
			Event.observe(id + "-header", 'click', this.clickHeader.bind(this));
			Event.observe(id + "-body", 'click', this.clickBody.bind(this));
		},
		clickHeader : function(event){
			event = window.event || event;
			Event.stop(event);
			var srcElement = Event.element(event);
			if(Element.hasClassName(srcElement, 'text')){
				this.setActive(this.activeKey, true);
				return;
			}
			var id = this.config.id;
			var header = $(id + "-header");
			var body = $(id + "-body");
			if(!this.bubblePanel){
				this.bubblePanel = new wcm.BubblePanel(body);
			}
			Position.clone(header, body, {setWidth:false, setHeight:false, offsetTop:header.offsetHeight});
			this.bubblePanel.bubble();
		},
		clickBody : function(event){
			event = window.event || event;
			Event.stop(event);
			var srcElement = Event.element(event);
			var fieldName = srcElement.getAttribute("field");
			if(!fieldName) return;
			this.setActive(fieldName, true);
		},
		setActive : function(sActiveKey, bExeCallBack){
			if(!sActiveKey) return;
			var activeItem = this.config.items[sActiveKey];
			if(!activeItem) return;
			if(!activeItem["order"]){
				activeItem["order"] = 'desc';
			}else if(!activeItem.isDefault){
				activeItem["order"] = activeItem["order"] == 'desc' ? 'asc' : "desc";
			}
			//handle the header style
			var dom = $(this.config.id + "-text");
			dom.className = 'text ' + (activeItem.isDefault ? "" : activeItem["order"]);
			if(this.activeKey != sActiveKey){
				Element.update(dom, activeItem["desc"]);

				//handle the body style
				if(this.activeKey){
					dom = $(this.activeKey + "-item");
					Element.removeClassName(dom.parentNode, 'active');
				}
				this.activeKey = sActiveKey;
				dom = $(this.activeKey + "-item");
				Element.addClassName(dom.parentNode, 'active');
			}
			if(bExeCallBack) this.config['callback'].call(activeItem, sActiveKey + " " + activeItem['order']);
		}
	});
})();
