<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<link href="<%=request.getContextPath()%>/script/browsebox/browse.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>��ѡ�����-������browsebox���������������ʱ�����б�</title>
<script language="javascript">
function SourceDefine()
{
	this.initFlag = false;
	this.window = null;
	this.show = null;
	this.showName = false;
	this.selectList = null;
	this.codebox = null;
	this.namebox = null;
	this.mixbox  = null;
	this.fieldName = null;
	this.getElement = _browse_getElement;
}

// ��Դ�ĵ���ȡ���������
function _browse_getElement( name )
{
	var index = 0;
	var ptr = name.lastIndexOf( '@' );
	if( ptr > 0 ){
		index = name.substring( ptr+1 );
		name = name.substring( 0, ptr );
	}
	
	var objs = this.window.document.getElementsByName( name );
	if( objs.length <= index ){
		return null;
	}
	
	return objs[index];
}

var	sourceDefine = new SourceDefine();

// ��ʼ��
/*******************************
���������
1��window
2����ʾ����:name��code��mix
3�����п���ѡ��Ĳ���
4����������������
5���������Ƶ�������
6�����������ݵ�������

************************************************/
function init( win, show, selectList, codebox, namebox, mixbox, fieldName )
{
	sourceDefine.initFlag = true;
	sourceDefine.window = win;
	sourceDefine.show = show;
	sourceDefine.showName = false;
	sourceDefine.selectList = selectList;
	sourceDefine.codebox = codebox;
	sourceDefine.namebox = namebox;
	sourceDefine.mixbox = mixbox;
	sourceDefine.fieldName = fieldName;
	
	// �����б���Ϣ
	var selectDefine = win.getSelectInfoByFieldName( fieldName );
	if( selectDefine != null && selectList != null ){
		if( selectList.constructor.toString().match(/array/i) != null ){
			selectDefine.setSelectList( selectList );
		}
	}
	
	// ���ƺʹ���һ��ʱ����ʾ����
	for( var ii=0; ii<selectList.length; ii++ ){
		selectList[ii][1] = selectList[ii][1].replace(/[;]/g, '.' );
		if( selectList[ii][0] != selectList[ii][1] ){
			sourceDefine.showName = true;
		}
	}
	
	// ɾ���б�
	obj=document.getElementById('s1');
	for (x=obj.options.length-1;x>=0;x--) {
		obj.remove(x);
	}
	
	// �����б�
	createSelectItem('',getSelectedCode());
}

function getSelectedCode(){
	var name;
	if( sourceDefine.show == 'name' ){
		name = sourceDefine.namebox;
	}
	else if ( sourceDefine.show == 'mix' ){
		name = sourceDefine.mixbox;
	}
	else{
		name = sourceDefine.codebox;
	}
	
	if( name == null || name == '' ){
		return '';
	}
	
	var obj = sourceDefine.getElement(name);
	if( obj == null ){
		return '';
	}
	
	return obj.value;
}

function getAllCode(){
  return sourceDefine.selectList;
}

// ���÷���ֵ
function setCode( val ){
	var name = sourceDefine.codebox;
	if( name == null || name == '' ){
		return;
	}
	
	var obj = sourceDefine.getElement(name);
	if( obj == null ){
		return;
	}
	
	obj.value = val;
}

function setName( val ){
	var name = sourceDefine.namebox;
	if( name == null || name == '' ){
		return;
	}
	
	var obj = sourceDefine.getElement(name);
	if( obj == null ){
		return;
	}
	
	obj.value = val;
}

function setMix( val ){
	var name = sourceDefine.mixbox;
	if( name == null || name == '' ){
		return;
	}
	
	var obj = sourceDefine.getElement(name);
	if( obj == null ){
		return;
	}
	
	obj.value = val;
}

