<!--

// ����ɹ��ļ�¼������ֻ��deleteRecord��addRecord��updateRecord��Ч��ͨ����Щ�����������޸ļ�¼���ٴβ�ѯ��¼������
// ��ǰѡ�еļ�¼��Ϣ

// ȡ���������
function page_getSavedActionData( win )
{
	if( win == null ){
		win = window;
	}
	
	// ȡ���������
	var txnCode = null;
	if( typeof(win) == 'string' ){
		txnCode = win;
	}
	else{
		if( win.currentFormName != null ){
			var fm = win.document.forms[win.currentFormName];
			if( fm != null ){
				txnCode = fm.action;
			}
		}
	}
	
	if( txnCode == null ){
		return null;
	}
	
	// ��������
	return _browse.getPageData( txnCode );
}

// ���õ�ǰ������GRID����
function page_setGridName( win, gridName )
{
	// ��������
	var savedData = page_getSavedActionData( win );
	if( savedData == null ){
		return;
	}
	
	// �ɹ��ļ�¼����
	savedData.gridName = gridName;
}

// ���õ�ǰ�����ĺ�������
function page_setFuncName( win, funcName )
{
	// ��������
	var savedData = page_getSavedActionData( win );
	if( savedData == null ){
		return;
	}
	
	// �ɹ��ļ�¼����
	savedData.funcName = funcName;
}

// ���ô���ɹ��ļ�¼����
function page_setSuccessNumber( win, num )
{
	// ��������
	var savedData = page_getSavedActionData( win );
	if( savedData == null ){
		return;
	}
	
	// �ɹ��ļ�¼����
	savedData.successNumber = num;
}

// ����ѡ�еļ�¼��Ϣ
function page_setSelectedRecord( win, gridName, selectedRecord )
{
	// ��������
	var savedData = page_getSavedActionData( win );
	if( savedData == null ){
		return;
	}
	
	// ����ѡ�еļ�¼��Ϣ
	savedData.setSelectedRecord( gridName, selectedRecord );
}

// ˢ��ҳ��ʱָ���Ĳ���
function page_setSubmitData( win, xurl )
{
	if( win == null ){
		win = window;
	}
	
	// ȡ���������
	var txnCode = xurl;
	if( txnCode == null && currentFormName != null ){
		var fm = win.document.forms[currentFormName];
		if( fm != null ){
			txnCode = fm.action;
		}
	}
	
	if( txnCode == null ){
		return xurl;
	}
	
	// ��������
	var savedData = _browse.getPageData( txnCode );
	if( savedData == null ){
		return xurl;
	}
	
	return savedData.loadData( xurl, win );
}


/* �򿪴��ڵķ�ʽ:
	�����ģʽ���ڣ�type=modal
	��������������У�type=window
	������´򿪵Ĵ��ڣ�type=new-window
	�����iframe��type=subframe
*/

// curr_page_varList = ��ǰҳ��ı�������
// dest_page_varList = Ŀ��ҳ��ı�������
function pageDefine( xurl, caption, type, width, height, curr_page_varList, dest_page_varList )
{
	this.caption = caption;
	
	if( type == null || type == '' ){
		this.type = "window";
	}
	else if( this.type == "windows" || this.type == "location" ){
		this.type = "window";
	}
	else{
		this.type = type;
	}
	
	if( width == null ){
		this.width = 500;
	}
	else if( width == 'full-screen' ){
		this.width = null;
	}
	else if( screen.availWidth - 10 < width ){
		this.width = screen.availWidth - 10;
	}
	else{
		this.width = width;
	}
	
	if( height == null ){
		this.height = 350;
	}
	else if( height == 'full-screen' ){
		this.height = null;
	}
	else if( screen.availHeight-20 < height ){
		this.height = screen.availHeight-20;
	}
	else{
		this.height = height;
	}
	
	// ����ɹ��󵼺���ҳ��
	this.nextPage = null;
	
	// ��ǰҳ���URL��ȱʡΪ_browse.pageName
	this._pageUrl = null;
	
	// ��Ҫ���ݵĲ���
	this.args = null;
	
	// ��������
	this.srcElement = null;
	if( window.event != null ){
		this.srcElement = window.event.srcElement;
	}
	
	// �ж��Ƿ���JSPҳ�� �� ҳ��ĺ�׺
	this.isJSP = false;
	this.pageType = null;
	
	var	pn = xurl;
	var	iptr = pn.indexOf('?');
	if( iptr > 0 ){
		pn = pn.substring( 0, iptr );
	}
	
	iptr = pn.lastIndexOf('.');
	if( iptr > 0 ){
		pn = pn.substring( iptr + 1 );
	}
	else{
		pn = '';
	}
	
	this.pageType = pn.toLowerCase();
	if( this.pageType == 'jsp' ){
		this.isJSP = true;
	}
	
	// ����·��
	if( xurl.indexOf('http:') != 0 && xurl.indexOf('ftp:') != 0 && xurl.indexOf('/') != 0 ){
		if( this.isJSP ){
			this.xurl = menuPath + xurl;
		}
		else{
			this.xurl = '/' + xurl;
		}
	}
	else{
		this.xurl = xurl;
	}
	
	// ��ˮ��
	this.flowId = null;
	this.prepareFlowId = page_prepareFlowId;
	
	// ȡ���������İ�ť
	this.getSrcElement = page_getSrcElement;
	
	// ����
	this.addParameter = page_addParameter;
	this.addValue = page_addValue;
	this.addArgs = page_addArgs;
	this.setNextPage = page_setNextPage;
	this.setForwardName = page_setNextPage;
	this.getPageAddr = page_getPageAddr;
	this.goPage = page_goPage;
	this.printPage = page_printPage;
	this.deleteRecord = page_deleteRecord;
	this.callService = page_callService;
	this.updateRecord = page_updateRecord;
	this.addRecord = page_addRecord;
	this.viewRecord = page_goPage;
	this.downFile = page_downFile;
	this.callAjaxService = page_callAjaxService;
	
	// ���Ӳ���
	this.add_param = null;
	this.dest_varList = new Array();
	this.curr_varList = new Array();
	this.addParameter( curr_page_varList, dest_page_varList );
}

// ���ӱ���
function page_addParameter( curr_page_varList, dest_page_varList )
{
	if( curr_page_varList == null || curr_page_varList == '' ){
		return;
	}
	
	if( dest_page_varList == null || dest_page_varList == '' ){
		return;
	}
	
 	// ��[,]�滻��[;]
 	curr_page_varList = curr_page_varList.replace(/,/g, ";");
 	dest_page_varList = dest_page_varList.replace(/,/g, ";");
	
	// ����
	var	cv = curr_page_varList.split( ';' );
	var	dv = dest_page_varList.split( ';' );
	if( cv.length != dv.length ){
		alert( '����ı���������һ��[' + curr_page_varList + '][' + dest_page_varList + ']' );
		return;
	}
	
	// ���Ӳ���
	var	num = this.curr_varList.length;
	for( ii=0; ii<cv.length; ii++ ){
		cv[ii] = trimSpace( cv[ii], 0 );
		dv[ii] = trimSpace( dv[ii], 0 );
		if( cv[ii] == "" || dv[ii] == "" ){
			continue;
		}
		
		this.curr_varList[num] = cv[ii];
		this.dest_varList[num] = dv[ii];
		num = num + 1;
	}
}

