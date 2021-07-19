<!--

// 最大的日志数量、日志数组、当前操作的日志序号
function _LoggerList( maxLength )
{
	// 日志列表
	this.maxLength = maxLength;
	this.index = 0;
	this.list = new Array();
	
	// 样式单列表
	this.styleList = new Array();
	this.addStyle = _operator_addStyle;
	this.lookupStyle = _operator_lookupStyle;
	
	// 方法
	this.lookupLogger = _operator_lookupLogger;	// 查询
	this.checkReentry = _operator_checkReentry;	// 检查日志是否已经存在
	this.addLogger1 = _operator_addLogger1;		// 增加日志
	this.addOperatorLogger = _operator_addOperatorLogger;
	this.addLogger = _operator_addLogger;		// 增加日志
	this.updateLogger = _operator_updateLogger;	// 处理完以后修改日志
	
	// 处理日志
	this.processLogger = _operator_processLogger;	// 通过回调函数处理日志，比如显示
	
	// 打开本地日志窗口
	this.openLoggerWindow = _operator_openLoggerWindow;
	
	return this;
}



// 样式单列表
function LoggerStyleDefine( styleName, styleContent )
{
	this.styleName = styleName;
	this.styleContent = styleContent;
}


// 记录客户端的日志信息，保存在顶层页面中
function _LoggerData( flowId, txnCode, txnName, requestHTML )
{
	// 流水号
	this.flowId = flowId;
	
	// 交易处理日期、时间
	var dt = new Date();
	this.startTime = dt.getTime();
	this.date = dt.getCurrentDate();
	this.time = dt.getCurrentTime();
	
	// 交易代码、交易名称
	this.txnCode = txnCode;
	this.txnName = txnName;
	
	// 请求页面
	this.requestHTML = requestHTML;
	
	// 应答页面
	this.responseHTML = null;
	
	// 交易处理结果、响应时间
	this.stoptime = 0;
	this.errCode = null;
	this.errDesc = null;
	
	return this;
}

// 日志信息
var logger = new _LoggerList( 300 );



// 增加到队列中
function _operator_addLogger1( data )
{
	if( this.list.length < this.maxLength ){
		this.index = this.list.length;
	}
	else if( this.index == this.maxLength ){
		this.index = 0;
	}
	else{
		this.index ++;
	}
	
	this.list[this.index] = data;
}

// 查找日志信息
function _operator_lookupLogger( flowId )
{
	if( this.list.length < this.maxLength ){
		var len = this.list.length;
	}
	else{
		var len = this.maxLength;
	}
	
	for( var ii=0; ii<len; ii++ ){
		if( this.list[ii].flowId == flowId ){
			return this.list[ii];
		}
	}
	
	return null;
}

// 检查是否重复提交
function _operator_checkReentry( flowId )
{
	var logger = this.lookupLogger( flowId );
	if( logger != null ){
		return true;
	}
	
	// 没有找到相同的流水号，第一次提交
	return false;
}


// 增加日志信息
// win 提交请求的窗口
// formName 提交请求的FORM名称
// 返回 null = 成功、其它 = 错误信息(错误代码:错误描述)
function _operator_addLogger( win, formName )
{
	// 流水号
	var obj = win.document.getElementById('inner-flag:flowno');
	var flowId = '0';
	if( obj != null ){
		flowId = obj.value;
	}
	
	// 交易代码和名称
	var fm = win.document.forms[formName];
	var txnCode = fm.action;
	var txnName = fm.caption;
	var reentry = fm.reentry;
	
	// 增加操作日志
	this.addOperatorLogger( flowId, txnCode, txnName, reentry, win );
}

