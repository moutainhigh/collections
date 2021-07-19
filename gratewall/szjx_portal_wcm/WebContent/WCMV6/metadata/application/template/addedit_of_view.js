Object.extend(ViewDataEditor,{
	afterInitData : function(){
		var oDivs = $(this.objectContainer).getElementsByTagName("div");
		for (var i = 0, length = oDivs.length; i < length; i++){
			var oDiv = oDivs[i];			
			var elements = Form.getElements(oDiv);
			this.initDefaultValue(oDiv, elements);
			this.initHiddenField(oDiv, elements);
			this.initNotEdit(oDiv, elements);
		}
	},

	//初始化控件的默认值
	initDefaultValue : function(oContainer, aElements){
		if(this.objectId != 0) return;
		var defaultValue = oContainer.getAttribute("_defaultValue");
		if(defaultValue == undefined || defaultValue == "") return;
		for (var j = 0; j < aElements.length; j++){
			var element = aElements[j];
			if(element.tagName.toUpperCase() == "INPUT"){
				var sName = element.name || element.id;
				if(window.DateDefaultValue && window.DateDefaultValue[sName]){//date controls
					if(defaultValue.startsWith('$sysdate')){
						var date = new Date();
						date.setDate(eval(defaultValue.replace('$sysdate', date.getDate())));
						defaultValue = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
					}
					element.value = defaultValue;
					continue;
				}
				//deal with classinfo
				if(element.getAttribute("_type") == 'classInfo'){
					var plainElement = $(element.name + HTMLElementParser.classInfoTextIdSuffix);
					if(plainElement){
						Element.update(plainElement, defaultValue);
					}
				}
				switch(element.type.toUpperCase()){
					case "CHECKBOX":
					case "RADIO":
						if(("," + defaultValue + ",").indexOf("," + element.value + ",") >= 0){
							element.checked = true;
						}
						break;
					default :
						element.value = defaultValue;
						break;
				}
			}else{
				element.value = defaultValue;
			}
		}
		// For 标准版		
		// 初始化分类法
		if(window.m_pInitClassInfo){
			for(var i=0; i<m_pInitClassInfo.length; i++){
				var sElementId = m_pInitClassInfo[i][0];
				if($(sElementId) == null)
					return;

				$(sElementId).value = m_pInitClassInfo[i][1];
				$(sElementId+'_Text').innerHTML = m_pInitClassInfo[i][2] + "["+m_pInitClassInfo[i][1]+"]";
			}
		}

		// For 证监会
		/*
		if(window.m_nSiteId && m_nSiteId == 3){
			if(m_sOrgancat.length>0){
				$('organcat').value = m_nOrgancat;
				$('organcat_Text').innerHTML = m_sOrgancat + "["+m_nOrgancat+"]";
			}
		}
		*/
		
		//下面是初始化的
		if(window.m_sPublisher != null && $('Publisher') != null)
			$('Publisher').value = m_sPublisher;
		
		if($('idxID')){
			$('idxID').disabled = true;
		}		
	},

	//初始化是否能隐藏
	initHiddenField : function(oContainer, aElements){
		var bHiddenField = oContainer.getAttribute("_hiddenField");
		if(bHiddenField == 1){
			oContainer.style.display = 'none';
		}
	},

	//初始化是否能够编辑
	initNotEdit : function(oContainer, aElements){
		var bNotEdit = oContainer.getAttribute("_notEdit");
		if(bNotEdit != 1){
			return;
		}
		for (var i = 0; i < aElements.length; i++){
			aElements[i].disabled = true;
		}
	}
});

Object.extend(HTMLElementParser, {
	/**
	*得到本页面中关于引用的其他字段名称
	*/
	getOtherTableFields : function(sTableName){
		return m_oOtherTableFields[sTableName] || [[]];
	}
});

