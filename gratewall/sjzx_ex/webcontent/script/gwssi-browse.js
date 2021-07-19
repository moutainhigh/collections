<!--

// ��ǰ����Ĳ�
var	browseLayer = null;
var	browseFrame = null;

// ��ǰ��browse������
var _openBrowseFiled = null;
var _openBrowseFiledIndex = 0;

// ��ǰ��onmousedown
var	_oldOnmousedown = null;

// tree�������Ƿ�����ѡ��ڵ�
var _browse_enabledNodeSelect = true;

// �������򴫵ݵĲ���
function _BrowseParameter( show, selectList, codebox, namebox, mixbox )
{
	this.show = show;
	this.selectList = selectList;
	this.codebox = codebox;
	this.namebox = namebox;
	this.mixbox = mixbox;
}

// ͨ����ť�򿪵�������
function _SelectParameter( selectList, selectedValue, showColumn, selectFunction )
{
	this.show = "mix"
	this.selectList = selectList;
	this.selectedValue = selectedValue;
	this.showColumn = showColumn;
	this.selectFunction = selectFunction;
}

// ��innerWindow���ݵĲ�����ȫ�ֱ���
var _browseParameter = null;

// ��������
function openBrowse( fieldName, index )
{
	// ȡѡ������Ϣ
	var	field = getSelectInfoByFieldName( fieldName );
	if( field == null ) return;
	
	// ����Ѿ��򿪣��ر�
	if( _openBrowseFiled == field && _openBrowseFiledIndex == index ){
		_openBrowseFiled = null;
		_openBrowseFiledIndex = -1;
		hiddenBrowse();
		return;
	}
	
	// ����򿪵�browse��Ϣ
	_openBrowseFiled = field;
	_openBrowseFiledIndex = index;
	
	// index
	index = '@' + index;
	
	// ȡID
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
	
	// �������
	codebox = field.getFullName( codebox );
	namebox = field.getFullName( namebox );
	mixbox = field.getFullName( mixbox );
	
	// �ж��Ƿ��Ѿ�����ֹ
	var	obj = fz_getShowBox( field.showType, codebox, namebox, mixbox );
	if( obj == null || obj.disabled == true || obj.readOnly == true ){
		return;
	}
	
	var multiple = false;
	if( obj.multiple == 'true' ){
		multiple = true;
	}
	
	// �б���Ϣ
	var	list = field.selectList;
	if( field.parameter != null && field.parameter != '' ){
		// �ж��Ƿ���Ҫ������������:���������仯 ���� ���ز����仯 ����Ҫ��������
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
	
	// �ȹر�ԭ�ȵĴ���
	hiddenBrowse();
	
	// �ж��Ƿ����б�
	if( list == null ) return;
	
	// ��ѯ���ص�SELECT�б����Ƿ��Ѿ������˲���
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
		// ��Ҫʵʱ���ش��룻selectList �������ƥ�����
		fz_openDelay( field, list, multiple, codebox, namebox, mixbox );
	}
	else{
		openBrowse1( field, list, multiple, codebox, namebox, mixbox );
	}
}




