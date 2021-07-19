Ext.apply(String.prototype, {
	byteLength : function(){
		var length = 0;
		this.replace(/[^\x00-\xff]/g,function(){length++;});
		return this.length+length;
	}
});

/*-------------------- 上传文件的逻辑 ----------------------*/
function fastCreate(namespace, scope){
	scope = scope || window;
	if(namespace==null || namespace=='')return;
	var arr = namespace.split('.');
	for(var i=0,n=arr.length;i<n;i++){
		if(!scope[arr[i]]){
			scope[arr[i]] = {};
		}
		scope = scope[arr[i]];
	}
}

function getAppendixArray(appendixes){
	var arr = $v(appendixes, "APPENDIXES.APPENDIX");
	if(arr==null || arr==false){
		fastCreate("APPENDIXES.APPENDIX", appendixes);
		arr = appendixes["APPENDIXES"]["APPENDIX"] = [];
	}
	else if(!Array.isArray(arr)){
		var tmpArr = appendixes["APPENDIXES"]["APPENDIX"] = [];
		tmpArr.push(arr);
		arr = tmpArr;
	}
	return arr;
}
//新增对外部图片的上传处理操作。
function uploadImg(_sImgSrc,_sAlt){
	var dataHelper = new com.trs.web2frame.BasicDataHelper();
	var oPostData = {
		"Sourfile":_sImgSrc
	};
	try{
		dataHelper.call("wcm61_viewDocument", 'uploadImg', oPostData, true,
		function(transport,_json){
			 cloneToAppendix(transport.responseText,_sAlt);
		});
	}catch(err){
		Ext.Msg.$alert(err.message, function(){
		});
	}
}

function cloneToAppendix(_realSrc,_sAlt){
	var dataHelper = new com.trs.web2frame.BasicDataHelper();
		var oPostData = {
		"Sourfile":_realSrc
	};
	dataHelper.call("wcm61_viewDocument", 'cloneFile', oPostData, true, 
	function(transport,_json){
		var sSrc = transport.responseText;
		sSrc = "../../file/read_image.jsp?FileName="+sSrc;
		var parameters = "photo="+encodeURIComponent(sSrc);
		var sUrl = WCMConstants.WCM6_PATH + "photo/photo_compress.jsp?"+parameters;
		var dialogArguments = window;
		var nWidth	= window.screen.width - 12;
		var nHeight = window.screen.height - 60;
		var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
		var bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
		if(bound == null){
			return;
		}
		var oAppendix = {};
		oAppendix["APPFILE"] = {};
		oAppendix["APPFILE"]["FILENAME"] = bound.FN;
		oAppendix["APPFILE"]["URL"] = bound.FN;
		oAppendix["SRCFILE"] = bound.FN;
		oAppendix["APPDESC"] = bound.FN;
		oAppendix["APPLINKALT"] = _sAlt;
		oAppendix["APPFLAG"] = 20;
		oAppendix["APPENDIXID"] = "0_" + $MsgCenter.genId();
		var appendixes = PgC.m_Appendixs["Type_20"] = PgC.m_Appendixs["Type_20"] || {};
		var arr = getAppendixArray(appendixes);
		arr.push(oAppendix);
		Ext.Msg.$alert("提取成功！");
	},function(){
		Ext.Msg.$alert("提取失败！");
	});
}
function createImageAppendix(_sImgSrc,_sAlt){
	var _sImgSrc = _sImgSrc;
	if(_sImgSrc.match(/^(http|https|ftp):/ig)){
		uploadImg(_sImgSrc,_sAlt);
		return;
	};
	if(_sImgSrc.indexOf("file") >= 0){
		_sImgSrc = uploadfromWord(_sImgSrc);
	}
	cloneToAppendix(_sImgSrc,_sAlt);
}

function uploadfromWord(sSrc){
	if(!sSrc.match(/^(file:\/{2,})/ig))return "";
	var sSrc = decodeURIComponent(sSrc.replace(/^(file:\/{2,})/ig,'')
		.replace(/\//g,'\\'));
	if(! Element.visible('html_editor')) return "";
	var oTrsEditor = $('_trs_editor_');
	var FCK = oTrsEditor.contentWindow.GetEditor();
	var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
	var sUploadedFile = oWindow.OfficeActiveX.m_OfficeActiveX.UploadFile(sSrc);
	return oWindow.OfficeActiveX._ExtractFileName(sUploadedFile);
}

//支持编辑器图片提取为图片库图片
function uploadToPhotoLib(oImage,_sImgSrc){
	if(_sImgSrc.match(/^(http|https|ftp):/ig)){
		upload(oImage,_sImgSrc);
		return;
	};
	if(_sImgSrc.indexOf("file") >= 0){
		_sImgSrc = uploadfromWord(_sImgSrc);
	}
	toPhtotLib(oImage,_sImgSrc);
}

function upload(oImage,_sImgSrc){
	var dataHelper = new com.trs.web2frame.BasicDataHelper();
	var oPostData = {
		"Sourfile":_sImgSrc
	};
	try{
		dataHelper.call("wcm61_viewDocument", 'uploadImg', oPostData, true,
		function(transport,_json){
			toPhtotLib(oImage,transport.responseText);
		});
	}catch(err){
		Ext.Msg.$alert(err.message, function(){
		});
	}
}

function toPhtotLib(oImage,_realSrc){
	//处理word中拷贝过来的图片
	var dataHelper = new com.trs.web2frame.BasicDataHelper();
	var cb = wcm.CrashBoard.get({
		id : 'Dialog_ImageKind_Selector',
		width : '250px',
		height : '360px',
		title : wcm.LANG.DOCUMENT_PROCESS_266 || '请选择所属的分类',
		appendParamsToUrl : false,
		url : WCMConstants.WCM6_PATH + 'photo/photos_site_step1.html',
		params : {
			mode : "radio",
			Type : 1
		},
		callback : function(param){
			var channelid = param[0].join(",");
			var sSrc = "../../file/read_image.jsp?FileName="+_realSrc;
			var parameters = "photo="+encodeURIComponent(sSrc);
			var sUrl = WCMConstants.WCM6_PATH + "photo/photo_compress.jsp?"+parameters+"&bAddTitleName=1";
			var dialogArguments = window;
			var nWidth	= window.screen.width - 12;
			var nHeight = window.screen.height - 60;
			var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
			var bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
			if(bound == null){
				return;
			}
			var oPostData = {UPLOADEDFILES:bound.FN,MAINKINDID:channelid,WATERMARKFILE:0,SOURCEFILES:bound.imageName}
			dataHelper.call("wcm6_photo", 'saveimageinfo', oPostData, true, 
			function(transport,_json){
				oImage.setAttribute("photodocid",transport.responseText);
				Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_237 || "提取成功！");
			},function(){
				Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_238 || "提取失败！");
			});
		}
	});
	cb.show();
}

function UpdateDocFile(){
	var sDocFileName = $("DocFile").value;
	try{
		FileUploadHelper.validFileExt(sDocFileName, ".*");
	}catch(err){
		Ext.Msg.$alert(err.message, function(){
			$("DocFile").focus();
		});
		return false;
	}
	YUIConnect.setForm('frmUploadDocFile', true, Ext.isSecure);
	YUIConnect.asyncRequest('POST',
		'../system/file_upload_dowith.jsp?FileParamName=DocFile',{
			"upload" : function(_transport){
				var sResponseText = _transport.responseText;
				FileUploadHelper.fileUploadedAlert(sResponseText,{
					succ : function(){
						$('DOCFILENAME').value = sResponseText;
					}
				});
			}
		}
	);
	return false;
}

/*-------------------- 获取文档附件 ----------------------*/
function getAppendixesXML(iType){
	/**
	 *  <OBJECTS>
	 *		<OBJECT ID="" APPFILE="" SRCFILE="" APPLINKALT="" APPFLAG="" APPDESC=""/>
	 *	</OBJECTS>
	 */
	var appendixs = PgC.m_Appendixs['Type_'+iType];
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
				if(myValue(oAppendix,'RELATEPHOTOIDS') != null)
					sRetVal += ' RELATEPHOTOIDS="' + myValue(oAppendix,'RELATEPHOTOIDS') + '"'
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

/*-------------------- 获取相关文档 ----------------------*/
function getRelationsXML(){
	/**
	 *  <OBJECTS>
	 *		<OBJECT ID="" RELDOCID="" CHANNELID=""/>
	 *	</OBJECTS>
	 */
	var arr = Ext.Json.array(PgC.m_Relations,"RELATIONS.RELATION")||[];
	var myValue = Ext.Json.value;
	var sRetVal = '<OBJECTS>';
	for(var i=0;i<arr.length;i++){
		var oRelation = arr[i];
		sRetVal += '<OBJECT ID="'+(myValue(oRelation,'RELATIONID')||0)+'" RELDOCID="'+myValue(oRelation,'RELDOC.ID')+ '" CHANNELID="'+myValue(oRelation,'RELDOC.CHANNELID')+'"/>';
		//sRetVal += '<OBJECT ID="'+(myValue(oRelation,'RELATIONID')||0)+'" RELDOCID="'+myValue(oRelation,'RELDOC.ID')+'"/>';
	}
	sRetVal += '</OBJECTS>';
	return sRetVal;
}
/*---------------- 获取html的内容 ------------------*/
function GetHTML(_bPreview){
	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
		if(!_bPreview && oWindow.OfficeActiveX)
			oWindow.OfficeActiveX.UploadLocals();
		var sRetrun = FCK.GetHTML_FORCHECK(true)//FCK.GetHTML(true,true);//FCK.getData();20111019
		if(sRetrun.replace(/(<p>)?(&nbsp;|\\s|　|<br\\s*(\/)?>)*(<\/p>)?/gi,"").trim() != "" ){
			if(oWindow.FCKConfig.AutoAppendStyle)
				sRetrun = ("<div class=TRS_Editor>" + sRetrun + "</div>");
		}
		if(FCK.saveStyle) {
			sRetrun = FCK.saveStyle + sRetrun;
		}
		return sRetrun;
	}
	return '';
}

/*function GetHTML(_bPreview){
	var oTrsEditor = $('_trs_editor_');
	return oTrsEditor.contentWindow.GetHTML();
}*/

