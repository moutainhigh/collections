/** 
 * @fileoverview 
 * 此文件定义了CTRSAction对象，完成WEB应用常用的交互方式（例如：Link/OpenWin/Dialog/XMLHTTP...）<BR>
 *  Copyright: 		www.trs.com.cn<BR>
 *  Company: 		TRS Info. Ltd.<BR>
 *  Author:			CH<BR>
 *  Created:		2004-11-24 08:38<BR>
 *  Vesion:			1.0<BR>
 *  Last EditTime:	2004-11-24/2004-11-24<BR>
 *	Update Logs:<BR>
 *		CH@2004-11-24 Created File<BR>
 *	Note:<BR>
 *		<BR>
 *	Depends:<BR>
 *		CTRSReqeustParam.js<BR>
 *		CTRSHashtable.js<BR>
 *<BR>
 *	Examples:<BR>
 *		@see CTRSAction_test.html<BR>
 * @author CH cao.hui@trs.com.cn
 * @version 1.o
 */

 /**
 * TRSAction构造函数
 * @class CTRSAction类
 * @constructor
 * @param {int} _sActionURL 交互的地址
 * @return CTRSAction的一个实例
 */

var RE_REPLACE2PATHSEP = new RegExp('([^:])\/{2,}','ig');

function CTRSAction(_sActionURL){
//Define Properties
	try{
		/**
		 * @private
		 */
		this.oActionParam = new CTRSRequestParam();
	}catch(e){
		alert("Not include file CTRSRequestParam.js!");
		return;
	}	

	/**
	 * 设置给交互页面传递的参数
	 * @param {String} 参数名称
	 * @param {String} 参数值
	 */
	this.setParameter = function(_sParameterName, _sValue){
		return this.oActionParam.setParameter(_sParameterName, _sValue);
	}

	//If Self Action
	if(CTRSAction_isSelfLocation(_sActionURL)){
		/**
		 * @private
		 */	
		this.sActionURL = CTRSAction_getLocationURL();
		this.oActionParam.setAllParameters(TRSRequestParam);		
	}else{
		/**
		 * @private
		 */
		this.sActionURL = _sActionURL;
	}

//Define Methods
	this.getActionURL		= CTRSAction_getActionURL;
	this.validateActionURL	= CTRSAction_validateActionURL;
	this.doAction			= CTRSAction_doAction;
	this.doDialogAction		= CTRSAction_doDialogAction;
	this.doModelessDialogAction = CTRSAction_doModelessDialogAction;
	this.doOpenWinAction	= CTRSAction_doOpenWinAction;
	this.doOpenWinActionX	= CTRSAction_doOpenWinActionX;
	this.doXMLHttpAction	= CTRSAction_doXMLHttpAction;
	this.getRemoteData		= CTRSAction_doXMLHttpAction;
	this.inheritParameters	= CTRSAction_inheritParameters;
	this.doNoScrollDialogAction	= CTRSAction_doNoScrollDialogAction;
}


/**
 * 发出交互请求
 * @requirs CTRSAction CTRSAcation的实例
 */
function CTRSAction_doAction(){
	if(!this.validateActionURL()) return;

	try{
		if(RunningProcessBar){
			RunningProcessBar.start();
		}
	}catch(e){}

	window.location.href = this.getActionURL();
}	

/**
 * 结合设置的交互参数,获取交互地址
 */
function CTRSAction_getActionURL(){
	var sURL		= this.sActionURL;
	var nStartPose  = sURL.indexOf("?");
	if(nStartPose >= 0)
		sURL += "&";
	else
		sURL += "?";
	sURL = sURL.replace(RE_REPLACE2PATHSEP,'$1/');
	return sURL + this.oActionParam.toURLParameters();
}

function CTRSAction_validateActionURL(){
	return (this.oActionParam.toURLParameters() != null);
}

function CTRSAction_isSelfLocation(_sURL){
	if(_sURL == null)return true;

	var sSelfURL		= window.location.href;
	var nStartPose		= sSelfURL.indexOf("?");
	if(nStartPose >= 0)
		sSelfURL = sSelfURL.substring(0, nStartPose);
	
	nStartPose		= sSelfURL.lastIndexOf("/");
	if(nStartPose >= 0)
		sSelfURL = sSelfURL.substring(nStartPose+1);
	
	return (sSelfURL == _sURL.replace(RE_REPLACE2PATHSEP,'$1/'));
}


