Object.extend(ViewDataDealer, {
	AppendixTypeImage : 20,
	AppendixTypeDoc : 10,
	AppendixTypeLink : 40,

	_afterInitData : function(){
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var oIdInfo = {
			FlowDocId : getParameter("FlowDocId") || 0,
			DocumentId:getParameter('objectId') || getParameter('documentId') ||0, 
			ChnlDocId:getParameter('ChnlDocId') || 0
		};
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', Object.extend({AppendixType:this.AppendixTypeImage}, oIdInfo)));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', Object.extend({AppendixType:this.AppendixTypeDoc}, oIdInfo)));
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'queryAppendixes', Object.extend({AppendixType:this.AppendixTypeLink}, oIdInfo)));
		aCombine.push(oHelper.Combine('wcm6_process', 'getProcessInfoOfContent', {CONTENTTYPE:605,CONTENTID:oIdInfo["DocumentId"]}));
		oHelper.MultiCall(aCombine, function(_transport, _json){
			_json = _json["MULTIRESULT"];
			sValue = TempEvaler.evaluateTemplater('document_image_appendixes_template', _json["APPENDIXES"][0]);
			Element.update($('document_image_appendixes'), sValue);	
			sValue = TempEvaler.evaluateTemplater('document_doc_appendixes_template', _json["APPENDIXES"][1]);
			Element.update($('document_doc_appendixes'), sValue);	
			sValue = TempEvaler.evaluateTemplater('document_link_appendixes_template', _json["APPENDIXES"][2]);
			Element.update($('document_link_appendixes'), sValue);	
			
			//1.决定显示哪些操作有关文档流转的按钮
			if(isAccessable4WcmObject(getParameter("RightValue"), 32)){
				var pinfo = _json['PROCESSINFO'];
				if(!pinfo || !$v(pinfo, 'FlowId') || $v(pinfo, 'FlowId') == '0') {
				}else{
					gFlowId= $v(pinfo, 'FlowId');
					if($v(pinfo, 'InFlow') == 'true') { //尚未开始流转
						Element.hide('spReflow');
						Element.hide('spCeaseflow');
						Element.show('spStartflow');
						Element.show("FlowDiv");
					}else{
						if($v(pinfo, 'ReInFlow') == 'true') { //流转已结束，显示“重新流转”操作
							Element.hide('spStartflow');
							Element.hide('spCeaseflow');
							Element.show('spReflow');
							Element.show("FlowDiv");
						}else if($v(pinfo, 'StopFlow') == 'true') { //流转中，当前用户为发起人，可以强制结束
							Element.hide('spReflow');
							Element.hide('spStartflow');
							Element.show('spCeaseflow');
							Element.show("FlowDiv");
						}
					}
				}
			}

			HTMLElementParser.parse();
			adjustDimension();
		});
	}
});

function DealWithRelDoc(sRelDocIds, sRelDocNames){
	if(!sRelDocNames || !sRelDocIds) return "";
	var aRelDocIds = sRelDocIds.split(",");
	var aDocTitles = sRelDocNames.split("`");
	var sResult = "";
	for (var i = 0; i < aDocTitles.length; i++){
		sResult += "<span class='relationDocument'><a href='../page/detail_show_redirection.jsp?objectId=" + aRelDocIds[i] + "' target='_blank' title='id:" + aRelDocIds[i] + "'>" + aDocTitles[i] + "</a></span>";
	}
	return sResult;
}

//格式化多行文本
function formatContent(sContent){
	return $trans2Html(sContent, true);
}

/*====================工作流=====================*/
var gFlowId = 0;
function dealFlow(event){
	event = window.event || event;
	var srcElement = Event.element(event);
	var handler = srcElement.getAttribute("fun");
	if(handler == null) return;
	var params = {
		ContentType: 605,
		ContentId: getParameter('objectId') || getParameter('documentId') ||0, 
		FlowId: gFlowId,
		DocTitle: document.title,
		FlowDocId : getParameter('FlowDocId') || 0
	}
	$workProcessor[handler](params);
}

if(!window.PageContext){
	var PageContext = {};
}
if(!PageContext.refreshCurrRows){
	PageContext.refreshCurrRows = function(){
		ViewDataDealer._afterInitData();
	};
}
