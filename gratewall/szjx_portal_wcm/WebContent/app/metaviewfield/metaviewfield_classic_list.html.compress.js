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
Ext.ns("wcm.MetaViewSelector");
(function(){
	Ext.apply(wcm.MetaViewSelector, {
		selectView : function(params, fCallBack){
			wcm.CrashBoarder.get('viewInfo_Select').show({
				id	: 'viewInfo_Select',
				title : wcm.LANG.METAVIEW_WINDOW_TITLE_4 || "选择视图",
				src : "./metaview/viewinfo_select_list.html",
				width : "500px",
				height : "300px",
     			maskable:true,
				params : params,
				callback : function (args) {
					fCallBack(args);
				}
			});
					
		},
		setChannelView : function(_sId, params, fCallBack){
			var oPostData = Object.extend({ViewId : _sId || 0}, params || {});
			new com.trs.web2frame.BasicDataHelper().call('wcm61_metaview', 'setViewEmployerByChannel', oPostData, true, function(transport, json){
					(fCallBack || Ext.emptyFn)(transport, json);
			});
		}
	});
})();
//视图操作信息和Mgr定义
Ext.ns('wcm.domain.MetaViewMgr','wcm.domain.MetaViewService');
(function(){
	var m_oMgr = wcm.domain.MetaViewMgr;
	var m_sServiceId = "wcm61_metaview";
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	//传入event不合适的函数
	var addEditStepOne = function(_id, event){
		var oParams = {objectId : _id};
		var channelId = event.getContext().params['CHANNELID'];
		if(channelId){
			oParams['channelId'] = channelId;
		}
		var url = 'metaview/viewinfo_add_edit_step1.jsp?' + $toQueryStr(oParams);
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + url,
			title : wcm.LANG.METAVIEW_WINDOW_TITLE_1 || '新建/修改视图步骤1:新建或选择表',
			callback : function(_tableInfoId, _viewId){				
				CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();
				var _params = {tableInfoId : _tableInfoId , viewId : _viewId};
				wcm.domain.MetaViewService.editMultiTable(_viewId);
				//wcm.domain.MetaViewService.addEditStepTwo(_params);
			}
		});			
	};
//-------------------------------------------------------------
	//供外部和内部共同调用的方法。
	Ext.apply(wcm.domain.MetaViewService, {
		generate : function(objIds){
			ProcessBar.start(wcm.LANG.METAVIEW_PROCESSBAR_TIP_1 || "生成应用");
			getHelper().call(m_sServiceId, 'createViewRelation', {viewIds : objIds}, true, function(transport, json){
				ProcessBar.exit();
				var isSuccess = com.trs.util.JSON.value(json, "REPORTS.IS_SUCCESS");
				if(isSuccess == true || isSuccess == 'true'){
					//var reportDailog = wcm.ReportDialog;
					//wcm.ReportDialog.show(json, wcm.LANG.METAVIEW_GENAPP_RPT || '生成应用结果');
				}
			});
		},
		addEditStepTwo : function(params,fCallBack){
			//var urlParams = "tableInfoId=" + params['tableInfoId'] + "&viewId=" + params['viewId'];
			var url = './metadbfield/metadbfield_list_select.html?' + $toQueryStr(params);
			wcm.CrashBoarder.get('crash-board').show({
					id : 'crash-board',
					title : wcm.LANG.METAVIEW_WINDOW_TITLE_2 || '新建/修改视图步骤2:新建或选择物理字段',
					src : url,
					width:'800px',
					height:'400px',
					maskable:true,
					callback : function (args) {
						(fCallBack || Ext.emptyFn)(args)
				}
			});
		},
		editMultiTable : function(_sId){
			wcm.CrashBoarder.get('editMultiTabler').show({
				id	: 'editMultiTabler',
				title : wcm.LANG.METAVIEW_WINDOW_TITLE_3 || "修改视图",
				src : "metadbtable/build_to_view.html",
				width : "800px",
				height : "500px",
				maskable:true,
				params : {viewId: _sId}
			});
		}
	});