/*---------------- 获取纯文本的内容 ------------------*/
function GetText(){
/*	if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		return FCK.document.getBody().getText();
	}
	else if(Element.visible('txt_editor')){
		return $('_editorValue_').value;
	}
	return '';
}*/
if(Element.visible('html_editor')){
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		return FCK.QuickGetText();
	}
	else if(Element.visible('txt_editor')){
		return $('_editorValue_').value;
	}
	return '';
}
/*---------------- 清空剪切板 ------------------*/
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

/*---------------- 切换文档类型编辑器相应改变 ------------------*/
String.prototype.replaceAll  = function(s1,s2){       
	return this.replace(new RegExp(s1,"gm"),s2);       
} 

function SwitchDocEditPanel(_nDocType){
	var nDocType = parseInt(_nDocType, 10);
	switch(nDocType){
		case 20:
			if(Element.visible('txt_editor')){
				var oTrsEditor = $('_trs_editor_');
				var eTmpDiv = document.createElement('DIV');
				if(Ext.isIE){
					eTmpDiv.innerText = $('_editorValue_').value;
				}else{
					eTmpDiv.innerHTML = $('_editorValue_').value;
					eTmpDiv.innerHTML = eTmpDiv.innerHTML.replaceAll('\n',"<BR>");
					eTmpDiv.innerHTML = eTmpDiv.innerHTML.replace(/\s/ig,"&nbsp;");
				}
				delete eTmpDiv;
				try{
					var FCK = oTrsEditor.contentWindow.GetEditor();
					oTrsEditor.contentWindow.SetHTML(eTmpDiv.innerHTML);
				}catch(err){
					alert(wcm.LANG.DOCUMENT_PROCESS_6 || 'HTML编辑器尚未加载完成,请稍后再试.');
					return;
				}
			}
			Element.hide('nothtml_editor');
			showCertainBlock(20);
			break;
		case 10:
			var sValue = $('_editorValue_').value;
			var oTrsEditor = $('_trs_editor_');
			var FCK = oTrsEditor.contentWindow.GetEditor();
			var bConfirm = confirm(wcm.LANG.DOCUMENT_PROCESS_7 || '切换到纯文本,当前文档中的字体,格式等信息都将丢失,是否确认切换?');
			if(bConfirm){
				Element.show('nothtml_editor');
				$('_editorValue_').value = FCK.QuickGetText(true,true);
				showCertainBlock(10);
			}else{
				showLastBlock();
			}
			break;
		case 30:
			Element.show('nothtml_editor');
			showCertainBlock(30);
			break;
		case 40:
			Element.show('nothtml_editor');
			showCertainBlock(40);
			break;
	}
}
function showLastBlock(){
	if(Element.visible('html_editor')){
		$("DocType").options[0].selected="selected";
		SwitchDocEditPanel(20);
	}else if(Element.visible('link_editor')){
		$("DocType").options[2].selected="selected";
		SwitchDocEditPanel(30);
	}else if(Element.visible('file_editor')){
		$("DocType").options[3].selected="selected";
		SwitchDocEditPanel(40);
	}
}
function showCertainBlock(n){
	Element.hide('file_editor');
	Element.hide('link_editor');
	Element.hide('txt_editor');
	Element.hide('html_editor');
	switch(n){
		case 10:
			Element.show('txt_editor');
			break;
		case 20:
			Element.show('html_editor');
			break;
		case 30:
			Element.show('link_editor');
			break;
		case 40:
			Element.show('file_editor');
			break;
	};
}
function UnLockMe(){
	if(CurrDocId!=0){
		LockerUtil.unlock(CurrDocId,605);
	}
}
function transTxt(_sTxt){
	if(!/<.*>/.test(_sTxt)){
		return _sTxt.replace(/\n/g,'<br>');
	}
	return _sTxt;
}
function k(s){
	return s;
}

/*---------------- 对一些基本数据的校验 ------------------*/
function doValidation(){
	if(bIsReadonly)return false;
	$('DocTitle').value = $('DocTitle').value.trim();
	var nDocType = parseInt($('DocType').value, 10);
	var filter = function(el){
		if(nDocType!=10 && el==$('frmData').DocContent)return true;
		if(nDocType!=20 && el==$('frmData').DocHtmlCon)return true;
		if(nDocType!=30 && el==$('DOCLINK'))return true;
		if(nDocType!=40 && el==$('DOCFILENAME'))return true;
		return false;
	};
	var rst = TRSValidator52.validatorForm('frmAction', true, filter);
	if(rst.valid)return true;
	Ext.Msg.$alert(rst.einfos.join('\n'), function(){
		try{rst.fstEle.focus();}catch(e){}
	});
	return false;
}

/*---------------- 预览或保存前的数据的准备 ------------------*/
function prepareDatas(_bPreview){
	var nDocType = parseInt($('DocType').value, 10);
	if(nDocType==20){
		$('frmData').DocHtmlCon.value = GetHTML(_bPreview);
		$('frmData').DocContent.value = GetText();
	}
	else if(nDocType==10){
		$('frmData').DocHtmlCon.value = '';
		$('frmData').DocContent.value = $('_editorValue_').value;
	}
	/*
	var doc_props = document.frames['doc_props'];
	if(doc_props.prepareDatas){
		doc_props.prepareDatas();
	}*/
}
function getFlowDocInfo(){
	var winFlowdoc = $('frmFlowDoc').contentWindow;
	if(!winFlowdoc.validate(false)) {
		return false;
	}
	var sPostData = winFlowdoc.buildData();
	return sPostData;
}
function makePostData(){
	var PostDataUtil = com.trs.web2frame.PostData;
	var postdata = PostDataUtil.form('frmAction', k);
	postdata = Ext.apply(postdata, PostDataUtil.form('frmData', k));
	postdata = Ext.apply(postdata, PostDataUtil.form('doc_props', k));
	postdata = Ext.apply(postdata, {
		FlowDocId	: getParameter('FlowDocId') || 0
	});
	postdata = Ext.apply(postdata, PgC.m_DocExtends);
	if($('frmFlowDoc') && $('frmFlowDoc').contentWindow.bCanInFlow){
		var flowDocInfo = getFlowDocInfo();
		if(!flowDocInfo) return false;
		postdata = Ext.apply(postdata, {
			ToUserIds : flowDocInfo.ToUserIds,
			NotifyTypes : flowDocInfo.NotifyTypes,
			startInFlow : true
		});
	}
	return postdata;
}
Ext.ns('PgC');
/*-------------------- 页面右侧添加引用后的展现(数据update了) ----------------------*/
var m_Templates = {
	quoto_table : [
		'<table border=0 cellspacing=1 cellpadding=0 class="extrict">',
		'<tbody>',
			'<tr bgcolor="#CCCCCC" align=center valign=middle>',
				'<td width="26">', wcm.LANG.DOCUMENT_PROCESS_178 || '序号','</td>',
				'<td>', wcm.LANG.DOCUMENT_PROCESS_179 || '栏目名称','</td>',
				'<td width="76">引用类型</td>',
				'<td width="32">',wcm.LANG.DOCUMENT_PROCESS_161 || '删除','</td>',
			'</tr>',
			'{0}',
		'</tbody>',
		'</table>'
	].join(''),
	quoto_item_tr : [
		'<tr bgcolor="#FFFFFF" align=center valign=middle>',
			'<td>{0}</td>',
			'<td align=left title="{1}-{2}"><div><span class="_quoteTypeImg">&nbsp;&nbsp;&nbsp;&nbsp;</span>{2}</div></td>',
			'<td><span><select id="quote_{0}" _quoteType="{3}" onchange="onchangeQuoteTypeStyle();"><option value="quote">链接引用</option><option value="mirror">镜像引用</option></select></span></td>',
			'<td _action="removeQuote" _channelid="{1}" _channeldesc="{2}"><span class="remove_quotechannel" title="', wcm.LANG.DOCUMENT_PROCESS_180 || "移除引用",'"></span></td>',
		'</tr>'
	].join(''),
	quoto_item_tr_blank : [
		'<tr bgcolor="#FFFFFF" align=center valign=middle>',
			'<td>&nbsp;</td>',
			'<td align=left>&nbsp;</td>',
			'<td>&nbsp;</td>',
			'<td>&nbsp;</td>',
		'</tr>'
	].join(''),
	topset_table : [
		'<table border=0 cellspacing=1 cellpadding=0 style="width:100%;table-layout:fixed;background:gray;">',
		'<thead>',
			'<tr bgcolor="#CCCCCC" align=center valign=middle>',
				'<td width="32">', wcm.LANG.DOCUMENT_PROCESS_178 || "序号",'</td>',
				'<td>', wcm.LANG.DOCUMENT_PROCESS_177 || "文档标题",'</td>',
				'<td width="40">', wcm.LANG.DOCUMENT_PROCESS_181 || "排序",'</td>',
			'</tr>',
		'</thead>',
		'<tbody id="topset_order_tbody">{0}</tbody>',
		'</table>'
	].join(''),
	topset_item_tr : [
		'<tr bgcolor="#FFFFFF" align=center valign=middle _docid="{0}" _doctitle="{2}">',
			'<td>{1}</td>',
			'<td align=left title="{0}"><div style="overflow:hidden">{2}</div></td>',
			'<td>&nbsp;</td>',
		'</tr>'
	].join(''),
	topset_curr_tr : [
		'<tr bgcolor="#FFFFCF" align=center valign=middle _docid="" _currdoc="1">',
			'<td>{0}</td>',
			'<td align=left style="color:red;">',wcm.LANG.DOCUMENT_PROCESS_182 || "--当前文档--",'</td>',
			'<td>',
				'<span class="topset_up" title="', wcm.LANG.DOCUMENT_PROCESS_183 || "上移",  '" _action="topsetUp">&nbsp;</span>',
				'<span class="topset_down" title="', wcm.LANG.DOCUMENT_PROCESS_184 || "下移", '" _action="topsetDown">&nbsp;</span>',
			'</td>',
		'</tr>'
	].join('')
}
Event.observe(window, 'unload', function(){
	//解锁
	if(CurrDocId!=0){
		LockerUtil.unlock(CurrDocId, 605);
	}
});

