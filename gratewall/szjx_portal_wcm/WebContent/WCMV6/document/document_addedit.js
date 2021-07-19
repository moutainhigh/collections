Calendar.displayType = 'lb';/*hv*///日期控件的显示位置
top.actualTop = window;
var sBase = top.location.href.replace(/((^.*\/|^)document\/)document_addedit\.jsp(\?.*)?$/g,'$1');
window.BasePath = sBase;
try{
	window.enableAutoSave = PageContext.individuationConfig.documentAutoSave.paramValue!=0;
}catch(err){
	window.enableAutoSave = false;
}
try{
	window.enableAutoPaste = PageContext.individuationConfig.documentAutoPaste.paramValue!=0;
}catch(err){
	window.enableAutoPaste = false;
}
try{
	window.enableAfterPasteSwitch = PageContext.individuationConfig.documentCursorIntelligent.paramValue!=0;
}catch(err){
	window.enableAfterPasteSwitch = false;
}
try{
	window.TabSpaces = PageContext.individuationConfig.documentTabAs.paramValue;
}catch(err){
	window.TabSpaces = 4;
}
try{
	window.EnterAs = PageContext.individuationConfig.documentEnterAs.paramValue;
}catch(err){
	window.EnterAs = 'p';
}
try{
	window.autoTitleLength = PageContext.individuationConfig.documentAsTitleLength.paramValue;
}catch(err){
	window.autoTitleLength = 30;
}
window.showAutoSaveMessage = function(){
	$('autosave_message').innerHTML = '自动保存于:'+new Date().toString(0);
}
Event.observe(window,'unload',function(){
	top.actualTop = null;
	top.window.opener = null;
});
function AssignDocChannel(){
	if(bIsReadOnly)return;
	var oParams = {
		"FromChannelId" : 0
	}
	FloatPanel.open('document/document_select_channel.html?' + $toQueryStr(oParams), '指定文档所属栏目', 300, 350);
}
function UpdateDocFile(){
	if(bIsReadOnly)return;
	ProcessBar.init('执行进度，请稍候...');
	var sDocFileName = $("DocFile").value;
	if(!FileUploadHelper.validFileExt(sDocFileName, ".*")){
		return false;
	}
	ProcessBar.addState('正在上传文件:'+sDocFileName);
	ProcessBar.addState('成功上传文件.');
	ProcessBar.start();
	var isSSL = location.href.indexOf("https://")!=-1;
	var callBack1 = {
		"upload":function(_transport){
			var sResponseText = _transport.responseText;
			FileUploadHelper.fileUploadedAlert(sResponseText,function(){
				$('DOCFILENAME').value = sResponseText;
				ProcessBar.next();
				setTimeout(ProcessBar.next.bind(ProcessBar),10);
			});
		}
	}
	try{
		YUIConnect.setForm('frmUploadDocFile',true,isSSL);
		YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=DocFile',callBack1);
	}catch(err){
		ProcessBar.exit();
		$alert(err.message);
	}
	return false;
}
function ExecuteCommand(_sCommandName){
	var oTrsEditor = $('_trs_editor_');
	oTrsEditor.contentWindow.ExecuteCommand(_sCommandName);
}
function SwitchMode(_sMode){
	var eSource = $('editor_btn_source');
	var eDesign = $('editor_btn_design');
	if(_sMode=='Source'){
		Element.addClassName(eSource,'toolbar_current_btn');
		Element.removeClassName(eDesign,'toolbar_current_btn');
	}
	else if(_sMode=='Design'){
		Element.addClassName(eDesign,'toolbar_current_btn');
		Element.removeClassName(eSource,'toolbar_current_btn');
	}
}
function AttachPic(_bAttach){
	/*
	var sDocTitle = $('DocTitle').value;
	if(_bAttach&&!sDocTitle.match(/\(图\)$/)){
		$('DocTitle').value = sDocTitle +'(图)';
	}
	else if(!_bAttach&&sDocTitle.match(/\(图\)$/)){
		$('DocTitle').value = sDocTitle.replace(/\(图\)$/g,'');
	}
	*/
}
function getDocTitle(){
	var sDocTitle = $('DocTitle').value;
	/*
	if($('AttachPic').checked&&!sDocTitle.match(/\(图\)$/)){
		return sDocTitle +'(图)';
	}
	*/
	return sDocTitle;
}
function getQuoteChannelIds(){
	return $('QuoteChannels').getAttribute("ChannelIds",2);
}

