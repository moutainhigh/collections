function getParameter(_sName, _sQuery){
	if(!_sName)return '';
	var query = _sQuery || location.search;
	if(!query)return '';
	var arr = query.substring(1).split('&');
	_sName = _sName.toUpperCase();
	for (var i=0,n=arr.length; i<n; i++){
		if(arr[i].toUpperCase().indexOf(_sName+'=')==0){
			return arr[i].substring(_sName.length + 1);
		}
	}
	return '';
}
/**
*  覆盖原来的TRY方法，防止AJAX调用时出现错误
*/
var Try = Ext.Try = {
  these : function() {
    var returnValue;

    for (var i = 0; i < arguments.length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) {}
    }

    return returnValue;
  }
}
function getRow(target){
	while(target!=null && target.tagName!='BODY'){
		if(target.getAttribute('grid_rowid', 2))return target;
		target = target.parentNode;
	}
	return null;
}
function isShowThumbEle(target){
	while(target!=null && target.tagName!='BODY'){
		if(target.getAttribute('_showthumb', 2))return true;
		target = target.parentNode;
	}
	return false;
}
/**
*  执行函数
*/
function doGridFunction(event, target){
	var row = getRow(target);
	if(row==null)return;
	var gridFunc = target.getAttribute('grid_function', 2);
	if(!PageContext[gridFunc])return;
	PageContext[gridFunc](row);
}
/**
*  
*/
function $removeNode(_node){
	if(_node){
		var childs = _node.childNodes;
		for(var i=childs.length-1;i>=0;i--){
			$removeNode(childs[i]);
		}
		childs = [];
		if(_node.parentNode){
			_node.parentNode.removeChild(_node);
		}
//		Event.stopAllObserving(_node);
		delete _node;
	}
}
Event.observe(window, 'load', function(){
	Ext.get('AppFileUpload').on('click', function(event, target){
		if(target.getAttribute('grid_function', 2)){
			return doGridFunction(event, target);
		}
	});

	Ext.get('AppFileUpload').on('keydown', function(event, target){
		if(target.name != 'order') return;
		OrderHandler.init(target);
	});

	/*初始化flash上传组件*/
	renderUploadFlash({
		jsessionid:jsessionid,
		channelId:getParameter("DocChannelId"),
		desc:"上传文件",
		handlerId:"uploadfile",
		flag : 10,
		type:"DOC_APPENDIX_FILE_SIZE_LIMIT",
		allowExt : '*.*'
	});

	var currDocumentId = getParameter("documentId");
	if(currDocumentId > 0){
		initFilesAppendixInfo(currDocumentId);
	}else{
		showFilesGrid();
	}
	
});

function initFilesAppendixInfo(currDocumentId){
	/**
	 * 获取到当前页面所嵌入页面的flowDocId参数
	 */
	var nFlowDocId = getParameter('FlowDocId', window.parent.location.search);
	var postData = {
		documentId: currDocumentId,
		AppendixType:10
	};
	if(nFlowDocId>0) {
		postData.FlowDocId = nFlowDocId;
	}
	BasicDataHelper.call('wcm61_viewdocument','queryAppendixes',postData,true,function(_transport,_json){
		var result = {
			Type_10 : Ext.Json.parseXml(_transport.responseXML)
		};
		m_Appendixes = result;
		showFilesGrid();
	});
}