window.onbeforeunload = function (){ 	
	return "你确定要放弃修改直接退出?";
};

/*---------------- 下面一排操作按钮的相关逻辑 ------------------*/
Ext.apply(PgC, {
/*---------------- 直接退出 ------------------*/
	SimpleExit : function(){
		if(!window.m_zDonotConfirmExit){
			if(!confirm("你确定要放弃修改直接退出?")) return;
		}
		window.onbeforeunload = null;
		if(PageWin.fireEvent('beforeclose', {actionCmd : window.actionCmd}) === false) return;
		window.open("","_self");//fix ie7
		window.close();
	},
/*---------------- 预览 ------------------*/
	Preview : function(){
		prepareDatas(true);
		if(!doValidation())return;
		if(!ContentNotNull()) return;
		ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_185 || '预览文档');
		var postdata = {};
		var myDocument = CMSObj.createFrom({
			objType : WCMConstants.OBJ_TYPE_DOCUMENT,
			objId : CurrDocId
		}, {
			postdata : postdata
		});
		if(!myDocument.preview())return;
		if(nModal == 3){   //如果是镜像型引用,需调整为当前栏目
			Ext.apply(postdata, {CHANNELID : CurrChannelId});
		}
		m_oAjaxHelper.Call('wcm6_document', 'preview', postdata, true,
			function(_transport, _json){
				myDocument.previewUrl = $v(_json, 'result');
				myDocument.afterpreview();
			},function(_transport, _json){
				ProcessBar.close();
				Ext.Msg.fault({
                message : $v(_json, "FAULT.MESSAGE"),
                detail :  $v(_json, "FAULT.DETAIL")
            },wcm.LANG.DIALOG_SERVER_ERROR || '与服务器交互时出错啦!');
			}
		);
	},
/*---------------- 保存 ------------------*/
	save : function(next, bSaveAsDraft){
		window.onbeforeunload = null;
		window.m_zDonotConfirmExit = true;
		window.actionCmd = 'save';
		if(!bSaveAsDraft){
			if(!ContentNotNull()) return;
			//如果需要进入工作流流转，则需要校验流转信息
			if(getParameter("FlowDocId") > 0 && $('frmFlowDoc')){
				var winFlowdoc = $('frmFlowDoc').contentWindow;
				//如果选择的是诸如“签收”、“返工”或者“拒绝”之类的操作，则不校验，但要保存
				if(!winFlowdoc.isSpecialOption()) {
					if(!winFlowdoc.validate(false)) {
						return;
					}
				}
			}
		}
		if($('DocStatus')){
			var nDraftStatus = m_nDraftStatus;
			if(!m_nDraftStatus){
				nDraftStatus = 1028;//草稿状态
			}
			if(bSaveAsDraft){
				$('DocStatus').value = nDraftStatus;//草稿状态
			}else{
				$('DocStatus').value = 1;//此状态存放元素存在的情况下，保存要么是草稿，要么是新稿
			}
		}
		var aCombine = [];
		var myDocument = CMSObj.createFrom({
			objType : WCMConstants.OBJ_TYPE_DOCUMENT,
			objId : CurrDocId
		}, {
			combine : aCombine
		});
		prepareDatas();
		if(!doValidation())return;
		if(!myDocument.save())return;
		//新建保存时设置来源cookie值
		if(CurrDocId==0 && $('DocSourceName')){
			CookieHelper.setCookie('DOC_SOURCE_NAME', $('DocSourceName').value.trim());
		}
		ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_186 ||'保存文档');
		m_oAjaxHelper.JspMultiCall('../../app/document/document_addedit_dowith.jsp', aCombine,
			function(_transport){
				var info = eval(_transport.responseText);
				Ext.apply(myDocument, info);
				myDocument.objId = info.docId;
				var bToDoAfterSave = true;
				//如果需要进入工作流流转的处理，则先进行处理，再做aftersave,和关闭,由于是异步的，此段代码不能放在aftersave中，否则工作流的列表页面不能刷新掉
				if(getParameter("FlowDocId") > 0 && $('frmFlowDoc')){
					//组织一下要提交的数据
					var winFlowdoc = $('frmFlowDoc').contentWindow;
					var sPostData = winFlowdoc.buildPostXMLData();
					if(sPostData && sPostData.length > 0) {
						bToDoAfterSave = false;
						new ajaxRequest({
							url : '/wcm/center.do',
							postBody: sPostData,
							contentType : 'multipart/form-data',
							method : 'post',
							onComplete : function(_trans, _json){
								if(!myDocument.aftersave())
									return false;
								if(next)(next)();
							},
							on500 : function(_trans, _json){
								Element.hide('tblProcessing');
								alert(_trans.responseText);
							}
						});
					}
				}
				if(bToDoAfterSave){
					if(!myDocument.aftersave())
						return false;
					if(next)(next)();
				}
			}
		);
	},
/*---------------- 保存并关闭 ------------------*/
	SaveExit : function(){
		window.actionCmd = 'saveandexit';
		$('frmData').DirectlyPublish.value = 0;
		PgC.save(PgC.SimpleExit);
	},

	addnew : function(){
		if(PageWin.fireEvent('beforeclose', {actionCmd : window.actionCmd}) === false) return;
		location.href = location.href.replace(/DocumentId=[^&]*/ig, 'DocumentId=0');
	},
/*---------------- 保存并新建 ------------------*/
	SaveAddNew : function(){
		window.actionCmd = 'saveaddnew';
		$('frmData').DirectlyPublish.value = 0;
		PgC.save(PgC.addnew);
	},
/*---------------- 发布并新建 ------------------*/
	PublishAddNew : function(){
		window.actionCmd = 'publishaddnew';
		$('frmData').DirectlyPublish.value = 1;
		PgC.save(PgC.addnew);
	},
/*---------------- 保存并发布 ------------------*/
	SavePublish : function(next){
		window.actionCmd = 'savepublish';
		$('frmData').DirectlyPublish.value = 1;
		PgC.save(PgC.SimpleExit);
	},
/*---------------- 保存并流转 ------------------*/
	SaveFlow : function(next){
		window.actionCmd = 'saveflow';
		$('frmData').Force2start.value = "true";
		PgC.save(PgC.SimpleExit);
	},
	getActionItem : function(target){
		while(target!=null&&target.tagName!='BODY'){
			if(target.getAttribute('_action', 2)!=null)return target;
			target = target.parentNode;
		}
		return null;
	},
	SaveAsDraft : function(){
		window.actionCmd = 'saveasdraft';
		PgC.save(PgC.SimpleExit,true);
	},
/*---------------- 所属栏目的选择 ------------------*/
	selectChannel : function(){
		var caller = this;
		FloatPanel.open({
			src : '../../app/include/channel_select.html',
			title : wcm.LANG.DOCUMENT_PROCESS_14 || '文档另存到栏目...',
			callback : function(selectIds, selectChnlDescs){
				if(selectIds[0]==window.DocChannelId)return;
				//置换当前栏目
				if(selectIds[0]!=$('frmData').ChannelId.value){
					window.DocMove = true;
				}
				else{
					window.DocMove = false;
				}
				window.DocChannelId = selectIds[0];
				var spChnl = $('sp_DocChannel');
				var reDesc = $trans(selectChnlDescs[0]);
				spChnl.innerHTML = reDesc.length > 15 ? reDesc.substring(0,12) + ".." : reDesc;
				spChnl.title = selectIds[0] + "-" + reDesc;
				//置换扩展字段
				//置换模板
				//置换按钮
			},
			dialogArguments : {
				IsRadio : 1,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ExcludeInfoView : 1,
				ExcludeOnlySearch : 1,
				//ExcludeSelf : 1,
				ShowOneType : 1,
				MultiSites : 1,
				SelectedChannelIds : DocChannelId,
				CurrChannelId : DocChannelId,
				RightIndex : 31
			}
		});
	},
	getQuotedChannelIds : function(){
		return $('quoto_table').getAttribute("_selectedChannelIds", 2) || '';
	},
	getQuotedType : function(){
		return $('quoto_table').getAttribute("quoteType");
	},
/*---------------- by CC 20120420 获取初始的所有引用栏目 ------------------*/
	getInitQuotedChannelIds : function(){
		return $('quoto_table').getAttribute("_InitChannelIds", 2) || '-1';
	},
/*---------------- 引用到栏目的选择 ------------------*/
	setQuote : function(){
		var caller = this;
		var sQuoteType = "quote";
		//如果不是新建引用，是在原有引用基础上追加则进入if
		if($('quote_1')) {
			for (var i=0; i<PgC.getQuotedChannelIds().split(',').length; i++) {
				if(i==0) {
					var arrQuoteTypes = $('quote_1').getAttribute("_quoteType");
				}else {
					arrQuoteTypes += ","+$('quote_'+(i+1)).getAttribute("_quoteType");
				}
			}
			$('quoto_table').setAttribute("_arrQuoteTypes", arrQuoteTypes);
		}
		FloatPanel.open({
			src : '../../app/include/channel_select.html',
			title : wcm.LANG.DOCUMENT_PROCESS_15 || '文档引用到栏目...',
			callback : function(selectIds, selectChnlDescs){
				PgC.refreshQuoteTable(selectIds,selectChnlDescs,true);
			},
			dialogArguments : {
				IsRadio : 0,
				ExcludeTop : 0,
				ExcludeLink : 1,
				ExcludeInfoView : 1,
				ExcludeOnlySearch : 1,
				ExcludeSelf : 1,
				SiteTypes : '0,4',
				MultiSiteType : 0,
				SelectedChannelIds : caller.getQuotedChannelIds(),//数组越界！
				CurrChannelId : DocChannelId,
				RightIndex : 31,
				canEmpty : true
			}
		});
	},
	
	/*setQuote : function(){
		var caller = this;
		var selectedChannelIds = $('quoto_table').getAttribute("_selectedChannelIds");
		FloatPanel.open({
			src : '../../app/document/document_quoteto.html?channelids=' + DocChannelId + '&channelType=0&onlySelect=1&SelectedChannelIds='+selectedChannelIds,
			title : wcm.LANG.DOCUMENT_PROCESS_15 || '文档引用到栏目...',
			callback : function(selectIds,selectChnlDescs,sQuoteType){
				PgC.refreshQuoteTable(selectIds,selectChnlDescs,sQuoteType);
				FloatPanel.close();
			}
		});
	},*/