Form.Element.Serializers.inputSelector=function(element) {
	if(element.type=='checkbox'&&element.getAttribute('isBoolean')!=null){
		var values = element.value.split('|');
		if(element.checked){
			return [element.name, values[0]];
		}
		else{
			return [element.name, values[1]];
		}
	}
	if (element.checked)
	  return [element.name, element.value];
};
function getPostData(){
	var elements = [];
	for(var i=0;i<arguments.length;i++){
		var tagElements = document.getElementsByName(arguments[i]);
		for (var j = 0; tagElements && j < tagElements.length; j++){
			elements.push(tagElements[j]);
		}
	}
	var vData = {};
	for(var i=0;i<elements.length;i++){
		var oElement = elements[i];
		if(oElement.name&&!oElement.getAttribute("ignore")){
			if(oElement.getAttribute("isAttr")){
				var attrs = vData["__ATTRIBUTE__"];
				if(!attrs){
					attrs = vData["__ATTRIBUTE__"] = [];
				}
				attrs.push(oElement.name.toUpperCase()+'='+Form.Element.getValue(oElement)||'');
			}
			else{
				var sValue = Form.Element.getValue(oElement);
				if(sValue!=null){
					var tmp = vData[oElement.name.toUpperCase()];
					if(!tmp){
						vData[oElement.name.toUpperCase()] = sValue;
					}
					else if(!Array.isArray(tmp)){
						var arr = vData[oElement.name.toUpperCase()] = [];
						arr.push(tmp);
						arr.push(sValue);
					}
					else{
						vData[oElement.name.toUpperCase()].push(sValue);
					}
				}
			}
		}
	}
	if(vData["__ATTRIBUTE__"]){
		vData["ATTRIBUTE"] = vData["__ATTRIBUTE__"].join("&");
		delete vData["__ATTRIBUTE__"];
	}
	for(var name in vData){
		if(Array.isArray(vData[name])){
			vData[name] = vData[name].join(",");
		}
	}
	return vData;
}
function doValidation(){
	var arrNow = ['DocTitle','DOCPEOPLE','SubDocTitle','DOCKEYWORDS',
			'DocAbstract','DocAuthor', 'DocSourceName'];
	var oValidInfo = ValidationHelper.valid.apply(ValidationHelper,arrNow.concat(ExtendFieldElements));
	for(var i=0;i<oValidInfo.length;i++){
		if(!oValidInfo[i].isValid()){
			$alert(oValidInfo[i].getWarning());
			return false;
		}
	}
	return true;
}
function Preview(_iDocType){
	var nDocType = parseInt($('DocType').value);
	if(doValidation()==false)return;
	//_iFlag==1时保存并发布
	var sDocHtml = '';
	var sDocContent = '';
	var sDocLink = '';
	var sDocFileName = '';
	if(nDocType==20){
		sDocHtml = GetHTML(true);
		sDocContent = GetText();
	}
	else if(nDocType==10){
		sDocHtml = '';
		sDocContent = $('_editorValue_').value;
	}
	else if(nDocType==30){
		sDocLink = $('DOCLINK').value;
		if(!sDocLink.match(/^(http|https|ftp):(\/){2,}.+$/ig)){
			$alert('链接地址不合法！正确格式为：http(s)|ftp://...');
			$('DOCLINK').focus();
			return;
		}
		if(sDocLink.byteLength()>100){
			$alert('链接地址长度不能超过100.');
			$('DOCLINK').focus();
			return;
		}
	}
	else if(nDocType==40){
		sDocFileName = $('DOCFILENAME').value;
	}
	try{
		if(nDocType==20){
			if(sDocHtml.trim()==''){
				$alert('正文内容不能为空.');
				return;
			}
		}
		else if(nDocType==10){
			if(sDocContent.trim()==''){
				$alert('正文内容不能为空.');
				return;
			}
		}
		else if(nDocType==30){
			if(sDocLink.trim()==''){
				$alert('链接内容不能为空.');
				return;
			}
		}
		else if(nDocType==40){
			if(sDocFileName.trim()==''){
				$alert('文件名称不能为空.');
				return;
			}
		}
	}catch(err){
	}
	var elements = ['ATTACHPIC','DOCTYPE','DOCPEOPLE','SubDocTitle','DOCKEYWORDS',
		'DocAbstract','DocAuthor','DocRelTime'].concat(ExtendFieldElements);
	var oPostData = getPostData.apply(null,elements);
//	,'DocContent','TITLECOLOR','ATTACHPIC','DOCLINK','DOCFILENAME','DOCCHANNEL'
	//'DOCHTMLCON';
	var eTempContainer = $('spDetailTemp');
	var eTemp = eTempContainer.children(0);

	var sTempId = (eTemp == null) ? '' : eTemp.getAttribute("_tempid",2);
	Object.extend(oPostData,{
		DocContent : sDocContent,
		DOCHTMLCON : sDocHtml,
		DocLink : sDocLink,
		DocFileName : sDocFileName,
		ObjectId : CurrDocId,
		ChannelId : DocChannelId,
		TitleColor : PageContext.TitleColor||'',
		DocTitle : getDocTitle(),
		DocSourceName : $('DocSourceName').value,
		DocSource : DocSourceHelper.FetchDocSource(),
		TemplateId	: sTempId,
		FlowDocId	: getParameter('FlowDocId') || 0 
	});
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call('wcm6_document','preview',oPostData,true,
		function(_transport,_json){
			var sPreviewUrl = com.trs.util.JSON.value(_json,'result');
			top.window.open(sPreviewUrl);
		}
	);
}
window.onSaveFailure = function(_transport,_json){
//	TODO
	if(_json){
		if($v(_json,'fault.code') == '17080') {
			$render500Err(_transport, _json, false, function(){
				window.Exit();
			});
			return;
		}
		//else
		$render500Err(_transport,_json);
	}

	if(window.whenSaveFailure){
		window.whenSaveFailure(_transport,_json);
	}
}
var isNewDoc = false;
function Save(_iDocType,_iFlag,CallBack){
	if(bIsReadOnly)return;
	isNewDoc = (CurrDocId==0);
	if(doValidation()==false)return;
	//_iFlag==1时保存并发布
	var sDocHtml = '';
	var sDocContent = '';
	var sDocLink = '';
	var sDocFileName = '';
	if(_iDocType==20){
		sDocHtml = GetHTML();
		sDocContent = GetText();
	}
	else if(_iDocType==10){
		sDocHtml = '';
		sDocContent = $('_editorValue_').value;
	}
	else if(_iDocType==30){
		sDocLink = $('DOCLINK').value;
		if(!sDocLink.match(/^(http|https|ftp):(\/){2,}.+$/ig)){
			$alert('链接地址不合法！正确格式为：http(s)|ftp://...');
			$('DOCLINK').focus();
			return;
		}
		if(sDocLink.byteLength()>100){
			$alert('链接地址长度不能超过100.');
			$('DOCLINK').focus();
			return;
		}
	}
	else if(_iDocType==40){
		sDocFileName = $('DOCFILENAME').value;
	}
	try{
		if(_iDocType==20){
			if(sDocHtml.trim()==''){
				$alert('正文内容不能为空.');
				return;
			}
		}
		else if(_iDocType==10){
			if(sDocContent.trim()==''){
				$alert('正文内容不能为空.');
				return;
			}
		}
		else if(_iDocType==30){
			if(sDocLink.trim()==''){
				$alert('链接内容不能为空.');
				return;
			}
		}
		else if(_iDocType==40){
			if(sDocFileName.trim()==''){
				$alert('文件名称不能为空.');
				return;
			}
		}
	}catch(err){
	}
	if(!doCKM('SimSearch')){
		return;
	}
	//较验限时置顶的时间是否有效
	if(bIsCanTop){
		if(!PageContext.PriSetResult&&PageContext.LastTopFlag==PageContext.TopFlag){//未改变置顶设置
		}
		else{
			if(PageContext.TopFlag==1||(!PageContext.PriSetResult&&PageContext.LastTopFlag==1)){//限时置顶
				var oInValidTime = Date.parseDate($('TopInvalidTime').value,'%Y-%m-%d %H:%M').getTime();
				var r = new Ajax.Request('../system/currtime.jsp',{
						method : 'get',
						asynchronous : false
					}
				);
				var nCurrTime = parseInt(r.transport.responseText.trim());
				if(oInValidTime-nCurrTime<=0){
					alert('设置了一个过期的限时置顶时间，请重置.')
					try{
						$('TopInvalidTime').focus();
					}catch(err){
						//Just Skip it.
					}
					return;
				}
				else if(oInValidTime-nCurrTime<=3*60*1000){
					alert('设置了一个过短(3分钟以内)的限时置顶时间，请重置');
					try{
						$('TopInvalidTime').focus();
					}catch(err){
						//Just Skip it.
					}
					return;
				}
			}
		};
	}
	ProcessBar.init('执行进度，请稍候...');
	ProcessBar.addState('正在保存文档内容.');
	ProcessBar.addState('正在保存引用栏目信息.');
	if(bIsCanTop){
		ProcessBar.addState('正在保存文档置顶信息.');
	}
	if(bIsCanPub&&!bIsNewsOrPics){
		ProcessBar.addState('正在保存文档模板信息.');
		ProcessBar.addState('正在保存文档发布设置信息.');
	}
	ProcessBar.addState('正在保存文档附件.');
	ProcessBar.addState('正在保存相关文档信息.');
	ProcessBar.addState('成功保存文档.');
	if(_iFlag==1){
		ProcessBar.addState('正在发布文档.');
	}
	ProcessBar.start();
	var steps = [];
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var nFlowDocId = getParameter('FlowDocId')||0;
	//文档内容保存
	steps.push(function (){
		var elements = ['ATTACHPIC','DOCTYPE','DOCPEOPLE','SubDocTitle','DOCKEYWORDS',
			'DocAbstract','DocAuthor','DocRelTime'].concat(ExtendFieldElements);
		var oPostData = getPostData.apply(null,elements);
	//	,'DocContent','TITLECOLOR','ATTACHPIC','DOCLINK','DOCFILENAME','DOCCHANNEL'
		//'DOCHTMLCON';
		Object.extend(oPostData,{
			DocContent : sDocContent,
			DOCHTMLCON : sDocHtml,
			DocLink : sDocLink,
			DocFileName : sDocFileName,
			ObjectId : CurrDocId,
			ChannelId : DocChannelId,
			TitleColor : PageContext.TitleColor||'',
			DocTitle : getDocTitle(),
			DocSourceName : $('DocSourceName').value,
			DocSource : DocSourceHelper.FetchDocSource(),
			FlowDocId : nFlowDocId,
			DocEditor : $('DocEditor').value
		});
//		if(CurrDocId==0 && $('DocSourceName').value.trim()!=''){
		if(CurrDocId==0){
			CookieHelper.setCookie('DOC_SOURCE_NAME', $('DocSourceName').value.trim());
		}
		oHelper.Call('wcm6_document','save',oPostData,true,
			function(_transport,_json){
				ProcessBar.next();
				notifyClearClipboard();
				if(CurrDocId==0){
					CurrDocId = com.trs.util.JSON.value(_json,'result');
				}
				if(steps.length>0){(steps.shift())()};
			},window.onSaveFailure,window.onSaveFailure
		);
	});
	//引用栏目保存
	steps.push(function (){
		var oPostData = {
			"DocumentId" : CurrDocId,
			"FromChannelId" : DocChannelId,
			"ToChannelIds" : getQuoteChannelIds(),
			FlowDocId : nFlowDocId
		}
		oHelper.Call('wcm6_document','setQuote',oPostData,true,
			function(_transport,_json){
				ProcessBar.next();
				if(steps.length>0){(steps.shift())()};
			}
		);
	});
	//置顶信息保存
	if(bIsCanTop){
		steps.push(function (){
			if(!PageContext.PriSetResult&&PageContext.LastTopFlag==PageContext.TopFlag){//未改变置顶设置
				ProcessBar.next();
				if(steps.length>0){(steps.shift())()};
				return;
			}
			if(PageContext.TopFlag==null){
				PageContext.TopFlag = PageContext.LastTopFlag;
			}
			if(!PageContext.PriSetResult){
				PageContext.PriSetResult = [CurrDocId,1,0];
			}
//			PageContext.PriSetResult = [_DocId,_iPosition,_TargetId];
//			_iPosition=1,表示当前文档在_TargetId之前
//			_iPosition=0,表示当前文档在_TargetId之后
//			_iPosition=2,表示当前文档在最后
			if(PageContext.PriSetResult[1]==2){
				PageContext.PriSetResult[1] = 1;
				PageContext.PriSetResult[2] = 0;
			}
			if(PageContext.TopFlag==2){
				PageContext.TopFlag = 3;
			}
			var oPostData = {
				"TopFlag" : PageContext.TopFlag,
				"ChannelId" : CurrChannelId,
				"DocumentId" : CurrDocId,//PageContext.PriSetResult[0],
				"Position" : PageContext.PriSetResult[1],
				"TargetDocumentId" : PageContext.PriSetResult[2],
				"InvalidTime" : (PageContext.TopFlag==1)?$('TopInvalidTime').value:'',
				FlowDocId : nFlowDocId
			}
			oHelper.Call('wcm6_document','setTopDocument',oPostData,true,
				function(_transport,_json){
					ProcessBar.next();
					if(steps.length>0){(steps.shift())()};
				}
			);
		});
	}
	//保存文档发布信息
//	if(bIsCanPub){
		steps.push(function (){
			var eTempContainer = $('spDetailTemp');
			var eTemp = eTempContainer.children(0);

			var sTempId = (eTemp == null) ? '' : eTemp.getAttribute("_tempid",2);
			var sOrigTempId = eTempContainer.getAttribute("_origTempId",2);
/*
			if(sTempId==sOrigTempId){
				ProcessBar.next();
				if(steps.length>0){(steps.shift())()};
				return;
			}
//*/
			var oPostData = {
				"ObjectId" : CurrDocId,
				"DetailTemplate" : sTempId,
				"ScheduleTime" : (bIsCanPub)?((PageContext.DefineSchedule)?$('ScheduleTime').value:''):'',
				FlowDocId : nFlowDocId
			}
			oHelper.Call('wcm6_publish','saveDocumentPublishConfig',oPostData,true,
				function(_transport,_json){
					ProcessBar.next();
					if(steps.length>0){(steps.shift())()};
				}
			);
		});
/*
			var oPostData = {
				"ObjectId" : CurrDocId,
				"ObjectType" : 605,
				"TemplateId" : sTempId,
				"TemplateType" : 2
			}
			oHelper.Call('wcm6_template','setDefaultTemplate',oPostData,true,
				function(_transport,_json){
					ProcessBar.next();
					if(steps.length>0){(steps.shift())()};
				}
			);
		});
		steps.push(function (){
			var oPostData = {
				"ObjectId" : CurrDocId,
				"ScheduleMode" : PageContext.DefineSchedule||'',
				"ExecTime" : $('ScheduleTime').value
			}
			oHelper.Call('wcm6_publish','saveDocumentPublishConfig',oPostData,true,
				function(_transport,_json){
					ProcessBar.next();
					if(steps.length>0){(steps.shift())()};
				}
			);
		});
//*/
//	}
	//保存文档附件
	steps.push(function (){
		var aCombine = [];
		var oPostData = {
			"DocId" : CurrDocId,
			"AppendixType" : 10,
			"AppendixesXML" : getAppendixesXML(10),
			FlowDocId : nFlowDocId
		}
		aCombine.push(oHelper.Combine('wcm6_document','saveAppendixes',oPostData));
		oPostData = {
			"DocId" : CurrDocId,
			"AppendixType" : 20,
			"AppendixesXML" : getAppendixesXML(20),
			FlowDocId : nFlowDocId
		}
		aCombine.push(oHelper.Combine('wcm6_document','saveAppendixes',oPostData));
		oPostData = {
			"DocId" : CurrDocId,
			"AppendixType" : 40,
			"AppendixesXML" : getAppendixesXML(40),
			FlowDocId : nFlowDocId
		}
		aCombine.push(oHelper.Combine('wcm6_document','saveAppendixes',oPostData));
		oHelper.MultiCall(aCombine,
			function(_transport,_json){
				ProcessBar.next();
				if(steps.length>0){(steps.shift())()};
			}
		);
	});
	//保存相关文档信息
	steps.push(function (){
		var oPostData = {
			"DocId" : CurrDocId,
			"RelationsXML" : getRelationsXML(),
			FlowDocId : nFlowDocId
		}
		oHelper.Call('wcm6_document','saveRelation',oPostData,true,
			function(_transport,_json){
				ProcessBar.next();
				if(steps.length>0){(steps.shift())()};
			}
		);
	});
	//完成保存文档
	steps.push(function (){
		ProcessBar.next();
		if(steps.length>0){(steps.shift())()};
	});
	//发布文档
	if(bIsCanPub&&_iFlag==1){
		steps.push(function (){
			var oPostData = {
				"ObjectIds" : CurrDocId,
				"ObjectType" : 605,
				FlowDocId : nFlowDocId
			}
			oHelper.Call('wcm6_publish','directPublish',oPostData,true,
				function(_transport,_json){
					ProcessBar.next();
					if(steps.length>0){(steps.shift())()};
				}
			);
		});
	}
	steps.push(CallBack);
	if(steps.length>0){(steps.shift())()};
}
function getAppendixesXML(iType){
	/**
	 *  <OBJECTS>
	 *		<OBJECT ID="" APPFILE="" SRCFILE="" APPLINKALT="" APPFLAG="" APPDESC=""/>
	 *	</OBJECTS>
	 */
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
function getRelationsXML(){
	/**
	 *  <OBJECTS>
	 *		<OBJECT ID="" RELDOCID=""/>
	 *	</OBJECTS>
	 */
	var arr = com.trs.util.JSON.array(Relations,"RELATIONS.RELATION")||[];
	var myValue = com.trs.util.JSON.value;
	var sRetVal = '<OBJECTS>';
	for(var i=0;i<arr.length;i++){
		var oRelation = arr[i];
		sRetVal += '<OBJECT ID="'+(myValue(oRelation,'RELATIONID')||0)+'" RELDOCID="'+myValue(oRelation,'RELDOC.ID')+'"/>';
	}
	sRetVal += '</OBJECTS>';
	return sRetVal;
}
function Publish(){
	Save(parseInt($('DocType').value),1);
}
function Exit(){
//TODO
	try{
		if(top.window.opener){
//			top.window.opener.$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', ""+CurrDocId);
			top.window.opener.$MessageCenter.sendMessage('main', 'PageContext.callBackByEditor', 'PageContext', {isNewDoc:isNewDoc,DocumentId:CurrDocId});
		}
	}catch(err){}
	try{
		if(top.window.opener){
			top.window.opener.PageContext.RefreshCurrDoc();
		}
	}catch(err){}
	top.window.opener = null;
	top.window.close();
}
function SimpleExit(){
	top.window.opener = null;
	top.window.close();
}
function SaveExit(){
	Save(parseInt($('DocType').value),0,Exit);
}
function AddNew(){
	try{
		if(top.window.opener){
//			top.window.opener.$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', CurrDocId);
			top.window.opener.$MessageCenter.sendMessage('main', 'PageContext.callBackByEditor', 'PageContext', {isNewDoc:isNewDoc,DocumentId:CurrDocId});
		}
	}catch(err){}
	try{//Detail Show
		if(top.window.opener&&top.window.opener.PageContext.RefreshCurrDoc){
			top.window.opener.PageContext.RefreshCurrDoc();
		}
	}catch(err){}
	top.window.location.href = top.window.location.href.replace(/DocumentId=[^&]*/ig,'DocumentId=0');
}
function SaveAddNew(){
	Save(parseInt($('DocType').value),0,AddNew);
}
function PublishAddNew(){
	Save(parseInt($('DocType').value),1,AddNew);
}
function SavePublish(){
	Save(parseInt($('DocType').value),1,Exit);
}
function GetEditorBody(){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		return FCK.EditorDocument.body;
	}
	return false;
}
function toggleToolbar(_sName){
	if(_sName=='WCM6'){
		$('xToolbar_row').className = 'editor_toolbar';
		$('xToolbar').className = 'xToolbar';
	}
	else if(_sName=='WCM6_ADV'){
		$('xToolbar_row').className = 'editor_toolbar_adv';
		$('xToolbar').className = 'xToolbar_adv';
	}
}
function GetEditorDocument(){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		return FCK.EditorDocument;
	}
	return false;
}
function GetEditorWindow(){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		return FCK.EditorWindow;
	}
	return false;
}
function GetEditor(){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var oEditor = oTrsEditor.contentWindow.GetTrueEditor();
		return oEditor;
	}
	return false;
}
function GetText(){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		/*
		var sHtml = FCK.GetHTML(true,true);
		var eTmpDiv = document.createElement('DIV');
		try{
			eTmpDiv.innerHTML = sHtml;
		}catch(err){
			//alert(FCK.EditorDocument.documentElement.innerHTML);
			//alert(err.message + sHtml);
		}
		var sRetVal = eTmpDiv.innerText;
		delete eTmpDiv;
		return sRetVal;
		*/
		return FCK.QuickGetText();
	}
	return '';
}
function GetHTML(_bPreview){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
		if(!_bPreview && oWindow.OfficeActiveX)
			oWindow.OfficeActiveX.UploadLocals();
		return FCK.QuickGetHtml(true,true);
	}
	return '';
}
function GetTrueText(){
	if(Element.visible('html_editor')){
		return GetText();
	}
	else if(Element.visible('txt_editor')){
		return $('_editorValue_').value;
	}
	return '';
}
function GetContent(){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		return FCK.QuickGetHtml(true,true);
	}
	else if(Element.visible('txt_editor')){
		return $('_editorValue_').value;
	}
	return '';
}
function notifyClearClipboard(){
	var oTrsEditor = $('_trs_editor_');
	if(oTrsEditor){
		try{
			oTrsEditor.contentWindow.ClearAutoPasteData();
		}catch(err){
			//Just skip it.
		}
	}
}
function SetHTML(_sHTML){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		oTrsEditor.contentWindow.SetHTML(_sHTML);
	}
}
function SwitchDocEditPanel(_iValue){
	PageContext.DocType = _iValue;
	switch(parseInt(_iValue)){
		case 20:
			if(Element.visible('txt_editor')){
				var oTrsEditor = $('_trs_editor_');
				var eTmpDiv = document.createElement('DIV');
				eTmpDiv.innerText = $('_editorValue_').value;
				delete eTmpDiv;
				try{
					var FCK = oTrsEditor.contentWindow.GetEditor();
					oTrsEditor.contentWindow.SetHTML(eTmpDiv.innerHTML);
				}catch(err){
					alert('HTML编辑器尚未加载完成，请稍后再试.');
					return;
				}
			}
			Element.hide('link_editor');
			Element.hide('txt_editor');
			Element.hide('file_editor');
			Element.show('html_editor');
			break;
		case 10:
			var sValue = $('_editorValue_').value;
			var bConfirm = true;
			if(Element.visible('html_editor')){
				var oTrsEditor = $('_trs_editor_');
				var FCK = oTrsEditor.contentWindow.GetEditor();
				sValue = FCK.QuickGetHtml(true,true);
				if(sValue.match(/<[^>]+>/ig)){
					bConfirm = confirm('从HTML切换到纯文本，当前文档中的字体、格式等信息都将丢失，是否确认切换？');
				}
				if(bConfirm){
					sValue = FCK.QuickGetText(true,true);
//					sValue = FCK.EditingArea.Document.body.innerText||sValue.replace(/<[^>]+>/ig,'');
				}
			}
			if(bConfirm){
				$('_editorValue_').value = sValue;
//				Element.hide('xToolbar');
				Element.hide('link_editor');
				Element.show('txt_editor');
				Element.hide('file_editor');
				Element.hide('html_editor');
//				Element.hide('btns4html');
			}
			break;
		case 30:
//			Element.hide('xToolbar');
			Element.show('link_editor');
			Element.hide('txt_editor');
			Element.hide('file_editor');
			Element.hide('html_editor');
//			Element.hide('btns4html');
			break;
		case 40:
//			Element.hide('xToolbar');
			Element.hide('link_editor');
			Element.hide('txt_editor');
			Element.show('file_editor');
			Element.hide('html_editor');
//			Element.hide('btns4html');
			break;
	}
}
function ManageAttachment(){
	if(bIsReadOnly){
		FloatPanel.open('document/document_attachments_readonly.html', '附件管理', 700, 330);
	}
	else{
		FloatPanel.open('document/document_attachments.html', '附件管理', 700, 350);
	}
}
function ManageRelativeDoc(){
	if(bIsReadOnly){
		FloatPanel.open('document/document_relations_readonly.html', '相关文档管理', 700, 250);
	}
	else{
		FloatPanel.open('document/document_relations.html', '相关文档管理', 700, 550);
	}
}
function ManageHotWord(){
	FloatPanel.open('document/document_hotwords_replace.html', '热词替换', 608, 250);
}
function ClearHotWordLinkNodes(parentNode,myDoc){
	var childNodes = parentNode.childNodes;
	for ( var i = 0 ; i < childNodes.length ; i++ ){
		var oNode = childNodes[i] ;
		if(oNode.nodeName.toLowerCase()=='a'){
			if(oNode.name=="AnchorAddByWCM"){
				var sValue = oNode.innerText;
				var oTextNode = myDoc.createTextNode(sValue);
				oNode.parentNode.insertBefore(oTextNode,oNode.nextSibling);
				$removeNode(oNode);
			}
		}
		else if(oNode.nodeType!=3){
			ClearHotWordLinkNodes(oNode,myDoc);
		}
	}
}
function clearAllHotWord(){
	var myDoc = GetEditorDocument();
	ClearHotWordLinkNodes(myDoc.body,myDoc);
}

