<!--

/* *************** ȡ�������� *************** */


// ȱʡ�����TAB��:ENTER
var	_defaultTabKey = 13;

// �Ƿ��Զ���browse����
var	_browseAutoOpen = false;

// �Ƿ��Զ������ڴ���
var	_dateAutoOpen = false;


var _fileCanInput = false;


var _dateCanInput = true;

/*
 * ��������
 * 1 ���� 2009-09-30 
 * 2 ���� 2009/09/30 
 * 3 ���� 20090930 
 * 4 ���� 2009��09��30�� 
 * 5 ���� 2005-07-13 14:51:06 
 */

// ȱʡ�����ͣ�20090930
var _defaultDateFormat = 1;

//
var _gridSupportCtrlKey = false;


// �������Ƿ����ո�false:�ύǰ��������Ŀո�ɾ����true:�������пո�?
var _keepFieldSpace = false;

// ҳ���Ƿ�������ˣ�false����true������
var _back_disabled = false;

//
var _browse_showMixData = false;

//
var _page_enableCopy = true;

// ���ڴ򿪵ķ�ʽ : window,new-window,modal,subframe
var openWindowType = document.getElementsByTagName('html').item(0).type;
if( openWindowType == null ){
	var b = document.getElementsByTagName('base')[0];
	if( b != null && b.target == '_self' ){
		openWindowType = 'modal';
	}
	else{
		openWindowType = 'window';
	}
}



// ȡ�����������ں�ʱ��
function _getServerDate()
{
	var date = new Date( parseInt(_serverTimeStamp) );
	
	var yyyy = '' + date.getFullYear();
	var mm = '' + (parseInt(date.getMonth()) + 1);
	if( mm.length == 1 ){
		mm = '0' + mm;
	}
	
	var dd = '' + date.getDate();
	if( dd.length == 1 ){
		dd = '0' + dd;
	}
	
	return yyyy + mm + dd; 
}

function _getServerTime()
{
	var date = new Date( parseInt(_serverTimeStamp) );
	
	var hh = '' + date.getHours();
	if( hh.length == 1 ){
		hh = '0' + hh;
	}
	
	var mm = '' + date.getMinutes();
	if( mm.length == 1 ){
		mm = '0' + mm;
	}
	
	var ss = '' + date.getSeconds();
	if( ss.length == 1 ){
		ss = '0' + ss;
	}
	
	return hh + mm + ss;
}

// �����������ں�ʱ��
var _serverTimeStamp = document.getElementsByTagName('stamp')[0];
if( _serverTimeStamp != null ){
	_serverTimeStamp = _serverTimeStamp.value;
}
else{
	_serverTimeStamp = (new Date()).getTime();
} 

var currentDate = _getServerDate();
var currentTime = _getServerTime();



// ȡҳ������
function _getPageName( win )
{
	var href = win.location.href;
	var	ptr = href.indexOf('?');
	if( ptr > 0 ){
		href = href.substring(0, ptr);
	}
	
	return href.substring( href.lastIndexOf("/") +1 );
}

// ȡ��������
function _getTopWindow()
{
	try{
		var t = top;
		
		// modal���ڲ��ܴ��µ��Ӵ��ڣ�����ڴ��ڵĴ����������modal���ڣ���϶������һ��
		if( openWindowType == 'modal' ){
			t = window.dialogArguments[0].top;
		}
		
		// ȡ��������
		var pageName = _getPageName( t );
		while( pageName != 'txn999999.do' && pageName != 'txn999997.do' && t.opener != null ){
			//
			try{
				if( t.opener.top == null ){
					break;
				}
			}
			catch( ex ){
				break;
			}
			
			t = t.opener.top;
			pageName = _getPageName( t );
		}
		
		return t;
	}
	catch( e ){
		return null;
	}
}

// ҳ���а�ť������Ĵ��������clickFlag > 0�����ֹҳ���ϵ����а�ť��ԭ����HTMLҳ����
var clickFlag = 0;

// ��ҳ��
var _top = _getTopWindow();


// ��ť���ձ�
var	_keyCodeMapping = [
	[48, '0'], [49, '1'], [50, '2'], [51, '3'], [52, '4'], [53, '5'], [54, '6'], [55, '7'],
	[56, '8'], [57, '9'], 
	[65, 'A'], [66, 'B'], [67, 'C'], [68, 'D'], [69, 'E'], [70, 'F'], [71, 'G'], [72, 'H'], 
	[73, 'I'], [74, 'J'], [75, 'K'], [76, 'L'], [77, 'M'], [78, 'N'], [79, 'O'], [80, 'P'], 
	[81, 'Q'], [82, 'R'], [83, 'S'], [84, 'T'], [85, 'U'], [86, 'V'], [87, 'W'], [88, 'X'], 
	[89, 'Y'], [90, 'Z'], 
	[8,  'DELETE'], [9,  'TAB'], [27, 'ESC'], [33, 'PAGEUP'], [34, 'PAGEDOWN'], [35, 'HOME'],
	[36, 'END'], [37, 'LEFT'], [38, 'UP'], [39, 'RIGHT'], [40, 'DOWN'], [45, 'INSERT'], [46, 'DELETE'],
	[112, 'F1'], [113, 'F2'], [114, 'F3'], [115, 'F4'], [116, 'F5'], [117, 'F6'], 
	[118, 'F7'], [119, 'F8'], [120, 'F9'], [121, 'F10'], [122, 'F11'], [123, 'F12'],
	[166, 'PAGEBACK'], [167, 'PAGENEXT']
];

//�ȼ����ձ�
var	_keyAliasMapping = [
	['ESC',		'CLOSE'],
	['CTRL S',	'SAVE'],
	['CTRL R',	'SAVE_CONTINUE'],
	['CTRL E',	'SAVE_CLOSE'],
	['CTRL G',	'SAVE_GO'],
	['CTRL D',	'DELETE'],
	['CTRL I',	'ADD'],
	['CTRL A',	'ADD'],
	['CTRL U',	'UPDATE'],
	['CTRL W',	'VIEW'],
	['CTRL P',	'PRINT']
];

// �ж��Ƿ�ΪTAB��
function _isDefaultTabKey( keyCode )
{
	return ( _defaultTabKey == keyCode );
}

// ȡ��ǰ�İ���������ALT��CTRL
function fz_getCurrentKeyCode()
{
	var	data = window.event;
	var	keyCode = null;
	var keyName = null;
	
	// ALT
	if( data.altKey ){
		keyCode = 'ALT';
	}
	
	// CTRL
	if( data.ctrlKey ){
		keyCode = (keyCode == null) ? 'CTRL' : keyCode + ' CTRL';
	}
	
	// ����
	for( var ii=0; ii<_keyCodeMapping.length; ii++ ){
		if( _keyCodeMapping[ii][0] == data.keyCode ){
			keyName = _keyCodeMapping[ii][1];
			break;
		}
	}
	
	if( keyName != null ){
		keyCode = (keyCode == null) ? keyName : keyCode + ' ' + keyName;
	}
	
	return keyCode;
}