/*---------------- 移除引用 ------------------*/
	removeQuote : function(event, target, actionItem){
		//在移除前，先记住栏目的引用类型，保存在数组中-by@sj-2012-06-11
		var els = document.getElementsByClassName("_quoteTypeImg");
		for (var i=0; i<els.length; i++) {
			var quoteTypee = document.getElementById('quote_'+(i+1));
			var value = quoteTypee.getAttribute("_quoteType");
			var sQuoteTypes;
			if(!sQuoteTypes) {
				sQuoteTypes = value;
			}else {
				sQuoteTypes += "," + value;
			}
		}
		//=============================================
		var row = actionItem.parentNode;
		var sChnlId = actionItem.getAttribute('_channelid', 2);
		row.parentNode.deleteRow(row.rowIndex);
		var currChnlIds = this.getQuotedChannelIds().split(',');
		var sChnlDesc = actionItem.getAttribute('_channeldesc', 2);
		var currChnlDescs = ($('quoto_table').getAttribute("_selectedChannelDescs", 2) || '').split(',');
		//根据删除引用栏目id在id序列中的位置，计算出要删除的栏目，并在引用类型的数组中去除-by@sj-2012-06-11
		var pos = currChnlIds.indexOf(sChnlId);
		var arrQuoteTypes = (sQuoteTypes || "").split(',');
		arrQuoteTypes.remove(arrQuoteTypes[pos]);
		currChnlIds.remove(sChnlId);
		currChnlDescs.remove(sChnlDesc);
		//====================================================
		PgC.refreshQuoteTable(currChnlIds,currChnlDescs,false,arrQuoteTypes);
		window.focus();
	},
	refreshQuoteTable : function(currChnlIds,currChnlDescs,bAdd,arrQuoteTypes){
		var sQuoteType;
		//新建引用时默认为链接引用
		if(bAdd) {
			//没有被引用过
			if(PgC.getQuotedChannelIds()=="") {
				sQuoteType = "quote";
			//在原有引用的基础上添加引用
			}else {
				//记录原有引用类型
				var els = document.getElementsByClassName("_quoteTypeImg");
				for (var i=0; i<els.length; i++) {
					var quoteTypee = document.getElementById('quote_'+(i+1));
					var value = quoteTypee.getAttribute("_quoteType");
					var sQuoteTypes;
					if(!sQuoteTypes) {
						sQuoteTypes = value;
					}else {
						sQuoteTypes += "," + value;
					}
				}
				var arrOriginalQuoteTypes = (sQuoteTypes || "").split(',');
				//根据原有类型在数组中的位置，保留原有引用类型
				var arrNewArrQuoteTypes = new Array(currChnlIds.length);
				for (var i=0; i<PgC.getQuotedChannelIds().split(",").length; i++) {
					var pos = currChnlIds.indexOf(PgC.getQuotedChannelIds().split(",")[i]);
					if(pos >= 0) {
						arrNewArrQuoteTypes[pos] = arrOriginalQuoteTypes[i];
					}
				}
				//=======================================================
			}
		}
		var items = [];
		for(var i=0,n=currChnlIds.length; i<n; i++){
			//打开页面加载引用列表
			if(arrQuoteTypes) {
				items.push(String.format(m_Templates.quoto_item_tr, (i+1),
					currChnlIds[i], currChnlDescs[i],arrQuoteTypes[i]));
			}else {//点击引用时画出引用列表
				var tempQuoteTypes = $('quoto_table').getAttribute("_onlyselectedArrQuoteTypes");
				if(tempQuoteTypes) {
					tempQuoteTypes = tempQuoteTypes.split(",");
					if(!arrNewArrQuoteTypes){
						//默认新添加的引用为链接引用
						arrNewArrQuoteTypes_i = tempQuoteTypes[i];
					}else if(arrNewArrQuoteTypes && !arrNewArrQuoteTypes[i]) {
						arrNewArrQuoteTypes[i] = "quote";
						arrNewArrQuoteTypes_i = arrNewArrQuoteTypes[i];
					}else {
						arrNewArrQuoteTypes_i = arrNewArrQuoteTypes[i];
					}
				}else {
					if(!arrNewArrQuoteTypes){
						//默认新添加的引用为链接引用
						arrNewArrQuoteTypes_i = "quote";
					}else if(arrNewArrQuoteTypes && !arrNewArrQuoteTypes[i]) {
						arrNewArrQuoteTypes[i] = "quote";
						arrNewArrQuoteTypes_i = arrNewArrQuoteTypes[i];
					}else {
						arrNewArrQuoteTypes_i = arrNewArrQuoteTypes[i];
					}
				}
					/*if(!arrNewArrQuoteTypes){
						//默认新添加的引用为链接引用
						arrNewArrQuoteTypes_i = "quote";
					}else if(arrNewArrQuoteTypes && !arrNewArrQuoteTypes[i]) {
						arrNewArrQuoteTypes[i] = "quote";
						arrNewArrQuoteTypes_i = arrNewArrQuoteTypes[i];
					}else {
						arrNewArrQuoteTypes_i = arrNewArrQuoteTypes[i];
					}*/
				items.push(String.format(m_Templates.quoto_item_tr, (i+1),
					currChnlIds[i], currChnlDescs[i], arrNewArrQuoteTypes_i));
			}
		}
		if(currChnlIds.length==0){
			items.push(m_Templates.quoto_item_tr_blank);
		}
		var html = String.format(m_Templates.quoto_table, items.join(''));
		Element.update('quoto_table', html);
		$('quoto_table').setAttribute("_selectedChannelIds", currChnlIds.join());
		$('quoto_table').setAttribute("_selectedChannelDescs", currChnlDescs.join());
		$('sp_quotenum').innerHTML = currChnlIds.length;
		//默认加载引用类型，通过点击引用下拉菜单改变引用类型
		var els = document.getElementsByClassName("_quoteTypeImg");
		for (var i=0; i<els.length; i++) {
			var el = els[i];
			var quoteTypee = document.getElementById('quote_'+(i+1));
			var value = quoteTypee.getAttribute("_quoteType");
			Element.removeClassName(el,"quote");
			Element.removeClassName(el,"mirror");
			Element.addClassName(el,value);
			document.getElementById('quote_'+(i+1)).value = value;
		}
	},
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
			infos.push({recid:recid, doctitle:$transHtml(doctitle)});
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
			Element.show("display_padding");
			Element.show('pri_set_deadline');
		}
		PgC.makeCurrDocInTopList();
	},
/*---------------- 打开发布设置中的设置模板的窗口 ------------------*/
	selectTemplate : function(event, target){
		var cb = wcm.CrashBoard.get({
			id : 'TEMPLATE_SELECT_SOLO',
            width : '480px',
            height : '340px',
			left : document.body.clientWidth/3  + 'px',
			top : document.body.clientHeight/3  + 'px',
            title : wcm.LANG.DOCUMENT_PROCESS_209 || '选择模板',
            url : WCMConstants.WCM6_PATH + 'template/template_select_list.html?' + $toQueryStr({
				OBJECTID :  nModal == 2 ? DocChannelId : CurrChannelId,
				OBJECTTYPE : 101,
				FILTERTYPE : 12,
				templatetype : 2,
				templateIds : $('spDetailTemp').getAttribute('_tempid', 2) || 0
			}),
			callback : function(_args){
				$('spDetailTemp').setAttribute('_tempid', _args.selectedIds.join(","));
				$('spDetailTemp').setAttribute('_tempname', _args.selectedNames.join(',&nbsp;&nbsp;') || (wcm.LANG.DOCUMENT_PROCESS_190 || "无"));
				$('spDetailTemp').setAttribute('title', _args.selectedNames.join(',&nbsp;&nbsp;') || (wcm.LANG.DOCUMENT_PROCESS_190 || "无"));
				Element.update('spDetailTemp', formatShow(_args.selectedNames.join(',&nbsp;&nbsp;')) || (wcm.LANG.DOCUMENT_PROCESS_190 || "无"));
			},
			next : wcm.LANG.DOCUMENT_PROCESS_168 || '刷新'
        });
        cb.show();
	},
/*---------------- 是否定时发布的设置 ------------------*/
	defineSchedule : function(){
		var bDefined = $('ip_DefineSchedule').checked;
		if(bDefined){
			Element.show('sp_PublishOnTime');
			Element.hide('sp_NoPublish');
		}else{
			Element.hide('sp_PublishOnTime');
			Element.show('sp_NoPublish');
		}
	},
/*---------------- 打开附件管理的窗口 ------------------*/
	manageAttachment : function(){

       document.cookie="cid="+CurrChannelId;
		var caller = this;
		FloatPanel.open({
			src : 'document_attachments.html?DocChannelId=' + CurrChannelId,
			title : wcm.LANG.DOCUMENT_PROCESS_16 || '附件管理',
			callback : function(info){
				caller.m_Appendixs = Object.deepClone(info);
				var arr = $a(caller.m_Appendixs['Type_20'], 'APPENDIXES.APPENDIX');
				if(arr.length>0){
					$('AttachPic').checked = true;
				}
			},
			dialogArguments : caller.m_Appendixs
		});
	},
/*---------------- 打开相关文档管理的窗口 ------------------*/
	manageRelation : function(){
		var caller = this;
		FloatPanel.open({
			src : 'document_relations.html',
			title : wcm.LANG.DOCUMENT_PROCESS_17 || '相关文档管理',
			callback : function(info){
				caller.m_Relations = Object.deepClone(info);
			},
			dialogArguments : {
				relations : caller.m_Relations,
				CurrDocId : CurrDocId,
				CurrChannelId : CurrChannelId/*,
				DocKeyWords : $('DOCKEYWORDS').value*/
			}
		});
	},
/*---------------- 打开管理扩展字段的窗口 ------------------*/
	manageExtends : function(){
		Element.hide('advancedprops');
		Element.hide('basicprops');
		Element.hide('otherprops');
		Element.show('extendprops');
//		var caller = this;
//		FloatPanel.open({
//			src : 'document_addedit_extendedfield.jsp?DocumentId=' + CurrDocId + '&ChannelId=' + DocChannelId,
//			title : wcm.LANG.DOCUMENT_PROCESS_187 ||'扩展字段管理',
//			callback : function(info){
//				caller.m_DocExtends = Object.deepClone(info);
//			},
//			dialogArguments : {
//				docextends : caller.m_DocExtends
//			}
//		});
	},
