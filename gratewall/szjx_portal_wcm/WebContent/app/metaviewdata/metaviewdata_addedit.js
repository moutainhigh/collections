var m_Templates = {
	topset_table : [
		'<table border=0 cellspacing=1 cellpadding=0 style="width:100%;table-layout:fixed;background:gray;">',
		'<thead>',
			'<tr bgcolor="#CCCCCC" align=center valign=middle>',
				'<td width="32">', wcm.LANG.METAVIEWDATA_118 || "序号",'</td>',
				'<td>', wcm.LANG.METAVIEWDATA_119 || "文档标题",'</td>',
				'<td width="40">', wcm.LANG.METAVIEWDATA_120 || "排序",'</td>',
			'</tr>',
		'</thead>',
		'<tbody id="topset_order_tbody">{0}</tbody>',
		'</table>'
	].join(''),
	topset_item_tr : [
		'<tr bgcolor="#FFFFFF" align=center valign=middle _docid="{0}" _doctitle="{2}">',
			'<td>{1}</td>',
			'<td align=left title="{0}-{2}"><div style="overflow:hidden">{2}</div></td>',
			'<td>&nbsp;</td>',
		'</tr>'
	].join(''),
	topset_curr_tr : [
		'<tr bgcolor="#FFFFCF" align=center valign=middle _docid="" _currdoc="1">',
			'<td>{0}</td>',
			'<td align=left style="color:red;">',wcm.LANG.METAVIEWDATA_121 || "--当前文档--",'</td>',
			'<td>',
				'<span class="topset_up" title="', wcm.LANG.METAVIEWDATA_122 || "上移",  '" _action="topsetUp">&nbsp;</span>',
				'<span class="topset_down" title="', wcm.LANG.METAVIEWDATA_123 || "下移", '" _action="topsetDown">&nbsp;</span>',
			'</td>',
		'</tr>'
	].join('')
}

Ext.ns('PgC');

Ext.apply(PgC, {
/*---------------- 获取置顶信息 ------------------*/
	getTopsetInfo : function(){
		if(!PgC.TopFlag || PgC.TopFlag=='0'){
			return {
				TopFlag : PgC.TopFlag,
				Position : 0,
				TargetDocumentId : 0
			};
		}
		var rows = $('topset_order_tbody').rows;
		var nCurrIndex = -1;
		for(var i=0,n=rows.length; i<n; i++){
			if(rows[i].getAttribute("_currdoc", 2)){
				nCurrIndex = i;
				break;
			}
		}
		var nPosition = 0;
		var nTargetDocId = 0;
		if(nCurrIndex==rows.length-1 && nCurrIndex!=0){
			var beforeRow = rows[nCurrIndex-1];
			nPosition = 0;
			nTargetDocId = beforeRow.getAttribute("_docid", 2);
		}else if(nCurrIndex!=rows.length-1){
			var afterRow = rows[nCurrIndex+1];
			nPosition = 1;
			nTargetDocId = afterRow.getAttribute("_docid", 2);
		}
		if(PgC.TopFlag == 2){
			PgC.TopFlag =3;
		}
		return {
			TopFlag : PgC.TopFlag,
			Position : nPosition,
			TargetDocumentId : nTargetDocId
		};
	},
	makeCurrDocInTopList : function(){
		if(PgC.DocInTopList)return;
		PgC.DocInTopList = true;
		PgC._renderTopList();
	},
	_renderTopList : function(index){
		index = index || 0;
		var rows = $('topset_order_tbody').rows;
		if(index<0 || index>=rows.length)return;
		var items = [];
		var infos = [];
		for(var i=0,n=rows.length; i<n; i++){
			var recid = rows[i].getAttribute("_docid", 2);
			if(!recid)continue;
			var doctitle = rows[i].getAttribute("_doctitle", 2);
			infos.push({recid:recid, doctitle:doctitle});
		}
		for(var i=0,n=infos.length; i<n; i++){
			var myIdx = i+1;
			if(i>=index)myIdx=i+2;
			items.push(String.format(m_Templates.topset_item_tr, infos[i].recid,
				myIdx, infos[i].doctitle));
		}
		items.splice(index, 0, String.format(m_Templates.topset_curr_tr, index+1));
		var html = String.format(m_Templates.topset_table, items.join(''));
		Element.update('topset_order_table', html);
	},
	topsetUp : function(event, target, actionItem){
		var row = actionItem.parentNode.parentNode;
		PgC._renderTopList(row.rowIndex-2);
	},
	topsetDown : function(event, target, actionItem){
		var row = actionItem.parentNode.parentNode;
		PgC._renderTopList(row.rowIndex);
	},
	topset : function(){
		var choises = document.getElementsByName('TopFlag');
		var value = 0;
		for(var i=0, n=choises.length; i<n; i++){
			if(choises[i].checked){
				value = choises[i].value;
				break;
			}
		}
		PgC.TopFlag = value;
		if(value=='0'){
			Element.hide('topset_order');
			Element.hide('pri_set_deadline');
		}else{
			Element.show('topset_order');
			Element.hide('pri_set_deadline');
		}
		if(value=='1'){
			Element.show('pri_set_deadline');
		}
		PgC.makeCurrDocInTopList();
	},
	getActionItem : function(target){
		while(target!=null&&target.tagName!='BODY'){
			if(target.getAttribute('_action', 2)!=null)return target;
			target = target.parentNode;
		}
		return null;
	},
	init : function(){
		Ext.getBody().on('click', function(event, target){
			var actionItem = PgC.getActionItem(target);
			if(actionItem==null)return;
			var action = actionItem.getAttribute('_action', 2);
			if(!action || !PgC[action])return;
			PgC[action].apply(PgC, [event, target, actionItem]);
		});
	}
});

