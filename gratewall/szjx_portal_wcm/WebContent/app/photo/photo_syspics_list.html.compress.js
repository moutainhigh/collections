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
//站点操作信息和Mgr定义
Ext.ns('wcm.domain.WebSiteMgr');
(function(){
	var m_nWebSiteObjType = 103;
	var m_sServiceId = 'wcm61_website';
	var m_oMgr = wcm.domain.WebSiteMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}

	function edit(event, oParams){
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'website/website_add_edit.jsp?' + $toQueryStr(oParams),
			title : wcm.LANG.WEBSITE_1||'新建/修改站点',
			callback : function(objId){
				CMSObj[oParams.objectid>0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
			}
		});
	}

	//add and edit operators.
	Ext.apply(wcm.domain.WebSiteMgr, {
		"new" : function(event){
			edit(event, {
				objectid : 0,
				sitetype : event.getContext().get("siteType") || 0
			});
		},
		edit : function(event){
			edit(event, {
				objectid : event.getObj().getId(),
				sitetype : event.getContext().get("siteType") || 0
			});
		},
		quicknew : function(event){
			FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_create.jsp', wcm.LANG.WEBSITE_2||"智能建站", function(objId){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
			});
		},
		likecopy : function(event){
			var id = event.getObj().getId();
			FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_likecopy.html?siteid=' + id, wcm.LANG.WEBSITE_3||"类似创建站点",function(objId){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
			});
		},
		setAppendixSize : function(event){
			var id = event.getObj().getId();
			wcm.CrashBoarder.get('set_appendix_size').show({
				title : wcm.LANG.website_2011 || '设置这个站点上传文档附件大小',
				src : WCMConstants.WCM6_PATH + 'website/website_set_doc_appendix_size.jsp',
				width:'550px',
				height:'200px',
				maskable:true,
				params :  {siteId : id}
			}); 
		}
	});

	//import and export operators
	function download(_sUrl){
		var frm = $('iframe4download');
		if(!frm) {
			frm = document.createElement('IFRAME');
			frm.id = "iframe4download";
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		_sUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=SITE&FileName=" + _sUrl;
		frm.src = _sUrl;
	}
	Ext.apply(wcm.domain.WebSiteMgr, {
		'import' : function(event){
			FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_import.html', wcm.LANG.WEBSITE_4||'站点导入', function(objId){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
			});
		},

		'export' : function(event){
			ProcessBar.start( wcm.LANG.WEBSITE_42||'执行站点导出..');
			var aIds = event.getObjsOrHost().getIds();
			getHelper().call(
					m_sServiceId,
					'export',
					{objectids: aIds.join(",")},
					true,
					function(_oXMLHttp, _json){
						ProcessBar.close();
						download(_oXMLHttp.responseText);
					}
			);
		},

		//wenyh@2009-06-23 添加从文本批量创建
		createFromFile : function(event){
			var nSiteType = event.getHost().getId();
			FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_create_fromfile.html?SiteType=' + nSiteType, wcm.LANG.WEBSITE_CREATE_FROMFILE||'批量创建', function(){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE,objId:0});
			});
		}
	});

	//delete operators.
	Ext.apply(wcm.domain.WebSiteMgr, {
		"delete" : function(event){
			this.recycle(event);
		},
		trash : function(event){
			this.recycle(event);
		},
		recycle : function(event){
			var aIds = event.getObjsOrHost().getIds();
			var nCount = aIds.length == 1 ? "" : aIds.length;
			Ext.Msg.confirm(
				String.format('确实要将这{0}个站点放入站点回收站吗?<br /><br /><span style="color:red;">这是危险操作！</span>',nCount),
				{ ok : function(){
								verifyPassword(function(){
										ProcessBar.start(wcm.LANG.WEBSITE_RECYCLE_PROCESSBAR||'将站点放入回收站');
										getHelper().call(
												m_sServiceId,
												'delete',
												{objectids:aIds.join(","), "drop":false},
												true,
												function(){
													ProcessBar.close();
													event.getObjsOrHost().afterdelete();
												}
										);
									} ,
									"删除站点-"
								);
				        }
				}
			);
			return false;
		}
	});

	function resetTemplates(_nFolderId, _nObjType, _fCallBack){
		getHelper().call('wcm6_template', 'impartTemplateConfig',
			{objectId: _nFolderId, ObjectType:_nObjType}, true, _fCallBack);
	}
	function synchTemplates(_nFolderId, _nObjType, childObjIds, _fCallBack){
		getHelper().call('wcm61_template', 'synTemplates',
			{objectId: _nFolderId, ObjectType:_nObjType, objectIds : childObjIds}, true, _fCallBack);
	}
	//密码校验
	/** 
     *  参数说明：
	 *          validCallback     匿名函数，密码验证通过后调用的函数
	 *          operateName    字符串，操作的名称，用来显示在弹出窗口的标题上，形如：删除站点
	**/
    function verifyPassword(validCallback ,operateName){
		wcm.CrashBoarder.get('validate-password').show({
			title : '<span style="color:red;" >'+operateName+'校验密码</span>',
			src : WCMConstants.WCM6_PATH+'include/validate_password.html',
			width :  '400px',
			height : '150px',
			callback : function(params){
				if(params=="true"){
					this.close();
					if(validCallback){
						validCallback();
					}
				}
			}  
		});								
	}
	Ext.apply(wcm.domain.WebSiteMgr, {
		synTemplates : function(event){
			var objId = event.getObj().getId();
			var params = {
				objType:m_nWebSiteObjType,
				objId:objId,
				close : 0,
				ExcludeTop : 1,
				ExcludeInfoview : 0,
				ExcludeLink : 1
			};
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'website/object_select.html',
				dialogArguments : params,
				title : wcm.LANG.WEBSITE_41||'选择栏目',
				callback : function(args){
					if(args.allChildren){
						var sHtml = wcm.LANG.WEBSITE_30||"确实要同步模板到子栏目吗?<br><span style=\'color:red;font:16px;\'>注意:<br>该操作会覆盖更改所有子栏目的模板设置!</span>";
						Ext.Msg.confirm(sHtml, {
							ok : function(){
								verifyPassword(function(){
										 FloatPanel.close();
										 resetTemplates(objId, m_nWebSiteObjType);
								},"同步模板到子栏目-");
							}
						});
					}else{
						FloatPanel.close();
						synchTemplates(objId, m_nWebSiteObjType, args.ids.join());
					}
				}
			});
		}
	});