/*---------------- 切换到基本属性窗口 ------------------*/
	manageBasic : function(){
		Element.hide('advancedprops');
		Element.show('basicprops');
		Element.hide('otherprops');
		Element.hide('extendprops');
	},
/*---------------- 切换到其它属性窗口 ------------------*/
	manageOther : function(){
		Element.hide('advancedprops');
		Element.hide('basicprops');
		Element.show('otherprops');
		Element.hide('extendprops');
	},
/*---------------- 切换到高级属性窗口 ------------------*/
	manageAdvanced : function(){
		Element.show('advancedprops');
		Element.hide('basicprops');
		Element.hide('otherprops');
		Element.hide('extendprops');
		setIframeHeight();
	},
/*---------------- 打开简易编辑器 ------------------*/
	openSimpleEditor : function(event){
		var cb = wcm.CrashBoard.get({
			id : 'SIMP_EDITOR',
            width : '800px',
            height : '300px',
			left : (event.pointer[0] - 300) + 'px',
			top : '40px',
            title : wcm.LANG.DOCUMENT_PROCESS_18 || '标题维护-简易编辑器',
			appendParamsToUrl : false,
            url : WCMConstants.WCM6_PATH + 'editor/simp_editor2.html',
			params : {
				html : $('DocTitle').value,
				Toolbar : 'Title'//'Abstract','Title','MetaData'
			},
			callback : function(params){
				var sTitle = params[0].replace(/^<(p|div|span)\s*[^>]*>((.|\n|\r)*)<\/\1>$/,function(_s0,_s1,_s2){
					if(_s2.indexOf('<\/'+_s1)!=-1)return _s0;
					return _s2;
				});
				$('DocTitle').value = sTitle;
			},
			cancel : wcm.LANG.DOCUMENT_PROCESS_224 || '关闭'
        });
        cb.show();
	},
	openSimpleEditor2 : function(event){
		var cb = wcm.CrashBoard.get({
			id : 'SIMP_EDITOR',
            width : '800px',
            height : '300px',			
			left : (event.pointer[0] - 620) + 'px',
			top : (event.pointer[1] - 150) + 'px',
            title : wcm.LANG.DOCUMENT_PROCESS_19 || '摘要维护-简易编辑器',
			appendParamsToUrl : false,
            url : WCMConstants.WCM6_PATH + 'editor/simp_editor2.html',
			params : {
				html : $('DocAbstract').value,
				Toolbar : 'Abstract'//'Abstract','Title','MetaData'
			},
			callback : function(params){
				var sTitle = params[0].replace(/^<(p|div|span)\s*[^>]*>((.|\n|\r)*)<\/\1>$/,function(_s0,_s1,_s2){
					if(_s2.indexOf('<\/'+_s1)!=-1)return _s0;
					return _s2;
				});
				$('DocAbstract').value = sTitle;
			},
			cancel : wcm.LANG.DOCUMENT_PROCESS_224 || '关闭'
        });
        cb.show();
	},
	titlecolor : function(event, target){
		var p = event.getPoint();
		var x = p.x + 4;
		var y = p.y + 4;
		var bubblePanel = new wcm.BubblePanel($('colorpicker'));
		bubblePanel.bubble([x,y]);
	},
	getSettingItem : function(target){
		while(target!=null&&target.tagName!='BODY'){
			if(Element.hasClassName(target, 'td_setting'))return target;
			target = target.parentNode;
		}
		return null;
	},
	init : function(){
		//Editor Header
		Ext.getBody().on('click', function(event, target){
			var actionItem = PgC.getActionItem(target);
			if(actionItem==null)return;
			var action = actionItem.getAttribute('_action', 2);
			if(!action || !PgC[action])return;
			PgC[action].apply(PgC, [event, target, actionItem]);
		});
		//
		var lastSettingItem = null;
		Ext.getBody().on('mouseover', function(event, target){
			var settingItem = PgC.getSettingItem(target);
			if(settingItem==lastSettingItem)return;
			if(lastSettingItem){
				Element.removeClassName(lastSettingItem, 'td_active');
			}
			lastSettingItem = settingItem;
			if(settingItem==null)return;
			Element.addClassName(settingItem, 'td_active');
		});
		//document_addedit_props.jsp设置的变量firstSettingItem
		var lastSettingItem2 = firstSettingItem || null;//$('td_basic_setting');
		Ext.get('props_setting').on('click', function(event, target){
			var settingItem = PgC.getSettingItem(target);
			if(settingItem==null)return;
			if(settingItem==lastSettingItem2)return;
			if(lastSettingItem2){
				Element.removeClassName(lastSettingItem2, 'td_active2');
			}
			Element.addClassName(settingItem, 'td_active2');
			lastSettingItem2 = settingItem;
		});
		var oAppendixs = null;
		try{
			oAppendixs = {
				Type_10:
					Ext.Json.parseXml(
						loadXml($('appendix_10').value)),
				Type_20:
					Ext.Json.parseXml(
						loadXml($('appendix_20').value)),
				Type_40:
					Ext.Json.parseXml(
						loadXml($('appendix_40').value))
			}
		}catch(err){
			oAppendixs = {
				Type_10:{},
				Type_20:{},
				Type_40:{}
			}
		}
		this.m_Appendixs = oAppendixs;
		//缓存相关文档管理中的数据
		var oRelations = null;
		try{
			oRelations = Ext.Json.parseXml(
						loadXml($('relations').value));
		}catch(err){
			oRelations = {};
		}
		this.m_Relations = oRelations;
		//colorpicker
		var m_colorpicker = new wcm.ColorPicker('colorpicker');
		m_colorpicker.doAfterClick = function(sColor){
			$('frmData').TitleColor.value = sColor || '';
			$('title_color').style.backgroundColor = sColor || '';
		}
	}
});

/*---------------- 文档来源的suggestion处理 ------------------*/
Event.observe(window, 'load', function(){
	if(!$('DocSourceName'))return;
	var sg1 = new wcm.Suggestion();
	sg1.init({
		el : 'DocSourceName',
		request : function(sValue){
			var all = [];
			BasicDataHelper.JspRequest(
			WCMConstants.WCM6_PATH + "nav_tree/source_create.jsp?SourceName=",
			{SiteId : nSiteId},  true,
			function(transport, json){
				var result = eval(transport.responseText.trim());
				for (var i = 0; i < result.length; i++){
					var sGroup = {};
					sGroup.value = result[i].title;
					sGroup.label = result[i].desc;
					all.push(sGroup);
				}
				var items = [];
				for (var i = 0; i < all.length; i++){
					if(all[i].label.toUpperCase().indexOf(sValue.toUpperCase()) >= 0) items.push(all[i]);
				}
				sg1.setItems(items);
			});
		}
	});
	if(getParameter('FlowDocId') > 0 && getParameter('WorklistViewType') == 1){
		PgC.manageAdvanced();
		$('frmFlowDocContainer').focus();
		Element.removeClassName($('td_basic_setting'), 'td_active2');
		Element.addClassName($('td_advanced_setting'), 'td_active2');
		lastSettingItem2 = $('td_advanced_setting');
	}
});
function ContentNotNull(){
	//添加对正文内容的非空判断
	var nDocType = parseInt($('DocType').value);
	var sDocHtml = '';
	var sDocContent = '';
	if(nDocType==20){
		/*为了控制_bGetAll的值，在此引入GetHTML_FORCHECK 20111019*/
		var oTrsEditor = $('_trs_editor_');
		var FCK = oTrsEditor.contentWindow.GetEditor();
		sDocHtml = FCK.GetHTML_FORCHECK(true);
		//sDocHtml = GetHTML();
		sDocContent = GetText();
	}
	else if(nDocType==10){
		sDocHtml = '';
		sDocContent = $('_editorValue_').value;
	}
	sDocHtml= sDocHtml.replace(/(<p>)?(&nbsp;|\\s|　|<br\\s*(\/)?>)*(<\/p>)?/gi,"");
	try{
		if(nDocType==20){
			if(sDocHtml.trim()=='' || sDocHtml.trim() == "<DIV class=TRS_Editor></DIV>"){
				Ext.Msg.alert(wcm.LANG.DOCUMENT_PROCESS_220 || '正文内容不能为空.');
				return false;
			}
		}
		else if(nDocType==10){
			if(sDocContent.trim()==''){
				Ext.Msg.alert(wcm.LANG.DOCUMENT_PROCESS_220 || '正文内容不能为空.');
				return false;
			}
		}
	}catch(err){
	}
	return true;
}
function CountTitle(event){
	var eTitle = $('DocTitle');
	var nLength = eTitle.value.byteLength();
	var eCountTitle = $('TitleCount');
	eCountTitle.innerHTML = nLength;
	if(nLength<=200){
		eCountTitle.style.color = 'green';
	}else{
		eCountTitle.style.color = 'red';
	}
}
function CalTitlePsn(event){
	var evt = event || window.event;
	var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
	//if(evt.type=='keyup'&&keyCode!=Event.KEY_LEFT&&keyCode!=Event.KEY_RIGHT)return;
	try{
		var psn = GetCursorPsn($('DocTitle'), true);
	}catch(err){
		psn = 0;
	}
	if(psn==-1)return;
	$('TitlePsn').innerHTML = psn;
}
//Cal Current cursor psn
function GetCursorPsn(_elInpt,_bUnicodeDouble){
	if(Ext.isIE){
		var slct = document.selection;
		var rng = slct.createRange();
		if(!rng.text||rng.text.length==0){
			_elInpt.select();
			rng.setEndPoint("StartToStart", slct.createRange());
			var psn = (_bUnicodeDouble)?rng.text.byteLength():rng.text.length;
			rng.collapse(false);
			rng.select();
			return psn;
		}
	}else{
		var obj = $("DocTitle");
		var prefix = obj.value.substring(0, obj.selectionStart);   
		return prefix.byteLength();
	}
	return -1;
}
function observeTitle(){
	var ua = navigator.userAgent.toLowerCase();
	var isIE9 = ua.indexOf("msie 9") > -1;
	if(Ext.isIE && !isIE9){
		//Ext.get('DocTitle').on('propertychange', function(event){
			//var event = event.browserEvent;
		Event.observe('DocTitle', 'propertychange',  function(event){
			event = window.event || event;
			if(event.propertyName=='value'){
				CountTitle(event);
			}
		});
	}else{
		if(isIE9){
			Ext.get('DocTitle').on('select', function(event){
				if($('DocTitle')) CountTitle(window.event);
			});
			Ext.get('DocTitle').on('blur', function(event){
				CalTitlePsn
			});
		}
		if($('DocTitle')) CountTitle(window.event);
		Ext.get('DocTitle').on('input', CountTitle);
	}
	$('DocTitle').value = $('DocTitle').value;
	Event.observe($('DocTitle'), 'keyup', CalTitlePsn);
	Event.observe($('DocTitle'), 'mouseup', CalTitlePsn);
}
/*---------------- 全屏打开编辑器 ------------------*/
function FullOpenEditor(bFull){
	Element[bFull ? 'addClassName' : 'removeClassName']('frmAction', 'full-edit');
}
//init
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
		if(document.domain =="localhost")
		myCookie += "; path=/; expires=" + expires.toGMTString() + ";";
		else{
			myCookie += "; path=/; expires=" + expires.toGMTString() + ";domain=" + document.domain;
		}
		document.cookie = myCookie;
	},
	setCookie : function(_sCookieName,_sCookieValue){
		var myCookie = '';
		var sSaveValue = null;
		sSaveValue = escape(_sCookieValue);
		myCookie += _sCookieName+"="+sSaveValue+"";
		var expires = new Date();
		expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000 * 30));
		if(document.domain =="localhost")
			myCookie += "; path=/; expires=" + expires.toGMTString()+";";
		else
			myCookie += "; path=/; expires=" + expires.toGMTString()+";domain="+document.domain;
		document.cookie = myCookie;
	}
};
Event.observe(window,'load',function(){
	//新建文档时自动设置文档来源
	if(CurrDocId==0 && use_predoc_docsource == "true"){
		var eDocSourceName = document.getElementById('DocSourceName');
		if(eDocSourceName!=null && eDocSourceName.value.trim() == ''){
			var oCookieData = CookieHelper.loadCookie();
			$('DocSourceName').value = eDocSourceName.value = oCookieData['DOC_SOURCE_NAME'] || '';
		}
	}
	observeTitle();
	PgC.init();
	window.focus();
});

