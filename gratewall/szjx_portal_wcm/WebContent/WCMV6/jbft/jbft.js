/**
*嘉宾访谈前端页面处理js
*@author	hxj
*@create	2007-5-14
*/

/**
*返回字符串_sDate对应日期与1970年1月1日午夜间全球标准时间的毫秒数
*/
function parseDate(_sDate){
	try{
		//_sDate = _sDate.substr(0,10) + " " + _sDate.substr(10);
		var dateInfo = _sDate.split(" ");
        var datePart = dateInfo[0].split("-");
        var timePart = dateInfo[1].split(":");
		return new Date(datePart[0],datePart[1],datePart[2],timePart[0],timePart[1],timePart[2]).getTime();
	}catch(error){
		alert('日期解析错误:' + error.message);
	}
}

/**
*返回列表奇偶行的样式
*/
function getExtraRowStyle(type){
	if(!type){
		return;
	}
	if(!window._lastRowIndex_){
		window._lastRowIndex_ = 1;
	}
	var style = window._lastRowIndex_ % 2 == 0 ? 'even' : 'odd';
	window._lastRowIndex_++;
	return style;
}

//iframe 自适应高度
function iframeAutoFit(iframes){
    var iframes = iframes || ["jbjs", "xgtp", "xgbd"]; 
    for (var i = 0; i < iframes.length; i++){
        var iframe = window.frames[iframes[i]];
        if(!iframe) continue;
        iframe.frameElement.height = iframe.document.body.offsetHeight;
    }
}

/**
*嘉宾访谈的配置信息类
*/
var JBFTConfig = Class.create();
Object.extend(JBFTConfig.prototype, {
	configs : {
		iframesRefreshInterval	: 60,	//页面中指定的iframe刷新时间间隔，单位为秒   
		iframesNeedRefresh		: true, //是否需要定时刷新iframe
		needRefreshIframes		: ['jbjs', 'xgtp', 'xgbd'],	//页面中需要刷新的iframe集合     
		countForEachParse		: 10,	//得到数据之后，每次解析的数目
		intervalForEachParse	: 100,	//得到数据之后，每隔多长时间解析一次，单位为毫秒
		getDataInterval			: 60,	//页面中列表数据刷新时间间隔，单位为秒        
//		requestUrl				: 'ftsj/index.json',	//页面刷新时，请求的url地址
		requestUrl				: 'vipdata.json',	//页面刷新时，请求的url地址
		listOrder				: 'desc',	//列表按时间的排序方式
		ascOrderId				: 'ascOrderId',
		descOrderId				: 'descOrderId',
		relatedReportArea		: 'relatedReportArea_',	//相关报道DOM ID
		relatedPhotoArea		: 'relatedPhotoArea_',	//相关图片DOM ID
		inputArea				: 'inputArea_',	//问题递交DOM ID
		listAreaContent			: 'listAreaContent_',	//列表内容DOM ID
		listAreaContent_Template: 'listAreaContent_Template'	//列表行模版DOM ID
	},

    initialize : function(_oConfigs){
		Object.extend(this.configs, _oConfigs || {});
    },

	//设置一些关于嘉宾访谈的配置信息
	setConfigs : function(_oConfigs){
		this.configs = _oConfigs || {};
	},

	addConfigItems : function(_oConfigItems){
		Object.extend(this.configs, _oConfigItems || {});
	}
});


