<!--

// 处理成功的记录数量，只对deleteRecord、addRecord和updateRecord有效，通过这些参数，避免修改记录后再次查询记录的数量
// 当前选中的记录信息

// 取缓存的数据
function page_getSavedActionData( win )
{
	if( win == null ){
		win = window;
	}
	
	// 取保存的数据
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
	
	// 加载数据
	return _browse.getPageData( txnCode );
}

// 设置当前操作的GRID名称
function page_setGridName( win, gridName )
{
	// 加载数据
	var savedData = page_getSavedActionData( win );
	if( savedData == null ){
		return;
	}
	
	// 成功的记录数量
	savedData.gridName = gridName;
}

// 设置当前操作的函数名称
function page_setFuncName( win, funcName )
{
	// 加载数据
	var savedData = page_getSavedActionData( win );
	if( savedData == null ){
		return;
	}
	
	// 成功的记录数量
	savedData.funcName = funcName;
}

// 设置处理成功的记录数量
function page_setSuccessNumber( win, num )
{
	// 加载数据
	var savedData = page_getSavedActionData( win );
	if( savedData == null ){
		return;
	}
	
	// 成功的记录数量
	savedData.successNumber = num;
}

// 设置选中的记录信息
function page_setSelectedRecord( win, gridName, selectedRecord )
{
	// 加载数据
	var savedData = page_getSavedActionData( win );
	if( savedData == null ){
		return;
	}
	
	// 设置选中的记录信息
	savedData.setSelectedRecord( gridName, selectedRecord );
}

// 刷新页面时指定的参数
function page_setSubmitData( win, xurl )
{
	if( win == null ){
		win = window;
	}
	
	// 取保存的数据
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
	
	// 加载数据
	var savedData = _browse.getPageData( txnCode );
	if( savedData == null ){
		return xurl;
	}
	
	return savedData.loadData( xurl, win );
}


/* 打开窗口的方式:
	如果是模式窗口：type=modal
	如果是在主窗口中：type=window
	如果是新打开的窗口：type=new-window
	如果是iframe：type=subframe
*/

// curr_page_varList = 当前页面的变量名称
// dest_page_varList = 目标页面的变量名称
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
	
	// 处理成功后导航的页面
	this.nextPage = null;
	
	// 当前页面的URL、缺省为_browse.pageName
	this._pageUrl = null;
	
	// 需要传递的参数
	this.args = null;
	
	// 触发的域
	this.srcElement = null;
	if( window.event != null ){
		this.srcElement = window.event.srcElement;
	}
	
	// 判断是否是JSP页面 和 页面的后缀
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
	
	// 完整路径
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
	
	// 流水号
	this.flowId = null;
	this.prepareFlowId = page_prepareFlowId;
	
	// 取触发动作的按钮
	this.getSrcElement = page_getSrcElement;
	
	// 函数
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
	
	// 增加参数
	this.add_param = null;
	this.dest_varList = new Array();
	this.curr_varList = new Array();
	this.addParameter( curr_page_varList, dest_page_varList );
}