//basic listen
/*---------------- 一些基本操作的监听 ------------------*/
var m_oAjaxHelper = new com.trs.web2frame.BasicDataHelper();
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		var context = event.getContext();
		return context && context.combine;
	},
	save : function(event){
		var aCombine = event.getContext().combine;
		var postdata = makePostData();
		if(!postdata) return false;
		if(nModal == 3){   //如果是镜像型引用,需调整为当前栏目
			Ext.apply(postdata, {CHANNELID : CurrChannelId});
		}
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_document',
			'save',
			postdata
		));
	},
	aftersave : function(event){
		var obj = event.getObj();
		//清空剪贴板
		try{
			var oTrsEditor = $('_trs_editor_');
			var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
			var oCookie = oWindow.FCK.loadCookie();
			if(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0")
				window.clipboardData.clearData('Text');
		}catch(err){
		}
		//关闭进度条
		ProcessBar.close();
		//刷新列表
		if(window.opener){
			try{
				if(getParameter('callback')){
					var fCallBack = eval("opener." + getParameter('callback'));
					if(fCallBack) {
						fCallBack(obj.getId(), obj.chnldocId);
						return true;
					}
				}
				if(getParameter('FlowDocId') > 0){
					window.opener.CMSObj.createFrom({
						objType : 'IFlowContent',
						objId : obj.getId()
					}).afteredit();
					return true;
				}
				var oMsgSrc = getParameter("openerTopAsMsgSrc") ? opener.top : opener;
				oMsgSrc.CMSObj.createFrom({
					objType : obj.getType(),
					objId : obj.getId()
				}, {chnldocId : obj.chnldocId}).aftersave();
				return true;
			}catch(err){
				alert(err.message);
			}
		}
	},
	beforepreview : function(event){
		var context = event.getContext();
		return context!=null;
	},
	preview : function(event){
		var context = event.getContext();
		var postdata = makePostData();
		Ext.apply(context.postdata, postdata);
	},
	afterpreview : function(event){
		var obj = event.getObj();
		$openMaxWin(obj.previewUrl, 'r_' + new Date().getTime());
		ProcessBar.close();
	}
});
//listening
/*---------------- 监听保存文档的时候设置引用 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		//组织参数，发送两次setQuote请求
		var selectedNum = PgC.getQuotedChannelIds().split(",").length;
		var selectedQuoteChannels,selectedMirrorChannels;
		if($('quote_1')) {
			for (var i=1; i<=selectedNum; i++) {
				var quoteType = $('quote_'+ i).value;
				if(quoteType=="quote") {
					if(!selectedQuoteChannels) {
						selectedQuoteChannels = PgC.getQuotedChannelIds().split(",")[i-1];
					}else {
						selectedQuoteChannels += ","+PgC.getQuotedChannelIds().split(",")[i-1];
					}
				}
				if(quoteType=="mirror") {
					if(!selectedMirrorChannels) {
						selectedMirrorChannels = PgC.getQuotedChannelIds().split(",")[i-1];
					}else {
						selectedMirrorChannels += ","+PgC.getQuotedChannelIds().split(",")[i-1];
					}
				}
			}
		}
		//20120420 by CC 获取到所有删除的引用栏目
		var sToChannelIds = PgC.getQuotedChannelIds();
		var sInitChannelIds = PgC.getInitQuotedChannelIds();
		var bIsIn = false;
		var sDelChannelIds = "";
		//初始化时，如果获取到的引用栏目为-1,则直接不进行其他操作了;
		if(sInitChannelIds != "-1"){
			var arrayInitChannelIds = sInitChannelIds.split(",");
			var arraysToChannelIds = sToChannelIds.split(",");
			for(var i=0; i<arrayInitChannelIds.length; i++){
				for(var j=0; j<arraysToChannelIds.length; j++){
					if(arrayInitChannelIds[i] == arraysToChannelIds[j]){
						bIsIn = true;
						continue;
					}
				}
				if(!bIsIn){
					if(!sDelChannelIds){
						sDelChannelIds = arrayInitChannelIds[i];
					}else{
						sDelChannelIds = sDelChannelIds + "," + arrayInitChannelIds[i];
					}
				} else{
					bIsIn = false;
				}
			}
		} else {
			sDelChannelIds = "-1";
		}
		//当对引用列表追加引用时，选中删除栏目的id序列为空，而不是预期的-1
		if(sDelChannelIds == "") {
			sDelChannelIds = "-1";
		}
		//先发送链接引用请求
		var postdata = {
			"QuoteType" : "quote",
			"DocumentId" : CurrDocId,
			"FromChannelId" : DocChannelId,
			"ToChannelIds" : selectedQuoteChannels,
			"DelChannelIds" : sDelChannelIds,
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_document',
			'setQuote',
			postdata
		));
		//再发送镜像引用请求
		postdata = {
			"QuoteType" : "mirror",
			"DocumentId" : CurrDocId,
			"FromChannelId" : DocChannelId,
			"ToChannelIds" : selectedMirrorChannels,
			"DelChannelIds" : sDelChannelIds,
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_document',
			'setQuote',
			postdata
		));
	}
});
/*---------------- 监听保存文档的时候设置置顶 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		if(!PgC.IsCanTop)return;
		var aCombine = event.getContext().combine;
		var info = PgC.getTopsetInfo();
		var postdata = {
			"TopFlag" : info.TopFlag,
			"ChannelId" : CurrChannelId,
			"Position" : info.Position,
			"DocumentId" : CurrDocId,
			"TargetDocumentId" : info.TargetDocumentId,
			"InvalidTime" : (PgC.TopFlag==1)?$('TopInvalidTime').value:'',
			FlowDocId : getParameter('FlowDocId') || 0
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_document',
			'setTopDocument',
			postdata
		));
	}
});
/*---------------- 监听保存文档的时候保存发布设置 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		if(!bIsCanPub)return;
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"ObjectId" : CurrDocId,
			"DetailTemplate" : $('spDetailTemp').getAttribute('_tempId', 2) || 0,//sTempId,
			"ScheduleTime" : $('ip_DefineSchedule').checked ? $('ScheduleTime').value : '',
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_publish',
			'saveDocumentPublishConfig',
			postdata
		));
	},
	preview : function(event){
		var context = event.getContext();
		Ext.apply(context.postdata, {
			TemplateId	: $('spDetailTemp').getAttribute('_tempId', 2) || 0
		});
	}
});
/*---------------- 监听保存文档的时候保存附件 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"DocId" : CurrDocId,
			"AppendixType" : 10,
			"AppendixesXML" : getAppendixesXML(10),
			FlowDocId : nFlowDocId
		}
		aCombine.push(m_oAjaxHelper.Combine('wcm6_document','saveAppendixes',postdata));
		postdata = {
			"DocId" : CurrDocId,
			"AppendixType" : 20,
			"AppendixesXML" : getAppendixesXML(20),
			FlowDocId : nFlowDocId
		}
		aCombine.push(m_oAjaxHelper.Combine('wcm6_document','saveAppendixes',postdata));
		postdata = {
			"DocId" : CurrDocId,
			"AppendixType" : 40,
			"AppendixesXML" : getAppendixesXML(40),
			FlowDocId : nFlowDocId
		}
		aCombine.push(m_oAjaxHelper.Combine('wcm6_document','saveAppendixes',postdata));
	}
});
/*---------------- 监听保存文档的时候保存相关文档 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"DocId" : CurrDocId,
			"RelationsXML" : getRelationsXML(),
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine('wcm6_document','saveRelation',postdata));
	}
});
/*---------------- 监听保存文档的时候执行移动文档 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		if(!window.DocMove)return;
		var FromChannelId = $('frmData').ChannelId.value;
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"ObjectIds" : CurrDocId,
			"FromChannelId" : FromChannelId,
			"ToChannelId" : DocChannelId,
			FlowDocId : nFlowDocId
		};
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_document',
			'move',
			postdata
		));
	}
});
/*---------------- 监听保存文档的时候直接发布 ------------------*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		if(!bIsCanPub || $('frmData').DirectlyPublish.value != 1)return;
		var aCombine = event.getContext().combine;
		var nFlowDocId = getParameter('FlowDocId');
		var postdata = {
			"ObjectIds" : CurrDocId,
			"ObjectType" : 605,
			FlowDocId : nFlowDocId
		}
		aCombine.push(m_oAjaxHelper.Combine(
			'wcm6_publish',
			'directPublish',
			postdata
		));
	}
});

function onPubjobset(){
	if($("unpubjob").checked){
		Element.show($("unpubjobdatetime"));
	}else{
		Element.hide($("unpubjobdatetime"));
	}
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var postData = {SenderType : 605,SchId:$F("UnpubSchId")||0};
		if($("unpubjob").checked){
			Object.extend(postData,{ETime:$F("UNPUBTIME")});
			aCombine.push(m_oAjaxHelper.Combine('wcm6_publish', 'setUnpubSchedule', postData));
		}else if($F("UnpubSchId")>0){
			Object.extend(postData,{Unset:1});
			aCombine.push(m_oAjaxHelper.Combine('wcm6_publish', 'setUnpubSchedule', postData));
		}
	}
});

//hdg@2010-1-28 限时置顶添加提醒
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		if($("pri_set_1").checked){
			var exetime = $F("TopInvalidTime") || "";
			if(exetime.trim() == ''){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_263 || "限时置顶时间不能为空", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('TopInvalidTime').focus();
						$('TopInvalidTime').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
			var s = exetime.split(" "); 
			var s1 = s[0].split("-"); 
			var s2 = s[1].split(":"); 
			var dtEtime = new Date(s1[0],s1[1]-1,s1[2],s2[0],s2[1]);
			var now = new Date();
			if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_261||"限时置顶时间不能早于当前时间", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('TopInvalidTime').focus();
						$('TopInvalidTime').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
		}
	}
});

//hdg@2010-1-28 定时发布添加提醒
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		if($("ip_DefineSchedule").checked){
			var exetime = $F("ScheduleTime") || "";
			if(exetime.trim() == ''){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_262 || "定时发布时间不能为空", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('ScheduleTime').focus();
						$('ScheduleTime').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
			var s = exetime.split(" "); 
			var s1 = s[0].split("-"); 
			var s2 = s[1].split(":"); 
			var dtEtime = new Date(s1[0],s1[1]-1,s1[2],s2[0],s2[1]);
			var now = new Date();
			if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_260||"定时发布时间不能早于当前时间", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('ScheduleTime').focus();
						$('ScheduleTime').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
		}
	}
});

//wenyh@2009-05-04 定时撤稿
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		if($("unpubjob").checked){
			var exetime = $F("UNPUBTIME") || "";
			if(exetime.trim() == ''){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_264 || "计划撤销发布时间不能为空", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('UNPUBTIME').focus();
						$('UNPUBTIME').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
			var s = exetime.split(" "); 
			var s1 = s[0].split("-"); 
			var s2 = s[1].split(":"); 
			var dtEtime = new Date(s1[0],s1[1]-1,s1[2],s2[0],s2[1]);
			var now = new Date();
			if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
				Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_207||"计划撤销发布时间不能早于当前时间", function(){
					try{
						oTabPanel.setActiveTab('tab-advance');
						$('UNPUBTIME').focus();
						$('UNPUBTIME').select();
					}catch(error){
						//just skip it
					}
				});
				return false;
			}
		}
	}
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	save : function(event){
		var aCombine = event.getContext().combine;
		var postData = {
			DocId : getParameter("DocId"),
			ChannelId : getParameter("ChannelId"),
			ChnlDocId : getParameter("ChnlDocId")
		};
		aCombine.push(m_oAjaxHelper.Combine('wcm61_viewdocument', 'findChnlDocId', postData));
	}
});

//广告选件
function loadTRSAdOption(){
	//广告选件已打开
	if(window.bEnableAdInTrs) {
		try{
			var strsAdCon = trsad_config['root_path'];
			if(strsAdCon==null)return;
			var nStrLen = strsAdCon.length;
			if(strsAdCon.charAt(nStrLen-1)!='/'){
				strsAdCon = strsAdCon + '/';
			}
			var sUrl = '../../console/integrate_installed/admanage_introduction.jsp';
			var oTRSAction = new CTRSAction(sUrl);
			oTRSAction.setParameter('URL', 7);     
			oTRSAction.setParameter('Channel', '1');
			var sResult = oTRSAction.doDialogAction(600, 600);						
			if(sResult){
				sResult = sResult.replace("${admanage_root_path}", strsAdCon + "adintrs/");
				var oTrsEditor = $('_trs_editor_');
				var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
				if(oWindow.FCK.doAdInTrs)
					oWindow.FCK.doAdInTrs({src:sResult,IGNOREAPD:1});
			}	
		}catch(err){
			//TODO
			alert(String.format('插入广告位出错:{0}' ,err.message));
		}
	}
};


//ckm similar search.
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		if(!editorCfg.enableCkmSimSearch) return true;
		var args = [];
		args[0] = CurrDocId;
		args[1] = GetText();

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
			alert((wcm.LANG.DOCUMENT_PROCESS_216 || "您的IE插件已经将对话框拦截!\n")
					+ (wcm.LANG.DOCUMENT_PROCESS_217 || "请将拦截去掉-->点击退出-->关闭IE,然后重新打开IE登录即可!\n")
					+ (wcm.LANG.DOCUMENT_PROCESS_218 || "给您造成不便,TRS致以深深的歉意!"));		
		}	
		return true;
	}
});

//ckm auto extract.
function extractAbstractAndKeywords(){
	var sURL = "../../ckm/auto_extract_editor.jsp";
	var content = GetText();
	BasicDataHelper.JspRequest(sURL, {Content : content}, true, 
		function(transport,_json) {
			try{    
				$('DocAbstract').value= $v(_json,'root.abstractcontent');  
				if($('DOCKEYWORDS')){
					$('DOCKEYWORDS').value= $v(_json,'root.keywords');
				}
			}catch(error){
				//Just Skip it.
			}
		},
		function(){
			alert(wcm.LANG.DOCUMENT_PROCESS_219 || '抽取失败.');
		}
	);
}

//ckm check spell.
function checkWordSpell(){
	var args = {value:GetText()};
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
		alert((wcm.LANG.DOCUMENT_PROCESS_216 || "您的IE插件已经将对话框拦截!\n")
					+ (wcm.LANG.DOCUMENT_PROCESS_217 || "请将拦截去掉-->点击退出-->关闭IE,然后重新打开IE登录即可!\n")
					+ (wcm.LANG.DOCUMENT_PROCESS_218 || "给您造成不便,TRS致以深深的歉意!"));			
	}	
}

//keywords replace
if($('DOCKEYWORDS')){
	Event.observe($('DOCKEYWORDS'), 'blur', function(){
		keyWordsReplace('DOCKEYWORDS');
	});
}
function keyWordsReplace(domId){
	var oldValue = $(domId).value;
	var sValue = oldValue.trim().replace(/[ 　,,;]/g , ";"); 
	$(domId).value = sValue.replace(/;;/g , ";"); 
}

function $trans(sParam){
	return sParam.replace(/&lt;/ig,"<").replace(/&gt;/ig,">");
}

function formatShow(sParam){
	return sParam.length > 19 ? sParam.substring(0,16) + ".." : sParam; 
}

function showDialog4wcm52Style(_url, _nWidth, _nHeight, myArgs){
	var nWidth = _nWidth , nHeight = _nHeight; 
	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;
	if(Ext.isIE6){
		nWidth = nWidth + 20;
		nHeight = nHeight + 35;
	}

	var sFeatures	= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:No;help: No; resizable: No; status: No;";
	return showModalDialog(_url, myArgs, sFeatures);
}

function AddInnerDocLink(){
	//构造参数、获取返回值
	var myArgs = new Object();
	myArgs.myActualTop = window;
	var _arrDocInfos = showDialog4wcm52Style('link_document_select.html?DocChannelId='+ getParameter("ChannelId") + '&DocId=' + CurrDocId, 875, 450 ,myArgs);

	var n = (_arrDocInfos)?_arrDocInfos.length:0;
	if(n<=0) return;
	if(n > 1){
		if(!confirm(wcm.LANG.DOCUMENT_PROCESS_233 || "只有一个文档将被选中.确定吗？")){
			return;
		}
	}

	$("DOCLINK").value = _arrDocInfos[0]['PUBURL'];	
}

function AddInnerChannelLink(){
	//构造参数、获取返回值
	var myArgs = new Object();
	myArgs.myActualTop = window;
	var _arrChnlInfos = showDialog4wcm52Style('link_channel_select.html?DocChannelId='+ getParameter("ChannelId") + "&IsRadio=" + 1, 400, 500, myArgs);

	var n = (_arrChnlInfos)?_arrChnlInfos.length:0;
	if(n<=0) return;

	$("DOCLINK").value = _arrChnlInfos[0]['PUBURL'];
}
function setIframeHeight(){
	setTimeout(function(){
		var iframedv = document.getElementById("frmFlowDocContainer");
		var iframe = document.getElementById("frmFlowDoc");
		if(iframedv && iframe){
			var contentHeight = iframe.contentWindow.document.body.scrollHeight;
			iframedv.style.height = contentHeight + 10 + 'px'; 
		}
	},200);	
}

//简易编辑器型扩展字段
function openSimpleEditor3(fieldName){
	var cb = wcm.CrashBoard.get({
		id : 'SIMP_EDITOR',
		width : '800px',
		height : '300px',
		title : wcm.LANG.DOCUMENT_PROCESS_265 || '简易编辑器',
		appendParamsToUrl : false,
		url : WCMConstants.WCM6_PATH + 'editor/simp_editor2.html',
		params : {
			html : $(fieldName).value,
			Toolbar : 'Abstract'//'Abstract','Title','MetaData'
		},
		callback : function(params){
			var sTitle = params[0].replace(/^<(p|div|span)\s*[^>]*>((.|\n|\r)*)<\/\1>$/,function(_s0,_s1,_s2){
				if(_s2.indexOf('<\/'+_s1)!=-1)return _s0;
				return _s2;
			});
			$(fieldName).value = sTitle;
		},
		cancel : wcm.LANG.DOCUMENT_PROCESS_224 || '关闭'
	});
	cb.show();
}

// add by ffx @2010-11-8 浮动按钮的拖动
Ext.ns('wcm.util.documentDrag');
(function(){
	wcm.util.Draggable = function(){};

	Ext.apply(wcm.util.Draggable.prototype, {
		init : function(){
			Ext.EventManager.on(document, 'mousedown', this.handleMouseDown, this);
			//Ext.EventManager.on(document, 'mouseover', this.handleMouseOver, this);
		},
		findDragEl : function(e){
		},
		getXY : function(e){
			return [parseInt(e.getPageX(), 10), parseInt(e.getPageY(), 10)];
		},
		handleMouseOver : function(e){
		},
		handleMouseDown : function(e){
			//获取当前单击的元素是否为需要拖动的元素，如果不是，则直接退出
			this.dragEl = this.findDragEl(e);
			if(!this.dragEl) return;

			//重置拖动状态，以便重新执行onDragStart接口
			this.dragging = false;

			//记录拖动的开始位置及偏移量
			var xy = this.getXY(e);
			this.lastPointer = xy;
			var page = Position.cumulativeOffset(this.dragEl);
			this.deltaX = xy[0] - parseInt(page[0], 10);
			this.deltaY = xy[1] - parseInt(page[1], 10);
			//window.status = xy.join(":")+":"+page.join(":");

			//注册移动相应的事件
			Ext.EventManager.on(document, "mousemove", this.handleMouseMove, this);
			Ext.EventManager.on(document, "mouseup", this.handleMouseUp, this);						
			return false;
		},
		handleMouseMove : function(e){
			if(!this.dragEl)return;
			//判断鼠标是否有小范围的移动
			var xy = this.getXY(e);
			if(Math.abs(xy[0] - this.lastPointer[0]) < 2 && Math.abs(xy[1] - this.lastPointer[1]) < 2){
				return;
			}
			
			if(!this.dragging){
				this.dragging = true;
				this.onDragStart(xy[0], xy[1], e);
			}
			
			this.onDrag(xy[0], xy[1], e);
			return false;
		},
		handleMouseUp : function(e){
			Ext.EventManager.un(document, "mousemove", this.handleMouseMove, this);
			Ext.EventManager.un(document, "mouseup", this.handleMouseUp, this);
			if(this.dragging){
				var xy = this.getXY(e);
				this.onDragEnd(xy[0], xy[1], e);
			}
			this.dragEl = null;		
			this.dragging = false;
			return false;
		},
		onDragStart : function(x, y, e){
			//override in subclasss.
		},
		onDrag : function(x, y, e){
			//override in subclasss.
		},
		onDragEnd : function(x, y, e){
			//override in subclasss.
		},
		destroy : function(){
			Ext.EventManager.un(document, "mousedown", this.handleMouseDown, this);
		}
	});
})();


(function(){
	wcm.util.documentDrag.Dragger = new wcm.util.Draggable();
	var getWindowDimensions = function(){//函数：获取尺寸
		var winWidth=0;
		var winHeight=0;
		//获取窗口宽度和高度
		if(window.innerWidth){
			winWidth=window.innerWidth;
			winHeight=window.innerHeight;
		}else if((document.body)&&(document.body.clientWidth)){
			winWidth=document.body.clientWidth;
			winHeight=document.body.clientHeight;
		}

		/*nasty hack to deal with doctype swith in IE*/
		//通过深入Document内部对body进行检测，获取窗口大小
		if(document.documentElement 
			&& document.documentElement.clientHeight 
			&& document.documentElement.clientWidth){
			winHeight=document.documentElement.clientHeight;
			winWidth=document.documentElement.clientWidth;
		}

		//返回结果
		return [winWidth, winHeight];
	}
	getWindowDimensions();
	window.onresize=getWindowDimensions();

	Ext.apply(wcm.util.documentDrag.Dragger, {
		findDragEl : function(event){
			var dom = Event.element(event.browserEvent);
			var widget = Element.find(dom, null, 'btn_box');
			return widget;
		},
		onDragStart : function(x, y, e){
			document.getElementById("dragMask").className="dragMask";
			Event.stop(e.browserEvent);
			//var dragEl = this.findDragEl(e);
		},
		onDrag : function(x, y, e){
			var dragEl = this.dragEl;
			// 判断是否超出了边界
			var windowSize = getWindowDimensions();
			window.status = this.dragEl.offsetWidth + "," + this.dragEl.offsetHeight;
			var outOfXRange = x + this.dragEl.offsetWidth - this.deltaX > windowSize[0] || x < 0;
			var outOfYRange = y + this.dragEl.offsetHeight - this.deltaY > windowSize[1] || y < 0;
			if(outOfXRange){
				if(outOfYRange){
					return;
				}else{
					dragEl.style.top = (y - this.deltaY) + "px";
				}
			}else if(outOfYRange){
				if(outOfXRange){
					return;
				}else{
					dragEl.style.left = (x - this.deltaX) + "px";
				}
			}else{
				dragEl.style.left = (x - this.deltaX) + "px";
				dragEl.style.top = (y - this.deltaY) + "px";
			}
		},
		onDragEnd : function(x, y, e){
			this.dragEl = null;
			this.dragging = false;
			document.getElementById("dragMask").className="";
		}
	});
})();

