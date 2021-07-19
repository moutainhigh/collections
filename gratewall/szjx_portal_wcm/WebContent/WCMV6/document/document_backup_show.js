var PageContext = {};
Object.extend(PageContext, {
	params : {
		Version : getParameter("Version"),
		DocumentId : getParameter("DocumentId")
	},
	AppendixTypeImage : 20,
	AppendixTypeDoc : 10,
	AppendixTypeLink : 40,
	LoadDocumentBak : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];
		aCombine.push(oHelper.Combine('wcm6_documentBak', 'findbyid', 
			Object.extend(Object.clone(this.params), {ObjectId:getParameter("Version")})));
		aCombine.push(oHelper.Combine('wcm6_documentBak', 'queryRelations', this.params));
		aCombine.push(oHelper.Combine('wcm6_documentBak', 'queryAppendixes', 
			Object.extend(Object.clone(this.params), {AppendixType:this.AppendixTypeImage})));
		aCombine.push(oHelper.Combine('wcm6_documentBak', 'queryAppendixes', 
			Object.extend(Object.clone(this.params), {AppendixType:this.AppendixTypeDoc})));
		aCombine.push(oHelper.Combine('wcm6_documentBak', 'queryAppendixes', 
			Object.extend(Object.clone(this.params), {AppendixType:this.AppendixTypeLink})));
		aCombine.push(oHelper.Combine('wcm6_documentBak', 'queryExtendFields', this.params));
		oHelper.MultiCall(aCombine, this.DocumentBakLoaded.bind(this));
	},
	DocumentBakLoaded : function(_transport, _json){
		_json = _json["MULTIRESULT"];
		var sValue = TempEvaler.evaluateTemplater('document_head_template', _json["DOCBAK"]);
		Element.update($('document_head'), sValue);
		sValue = TempEvaler.evaluateTemplater('document_body_template', _json["DOCBAK"]);
		Element.update($('document_body'), sValue);	
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
		$('docDetail').style.visibility = 'visible';
	},
	showContent : function(_sHtml, docType, docLink, docFileName){
		if(docType == 30){//链接文档和文件文档单独处理			
			return "<a href=" + docLink + " target='_blank' style='color:black;'>" + docLink + "</a>";
		}else if(docType == 40){
			return "<a href=/wcm/file/read_file.jsp?FileName=" + docFileName + " target='_blank' style='color:black;'>" + docFileName + "</a>";
		}
		return _sHtml;
	},
	formatDocAbstract : function(DocAbstract){
		return DocAbstract.replace(/\n/mg, "<br>");
	}
});

Event.observe(window, 'load', function(){
	Event.observe('document_top_nav', 'click', function(event){
		event = event || window.event;
		var eSpan = Event.element(event);
		var sFunc = eSpan.getAttribute("_function");
		var fCallBack1 = function(){
			try{
				window.opener.$MessageCenter.sendMessage('window_iframe', 'PageContext.RefreshList', 'PageContext', null);
			}catch(err){
			}
			window.opener = null;
			window.close();
		}
		var fCallBack2 = function(){
			try{
				window.opener.$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext');
			}catch(err){
			}
			window.opener = null;
			window.close();
		}
		if(sFunc=='recoverBak')
			$chnlDocMgr[sFunc](getParameter("Version"),{DocumentId:getParameter("DocumentId")},fCallBack2);
		else
			$chnlDocMgr[sFunc](getParameter("Version"),{DocumentId:getParameter("DocumentId")},fCallBack1);
	});
});
