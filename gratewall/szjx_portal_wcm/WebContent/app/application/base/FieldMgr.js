Ext.ns('wcm.metadata');

/**
*视图字段的常量定义
*/
wcm.metadata.FieldConstants = {
	/**
	*常用字段类型
	*/
    TYPE_NORMALTEXT : 3,
    TYPE_PASSWORD : 2,
    TYPE_MULTITEXT : 4,
    TYPE_ENUMVALUE : -1,
    TYPE_TRUEORFALSE : 5,
    TYPE_RADIO : 6,
    TYPE_CHECKBOX : 9,
    TYPE_SELECT : 7,
    TYPE_INPUT_SELECT : 15,
    TYPE_SUGGESTION : 17,
    TYPE_TIMESTAMP : 11,
    TYPE_CLASSINFO : 10,
    TYPE_APPENDIX : 8,
    TYPE_SIMPLE_EDITOR : 16,
    TYPE_EDITOR : 12,
    TYPE_RELATEDDOCUMENT : 14,
    TYPE_OTHERTABLE : -100,
    TYPE_SELFDEFINE : 1
};
//shorthand for FieldConstants
wcm.XFieldConstants = wcm.metadata.FieldConstants;


/**
*FieldMgr工厂类
*/
wcm.metadata.FieldMgrFactory = function(){
	//private fields
	var clsCache = {}, objCache = {};

	return {
		/**
		*注册相应的视图字段管理器
		*/
		register : function(fieldType, mgrCls){
			if(Array.isArray(fieldType)){
				for (var i = 0; i < fieldType.length; i++){
					this.register(fieldType[i], mgrCls);
				}
				return;
			}
			clsCache[fieldType] = mgrCls;
		},

		/**
		*获取当前所有已经加载过的视图字段管理器
		*/
		getLoadedMgrs : function(){
			var mgrs = [];
			for (var fieldType in objCache){
				var mgr = objCache[fieldType];
				if(mgr.isFieldMgr) mgrs.push(mgr);
			}
			return mgrs;
		},

		/**
		*获取相应的视图字段管理器,没有加载时不创建
		*/
		findFieldMgr : function(fieldType){
			return objCache[fieldType];
		},

		/**
		*获取相应的视图字段管理器,没有加载时则创建
		*/
		getFieldMgr : function(fieldType){
			var objMgr = objCache[fieldType];
			if(objMgr) return objMgr;
			objMgr = new (clsCache[fieldType] || wcm.metadata.FieldMgr)();
			objCache[fieldType] = objMgr; 
			return objMgr;
		}
	};
}();
//shorthand for FieldMgrFactory
wcm.XFieldMgrFactory = wcm.metadata.FieldMgrFactory;


/**
*抽象视图字段管理器
*/
wcm.metadata.FieldMgr = function(){
	this.init();
}

