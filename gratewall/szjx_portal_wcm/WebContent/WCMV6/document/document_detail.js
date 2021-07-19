$package('com.trs.wcm.document');

Object.extend(PageContext, {
	initialize : function(_params){
		this.params = _params||{};
		this.AppendixTypeImage = 20;
		this.AppendixTypeDoc = 10;
		this.AppendixTypeLink = 40;
	},
	initStatuses : function(){
		PageContext.StatusCombox.oRelatedElement.setAttribute("noPromptInfo", true);
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call('wcm6_status','queryDocumentStatuses',{
				"ChannelId":this.params.channelid,
				"ChnlDocIds":this.params.chnldocid,
				"selectfields":'SDISP,STATUSID'
		}, true, function(_transport,_json){
				PageContext.StatusCombox.clearAllItems();
				var Json = com.trs.util.JSON;
				var statuses = Json.array(_json,'Statuses.Status')||[];
				statuses.each(function(e){
					PageContext.StatusCombox.addItem(Json.value(e, "SDISP"), Json.value(e, "STATUSID"));
				});
			}
		);
	},
	response : function(_oInfo){
		Object.extend(this.params, _oInfo);
		this.initStatuses();
		if(!this.checkRight(this.params["right"])) 
			return;
		this.loadDocumentDetail();
	},
	checkRight : function(right){
		if(!isAccessable4WcmObject(right, PageContext.rightIndexs["detail"])){//没有权限
			Element.hide('docDetail');			
			Element.show('divNoRights');	
			return false;			
		}	
		Element.hide('divNoRights');
		Element.show('docDetail');			
		$('docDetail').style.visibility = 'visible';
		Element.show('docDetail');	
		$('skeepToDomId').style.visibility = "visible";
		if(isAccessable4WcmObject(right, PageContext.rightIndexs["edit"])){//有编辑权限
			$('editDomId').style.visibility = "visible";
		}else{
			$('editDomId').style.visibility = "hidden";
		}
		if(isAccessable4WcmObject(right, PageContext.rightIndexs["preview"])){//有预览权限
			$('previewDomId').style.visibility = "visible";
		}else{
			$('previewDomId').style.visibility = "hidden";
		}
		if(isAccessable4WcmObject(right, PageContext.rightIndexs["basicpublish"])){//有发布权限
			$('publishDomId').style.visibility = "visible";
		}else{
			$('publishDomId').style.visibility = "hidden";
		}
/*
		if(isAccessable4WcmObject(right, PageContext.rightIndexs["changestatus"])){//有改变状态权限
			$('statusDomId').style.visibility = "visible";
		}else{
			$('statusDomId').style.visibility = "hidden";
		}	
*/
		return true;
	},
	loadDocumentDetail : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];

		var oPostData = {
			fieldsToHtml: '',
			objectid: this.params['chnldocid'],
			channelid: this.params['channelid'],
			SelectFieldsOfDocument: 'CrUser,DocAbstract,DOCAuthor,DOCID,DocTitle,DocType,DocLink,DocFileName,DocKeyWords,DocPeople,SubDocTitle,DocChannel,DocSourceName,DocHtmlCon,DOCRELTIME,DOCPUBTIME',    
			containsright: true
		};
		var oIdInfo = {
			DocumentId:this.params["objectid"], 
			ChnlDocId:this.params['chnldocid']
		};
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'findbyid', oPostData));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryRelations', oIdInfo));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', Object.extend({AppendixType:this.AppendixTypeImage}, oIdInfo)));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', Object.extend({AppendixType:this.AppendixTypeDoc}, oIdInfo)));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', Object.extend({AppendixType:this.AppendixTypeLink}, oIdInfo)));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryExtendFields', oIdInfo));

		oHelper.MultiCall(aCombine, this.DocumentDetailLoaded.bind(this), function(){
			Element.hide('docDetail');
			Element.show('divNoRights');
		});
	},
	DocumentDetailLoaded : function(_transport, _json){
		if(Element.visible('divNoRights')){
			Element.hide('divNoRights');
			Element.show('docDetail');			
		}
		_json = _json["MULTIRESULT"];
		//设置文档状态
		$('status').value = _json["VIEWDOCUMENT"]["DOCSTATUS"]["NAME"];
		var sValue = TempEvaler.evaluateTemplater('document_head_template', _json["VIEWDOCUMENT"]);
		Element.update($('document_head'), sValue);	
		sValue = TempEvaler.evaluateTemplater('document_body_template', _json["VIEWDOCUMENT"]);
		Element.update($('document_body'), sValue);	
		Element.show("appendixesHead");
		sValue = TempEvaler.evaluateTemplater('document_image_appendixes_template', _json["APPENDIXES"][0]);
		Element.update($('document_image_appendixes'), sValue);	
		sValue = TempEvaler.evaluateTemplater('document_doc_appendixes_template', _json["APPENDIXES"][1]);
		Element.update($('document_doc_appendixes'), sValue);	
		sValue = TempEvaler.evaluateTemplater('document_link_appendixes_template', _json["APPENDIXES"][2]);
		Element.update($('document_link_appendixes'), sValue);	
		sValue = TempEvaler.evaluateTemplater('document_relations_template', _json["RELATIONS"]);
		Element.update($('document_relations'), sValue);
		sValue = TempEvaler.evaluateTemplater('document_extendfields_template', _json["CONTENTEXTENDVALUES"]);
		Element.update($('document_extendfields'), sValue);	
		
		//alert(window.currStatus + '\n' + $('document_detail_content_more'))
		if(window.currStatus == 'visible' && $('document_detail_content_more')){
			this.toggleMoreContent();
		}
	},
	showContent : function(_sHtml, docType, docLink, docFileName){
		var sHtml = '';
		if(docType == 30){//链接文档和文件文档单独处理			
			sHtml = "<a href=" + docLink + " target='_blank' style='color:black;'>" + docLink + "</a>";
		}else if(docType == 40){
			sHtml = "<a href=/wcm/file/read_file.jsp?FileName=" + docFileName + " target='_blank' style='color:black;'>" + docFileName + "</a>";
		}else{
			var eDiv = document.createElement('DIV');
			var sMore = '',sHeight='';
			eDiv.className = 'document_detail_content';
			eDiv.style.display = 'none';
			eDiv.innerHTML = _sHtml;
			document.body.appendChild(eDiv);
			var iMaxHeight = Element.getDimensions(eDiv)["height"];
			eDiv.style.overflowY = 'visible';
			eDiv.style.height = 0;
			var currHeight = Element.getDimensions(eDiv)["height"];
			if(currHeight>iMaxHeight){
				sMore = '<div id="document_detail_content_more" class="document_detail_content_more" onclick="PageContext.toggleMoreContent(true);">更多&gt;&gt;</div>';
			}
			else{
				sHeight = 'style="height:'+currHeight+'px;"';
			}
			document.body.removeChild(eDiv);
			delete eDiv;
			sHtml = '<div id="document_detail_content" class="document_detail_content" '+sHeight+'>'+_sHtml+'</div>'+sMore;		
		}
		//Element.update($('divTip'), sHtml);
		return sHtml;

	},	
	toggleMoreContent : function(causeByUser){
		var eContent = $('document_detail_content');
		var eMore = $('document_detail_content_more');
		if((Element.getStyle(eContent,'overflow-y')||'visible')!='hidden'){
			eContent.style.overflowY = 'hidden';
			(eMore||{}).innerHTML = '更多&gt;&gt;';	
			if(causeByUser){
				window.currStatus = 'hidden';
			}
		}
		else{
			eContent.style.overflowY = 'visible';
			(eMore||{}).innerHTML = '更少&lt;&lt;';
			if(causeByUser){
				window.currStatus = 'visible';
			}
		}
	},
	formatDocAbstract : function(DocAbstract){
		return DocAbstract.replace(/\n/mg, "<br>");
	},
	getAllDocStatus : function(){
		if(this['_all_doc_status_']){
			return this['_all_doc_status_'];
		}
		else{//TODO
			return ["新稿"];
		}
	},
	// 同步status-combox的发布状态
	sychStatus : function(_nStatusVal, _sStatusName){
		$('status').value = _sStatusName;
		//PageContext.StatusCombox.setValue(_nStatusVal);
	},
	doRelation : function(_aHref){
		var sUrl = _aHref.getAttribute('href',2);
		$openMaxWin(sUrl);
		return false;
	}
});

