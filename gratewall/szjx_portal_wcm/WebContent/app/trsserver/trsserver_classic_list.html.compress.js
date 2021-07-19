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
//扩展字段操作信息和Mgr定义
Ext.ns('wcm.domain.TRSServerMgr');
(function(){
	var m_oMgr = wcm.domain.TRSServerMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	Ext.apply(wcm.domain.TRSServerMgr, {
		"pick" : function(event){
			var sIds = event.getObjs().getIds();
			var args = {
				IsRadio : 1,
				CurrSiteType : 0,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeVirtual : 1,
				ExcludeInfoView : 1,
				ExcludeOnlySearch : 1,
				ShowOneType : 1,
				NotSelect : 1,
				canEmpty : true
			};
			FloatPanel.open(
				WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
				wcm.LANG.TRSSERVER_7 || '选择所属栏目',
				function(selectIds, selectChnlDescs){
					if(!selectIds||selectIds.length==0) {
						Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_32 ||'请选择当前文档要复制到的目标栏目!');
						return false;
					}
					var oPostData = {
						ServerIds :sIds,
						ChannelId : selectIds[0]
					}
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call('wcm6_document', 'trsSaveToWCM', oPostData, true, function(_oTrans, _json){ 
						alert(wcm.LANG.DOCUMENT_PROCESS_237 || "提取成功！");
						FloatPanel.close();
						CMSObj.afteredit(event)();
					},function(){
						alert(wcm.LANG.DOCUMENT_PROCESS_238 || "提取失败！");
					});
				},
				dialogArguments = args
			);
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.TRSServerMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'pick',
		type : 'trsserver',
		desc : wcm.LANG.TRSSERVER_5 || '提取',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['pick'],
		quickKey : 'P'
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