//init something on load.
Event.observe(window, 'load', function(){
	if($('releatedAppendix')){
		Event.observe('releatedAppendix', 'click', function(){
			var sTitle = '附件管理';
			var sURL = getWebURL() + "WCMV6/metadata/application/page/attachments_relations_top.jsp";
			//var sURL = "./metadata/application/page/attachments_relations_top.jsp";
			var sParams = "DocumentId=" + (getParameter('objectId') || getParameter('documentId') || 0);
			sParams += "&ChannelId=" + (ViewDataEditor.getChannelId() || 0);
			sParams += "&FlowDocId=" + (getParameter('FlowDocId') || 0);
			sURL += "?" + sParams;
			var sCrashBoardWinInd = "releatedAppendixer";
			var sCrashBoardNameInd = "releatedAppendix";
			if(window[sCrashBoardWinInd] == null) {
				TRSCrashBoard.setMaskable(true);
			}
			window[sCrashBoardWinInd] = TRSDialogContainer.register(sCrashBoardNameInd, sTitle, sURL, '700px', '400px', true, true);
			window[sCrashBoardWinInd].onFinished = function(_args){
				window.ReleatedAppendixesCache = Object.clone(_args, true);
			};
			TRSDialogContainer.display(sCrashBoardNameInd, {Appendixs : window.ReleatedAppendixesCache});					
		});
	}
});

/**
*相关文档的处理
*/
function DealWithRelationDoc(sAttachElement){
	if(!window.ReleatedDocumentCache){
		window.ReleatedDocumentCache = {};
	}
	var oAttachElement = $(sAttachElement);
	if(!oAttachElement) return;
	var sTitle = '相关文档';
	var sURL = getWebURL() + "WCMV6/metadata/application/page/attachments_relations_top.jsp";
	//var sURL = "./metadata/application/page/attachments_relations_top.jsp";
	var sParams = "DocumentId=" + getParameter('objectId') || 0;
	sParams += "&ChannelId=" + (ViewDataEditor.getChannelId() || 0);
	sParams += "&ChannelName=" + encodeURIComponent(oAttachElement.getAttribute('locateChannel') || "");
	sParams += "&_objType_=relations&RelationDocIds=" + (oAttachElement.value || 0);
	sURL += "?" + sParams;
	var sCrashBoardWinInd = "releatedDocument";
	var sCrashBoardNameInd = "releatedDocument";
	if(window[sCrashBoardWinInd] == null) {
		TRSCrashBoard.setMaskable(true);
	}
	window[sCrashBoardWinInd] = TRSDialogContainer.register(sCrashBoardNameInd, sTitle, sURL, '700px', '650px', true, true);
	window[sCrashBoardWinInd].onFinished = function(_args){
		window.ReleatedDocumentCache[sAttachElement] = Object.clone(_args, true);
		var aRelation = $a(_args, "RELATIONS.RELATION") || [];
		var aDocId = [];
		var aTitle = [];
		for (var i = 0; i < aRelation.length; i++){
			aDocId.push($v(aRelation[i],'RELDOC.ID'));
			aTitle.push($v(aRelation[i],'RELDOC.TITLE'));
		}
		oAttachElement.value = aDocId.join();
		var oTip = $(oAttachElement.name + "_tip");
		if(oTip){
			oTip.innerHTML = DealWithRelDoc(aDocId.join(), aTitle.join("`"));
		}
	};
	TRSDialogContainer.display(sCrashBoardNameInd, {Relations : window.ReleatedDocumentCache[sAttachElement]});					
}

function DealWithRelDoc(sRelDocIds, sRelDocNames){
	if(sRelDocIds == "-1"){
		return '<font color="red">非法数据</font>';
	}
	if(!sRelDocNames || !sRelDocIds) return "";
	var aRelDocIds = sRelDocIds.split(",");
	var aDocTitles = sRelDocNames.split("`");
	var sResult = "";
	for (var i = 0; i < aDocTitles.length; i++){
		sResult += "<span class='relationDocumentTip'><a href='../page/detail_show_redirection.jsp?objectId=" + aRelDocIds[i] + "' target='_blank' title='id:" + aRelDocIds[i] + "'>" + aDocTitles[i] + "</a></span>";
	}
	return sResult;
}

var TemplateSelector = null;
function SelectTemps(){
	if(!TemplateSelector){
		TemplateSelector = new com.trs.wcm.TemplateSelector({objecttype:101,objectid:ViewDataEditor.getChannelId()}, true, false);
	}
	TemplateSelector.selectTemps(false, 'spDetailTemp', 2);
}