// ����� field=ѡ����Ķ�����Ϣ
// multiple �Ƿ��ѡ
function openBrowse1( field, selectList, multiple, codebox, namebox, mixbox )
{
	// �ж��Ƿ����б�
	if( selectList.length == 0 ) return;
	
	// �ж��Ƿ��в�ι�ϵ
	var treeFlag = false;
	for( var ii=selectList.length-1; ii>=0; ii-- ){
		if( selectList[ii][3] != '' ){ treeFlag = true; _addTreeSortName(selectList); break; }
	}
	
	// ȡ��ַ
	var	addr = rootPath + "/script/browsebox/";
	if( multiple ){
		if( treeFlag ) addr += "pop_tree_multiple_new.html";
		else addr += "pop_select_multiple_new.html";
	}
	else{
		if( treeFlag ) addr += "pop_tree_one_new.html";
		else addr += "pop_select_one_new.html";
	}
	
	// ��ʾ����
	var	obj = fz_getShowBox( field.showType, codebox, namebox, mixbox );
	
	// �������
	browseLayer = fz_getBrowseById("innerWindow");
	browseLayer.innerHTML = "";
	
	// ���ÿ�Ⱥ͸߶�
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
		
		// ��ȣ����ó���Ŀ��
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
		
		// ��ȣ����ó���Ŀ��
		if( obj.offsetWidth > 250 ){
			browseLayer.style.width = obj.offsetWidth;
		}
		else{
			browseLayer.style.width = 250;
		}
	}
	
	// ����
	var	list = fz_filterBrowseList( field, obj );
	if( list == null ) list = selectList;
	if( list == null || list.length == 0 ) return;
	
	// ��ʼ��
	_browseParameter = new _BrowseParameter( field.showType, list, codebox, namebox, mixbox );
	
	// ��ʾ
	browseLayer.style.display = "";
	
	var needHeight = parseInt(browseLayer.style.height) + getElementPos(obj).y + obj.offsetHeight;
    if(document.body.offsetHeight < needHeight){
    	testResizeFrame(null, needHeight);
  	}
    
	// ��λ
	if( obj != null ){
		setBrowsePosition( obj );
		fz_reShowFieldHint( obj );
	}
	
	//����뿪�Զ�����
	_oldOnmousedown = document.onmousedown;
	document.onmousedown = hiddenBrowse;
	
	// ��������
	setTimeout( function(){ __delay_openInnerWindow(addr) }, 1 );
}

// ѡ���
// multiple �Ƿ��ѡ
// selectList ��ѡ����б�
// selectedValue �Ѿ�ѡ����б�
// showColumn ��ʾ���У���selectList�����е���Щ�ֶ���Ҫ��ʾ�����磺[1;3] ==> �ڶ��к͵�����
// selectFunction ������ǰִ�еĺ���
// inputField ������
function openSelect( multiple, selectList, selectedValue, showColumn, selectFunction, inputField )
{
	// �ȹر�ԭ�ȵĴ���
	hiddenBrowse();
	
	// ȡ���������
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
	
	// ȡѡ�е�ֵ
	if( inputObject != null ){
		selectedValue = inputObject.value;
	}
	
	// ���ɲ���
	_browseParameter = new _SelectParameter( selectList, selectedValue, showColumn, selectFunction );
	
	// ȡ��ַ
	if( multiple ){
		var	addr = rootPath + "/script/browsebox/select_multiple_record_new.html";
	}
	else{
		var	addr = rootPath + "/script/browsebox/select_one_record_new.html";
	}
	
	// �������
	browseLayer = fz_getBrowseById("innerWindow");
	browseLayer.innerHTML = "";
	
	// ���ÿ�Ⱥ͸߶�
	if( multiple ){
		browseLayer.style.height = 236;
		browseLayer.style.width = 370;
	}
	else{
		browseLayer.style.height = 222;
		browseLayer.style.width = 250;
		
		// ���ó�������Ŀ��
		if( inputObject != null && inputObject.offsetWidth > 250 ){
			browseLayer.style.width = inputObject.offsetWidth;
		}
	}
	
	// ��ʾ
	browseLayer.style.display = "";
	
	// ��λ
	if( inputObject != null ){
		setBrowsePosition( inputObject );
		fz_reShowFieldHint( inputObject );
	}
	
	//����뿪�Զ�����
	_oldOnmousedown = document.onmousedown;
	document.onmousedown = hiddenBrowse;
	
	// ��������
	setTimeout( function(){ __delay_openInnerWindow(addr) }, 1 );
}

// �ӷ�������������
// multiple �Ƿ��ѡ
function fz_openDelay( field, parameter, multiple, codebox, namebox, mixbox )
{
	// ����ҳ��
	if( field.optionName == null || field.optionName == '' ){
		return;
	}
	
	// �������
	browseLayer = fz_getBrowseById("innerWindow");
	browseLayer.innerHTML = "";
	
	// ȡ������
	var	obj = fz_getShowBox( field.showType, codebox, namebox, mixbox );
	
	// ���ÿ�Ⱥ͸߶�
	if( multiple ){
		browseLayer.style.width = 370;
		browseLayer.style.height = 236;
	}
	else{
		browseLayer.style.height = 233;
		
		// ��ȣ����ó���Ŀ��
		if( obj.offsetWidth > 250 ){
			browseLayer.style.width = obj.offsetWidth;
		}
		else{
			browseLayer.style.width = 250;
		}
	}
	
	// ��ʾ
	browseLayer.style.display = "";
	var needHeight = parseInt(browseLayer.style.height) + getElementPos(obj).y + obj.offsetHeight;
    if(document.body.offsetHeight < needHeight){
    	testResizeFrame(null, needHeight);
  	}
	
	// ��λ
	if( obj != null ){
		setBrowsePosition( obj );
		fz_reShowFieldHint( obj );
	}
	
	//����뿪�Զ�����
	_oldOnmousedown = document.onmousedown;
	document.onmousedown = hiddenBrowse;
	
	// ȡ��ַ
	var	addr = fz_getPageName( field, codebox, namebox, mixbox, parameter, multiple );
	
	// ��������
	setTimeout( function(){ __delay_openInnerWindow(addr) }, 1 );
}