// ȡ����
function fz_getKeyAliasName( keyName )
{
	for( var ii=0; ii<_keyAliasMapping.length; ii++ ){
		if( _keyAliasMapping[ii][0] == keyName ){
			return _keyAliasMapping[ii][1];
		}
	}
	
	return null;
}

function KeyboardFunction()
{
	//
	this.funcList = new Array();
	this.addFunction = _keyboard_addFunction;
	this.fireEvent = _keyboard_fireEvent;
}

var _keyboard = new KeyboardFunction();

//
function _keyboard_addFunction( keyName, func )
{
	this.funcList.push( [keyName, func] );
}

function _keyboard_fireEvent( keyName )
{
	for( var ii=0; ii<this.funcList.length; ii++ ){
		if( this.funcList[ii][0] == keyName ){
			var func = this.funcList[ii][1];
			func();
			return true;
		}
	}
	
	return false;
}

//
function _DebugMessage( maxLength )
{
	if( maxLength == null ){ maxLength = 128; }
	
	this.maxLength = maxLength;
	this.index = 0;
	this.messages = new Array();
	
	//
    this.count = 0;
    this.errorTrace = _debug_errorTrace;
    this.errorHook = _debug_errorHook;
	
	//
	this.debugEnabled = false;
	try{
		if( _top != null && _top != window ){
			this.debugEnabled = _top._debug_enabled;
		}
	}
	catch( e ){ }
	
	if( this.debugEnabled ){
		this.debug = _debug_message;
		this.showMessage = _debug_showMessage;
		this.showProperties = _debug_showFieldProperties;
		
		//
		this.showPageHtml = _debug_showPageHtml;
		this.showSourceCode = _debug_showSourceCode;
		
		//
		window.onerror = this.errorHook;
	}
	else{
		this.debug = function(msg){ return null; };
		this.showMessage = function(){ return null; }
		this.showProperties = function(){ return null; }
		this.showPageHtml = function(){ return null; }
		this.showSourceCode = function(){ return null; }
	}
}

