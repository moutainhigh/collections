<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<link href="<%=request.getContextPath()%>/script/browsebox/browse.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>��ѡ�����</title>
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
	var	ii;
	for( ii=0; ii<selectList.length; ii++ ){
		selectList[ii][1] = selectList[ii][1].replace(/[,;]/g, '.' );
		if( selectList[ii][0] != selectList[ii][1] ){
			sourceDefine.showName = true;
		}
	}
	
	// ɾ���б�
	var obj1=document.getElementById('s1');
	for (x=obj1.options.length-1;x>=0;x--) {
		obj1.remove(x);
	}
	
	var obj2=document.getElementById('s2');
	for (x=obj2.options.length-1;x>=0;x--) {
		obj2.remove(x);
	}
	
	// �����б�
	createSelectItem('s1', 's2');
	
	// �Ƿ���ʾ���˿�
	if( sourceDefine.selectList != null && sourceDefine.selectList.length < 16 ){
		var tdFilter = document.getElementById( 'td_filter' );
		tdFilter.style.height = 0;
		
		var tdUnselect = document.getElementById( 'td_unselect' );
		tdUnselect.style.height = 188;
		tdUnselect.lastChild.style.height = 184;
		obj1.style.height = 188;
	}
}

/*******************************
���������
1��window
2�����п���ѡ��Ĳ���
3����������������
4���������Ƶ�������
5�����������ݵ�������

************************************************/
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
	if( obj == null ){
		return;
	}
	
	obj.focus();
}