/**
*嘉宾访谈的基类
*/
var JBFTBase = Class.create();
Object.extend(JBFTBase.prototype, {
	oJBFTConfig			: null,		//内部的配置变量
	_executeRefreshing_	: false,	//是否正在执行refresh的状态标记
	isDataDealing		: false,	//是否正在处理数据的标记（包括载入、解析两个阶段），防止数据可能发生交叉错误[setTimeout]
	timeStamp			: 0,		//页面保留的时间戳，表示最后访问的记录时间

    initialize : function(_oJBFTConfig){
		this.oJBFTConfig = _oJBFTConfig || new JBFTConfig();		
		this.initEvents();
    },

	//邦定页面的一些基本事件
	initEvents : function(){
		var fillQuestionDom = $('fillQuestion');
		if(fillQuestionDom){
			Event.observe(fillQuestionDom, 'click', function(){
				$('submitA').scrollIntoView(false);
				$('content').focus();
				$('content').select();
			}.bind(this));
		}
		var ascOrderDom = $('ascOrderId');
		if(ascOrderDom){
			Event.observe(ascOrderDom, 'click', function(){
				this.changeListOrder('asc');
			}.bind(this));
		}
		var descOrderDom = $('descOrderId');
		if(descOrderDom){
			Event.observe(descOrderDom, 'click', function(){
				this.changeListOrder('desc');
			}.bind(this));
		}
		new ToggleDemension('source', 'listAreaContent_');

		var configs = this.oJBFTConfig.configs;
		if(configs.iframesNeedRefresh){
			var index = 0;
			setTimeout(function(){
				var sIframe = configs.needRefreshIframes[index];
				var oIframe = $(sIframe);
				if(oIframe){
					var search = oIframe.contentWindow.location.search;
					if(!search){
						var oParams = {};
					}else{
						var oParams = search.toQueryParams();
					}
					oParams["r"] = new Date().getTime();
					oIframe.src = oIframe.contentWindow.location.pathname + "?" + $toQueryStr(oParams);
				}
				index = (index + 1) % configs.needRefreshIframes.length;
				setTimeout(arguments.callee.bind(this), configs.iframesRefreshInterval*1000); 
			}.bind(this), configs.iframesRefreshInterval*1000);
		}
	},

	/*
	*改变列表的顺序
	*@_sOrder	: 'asc','desc'
	*/
	changeListOrder : function(_sOrder){			
		if(_sOrder){
			_sOrder = _sOrder.trim().toLowerCase();
			if(_sOrder != 'asc' && _sOrder != 'desc'){
				alert("排序方式不合法");
				return;
			}
		}else{
			_sOrder = this.oJBFTConfig.configs.listOrder == "desc" ? "asc" : "desc";
		}
		this.stopRefresh();
		(function(){//等到请求回来才能执行此函数
			if(this.isComplete){
				this.stopParse();
				$(this.oJBFTConfig.configs.listAreaContent).innerHTML = '';
				this.oJBFTConfig.configs.listOrder = _sOrder;
				this.timeStamp = 0;
				this.startRefresh();
			}else{
				setTimeout(arguments.callee.bind(this), 100);
			}
		}.bind(this))();
	},

	//启动“定时与服务器请求”服务
	startRefresh : function(){
		if(this._executeRefreshing_) return;
		this._executeRefreshing_ = true;
		this.execRefresh();
	},

	//停止“定时与服务器请求”服务
	stopRefresh : function(){
		this._executeRefreshing_ = false;
		if(this.refreshHandler){
			clearTimeout(this.refreshHandler);
		}		
	},
	
	//执行实际的刷新动作
	execRefresh : function(){
		if(this._executeRefreshing_){
			this.loadData();
			this.refreshHandler = setTimeout(function(){
				//保证loadData执行完成之后才能再次执行loadData，防止列表数据交叉错位
				if(!this.isDataDealing){
					this.loadData();
				}
				if(this._executeRefreshing_){
					this.refreshHandler = setTimeout(arguments.callee.bind(this), this.oJBFTConfig.configs.getDataInterval * 1000);
				}
			}.bind(this), this.oJBFTConfig.configs.getDataInterval * 1000);
		}
	},

	//向服务器请求失败的提示
	requestFailure : function(msg){
		alert(msg);
	},

	loadData : function(){
		alert("need overwrite in subclass.");
	}
});

