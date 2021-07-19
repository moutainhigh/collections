var PageContext = {};
Object.extend(PageContext, {
	params : {
		ChannelId : Math.abs(getParameter("Channelid")),
		DocumentId : getParameter("DocumentId"),
		ChnlDocId : getParameter("ChnlDocId") || 0,
		FlowDocId : getParameter('FlowDocId') || 0
	},
	AppendixTypeImage : 20,
	AppendixTypeDoc : 10,
	AppendixTypeLink : 40,
	LoadDocument : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];
		aCombine.push(oHelper.Combine('wcm6_document', 'findbyid', 
			Object.extend(Object.clone(this.params), {fieldsToHtml: '',ObjectId:getParameter("DocumentId"),containsright: true})));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryRelations', 
			Object.extend(Object.clone(this.params),{fieldsToHtml: 'RELDOC.Title'})));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', 
			Object.extend(Object.clone(this.params), {AppendixType:this.AppendixTypeImage})));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', 
			Object.extend(Object.clone(this.params), {fieldsToHtml: 'APPDESC',AppendixType:this.AppendixTypeDoc})));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', 
			Object.extend(Object.clone(this.params), {AppendixType:this.AppendixTypeLink})));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryExtendFields', this.params));
		//ge gfc add @ 2007-5-23 16:30 流转情况
		var postData = {
			ContentType: 605,
			ContentId: PageContext.params['DocumentId'],
			FlowDocId : getParameter('FlowDocId') || 0,
			PageSize: -1
		};
		aCombine.push(oHelper.Combine('wcm6_process', 'getFlowDocsOfContent', postData));
		aCombine.push(oHelper.Combine('wcm6_process', 'getProcessInfoOfContent', postData));
		oHelper.MultiCall(aCombine, this.DocumentLoaded.bind(this));
	},
	DocumentLoaded : function(_transport, _json){
		_json = _json["MULTIRESULT"];
		$('status').value = _json["DOCUMENT"]["DOCSTATUS"]["NAME"];
		var sValue = TempEvaler.evaluateTemplater('document_head_template', _json["DOCUMENT"]);
		Element.update($('document_head'), sValue);
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
		sValue = TempEvaler.evaluateTemplater('document_abstract_template', _json["DOCUMENT"]);
		Element.update($('document_abstract'), sValue);
		$('docDetail').style.visibility = 'visible';
		setTimeout(function(){
			sValue = TempEvaler.evaluateTemplater('document_body_template', _json["DOCUMENT"]);
//			alert(sValue.length);
			$('document_body').innerHTML = sValue;
//			Element.update($('document_body'), sValue);	
		},100);


		//ge gfc add @ 2007-5-23 16:30 流转情况
		this.showProcessInfo(_json);

		//ge gfc add @ 2007-8-1 9:38 判断是否需要显示操作按钮
		var nStatus = _json["DOCUMENT"]["DOCSTATUS"]["ID"];
		if(PageContext.params['FlowDocId'] > 0 
			|| nStatus <= 0 
			|| getParameter("Channelid") < 0 || getParameter('FromRecycle')=='1') {
			Element.hide('trOptions');
			return;
		}

		//hxj 没有权限时,隐藏相应按钮
		var rightValue = _json["DOCUMENT"]["RIGHT"];
		if(rightValue){
			if(isAccessable4WcmObject(rightValue, PageContext.rightIndexs["edit"])){
				Element.show("edit");
			}else{
				Element.hide("docstatus");
			}
			if(isAccessable4WcmObject(rightValue, PageContext.rightIndexs["backup"])){
				Element.show("versionSave");
			}
		}else{
			Element.hide("docstatus");
		}

	},
	showProcessInfo : function(_json){
		//1.决定显示哪些操作有关文档流转的按钮
		var pinfo = _json['PROCESSINFO'];
		//alert(pinfo)
		if(!pinfo || !$v(pinfo, 'FlowId') || $v(pinfo, 'FlowId') == '0') {//TODO
			return;
		}
		//else
		PageContext.params['FlowId'] = $v(pinfo, 'FlowId');
		if($v(pinfo, 'InFlow') == 'true') { //尚未开始流转
			Element.hide('spReflow');
			Element.hide('spCease');
			Element.show('spStartFlow');
		}else{
			if($v(pinfo, 'ReInFlow') == 'true') { //流转已结束，显示“重新流转”操作
				Element.hide('spStartFlow');
				Element.hide('spCease');
				Element.show('spReflow');
			}else if($v(pinfo, 'StopFlow') == 'true') { //流转中，当前用户为发起人，可以强制结束
				Element.hide('spReflow');
				Element.hide('spStartFlow');
				Element.show('spCease');
			}
		}
		//2.1.获取数据		
		var json = $a(_json, 'FLOWDOCS.FLOWDOC');
		if(json == null || json.lenght == 0) {
			return;
		}
		//else
		json = json.reverse();
		
		//2.2.显示流转甘特图
		sValue = TempEvaler.evaluateTemplater('gunter_template', _json);
		Element.update($('divGunter'), sValue);
		Element.show('divGunter');
		window.setTimeout(function(){
			var rows = $('divGunter').children;
			if(rows != null && rows.length > 0) {
				var curNode = rows(rows.length - 1);
				Element.addClassName(curNode, 'current_gunter_node');
				curNode.style.backgroundImage = 'url(../images/workflow/bg_tracing_s'
					+ curNode.getAttribute('_flag', 2) + '.gif)';
			}
		}, 10);
	},
	showContent : function(_sHtml, docType, docLink, docFileName){
		if(docType == 30){//链接文档和文件文档单独处理			
			return "<a href=" + docLink + " target='_blank' style='color:black;'>" + docLink + "</a>";
		}else if(docType == 40){
			return "<a href=/wcm/file/read_file.jsp?FileName=" + docFileName + " target='_blank' style='color:black;'>" + docFileName + "</a>";
		}else if(docType == 20){
			return FCKFormatEditedHtml.render(_sHtml);
		}
		return _sHtml;
	},
	formatDocAbstract : function(DocAbstract){
		if(DocAbstract.match(/<[^>]*>/))return DocAbstract;
		return DocAbstract.replace(/\n/mg, "<br>").replace(/\s/mg, "&nbsp;");
	},
	doRelation : function(_aHref){
		var sUrl = _aHref.getAttribute('href',2);
		$openMaxWin(sUrl);
		return false;
	}
});
var FCKFormatEditedHtml = {};
FCKFormatEditedHtml.Processers = [];
FCKFormatEditedHtml.AppendNew = function(_oProcesser){
	FCKFormatEditedHtml.Processers.push(_oProcesser);
}
FCKFormatEditedHtml.render = function(_sHtml){
	for (var i = 0; i < FCKFormatEditedHtml.Processers.length; i++){
		var oProcesser = FCKFormatEditedHtml.Processers[i];
		try{
			_sHtml = oProcesser(_sHtml);
		}catch(err){}
	}
	return _sHtml;
}
FCKFormatEditedHtml.AppendNew(function(_sHTML){
	return _sHTML.replace(/<trs_page_separator><\/trs_page_separator>/ig,
		'<table border=0 cellspacing=0 cellpadding=0 width="100%">\
		<tbody>\
			<tr>\
				<td style="font-size:8px;line-height:8px;" height="8" align="center" valign="middle">\
					<span style="width:40%;height:1px;overflow:hidden;background:gray;"></span>\
					分页符\
					<span style="width:40%;height:1px;overflow:hidden;background:gray;"></span>\
				</td>\
			</tr>\
		</tbody>\
		</table>');
});
FCKFormatEditedHtml.AppendNew(function(_sHTML){
	return _sHTML.replace(/<TRS_COMMENT([^>]*)>((.|\n|\r)*?)<\/TRS_COMMENT>/ig,'<SPAN class="fck_comment" _trscomment="true" contentEditable="false" onresizestart="return false"$1>$2<\/SPAN>');
});
Event.observe(window, 'load', function(){
	var _params = "SELECTFIELDS='SDISP,STATUSID'"
			+ "&ChannelId=" + PageContext.params.ChannelId
			+ "&ChnlDocIds=" + PageContext.params.ChnlDocId;
	$('status').setAttribute('_params', _params);
	var oComboxStatus = new com.trs.combox.Combox("status");
	oComboxStatus.setHeight(70);
	oComboxStatus.onOptionClick = function(event, statusId){
		BasicDataHelper.call('wcm6_viewdocument', 'changeStatus', {
			objectIds: PageContext.params['ChnlDocId'],
			statusId: statusId,
			ExcludeTrashed: true
		}, true);
		return true;
	};
	Event.observe('document_top_nav', 'click', function(event){
		event = event || window.event;
		var eSpan = Event.element(event);
		var sFunc = eSpan.getAttribute("_function");
		if(sFunc == null) {
			return;
		}
		if(sFunc=='backup'){
			$chnlDocMgr[sFunc](PageContext.params["DocumentId"],{
					docids : PageContext.params["DocumentId"],
					ExcludeTrashed: true
				});
		}
		else if(sFunc=='hide_comment'){
			var aSpans = document.getElementsByTagName("SPAN");
			for(var i=0;i<aSpans.length;i++){
				if(aSpans[i].getAttribute('_trscomment',2))
					aSpans[i].style.display = 'none';
			}
			$('comment').innerHTML = $('comment').title = '显示注释';
			$('comment').setAttribute('_function','show_comment');
		}
		else if(sFunc=='show_comment'){
			var aSpans = document.getElementsByTagName("SPAN");
			for(var i=0;i<aSpans.length;i++){
				if(aSpans[i].getAttribute('_trscomment',2))
					aSpans[i].style.display = '';
			}
			$('comment').innerHTML = $('comment').title = '隐藏注释';
			$('comment').setAttribute('_function','hide_comment');
		}
		else if(sFunc=='close'){
			try{
//				if(top.window.opener){
//					top.window.opener.$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', PageContext.params['DocumentId']);
//				}
			}catch(err){}
			top.window.opener = null;
			top.window.close();
		}
		else{
			var flag = eSpan.getAttribute('_flag', 2);
			if(flag == 'workflow') { //文档流转操作
				var params = {
					ContentType: 605,
					ContentId: PageContext.params["DocumentId"],
					FlowId: PageContext.params['FlowId'] || 0,
					DocTitle: $('divDocTitle').innerHTML,
					FlowDocId : getParameter('FlowDocId') || 0
				}
				
				$workProcessor[sFunc](params);
			}else{ //默认为$chnlDocMgr操作
				$chnlDocMgr[sFunc](PageContext.params["DocumentId"],{channelid:PageContext.params["ChannelId"]});
			}
		}
	});
});
PageContext.RefreshCurrDoc = function(){
	PageContext.LoadDocument();
	try{
		if(top.window.opener){
			top.window.opener.$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', PageContext.params['ChnlDocId']);
		}
	}catch(err){}
}
Event.observe(window,'unload',function(){
	top.window.opener = null;
});
PageContext.refreshCurrRows = PageContext.RefreshCurrDoc;

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

//通过Flag.Id获取流转状态的说明
function getAdaptedFlagDesc(_nFlagId){
	if(_nFlagId == 7) {
		return '强制结束流转';
	}else if(_nFlagId == 18) {
		return '流转正常结束';
	}
	
	//else 
	return '';
}