/**
 * 获取当前页面的链接地址
 */
function CTRSAction_getLocationURL(){
	var sURL		= window.location.href.replace(RE_REPLACE2PATHSEP,'$1/');
	var nStartPose  = sURL.indexOf("?");
	if(nStartPose >= 0)
		sURL = sURL.substring(0, nStartPose);
	sURL = sURL.replace(RE_REPLACE2PATHSEP,'$1/');

	return sURL;
}

/**
 * 发出带有滚动条对话框交互的请求
 * @param {int} _nWidth 对话框宽度
 * @param {int} _nHeight 对话框高度
 * @param {object} _oArgs 传入对话框的参数（可以为int string array htmlelement；其它自定义对象不行）
 * @throws 如果浏览器设置了拦截，会弹出解除拦截提示
 * @return {object} 返回值
 */
function CTRSAction_doDialogAction(_nWidth, _nHeight, _oArgs, _nTop, _nLeft){
	//1.verify parameters
	if(!this.validateActionURL()) return;

	var nWidth	= _nWidth	|| 200;
	var nHeight = _nHeight	|| 200;

	var nLeft	= _nLeft || (window.screen.availWidth - nWidth)/2;
	var nTop	= _nTop || (window.screen.availHeight - nHeight)/2;

	//2.Construct parameters for dialog
	var sFeatures		= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	//3.display Dialog
	var sDialogUrl = TRSAction_ROOT_PATH + "/include/dialog_window.html";
    sDialogUrl = sDialogUrl.replace(RE_REPLACE2PATHSEP,'$1/');
	var sURL = this.getActionURL();
	var arArgs = new Array();
	arArgs[0] = sURL;
	arArgs[1] = window.location.href.replace(RE_REPLACE2PATHSEP,'$1/');
	arArgs[2] = _oArgs;
	arArgs[3] = window;
	try{
		if(window.showModalDialog!=null){
			var sResult = window.showModalDialog(sDialogUrl, arArgs, sFeatures);
			return sResult;
		}else{
			window.open(sURL, null,"top="+nTop+",left="+nLeft+",fullscreen =no,menubar =no,toolbar =no,width="+nWidth+",height="+nHeight+",scrollbars=yes,location =no,titlebar=no,modal=yes",false);
		}
	}catch(e){
		alert("您的IE插件已经将对话框拦截！\n"
				+ "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n"
				+ "给您造成不便，TRS致以歉意！");
		return true;
	}
	
}

/**
 * 发出带有滚动条非模态对话框交互的请求
 * @param {int} _nWidth 对话框宽度
 * @param {int} _nHeight 对话框高度
 * @param {object} _oArgs 传入对话框的参数（可以为int string array htmlelement；其它自定义对象不行）
 * @throws 如果浏览器设置了拦截，会弹出解除拦截提示
 * @return {object} 返回值
 */
function CTRSAction_doModelessDialogAction(_nWidth, _nHeight, _oArgs, _nTop, _nLeft){
	//1.verify parameters
	if(!this.validateActionURL()) return;

	var nWidth	= _nWidth	|| 200;
	var nHeight = _nHeight	|| 200;

	var nLeft	= _nLeft || (window.screen.availWidth - nWidth)/2;
	var nTop	= _nTop || (window.screen.availHeight - nHeight)/2;

	//2.Construct parameters for dialog
	var sFeatures		= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	//3.display Dialog
	var sDialogUrl = TRSAction_ROOT_PATH + "/include/dialog_window.html";
	sDialogUrl = sDialogUrl.replace(RE_REPLACE2PATHSEP,'$1/');
	var sURL = this.getActionURL();
	var arArgs = new Array();
	arArgs[0] = sURL;
	arArgs[1] = window.location.href.replace(RE_REPLACE2PATHSEP,'$1/');
	arArgs[2] = _oArgs;
	try{
		if(window.showModelessDialog != null){
			var sResult = window.showModelessDialog(sDialogUrl, arArgs, sFeatures);	
			return sResult;
		}else{
			window.open(sURL, null,"top="+nTop+",left="+nLeft+",fullscreen =no,menubar =no,toolbar =no,width="+nWidth+",height="+nHeight+",scrollbars=no,location =no,titlebar=no,modal=yes",false);
		}
	}catch(e){
		alert("您的IE插件已经将对话框拦截！\n"
				+ "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n"
				+ "给您造成不便，TRS致以歉意！");
		return true;
	}
	
}

