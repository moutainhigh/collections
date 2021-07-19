$package('com.trs.ajaxframe.DataHelper');

com.trs.ajaxframe.DataHelper.Service = new com.trs.ajaxframe.Service();

com.trs.ajaxframe.DataHelper.Info = function(_isLocal,_sXml,_sServiceName,_sPrimaryKey,_sXPath,_sRecordsNum,_sPageSize,_sPage){
	this.isLocal=_isLocal;
	this.xml=_sXml;
	this.serviceName=_sServiceName;
	this.primaryKey=_sPrimaryKey;
	this.xPath=_sXPath;
	this.recordsNumKey=_sRecordsNum;
	this.pageSizeKey=_sPageSize;
	this.pageKey=_sPage;
}

function $registerDatasource(_sTagName,_oInfo){
	var oService={};
	Object.extend(oService,com.trs.ajaxframe.DataHelper.Service);
	oService.xml=_oInfo.xml;
	oService.setServiceName(_oInfo.serviceName);
	var oDataSource=new com.trs.ajaxframe.DataSource(_sTagName,_oInfo.isLocal,oService,_oInfo.primaryKey,_oInfo.xPath);
	oDataSource.setRecordsNum(_oInfo.recordsNumKey);
	oDataSource.setPageSize(_oInfo.pageSizeKey);
	oDataSource.setPage(_oInfo.pageKey);
	$dataSourcePool.register(_sTagName,oDataSource);
	return oDataSource;
}
function $dataHelper(_oDataSource){
	if(!_oDataSource){
		throw new Error('尚未注册或者引入数据源');
	}
	var oHelper=Object.extend({},_oDataSource);
	oHelper._bindEvent=function(_sAction,_fSuccess,_f500,_fFailure){
		_oDataSource.detachEvent(_sAction,'success');
		_oDataSource.detachEvent(_sAction,'500');
		_oDataSource.detachEvent(_sAction,'failure');
		if(_fSuccess)_oDataSource.attachEvent(_sAction,'success',_fSuccess);
		if(_f500)_oDataSource.attachEvent(_sAction,'500',_f500);
		if(_fFailure)_oDataSource.attachEvent(_sAction,'failure',_fFailure);
	};
	oHelper.save=function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
		var postData= this.__makePostData(_oPost);
		var iId = this.__getId(_oPost, _oDataSource.primaryKey);//Form.$(_oDataSource.primaryKey).value;
		if(!iId||isNaN(iId))iId=0;
		this._bindEvent('save',_fSuccess,_f500,_fFailure);
		_oDataSource.save(iId,postData,'',_bSend);
	};
	oHelper._delete=function(_sIds,_fSuccess,_f500,_fFailure){
		this._bindEvent('_delete',_fSuccess,_f500,_fFailure);
		_oDataSource._delete(_sIds,'',false);
	};
	oHelper.remove=function(_sIds,_fSuccess,_f500,_fFailure){
		this._bindEvent('_delete',_fSuccess,_f500,_fFailure);
		_oDataSource._delete(_sIds,'',false);
	};
	oHelper.refresh=function(_sDataonId,_fSuccess,_f500,_fFailure){
		var elDaton		= $(_sDataonId);
		var method		= elDaton.getAttribute("method");
		method			= (method)?method.toLowerCase():'get';
		this._bindEvent(method,_fSuccess,_f500,_fFailure);
		$parser.loadData(_sDataonId);
	};
	oHelper.search=function(_sDataonId,_sParams,_fSuccess,_f500,_fFailure,_bParamsNOTAutoEncoding){
		if(_sParams&&typeof _sParams=='object'&&_sParams.constructor!=Object){
			var oForm	= _sParams;
			this.searchForm(_sDataonId,oForm,_fSuccess,_f500,_fFailure);
			return;
		}
		var elDaton		= $(_sDataonId);
		var method		= elDaton.getAttribute("method");
		method			= (method)?method.toLowerCase():'get';
		this._bindEvent(method,_fSuccess,_f500,_fFailure);
		$parser.loadData(_sDataonId, _bParamsNOTAutoEncoding ? _sParams : encodeParams(_sParams));
	};
	oHelper.searchForm=function(_sDataonId,_sForm,_fSuccess,_f500,_fFailure){
		var elDaton		= $(_sDataonId);
		var method		= elDaton.getAttribute("method");
		method			= (method)?method.toLowerCase():'get';
		var postData=com.trs.ajaxframe.PostData.form(_sForm);
		var sParams=com.trs.util.JSON.parseJsonToParams(postData);
		this._bindEvent(method,_fSuccess,_f500,_fFailure);
		$parser.loadData(_sDataonId,sParams);
	};
	oHelper.findByIds=function(_sDataonId,_sIds,_fSuccess,_f500,_fFailure){
		this._bindEvent('get',_fSuccess,_f500,_fFailure);
		$parser.findByIds(_sDataonId,'',_sIds);
	};
	oHelper.dataBind=function(_sDataonId,_sIds,_fSuccess,_f500,_fFailure){
		this._bindEvent('get',_fSuccess,_f500,_fFailure);
		$parser.findByIds(_sDataonId,'',_sIds);
	};
	oHelper.call=function(_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		this._bindEvent((_sMethodName||'call').toLowerCase(),_fSuccess,_f500,_fFailure);
		var postData = this.__makePostData(_oPost);
		_oDataSource.call(_sMethodName,postData,'',_bSend);
	};
	/**
	 * 此方法涉及到保留参数的问题,故不能使用大字段数据提交
	 */
	oHelper.callBind=function(_sDataonId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		if($parser.pageTrsAjaxDoms[_sDataonId] && (document.tagName($parser.pageTrsAjaxDoms[_sDataonId].element)||'').toUpperCase()
			!=(document.tagName(_oDataSource)||'').toUpperCase()){
			alert('不匹配的数据源控件名称和ID');
			return;
		}
		this._bindEvent((_sMethodName||'call').toLowerCase(),_fSuccess,_f500,_fFailure);
		var postData = this.__makePostData(_oPost);
		if(_bSend){
			$parser.callBind(_sDataonId,'',_sMethodName, postData, true);
			return;
		}
		// else
		$parser.callBind(_sDataonId,com.trs.util.JSON.parseJsonToParams(postData)
			,_sMethodName, null, false);
	};
	oHelper.combine=function(_serviceName,_methodName,_oPost){
		var postData = this.__makePostData(_oPost);
		return {"serviceName":_serviceName,"methodName":_methodName,"params":postData};
	};
	oHelper.multiCall=function(_arrCombined,_fSuccess,_f500,_fFailure){
		this._bindEvent('__multicall__',_fSuccess,_f500,_fFailure);
		_oDataSource.call('__multicall__',_arrCombined);
	};
	oHelper.multiCallBind=function(_sDataonId,_arrCombined,_fSuccess,_f500,_fFailure){
		this._bindEvent('__multicall__',_fSuccess,_f500,_fFailure);
		$parser.callBind(_sDataonId,'','__multicall__',_arrCombined,true);
	};
	oHelper.__makePostData = function(_oPost){
		var postData=null;
		if(!_oPost){
			postData=null;
		}
		else if(!String.isString(_oPost)){
			postData=com.trs.ajaxframe.PostData.params(_oPost);
		}
		else{
			postData=com.trs.ajaxframe.PostData.form(_oPost);
		}
		return postData;
	};
	oHelper.__getId = function(_oPost, _sPrimaryKey){
		var id = null;
		if(!_oPost){
			id = null;
		}
		else if(!String.isString(_oPost)){
			id = _oPost[_sPrimaryKey];
		}
		else{
			id = Form.$(_sPrimaryKey).value;
		}
		return id;
	};
	ClassName(oHelper , '$dataHelper');
	return oHelper;
}
function $basicDataHelper(){
	var oService={};
	Object.extend(oService,com.trs.ajaxframe.DataHelper.Service);
	oService.xml = '';
	oService.setServiceName('');
	var oDataSource=new com.trs.ajaxframe.DataSource('',false,oService,'','');
	var oHelper = Object.extend({},oDataSource);
	oHelper._bindEvent=function(_sAction,_fSuccess,_f500,_fFailure){
		oDataSource.detachEvent(_sAction,'success');
		if(_f500)oDataSource.detachEvent(_sAction,'500');
		if(_fFailure)oDataSource.detachEvent(_sAction,'failure');
		if(_fSuccess)oDataSource.attachEvent(_sAction,'success',_fSuccess);
		if(_f500)oDataSource.attachEvent(_sAction,'500',_f500);
		if(_fFailure)oDataSource.attachEvent(_sAction,'failure',_fFailure);
	};
	oHelper.LocalCall=function(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		oDataSource.serviceName = _sServiceId;
		oDataSource.url = com.trs.util.Common.BASE + '../localxml/'+_sServiceId+'/'+_sMethodName+'.xml';
		if(!_fFailure){
			_fFailure = function(transport){
				var abc = document.createElement("IMG");
				abc.src = oDataSource.url;
				abc.style.display = 'none';
				document.body.appendChild(abc);
				$alert("Error "+transport.status+":<br>"+abc.src);
				document.body.removeChild(abc);
			}
		}
		this._bindEvent((_sMethodName||'call').toLowerCase(),_fSuccess,_f500,_fFailure);
		oDataSource.call(_sMethodName,{});
	};
	oHelper.call=function(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		oDataSource.serviceName = _sServiceId;
		if(!_fFailure){
			_fFailure = function(transport){
				$alert("Failure:"+transport.responseText);
			}
		}
		this._bindEvent((_sMethodName||'call').toLowerCase(),_fSuccess,_f500,_fFailure);
		var postData = this.__makePostData(_oPost);
		oDataSource.call(_sMethodName,postData,'',_bSend);
	};
	oHelper.combine=function(_serviceName,_methodName,_oPost){
		var postData = this.__makePostData(_oPost);
		return {"serviceName":_serviceName,"methodName":_methodName,"params":postData};
	};
	oHelper.multiCall=function(_arrCombined,_fSuccess,_f500,_fFailure){
		this._bindEvent('__multicall__',_fSuccess,_f500,_fFailure);
		oDataSource.call('__multicall__',_arrCombined);
	};
	oHelper.__makePostData = function(_oPost){
		var postData=null;
		if(!_oPost){
			postData=null;
		}
		else if(!String.isString(_oPost)){
			postData=com.trs.ajaxframe.PostData.params(_oPost);
		}
		else{
			postData=com.trs.ajaxframe.PostData.form(_oPost);
		}
		return postData;
	};
	ClassName(oHelper , '$basicDataHelper');
	return oHelper;
};
