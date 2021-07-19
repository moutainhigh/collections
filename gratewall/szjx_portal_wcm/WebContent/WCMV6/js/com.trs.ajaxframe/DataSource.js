$package('com.trs.ajaxframe');

/**
 * 数据源对象
 */
com.trs.ajaxframe.DataSource = Class.create('ajaxframe.DataSource');
com.trs.ajaxframe.DataSource.prototype = {
	/**local xml路径*/
	xml:'',
	/**remote url路径*/
	url:'',
	/**remote Service名称*/
	serviceName:'',
	/**数据源关键字字段名*/
	primaryKey:null,
	/**数据源列表字段字段名*/
	xPath:null,
	/**
	 * 构造函数
	 * @param _sTagName 数据源对应的置标名
	 * @param _bLocal 区分local和remote模式,true表示local
	 * @param _oService 从Service对象中提取必要的参数
	 * @param _sPrimaryKey 数据源关键字字段名
	 * @param _sXPath 数据源列表字段字段名
	 */
	initialize : function(_sTagName,_bLocal,_oService,_sPrimaryKey,_sXPath) {
		this.tagName	= _sTagName;
		this.isLocal	= _bLocal;
		if(_bLocal){
			this.url	= this.xml	= _oService['xml'];
		}
		else{
			this.url	= _oService['url'];
		}
		this.serviceName	= _oService['serviceName'];
		Object.extend(this,_oService.action);
		this.primaryKey	= (_sPrimaryKey)?_sPrimaryKey.trim():null;//设置数据源关键字字段
		this.xPath		= (_sXPath)?_sXPath.trim():null;//设置数据源节点选取字段
		this.service	= _oService;
	},
	/**
	 * 私有方法
	 * 获取事件名全称
	 * @param _sEvent 事件名的简写
	 */
	_getEvent : function(_sEvent){
		_sEvent			= _sEvent.toLowerCase().trim();
		if(_sEvent == 'success'){
			_sEvent		= 'onSuccess';
		}
		else if(_sEvent == 'failure'){
			_sEvent		= 'onFailure';
		}
		else if(_sEvent == '500'){
			_sEvent		= 'on500';
		}
		return _sEvent;
	},
	/**
	 * 数据源监听不同操作对应的不同事件
	 * @param _sAction 操作名
	 * @param _sEvent 事件名
	 * @param _fObserver 监听方法
	 */
	observe : function(_sAction,_sEvent,_fObserver){
		if(!_sAction||!_sEvent||!_fObserver){
			throw new Error('数据源注册事件失败:三个参数均为必须参数.');
		}
		_sAction	= _sAction.toLowerCase().trim();
		_sEvent		= this._getEvent(_sEvent);
		this[_sAction+'_'+_sEvent]	= _fObserver;
	},
	/**
	 * 数据源取消监听不同操作对应的不同事件
	 * @param _sAction 操作名
	 * @param _sEvent 事件名
	 */
	unobserve : function(_sAction,_sEvent){
		if(!_sAction||!_sEvent){
			throw new Error('数据源取消注册事件失败:两个参数均为必须参数.');
		}
		_sAction	= _sAction.toLowerCase().trim();
		_sEvent		= this._getEvent(_sEvent);
		this[_sAction+'_'+_sEvent]	= null;
	},
	/**
	 * 数据源监听不同操作对应的不同事件
	 * @param _sAction 操作名
	 * @param _sEvent 事件名
	 * @param _fObserver 监听方法
	 */
	attachEvent:function(_sAction,_sEvent,_fObserver){
		this.observe(_sAction,_sEvent,_fObserver);
	},
	/**
	 * 数据源取消监听不同操作对应的不同事件
	 * @param _sAction 操作名
	 * @param _sEvent 事件名
	 */
	detachEvent:function(_sAction,_sEvent){
		this.unobserve(_sAction,_sEvent);
	},
	/**
	 * 设置数据源关键字字段名
	 * @param _sPrimaryKey
	 */
	setPrimaryKey : function(_sPrimaryKey){
		this.primaryKey=(_sPrimaryKey)?_sPrimaryKey.trim():null;
	},
	/**
	 * 数据源列表字段字段名
	 * @param _sXPath
	 */
	setXPath : function(_sXPath){
		this.xPath=(_sXPath)?_sXPath.trim():null;
	},
	/**
	 * 数据源记录总数字段名
	 * @param _sRecordsNum
	 */
	setRecordsNum  : function(_sRecordsNum){
		this.recordsNumKey=_sRecordsNum;
	},
	/**
	 * 数据源每页显示记录数字段名
	 * @param _sPageSize
	 */
	setPageSize  : function(_sPageSize){
		this.pageSizeKey=_sPageSize;
	},
	/**
	 * 数据源表示页数的字段名
	 * @param _sPage
	 */
	setPage : function(_sPage){
		this.pageKey=_sPage;
	},
	/**
	 * 数据源获取记录总数
	 * @param _oJson 数据源对应的JSON数据对象
	 */
	recordsNum : function(_oJson){
		if(!this.recordsNumKey){
			throw new Error('数据源未指定总记录数(recordsNumKey)字段,不能实现导航.');
		}
		var sNum	= null;
		try{
			sNum	= com.trs.util.JSON.value(_oJson , this.recordsNumKey);
		}catch(err){}
		if(!isNaN(sNum)){
			return parseInt(sNum);
		}
		return 0;
	},
	/**
	 * 数据源获取每页显示记录数
	 * @param _oJson 数据源对应的JSON数据对象
	 */
	pageSize : function(_oJson){
		if(!this.pageSizeKey){
			throw new Error('数据源未指定每页显示记录数(pageSizeKey)字段,不能实现导航.');
		}
		var sPageSize	= null;
		try{
			sPageSize	= com.trs.util.JSON.value(_oJson , this.pageSizeKey);
		}catch(err){}
		if(!isNaN(sPageSize)){
			return parseInt(sPageSize);
		}
		return 0;
	},
	/**
	 * 扩展onFailure,包括通过responseXML解析出JSON对象
	 * @param _fTagParserOnFailure TagParser的onFailure回调方法
	 * @param _fCallBack 其他的onFailure回调方法
	 */
	_extendOnFailure : function(options , _fTagParserOnFailure , _fCallBack){
		Object.extend(options,{
			onFailure:function(transport, json){
				var tmpJson = null;
				try{
					if(transport.getResponseHeader('ReturnJson')=='true'){
						tmpJson	= com.trs.util.JSON.eval(transport.responseText);
					}
					else{
						tmpJson = com.trs.util.JSON.parseXml(transport.responseXML);
					}
				}catch(err){
					$log().warn(err.message);
				}
				if(!tmpJson){
					if(transport.getResponseHeader('Content-Type').indexOf('/xml')!=-1){
						tmpJson = com.trs.util.JSON.parseXml(com.trs.util.XML.loadXML(transport.responseText));
					}
				}
				if(_fTagParserOnFailure){
					(_fTagParserOnFailure)(transport, tmpJson);
				}
				if(_fCallBack){
					(_fCallBack)(transport, tmpJson);
				}
			}
		});
	},
	/**
	 * 扩展on500,包括通过responseXML解析出JSON对象
	 * @param _fTagParserOn500 TagParser的on500回调方法
	 * @param _fCallBack 其他的on500回调方法
	 */
	_extendOn500 : function(options , _fTagParserOn500 , _fCallBack){
		Object.extend(options,{
			on500:function(transport, json){
				var tmpJson = null;
				try{
					if(transport.getResponseHeader('ReturnJson')=='true'){
						tmpJson	= com.trs.util.JSON.eval(transport.responseText);
					}
					else{
						tmpJson = com.trs.util.JSON.parseXml(transport.responseXML);
					}
				}catch(err){
					$log().warn(err.message);
				}
				if(!tmpJson){
					if(transport.getResponseHeader('Content-Type').indexOf('/xml')!=-1){
						tmpJson = com.trs.util.JSON.parseXml(com.trs.util.XML.loadXML(transport.responseText));
					}
				}
				if(_fTagParserOn500){
					(_fTagParserOn500)(transport, tmpJson);
				}
				if(_fCallBack){
					(_fCallBack)(transport, tmpJson);
				}
			}
		});
	},
	/**
	 * 扩展onSuccess,包括通过responseXML解析出JSON对象
	 * @param _fTagParserOnSuccess TagParser的onSuccess回调方法
	 * @param _fCallBack 其他的onSuccess回调方法
	 */
	_extendOnSuccess : function(options , _fTagParserOnSuccess , _fCallBack){
		Object.extend(options,{
			onSuccess:function(transport, json){
				var tmpJson = null;
				try{
					if(transport.getResponseHeader('ReturnJson')=='true'){
						tmpJson	= com.trs.util.JSON.eval(transport.responseText);
					}
					else{
						tmpJson = com.trs.util.JSON.parseXml(transport.responseXML);
					}
				}catch(err){
					$log().warn(err.message);
				}
				if(!tmpJson){
					if(transport.getResponseHeader('Content-Type').indexOf('/xml')!=-1){
						tmpJson = com.trs.util.JSON.parseXml(com.trs.util.XML.loadXML(transport.responseText));
					}
				}
				if(_fTagParserOnSuccess){
					(_fTagParserOnSuccess)(transport, tmpJson , _fCallBack);
				}
				else{
					if(_fCallBack){
						(_fCallBack)(transport, tmpJson);
					}
				}
			}
		});
	},
	/**
	 * 统一的远程操作处理
	 * @param _sAction 操作名
	 * @param _oPostData 提交的数据
	 * @param _oOptions AJAX请求需要的可选参数
	 * @param _bSend 是否POST方式提交数据,true表示POST SEND,false表示URL GET
	 * @param _sMethodName call时的方法名
	 */
	_remoteAction : function(_sAction,_oPostData,_oOptions,_bSend,_sMethodName){
		if(!this['url']){
			throw new Error('尚未指定远程Service服务URL,不能执行操作.');
		}
		if(_sAction=='call'){
			if(!_bSend){
				com.trs.ajaxframe.URLAction.call(this['url'],this['serviceName'],_sMethodName,_oPostData,_oOptions);
			}
			else{
				com.trs.ajaxframe.Action.call(this['url'],this['serviceName'],_sMethodName,_oPostData,_oOptions);
			}
		}
		else{
			if(!_bSend){
				com.trs.ajaxframe.URLAction[_sAction](this['url'],this['serviceName'],_oPostData,_oOptions);
			}
			else{
				com.trs.ajaxframe.Action[_sAction](this['url'],this['serviceName'],_oPostData,_oOptions);
			}
		}
	},
	/**
	 * 数据源操作:GET
	 * @param _sParams URL参数
	 * @param _oOptions TagParser的处理参数,用于数据绑定
	 */
	get : function(_sParams,_oOptions){
		var options		= {
			parameters:_sParams,method:'GET'
		};
		_oOptions		= _oOptions||{};
		Object.extend(options,_oOptions);
		this._extendOn500(options,_oOptions['on500'],this['get_on500']||this['on500']);
		this._extendOnFailure(options,_oOptions['onFailure'],this['get_onFailure']||this['onFailure']);
		this._extendOnSuccess(options,_oOptions['onSuccess'],this['get_onSuccess']||this['onSuccess']);
		if(!this.isLocal){//AJAX远程请求
			this._remoteAction('get',null,options,false);
		}
		else{//读取本地XML文件
			if(!this['url']){
				throw new Error('未指定本地获取数据的XML.');
			}
			new com.trs.ajax.LocalRequest(this.getLocalXML(_sParams,'get'),options);
		}
	},
	/**
	 * 数据源操作:findByIds
	 * @param _sParams URL参数
	 * @param _sIds findByIds所提交的id集
	 * @param _oOptions TagParser的处理参数,用于数据绑定
	 */
	findByIds : function(_sParams,_sIds,_oOptions){
		_sIds			= _sIds||'0';
		_oOptions		= _oOptions||{};
		var options		= {
			parameters:_sParams,method:'GET'
		};
		Object.extend(options,_oOptions);
		this._extendOn500(options,_oOptions['on500'],this['get_on500']||this['on500']);
		this._extendOnFailure(options,_oOptions['onFailure'],this['get_onFailure']||this['onFailure']);
		if(!this.isLocal){//AJAX远程请求
			this._extendOnSuccess(options,_oOptions['onSuccess'],this['get_onSuccess']||this['onSuccess']);
			com.trs.ajaxframe.URLAction.findByIds(this['url'],this['serviceName'],_sIds,options);
		}
		else{//读取本地XML文件
			if(!this.xPath||!this.primaryKey){
				throw new Error('未指定关键字字段,无法完成通过ID查找数据的操作.');
			}
			var fOnSuccess=function(transport, tmpJson , callBack){
				com.trs.util.JSON.transform(tmpJson,this.xPath,this.key,'=',this.values);
				if(_oOptions['onSuccess']){
					(_oOptions['onSuccess'])(transport, tmpJson , callBack);
				}
			}.bind({xPath:this.xPath,key:this.primaryKey,values:_sIds.split(',')});
			this._extendOnSuccess(options,fOnSuccess,this['get_onSuccess']||this['onSuccess']);
			new com.trs.ajax.LocalRequest(this.getLocalXML(_sParams),options);
		}
	},
	/**
	 * 数据源操作:insert
	 * @param _oPostData 提交的数据
	 * @param _sParams URL参数
	 * @param _bSend 是否POST方式提交数据,true表示POST SEND,false表示URL GET
	 */
	insert : function(_oPostData,_sParams,_bSend){
		var options		= {
			parameters:_sParams,
			method:'GET',
			on500:this['insert_on500']||this['save_on500']||this['on500'],
			onFailure:this['insert_onFailure']||this['save_onFailure']||this['onFailure'],
			onSuccess:this['insert_onSuccess']||this['save_onSuccess']||this['onSuccess']
		};
		if(this.isLocal){
			if(!this.xPath||!this.primaryKey){
				throw new Error('未指定列表字段的XPath路径或者未指定关键字字段,local模式下无法完成插入数据的操作.');
			}
			var vUrl	= this.getLocalXML(_sParams);
			var vJson	= this.localJson(vUrl);
			var vTmp	= this._insertDefaultData(_sParams);
			vTmp		= com.trs.ajaxframe.PostData.param(this.primaryKey,this.increaseId(vJson),vTmp);
			Object.extend(_oPostData,vTmp);
			var iNum	= com.trs.util.JSON.insert(vJson,this.xPath,_oPostData);
			try{
				com.trs.util.JSON.increase(vJson,this.recordsNumKey,iNum);
			}catch(err){}
			options['returnValue']	= {};
			var iIndex	= this.xPath.lastIndexOf('.');
			var sRoot	= this.xPath.substring(iIndex+1).toUpperCase();
			options['returnValue'][sRoot]	= _oPostData;
			com.trs.ajaxframe.LocalAction.insert(vUrl,null,vJson,options);
		}
		else{
			this._extendOn500(options,null,options['on500']);
			this._extendOnFailure(options,null,options['onFailure']);
			this._extendOnSuccess(options,null,options['onSuccess']);
			this._remoteAction('save',_oPostData,options,_bSend);
		}
	},
	/**
	 * local模式下模拟获得自增关键字字段值
	 */
	increaseId : function(_oJson){
		return com.trs.util.JSON.max(_oJson,this.xPath,this.primaryKey)+1;
	},
	/**
	 * 数据源操作:update
	 * @param _sIds update所提交的id集
	 * @param _oPostData 提交的数据
	 * @param _sParams URL参数
	 * @param _bSend 是否POST方式提交数据,true表示POST SEND,false表示URL GET
	 */
	update : function(_sIds,_oPostData,_sParams,_bSend){
		if(!_sIds){
			throw new Error('未指定更新项的关键字(id)值.');
		}
		var options		= {
			parameters:_sParams,
			method:'GET',
			on500:this['update_on500']||this['save_on500']||this['on500'],
			onFailure:this['update_onFailure']||this['save_onFailure']||this['onFailure'],
			onSuccess:this['update_onSuccess']||this['save_onSuccess']||this['onSuccess']
		};
		if(this.isLocal){
			if(!this.xPath||!this.primaryKey){
				throw new Error('未指定列表字段的XPath路径或者未指定关键字字段,local模式下无法完成更新数据的操作.');
			}
			var vJson	= this.localJson(this.getLocalXML(_sParams));
			var aIds	= (_sIds+'').split(',');
			for(var i=0;i<aIds.length;i++){
				com.trs.util.JSON.update(vJson,this.xPath,_oPostData,this.primaryKey,aIds[i]);
			}
			com.trs.ajaxframe.LocalAction.save(this.getLocalXML(_sParams),null,vJson,options);
		}
		else{
			this._extendOn500(options,null,options['on500']);
			this._extendOnFailure(options,null,options['onFailure']);
			this._extendOnSuccess(options,null,options['onSuccess']);
			_oPostData	= com.trs.ajaxframe.PostData.param(this._paramNameOfUpdateIds(),_sIds,_oPostData);
			this._remoteAction('save',_oPostData,options,_bSend);
		}
	},
	/**
	 * 数据源操作:_delete
	 * @param _sIds _delete所提交的id集
	 * @param _sParams URL参数
	 * @param _bSend 是否POST方式提交数据,true表示POST SEND,false表示URL GET
	 */
	_delete : function(_sIds,_sParams,_bSend){
		if(!_sIds){
			throw new Error('未指定删除项的关键字(id)值.');
		}
		var options		= {
			parameters:_sParams,
			method:'GET',
			on500:this['_delete_on500']||this['on500'],
			onFailure:this['_delete_onFailure']||this['onFailure'],
			onSuccess:this['_delete_onSuccess']||this['onSuccess']
		};
		var _oPostData	= {};
		if(this.isLocal){
			if(!this.xPath||!this.primaryKey){
				throw new Error('未指定列表字段的XPath路径或者未指定关键字字段,local模式下无法完成删除数据的操作.');
			}
			var vJson	= this.localJson(this.getLocalXML(_sParams));
			var iNum	= com.trs.util.JSON.without(vJson,this.xPath,this.primaryKey,'=',_sIds.split(','));
			com.trs.util.JSON.decrease(vJson,this.recordsNumKey,iNum);
			com.trs.ajaxframe.LocalAction._delete(this.getLocalXML(_sParams),null,vJson,options);
		}
		else{
			this._extendOn500(options,null,options['on500']);
			this._extendOnFailure(options,null,options['onFailure']);
			this._extendOnSuccess(options,null,options['onSuccess']);
			_oPostData	= com.trs.ajaxframe.PostData.param(this._paramNameOfDeleteIds(),_sIds,_oPostData);
			this._remoteAction('_delete',_oPostData,options,_bSend);
		}
	},
	/**
	 * 数据源操作:call
	 * @param _sMethodName call的方法名
	 * @param _oPostData 提交的数据
	 * @param _sParams URL参数
	 * @param _bSend 是否POST方式提交数据,true表示POST SEND,false表示URL GET
	 * @param _oOptions TagParser的处理参数,用于数据绑定
	 */
	call : function(_sMethodName,_oPostData,_sParams,_bSend,_oOptions){
		var sMethod		= (_sMethodName)?_sMethodName.toLowerCase():'call';
		var options		= {
			parameters:_sParams,
			method:'GET'
		};
		_oOptions		= _oOptions||{};
		Object.extend(options,_oOptions);
		this._extendOn500(options,_oOptions['on500'],this[sMethod+'_on500']||this['on500']);
		this._extendOnFailure(options,_oOptions['onFailure'],this[sMethod+'_onFailure']||this['onFailure']);
		this._extendOnSuccess(options,_oOptions['onSuccess'],this[sMethod+'_onSuccess']||this['onSuccess']);
		if(_sMethodName == '__multicall__'){
			return com.trs.ajaxframe.Action.multiCall2(this['url'],_oPostData,options);
		}
		if(this.isLocal){
			new com.trs.ajax.LocalRequest(this.getLocalXML(_sParams,_sMethodName,com.trs.util.JSON.parseJsonToParams(_oPostData)),options);
		}
		else{
			this._remoteAction('call',_oPostData,options,_bSend,_sMethodName);
		}
	},
	/**
	 * 通过id区分save操作是insert或者update
	 * @param _iId id
	 * @param _oPostData 提交的数据
	 * @param _sParams URL参数
	 * @param _bSend 是否POST方式提交数据,true表示POST SEND,false表示URL GET
	 */
	save : function(_iId,_oPostData,_sParams,_bSend){
		if(!_iId||parseInt(_iId)=='NaN'||parseInt(_iId)<=0){//insert
			this.insert(_oPostData,_sParams,_bSend);
		}
		else{
			this.update(_iId,_oPostData,_sParams,_bSend);
		}
	},
	/**
	 * 私有方法,同步调用得到一个xml数据的JSON对象
	 * @param _sUrl xml路径
	 */
	localJson : function(_sUrl){
		var vJson	= null;
		new com.trs.ajax.LocalRequest(_sUrl,{asynchronous:false,onSuccess:function(transport){
												vJson = com.trs.util.JSON.parseXml(transport.responseXML);
											}});
		return vJson;
	},
	/**
	 * 用于扩展,表示插入数据时的缺省数据
	 */
	_insertDefaultData : function(_sParams){
		vReturn={};
		/*
		switch(_sParams){//TODO
		}
		*/
		return vReturn;
	},
	/**
	 * 用于扩展,模拟local方式下搜索的接口
	 */
	getLocalXML : function(_sParams){
		return this['xml'];
	},
	/**
	 * 用于扩展,表示save时表示id字段的参数名
	 */
	_paramNameOfUpdateIds:function(){
		return this.primaryKey;
	},
	/**
	 * 用于扩展,表示delete时表示id字段的参数名
	 */
	_paramNameOfDeleteIds:function(){
		return this.primaryKey;
	}
};