/**@private*/
var m_oXMLHttpTemp = null;

var INTERVAL_CHECK_XMLHTTP_STATE = 1000;

/**@private*/
function CTRSAction_checkXMLHttpStatus(){
	if(m_oXMLHttpTemp == null || m_oXMLHttpTemp.readyState == 4){
		if(RunningProcessBar){
			RunningProcessBar.close();
		}
		if(m_oXMLHttpTemp.status != 200){
			switch(m_oXMLHttpTemp.status){
				case 401:
					//CTRSAction_alert("您登录超时，请重新登录！");
					window.top.location.href = "../include/not_login.htm";
					return null;
				default:
					CTRSAction_displyInfo(600, 600, m_oXMLHttpTemp.responseText);
					return null;
			}
		}
		m_oXMLHttpTemp = null;
		return;
	}

	setTimeout("CTRSAction_checkXMLHttpStatus();", INTERVAL_CHECK_XMLHTTP_STATE);
}

/**
 * 发出XMLHttp请求
 * @param {string} _sPostData 传送的数据
 * @param {boolean} _bAsync 是否为非同步方式
 * @param {boolean} _bStartingProcessBar 非同步方式的时候是否显示进度条
 */
function CTRSAction_doXMLHttpAction(_sPostData, _bAsync, _bStartingProcessBar){
	//1.verify parameters
	if(!this.validateActionURL()) return;

	var bAsync = false;
	if(_bAsync)bAsync = true;
	
	var oXMLHttp = null;
	if(window.ActiveXObject){
		oXMLHttp = new ActiveXObject("Microsoft.XMLHTTP"); //建立XMLHTTP对象
	}else if(window.XMLHttpRequest){  //for ff , or other????
		oXMLHttp = new XMLHttpRequest();
	}
	
	if(_bStartingProcessBar){
		if(!RunningProcessBar){
			//CTRSAction_alert("Pleas include [../js/CRunningProcessBar.js]");
			return;
		}
		//RunningProcessBar.start();

		try{
			oXMLHttp.open("POST", this.getActionURL(), bAsync);		
			oXMLHttp.send(_sPostData);
		}catch(e){
			if(e == "[object Error]"){
				alert("系统已经关闭！请退出！");
				window.top.window.close();
				return null;
			}
		}
		m_oXMLHttpTemp = oXMLHttp;
		setTimeout("CTRSAction_checkXMLHttpStatus();", INTERVAL_CHECK_XMLHTTP_STATE);
		return;
	}
	oXMLHttp.open("POST", this.getActionURL(), bAsync);
	
	try{
		oXMLHttp.send(_sPostData);
		if(oXMLHttp.status != 200){
			switch(oXMLHttp.status){
				case 401:
					//alert("您登录超时，请重新登录！");
					//CTRSAction_displyInfo(600, 600, oXMLHttp.responseText);
					window.top.location.href = "../include/not_login.htm";
					return null;
				default:
					CTRSAction_displyInfo(600, 600, oXMLHttp.responseText);
					return null;
			}
		}
	}catch(e){
		if(e == "[object Error]"){
			alert("系统已经关闭！请退出！");
			window.top.window.close();
			return null;
		}
	}

	delete oXMLHttp;
	return oXMLHttp.responseText;	
}

/**
 * 发出弹出窗口的交互请求
 * @param {int} _nWidth 对话框宽度
 * @param {int} _nHeight 对话框高度
 */