Ext.extend(wcm.metadata.FieldMgr, wcm.util.Observable, {
	/**
	*标识当前对象是否为FieldMgr
	*/
	isFieldMgr : true,

	init : function(){
		/**
		*保存该类型的所有视图字段相关信息
		*/
		this.fields = [];

		this.addEvents(
			/**
			*在视图字段生成html内容之前触发的事件
			*/
			'beforerender',

			/**
			*在视图字段将生成html添加到dom树之后触发的事件
			*/
			'render'
		);
	},

	/**
	*获取注册的字段信息
	*/
	getFields : function(){
		return this.fields;
	},

	/**
	*注册视图字段信息
	*/
	register : function(fieldName){
		this.getFields().push(fieldName);
	},

	/**
	*视图字段渲染之前的处理
	*/
	beforeRender : function(){
		this.fireEvent('beforerender', this);
	},

	/**
	*初始化单个视图字段的一些交互
	*/
	initFieldActions : function(fieldName){
		//TODO...
	},

	/**
	*渲染单个视图字段
	*/
	renderField : function(fieldName){
		this.initFieldActions(fieldName);
	},

	/**
	*初始化所有视图字段的一些交互
	*/
	initActions : function(){
		//TODO...
	},

	/**
	*视图字段渲染之前的处理
	*/
	render : function(){
		var fields = this.getFields();
		for (var i = 0; i < fields.length; i++){
			this.renderField(fields[i]);
		}
		this.fireEvent('render', this);
		this.initActions();
	},

	//private
	validByRequired : function(sFieldName, sFieldValue){
		var dom = $(sFieldName);
		if(dom.getAttribute("required") == 1){
			if(sFieldValue == null){
				var data = this.getFieldData(sFieldName);
				sFieldValue = data[sFieldName].trim();
			}
			if(sFieldValue.length <= 0){
				Ext.Msg.alert(String.format("{0}不能为空", dom.getAttribute("desc") || ""));
				return false;
			}
		}
		return true;
	},

	//private
	validByValidation : function(sFieldName, sFieldValue){
		var dom = $(sFieldName);
		var sValidation = dom.getAttribute("validation");
		if(sValidation == null) return true;
		var validation = eval('({' + sValidation + '})');
		if(sFieldValue == null){
			var data = this.getFieldData(sFieldName);
			sFieldValue = data[sFieldName].trim();
		}

		//valid required.
		if(validation["required"] == 1 && sFieldValue.length <= 0){
			Ext.Msg.alert(String.format("{0}不能为空",validation["desc"] || ""));
			return false;
		}

		//valid max length.
		var maxLen = validation['max_len'] || 0;
		if(maxLen > 0 && sFieldValue.length > maxLen){
			Ext.Msg.alert(String.format("{0}大于最大长度[{1}]",validation["desc"] || "",maxLen));
			return false;
		}	
		return true;
	},

	/**
	*校验注册的视图字段的合法性
	*/
	valid : function(){
		var fields = this.getFields();
		for (var i = 0; i < fields.length; i++){
			if(!this.validByRequired(fields[i])) return false;
			if(!this.validByValidation(fields[i])) return false;
		}
		return true;
	},
	
	/**
	*获取需要发送的某个字段的数据
	*/
	getFieldData : function(sFieldName){
		var data = {};
		var dom = $(sFieldName);
		data[dom.name] = dom.value;
		return data;
	},

	/**
	*获取需要发送的数据
	*/
	getFieldsData : function(){
		var obj = {};
		var fields = this.getFields();
		for (var i = 0; i < fields.length; i++){
			Ext.apply(obj, this.getFieldData(fields[i]));
		}
		return obj;	
	}
});


/**
*枚举值解析器
*/
wcm.metadata.ItemParser = function(){
	/**
	*item之间的分隔符
	*/
	var itemSeparator = '~';

	/**
	*item内部label和value之间的分隔符
	*/
	var labelValueSeparator = '`';

	return {
		parse : function(sItems){
			if(!sItems) return [];
			var aItems = sItems.split(itemSeparator);
			var result = [];
			for (var i = 0; i < aItems.length; i++){
				var aItem = aItems[i].split(labelValueSeparator);
				result.push([aItem[0], aItem[1] || aItem[0]]);
			}
			return result;
		}
	}
}();