// 增加变量
function page_addParameter( curr_page_varList, dest_page_varList )
{
	if( curr_page_varList == null || curr_page_varList == '' ){
		return;
	}
	
	if( dest_page_varList == null || dest_page_varList == '' ){
		return;
	}
	
 	// 把[,]替换成[;]
 	curr_page_varList = curr_page_varList.replace(/,/g, ";");
 	dest_page_varList = dest_page_varList.replace(/,/g, ";");
	
	// 解析
	var	cv = curr_page_varList.split( ';' );
	var	dv = dest_page_varList.split( ';' );
	if( cv.length != dv.length ){
		alert( '输入的变量数量不一致[' + curr_page_varList + '][' + dest_page_varList + ']' );
		return;
	}
	
	// 增加参数
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

// 增加页面的输入参数
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
		
		// 替换 [&] ==> [%26]；[#] ==> [%23]
		value = value.replaceAll( '&', '%26' );
		value = value.replaceAll( '#', '%23' );
		value = value.replaceAll( '"', '%22' );
 		value = value.replace( /[=]/g, '%3D' );
    	
    	// 是否删除空格
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

// 设置导航信息，可以是页面文件，也可以是导航名称
function page_setNextPage( nextPage )
{
	this.nextPage = nextPage;
}

// 打开窗口时传递的参数
function page_addArgs( value )
{
	if( this.args == null ){
		this.args = new Array();
	}
	
	this.args[this.args.length] = value;
}


// 取页面地址
function page_getPageAddr( param, win )
{
 	// 组织输入参数
 	var	ii;
 	var	jj;
 	var	ptr;
 	var	name;
 	var	input_parameter = "";
	
 	// 取内容
 	var	fname;
 	var	value = new Array();
 	var	frameName = new Array();
 	var	frameNumber = new Array();
 	
 	for( ii=0; ii<this.curr_varList.length; ii++ ){
 		name = this.curr_varList[ii];
 		
 		// 取参数，如果是空值，增加一个空数据，便于处理
 		value[ii] = getFormFieldValues( name );
 		if( value[ii].length == 0 ){
 			value[ii][0] = "";
 		}
 		else{
 			// 替换单引号
 			for( var vi=0; vi < value[ii].length; vi++ ){
 				value[ii][vi] = '' + value[ii][vi];
 				value[ii][vi] = value[ii][vi].replace(/[\']/g, '"' );	// '"
 				
 				// 替换 [&] ==> [%26]；[#] ==> [%23]
 				value[ii][vi] = value[ii][vi].replaceAll( '&', '%26' );
 				value[ii][vi] = value[ii][vi].replaceAll( '#', '%23' );
				value[ii][vi] = value[ii][vi].replaceAll( '"', '%22' );
 				value[ii][vi] = value[ii][vi].replace( /[=]/g, '%3D' );
    	
		    	// 是否删除空格
		    	if( _keepFieldSpace == false ){
		    		value[ii][vi] = trimSpace( value[ii][vi], 0 );
		    	}
 			}
 		}
 		
 		// 取最大的记录数量
 		name = this.dest_varList[ii];
 		ptr = name.indexOf(':');
 		if( ptr > 0 ){
 			fname = name.substring(0, ptr);
 			for( jj=0; jj<frameNumber.length; jj++ ){
 				if( frameName[jj] == fname ){
 					break;
 				}
 			}
 			
 			// 设置block的最大记录数量，如果不一致时错误
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
	 					alert( '输入的参数值数量不匹配' );
	 					return null;
	 				}
	 			}
 			}
 		}
 	}
 	
 	// 生成输入参数部分
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
	
	// 按钮名称
	var objs = document.getElementsByName('inner-flag:menu-name');
	if( objs != null && objs.length > 0 ){
		var menuName = objs[0].value;
		if( menuName != null && menuName != '' ){
			input_parameter = input_parameter + "&inner-flag:menu-name=" + menuName;
		}
	}
	
	// 预览页面标志
	objs = document.getElementsByName('inner-flag:preview-flag');
	if( objs != null && objs.length > 0 ){
		var previewFlag = objs[0].value;
		if( previewFlag != null && previewFlag != '' ){
			input_parameter = input_parameter + "&inner-flag:preview-flag=" + previewFlag;
		}
	}
	
	// freeze-next-page：nextPage在CallService和deleteRecord中有不同的用途，用于注明调用成功后的操作
	if( this.nextPage != null && this.nextPage != '' ){
		if( param == null || param.indexOf(this.nextPage) < 0 ){
			input_parameter = input_parameter + '&freeze-next-page=' + this.nextPage;
		}
	}
 	
 	// 输入参数
 	
 	if( param != null && param != "" ){
 		input_parameter = input_parameter + "&" + param;
 	}
 	
 	// 通过addValue增加的参数
 	if( this.add_param != null && this.add_param != "" ){
 		input_parameter = input_parameter + "&" + this.add_param
 	}
 	
 	// 取参数
 	if( this.type == "modal" ){
		var	dd = new Date();
		input_parameter = "inner-flag:open-type=modal&inner-flag:freeze-stamp=" + dd.getTime() + input_parameter;
 	}
 	else{
	 	if( win == null ){
	 		win = window;
	 	}
	 	
	 	if( this.type == "window" ){
 			// 和当前打开的窗口相同的方式:如果是在主窗口中：type=window；如果是新打开的窗口：type=new-window；如果是iframe：type=subframe
 			input_parameter = "inner-flag:open-type=" + win.openWindowType + input_parameter;
		}
		else if( this.type == "frame" || this.type == "inner" ){
	 		// 在iframe中打开
 			input_parameter = "inner-flag:open-type=subframe" + input_parameter;
	 	}
 		else{
 			// 打开新窗口
 			input_parameter = "inner-flag:open-type=new-window" + input_parameter;
 		}
 	}
 	
 	// 容器地址
 	var	xurl = this.xurl;
 	if( xurl.indexOf(rootPath) != 0 ){
 		xurl = rootPath + xurl;
 	}
 	
 	// 取地址
	if( xurl.indexOf('?') > 0 ){
		xurl = xurl + "&" + input_parameter;
	}
	else{
		xurl = xurl + "?" + input_parameter;
	}
	
	return _browse.resolveURL( xurl );
}


