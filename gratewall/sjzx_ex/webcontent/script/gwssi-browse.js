<!--

// 当前处理的层
var	browseLayer = null;
var	browseFrame = null;

// 当前的browse域名称
var _openBrowseFiled = null;
var _openBrowseFiledIndex = 0;

// 当前的onmousedown
var	_oldOnmousedown = null;

// tree下拉框，是否允许选择节点
var _browse_enabledNodeSelect = true;

// 向下拉框传递的参数
function _BrowseParameter( show, selectList, codebox, namebox, mixbox )
{
	this.show = show;
	this.selectList = selectList;
	this.codebox = codebox;
	this.namebox = namebox;
	this.mixbox = mixbox;
}

// 通过按钮打开的下拉框
function _SelectParameter( selectList, selectedValue, showColumn, selectFunction )
{
	this.show = "mix"
	this.selectList = selectList;
	this.selectedValue = selectedValue;
	this.showColumn = showColumn;
	this.selectFunction = selectFunction;
}

// 向innerWindow传递的参数的全局变量
var _browseParameter = null;

// 打开下拉框
function openBrowse( fieldName, index )
{
	// 取选择项信息
	var	field = getSelectInfoByFieldName( fieldName );
	if( field == null ) return;
	
	// 如果已经打开，关闭
	if( _openBrowseFiled == field && _openBrowseFiledIndex == index ){
		_openBrowseFiled = null;
		_openBrowseFiledIndex = -1;
		hiddenBrowse();
		return;
	}
	
	// 保存打开的browse信息
	_openBrowseFiled = field;
	_openBrowseFiledIndex = index;
	
	// index
	index = '@' + index;
	
	// 取ID
	var	codebox = '';
	if( field.codeBox != null && field.codeBox != '' ){
		codebox = field.codeBox + index;
	}
	else if( field.showType == 'code' ){
		codebox = '_tmp_' + field.fieldName + index;
	}
	
	var	namebox = '';
	if( field.nameBox != null && field.nameBox != '' ){
		namebox = field.nameBox + index;
	}
	else if( field.showType == 'name' ){
		namebox = '_tmp_' + field.fieldName + index;
	}
	
	var	mixbox = '';
	if( field.mixBox != null && field.mixBox != '' ){
		mixbox = field.mixBox + index;
	}
	else if( field.showType == 'mix' ){
		mixbox = '_tmp_' + field.fieldName + index;
	}
	
	// 域的名称
	codebox = field.getFullName( codebox );
	namebox = field.getFullName( namebox );
	mixbox = field.getFullName( mixbox );
	
	// 判断是否已经被禁止
	var	obj = fz_getShowBox( field.showType, codebox, namebox, mixbox );
	if( obj == null || obj.disabled == true || obj.readOnly == true ){
		return;
	}
	
	var multiple = false;
	if( obj.multiple == 'true' ){
		multiple = true;
	}
	
	// 列表信息
	var	list = field.selectList;
	if( field.parameter != null && field.parameter != '' ){
		// 判断是否需要重新下载数据:下载条件变化 或者 下载参数变化 都需要重新下载
		var optName = field.optionName;
		var p = eval( field.parameter );
		
		if( list == null || list.length < 1 ){
			list = p;
			field.loader_parameter = list;
		}
		else if( p != field.loader_parameter || optName != field.optionName ){
			list = p;
			field.loader_parameter = list;
			field.selectList = null;
		}
	}
	else if( field.loader_parameter != field.optionName ){
		list = "";
		field.loader_parameter = field.optionName;
	}
	
	// 先关闭原先的窗口
	hiddenBrowse();
	
	// 判断是否有列表
	if( list == null ) return;
	
	// 查询本地的SELECT列表，看是否已经下载了参数
	if( isArray(list) == false ){
		for( ii=_selectFieldList.length-1; ii>=0; ii-- ){
			var sf = _selectFieldList[ii];
			if( sf != field && sf.selectList != null && isArray(sf.selectList) && sf.loader_parameter == field.loader_parameter && sf.optionName == field.optionName ){
				list = sf.selectList;
				field.selectList = sf.selectList;
				break;
			}
		}
	}
	
	if( isArray(list) == false ){
		// 需要实时下载代码；selectList 是输入的匹配参数
		fz_openDelay( field, list, multiple, codebox, namebox, mixbox );
	}
	else{
		openBrowse1( field, list, multiple, codebox, namebox, mixbox );
	}
}