function init(){	
	autoJusty();

	//lock handle
	lockHandle();

	//init validation.
	ValidationHelper.registerValidations(validations);
	ValidationHelper.addValidListener(function(){
		wcmXCom.get('btnSave').enable();		
	});
	ValidationHelper.addInvalidListener(function(){
		wcmXCom.get('btnSave').disable();
	});
	ValidationHelper.initValidation();
}

function autoJusty(){
	var cbSelf = wcm.CrashBoarder.get(window).getCrashBoard();
	if(!cbSelf) return;
	try{
		var box = Ext.isStrict?document.documentElement:document.body;
		var minWidth = 700, minHeight = 250, maxWidth = 900, maxHeight = 600;
		var realWidth = box.scrollWidth;		
		var realHeight = box.scrollHeight;
		realWidth = realWidth > maxWidth ? maxWidth : (realWidth < minWidth ? minWidth : realWidth);
		realHeight = realHeight > maxHeight ? maxHeight : (realHeight < minHeight ? minHeight : realHeight);		
	}catch(e){
		Ext.Msg.alert(e.message)
	}
	cbSelf.setSize(realWidth+"px",realHeight+"px");		
	box.style.overflowY = 'auto';
	box.style.overflowX = 'hidden';	
	if(Ext.isGecko){//如果IE设上这个,会出现两个滚动条
		document.body.style.overflowY = 'auto';
		document.body.style.overflowX = 'hidden';
	}
	cbSelf.center();
}
var m_Appendixs = null;
function initRelData(){
	var oAppendixs = null;
	try{
		oAppendixs = {
			Type_10:
				Ext.Json.parseXml(
					Ext.Xml.loadXML($('appendix_10').value)),
			Type_20:
				Ext.Json.parseXml(
					Ext.Xml.loadXML($('appendix_20').value)),
			Type_40:
				Ext.Json.parseXml(
					Ext.Xml.loadXML($('appendix_40').value))
		}
	}catch(err){
		oAppendixs = {
			Type_10:{},
			Type_20:{},
			Type_40:{}
		}
	}
	m_Appendixs = oAppendixs;

	//缓存相关文档管理中的数据
	var oRelations = null;	
	var sRelationId = null;
	for(var i=0,len=m_arRelationIds.length;i<len;i++){
		sRelationId = m_arRelationIds[i];		
		try{			
			oRelations=Ext.Json.parseXml(Ext.Xml.loadXML($(sRelationId+"_relations_text").value))
		}catch(err){
			oRelations = {};
		}				
		m_Relations[sRelationId] = oRelations;		
		Element.update($(sRelationId+"_tip_container"),formatRelations(oRelations).html);
	}	
}
var m_sRelationTip = '<a href="viewdata_detail.jsp?objectId={0}" target="_blank" title="id:{0}">{1}</a>';
function formatRelations(_relations){	
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
		sHtml.push(String.format(m_sRelationTip, $v(rel,"RELDOC.ID"), $v(rel,"RELDOC.TITLE")));
		arId.push($v(rel,"RELDOC.ID"));
	}	
	return {ids:arId.join(","),html:sHtml.join("<br>")};
}
function clickInputSel(_inputId,_selId){
	$(_selId).value = $F(_inputId)||"";
	ValidatorHelper.forceValid($(_inputId));
}
function changeInputSel(_inputId,_selId){
	$(_inputId).value = $F(_selId)||"";
	ValidatorHelper.forceValid($(_inputId));
}
/* inputable select related start.*/
function showInputSelectSuggestion(sFieldName){	
	var oOptions = $(sFieldName+"_sel").options;
	if(!oOptions || oOptions.length <= 0) return;		
	var oInput = $(sFieldName);
	var iframe = $(sFieldName + "_iframe");
	var container = $(sFieldName + "_suggestion");

	if(!container){
		//创建iframe遮布
		iframe = document.createElement("iframe");
		iframe.id = sFieldName + '_iframe';
		iframe.className = 'inputSelect_iframe';
		iframe.style.display = 'none';
		iframe.src = 'about:blank';
		oInput.parentNode.insertBefore(iframe, oInput);
		
		//创建div容器
		container = document.createElement("div");
		container.id = sFieldName + '_suggestion';
		container.className = 'inputSelect_suggestion';
		container.tabindex="1";
		container.style.outline = 0;
		container.style.display = 'none';		
		oInput.parentNode.insertBefore(container, oInput);
		
		//设置div内容
		var sHtml = "";
		var oOption = null;
		for (var i = 0,len=oOptions.length; i < len; i++){			
			oOption = oOptions[i];
			sHtml += "<div triggerEvent='true' value='" + oOption.value + "' unselectable='on'>";
			sHtml += oOption.innerHTML;
			sHtml += "</div>";
		}
		container.innerHTML = sHtml;

		//邦定div容器的一些事件
		Event.observe(container, 'mouseover', function(event){
			var event = window.event || event;
			var srcElement = Event.element(event);
			if(!srcElement.getAttribute("triggerEvent")){
				return;
			}
			setActiveItem(sFieldName, srcElement)
		});
		Event.observe(container, 'click', function(event){			
			var event = window.event || event;
			var srcElement = Event.element(event);			
			if(srcElement.getAttribute("triggerEvent")){
				oInput.value  = srcElement.getAttribute("value");				
				hideInputSelectSuggestion(sFieldName);
			}else{
				oInput.focus();
			}
		});
		Event.observe(container, 'blur', function(event){
			hideInputSelectSuggestion(sFieldName);
		});
	}

	//定位到指定的item
	var tempNode = Element.first(container);
	while(tempNode){
		var sValue = tempNode.getAttribute("value");
		if(sValue.startsWith(oInput.value)){
			setActiveItem(sFieldName, tempNode);
			container.scrollTop = tempNode.offsetTop;
			break;
		}
		tempNode = Element.next(tempNode);
	}
	
	container.style.display = '';
	iframe.style.display = '';
	//set position
	Position.clone(oInput, container, {setHeight:false, offsetTop:oInput.offsetHeight});
	Position.clone(container, iframe, {offsetTop:container.scrollTop});
}