// 页面跳转
// @return 0  ==> 处理不成功，不刷新屏幕
// @return 1  ==> 处理成功，更新屏幕
// @return 2  ==> 处理成功，有调用方决定是否刷新
function page_goPage( param, win )
{
 	if( win == null ){
 		win = window;
 	}
 	
 	// 组织输入参数
 	var	addr = this.getPageAddr(param);
 	
 	
 	// 添加流水号
 	addr = this.prepareFlowId( addr );
 	
 	// 当前页名称
 	try{
 		var x = (this._pageUrl == null) ? win._browse.pageName : this._pageUrl;
 		addr = addr + '&inner-flag:pre-page=' + x;
 	}
 	catch( e ){ }
 	
	var fPage = _getHrefParameter( win.location.href, "freeze-next-page" );
	if( fPage != null ) addr = addr + '?freeze-next-page=' + fPage;
	
	// 打开窗口
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
			// 执行成功，返回no-update
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
		// 判断win是否[iframe]
		if( win.tagName != null && win.tagName.toLowerCase() == 'iframe' ){
			addr = _setUrlOpenType( addr, 'subframe' );
			setTimeout( function(){win.src = addr;}, 1 );
		}
		else{
			// 可能不能取页面文件的变量，如果是不同服务器的文件
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
		// 窗口的坐标
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

// 在grid中增加记录
function page_addRecord( param, win )
{
	// 取grid名称
	element = this.getSrcElement();
	if( element != null ){
		var grid = getGridDefineByElement( element );
		if( grid != null ){
			page_setGridName( window, grid.gridName );
			page_setFuncName( window, "insert" );
		}
	}
	
	// 页面跳转
	return this.goPage( param, win );
}

// 在grid中修改记录
function page_updateRecord( param, win )
{
	// 取grid名称
	element = this.getSrcElement();
	if( element != null ){
		var grid = getGridDefineByElement( element );
		if( grid != null ){
			page_setGridName( window, grid.gridName );
			page_setFuncName( window, "update" );
		}
	}
	
	// 页面跳转
	return this.goPage( param, win );
}




// 下载文件
function page_downFile( param )
{
	// 参数
	if( param == null || param == '' ){
		param = 'inner-flag:failure-hint=下载文件时错误';
	}
	else{
		param = param + '&inner-flag:failure-hint=下载文件时错误';
	}
	
 	// 组织输入参数
	this.type = 'window';
 	var	addr = this.getPageAddr(param);
	
	// 恢复所有按钮
	clickFlag = 0;
	checkAllMenuItem();
	
	// 下载文件
	openInnerWindow( addr, 1, 1 );
}


// 返回页面
function goWindowBack( nextPage, win )
{
	// 打开的子窗口，直接关闭
	if( openWindowType == 'new-window' ){
		window.close();
		return;
	}
	
	if( win == null ){
		win = window;
	}
	
	// 缺省导航到上一个页面
	if( nextPage == null ){
		nextPage = _prePageName;
	}
	
	// 取缓存数据标志
	var addr = _addHrefParameter( nextPage, 'inner-flag:back-flag', 'true' );
 	// 打开窗口的类型
	if( win.openWindowType == 'subframe' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'subframe' );
	}
	else{
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'window' );
	}
	
	// 生成全路径
	addr = _browse.resolveURL( addr );
	
 	// 转换编码
 	addr = page_setSubmitData( win, addr );
	win.location = addr;
}