/*
*  全局的附件对象
*/
var m_Appendixes = {
	Type_10 : {}
};
var PageContext = {};
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
Ext.apply(PageContext, {
	_collectData : function(flag){
		var tbody = $('appendix_tbody_' + flag);
		if(tbody==null)return;
		var appendixes = m_Appendixes["Type_" + flag];
		appendixes = appendixes || {};
		m_Appendixes["Type_" + flag] = appendixes;
	},
	collectDatas : function(){
		this._collectData(10);
	},
	"add" : function(_iAppendixType, _Appendix){
		this.collectDatas();
		var appendixes = m_Appendixes["Type_"+_iAppendixType];
		appendixes = appendixes || {};
		var arr = getAppendixArray(appendixes);
		var oAppendix = {};
		if(_iAppendixType=='20'){
			oAppendix["APPFILE"] = {};
			oAppendix["APPFILE"]["FILENAME"] = _Appendix["AppFile"];
			oAppendix["APPFILE"]["URL"] = _Appendix["FileUrl"];
			if(_Appendix["RELATEPHOTOIDS"])
				oAppendix["RELATEPHOTOIDS"] = _Appendix["RELATEPHOTOIDS"];
			oAppendix["APPLINKALT"] = _Appendix["AppDesc"];
		}
		else{
			oAppendix["APPFILE"] = _Appendix["AppFile"];
		}
		oAppendix["SRCFILE"] = _Appendix["SrcFile"];
		oAppendix["APPDESC"] = _Appendix["AppDesc"];
		oAppendix["APPFLAG"] = _iAppendixType;
		oAppendix["APPENDIXID"] = "0_" + $MsgCenter.genId();
		arr.push(oAppendix);
		refreshGrid(_iAppendixType);
	},
	"delete" : function(row){
		this.collectDatas();
		var appendixId = row.getAttribute("grid_rowid", 2);
		var iAppendixType = row.getAttribute("appendix_type", 2);
//		row.parentNode.deleteRow(row.rowIndex - 1);
		var appendixes = m_Appendixes["Type_"+iAppendixType];
		var arr = getAppendixArray(appendixes);
		var result = [];
		for(var i=0;arr&&i<arr.length;i++){
			if(appendixId != $v(arr[i],"APPENDIXID")){
				result.push(arr[i]);
			}
		}
		appendixes["APPENDIXES"]["APPENDIX"] = result;
		refreshGrid(iAppendixType);
	},
	"up" : function(row){
		if(row.rowIndex<=1)return;
		this.collectDatas();
		var iAppendixType = row.getAttribute("appendix_type", 2);
		var appendixes = m_Appendixes["Type_"+iAppendixType];
		var arr = getAppendixArray(appendixes);
		var currData = arr[row.rowIndex-1];
		arr[row.rowIndex-1] = arr[row.rowIndex-2];
		arr[row.rowIndex-2] = currData;
		appendixes["APPENDIXES"]["APPENDIX"] = arr;
		refreshGrid(iAppendixType);
	},
	"down" : function(row){
		if(row.rowIndex>=row.parentNode.rows.length)return;
		this.collectDatas();
		var iAppendixType = row.getAttribute("appendix_type", 2);
		var appendixes = m_Appendixes["Type_"+iAppendixType];
		var arr = getAppendixArray(appendixes);
		var currData = arr[row.rowIndex-1];
		arr[row.rowIndex-1] = arr[row.rowIndex];
		arr[row.rowIndex] = currData;
		appendixes["APPENDIXES"]["APPENDIX"] = arr;
		refreshGrid(iAppendixType);
	},
	"edit" : function(row){
		this.collectDatas();
		var appendixId = row.getAttribute("grid_rowid", 2);
		var url = row.getAttribute("PicUrl",2);
		var iAppendixType = row.getAttribute("appendix_type", 2);
		if(url == null)return;
		var url = "../../file/read_image.jsp?FileName="+url;
		var parameters = "photo="+encodeURIComponent(url);
		var sUrl = WCMConstants.WCM6_PATH + "photo/photo_compress.jsp?"+parameters;
		var dialogArguments = window;
		var nWidth	= window.screen.width - 12;
		var nHeight = window.screen.height - 60;
		var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
		var bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
		if(bound == null)return;
		//更新图片并刷新
		var appendixes = m_Appendixes["Type_"+iAppendixType];
		var arr = getAppendixArray(appendixes);
		var result = [];
		for(var i=0;arr&&i<arr.length;i++){
			if(appendixId != $v(arr[i],"APPENDIXID")){
				result.push(arr[i]);
			}else{
				var oAppendix = {};
				oAppendix["APPFILE"] = {};
				oAppendix["APPFILE"]["FILENAME"] = bound.FN;
				oAppendix["APPFILE"]["URL"] = bound.FN;
				oAppendix["SRCFILE"] = arr[i]["SRCFILE"];
				oAppendix["APPDESC"] = arr[i]["APPDESC"];
				oAppendix["APPFLAG"] = iAppendixType;
				oAppendix["APPENDIXID"] = "0_" + $MsgCenter.genId();
				result.push(oAppendix);
				//bound.FN
			}
		}
		appendixes["APPENDIXES"]["APPENDIX"] = result;
		refreshGrid(iAppendixType);
	}
});