//keywordmgr
	Ext.apply(wcm.domain.WebSiteMgr, {
		keyword : function(event){
			var aId = event.getObjsOrHost().getIds();
			var type = event.getObjsOrHost().objType;
			var nId = parseInt(aId.join(","))||event.getContext().get('siteType');
			var para = {};
			if(type.toUpperCase() =="WEBSITEROOT"||type.toUpperCase()=="WEBSITEINROOT"){
				para = {
					siteType : nId,
					siteId : 0
				}
			}else {
				para = {
					siteType : event.getContext().get('siteType'),
					siteId : nId
				}
			}
			wcm.CrashBoarder.get('DIALOG_KEYWORD_MGR').show({
				title : wcm.LANG.WEBSITE_40 || '管理关键词',
				src : WCMConstants.WCM6_PATH + 'keyword/keyword_list.html',
				width:'850px',
				height:'400px',
				maskable:true,
				params :  para
			});
		}
	});

	//preview and publish operators.
	function preview(_sId, _nObjectType){
		wcm.domain.PublishAndPreviewMgr.preview(_sId, _nObjectType);
	}

	function publish(_sIds, _nObjectType, _sPublishTypeMethod){
		wcm.domain.PublishAndPreviewMgr.publish(_sIds, _nObjectType, _sPublishTypeMethod);
	}

	Ext.apply(wcm.domain.WebSiteMgr, {
		preview : function(event){
			var aIds = event.getObjsOrHost().getIds();
			preview(aIds.join(","), m_nWebSiteObjType);
		},
		quickpublish : function(event){
			m_oMgr.homepublish(event);
		},
		homepublish : function(event){
			m_oMgr.publish(event, "soloPublish");
		},
		increasepublish : function(event){
			m_oMgr.publish(event, "increasingPublish");
		},
		completepublish : function(event){
			m_oMgr.publish(event, "fullyPublish");
		},
		updatepublish : function(event){
			m_oMgr.publish(event, "refreshPublish");
		},
		cancelpublish : function(event){
			m_oMgr.publish(event, "recallPublish");
		},
		publish : function(event, sPublishTypeMethod){
			var aIds = event.getObjsOrHost().getIds();
			publish(aIds.join(","), m_nWebSiteObjType, sPublishTypeMethod);
		}
	});

	function $openCentralWin(_sUrl, _sName){
		var _WIN_WIDTH = window.screen.availWidth;
		var _WIN_HEIGHT = window.screen.availHeight;
		var y = _WIN_HEIGHT * 0.12;
		var x = _WIN_HEIGHT * 0.17;
		var w = _WIN_WIDTH - 2 * x;
		var h = w * 0.618;

		var sFeature = 'resizable=yes,top=' + y + ',left='
				+ x + ',menubar =no,toolbar =no,width=' + w + ',height='
				+ h + ',scrollbars=yes,location =no,status=no,titlebar=no';

		var sName	= _sName || "";
		sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
		var oWin = window.open(_sUrl, sName, sFeature);
		if(oWin) oWin.focus();
	}
	Ext.apply(wcm.domain.WebSiteMgr, {
		setSiteUser : function(event){
			var id = event.getObj().getId();
			$openCentralWin(WCMConstants.WCM6_PATH + "../console/channel/siteuser_list.jsp?SiteId=" + id, "wcm61-siteuser");
		}
	});

	Ext.apply(wcm.domain.WebSiteMgr, {
		commentmgr : function(event){
			var hostId = event.getHost().getId();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			var oParams = {
				SiteId : sIds
			}
			var sUrl = WCMConstants.WCM_ROOTPATH + 'comment/comment_mgr.jsp?' + $toQueryStr(oParams);
			var sWinName = 'comment_name';
			$openMaxWin(sUrl, sWinName);
		}
	});
	Ext.apply(wcm.domain.WebSiteMgr, {
		pubstat : function(event){
			var hostId = event.getHost().getId();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			var oParams = {
				SiteId : sIds
			}
			var sUrl = WCMConstants.WCM6_PATH + '../console/stat/doccount_site_byuser.jsp?' + $toQueryStr(oParams);
			$openMaxWin(sUrl);
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.WebSiteMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'website',
		desc : wcm.LANG.WEBSITE_EDIT||'修改这个站点',
		title : wcm.LANG.WEBSITE_EDIT_TITLE||'修改站点的基本属性和发布设置等相关信息',
		rightIndex : 1,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'preview',
		type : 'website',
		desc : wcm.LANG.WEBSITE_PREVIEW||'预览这个站点',
		title : wcm.LANG.WEBSITE_PREVIEW_TITLE||'重新生成并打开这个站点的预览页面',
		rightIndex : 3,
		order : 2,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'increasepublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_ADDPUB||'增量发布这个站点',
		title : wcm.LANG.WEBSITE_ADDPUB_TITLE||'发布站点和栏目的首页，并且发布所有处于可发布状态的文档',
		rightIndex : 5,
		order : 3,
		fn : pageObjMgr['increasepublish'],
		quickKey : 'P'
	});
	reg({
		key : 'homepublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_HOMEPUB||'仅发布这个站点首页',
		title : wcm.LANG.WEBSITE_HOMEPUB_TITLE|| '只更新和发布当前站点的首页',
		rightIndex : 5,
		order : 4,
		fn : pageObjMgr['homepublish']
	});
	reg({
		key : 'synTemplates',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SYNTEMP||'同步模板到栏目',
		title : wcm.LANG.WEBSITE_SYNTEMP_TITLE||'将当前站点的模板应用到指定的栏目上',
		rightIndex : 1,
		order : 5,
		fn : pageObjMgr['synTemplates']
	});
	reg({
		key : 'seperate',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		title : "分隔线",
		rightIndex : -1,
		order : 6,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'export',
		type : 'website',
		desc : wcm.LANG.WEBSITE_EXPORT||'导出这个站点到',
		title : wcm.LANG.WEBSITE_EXPORT_TITLE ||'将当前站点内容导出成XML格式文件',
		rightIndex : 1,
		order : 7,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'seperate',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		rightIndex : -1,
		order : 8,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'completepublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_COMPUB||'完全发布这个站点',
		title : wcm.LANG.WEBSITE_COMPUB_TITLE||'重新生成这个站点的所有文件',
		rightIndex : 4,
		order : 9,
		fn : pageObjMgr['completepublish']
	});
	reg({
		key : 'updatepublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_UPDATEPUB||'更新发布这个站点',
		title : wcm.LANG.WEBSITE_UPDATEPUB_TITLE||'更新当前站点和相关栏目的首页',
		rightIndex : 5,
		order : 10,
		fn : pageObjMgr['updatepublish']
	});
	/*reg({
		key : 'cancelpublish',
		type : 'website',
		desc : wcm.LANG.WEBSITE_CANCELPUB||'撤销发布这个站点',
		rightIndex : 4,
		order : 11,
		fn : pageObjMgr['cancelpublish']
	});*/
	reg({
		key : 'seperate',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		rightIndex : -1,
		order : 12,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'likecopy',
		type : 'website',
		desc : wcm.LANG.WEBSITE_LIKECOPY||'类似创建',
		title : wcm.LANG.WEBSITE_LIKECOPY_TITLE||'创建相似的站点（唯一标识和存放位置不同）',
		rightIndex : -2,
		order : 13,
		fn : pageObjMgr['likecopy']
	});
	reg({
		key : 'seperate',
		type : 'website',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		rightIndex : -1,
		order : 14,
		fn : pageObjMgr['seperate']
	});
	/*reg({
		key : 'setSiteUser',
		type : 'website',
		desc : wcm.LANG.WEBSITE_39||'设置站点用户',
		rightIndex : 6,
		order : 15,
		fn : pageObjMgr['setSiteUser']
	});*/
	reg({
		key : 'keyword',
		type : 'website',
		desc : wcm.LANG.WEBSITE_40||'管理关键词',
		title : wcm.LANG.WEBSITE_40_TITLE||'管理当前站点下的所有关键词',
		rightIndex : 1,
		order : 16,
		fn : pageObjMgr['keyword']
	});
	reg({
		key : 'keyword',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_40||'管理关键词',
		title : wcm.LANG.WEBSITE_40_TITLE||'管理当前站点下的所有关键词',
		rightIndex : -2,
		order : 5,
		fn : pageObjMgr['keyword']
	});
	reg({
		key : 'recycle',
		type : 'website',
		desc : wcm.LANG.WEBSITE_RECYCLE||'删除站点',
		title : wcm.LANG.WEBSITE_RECYCLE_TITLE||'将站点放入回收站',
		rightIndex : 2,
		order : 15,
		fn : pageObjMgr['recycle'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'new',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_NEW||'创建一个新站点',
		title : wcm.LANG.WEBSITE_NEW_TITLE||'新建一个站点节点',
		rightIndex : -2,
		order : 1,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'quicknew',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_QUICKNEW||'智能创建一个新站点',
		title : wcm.LANG.WEBSITE_QUICKNEW_TITLE||'按照已经完成的站点样例智能创建一个相似的新站点',
		rightIndex : -2,
		order : 2,
		fn : pageObjMgr['quicknew'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["SITETYPE"] == 0){
				return true;
			}
			return false;
		}
	});
	reg({
		key : 'import',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_IMPORT||'从外部导入站点',
		title : wcm.LANG.WEBSITE_IMPORT_TITLE||'读取外部站点文件（只支持XML和ZIP文件），并生成相应的站点',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_EXPORTSOME||'导出这些站点到',
		title : wcm.LANG.WEBSITE_EXPORTSOME_TITLE||'导出这些站点，每个站点生成相应的XML文件',
		rightIndex : 1,
		order : 22,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'recycle',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_RECYCLE||'删除站点',
		title : wcm.LANG.WEBSITE_RECYCLE_TITLE|| '将站点放入回收站',
		rightIndex : 2,
		order : 23,
		fn : pageObjMgr['recycle'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'preview',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_PREVIEWSOME||'预览这些站点',
		title : wcm.LANG.WEBSITE_PREVIEWSOME_TITLE || '重新生成并打开这些站点的预览页面',
		rightIndex : 3,
		order : 24,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'increasepublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_ADDPUBSOME||'增量发布这些站点',
		title : wcm.LANG.WEBSITE_ADDPUBSOME_TITLE||'发布选中站点和他们的栏目的首页，并且发布所有可发布状态的文档',
		rightIndex : 5,
		order : 25,
		fn : pageObjMgr['increasepublish'],
		quickKey : 'P'
	});
	reg({
		key : 'homepublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_HOMEPUBSOME||'仅发布这些站点首页',
		title : wcm.LANG.WEBSITE_HOMEPUBSOME_TITLE||'重新生成并发布选中站点的首页',
		rightIndex : 5,
		order : 26,
		fn : pageObjMgr['homepublish']
	});
	reg({
		key : 'completepublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_COMPUBSOME||'完全发布这些站点',
		title : wcm.LANG.WEBSITE_COMPUBSOME_TITLE||'重新生成这些站点的所有文件',
		rightIndex : 4,
		order : 27,
		fn : pageObjMgr['completepublish']
	});
	reg({
		key : 'updatepublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_UPDATEPUBSOME||'更新发布这些站点',
		title :  wcm.LANG.WEBSITE_UPDATEPUBSOME_TITLE||'更新这些站点和相关栏目的首页',
		rightIndex : 5,
		order : 28,
		fn : pageObjMgr['updatepublish']
	});
	/*reg({
		key : 'cancelpublish',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_CANCELPUBSOME||'撤销发布这些站点',
		rightIndex : 4,
		order : 29,
		fn : pageObjMgr['cancelpublish']
	});*/
	reg({
		key : 'seperate',
		type : 'websites',
		desc : wcm.LANG.WEBSITE_SEP||'分隔线',
		title : "分隔线",
		rightIndex : -1,
		order : 30,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'createFromFile',
		type : 'websiteInRoot',
		desc : wcm.LANG.WEBSITE_BATCHNEW||'批量创建站点',
		title : wcm.LANG.WEBSITE_BATCHNEW_TITLE||'从文件批量创建站点结构',
		rightIndex : -2,
		order : 4,
		fn : pageObjMgr['createFromFile']
	});
	reg({
		key : 'commentmgr',
		type : 'website',
		desc : wcm.LANG.WEBSITE_COMMENTMGR||'管理评论',
		title : wcm.LANG.WEBSITE_COMMENTMGR_TITLE||'管理该站点下的所有评论',
		rightIndex : 8,
		order : 17,
		fn : pageObjMgr['commentmgr'],
		isVisible : function(event){
			try{
				return $MsgCenter.getActualTop().g_IsRegister['comment'];
			}catch(err){
				return false;
			}
		}
	});
	reg({
		key : 'pubstat',
		type : 'website',
		desc : wcm.LANG.WEBSITE_PUBSTAT ||'发文统计',
		titile : '发文统计',
		rightIndex : 1,
		order : 18,
		fn : pageObjMgr['pubstat']
	});
	reg({
		key : 'setappendixsize',
		type : 'website',
		desc : wcm.LANG.website_2011 || '设置这个站点文档附件大小',
		title : wcm.LANG.website_2011 || '设置这个站点上传文档附件大小',
		rightIndex : 1,
		order : 10,
		fn : pageObjMgr['setAppendixSize'],
		isVisible : function(event){
			 return true;
		}
	});
})();
//栏目操作信息和Mgr定义
Ext.ns('wcm.domain.ChannelMgr');