/**
*抽象列表类型视图字段管理器
*/
wcm.metadata.ListMgr = Ext.extend(wcm.metadata.FieldMgr, {
	preElName : '',

	/**
	*当前元素的标记名称
	*/
	tagName : '',

	renderField : function(fieldName){
		var dom = $(fieldName);
		var items = dom.getAttribute('items');
		items = wcm.metadata.ItemParser.parse(items);
		var fieldValue = dom.value;
		var selectedValue = ',' + dom.value + ','; 
		var elName = this.preElName + dom.name;
		var aHtml = [];
		for (var i = 0; i < items.length; i++){
			var sId = elName + '_' + i;
			aHtml.push( 
				'<span class="', this.tagName,  '-item">',
					'<input type="', this.tagName, '"',
						' name="', elName, '"',
						' id="', sId,  '"',
						selectedValue.indexOf(',' + items[i][1] + ',') >= 0 ? ' checked="checked"' : '',
						' value="', items[i][1] + '"',
					' />',
					'<label for="', sId, '">', items[i][0], '</label>',
				'</span>'
			);
		}
		Element.update(dom.getAttribute('container'), aHtml.join(""));
		wcm.metadata.ListMgr.superclass.renderField.apply(this, arguments);
	},

	/**
	*获取需要发送的数据
	*/
	getFieldData : function(sFieldName){
		var dom = $(sFieldName);
		var elName = this.preElName + dom.name;
		var doms = document.getElementsByName(elName);
		var selected = [];
		for (var i = 0; i < doms.length; i++){
			if(doms[i].checked) selected.push(doms[i].value);
		}
		var data = {};
		data[dom.name] = selected.join(",");
		return data;
	}
});


/**
*单选框视图字段管理器
*/
wcm.metadata.RadioMgr = Ext.extend(wcm.metadata.ListMgr, {
	preElName : 'rdo_',
	tagName : 'radio'
});
wcm.metadata.FieldMgrFactory.register(
	[wcm.metadata.FieldConstants.TYPE_RADIO, wcm.metadata.FieldConstants.TYPE_TRUEORFALSE],
	wcm.metadata.RadioMgr
);


/**
*多选框视图字段管理器
*/
wcm.metadata.CheckboxMgr = Ext.extend(wcm.metadata.ListMgr, {
	preElName : 'cbx_',
	tagName : 'checkbox'
});
wcm.metadata.FieldMgrFactory.register(
	wcm.metadata.FieldConstants.TYPE_CHECKBOX, 
	wcm.metadata.CheckboxMgr
);


/**
*下拉列表视图字段管理器
*/
wcm.metadata.SelectMgr = Ext.extend(wcm.metadata.FieldMgr, {
	preElName : 'sel_',
	template : '<option value="{0}"{2}>{1}</option>', 

	/**
	*获取需要发送的数据
	*/
	getFieldData : function(sFieldName){
		var dom = $(sFieldName);
		var elName = this.preElName + dom.name;
		var sel = $(elName);
		var data = {};
		data[dom.name] = sel.value;
		return data;
	},

	renderField : function(fieldName){
		var dom = $(fieldName);
		var items = dom.getAttribute('items');
		items = wcm.metadata.ItemParser.parse(items);
		var selectedValue = ',' + dom.value + ','; 
		var elName = this.preElName + dom.name;
		var aHtml = ['<select name="', elName, '" id="', elName, '">'];
		if(dom.getAttribute("hasBlank")){
			aHtml.push('<option value="">--请选择--</option>');
		}
		for (var i = 0; i < items.length; i++){
			var bSelected = selectedValue.indexOf(',' + items[i][1] + ',') >= 0;
			var strSelected = bSelected ? ' selected="selected"' : '';
			aHtml.push(String.format(this.template, items[i][1], items[i][0], strSelected));
		}
		aHtml.push('</select>');
		Element.update(dom.getAttribute('container'), aHtml.join(""));
		wcm.metadata.SelectMgr.superclass.renderField.apply(this, arguments);
	}
});
wcm.metadata.FieldMgrFactory.register(
	wcm.metadata.FieldConstants.TYPE_SELECT, 
	wcm.metadata.SelectMgr
);