// 增加操作日志
function _operator_addOperatorLogger( flowId, txnCode, txnName, reentry, win )
{
	// 格式化交易码
	var	iptr = txnCode.indexOf('?');
	if( iptr > 0 ){
		txnCode = txnCode.substring( 0, iptr );
	}
	
	// 删除根
	if( txnCode.indexOf(_browse.contextPath+'/') == 0 ){
		txnCode = txnCode.substring( _browse.contextPath.length );
	}
	
	if( txnCode.substring(0,1) == '/' ){
		txnCode = txnCode.substring( 1 );
	}
	
	// 取类型
	iptr = txnCode.lastIndexOf('.');
	if( iptr > 0 ){
		var postfix = txnCode.substring( iptr + 1 );
		txnCode = txnCode.substring( 0, iptr );
		
		if( postfix == 'do' ){
			if( txnCode.indexOf('txn') == 0 ){
				txnCode = txnCode.substring(3);
			}
		}
	}
	else{
		var postfix = '';
	}
	
	// 检查重入标志
	if( reentry == 'false' ){
		// 检查请求是否已经提交过，不允许重复提交
		if( this.checkReentry(flowId) == true ){
			return '错误[100001] ==> 此请求不允许重复提交，请设置交易重入标志';
		}
	}
	
	// 请求页面
	if( win != null ){
		var requestHTML = win._browse.html.outerHTML;
	}
	else{
		var requestHTML = null;
	}
	
	// 生成日志
	var d = new _LoggerData( flowId, txnCode, txnName, requestHTML );
	
	// 增加到队列中
	this.addLogger1( d );
	
	// 增加样式单
	this.addStyle( win );
}

// 修改日志信息
function _operator_updateLogger( win, flag )
{
	if( win.oldFlowId == '' ){
		return;
	}
	
	// 查找日志
	var flowId = win.oldFlowId;
	var logger = this.lookupLogger( flowId );
	if( logger == null ){
		return;
	}
	
	// 是否已经处理过
	if( logger.stoptime > 0 ){
		return;
	}
	
	// 修改内容
	var dt = new Date();
	logger.stopTime = dt.getTime();
	
	// 处理结果
	logger.errCode = win.errorCode;
	logger.errDesc = win.errorDesc;
	
	// 响应页面
	if( flag ){
		logger.responseHTML = win._browse.html.outerHTML;
	}
	
	// 增加样式单
	this.addStyle( win );
}

// 生成日志信息
function _operator_processLogger( callback )
{
	if( this.list.length < this.maxLength ){
		// 顺序处理
		for( var ii=0; ii<this.list.length; ii++ ){
			callback( this.list[ii] );
		}
	}
	else{
		// 从index开始处理
		for( var ii=this.index+1; ii<this.maxLength; ii++ ){
			callback( this.list[ii] );
		}
		
		// 从0开始
		for( var ii=0; ii<this.index; ii++ ){
			callback( this.list[ii] );
		}
	}
}

// 打开本地日志窗口
function _operator_openLoggerWindow( )
{
	var xurl = _browse.contextPath + '/script/logger/logger-main.html';
	window.open( xurl, 'localLogger' );
}

// 增加样式单
function _operator_addStyle( win )
{
	if( win == null ){
		return;
	}
	
	var styles = win.document.styleSheets;
	if( styles == null ){
		return;
	}
	
	for( var ii=0; ii<styles.length; ii++ ){
		var styleName = styles[ii].href;
		var st = this.lookupStyle( styleName );
		if( st == null ){
			var styleContent = styles[ii].cssText;
			st = new LoggerStyleDefine( styleName, styleContent );
			this.styleList[this.styleList.length] = st;
		}
	}
}

// 查找样式单
function _operator_lookupStyle( styleName )
{
	var list = this.styleList;
	for( var ii=0; ii<list.length; ii++ ){
		if( list[ii].styleName == styleName ){
			return list[ii];
		}
	}
	
	return null;
}


/* ****************************** 访问过的页面列表 ********************************* */


// 访问过的页面列表:流水号列表
function _LoadHistoryList( maxLength )
{
	if( maxLength < 64 ){
		maxLength = 64;
	}
	
	// 日志列表
	this.maxLength = maxLength;
	this.index = -1;
	this.list = new Array();	// 格式：页面的时间戳，是否允许重入(0=允许、1=不允许)
	this.addSubmitPage = _history_addSubmitPage;
	this.checkPageReentry = _history_checkPageReentry;
}

var histortList = new _LoadHistoryList( 256 );

// 增加不允许重入的已经访问过的页面
function _history_addSubmitPage( win )
{
	if( this.list.length < this.maxLength ){
		var len = this.list.length;
	}
	else{
		var len = this.maxLength;
	}
	
	for( var ii=0; ii<len; ii++ ){
		if( this.list[ii][0] == win._serverTimeStamp ){
			this.list[ii][1] = 1;
		}
	}
}