//change order start
var OrderHandler = {
	init : function(dom){
		if(dom.getAttribute('inited')) return;
		dom.setAttribute('inited', true);
		dom.onblur = OrderHandler.blur;
		dom.onkeydown = OrderHandler.keydown;
	},
	destroy : function(dom){
		dom.removeAttribute('inited');
		dom.onblur = null;
		dom.onkeydown = null;
	},
	valid : function(dom){
		if(!/^\d+$/.test(dom.value)){
			alert(wcm.LANG.DOCUMENT_PROCESS_215 || '请输入合法的数字');
			dom.select();
			return false;
		}		
		return true;
	},
	blur : function(event){
		var dom = this;
		if(!OrderHandler.valid(dom)) return;
		OrderHandler.destroy(dom);
		OrderHandler.change(dom);
	},
	keydown : function(event){
		event = window.event || event;
		if(event.keyCode != 13) return;
		this.blur();
	},
	change : function(dom){
		PageContext.collectDatas();
		var iAppendixType = getRow(dom).getAttribute("appendix_type", 2);
		var appendixes = m_Appendixes["Type_"+iAppendixType];
		var arr = getAppendixArray(appendixes);
		appendixes["APPENDIXES"]["APPENDIX"] = arr;
		var newOrder = dom.value;
		if(newOrder <= 0) newOrder = 1;
		if(newOrder > arr.length) newOrder = arr.length;
		var oldOrder = dom.getAttribute("_value");
		if(oldOrder == newOrder) return;
		OrderHandler.insert(arr, oldOrder - 1, newOrder - 1);
		refreshGrid(iAppendixType);
	},
	insert : function(arr, from, to){
		arr.splice(to, 0, arr.splice(from, 1)[0]);
	}
};
//change order end

function refreshGrid(_iAppendixType){
	switch(parseInt(_iAppendixType, 10)){
		case 10:
			showFilesGrid();
			break;
	}
}
/** 附件类型标识:文档附件 */
var FLAG_DOCAPD = 10; //文档附件:Document Appendix

/**
*  上传成功
*/
function uploadOk(sResponseText, info){
	return function(){
		var fileNames = null;
		eval("fileNames="+sResponseText);
		var sUploadFileName = fileNames[0];
		var sShowName = fileNames[1];
		PageContext.add(info.flag, {
			"AppFile" : sUploadFileName,
			"SrcFile" : sShowName,
			"AppDesc" : sShowName,
			"AppLinkAlt" : "",
			"FileUrl" : info.fileName
		});
		$(info.frm).reset();
	}
}
/*
*  执行上传操作
*/
function Upload(info){
	var elementId = info.elementId;
	var sFileName = info.fileName = $(elementId).value;
	if(sFileName.trim().length > 100){
		Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_214 || '输入串长度超长');
		return ;
	}
	try{
		FileUploadHelper.validFileExt(sFileName, info.allowExt);
	}catch(err){
		Ext.Msg.$alert(err.message, function(){
			$(elementId).focus();
		});
		return;
	}
	YUIConnect.setForm(info.frm, true, Ext.isSecure);
	YUIConnect.asyncRequest('POST', 
		'../system/file_upload_dowith.jsp?ResponseType=2&FileParamName=' + elementId+'&Type='+info.configType + '&channelId=' + getParameter("DocChannelId"), {
		"upload" : function(_transport){
			var sResponseText = _transport.responseText;
			FileUploadHelper.fileUploadedAlert(sResponseText,{
				succ : uploadOk(sResponseText, info)
			});
		}
	});
	return false;
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
	var _arrDocInfos = showDialog4wcm52Style('link_document_select.html?DocChannelId='+ getParameter("DocChannelId"), 875, 450, myArgs);

	var n = (_arrDocInfos)?_arrDocInfos.length:0;
	if(n<=0) return;
	for(var i=0;i<n;i++){
		var oDocInfo = _arrDocInfos[i];
		try{
			PageContext.add(40, {
				'AppFile' : oDocInfo["PUBURL"],
				'SrcFile' : oDocInfo["PUBURL"],
				'AppDesc' : oDocInfo["TITLE"],
				'AppLinkAlt' : oDocInfo["TITLE"],
				'FileUrl' : oDocInfo["PUBURL"]
			});
		}catch(err){
		}
	}
}