// 浏览框 field=选择域的定义信息
// multiple 是否多选
function openBrowse1( field, selectList, multiple, codebox, namebox, mixbox )
{
	// 判断是否有列表
	if( selectList.length == 0 ) return;
	
	// 判断是否有层次关系
	var treeFlag = false;
	for( var ii=selectList.length-1; ii>=0; ii-- ){
		if( selectList[ii][3] != '' ){ treeFlag = true; _addTreeSortName(selectList); break; }
	}
	
	// 取地址
	var	addr = rootPath + "/script/browsebox/";
	if( multiple ){
		if( treeFlag ) addr += "pop_tree_multiple_new.html";
		else addr += "pop_select_multiple_new.html";
	}
	else{
		if( treeFlag ) addr += "pop_tree_one_new.html";
		else addr += "pop_select_one_new.html";
	}
	
	// 显示的域
	var	obj = fz_getShowBox( field.showType, codebox, namebox, mixbox );
	
	// 清除内容
	browseLayer = fz_getBrowseById("innerWindow");
	browseLayer.innerHTML = "";
	
	// 设置宽度和高度
	if( treeFlag ){
		var maxHeight = getIframeMaxHeight( obj );
		if( maxHeight > 330 ) maxHeight = 330;
		
		if( multiple ){
			if( maxHeight > selectList.length*18 + 27 ) maxHeight = selectList.length * 18 + 27;
		}
		else{
			if( maxHeight > selectList.length*18 ) maxHeight = selectList.length * 18;
		}
		
		browseLayer.style.height = (maxHeight > 236) ? maxHeight : 236;
		
		// 宽度，设置成域的宽度
		if( obj.offsetWidth > 250 ){
			browseLayer.style.width = obj.offsetWidth;
		}
		else{
			browseLayer.style.width = 250;
		}
	}
	else if( multiple ){
		browseLayer.style.height = 236;
		browseLayer.style.width = 370;
	}
	else{
		browseLayer.style.height = 231;
		
		// 宽度，设置成域的宽度
		if( obj.offsetWidth > 250 ){
			browseLayer.style.width = obj.offsetWidth;
		}
		else{
			browseLayer.style.width = 250;
		}
	}
	
	// 过虑
	var	list = fz_filterBrowseList( field, obj );
	if( list == null ) list = selectList;
	if( list == null || list.length == 0 ) return;
	
	// 初始化
	_browseParameter = new _BrowseParameter( field.showType, list, codebox, namebox, mixbox );
	
	// 显示
	browseLayer.style.display = "";
	
	var needHeight = parseInt(browseLayer.style.height) + getElementPos(obj).y + obj.offsetHeight;
    if(document.body.offsetHeight < needHeight){
    	testResizeFrame(null, needHeight);
  	}
    
	// 定位
	if( obj != null ){
		setBrowsePosition( obj );
		fz_reShowFieldHint( obj );
	}
	
	//鼠标离开自动隐藏
	_oldOnmousedown = document.onmousedown;
	document.onmousedown = hiddenBrowse;
	
	// 加载内容
	setTimeout( function(){ __delay_openInnerWindow(addr) }, 1 );
}

// 选择框
// multiple 是否多选
// selectList 可选择的列表
// selectedValue 已经选择的列表
// showColumn 显示的列：在selectList数组中的那些字段需要显示，比如：[1;3] ==> 第二列和第四列
// selectFunction 返回以前执行的函数
// inputField 输入域
function openSelect( multiple, selectList, selectedValue, showColumn, selectFunction, inputField )
{
	// 先关闭原先的窗口
	hiddenBrowse();
	
	// 取输入域对象
	var	inputObject = null;
	if( inputField != null ){
		if( typeof(inputField) == 'string' ){
			inputObject = document.getElementById( inputField );
		}
		else{
			inputObject = inputField;
		}
	}
	
	if( inputObject == null && window.event != null ){
		inputObject = window.event.srcElement;
	}
	
	// 取选中的值
	if( inputObject != null ){
		selectedValue = inputObject.value;
	}
	
	// 生成参数
	_browseParameter = new _SelectParameter( selectList, selectedValue, showColumn, selectFunction );
	
	// 取地址
	if( multiple ){
		var	addr = rootPath + "/script/browsebox/select_multiple_record_new.html";
	}
	else{
		var	addr = rootPath + "/script/browsebox/select_one_record_new.html";
	}
	
	// 清除内容
	browseLayer = fz_getBrowseById("innerWindow");
	browseLayer.innerHTML = "";
	
	// 设置宽度和高度
	if( multiple ){
		browseLayer.style.height = 236;
		browseLayer.style.width = 370;
	}
	else{
		browseLayer.style.height = 222;
		browseLayer.style.width = 250;
		
		// 设置成输入域的宽度
		if( inputObject != null && inputObject.offsetWidth > 250 ){
			browseLayer.style.width = inputObject.offsetWidth;
		}
	}
	
	// 显示
	browseLayer.style.display = "";
	
	// 定位
	if( inputObject != null ){
		setBrowsePosition( inputObject );
		fz_reShowFieldHint( inputObject );
	}
	
	//鼠标离开自动隐藏
	_oldOnmousedown = document.onmousedown;
	document.onmousedown = hiddenBrowse;
	
	// 加载内容
	setTimeout( function(){ __delay_openInnerWindow(addr) }, 1 );
}

