$import('com.trs.dialog.Dialog');

com.trs.ajaxframe.DataHelper.Service.setUrl('/wcm/center.do');

/**
 *------------------------------------------------------------------
 * 系统定义的几种导航
 *------------------------------------------------------------------
 */
com.trs.ajaxframe.NAVIGATE_Custom1=com.trs.ajaxframe.Navigate('','normal');
com.trs.ajaxframe.NAVIGATE_Custom1.more=function(){
	return '<a href="#">more</a>';
}
com.trs.ajaxframe.NAVIGATE_Custom1.space=function(){
	return '---';
}

/**
 *------------------------------------------------------------------
 * 系统实现的com.trs.ajaxframe.Action中需要实现的interface
 *------------------------------------------------------------------
 */
com.trs.ajaxframe.Action._buildXML=function(_sServiceName,_sMethodName,_sXmlParams){
	var xml='<post-data>'+
				'\n\t<method type="'+_sMethodName+'">'+
				 _sServiceName+
				'</method>'+
				'\n\t<parameters>\n'+
					_sXmlParams+
				'\n\t</parameters>'+
			'\n</post-data>';
	return xml;
};
com.trs.ajaxframe.URLAction._buildUrl=function(_sServiceName,_sMethodName,_sParams){
	if(_sMethodName=='get'){
		_sMethodName='query';
	}
	else if(_sMethodName=='findbyids'){
		if(!_sParams.match(/objectids=/ig)){
			_sParams='objectids='+_sParams;
		}
	}
	
	var sUrl='serviceid='+_sServiceName+'&methodname='+_sMethodName;
	if(_sParams) sUrl+='&'+_sParams;
	return sUrl;
};

/**
 *------------------------------------------------------------------
 * 删除的时候使用固定参数objectids作为主键
 *------------------------------------------------------------------
 */
com.trs.ajaxframe.DataSource.prototype._paramNameOfDeleteIds=function(){
	return "objectids";
}


/**
 *------------------------------------------------------------------
 * 为更快捷的注册数据源提供一些自定义的方法.
 *------------------------------------------------------------------
 */
function $infoHelper(_oInfo){
	var sXml = com.trs.util.Common.BASE+'../xml/'+_oInfo["xml"]+'.xml';
	var sPrimaryKey = _oInfo['key'];
	var sRootTagName = _oInfo['rootTagName'] || _oInfo['tagName'];
	var sRecordsNum = sRootTagName+'.Num';
	var sPageSize = sRootTagName+'.PageSize';
	var sCurrPage = 'CurrPage';
	return Object.extend(_oInfo,{
		'xml':sXml,
		'primaryKey':sPrimaryKey,
		'recordsNumKey':sRecordsNum,
		'pageSizeKey':sPageSize,
		'pageKey':sCurrPage
	});
}
function $setup(_oInfo){
	var oService = Object.extend({},com.trs.ajaxframe.DataHelper.Service);
	oService.xml = _oInfo['xml'];
	oService.setServiceName(_oInfo['serviceName']);
	var oDataSource = new com.trs.ajaxframe.DataSource(_oInfo['tagName'],_oInfo['isLocal'],oService,_oInfo['primaryKey'],_oInfo['xPath']);
	oDataSource.setRecordsNum(_oInfo['recordsNumKey']);
	oDataSource.setPageSize(_oInfo['pageSizeKey']);
	oDataSource.setPage(_oInfo['pageKey']);
	$dataSourcePool.register(_oInfo['tagName'],oDataSource);
	return oDataSource;
}
/**
 *------------------------------------------------------------------	
 * 绑定数据源事件响应.	
 *------------------------------------------------------------------	
 */
com.trs.ajaxframe.DataHelper.Service.attachEvent('action','success',
	function(_oTransport,_sJson){
		//TODO 
	}
);