// ȡ�����ҳ��
// show,codebox,namebox,mixbox,parameter,pagename,option-name
function fz_getPageName( field, codebox, namebox, mixbox, parameter, multiple )
{
	if( parameter == null ){
		parameter = "";
	}
	
	var name = field.getFullName( field.fieldName );
	
	// ����
	var	param = "show=" + field.showType
		 + "&codebox=" + codebox
		 + "&namebox=" + namebox
		 + "&mixbox=" + mixbox
		 + "&name=" + name
		 + "&option-name=" + field.optionName;
	
	// �������Ƿ��Ѿ�������ҳ���ַ
	var s = (window.rootPath == '') ? '/' : window.rootPath;
	if( parameter.indexOf(s) == 0 ){
		// ָ������ʾҳ��
		return (parameter.indexOf('?') > 0) ? parameter + "&" + param : parameter + "?" + param;
	}
	
	// ȱʡ��ѡ���
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



// ��λ
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


// ������
function hiddenBrowse()
{
	// �ж��Ƿ�Ϊ�򿪵�BROWSE
	if( window.event != null && window.event.srcElement != null ){
		if( isCurrentOpenBrowse(window.event.srcElement) && window.event.srcElement.onclick != null ){
			return;
		}
	}
	
	_openBrowseFiled = null;
	_openBrowseFiledIndex = -1;
	
	// �ر�
	if( browseLayer != null ){
		browseLayer.style.display = "none";
		browseLayer = null;
		
		// �ָ������¼�����
		if( _oldOnmousedown != null ){
			document.onmousedown = _oldOnmousedown;
			_oldOnmousedown();
		}
	}
}


// �ж��Ƿ�Ϊ�򿪵�BROWSE
function isCurrentOpenBrowse( obj )
{
	if( _openBrowseFiled != null && _openBrowseFiledIndex >= 0 ){
		// �ж��Ƿ�͵�ǰ�򿪵���һ��
		var	showBox = _openBrowseFiled.getSelectShowBoxName( );
		var imgBox = 'img:' + showBox
		
		// ��
		var objs = document.getElementsByName( showBox );
		if( objs.length > _openBrowseFiledIndex ){
			if( obj == objs[_openBrowseFiledIndex] ){
				return true;
			}
		}
		
		// ��ť
		objs = document.getElementsByName( imgBox );
		if( objs.length > _openBrowseFiledIndex ){
			if( obj == objs[_openBrowseFiledIndex] ){
				return true;
			}
		}
	}
	
	return false;
}



// չ��������������
function openBrowseFieldBox( obj )
{
	if( obj == null || obj.name == null || obj.name == '' ){
		return;
	}
	
	// ȡ����
	var	objName = obj.name;
	
	// �����¼�
	var	objs = document.getElementsByName( 'img:' + objName );
	if( objs.length > 0 ){
		var	index = getFormFieldIndex( obj );
		if( objs.length > index ){
			objs[index].fireEvent( 'onclick' );
		}
	}
}


// ����browse��ѡ���ֶ�
function fz_filterBrowseList( field, obj )
{
	// �Ƿ������˹��˺���
	if( field.filter == '' ) return;
	
	var	filterList = new Array();
	var	selectedValue = obj.value;
	
	// ����
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

// ����Ŀ¼��ʹ�õ���ʱ�ڵ�
function _addTreeSortName( selectList )
{
	var len = selectList.length;
	if( selectList[len-1][0] == '@' ) return;
	
	// ���ӷ����ֶ�
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

