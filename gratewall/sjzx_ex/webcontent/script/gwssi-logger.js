<!--

// ������־��������־���顢��ǰ��������־���
function _LoggerList( maxLength )
{
	// ��־�б�
	this.maxLength = maxLength;
	this.index = 0;
	this.list = new Array();
	
	// ��ʽ���б�
	this.styleList = new Array();
	this.addStyle = _operator_addStyle;
	this.lookupStyle = _operator_lookupStyle;
	
	// ����
	this.lookupLogger = _operator_lookupLogger;	// ��ѯ
	this.checkReentry = _operator_checkReentry;	// �����־�Ƿ��Ѿ�����
	this.addLogger1 = _operator_addLogger1;		// ������־
	this.addOperatorLogger = _operator_addOperatorLogger;
	this.addLogger = _operator_addLogger;		// ������־
	this.updateLogger = _operator_updateLogger;	// �������Ժ��޸���־
	
	// ������־
	this.processLogger = _operator_processLogger;	// ͨ���ص�����������־��������ʾ
	
	// �򿪱�����־����
	this.openLoggerWindow = _operator_openLoggerWindow;
	
	return this;
}



// ��ʽ���б�
function LoggerStyleDefine( styleName, styleContent )
{
	this.styleName = styleName;
	this.styleContent = styleContent;
}


// ��¼�ͻ��˵���־��Ϣ�������ڶ���ҳ����
function _LoggerData( flowId, txnCode, txnName, requestHTML )
{
	// ��ˮ��
	this.flowId = flowId;
	
	// ���״������ڡ�ʱ��
	var dt = new Date();
	this.startTime = dt.getTime();
	this.date = dt.getCurrentDate();
	this.time = dt.getCurrentTime();
	
	// ���״��롢��������
	this.txnCode = txnCode;
	this.txnName = txnName;
	
	// ����ҳ��
	this.requestHTML = requestHTML;
	
	// Ӧ��ҳ��
	this.responseHTML = null;
	
	// ���״���������Ӧʱ��
	this.stoptime = 0;
	this.errCode = null;
	this.errDesc = null;
	
	return this;
}

// ��־��Ϣ
var logger = new _LoggerList( 300 );



// ���ӵ�������
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

// ������־��Ϣ
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

// ����Ƿ��ظ��ύ
function _operator_checkReentry( flowId )
{
	var logger = this.lookupLogger( flowId );
	if( logger != null ){
		return true;
	}
	
	// û���ҵ���ͬ����ˮ�ţ���һ���ύ
	return false;
}


// ������־��Ϣ
// win �ύ����Ĵ���
// formName �ύ�����FORM����
// ���� null = �ɹ������� = ������Ϣ(�������:��������)
function _operator_addLogger( win, formName )
{
	// ��ˮ��
	var obj = win.document.getElementById('inner-flag:flowno');
	var flowId = '0';
	if( obj != null ){
		flowId = obj.value;
	}
	
	// ���״��������
	var fm = win.document.forms[formName];
	var txnCode = fm.action;
	var txnName = fm.caption;
	var reentry = fm.reentry;
	
	// ���Ӳ�����־
	this.addOperatorLogger( flowId, txnCode, txnName, reentry, win );
}

// ���Ӳ�����־
function _operator_addOperatorLogger( flowId, txnCode, txnName, reentry, win )
{
	// ��ʽ��������
	var	iptr = txnCode.indexOf('?');
	if( iptr > 0 ){
		txnCode = txnCode.substring( 0, iptr );
	}
	
	// ɾ����
	if( txnCode.indexOf(_browse.contextPath+'/') == 0 ){
		txnCode = txnCode.substring( _browse.contextPath.length );
	}
	
	if( txnCode.substring(0,1) == '/' ){
		txnCode = txnCode.substring( 1 );
	}
	
	// ȡ����
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
	
	// ��������־
	if( reentry == 'false' ){
		// ��������Ƿ��Ѿ��ύ�����������ظ��ύ
		if( this.checkReentry(flowId) == true ){
			return '����[100001] ==> �����������ظ��ύ�������ý��������־';
		}
	}
	
	// ����ҳ��
	if( win != null ){
		var requestHTML = win._browse.html.outerHTML;
	}
	else{
		var requestHTML = null;
	}
	
	// ������־
	var d = new _LoggerData( flowId, txnCode, txnName, requestHTML );
	
	// ���ӵ�������
	this.addLogger1( d );
	
	// ������ʽ��
	this.addStyle( win );
}