//-------------------------------------------------------------

	Ext.apply(wcm.domain.MetaViewMgr, {
		'delete' : function(event){
			var _arrIds = event.getObjs().getIds();
			if(!confirm(String.format("确实要将这{0}个视图删除吗?",_arrIds.length ))){
				return;
			}
			getHelper().call(m_sServiceId, "deleteView", {objectids:_arrIds + ""}, true, function(transport, json){
				var isSuccess = $v(json, "REPORTS.IS_SUCCESS");
				if(isSuccess == 'true'){
					event.getObjs().afterdelete();
					return;
				}
				wcm.ReportDialog.show(json, wcm.LANG.METAVIEW_ALERT_3 || '视图删除结果', function(){
					event.getObjs().afterdelete();
				});				
			});
		},

		/**
		 * @param : wcm.CMSObjEvent event
		 */
		//
		edit : function(event){
			var isSingleTable = event.getObj().getPropertyAsString("isSingleTable") || true;
			if(isSingleTable =="true"||isSingleTable ==true )
				isSingleTable = true;
			else{
				isSingleTable = false;
			}
			var _sId = event.getObj().getId();
			if(isSingleTable){
				addEditStepOne(_sId, event);
			}else{
				wcm.domain.MetaViewService.editMultiTable(_sId);
			}
		},
		add : function(event){
			addEditStepOne(0, event);
		},
		'export' : function(event){
			if(event.length()==0){
				Ext.Msg.$alert(wcm.LANG.metaview_1001 || '必须选中至少一个视图!');
				return;
			}
			var oPostData = {
				ViewIds: event.getIds().join()
			};
			BasicDataHelper.call(m_sServiceId, 'exportViews', oPostData, true, function(_trans, _json){
				var sFileUrl = _trans.responseText;
				var frm = document.getElementById("ifrmDownload");
				if(frm == null) {
					frm = document.createElement('iframe');
					frm.style.height = 0;
					frm.style.width = 0;
					document.body.appendChild(frm);
				}
				sFileUrl = WCMConstants.WCM6_PATH + "file/read_file.jsp?DownName=" + sFileUrl + "&FileName=" + sFileUrl;
				frm.src = sFileUrl;
			
			}.bind(this));	
		},
		'import' : function(event){
			var context = event.getContext();
			var params = {
				OwnerId: context.OwnerId,
				OwnerType: context.OwnerType
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'metaview/metaview_import.html?' + $toQueryStr(params), 
				wcm.LANG.metaview_1002 || '视图导入', CMSObj.afteradd(event));
		},
		'setsynrule' : function(event){
			//获取到视图Id
			var nViewId = event.getObj().getId();
			FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/syn_rule_set.html?synRuleSetFrom=metaView&viewId=' + nViewId, (wcm.LANG.METAVIEWDATA_34 || '设置数据同步到WCMDocument的规则'), CMSObj.afteredit(event));
		}

	});

})();


(function(){
	var pageObjMgr = wcm.domain.MetaViewMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'add',
		type : 'MetaViewInRoot',
		desc : '新建一个视图',
		title : '新建一个视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'MetaViewInRoot',
		desc :  '导入视图',
		title : '导入视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['import'],
		quickKey : 'I'
	});
	reg({
		key : 'edit',
		type : 'MetaView',
		desc : '修改这个视图',
		title : '修改这个视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'E'
	});
	reg({
		key : 'delete',
		type : 'MetaView',
		desc : '删除这个视图',
		title : '删除这个视图...',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'export',
		type : 'MetaView',
		desc : '导出这个视图',
		title : '将当前视图以xml文件导出',
		rightIndex : -1,
		order : 3,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'setsynrule',
		type : 'MetaView',
		desc : '设置同步规则',
		title : '设置同步规则...',
		rightIndex : -1,
		order : 4,
		fn : pageObjMgr['setsynrule']
	});
	reg({
		key : 'delete',
		type : 'MetaViews',
		desc : '删除这些视图',
		title : '删除这些视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'export',
		type : 'MetaViews',
		desc : '导出这些视图',
		title : '将这些视图以xml文件导出',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});

})();
//经典列表
Ext.ns('ClassicList.cfg');
var m_sToolbarTemplate = {
	item : [
		'<table class="toolbar_item {3}" {4} id="{0}" {2}>',
		'<tr>',
			'<td style="width:16px;"><div class="toolbar_icon {0}">&nbsp;</div></td>',
			'<td class="toolbar_cont" nowrap="true">{1}</td>',
		'</tr>',
		'</table>'
	].join(''),
	sep : [
		'<table class="toolbar_sep">',
		'<tr>',
			'<td>&nbsp;</td>',
		'</tr>',
		'</table>'
	].join(''),
	main : [
		'<table cellspacing="0" cellpadding="0" border="0" valign="top" class="list_table">',
			'<tr>',
				'<td height="26" class="head_td">',
				'<span>{0}：</span>',
				'<span id="literator_path"></span></td>',
				'<td class="head_td">{2}</td>',
			'</tr>',
		'</table>',
		'<table cellspacing="0" cellpadding="0" border="0" class="toolbar">',
			'<tr>',
				'<td height="32" valign="center" id="toolbar_container" style="visibility:hidden;">{1}</td>',
				'<td id="query_box"></td>',
				'<td width="20">&nbsp;</td>',
			'</tr>',
		'</table>'
	].join(''),
	morebtn : [
		'<span class="toolbar_more_btn" style="display:{1};" id="toolbar_more_btn">&nbsp;</span>',	
		'<div id="more_toolbar" class="more_toolbar" style="display:none;">{0}</div>'
	].join('')
};