function hideInputSelectSuggestion(sFieldName){
	var container = $(sFieldName + "_suggestion");
	if(container){
		container.style.display = 'none';
	}
	var iframe = $(sFieldName + "_iframe");
	if(iframe){
		iframe.style.display = 'none';
	}
}

function setActiveItem(sFieldName, srcElement){
	if(window['lastActiveRowCache'] && window['lastActiveRowCache'][sFieldName]){
		Element.removeClassName(window['lastActiveRowCache'][sFieldName], 'activeRow');
	}
	if(srcElement == null){
		srcElement = Element.first($(sFieldName + "_suggestion"));
	}
	if(srcElement == null) return;
	Element.addClassName(srcElement, 'activeRow');
	if(!window['lastActiveRowCache']){
		window['lastActiveRowCache'] = {};
	}
	window['lastActiveRowCache'][sFieldName] = srcElement;
}

function initSuggestionEvents(){
	var sId = null;
	for(var i=0,len=m_SuggestionIds.length;i<len;i++){		
		sId = m_SuggestionIds[i];
		makeSuggestion(sId);
	}
}

function makeSuggestion(sId){		
	Event.observe($(sId),'focus',function(event){
		showInputSelectSuggestion(sId);				
	});
	Event.observe($(sId),'blur',function(event){
		var container = $(sId + "_suggestion");
		if(!container) return;	
		event = window.event || event;				
		if(!Position.within(container,Event.pointerX(event), Event.pointerY(event))){
			hideInputSelectSuggestion(sId);
		}						
	});
	Event.observe($(sId), 'keyup', function(event){			
		event = window.event || event;
		switch(event.keyCode){
			case Event.KEY_RETURN :
				if(window['lastActiveRowCache'] 
						&& window['lastActiveRowCache'][sId]){
					var activeRow = window['lastActiveRowCache'][sId];
					$(sId).value = activeRow.getAttribute("value");
					hideInputSelectSuggestion(sId);
				}
				break;
			case Event.KEY_UP :
			case Event.KEY_DOWN :
				var nextActiveRow = null;
				if(window['lastActiveRowCache'] 
						&& window['lastActiveRowCache'][sId]){
					var lastActiveRow = window['lastActiveRowCache'][sId];							
					nextActiveRow = event.keyCode == Event.KEY_UP ?Element.previous(lastActiveRow):Element.next(lastActiveRow);
				}
				setActiveItem(sId, nextActiveRow);
				var container = $(sId + "_suggestion");
				nextActiveRow = window['lastActiveRowCache'][sId];
				container.scrollTop = nextActiveRow.offsetTop;
				$(sId).value = nextActiveRow.getAttribute("value");
				break;
			default:
				showInputSelectSuggestion(sId);
				break;
		}
	});	
}

