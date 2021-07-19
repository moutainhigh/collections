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
Ext.apply(wcm.LANG, {
	DOC_UNIT : '篇',
	DOCUMENT : '文档',
	PHOTO : '图片',
	FILTER_DOCUMENT_ALL : '全部文档',
	FILTER_NEW : '新稿',
	FILTER_CANPUB : '可发',
	FILTER_PUBED : '已发',
	FILTER_REJECTED : '已否',
	FILTER_EDITED : '已编',
	FILTER_RETURN : '返工',
	FILTER_SIGNED : '已签',
	FILTER_THINKING : '正审',
	FILTER_MY : '我创建的',
	FILTER_LAST3 : '最近三天',
	FILTER_LASTWEEK : '最近一周',
	FILTER_LASTMONTH : '最近一月',
	DOCUMENT_PROCESS_1 : '执行',
	DOCUMENT_PROCESS_2 : '未选中任何{0}状态',
	DOCUMENT_PROCESS_3 : '个',
	DOCUMENT_PROCESS_4 : '文档版本',
	DOCUMENT_PROCESS_5 : "成功恢复文档[{0}]版本[版本号={1}]!",
	DOCUMENT_PROCESS_6 : 'HTML编辑器尚未加载完成，请稍后再试.',
	DOCUMENT_PROCESS_7 : '切换到纯文本，当前文档中的字体、格式等信息都将丢失，是否确认切换？',
	DOCUMENT_PROCESS_8 : '正文',
	DOCUMENT_PROCESS_9 : '链接',
	DOCUMENT_PROCESS_10 : '链接地址不合法！正确格式为：http(s)|ftp://...',
	DOCUMENT_PROCESS_11 : '链接地址长度不能超过100.',
	DOCUMENT_PROCESS_12 : '文件',
	DOCUMENT_PROCESS_13 : '{0}内容不能为空.',
	DOCUMENT_PROCESS_14 : '文档另存到栏目...',
	DOCUMENT_PROCESS_15 : '文档引用到栏目...',
	DOCUMENT_PROCESS_16 : '附件管理',
	DOCUMENT_PROCESS_17 : '相关文档管理',
	DOCUMENT_PROCESS_18 : '标题维护-简易编辑器',
	DOCUMENT_PROCESS_19 : '摘要维护-简易编辑器',
	DOCUMENT_PROCESS_20 : "预览",
	DOCUMENT_PROCESS_21 : "保存并关闭",
	DOCUMENT_PROCESS_22 : "保存并新建",
	DOCUMENT_PROCESS_23 : "发布并新建",
	DOCUMENT_PROCESS_24 : "保存并发布",
	DOCUMENT_PROCESS_25 : "关闭",
	DOCUMENT_PROCESS_26 : '保存',
	DOCUMENT_PROCESS_27 : '输入的URL地址不符合规范"(http|ftp|https|file)://..".',
	DOCUMENT_PROCESS_28 : 'SimpleEditPanel控件上未指定属性"grid_rowid"',
	DOCUMENT_PROCESS_29 : 'SimpleEditPanel控件上未指定属性"_fieldName"',
	DOCUMENT_PROCESS_30 : 'SimpleEditPanel控件上未指定属性"appendix_type"',
	DOCUMENT_PROCESS_31 : '确定',
	DOCUMENT_PROCESS_32 : '请选择当前文档要复制到的目标栏目!',
	DOCUMENT_PROCESS_33 : '执行进度，请稍候...',
	DOCUMENT_PROCESS_34 : '提交数据',
	DOCUMENT_PROCESS_35 : '成功执行完成',
	DOCUMENT_PROCESS_36 : '文档复制结果',
	DOCUMENT_PROCESS_37 : '导出',
	DOCUMENT_PROCESS_38 : "请输入一个整数.",
	DOCUMENT_PROCESS_39 : '创建',
	DOCUMENT_PROCESS_40 : '请选择当前文档要引用到的目标栏目!',
	DOCUMENT_PROCESS_41 : '导入',
	DOCUMENT_PROCESS_42 : '文档导入结果',
	DOCUMENT_PROCESS_43 : '尚未选择映射文件.',
	DOCUMENT_PROCESS_44 : '未选择Xsl文件.',
	DOCUMENT_PROCESS_45 : '管理TRS映射文件',
	DOCUMENT_PROCESS_46 : '关闭',
	DOCUMENT_PROCESS_47 : '文档引用结果',
	DOCUMENT_PROCESS_48 : '正在上传文件:',
	DOCUMENT_PROCESS_49 : '请选择文档的所属栏目!',
	DOCUMENT_PROCESS_50 : '请选择要批量导入Office文档的所属栏目!',
	DOCUMENT_PROCESS_51 : '与服务器交互时出现错误',
	DOCUMENT_PROCESS_52 : '请选择当前文档要移动到的目标栏目!',
	DOCUMENT_PROCESS_53 : '不能将当前文档从当前栏目移动到自身!',
	DOCUMENT_PROCESS_54 : '文档移动结果',
	DOCUMENT_PROCESS_55 : '文件名输入为空!',
	DOCUMENT_PROCESS_56 : '编辑映射规则',
	DOCUMENT_PROCESS_57 : '您确定要删除这个映射文件？',
	DOCUMENT_PROCESS_58 : '映射关系',
	DOCUMENT_PROCESS_59 : '请选择数据库字段.',
	DOCUMENT_PROCESS_60 : "请指定TRS库字段名.",
	DOCUMENT_PROCESS_61 : "数据库字段 [{0}] 已经存在！",
	DOCUMENT_PROCESS_62 : "三天内",
	DOCUMENT_PROCESS_63 : "一周内",
	DOCUMENT_PROCESS_64 : "一月内",
	DOCUMENT_PROCESS_65 : "未知",
	DOCUMENT_PROCESS_66 : '是',
	DOCUMENT_PROCESS_67 : "标题包含",
	DOCUMENT_PROCESS_68 : "发稿人",
	DOCUMENT_PROCESS_69 : "检索范围",
	DOCUMENT_PROCESS_70 : "含子栏目",
	DOCUMENT_PROCESS_71 : "创建时间",
	DOCUMENT_PROCESS_72 : "发布时间",
	DOCUMENT_PROCESS_73 : "文档来源",
	DOCUMENT_PROCESS_74 : "文档状态",
	DOCUMENT_PROCESS_75 : "关键词",
	DOCUMENT_PROCESS_76 : '站点文档操作任务',
	DOCUMENT_PROCESS_77 : '栏目文档操作任务',
	DOCUMENT_PROCESS_78 : '文档操作任务',
	DOCUMENT_PROCESS_79 : '检索结果操作任务',
	DOCUMENT_PROCESS_80 : '检索条件详细信息',
	DOCUMENT_PROCESS_81 : '选择要新建文档的栏目',
	DOCUMENT_PROCESS_82 : '文档-智能创建Office文档到栏目[{0}]',
	DOCUMENT_PROCESS_83 : '文档-导入文档到栏目[{0}]',
	DOCUMENT_PROCESS_84 : '文档-批量导入Office文档到栏目[{0}]',
	DOCUMENT_PROCESS_85 : '选择要智能创建文档的栏目',
	DOCUMENT_PROCESS_86 : '选择要批量导入Office文档的栏目',
	DOCUMENT_PROCESS_87 : '文档-批量导入Office文档',
	DOCUMENT_PROCESS_88 : '选择文档导入的目标栏目',
	DOCUMENT_PROCESS_89 : '文档导入',
	DOCUMENT_PROCESS_90 : '此操作可能需要较长时间。确实要导出所有文档吗？',
	DOCUMENT_PROCESS_91 : '文档-导出所有文档',
	DOCUMENT_PROCESS_92 : '文档-改变状态',
	DOCUMENT_PROCESS_93 : '文档-文档复制到...',
	DOCUMENT_PROCESS_94 : '文档-文档移动到...',
	DOCUMENT_PROCESS_95 : '文档-文档引用到...',
	DOCUMENT_PROCESS_96 : '文档-导出文档',
	DOCUMENT_PROCESS_97 : '文档版本保存结果',
	DOCUMENT_PROCESS_98 : '文档-版本管理',
	DOCUMENT_PROCESS_99 : '-调整顺序',
	DOCUMENT_PROCESS_100 : '您确定要{0}以下文档？\n',
	DOCUMENT_PROCESS_101 : '引用',
	DOCUMENT_PROCESS_102 : '移动',
	DOCUMENT_PROCESS_103 : '复制',
	DOCUMENT_PROCESS_104 :'结果',
	DOCUMENT_PROCESS_105 : "成功恢复文档[ID={0}]为版本[版本号={1}]!",
	DOCUMENT_PROCESS_106 :'创建一篇新文档',
	DOCUMENT_PROCESS_107 :'智能创建文档',
	DOCUMENT_PROCESS_108 :'从外部导入文档',
	DOCUMENT_PROCESS_109 :'导出所有文档',
	DOCUMENT_PROCESS_110 :'批量导入Office文档',
	DOCUMENT_PROCESS_111 :'导出当前站点下的所有文档',
	DOCUMENT_PROCESS_112 :'移动所有文档到',
	DOCUMENT_PROCESS_113 :'复制所有文档到',
	DOCUMENT_PROCESS_114 :'导出当前栏目下的所有文档',
	DOCUMENT_PROCESS_115 :'修改这篇文档',
	DOCUMENT_PROCESS_116 :'预览这篇文档',
	DOCUMENT_PROCESS_117 :'预览这篇文档发布效果',
	DOCUMENT_PROCESS_118 :'发布这篇文档',
	DOCUMENT_PROCESS_119 :'发布这篇文档，生成这篇文档的细览页面以及更新相关概览页面',
	DOCUMENT_PROCESS_120 :'复制这篇文档到',
	DOCUMENT_PROCESS_121 :'引用这篇文档到',
	DOCUMENT_PROCESS_122 :'移动这篇文档到',
	DOCUMENT_PROCESS_123 :'删除文档',
	DOCUMENT_PROCESS_124 :'删除文档',
	DOCUMENT_PROCESS_125 :'改变这篇文档状态',
	DOCUMENT_PROCESS_126 :'设置这篇文档权限',
	DOCUMENT_PROCESS_127 :'仅发布这篇文档细览',
	DOCUMENT_PROCESS_128 :'仅发布这篇文档细览，仅重新生成这篇文档的细览页面',
	DOCUMENT_PROCESS_129 :'撤销发布这篇文档',
	DOCUMENT_PROCESS_130 : '撤销发布这篇文档，撤回已发布目录或页面',
	DOCUMENT_PROCESS_131 :'为这篇文档产生版本',
	DOCUMENT_PROCESS_132 :'管理这篇文档版本',
	DOCUMENT_PROCESS_133 :'管理这篇文档历史版本',
	DOCUMENT_PROCESS_134 :'分隔线',
	DOCUMENT_PROCESS_135 :'导出这篇文档',
	DOCUMENT_PROCESS_136 :'将这篇文档导出成zip文件',
	DOCUMENT_PROCESS_137 :'管理评论',
	DOCUMENT_PROCESS_138 :'管理文档的评论',
	DOCUMENT_PROCESS_139 :'调整顺序',
	DOCUMENT_PROCESS_140 :'预览这些文档',
	DOCUMENT_PROCESS_141 :'预览这些文档发布效果',
	DOCUMENT_PROCESS_142 :'发布这些文档',
	DOCUMENT_PROCESS_143 :'发布这些文档，生成这些文档的细览页面以及更新相关概览页面',
	DOCUMENT_PROCESS_144 :'复制这些文档到',
	DOCUMENT_PROCESS_145 :'移动这些文档到',
	DOCUMENT_PROCESS_146 :'引用这些文档到',
	DOCUMENT_PROCESS_147 :'改变这些文档的状态',
	DOCUMENT_PROCESS_148 :'设置这些文档的权限',
	DOCUMENT_PROCESS_149 :'仅发布这些文档细览',
	DOCUMENT_PROCESS_150 :'仅发布这些文档细览，仅重新生成这些文档的细览页面',
	DOCUMENT_PROCESS_151 :'撤销发布这些文档',
	DOCUMENT_PROCESS_152 :'撤销发布这些文档，撤回已发布目录或页面',
	DOCUMENT_PROCESS_153 :'为这些文档产生版本',
	DOCUMENT_PROCESS_154 :'导出这些文档',
	DOCUMENT_PROCESS_155 :'将当前文档导出成zip文件',
	DOCUMENT_PROCESS_156 : '映射文件名',
	DOCUMENT_PROCESS_157 : '映射文件名只支持字母和数字,长度为1-30.',
	DOCUMENT_PROCESS_158 : '新建',
	DOCUMENT_PROCESS_159 : '新建一篇文档',
	DOCUMENT_PROCESS_160 : '请选择要删除的文档',
	DOCUMENT_PROCESS_161 : '删除',
	DOCUMENT_PROCESS_162 : '删除这篇/些文档',
	DOCUMENT_PROCESS_163 : '请选择要预览的文档',
	DOCUMENT_PROCESS_164 : '预览这篇/些文档',
	DOCUMENT_PROCESS_165 : '请选择要发布的文档',
	DOCUMENT_PROCESS_166 : '快速发布',
	DOCUMENT_PROCESS_167 : '发布这篇/些文档',
	DOCUMENT_PROCESS_168 : '刷新',
	DOCUMENT_PROCESS_169 : '刷新列表',
	DOCUMENT_PROCESS_170 : '文档列表管理',
	DOCUMENT_PROCESS_171 : '请选择要移动的文档',
	DOCUMENT_PROCESS_172 : '请选择要复制的文档',
	DOCUMENT_PROCESS_173 : '请选择要引用的文档',
	DOCUMENT_PROCESS_174 : '移动这篇/些文档到',
	DOCUMENT_PROCESS_175 : '复制这篇/些文档到',
	DOCUMENT_PROCESS_176 : '引用这篇/些文档到',
	DOCUMENT_PROCESS_177 : '文档标题',
	DOCUMENT_PROCESS_178 : '序号',
	DOCUMENT_PROCESS_179 : '栏目名称',
	DOCUMENT_PROCESS_180 : '移除引用',
	DOCUMENT_PROCESS_181 : '排序',
	DOCUMENT_PROCESS_182 : "--当前文档--",
	DOCUMENT_PROCESS_183 : "上移",
	DOCUMENT_PROCESS_184 : "下移",
	DOCUMENT_PROCESS_185 : "预览文档",
	DOCUMENT_PROCESS_186 : "保存文档",
	DOCUMENT_PROCESS_187 : "扩展字段管理",
	DOCUMENT_PROCESS_188 : "至",
	DOCUMENT_PROCESS_189 : "文档-智能创建Office文档",
	DOCUMENT_PROCESS_190 : "无",
	DOCUMENT_PROCESS_191 : '自动保存于:',
	DOCUMENT_PROCESS_192 : '是否进入只读模式，点确定进入？',
	DOCUMENT_PROCESS_193 : "扩展字段[",
	DOCUMENT_PROCESS_194 : '文档版本保存结果',
	DOCUMENT_PROCESS_195 : '显示注释',
	DOCUMENT_PROCESS_196 : '隐藏注释',
	DOCUMENT_PROCESS_197 :"当前文档列表不支持排序",
	DOCUMENT_PROCESS_198 :"自动排序列表不支持手动排序",
	DOCUMENT_PROCESS_199 :"当前文档没有权限排序",
	DOCUMENT_PROCESS_200 :"[文档RecID-",
	DOCUMENT_PROCESS_201 :'置顶文档与非置顶文档间不能交叉排序.',
	DOCUMENT_PROCESS_202 :'您确定要调整文档的顺序？',
	DOCUMENT_PROCESS_203 : "[引用多篇文档:",
	DOCUMENT_PROCESS_204 : '链接型栏目不允许文档管理.',
	DOCUMENT_PROCESS_205 : '链接地址:',
	DOCUMENT_PROCESS_206 : '文档LOGO',
	DOCUMENT_PROCESS_207 : '计划撤销发布时间不能早于当前时间',
	DOCUMENT_PROCESS_208 : '系统提示信息',
	DOCUMENT_PROCESS_209 : '选择模板',
	DOCUMENT_PROCESS_210 : '插入广告位出错:',
	DOCUMENT_PROCESS_211 : '标题',
	DOCUMENT_PROCESS_212 : '发稿人',
	DOCUMENT_PROCESS_213 : '关键词',
	DOCUMENT_PROCESS_214 : '输入串长度超长',
	DOCUMENT_PROCESS_215 : '请输入合法的数字',
	DOCUMENT_PROCESS_216 : "您的IE插件已经将对话框拦截！\n",
	DOCUMENT_PROCESS_217 : "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n",
	DOCUMENT_PROCESS_218 : "给您造成不便，TRS致以深深的歉意！",
	DOCUMENT_PROCESS_219 : '抽取失败.',
	DOCUMENT_PROCESS_220 : '正文内容不能为空.',
	DOCUMENT_PROCESS_221 : '执行导入文档..',
	DOCUMENT_PROCESS_222 : '{0}-调整顺序',
	DOCUMENT_PROCESS_223 : '没有需要导出的文档.',
	DOCUMENT_PROCESS_224 : '关闭',
	DOCUMENT_PROCESS_225 : '请选择链接栏目.',
	DOCUMENT_PROCESS_226 : '链接地址长度不能超过',
	DOCUMENT_PROCESS_227 : "没有找到指定的文档版本[ID={0}]",
	DOCUMENT_PROCESS_228 : '引用文档',
	DOCUMENT_PROCESS_229 : "确定要<font color='red' style='font-size:14px;'>复制所有</font>文档么？将<font color='red' style='font-size:14px;'>不可逆转</font>！",
	DOCUMENT_PROCESS_230 : "确定要<font color='red' style='font-size:14px;'>移动所有</font>文档么？将<font color='red' style='font-size:14px;'>不可逆转</font>！",
	DOCUMENT_PROCESS_231 : "确定要<font color='red' style='font-size:14px;'>撤销发布</font>所选文档么？将<font color='red' style='font-size:14px;'>不可逆转</font>！",
	DOCUMENT_PROCESS_232 : "取消置顶",
	DOCUMENT_PROCESS_233 : "只有一个文档将被选中.确定吗？",
	DOCUMENT_PROCESS_234 : "显示子栏目的文档",
	DOCUMENT_PROCESS_235 : "隐藏子栏目的文档",
	DOCUMENT_PROCESS_236 : "跟踪文档",
	DOCUMENT_PROCESS_237 : "提取成功！",
	DOCUMENT_PROCESS_238 : "提取失败！",
	DOCUMENT_PROCESS_239 : "直接发布这篇文档",
	DOCUMENT_PROCESS_240 : '发布这篇文档,同时发布此文档的所有引用文档',
	DOCUMENT_PROCESS_241 : '直接发布这些文档',
	DOCUMENT_PROCESS_242 : '发布这些文档，同步发布这些文档所有的引用文档',
	DOCUMENT_PROCESS_243 : '改变状态',
	DOCUMENT_PROCESS_244 : '设置权限',
	DOCUMENT_PROCESS_245 : '仅发布细览',
	DOCUMENT_PROCESS_246 : '仅发布这篇/些文档细览',
	DOCUMENT_PROCESS_247 : '直接发布',
	DOCUMENT_PROCESS_248 : '撤销发布',
	DOCUMENT_PROCESS_249 : '撤销发布这篇/些文档',
	DOCUMENT_PROCESS_250 : '产生版本',
	DOCUMENT_PROCESS_251 : '为这篇/些文档产生版本',
	DOCUMENT_PROCESS_252 : '导出文档',
	DOCUMENT_PROCESS_253 : '导出这/篇些文档',
	DOCUMENT_PROCESS_254 : '直接撤销发布这篇文档',
	DOCUMENT_PROCESS_255 : '直接撤销发布这些文档',
	DOCUMENT_PROCESS_256 : '撤回当前文档的发布页面，并同步撤销文档的所有引用以及原文档发布页面',
	DOCUMENT_PROCESS_257 : '撤回这些文档的发布页面，并同步撤销文档的所有引用以及原文档发布页面',
	DOCUMENT_PROCESS_258 : "确定要<font color='red' style='font-size:14px;'>撤销发布</font>所选文档及其所有引用文档么？将<font color='red' style='font-size:14px;'>不可逆转</font>！",
	DOCUMENT_PROCESS_259 : "直接撤销发布",
	DOCUMENT_PROCESS_260 : "定时发布时间不能早于当前时间",
	DOCUMENT_PROCESS_261 : "限时置顶时间不能早于当前时间",
	DOCUMENT_PROCESS_262 : "定时发布时间不能为空",
	DOCUMENT_PROCESS_263 : "限时置顶时间不能为空",
	DOCUMENT_PROCESS_264 : "计划撤销发布时间不能为空",
	DOCUMENT_PROCESS_265 : "简易编辑器",
	DOCUMENT_PROCESS_266 : '请选择所属的分类',
	DOCUMENT_PROCESS_267 : '修改这篇文档属性',
	DOCUMENT_PROCESS_268 : '修改这些文档属性',
	DOCUMENT_PROCESS_269 : '执行复制文档..',
	DOCUMENT_PROCESS_270 : '执行移动文档..',
	DOCUMENT_PROCESS_271 : '执行导出文档..',
	DOCUMENT_PROCESS_272 : '下一步',
	DOCUMENT_PROCESS_273 : "改变这篇文档的密级",
	DOCUMENT_PROCESS_274 : "改变文档的密级",
	DOCUMENT_PROCESS_275 : '改变这篇/些文档的密级',
	DOCUMENT_PROCESS_276 : '置顶设置'
});
//版本管理对象定义
Ext.ns('wcm.DocBaks', 'wcm.DocBak');
WCMConstants.OBJ_TYPE_DOCBAK = 'DocBak';
wcm.DocBaks = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_DOCBAK;
	wcm.DocBaks.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.DocBaks, wcm.CMSObjs, {
});
wcm.DocBak = function(_config, _context){
	this.objType = WCMConstants.OBJ_TYPE_DOCBAK;
	wcm.DocBak.superclass.constructor.call(this, _config, _context);
	var arrRights = {
		//TODO type rights here
		"view" : -1
	};
	for(rightName in arrRights){
		var cameRightName = rightName.camelize();
		var nRightIndex = parseInt(arrRights[rightName], 10);
		this['can'+cameRightName] = function(){
			return wcm.AuthServer.hasRight(this.right, nRightIndex);
		}
	}
}
CMSObj.register(WCMConstants.OBJ_TYPE_DOCBAK, 'wcm.DocBak');
Ext.extend(wcm.DocBak, wcm.CMSObj, {
});