/**
*编辑器视图字段管理器
*/
wcm.metadata.EditorMgr = Ext.extend(wcm.metadata.FieldMgr, {
	showEditor : function(target, event){
		//TODO...
		alert('open a editor window.');
	},
	initActions : function(){
		wcm.metadata.EditorMgr.superclass.initActions.apply(this, arguments);
		wcm.ClickEventHandler.register('showEditor', this.showEditor, this);
	},
	/**
	*获取需要发送的数据
	*/
	getFieldData : function(sFieldName){
		var dom = $(sFieldName);
		var sFieldValue = wcm.metadata.EditorContentHandler.getContent(dom.name + "_frame");
		if(sFieldValue == '<DIV>&nbsp;</DIV>'){
			sFieldValue = '';
		}
		var data = {};
		data[dom.name] = sFieldValue;
		return data;
	},
	renderField : function(fieldName){
		var dom = $(fieldName);
		wcm.metadata.EditorContentHandler.setContent(dom.name + "_frame", dom.value);
		wcm.metadata.EditorMgr.superclass.renderField.apply(this, arguments);
	}
});
wcm.metadata.FieldMgrFactory.register(
	[wcm.metadata.FieldConstants.TYPE_SIMPLE_EDITOR, wcm.metadata.FieldConstants.TYPE_EDITOR],
	wcm.metadata.EditorMgr
);


/**
*编辑器控件管理器
*/
wcm.metadata.EditorContentHandler = function(){
	var _SetContent_ = function(dom, _sContent){
		if(dom.contentWindow.setHTML){
			dom.contentWindow.setHTML(_sContent);
		}else{
			dom.contentWindow.document.body.innerHTML = _sContent;
		}
	}

	return {
		/**
		*设置编辑器控件的内容
		*/
		setContent : function(fieldName, _sContent){
			var dom = $(fieldName);
			if(dom.tagName == "IFRAME"){
				if(dom.readyState){
					if(dom.readyState != 'complete'){
						dom.onreadystatechange = function(){
							try{
								if(dom.readyState == 'complete'){
									dom.onreadystatechange = null;
									_SetContent_(dom, _sContent);
								}
							}catch(err){
								alert(err.message);
							}
						};
					}else{
						try{
							_SetContent_(dom, _sContent);
						}catch(error){
							alert(error.message);
						}
					}
				}else{
					try{	
						var win = dom.contentWindow;
						//页面可能没加载完则，_setContent_方法不存在
						if(win && win.document.body){
							_SetContent_(dom, _sContent);
						}else{
							//iframe加载完后开始赋值。
							dom.onload = function(){
								_SetContent_(dom, _sContent);
							}
						}
					}catch(error){

					}
				}
			}
		},

		/**
		*获取编辑器控件的内容
		*/
		getContent : function(fieldName){
			try{
				var dom = $(fieldName);
				if(dom.contentWindow.getHTML){
					return dom.contentWindow.getHTML();	
				}else{
					return dom.contentWindow.document.body.innerHTML;
				}
			}catch(error){
				//just skip it.
			}
		}
	};
}();


/**
*分类法视图字段管理器
*/
wcm.metadata.ClassInfoMgr = Ext.extend(wcm.metadata.FieldMgr, {
	selectClassInfo : function(target, event){
		var sFieldName = target.getAttribute("related");
		var dom = $(sFieldName);
		var classId = dom.value;
		var rootId = dom.getAttribute("rootId");
	    var treeType = dom.getAttribute("treeType");
		if(rootId == 0){
			Ext.Msg.alert(wcm.LANG.METAVIEWDATA_124 || "请先在视图中设置分类法");
			return ;
		}
		var params = {
			objectId:rootId,
			treeType:treeType,
			selectedValue:classId
		};
		wcm.ClassInfoSelector.selectClassInfoTree(params, function(_args){	
			var arIds = _args.ids || [];			
			var arNames = _args.names||[];
			var arDescs = [];
			for(var i=0,len=arIds.length;i<len;i++){
				arDescs.push(arNames[i]+'['+arIds[i]+']');
			}	
			var dom = $(sFieldName);
			dom.value = arIds.join(",");	
			Element.update(dom.getAttribute("container"), arDescs.join(","));
		});
	},
	initActions : function(){
		wcm.metadata.ClassInfoMgr.superclass.initActions.apply(this, arguments);
		wcm.ClickEventHandler.register('selectClassInfo', this.selectClassInfo, this);
	}
});
wcm.metadata.FieldMgrFactory.register(
	wcm.metadata.FieldConstants.TYPE_CLASSINFO,
	wcm.metadata.ClassInfoMgr
);