//嘉宾访谈的具体实现类
var JBFTImpl = Class.create();
Object.extend(JBFTImpl.prototype, JBFTBase.prototype);
Object.extend(JBFTImpl.prototype, {
	//向服务器请求数据的操作
	loadData : function(){		
		this.isDataDealing = true;
		new Ajax.Request(this.oJBFTConfig.configs.requestUrl, {	
			method		: 'get',
			parameters	: 'r=' + new Date().getTime(),
			onSuccess	: this.parseData.bind(this),
			onFailure	: function(){
				this.requestFailure("请求数据列表列表失败");
				this.dataParsed();
			}.bind(this),
			onComplete : function(){
				this.isComplete = true;
			}.bind(this)
		});
	},

	getStartIndex : function(jsonData){
		if(this.timeStamp == 0 || jsonData.length == 0){
			return jsonData.length - 1;
		}
		for(var currIndex = 0; currIndex < jsonData.length; currIndex++){
			if(this.timeStamp >= parseDate(jsonData[currIndex]["PT"])){
				return currIndex - 1;
			}
		}
	},
	
	stopParse : function(){
		if(this.parseHandler){
			clearTimeout(this.parseHandler);
		}
	},

	dataParsed : function(){
		this.isDataDealing = false;
	},

	//得到服务器返回数据之后的解析操作
	parseData : function(transport){
		var json = com.trs.util.JSON.eval(transport.responseText);
		var data = json["DATA"];
		var startIndex = this.getStartIndex(data);		
		if(startIndex == undefined || startIndex < 0){
			this.dataParsed();
			return;
		}
		var configs = this.oJBFTConfig.configs;			
		var insertMethod = configs.listOrder == 'desc' ? "Top" : "Bottom";		
		this.parseHandler = setTimeout(function(){
			var flag = true;//下一次是否执行的标记
			var filterJsonData = [];
			for (var i = 0; i < configs.countForEachParse; i++){
				if(startIndex < 0){
					this.dataParsed();
					this.timeStamp = parseDate(data[0]["PT"]);
					flag = false;
					break;
				}
				filterJsonData.push(json["DATA"][startIndex--]);
			}
			var sValue = TempEvaler.evaluateTemplater(configs.listAreaContent_Template, filterJsonData);
			new Insertion[insertMethod](configs.listAreaContent, sValue);
			if(flag){
				this.parseHandler = setTimeout(arguments.callee.bind(this), configs.intervalForEachParse);
			}
		}.bind(this), configs.intervalForEachParse);
	}		
});

/**
*单击某个对象triggerSource时，切换其他对象target的显示
*/
var ToggleDemension = Class.create();
Object.extend(ToggleDemension.prototype, {
	/**
	*@param	triggerSource	 触发target显示/隐藏的对象
	*@param	target			 要显示/隐藏的对象
	*/
	initialize : function(triggerSource, target){
		this.triggerSource = $(triggerSource);
		this.target = $(target);
		if(this.triggerSource == null || this.target == null){
			alert("参数不合法[triggerSource, target]");
			return;
		}
		this.originalDemension = [this.target.offsetWidth, this.target.offsetHeight];
		this.bindEvent();
	},
	/**
	*邦定triggerSource的click事件
	*/
	bindEvent : function(){
		Event.observe(this.triggerSource, 'click', function(){
			if(this.target.style.display == 'none'){
				this.showElement();
			}else{
				this.hideElement();
			}
		}.bind(this));
	},
	/**
	*显示target
	*/
	showElement : function(){
		this.target.style.display = '';
		setTimeout(function(){
			if(this.target.offsetHeight < this.originalDemension[1]){
				this.target.style.height = this.target.offsetHeight + 50;
				setTimeout(arguments.callee.bind(this), 10);
			}			
		}.bind(this), 10);
	},
	/**
	*隐藏target
	*/
	hideElement : function(){
		setTimeout(function(){
			if(this.target.offsetHeight > 50){
				this.target.style.height = this.target.offsetHeight - 50;
				setTimeout(arguments.callee.bind(this), 10);
			}else{
				this.target.style.display = 'none';
			}
		}.bind(this), 10);
	}
});

Event.observe(window, 'load', function(){
	var oConfigs = new JBFTConfig();
	iframeAutoFit(oConfigs.configs.needRefreshIframes);
	window.jbftObj = new JBFTImpl(oConfigs);
	window.jbftObj.startRefresh();
});