Ext.ns("wcm.AppendixFieldMgr");
(function(){
	var m_FileUploaderIds = [];
	var sSuffixForm = "_frm";
	var sSuffixBrowserBtn = "_browser_btn";
	var sSuffixDeleteBtn = "_delete_btn";
	var sSuffixTextId = "_text";		

	var bindEvents = function(sId){
		var sForm = sId + sSuffixForm;
		var sBrowserBtn = sId + sSuffixBrowserBtn;
		var sDeleteBtn = sId + sSuffixDeleteBtn;
		var sTextId = sId + sSuffixTextId;		
		Event.observe(sBrowserBtn, 'change', function(){
			var sValue = $(sBrowserBtn).value;
			var index = sValue.lastIndexOf("\\") + 1;
			$(sTextId).innerHTML = sValue.substr(index);
			Element.show(sDeleteBtn);
		});
		Event.observe(sDeleteBtn, 'click', function(){
			$(sForm).reset();
			$(sId).value = "";
			$(sTextId).innerHTML = "";	
			Element.hide(sDeleteBtn);
		});			
	};

	var index = 0;
	function getNextAppendix(){
		for(len = m_FileUploaderIds.length; index < len;){		
			index++;
			var dom = $(m_FileUploaderIds[index-1] + sSuffixBrowserBtn);
			if(dom.value.trim() != "") return m_FileUploaderIds[index-1];
		}
		return null;
	}

	wcm.AppendixFieldMgr = {
		add : function(sId){
			m_FileUploaderIds.push(sId);
		},
		init : function(){
			for(var i=0, len = m_FileUploaderIds.length; i < len; i++){		
				bindEvents(m_FileUploaderIds[i]);
			}
		},
		upload : function(fCallBack){
			var sId = getNextAppendix();
			if(!sId) {
				if(fCallBack) fCallBack();
				return;
			}
			var sParams = "fileNameParam=" + (sId + sSuffixBrowserBtn) + "&fileNameValue=" + encodeURI($(sId + sSuffixTextId).innerHTML);
			YUIConnect.setForm(sId + sSuffixForm, true, Ext.isSecure);
			YUIConnect.asyncRequest('POST',
				'file_upload_dowith.jsp?'+sParams, {
					"upload" : function(_transport){
						var sResponseText = _transport.responseText;
						eval("var result="+sResponseText);
						if(result["Error"]){
							Ext.Msg.alert(result["Error"]);
							return;
						}
						$(sId).value = result["Message"];
						wcm.AppendixFieldMgr.upload(fCallBack);
					}
				}
			);
		}
	};
})();