/**
*相关文档视图字段管理器
*/
wcm.metadata.RelatedDocumentMgr = Ext.extend(wcm.metadata.FieldMgr, {
	template : [
		"<li class='relateddoc-item'>",
			"<a href='viewdata_detail.jsp?objectId={0}' target='_blank' title='{0}' channelId='{2}'>{1}</a>",
		"</li>"
	].join(""),
	/**
	*获取某相关文档字段的json表示
	*/
	jsonRelatedDocs : function(sFieldName){
		var box = $(sFieldName + "-box");
		var doms = box.getElementsByTagName("a");
		var json = {
			RELATIONS : {
				RELATION : []
			}
		};
		var relDocs = json['RELATIONS']['RELATION'];
		for (var i = 0; i < doms.length; i++){
			relDocs.push({
				RELDOC : {
					ID : doms[i].getAttribute("title"),
					CHANNELID : doms[i].getAttribute("channelId"),
					TITLE : doms[i].innerHTML
				}
			});
		}
		return json;
	},
	/**
	*将相关文档字段的xml串信息转成html.
	*/
	htmlRelatedDocs : function(_relations){
		if(!_relations) return{ids:"",html:""};
		var rels = $v(_relations, "RELATIONS.RELATION");	
		if(rels==null || rels==false){
			_relations["RELATIONS"] = _relations["RELATIONS"] || {};
			rels = _relations["RELATIONS"]["RELATION"] = [];
		}else if(!Array.isArray(rels)){
			_relations["RELATIONS"] = _relations["RELATIONS"] || {};
			var tmpArr = _relations["RELATIONS"]["RELATION"] = [];
			tmpArr.push(rels);
			rels = tmpArr;
		}	
		var sHtml = [];
		var arId = [];
		var rel = null;
		for(var i=0,len=rels.length;i<len;i++){	
			rel = rels[i];		
			sHtml.push(String.format(this.template, $v(rel,"RELDOC.ID"), $transHtml($v(rel,"RELDOC.TITLE")), $v(rel,"RELDOC.CHANNELID")));
			arId.push($v(rel,"RELDOC.ID"));
		}	
		return {ids:arId.join(","),html:sHtml.join("")};		
	},
	selectRelatedDocs : function(target, event){
		var sFieldName = target.getAttribute("related");
		var dom = $(sFieldName);
		var caller = this;
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'document/document_relations.html',
			title : wcm.LANG.DOCUMENT_PROCESS_17 || '相关文档管理',
			callback : function(info){
				var formatedRels = caller.htmlRelatedDocs(Object.deepClone(info));
				Element.update($(sFieldName+"-box"), formatedRels.html);
				$(sFieldName).value = formatedRels.ids;
			},
			dialogArguments : {
				relations : this.jsonRelatedDocs(sFieldName),
				CurrDocId : oXMetaViewData.getObjectId(),
				CurrChannelId : oXMetaViewData.getChannelId()
			}
		});
	},
	initActions : function(){
		wcm.metadata.RelatedDocumentMgr.superclass.initActions.apply(this, arguments);
		wcm.ClickEventHandler.register('selectRelatedDocs', this.selectRelatedDocs, this);
	}
});
wcm.metadata.FieldMgrFactory.register(
	wcm.metadata.FieldConstants.TYPE_RELATEDDOCUMENT,
	wcm.metadata.RelatedDocumentMgr
);

	
/**
*多表视图其它表视图字段管理器
*/
wcm.metadata.OtherTableMgr = Ext.extend(wcm.metadata.FieldMgr, {
	/**
	*(_sTableName : _sDBFieldName, _sAnotherName, _sFieldName)
	*记录_sTableName和fieldName的一个映射
	*/
	init : function(){
		wcm.metadata.OtherTableMgr.superclass.init.apply(this, arguments);
		this.tablesCache = {};
	},
	register : function(fieldName){
		wcm.metadata.OtherTableMgr.superclass.register.apply(this, arguments);
		var dom = $(fieldName);
		var tableName = dom.getAttribute("tableName");
		var tableCache = this.tablesCache[tableName];
		if(!tableCache){
			this.tablesCache[tableName] = tableCache = [];
		}
		tableCache.push([dom.getAttribute("dbFieldName"), dom.getAttribute("anotherName"), fieldName]);
	},
	/**
	*获取需要发送的数据
	*/
	getFieldData : function(sFieldName){
		//没有修改多表数据
		var dom = $(sFieldName);
		var sTableName = dom.getAttribute("tableName");
		if(this.tablesCache[sTableName].id == null) return {};
		var data = {};
		data[sTableName+"ID"] = this.tablesCache[sTableName].id;
		return data;
	},
	/**
	*校验注册的视图字段的合法性
	*/
	valid : function(){
		var fields = this.getFields();
		for (var i = 0; i < fields.length; i++){
			var sFieldValue = $(fields[i]).value.trim();
			if(!this.validByRequired(fields[i], sFieldValue)) return false;
			if(!this.validByValidation(fields[i], sFieldValue)) return false;
		}
		return true;
	},
	//private, 此处取的是副表的视图字段名称。
	makeSelectFields : function (sTableName){
		var fields = this.tablesCache[sTableName]	
		var rst = [];
		for(var i=0,len=fields.length;i<len;i++){
			rst.push(fields[i][2]);
		}	
		return rst.join(",");
	},
	selectOtherTable : function(target, event){
		var sFieldName = target.getAttribute("related");
		var dom = $(sFieldName);
		var sTableName = dom.getAttribute("tableName");
		var sURL = WCMConstants.WCM6_PATH + "metaviewdata/metaviewdata_classic_list_select.html";
		var caller = this;
		new wcm.CrashBoard({				
				title : wcm.LANG.METAVIEWDATA_97 || "选择数据",
				src : sURL,
				width:'800px',
				height:'450px',
				border:false,
				params:{					
					ViewId:oXMetaViewData.getViewId(),
					TableName:sTableName,
					SelectFields: this.makeSelectFields(sTableName),
					CurrField:sFieldName
				},
				callback : function(info){
					var tableCache = caller.tablesCache[sTableName];
					tableCache.id = info["id"] || 0;
					var fields = info["values"];
					if(fields){
						for(var i=0,len=fields.length;i<len;i++){
							var dom = $(fields[i]["name"] + "-text");
							if(!dom) continue;
							$(fields[i]["name"]).value = fields[i]["value"];
							Element.update(dom, fields[i]["value"]);
						}
					}else{
						for(var i = 0,len=tableCache.length; i<len; i++){
							$(tableCache[i][2]).value = "";
							Element.update(tableCache[i][2] + "-text", "");
						}
					}
				}
		}).show();				
	},
	initActions : function(){
		wcm.metadata.OtherTableMgr.superclass.initActions.apply(this, arguments);
		wcm.ClickEventHandler.register('selectOtherTable', this.selectOtherTable, this);
	}
});
wcm.XFieldMgrFactory.register(wcm.XFieldConstants.TYPE_OTHERTABLE, wcm.metadata.OtherTableMgr);