function _mergeRight(objs){
	var arrRight = [];
	for (var i=0,n=objs.length(); i<n; i++){
		arrRight.push(objs.getAt(i).right);
	}
	return wcm.AuthServer.mergeRights(arrRight);
}

function getRight(item, event){
	if(!event) return wcm.AuthServer.getRightValue();
	var host = event.getContext().getHost();
	if(item.isHost) return host.right;
	var objs = event.getObjs();
	if(objs.length()==0) return host.right;
	if(objs.length()>1) return _mergeRight(objs);
	return objs.getAt(0).right;
}

function toToolbarHtml(cfg){
	var result = [];
	var moreResult = [];
	var json = {};
	var displayNum = window.screen.width <= 1024 ? 4 : 8;
	var nSep = 0;
	for(var i=0, n=cfg.length; i<n; i++){
		var item = cfg[i];
		if(item=='/'){
			result.push(m_sToolbarTemplate.sep);
			nSep ++;
			continue;
		}
		var bisvisible = true;
		if(item.isVisible){
			bisvisible = item.isVisible(PageContext.event);
		}
		if(!bisvisible){
			nSep ++;
			continue;
		}
		var event = PageContext.event;
		var right = getRight(item, event);
		json[item.id.toLowerCase()] = item;
		var bDisabled = (item.isDisabled && item.isDisabled(PageContext.event)) ||
			(item.rightIndex != undefined && !wcm.AuthServer.checkRight(right, item.rightIndex));
		item.disabled = bDisabled;
		if(i-nSep<displayNum){
			result.push(String.format(m_sToolbarTemplate.item,
					item.id.toLowerCase(), item.name, (item.desc?("title='"+item.desc+"'"):""),
					bDisabled ? 'toolbar_item_disabled' : '',
					bDisabled ? 'disabled' : ''
				)
			);
		}else{
			moreResult.push(String.format(m_sToolbarTemplate.item,
					item.id.toLowerCase(), item.name, (item.desc?("title='"+item.desc+"'"):""),
					bDisabled ? 'toolbar_item_disabled' : '',
					bDisabled ? 'disabled' : ''
				)
			);
		}
	}
	result.push(String.format(m_sToolbarTemplate.morebtn,
			moreResult.join(''), moreResult.length > 0 ? '' : 'none'));
	return {
		html : result.join(''),
		json : json
	};
}
function refreshToolbars(cfg){
	var result = toToolbarHtml(cfg);
	$('toolbar_container').style.visibility = 'visible';
	$('toolbar_container').innerHTML = result.html;
}
function doClassicList(){
	var loaded = false;
	ClassicList.makeLoad = ClassicList.autoLoad = function(){
		if(loaded)return;
		loaded = true;
		var arrToolbarCfg = ClassicList.cfg.toolbar || [];
		var result = toToolbarHtml(arrToolbarCfg);
		$('classic_cnt').innerHTML = String.format(m_sToolbarTemplate.main,
			ClassicList.cfg.listTitle || "",
			result.html,
			ClassicList.cfg.path || ""
		);
		function findTarget(target){
			while(target!=null && target.tagName!='BODY'){
				if(Element.hasClassName(target, 'toolbar_item'))return target;
				target = target.parentNode;
			}
			return null;
		}
		function clickToolbarMoreBtn(event, target){
			var p = event.getPoint();
			var x = p.x + 4;
			var y = p.y + 4;
			var bubblePanel = new wcm.BubblePanel($('more_toolbar'));
			bubblePanel.bubble([x,y], function(_Point){
				return [_Point[0], _Point[1]];
			});
		}
		Ext.get('classic_cnt').on('click', function(event, target){
			if(target.id == 'toolbar_more_btn'){
				clickToolbarMoreBtn.apply(this, arguments);
				return;
			}
			var target = findTarget(target);
			if(target==null || target.id==null)return;
			var toolbarItem = result.json[target.id];
			if(toolbarItem==null || !toolbarItem.fn)return;
			if(toolbarItem.disabled)return;
			toolbarItem.fn.call(null, PageContext.event, target);
		});
	}
}
doClassicList();
Event.observe(window, 'load', ClassicList.autoLoad);
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		PageContext.event = event;
		try{
			refreshToolbars(ClassicList.cfg.toolbar);
		}catch(err){
		}
	}
});