// ȡ��ʾ�������
function _getShowSelectBoxName()
{

	var name;
	if( sourceDefine.show == 'name' ){
		name = sourceDefine.namebox;
	}
	else if ( sourceDefine.show == 'mix' ){
		name = sourceDefine.mixbox;
	}
	else{
		name = sourceDefine.codebox;
	}
	
	if( name == null || name == '' ){
		return null;
	}
	
	var	obj = sourceDefine.getElement(name);
	if( obj == null ){
		return null;
	}
	
	return obj;
}

// �رմ���
function hideWindow()
{
	sourceDefine.window.hiddenBrowse();
	
	var	obj = _getShowSelectBoxName();
	if( obj != null ){
		obj.focus();
	}
}

// ����ѡ��
function addOptionValue( objName, selectList, addIndexList )
{
	if( addIndexList == null || addIndexList.length <= 0 ){
		return;
	}
	
	var	idx;
	var obj = document.getElementById( objName );
	var newOptionItem;
	
	// ��ʾ������
	var showType = sourceDefine.show;
	
	// �ж��Ƿ�mix��ʽ��ʾ
	if( sourceDefine.window._browse_showMixData ){
		if( sourceDefine.showName ){
			showType = "mix";
		}
		else{
			showType = "name";
		}
	}
	else{
		// �������ж��Ƿ�ֻ��ʾ����
		if( sourceDefine.showName == false ){
			if( showType == 'mix' ){
				showType = 'name';
			}
		}
	}
	
	for (var i=0; i<addIndexList.length; i++) {
		idx = addIndexList[i];
		if( showType == 'mix' ){
			newOptionItem = new Option(selectList[idx][0] + '--' + selectList[idx][1], idx);
		}
		else if( showType == 'name' ){
			newOptionItem = new Option(selectList[idx][1], idx);
		}
		else{
			newOptionItem = new Option(selectList[idx][0], idx);
		}
		
		obj.add( newOptionItem );
	}
}


	
// �����Ѿ�ѡ�е�����
function saveSelectedValue( objName, allFlag )
{
	var	nameList = '';
	var	codeList = '';
	var	mixList = '';
	var	i;
	var	num = 0;
	var	idx;
	
	var	allCode = getAllCode();
	var	obj = document.getElementById( objName );
	for (i=0; i<obj.options.length; i++) {
		// �ж��Ƿ�ѡ��
		if( allFlag == false && obj.options[i].selected == false ){
			continue;
		}
		
		if( num > 0 ){
			nameList = nameList + ',';
			codeList = codeList + ',';
			mixList = mixList + ',';
		}
		
		idx = obj.options[i].value;
		nameList = nameList + allCode[idx][1];
		codeList = codeList + allCode[idx][0];
		mixList = mixList + allCode[idx][0] + '--' + allCode[idx][1];
		
		// ѡ�еļ�¼����
		num = num + 1;
	}
	
	// ��������
	setCode( codeList );
	setMix( mixList );
	setName( nameList );
	
	// ����ѡ�еļ�¼����
	return num;
}




// �����޸ĺ����
function fireBrowseChange( )
{
	var	obj = _getShowSelectBoxName();
	if( obj == null ){
		return;
	}
	
	obj.focus();
	obj.fireEvent( "onchange" );
}


// �ȼ�
document.onkeydown = defaultKeydown;

function defaultKeydown()
{
	var	keyCode = window.event.keyCode;
	if( keyCode == 27 ){
		hideWindow();
	}
	else if( window.event.ctrlKey && keyCode == 83 ){
		submitPress();
	}
	else if( keyCode == 9 ){
		// ���ý���
		_nextField();
	}
}

