function $(el) {
	if (typeof el == 'string')
		el = document.getElementById(el) || document.getElementsByName(el)[0];
	return el;
} 
var PageContext = {
	refreshCurrRows : function(){
		location.reload();
	}
}
var m_nFlowDocId = getParameter("FlowDocId") || 0, m_nFlowId = 0;
var m_bFlowDocInFlow = !!getParameter("FlowDocId");

function submitData(){
	if(m_bFlowDocInFlow){
		var winFlowdoc = $('frmFlowDoc').contentWindow;
		//先进行校验
		if(!winFlowdoc.validate(true)) {
			return;
		}
		//else 组织一下要提交的数据
		var sPostData = winFlowdoc.buildPostXMLData();
		if(sPostData && sPostData.length > 0) {
			$('hdFlowDocPostXMLData').value = sPostData;
			//这是一个冗余数据，为了增强dowith页面的校验
			$('hdFlowDocId').value = m_nFlowDocId;
		}
		//如果选择的是诸如“签收”、“返工”或者“拒绝”之类
		//的操作，//则不需要校验/保存表单数据
		if(winFlowdoc.isSpecialOption() || !hasRight) {
			//指明参数后提交数据
			$('hdSkipDocSaving').value = 1;
		}
	}
	if(document.getElementsByName("FlowDocId")[0]){
		$("hdFlowDocId").value = getParameter("FlowDocId") || 0;
	}
	if(document.getElementsByName("input_IndexCode")[0]){
		$("indexCode").value = document.getElementsByName("input_IndexCode")[0].value;
	}
	if(document.getElementsByName("sel_DestDpt")[0]){
		$("DestDpt").value = document.getElementsByName("sel_DestDpt")[0].value;
	}
	if(document.getElementById("mailBodyIframe")){
		$('mailBody').value = $('mailBodyIframe').contentWindow.document.body.innerHTML;
	}
	if(window.Appendixs){
		var nApplyFormId = parseInt($('ApplyFormId').value);
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];
		var oPostData = {
			"ApplyFormId" : nApplyFormId,
			"AppendixType" : 10,
			"AppendixesXML" : getAppendixesXML(10),
			"FlowDocId" : m_nFlowDocId
		}
		aCombine.push(oHelper.Combine('wcm6_applyform','saveAppendixes',oPostData));
		oPostData = {
			"ApplyFormId" : nApplyFormId,
			"AppendixType" : 20,
			"AppendixesXML" : getAppendixesXML(20),
			"FlowDocId" : m_nFlowDocId
		}
		aCombine.push(oHelper.Combine('wcm6_applyform','saveAppendixes',oPostData));
		oPostData = {
			"ApplyFormId" : nApplyFormId,
			"AppendixType" : 40,
			"AppendixesXML" : getAppendixesXML(40),
			"FlowDocId" : m_nFlowDocId
		}
		aCombine.push(oHelper.Combine('wcm6_applyform','saveAppendixes',oPostData));
		oHelper.MultiCall(aCombine);
	}
	$('frmData').submit();
}
function getAppendixesXML(iType){
	var appendixs = Appendixs['Type_'+iType];
	var arr = com.trs.util.JSON.array(appendixs,"APPENDIXES.APPENDIX")||[];
	var sParams = ["APPENDIXID","APPFILE","SRCFILE","APPLINKALT","APPFLAG","APPDESC"];
	var myValue = com.trs.util.JSON.value;
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
function setMailContent(ApplyFormId){
	event.returnValue = false;
	new Ajax.Request("default_mailcontent_get.jsp", {
		method : 'post',
		parameters : "ApplyFormId=" + ApplyFormId,
		contentType : 'application/x-www-form-urlencoded', 
		onSuccess : function(transport, json){
			var xmlDoc = transport.responseXML;
			$("mailBody").value = xmlDoc.getElementsByTagName("mailBody")[0].text;
			if($('mailBodyIframe')){
				$('mailBodyIframe').contentWindow.document.body.innerHTML = $('mailBody').value;
			}
		},
		onFailure : function(){
			alert("获取邮件默认信息失败！");
		}
	});
}

function showContent(srcElement, targetElement){
	Element.hide(srcElement);
	Element.show(targetElement);
}

Event.observe(window, 'load', function(){
	if($('mailBodyIframe')){
		$('mailBodyIframe').contentWindow.document.body.innerHTML = $('mailBody').value;
	}
});


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
function showProcessInfo(_trans, _json){
	//1.决定显示哪些操作有关文档流转的按钮
	_json = _json["MULTIRESULT"];
	var pinfo = _json['PROCESSINFO'];
	if(!pinfo || !$v(pinfo, 'FlowId') || $v(pinfo, 'FlowId') == '0') {//TODO
		return;
	}
	//else
	m_nFlowId = $v(pinfo, 'FlowId');
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
			curNode.style.backgroundImage = 'url(../../images/workflow/bg_tracing_s'
				+ curNode.getAttribute('_flag', 2) + '.gif)';
		}
	}, 10);
}
Event.observe(window, 'load', function(){
	if(m_bFlowDocInFlow){
		$('tb_gunter').style.display = '';
		if($('dvFlowDocPanel')){
			document.body.style.textAlign = 'left';
			$('dvFlowDocPanel').style.display = 'inline';
			$('frmFlowDoc').src = '../../workflow/workflow_process_render_4applyform.jsp?FlowDocId='
							+ (getParameter("FlowDocId")||0);
		}
	}
	
	if(!$('dvFlowDocPanel') || !Element.visible('dvFlowDocPanel')){
		document.body.style.paddingRight = '100px';
	}
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var aCombine = [];
	var postData = {
		ContentType: 961799218,
		ContentId: getParameter('ApplyFormId'),
		FlowDocId : getParameter('FlowDocId') || 0,
		PageSize: -1
	};
	aCombine.push(oHelper.Combine('wcm6_process', 'getFlowDocsOfContent', postData));
	aCombine.push(oHelper.Combine('wcm6_process', 'getProcessInfoOfContent', postData));
	oHelper.MultiCall(aCombine, showProcessInfo);
	Event.observe('td_top_nav', 'click', function(event){
		event = event || window.event;
		var eSpan = Event.element(event);
		var sFunc = eSpan.getAttribute("_function");
		if(sFunc == null) {
			return;
		}
		if(sFunc=='close'){
			if(window.opener && window.opener.PageContext && window.opener.PageContext.RefreshList){
				window.opener.PageContext.RefreshList();
			}
			top.window.opener = null;
			top.window.close();
		}
		else{
			var flag = eSpan.getAttribute('_flag', 2);
			if(flag == 'workflow') { //文档流转操作
				var params = {
					ContentType: 961799218,
					ContentId: getParameter('ApplyFormId'),
					FlowId: m_nFlowId || 0,
					DocTitle: $('tdDocTitle').innerHTML,
					ServiceId: 'wcm6_applyform',
					MethodName: 'startDocumentInFlow',
					FlowDocId : getParameter('FlowDocId') || 0
				}
				$workProcessor[sFunc](params);
			}
		}
	});
});