// 关闭模式窗口
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
	
	// 设置刷新页面
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
		
		// 添加[freeze-next-page]
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

// 返回页面
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



// 调用请求后直接退出页面，saveAndExit等函数调用后的处理
function _auto_goBack( nextPage, successNumber )
{
	// 修改日志信息
	_browse.updateLogger( window, false );
	
	if( errorCode == '000000' ){
		successNumber = 1 + successNumber;
	}
	
	// 不同方式打开的窗口，不同的退出方式：
	// modal=close、
	// new-window=close、
	// window=window.location=nextPage、
	// subframe=window.location=nextPage
	if( openWindowType == 'modal' ){
		_closeModalWindow( false, successNumber, nextPage );
	}
	else if( openWindowType == 'new-window' ){
		// 打开的子窗口，直接关闭
		window.close();
	}
	else{
		if( nextPage != '' ){
 			// 打开窗口的类型
			if( openWindowType == 'subframe' ){
				nextPage = _addHrefParameter( nextPage, 'inner-flag:open-type', 'subframe' );
			}
			else{
				nextPage = _addHrefParameter( nextPage, 'inner-flag:open-type', 'window' );
			}
			
			// 设置成功的记录数量
			page_setSuccessNumber( nextPage, successNumber );
 			nextPage = page_setSubmitData( window, nextPage );
			window.location = _browse.resolveURL( nextPage );
		}
	}
}