window.DefaultAjax500CallBack = function $render500Err(_trans, _json, _bIsJson, _request){
	try{
		var elDiv=$('daton_loading');
		if (elDiv){
			Element.remove(elDiv);
		}
		
		var sDefaultMsg = '与服务器交互失败！原因可能是未登录或者当前用户会话已失效。';
		if(_bIsJson === true) {
			FaultDialog.show({
				code		: _json.code,
				message		: _json.message || sDefaultMsg,
				detail		: _json.detail || sDefaultMsg,
				suggestion  : _json.suggestion 
			}, '与服务器交互时出现错误');
		}else{
			var json= com.trs.util.JSON.parseXml(_trans.responseXML);
			var getNodeVal = com.trs.util.JSON.value;
			FaultDialog.show({
				code		: getNodeVal(json,'fault.code'),
				message		: getNodeVal(json,'fault.message') || sDefaultMsg,
				detail		: getNodeVal(json,'fault.detail') || sDefaultMsg,
				suggestion  : getNodeVal(json,'fault.suggestion')
			}, '与服务器交互时出现错误');
		}

		try{
			if(ProcessBar != null) 
				ProcessBar.close();
		}catch (ex){
			//just skip it
		}
	}catch (ex){
		//alert(ex.description);
		try{
			$alert('与服务器交互时发生了以下异常：\n' + _trans.responseText);
		}catch (ex){
			//alert(ex.description);
		}
	}
}
com.trs.ajaxframe.DataHelper.Service.attachEvent('action','500', window.DefaultAjax500CallBack);
window.DefaultAjaxFailureCallBack = function(_oTransport,_sJson){
	try{
		var elDiv=$('daton_loading');
		if (elDiv){
			Element.remove(elDiv);
		}
		$errorMsg('&nbsp; 与[' + com.trs.portal.ServiceExt.URL + ']交互失败了！');	
		try{
			if(ProcessBar != null) 
				ProcessBar.close();
		}catch (ex){
			//just skip it
		}

	}catch(ex){
		//alert(ex.description);
	}
}
com.trs.ajaxframe.DataHelper.Service.attachEvent('action','failure',window.DefaultAjaxFailureCallBack);

window.DefaultAjaxException = function(_ajaxRequest,_err){
	if(_IE){
		var a = arguments.caller;
		var stack = [];
		stack.push(a.callee);
		while(true){
			a = a.caller;
			if (!a||stack.include(a.callee)) {
				break;
			}
			stack.push(a.callee);
		}
		_err.stack = stack.join('\n------------------------\n');
	}
	var sMessage = _err.message+'\n'+ _err.stack + '\n';
	try{
		var transport = _ajaxRequest.transport;
		sMessage += 'ResponseText:------------\n'+transport.responseText;
	}catch(err){
	}
	alert(sMessage);
}
Error.prototype.getStackTrace = function(args){
	if(this.stack)return this.stack;
	var s = this.message+'\n';
	var a = args.caller;
	var stack = [];
	stack.push(a.callee);
	while(true){
		a = a.caller;
		if (!a||stack.include(a.callee)) {
			break;
		}
		stack.push(a.callee);
	}
	s += stack.join('\n------------------------\n');
	return s;
}
window.DoNotLogin = function(){
	//alert('not login...');
	(top.actualTop || top).location.href = '../../include/not_login.htm';
}
/**
 *------------------------------------------------------------------
 * 全局loading条设置.
 *------------------------------------------------------------------
 */
$parser.loadingType=0;
$parser.disableAutoLoad();
$parser.onCreate=function(){
	if($parser.loadingType==0){
		var e=this;
		var elLoading = document.createElement('DIV');
		elLoading.id = "__tagparser_loading__" + this.id;
		elLoading.innerHTML='<center><img src="'+com.trs.util.Common.BASE+'../images/loading.gif" align="absmiddle"></center>';
		if (e.parentNode){
			e.parentNode.insertBefore(elLoading,e.nextSibling);
		}else{
			Insertion.After(e, elLoading);
		}
		e.innerHTML = '';
	}
	else if($parser.loadingType==1){
		var elDiv=$('daton_loading');
		if(!elDiv){
			var elDiv=document.createElement("DIV");
			elDiv.id='daton_loading';
			elDiv.user=0;
			document.body.appendChild(elDiv);
			elDiv.style.position='absolute';
			elDiv.style.right='0px';
			elDiv.style.top='0px';
			elDiv.style.width='80px';
			elDiv.style.height='30px';
			elDiv.align='center';
			elDiv.vAlign ='center'; 
			elDiv.style.backgroundColor ='brown';
			elDiv.style.color ='white';
			elDiv.style.fontSize ='9pt';
			elDiv.style.fontWeight ='bold';
			elDiv.innerHTML='正在加载';
		}
		elDiv.user++;
	}
	else if($parser.loadingType==100){
		var elDiv=$('daton_loading');
		if(!elDiv){
			var elDiv=document.createElement("DIV");
			elDiv.id='daton_loading';
			elDiv.user=0;
			document.body.appendChild(elDiv);
			elDiv.style.position='absolute';
			elDiv.style.right='0px';
			elDiv.style.top='0px';
			elDiv.style.width='80px';
			elDiv.style.height='30px';
			elDiv.align='center';
			elDiv.vAlign ='center'; 
			elDiv.style.backgroundColor ='brown';
			elDiv.style.color ='white';
			elDiv.style.fontSize ='9pt';
			elDiv.style.fontWeight ='bold';
			elDiv.innerHTML='正在加载';
		}
		elDiv.user++;
	}
}
$parser.onSuccess=function(){
	if($parser.loadingType==0){
		var e = this;
		var elLoading = $('__tagparser_loading__'+e.id);
		e.parentNode.removeChild(elLoading);
		delete elLoading;
		delete b;
	}
	else if($parser.loadingType==1){
		var elDiv=$('daton_loading');
		if(elDiv){
			elDiv.user--;
			if(elDiv.user==0){
				document.body.removeChild(elDiv);
				delete elDiv;
			}
		}
	}
	else if($parser.loadingType==100){
		var elDiv=$('daton_loading');
		if(elDiv){
			elDiv.user--;
			if(elDiv.user==0){
				document.body.removeChild(elDiv);
				delete elDiv;
				try{
					$finishHTMLTranslate(document.body.innerHTML);
				}
				catch(error){
					//TODO logger
					//alert(error.message);
				}
			}
		}
	}
}
$parser.onFailure=function(){
	if($parser.loadingType==0){
		var e = this;
		var elLoading = $('__tagparser_loading__'+e.id);
		e.parentNode.removeChild(elLoading);
		delete elLoading;
	}
	else if($parser.loadingType==1){
		var elDiv=$('daton_loading');
		if(elDiv){
			elDiv.innerHTML='加载失败.';
		}
	}
}

