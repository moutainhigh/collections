var bInFloatPanel = getParameter("_fromfp_")=='1';
if(!bInFloatPanel){
	var m_oCommands = {};
	var m_tmpBtn = '<input id="ipt_{1}" type="button" value="{0}"/>';
	function addCommand(_sCmdId,_sCmdHtml,_fnCmd,_oScope,_arrArgs){
		var sBtnId = 'cmd_'+_sCmdId;
		var btnCmd = document.createElement("SPAN");
		btnCmd.innerHTML = String.format(m_tmpBtn, _sCmdHtml, _sCmdId);
		btnCmd.className = 'command_btn';
		btnCmd.id = sBtnId;
		m_oCommands[sBtnId] = {
			fn : _fnCmd,
			scope : _oScope,
			args : _arrArgs
		};
		$('buttons_container').appendChild(btnCmd);
	}
	function addCloseCommand(_sCmdHtml){
		addCommand("_close_", _sCmdHtml||"取消", '_close_');
	}
	Event.observe(window, 'load', function(){
		var divBtns = document.createElement('DIV');
		divBtns.id = 'buttons_container';
		divBtns.align = 'center';
		document.body.appendChild(divBtns);
		if(window.m_fpCfg && window.m_fpCfg.m_arrCommands){
			var bHasClose = (window.m_fpCfg.withclose===false) || false;
			for(var i=0,n=window.m_fpCfg.m_arrCommands.length;i<n;i++){
				var o = window.m_fpCfg.m_arrCommands[i];
				if(o.cmd=='close'){
					bHasClose = true;
					addCloseCommand(o.name);
					continue;
				}
				addCommand(o.cmd, o.name, o.cmd, o.scope, o.args);
			}
			if(!bHasClose){
				addCloseCommand();
			}
		}else{
			addCommand('onOk', '确定', 'onOk', null);
			addCloseCommand();
		}
		var bodyStyle = document.body.style;
		bodyStyle.paddingLeft = bodyStyle.paddingRight
			= (document.body.offsetWidth - m_fpCfg.size[0])/2;
		bodyStyle.paddingTop = bodyStyle.paddingBottom
			= (document.body.offsetHeight - m_fpCfg.size[1])/2;
	});
	Event.observe(window, 'unload', function(){
		document.onclick = null;
		document.onkeydown = null;
	});
	function findCommand(target){
		while(target!=null && target.tagName!='BODY'){
			if(target.className=='command_btn')return target;
			target = target.parentNode;
		}
		return null;
	}
	document.onclick = function(event){
		event = event || window.event;
		var target = event.target || event.srcElement;
		target = findCommand(target);
		if(target==null)return;
		if(target.disabled)return false;
		var cmdId = target.id;
		var cmdInfo = m_oCommands[cmdId];
		if(cmdInfo.fn=='_close_'){
			window.opener = null;
			window.close();
			return;
		}
		var retVal = window[cmdInfo.fn].apply(cmdInfo.scope, []);
		if(retVal!==false){
			window.opener = null;
			window.close();
		}
		return false;
	};
}
function setFPCmdDisable(_cmdName, disable, hide){
	if(parent.setCmdDisable){
		return parent.setCmdDisable(_cmdName, disable, hide);
	}
	var sBtnId = 'ipt_'+_cmdName;
	var oButton = document.getElementById(sBtnId);
	if(!oButton)return;
	oButton.disabled = disable;
	oButton.parentNode.style.display = hide===true ? 'none' : '';
}
function notifyFPCallback(){
	if(Ext.isFunction(parent.m_winFromCallback)){
		window.__notifyFPResult = parent.m_winFromCallback.apply(null, arguments);
		return;
	}
	window.__notifyFPResult = true;
}
var FloatPanel = {
	close : function(){
		closeWindow();
	},
	hide : function(){
		Element.hide(parent.window.frameElement);
	},
	addCommand : function(){
	},
	setTitle : function(_title){
		if(parent.replaceTitleWith){
			return parent.replaceTitleWith(_title);
		}
		window.title = _title;
	},
	disableCommand : setFPCmdDisable,
	dialogArguments : (function(){
		return parent.m_winDialogArguments || {};
	})()
}
function closeWindow(){
	if(window.__notifyFPResult===false)return;
	if(Ext.isFunction(parent.closeMe)){
		parent.closeMe();
	}
	else if(window.frameElement){
		window.frameElement.src = Ext.isSecure?SSL_SECURE_URL:'';
	}else{
		window.opener = null;
		window.close();
	}
}
Event.observe(document, 'keydown', function(event){
	event = event || window.event;
	if(event.keyCode==27)FloatPanel.close();
});
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