function InsertImage(){
	GetEditor().FCKFocusManager.Lock();
	FloatPanel.open('document/document_insert_image.html', '插入图片', 700, 300);
}

function SelectAllQuoteChannel(){
	var checkBoxs = document.getElementsByName('cb_quotechannel');
	var tmpCb = null;
	var hasNotChecked = false;
	for(var i=0;i<checkBoxs.length;i++){
		tmpCb = checkBoxs[i];
		if(!tmpCb.disabled&&!tmpCb.checked){
			hasNotChecked = true;
		}
	}
	for(var i=0;i<checkBoxs.length;i++){
		tmpCb = checkBoxs[i];
		if(!tmpCb.disabled){
			tmpCb.checked = hasNotChecked;
		}
	}
}
function AddQuoteChannel(){
	if(bIsReadOnly)return;
	var eQuoteChannels = $('QuoteChannels');
	var sChannelIds = eQuoteChannels.getAttribute("ChannelIds",2);
	var oParams = {
		"SelectedChannelIds" : sChannelIds,
		"FromChannelId" : CurrChannelId
	}
	FloatPanel.open('document/document_select_quotechannel.html?' + $toQueryStr(oParams), '引用文档到...', 300, 350);
}
function SetQuoteChannels(_aChannelIds,_aChannelNames){
	if(bIsReadOnly)return;
	var eQuoteChannels = $('QuoteChannels');
	RemoveAllQuoteChannel();
	var sHtml = '';
	var sRowHtml = '<TR id="quotechanel_row_#ID#" class="attr_quotechanel_row" valign="middle">\
		<TD>\
			<input type="checkbox" name="cb_quotechannel" value="#ID#" #DD#>\
		</TD>\
		<TD class="attr_quotechanel_column">\
			#NAME#\
		</TD>\
	</TR>';
	_aChannelIds.each(function(_sId,_index){
		sHtml += sRowHtml.replace(/#ID#/ig,_sId).replace(/#NAME#/ig,_aChannelNames[_index]||'todo').replace(/#DD#/ig,'');
	});
	if(_aChannelIds.length==0){
		sHtml += sRowHtml.replace(/#ID#/ig,'').replace(/#DD#/ig,'disabled').replace(/#NAME#/ig,'');
	}
	var tBody = $('attr_quotechanel_tbody');
	new Insertion.Bottom(tBody,sHtml);
	eQuoteChannels.setAttribute("ChannelIds",_aChannelIds.join(','));
	$('QuoteChannelsNum').innerHTML = _aChannelIds.length;

	UpdateQuoteTitle(_aChannelIds);
}

function UpdateQuoteTitle(_aChannelIds){
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var sURL = "../channel/channels_get_path.jsp";
	var oPost = {ChannelIds : _aChannelIds.join()};
	oHelper.JspRequest(sURL, oPost, true, function(_transport, _json){
		try{
			var sChannelPath = eval("(" + _transport.responseText.trim() + ")");
			for(var index = 0; index < _aChannelIds.length; index++){
				var element = $('quotechanel_row_' + _aChannelIds[index]);
				if(!element) continue;
				element.title = sChannelPath[index];
			}
		}catch(error){
			alert(error.message);
		}
	});
}

function RemoveAllQuoteChannel(){
	if(bIsReadOnly)return;
	var tBody = $('attr_quotechanel_tbody');
	$removeChilds(tBody);
	$('QuoteChannelsNum').innerHTML = 0;
	var eQuoteChannels = $('QuoteChannels');
	eQuoteChannels.setAttribute("ChannelIds","");
}
function RemoveSelectedQuoteChannel(){
	if(bIsReadOnly)return;
	var checkBoxs = document.getElementsByName('cb_quotechannel');
	var tmpCb = null;
	var selectedChannelIds = [];
	for(var i=0;i<checkBoxs.length;i++){
		tmpCb = checkBoxs[i];
		if(!tmpCb.disabled&&tmpCb.checked){
			selectedChannelIds.push(tmpCb.value);
		}
	}
	selectedChannelIds.each(function(_sId){
		var eRow = $('quotechanel_row_'+_sId);
		eRow.parentNode.deleteRow(eRow.rowIndex-1);
	});
	var eQuoteChannels = $('QuoteChannels');
	var sChannelIds = eQuoteChannels.getAttribute("ChannelIds",2);
	if(sChannelIds.trim()!=''){
		sChannelIds = sChannelIds.split(',');
		sChannelIds = sChannelIds.without.apply(sChannelIds,selectedChannelIds);
		eQuoteChannels.setAttribute("ChannelIds",sChannelIds.join(','));
		$('QuoteChannelsNum').innerHTML = sChannelIds.length;
	}
	else{
		$('QuoteChannelsNum').innerHTML = 0;
	}
}
function ShowAttrColumn(_sId){
	var contents = document.getElementsByClassName('attr_column_content');
	for(var i=0;i<contents.length;i++){
		if(contents[i].id==_sId+'_attr_content'){
			Element.show(contents[i]);
		}
		else{
			Element.hide(contents[i]);
		}
	}
	return false;
}
function RemoveCurrDocRow(){
	if(bIsReadOnly)return;
	var sId = CurrDocId;
	var eBody = $('attr_topsort_tbody');
	var rows = eBody.rows;
	for(var i=0;i<rows.length;i++){
		if(rows[i].getAttribute("DocumentId")==sId){
			eBody.deleteRow(i);
			break;
		}
	}
	PageContext.PriSetResult = [sId,-1,0];
}
function InsertCurrDocRow(){
	if(bIsReadOnly)return;
	var sId = CurrDocId;
	var eBody = $('attr_topsort_tbody');
	var rows = eBody.rows;
	var targetDocId = 0;
	if(rows.length>0){
		targetDocId = rows[rows.length-1].getAttribute("DocumentId");
	}
	var newRow = eBody.insertRow(-1);
	newRow.align = 'center';
	newRow.valign = 'middle';
	newRow.className = 'attr_topsort_row current_doc_row';
	newRow.setAttribute('DocumentId',sId);
	var newCol = newRow.insertCell(-1);
	newCol.className = 'attr_topsort_title';
	newCol.innerHTML = '当前文档';
	PageContext.PriSetResult = [sId,0,targetDocId];
	new com.trs.wcm.PriSetDragger(newRow);
}

function PriSet(_iValue){
	if(bIsReadOnly)return;
	var eClicked = $('pri_set_'+_iValue);
	var eBody = $('attr_topsort_tbody');
	var bTopped = eBody.getAttribute("Topped",2)=="true";
	if(eClicked.checked){
		switch(_iValue){
			case 0://不置顶
				if(bTopped){
					RemoveCurrDocRow();
				}
				Element.hide('pri_set_deadline');
				break;
			case 1://限时置顶
				if(!bTopped){
					InsertCurrDocRow();
				}
				Element.show('pri_set_deadline');
				break;
			case 2://永久置顶
				if(!bTopped){
					InsertCurrDocRow();
				}
				Element.hide('pri_set_deadline');
				break;
		}
		PageContext.TopFlag = _iValue;
		eBody.setAttribute("Topped",""+(_iValue!=0));
	}
}
com.trs.wcm.PriSetDragger = Class.create('wcm.PriSetDragger');
com.trs.wcm.PriSetDragger.prototype = Object.extend({},com.trs.wcm.SimpleDragger.prototype);
Object.extend(com.trs.wcm.PriSetDragger.prototype,{
	delegate : function(_eRoot){
		this.shadow = _eRoot;
		var eBody = $('attr_topsort_tbody');
		this.rows = eBody.rows;
		var eHandler = document.createElement("SPAN");
		eHandler.innerHTML = this._getHint(_eRoot);
		document.body.appendChild(eHandler);
		this._handler = eHandler;
		Element.addClassName(this.shadow,"row_dragging");
		this.lastNextSibling = _eRoot.nextSibling;
		return eHandler;
	},
	_getHint : function(_eRoot){
		return 'test';
	},
	onDragStart : function(nx,ny,_pXY,_event){
		this._handler.style.left = _pXY[0] - 5;
		this._handler.style.top = _pXY[1] - 5;
	},
	onDrag : function(nx,ny,_pXY,_event){
		for(var i=0;i<this.rows.length;i++){
			var eRow = this.rows[i];
			var offset = Position.cumulativeOffset(eRow);
			var rOffset = Position.realOffset(eRow);
			var iTop = offset[1]-rOffset[1]+Position.deltaY;
			var iLeft = offset[0]-rOffset[0]+Position.deltaX;
			var iRight = iLeft+eRow.offsetWidth;
			var iBottom = iTop+eRow.offsetHeight;
			var iCenter = (iTop+iBottom)/2;
			if(eRow!=this.root){
				if(_pXY[0]>=iLeft&&_pXY[0]<=iRight){
					if(_pXY[1]>=iTop&&_pXY[1]<=iCenter){
						eRow.parentNode.insertBefore(this.shadow,eRow);
						break;
					}
					else if(_pXY[1]<=iBottom&&_pXY[1]>iCenter){
						eRow.parentNode.insertBefore(this.shadow,eRow.nextSibling);
						break;
					}
				}
			}
		}
	},
	_move : function(_eCurr,_iPosition,_eTarget){
		//_iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后,2表示最后,-1表示删除
		//alert(_eCurr.innerHTML)
	},
	onDragEnd : function(nx,ny){
		Element.removeClassName(this.shadow,"row_dragging");
		$removeNode(this._handler);
		var needMove = false;
		if(this.lastNextSibling!=this.root.nextSibling){
			if(this.root.nextSibling==null){
				needMove = this._move(this.root,0,this.root.previousSibling);
			}
			else{
				needMove = this._move(this.root,1,this.root.nextSibling);
			}
		}
		if(needMove===false){
			this.root.parentNode.insertBefore(this.root,this.lastNextSibling);
		}
		this.lastNextSibling = null;
		this.rows = null;
		this._handler = null;
		this.shadow = null;
	}
});
Object.extend(com.trs.wcm.PriSetDragger.prototype,{
	_getHint : function(_eRoot){
		return '<div class="attr_topsort_dragicon"></div>';
	},
	_move : function(_eCurr,_iPosition,_eTarget){
		//_iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
		var docid = _eCurr.getAttribute("DocumentId");
		var targetDocId = _eTarget.getAttribute("DocumentId");
		if(confirm('您确定要调整当前文档的置顶顺序？')){
			PageContext.PriSetResult = [docid,_iPosition,targetDocId];
		}
		else return false;
		delete _eCurr;
		delete _eNext;
		return true;
	}
});
function setPublishOnTime(_bChecked){
	PageContext.DefineSchedule = _bChecked;
	if(_bChecked){
		$('sp_PublishOnTime').style.display = '';
		$('sp_NotPublish').style.display = 'none';
	}
	else{
		$('sp_PublishOnTime').style.display = 'none';
		$('sp_NotPublish').style.display = '';
	}
}
var TemplateSelector = null;
function SelectTemps(){
	if(!TemplateSelector){
		TemplateSelector = new com.trs.wcm.TemplateSelector({objecttype:101,objectid:DocChannelId}, false, true);
	}
	TemplateSelector.selectTemps(false, 'spDetailTemp', 2);
}
function UnLockMe(){
	if(CurrDocId!=0){
		LockerUtil.unlock(CurrDocId,605);
	}
}
function CountTitle(ev){
	var nLength = eTitle.value.byteLength();
	eCountTitle.innerHTML = nLength;
	if(nLength<=200){
		eCountTitle.style.color = 'green';
	}
	else{
		eCountTitle.style.color = 'red';
	}
//	CalTitlePsn(ev,true);
}
function CalTitlePsn(ev,_bCal){
	var event = window.event||ev;
	if(!_bCal&&event.type=='keyup'&&event.keyCode!=Event.KEY_LEFT&&event.keyCode!=Event.KEY_RIGHT)return;
	try{
		var psn = GetCursorPsn(eTitle,true);
	}catch(err){
		psn = 0;
	}
	if(psn!=-1)
		eTitlePsn.innerHTML = psn;
	return true;
}
function SetTitleValue(_sTitle){
//	_sTitle = _sTitle.replace(/^<(p|div|span)\s*[^>]*>(.*)<\/\1>$/ig,'$2');
	_sTitle = _sTitle.replace(/^<(p|div|span)\s*[^>]*>((.|\n|\r)*)<\/\1>$/,function(_s0,_s1,_s2){
		if(_s2.indexOf('<\/'+_s1)!=-1)return _s0;
		return _s2;
	});
	$('DocTitle').value = _sTitle;
}
function SetAbstractValue(_sMemo){
	_sMemo = _sMemo.replace(/^<(p|div|span)\s*[^>]*>((.|\n|\r)*)<\/\1>$/,function(_s0,_s1,_s2){
		if(_s2.indexOf('<\/'+_s1)!=-1)return _s0;
		return _s2;
	});
	$('DocAbstract').value = _sMemo;
}
var oSimpleEditorDialog = null;
function transTxt(_sTxt){
	if(!/<.*>/.test(_sTxt)){
		return _sTxt.replace(/\n/g,'<br>');
	}
	return _sTxt;
}
function OpenSimpleEditor(_nType){
	var sToolBar = '';
	var sElementCmd = '';
	var fCallBack = '';
	var nWidth = 580;
	var nHeight = 200;
	if(_nType==1){
		sToolBar = 'Title';
		sElementCmd = 'parent.$("DocTitle").value';
		fCallBack = SetTitleValue;
		nWidth = 500;
		nHeight = 150;
	}
	else if(_nType==2){
		sToolBar = 'Abstract';
		sElementCmd = 'parent.transTxt(parent.$("DocAbstract").value)';
		fCallBack = SetAbstractValue;
		nWidth = 580;
		nHeight = 350;
	}
//	var sUrl = '../simpleeditor/editor.html';
	var sUrl = '../simpleeditor/index.html';
	if(oSimpleEditorDialog==null){
		oSimpleEditorDialog = TRSDialogContainer.register('Trs_Simple_Editor', 
				'简易编辑器', sUrl, nWidth, nHeight, true);
	}
	oSimpleEditorDialog.height = nHeight;
	oSimpleEditorDialog.width = nWidth;
	oSimpleEditorDialog.onFinished = fCallBack;
	TRSCrashBoard.setMaskable(true);
	TRSDialogContainer.display('Trs_Simple_Editor',{
		toolbar : sToolBar,
		valueEval : sElementCmd
	});
}
function FCKeditor_OnComplete(){
	$('_trs_editor_').style.display = '';
	$('bottom_toolbar').style.visibility = 'visible';
}
var eTitle,eCountTitle,eTitlePsn;
var CookieHelper = {
	loadCookie : function(){
		var myCookies = document.cookie.split(";");
		var oCookieData = {};
		for(var i=0; i<myCookies.length; i++){
			var cookiePair = myCookies[i].split("=");
			if(cookiePair[0].trim()=='expires')continue;
			oCookieData[cookiePair[0].trim()] = unescape((cookiePair[1]||''));
		}
		return oCookieData;
	},
	clearCookie : function(_sCookieName){
		var myCookie = '';
		var sSaveValue = null;
		myCookie += _sCookieName+"=false";
		var expires = new Date();
		expires.setTime(expires.getTime() - 1);
		myCookie += "; path=/; expires=" + expires.toGMTString() + ";domain=" + document.domain;
		document.cookie = myCookie;
	},
	setCookie : function(_sCookieName,_sCookieValue){
		var myCookie = '';
		var sSaveValue = null;
		sSaveValue = escape(_sCookieValue);
		myCookie += _sCookieName+"="+sSaveValue+"";
		var expires = new Date();
		expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000 * 30));
		myCookie += "; path=/; expires=" + expires.toGMTString()+";domain="+document.domain;
		document.cookie = myCookie;
	}
};
Event.observe(window,'load',function(){
	eTitle = $('DocTitle');
	eCountTitle = $('TitleCount');
	eTitlePsn = $('TitlePsn');
	if(CurrDocId==0){
		var eDocSourceName = document.getElementsByName('DocSourceName')[0];
		if(eDocSourceName!=null && eDocSourceName.value.trim() == ''){
			var oCookieData = CookieHelper.loadCookie();
			$('DocSource').value = eDocSourceName.value = oCookieData['DOC_SOURCE_NAME'] || '';
		}
	}
	var sOldTitle = eTitle.value;
	if(_IE){
		Event.observe(eTitle,'propertychange',function(ev){
			var event = window.event||ev;
			if(event.propertyName=='value'){
				CountTitle(event);
			}
		});
	}
	else{
		Event.observe(eTitle,'input',CountTitle);
	}
	if(sOldTitle != ''){
		eTitle.value = sOldTitle;
	}
	Event.observe(eTitle,'keyup',CalTitlePsn);
//	Event.observe(eTitle,'focus',CalTitlePsn);
	Event.observe(eTitle,'mouseup',CalTitlePsn);
//	buttonGroup.bindEvent();
	document.oncontextmenu = function(){
		return false;
	}
});
var tcc = new FCKTextColorCommand('ForeColor',function(_sColor){
	PageContext.TitleColor = _sColor || '';//'#000000';
	$('title_color').style.backgroundColor = PageContext.TitleColor||'#000000';
},'../editor/editor/skins/silver/fck_editor.css');
function SetTitleColor(_element){
	tcc.Execute(0,_element.offsetHeight,_element);
}
Event.observe(window,'unload',function(){
	UnLockMe();
	Event.stopAllObserving(eTitle);
	Event.stopAllObserving(eCountTitle);
	Event.stopAllObserving(eTitlePsn);
	eTitle = eCountTitle = eTitlePsn = null;
	FCKTextColorCommand_Cleanup.call(tcc);
	tcc = null;
//	$destroy(tcc);
	$destroy(ExtendFieldElements);
	$destroy(Relations);
	$destroy(Appendixs);
	$destroy(PageContext);
//	$destroy(buttonGroup);
	$destroy(TemplateSelector);
	ExtendFieldElements = null;
	Relations = null;
	Appendixs = null;
	PageContext = null;
//	buttonGroup = null;
	TemplateSelector = null;
	top.actualTop = null;
	if(document.body)document.body.onunload = null;
	window.onerror = window.onunload = null;
	document.oncontextmenu = null;
});
function setAttachPic(){
	$('AttachPic').checked = true;
	AttachPic(true);
}

