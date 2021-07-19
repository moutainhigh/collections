$package('com.trs.ajaxframe');

$import('com.trs.util.JSON');
$import('com.trs.util.XML');
$import('com.trs.ajaxframe.DataSource');
$import('com.trs.ajaxframe.TrsAjaxElement');
$import('com.trs.ajaxframe.DataSourcePool');
$import('com.trs.ajaxframe.Navigate');
$import('com.trs.ajaxframe.Action');
$import('com.trs.ajaxframe.Service');
$import('com.trs.ajaxframe.DataHelper');
$import('com.trs.ajaxframe.PostData');
$import('com.trs.ajaxframe.Instances');
$import('com.trs.logger.Logger');
$import('com.trs.ajaxframe.Implements');
$import('com.trs.ajax.Request');
$import('com.trs.dialog.Dialog');

/**
 * 置标解析类
 * @author TRS WCM
 */
com.trs.ajaxframe.TagParser = Class.create('ajaxframe.TagParser');
com.trs.ajaxframe.TagParser.prototype = {
	/**命名空间*/
	nameSpace		: 'TRS',
	/**数据源池*/
	dataSourcePool	: null,
	/**URL参数*/
	urlQuery		: null,
	/**缓存的页面AJAX DOM*/
	pageTrsAjaxDoms	: null,
	/**页面级的事件监听器*/
	listeners		: null,
	/**缺省的onCreate方法*/
	onCreate		: null,
	/**缺省的onSuccess方法*/
	onSuccess		: null,
	/**缺省的on500方法*/
	on500			: null,
	/**缺省的onFailure方法*/
	onFailure		: null,
	/**
	 * 构造函数
	 * @param _sNameSpace 命名空间
	 */
	initialize : function(_sNameSpace) {
		this.nameSpace			= _sNameSpace;
		this.dataSourcePool		= $dataSourcePool;
		var urlQuery			= location.search.replace(/\?/g,'').parseQuery();
		this.urlQuery			= {};
		for(var param in urlQuery){
			this.urlQuery['$'+param.toUpperCase()]	= urlQuery[param];
		}
		delete urlQuery;
		this.pageTrsAjaxDoms	= {};
		this.listeners			= {};
		this._func_onload		= this.onload.bind(this);
		this.enableAutoLoad();
		this.onCreate			= Prototype.emptyFunction;
		this.onSuccess			= Prototype.emptyFunction;
		this.on500				= Prototype.emptyFunction;
		this.onFailure			= Prototype.emptyFunction;
		Event.observe(window, 'unload',this.destroy.bind(this));
	},
	/**
	 * 开启自动加载功能
	 */
	enableAutoLoad : function(){
		Event.observe(window, 'load',this._func_onload, false);
	},
	/**
	 * 关闭自动加载功能
	 */
	disableAutoLoad : function(){
		Event.stopObserving(window, 'load',this._func_onload, false);
	},
	/**
	 * 注册数据源置标上的事件监听器
	 * @param _sDatonId 数据源置标id
	 * @param _sEvent 事件名
	 * @param _fObserver 事件监听方法
	 */
	observe : function(_sDatonId,_sEvent,_fObserver){
		if(typeof _sDatonId != 'string'){
			_sDatonId		= _sDatonId.id;
		}
		if(!this.listeners[_sDatonId]){
			this.listeners[_sDatonId]		= {};
		}
		this.listeners[_sDatonId][_sEvent]	= _fObserver;
	},
	/**
	 * 取消注册数据源置标上的事件监听器
	 * @param _sDatonId 数据源置标id
	 * @param _sEvent 事件名
	 */
	unobserve : function(_sDatonId,_sEvent){
		if(typeof _sDatonId != 'string'){
			_sDatonId		= _sDatonId.id;
		}
		if(this.listeners[_sDatonId]){
			this.listeners[_sDatonId][_sEvent]	= null;
		}
	},
	/**
	 * 私有方法
	 * 页面数据源的TrsAjaxDom数据
	 * @param _sDsId 数据源置标id
	 */
	_TrsAjaxDom:function(_sDsId){
		return this.pageTrsAjaxDoms[_sDsId];
	},
	/**
	 * 页面加载时初始化所有的数据源置标并加载数据源置标
	 */
	onload : function(){
		//$log().info('onload');
		var vTagNames=this.dataSourcePool.tagNames;
		var elDatons=[];
		for(var i=0;i<vTagNames.length;i++){
			var tmpDataSources=null;
			if(_IE){
				tmpDataSources=document.getElementsByTagName(vTagNames[i]);
			}
			else{
				tmpDataSources=document.getElementsByTagName(this.nameSpace+':'+vTagNames[i]);
			}
			for(var j=0;j<tmpDataSources.length;j++){
				elDatons.push(tmpDataSources[j]);
			}
		}
		var aAutoLoads		= this._getAutoLoadDs(elDatons);
		this._autoLoad(aAutoLoads);
	},
	/**
	 * 私有方法
	 * 从数据源置标数组中分离出需要自动加载的数据源置标
	 * @param _aDatons 数据源置标数组
	 * @return aAutoLoad 自动加载的数据源置标数组 
	 */
	_getAutoLoadDs : function(_aDatons){
		var vAutoLoad=[];
		for(var i=0 ; i<_aDatons.length ; i++){
			var sAuto	= (_aDatons[i].getAttribute("autoLoad")+'').toLowerCase();
			var bAuto	= false;
			if(!this._dataSourceTag(_aDatons[i])){//最外层的数据源缺省为自动加载
				this.pageTrsAjaxDoms[_aDatons[i].id]	= this.toTrsAjaxDom('DS' , _aDatons[i] , null);
				if(sAuto != 'false'){
					bAuto	= true;
				}
				if(bAuto){
					vAutoLoad.push(_aDatons[i]);
				}
			}
			else{//非最外层的数据源缺省为手动加载
				if(sAuto == 'true'){
					bAuto	= true;
				}
				if(bAuto){
					vAutoLoad.push(_aDatons[i]);
				}
			}
		}
		return vAutoLoad;
	},
	/**
	 * 私有方法
	 * 加载需要自动加载的数据源置标
	 * @param _aAutoLoads 自动加载的数据源置标
	 */
	_autoLoad : function(_aAutoLoads){
		for(var i=0	; i<_aAutoLoads.length ; i++){
			var dsParams	= _aAutoLoads[i].getAttribute("params");
			dsParams		= (dsParams)?dsParams.trim():dsParams;
			var trsAjaxDom	= this._preLoadData(_aAutoLoads[i].id , dsParams);
			var elDaton		= trsAjaxDom.element;
			dsParams		= elDaton.getAttribute('params');
			this._loadData(trsAjaxDom , dsParams);
		}
	},
	/**
	 * 加载控件内部自动加载的数据源置标
	 * @param _eElement HTML DOM控件
	 * @exception if _eElement is null
	 */
	load : function(_eElement){
		//$log().info('load'+_eElement);
		var vTagNames=this.dataSourcePool.tagNames;
		var elDatons=[];
		var tmp=_eElement;
		_eElement=$(_eElement);
		if(!_eElement){
			throw new Error('html element "'+tmp+'" doesn\'t exsit.');
		}
		for(var i=0;i<vTagNames.length;i++){
			var tmpDataSources=null;
			if(_IE){
				var tmpDataSources=_eElement.getElementsByTagName(vTagNames[i]);
			}
			else{
				var tmpDataSources=[];
				//临时定义查找所有的数据源方法
				function tmpFindDataon(_el,_tag,_a){
					var tmpChilds=_el.childNodes;
					for(var j=0;j<tmpChilds.length;j++){
						if(tmpChilds[j].tagName && tmpChilds[j].tagName.toUpperCase()==_tag){
							_a.push(tmpChilds[j]);
						}
						else if(tmpChilds[j].tagName){
							tmpFindDataon(tmpChilds[j],_tag,_a);
						}
					}
				}
				tmpFindDataon(_eElement , this.nameSpace+':'+vTagNames[i] , tmpDataSources);
				delete tmpFindDataon;
			}
			for(var j=0;j<tmpDataSources.length;j++){
				elDatons.push(tmpDataSources[j]);
			}
		}
		var aAutoLoads		= this._getAutoLoadDs(elDatons);
		this._autoLoad(aAutoLoads);
	},
	/**
	 * 得到当前数据源置标对应的数据源
	 * @param _elDaton 数据源置标
	 * @return dataSource
	 */
	$datasource : function(_elDaton){
		var sTagName	= _elDaton.tagName||'';
		sTagName		= sTagName.replace(new RegExp(this.nameSpace+':','i'),'');
		return this.dataSourcePool.getDataSource(sTagName);
	},
	/**
	 * 私有方法
	 * 在加载数据源置标前的预处理
	 * @param _sDatonId 数据源置标ID
	 * @param _sDsParams 数据源置标param参数值
	 * @return trsAjaxDom 对应的AJAX DOM对象
	 */
	_preLoadData : function(_sDatonId,_sDsParams){
		var elDaton		= $(_sDatonId);
		var trsAjaxDom	= this._TrsAjaxDom(_sDatonId);
		if(!trsAjaxDom){
			trsAjaxDom	= this.toTrsAjaxDom('DS' , elDaton , null);
			this.pageTrsAjaxDoms[_sDatonId]	= trsAjaxDom;
		}
		else{
			elDaton		= trsAjaxDom.element;
		}
		elDaton.setAttribute('currentPage',1);
		if(_sDsParams!=null){
			elDaton.setAttribute('params',_sDsParams);
		}
		return trsAjaxDom;
	},
	/**
	 * 指定数据源参数加载数据源数据
	 * @param _sDatonId 数据源置标Id
	 * @param _sDsParams 数据源的请求参数
	 */
	loadData:function(_sDatonId,_sDsParams){
		var trsAjaxDom	= this._preLoadData(_sDatonId , _sDsParams);
		var elDaton		= trsAjaxDom.element;
		_sDsParams		= elDaton.getAttribute('params');
		this._loadData(trsAjaxDom , _sDsParams);
	},
	/**
	 * 追加数据源参数加载数据源数据
	 * @param _sDatonId 数据源置标Id
	 * @param _sAddParams 数据源的追加请求参数
	 */
	loadData2:function(_sDatonId,_sAddParams){
		var trsAjaxDom	= this._preLoadData(_sDatonId , null);
		var elDaton		= trsAjaxDom.element;
		if(_sAddParams){
			var oAddParams	= _sAddParams.parseQuery();
			var sOldParams	= elDaton.getAttribute('params');
			var oOldParams	= (sOldParams)?sOldParams.parseQuery():{};
			var oNewParams	= Object.extend(oOldParams,oAddParams);
			elDaton.setAttribute('params',encodeParams(oNewParams));
		}
		var _sDsParams	= elDaton.getAttribute('params');
		this._loadData(trsAjaxDom , _sDsParams);
	},
	/**
	 * 请求数据的方式：通过id查找数据
	 * @param _sDatonId 数据源置标Id
	 * @param _sDsParams 数据源的请求参数
	 * @param _sIds 提交查找的id集
	 */
	findByIds : function( _sDatonId , _sDsParams , _sIds){
		var trsAjaxDom	= this._preLoadData(_sDatonId , _sDsParams);
		var elDaton		= trsAjaxDom.element;
		_sDsParams		= elDaton.getAttribute('params');
		this._loadData(trsAjaxDom , _sDsParams , {method:'findByIds' , ids:_sIds});
	},
	/**
	 * 请求数据的方式：广义调用获取数据
	 * @param _sDatonId 数据源置标Id
	 * @param _sDsParams 数据源的请求参数
	 * @param _sMethod 调用数据的方式
	 * @param _oPostData 调用数据需要提交的准备数据
	 * @param _bSend 是否以Post Send的方式提交,false表示以URL GET的方式提交
	 */
	callBind : function( _sDatonId , _sDsParams , _sMethod , _oPostData , _bSend ){
		var trsAjaxDom	= this._preLoadData(_sDatonId , _sDsParams);
		var elDaton		= trsAjaxDom.element;
		_sDsParams		= elDaton.getAttribute('params');
		this._loadData(trsAjaxDom , _sDsParams , {method : _sMethod , postData : _oPostData , bSend : _bSend});
	},
	/**
	 * 私有方法
	 * 针对一个字符串文本进行数据填充,解析{$},{#}<br>
	 * $开头的,从_oParams中取值,包括优先从URL参数中取值<br>
	 * #开头的,从_oJson中取值,数据域将附加上_sSelection前缀<br>
	 * @param _sSource 字符串文本
	 * @param _oJson 存储XML数据的JS对象
	 * @param _oParams 存储特殊参数的对象
	 * @param _sSelection 当前数据域的前缀
	 * @return 数据填充成功的值
	 */
	_fillValue : function(_sSource , _oJson , _oParams ,_sSelection){
		try{
			if(typeof _sSource!='string'){
				_sSource	= '';
//				throw new Error('非字符串不能进行转换:'+_sSource);
			}
			_oParams		= _oParams || {};
			_oJson			= _oJson || {};
			var pattern		= '\\{(\\$|#|@)[^\\}]{0,100}\\}';
			var regExp		= new RegExp(pattern,'g');
			var vReturn		= '';
			var aCaptures	= null;
			var sCapture	= null;
			var sSelection	= null;
			var sValue		= '';
			var sCmd		= '';
			var lastIndex	= 0;
			var sParam		= '';
			var tmpParams	= null;
			var tmpParam	= '';
			var tmpDefaults	= null;
			var tmp			= '';
			while((aCaptures = regExp.exec(_sSource))!=null){
				vReturn		+= _sSource.substring(lastIndex,aCaptures.index);
				lastIndex	= aCaptures.index + aCaptures[0].length;
				sCapture	= aCaptures[0];
				sParam		= sCapture.substring(1 , sCapture.length-1);
				if(sParam.startsWith('@')){//Function
					try{
						var myRe = new RegExp('^@([^\\(]*)(\\((.*)\\))?$','g');
							///^@([^\(]*)(\((.*)\))?$/g;
						var myCaptures = myRe.exec(sParam);
						var sFunctionName = myCaptures[1];
						var myParams = myCaptures[3];
						tmpParams = [];
						if(myParams.trim()!=''){
							tmpParams	= myParams.split(',');
							for(var i=0;i<tmpParams.length;i++){
								var myReParam = new RegExp('^\\s*([^\\|\\s]*)\\s*(\\|\\|(.*))?$','g');
									//--用后面的式子FF有问题/^\s*([^\|\s]*)\s*(\|\|(.*))?$/g;
								var myCapturesParam = myReParam.exec(tmpParams[i])||[];
								var myTmpParam	= (myCapturesParam[1]||'').toUpperCase();
								var myDefaultParam	= (myCapturesParam[3]||'').trim();
								if(myTmpParam.startsWith('$')){
									tmpParams[i] = this.urlQuery[myTmpParam] || _oParams[myTmpParam] || myDefaultParam;
								}
								else if(myTmpParam.startsWith('#')){
									sSelection		= myTmpParam.substring(1);
									if(_sSelection){
										sSelection	= _sSelection + '.' + sSelection;
									}
									//支持.表示当前节点的值
									if(sSelection.endsWith('..')){
										sSelection = sSelection.substring(0,sSelection.length-2);
									}
									tmpParams[i] = com.trs.ajaxframe.TagHelper.value(sSelection , _oJson) || myDefaultParam;
								}
							}
						}
						var tmpEvalRet = null;
						sValue = 'tmpEvalRet = '+sFunctionName+'.apply('+sFunctionName.replace(/\.[^\.]*$/g,'')+',tmpParams);';
						eval(sValue);
						if(tmpEvalRet!=null&&tmpEvalRet!=undefined){
							sValue = tmpEvalRet;
						}
						else{
							sValue = '';
						}
					}catch(err){
						throw new Error('表达式"'+sCapture+'" error occurs when eval "'+sValue+'"\n'+err.message);
					}
					vReturn		+= sValue;
					continue;
				}
				var aAction	= sParam.split(',');
				if(aAction.length<=2){
					var myDefaultParam = (aAction[1] || '').trim();
					tmpParam	= aAction[0].toUpperCase().trim();
					if(tmpParam.startsWith('$')){
						sValue = (this.urlQuery[tmpParam]||_oParams[tmpParam]||myDefaultParam)+'';
					}
					else if(tmpParam.startsWith('#')){
						sSelection		= tmpParam.substring(1);
						if(_sSelection){
							sSelection	= _sSelection + '.' + sSelection;
						}
						//支持.表示当前节点的值
						if(sSelection.endsWith('..')){
							sSelection = sSelection.substring(0,sSelection.length-2);
						}
						sValue = (com.trs.ajaxframe.TagHelper.value(sSelection,_oJson)||myDefaultParam)+'';
					}
				}
				else if(aAction.length==3){
					tmpParams	= aAction[0].toUpperCase();
					tmpParams	= tmpParams.split(';');
					tmpDefaults	= aAction[1] || '';
					tmpDefaults	= tmpDefaults.split(';');
					for(var i=0;i<tmpParams.length;i++){
						tmpDefaults[i] = (tmpDefaults[i]||'').trim();
						tmpParam	= tmpParams[i].trim();
						if(tmpParam.startsWith('$')){
							tmpParams[i] = (this.urlQuery[tmpParam] || _oParams[tmpParam]||tmpDefaults[i])+'';
						}
						else if(tmpParam.startsWith('#')){
							sSelection		= tmpParam.substring(1);
							if(_sSelection){
								sSelection	= _sSelection + '.' + sSelection;
							}
							//支持.表示当前节点的值
							if(sSelection.endsWith('..')){
								sSelection = sSelection.substring(0,sSelection.length-2);
							}
							tmpParams[i] = (com.trs.ajaxframe.TagHelper.value(sSelection , _oJson)||tmpDefaults[i])+'';
						}
					}
					sCmd	= aAction[2];
					try{
						var tmpEvalRet = null;
						sValue = 'tmpEvalRet = '+sCmd+'.apply('+sCmd.replace(/\.[^\.]*$/g,'')+',tmpParams);';
						eval(sValue);
						if(tmpEvalRet!=null&&tmpEvalRet!=undefined){
							sValue = tmpEvalRet;
						}
						else{
							sValue = '';
						}
					}catch(err){
						throw new Error('表达式"'+sCapture+'" error occurs when eval "'+sValue+'"\n'+err.message);
					}
				}
				delete tmpParams;
				vReturn		+= sValue;
			}
			vReturn			+= _sSource.substring(lastIndex);
			return vReturn;
		}catch(err){
//			$log().error(err.message);
			//TODO logger
			//alert(err.message);
		}
	},
	/**
	 * 转换成AJAX DOM对象的递归方法<br>
	 * ------------------------------------<br>
	 * 根据不同的节点名,返回不同的AJAX DOM实例:<br>
	 * DS ==> com.trs.ajaxframe.TrsAjax.Ds<br>
	 * DATASOURCE ==> com.trs.ajaxframe.TrsAjax.InnerDs<br>
	 * FOR ==> com.trs.ajaxframe.TrsAjax.FOR<br>
	 * PARAM ==> com.trs.ajaxframe.TrsAjax.PARAM<br>
	 * VALUE ==> com.trs.ajaxframe.TrsAjax.VALUE<br>
	 * IF ==> com.trs.ajaxframe.TrsAjax.IF<br>
	 * RECORD ==> com.trs.ajaxframe.TrsAjax.RECORD<br>
	 * NAVIGATE ==> com.trs.ajaxframe.TrsAjax.NAVIGATE<br>
	 * others ==> com.trs.ajaxframe.TrsAjax.Element<br>
	 * -------------------------------------<br>
	 * 根据特殊的条件,子节点进行不同的处理:<br>
	 * nodeName=='#TEXT' || nodeName=='SCRIPT' ==> 子节点直接返回 com.trs.ajaxframe.TrsAjax.Element<br>
	 * nodeName=='#COMMENT'==> 过滤<br>
	 * 属于扩展置标More Custom Tag ==> 子节点直接返回 com.trs.ajaxframe.TrsAjax.Element<br>
	 * 含有justReplace==true属性 ==> 子节点直接返回 com.trs.ajaxframe.TrsAjax.Element<br>
	 * otherwise ==> 递归调用本方法返回DOM实例<br>
	 * --------------------------------------<br>
	 * @param _sNodeName 当前置标节点名
	 * @param _eElement 当前置标Element对象
	 * @param _eParent 当前置标的Parent Element对象
	 * @return AJAX DOM对象
	 */
	toTrsAjaxDom:function(_sNodeName , _eElement , _eParent){
		var trsAjaxElement			= null;
		switch(_sNodeName){
			case 'DS':
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Ds(_sNodeName , _eElement , _eParent);
				break;
			case 'DATASOURCE':
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.InnerDs(_sNodeName , _eElement , _eParent);
				break;
			case 'FOR':
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.For(_sNodeName , _eElement , _eParent);
				break;
			case 'PARAM':
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Param(_sNodeName , _eElement , _eParent);
				return trsAjaxElement;
			case 'VALUE':
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Value(_sNodeName , _eElement , _eParent);
				return trsAjaxElement;
			case 'IF':
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.If(_sNodeName , _eElement , _eParent);
				break;
			case 'RECORD':
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Record(_sNodeName , _eElement , _eParent);
				break;
			case 'NAVIGATE':
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Nav(_sNodeName , _eElement , _eParent);
				break;
			default:
				trsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Element(_sNodeName , _eElement , _eParent);
		}
		var childTrsAjaxElement		= null;
		for(;_eElement.childNodes.length>0;) {
			var currChildElement	= _eElement.childNodes[0];
			currChildElement		= _eElement.removeChild(currChildElement);
			var nodeName			= currChildElement.nodeName.toUpperCase();
			if(!_IE){
				nodeName			= nodeName.replace(new RegExp(this.nameSpace+':','i'),'');
			}
			if(this.dataSourcePool.tagNames.include(nodeName)){
				nodeName			= 'DATASOURCE';
			}
			if(nodeName=='#TEXT'){
				childTrsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Element('#TEXT' , currChildElement , trsAjaxElement);
			}
			else if(nodeName=='#COMMENT'){
				continue;
			}
			else if(currChildElement.getAttribute('justReplace')){
				childTrsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Element(nodeName , currChildElement , trsAjaxElement);
			}
			else if(this['_parse_'+nodeName]){//More Custom Tag
				childTrsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Element(nodeName , currChildElement , trsAjaxElement);
			}
			else if(nodeName=='SCRIPT'){
				childTrsAjaxElement		= new com.trs.ajaxframe.TrsAjax.Element(nodeName , currChildElement , trsAjaxElement);
			}
			else{
				childTrsAjaxElement		= this.toTrsAjaxDom(nodeName , currChildElement , trsAjaxElement);
			}
			trsAjaxElement.pushChild(childTrsAjaxElement);
		}
		return trsAjaxElement;
	},
	/**
	 * 分页导航
	 * @param _sDatonId 数据源置标的Id
	 * @param _iPage 欲跳转的分页数
	 */
	go:function(_sDatonId,_iPage){
		var trsAjaxDom	= this._preLoadData(_sDatonId , null);
		var elDaton		= trsAjaxDom.element;
		var dsParams	= elDaton.getAttribute('params');
		dsParams		= (dsParams)?dsParams.trim():'';
		elDaton.setAttribute('currentPage',_iPage||1);
		this._loadData( trsAjaxDom , dsParams , null , _iPage||1 );
	},
	/**
	 * 私有方法
	 * 数据源置标监听器响应事件
	 * @param _eDaton 数据源置标对象
	 * @param _oTransport 传递的参数1
	 * @param _oJson 传递的参数2
	 * @param _sEvent 事件名
	 */
	_fireEvent : function(_eDaton , _oTransport , _oJson ,_sEvent){
		//$log().info("_fireEvent:"+_sEvent);
		var sDatonid	= _eDaton.id;
		if(this.listeners[sDatonid]){
			if(this.listeners[sDatonid][_sEvent]){
				(this.listeners[sDatonid][_sEvent]).call($(sDatonid),_oTransport, _oJson);
			}
		}
	},
	/**
	 * 私有方法
	 * 数据源置标监听器响应事件(create)
	 * @see _fireEvent
	 */
	_onCreate : function(_eDaton , _oTransport , _oJson){
		this._fireEvent(_eDaton , _oTransport , _oJson , 'create');
	},
	/**
	 * 私有方法
	 * 数据源置标监听器响应事件(success)
	 * @see _fireEvent
	 */
	_onSuccess:function(_eDaton , _oTransport , _oJson){
		this._fireEvent(_eDaton , _oTransport , _oJson , 'success');
	},
	/**
	 * 私有方法
	 * 数据源置标监听器响应事件(failure)
	 * @see _fireEvent
	 */
	_onFailure:function(_eDaton , _oTransport , _oJson){
		this._fireEvent(_eDaton , _oTransport , _oJson , 'failure');
	},
	/**
	 * 私有方法
	 * 数据源置标监听器响应事件(500)
	 * @see _fireEvent
	 */
	_on500:function(_eDaton , _oTransport , _oJson){
		this._fireEvent(_eDaton , _oTransport , _oJson , '500');
	},
	/**
	 * 私有方法
	 * 数据源数据加载完成时调用方法,完成数据源置标的数据填充
	 * @param _oScenes 异步提交时的现场数据
	 * @param _oTransport AJAX请求的Transport对象
	 * @param _oJson JSON对象
	 * @param _fCallBack 回调函数
	 */
	_onSuccessing : function(_oScenes , _oTransport, _oJson , _fCallBack){
		//$log().info("_onSuccessing");
		var oDataSource		= _oScenes.oDataSource;
		var eDataSource		= _oScenes.eDataSource;
		var oTrsAjaxDom		= _oScenes.oTrsAjaxDom;
		var vParams			= _oScenes.oParams||{};
		//added by gongfuchang 
		//为了实现远程服务器控制翻页的效果
		var sCurrPageKey	= oDataSource.currPageKey;
		if (sCurrPageKey){	
			var sCurrPage	= com.trs.util.JSON.value(_oJson, sCurrPageKey);
			if (sCurrPage)
				eDataSource.setAttribute('currentPage', sCurrPage);
		}

		oTrsAjaxDom.parse(this , oDataSource , eDataSource , _oJson , vParams , '' , _oTransport , _fCallBack);
	},
	/**
	 * 私有方法
	 * 当前节点所在的dataSource置标
	 * @param _eElement HTML Element对象
	 * @return 数据源置标对象
	 */
	_dataSourceTag:function(_eElement){
		var pNode=_eElement.parentNode;
		while(pNode)
		{
			var tagName=pNode.nodeName;
			if(tagName&&!_IE)
			{
				var index=tagName.indexOf(':')+1;
				tagName=tagName.substring(index);
			}
			if(tagName){
				tagName=tagName.toUpperCase();
				if(this.dataSourcePool.tagNames.include(tagName))break;
			}
			pNode=pNode.parentNode;
		}
		return pNode;
	},
	/**
	 * 私有方法
	 * 为数据源绑定事件
	 * @param _eDaton 数据源置标对象
	 * @param _bPageScope 是否为页面级域数据源
	 */
	_bindEvents : function( _eDaton , _bPageScope) {
		var createFunc	= _eDaton.getAttribute("onCreate");
		var successFunc = _eDaton.getAttribute("onSuccess");
		var failureFunc = _eDaton.getAttribute("onFailure");
		var _500Func = _eDaton.getAttribute("on500");
		var _sDatonid	= _eDaton.id;
		if( createFunc != null) {
			this.observe( _sDatonid , 'create' , function(){eval(createFunc);});
		}
		else if(_bPageScope){
			this.observe( _sDatonid , 'create' , this.onCreate);
		}
		if( successFunc != null) {
			this.observe( _sDatonid ,'success' , function(){eval(successFunc);});
		}
		else if(_bPageScope){
			this.observe( _sDatonid , 'success' , this.onSuccess);
		}
		if( _500Func != null) {
			this.observe( _sDatonid , '500' , function(){eval(_500Func);});
		}
		else if(_bPageScope){
			this.observe( _sDatonid , '500' , this.on500);
		}
		if( failureFunc != null) {
			this.observe( _sDatonid , 'failure' , function(){eval(failureFunc);});
		}
		else if(_bPageScope){
			this.observe( _sDatonid , 'failure' , this.onFailure);
		}
	},
	/**
	 * 私有方法
	 * 异步加载数据源的数据并填充数据源置标
	 * @param _trsAjaxDom AJAX DOM对象
	 * @param _sDsParams 请求参数
	 * @param _sMethodAbout CallBind的相关参数
	 */
	_loadData : function(_trsAjaxDom , _sDsParams , _sMethodAbout , _iPage){
		var eDaton		= _trsAjaxDom.element;
		this._bindEvents(eDaton , true);
		this._onCreate(eDaton);
		_sDsParams		= this._fillValue(_sDsParams || '' , null , null , '' );
		_sMethodAbout	= _sMethodAbout || {};
		var sMethod		= _sMethodAbout['method'] || eDaton.getAttribute('method');
		if(sMethod!=null){
			eDaton.setAttribute('method' , sMethod);
		}
		sMethod			= (sMethod) ? sMethod : 'GET';
		var dataSource	= this.$datasource(eDaton);
		if( ! dataSource.isLocal ){
			if(_iPage){
				_sDsParams	= _sDsParams.replace(new RegExp('^((.*)&|)'+dataSource.pageKey+'=[^&]*','ig'),'$2');
			}
			if(!_sDsParams||!_sDsParams.match(new RegExp('^(.*&|)'+dataSource.pageKey+'=','ig'))){
				var page = eDaton.getAttribute('currentPage') || 1;
				_sDsParams	= (_sDsParams) ? (_sDsParams+'&') : '';
				_sDsParams	+= dataSource.pageKey + '=' + page;
			}
		}
		var scenes = {
			oDataSource : dataSource,
			eDataSource : eDaton,
			oTrsAjaxDom : _trsAjaxDom
		};
		var caller		= this;
		var options		= {
			onSuccess : function(transport , vJson , _fCallBack){caller._onSuccessing(this , transport , vJson , _fCallBack);}.bind(scenes),
			on500 : function(transport , vJson){caller._on500(this , transport , vJson);}.bind(eDaton),
			onFailure : function(transport , vJson){caller._onFailure(this , transport , vJson);}.bind(eDaton),
			asynchronous : true
		};
		setTimeout(function(){
			switch ( sMethod.toUpperCase() ){
				case 'GET':
					dataSource.get(_sDsParams , options);
					break;
				case 'FINDBYIDS':
					var sIds = _sMethodAbout['ids'];
					if( ! sIds ) {
						sIds = eDaton.getAttribute("params");
						sIds = this._fillValue(sIds || '' , null , null , '' );
					}
					dataSource.findByIds(_sDsParams , sIds , options);
					break;
				default:
					var oPostData = _sMethodAbout['postData'] || null;
					var bSend = _sMethodAbout['bSend'] || false;
					dataSource.call(sMethod , oPostData , _sDsParams , bSend , options);
					break;
			}
		}.bind(caller),1);
	},
	/**
	 * 私有方法
	 * 生成导航条的HTML代码
	 * @param _eDaton 导航条对应的数据源置标对象
	 * @param _oDataSource 导航条对应的数据源对象
	 * @param _oJson 导航条对应的数据JSON对象
	 * @param _oOptions 导航条对应的参数对象,以构造不同的导航条
	 */
	_navigate:function(_eDaton,_oDataSource,_oJson,_oOptions){
		var recordsNum=_oDataSource.recordsNum(_oJson);
		var pageSize=_oDataSource.pageSize(_oJson);
		var currentPage=_eDaton.getAttribute("currentPage");
		var id=_eDaton.getAttribute("id");
		currentPage=(currentPage)?parseInt(currentPage):1;
		var pages=parseInt((recordsNum+pageSize-1)/pageSize);
		var type=(_oOptions.type)?_oOptions.type.toLowerCase():'list';
		var s=[];
		if(type=='list'){
			for(var i=1;i<=pages;i++){
				s.push((_oOptions.page||Prototype.K)(id,i,i==currentPage));
			}
			s.push((_oOptions.more||Prototype.emptyFunction)());
		}
		else if(type=='normal'){
			if(pages>1){
				s.push((_oOptions.button||Prototype.K)(id,1,currentPage==1,1));
				s.push((_oOptions.button||Prototype.K)(id,currentPage-1,currentPage<=1,2));
				s.push((_oOptions.button||Prototype.K)(id,currentPage+1,currentPage>=pages,3));
				s.push((_oOptions.button||Prototype.K)(id,pages,currentPage==pages,4));
			}
		}
		return s.join((_oOptions.space||Prototype.emptyFunction)());
	},
	destroy:function(){
		$destroy(this);
	}
};