/**
*  floatPanel按钮和大小
*/
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'myClose',
		name : wcm.LANG.DOCUMENT_PROCESS_26 || '保存'
	}],
	size : [730, 365]
};
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
function doMouseOverRow(event, row){
	event = event || window.event;
	if($('picthumb_' + row.rowIndex)) return;
	var eDiv = document.createElement("DIV");
	eDiv.id = 'picthumb_' + row.rowIndex;
	document.body.appendChild(eDiv);
	var url = row.getAttribute("PicUrl",2);
	eDiv.innerHTML = '<img src="../../file/read_image.jsp?FileName=' + url + '&id=' + Math.random()  + '" border="0" onload="if(this.width>200)this.width=200;"/>';
	eDiv.style.position = 'absolute';
	eDiv.style.left = (event.x ? event.x : event.pageX) + 50 ; //event.x + 50;
	eDiv.style.zIndex = 1000;
	if(row.rowIndex<=5){
		eDiv.style.top = 100;//event.y - eDiv.offsetHeight/2 + 4;
	}else{
		eDiv.style.bottom = 100;//document.body.offsetHeight - event.y -4;
	}
}
function doMouseOutRow(event, row){
	var eDiv = $('picthumb_'+row.rowIndex);
	if(eDiv){
		$removeNode(eDiv);
	}
}
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
document.getElementsByClassName = function(cls, p) {
	if(p && p.getElementsByClassName) return p.getElementsByClassName(cls);
	var arr = ($(p) || document.body).getElementsByTagName('*');
	var rst = [];
	var regExp = new RegExp("(^|\\s)" + cls + "(\\s|$)");
	for(var i=0,n=arr.length;i<n;i++){
		if (arr[i].className.match(regExp))
			rst.push(arr[i]);
	}
	return rst;
};
Event.observe(window, 'load', function(){
	var tabpanel = new wcm.TabPanel({
		id : 'tabPanel',
		activeTab : 'AppPicUpload'
	});
	tabpanel.on('beforetabchange', function(currTab, toKey){
		PageContext.collectDatas();
		switch(toKey){
			case 'AppPicUpload':showPicsGrid();break;
			case 'AppFileUpload':showFilesGrid();break;
			case 'AppLink':showLinksGrid();break;
		}
	});
	tabpanel.show();
	Ext.get('tabPanel').on('click', function(event, target){
		if(target.getAttribute('grid_function', 2)){
			return doGridFunction(event, target);
		}
	});

	Ext.get('tabPanel').on('keydown', function(event, target){
		if(target.name != 'order') return;
		OrderHandler.init(target);
	});

	//初始化拖拽
	Dragger.init();

	/*初始化flash上传组件*/
	renderUploadFlash({
		jsessionid:jsessionid,
		channelId:getParameter("DocChannelId"),
		desc:"上传图片",
		flag : 20,
		handlerId:"uploadpic",
		type:"DOC_APPENDIX_IMAGE_SIZE_LIMIT",
		allowExt : '*.jpg;*.gif;*.png;*.bmp'
	});
	renderUploadFlash({
		jsessionid:jsessionid,
		channelId:getParameter("DocChannelId"),
		desc:"上传文件",
		handlerId:"uploadfile",
		flag : 10,
		type:"DOC_APPENDIX_FILE_SIZE_LIMIT",
		allowExt : '*.*'
	});
	//初始化与图片库相关的操作
	if(!bPhotoEnable){
		hidePicPluginsRelationEls();
	}
});
function hidePicPluginsRelationEls(){
	var relatePicPluginsEls = document.getElementsByClassName("relatepicplugins");
	for(var i=0;i<relatePicPluginsEls.length;i++){
		var el = relatePicPluginsEls[i];
		if(el) Element.hide(el);
	}
}
/*
*  全局的附件对象
*/
var m_Appendixes = Object.deepClone(FloatPanel.dialogArguments) || {
	Type_40 : {
		APPENDIXES : {
			APPENDIX : [
			]
		}
	},
	Type_10 : [],
	Type_20 : []
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
		var arr = getAppendixArray(appendixes);
		for(var i=0,k=0,n=tbody.childNodes.length; i<n; i++){
			var row = tbody.childNodes[i];
			if(row.nodeType != 1) continue;
			if(!row.getAttribute("appendix_type"))continue;
			var appendixInfo = arr[k++];
			if(appendixInfo==null)continue;
			var arrInputs = row.getElementsByTagName("input");
			for(var j=0, nj=arrInputs.length; j<nj; j++){
				var input = arrInputs[j];
				if(input.name == 'order') continue;
				appendixInfo[input.name.toUpperCase()] = input.value;
			}
			if(flag == 20){
				appendixInfo["APPLINKALT"] = appendixInfo["APPDESC"]
			}
		}
		m_Appendixes["Type_" + flag] = appendixes;
	},
	collectDatas : function(){
		this._collectData(10);
		this._collectData(20);
		this._collectData(40);
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
			oAppendix["APPLINKALT"] = _Appendix["AppLinkAlt"];
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
			if(appendixId != $v(arr[i],"APPENDIXID") || arr[i]["APPFILE"]["FILENAME"] == bound.FN){
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
	},
	"importToLib" : function(row){
		this.collectDatas();
		var url = row.getAttribute("PicUrl",2);
		toPhtotLib(url);
	}
});