// 检查页面是否已经访问过
function _history_checkPageReentry( win )
{
	// 初始化
	if( this.index == -1 ){
		this.index = 0;
		this.list[this.index] = [win._serverTimeStamp, 0];
		return false;
	}
	
	// 检查是否最后一个页面
	if( this.list[this.index][0] == win._serverTimeStamp ){
		return false;
	}
	
	// 检查是否回退
	if( this.list.length < this.maxLength ){
		var len = this.list.length;
	}
	else{
		var len = this.maxLength;
	}
	
	for( var ii=0; ii<len; ii++ ){
		if( this.list[ii][0] == win._serverTimeStamp ){
			if( this.list[ii][1] == 1 || win._back_disabled == true ){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	// 增加页面编号
	if( this.list.length < this.maxLength ){
		this.index = this.list.length;
	}
	else if( this.index == this.maxLength ){
		this.index = 0;
	}
	else{
		this.index ++;
	}
	
	this.list[this.index] = [win._serverTimeStamp, 0];
	return false;
}




/* ****************************** 交易的缓存数据 ********************************* */
// 保存的grid信息
function _SavedGridInfo( gridName )
{
	this.gridName = gridName;
	this.recordNumber = null;
	this.selectedRecord = null;
}

// 保存的页面信息,目前主要是GRID中成功处理的记录数量
function _SavedActionData( txnCode )
{
	// 交易代码
	this.txnCode = txnCode;
	
	// 处理成功的记录数量，只对deleteRecord、addRecord和updateRecord有效，通过这些参数，避免修改记录后再次查询记录的数量
	this.successNumber = 0;
	this.funcName = null;
	this.gridName = null;
	
	// grid列表
	this.gridList = new Array();
	
	// 保存数据
	this.saveData = _action_saveData;
	this.loadData = _action_loadData;
	
	// 设置选中的记录
	this.setSelectedRecord = _action_setSelectedRecord;
}

// 页面信息列表:maxLength＝保存的页面信息数量
function _SavedActionList( maxLength )
{
	if( maxLength < 128 ){
		maxLength = 128;
	}
	
	// 页面列表
	this.maxLength = maxLength;
	this.index = 0;
	this.list = new Array();
	
	// 存取页面信息
	this.saveData = _page_saveData;
	this.getData = _page_getData;
	this.addActionData = _page_addActionData;
}

// 页面的交易列表
var _actionList = new _SavedActionList( 128 );


// 初始化页面信息
function _page_saveData( win )
{
	if( win == null ){
		return null;
	}
	
	var forms = win.document.forms;
	if( forms == null || forms.length == 0 ){
		return;
	}
	
	var grids = win.grids;
	if( grids == null || grids.length == 0 ){
		return;
	}
	
	// 每个action的grids
	for( var ii=0; ii<forms.length; ii++ ){
		// form中的grid列表
		var g = new Array();
		
		var formName = forms[ii].name;
		for( var jj=0; jj<grids.length; jj++ ){
			var grid = grids[jj];
			if( grid.getFormName() == formName ){
				g[g.length] = grid;
			}
		}
		
		// 判断 form 中是否有 grid
		if( g.length > 0 ){
			// 取 txnCode
			var txnCode = forms[ii].action;
			var ptr = txnCode.indexOf( '?' );
			if( ptr > 0 ){
				txnCode = txnCode.substring( 0, ptr );
			}
			
			ptr = txnCode.lastIndexOf( '/txn' );
			if( ptr >= 0 ){
				txnCode = txnCode.substring( ptr + 4 );
				ptr = txnCode.indexOf( '.do' );
				if( ptr > 0 ){
					txnCode = txnCode.substring( 0, ptr );
					
					// 取 或者 生成Action
					var action = this.getData( txnCode );
					if( action == null ){
						action = new _SavedActionData( txnCode );
						this.addActionData( action );
					}
					
					// 保存数据
					action.saveData( win, g );
				}
			}
		}
	}
}

// 根据校验码获取保存的交易信息
function _page_getData( txnCode )
{
	// 格式化校验码
	var ptr = txnCode.indexOf( '?' );
	if( ptr > 0 ){
		txnCode = txnCode.substring( 0, ptr );
	}
	
	ptr = txnCode.lastIndexOf( '/txn' );
	if( ptr >= 0 ){
		txnCode = txnCode.substring( ptr + 4 );
	}
	
	ptr = txnCode.indexOf( '.do' );
	if( ptr > 0 ){
		txnCode = txnCode.substring( 0, ptr );
	}
	
	if( this.list.length < this.maxLength ){
		var len = this.list.length;
	}
	else{
		var len = this.maxLength;
	}
	
	for( var ii=0; ii<len; ii++ ){
		if( this.list[ii].txnCode == txnCode ){
			return this.list[ii];
		}
	}
	
	return null;
}

// 增加交易信息
function _page_addActionData( action )
{
	if( this.list.length < this.maxLength ){
		this.index = this.list.length;
	}
	else if( this.index == this.maxLength ){
		this.index = 0;
	}
	else{
		this.index ++;
	}
	
	this.list[this.index] = action;
}

// 保存交易信息
function _action_saveData( win, grids )
{
	this.successNumber = 0;
	this.funcName = null;
	this.gridName = null;
	
	// grid列表
	this.gridList = new Array();
	
	// 增加记录总数 和 选中的记录
	for( var ii=0; ii<grids.length; ii++ ){
		var gridName = grids[ii].gridName;
		var g = new _SavedGridInfo( gridName );
		this.gridList[ii] = g;
		
		// 取记录总数 和 选中的记录
		var fieldName = 'attribute-node:' + gridName + '_selected-key';
		var obj = win.document.getElementById( fieldName );
		if( obj != null && obj.value != '' ){
			g.selectedRecord = obj.value;
		}
		
		g.recordNumber = grids[ii].totalRecord;
	}
}

// 设置选中的记录
function _action_setSelectedRecord( gridName, selectedRecord )
{
	for( var ii=0; ii<this.gridList.length; ii++ ){
		if( this.gridList[ii].gridName == gridName ){
			this.gridList[ii].selectedRecord = selectedRecord;
		}
	}
}

// 取交易信息
function _action_loadData( xurl, win )
{
	// 取记录数量
	var totalRecord = 0;
	for( var ii=0; ii<this.gridList.length; ii++ ){
		totalRecord = this.gridList[ii].recordNumber;
		
		if( totalRecord > 0 ){
			// 计算当前处理的grid
			if( this.gridList[ii].gridName == this.gridName ){
				// 设置记录数量，避免提交时再次查询记录总数
				if( this.funcName == 'insert' ){
					totalRecord = totalRecord + this.successNumber;
				}
				else if( this.funcName == 'delete' ){
					totalRecord = totalRecord - this.successNumber;
				}
			}
			
			// 设置记录数量
			var fieldName = 'attribute-node:' + this.gridList[ii].gridName + '_record-number';
			if( xurl == null || xurl == '' ){
				if( win != null ){
					var obj = win.document.getElementById(fieldName);
					if( obj != null ){
						obj.value = totalRecord;
					}
				}
			}
			else{
				// 总记录数量
				var ptr = xurl.indexOf( '?' );
				if( ptr > 0 ){
					xurl = xurl + '&';
				}
				else{
					xurl = xurl + '?';
				}
				
				xurl = xurl +  fieldName + '=' + totalRecord;
			}
		}
	}
	
	// 选中的记录信息
	if( xurl != null && xurl != '' ){
		for( var ii=0; ii<this.gridList.length; ii++ ){
			var selectedRecord = this.gridList[ii].selectedRecord;
			if( selectedRecord != null && selectedRecord != '' ){
				var gridName = this.gridList[ii].gridName;
				var fieldName = 'attribute-node:' + gridName + '_selected-key';
				
				var ptr = xurl.indexOf( '?' );
				if( ptr > 0 ){
					xurl = xurl + '&';
				}
				else{
					xurl = xurl + '?';
				}
				
				xurl += fieldName + '=' + selectedRecord;
			}
		}
		
		return xurl;
	}
}


// 设置日志处理函数
_browse.addLogger = function(win, formName){ logger.addLogger(win, formName) };
_browse.updateLogger = function(win, flag){ logger.updateLogger(win, flag) };
_browse.processLogger = function(callback){ logger.processLogger(callback) };
_browse.addOperatorLogger = function(flowId, txnCode, txnName, reentry, win){ logger.addOperatorLogger(flowId, txnCode, txnName, reentry, win) };
_browse.openLoggerWindow = function(){ logger.openLoggerWindow() };

// 检查页面是否回退了
_browse.addSubmitPage = function(win){ return histortList.addSubmitPage(win) };
_browse.checkPageReentry = function(win){ return histortList.checkPageReentry(win) };

// 保存、获取 页面数据
_browse.savePageData = function(win){ _actionList.saveData(win) };
_browse.getPageData = function(txnCode){ return _actionList.getData(txnCode) };

//-->