function uploadAppendixFields(){
	
	for (var i = 0, length = m_FileUploaderIds.length; i < length; i++){
		var sId = m_FileUploaderIds[i];
		var browserBtn = $(sId+"_delete_btn");
		if(browserBtn.value.trim() == "" || browserBtn.getAttribute("bindata") == '0') continue;

	}
}

/* inputable select related end.*/
var ClickFns = {
	dealWithAppendix : function(){		
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH +
					'document/document_attachments.html?DocChannelId='+ (m_nChannelId||$F("channelIdOfMetaView")),
			title : wcm.LANG.METAVIEWDATA_95 || '附件管理',		
			callback : function(info){
				m_Appendixs = Object.deepClone(info);				
			},
			dialogArguments : m_Appendixs
		});
	},
	selTemplate	: function(event){
		var nChnlId = m_nChannelId || $F("channelIdOfMetaView");
		var params = {channelId:nChnlId,templateType:2,selectType:"radio",templateIds:$F("param_tempid_leafgray")};
		wcm.TemplatSelector.selectTemplate(params,function(_args){
			var sName = _args.selectedNames[0] || "";
			var sId = _args.selectedIds[0] || 0;
			Element.first($("spDetailTemp")).innerText = sName;
			$("param_tempid_leafgray").value = sId;
		});
	},
	openEditor : function(event){
		var sId = Event.element(window.event||event).getAttribute("_relFram");		
		var sEditorId = "simple_editor_" + sId;
		var sURl = WCMConstants.WCM6_PATH + 'editor/simp_editor.html';
		wcm.CrashBoarder.get(sId).show({
				id : sEditorId,
				title : wcm.LANG.METAVIEWDATA_96 || "简易编辑器",
				src : sURl,
				width:'800px',
				height:'450px',
				border:false,
				appendParamsToUrl : false,
				params:{
					'CurrDocId'	:	m_nDocumentId,
					'ChannelId'	:	m_nChannelId,
					Toolbar	: 'MetaData',
					html	: 	_GetContent_($(sId))
				},
				callback : function(params){
					_SetContent_($(sId),params[0]);
				}
		});
	},
	selClassInfo : function(event){
		var srcEl = Event.element(window.event||event);
		var classId = srcEl.getAttribute("_classId");
		var rootId = srcEl.getAttribute("_rootId");
		var classDesc = srcEl.getAttribute("_classDesc");
	    var treeType = srcEl.getAttribute("_treeType");
		var selectedValue = Element.previous(srcEl).value;
		//防止视图分类法字段被修改时打开的是错误的分类法。rootid != -1说明有分类法信息，classId != rootId说明分类法id被修改过。
		if(rootId != -1 && classId != 0 && classId != rootId){
			if(confirm(wcm.LANG.METAVIEWDATA_125 || "分类法id可能被修改过，请注意")){
				selectedValue = "";
			}else{
				return;
			}
		}
		if(classId == 0){
			Ext.Msg.alert(wcm.LANG.METAVIEWDATA_124 || "请先在视图中设置分类法");
			return ;
		}
		var params = {
			objectId:classId,
			objectName:classDesc,
			treeType:treeType,
			selectedValue:selectedValue
		};
		wcm.ClassInfoSelector.selectClassInfoTree(params,function(_args){	
			var arIds = _args.ids || [];			
			var arNames = _args.names||[];
			var arDescs = [];
			for(var i=0,len=arIds.length;i<len;i++){
				arDescs.push(arNames[i]+'['+arIds[i]+']');
			}			
			Element.next(srcEl).innerText = arDescs.join(",");
			Element.previous(srcEl).value = arIds.join(",");			
		});
	},
	selRelations : function(event){
		var sJsonId = Event.element(window.event||event).getAttribute("_jsonId");
		var sURl = WCMConstants.WCM6_PATH + 'document/document_relations.html';
		FloatPanel.open({
			src : sURl,
			title : wcm.LANG.DOCUMENT_PROCESS_17 || '相关文档管理',
			callback : function(info){
				m_Relations[sJsonId] = Object.deepClone(info);
				var formatedRels = formatRelations(m_Relations[sJsonId]);
				Element.update($(sJsonId+"_tip_container"),formatedRels.html);
				$(sJsonId).value = formatedRels.ids;
			},
			dialogArguments : {
				relations : m_Relations[sJsonId],
				CurrDocId : m_nDocumentId,
				CurrChannelId : m_nChannelId||$F("channelIdOfMetaView")
			}
		});	
	},
	browserOtherData : function(event){
		var srcEl = Event.element(window.event||event);
		var sId = srcEl.getAttribute("_referTo");
		var sFieldName = srcEl.getAttribute("_fieldName");
		var sURL = WCMConstants.WCM6_PATH + "metaviewdata/metaviewdata_classic_list_select.html";
		new wcm.CrashBoard({				
				title : wcm.LANG.METAVIEWDATA_97 || "选择数据",
				src : sURL,
				width:'800px',
				height:'450px',
				border:false,
				params:{					
					ViewId:m_nViewId,
					TableName:sId,
					SelectFields: makeSelectFields(sId),
					CurrField:sFieldName
				},
				callback : function(info){
					var sMetaDataId = info["id"];
					if(window.m_oOtherTableFields)
						m_oOtherTableFields[sId].id = sMetaDataId;
					var values = info["values"];							
					var field = null;
					if(!values){
						for(var i = 0,len=m_oOtherTableFields[sId].length; i<len; i++){
							if(! $(sId+"_" + m_oOtherTableFields[sId][i][2].toUpperCase())) continue;
							Element.update($(sId+"_" + m_oOtherTableFields[sId][i][2].toUpperCase()),"");
						}
					}else{
						for(var i=0,len=values.length;i<len;i++){
							field = values[i];
							//如果是隐藏字段，找不到其元素，则继续一下。
							if(! $(sId+"_"+field["name"].toUpperCase())) continue;
							Element.update($(sId+"_"+field["name"].toUpperCase()),field["value"]);
						}
					}
				}
		}).show();		
	}
};
function makeSelectFields(_tableName){
	var fields = m_oOtherTableFields[_tableName]	
	var rst = [];
	for(var i=0,len=fields.length;i<len;i++){
		//此处取的是副表的视图字段名称。
		rst.push(fields[i][2]);
	}	
	return rst.join(",");
}