Event.observe(window, 'load', function(){
	wcm.util.documentDrag.Dragger.init();
	if($("DocSourceName")){
		Event.observe($("DocSourceName"), 'blur', function(){
			//对docsource重新赋值
			if($("DocSourceName").value.trim() != ""){
				var oPostData = {
					DocSourceName : $("DocSourceName").value.trim()
				}
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.JspRequest(
					WCMConstants.WCM6_PATH + "include/docsource_reset.jsp",
					oPostData,  true,
					function(transport, json){
						if($("DocSource")){
							var v = transport.responseText.trim();
							if(!v) v = "0";		
							$("DocSource").value = v;
						}
					});
			}
		});
	}
});

/*
*支持页面关闭前的事件处理
*/
Ext.ns('wcm.Page');
wcm.Page = function(config){
	wcm.Page.superclass.constructor.apply(this, arguments);
	this.addEvents('beforeclose');
};
Ext.extend(wcm.Page, wcm.util.Observable);
PageWin = new wcm.Page();

function uploadCallback(fpInfo){
	setTimeout(function(){
		//FloatPanel.open(fpInfo.src,fpInfo.title);
		wcm.CrashBoard.get({
			title	:	fpInfo.title,
			url		:	fpInfo.src,
			width	:	'690px',
			height	:	'380px'
		}).show();
	}, 200);
	return false;
}