function loadTRSAdOption(){
	//广告选件已打开
	if(window.bEnableAdInTrs) {
		try{
			var sUrl = '../../console/integrate_installed/admanage_introduction.jsp';
			var oTRSAction = new CTRSAction(sUrl);
			oTRSAction.setParameter('URL', 7);     
			oTRSAction.setParameter('Channel', '1');
			var sResult = oTRSAction.doDialogAction(600, 600);	
			
			if(sResult){
				var sRootPath = trsad_config['root_path'];
				sRootPath = (sRootPath||sRootPath).endsWith("/") ? sRootPath:sRootPath + '/';
				sResult = sResult.replace("${admanage_root_path}", sRootPath);
//				sResult = '<script src="' + sResult + '" IGNOREAPD="1"></'+ 'script>';
				var oTrsEditor = $('_trs_editor_');
				var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
				if(oWindow.FCK.doAdInTrs)
					oWindow.FCK.doAdInTrs({src:sResult,IGNOREAPD:1});
			}
		}catch(err){
			//TODO
			alert('插入广告位出错：' + err.message);
		}
	}
};
//自动抽取
function extractAbstractAndKeywords(){
	 var sURL = "../../ckm/auto_extract_editor.jsp";
	 var content = GetTrueText();
	 new Ajax.Request(sURL, {  
		method : 'post', 
		contentType : 'application/x-www-form-urlencoded',	
		parameters : "Content="+encodeURIComponent(content),
		onSuccess : function(transport,_json) {
			try{    
				$('DocAbstract').value= $v(_json,'root.abstractcontent');   
				$('DOCKEYWORDS').value= $v(_json,'root.keywords');   ;
			}catch(error){
				//Just Skip it.
//				alert(error.message);
			}
		},
		onFailure : function(){
			alert('抽取失败.');
		}
	});
}