/*----------------------------------editor start------------------------------------------*/
function SetValueByEditor(_sElement, _sContent){
	var oElement = $(_sElement);
	if(oElement.contentWindow && oElement.contentWindow.setHTML){
		_SetContent_(oElement, _sContent);
		return;
	}
	Event.observe(oElement, 'load', function(oElement, _sContent){
		_SetContent_(oElement, _sContent);
	}.bind(null, oElement, _sContent));
}
function _GetContent_(oElement){
	return oElement.contentWindow.getHTML();	
}
function _SetContent_(oElement, _sContent){
	try{
		oElement.contentWindow.setHTML(_sContent);
	}catch(e){
		//此处为兼容当内容不可编辑时的情况，因为不可编辑页面和可编辑状态用的不是同一个页面
		oElement.contentWindow.document.body.innerHTML = _sContent;
	}
}
function transTxt(_sTxt){
	if(!/<.*>/.test(_sTxt)){
		return _sTxt.replace(/\n/g,'<br>');
	}
	return _sTxt;
}
/*----------------------------------editor end------------------------------------------*/

function getAppendixesXML(iType){
	/**
	 *  <OBJECTS>
	 *		<OBJECT ID="" APPFILE="" SRCFILE="" APPLINKALT="" APPFLAG="" APPDESC=""/>
	 *	</OBJECTS>
	 */
	var appendixs = m_Appendixs['Type_'+iType];
	var arr = Ext.Json.array(appendixs,"APPENDIXES.APPENDIX")||[];
	var sParams = ["APPENDIXID","APPFILE","SRCFILE","APPLINKALT","APPFLAG","APPDESC"];
	var myValue = Ext.Json.value;
	var sRetVal = '<OBJECTS>';
	for(var i=0;i<arr.length;i++){
		var oAppendix = arr[i];
		sRetVal += '<OBJECT';
		for(var j=0;j<sParams.length;j++){
			var sName = sParams[j];
			var sValue = myValue(oAppendix,sName)||'';
			if(sName=='APPENDIXID'){
				if(isNaN(sValue)) sValue = 0;
			}
			if(iType==20&&sName=='APPFILE'){
				sRetVal += ' APPFILE="'+((myValue(oAppendix,'APPFILE.FILENAME')||'')+'').escape4Xml()+'"';
			}
			else if(sName=='APPENDIXID'){
				sRetVal += ' ID="'+sValue+'"';
			}
			else{
				sRetVal += ' '+sName+'="'+(sValue+'').escape4Xml()+'"';
			}
		}
		sRetVal += '/>';
	}
	sRetVal += '</OBJECTS>';
	return sRetVal;
}