//获取系统设置的编辑器样式文件
function getEditorCss(){
	var FCK = $('_trs_editor_').contentWindow.GetEditor();
	var oCookies = FCK.loadCookie();
	var initStyleSelect = oCookies["initStyleSelect"] || 0;
	var request = new Ajax.Request('../editor/getEditorCss.jsp?initStyleSelect=' + initStyleSelect + "&nSiteId=" + FCK.nSiteId, {
		asynchronous:false, 
		method:'get',
		parameters:{}
	}); 
	var css = "";
	try{
		css = request.transport.responseText.trim();
		css = css.replace(/BODY/ig,".TRS_Editor");
	}catch(ex){}
	return css;    	
}
//设置引用样式
function onchangeQuoteTypeStyle(){
	var els = document.getElementsByClassName("_quoteTypeImg");
	var arrValue = [];
	for (var i=0; i<els.length; i++) {
		var quoteTypee = $('quote_'+(i+1));
		var value = quoteTypee.value;
		quoteTypee.setAttribute("_quoteType", value);
		//
		arrValue.push(value);
		//
		var el = els[i];
		Element.removeClassName(el,"quote");
		Element.removeClassName(el,"mirror");
		Element.addClassName(el,value);
	}
	var onlySelectedQuoteTypes = arrValue.join(",");
	$('quoto_table').setAttribute("_onlyselectedArrQuoteTypes", onlySelectedQuoteTypes);
}
function SetTitle(str){
	document.getElementById("DocTitle").value = str;
}
/*
 * 栏目及站点页面属性中必选字段的校验
*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		var arrNeededProps = m_sNeededProps.split(",");
		var el;
		var count = 0;
		var arrError = [];
		for (var i=0; i<arrNeededProps.length; i++) {
			el = $(arrNeededProps[i]);
			if(el && el.value.trim() == "") {//必须存在el这个页面元素，兼容当栏目继承自站点的扩展字段时el为undefined
				var sText = el.getAttribute("elname") || el.getAttribute("name");
				arrError.push(sText);
				count++;
			}
		}
		if(count > 0) {
			Ext.Msg.$alert("页面属性中有必填项没有填写！<br>请检查如下字段：<br>【"+arrError.join(",")+"】");
			return false;
		}

	}
});
//检查PUBLISHFILENAME字段的唯一性
function checkFiledVal() {
	var $channel = document.getElementById("_channelId");		
	var $documentId = document.getElementById("_documentId");
	var $fieldObj = document.getElementById("PUBLISHFILENAME");	
	var filedVal = $fieldObj.value;
	var result = "";		
	if(filedVal!=""){
		var _opostData ={channelId:$channel.value,filedVal:filedVal,documentId:$documentId.value};

		var ajaxRequest = new Ajax.Request(WCMConstants.WCM6_PATH + 'document/checkExtendFieldUnique.jsp',{
				method:'get', 
				asynchronous:false, 
				parameters:$toQueryStr(_opostData)
			});
		result = ajaxRequest.transport.responseText.trim();
		if(result!=""){
			$fieldObj.value = "";
			Ext.Msg.alert(result, function(){});
			return false;
		}
	}
	return true;
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	beforesave : function(event){
		return checkFiledVal();
	}
});