//-----portal navigate   用图片代替“上一页”、“下一页”。
com.trs.ajaxframe.NAVIGATE_Portal=com.trs.ajaxframe.Navigate('','normal');
com.trs.ajaxframe.NAVIGATE_Portal.button=function(_sDataSourceId,_nPageIndex,_disabled,_nButtonType){
		switch(_nButtonType){
			case 1:
				if(_disabled)return '';
				return '<a href="#" style="color:black"'+
					' onclick="com.trs.ajaxframe.TagParser.Singleton.go(\''+
					_sDataSourceId+'\','+_nPageIndex+');"><img src="../images/shouye.gif" width="16" height="16" border="0"/></span></a>';
			case 2:
				if(_disabled)return '';
				return '<a href="#" style="color:black"'+
					' onclick="com.trs.ajaxframe.TagParser.Singleton.go(\''+
					_sDataSourceId+'\','+_nPageIndex+');"><img src="../images/shangye.gif" width="16" height="16" border="0"/></span></a>';
			case 3:
				if(_disabled)return '';
				return '<a href="#" style="color:black"'+
					' onclick="com.trs.ajaxframe.TagParser.Singleton.go(\''+
					_sDataSourceId+'\','+_nPageIndex+');"><img src="../images/xiaye.gif" width="16" height="16" border="0"/></span></a>';
			case 4:
				if(_disabled)return '';
				return '<a href="#" style="color:black"'+
					' onclick="com.trs.ajaxframe.TagParser.Singleton.go(\''+
					_sDataSourceId+'\','+_nPageIndex+');"><img src="../images/weiye.gif" width="16" height="16" border="0"/></span></a>';
		}
};

var BasicDataHelper = $basicDataHelper();

Object.extend(Form.Element.Serializers, {
		inputSelector: function(element) {
			var sIsBoolean = $GS(element, 'isboolean');
			if (sIsBoolean != null && 
				( (sIsBoolean = sIsBoolean.trim().toLowerCase()) == '1' || sIsBoolean == 'true' )){
				return [element.name, element.checked ? '1' : '0'];
			}

			if (element.checked)
				return [element.name, element.value];
		},
		textarea: function(element) {
			var sIsTrans2Html = $GS(element, 'transHtml');
			if (sIsTrans2Html != null &&
				( (sIsTrans2Html = sIsTrans2Html.trim().toLowerCase()) == '1' || sIsTrans2Html == 'true' )){
				return [element.name, $transHtml(element.value)];
			}
			return [element.name, element.value];
		}
	}
);

Object.extend(Ajax.Request.prototype, {
	setRequestHeaders: function() {
    var requestHeaders =
      ['X-Requested-With', 'XMLHttpRequest',
       'X-Prototype-Version', Prototype.Version];

    if (this.options.method == 'post') {
      requestHeaders.push('Content-type',
        this.options.contentType||'multipart/form-data');
//      requestHeaders.push('Content-type',
//        'application/x-www-form-urlencoded');

      /* Force "Connection: close" for Mozilla browsers to work around
       * a bug where XMLHttpReqeuest sends an incorrect Content-length
       * header. See Mozilla Bugzilla #246651.
       */
      if (this.transport.overrideMimeType)
        requestHeaders.push('Connection', 'close');
    }

    if (this.options.requestHeaders)
      requestHeaders.push.apply(requestHeaders, this.options.requestHeaders);

    for (var i = 0; i < requestHeaders.length; i += 2)
      this.transport.setRequestHeader(requestHeaders[i], requestHeaders[i+1]);
  }
});