/**
*附件视图字段管理器
*/
wcm.metadata.AppendixMgr = Ext.extend(wcm.metadata.FieldMgr, {
	//private some id suffix.
	'suf-form' : '_frm',
	'suf-browser-btn' : '_browser_btn',
	'suf-delete-btn' : '_delete_btn',
	'suf-text' : '_text',

	//private, the appendix index
	index : 0,

	//判断附件是否已经全部上传完成
	isUploadAll : function(){
		var fields = this.getFields();
		return fields.length <= this.index;
	},
	
	//private
	getNextAppendix : function(){
		var fields = this.getFields();
		var dom = null;
		for (var index = this.index; index < fields.length; index++){
			dom = $(fields[index] + this['suf-browser-btn']);
			if(dom.value.trim() != "") break;
		}
		//返回需要上传的附件
		this.index = index + 1;
		return fields[index];
	},
	
	//init events for Appendix Field.
	initFieldActions : function(sFieldName){
		wcm.metadata.AppendixMgr.superclass.initActions.apply(this, arguments);
		var sForm = sFieldName + this['suf-form'];
		var sBrowserBtn = sFieldName + this['suf-browser-btn'];
		var sDeleteBtn = sFieldName + this['suf-delete-btn'];
		var sTextId = sFieldName + this['suf-text'];		
		var caller = this;

		//bind event for browser button.
		Event.observe(sBrowserBtn, 'change', function(){
			var sValue = $(sBrowserBtn).value;
			var index = sValue.lastIndexOf("\\") + 1;
			$(sTextId).innerHTML = sValue.substr(index);
			$(sFieldName).value = "temp";
			Element.show(sDeleteBtn);
			this.index = 0;
		});

		//bind event for delete button.
		Event.observe(sDeleteBtn, 'click', function(){
			$(sForm).reset();
			$(sFieldName).value = "";
			$(sTextId).innerHTML = "";	
			Element.hide(sDeleteBtn);
			this.index = 0;
		});			
	},
	upload : function(fSuccess, fFailure){
		var sFieldName = this.getNextAppendix();
		if(!sFieldName) {
			if(fSuccess) fSuccess();
			return;
		}
		var sParams = "fileNameParam=" + $(sFieldName + this['suf-browser-btn']).name;
		sParams += "&fileNameValue=" + encodeURIComponent($(sFieldName + this['suf-text']).innerHTML);
		YUIConnect.setForm(sFieldName + this['suf-form'], true, Ext.isSecure);
		var caller = this;
		YUIConnect.asyncRequest('POST',
			'../base/file_upload_dowith.jsp?'+sParams, {
				"upload" : function(_transport){
					var sResponseText = _transport.responseText;
					eval("var result="+sResponseText);
					if(result["Error"]){
						fFailure(result["Error"]);
						return;
					}
					$(sFieldName).value = result["Message"];
					Element.update(sFieldName + caller['suf-text'], result["Message"]);
					$(sFieldName + caller['suf-form']).reset();//清空form，防止上传出错时，下次再次上传
					caller.upload(fSuccess, fFailure);
				}
			}
		);
	}
});
wcm.XFieldMgrFactory.register(wcm.XFieldConstants.TYPE_APPENDIX, wcm.metadata.AppendixMgr);


/**
*整个页面的click事件管理器
*/
wcm.ClickEventHandler = function(){
	//已经注册的click处理缓存
	var cache = {};

	//需要执行click处理的dom属性标识名
	var fnAttrName = 'clickFn';

	//获取感兴趣（带有clickFn属性）的dom结点
	var getTarget = function(dom){
		while(dom && dom.tagName != 'BODY'){
			if(!dom.getAttribute) break;
			var fnAttrValue = dom.getAttribute(fnAttrName);
			if(fnAttrValue) return dom;
			dom = dom.parentNode;
		}
		return null;
	};

	return {
		/**
		*click事件分发处理
		*/
		dispatch : function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var target = getTarget(srcElement);
			if(!target) return;
			var fnAttrValue = target.getAttribute(fnAttrName).toUpperCase();
			var item = cache[fnAttrValue];
			if(!item) return;
			item[0].call(item[1] || window, target, event);			
		},
		
		/**
		*注册新的click事件处理
		*/
		register : function(key, handler, scope){
			cache[key.toUpperCase()] = [handler, scope];
		}
	}
}();

/**
*整个页面的click事件入口点
*/
Event.observe(document, 'click', wcm.ClickEventHandler.dispatch.bind(wcm.ClickEventHandler));