function UploadFile(){
	return Upload({
		flag : 10,
		frm : 'fm_file',
		elementId : 'FileUpload',
		allowExt : '.*',
		configType: 'DOC_APPENDIX_FILE_SIZE_LIMIT'
	});
}
function LoadFromPicLib(){
	var result = showDialog4wcm52Style('attachment_photolib.jsp', 950, 660,window);
	if(result!=null && Array.isArray(result)){
		for(var i = 0; i < result.length; i++ ){
			if(!result[i])continue;
			var sUploadFileName = result[i]['src'];
			var sShowName = result[i]['desc'];
			PageContext.add(20,{
				"AppFile":sUploadFileName,
				"SrcFile":sShowName,
				"AppDesc":sShowName,
				"AppLinkAlt":"",
				"FileUrl":result[i]['url'],
				"RELATEPHOTOIDS":result[i]['photoDocid']
			});
		}
	}
}
/**
* 图片为type:photo
* 视频为type:video
* 音频为type:audio
*/
function uploadFiles(type,desc){
	var cb = wcm.CrashBoard.get({
		id : 'upload_'+type,
		title : String.format('批量上传{0}',desc),
		url : WCMConstants.WCM6_PATH +
					'document/document_'+type+'_attachments_import.jsp?ChannelId=' + getParameter('DocChannelId'),
		width: '540px',
		height: '350px',
		draggable : false,
		callback : function(params){
			 uploaded(params);
		}
	})
	cb.show();
	return false;
}
function selectAll(){
	var thumbs = $("appendix_tbody_20").childNodes,hasUnselected = false;;
	for (var i = 0; i < thumbs.length; i++){
		if(!Element.hasClassName(thumbs[i],"selected")){
			hasUnselected = true;
			break;
		}
		//Element.addClassName(thumbs[i],"selected");
	}
	for (var i = 0; i < thumbs.length; i++){
		Element[hasUnselected?"addClassName":"removeClassName"](thumbs[i],"selected");
	}
}
function uploaded(params){
	if(params!= null){
		for(var i = 0; i < params.length; i++ ){
			if(!params[i])continue;
			var sUploadFileName = params[i]['src'];
			var sShowName = params[i]['desc'];
			PageContext.add(params[i]['type'],{
				"AppFile":sUploadFileName,
				"SrcFile":sShowName,
				"AppDesc":sShowName,
				"AppLinkAlt":"",
				"FileUrl":params[i]['url']
			});
		}
	}
}
function SaveValue(_Element){
	var appendixId = _Element.getAttribute("grid_rowid",2);
	if(appendixId==null){
		alert(wcm.LANG.DOCUMENT_PROCESS_28 || 'SimpleEditPanel控件上未指定属性"grid_rowid"');
		return;
	}
	var sFieldName = _Element.getAttribute("_fieldName",2);
	if(sFieldName==null){
		alert(wcm.LANG.DOCUMENT_PROCESS_29 || 'SimpleEditPanel控件上未指定属性"_fieldName"');
		return;
	}
	var iAppendixType = _Element.getAttribute("appendix_type",2);
	if(iAppendixType==null){
		alert(wcm.LANG.DOCUMENT_PROCESS_30 || 'SimpleEditPanel控件上未指定属性"appendix_type"');
		return;
	}
	sFieldName = sFieldName.toUpperCase().trim();
	var appendixes = myActualTop.m_Appendixes["Type_"+iAppendixType];
	var arr = getAppendixArray(appendixes);
	for(var i=0;i<arr.length;i++){
		if(appendixId==com.trs.util.JSON.value(arr[i],"APPENDIXID")){
			arr[i][sFieldName] = _Element.getAttribute("title",2);
		}
	}
}
function showFilesGrid(){
	Element.update($('curr_files'), '');
	var sValue = TempEvaler.evaluateTemplater('template_files', m_Appendixes["Type_10"],{});
	Element.update($('curr_files'), sValue);
}
function toggleClass(el,className){
	Element[Element.hasClassName(el,className)?"removeClassName":"addClassName"](el,className);
}
function clickEl(el){
	toggleClass(el,"selected");
}