// �޸���־��Ϣ
function _operator_updateLogger( win, flag )
{
	if( win.oldFlowId == '' ){
		return;
	}
	
	// ������־
	var flowId = win.oldFlowId;
	var logger = this.lookupLogger( flowId );
	if( logger == null ){
		return;
	}
	
	// �Ƿ��Ѿ������
	if( logger.stoptime > 0 ){
		return;
	}
	
	// �޸�����
	var dt = new Date();
	logger.stopTime = dt.getTime();
	
	// ������
	logger.errCode = win.errorCode;
	logger.errDesc = win.errorDesc;
	
	// ��Ӧҳ��
	if( flag ){
		logger.responseHTML = win._browse.html.outerHTML;
	}
	
	// ������ʽ��
	this.addStyle( win );
}

// ������־��Ϣ
function _operator_processLogger( callback )
{
	if( this.list.length < this.maxLength ){
		// ˳����
		for( var ii=0; ii<this.list.length; ii++ ){
			callback( this.list[ii] );
		}
	}
	else{
		// ��index��ʼ����
		for( var ii=this.index+1; ii<this.maxLength; ii++ ){
			callback( this.list[ii] );
		}
		
		// ��0��ʼ
		for( var ii=0; ii<this.index; ii++ ){
			callback( this.list[ii] );
		}
	}
}

// �򿪱�����־����
function _operator_openLoggerWindow( )
{
	var xurl = _browse.contextPath + '/script/logger/logger-main.html';
	window.open( xurl, 'localLogger' );
}

// ������ʽ��
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

// ������ʽ��
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


/* ****************************** ���ʹ���ҳ���б� ********************************* */


// ���ʹ���ҳ���б�:��ˮ���б�
function _LoadHistoryList( maxLength )
{
	if( maxLength < 64 ){
		maxLength = 64;
	}
	
	// ��־�б�
	this.maxLength = maxLength;
	this.index = -1;
	this.list = new Array();	// ��ʽ��ҳ���ʱ������Ƿ���������(0=����1=������)
	this.addSubmitPage = _history_addSubmitPage;
	this.checkPageReentry = _history_checkPageReentry;
}

var histortList = new _LoadHistoryList( 256 );

// ���Ӳ�����������Ѿ����ʹ���ҳ��
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

// ���ҳ���Ƿ��Ѿ����ʹ�
function _history_checkPageReentry( win )
{
	// ��ʼ��
	if( this.index == -1 ){
		this.index = 0;
		this.list[this.index] = [win._serverTimeStamp, 0];
		return false;
	}
	
	// ����Ƿ����һ��ҳ��
	if( this.list[this.index][0] == win._serverTimeStamp ){
		return false;
	}
	
	// ����Ƿ����
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
	
	// ����ҳ����
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




/* ****************************** ���׵Ļ������� ********************************* */
// �����grid��Ϣ
function _SavedGridInfo( gridName )
{
	this.gridName = gridName;
	this.recordNumber = null;
	this.selectedRecord = null;
}

// �����ҳ����Ϣ,Ŀǰ��Ҫ��GRID�гɹ�����ļ�¼����
function _SavedActionData( txnCode )
{
	// ���״���
	this.txnCode = txnCode;
	
	// ����ɹ��ļ�¼������ֻ��deleteRecord��addRecord��updateRecord��Ч��ͨ����Щ�����������޸ļ�¼���ٴβ�ѯ��¼������
	this.successNumber = 0;
	this.funcName = null;
	this.gridName = null;
	
	// grid�б�
	this.gridList = new Array();
	
	// ��������
	this.saveData = _action_saveData;
	this.loadData = _action_loadData;
	
	// ����ѡ�еļ�¼
	this.setSelectedRecord = _action_setSelectedRecord;
}

// ҳ����Ϣ�б�:maxLength�������ҳ����Ϣ����
function _SavedActionList( maxLength )
{
	if( maxLength < 128 ){
		maxLength = 128;
	}
	
	// ҳ���б�
	this.maxLength = maxLength;
	this.index = 0;
	this.list = new Array();
	
	// ��ȡҳ����Ϣ
	this.saveData = _page_saveData;
	this.getData = _page_getData;
	this.addActionData = _page_addActionData;
}

// ҳ��Ľ����б�
var _actionList = new _SavedActionList( 128 );


// ��ʼ��ҳ����Ϣ
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
	
	// ÿ��action��grids
	for( var ii=0; ii<forms.length; ii++ ){
		// form�е�grid�б�
		var g = new Array();
		
		var formName = forms[ii].name;
		for( var jj=0; jj<grids.length; jj++ ){
			var grid = grids[jj];
			if( grid.getFormName() == formName ){
				g[g.length] = grid;
			}
		}
		
		// �ж� form ���Ƿ��� grid
		if( g.length > 0 ){
			// ȡ txnCode
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
					
					// ȡ ���� ����Action
					var action = this.getData( txnCode );
					if( action == null ){
						action = new _SavedActionData( txnCode );
						this.addActionData( action );
					}
					
					// ��������
					action.saveData( win, g );
				}
			}
		}
	}
}