PageContext.initialize();

function toggleMe(key){
	Element.toggle(key + "Content");
	if(Element.visible(key + "Content")){
		Element.removeClassName(key + "Head", 'document_detail_other_header_down');
		Element.addClassName(key + "Head", 'document_detail_other_header_up');
	}else{
		Element.removeClassName(key + "Head", 'document_detail_other_header_up');
		Element.addClassName(key + "Head", 'document_detail_other_header_down');
	}
}

Event.observe(window, 'load', function(){
	Event.observe('previewDomId', 'click', function(){
		$chnlDocMgr.preview(PageContext.params["chnldocid"], PageContext.params);
	});

	Event.observe('publishDomId', 'click', function(){
		$chnlDocMgr.publish(PageContext.params["chnldocid"], $('publishDomId').getAttribute("_key"));
	});	

	Event.observe('editDomId', 'click', function(){
		$chnlDocMgr.edit(PageContext.params["objectid"], PageContext.params);
	});	

	var oComboxStatus = new com.trs.combox.Combox("status");
	PageContext.StatusCombox = oComboxStatus;
	$('status').disabled = true;
	oComboxStatus.setHeight(70);
	oComboxStatus.onOptionClick = function(event, statusId){
		BasicDataHelper.call('wcm6_viewdocument', 'changeStatus', {
			objectIds: PageContext.params['chnldocid'],
			statusId: statusId
		}, true, function(){
			// ge gfc modify @ 2007-4-4 9:33 修改在阅读模式列表中寻找status的id方式
			//var statusDomObj = parent.$("status_" + PageContext.params["objectid"]);
			var statusDomObj = parent.$("status_" + PageContext.params["chnldocid"]);

			if(statusDomObj){
				statusDomObj.innerHTML = $('status').value;
			}
		});
		return true;
	};
	Event.observe('statusDomId', 'click', oComboxStatus.onComboxImgClick.bind(oComboxStatus));

	var oComboxSkeepTo = new com.trs.combox.Combox("skeepTo");
	$('skeepTo').disabled = true;
	oComboxSkeepTo.addItem("文档正文", "contentBody");
    oComboxSkeepTo.addItem("文档摘要", "abstract");
	oComboxSkeepTo.addItem("扩展字段", "extendfields");
	oComboxSkeepTo.addItem("附 件", "appendixes");
	oComboxSkeepTo.addItem("相关文档", "relations");

	oComboxSkeepTo.setHeight(90);
	oComboxSkeepTo.onOptionClick = function(event, keepToName){
		if(keepToName != 'contentBody' && !Element.visible(keepToName + "Content")){
			toggleMe(keepToName);
		}
		$(keepToName).scrollIntoView();
		return true;
	};
	Event.observe('skeepToDomId', 'click', oComboxSkeepTo.onComboxImgClick.bind(oComboxSkeepTo));
});


PageContext.rightIndexs = {
	"view":30,
	"new":31,
	"quicknew":31,
	"import":31,
	"edit":32,
	"trash":33,
	"detail":34,
	"copy":34,
	"quote":34,
	"setright":54,
	"preview":38,
	"basicpublish":39,
	"detailpublish":39,
	"recallpublish":39,
	"restore":33,
	"backup":32,
	"export":34,
	"saveorder":32,
	"changestatus":35,
	"changesource":46
};