// 从服务器下载数据
// multiple 是否多选
function fz_openDelay( field, parameter, multiple, codebox, namebox, mixbox )
{
	// 请求页面
	if( field.optionName == null || field.optionName == '' ){
		return;
	}
	
	// 清除内容
	browseLayer = fz_getBrowseById("innerWindow");
	browseLayer.innerHTML = "";
	
	// 取输入域
	var	obj = fz_getShowBox( field.showType, codebox, namebox, mixbox );
	
	// 设置宽度和高度
	if( multiple ){
		browseLayer.style.width = 370;
		browseLayer.style.height = 236;
	}
	else{
		browseLayer.style.height = 233;
		
		// 宽度，设置成域的宽度
		if( obj.offsetWidth > 250 ){
			browseLayer.style.width = obj.offsetWidth;
		}
		else{
			browseLayer.style.width = 250;
		}
	}
	
	// 显示
	browseLayer.style.display = "";
	var needHeight = parseInt(browseLayer.style.height) + getElementPos(obj).y + obj.offsetHeight;
    if(document.body.offsetHeight < needHeight){
    	testResizeFrame(null, needHeight);
  	}
	
	// 定位
	if( obj != null ){
		setBrowsePosition( obj );
		fz_reShowFieldHint( obj );
	}
	
	//鼠标离开自动隐藏
	_oldOnmousedown = document.onmousedown;
	document.onmousedown = hiddenBrowse;
	
	// 取地址
	var	addr = fz_getPageName( field, codebox, namebox, mixbox, parameter, multiple );
	
	// 加载内容
	setTimeout( function(){ __delay_openInnerWindow(addr) }, 1 );
}