(function(){
	var m_nChannelObjType = 101;
	var m_sServiceId = 'wcm6_channel';
	var m_oMgr = wcm.domain.ChannelMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}

	function edit(event, oParams){
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'channel/channel_add_edit.jsp?' + $toQueryStr(oParams),
			title : wcm.LANG.CHANNEL_9||'新建/修改栏目',
			callback : function(objId){
				CMSObj[oParams["objectid"] > 0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
			}
		});
	}

	function renderMove(event, _args){
		moveAsChild(_args.srcId, _args.dstId, (_args.dstType == 103), event);
	}
	function moveAsChild(_nSrcChnlIds, _nTargetId, _bIsSite, event){
		var oPostData = {srcChannelIds : _nSrcChnlIds};
		if(_bIsSite === true) {
			oPostData.DstSiteId = _nTargetId;
		}else{
			oPostData.DstChannelId = _nTargetId;
		}
		getHelper().call(m_sServiceId, 'moveAsChild', oPostData, true, function(){
			//通知其他页面刷新
			var objsOrHost = event.getObjsOrHost();
			var items = [];
			for (var i = 0; i < objsOrHost.length(); i++){
				items.push({objId : objsOrHost.getAt(i).getId()});
			}
			var context = {dstObjectId : _nTargetId, isSite : _bIsSite};
			var oCmsObjs = CMSObj.createEnumsFrom({
				objType : objsOrHost.getType()
			}, context);
			oCmsObjs.addElement(items);
			oCmsObjs.aftermove();
		});
	}
	function renderLikecopy(event,_args){
		var oPostData = {srcChannelId: _args.srcId};
		if(_args.dstType == 103) {
			oPostData.DstSiteId = _args.dstId;
		}else{
			oPostData.DstChannelId = _args.dstId;
		}
		var nType = _args.dstType, nDstSiteId = oPostData.DstSiteId, nDstChnlId = oPostData.DstChannelId;
		ProcessBar.start(wcm.LANG.CHANNEL_8||'栏目类似创建');
		getHelper().call(m_sServiceId, 'createFrom', oPostData, true, function(_trans, _json){
			var bSucess = $v(_json, 'REPORTS.IS_SUCCESS');
			var objectIds = $v(_json, 'REPORTS.ObjectIds.ObjectId');
			ProcessBar.close();
			Ext.Msg.report(_json, wcm.LANG.CHANNEL_7||'栏目类似创建结果', function(){
				if(bSucess != 'true') return;
				var wcmEvent = CMSObj.createEvent(event.getHost(), {
					objId : _args.dstId,
					objType : _args.dstType==103 ?
						WCMConstants.OBJ_TYPE_WEBSITE : WCMConstants.OBJ_TYPE_CHANNELMASTER
				});
				CMSObj.afteradd(wcmEvent)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objectIds});
			});
		});
	}
	function resetTemplates(_nFolderId, _nObjType, _fCallBack){
		getHelper().call('wcm6_template', 'impartTemplateConfig',
			{objectId: _nFolderId, ObjectType:_nObjType}, true, _fCallBack);
	}
	function synchTemplates(_nFolderId, _nObjType, childObjIds, _fCallBack){
		getHelper().call('wcm61_template', 'synTemplates',
			{objectId: _nFolderId, ObjectType:_nObjType, objectIds : childObjIds}, true, _fCallBack);
	}
	Ext.apply(wcm.domain.ChannelMgr, {
		'new' : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			edit(event, {
				objectid:	0,
				channelid:	0,
				parentid:	hostType == 101 ? hostId : 0,
				siteid:		hostType == 101 ? 0 : hostId
			});
		},
		edit : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var obj = event.getObj().getId();
			edit(event, {
				objectid:	event.getObj().getId(),
				channelid:	event.getObj().getId(),
				parentid:	hostType == 101 ? hostId : 0,
				siteid:		hostType == 101 ? 0 : hostId
			});
		},
		'import' : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var params = {
				parentid : hostId,
				objecttype : hostType
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_import.html?' + $toQueryStr(params), wcm.LANG.CHANNEL_6||'栏目导入', function(){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL});
			});
		},

		export0 : function (oPostData){
			if(oPostData.ObjectIds){
				FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_export.html?' + $toQueryStr(oPostData)
					, wcm.LANG.CHANNEL_10||'导出栏目');
			}else
				FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_export.html?' + $toQueryStr(oPostData)
					, wcm.LANG.CHANNEL_66||'导出所有栏目');
		},

		exportAll : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			if(event.getContext().RecordNum <= 0) {
				Ext.Msg.$fail(wcm.LANG.CHANNEL_67||'没有任何要导出的栏目。');
				return false;
			}
			var objId = hostId;
			var oPostData = {};
			oPostData[hostType == 101 ? 'parentChannelId' : 'parentSiteId'] = objId;
			wcm.domain.ChannelMgr.export0(oPostData);
		},
		'export' : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var objType = event.getObjs().getIntType();
			var objIds = objType == 101 ? event.getIds().join(",") || hostId : hostId;
			wcm.domain.ChannelMgr.export0({ObjectIds : objIds});
		},
		move : function(event){
			var host = event.getHost();
			var hostId = host.getId();
			var hosttype = host.getIntType();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			var sUrl = WCMConstants.WCM6_PATH + 'channel/channel_select_move.html?srcId=' + sIds;
			var sFolderInfo = 'folderType=' + hosttype;
			sFolderInfo += '&folderId=' + hostId;
			sUrl += '&' + sFolderInfo;
			FloatPanel.open(sUrl, wcm.LANG.CHANNEL_5||'栏目移动',renderMove.bind(this, event));
		},

		likecopy : function(event){
			var host = event.getHost();
			var hostId = host.getId();
			var sSrcId = (event.getIds().length == 0)?hostId : event.getIds();
			var sSiteType = event.getContext().params["SITETYPE"];
			var oParams = {srcId : sSrcId, siteType : sSiteType};
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_select_likecopy.jsp?' + $toQueryStr(oParams) , wcm.LANG.CHANNEL_LIKECOPY_2 || '类似创建',renderLikecopy.bind(this,event));
		},

		trash : function(event){
			var hostId = event.getHost().getId();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			sIds = sIds + '';
			var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
			var sCon = "";
			if(nCount == 1){
				sCon = wcm.LANG.CHANNEL_12||"确实要将此栏目放入回收站吗?";
			}else{
				sCon = String.format(wcm.LANG.CHANNEL_13||("确实要将这 {0} 个栏目放入回收站吗?"),nCount);
			}
			// 添加对栏目是否是专题对应栏目的判断
			getHelper().call('wcm61_special', 'findByChnlIds', {ChannelIds : sIds}, true, function(transport, _json){
				try{
					var oSpecials = $a(_json, "Specials.Special");
					if(!oSpecials[0]){
						if (confirm(sCon)){
							ProcessBar.start(wcm.LANG.CHANNEL_4||'删除栏目');
							getHelper().call(m_sServiceId, 'delete', {ObjectIds: sIds, drop: false}, true, function(){
								ProcessBar.close();
								window.setTimeout(function(){
									event.getObjsOrHost().afterdelete();
								}, 500);
							});
						}
					}else {
						alert("包含与专题对应的栏目，这样的栏目不能直接被删除，请到专题管理页面删除对应专题！");
					}
					
				}catch(e){
					alert(e);
				}
			});
			
		},
		commentmgr : function(event){
			var hostId = event.getHost().getId();
			var sIds = (event.getIds().length == 0)?hostId : event.getIds();
			var oParams = {
				ChannelId : sIds
			}
			var sUrl = WCMConstants.WCM_ROOTPATH + 'comment/comment_mgr.jsp?' + $toQueryStr(oParams);
			var sWinName = 'comment_name';
			$openMaxWin(sUrl, sWinName);
		},
		chnlpositionset : function(event){
			var oPageParams = event.getContext();
			//var host = event.getHost();
			//var hostid = host.getId();
			//var objType = event.getObjs().getAt(0).objType;
			var channelId = 0;
			if(event.getObjs().getAt(0)==null){
				channelId = event.getHost().getId();
			}
			else {
				channelId = event.getObjs().getAt(0).getId();
			}
			//Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
			Object.extend(oPageParams,{"ChannelId":channelId});
			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_position_set.jsp?' + $toQueryStr(oPageParams),
			wcm.LANG.CHANNEL_48||'栏目-调整顺序', CMSObj.afteredit(event));
		},
		synTemplates : function(event){
			var objId = event.getObj().getId();
			var params = {
				objType:m_nChannelObjType,
				objId:objId,
				close : 0,
				ExcludeTop : 1,
				ExcludeInfoview : 0,
				ExcludeLink : 1
			};
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'channel/object_select.html',
				dialogArguments : params,
				title : wcm.LANG.CHANNEL_64||'选择栏目',
				callback : function(args){
					if(args.allChildren){
						var sHtml = wcm.LANG.CHANNEL_11||"确实要同步模板到子栏目吗？<br><span style=\'color:red;font:16px;\'>注意：<br>该操作会覆盖更改所有子栏目的模板设置！</span>";
						Ext.Msg.confirm(sHtml, {
							ok : function(){
								FloatPanel.close();
								resetTemplates(objId, m_nChannelObjType);
							}
						});
					}else{
						FloatPanel.close();
						synchTemplates(objId, m_nChannelObjType, args.ids.join());
					}
				}
			});
		},
		preview : function(event){
			var hostId = event.getHost().getId();
			var aObjIds = (event.getIds().length == 0)?hostId : event.getIds().join(",");
			wcm.domain.PublishAndPreviewMgr.preview(aObjIds, m_nChannelObjType);
		},
		publish : function(event, _sServiceId){
			var hostId = event.getHost().getId();
			var aObjIds = (event.getIds().length == 0)?hostId : event.getIds().join(",");
			wcm.domain.PublishAndPreviewMgr.publish(aObjIds, m_nChannelObjType, _sServiceId);
		},
		increasingpublish : function(event){
			m_oMgr.publish(event, 'increasingPublish');
		},
		increasepublish : function(event){
			m_oMgr.increasingpublish(event);
		},
		fullypublish : function(event){
			m_oMgr.publish(event, 'fullyPublish');
		},
		refreshpublish : function(event){
			m_oMgr.publish(event, 'refreshPublish');
		},
		solopublish : function(event){
			m_oMgr.publish(event, 'soloPublish');
		},
		recallpublish : function(event){
			var sHtml = String.format("确实要{0}撤销发布{1}这个栏目么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
			Ext.Msg.confirm(sHtml,{
				yes : function(){
                   m_oMgr.publish(event, 'recallPublish');
                }
			})
		},
		createFromFile : function(event){
			var hostId = event.getHost().getId();
			var hostType = event.getHost().getIntType();
			var params = {
				parentid : hostId,
				objecttype : hostType
			}

			FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_create_fromfile.html?' + $toQueryStr(params), '批量创建栏目', function(){
				CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL});
			});
		}
	});

})();
(function(){
	var pageObjMgr = wcm.domain.ChannelMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
		var objs = event.getObjs();
		var context = event.getContext();
		if(objs.length() > 0){
			var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
		}else{
			var chnlTypes = context.get('ChannelType') || 0;
		}
		if(!Array.isArray(chnlTypes)){
			chnlTypes = [chnlTypes];
		}
		var hideChnlTypes = [1,2,11];
		for (var i = 0; i < chnlTypes.length; i++){
			if(hideChnlTypes.include(chnlTypes[i])) return false;
		}
		return true;
	}
	var fnIsVisible2 = function(event){
		var objs = event.getObjs();
		var context = event.getContext();
		if(objs.length() > 0){
			var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
		}else{
			var chnlTypes = context.get('ChannelType') || 0;
		}
		if(!Array.isArray(chnlTypes)){
			chnlTypes = [chnlTypes];
		}
		var hideChnlTypes = [1,2,11,13];
		for (var i = 0; i < chnlTypes.length; i++){
			if(hideChnlTypes.include(chnlTypes[i])) return false;
		}
		return true;
	}
	var fnIsVisible3 = function(event){
		var objs = event.getObjs();
		var context = event.getContext();
		if(context.params.SITETYPE && context.params.SITETYPE.indexOf("4") != -1)
			return false;
		if(objs.length() > 0){
			var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
		}else{
			var chnlTypes = context.get('ChannelType') || 0;
		}
		if(!Array.isArray(chnlTypes)){
			chnlTypes = [chnlTypes];
		}
		var hideChnlTypes = [1,2,11,13];
		for (var i = 0; i < chnlTypes.length; i++){
			if(hideChnlTypes.include(chnlTypes[i])) return false;
		}
		return true;
	}
	reg({
		key : 'edit',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_EDIT || '修改这个栏目',
		title : wcm.LANG.CHANNEL_EDIT_TITLE || '修改栏目的基本属性和发布设置等相关信息',
		rightIndex : 13,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'move',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_MOVE || '移动这个栏目',
		title : wcm.LANG.CHANNEL_MOVE_TITLE || '移动这个栏目到指定的栏目',
		rightIndex : 12,
		order : 2,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'preview',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_PREVIEW || '预览这个栏目',
		title : wcm.LANG.CHANNEL_PREVIEW_TITLE || '重新生成并打开这个栏目的预览页面',
		rightIndex : 15,
		order : 4,
		fn : pageObjMgr['preview'],
		isVisible : fnIsVisible,
		quickKey : 'R'
	});
	reg({
		key : 'increasingpublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_ADDPUB || '增量发布这个栏目',
		title : wcm.LANG.CHANNEL_ADDPUB_TITLE || '发布栏目的首页，并且发布所有处于可发布状态的文档',
		rightIndex : 17,
		order : 5,
		fn : pageObjMgr['increasingpublish'],
		isVisible : fnIsVisible,
		quickKey : 'P'
	});
	reg({
		key : 'solopublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SOLOPUB || '仅发布此栏目的首页',
		title : wcm.LANG.CHANNEL_SOLOPUB_TITLE || '重新生成并发布当前栏目的首页',
		rightIndex : 17,
		order : 6,
		fn : pageObjMgr['solopublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'export',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_EXPORT || '导出这个栏目',
		title : wcm.LANG.CHANNEL_EXPORT_SEL || '导出当前选中的栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 7,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'synTemplates',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SYNCH || '同步模板到子栏目',
		title : wcm.LANG.CHANNEL_SYNCH_TITLE || '将当前栏目的模板应用到其子栏目上',
		rightIndex : 13,
		order : 8,
		fn : pageObjMgr['synTemplates'],
		isVisible : fnIsVisible2
	});
	reg({
		key : 'seperate',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 9,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'fullypublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_FULLPUB || '完全发布这个栏目',
		title : wcm.LANG.CHANNEL_FULLPUB_TITLE || '重新生成并发布当前栏目下的的所有文件',
		rightIndex : 16,
		order : 10,
		fn : pageObjMgr['fullypublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'refreshpublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_REFRESHPUB || '更新发布这个栏目',
		title : wcm.LANG.CHANNEL_REFRESHPUB_TITLE || '更新当前栏目和其相关栏目的首页',
		rightIndex : 17,
		order : 11,
		fn : pageObjMgr['refreshpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'recallpublish',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_CANCELPUB || '撤销发布这个栏目',
		title : wcm.LANG.CHANNEL_CANCELPUB_PUBED || '撤回已发布目录或页面',
		rightIndex : 16,
		order : 12,
		fn : pageObjMgr['recallpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'seperate',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 13,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'likecopy',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_LIKECOPY || '类似创建',
		title : wcm.LANG.CHANNEL_LIKECOPY_TITLE || '创建相似的栏目（唯一标识和存放位置不同）',
		rightIndex : 13,
		order : 14,
		fn : pageObjMgr['likecopy']
	});
	reg({
		key : 'docpositionset',
		type : 'channel',
		desc :  wcm.LANG.CHANNEL_DOCPOSITIONSET ||  '调整顺序',
		title : wcm.LANG.CHANNEL_DOCPOSITIONSET_TITLE ||  '调整当前栏目在站点中的顺序',
		isVisible : fnIsVisible,
		rightIndex : 13,
		order : 35,
		fn : pageObjMgr['chnlpositionset']
	});
	reg({
		key : 'commentmgr',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_COMMENT || '管理评论',
		title : wcm.LANG.CHANNEL_COMMENT_TITLE || '管理当前栏目下的所有评论',
		rightIndex : 8,
		order : 16,
		fn : pageObjMgr['commentmgr'],
		isVisible : function(event){
			try{
				return $MsgCenter.getActualTop().g_IsRegister['comment'];
			}catch(err){
				return false;
			}
		}
	});
	reg({
		key : 'new',
		type : 'websiteHost',
		desc : wcm.LANG.CHANNEL_NEW || '新建一个栏目',
		title : wcm.LANG.CHANNEL_NEW_INSITE || '在当前站点下创建一个新栏目',
		rightIndex : 11,
		order : 258,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'websiteHost',
		desc : wcm.LANG.CHANNEL_IMPORT || '导入栏目',
		title : wcm.LANG.CHANNEL_IMPORT_INSITE || '在当前站点中导入一个子栏目（从zip文件中）',
		rightIndex : 11,
		order : 259,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'websiteHost',
		desc : wcm.LANG.CHANNEL_EXPORTALL || '导出所有子栏目',
		title : wcm.LANG.CHANNEL_EXPORTALL_INSITE || '导出当前站点下的所有子栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 260,
		fn : pageObjMgr['exportAll']
	});
	reg({
		key : 'new',
		type : 'channelHost',
		desc : wcm.LANG.CHANNEL_NEW || '新建一个栏目',
		title : wcm.LANG.CHANNEL_NEW_INCHANNEL ||'在当前栏目中新建一个子栏目',
		rightIndex : 11,
		order : 18,
		fn : pageObjMgr['new'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'channelHost',
		desc : wcm.LANG.CHANNEL_IMPORT || '导入栏目',
		title : wcm.LANG.CHANNEL_IMPORT_INCHANNEL || '在当前栏目中导入一个子栏目（从zip文件中）',
		rightIndex : 11,
		order : 19,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'channelHost',
		desc : wcm.LANG.CHANNEL_EXPORTALL || '导出所有子栏目',
		title : wcm.LANG.CHANNEL_EXPORTALL_INCHANNEL || '导出当前栏目下的所有子栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 20,
		fn : pageObjMgr['exportAll'],
		isVisible : function(event){
			var hostchnl = event.getHost();
			if(hostchnl.channelType && hostchnl.channelType==13)return false;
			return true;
		}
	});
	reg({
		key : 'edit',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_EDIT_NOW || '修改当前栏目',
		title : wcm.LANG.CHANNEL_EDIT_NOW_TITLE || '修改当前栏目的基本属性和发布设置等相关信息',
		rightIndex : 13,
		order : 21,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'move',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_MOVE_NOW || '移动当前栏目',
		title : wcm.LANG.CHANNEL_MOVE_NOW_TITLE || '移动当前栏目到指定的位置',
		rightIndex : 12,
		order : 22,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'trash',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
		title : wcm.LANG.CHANNEL_TRASH_TITLE || '将栏目放入回收站',
		rightIndex : 12,
		order : 23,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'preview',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_PREVIEW_NOW || '预览当前栏目',
		title : wcm.LANG.CHANNEL_PREVIEW_NOWPUB || '预览当前栏目的发布效果',
		rightIndex : 15,
		order : 24,
		fn : pageObjMgr['preview'],
		isVisible : fnIsVisible,
		quickKey : 'R'
	});
	reg({
		key : 'increasingpublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_ADDPUB_NOW || '增量发布当前栏目',
		title : wcm.LANG.CHANNEL_ADDPUB_NOW_TITLE || '发布当前栏目和其子栏目的首页，并且发布该栏目下所有可发布状态的文档',
		rightIndex : 17,
		order : 25,
		fn : pageObjMgr['increasingpublish'],
		isVisible : fnIsVisible,
		quickKey : 'P'
	});
	reg({
		key : 'solopublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SOLOPUB_NOW || '仅发布当前栏目首页',
		title : wcm.LANG.CHANNEL_SOLOPUB_NOW_TITLE || '重新生成并发布当前栏目的首页',
		rightIndex : 17,
		order : 26,
		fn : pageObjMgr['solopublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'export',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_EXPORT_NOW || '导出当前栏目',
		title : wcm.LANG.CHANNEL_EXPORT_NOW_INFO || '导出当前栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 27,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'synTemplates',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SYNCH || '同步模板到子栏目',
		title : wcm.LANG.CHANNEL_SYNCH_TITLE || '将当前栏目的模板应用到其子栏目上',
		rightIndex : 13,
		order : 28,
		fn : pageObjMgr['synTemplates'],
		isVisible : fnIsVisible2
	});
	reg({
		key : 'seperate',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 29,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'fullypublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_FULLPUB_NOW || '完全发布当前栏目',
		title : wcm.LANG.CHANNEL_FULLPUB_NOW_TITLE || '重新生成和发布当前栏目下的所有文档',
		rightIndex : 16,
		order : 30,
		fn : pageObjMgr['fullypublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'refreshpublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_REFRESHPUB_NOW || '更新发布当前栏目',
		title : wcm.LANG.CHANNEL_REFRESHPUB_SEL || '仅重新生成当前栏目的首页以及相关的概览页面',
		rightIndex : 17,
		order : 31,
		fn : pageObjMgr['refreshpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'recallpublish',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_CANCELPUB_NOW || '撤销发布当前栏目',
		title : wcm.LANG.CHANNEL_CANCELPUB_PUBED || '撤回已发布目录或页面',
		rightIndex : 16,
		rightIndex : 16,
		order : 32,
		fn : pageObjMgr['recallpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'seperate',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 33,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'likecopy',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_LIKECOPY || '类似创建',
		title : wcm.LANG.CHANNEL_LIKECOPY_TITLE || '创建相似的栏目（唯一标识和存放位置不同）',
		rightIndex : 13,
		order : 34,
		fn : pageObjMgr['likecopy']
	});
	reg({
		key : 'docpositionset',
		type : 'channelMaster',
		desc :  wcm.LANG.CHANNEL_DOCPOSITIONSET ||  '调整顺序',
		title : wcm.LANG.CHANNEL_DOCPOSITIONSET_TITLE || '调整当前栏目在站点中的顺序',
		isVisible : fnIsVisible,
		rightIndex : 13,
		order : 35,
		fn : pageObjMgr['chnlpositionset']
	});
	reg({
		key : 'seperate',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 36,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'commentmgr',
		type : 'channelMaster',
		desc : wcm.LANG.CHANNEL_COMMENT || '管理评论',
		title : wcm.LANG.CHANNEL_COMMENT_TITLE || '管理当前栏目下的所有评论',
		rightIndex : 8,
		order : 37,
		fn : pageObjMgr['commentmgr'],
		isVisible : function(event){
			try{
				return $MsgCenter.getActualTop().g_IsRegister['comment'];
			}catch(err){
				return false;
			}
		}
	});
	reg({
		key : 'move',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_MOVESOME || '移动这些栏目',
		title : wcm.LANG.CHANNEL_MOVESOME_TITLE || '移动这些栏目到指定的栏目',
		rightIndex : 12,
		order : 39,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'increasingpublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_ADDPUBSOME || '增量发布这些栏目',
		title : wcm.LANG.CHANNEL_ADDPUBSOME_TITLE ||'发布选中的栏目的首页，并且发布所有可发布状态的文档',
		rightIndex : 17,
		order : 40,
		fn : pageObjMgr['increasingpublish'],
		isVisible : fnIsVisible,
		quickKey : 'P'
	});
	reg({
		key : 'preview',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_PREVIEWSOME || '预览这些栏目',
		title : wcm.LANG.CHANNEL_PREVIEWSOME_SEL || '预览当前选中栏目的发布效果',
		rightIndex : 15,
		order : 41,
		fn : pageObjMgr['preview'],
		isVisible : fnIsVisible,
		quickKey : 'R'
	});
	reg({
		key : 'solopublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_SOLOPUBSOME || '仅发布这些栏目的首页',
		title : wcm.LANG.CHANNEL_SOLOPUBSOME_TITLE || '重新生成并发布选中栏目的首页',
		rightIndex : 17,
		order : 42,
		fn : pageObjMgr['solopublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'export',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_EXPORTPUBSOME || '导出这些栏目',
		title : wcm.LANG.CHANNEL_EXPORT_SEL || '导出当前选中的栏目（提供下载的zip或者xml文件）',
		rightIndex : 13,
		order : 43,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'seperate',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
		rightIndex : -1,
		order : 44,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'fullypublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_FULLPUBSOME || '完全发布这些栏目',
		title : wcm.LANG.CHANNEL_FULLPUBSOME_TITLE || '重新生成并发布选中栏目下的所有文件',
		rightIndex : 16,
		order : 45,
		fn : pageObjMgr['fullypublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'refreshpublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_REFRESHPUBSOME || '更新发布这些栏目',
		title : wcm.LANG.CHANNEL_REFRESHPUBSOME_TITLE||'重新生成并发布选中栏目和与其相关栏目的首页',
		rightIndex : 17,
		order : 46,
		fn : pageObjMgr['refreshpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'recallpublish',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_CANCELPUBSOME || '撤销发布这些栏目',
		title : wcm.LANG.CHANNEL_CANCELPUBSOME_TITLE || '撤销选中栏目的发布操作',
		rightIndex : 16,
		order : 47,
		fn : pageObjMgr['recallpublish'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'trash',
		type : 'channels',
		desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
		title : wcm.LANG.CHANNEL_TRASH_TITLE || '将这个栏目放入回收站',
		rightIndex : 12,
		order : 48,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'createFromFile',
		type : 'channelHost',
		desc : wcm.LANG.CHANNEL_CREATE_FROMFILE|| '批量创建栏目',
		title : wcm.LANG.CHANNEL_CREATE_FROMFILE_TITLE || '从文件批量创建栏目',
		rightIndex : 11,
		order : 22,
		fn : pageObjMgr['createFromFile']
	});
	reg({
		key : 'createFromFile',
		type : 'websiteHost',
		desc : wcm.LANG.CHANNEL_CREATE_FROMFILE|| '批量创建栏目',
		title : wcm.LANG.CHANNEL_CREATE_FROMFILE_TITLE || '从文件批量创建栏目',
		rightIndex : 11,
		order : 260,
		fn : pageObjMgr['createFromFile']
	});
	
	reg({
		key : 'trash',
		type : 'channel',
		desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
		title : wcm.LANG.CHANNEL_TRASH_TITLE || '将这个栏目放入回收站',
		rightIndex : 12,
		order : 213,
		fn : pageObjMgr['trash'],
		quickKey : ['Delete', 'ShiftDelete']
	});
})();