function CTRSAction_doOpenWinActionX(_sName, _bReplaced, _nWidth, _nHeight){
	if(!this.validateActionURL()) return;

	var nWidth	= _nWidth || (window.screen.availWidth  - 20);
	var nHeight = _nHeight || (window.screen.availHeight - 40);
	var nLeft	=(window.screen.availWidth - nWidth-10)/2;
	var nTop	= (window.screen.availHeight - nHeight-20)/2;
	var sName	= _sName || "";

	var oWin = window.open(this.getActionURL(), sName, "top="+nTop+",left="+nLeft+",fullscreen =true,menubar =no,toolbar =no,width="+nWidth+",height="+nHeight+",scrollbars=yes,location =no,titlebar=no", _bReplaced);
	if(oWin == null){
		alert("您的IE插件已经将窗口拦截！\n"
				+ "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n"
				+ "给您造成不便，TRS致以歉意！");
		window.close();
	}else{
		oWin.opener =  window;
		oWin.focus();
	}
	//window.showModalDialog('../include/open_win.jsp', args,'dialogWidth:1px;dialogHeight:1px;dialogTop:1;dialogLeft:1;');
}

function CTRSAction_doOpenWinAction(_nWidth, _nHeight){
	this.doOpenWinActionX("", false, _nWidth, _nHeight);
}

/**
 * 发出不带滚动条对话框交互的请求
 * @param {int} _nWidth 对话框宽度
 * @param {int} _nHeight 对话框高度
 * @param {object} _oArgs 传入对话框的参数（可以为int string array htmlelement；其它自定义对象不行）
 * @throws 如果浏览器设置了拦截，会弹出解除拦截提示
 * @return {object} 返回值
 */
function CTRSAction_doNoScrollDialogAction(_nWidth, _nHeight, _oArgs){
	//1.verify parameters
	if(!this.validateActionURL()) return;

	var nWidth	= _nWidth	|| 200;
	var nHeight = _nHeight	|| 200;

	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;


	//2.Construct parameters for dialog
	var sFeatures		= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:No;help: No; resizable: No; status: No;";
	//3.display Dialog
	var sDialogUrl = TRSAction_ROOT_PATH + "/include/dialog_window.html";
	 sDialogUrl = sDialogUrl.replace(RE_REPLACE2PATHSEP,'$1/');
	var sURL = this.getActionURL();
	var arArgs = new Array();
	arArgs[0] = sURL;
	arArgs[1] = window.location.href.replace(RE_REPLACE2PATHSEP,'$1/');
	arArgs[2] = _oArgs;
	try{
		if(window.showModalDialog!=null){
			var sResult = window.showModalDialog(sDialogUrl, arArgs, sFeatures);
			return sResult;
		}else{
			window.open(sURL, null,"top="+nTop+",left="+nLeft+",fullscreen =no,menubar =no,toolbar =no,width="+nWidth+",height="+nHeight+",scrollbars=no,location =no,titlebar=no,modal=yes",false);
		}
	}catch(e){
		alert("您的IE插件已经将对话框拦截！\n"
				+ "请将拦截去掉-->点击退出-->关闭IE，然后重新打开IE登录即可！\n"
				+ "给您造成不便，TRS致以歉意！");
		return true;
	}
	
}


/**@private*/
function CTRSAction_inheritParameters(){
	this.oActionParam.setAllParameters(TRSRequestParam);
}

//Self Action 1----refreshMe
/**
 * 刷新当前的页面
 */
function CTRSAction_refreshMe(){
	var oTRSAction = new CTRSAction();
	oTRSAction.doAction();
}

//Self Action 2----gotoPage
/**
 * 在列表页面中定位到指定分页
 * @param {int} _nPageIndex 页面序号，从1开始
 */
function CTRSAction_gotoPage(pageIndexOrEl, event, _nPageCount){
	var _nPageIndex;
	if(!(typeof(pageIndexOrEl) == 'number')){
		event = event || window.event;
		switch(event.type){
			case 'blur':
				_nPageIndex = parseInt(pageIndexOrEl.value);
				if(isNaN(_nPageIndex)){pageIndexOrEl.value=''; return;}
				break;
			case 'keydown':
				if(event.keyCode==13){
					pageIndexOrEl.blur();
					return;
				}
//				Event.stop(event);
				return;
				break;
				
		}
		
	}
	else _nPageIndex = pageIndexOrEl;
	_nPageIndex = _nPageIndex > _nPageCount ? _nPageCount:_nPageIndex;
	var oTRSAction = new CTRSAction();
	oTRSAction.setParameter("PageIndex", _nPageIndex);
	oTRSAction.doAction();	
}


