var aFieldType = ['check' , 'appendix' ,'radio' , 'editor'];

Event.observe(window, 'load', function(){
	Event.observe(document.body, 'click', function(event){
		EventHandler.dispatch(window.event || event);
	}, false)
});


var EventHandler = {
	dispatch : function(event){
		//先找到事件触发元素
		var dom = Event.element(event);
		//在事件元素外（的target元素上）包含事件产生所需要的属性。
		dom = this.findTarget(dom);
		if(!dom) return;
		//根据属性调用方法
		var type = dom.getAttribute('_type');
		//是单个数据类型的问题交给单个来处理。
		(this[type] || Ext.emptyFn)(dom, event);
	},
	findTarget : function(dom){
		while(dom){
			if(dom.tagName == "BODY") return null;
			if(dom.getAttribute("_type")) return dom;
			dom = dom.parentNode;
		}
		return null;
	},
	appendix : function(dom, event){
		var bIsTitleField = Element.hasClassName(dom.parentNode,"titleField");
		if(!bIsTitleField){
			var sFileName = dom.getAttribute("value");
			if(sFileName != ""){
				FileDownloader.download("/wcm/file/read_file.jsp?FileName=" + sFileName);
			}
		}
	}
};
var FileUploader = Class.create();
Object.extend(FileUploader, {
	_cache_ : [],
	onUploadAll : null,
	getCache : function(){
		return this._cache_;
	},
	setUploadAll : function(fUploadAll){
		this.onUploadAll = fUploadAll;
	},
	isEmptyValue : function(){
		return this.isUploadAll();
	},
	isUploadAll : function(){
		for (var i = 0, length = this._cache_.length; i < length; i++){
			var instance = this._cache_[i];
			if(instance.isBindData) return false;
		}
		return true;
	},
	destory : function(){
		for (var i = 0, length = this._cache_.length; i < length; i++){
			var instance = this._cache_[i];
			delete instance["oRelateElement"];
			delete instance["oIframeElement"];
			delete instance["oFileControl"];
			delete instance["fChangeEvent"];
			delete instance["fUploadedCallBack"];
			delete instance["fUploadedCallBack2"];
			this._cache_[i] = null;
		}
		delete FileUploader["onUploadAll"];
		this._cache_ = [];
	}
});
Object.extend(FileUploader.prototype, {
	uploadSrc					: 'file_upload.html',
	uploadDoWithSrc				: 'file_upload_dowith.jsp',
	appendixIframeIdSuffix		: '_iframe',
	appendixFileControlId		: 'fileNameControl',
	oRelateElement				: null,
	fChangeEvent				: null,
	fUploadedCallBack			: null,
	fUploadedCallBack2			: null,
	oIframeElement				: null,
	oFileControl				: null,
	isBindData					: false,

	initialize : function(sRelateElement, fChangeEvent, fUploadedCallBack){
		//1.cache the instance, for destroy.
		FileUploader._cache_.push(this);

		//2.init apperance.
		this.oRelateElement = $(sRelateElement);
		this.fChangeEvent = fChangeEvent;
		this.fUploadedCallBack = fUploadedCallBack;
		this.oIframeElement = document.createElement('iframe');
		this.oIframeElement.id = sRelateElement + this.appendixIframeIdSuffix;
		this.oIframeElement.style.display = 'none';
		this.oIframeElement.src = this.uploadSrc;
		document.body.appendChild(this.oIframeElement);

		//3.init actions--events.
		this.initEvent();
	},
	initEvent : function(){
		//1.bind this.oRelateElement events.
		Event.observe(this.oRelateElement, 'click', this.onBrowse.bind(this));

		//2.bind this.oIframeElement events.
		Event.observe(this.oIframeElement, 'readystatechange', this.onIframeStateChanged.bind(this));
	},
	onBrowse : function(){
		try{			
			if(!this.oFileControl){
				this.oFileControl = this.oIframeElement.contentWindow.document.getElementById(this.appendixFileControlId);
				this.oFileControl.onchange = function(event){
					this.isBindData = true;
					if(this.fChangeEvent){
						this.fChangeEvent(this.oFileControl.value);
					}
				}.bind(this);
			}	
			this.oFileControl.click();
		}catch(error){
			alert(error.message);
		}
	},
	onIframeStateChanged : function(){
		if(!this.isBindData) return;
		if(this.oIframeElement.readyState.toLowerCase() != 'complete') return;
		this.onUpload();
	},
	onUpload : function(){
		var oInfoDiv = this.oIframeElement.contentWindow.document.getElementById("infoId");
		if(oInfoDiv){
			if(oInfoDiv.getAttribute("isError")){
				alert(wcm.LANG.METAVIEWDATA_109 || "上传文件失败！\n" + oInfoDiv.innerText);
			}else{
				this.isBindData = false;
				var fUploadCallBack = this.fUploadedCallBack2 || this.fUploadedCallBack;
				if(fUploadCallBack){
					fUploadCallBack(decodeURI(oInfoDiv.innerHTML));
				}
			}
		}
		if(FileUploader.isUploadAll() && FileUploader.onUploadAll){
			FileUploader.onUploadAll();
		}
		this.oIframeElement.setAttribute("src", this.uploadSrc);
	},
	reset : function(){
		//reset the file control value by reload the page.
		if(!this.oFileControl) return;
		this.isBindData = false;
		this.oFileControl.form.reset();
	},
	doUpload : function(fUploadedCallBack){
		if(!this.oFileControl || !this.isBindData){
			//not trigger the browser action, so return.
			if(FileUploader.isUploadAll() && FileUploader.onUploadAll){
				FileUploader.onUploadAll();
			}
			return;
		}
		this.fUploadedCallBack2 = fUploadedCallBack;
		var sValue = this.oFileControl.value;
		var fileNameValue = sValue.substring(sValue.lastIndexOf("\\")+1);
		var sParams = "fileNameParam=" + this.appendixFileControlId + "&fileNameValue="+encodeURI(fileNameValue);
		var fileForm = this.oFileControl.form;
		fileForm.action = this.uploadDoWithSrc + "?" + sParams;
		fileForm.submit();
	}
});