/**
*  提取到图片库
*/
function toPhtotLib(_realSrc){
	//处理word中拷贝过来的图片
	var cb = wcm.CrashBoard.get({
		id : 'Dialog_ImageKind_Selector',
		width : '300px',
		height : '360px',
		title : wcm.LANG.DOCUMENT_PROCESS_266 || '请选择所属的分类',
		appendParamsToUrl : false,
		next: wcm.LANG.DOCUMENT_PROCESS_272 || '下一步',
		url : WCMConstants.WCM6_PATH + 'photo/photos_site_step1.html',
		params : {
			mode : "radio",
			Type : 1
		},
		callback : function(param){
			var surl = _realSrc;
			var channelid = param[0].join(",");
			if(!param[1]){
				var sSrc = "../../file/read_image.jsp?FileName="+_realSrc;
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
				surl = bound.FN;
			}
			var oPostData = {UPLOADEDFILES:surl,MAINKINDID:channelid,WATERMARKFILE:0}
			BasicDataHelper.call("wcm6_photo", 'saveimageinfo', oPostData, true, 
			function(transport,_json){
				if(param[1] && top.uploadCallback){
					var queryStr = "DocIds="+transport.responseText + "&ChannelId="+ channelid;
					var fpInfo = {
						src : WCMConstants.WCM6_PATH +
								'document/attachPhotoProps_edit.jsp?'+queryStr,
						title : wcm.LANG.PHOTO_CONFIRM_45 || "标注图片属性"
					}
					top.uploadCallback(fpInfo);
				}else{
					Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_237 || "提取成功！");
				}
			},function(){
				Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_238 || "提取失败！");
			});
		}
	});
	cb.show();
}

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
		case 20:
			showPicsGrid();
			break;
		case 40:
			showLinksGrid();
			break;
	}
}
function myClose(){
	if(!ValidationHelper.doValid('frmData')){
		return false;
	}
	PageContext.collectDatas();
	notifyFPCallback(m_Appendixes);
}
/** 附件类型标识:文档附件 */
var FLAG_DOCAPD = 10; //文档附件:Document Appendix
/** 附件类型标识:文档图片 */
var FLAG_DOCPIC = 20; //文档图片:Document Picture
/** 附件类型标识:链接附件 */
var FLAG_LINK = 40; //链接附件:Link
var FLAG_ALL = -1;

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
function AddInnerChannelLink(){
	//构造参数、获取返回值
	var myArgs = new Object();
	myArgs.myActualTop = window;
	var _arrChnlInfos = showDialog4wcm52Style('link_channel_select.html?DocChannelId='+ getParameter("DocChannelId"), 400, 500, myArgs);

	var n = (_arrChnlInfos)?_arrChnlInfos.length:0;
	if(n<=0) return;
	for(var i=0;i<n;i++){
		var oChnlInfo = _arrChnlInfos[i];
		try{
			PageContext.add(40, {
				'AppFile' : oChnlInfo["PUBURL"],
				'SrcFile' : oChnlInfo["PUBURL"],
				'AppDesc' : oChnlInfo["DESC"],
				'AppLinkAlt' : oChnlInfo["DESC"],
				'FileUrl' : oChnlInfo["PUBURL"]
			});
		}catch(err){
		}
	}
}