// ��������һ����
function _nextField( )
{
	var	obj = window.event.srcElement;
	if( obj == null ){
		return;
	}
	
	var	id = obj.id;
	if( id == null ){
		return;
	}
	
	// ���ҵ�ǰ��
	var	ii;
	var	fieldList = ['filter', 's1', 'ok', 'close', 'filter'];
	
	for( ii=0; ii<fieldList.length; ii++ ){
		if( fieldList[ii] == id ){
			break;
		}
	}
	
	if( ii >= fieldList.length ){
		ii = 0;
	}
	
	// ���ý���
	var	obj = document.getElementById( fieldList[ii+1] );
	if( obj != null ){
		obj.focus();
	}
	
	window.event.keyCode = 0;
	window.event.returnValue = false;
}


	
function getValidSelectItem( filter, selectedValue )
{
	var	num = 0;
	var validList = new Array();	
	
	var	allCode = getAllCode();
	if( allCode == null ){
		return null;
	}
	
	var showType = sourceDefine.show;
	if( sourceDefine.showName == false ){
		if( showType == 'mix' ){
			showType = 'name';
		}
	}
		
	//���ѡ�еļ�¼��û�и�ֵ����ô�п���Ҫ�ŵ���ѡ��¼�б���
	for (var i=0; i<allCode.length; i++) {
		var flag = false;	
		if( filter == null || filter == '' ){
			flag = true;
		}
		else {			
 			if( showType == 'mix' ){
		 		if( allCode[i][0].indexOf(filter) >= 0 ||
	  				allCode[i][1].indexOf(filter) >= 0 ) {
			  		flag = true;
			  	}
			}
			else if( showType == 'name' ){
	  			if( allCode[i][1].indexOf(filter) >= 0 ) {
			  		flag = true;
			  	}
			}
			else{
	  			if( allCode[i][0].indexOf(filter) >= 0 ) {
			  		flag = true;
			  	}
			}
 		}
		if( !flag ){
			continue;
		}
  		
	  	// �Ƿ����ϣ�����û��ѡ�в����Ѿ����ϵ�����
	  	if( allCode[i][2] == false ){
	  		if( selectedValue == null || selectedValue == '' ){
	  			continue;
	  		}
	  		
	  		if( allCode[i][0] != selectedValue && 
	  				allCode[i][1] != selectedValue &&
	  				allCode[i][0] + '--' + allCode[i][1] != selectedValue )
	  		{
	  			continue;
	  		}
	  	}
	  	
	  	validList[num] = i;
	  	num = num + 1;
  }
  
  return validList;
}

/***************���ַ���ת��select��option��****************
�����������б�
createSelectItem()
************************************************/
function createSelectItem( filter, selectedValue ){
	// ȡ��Ч������
	var	validList = getValidSelectItem( filter, selectedValue );
	
	// �����б�
	var	allCode = getAllCode();
	addOptionValue( "s1", allCode, validList );
  	
	// �Ѿ�ѡ�е�����
	var	i, idx
	var obj = document.getElementById( "s1" );
	if( selectedValue != null && selectedValue != '' ){
	 	for( i = 0; i < obj.options.length; i++ ){
	 		idx = obj.options[i].value;
			if( allCode[idx][0] == selectedValue || 
					allCode[idx][1] == selectedValue ||
					allCode[idx][0] + '--' + allCode[i][1] == selectedValue )
			{
				obj.options[i].selected = true;
			}
		}
	}
}

// ������������Ƹı�ʱ����������
function recreateSelectItem()
{
	if(event.keyCode == 13 || event.keyCode == 9){
		document.getElementById('s1').focus();
		return false;
	}
	
	var	allCode = getAllCode();
	var	selectedValue = null;
	obj=document.getElementById('s1');
	for(var x=obj.options.length-1;x>=0;x-- ){
		if( obj.options[x].selected ){
			selectedValue = allCode[obj.options[x].value][0];
		}
		
		obj.remove(x);
	}
	
	var	filter = document.getElementById("filter").value;
	createSelectItem( filter, selectedValue );
}

// ����ѡ�еļ�¼
function submitPress()
{
	saveSelectedValue( 's1', false );
	hideWindow();
	
	// �����޸ĺ���¼�
	fireBrowseChange( );
}

function selectItem()
{
	if( event.keyCode == 13 ){
		submitPress();
	}
}