function doAppendixs(){
	window.showModalDialog("document_attachments.html", window, 
      "dialogHeight:400px;dialogWidth:780px;");
}
function showAppendixs(){
	$('appendix_container').innerHTML = getAppendixsHtml(window.Appendixs);
}
function getAppendixsHtml(_oAppendixs){
	try{
		var sResult = '';
		if(!_oAppendixs)return sResult;
		var oAppendixes = _oAppendixs["Type_20"];
		if(oAppendixes){
			var arrAppendixes = $a(oAppendixes, "APPENDIXES.APPENDIX");
			if(arrAppendixes && arrAppendixes.length>0)
				sResult += '图片附件:';
			for(var i=0;arrAppendixes&&i<arrAppendixes.length;i++){
				var altDesc = "";
				if(arrAppendixes[i]['APPLINKALT']){
					altDesc = arrAppendixes[i]['APPLINKALT'].NODEVALUE;
				}
				
				sResult += '<a href="/wcm/file/read_image.jsp?FileName=' + arrAppendixes[i]['APPFILE']['FILENAME'] + '" target=_blank><img src="' + arrAppendixes[i]['APPFILE']['URL'] + '" alt="'+ altDesc+'" title="'+(altDesc||arrAppendixes[i]['APPDESC'].NODEVALUE) +'" style="border:0px"></a>&nbsp;&nbsp;';
			}
		}
		oAppendixes = _oAppendixs["Type_10"];
		if(oAppendixes){
			var arrAppendixes = $a(oAppendixes, "APPENDIXES.APPENDIX");
			if(sResult) sResult += '<br>';
			if(arrAppendixes && arrAppendixes.length>0)
				sResult += '文件附件:';
			for(var i=0;arrAppendixes&&i<arrAppendixes.length;i++){
				sResult += '<a href="/wcm/file/read_file.jsp?FileName=' + (arrAppendixes[i]['APPFILE'].NODEVALUE || arrAppendixes[i]['APPFILE']) + '" target=_blank>' + $v(arrAppendixes[i],'APPDESC') + '</a>&nbsp;&nbsp;';
			}
		}
		oAppendixes = _oAppendixs["Type_40"];
		if(oAppendixes){
			var arrAppendixes = $a(oAppendixes, "APPENDIXES.APPENDIX");
			if(sResult) sResult += '<br>';
			if(arrAppendixes && arrAppendixes.length>0)
				sResult += '链接附件:';
			for(var i=0;arrAppendixes&&i<arrAppendixes.length;i++){
				sResult += '<a href="' + (arrAppendixes[i]['APPFILE'].NODEVALUE || arrAppendixes[i]['APPFILE']) + '" target=_blank>' + $v(arrAppendixes[i],'APPDESC') + '</a>&nbsp;&nbsp;';
			}
		}
		return sResult;
	}catch(err){
		return '';
	}
}