// 取请求的页面
// show,codebox,namebox,mixbox,parameter,pagename,option-name
function fz_getPageName( field, codebox, namebox, mixbox, parameter, multiple )
{
	if( parameter == null ){
		parameter = "";
	}
	
	var name = field.getFullName( field.fieldName );
	
	// 参数
	var	param = "show=" + field.showType
		 + "&codebox=" + codebox
		 + "&namebox=" + namebox
		 + "&mixbox=" + mixbox
		 + "&name=" + name
		 + "&option-name=" + field.optionName;
	
	// 参数中是否已经包含了页面地址
	var s = (window.rootPath == '') ? '/' : window.rootPath;
	if( parameter.indexOf(s) == 0 ){
		// 指定的显示页面
		return (parameter.indexOf('?') > 0) ? parameter + "&" + param : parameter + "?" + param;
	}
	
	// 缺省的选择框
	var addr = window.rootPath + "/tag.service?txn-code=option&" + param;
	
	if( parameter != null ){
		parameter = parameter.replace( /[=]/g, '%3D' );
		parameter = parameter.replace( /[&]/g, '%26' );
 		parameter = parameter.replace( /[#]/g, '%23' );
	}
	
	addr = addr + "&parameter=" + parameter;
	
	if( multiple == true ){
		addr = addr + "&pagename=/script/browsebox/delay_select_multiple.jsp";
	}
	else{
		addr = addr + "&pagename=/script/browsebox/delay_select_one.jsp";
	}
	
	return addr;
}



// 定位
function setBrowsePosition( obj )
{
	setIframePosition( browseLayer, obj );
}


function fz_getShowBox( show, codebox, namebox, mixbox ){
	var name;
	if( show == 'name' ){
		name = namebox;
	}
	else if ( show == 'mix' ){
		name = mixbox;
	}
	else{
		name = codebox;
	}
	
	if( name == null || name == '' ){
		return null;
	}
	
	var index = 0;
	var ptr = name.lastIndexOf( '@' );
	if( ptr > 0 ){
		index = name.substring( ptr+1 );
		name = name.substring( 0, ptr );
	}
	
	var objs = window.document.getElementsByName( name );
	if( objs.length <= index ){
		return null;
	}
	
	return objs[index];
}



function fz_getBrowseById(id)
{ 
	if(document.all){
		return(eval("document.all."+ id));
	}
	else{
		return(eval(id)); 
	}
}


// 隐藏域
function hiddenBrowse()
{
	// 判断是否为打开的BROWSE
	if( window.event != null && window.event.srcElement != null ){
		if( isCurrentOpenBrowse(window.event.srcElement) && window.event.srcElement.onclick != null ){
			return;
		}
	}
	
	_openBrowseFiled = null;
	_openBrowseFiledIndex = -1;
	
	// 关闭
	if( browseLayer != null ){
		browseLayer.style.display = "none";
		browseLayer = null;
		
		// 恢复触发事件监听
		if( _oldOnmousedown != null ){
			document.onmousedown = _oldOnmousedown;
			_oldOnmousedown();
		}
	}
}


// 判断是否为打开的BROWSE
function isCurrentOpenBrowse( obj )
{
	if( _openBrowseFiled != null && _openBrowseFiledIndex >= 0 ){
		// 判断是否和当前打开的域一致
		var	showBox = _openBrowseFiled.getSelectShowBoxName( );
		var imgBox = 'img:' + showBox
		
		// 域
		var objs = document.getElementsByName( showBox );
		if( objs.length > _openBrowseFiledIndex ){
			if( obj == objs[_openBrowseFiledIndex] ){
				return true;
			}
		}
		
		// 按钮
		objs = document.getElementsByName( imgBox );
		if( objs.length > _openBrowseFiledIndex ){
			if( obj == objs[_openBrowseFiledIndex] ){
				return true;
			}
		}
	}
	
	return false;
}



// 展开日期域和浏览域
function openBrowseFieldBox( obj )
{
	if( obj == null || obj.name == null || obj.name == '' ){
		return;
	}
	
	// 取名称
	var	objName = obj.name;
	
	// 触发事件
	var	objs = document.getElementsByName( 'img:' + objName );
	if( objs.length > 0 ){
		var	index = getFormFieldIndex( obj );
		if( objs.length > index ){
			objs[index].fireEvent( 'onclick' );
		}
	}
}


// 过虑browse的选择字段
function fz_filterBrowseList( field, obj )
{
	// 是否配置了过滤函数
	if( field.filter == '' ) return;
	
	var	filterList = new Array();
	var	selectedValue = obj.value;
	
	// 过虑
	var	index = getFormFieldIndex( obj );
	for( var x=0; x<field.selectList.length; x++ ){
		data = field.selectList[x];
		
		if( field.showType == 'code' ){
			optText = data[0];
		}
		else if( field.showType == 'mix' ){
			optText = data[0] + '--' + data[1];
		}
		else{
			optText = data[1];
		}
		
		if( selectedValue.indexOf(optText) >= 0 ){
			filterList.push( data );
		}
		else{
			flag = eval( field.filter + '( data, index )' );
			if( flag == true ){
				filterList.push( data );
			}
		}
	}
	
	return filterList;
}

// 增加目录中使用的临时节点
function _addTreeSortName( selectList )
{
	var len = selectList.length;
	if( selectList[len-1][0] == '@' ) return;
	
	// 增加分类字段
	var sortNameList = new Array();
	for( var ii=0; ii<len; ii++ ){
		var sn = selectList[ii][3];
		if( sn == '' ) continue;
		
		var f = false;
		for( var xx=0; xx<sortNameList.length; xx++ ){
			if( sortNameList[xx][0] == sn ){
				f = true;
				break;
			}
		}
		
		if( f == false ){
			for( var xx=0; xx<len; xx++ ){
				if( selectList[xx][0] == sn ){
					if( _browse_enabledNodeSelect == false ) selectList[xx][2] = false;
					f = true;
					break;
				}
			}
			
			sortNameList.push( [sn, f] );
		}
	}
	
	for( var xx=0; xx<sortNameList.length; xx++ ){
		if( sortNameList[xx][1] == false ){
			var n = sortNameList[xx][0];
			selectList.unshift( [n, n, false, '', ''] );
		}
	}
	
	selectList.push( ['@', '@', false, '', ''] );
}



//-->