//Self Action 3----doSearch
/**
 * 按照Form中的数据对当前页面进行检索，一般应用在列表页面
 * @param {Form} _oForm 检索数据的Form对象
 */
function CTRSAction_doSearch(_oForm){
	var oForm = _oForm;
	if(_oForm == null)
		oForm = document.frmSearch;	

	var oTRSAction = new CTRSAction();

	var arEls = oForm.elements;//get All data from the form
	for(var i = 0;i<arEls.length;i++){			
		var sType = arEls[i].type;
		if(sType != null ){
			sType = sType.toUpperCase();
			if(sType == "BUTTON" || sType == "SUBMIT" || sType=="IMG" || sType=="RESET"){
				continue;
			}
		}

		var sValue = arEls[i].value;
		/*if(sValue.length && sValue.length > 200){
			CTRSAction_displyReports(300, 200,  "对不起，您输入的检索内容过长，超出最大长度[200]，请重新输入！");
			return;
		}
		*/

		oTRSAction.setParameter(arEls[i].name, sValue);
	}	
	//oTRSAction.setParameter("SearchKey", oForm.SearchKey.value);
	//oTRSAction.setParameter("SearchValue", oForm.SearchValue.value);
	oTRSAction.setParameter("PageIndex", 1);
	
	oTRSAction.doAction();
}

//Self Action 4----doOrderBy
/**
 * 按照指定的方式对页面进行排序，应用在列表页面中
 * @param {String} _sOrderField 排序字段
 * @param {String} _sOrderType 排序方式(DESC|ASC)
 */
function CTRSAction_doOrderBy(_sOrderField, _sOrderType){
	var oTRSAction = new CTRSAction();
	
	oTRSAction.setParameter("OrderField", _sOrderField);
	oTRSAction.setParameter("OrderType", _sOrderType);
	
	oTRSAction.setParameter("PageIndex", 1);
	
	oTRSAction.doAction();
}

/**
 * 根据指定的HTML代码显示信息提示页面
 * @param {int} _nWidth 对话框宽度
 * @param {int} _nHeight 对话框高度
 * @param {String} _sInforHTML 信息提示页面的内容
 */
function CTRSAction_displyInfo(_nWidth, _nHeight, _sInforHTML){
	var sURL = TRSAction_ROOT_PATH + "/include/info.html";
     sURL = sURL.replace(RE_REPLACE2PATHSEP,'$1/');
	var oTRSAction = new CTRSAction(sURL);	
	var oArgs = new Object();
	oArgs.InfoHTML = _sInforHTML;
	oTRSAction.doDialogAction(_nWidth, _nHeight, oArgs);
}

/**
 * 根据指定的HTML代码显示警告信息提示页面
 * @param {int} _nWidth 对话框宽度
 * @param {int} _nHeight 对话框高度
 * @param {String} _sInforHTML 信息提示页面的内容
 */
function CTRSAction_displyReports(_nWidth, _nHeight, _sInforHTML, _bInfo){
	var sURL = TRSAction_ROOT_PATH + "include/reports.html";
    sURL = sURL.replace(RE_REPLACE2PATHSEP,'$1/');
	var oTRSAction = new CTRSAction(sURL);	
	var oArgs = new Object();
	oArgs.InfoHTML = _sInforHTML;
	oArgs.IsInfo = _bInfo;
	oTRSAction.doNoScrollDialogAction(_nWidth, _nHeight, oArgs);
}

/**
 * 根据指定的HTML代码显示警告信息提示页面 confirm
 * @param {int} _nWidth 对话框宽度
 * @param {int} _nHeight 对话框高度
 * @param {String} _sInforHTML 信息提示页面的内容
 */
function CTRSAction_confirmReports(_nWidth, _nHeight, _sInforHTML, _bInfo){
	var sURL = TRSAction_ROOT_PATH + "include/reports_confirm.html";
	sURL = sURL.replace(/\/+/,'\/');
	var oTRSAction = new CTRSAction(sURL);	
	var oArgs = new Object();
	oArgs.InfoHTML = _sInforHTML;
	oArgs.IsInfo = _bInfo;
	var result = oTRSAction.doNoScrollDialogAction(_nWidth, _nHeight, oArgs);
	return result;
}