// ����ҳ����������
function page_addValue( value, name )
{
	if( name == null || name == '' ){
		return;
	}
	
	if( value == null ){
		name = name + '=';
	}
	else{
		value = '' + value;
		value = value.replace(/[\']/g, '"' );	// '"
		
		// �滻 [&] ==> [%26]��[#] ==> [%23]
		value = value.replaceAll( '&', '%26' );
		value = value.replaceAll( '#', '%23' );
		value = value.replaceAll( '"', '%22' );
 		value = value.replace( /[=]/g, '%3D' );
    	
    	// �Ƿ�ɾ���ո�
    	if( _keepFieldSpace == false ){
    		value = trimSpace( value, 0 );
    	}
    	
		name = name + '=' + value;
	}
	
	if( this.add_param == null || this.add_param == '' ){
		this.add_param = name;
	}
	else{
		this.add_param = this.add_param + '&' + name;
	}
}

// ���õ�����Ϣ��������ҳ���ļ���Ҳ�����ǵ�������
function page_setNextPage( nextPage )
{
	this.nextPage = nextPage;
}

// �򿪴���ʱ���ݵĲ���
function page_addArgs( value )
{
	if( this.args == null ){
		this.args = new Array();
	}
	
	this.args[this.args.length] = value;
}


// ȡҳ���ַ
function page_getPageAddr( param, win )
{
 	// ��֯�������
 	var	ii;
 	var	jj;
 	var	ptr;
 	var	name;
 	var	input_parameter = "";
	
 	// ȡ����
 	var	fname;
 	var	value = new Array();
 	var	frameName = new Array();
 	var	frameNumber = new Array();
 	
 	for( ii=0; ii<this.curr_varList.length; ii++ ){
 		name = this.curr_varList[ii];
 		
 		// ȡ����������ǿ�ֵ������һ�������ݣ����ڴ���
 		value[ii] = getFormFieldValues( name );
 		if( value[ii].length == 0 ){
 			value[ii][0] = "";
 		}
 		else{
 			// �滻������
 			for( var vi=0; vi < value[ii].length; vi++ ){
 				value[ii][vi] = '' + value[ii][vi];
 				value[ii][vi] = value[ii][vi].replace(/[\']/g, '"' );	// '"
 				
 				// �滻 [&] ==> [%26]��[#] ==> [%23]
 				value[ii][vi] = value[ii][vi].replaceAll( '&', '%26' );
 				value[ii][vi] = value[ii][vi].replaceAll( '#', '%23' );
				value[ii][vi] = value[ii][vi].replaceAll( '"', '%22' );
 				value[ii][vi] = value[ii][vi].replace( /[=]/g, '%3D' );
    	
		    	// �Ƿ�ɾ���ո�
		    	if( _keepFieldSpace == false ){
		    		value[ii][vi] = trimSpace( value[ii][vi], 0 );
		    	}
 			}
 		}
 		
 		// ȡ���ļ�¼����
 		name = this.dest_varList[ii];
 		ptr = name.indexOf(':');
 		if( ptr > 0 ){
 			fname = name.substring(0, ptr);
 			for( jj=0; jj<frameNumber.length; jj++ ){
 				if( frameName[jj] == fname ){
 					break;
 				}
 			}
 			
 			// ����block������¼�����������һ��ʱ����
 			if( jj == frameNumber.length ){
 				frameName[jj] = fname;
 				frameNumber[jj] = value[ii].length;
 			}
 			else{
 				if( frameNumber[jj] == 1 ){
 					frameNumber[jj] = value[ii].length;
 				}
 				else if( value[ii].length != 1 ){
	 				if( frameNumber[jj] != value[ii].length ){
	 					alert( '����Ĳ���ֵ������ƥ��' );
	 					return null;
	 				}
	 			}
 			}
 		}
 	}
 	
 	// ���������������
 	var	num;
 	for( ii=0; ii<this.curr_varList.length; ii++ ){
 		num = 1;
 		name = this.dest_varList[ii];
 		ptr = name.indexOf(':');
 		if( ptr > 0 ){
 			fname = name.substring(0, ptr);
			for( jj=0; jj<frameName.length; jj++ ){
				if( frameName[jj] == fname ){
					num = frameNumber[jj];
					break;
				}
			}
 		}
		
		if( num == 1 ){
			input_parameter = input_parameter + "&" + name + "=" + value[ii][0];
		}
		else{
			if( value[ii].length == 1 ){
				for( jj=0; jj<num; jj++ ){
					input_parameter = input_parameter + "&" + name + "=" + value[ii][0];
				}
			}
			else{
				for( jj=0; jj<num; jj++ ){
					input_parameter = input_parameter + "&" + name + "=" + value[ii][jj];
				}
			}
		}
	}
	
	// ��ť����
	var objs = document.getElementsByName('inner-flag:menu-name');
	if( objs != null && objs.length > 0 ){
		var menuName = objs[0].value;
		if( menuName != null && menuName != '' ){
			input_parameter = input_parameter + "&inner-flag:menu-name=" + menuName;
		}
	}
	
	// Ԥ��ҳ���־
	objs = document.getElementsByName('inner-flag:preview-flag');
	if( objs != null && objs.length > 0 ){
		var previewFlag = objs[0].value;
		if( previewFlag != null && previewFlag != '' ){
			input_parameter = input_parameter + "&inner-flag:preview-flag=" + previewFlag;
		}
	}
	
	// freeze-next-page��nextPage��CallService��deleteRecord���в�ͬ����;������ע�����óɹ���Ĳ���
	if( this.nextPage != null && this.nextPage != '' ){
		if( param == null || param.indexOf(this.nextPage) < 0 ){
			input_parameter = input_parameter + '&freeze-next-page=' + this.nextPage;
		}
	}
 	
 	// �������
 	
 	if( param != null && param != "" ){
 		input_parameter = input_parameter + "&" + param;
 	}
 	
 	// ͨ��addValue���ӵĲ���
 	if( this.add_param != null && this.add_param != "" ){
 		input_parameter = input_parameter + "&" + this.add_param
 	}
 	
 	// ȡ����
 	if( this.type == "modal" ){
		var	dd = new Date();
		input_parameter = "inner-flag:open-type=modal&inner-flag:freeze-stamp=" + dd.getTime() + input_parameter;
 	}
 	else{
	 	if( win == null ){
	 		win = window;
	 	}
	 	
	 	if( this.type == "window" ){
 			// �͵�ǰ�򿪵Ĵ�����ͬ�ķ�ʽ:��������������У�type=window��������´򿪵Ĵ��ڣ�type=new-window�������iframe��type=subframe
 			input_parameter = "inner-flag:open-type=" + win.openWindowType + input_parameter;
		}
		else if( this.type == "frame" || this.type == "inner" ){
	 		// ��iframe�д�
 			input_parameter = "inner-flag:open-type=subframe" + input_parameter;
	 	}
 		else{
 			// ���´���
 			input_parameter = "inner-flag:open-type=new-window" + input_parameter;
 		}
 	}
 	
 	// ������ַ
 	var	xurl = this.xurl;
 	if( xurl.indexOf(rootPath) != 0 ){
 		xurl = rootPath + xurl;
 	}
 	
 	// ȡ��ַ
	if( xurl.indexOf('?') > 0 ){
		xurl = xurl + "&" + input_parameter;
	}
	else{
		xurl = xurl + "?" + input_parameter;
	}
	
	return _browse.resolveURL( xurl );
}


// ҳ����ת
// @return 0  ==> �����ɹ�����ˢ����Ļ
// @return 1  ==> ����ɹ���������Ļ
// @return 2  ==> ����ɹ����е��÷������Ƿ�ˢ��
function page_goPage( param, win )
{
 	if( win == null ){
 		win = window;
 	}
 	
 	// ��֯�������
 	var	addr = this.getPageAddr(param);
 	
 	
 	// �����ˮ��
 	addr = this.prepareFlowId( addr );
 	
 	// ��ǰҳ����
 	try{
 		var x = (this._pageUrl == null) ? win._browse.pageName : this._pageUrl;
 		addr = addr + '&inner-flag:pre-page=' + x;
 	}
 	catch( e ){ }
 	
	var fPage = _getHrefParameter( win.location.href, "freeze-next-page" );
	if( fPage != null ) addr = addr + '?freeze-next-page=' + fPage;
	
	// �򿪴���
 	if( this.type == "modal" ){
 		if( this.width == null ) this.width = 800;
 		if( this.height == null ) this.height = 600;
		var showx = parseInt((screen.availWidth-this.width)/2);
	 	var showy = parseInt((screen.availHeight-10-this.height)/2);
	 	
		ret = win.showModalDialog( addr, [self, this.caption], "dialogLeft:"+showx+";dialogTop:"+showy+";dialogWidth:"+this.width+"px;dialogHeight:"+this.height+"px; resizable:yes;status:no;help:no" );
		_hideProcessHintWindow();
		clickFlag = 0;
		//console.log(ret + ' ==> ' + typeof ret);
		if( ret == 1 ){
			if( currentFormName != null ){
				var f = win.document.forms[currentFormName];
				if( f.reentry != 'false' ){
					page_setSubmitData( win );
					f.submit();
				}
			}
			return 1;
		}
		else if( ret == 2 ){
			// ִ�гɹ�������no-update
			return 1;
		}else{
			if(ret && ret!=''){
			   window.location="/"+ret;
			}
		}
	}else if( this.type == "frame" ){
 		if( this.width == null ) this.width = 500;
 		if( this.height == null ) this.height = 350;
		openInnerWindow( addr, this.width, this.height, this.srcElement, false, this.args );
		clickFlag = 0;
	}
	else if( this.type == "inner" ){
 		if( this.width == null ) this.width = 500;
 		if( this.height == null ) this.height = 350;
		openInnerWindow( addr, this.width, this.height, this.srcElement, true, this.args );
		clickFlag = 0;
	}
	else if( this.type == "window" ){
		// �ж�win�Ƿ�[iframe]
		if( win.tagName != null && win.tagName.toLowerCase() == 'iframe' ){
			addr = _setUrlOpenType( addr, 'subframe' );
			setTimeout( function(){win.src = addr;}, 1 );
		}
		else{
			// ���ܲ���ȡҳ���ļ��ı���������ǲ�ͬ���������ļ�
			try{
				var fe = win.frameElement.tagName;
				var type = win.openWindowType;
			}
			catch( e ){
				var fe = null;
				var type = 'window';
			}
			
			if( fe != null && fe.toLowerCase() == 'iframe' ){
				addr = _setUrlOpenType( addr, 'subframe' );
			}
			else{
				addr = _setUrlOpenType( addr, type );
			}
			
			setTimeout( function(){win.location = addr;}, 1 );
		}
		
		// alert( addr );
		return 1;
	}
	else{
		// ���ڵ�����
		if( this.width == null || this.height == null ){
			var x = screen.availWidth;
			var y = screen.availHeight;
			var features = "resizable,scrollbars,left=0,top=0,width=" + x + ",height=" + y;
			var w = window.open( addr, this.type, features );
			w.moveTo( 0,0 ); 
			w.resizeTo( x, y );
		}
		else{
			var showx = parseInt((screen.availWidth-this.width)/2);
		 	var showy = parseInt((screen.availHeight-10-this.height)/2);
			var features = "resizable,scrollbars,left=" + showx + ",top=" + showy + ",width=" + this.width + ",height=" + this.height;
			var w = window.open( addr, this.type, features );
		}
	 	
 		w.focus();
 		clickFlag = 0;
 		return 1;
	}
	
	return 0;
}

// ��grid�����Ӽ�¼
function page_addRecord( param, win )
{
	// ȡgrid����
	element = this.getSrcElement();
	if( element != null ){
		var grid = getGridDefineByElement( element );
		if( grid != null ){
			page_setGridName( window, grid.gridName );
			page_setFuncName( window, "insert" );
		}
	}
	
	// ҳ����ת
	return this.goPage( param, win );
}

// ��grid���޸ļ�¼
function page_updateRecord( param, win )
{
	// ȡgrid����
	element = this.getSrcElement();
	if( element != null ){
		var grid = getGridDefineByElement( element );
		if( grid != null ){
			page_setGridName( window, grid.gridName );
			page_setFuncName( window, "update" );
		}
	}
	
	// ҳ����ת
	return this.goPage( param, win );
}




// �����ļ�
function page_downFile( param )
{
	// ����
	if( param == null || param == '' ){
		param = 'inner-flag:failure-hint=�����ļ�ʱ����';
	}
	else{
		param = param + '&inner-flag:failure-hint=�����ļ�ʱ����';
	}
	
 	// ��֯�������
	this.type = 'window';
 	var	addr = this.getPageAddr(param);
	
	// �ָ����а�ť
	clickFlag = 0;
	checkAllMenuItem();
	
	// �����ļ�
	openInnerWindow( addr, 1, 1 );
}


// ����ҳ��
function goWindowBack( nextPage, win )
{
	// �򿪵��Ӵ��ڣ�ֱ�ӹر�
	if( openWindowType == 'new-window' ){
		window.close();
		return;
	}
	
	if( win == null ){
		win = window;
	}
	
	// ȱʡ��������һ��ҳ��
	if( nextPage == null ){
		nextPage = _prePageName;
	}
	
	// ȡ�������ݱ�־
	var addr = _addHrefParameter( nextPage, 'inner-flag:back-flag', 'true' );
 	// �򿪴��ڵ�����
	if( win.openWindowType == 'subframe' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'subframe' );
	}
	else{
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'window' );
	}
	
	// ����ȫ·��
	addr = _browse.resolveURL( addr );
	
 	// ת������
 	addr = page_setSubmitData( win, addr );
	win.location = addr;
}

// �ر�ģʽ����
function _closeModalWindow( refreshFlag, successNumber, nextPage )
{
	if( successNumber > 0 ){
		page_setSuccessNumber( window.dialogArguments[0], successNumber );
	}
	
	if(nextPage == null || nextPage == '') nextPage=null;
	if( refreshFlag == false ){
		if( successNumber > 0 ){
			if(nextPage == null) window.returnValue = 2;
			else window.returnValue = 1;
		}
		else window.returnValue = 0;
	}
	else window.returnValue = 1;
	
	// ����ˢ��ҳ��
	if( 1 == window.returnValue && nextPage != null ){
		var pwin = window.dialogArguments[0];
		if( pwin.currentFormName == null ){
			var fobj = pwin.document.createElement( "<form name='_tempForm'>" );
			fobj.method = "post";
			fobj.action = nextPage;
			pwin._browse.appendElement( pwin.document.body, fobj );
			pwin.currentFormName = '_tempForm';
		}
		else{
			var fobj = pwin.document.forms[pwin.currentFormName];
		}
		
		// ���[freeze-next-page]
		var fPage = _getHrefParameter( pwin.location.href, "freeze-next-page" );
		if( fPage != null ){
			var xurl = fobj.action;
			var iptr = xurl.indexOf( '?' );
			if( iptr > 0 ) xurl = xurl.substring(0, iptr);
			
			if( pwin._browse.contextPath + pwin._browse.pageName == xurl ){
				fobj.action = _addHrefParameter( fobj.action, 'freeze-next-page', fPage );
			}
		}
		
		if( pwin._prePageName != '' ) fobj.action = _addHrefParameter( fobj.action, 'inner-flag:pre-page', pwin._prePageName );
	}
	
	window.close();
}

// ����ҳ��
function goBack( nextPage, win )
{
	if( openWindowType == 'modal' ){
		if( nextPage == null ) nextPage = _prePageName;
		_closeModalWindow( false, getSuccessNumber(), nextPage );
	}
	else{
		goWindowBack( nextPage, win );
	}
}

function goBackNoUpdate( nextPage, win )
{
	if( openWindowType == 'modal' ){
		_closeModalWindow( false, getSuccessNumber(), null );
	}
	else{
		goWindowBack( nextPage, win );
	}
}

function goBackWithUpdate( nextPage, win )
{
	if( openWindowType == 'modal' ){
		if( nextPage == null ) nextPage = _prePageName;
		_closeModalWindow( true, getSuccessNumber(), nextPage );
	}
	else{
		goWindowBack( nextPage, win );
	}
}



// ���������ֱ���˳�ҳ�棬saveAndExit�Ⱥ������ú�Ĵ���
function _auto_goBack( nextPage, successNumber )
{
	// �޸���־��Ϣ
	_browse.updateLogger( window, false );
	
	if( errorCode == '000000' ){
		successNumber = 1 + successNumber;
	}
	
	// ��ͬ��ʽ�򿪵Ĵ��ڣ���ͬ���˳���ʽ��
	// modal=close��
	// new-window=close��
	// window=window.location=nextPage��
	// subframe=window.location=nextPage
	if( openWindowType == 'modal' ){
		_closeModalWindow( false, successNumber, nextPage );
	}
	else if( openWindowType == 'new-window' ){
		// �򿪵��Ӵ��ڣ�ֱ�ӹر�
		window.close();
	}
	else{
		if( nextPage != '' ){
 			// �򿪴��ڵ�����
			if( openWindowType == 'subframe' ){
				nextPage = _addHrefParameter( nextPage, 'inner-flag:open-type', 'subframe' );
			}
			else{
				nextPage = _addHrefParameter( nextPage, 'inner-flag:open-type', 'window' );
			}
			
			// ���óɹ��ļ�¼����
			page_setSuccessNumber( nextPage, successNumber );
 			nextPage = page_setSubmitData( window, nextPage );
			window.location = _browse.resolveURL( nextPage );
		}
	}
}


// ��ӡ��ǰҳ��
// flag = 0 ��ǰҳ��ļ�¼
// flag = 1 ѡ�еļ�¼
// flag = 2 ���в�ѯ���ļ�¼
// flag = 3 ���м�¼�����ָ���ѯ����
function page_printPage( flag, win )
{
	if( currentGrid == null ){
		return;
	}
	
	// ȱʡ�ǵ�ǰҳ��ļ�¼
	if( flag == null ){
		flag = 0;
	}
	
	var	pageType;
	if( flag == 3 ){
		pageType = 'attribute-node:' + currentGrid.gridName + '_page-row=-1&attribute-node:' + currentGrid.gridName + '_start-row=0';
	}
	else{
		pageType = 'inner-flag:back-flag=true&attribute-node:print-node=' + currentGrid.gridName + '&attribute-node:print-type=' + flag;
	}
	
	var	param = pageType + '&freeze-next-page=print';
	this.goPage( param, win );
}


// ɾ����¼
function page_deleteRecord( hint, param )
{
	clickFlag = 0;
	if( hint != null && hint != "" ){
		var	rc = window.confirm( hint );
		if( rc == false ){
			return 0;
		}
	}
	
	// ȡ�����ɹ�ִ�к�Ĵ���ʽ
	var nextPage = this.nextPage;
	if( nextPage == null || nextPage == '' ){
		if( currentFormName == null || currentFormName == '' ) return 0;
		var fobj = document.forms[currentFormName];
		if( fobj.reentry != 'true' ) return 0;
		
		nextPage = fobj.action;
		if( nextPage.indexOf(_browse.contextPath) == 0 ){
			nextPage = nextPage.substring(_browse.contextPath.length);
		}
	}
	
	var fPage = _getHrefParameter( window.location.href, "freeze-next-page" );
	if( fPage != null && _browse.pageName == nextPage ){
		nextPage = _addHrefParameter( nextPage, 'freeze-next-page', fPage );
	}
	
	// ȡgrid���ƣ�ɾ���ļ�¼����=��ǰѡ�еļ�¼����
	element = this.getSrcElement();
	if( element != null ){
		var grid = getGridDefineByElement( element );
		if( grid != null ){
			page_setGridName( window, grid.gridName );
			page_setFuncName( window, "delete" );
			page_setSuccessNumber( window, grid.getSelectedRowNumber() );
		}
	}
	
	// ȱʡ����
	this.type = 'window';
	if( this.caption == null || this.caption == '' ){
		this.caption = "ɾ����¼";
	}
	
	// ����
	if( param == null || param == '' ){
		param = "inner-flag:failure-hint=" + this.caption + "ʱ����";
	}
	else{
		param = param + "&inner-flag:failure-hint=" + this.caption + "ʱ����";
	}
	
 	// ��֯�������
 	if( nextPage != null && nextPage != "" ){
 		param = param + "&inner-flag:delete-success-page=" + nextPage
 	}
 	
 	// ȡURL��ַ
 	var	addr = this.getPageAddr(param);
 	
 	// �����ˮ��
 	addr = this.prepareFlowId( addr );
	
	// ɾ����¼
 	hint = '����' + this.caption + ' ... ...';
	_showProcessHintWindow( hint );
	openInnerWindow( addr, 1, 1 );
}


// ɾ����¼
function page_callService( hint, param )
{
	clickFlag = 0;
	
	// ȡ�����ɹ�ִ�к�Ĵ���ʽ
	var nextPage = this.nextPage;
	if( nextPage == null ){
		if( currentFormName == null || currentFormName == '' ){
			return 0;
		}
		
		nextPage = document.forms[currentFormName].action;
		if( nextPage.indexOf(rootPath) == 0 ){
			nextPage = nextPage.substring(rootPath.length);
		}
	}
	
	// ȱʡ����
	this.type = 'window';
	if( this.caption == null || this.caption == '' ){
		this.caption = "��������";
	}
	
	// ����
	if( param == null || param == '' ){
		param = "inner-flag:failure-hint=" + this.caption + "ʱ����";
	}
	else{
		param = param + "&inner-flag:failure-hint=" + this.caption + "ʱ����";
	}
	
 	// ��֯�������
 	if( nextPage != null && nextPage != "" ){
 		param = param + "&inner-flag:service-success-page=" + nextPage
 	}
 	
 	if( hint != null && hint != '' ){
	 	hint = '����' + hint + ' ... ...';
		_showProcessHintWindow( hint );
	}
	
	// ��������
	this.nextPage = "call-service";
 	var	addr = this.getPageAddr( param );
	openInnerWindow( addr, 1, 1 );
}



// ����AJAX����
var __xml_Http = new Array();

/**
callback �ص�������������ɺ󣬻��Զ���������������к���
param URL�Ĳ���
*/
function page_callAjaxService( callback, param )
{
	// ʹ��ť��Ч
	clickFlag = 0;
	
	// ����ĵ�ַ
	var xurl = this.getPageAddr( param );
	
	// �򿪴��ڵ����ͣ�new-window
	xurl = _setUrlOpenType( xurl, 'new-window' );
	
	// ���⻺��
	var	dd = new Date();
	var stamp = dd.getTime();
	xurl = xurl + "&inner-flag:freeze-stamp=" + stamp;
	
	// �ص�����
	if( callback == null ){
		callback = '';
	}
	
	// �������ĺ���:����XML��Ӧ���� he ҳ����Ӧ���ĵĴ���һ��
	if( this.pageType == 'ajax' ){
		var procFunc = function(){ _processAjaxResponse(stamp, callback); };
	}
	else{
		var procFunc = function(){ _processAjaxHTMLResponse(stamp, callback); };
	}
	
	// branch for native XMLHttpRequest object
	var _ajax = null;
	if( window.XMLHttpRequest ){
    	_ajax = new XMLHttpRequest();
    	_ajax.onreadystatechange = procFunc;
    	_ajax.open( "GET", xurl, true );
    	_ajax.send( null );
	} // branch for IE/Windows ActiveX version
	else if( window.ActiveXObject ){
    	_ajax = new ActiveXObject( "Microsoft.XMLHTTP" );
   		if( _ajax ){
        	_ajax.onreadystatechange = procFunc;
        	_ajax.open( "GET", xurl, true );
        	_ajax.send();
    	}
	}
	else{
		alert( '��֧��XML-HTTP' );
	}
	
	if( _ajax ){
		for( var ii=0; ii<=__xml_Http.length; ii++ ){
			if( __xml_Http[ii] == null ){
				__xml_Http[ii] = [stamp, _ajax];
				break;
			}
		}
	}
}


/* ��XML�����ȡ�������ݣ�����Ƕ��¼���ݣ�ֻ���ص�һ��ֵ
xDoc callAjaxService�ɹ��󷵻صĽ������callback����Ϊ����������
param XPATH·�����ͷ������˵�FORM����һ�£�
	��������/context/record/codetype {contextΪ�̶������ݣ�XML�ĸ��ǹ̶��ģ�record/codetype���������ƣ�����FORM�еľ���������Ϣȷ��}
		����: record:codetype {�������ƣ�����FORM�еľ���������Ϣȷ��}������ʱ���Զ�ת����[/context/record/codetype]
*/
function _getXmlNodeValue( xDoc, xpath )
{
	// ȡ·��
	xpath = xpath.replace( /[:]/g, '/' );
	if( xpath.indexOf('/') != 0 ){
		xpath = '/' + xpath;
	}
	
	if( xpath.indexOf('/context') != 0 ){
		xpath = '/context' + xpath;
	}
	
	// ȡֵ
	var val = "";
	var obj = xDoc.selectSingleNode( xpath );
	if( obj != null ){
		val = obj.text;
	}
	
	return val;
}

/* ��XML�����ȡ���Ե�ֵ������һ�����顣����Ƕ��¼���ݣ����ض��ֵ�����飻����ǵ���¼������ֻ����һ��ֵ������
xDoc��xpath������������һ��
*/
function _getXmlNodeValues( xDoc, xpath )
{
	// ȡ·��
	xpath = xpath.replace( /[:]/g, '/' );
	if( xpath.indexOf('/') != 0 ){
		xpath = '/' + xpath;
	}
	
	if( xpath.indexOf('/context') != 0 ){
		xpath = '/context' + xpath;
	}
	
	// ȡ�ڵ����ƺ��ֶ�����
	var ptr = xpath.lastIndexOf( '/' );
	var name = xpath.substring( ptr );
	xpath = xpath.substring( 0, ptr );
	
	// ���
	var val = new Array();
	
	// ȡ�ڵ�
	if( xpath == '/context' ){
		xpath = xpath + name;
		var items = xDoc.selectNodes( xpath );
		
		for( var ii=0; ii<items.length; ii++ ){
			var data = "";
			var itemName = xpath + '[' + ii + ']';
			var obj = xDoc.selectSingleNode( itemName );
			if( obj != null ){
				data = obj.text;
			}
			
			val[ii] = data;
		}
	}
	else{
		var items = xDoc.selectNodes( xpath );
		
		// ȡֵ
		for( var ii=0; ii<items.length; ii++ ){
			var data = "";
			var itemName = xpath + '[' + ii + ']' + name;
			var obj = xDoc.selectSingleNode( itemName );
			if( obj != null ){
				data = obj.text;
			}
			
			val[ii] = data;
		}
	}
	
	return val;
}


/* ��XML�����ȡ���Ե�ֵ������һ�����顣����Ƕ��¼���ݣ����ض��ֵ�����飻����ǵ���¼������ֻ����һ��ֵ������
xDoc��xpath������������һ��
nodeList ��¼���Ľڵ��б�
*/
function _getXmlRecordset( xDoc, xpath, nodeList )
{
	// ȡ·��
	xpath = xpath.replace( /[:]/g, '/' );
	if( xpath.indexOf('/') != 0 ){
		xpath = '/' + xpath;
	}
	
	if( xpath.indexOf('/context') != 0 ){
		xpath = '/context' + xpath;
	}
	
	// ���
	var result = new Array();
	
	// ȡ�ڵ�
	var items = xDoc.selectNodes( xpath );
	if( items != null ){
		for( var ii=0; ii<items.length; ii++ ){
			var val = new Array();
			
			// ȡ��¼
			for( var jj=0; jj<nodeList.length; jj++ ){
				var data = "";
				var obj = items[ii].selectSingleNode( nodeList[jj] );
				if( obj != null ){
					data = obj.text;
				}
				
				val[jj] = data;
			}
			
			result[ii] = val;
		}
	}
	
	return result;
}


// ������:XML���
function _processAjaxResponse( stamp, _procFunc ) 
{
	// ȡ�ص��ĺ���
	var index = 0;
	var _ajax = null;
	for( index=0; index<__xml_Http.length; index++ ){
		if( __xml_Http[index] != null && __xml_Http[index][0] == stamp ){
			_ajax = __xml_Http[index][1];
			break;
		}
	}
	
	if( _ajax == null ){
		return;
	}
	
	// �Ƿ��Ѿ����
	if( _ajax.readyState != 4 ){
		return false;
	}
	
	// ɾ��
	__xml_Http[index] = null;
	
	// �Ƿ�ɹ�
	if( _ajax.status != 200 ){
		alert( "ϵͳδ֪����:" + _ajax.status );
		_ajax.abort();
		return;
	}
	
	// �жϽ��
	var xData = _ajax.responseXML.xml;
	var xmlResults= new ActiveXObject( "MSXML2.DOMDocument.3.0" );
	xmlResults.loadXML( xData );
	
	// ȡ�������
	var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
	
	// ��������
	var errDesc = '';
	if( errCode != '000000' ){
		errDesc = _getXmlNodeValue( xmlResults, "/context/error-desc" );
		// alert( '�������[' + errCode + '] ==> ' + errDesc );
		// return;
	}
	
	// ����ص�����
	if( _procFunc != null && _procFunc != '' ){
		var ptr = _procFunc.indexOf( '(' );
		if( ptr > 0 ){
			_procFunc = _procFunc.substring( 0, ptr );
		}
		
		_procFunc = _procFunc + "(errCode,errDesc,xmlResults)";
		eval( _procFunc );
	}
} 

// ������:HTML���
function _processAjaxHTMLResponse( stamp, _procFunc )
{
	// ȡ�ص��ĺ���
	var index = 0;
	var _ajax = null;
	for( index=0; index<__xml_Http.length; index++ ){
		if( __xml_Http[index] != null && __xml_Http[index][0] == stamp ){
			_ajax = __xml_Http[index][1];
			break;
		}
	}
	
	if( _ajax == null ){
		return;
	}
	
	// �Ƿ��Ѿ����
	if( _ajax.readyState != 4 ){
		return false;
	}
	
	// ɾ��
	__xml_Http[index] = null;
	
	// �Ƿ�ɹ�
	if( _ajax.status != 200 ){
		alert( "ϵͳδ֪����:" + _ajax.status );
		_ajax.abort();
		return;
	}
	
	// ȡ�������
	var errCode = '000000';
	
	// ��������
	var errDesc = '';
	
	// HTML���
	var htmlResults= _ajax.responseText;
	
	// ����ص�����
	if( _procFunc != null && _procFunc != '' ){
		var ptr = _procFunc.indexOf( '(' );
		if( ptr > 0 ){
			_procFunc = _procFunc.substring( 0, ptr );
		}
		
		_procFunc = _procFunc + "(errCode,errDesc,htmlResults)";
		eval( _procFunc );
	}
}



// ��ȡ���������İ�ť
function page_getSrcElement()
{
	if( this.srcElement != null ){
		return this.srcElement;
	}
	
	if( window.event != null && window.event.srcElement != null ){
		return window.event.srcElement;
	}
}


// ��GET������������ˮ��
function page_prepareFlowId( addr )
{
	if( this.pageType != 'do' ){
		return addr;
	}
	
	// ������ˮ��
	var ii = addr.indexOf( 'inner-flag:flowno=' );
	if( ii > 0 ){
		var temp = addr.substring( ii + 18 );
		ii = temp.indexOf( '&' );
		if( ii > 0 ){
			temp = temp.substring( 0, ii );
		}
		
		this.flowId = temp;
	}
	else{
		var	dd = new Date();
		var stamp = '' + dd.getTime();
		var ptr = stamp.length - 10;
		this.flowId = stamp.substring( ptr );
		
		if( addr.indexOf('?') > 0 ){
			addr += '&inner-flag:flowno=' + this.flowId;
		}
		else{
			addr += '?inner-flag:flowno=' + this.flowId;
		}
	}
	
	// ���Ӳ�����־
	_browse.addOperatorLogger(this.flowId, this.xurl, this.caption, true, window);
	
	return addr;
}



// �������Ʋ���Iframe����֪��window.frames(...)Ϊʲô�ᱨ��,�����ǻ�û��ʼ����ʱ��͵���
function getIframeByName( frameName )
{
	for( var ii=0; ii<window.frames.length; ii++ ){
		if( window.frames[ii].name == frameName ){
			return window.frames[ii];
		}
	}
	
	return null;
}



// ʵʱ�ӷ�������������
// ��һ��ҳ���ǽ�����
document.write("<div id=innerWindow style='position: absolute; z-index: 9999; width: 100px; height: 100px; display: none; background-color: #C0C0C0'></div>");
//document.write("<iframe name=innerWindowIframe scrolling=no frameborder=0 style='width:100%; height:100%' src='" + _browse.contextPath + "/script/progress.html'></iframe></div>");

function InnerWindowArgs()
{
	this.baseCtrl = null;
	this.args = null;
}

var _innerArgs = new InnerWindowArgs();

// �򿪴���
// obj ���ڶ�λ��Ķ���
// flag = true �� null ʱ��ʧȥ����ʱ�Զ��رգ� ����ʱ���رմ���
function openInnerWindow( xurl, width, height, obj, flag, args )
{
	// ���ò���
	_innerArgs.args = args;
	
	// ��ʾ����������
	var bLayer = document.all.innerWindow;
	if( width != null && height != null ){
		bLayer.innerHTML = "";
		var o = bLayer.style;
		o.display = "";
		setInnerWindowPosition( obj, width, height );
		
		//����뿪�Զ�����
		if( flag == null || flag == true ){
			document.onmousedown = hiddenInnerWindow;
		}
	}
	
	// ���ڵĴ򿪷�ʽ
	if( xurl.indexOf('?') > 0 ){
		xurl = xurl + '&inner-flag:open-type=subframe';
	}
	else{
		xurl = xurl + '?inner-flag:open-type=subframe';
	}
	
	// �򿪴���
	if( xurl.charAt(0) == '/' ){
		if( xurl.indexOf(_browse.contextPath + '/') != 0 ){
			xurl = _browse.contextPath + xurl;
		}
	}
	
	setTimeout( function(){ __delay_openInnerWindow(xurl) }, 1 );
}

// �ӳٴ򿪴���
// openWindowType=ȫ�ֱ�������freeze:html�����ɣ�˵�������ַ�ʽ�򿪵Ĵ���
function __delay_openInnerWindow( xurl )
{
	var	fsrc = "";
	if( openWindowType == 'modal' ){
		fsrc = " src='" + xurl + "'";
	}
	
	// ���
	var bLayer = document.all.innerWindow;
	bLayer.innerHTML = "<iframe name=innerWindowIframe" + fsrc + " scrolling=no frameborder=0 width='100%' height='100%'>";
	var	bFrame = window.frames("innerWindowIframe");
	
	if( openWindowType != 'modal' ){
		// ��ʾ��Ϣ
		if( xurl.length < 2000 ){
			var	result = '<html><head></head><body style="margin:0;padding:0;"><table border="0" width="100%" height="100%" bgcolor="#99CCFF">' +
					'<tr valign=middle><td align=center>���ڼ������� ... ...</td></tr></table></body>' +
					'<script language="javascript">window.location="' +
					xurl +
					'";</script></html>';
		}
		else{
			var ptr = xurl.indexOf( '?' );
			var action = xurl.substring( 0, ptr );
			var parameter = xurl.substring( ptr + 1 );
			
			var	result = '<html><head>' +
					'<meta http-equiv="Content-Type" content="text/html; charset=GBK">' +
					'</head><body style="margin:0;padding:0;"><table border="0" width="100%" height="100%" bgcolor="#99CCFF">' +
					'<tr valign=middle><td align=center>���ڼ������� ... ...</td></tr></table>';
			result += '<form name="xx" id="xx" method="post" action="' + action + '">';
			
			// ��������
			var startPos = 0;
			var stopPos = 0;
			var item;
			while( startPos >= 0 ){
				stopPos = parameter.indexOf( '&', startPos );
				if( stopPos > 0 ){
					item = parameter.substring( startPos, stopPos );
					startPos = stopPos + 1;
				}
				else{
					item = parameter.substring( startPos );
					startPos = -1;
				}
				
				ptr = item.indexOf( '=' );
				if( ptr < 0 ){
					alert( '��������̫�󣬴����ɹ�' );
					return;
				}
				
				var name = item.substring( 0, ptr );
				var value = item.substring( ptr+1 );
				value = _decodeUrlValue( value );
				
				result += '<input type="hidden" name="' + name + '" value="' + value + '">';
			}
			
			result += '</form></body><script language="javascript">document.forms["xx"].submit();</script></html>';
		}
		bFrame.document.write( result );
		try{
			bFrame.document.charset  = 'GBK';
		}catch(e){
		}
	}
}

// drag/drop�¼�
var __startDragX = 0;
var __startDragY = 0;
var __startLayerX = 0;
var __startLayerY = 0;
function _innerWindowDragEvent( )
{
	var	bLayer = document.all.innerWindow;
	
	if( event.type == 'dragstart' ){
		__startDragX = event.screenX;
		__startDragY = event.screenY;
		__startLayerX = bLayer.offsetLeft;
		__startLayerY = bLayer.offsetTop;
		document.body.ondragover = function(){event.returnValue=false;};
		document.body.ondragenter = function(){event.returnValue=false;};
	}
	else if( event.type == 'drag' ){
		var x = event.screenX;
		var y = event.screenY;
		
		var l = __startLayerX + (x - __startDragX);
		var w = bLayer.clientWidth;
		var bw = document.body.clientWidth;
		if( l < 5 ) l = 5;
		else if( l+w > bw ) l = bw - w;
		
		var t = __startLayerY + (y - __startDragY);
		var h = bLayer.clientHeight;
		var bh = document.body.clientHeight;
		if( t < 5 ) t = 5;
		else if( t+h > bh ) t = bh - h;
		
		var o = bLayer.style;
		o.left = l;
		o.top = t;
	}
}

// ��valueת����ԭ��������: %26 ==> &; %23 ==> #; %3D ==> =
function _decodeUrlValue( value )
{
	var formatValue = '';
	var startPos = 0;
	var stopPos = 0;
	while( startPos >= 0 ){
		stopPos = value.indexOf( '%', startPos );
		if( stopPos == startPos ){
			var code = value.substring( stopPos+1, stopPos+3 );
			code = parseInt( code, 16 );
			formatValue += String.fromCharCode( code );
			startPos += 3;
		}
		else if( stopPos > 0 ){
			formatValue += value.substring( startPos, stopPos );
			var code = value.substring( stopPos+1, stopPos+3 );
			code = parseInt( code, 16 );
			formatValue += String.fromCharCode( code );
			startPos = stopPos + 3;
		}
		else{
			if( startPos == 0 ){
				formatValue = value;
			}
			else{
				formatValue += value.substring( startPos );
			}
			
			startPos = -1;
		}
	}
	
	return formatValue;
}

// ��ʾ��Ϣ
function _showInnerAlert( title, msg, width, height, _onclick )
{
	// ���
	var messageLayer = getObjectById("innerWindow");
	var o = messageLayer.style;
	
	var align = 'center';
	if( title.charAt(0) == '@' ){
		align = 'left';
		title = title.substring( 1 );
	}
    
    title = '<div style="WIDTH: 100%; HEIGHT: 100%; POSITION: absolute; line-height:160%;color: #FFFFFF; font-size:10pt; font-weight: bold;cursor:hand" onselectstart="return false" onmousedown="event.srcElement.dragDrop()" ondragstart="_innerWindowDragEvent()" ondrag="_innerWindowDragEvent()">&nbsp;' + title + '</div>'
    title += '<DIV style="FILTER: Alpha(Opacity=70, FinishOpacity=10, Style=1, StartX=0, StartY=0, FinishX=100, FinishY=0); BACKGROUND:#000099; WIDTH: 100%; HEIGHT: 100%"></DIV>';
    
    if( _onclick == null || _onclick == '' ){
    	var tail = '';
    }
	else{
		if( _onclick.indexOf('(') < 0 ) _onclick = _onclick + '()';
		var tail = '<tr height="28px"><td align="center">' + 
			'<input type="button" name="cmdOK" value="ȷ ��" class="menu" onclick="hiddenInnerWindow();' + _onclick + '" style="width:60px">&nbsp;&nbsp;' + 
			'<input type="button" name="cmdClose" value="ȡ ��" class="menu" onclick="hiddenInnerWindow();" style="width:60px">' +
			'</td></tr>';
	}
    
    msg = msg.replaceAll('\n', '<br>');
    msg = msg.replaceAll( '\t', '&nbsp;&nbsp;&nbsp;&nbsp;' );
    msg = msg.replaceAll( '  ', '&nbsp;&nbsp;' );
    
  	var data = '<div class="body-div">' +
			'<table border="0" width="100%" height="100%" cellspacing="0" cellpadding="0" bgcolor="#6699FF">' +
			'<tr height="22px"><td>' + title + '</td></tr>' +
			'<tr valign=middle bgcolor="#99CCFF"><td align=' + align + ' style="padding-left:6">' + msg + '</td></tr>' +
			tail + '</table></div>';
    
    // ����
    messageLayer.innerHTML = data;
	
	// ��ʾ����������
	o.display = "";
    o.width = width;
    o.height = height;
 	
    var cw = messageLayer.clientWidth, ch = messageLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft;
    var dh = document.body.clientHeight, dt = document.body.scrollTop;
    
    o.top = dt + (dh - ch)/2;
    o.left = dl + (dw - cw)/2;
}

// ����
function hiddenInnerWindow()
{
	var	bLayer = document.all.innerWindow;
	bLayer.style.display = "none";
}

// ��λ
function setInnerWindowPosition( obj, width, height )
{
	// ��׼�ؼ�
	_innerArgs.baseCtrl = obj;
	
	// ���λ��
	var	bLayer = document.all.innerWindow;
	var o = bLayer.style;
	
	// ���ÿ�Ⱥ͸߶�
	if( width != null && width != '' ){
		o.width = width;
		
		if( width < 2 ){
			o.top = 0;
	   		o.left = 0;
			return;
		}
	}
	
	if( height != null && height != '' ){
		o.height = height;
	}
	
	// ҳ��
    var dw = document.body.clientWidth, dl = document.body.scrollLeft;
    var dh = document.body.clientHeight, dt = document.body.scrollTop;
    var cw = bLayer.clientWidth, ch = bLayer.clientHeight;
	
	// ��λ��ҳ�������
	if( obj == null ){
		o.top = dt + (dh - ch)/2;
   		o.left = dl + (dw - cw)/2;
		return;
	}
	
	// ���ݰ�ťλ����ʾ
	var t = obj.offsetTop,  h = obj.clientHeight, l = obj.offsetLeft;
	var pobj = obj;
 	while( pobj = pobj.offsetParent ){
 		t += pobj.offsetTop; l += pobj.offsetLeft;
 		if( pobj.scrollTop > 0 ){
 			t -= pobj.scrollTop;
 		}
 		
 		if( pobj.scrollLeft > 0 ){
 			l -= pobj.scrollLeft;
 		}
 	}
 	
 	// dh=ҳ��ĸ߶ȣ�h=�ؼ��ĸ߶ȣ�ch=iframe�ĸ߶�
 	var	topx;
    if (dh - t - h >= ch){
    	topx = t + h + 2;
    }
    else{ 
    	topx  = (t < ch) ? t + h + 6 : t - ch;
    }
    
    if( topx + ch > dh ){
    	o.top = dh - ch + dt;
    }
	else{
		o.top = topx + dt;
	}
    
    if (dw + dl - l >= cw){ 
    	topx = l; 
    }else{ 
    	topx = (dw >= cw) ? dw - cw : 1;
    }
    
    if( topx + cw > dw ){
    	o.left = dw - cw + dl;
    }
	else{
		o.left = topx + dl;
	}
}

function setInnerWindowVisible()
{
	setInnerWindowPosition( _innerArgs.baseCtrl );
}

// ���ô��ڴ�С
function setInnerWindowWidth( width )
{
	var	bLayer = document.all.innerWindow;
	bLayer.style.width = width;
}

function setInnerWindowHeight( height )
{
	var	bLayer = document.all.innerWindow;
	bLayer.style.height = height;
}

// ȡ���ݵĲ���
function getInnerWindowArgs()
{
	return _innerArgs.args;
}




// ȡ���ܹ������߶�
function getIframeMaxHeight( obj )
{
	var t = obj.offsetTop,  h = obj.clientHeight; 
 	while (obj = obj.offsetParent){
 		t += obj.offsetTop - obj.scrollTop;
 	}
 	
 	t = t - document.body.scrollTop;
 	var dh = document.body.clientHeight - h - t;
 	return (t > dh) ? t - 20 : dh - 20;
}


// ���ò��λ��
function setIframePosition( iLayer, obj )
{
	var o = iLayer.style;
	
	var t = obj.offsetTop,  h = obj.clientHeight, l = obj.offsetLeft; 
 	while (obj = obj.offsetParent){
 		t += obj.offsetTop - obj.scrollTop;
 		l += obj.offsetLeft - obj.scrollLeft;
 	}
 	
 	var	topx;
    var cw = iLayer.clientWidth, ch = iLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
    
    if (document.body.clientHeight + dt - t - h >= ch){
    	topx = t + h + 2;
    }
    else{ 
    	topx  = (t - dt < ch) ? t + h + 6: t - ch;
    }
    
    if( topx + ch > document.body.clientHeight + dt ){
    	o.top = document.body.clientHeight - ch;
    }
	else{
		o.top = topx;
	}
    
    if (dw + dl - l >= cw){ 
    	topx = l; 
    }else{ 
    	topx = (dw >= cw) ? dw - cw + dl : dl;
    }
    
    if( topx + cw > document.body.clientWidth + dl ){
    	o.left = document.body.clientWidth - cw;
    }
	else{
		o.left = topx;
	}
}


// ���ò��λ�ã��������������
function setIframeUpPosition( iLayer, obj )
{
	var o = iLayer.style;
	var t = obj.offsetTop,  h = obj.clientHeight, l = obj.offsetLeft; 
 	while (obj = obj.offsetParent){
 		t += obj.offsetTop - obj.scrollTop;
 		l += obj.offsetLeft - obj.scrollLeft;
 	}
 	
 	var	topx;
    var cw = iLayer.clientWidth, ch = iLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
    
    // �Ƿ��ڿؼ����Ϸ�û���㹻�Ŀռ�
    if( t - dt > ch ){
    	topx = t - ch;
    }
	else{
		topx = (document.body.clientHeight + dt - t - h >= ch) ? t + h + 6 : t - ch;
	}
    
    o.top = (topx < dt) ? dt : topx;
    
    if (dw + dl - l >= cw){ 
    	topx = l; 
    }else{ 
    	topx = (dw >= cw) ? dw - cw + dl : dl;
    }
    
    if( topx + cw > document.body.clientWidth + dl ){
    	o.left = document.body.clientWidth - cw;
    }
	else{
		o.left = topx;
	}
}



// ���ò��λ�ã��������������
function setHintframePosition( iLayer, obj )
{
	var o = iLayer.style;
	var t = obj.offsetTop,  h = obj.clientHeight, l = obj.offsetLeft, r = obj.clientWidth; 
 	while (obj = obj.offsetParent){
 		t += obj.offsetTop - obj.scrollTop;
 		l += obj.offsetLeft - obj.scrollLeft;
 	}
 	
    var cw = iLayer.clientWidth, ch = iLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
    
    // �Ƿ��ڿؼ����Ϸ�û���㹻�Ŀռ�
    o.top = (t - dt > ch) ? t - ch : t + h + 2;
    o.left = (cw < dw - l) ? l + dl : dw - cw + dl;
    
    // �ж��Ƿ��Iframe�ص�
    var d = document.getElementById( 'innerWindow' );
    if( d.style.display != 'none' ){
    	if( iLayer.offsetTop > d.offsetTop && iLayer.offsetTop < d.offsetTop+d.clientHeight ){
    		o.top = (t - dt > ch) ? t + h + 2 : t - ch;
    	}
    }
}



// �������ݵ��ļ�
function _save2File( data )
{
	if( data.indexOf('<html') < 0 ){
		data = data.replaceAll( '\n', '<br>' );
		data = data.replace( /  /g, '&nbsp;&nbsp;' );
	}
	
	var bLayer = document.all.innerWindow;
	bLayer.innerHTML = "<iframe name=saveWindowIframe>";
	var	bFrame = window.frames("saveWindowIframe");
	bFrame.document.write( data );
	bFrame.document.close();
	bFrame.document.charset = "GBK";
	bFrame.document.execCommand('saveAs');
}


// ��ʾ��Ϣ
function _getInputData( callback, title, defaultValue, width, height )
{
	if( callback == null ){
		return;
	}
	
	// ��������ʾ��Ϣ
	fz_hiddenHintLayer();
	
	if( title == null ){
		title = '����������';
	}
	
	if( defaultValue == null ){
		defaultValue = '';
	}
	
	if( width == null ){
		width = document.body.clientWidth;
		if( width > 600 ){
			width = 400;
		}
		else if( width < 200 ){
			width = (width * 5) / 6;
		}
		else{
			width = (width * 2) / 3;
		}
	}
	
	if( height == null ){
		height = document.body.clientHeight;
		if( height > 400 ){
			height = 200;
		}
		else if( width < 100 ){
			height = (height * 2) / 3;
		}
		else{
			height = height / 2;
		}
	}
	
    // ����
    title = '<div style="WIDTH: 100%; HEIGHT: 100%; POSITION: absolute; line-height:160%;color: #FFFFFF; font-size:10pt; font-weight: bold;">&nbsp;' + title + '</div>'
    title += '<DIV style="FILTER: Alpha(Opacity=70, FinishOpacity=10, Style=1, StartX=0, StartY=0, FinishX=100, FinishY=0); BACKGROUND:#000099; WIDTH: 100%; HEIGHT: 100%"></DIV>';
    
    // ������
    var inputString = '<textarea id="@inputdata" style="width:100%;height:100%">' + defaultValue + '</textarea>';
    
    // ��ť
    var btnString = '<input type="button" value="ȷ ��" class="menu" onclick="_completeInput(\'' + callback + '\')">' +
    			'&nbsp;&nbsp;' +
    			'<input type="button" value="ȡ ��" class="menu" onclick="_clearInnerWindow()">';
    
  	var data = '<table border="0" width="100%" height="100%" cellspacing="1" cellpadding="0" bgcolor="#6699FF">' +
					'<tr height="22px"><td>' + title + '</td></tr>' +
					'<tr valign=middle bgcolor="#99CCFF"><td align=center>' + inputString + '</td></tr>' +
					'<tr height="28px" valign=middle bgcolor="#99CCFF"><td align=center>' + btnString + '</td></tr></table>';
    
	// ���
	var messageLayer = getObjectById("innerWindow");
	var o = messageLayer.style;
    
    // ����
    messageLayer.innerHTML = data;
	
	// ��ʾ����������
	o.display = "";
    o.width = width;
    o.height = height;
 	
    var cw = messageLayer.clientWidth, ch = messageLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft;
    var dh = document.body.clientHeight, dt = document.body.scrollTop;
    
    o.top = dt + (dh - ch)/2;
    o.left = dl + (dw - cw)/2;
    
    // ����select��
    fz_hiddenSelectBox( messageLayer );
    
    // ���ý���
    var obj = document.getElementById('@inputdata');
    obj.focus();
}


function _clearInnerWindow()
{
	var	bLayer = document.all.innerWindow;
	bLayer.style.display = "none";
	bLayer.innerHTML = '';
	
	// ��ʾ�����ص���
	fz_showSelectBox();
}

function _completeInput( callback )
{
	var _inputData = document.getElementById('@inputdata').value;
	_clearInnerWindow();
	
	var ptr = callback.indexOf( '(' );
	if( ptr > 0 ){
		callback = callback.substring( 0, ptr );
	}
	
	callback = callback + '(_inputData)';
	eval( callback );
}

//-->