function UploadPic(){
	return Upload({
		flag : 20,
		frm : 'fm_pic',
		elementId : 'PicUpload',
		allowExt : 'jpg,gif,png,bmp',
		configType: 'DOC_APPENDIX_IMAGE_SIZE_LIMIT'
	});
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
function AddLink(){
	var sLinkUrl = $("Link").value;
	if(!sLinkUrl.match(/^(http|ftp|https|file):(\/){2}.+$/i)){
		Ext.Msg.alert(wcm.LANG.DOCUMENT_PROCESS_27 || '输入的URL地址不符合规范(http|ftp|https|file)://...',function(){
			$("Link").focus();
		});
		return;
	}
	// 检测添加了URL长度
	if(sLinkUrl.length>50){
		Ext.Msg.alert(String.format(wcm.LANG.DOCUMENT_PROCESS_226 || '链接地址长度不能超过50.'),function(){
			$("Link").focus();
		});
		return;
	}
	PageContext.add(40, {
		"AppFile":sLinkUrl,
		"SrcFile":sLinkUrl,
		"AppDesc":sLinkUrl,
		"AppLinkAlt":"",
		"FileUrl":sLinkUrl
	});
	$("Link").value = '';
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
function setWaterMark(){
	//1.获取选中图片
	var thumbs = $("appendix_tbody_20").childNodes;
	var items = [],param={};
	param.filenames = [];
	for (var i = 0; i < thumbs.length; i++){
		if(Element.hasClassName(thumbs[i],"selected")){
			items.push(thumbs[i]);
			param.filenames.push(thumbs[i].getAttribute("PicUrl"));
		}
	}
	if(items.length == 0){
		alert("您还没有选择需要处理的图片！");
		return;
	}
	//2.打开对话框
	var cb = wcm.CrashBoard.get({
		id : 'setwatermark',
		title : '设置水印',
		url : WCMConstants.WCM6_PATH +
					'document/document_photo_setwatermark.jsp',
		width: '540px',
		height: '350px',
		draggable : false,
		params : param,
		callback : function(params){
			 refresh_photo(items);
		}
	});
	cb.show();
	return false;
}
function refresh_photo(arr){
	for (var i = 0; i < arr.length; i++){
		var imgs = document.getElementsByClassName("appendix_img",arr[i]);
		if(!imgs[0])continue;
		imgs[0].src = imgs[0].src+"&random="+Math.random();
	}
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
function showPicsGrid(){
	Element.update($('curr_pics'), '');
	var sValue = TempEvaler.evaluateTemplater('template_pics', m_Appendixes["Type_20"],{});
	Element.update($('curr_pics'), sValue);
	// 显示图片的时候给图片绑定onload事件，设置图片的宽度
	imgOnloadBind();
	if(!bPhotoEnable){
		hidePicPluginsRelationEls();
	}
	
}
function showFilesGrid(){
	Element.update($('curr_files'), '');
	var sValue = TempEvaler.evaluateTemplater('template_files', m_Appendixes["Type_10"],{});
	Element.update($('curr_files'), sValue);
}
function showLinksGrid(){
	Element.update($('curr_links'), '');
	var sValue = TempEvaler.evaluateTemplater('template_links', m_Appendixes["Type_40"],{});
	Element.update($('curr_links'), sValue);
}
function toggleClass(el,className){
	Element[Element.hasClassName(el,className)?"removeClassName":"addClassName"](el,className);
}
function clickEl(el){
	toggleClass(el,"selected");
}
/**
*  图片附件的一些js动作
*/
Event.observe(document,"click",function(e){
	e = window.event || e;
	var dom = Event.element(e);
	// 如果是操作
	if(Element.hasClassName(dom,"grid_function"))return;
	// 如果是输入框
	if(dom.tagName=="INPUT")return;
	var ddEl = Element.find(dom,"","picthumb");
	if(!ddEl)return;
	clickEl(ddEl);
});
var getTargets = function(dom){
	var doms = [];
	var thumbs = document.getElementsByClassName('picthumb', dom);
	for(var index = 0; index < thumbs.length; index++){
		var thumb = thumbs[index];
		doms.push(thumb);
	}
	return doms;
}
var inElement = function(x, y, element){
	var page = Position.cumulativeOffset(element);
	var scrollEl = $("curr_pics");
	page = [page[0]-scrollEl.scrollLeft,page[1]-scrollEl.scrollTop,]
	var width = parseInt(element.offsetWidth, 10);
	var height = parseInt(element.offsetHeight, 10);
	return x> page[0] && x<page[0] + width && y > page[1] && y < page[1] + height;
}
var getOrder = function(el){
	if(!el)return 0;
	var els = el.getElementsByTagName("INPUT");
	for (var i = 0; i < els.length; i++){
		if(els[i].name != "order")continue;
		return els[i].value;
	}
	return 0;
}
var imgOnloadBind = function(){
	// 对元素绑定事件
	var imgEls = document.getElementsByClassName("appendix_img",$("appendix_tbody_20"));
	for (var i = 0; i < imgEls.length; i++){
		Event.observe(imgEls[i],"load",function(e){
			// 图片的显示信息
			if(this.width/this.height>2 && this.width>200){
				this.width = 200;
			}else if(this.height>100){
				this.height = 100;
			}
			this.style.marginTop = (100-this.height)/2+"px";
			return;
			/**由于有缩略图的存在，会导致显示的信息不正确，故不显示*/
			// 图片的尺寸信息
			var sInfo = String.format("尺寸：[{0}px,{1}px]",this.width,this.height);
			var picThumb = Element.find(this,null,"picthumb");
			if(!picThumb) return;
			var picInfo = document.getElementsByClassName("picinfo",picThumb)[0];
			if(!picInfo) return;
			picInfo.innerHTML = sInfo;
		}.bind(imgEls[i]));
	}
}
var Dragger = window.Dragger || {};
Ext.apply(Dragger, {
	init : function(){
		Ext.EventManager.on('curr_pics', 'mousedown', this.handleMouseDown, this);
	},
	findDragEl : function(e){
		e = window.event || e;
		var dom = Event.element(e);
		if(dom.tagName=="INPUT") return null;
		return Element.find(dom,"","picthumb");
	},
	getXY : function(e){
		return [parseInt(e.getPageX(), 10), parseInt(e.getPageY(), 10)];
	},
	handleMouseDown : function(e){
		Event.stop(e.browserEvent);
		//获取当前单击的元素是否为需要拖动的元素，如果不是，则直接退出
		this.dragEl = this.findDragEl(e);
		if(!this.dragEl) return;

		//重置拖动状态，以便重新执行onDragStart接口
		this.dragging = false;

		//记录拖动的开始位置及偏移量
		var xy = this.getXY(e);
		this.lastPointer = xy;
		var page = Position.cumulativeOffset(this.dragEl);
		// 需要去除滚动条高度
		var scrollEl = $("curr_pics");
		this.deltaX = xy[0] - parseInt(page[0]-scrollEl.scrollLeft, 10);
		this.deltaY = xy[1] - parseInt(page[1]-scrollEl.scrollTop, 10);
		//window.status = xy.join(":")+":"+page.join(":");

		//注册移动相应的事件
		Ext.EventManager.on(document, "mousemove", this.handleMouseMove, this);
		Ext.EventManager.on(document, "mouseup", this.handleMouseUp, this);						
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
		// 如果鼠标离页面的顶部和底部较近，需要移动滚动条的位置
		//rollPage(xy[0], xy[1],this.dragEl);
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
	},
	onDragStart : function(x, y, e){
		Event.stop(e.browserEvent);
		var dragEl = this.dragEl;
		var destPosition = $('wcm-drag-destPosition');
		if(!destPosition){
			new Insertion.Before(dragEl, "<dd class='picthumb' id='wcm-drag-destPosition'><dd>");
			destPosition = $('wcm-drag-destPosition');
		}
		if(!destPosition) return;
		document.body.appendChild(dragEl);
		//Element.addClassName(dragEl, dragElCls);
		dragEl.style.position="absolute";
		dragEl.style.zIndex = 10000;
		this.targets = getTargets($("appendix_tbody_20"));
	},
	onDrag : function(x, y, e){
		var dragEl = this.dragEl;
		dragEl.style.left = (x - this.deltaX) + "px";
		dragEl.style.top = (y - this.deltaY) + "px";
		//window.status = $("curr_pics").scrollTop;//(x - this.deltaX)+":"+(y - this.deltaY-100);
		var targets = this.targets;
		// 如果没有目标，直接返回
		if(!targets)return;
		for(var index = 0; index < targets.length; index++){
			var target = targets[index];
			if(inElement(x, y, target)){
				var destPosition = $('wcm-drag-destPosition');
				/* 主要区分空白元素与当前元素之间的位置来判断是否放在前面还是后面 */
				if(destPosition != target.previousSibling)
					target.parentNode.insertBefore(destPosition,target);
				else
					target.parentNode.insertBefore(destPosition,Element.next(target));
				break;
			}
		}
	},
	onDragEnd : function(x, y, e){
		var destPosition = $('wcm-drag-destPosition');
		if(!destPosition) return;
		destPosition.parentNode.insertBefore(this.dragEl, destPosition);
		this.dragEl.style.position = "static";
		$removeNode(destPosition);
		this.widgets = null;
		//防止在复制时，由于targets不为空，而导致拖动元素时不产生占位元素
		this.targets = null;
		var appendixes = m_Appendixes["Type_20"];
		var arr = getAppendixArray(appendixes);
		appendixes["APPENDIXES"]["APPENDIX"] = arr;
		var newOrder = parseInt(getOrder(Element.previous(this.dragEl)))+1;
		var oldOrder = getOrder(this.dragEl);
		if(oldOrder<newOrder)
			newOrder--;
		else if(oldOrder == newOrder)
			return;
		OrderHandler.insert(arr, oldOrder - 1, newOrder - 1);
		//当修改名称时再拖动，需要先收集原来的信息
		PageContext._collectData(20);
		refreshGrid(20);
		this.dragEl = null;
	},
	destroy : function(){
		Ext.EventManager.un(document, "mousedown", this.handleMouseDown, this);
	}
});