// destroy the FileUploader cache.
Event.observe(window, 'unload', function(){
	FileUploader.destory();
});
/**-------------------------------------------------------------------------**/

var FileDownloader = Class.create();
Object.extend(FileDownloader, {
	download : function(sUrl){
		var frm = (top.actualTop||top).$('iframe4download');
		if(!frm) {
			frm = (top.actualTop||top).document.createElement('IFRAME');
			frm.id = "iframe4download";
			frm.style.display = 'none';
			(top.actualTop||top).document.body.appendChild(frm);
		}
		frm.src = sUrl;		
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
Ext.apply(PageContext, {
	contextMenuEnable : true,
	gridDraggable : true,
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
				var sCurrId = row.getAttribute("docid");
				var aSelectedIds = wcm.Grid.getRowIds(true);
				if(!aSelectedIds.include(sCurrId)) aSelectedIds.push(sCurrId);			
				Object.extend(top.DragAcross,{
					ObjectType : 605 ,
					FolderId :  row.getAttribute("channelid"),
					ObjectId : sCurrId,
					ObjectIds : aSelectedIds
				});
				if(!PageContext.CanSort){
					return "当前记录列表不支持排序";
				}
				if(PageContext.params["OrderBy"]){
					return "自动排序列表不支持手动排序";
				}
				if(!this.sortable){
					return "当前记录没有权限排序";
				}
				return String.format("[记录-{0}]",sCurrId);
			},
			_isSortable : function(row){
				if(!PageContext.CanSort || PageContext.params["OrderBy"]) return false;
				var sRight = row.dom.getAttribute("right", 2);
				if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 62)){
					return false;
				}
				return true;
			},
			_move : function(srcRow, iPosition, dstRow, eTargetMore){
				//iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
				if(!PageContext.CanSort&&PageContext.params["OrderBy"]) return false;
				var bCurrTopped = srcRow.getAttribute("isTopped")=='true';
				var bTargetTopped = dstRow.getAttribute("isTopped")=='true';
				var docid = srcRow.getAttribute('docid');
				var rowId = srcRow.getAttribute('rowId');
				var targetDocId = dstRow.getAttribute('docid');
				if(bCurrTopped!=bTargetTopped){//当前行与目标行，一行是置顶，一行是非置顶
					var bFixedUp = true;
					if(iPosition==0){//有前一行，插入到目标行之后的情况
						if(!bCurrTopped&&eTargetMore!=null){//非置顶行判断前一行和后一行
							//若下一行为非置顶行，则不交叉
							//反之，则交叉
							var bTargetMoreTopped = eTargetMore.getAttribute("isTopped")=='true';
							if(!bTargetMoreTopped){
								//用后一行的数据，表示插入到它之前
								targetDocId = eTargetMore.getAttribute('docid');
								iPosition = (iPosition==0)?1:0;
								bFixedUp = false;
							}
						}
						else if(!bCurrTopped&&eTargetMore==null){//非置顶行上一行为置顶行，下一行无
							//相当于至少有n-1个置顶行,而被拖动的那行是非置顶行
							//所以非置顶行本身未移动,此种情况其实不需要考虑
							//考虑的话不计为交叉
							bFixedUp = false;
						}
						else if(bCurrTopped){//置顶行的上一行为非置顶行,必然交叉
							bFixedUp = true;
						}
					}
					else{//无前一行，但有后一行，插入到目标行之前的情况
						if(!bCurrTopped){//当前行非置顶，插在置顶行之前必然交叉
							bFixedUp = true;
						}
						else{//当前行置顶，插在非置顶行之前必然不交叉
							//但此种情况可以不考虑
							//当前置顶行拖动后前无置顶行(在第一行)，后无置顶行
							//可以知道当前只有一个置顶行，且没有交换位置
							//不应该到这里来
							bFixedUp = false;
							//若来到这里就不能按置顶交换顺序的方式处理了
							bCurrTopped = false;
						}
					}
					if(bFixedUp){
						Ext.Msg.$timeAlert(wcm.LANG.metaviewdata_list_base_5000 || '置顶记录与非置顶记录间不能交叉排序.',5);
						return false;
					}
				}
				if(!confirm(wcm.LANG.metaviewdata_list_base_6000 || '您确定要调整记录的顺序？')) return false;
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
					BasicDataHelper.call('wcm6_viewdocument', 'changeOrder', oPostData, false, function(){
						PageContext.updateCurrRows(rowId);
					}, function(trans,json){
						wcm.FaultDialog.show({
							code		: $v(json,'fault.code'),
							message		: $v(json,'fault.message'),
							detail		: $v(json,'fault.detail'),
							suggestion  : $v(json,'fault.suggestion')
						},wcm.LANG.metaviewdata_list_base_7000 ||  '与服务器交互时出现错误' , function(){
							PageContext.updateCurrRows(rowId);
						});
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
									this.dd.hintInTree = String.format("[引用多篇记录:{0}]",top.DragAcross.ObjectIds);
								}
								else{
									this.dd.hintInTree = String.format("[记录-{0}]",top.DragAcross.ObjectId);
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
									Ext.Msg.report(_json,wcm.LANG.metaviewdata_list_base_10000 ||  '记录引用结果', function(){
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