// ����ѡ��
function addOptionValue( objName, selectList, addIndexList )
{
	if( addIndexList == null || addIndexList.length <= 0 ){
		return;
	}
	
	var	i;
	var	idx;
	var obj = document.getElementById( objName );
	var newOptionItem;
	
	for (i=0; i<addIndexList.length; i++) {
		idx = addIndexList[i];
		if( sourceDefine.showName ){
			newOptionItem = new Option(selectList[idx][0] + '--' + selectList[idx][1], idx);
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
		mixList = mixList + obj.options[i].text;
		
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
	var	fieldList = ['close', 's1', 's2', 'ok', 'close'];
	
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






/***************���ַ���ת��select��option��****************
������   
obj=���select��id
************************************************/
function createSelectItem( obj,obj1 )
{
	var	allCode = getAllCode();
	var	selectedCode = getSelectedCode();
	
	// �Ѿ�ѡ��
	var selectedList = getSelectedList( allCode, selectedCode );
	addOptionValue( obj1, allCode, selectedList );
	
	// ����ѡ��
	var unselectList = getUnselectList( allCode );
	addOptionValue( obj, allCode, unselectList );
}

// �Ѿ�ѡ��
function getSelectedList(HiddenAllValue,selectedAllValue){
	var	i, j;
	var	num = 0;
	var selectedList = new Array();	
	var SArray = selectedAllValue.split(',');
	
	for (j=0; j<SArray.length; j++) {
		SArray[j] = trimSpace( SArray[j], 0 );
		
		for (i=0; i<HiddenAllValue.length; i++) {
			if (SArray[j] == HiddenAllValue[i][0] || 
					SArray[j] == HiddenAllValue[i][1] || 
					SArray[j] == HiddenAllValue[i][0] + '--' + HiddenAllValue[i][1])
			{
				selectedList[num] = i;
				num = num + 1;
				break;			
			}
		}
	}
	
	return selectedList;
} 


// ����ѡ��
function getUnselectList( allCode, filter ){
	var	i, j;
	var	num = 0;
	var showType = sourceDefine.show;
	var unselectList = new Array();
	
	// �Ѿ�ѡ����ֶ��б�
	var SArray = new Array();
	var obj =document.getElementById("s2");	
	for(i=0; i<obj.options.length; i++ ){
		SArray[i] = obj.options[i].text;
		SArray[i] = trimSpace( SArray[i], 0 );
	}	
	
	// ȡδѡ����ֶ�
	for (j=0;j<allCode.length;j++) {
		for( i=0; i<SArray.length; i++ ){
			if (SArray[i] == allCode[j][0] || 
					SArray[i] == allCode[j][1] || 
					SArray[i] == allCode[j][0] + '--' + allCode[j][1])
			{
				break;
			}
		}
		
		if( i >= SArray.length && allCode[j][2] == true ){
			var flag = false;	
			if( filter == null || filter == '' ){
				flag = true;
			}
			else {			
	 			if( showType == 'mix' ){
			 		if( allCode[j][0].indexOf(filter) >= 0 ||
		  				allCode[j][1].indexOf(filter) >= 0 ) {
				  		flag = true;
				  	}
				}
				else if( showType == 'name' ){
		  			if( allCode[j][1].indexOf(filter) >= 0 ) {
				  		flag = true;
				  	}
				}
				else{
		  			if( allCode[j][0].indexOf(filter) >= 0 ) {
				  		flag = true;
				  	}
				}
	 		}
	 		
	 		if( flag ){
				unselectList[num] = j;
				num = num + 1
			}
		}
	}
	
	return unselectList;	
}


// ���������������ݷ����仯ʱ����������
function recreateSelectItem()
{
	if(event.keyCode == 13 || event.keyCode == 9){
		document.getElementById('s1').focus();
		return false;
	}
	
	var	allCode = getAllCode();
	var filter = document.getElementById("filter").value;
	
	var obj=document.getElementById('s1');
	for (x=obj.options.length-1;x>=0;x--) {
		obj.remove(x);
	}
	
	// ����ѡ��	
	var unselectList = getUnselectList( allCode, filter );
	addOptionValue( "s1", allCode, unselectList );
	
}


/**************��ҳ��****************
������  
���أ�
************************************************/
function submitPress() {
	saveSelectedValue( 's2', true );
	hideWindow();
	
	// �����޸ĺ���¼�
	fireBrowseChange( );
}

/**
*�д�Դselect���ƶ���ѡ�е�option�Ŀ��select��
*2005/06/28
*����: objectSelԴselect��,funSelĿ��select��,bFlagΪ���ж��Ƿ���option�ѡ��,��Ϊ���жϼ�ȫ���ƶ�
*/
function moveSelect(objectSel, funSel, bFlag)
{
	var newOption
	
	//�õ�ԴoptionԪ����
	obj=document.getElementById(objectSel);
	
	//�õ�Ŀ��optionԪ����
	oobj=document.getElementById(funSel);
	
	// options����
	var	len = obj.options.length;
	
	if( !bFlag ){
		// ����ȫ����¼
		for( x=0; x<len; x++ ){
			//�½�һ����(optionitem)
			newOption = new Option(obj.options[0].text, obj.options[0].value);
			
			//��Ŀ���ǲ�����
			oobj.add( newOption );
			
			//��Դ��ɾ����
			obj.remove( 0 );
		}
	}
	else{
		// ����ѡ�еļ�¼
		for( x=0; x<len; x++ ){
			//���һ��Ԫ�ر�ѡ����
			if ( obj.options[x].selected ){
				//�½�һ��(optionitem)
				var newOption = new Option(obj.options[x].text, obj.options[x].value);
				
				//��Ŀ���ǲ�����
				oobj.add(newOption);
			}
		}
		
		// ɾ��ѡ�еļ�¼
		for (x=obj.options.length-1;x>=0;x--){
			if ( obj.options[x].selected ){
				obj.remove(x);
			}
		}
	}
}

function selectItem(objectSel,funSel,bFlag)
{
	if( event.keyCode == 13 ){
		moveSelect(objectSel,funSel,bFlag);
	}
}

	
// ��ȥ�ո�
// type=0 - ȥ��ǰ��ո�; 1 - ȥǰ���ո�; 2 - ȥβ���ո�
function trimSpace( str, trimType )
{
	var i = -1
	var tmpStr = ' '
	
	// ǰ�ո�
	if(trimType == 0 || trimType == 1){
		while( tmpStr == ' ' ){
			++i
			tmpStr = str.substr(i,1)
		}
		
		str = str.substring(i)
	}
	
	// ��ո�
	if(trimType == 0 || trimType == 2){
		tmpStr = ' '
		i = str.length
		while(tmpStr == ' '){
			--i
			tmpStr = str.substr(i,1)
		}
		
		str = str.substring(0,i+1)
	}
	
	return str
}

</script>
</head>

<body leftmargin="0" topmargin="0">
<form name="seltestformA">
	<table class='td_color' bgcolor="#efefde" width="370px" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
	<tr>
		<td width="174px" class='td_title'>��ѡ�������</td>            
		<td width="20px" class='td_title'> </td>
		<td width="174px" class='td_title'>��ѡ�������</td>
	</tr>
	<tr>
	<td id='td_filter' class='td_minput' align="right" valign=bottom style="cursor:default;height:18px">
		<font color="#0000FF">���ˣ�</font>
		<input type="text" name="filter" id="filter" size="18" style="height:16px" onkeyup="return recreateSelectItem();">
	</td>
	<td class='td_form' align="left" rowspan="2">		
		 <div onclick="moveSelect('s1', 's2', true)" style="height:20pt;cursor:hand;"> <img src="<%=request.getContextPath()%>/script/browsebox/button_nexts.gif" alt="ѡȡһ��"></div>		
		 <div onclick="moveSelect('s1', 's2', false)" style="height:20pt;cursor:hand;"> <img src="<%=request.getContextPath()%>/script/browsebox/button_ends.gif" alt="ѡȡȫ��"></div>		
		 <div onclick="moveSelect('s2', 's1', true)" style="height:20pt;cursor:hand;"><img src="<%=request.getContextPath()%>/script/browsebox/button_prvs.gif" alt="ȡ��һ��"></div>
		 <div onclick="moveSelect('s2', 's1', false)" style="height:20pt;cursor:hand;"> <img src="<%=request.getContextPath()%>/script/browsebox/button_begins.gif" alt="ȡ��ȫ��"></div>		
	</td>
	<td class='td_form' align="center" rowspan="2">
		<span style="width:170px; height:184px; bgcolor:white; border:1px solid #6371B5; overflow:hidden">
		<select name="s2" multiple  id="s2" size="12" style="width:174px; height:188px; cursor:hand; margin-left:-3px; margin-right:-2px; margin-top:-3px; margin-bottom:-2px" class="mselect" onkeydown="selectItem('s2', 's1', true)" ondblclick="moveSelect('s2', 's1', true)"></select>
		</span>
	</td></tr>
	<tr>
	<td id='td_unselect' class='td_form' align="center" style="height:173px">
		<span id='span_unselect' style="width:170px; height:170px; bgcolor:white; border:1px solid #6371B5; overflow:hidden">
		<select name="s1" multiple id="s1" size="12" style="width:174px; height:174px; cursor:hand; margin-left:-3px; margin-right:-2px; margin-top:-3px; margin-bottom:-2px" class="mselect" onkeydown="selectItem('s1', 's2', true)" ondblclick="moveSelect('s1', 's2', true)"></select>
		</span>
	</td>
	</tr>
	<tr><td colspan="3" class='td_form' align="center"><input type="button" id="ok" value="&nbsp;ȷ��&nbsp;" style="cursor:hand;" onclick="submitPress();">&nbsp; &nbsp;&nbsp;&nbsp;<input type="reset" id="close" value="&nbsp;�ر�&nbsp;" style="cursor:hand;" onclick="hideWindow();"></td></tr>
	</table>
	
	<freeze:data name="tag-input-data" property="input-data" fields="show,codebox,namebox,mixbox,name"/>
	<freeze:data name="tag-options" property="options" fields="codevalue,codename,status,sortvalue,description"/>
	
	<script language="javascript">
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
		
		// ���ý��㣺�������ȽϿ죬�رմ���ʱ����ܻ�û����ʾ�������
		try{
			document.getElementById('s1').focus();
		}
		catch( e ){
			
		}
	}
	
	// ��ʼ��
	loadData();
	</script>
</form>
</body>
</html>