/**
 * 根据指定的HTML代码显示警告信息提示页面 confirm
 * @param {int} _nWidth 对话框宽度
 * @param {int} _nHeight 对话框高度
 * @param {String} _sInforHTML 信息提示页面的内容
 */
function CTRSAction_ifReports(_nWidth, _nHeight, _sInforHTML, _bInfo){
	var sURL = TRSAction_ROOT_PATH + "include/reports_if.html";
	sURL = sURL.replace(RE_REPLACE2PATHSEP,'$1/');
	var oTRSAction = new CTRSAction(sURL);	
	var oArgs = new Object();
	oArgs.InfoHTML = _sInforHTML;
	oArgs.IsInfo = _bInfo;
	var result = oTRSAction.doNoScrollDialogAction(_nWidth, _nHeight, oArgs);
	return result;
}

function CTRSAction_convertErrorInfo(_sErrorInfo){
	var MAX_LENGTH = 17;

	var nCarrigeCount	= 2;
	var nMaxLength		= MAX_LENGTH;
	var nNextPos		= _sErrorInfo.indexOf("\n");
	var nPrePos			= 0;
	while(nNextPos>=0){
		if((nNextPos-nPrePos)>nMaxLength){
			nMaxLength = (nNextPos-nPrePos);
		}
		nPrePos		= nNextPos;
		nNextPos	= _sErrorInfo.indexOf("\n", nNextPos+1);
		nCarrigeCount++;
	}	
	if((_sErrorInfo.length-nPrePos) > nMaxLength){
		nMaxLength = _sErrorInfo.length;
	}
	

	var nWidth = 300;
	if(nMaxLength>MAX_LENGTH){
		nWidth += (nMaxLength-MAX_LENGTH)*3;
	}
	var nHeight = 200;
	nHeight += 20*nCarrigeCount;

	var oInfo = new Object();
	oInfo.CarrigeCount	= nCarrigeCount;
	oInfo.MaxLength		= nMaxLength;
	oInfo.Width			= nWidth;
	oInfo.Height		= nHeight;
	oInfo.InfoHTML		= _sErrorInfo.replace(/\n/g, "<BR>");

	return oInfo;
}

function CTRSAction_alert(_sInforHTML, _bInfo){
	var oInfo = CTRSAction_convertErrorInfo(_sInforHTML);
	CTRSAction_displyReports(oInfo.Width, oInfo.Height, oInfo.InfoHTML, _bInfo);
}

function CTRSAction_confirm(_sInforHTML){
	var oInfo = CTRSAction_convertErrorInfo(_sInforHTML);
	var result = CTRSAction_confirmReports(oInfo.Width, oInfo.Height, oInfo.InfoHTML);
	return result;
}

function CTRSAction_if(_sInforHTML){
	var oInfo = CTRSAction_convertErrorInfo(_sInforHTML);
	var result = CTRSAction_ifReports(oInfo.Width, oInfo.Height, oInfo.InfoHTML);
	return result;
}


function CTRSAction_setRootPath(_sRootPath){
	this.TRSAction_ROOT_PATH = _sRootPath;
}


function CTRSAction_validateURL(_sURL,_sContent){
	var oXMLHttp = null;
	if(window.ActiveXObject){
		oXMLHttp = new ActiveXObject("Microsoft.XMLHTTP"); //建立XMLHTTP对象
	}else if(window.XMLHttpRequest){  //for ff , or other????
		oXMLHttp = new XMLHttpRequest();
	}	
	var sContent=_sContent||"";
	var bResult = false;
	try{
		oXMLHttp.open("get", _sURL.replace(RE_REPLACE2PATHSEP,'$1/'), false);
		oXMLHttp.send();
		bResult = (oXMLHttp.status != 404);
		if(sContent!=""){
			bResult = bResult&&(oXMLHttp.responseText == sContent );
		}
	}catch(e){		
		//alert(e);
	}
	
	delete oXMLHttp;
	return bResult;	
}


var TRSAction_ROOT_PATH = "../";