// 打印当前页面
// flag = 0 当前页面的记录
// flag = 1 选中的记录
// flag = 2 所有查询到的记录
// flag = 3 所有记录，不恢复查询条件
function page_printPage( flag, win )
{
	if( currentGrid == null ){
		return;
	}
	
	// 缺省是当前页面的记录
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


// 删除记录
function page_deleteRecord( hint, param )
{
	clickFlag = 0;
	if( hint != null && hint != "" ){
		var	rc = window.confirm( hint );
		if( rc == false ){
			return 0;
		}
	}
	
	// 取函数成功执行后的处理方式
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
	
	// 取grid名称，删除的记录数量=当前选中的记录数量
	element = this.getSrcElement();
	if( element != null ){
		var grid = getGridDefineByElement( element );
		if( grid != null ){
			page_setGridName( window, grid.gridName );
			page_setFuncName( window, "delete" );
			page_setSuccessNumber( window, grid.getSelectedRowNumber() );
		}
	}
	
	// 缺省参数
	this.type = 'window';
	if( this.caption == null || this.caption == '' ){
		this.caption = "删除记录";
	}
	
	// 参数
	if( param == null || param == '' ){
		param = "inner-flag:failure-hint=" + this.caption + "时错误";
	}
	else{
		param = param + "&inner-flag:failure-hint=" + this.caption + "时错误";
	}
	
 	// 组织输入参数
 	if( nextPage != null && nextPage != "" ){
 		param = param + "&inner-flag:delete-success-page=" + nextPage
 	}
 	
 	// 取URL地址
 	var	addr = this.getPageAddr(param);
 	
 	// 添加流水号
 	addr = this.prepareFlowId( addr );
	
	// 删除记录
 	hint = '正在' + this.caption + ' ... ...';
	_showProcessHintWindow( hint );
	openInnerWindow( addr, 1, 1 );
}


// 删除记录
function page_callService( hint, param )
{
	clickFlag = 0;
	
	// 取函数成功执行后的处理方式
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
	
	// 缺省参数
	this.type = 'window';
	if( this.caption == null || this.caption == '' ){
		this.caption = "处理请求";
	}
	
	// 参数
	if( param == null || param == '' ){
		param = "inner-flag:failure-hint=" + this.caption + "时错误";
	}
	else{
		param = param + "&inner-flag:failure-hint=" + this.caption + "时错误";
	}
	
 	// 组织输入参数
 	if( nextPage != null && nextPage != "" ){
 		param = param + "&inner-flag:service-success-page=" + nextPage
 	}
 	
 	if( hint != null && hint != '' ){
	 	hint = '正在' + hint + ' ... ...';
		_showProcessHintWindow( hint );
	}
	
	// 处理请求
	this.nextPage = "call-service";
 	var	addr = this.getPageAddr( param );
	openInnerWindow( addr, 1, 1 );
}



// 调用AJAX请求
var __xml_Http = new Array();

/**
callback 回调函数，调用完成后，会自动调用这个函数进行后处理
param URL的参数
*/
function page_callAjaxService( callback, param )
{
	// 使按钮有效
	clickFlag = 0;
	
	// 服务的地址
	var xurl = this.getPageAddr( param );
	
	// 打开窗口的类型：new-window
	xurl = _setUrlOpenType( xurl, 'new-window' );
	
	// 避免缓存
	var	dd = new Date();
	var stamp = dd.getTime();
	xurl = xurl + "&inner-flag:freeze-stamp=" + stamp;
	
	// 回调函数
	if( callback == null ){
		callback = '';
	}
	
	// 处理结果的函数:对于XML响应报文 he 页面响应报文的处理不一样
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
		alert( '不支持XML-HTTP' );
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


/* 从XML结果中取单个数据，如果是多记录数据，只返回第一个值
xDoc callAjaxService成功后返回的结果，在callback中作为第三个参数
param XPATH路径，和服务器端的FORM配置一致，
	命名规则：/context/record/codetype {context为固定的内容，XML的根是固定的；record/codetype是属性名称，根据FORM中的具体配置信息确定}
		或者: record:codetype {属性名称，根据FORM中的具体配置信息确定}，调用时会自动转换成[/context/record/codetype]
*/
function _getXmlNodeValue( xDoc, xpath )
{
	// 取路径
	xpath = xpath.replace( /[:]/g, '/' );
	if( xpath.indexOf('/') != 0 ){
		xpath = '/' + xpath;
	}
	
	if( xpath.indexOf('/context') != 0 ){
		xpath = '/context' + xpath;
	}
	
	// 取值
	var val = "";
	var obj = xDoc.selectSingleNode( xpath );
	if( obj != null ){
		val = obj.text;
	}
	
	return val;
}

/* 从XML结果中取属性的值，返回一个数组。如果是多记录数据，返回多个值的数组；如果是单记录，返回只包含一个值的数组
xDoc和xpath的命名和上面一致
*/
function _getXmlNodeValues( xDoc, xpath )
{
	// 取路径
	xpath = xpath.replace( /[:]/g, '/' );
	if( xpath.indexOf('/') != 0 ){
		xpath = '/' + xpath;
	}
	
	if( xpath.indexOf('/context') != 0 ){
		xpath = '/context' + xpath;
	}
	
	// 取节点名称和字段名称
	var ptr = xpath.lastIndexOf( '/' );
	var name = xpath.substring( ptr );
	xpath = xpath.substring( 0, ptr );
	
	// 结果
	var val = new Array();
	
	// 取节点
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
		
		// 取值
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


/* 从XML结果中取属性的值，返回一个数组。如果是多记录数据，返回多个值的数组；如果是单记录，返回只包含一个值的数组
xDoc和xpath的命名和上面一致
nodeList 记录集的节点列表
*/
function _getXmlRecordset( xDoc, xpath, nodeList )
{
	// 取路径
	xpath = xpath.replace( /[:]/g, '/' );
	if( xpath.indexOf('/') != 0 ){
		xpath = '/' + xpath;
	}
	
	if( xpath.indexOf('/context') != 0 ){
		xpath = '/context' + xpath;
	}
	
	// 结果
	var result = new Array();
	
	// 取节点
	var items = xDoc.selectNodes( xpath );
	if( items != null ){
		for( var ii=0; ii<items.length; ii++ ){
			var val = new Array();
			
			// 取记录
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


// 处理结果:XML结果
function _processAjaxResponse( stamp, _procFunc ) 
{
	// 取回调的函数
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
	
	// 是否已经完成
	if( _ajax.readyState != 4 ){
		return false;
	}
	
	// 删除
	__xml_Http[index] = null;
	
	// 是否成功
	if( _ajax.status != 200 ){
		alert( "系统未知错误:" + _ajax.status );
		_ajax.abort();
		return;
	}
	
	// 判断结果
	var xData = _ajax.responseXML.xml;
	var xmlResults= new ActiveXObject( "MSXML2.DOMDocument.3.0" );
	xmlResults.loadXML( xData );
	
	// 取错误代码
	var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
	
	// 错误描述
	var errDesc = '';
	if( errCode != '000000' ){
		errDesc = _getXmlNodeValue( xmlResults, "/context/error-desc" );
		// alert( '处理错误[' + errCode + '] ==> ' + errDesc );
		// return;
	}
	
	// 处理回调函数
	if( _procFunc != null && _procFunc != '' ){
		var ptr = _procFunc.indexOf( '(' );
		if( ptr > 0 ){
			_procFunc = _procFunc.substring( 0, ptr );
		}
		
		_procFunc = _procFunc + "(errCode,errDesc,xmlResults)";
		eval( _procFunc );
	}
} 

// 处理结果:HTML结果
function _processAjaxHTMLResponse( stamp, _procFunc )
{
	// 取回调的函数
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
	
	// 是否已经完成
	if( _ajax.readyState != 4 ){
		return false;
	}
	
	// 删除
	__xml_Http[index] = null;
	
	// 是否成功
	if( _ajax.status != 200 ){
		alert( "系统未知错误:" + _ajax.status );
		_ajax.abort();
		return;
	}
	
	// 取错误代码
	var errCode = '000000';
	
	// 错误描述
	var errDesc = '';
	
	// HTML结果
	var htmlResults= _ajax.responseText;
	
	// 处理回调函数
	if( _procFunc != null && _procFunc != '' ){
		var ptr = _procFunc.indexOf( '(' );
		if( ptr > 0 ){
			_procFunc = _procFunc.substring( 0, ptr );
		}
		
		_procFunc = _procFunc + "(errCode,errDesc,htmlResults)";
		eval( _procFunc );
	}
}



// 获取触发动作的按钮
function page_getSrcElement()
{
	if( this.srcElement != null ){
		return this.srcElement;
	}
	
	if( window.event != null && window.event.srcElement != null ){
		return window.event.srcElement;
	}
}


// 在GET请求中增加流水号
function page_prepareFlowId( addr )
{
	if( this.pageType != 'do' ){
		return addr;
	}
	
	// 生成流水号
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
	
	// 增加操作日志
	_browse.addOperatorLogger(this.flowId, this.xurl, this.caption, true, window);
	
	return addr;
}



// 根据名称查找Iframe，不知道window.frames(...)为什么会报错,可能是还没初始化的时候就调用
function getIframeByName( frameName )
{
	for( var ii=0; ii<window.frames.length; ii++ ){
		if( window.frames[ii].name == frameName ){
			return window.frames[ii];
		}
	}
	
	return null;
}



// 实时从服务器下载数据
// 第一个页面是进度条
document.write("<div id=innerWindow style='position: absolute; z-index: 9999; width: 100px; height: 100px; display: none; background-color: #C0C0C0'></div>");
//document.write("<iframe name=innerWindowIframe scrolling=no frameborder=0 style='width:100%; height:100%' src='" + _browse.contextPath + "/script/progress.html'></iframe></div>");

function InnerWindowArgs()
{
	this.baseCtrl = null;
	this.args = null;
}

var _innerArgs = new InnerWindowArgs();

// 打开窗口
// obj 用于定位层的对象
// flag = true 或 null 时，失去焦点时自动关闭； 其他时不关闭窗口
function openInnerWindow( xurl, width, height, obj, flag, args )
{
	// 设置参数
	_innerArgs.args = args;
	
	// 显示并设置座标
	var bLayer = document.all.innerWindow;
	if( width != null && height != null ){
		bLayer.innerHTML = "";
		var o = bLayer.style;
		o.display = "";
		setInnerWindowPosition( obj, width, height );
		
		//鼠标离开自动隐藏
		if( flag == null || flag == true ){
			document.onmousedown = hiddenInnerWindow;
		}
	}
	
	// 窗口的打开方式
	if( xurl.indexOf('?') > 0 ){
		xurl = xurl + '&inner-flag:open-type=subframe';
	}
	else{
		xurl = xurl + '?inner-flag:open-type=subframe';
	}
	
	// 打开窗口
	if( xurl.charAt(0) == '/' ){
		if( xurl.indexOf(_browse.contextPath + '/') != 0 ){
			xurl = _browse.contextPath + xurl;
		}
	}
	
	setTimeout( function(){ __delay_openInnerWindow(xurl) }, 1 );
}

// 延迟打开窗口
// openWindowType=全局变量，在freeze:html中生成，说明是哪种方式打开的窗口
function __delay_openInnerWindow( xurl )
{
	var	fsrc = "";
	if( openWindowType == 'modal' ){
		fsrc = " src='" + xurl + "'";
	}
	
	// 框架
	var bLayer = document.all.innerWindow;
	bLayer.innerHTML = "<iframe name=innerWindowIframe" + fsrc + " scrolling=no frameborder=0 width='100%' height='100%'>";
	var	bFrame = window.frames("innerWindowIframe");
	
	if( openWindowType != 'modal' ){
		// 提示信息
		if( xurl.length < 2000 ){
			var	result = '<html><head></head><body style="margin:0;padding:0;"><table border="0" width="100%" height="100%" bgcolor="#99CCFF">' +
					'<tr valign=middle><td align=center>正在加载数据 ... ...</td></tr></table></body>' +
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
					'<tr valign=middle><td align=center>正在加载数据 ... ...</td></tr></table>';
			result += '<form name="xx" id="xx" method="post" action="' + action + '">';
			
			// 解析参数
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
					alert( '请求数据太大，处理不成功' );
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

// drag/drop事件
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

// 把value转换成原来的内容: %26 ==> &; %23 ==> #; %3D ==> =
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

// 显示信息
function _showInnerAlert( title, msg, width, height, _onclick )
{
	// 框架
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
			'<input type="button" name="cmdOK" value="确 定" class="menu" onclick="hiddenInnerWindow();' + _onclick + '" style="width:60px">&nbsp;&nbsp;' + 
			'<input type="button" name="cmdClose" value="取 消" class="menu" onclick="hiddenInnerWindow();" style="width:60px">' +
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
    
    // 内容
    messageLayer.innerHTML = data;
	
	// 显示并设置座标
	o.display = "";
    o.width = width;
    o.height = height;
 	
    var cw = messageLayer.clientWidth, ch = messageLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft;
    var dh = document.body.clientHeight, dt = document.body.scrollTop;
    
    o.top = dt + (dh - ch)/2;
    o.left = dl + (dw - cw)/2;
}

// 隐藏
function hiddenInnerWindow()
{
	var	bLayer = document.all.innerWindow;
	bLayer.style.display = "none";
}

// 定位
function setInnerWindowPosition( obj, width, height )
{
	// 基准控件
	_innerArgs.baseCtrl = obj;
	
	// 层的位置
	var	bLayer = document.all.innerWindow;
	var o = bLayer.style;
	
	// 设置宽度和高度
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
	
	// 页面
    var dw = document.body.clientWidth, dl = document.body.scrollLeft;
    var dh = document.body.clientHeight, dt = document.body.scrollTop;
    var cw = bLayer.clientWidth, ch = bLayer.clientHeight;
	
	// 定位到页面的中心
	if( obj == null ){
		o.top = dt + (dh - ch)/2;
   		o.left = dl + (dw - cw)/2;
		return;
	}
	
	// 根据按钮位置显示
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
 	
 	// dh=页面的高度；h=控件的高度；ch=iframe的高度
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

// 设置窗口大小
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

// 取传递的参数
function getInnerWindowArgs()
{
	return _innerArgs.args;
}




// 取层能够的最大高度
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


// 设置层的位置
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


// 设置层的位置，优先在域的上面
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
    
    // 是否在控件的上方没有足够的空间
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



// 设置层的位置，优先在域的上面
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
    
    // 是否在控件的上方没有足够的空间
    o.top = (t - dt > ch) ? t - ch : t + h + 2;
    o.left = (cw < dw - l) ? l + dl : dw - cw + dl;
    
    // 判断是否和Iframe重叠
    var d = document.getElementById( 'innerWindow' );
    if( d.style.display != 'none' ){
    	if( iLayer.offsetTop > d.offsetTop && iLayer.offsetTop < d.offsetTop+d.clientHeight ){
    		o.top = (t - dt > ch) ? t + h + 2 : t - ch;
    	}
    }
}



// 保存数据到文件
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


// 显示信息
function _getInputData( callback, title, defaultValue, width, height )
{
	if( callback == null ){
		return;
	}
	
	// 先隐藏提示信息
	fz_hiddenHintLayer();
	
	if( title == null ){
		title = '请输入数据';
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
	
    // 标题
    title = '<div style="WIDTH: 100%; HEIGHT: 100%; POSITION: absolute; line-height:160%;color: #FFFFFF; font-size:10pt; font-weight: bold;">&nbsp;' + title + '</div>'
    title += '<DIV style="FILTER: Alpha(Opacity=70, FinishOpacity=10, Style=1, StartX=0, StartY=0, FinishX=100, FinishY=0); BACKGROUND:#000099; WIDTH: 100%; HEIGHT: 100%"></DIV>';
    
    // 输入区
    var inputString = '<textarea id="@inputdata" style="width:100%;height:100%">' + defaultValue + '</textarea>';
    
    // 按钮
    var btnString = '<input type="button" value="确 定" class="menu" onclick="_completeInput(\'' + callback + '\')">' +
    			'&nbsp;&nbsp;' +
    			'<input type="button" value="取 消" class="menu" onclick="_clearInnerWindow()">';
    
  	var data = '<table border="0" width="100%" height="100%" cellspacing="1" cellpadding="0" bgcolor="#6699FF">' +
					'<tr height="22px"><td>' + title + '</td></tr>' +
					'<tr valign=middle bgcolor="#99CCFF"><td align=center>' + inputString + '</td></tr>' +
					'<tr height="28px" valign=middle bgcolor="#99CCFF"><td align=center>' + btnString + '</td></tr></table>';
    
	// 框架
	var messageLayer = getObjectById("innerWindow");
	var o = messageLayer.style;
    
    // 内容
    messageLayer.innerHTML = data;
	
	// 显示并设置座标
	o.display = "";
    o.width = width;
    o.height = height;
 	
    var cw = messageLayer.clientWidth, ch = messageLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft;
    var dh = document.body.clientHeight, dt = document.body.scrollTop;
    
    o.top = dt + (dh - ch)/2;
    o.left = dl + (dw - cw)/2;
    
    // 隐藏select域
    fz_hiddenSelectBox( messageLayer );
    
    // 设置焦点
    var obj = document.getElementById('@inputdata');
    obj.focus();
}


function _clearInnerWindow()
{
	var	bLayer = document.all.innerWindow;
	bLayer.style.display = "none";
	bLayer.innerHTML = '';
	
	// 显示被隐藏的域
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