// ����У�����ȡ����Ľ�����Ϣ
function _page_getData( txnCode )
{
	// ��ʽ��У����
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

// ���ӽ�����Ϣ
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

// ���潻����Ϣ
function _action_saveData( win, grids )
{
	this.successNumber = 0;
	this.funcName = null;
	this.gridName = null;
	
	// grid�б�
	this.gridList = new Array();
	
	// ���Ӽ�¼���� �� ѡ�еļ�¼
	for( var ii=0; ii<grids.length; ii++ ){
		var gridName = grids[ii].gridName;
		var g = new _SavedGridInfo( gridName );
		this.gridList[ii] = g;
		
		// ȡ��¼���� �� ѡ�еļ�¼
		var fieldName = 'attribute-node:' + gridName + '_selected-key';
		var obj = win.document.getElementById( fieldName );
		if( obj != null && obj.value != '' ){
			g.selectedRecord = obj.value;
		}
		
		g.recordNumber = grids[ii].totalRecord;
	}
}

// ����ѡ�еļ�¼
function _action_setSelectedRecord( gridName, selectedRecord )
{
	for( var ii=0; ii<this.gridList.length; ii++ ){
		if( this.gridList[ii].gridName == gridName ){
			this.gridList[ii].selectedRecord = selectedRecord;
		}
	}
}

// ȡ������Ϣ
function _action_loadData( xurl, win )
{
	// ȡ��¼����
	var totalRecord = 0;
	for( var ii=0; ii<this.gridList.length; ii++ ){
		totalRecord = this.gridList[ii].recordNumber;
		
		if( totalRecord > 0 ){
			// ���㵱ǰ�����grid
			if( this.gridList[ii].gridName == this.gridName ){
				// ���ü�¼�����������ύʱ�ٴβ�ѯ��¼����
				if( this.funcName == 'insert' ){
					totalRecord = totalRecord + this.successNumber;
				}
				else if( this.funcName == 'delete' ){
					totalRecord = totalRecord - this.successNumber;
				}
			}
			
			// ���ü�¼����
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
				// �ܼ�¼����
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
	
	// ѡ�еļ�¼��Ϣ
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


// ������־������
_browse.addLogger = function(win, formName){ logger.addLogger(win, formName) };
_browse.updateLogger = function(win, flag){ logger.updateLogger(win, flag) };
_browse.processLogger = function(callback){ logger.processLogger(callback) };
_browse.addOperatorLogger = function(flowId, txnCode, txnName, reentry, win){ logger.addOperatorLogger(flowId, txnCode, txnName, reentry, win) };
_browse.openLoggerWindow = function(){ logger.openLoggerWindow() };

// ���ҳ���Ƿ������
_browse.addSubmitPage = function(win){ return histortList.addSubmitPage(win) };
_browse.checkPageReentry = function(win){ return histortList.checkPageReentry(win) };

// ���桢��ȡ ҳ������
_browse.savePageData = function(win){ _actionList.saveData(win) };
_browse.getPageData = function(txnCode){ return _actionList.getData(txnCode) };

//-->