// ����window�ĸ߶�
function setWindowHeight( newHeight )
{
	var name = sourceDefine.codebox;
	if( name == null || name == '' ){
		return;
	}
	
	var obj = sourceDefine.getElement(name);
	if( obj == null ){
		return;
	}
	
	// �������top
	var objTop = obj.offsetTop; 
 	while (obj = obj.offsetParent){
 		objTop += obj.offsetTop - obj.scrollTop;
 	}
 	
	var win = window.parent.fz_getBrowseById("innerWindow");
	var oldHeight = win.clientHeight;
	win.style.height = newHeight;
	
	// iframe��top
	obj = win;
	var windowTop = obj.offsetTop; 
 	while (obj = obj.offsetParent){
 		windowTop += obj.offsetTop - obj.scrollTop;
 	}
	
	if( windowTop < objTop ){
		win.style.top = windowTop + (oldHeight - newHeight);
	}
}


// �ӷ�������������
function loadData()
{
	if( sourceDefine.initFlag == true ){
		return;
	}
	
	var	ii;
	var	item;
	var	itemList = new Array();
	
	// ѡ����
	for( ii=0; ii<tag_options.length; ii++ ){
		item = new Array();
		item[0] = tag_options[ii][0];
		item[1] = tag_options[ii][1];
		item[3] = tag_options[ii][3];
		item[4] = tag_options[ii][4];
		if( tag_options[ii][2] == 'false' ){
			item[2] = false;
		}
		else{
			item[2] = true;
		}
		
		itemList[ii] = item;
	}
	
	// ��ʼ��
	init( parent.window, tag_input_data[0][0], itemList, tag_input_data[0][1], 
		tag_input_data[0][2], tag_input_data[0][3], tag_input_data[0][4]
	);
	
	// �Ƿ���ʾ���˿�
	if( tag_options.length < 15 ){
		setWindowHeight( 212 );
		
		var t = document.getElementById( 'blist' );
		var filterRow = t.rows[1];
		filterRow.style.display = 'none';
		t.style.height = 212;
	}
	
	// ���ý��㣺�������ȽϿ죬�رմ���ʱ����ܻ�û����ʾ�������
	try{
		document.getElementById('s1').focus();
	}
	catch( e ){
		
	}
}

// ��ʼ��
function initPage()
{
	// �������
	var w = document.getElementById( 'blist' );
	var rows = w.rows;
	rows[1].cells[1].style.width = w.clientWidth - 55;
	
	var sp = rows[2].cells[0].childNodes[0];
	sp.style.width = w.clientWidth - 8;
	
	var sl = sp.childNodes[0];
	sl.style.width = w.clientWidth - 4;
	
	// ��������
	loadData();
}
</script>
</head>

<body leftmargin="0" topmargin="0" id="main" onload="initPage()">
	<table id='blist' class='td_color' width="100%" bgcolor='#efefef' border="0" cellspacing="0" cellpadding="0">
	<tr><td colspan="2" width="100%" class='td_title'>��ѡ��</td></tr>
	<tr><td class='td_input' width="55px" align="right" valign=bottom style="cursor:default"><font color="#0000FF">��������</font></td><td class='td_input' width='90%'><input type="text" name="filter" id="filter" align="left" onkeyup="return recreateSelectItem();" style="width=98%"></td>
	<tr><td class='td_input' colspan="2" height="188px">
		<span style="width:242px; height:184px; bgcolor:white; border:1px solid #6371B5; overflow:hidden">
			<select name="s1" id="s1" size="8" style="width:246px; height:188px; cursor:hand; margin-left:-3px; margin-right:-2px; margin-top:-3px; margin-bottom:-2px" class="mselect" onkeydown="selectItem();" ondblclick="submitPress();" onclick="submitPress();"></select>
		</span>
	</td></tr>
	</table>
	
	<freeze:data name="tag-input-data" property="input-data" fields="show,codebox,namebox,mixbox,name"/>
	<freeze:data name="tag-options" property="options" fields="codevalue,codename,status,sortvalue,description"/>
</body>
</html>