//
function _debug_message( message )
{
	if( this.index >= this.maxLength ) return;
	
	//
	var funcName = 'N/A';
	var arg = arguments.caller;
	if( arg != null ){
		var regexp  = /function([^{}]+)/;
		var body = arg.callee.toString();
		
		var name =  body.match( regexp )[0];
    	if( name != null || name != '' ){
	    	var args = name.match(/^function(\([^{}]+)/)
	    	if( args != null ){
	    		var name2 = body.match( /\bid\s+=.*[\"\']([^\'\";]+)/ )[1];
	    		if( name2 == null ){
	    			name2 = '';
	    		}
	    		
	    		name = name2 + args[1];
	    	}
	    	
	    	funcName = name.substring(8).trim();
	    }
	}
	
	this.messages.push( [funcName, message] );
}

function _debug_showMessage()
{
	if( this.messages == 0 ) return;
	
	var w = window.open( '', '_debug', "resizable,scrollbars=no,width=700,height=600" );
	var doc = w.document;
	doc.write( '<center><textarea cols="85" rows="35" id="properties" name="properties" style="width:98%;height:98%">\n' );
	
	for( var ii=0; ii<this.messages.length; ii++ ){
		var v = this.messages[ii];
		doc.write( v[0] + ' ==> ' + v[1].replaceAll('>', '??') + '\n' );
	}
	
	doc.write( '\n</textarea></center>\n' );
}


//
function _debug_errorTrace()
{
    var stack = "";
	
    var regexp  = /function([^{}]+)/;
    for( var arg = arguments.caller.caller; arg != null; arg = arg.caller ){
    	var body = arg.callee.toString();
    	var name =  body.match( regexp )[0];
    	if( name == null || name == '' ){
    		name = "anonymous";
    	}
    	else{
	    	var args = name.match(/^function(\([^{}]+)/)
	    	if( args != null ){
	    		var name2 = body.match( /\bid\s+=.*[\"\']([^\'\";]+)/ )[1];
	    		if( name2 == null ){
	    			name2 = '';
	    		}
	    		
	    		name = name2 + args[1];
	    	}
	    }
	    
	    nn = this.count++;
    	stack += '<a href="javascript:;" onclick="showBody(' + "'body" + nn + "'" + ')">' + name + '</a><BR>';
    	stack += '<textarea style="display:none" id="body' + nn + '">' + body + '</textarea>';
		
        if ( arg.callee == arg ){
            break;
        }
    }

    return stack;
}

//
function _debug_errorHook(msg, url, line)
{
	this.count = 0;
	var stack = _browse.errorTrace();
	var info = '<B>error:</B> ' + msg  + '<BR>'
			 + '<B>browse:</B> ' + _browse.browserType + ' : ' + _browse.majVersion  + '<BR>'
             + '<B>url:</B> ' + url  + '<BR>'
             + '<B>line number in file:</B> ' + (line-1) + '<BR>'
             + '<B>call stack:</B>' + '<BR>'
             + stack
             + '<br><textarea cols="70" rows="20" name="body" id="body"></textarea>\n'
             + '<script language="javascript">\n'
             + 'function showBody(bodyName){\n'
             + '  var obj = document.getElementById(bodyName);\n'
             + '  if( obj != null ){\n'
             + '    document.getElementById("body").value = obj.value;\n'
             + '  }\n'
             + '}\n'
             + 'showBody("body0");\n'
             + '</script>\n';
    
    //
    var w = window.open( '', 'error', "resizable,scrollbars,width=625,height=600" );
    var d = w.document;
    d.write( info );
    d.close();
    
    return true;
}


function _debug_showFieldProperties( obj )
{
	if( obj == null ){
		obj = window.event.srcElement;
		if( obj == null ) return;
	}
	
	var w = window.open( '', 'properties', "resizable,scrollbars=no,width=700,height=600" );
	var doc = w.document;
	doc.write( '<center><textarea cols="85" rows="35" id="properties" name="properties" style="width:98%;height:98%">\n' );
	
	for( propertyName in obj ){
		var v = obj[propertyName];
		if( v != null && typeof(v) != "object" ){
			v = ('' + v).replaceAll( '>', '??' );
			doc.write( propertyName + ' = ' + v + '\n' );
		}
	}
	
	doc.write( '\n</textarea></center>\n' );
}



// ???????HTML????
function _debug_showPageHtml()
{
	var w = window.open( '', 'htmlcode', "resizable,scrollbars=no,width=700,height=600" );
	var doc = w.document;
	doc.write( '<center><textarea cols="85" rows="35" id="html-code" name="html-code" style="width:98%;height:98%">\n' );
	
	if( this.html != null ){
		var str = this.html.outerHTML;
		str = str.replaceAll( '</TEXTAREA>', '&lt;/TEXTAREA&gt;' );
		doc.write( str );
	}
	else{
		if( _browse.head != null ){
			doc.write( _browse.head.outerHTML );
			doc.write( '\n' );
		}
		
		var str = document.body.outerHTML;
		str = str.replaceAll( '</TEXTAREA>', '&lt;/TEXTAREA&gt;' );
		doc.write( str );
	}
	
	doc.write( '\n</textarea></center>\n' );
}

function _debug_showSourceCode()
{
	if( openWindowType == 'modal' ){
		document.execCommand('saveAs');
	}
	else{
		this.showPageHtml();
	}
}

//
var log = new _DebugMessage( );


//
_keyboard.addFunction( 'CTRL F10', function(){ log.showPageHtml() } );
_keyboard.addFunction( 'CTRL F12', function(){ log.showSourceCode() } );
_keyboard.addFunction( 'CTRL F8', function(obj){ log.showProperties(obj) } );
_keyboard.addFunction( 'CTRL F7', function(){ log.showMessage() } );


// ��ȥ�ո�
// type = 0 - ȥ��ǰ��ո�; 1 - ȥǰ���ո�; 2 - ȥβ���ո�
function trimSpace( str, trimType )
{
	str = '' + str;
	if( trimType == 0 ) return str.replace(/(\s*$)/g,"");
	else if( trimType == 1 ) return str.replace(/(^\s*)/g,"");
	else return str.replace(/(^\s*)|(\s*$)/g,"");
}

// �ַ����Ĵ���������ȡ�ո�
// type = 0 - ȥ��ǰ��ո�; 1 - ȥǰ���ո�; 2 - ȥβ���ո�
String.prototype.trim = function( trimType )
{
	return trimSpace( this, trimType );
}

String.prototype.ltrim = function( )
{
	return trimSpace( this, 1 );
}

String.prototype.rtrim = function( )
{
	return trimSpace( this, 2 );
}

// ��Ϊ������
Number.prototype.trim = function( trimType )
{
	return trimSpace( '' + this, trimType );
}

Number.prototype.ltrim = function( )
{
	return trimSpace( '' + this, 1 );
}

Number.prototype.rtrim = function( )
{
	return trimSpace( '' + this, 2 );
}

// ȡ���ڵĺ������ڲ�ʹ��
function _getDate10( date )
{
	var yyyy = '' + date.getFullYear();
	var mm = '' + (parseInt(date.getMonth()) + 1);
	if( mm.length == 1 ){
		mm = '0' + mm;
	}
	
	var dd = '' + date.getDate();
	if( dd.length == 1 ){
		dd = '0' + dd;
	}
	
	return yyyy + '/' + mm + '/' + dd; 
}

function _getTime8( date )
{
	var hh = '' + date.getHours();
	if( hh.length == 1 ){
		hh = '0' + hh;
	}
	
	var mm = '' + date.getMinutes();
	if( mm.length == 1 ){
		mm = '0' + mm;
	}
	
	var ss = '' + date.getSeconds();
	if( ss.length == 1 ){
		ss = '0' + ss;
	}
	
	return hh + ':' + mm + ':' + ss;
}

Date.prototype.getCurrentDate = function()
{
	return _getDate10( this );
}

Date.prototype.getCurrentTime = function()
{
	return _getTime8( this );
}


//

function _binarySearch( array, index, key )
{
	var low = 0;
	var high = array.length-1;
	
	while (low <= high) {
		var mid = (low + high) >> 1;
		var value = array[mid][index];
		
		if (value < key){
			low = mid + 1;
		}
		else if (value > key){
			high = mid - 1;
		}
		else{
			return mid; // key found
		}
	}
	
	return -1;  // key not found.
}

Array.prototype.binarySearch = function( index, key )
{
	return _binarySearch( this, index, key );
}


// �滻������['] ==> [\']
function escapeQuotes( str )
{
	var newString = "";
	var cur_pos = str.indexOf("'");
	var prev_pos = 0;
	while (cur_pos != -1) {
		if(cur_pos == 0) {
			newString += "\\";
		}
		else if( str.charAt(cur_pos-1) != "\\" ){
			newString += str.substring(prev_pos, cur_pos) + "\\";
		}
		else if( str.charAt(cur_pos-1) == "\\" ){
			newString += str.substring(prev_pos, cur_pos);
		}
		
		prev_pos = cur_pos++;
		cur_pos = str.indexOf("'", cur_pos);
	}
	
	return(newString + str.substring(prev_pos, str.length));
}

// �滻�ַ���
function replaceAll( buffer, src, dest )
{
	var result = "";
	var iptr1 = 0;
	var iptr = buffer.indexOf( src );
	while( iptr >= 0 ){
		result = result + buffer.substring(iptr1, iptr) + dest;
		iptr1 = iptr + src.length;
		iptr = buffer.indexOf( src, iptr1 );
	}
	
	return result + buffer.substring(iptr1);
}

String.prototype.replaceAll = function( src, dest ){ return replaceAll(this, src, dest); };


// �ж��Ƿ�������
// value.constructorĪ������Ĵ���
function isArray( value )
{
	if( typeof value == 'object' ){
		var c = value.constructor;
		if( c == null ){
			try{
				return (value.length != null);
			}
			catch( e ){
				return false;
			}
		}
		
		return (c.toString().match(/array/i) != null); 
	}
 	
	return false;
}

// ת��url������
function escapeUrl( xurl )
{
	var	result = '';
	var	ptr = xurl.indexOf( '?' );
	if( ptr > 0 ){
		result = xurl.substring( 0, ptr + 1 );
		xurl = xurl.substring( ptr + 1 );
	}
	
	// �����б�
	var	flag = 0;
	var	vlist = xurl.split( '&' );
	for( var ii=0; ii<vlist.length; ii++ ){
		if( vlist[ii] != '' ){
			ptr = vlist[ii].indexOf( '=' );
			if( ptr > 0 ){
				var name = escape( vlist[ii].substring(0, ptr) );
				var value = escape( vlist[ii].substring(ptr + 1) );
			}
			else{
				var name = escape( vlist[ii] );
				var value = '';
			}
			
			if( flag == 0 ){
				flag = 1;
			}
			else{
				result = result + '&';
			}
			
			result = result + name + '=' + value;
		}
	}
	
	return result;
}


// ȡָ�����͵��ϼ���ǩ
function findAncestorWithName( obj, tagName )
{
	obj = obj.parentNode;
	tagName = tagName.toUpperCase();
	while( obj != null && obj.tagName != null ){
		if( obj.tagName.toUpperCase() == tagName ){
			return obj;
		}
		
		obj = obj.parentNode;
	}
	
	return null;
}


//
function _getFormName( fieldName, index )
{
	var obj = null;
	
	if( index == null || index == '' ){
		index = 0;
	}
	
	//
	if( fieldName == null ){
		obj = window.event.srcElement;
	}
	else if( typeof(fieldName) == 'string' ){
		var objs = document.getElementsByName(fieldName);
		if( objs.length > index ){
			obj = objs[index];
		}
	}
	else if( isArray(fieldName) ){
		if( fieldName.length > index ){
			obj = fieldName[index];
		}
	}
	else{
		obj = fieldName;
	}
	
	if( obj == null ) return null;
	
	//
	var pForm = findAncestorWithName( obj, "FORM" );
	return (pForm == null) ? null : pForm.name;
}


// ȡ�������Ϣ
function BrowserInformation()
{
	// default properties and values
	this.contextPath = null;
	this.hostAddr = "";
	this.preHREF = "";
	this.pageName = "";
	this.browserType = "other";
	this.majVersion = null;
	this.DOMable = null;
	
	// �¼�������
	this.onkeyup = null;
	this.onkeydown = null;
	this.onmousedown = null;
	this.onmouseover = null;
	
	// head ����
	this.head = null;
	this.html = null;
	
	// ��ʽ��
	this.getCssStyle = fz_getCssStyle;
	
	// �ȵ�ִ�еĺ����б�
	this.waitProcess = new Array();
	this.execute = _browse_execute;
	this.onload = _onload_complete
	
	// ��ʼ��ҳ��ĺ���
	this.initHtmlPage = null;
	

	this.appendElementList = new Array();
	this._onunload = new Array();
	
	// ����
	this.appendElement = fz_appendElement;	// ������
	this.refresh = fz_refresh;
	this.getCookie = fz_getCookie;
	this.setCookie = fz_setCookie;
	this.resolveURL = fz_resolveURL;
	
	// �޸Ĵ��ڵĴ�С
	this.resizeTo = browse_resizeTo;
	
	// ȡ�������Ϣ
	if(navigator.userAgent.indexOf("Opera") != -1) {
		if(navigator.appName == "Opera") {
			this.majVersion = parseInt(navigator.appVersion);
		}
		else {
			this.majVersion = parseInt(navigator.userAgent.substring(navigator.userAgent.indexOf("Opera")+6));
		}
		
		if(this.majVersion >= 5) {
			this.browserType = "O";
		}
	}
	else if(navigator.appName == "Netscape" && navigator.userAgent.indexOf("WebTV") == -1) {
		this.browserType = "NN";
		if(parseInt(navigator.appVersion) == 3) {
			this.majVersion = 3;
		}
		else if(parseInt(navigator.appVersion) >= 4) {
			this.majVersion = parseInt(navigator.appVersion) == 4 ? 4 : 5;
			if(this.majVersion >= 5) {
				this.DOMable = true;
			}
		}
	}
	else if(navigator.appName == "Microsoft Internet Explorer" && parseInt(navigator.appVersion) >= 4) {
		if(navigator.userAgent.toLowerCase().indexOf("mac") != -1) {
			this.browserType = "NN";
			this.majVersion = 4;
			this.DOMable = false;
		}
		else {
			this.browserType = "IE";
			
			// ȡIE�İ汾��
			var ptr = navigator.appVersion.indexOf("MSIE ");
			if( ptr < 0 ){
				this.majVersion = 4;
			}
			else{
				var v = navigator.appVersion.substring( ptr + 5 );
				var ptr = v.indexOf( '.' );
				if( ptr < 0 ){
					this.majVersion = 4;
				}
				else{
					this.majVersion = parseInt( v.substring(0, ptr) );
				}
			}
			
			if(this.majVersion >= 5) {
				this.DOMable = true;
			}
		}
	}
	
	var	ptr = location.href.indexOf('?');
	if( ptr > 0 ){
		var pageUrl = location.href.substring(0, ptr);
		if( pageUrl.indexOf('/freeze.main') + 12 == pageUrl.length ){
			var param = location.href.substring( ptr );
			var pageName = _getHrefParameter( param, "page" );
			
			//
			ptr = pageName.indexOf( '?' );
			if( ptr > 0 ){
				pageName = pageName.substring(0, ptr);
			}
			
			if( pageName.indexOf('.do') + 3 != pageName.length ){
				pageName = _getHrefParameter(param, "path") + pageName;
			}
			
			this.pageName = pageName;
		}
	}
	else{
		pageUrl = location.href;
	}
	
	// delete http://host:port/
	ptr = pageUrl.indexOf('//');
	if( ptr > 0 ){
		ptr = pageUrl.indexOf( '/', ptr+2 );
		if( ptr > 0 ){
			this.hostAddr = pageUrl.substring( 0, ptr );
			pageUrl = pageUrl.substring( ptr );
		}
	}
	
	// ����Ŀ¼
	this.preHREF = pageUrl.substring(0, pageUrl.lastIndexOf("/") +1);
	
	//
	try{
		this.contextPath = rootPath;
	}
	catch( e ){
		var scripts = document.getElementsByTagName("script");
		var src = "/script/public-func.js";
		for( var i = 0; i < scripts.length; i++ ){
			if (scripts[i].src.match(src)) {
				this.contextPath = scripts[i].src.replace( src, "" );
				break;
			}
		}
		
		if( this.contextPath == null ){
			this.contextPath = _top._browse.contextPath;
			// alert( '??��?public-func.js???????????????' );
		}
	}
	
	if( this.contextPath != '' ){
		pageUrl = pageUrl.substring( this.contextPath.length );
	}
	
	if( this.pageName == null || this.pageName == '' ){
		this.pageName = pageUrl;
	}
	
	// alert( this.hostAddr + '+' + this.contextPath + '+' + this.preHREF + '+' + this.pageName );
	
	// ȡ head
	if(this.DOMable) {
		if( this.browserType == "IE" ){
			this.head = document.all.tags('head')[0];
			this.html = document.all.tags('html')[0];
		}
		else{
			this.head = document.getElementsByTagName('head').item(0);
			this.html = document.getElementsByTagName('html').item(0);
		}
	}
	
	//
	if( _top != null && _top != window ){
		this.addLogger = _top._browse.addLogger;
		this.updateLogger = _top._browse.updateLogger;
		this.processLogger = _top._browse.processLogger;
		this.addOperatorLogger = _top._browse.addOperatorLogger;
		this.openLoggerWindow = _top._browse.openLoggerWindow;
		this.addSubmitPage = _top._browse.addSubmitPage;
		this.checkPageReentry = _top._browse.checkPageReentry;
		this.savePageData = function(win){ if(win == null){ win=window; } _top._browse.savePageData(win); };
		this.getPageData = _top._browse.getPageData;
	}
	else{
		this.addLogger = function(win, formName){ return null; }
		this.updateLogger = function(win, flag){ return null; };
		this.processLogger = function(callback){ return null; };
		this.addOperatorLogger = function(flowId, txnCode, txnName, reentry, win){ return null; };
		this.openLoggerWindow = function(){ return null; }
		this.addSubmitPage = function(win){ return false; };
		this.checkPageReentry = function(win){ return false; };
		this.savePageData = function(win){ return null; };
		this.getPageData = function(txnCode){ return null; };
	}
}

var _browse = new BrowserInformation();

//
_keyboard.addFunction( 'F8', function(){_browse.openLoggerWindow() } );

// ���ú��������ҳ�滹û�м�����ɣ��ȵȼ������Ժ�ִ��
function _browse_execute( func )
{
	if( func == null ){
		// ��ʼ��ҳ��
		if( this.initHtmlPage != null ){
			this.initHtmlPage();
		}
		
		// ��ʼ��
		this.onload();
	}
	else{
		if( this.waitProcess == null || document.readyState == 'complete' ){
			return eval( func );
		}
		else{
			this.waitProcess.push( func );
		}
	}
}

// �������ҳ��󣬳�ʼ��ҳ��
function _onload_complete()
{
	// ���º�����ҳ��������Ժ���
	if( document.readyState != 'complete' ){
		var t = this;
		setTimeout( function(){ t.onload() }, 50 );
		return;
	}
	
	//
	__initBrowseValue();
	
	// ���ô��ڵı���
	if( openWindowType == 'modal' ){
		document.title = window.dialogArguments[1];
	}
	
	// ���ع�����
	if( document.body.className == 'body-main' ){
		document.body.scroll = 'no';
	}
	
	// ˢ��ҳ��
	var t = document.getElementsByName('roundTable');
	for( var ii=0; ii<t.length; ii++ ){
		t[ii].border = 0;
		t[ii].cellSpacing = 0;
		t[ii].cellPadding = 0;
	}
	
	// ����ҳ�������
	_fz_setBodyAttr();
	
	// �޸Ĵ��ڵĸ߶�
	if( openWindowType == 'new-window' || openWindowType == 'modal' ){
		var div = null;
		var ds = document.getElementsByTagName( 'DIV' );
		for( var ii=0; ii<ds.length; ii++ ){
			if( ds[ii].className == 'body-div' ){
				div = ds[ii];
				break;
			}
		}
		
		if( div != null && div.scrollHeight > div.clientHeight-36 ){
			if( openWindowType == 'modal' ){
				var wh = '';
			}
			else{
				var wh = div.clientWidth + 24;
			}
			
			if( div.scrollHeight > screen.height - 100 ){
				if( div.clientHeight < screen.height - 100 ){
					this.resizeTo( wh, screen.height - 100, true );
				}
			}
			else{
				this.resizeTo( wh, div.scrollHeight + 36, true );
			}
		}
	}
	
	// �ر���ʾ����
	_hideProcessHintWindow();
		
	// ������ĺ�������
	if( this.waitProcess != null ){
		// ������������б�
		var len = this.waitProcess.length;
		for( var ii=0; ii<len; ii++ ){
			if( typeof(this.waitProcess[ii]) == 'string' ){
				eval( this.waitProcess[ii] );
			}
			else{
				this.waitProcess[ii]();
			}
		}
		testResizeFrame();
		// ҳ��������
		this.waitProcess = null;
	}
	
	// ���ҳ���Ƿ����
	if( this.checkPageReentry(window) == true ){
		_disableJavascript();
		document.oncontextmenu = function(){return false;};
		
		var hint = '<a href="#" onclick="history.back()" style="font-size: 12pt; color: red; font-weight: bold">&lt;&lt; ???</a>&nbsp;&nbsp;<a href="#" onclick="history.forward()" style="font-size: 12pt; color: red; font-weight: bold">???? &gt;&gt;</a>'
		_showInnerAlert( '??��?????!!!', hint, 220, 70 );
		
		// ???????
		var ct = document.body;
		var divs = document.getElementsByTagName( 'div' );
		for( var ii=0; ii<divs.length; ii++ ){
			if( divs[ii].id == 'body-div' ){
				ct = divs[ii];
				break;
			}
		}
		
		ct.style.filter = "Alpha(Opacity=55, FinishOpacity=55, Style=1, StartX=0, StartY=0, FinishX=100, FinishY=0)";
	}
	else{
		// �޸ı�����־��Ϣ
		this.updateLogger( window, true );
		
		//
		this.savePageData( window );
		
		//
		if( document.body.onunload != null ){
			this._onunload.push( document.body.onunload );
		}
		
		document.body.onunload = _browse_finitHtmlPage;
	}
	
	//
	setFirstFieldFocus();
}

//
function _browse_finitHtmlPage()
{
	//
	for( var ii=0; ii<_browse.appendElementList.length; ii++ ){
		var e = _browse.appendElementList[ii];
		try{ e[0].removeChild( e[1] ); } catch(exx){}
		_browse.appendElementList[ii] = null;
	}
	
	_browse.appendElementList = new Array();
	
	//
	_selectFieldList = null;
	
	//
	for( var ii = _browse._onunload.length-1; ii>=0;  ii-- ){
		_browse._onunload[ii]();
	}
	
	_browse._onunload = new Array();
}


// ��ֹҳ��Ĳ���
function _disableJavascript()
{
	var buttons = document.getElementsByTagName( 'button' );
	for( var ii=0; ii<buttons.length; ii++ ){
		buttons[ii].onclick = function(){alert('���ܴ������ҳ��Ĳ���!!!')};
	}
	
	var inputs = document.getElementsByTagName( 'input' );
	for( var ii=0; ii<inputs.length; ii++ ){
		if( inputs[ii].type == 'button' ){
			inputs[ii].onclick = function(){alert('���ܴ������ҳ��Ĳ���!!!')};
		}
		else{
			inputs[ii].onclick = function(){return false;};
			inputs[ii].onchange = function(){return false;};
			inputs[ii].onkeydown = function(){return false;};
		}
	}
	
	var inputs = document.getElementsByTagName( 'select' );
	for( var ii=0; ii<inputs.length; ii++ ){
		inputs[ii].onclick = function(){return false;};
		inputs[ii].onchange = function(){return false;};
	}
	
	var images = document.getElementsByTagName( 'img' );
	for( var ii=0; ii<images.length; ii++ ){
		images[ii].onclick = function(){return false;};
	}
	
	var links = document.getElementsByTagName( 'a' );
	for( var ii=0; ii<links.length; ii++ ){
		links[ii].onclick = function(){return false;};
		links[ii].href = '';
	}
	
	var forms = document.getElementsByTagName( 'form' );
	for( var ii=0; ii<forms.length; ii++ ){
		forms[ii].action = null;
	}
	
	document.onkeyup = function(){return false;};
	document.onkeydown = function(){return false;};
	document.onmousedown = function(){return false;};
	document.onmouseover = function(){return false;};
}


// ����ҳ�������
function _fz_setBodyAttr()
{
	// ��ֹ����ҳ��/ѡ��ҳ��
	_browse_disableCopyFunction();
}

// ��ֹ����ҳ��/ѡ��ҳ��
function _browse_disableCopyFunction()
{
	var func;
	
	// ҳ���Ƿ�������
	if( _page_enableCopy == true ){
		return;
	}
	
	// onselectstart
	func = document.body.onselectstart;
	if( func == null ){
		document.body.onselectstart = function(){ return false; };
	}
	else{
		document.body.onselectstart = function(){ func(); return false; };
	}
	
	// onselect
	func = document.body.onselect;
	if( func == null ){
		document.body.onselect = function(){ document.selection.empty(); };
	}
	else{
		document.body.onselect = function(){ func(); document.selection.empty(); };
	}
	
	// oncopy
	func = document.body.oncopy;
	if( func == null ){
		document.body.oncopy = function(){ document.selection.empty(); };
	}
	else{
		document.body.oncopy = function(){ func(); document.selection.empty(); };
	}
	
	// onbeforecopy
	func = document.body.onbeforecopy;
	if( func == null ){
		document.body.onbeforecopy = function(){ return false; };
	}
	else{
		document.body.onbeforecopy = function(){ func(); return false; };
	}
}


// �������غ���
function fz_appendElement( parentNode, element )
{
	var pobj;
	if( parentNode == 'head' ) {
		pobj = this.head;
	}
	else if( parentNode == 'body' ){
		pobj = document.body;
	}
	else{
		pobj = parentNode;
	}
	
	// alert( 'append:' + element.tagName + ':' + element.name );
	pobj.appendChild( element );
	this.appendElementList.push( [pobj,element] );
}


// These are from Netscape's Client-Side JavaScript Guide.
// setCookie() is altered to make it easier to set expiry.

function fz_getCookie(Name) {
	var ck_search = Name + "="
	if (document.cookie.length > 0) { // if there are any cookies
		offset = document.cookie.indexOf(ck_search)
		if (offset != -1) { // if cookie exists
			offset += ck_search.length
			// set index of beginning of value
			end = document.cookie.indexOf(";", offset)
			// set index of end of cookie value
			if (end == -1)
				end = document.cookie.length
			return unescape(document.cookie.substring(offset, end))
		}
	}
}

function fz_setCookie(name, value, daysExpire) {
	if(daysExpire) {
		var expires = new Date();
		expires.setTime(expires.getTime() + 1000*60*60*24*daysExpire);
	}
	document.cookie = name + "=" + escape(value) + (daysExpire == null ? "" : (";expires=" + expires.toGMTString())) + ";path=/";
}


// ˢ����Ļ
function fz_refresh()
{
	setTimeout( _fz_refresh, 1 );
}

function _fz_refresh()
{
	window.location = location.href;
}


function fz_resolveURL( thisURL )
{
	
	// resolves 'thisURL' against this.preHREF depending on whether it's an absolute
	// or relative URL.  if 'testLocal' is set it'll return true for local (relative) URLs.
	var absoluteArray = new Array("http://", "https://", "mailto:", "ftp://", "telnet:", "news:", "gopher:", "nntp:", "javascript:", "file:");
	
	var absoluteUrl = false;
	
	if( thisURL.indexOf("$(DOMAIN)") == 0 && _top != null ){
		if( _top.domainHost == '' ){
			thisURL = thisURL.substring(9);
		}
		else{
			absoluteUrl = true;
			thisURL = _top.domainHost + thisURL.substring(9);
		}
	}
	else{
		for(var i = 0; i < absoluteArray.length; i++) {
			if(thisURL.indexOf(absoluteArray[i]) == 0) {
				absoluteUrl = true;
				break;
			}
		}
	}
	
	if( absoluteUrl == false ){
		if(thisURL.charAt(0) == '/'){
			if( thisURL.indexOf(this.contextPath+'/') != 0 ){
				if( this.contextPath.substring(this.contextPath.length-1) == "/" ){
					thisURL = this.contextPath + thisURL.substring(1);
				}
				else{
					thisURL = this.contextPath + thisURL;
				}
			}
		}
		else if(thisURL.indexOf("../") == 0) {
			var parentPath = this.preHREF;
			
			do {
				thisURL = thisURL.substr(3);
				parentPath = parentPath.substr(0, parentPath.lastIndexOf("/", parentPath.length-2) +1);
			} while(thisURL.indexOf("../") == 0);
			
			thisURL = parentPath + thisURL;
		}
		else {
			thisURL = this.contextPath + menuPath + thisURL;
		}
	}
	
	//
	if( absoluteUrl ){
		if( thisURL.indexOf(this.hostAddr) != 0 ){
			// ???SSO TOKEN
			var token = this.getCookie( "SSOTOKEN" );
			if( token != null && token != '' ){
				var ptr = thisURL.indexOf( '?' );
				if( ptr > 0 ){
					thisURL = thisURL + "&SSOTOKEN=" + token;
				}
				else{
					thisURL = thisURL + "?SSOTOKEN=" + token;
				}
			}
		}
	}
	
	return thisURL;
}

function fz_copyCssStyle( doc )
{
	var styleObject
	for(var i = 0; i < doc.styleSheets.length; i++) {
		document.createStyleSheet();
		styleObject = document.styleSheets(document.styleSheets.length-1);
		styleObject.styleSheets(i).cssText = doc.styleSheets(i).cssText;
	}
}

// �������ƻ�ȡcss
function fz_getCssStyle( name )
{
	if( name == null || name == '' ){
		return null;
	}
	
	// �Ƚ�Сд
	name = name.toLowerCase();
	
	var rules;
	var ruleName;
	var styles = document.styleSheets;
	for( var i=0; i<styles.length; i++ ){
		rules = styles[i].rules;
		if( rules == null ){
			rules = styles[i].cssRules;
		}
		
		for( var j=0; j<rules.length; j++ ){
			ruleName = rules[j].selectorText.toLowerCase();
			if( ruleName == name || ruleName == "." + name ){
				return rules[j].style;
			}
		}
	}
	
	return null;
}


// ȡ�������������:�Ȳ������ԣ�Ȼ��style��������css
function getObjectStyleValue( obj, attrName )
{
	// ȡ�����ֵ
	var attrValue = obj[attrName];
	if( attrValue != null && attrValue != '' ){
		return attrValue;
	}
	
	// ��style����
	attrValue = obj.style[attrName];
	if( attrValue != null && attrValue != '' ){
		return attrValue;
	}
	
	// ��styleClass
	var win = obj.document.parentWindow;
	var className = obj.className;
	if( className != null ){
		var css = win._browse.getCssStyle( className );
		if( css != null ){
			attrValue = css[attrName];
			if( attrValue != null && attrValue != '' ){
				return attrValue;
			}
		}
	}
	
	// û���ҵ�
	return null;
}



// ������
function appendTableRow( table, rowId, data, rowNumber )
{
	// ������б��
	if( rowNumber == null || rowNumber < 0 || rowNumber > table.rows.length ){
		rowNumber = table.rows.length;
	}
	else if( rowNumber == 0 ){
		rowNumber = 1;
	}
	
	// ������
	var myRow = table.insertRow( rowNumber );
	myRow.id = rowId;
	
	var ii;
	for( ii=0; ii<data.length; ii++ ){
		var myCell = myRow.insertCell( myRow.cells.length );
		myCell.noWrap = true;
		myCell.innerHTML = data[ii];
		myCell.vAlign = "center";
	}
	
	return myRow;
}



//
function _getWindowOffsetTop()
{
	var topx = 0;
	var win = window;
	var parentFrame = null;
	while( win != null && (parentFrame = win.frameElement) != null ){
		topx += parentFrame.offsetTop - parentFrame.scrollTop;
		
		var obj = parentFrame;
		while (obj = obj.offsetParent){
	 		topx += obj.offsetTop - obj.scrollTop;
	 	}
	 	
	 	win = parentFrame.window;
	}
	
	return topx;
}


// �޸Ĵ��ڵĴ�С
function browse_resizeTo( width, height, centerFlag )
{
	if( width == null || height == null ){
		return;
	}
	
	// ���Ƕ��㴰�ڣ�����Ҫ������С
	if( window.top != self ){
		return;
	}
	
	// ���ַ���
	width = '' + width;
	height = '' + height;
	
	if( openWindowType == 'modal' ){
		if( width != '' && width != 0 ){
			if( width.indexOf('px') <= 0 ){
				width = width + 'px';
			}
			
			window.dialogWidth = width;
		}
		
		if( height != '' && height != 0 ){
			if( height.indexOf('px') <= 0 ){
				height = height + 'px';
			}
			
			window.dialogHeight = height;
		}
		
		if( centerFlag == true ){
			var w = window.dialogWidth;
			var iptr = w.indexOf( 'px' );
			if( iptr > 0 ){
				w = w.substring( 0, iptr );
			}
			
			var wleft = (window.screen.width - w)/2 + 'px';
			window.dialogLeft = wleft;
			
			var h = window.dialogHeight;
			var iptr = h.indexOf( 'px' );
			if( iptr > 0 ){
				h = h.substring( 0, iptr );
			}
			
			var wtop = (window.screen.height - h)/2 + 'px';
			window.dialogTop = wtop;
		}
	}
	else{
		if( width == '' || width == 0 ){
			width = 0;
		}
		else{
			var iptr = width.indexOf( 'px' );
			if( iptr > 0 ){
				width = width.substring( 0, iptr );
			}
		}
		
		if( height == 0 || height == '' ){
			height = 0;
		}
		else{
			var iptr = height.indexOf( 'px' );
			if( iptr > 0 ){
				height = height.substring( 0, iptr );
			}
		}
		
		window.resizeTo( width, height );
		
		// �Ƿ����
		if( centerFlag == true && width != 0 && height != 0 ){
			var left = (window.screen.width - width)/2;
			var wtop = (window.screen.height - height)/2;
			window.moveTo( left, wtop );
		}
	}
}


// ����ҳ��
function navigateTo( win, xurl, target, features )
{
	// �Ƿ�ָ����ҳ���ַ
	if( xurl == null || xurl == '' ){
		return;
	}
	
	// ��ʽ����ַ
	xurl = _browse.resolveURL( xurl );
	
	// ȡ������
	if( xurl.match('freeze.main') ){
		href = _getHrefParameter( xurl, 'page' );
		var txnCode = _getTxnCodeFromHref( href );
		
		var txnName = _getHrefParameter( xurl, 'cmd' );
		ptr = txnName.lastIndexOf( '/' );
		if( ptr > 0 ){
			txnName = txnName.substring( ptr+1 );
		}
	}
	else{
		var txnCode = _getTxnCodeFromHref( xurl );
		var txnName = 'NA';
	}
	
	// ������־
	if( txnCode != null ){
		// ��ˮ��
		var flowId = _getHrefParameter( xurl, 'inner-flag:flowno' );
		if( flowId == null ){
			var	dd = new Date();
			var stamp = '' + dd.getTime();
			var ptr = stamp.length - 10;
			flowId = stamp.substring( ptr );
			
			xurl = _addHrefParameter( xurl, 'inner-flag:flowno', flowId );
			_browse.addOperatorLogger( flowId, txnCode, txnName, true, null );
		}
	}
	
	// �򿪴���
	if( target == null || target == '' || target == "_self" ){
		if( win.tagName != null && win.tagName.toLowerCase() == 'iframe' ){
			xurl = _setUrlOpenType( xurl, "subframe" );
			setTimeout( function(){win.src = xurl;}, 1 );
		}
		else{
			//
			try{
				var fe = win.frameElement.tagName;
				var type = win.openWindowType;
			}
			catch( e ){
				var fe = null;
				var type = 'window';
			}
			
			if( fe != null && fe.toLowerCase() == 'iframe' ){
				xurl = _setUrlOpenType( xurl, "subframe" );
				setTimeout( function(){win.location = xurl;}, 1 );
			}
			else{
				xurl = _setUrlOpenType( xurl, type );
				setTimeout( function(){win.location = xurl;}, 1 );
			}
		}
	}
	else if( target == "_blank" ){
		xurl = _setUrlOpenType( xurl, 'new-window' );
		if( features != null && features != '' ){
			win.open( xurl, null, features );
		}
		else{
			win.open( xurl );
		}
	}
	else if( target=="_top" || target=="window" ){
		setTimeout( function(){win.top.location = xurl;}, 1 );
	}
	else if( win.top[target] ){
		setTimeout( function(){win.top[target].location = xurl;}, 1 );
	}
	else{
		// �Ƿ�iframe
		var fr = null;
		for( var ii=0; ii < win.frames.length; ii++ ){
			if( win.frames[ii].name == target ){
				fr = win.frames[ii];
				break;
			}
		}
		
		if( fr ){
			xurl = _setUrlOpenType( xurl, 'subframe' );
			setTimeout( function(){fr.location = xurl;}, 1 );
		}
		else{
			xurl = _setUrlOpenType( xurl, 'new-window' );
			target = target.replace('-', '_');
			
			if( features != null && features != '' ){
				win.open( xurl, target, features );
			}
			else{
				win.open( xurl, target );
			}
		}
	}
}


// ���ô򿪴��ڵķ�ʽ��
function _setUrlOpenType( addr, openType )
{
	var iptr = addr.indexOf( 'inner-flag:open-type=' );
	if( iptr > 0 ){
		var temp = addr.substring( iptr );
		addr = addr.substring( 0, iptr );
		
		iptr = temp.indexOf( '&' );
		if( iptr > 0 ){
			addr = addr + 'inner-flag:open-type=' + openType + temp.substring(iptr);
		}
		else{
			addr = addr + 'inner-flag:open-type=' + openType;
		}
	}
	else{
		if( addr.indexOf('?') > 0 ){
			addr = addr + '&inner-flag:open-type=' + openType;
		}
		else{
			addr = addr + '?inner-flag:open-type=' + openType;
		}
	}
	
	return addr;
}


// ��HREF��ȡ������
function _getTxnCodeFromHref( href )
{
	var ptr = href.indexOf( '?' );
	if( ptr > 0 ){
		href = href.substring( 0, ptr );
	}
	
	ptr = href.indexOf( '.' );
	if( ptr > 0 ){
		var postfix = href.substring( ptr );
		if( postfix == '.do' ){
			href = href.substring( 0, ptr );
			ptr = href.lastIndexOf( '/' );
			if( ptr >= 0 ){
				href = href.substring( ptr+1 );
			}
			
			if( href.indexOf('txn') == 0 ){
				href = href.substring( 3 );
			}
			
			return href;
		}
	}
	
	return null;
}

// ��URL��ȡ����
function _getHrefParameter( href, name )
{
	var ptr = href.indexOf( '?' );
	if( ptr >= 0 ){
		href = href.substring( ptr+1 );
	}
	
	var parameters = href.split( '&' );
	for( var ii=0; ii<parameters.length; ii++ ){
		ptr = parameters[ii].indexOf( '=' );
		if( ptr > 0 ){
			var n = parameters[ii].substring( 0, ptr );
			if( n == name ){
				var value = parameters[ii].substring( ptr+1 );
				return value.trim();
			}
		}
	}
	
	return null;
}

// ��URL�����ӱ���
function _addHrefParameter( href, name, value )
{
	// �滻������
	value = '' + value;
 	value = value.replace(/[\']/g, '"' );	// '"
 	
 	// ���ӷָ��
 	if( href.indexOf('?') > 0 ){
 		href += '&'
 	}
 	else{
 		href += '?'
 	}
 	
 	// ����
 	href += name + '=' + value;
 	return href;
}


// ������ �� XBM ͼ��

// image/x-xbitmap
var xbmDigital = [ 
	[ "0x3c", "0x66", "0xc3", "0xc3", "0xc3", "0xc3", "0xc3", "0xc3", "0x66", "0x3c" ],
	[ "0x18", "0x1c", "0x18", "0x18", "0x18", "0x18", "0x18", "0x18", "0x18", "0x7e" ],
	[ "0x3c", "0x66", "0x60", "0x60", "0x30", "0x18", "0x0c", "0x06", "0x06", "0x7e" ],
	[ "0x3c", "0x66", "0xc0", "0x60", "0x1c", "0x60", "0xc0", "0xc0", "0x66", "0x38" ],
	[ "0x38", "0x3c", "0x36", "0x33", "0x33", "0x33", "0xff", "0x30", "0x30", "0xfe" ],
	[ "0xfe", "0xfe", "0x06", "0x06", "0x3e", "0x60", "0xc0", "0xc3", "0x66", "0x3c" ],
	[ "0x60", "0x30", "0x18", "0x0c", "0x3e", "0x63", "0xc3", "0xc3", "0x66", "0x3c" ],
	[ "0xff", "0xc0", "0x60", "0x30", "0x18", "0x18", "0x18", "0x18", "0x18", "0x18" ],
	[ "0x3c", "0x66", "0xc3", "0x66", "0x3c", "0x66", "0xc3", "0xc3", "0x66", "0x3c" ],
	[ "0x3c", "0x66", "0xc3", "0xc3", "0x66", "0x3c", "0x18", "0x0c", "0x06", "0x03" ]
];

// ��������ͼƬ
function createXbmImage( num, length )
{
	var i, j;
	var digtal = "";
	
	// ��ʽ��
	if( num.length < length ){
		for( i = 0; i < length - num.length; i++ ){
			digital = digital + "0";
		}
		
		digital = digital + num;
	}
	else if( num.length > length ){
		digital = num.substring( 0, length );
	}
	else{
		digital = num;
	}
	
	// �����Ⱥ͸߶�
	var width = 8 * length;
	var height = 10;
	
	var sort = new Array();
	for( i=0; i<length; i++ ){
		sort[i] = digital.substring( i, 1 );
	}
	
	var hc = chr(13) & chr(10);
	var data = "#define counter_width " + width + hc
	data = data + "#define counter_height " + height + hc
	data = data + "static unsigned char counter_bits[] = {" + hc

	for( i=0; i<height; i++ ){
		for( j=0; j<length; j++ ){
			data = data + xbmDigital[sort[j]][i] + ",";
		}
	}
	
	data = data.substring(0, data.length-1);
	data = data + "};" + hc;
	
	return data;
}

// ������
function buildBinStr(decValue)
{
	var i;
	var tempBinary=''; 
	var tempChar='';
	
	// Insert UPC Start Code 
	tempBinary=tempBinary+upcStartEndCode;
	
	// Insert 6 Left User Values 
	for (i =0; i < 6; i++){
		tempChar=parseInt(decValue.charAt(i));
		tempBinary=tempBinary+upcLeftValues[tempChar].toString();
	}
	
	// Insert UPC Center Code 
	tempBinary=tempBinary+upcCenterCode; 
	
	// Insert 6 Right User Values 
	for (i =6; i < 12; i++) {
		tempChar=parseInt(decValue.charAt(i));
		tempBinary=tempBinary+upcRightValues[tempChar].toString();
	}
	
	// Insert UPC End Code
	tempBinary=tempBinary+upcStartEndCode;
	return tempBinary;
}

// Convert Binary Barcode to XBM Compatible Hex
function buildHexStr(binValue)
{
	var i;
	tempOctet='';
	tempHexStr='';
	
	for (i =0; i < 12; i++) {
		start=i*8;
		end=(i*8)+8;
		tempOctet=binValue.substring(start,end);
		tempLeft=tempOctet.charAt(0)*1+tempOctet.charAt(1)*2+tempOctet.charAt(2)*4+tempOctet.charAt(3)*8;
		tempRight=tempOctet.charAt(4)*1+tempOctet.charAt(5)*2+tempOctet.charAt(6)*4+tempOctet.charAt(7)*8;
		tempHex="0x"+xbmHexValues[tempRight]+xbmHexValues[tempLeft];
		tempHexStr=tempHexStr+tempHex;
		if (i<11){
			tempHexStr=tempHexStr+', ';
		}
	}
	
	return tempHexStr;
}


//
function formatClassName( className )
{
	var r = '';
	var iptr = className.lastIndexOf( '.' );
	if( iptr > 0 ){
		r = className.substring( 0, iptr+1 );
		className = className.substring( iptr+1 );
	}
	
	var sn = className.split( '_' );
	for( var ii=0; ii<sn.length; ii++ ){
		var c = sn[ii];
		r = r + c.substring(0,1).toUpperCase() + c.substring(1);
	}
	
	return r;
}

// add By WeiQiang 2008-11-14  Line 1971-1986
(function(){
    var ua = navigator.userAgent.toLowerCase();

    var isOpera = ua.indexOf("opera") > -1,
        isIE = !isOpera && ua.indexOf("msie") > -1,
        isIE7 = !isOpera && ua.indexOf("msie 7") > -1;

    // remove css image flicker
	if(isIE && !isIE7){
        try{
            document.execCommand("BackgroundImageCache", false, true);
        }catch(e){
        }
    }
})();

//-->