function saveData(){
	var thisSelf = this;
	var sObjForm = "objectForm";
	var chnlIdInput = $("channelId");
	if(chnlIdInput.value == 0) chnlIdInput.value = $F("channelIdOfMetaView");

	//处理编辑
	var sEditorId = null;
	for(var i=0,len=m_EditorIds.length;i<len;i++){
		sEditorId = m_EditorIds[i];
		$(sEditorId).value = _GetContent_($(sEditorId+"_frame"))
		//ff下有些取出的是<div>&nbsp;</div>,ie下取出的是<DIV>&nbsp;</DIV>,故在此添加toLowerCase().
		if($(sEditorId).value.toLowerCase() == "<div>&nbsp;</div>"){
			$(sEditorId).value ="";
		}
	}
	//otherTable
	if(window.m_oOtherTableFields){
		for (var sTableName in m_oOtherTableFields){
			var oTableInfo = m_oOtherTableFields[sTableName];
			//if(oTableInfo.id) continue;
			if(!oTableInfo || !oTableInfo.id) continue;
			var oInput = document.createElement("<input type='hidden' name='" + sTableName + "Id'>");
			oInput.value = oTableInfo.id;
			$(sObjForm).appendChild(oInput);
		}
	}

	//保存前空值校验
	var rowFields = document.getElementsByClassName("row");
	if(rowFields && rowFields.length > 0){
		var objectForm = document.getElementById("objectForm");
		
		var elements = new Array();
		var arr = [];
		for(var loop0 = 0; loop0 < rowFields.length; loop0++){
			if(rowFields[loop0].getAttribute("required") != 1) continue;
				
			arr[0] = rowFields[loop0].getElementsByTagName("input");
			arr[1] = rowFields[loop0].getElementsByTagName("select");
			arr[2] = rowFields[loop0].getElementsByTagName("textarea");

			for(var loop1 = 0; loop1 < 3; loop1++){
				if(arr[loop1] && arr[loop1].length > 0) {
					for(var loop2 = 0 ;loop2 < arr[loop1].length ; loop2++){
						elements.push(arr[loop1][loop2]);
					}
				}
			}
			
			if(elements.length > 0){
				for(var loop3 = 0; loop3 < elements.length ; loop3++){
					var bValid = false;
					var flagForRadioOrCheck = false;
					var fieldType = rowFields[loop0].getAttribute("_type");

					//如果是附件类型
					if(fieldType == "8"){
						if(arr[0][0].value.trim() != "" || (arr[0][1].value.trim()) != ""){
							 bValid = true;
						}
					}
					if(bValid) break;
					switch(elements[loop3].tagName){
						case "INPUT" : 
							var sType = elements[loop3].type.toLowerCase();
							if(sType == "radio" || sType == "checkbox"){
								bValid = false;
								var radioOrCheck = document.getElementsByName(elements[loop3].name);
								for (var loop4 = 0; loop4 < radioOrCheck.length; loop4++){
									if(radioOrCheck[loop4].checked){
										bValid = true;
										flagForRadioOrCheck = true;
										break;
									}
								}
							}else {
								bValid =elements[loop3].value.trim() != "";
							}
							break;
						case "SELECT":
						case "TEXTAREA":
							bValid = elements[loop3].value.trim() != "";
							break;
					}
					if(!bValid){
						Ext.Msg.alert("\"" + rowFields[loop0].getAttribute("desc") + "\"" + wcm.LANG.METAVIEWDATA_116 || "不能为空.");
						return false;
					}
					if(bValid && flagForRadioOrCheck){
						break;
					}
				}
			}
			elements = [];
			arr = [];
		}
	}
	
	//trigger the file upload.
	ProcessBar.start(wcm.LANG.METAVIEWDATA_114 || '提交数据');
	wcm.AppendixFieldMgr.upload(function(){		 
		_doSave(thisSelf);
	})
	return false;
}