//语法检测
function checkWordSpell(){
	var args = {value:GetTrueText()};
	var nWidth	= 400;
	var nHeight = 500;
	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;

	//2.Construct parameters for dialog
	var sFeatures		= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
	+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
	+ "center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	//3.display Dialog
	var sURL = "../../ckm/document_spell_check.html";
	try{
		var oResult = window.showModalDialog(sURL, args, sFeatures);
	}catch(e){
		alert("您的IE插件已经将对话框拦截！\n"
		+ "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n"
		+ "给您造成不便，TRS致以深深的歉意！");		
	}	
}
//相关性检索
function SimSearch(){
	var args = [];
	args[0] = CurrDocId;
	args[1] = GetTrueText();

	//1.verify parameters
	var nWidth	= 700;
	var nHeight = 300;

	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;


	//2.Construct parameters for dialog
	var sFeatures		= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	//3.display Dialog
	var sURL = "../../ckm/document_sim_search.html";
	try{
		var bResult = window.showModalDialog(sURL, args, sFeatures);
		return bResult;			
	}catch(e){
		alert("您的IE插件已经将对话框拦截！\n"
				+ "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n"
				+ "给您造成不便，TRS致以深深的歉意！");		
	}	
	return true;
}
function doCKM(_sType){
	if(!window.CKMConfig||!window.CKMConfig['bCKM'+_sType])return true;
	switch(_sType){
		case 'SpellCheck'://语法检测
			return checkWordSpell();
			break;
		case 'Extract'://抽取
			return extractAbstractAndKeywords();
			break;
		case 'SimSearch'://相关性检索
			return SimSearch();
			break;
	}
}

var DocSourceHelper = {
	NotifyInput : function(){
		$('DocSourceName').value = $('DocSource').value;
	},
	NotifySelect : function(){
		if($('DocSource').value!=''){
			$('DocSource').value = '';
			$('DocSource').value = $('DocSourceName').value;
		}
	},
	FetchDocSource : function(){
		DocSourceHelper.NotifySelect();
		var nIndex = $('DocSource').selectedIndex;
		if(nIndex>=0){
			return ($('DocSource').options[nIndex]).getAttribute("sourceid");
		}
		return 0;
	}
}