function _doSave(_thisSelf){
	var aCombine = [];
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	
	//对象自身属性
	var PostDataUtil = com.trs.web2frame.PostData;
	var postdata = PostDataUtil.form("objectForm", function(m){return m;});
	aCombine.push(oHelper.Combine(
		"wcm61_metaviewdata", 
		'saveMetaViewData', 
		postdata
	));		

	//附件	
	var postdata = {
		"DocId" : m_nDocumentId,
		"AppendixType" : 10,
		"AppendixesXML" : getAppendixesXML(10),
		FlowDocId : m_nFlowDocId
	}
	aCombine.push(oHelper.Combine('wcm6_document','saveAppendixes',postdata));
	postdata = {
		"DocId" : m_nDocumentId,
		"AppendixType" : 20,
		"AppendixesXML" : getAppendixesXML(20),
		FlowDocId : m_nFlowDocId
	}
	aCombine.push(oHelper.Combine('wcm6_document','saveAppendixes',postdata));
	postdata = {
		"DocId" : m_nDocumentId,
		"AppendixType" : 40,
		"AppendixesXML" : getAppendixesXML(40),
		FlowDocId : m_nFlowDocId
	}
	aCombine.push(oHelper.Combine('wcm6_document','saveAppendixes',postdata));

	//细览模板
	postdata = {
		"ObjectId" : m_nDocumentId,
		"DetailTemplate" : $F("param_tempid_leafgray") || "0"
	}
	aCombine.push(oHelper.Combine('wcm6_publish','saveDocumentPublishConfig',postdata));

	//置顶信息	if(!PgC.IsCanTop)return;
	var info = PgC.getTopsetInfo();
	if(info.TopFlag == 2){
		info.TopFlag = 3;
	}
	var chnlIdInput = $("channelId");
	if(chnlIdInput.value == 0) chnlIdInput.value = $F("channelIdOfMetaView");
	var postdata = {
		"TopFlag" : info.TopFlag,
		"ChannelId" : chnlIdInput.value,
		"Position" : info.Position,
		"DocumentId" : m_nDocumentId,
		"TargetDocumentId" : info.TargetDocumentId,
		"InvalidTime" : (PgC.TopFlag==1)?$('TopInvalidTime').value:'',
		FlowDocId : getParameter('FlowDocId') || 0
	};
	aCombine.push(oHelper.Combine('wcm6_document','setTopDocument',postdata));



	oHelper.JspMultiCall('metaviewdata_addedit_dowith.jsp', aCombine,function(){
		ProcessBar.exit();
		_thisSelf.notify();
		_thisSelf.close();
	});
	
}


Event.observe(window, 'load', function(){		
	initRelData();
	initSuggestionEvents();
	wcm.AppendixFieldMgr.init();
	PgC.init();
	Event.observe(document.body,'click',function(event){
		var clickFn = Event.element(window.event||event).getAttribute("clickFn");
		if(!clickFn) return;
		ClickFns[clickFn](event);
	});
});

Object.extend(Event,{
	pointerX: function(event) {		
		return event.pageX || (event.clientX +
		  (document.documentElement.scrollLeft || document.body.scrollLeft));
	},
	pointerY: function(event) {
		return event.pageY || (event.clientY +
		  (document.documentElement.scrollTop || document.body.scrollTop));
	}
});

window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG.METAVIEWDATA_100 || '保存',
			cmd : function(){
				saveData.call(this);
				return false;
			},
			id : "btnSave"
		},
		{text : wcm.LANG.METAVIEWDATA_101 || '关闭'}
	]
};

function lockHandle(){	
	LockerUtil.register(m_nDocumentId, 1936280531, true, {
		failToLock : function(_msg, _json){
			wcmXCom.get('btnSave').disable();
			Ext.Msg.$timeAlert('<b>' + (wcm.LANG['TIPS'] || '提示：')+ '</b>' + _msg, 5);
		}
	}, true);
}