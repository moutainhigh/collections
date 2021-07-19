//<!--
/* ***************** ��ť ******************** */

// name ��ť����
// menuId ��ť��ΨһID
// validRule ��Ч����
// func ����
// hotkey �ȼ�
function menuDefine( name, menuId, validRule, func, hotkey, icon )
{
	this.name = (name == null) ? '' : name;
	this.menuId = menuId;
	this.validRule = validRule;
	this.func = func;
	this.hotkey = hotkey;
	this.icon = icon;
//	var btn = document.getElementById('btn'+menuId);
		//$(btn).parent().before("<td class='"+btn_img+"'></td>");
	// ����״̬
	this.setStatus = button_setStatus;
	
	// ����
	this.fireEvent = button_fireEvent;
	
}

// ����״̬
function button_setStatus( status )
{
	var obj = document.getElementById( 'btn' + this.menuId );
	if( obj == null ){
		return;
	}
	//console.log(this.name); 
	if(obj.parentNode.children.length == 1){
		obj.style.marginLeft = "-1000px";
		obj.style.width = "5px";
		var btn_img = '';
		var btn_value = obj.value.replace(" ",'');
		var btnImg = document.createElement("div");
		btnImg.setAttribute("title", btn_value);
		if('submit'==this.name){
			//��ѯ
			btn_img = 'btn_query';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_addRecord'==this.name){
			//���
			btn_img = 'btn_add';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_backupRecord'==this.name){
			//���
			btn_img = 'btn_backup';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_prevRecord'==this.name){
			//ͬ��
			btn_img = 'btn_prev';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_nextRecord'==this.name){
			//ͬ��
			btn_img = 'btn_next';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_saveAndExit'==this.name || 'record_saveRecord'==this.name){
			//����
			btn_img = 'btn_save';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_goBack'==this.name || 'record_goBackNoUpdate'==this.name){
			//����
			btn_img = 'btn_cancel';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_syncRecord'==this.name){
			//ͬ��
			btn_img = 'btn_sync';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_importRecord'==this.name){
			//����
			btn_img = 'btn_import';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_createTable'==this.name){
			//���ɱ�ṹ
			btn_img = 'btn_gen';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_getFunction'==this.name){
			//��ȡ����
			btn_img = 'btn_get';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_getFtpFile'==this.name){
			//ftp
			btn_img = 'btn_getftp';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_testRecord'==this.name){
			//����
			btn_img = 'btn_test';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_testRecord_old'==this.name){
			//����������(��)
			btn_img = 'btn_testold';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_testRecord_new'==this.name){
			//����������(��)
			btn_img = 'btn_testnew';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('psw-inte'==this.name){
			//����������(��)
			btn_img = 'btn_pwd_init';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else{
			obj.style.marginLeft = "0px";
			obj.style.width = "64px";
		}
		//btnImg.setAttribute("class", btn_img);
		btnImg.className = btn_img;
		obj.parentNode.appendChild(btnImg);
	}
	//console.log(obj.attr("id"));
	_fz_setButtonStatus_1( obj, status );
}

// ִ�к���
function button_fireEvent( selRows, frameName, frameIndex )
{
	// ���ð�ť����
	var objs = document.getElementsByName('inner-flag:menu-name');
	if( objs != null ){
		for( var i=0; i<objs.length; i++ ){
			objs[i].value = this.name;
		}
	}
	
	// ������
	currentFormName = _getFormName( frameName, frameIndex );
	
	// ִ�в���
	if( this.func != null ) this.func( selRows, frameName, frameIndex, this.name );
}

// ��ť��click�¼�
function _menuClick( menuId )
{
	var menu = null;
	
	// ȡGRID��MENU
	var	grid = null;
	for( var i=0; i<grids.length && grid==null; i++ ){
		var menus = grids[i].menus;
		if( menus == null ){
			continue;
		}
		
		for( var j=0; j<menus.length; j++ ){
			if( menus[j].menuId == menuId ){
				grid = grids[i];
				menu = menus[j];
				break;
			}
		}
	}
	
	// ƥ�䵽��ť
	if( menu != null ){
		return grid.menuClick( menu );
	}
	
	// ����frame��MENU
	var frame = null;
	for( var i=0; i<gframes.length && frame==null; i++ ){
		var menus = gframes[i].menus;
		if( menus == null ){
			continue;
		}
		
		for( var j=0; j<menus.length; j++ ){
			if( menus[j].menuId == menuId ){
				frame = gframes[i];
				menu = menus[j];
				break;
			}
		}
	}
	
	// ƥ�䵽��ť
	if( menu != null ){
		return frame.menuClick( menu );
	}
	
	// ����TABS��MENU
	var tab = null;
	for( var i=0; i<tabFrames.length && tab==null; i++ ){
		var menus = tabFrames[i].menus;
		if( menus == null ){
			continue;
		}
		
		for( var j=0; j<menus.length; j++ ){
			if( menus[j].menuId == menuId ){
				tab = tabFrames[i];
				menu = menus[j];
				break;
			}
		}
	}
	
	// ƥ�䵽��ť
	if( menu != null ){
		return tab.menuClick( menu );
	}
	
	return false;
}



// ������а�ť����Ч��
function checkAllMenuItem( formName )
{
  	if(clickFlag == 0){
  		var status = false;
  	}
  	else{
  		var status = true;
  	}
  	
	// frame
	for( var i=0; i<gframes.length; i++ ){
		gframes[i].checkMenuItem( );
	}
	
	// grid
	for( var i=0; i<grids.length; i++ ){
		grids[i].checkMenuItem( );
	}
	
	// tabs
	for( var i=0; i<tabFrames.length; i++ ){
		tabFrames[i].checkMenuItem( );
	}
}


// �ж��Ƿ�ť
function isButton( obj )
{
	if( obj == null ){
		return false;
	}
	
	var tName = obj.tagName;
	if( tName == null ){
		return false;
	}
	
	tName = tName.toLowerCase();
	if( tName == 'input' ){
		var tType = obj.type.toLowerCase();
		if( tType == 'image' || tType == 'button' ){
			return true;
		}
	}
	else if( tName == 'button' ){
		return true;
	}
	else if( tName != 'img' ){
		return true;
	}

	return false;
}


// �˾�
var __activeFilter = 'blur(add=1,direction=120,strength=2)';
var __disableFilter = 'Alpha(stype=0,opacity=40)';
var __selectedFilter = 'Alpha(stype=0,opacity=70)';

// ���ð�ť����Ч״̬
// status = true ��ֹ
function _fz_setButtonStatus( buttonName, index, status )
{
	var objs = document.getElementsByName( buttonName );
	if( objs.length <= index ){
		return;
	}
	
	_fz_setButtonStatus_1( objs[index], status );
}

function _fz_setButtonStatus_1( obj, status )
{
	// �жϰ�ť�Ƿ񱻽�ֹ
	if( obj._disabled == 'true' || obj._disabled == true ){
		status = true;
	}
	
	var tName = obj.tagName.toLowerCase();
	if( tName == 'input' ){
		obj.disabled = status;
		if(status===true){
			if(obj.nextSibling.className.indexOf("_disabled")==-1){
				obj.nextSibling.className = obj.nextSibling.className+"_disabled";
			}
		}else{
			obj.nextSibling.className = obj.nextSibling.className.replace(/_disabled$/ig, "");
		}
		// ��ͼ�ΰ�ť������
		if( obj.type.toLowerCase() != 'image' ){
			return;
		}
	}
	else if( tName == 'button' ){
		obj.disabled = status;
		return;
	}
	else if( tName != 'img' ){
		return;
	}
	// �����˾�
	if( status ){
		obj.style.filter = __disableFilter;
	}
	else{
		if( __selectedButton == obj && (obj.selected == null || obj.selected == '') ){
			obj.style.filter = __selectedFilter;
		}
		else{
			obj.style.filter = '';
		}
	}
}

// ���ð�ť��ʽ
// flag=true ��ť����ѡ�У�����ڰ�ť�
// flag=false ��ťû��ѡ��
function setButtonStyle(flag)
{
	obj = window.event.srcElement;
	if( obj == null ){
		return;
	}
	
	if( obj.tagName.toLowerCase() == 'input' ){
		// ��ͼ�ΰ�ť������
		if( obj.type.toLowerCase() != 'image' ){
			return;
		}
	}
	else if( obj.tagName.toLowerCase() != 'img' ){
		return;
	}
	
	if( flag ){
		// �
		if( obj.active != null && obj.active != '' ){
			obj.src = obj.active;
		}
		else{
			obj.style.filter = __activeFilter;
		}
	}
	else{
		if( __selectedButton == obj ){
			// ѡ��
			if( obj.selected != null && obj.selected != '' ){
				obj.src = obj.selected;
			}
			else{
				obj.src = obj.normal;
				obj.style.filter = __selectedFilter;
			}
		}
		else{
			// ����
			if( obj.active != null && obj.active != '' ){
				obj.src = obj.normal;
			}
			else{
				obj.style.filter = '';
			}
		}
	}
}

// ��ǰѡ�еİ�ť
var __selectedButton = null;

// ����ѡ�еİ�ť��ʽ
function setSelectedButton()
{
	obj = window.event.srcElement;
	if( __selectedButton == obj ){
		return;
	}
	
	if( obj == null ){
		return;
	}
		
	if( obj.tagName.toLowerCase() == 'input' ){
		// ��ͼ�ΰ�ť������
		if( obj.type.toLowerCase() != 'image' ){
			return;
		}
	}
	else if( obj.tagName.toLowerCase() != 'img' ){
		return;
	}
	
	// �Ƿ��Ѿ�ѡ���˰�ť������
	if( __selectedButton != null ){
		var selectedImage = __selectedButton.selected;
		if( selectedImage != null && selectedImage != '' ){
			__selectedButton.src = __selectedButton.normal;
		}
		else{
			__selectedButton.style.filter = '';
		}
	}
	
	// ����ǰ�İ�ť������
	__selectedButton = obj;
	
	// �ж��Ƿ����˻ͼ��
	var selectedImage = __selectedButton.selected;
	if( selectedImage != null && selectedImage != '' ){
		__selectedButton.src = selectedImage;
	}
	else{
		__selectedButton.src = __selectedButton.normal;
		__selectedButton.style.filter = __selectedFilter;
	}
}



/* ***************** frame �� block ******************* */
// Frame��ǩ����Ϣ
var	gframes = new Array();

// frame��Ϣ
function frameDefine( frameName, frameType, index, captionWidth, 
		fieldStype, labelStyle, onkeyup, onkeydown, menus )
{
	this.frameName = frameName;
	this.frameType = frameType;
	this.index = index;
	
	// ����Ŀ��
	this.captionWidth = captionWidth;
	
	// ��ʽ
	this.fieldStype = fieldStype;
	if( labelStyle == '' ){
		this.labelStyle = fieldStype;
	}
	else{
		this.labelStyle = labelStyle;
	}
	
	// �¼�
	this.onkeyup = onkeyup;
	this.onkeydown = onkeydown;
	
	// ��ť�б�
	this.menus = menus;
	
	// ����
	this.checkMenuItem = frame_checkMenuItem;
	this.menuClick = frame_menuClick;
	
	// ��ʼ������
	this.initFrameStyle = frame_initFrameStyle;
}

// ����frame��Ϣ
function addFrameDefine( fmDefine )
{
	var	i;
	var	num = gframes.length;
	for( i=0; i < num; i++ ){
		if( gframes[i].frameName == fmDefine.frameName && gframes[i].index == fmDefine.index ){
			// �ϲ���ť
			if( gframes[i].menus != null && gframes[i].menus.length > 0 ){
				if( fmDefine.menus == null ){
					fmDefine.menus = new Array();
				}
				
				var	num = fmDefine.menus.length;
				for( var jj=0; jj<gframes[i].menus.length; jj++ ){
					fmDefine.menus[num + jj] = gframes[i].menus[jj];
				}
			}
			
			break;
		}
	}
	
	// ����
	gframes[i] = fmDefine;
	
	// ��ʼ��
	gframes[i].initFrameStyle();
}


// �������ƻ�ȡframe������Ϣ
function getFrameDefine( frameName )
{
	var	i;
	var name;
	for( i=0; i<gframes.length; i++ ){
		if( gframes[i].index > 0 ){
			name = gframes[i].frameName + gframes[i].index;
		}
		else{
			name = gframes[i].frameName;
		}
		
		if( name == frameName ){
			return gframes[i];
		}
	}
	
	return null;
}


// ִ�а�ť����
function frame_menuClick( menu )
{
	// ��ֹ��ť
	clickFlag = 1;
	checkAllMenuItem( );
	clickFlag = 0;
	
	// ִ�в���
	menu.fireEvent(null, this.frameName, this.index);
	
	// �ָ���ť
	checkAllMenuItem( );
	
	// ��ֹ����ִ�У�����image�ύ
	return false;
}


// ��鰴ť����Ч��
function frame_checkMenuItem( )
{
	// ��ť��Ϣ
  	var	menus = this.menus;
  	if( menus == null ){
  		return;
  	}
	
  	if(clickFlag == 0){
  		var status = false;
  	}
  	else{
  		var status = true;
  	}
  	
	// �ָ�/��ֹ ���а�ť
	for( var i=0; i<menus.length; i++ ){
		menus[i].setStatus( status );
	}
}


// ��ʾ��
function setFrameRowVisible( frameName, rowid, flag )
{
	if( window.event != null && window.event.srcElement != null ){
		var frameObject = getFieldFrameObject( window.event.srcElement );
		if( frameObject != null && frameObject.name == frameName ){
			frameName = frameObject.id;
		}
	}
	
	// ȡ��
	var	table = document.getElementById( 'main_' + frameName );
	if( table == null ){
		return null;
	}
	
	// ȡ��
	var	row = table.rows( "row_" + rowid );
	if( row == null ){
		return null;
	}
	
	if( flag ){
		row.style.display = '';
	}
	else{
		row.style.display = 'none';
	}
	
	return row;
}


// ȡ���FRAME
function getFieldFrameObject( field )
{
	while( field != null ){
		if( field.frameType != null && field.frameType != '' ){
			break;
		}
		
		field = field.parentNode;
	}
	
	return field;
}


// ����block�Ŀ�ʼ����
function frame_beginBlockHead( name, index, align, width, height, border, borderColor, styleClass )
{
	var str = "<table cellspacing='0' cellpadding='0'  frameType='block'";
	
	// ����
	str += " name='" + name + "'";
	if( index > 0 ){
		str += " id='" + name + index + "'";
	}
	else{
		str += " id='" + name + "'";
	}
	
	// ���
	if( width != '' ){
		str += " width='" + width + "'";
	}
	
	if( height != '' ){
		str += " height='" + height + "'";
	}
	
	// ����
	if( align == '' ){
		str += " align='center'"
	}
	else{
		str += " align='" + align + "'";
	}
	
	border = '0';
	// �߿�
	if( border != '' ){
		// ȡ��������ɫ
		if( borderColor == null || borderColor == '' ){
			css = _browse.getCssStyle( styleClass );
			if( css != null ){
				borderColor = css.backgroundColor;
			}
		}
		/*borderColor = '#006699';
		// ������ʽ
		if( borderColor != null && borderColor != '' ){
			str += " style='border-left-style:solid; border-left-width:" + border + "px;";
			str += " border-right-style:solid; border-right-width:" + border + "px;'";
			str += " border-bottom-style:solid; border-bottom-width:" + border + "px;'";
			str += " bordercolor='" + borderColor + "'";
		}*/
		str += "style='margin-top:10px;'";
	}
	
	// ��ʽ
	if( styleClass != '' ){
		str += " class='" + styleClass + "'";
	}
	
	document.write( str + '>' );
}


// ԰�Ǳ��Ŀ�ʼ
function frame_beginRoundBlockHead( name, index, align, width, borderColor, bgColor, styleClass )
{
	// ȡ����ɫ
	var css = _browse.getCssStyle( styleClass );
	if( css != null ){
		if( css.backgroundColor != null ){
			bgColor = css.backgroundColor;
		}
	}
	
	if( bgColor == null || bgColor == '' ){
		bgColor = '#FFFFFF';
	}
	
	// ȡ��������ɫ
	if( borderColor == null || borderColor == '' ){
		if( css != null ){
			borderColor = css.borderColor;
		}
		
		if( borderColor == null || borderColor == '' ){
			borderColor = '#000000';
		}
	}
	
	// ����
	var str = '<table name="roundTable" id="roundTable"'
	if( align == '' ){
		str += " align='center'"
	}
	else{
		str += " align='" + align + "'";
	}
	
	// ���
	if( width != '' ){
		str += " width='" + width + "'";
	}
	else{
		str += "width='100%'";
	}
	
	str += '><tr><td>';
	
	str += "<span style='width:100%'>";
	
	// �߿�
	str += '<b class="roundtop">';
	str += '<b class="line1" style="background:' + borderColor + '"></b>';
	str += '<b class="line2" style="background:' + borderColor + '"><b class="line12" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line3" style="background:' + borderColor + '"><b class="line13" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line4" style="background:' + borderColor + '"><b class="line13" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line5" style="background:' + borderColor + '"><b class="line15" style="background:' + bgColor + '"></b></b>';
	str += '</b>';
	
	// table
	str += "<table border='0' cellspacing='0' cellpadding='0' width='100%' frameType='block' bgcolor='" + bgColor + "'";
	
	// ����
	str += " name='" + name + "'";
	if( index > 0 ){
		str += " id='" + name + index + "'";
	}
	else{
		str += " id='" + name + "'";
	}
	
	// �߿�
	var border = 1;
	str += " style='border-left-style:solid; border-left-width:" + border + "px;";
	str += " border-right-style:solid; border-right-width:" + border + "px;'";
	str += " bordercolor='" + borderColor + "'";
	
	// ��ʽ
	if( styleClass != '' ){
		str += " class='" + styleClass + "'";
	}
	
	// alert( str );
	document.write( str + '>' );
}


function frame_endRoundBlockHead( borderColor, bgColor, styleClass )
{
	// ȡ����ɫ
	var css = _browse.getCssStyle( styleClass );
	if( css != null ){
		if( css.backgroundColor != null ){
			bgColor = css.backgroundColor;
		}
	}
	
	if( bgColor == null || bgColor == '' ){
		bgColor = '#FFFFFF';
	}
	
	// ȡ��������ɫ
	if( borderColor == null || borderColor == '' ){
		if( css != null ){
			borderColor = css.borderColor;
		}
		
		if( borderColor == null || borderColor == '' ){
			borderColor = '#000000';
		}
	}
	
	str = "</td></tr></table>";
	str += '<b class="roundbottom">';
	str += '<b class="line5" style="background:' + borderColor + '"><b class="line15" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line4" style="background:' + borderColor + '"><b class="line13" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line3" style="background:' + borderColor + '"><b class="line13" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line2" style="background:' + borderColor + '"><b class="line12" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line1" style="background:' + borderColor + '"></b>';
	str += '</b>';
	str += '</span>';
	str += '</td></tr></table>';
	
	document.write( str );
}


// ���ڴ�ӡ��block�Ŀ�ʼ����
function frame_beginPrintBlockHead( name, index, align, width )
{
	var str = "<table cellspacing='0' cellpadding='0'";
	
	// ����
	str += " name='" + name + "'";
	if( index > 0 ){
		str += " id='" + name + index + "'";
	}
	else{
		str += " id='" + name + "'";
	}
	
	// ���
	if( width != '' ){
		str += " width='" + width + "'";
	}
	
	// ����
	if( align == '' ){
		str += " align='center'"
	}
	else{
		str += " align='" + align + "'";
	}
	
	
	document.write( str + '>' );
}


// ����
function frame_prepareBlockTitle( align, height, styleClass, bgColor, icon, caption )
{
	var str = "<tr valign='center'";
	
	// ����
	if( align != '' ){
		str += " align='" + align + "'";
	}
	
	// ��ʽ
	if( styleClass != '' ){
		str += " class='" + styleClass + "'";
	}
	else if( bgColor != '' ){
		str += " bgcolor='" + bgColor + "'";
	}
	
	if( height != null && height != '' ){
		str += " style=' height:" + height + ";";
	}
	
	// ��ɫ
	str += ">\n<td><table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td style='display:none;' class='secTitle'>";
	
	// ͼ��
	if( icon != '' ){
		if( icon.charAt(0) == '/' ){
			icon = _browse.contextPath + icon;
		}
		
		str += "<img border='0' style='display:;' src='" + icon + "'>&nbsp;";
	}
	
	// ����
	str += caption;
	
	// ����
	str += "</td></tr></table></td>\n</tr>\n";
	
	document.write( str );
}


// ��ʼ����
// borderColor �ָ��ߵ���ɫ��noneʱû�зָ���
function frame_beginBlockBody( name, index, borderColor, nowrap, styleClass )
{
	// �µı��
	var str = "<table  width='100%' height='100%' cellpadding='0'";
	
	// name �� id
	if( index > 0 ){
		var t = "'main_" + name + index + "'";
	}
	else{
		var t = "'main_" + name + "'";
	}
	
	str += " name=" + t + " id=" + t;
	
	borderColor = '#006699';
	
	// �߿�
	if( borderColor != 'none' ){
		// ȡ��������ɫ
//		if( borderColor == null || borderColor == '' ){
//			css = _browse.getCssStyle( styleClass );
//			if( css != null ){
//				borderColor = css.backgroundColor;
//			}			
//			if( borderColor == null || borderColor == '' ){
//				borderColor = '#7f9db9';				
//			}		
//		}
		
		// ����
		str += " cellspacing='0' ";
	}
	else{
		str += " cellspacing='0'";
	}
	
	// ��ʽ
	if( nowrap == 'true' ){
		str += " style='table-layout:fixed; display:none;border:1px solid RGB(207,207,254);border-collapse:collapse;'";
	}
	else{
		str += " style='word-break:break-all; display:none;border:1px solid RGB(207,207,254);border-collapse:collapse;'";
	}
	
	// ����
	str += ">\n";
	document.write( str );
}


// ��ʼ����
function frame_beginRoundBlockBody( name, index, borderColor, nowrap, styleClass )
{
	// �µı��
	var str = "<table  width='95%' height='100%' align='center' cellpadding='2'";
	
	// name �� id
	if( index > 0 ){
		var t = "'main_" + name + index + "'";
	}
	else{
		var t = "'main_" + name + "'";
	}
	
	str += " name=" + t + " id=" + t;
	
	// ȡ��������ɫ
	if( borderColor == 'none' ){
		str += " cellspacing='0'";
	}
	else{
		if( borderColor == '' ){
			css = _browse.getCssStyle( styleClass );
			if( css != null ){
				borderColor = css.borderColor;
			}
			
			if( borderColor == null || borderColor == '' ){
				borderColor = 'RGB(207,207,254)';
			}
		}
		
		str += " cellspacing='1' bgcolor='" + borderColor + "'";
	}
	
	// ��ʽ
	if( nowrap == 'true' ){
		str += " style='table-layout:fixed; display:none'";
	}
	else{
		str += " style='word-break:break-all; display:none'";
	}
	
	// ����
	str += ">\n";
	document.write( str );
}


// ��ʼ����
function frame_beginPrintBlockBody( name, index, border, borderColor, nowrap, styleClass )
{
	// �µı��
	var str = "<table cellpadding='0' cellspacing='0' width='100%' height='100%'";
	
	// ��ʽ
	if( nowrap == 'true' ){
		str += " style='table-layout:fixed; display:none'";
	}
	else{
		str += " style='word-break:break-all; display:none'";
	}
	
	// ��ʽ
	if( styleClass == '' ){
		styleClass = 'print';
	}
	
	str += " class='" + styleClass + "'"
	
	// name �� id
	if( index > 0 ){
		var t = "'main_" + name + index + "'";
	}
	else{
		var t = "'main_" + name + "'";
	}
	
	str += " name=" + t + " id=" + t;
	
	// �߿�
	if( border == '' ){
		str += " border='1'";
	}
	else{
		str += " border='" + border + "'";
	}
	
	// �߿���ɫ
	if( borderColor != 'none' ){
		// ȡ��������ɫ
		if( borderColor == null || borderColor == '' ){
			css = _browse.getCssStyle( styleClass );
			if( css != null ){
				borderColor = css.backgroundColor;
			}
			
			if( borderColor == null || borderColor == '' ){
				borderColor = 'RGB(207,207,254)';
			}
		}
		
		str += " bordercolor='" + borderColor + "'";
	}
	
	// ����
	str += ">\n";
	document.write( str );
}

// ��ʼ��BLOCK
function frame_initFrameStyle()
{
	var btn_reset = document.getElementsByName("form_reset");
	for(var ii=0; ii<btn_reset.length; ii++){
		var btnImg = document.createElement("div");
		btn_img = 'btn_reset';
		btnImg.onclick = function(){
			//clearFormFieldValue();
			var btn_reset = document.getElementsByName("form_reset");
			btn_reset[0].click();
		}
		btnImg.className = btn_img;
		btn_reset[ii].parentNode.appendChild(btnImg);
		btn_reset[ii].style.marginLeft = "-1000px";
	}
	// ȡ����
	if( this.index > 0 ){
		var frameId = "main_" + this.frameName + this.index;
	}
	else{
		var frameId = "main_" + this.frameName;
	}
	
	// ������frame
	if( this.frameType == 'frame' ){
		frameId = '_frame_' + frameId;
	}
	
	// ��ʽ��
	var tables = document.getElementsByName( frameId );
	for( var ti=0; ti<tables.length; ti++ ){
		var table = tables[ti];
		
		// �ж��Ƿ��Ѿ�����:�ڴ��������ݺ󣬻�ɾ�������������Ұ�״̬�ĳ�[_processed]
		if( table.status == '_processed' ){
			continue;
		}
		
		// ��
		var rows = table.rows;
		
		// ȡ�е�����
		var columns = 0;
		if( rows.length > 0 ){
			var tds = rows[0].cells;
			for( var jj=0; jj<tds.length; jj++ ){
				columns += parseInt(tds[jj].colSpan);
			}
		}
		else{
			columns = 1;
		}
		
		// ������
		if( this.frameType == 'compare' ){
			labelWidth = 50 * this.captionWidth;
			
			if( columns == 3 ){
				fieldWidth = 25 * (1 - this.captionWidth);
			}
			else{
				fieldWidth = 50 * (1 - this.captionWidth);
			}
			
			// ���õ�һ�е���ʽ
			var tds = rows[0].cells;
			for( var ii=0; ii<tds.length; ii++ ){
				tds[ii].width = fieldWidth + '%';
				tds[ii].align = 'center';
				tds[ii].vAlign = 'middle';
				
				if( this.labelStyle == '' ){
					tds[ii].bgColor = '#FFFFFF';
				}
				else{
					tds[ii].className = this.labelStyle;
				}
			}
			
			// ��һ��
			tds[0].width = labelWidth + '%';
			
			// ������
			var lineFlag = table.cellSpacing;
			for( var ii=1; ii<rows.length-1; ii++ ){
				var tds = rows[ii].cells;
				tds[0].align = 'right';
				if( this.labelStyle == '' ){
					tds[0].bgColor = '#FFFFFF';
				}
				else{
					tds[0].className = this.labelStyle;
				}
				
				for( var jj=1; jj<tds.length; jj++ ){
					if( this.fieldStype == '' ){
						tds[jj].bgColor = '#FFFFFF';
					}
					else{
						tds[jj].className = this.fieldStype;
					}
					
					if( lineFlag > 0 ){
						tds[jj].style.paddingLeft = "4px";
					}
				}
			}
		}
		else{
			columns = columns / 2;
		   	labelWidth = (100 / columns) * this.captionWidth;
		   	fieldWidth = (100 / columns) * (1 - this.captionWidth);
			
			// �޸�����
			var lineFlag = table.cellSpacing;
			for( var ii=0; ii<rows.length; ii++ ){
				var flag = false;
				var tds = rows[ii].cells;
				for( var jj=0; jj<tds.length; jj++ ){
					var p = tds[jj].colSpan;
					var p2 = parseInt(p/2);
					var p1 = p - 2*p2;
					
					// ���ÿ��
					if( flag == false ){
						if( this.labelStyle == '' ){
							tds[jj].bgColor = '#FFFFFF';
						}
						else{
							tds[jj].className = this.labelStyle;
						}
						
						var width = p1 * labelWidth + p2 * (labelWidth + fieldWidth);
					}
					else{
						if( this.fieldStype == '' ){
							tds[jj].bgColor = '#FFFFFF';
						}
						else{
							tds[jj].className = this.fieldStype;
						}
						
						var width = p1 * fieldWidth + p2 * (labelWidth + fieldWidth);
					}
					
					if( width > 0 ){
						tds[jj].width = width + '%';
					}
					else{
						tds[jj].style.display = 'none';
					}
					
					// ������ʽ
					if(  p1 == 1 ){
						if( flag == false ){
							tds[jj].align = 'right';
							flag = true;
						}
						else{
							flag = false;
							if( lineFlag > 0 ){
								tds[jj].style.paddingLeft = "4px";
							}
						}
					}
				}
			}
		}
		
		// ��ʾ����
		table.style.display = '';
		table.status = '_processed';
		
		//���и��л�ɫ
		for(var rowii=0;rowii<rows.length;rowii++){
			for(var coljj=0;coljj<rows[rowii].cells.length;coljj++){
				//if(rowii % 2 == 0){
				//	table.rows[rowii].cells[coljj].className="odd2_b";
				//}else{
					table.rows[rowii].cells[coljj].className="odd12";
				//}
				//if(coljj%2==0){
				//	table.rows[rowii].cells[coljj].style.whiteSpace = "nowrap";
				//}
			}
		}
	}
	
	// ��ʼ����ť��״̬
	this.checkMenuItem();
}

//lable�ĵ���¼�
function doChangeClick(e){
  var tg=e.srcElement.id.replace("label","check");
  var ch_check=document.getElementById(tg);
  if(ch_check){
      var lab_check=document.getElementById(e.srcElement.id);
      
	  if(ch_check.checked==false){
			delClass(lab_check,"back_up");
		    addClass(lab_check,"back_down");
		}else{
			delClass(lab_check,"back_down");
			addClass(lab_check,"back_up");
	  }
	  ch_check.click();
  }
}



//checkbox��ʽ���滻
function changeCheckbox(flag){
    var check_record=document.getElementsByName('record:_flag');
    for(var j=0;j<check_record.length;j++){
       var lab=document.getElementById('label_'+j);
        if(lab){
	        if(window.addEventListener) { 
			 lab.addEventListener("onclick", doChangeClick, false); 
			} else if(window.attachEvent) { 
			  lab.attachEvent("onclick", doChangeClick); 
		    } 
	    }
	}
}


//ȫѡcheckbox��ʵ��
function checkboxAll(){
 var select_all=document.getElementById('select-all_label');
	if(select_all != null){
		select_all.attachEvent('onclick', 
			function(){
				var check_all = document.getElementById("record:_select-all");
				if(check_all.checked==false){
					delClass(select_all,"back_up");
					addClass(select_all,"back_down");
				}else{
					delClass(select_all,"back_down");
					addClass(select_all,"back_up");
				}
				document.getElementById('record:_select-all').click();
			});
	}//else{
//		console.log("ҳ��û��ȫѡ����");
//	}
}

function changeRadio(){
//	$(".radioNew").each(function(index){
//		$(this).click(function(){
//			$(".radioNew").css("background-position-y","bottom");
//			$(this).css("background-position-y","top");
//		});
//	});
}

// �ⲿ��ı������ݣ����ɱ��⡢��ʽ��ID
// fieldName : �ֶ�����
// visible : �Ƿ񿴼�
// flag : ���״̬ disabled/readonly/notnull/normal
// caption : ����
function _prepareFieldCaption( fieldName, index, visible, flag, caption )
{
	if( caption == null ){
		return;
	}
	
	// ȡ����Ķ���
	var labelName = "label:" + fieldName;
	var labels = document.getElementsByName( labelName );
	if( index >= labels.length ){
		return;
	}
	
	// ���ñ������Ϣ
	var label = labels[index];
	
	// �Ƿ�ɼ�
	if( visible == false ){
		label.innerHTML = '';
		return;
	}
	
	if( flag == 'disabled' || flag == 'readonly' ){
		label.style.color = "#909090";
	}
	else if( flag == 'notnull' ){
		label.style.color = "red";
	}
	else{
		label.style.color = "";
	}
	
	// ����
	if( isGridField(fieldName) ){
		label.innerHTML = caption;
	}
	else if( flag == 'notnull' ){
		var ptr = caption.lastIndexOf( '>' );
		if( ptr > 0 ){
			label.innerHTML = caption.substring(0,ptr+1) + "*" + caption.substring(ptr+1) + "��";
		}
		else{
			label.innerHTML = "*" + caption + "��";
		}
	}
	else{
		label.innerHTML = caption + "��";
	}
}

// ������ı�ǩ
function _setFieldCaption( fieldName, index, obj )
{
	// ȱʡ�����
	if( index == null || isNaN(parseInt(index)) || index < 0 ){
		index = 0;
	}
	
	// ����
	var caption = getFormFieldCaption( fieldName, index );
	if( caption == null || caption == '' ){
		return;
	}
	
	// ��������
	var field = getSelectInfoByFieldName( fieldName );
	if( field != null ){
		var disabled = field.getDisabled( index );
		var readonly = field.getReadonly( index );
		var notnull = field.getNotnull( index );
		var visible = field.getVisible( index );
	}
	else{
		// ȡ����
		if( obj == null ){
			var objs = document.getElementsByName( fieldName );
			if( objs.length <= index ){
				return;
			}
			
			obj = objs[index];
		}
		
		// ״̬
		var disabled = obj.disabled;
		var readonly = obj.readOnly;
		
		if( obj.notnull == 'true' ){
			var notnull = true;
		}
		else{
			var notnull = false;
		}
		
		if( obj.style.display == "none" ){
			var visible = false;
		}
		else{
			var visible = true;
		}
	}
	
	// flag
	if( disabled == true ){
	   var flag = "disabled";
	}
	else if( readonly == true ){
	   var flag = "readonly";
	}
	else if( notnull ){
	   var flag = "notnull";
	}
	else{
	   var flag = "";
	}
	
	// ���ñ���
	_prepareFieldCaption( fieldName, index, visible, flag, caption )
}


// �����������Ƿ��������뺺��
function _setFieldImeMode( obj )
{
	// ȡ����
	var datatype = obj.datatype;
	
	// �����������뺺��
	if (datatype=="int"){			
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="double"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="money"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="string" ){
		
	}
	else if ( datatype=="password"){
		
	}
	else if (datatype=="date"){
		var fmt = obj.dataformat;
		if( fmt != '4' ){
			obj.style.imeMode = "disabled";
		}
	}
	else if (datatype=="mail"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="idcard" || datatype=="idcard18"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="phone"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="mobile"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="zip"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="url"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="qq"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="number"){
		obj.style.imeMode = "disabled";
	}
	else if (datatype=="file"){
		
	}
	else{
		
	}//end if
}


// ��ʼ����ʽ
function _initFieldStyle( fieldName, index, obj )
{
	if( obj == null ){
		// ȡ����
		var objs = document.getElementsByName( fieldName );
		if( objs.length <= index ){
			return;
		}
		
		obj = objs[index];
	}
	
	// ����
	if( obj._type == 'datebox' ){
		// �ж��Ƿ��Ѿ���ʼ��
		if( obj._inited ) return;
		
		// ���ó�ʼ����־
		obj._inited = true;
		
		// ����TABLE����ʽ
		var tableObj = findAncestorWithName( obj, "TABLE" );
		if( tableObj != null ){
			tableObj.border = 0;
			tableObj.cellSpacing = 0;
			tableObj.cellPadding = 0;
			tableObj = null;
		}
		
		// td�Ŀ��
		obj.parentNode.width = '99%';
		
		// ��������
		obj.datatype='date';
		obj.style.width = '100%';
		
		var notnull = false;
		if( obj.notnull == 'true' ){
			notnull = true;
		}
		
		// �����ֶγ��ȡ��͸�ʽ
		var fmt = obj.dataformat;
		if( fmt == null || fmt == '' ){
			fmt = _defaultDateFormat;
		}
		else{
			if( isNaN(parseInt(fmt)) == true ){
				fmt = _defaultDateFormat;
			}
			else{
				fmt = parseInt(fmt);
				if( fmt < 1 || fmt > 5 ){
					fmt = _defaultDateFormat;
				}
			}
		}
		
		obj.dataformat = fmt;
		
		// ��ʽ������
		var v = obj.value;
		if( v.length == 8  && fmt != 3 ){
			var yyyy = v.substr(0,4);
			var mm = v.substr(4,2);
			var dd = v.substr(6,2);
			
			if( fmt == 1 ){
				v = yyyy + '-' + mm + '-' + dd;
			}
			else if( fmt == 2 ){
				v = yyyy + '/' + mm + '/' + dd;
			}
			else if( fmt == 4 ){
				v = yyyy + '��' + mm + '��' + dd + '��';
			}
			else{
				v = yyyy + '-' + mm + '-' + dd + ' 00:00:00';
			}
		}
		if( v.length == 10  && fmt != 1){
			var yyyy = v.substr(0,4);
			var mm = v.substr(5,2);
			var dd = v.substr(8,2);
			
			if( fmt == 3 ){
				v = yyyy + mm + dd;
			}
			else if( fmt == 2 ){
				v = yyyy + '/' + mm + '/' + dd;
			}
			else if( fmt == 4 ){
				v = yyyy + '��' + mm + '��' + dd + '��';
			}
			else{
				v = yyyy + '-' + mm + '-' + dd + ' 00:00:00';
			}
		}
		
		obj.value = v;
		
		// ȡ����
		if( fmt == 1 ){
			var len = 10;
		}
		else if( fmt == 2 ){
			var len = 10;
		}
		else if( fmt == 3 ){
			var len = 8;
		}
		else if( fmt == 4 ){
			var len = 11;
		}
		else{
			var len = 19;
		}
		
		obj.maxLength = len;
		
		// У����Ϣ
		if( notnull ){
			obj.minvalue = len;
			obj.maxvalue = len;
		}
		else{
			obj.minvalue = 0;
			obj.maxvalue = 0;
		}
		
		// ͼ��ͷ���
		var imgs = document.getElementsByName( 'img:' + fieldName );
		if( imgs.length > index ){
			var img = imgs[index];
			
			// td�Ŀ��
			img.parentNode.width = '1%';
			
			// ��ʽ
			img.border = 0;
			img.vspace = 0;
			img.hspace = 0;
			img.style.cursor = 'hand';
			
			// ����
			if( obj.fieldCaption != null && obj.fieldCaption != '' ){
				img.alt = 'ѡ�����ڣ�' + obj.fieldCaption;
			}
			else{
				img.alt = 'ѡ������';
			}
			
			// �¼�
			img.onclick = function(){ calendar(document.getElementsByName(fieldName)[index], fmt); };
			
			// ����ͼ��
			var display = obj.style.display;
			var imgsrc = _browse.contextPath + "/script/calendar/image_calendar.gif";
			setTimeout( function(){img.src = imgsrc; img.style.display = display; img = null;}, 1 );
		}
		
		// �¼�
		var func = function(){ document.getElementsByName('img:'+fieldName)[index].fireEvent('onclick'); };
		obj.ondblclick = func;
		
		// ��������ֹ�����
		if( _dateCanInput == false ){
			obj.contentEditable = false;
			obj.style.cursor = "hand";
			obj.onclick = func;
		}
	}	// end databox
	
	// ����ime-mode
	else if( obj.type == 'text' ){
		_setFieldImeMode( obj );
	}
	
	else if( obj.type == 'file' ){
		if( _fileCanInput == false ){
			obj.contentEditable = false;
			//obj.style.cursor = "hand";
			//obj.onmouseup = function(){ event.srcElement.click() };
		}
	}
	
	obj = null;
}


// data��ǩ�Ĵ�����
var	_loadDataFieldList = new Array();

// ҳ������
var	_loadPageContent = '<html><base target="_self"><head>' +
	'<meta http-equiv="Content-Language" content="zh-cn">' +
	'<meta http-equiv="Content-Type" content="text/html; charset=GBK">' +
	'<title>��ȴ�</title>' +
	'</head>' +
	'<body style="margin:0;padding:0;">' +
	'<table border="0" width="100%" height="100%" cellspacing="0" cellpadding="0" style="border-style: solid; border-width: 1px" bordercolor="#FFFF00">' +
	'<tr height="20%" bgcolor="#FFFF00"><td><font color="blue">&nbsp;��ȴ� ... ...</font></td></tr>' +
	'<tr height="80%" bgcolor="#99CCFF"><td align=center><span name="hint" id="hint" style="font-size: 12pt">�����������ݣ���ȴ� ... ...</span></td></tr>' +
	'</table>' +
	'<form style="margin:0;padding:0;" name="load" method="post">' +
	'<input type="hidden" name="load-data-name" id="load-data-name">' +
	'<input type="hidden" name="load-data-property" id="load-data-property">' +
	'<input type="hidden" name="load-data-fields" id="load-data-fields">' +
	'<input type="hidden" name="load-data-txnCode" id="load-data-txnCode">' +
	'</form></body></html>';

// ����ʵʱ���������Ϣ
function LoadDataField( name,property,fields,txnCode,hint,parameter,onload )
{
	this.name = name;
	this.property = property;
	this.fields = fields;
	this.txnCode = txnCode;
	this.hint = hint;
	this.parameter = parameter;
	this.onload = onload;
}

function addLoadDataField( field )
{
	var	num = _loadDataFieldList.length;
	_loadDataFieldList[num] = field;
}


// ������������Ϣ
function _getLoadFieldByName( name )
{
	var	ii;
	for( ii=0; ii<_loadDataFieldList.length; ii++ ){
		if( _loadDataFieldList[ii].name == name ){
			return _loadDataFieldList[ii];
		}
	}
	
	return null;
}


// ��ʼ��������
function beginLoadData( name, param )
{
	// ��������Ϣ
	var	field = _getLoadFieldByName( name );
	if( field == null ){
		return;
	}
	
	// ȡ�������
	var	parameter = "";
	if( field.parameter != "" ){
		if( field.parameter.indexOf('(') > 0 ){
			parameter = eval( field.parameter );
		}
		else{
			parameter = eval( field.parameter + "()" );
		}
	}
	
	// ���
	var bLoadDataLayer = getObjectById("innerWindow");
    bLoadDataLayer.style.width = 350;
    bLoadDataLayer.style.height = 80;
    
    // ����
    bLoadDataLayer.innerHTML = "<iframe name=innerWindowIframe scrolling=no frameborder=0 width='100%' height='80'></iframe>";
    var	bLoadDataFrame = window.frames("innerWindowIframe");
	
	// ��ʾ����������
	if(!bLoadDataLayer)return;
	var o = bLoadDataLayer.style;
	o.display = "";
 	
    var cw = bLoadDataLayer.clientWidth, ch = bLoadDataLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft;
    var dh = document.body.clientHeight, dt = document.body.scrollTop;
    
    o.top = dt + (dh - ch)/2;
    o.left = dl + (dw - cw)/2;
    
    // ҳ������
    bLoadDataFrame.document.write( _loadPageContent );
    
    // ��ʾ��Ϣ
    if( field.hint != '' ){
    	bLoadDataFrame.document.getElementById("hint").innerText = field.hint;
    }
    
    // ��ʼ��������
	bLoadDataFrame.document.getElementById("load-data-name").value = field.name;
	bLoadDataFrame.document.getElementById("load-data-property").value = field.property;
	bLoadDataFrame.document.getElementById("load-data-fields").value = field.fields;
	bLoadDataFrame.document.getElementById("load-data-txnCode").value = field.txnCode;
	
	// ׼������
	var xurl = rootPath  + '/tag.service?txn-code=data';
	if( param != null && param != '' ){
		xurl = xurl + '&' + param;
	}
	
	if( parameter != null && parameter != '' ){
		xurl = xurl + '&' + parameter;
	}
	
	bLoadDataFrame.document.forms['load'].action = xurl;
	bLoadDataFrame.document.forms['load'].submit();
}


// �������
function _onLoadDataComplete( name, errCode, errDesc, result )
{
	// ���ز�
	var bLoadDataLayer = document.all.innerWindow;
	
	var o = bLoadDataLayer.style;
	if(!o)return;
	o.display = "none";
	
	// �ָ���ť
	clickFlag = 0;
	checkAllMenuItem();
	
	// ��������Ϣ
	var	field = _getLoadFieldByName( name );
	if( field == null ){
		return;
	}
	
	// ��������
	name = name.replace(/[-]/g, '_' );
	
	// ���ú���
	if( errCode == '' || errCode == '000000' ){
		eval( name + ' = result' );
		errCode = '000000';
	}
	else{
		eval( name + ' = new Array()' );
	}
	
	if( field.onload != "" ){
		if( field.onload.indexOf('(') > 0 ){
			eval( field.onload );
		}
		else{
			eval( field.onload + "( errCode, errDesc )" );
		}
	}
}





// TABS��������
// tab�����б�
var	tabFrames = new Array();

// ��Ҫ��������һ��ҳ��
var nextTabUrl = null;

// ��ǰ���ڲ�����tabs����
var	currentTabsName = null;

// ������һ������ҳ���ַ
function setTabUrl( xurl )
{
	nextTabUrl = xurl;
}


// tab��������ʽ
// Active/ActiveR ��ǰ���tab/������ʽ
// Selected/SelectedR �Ѿ�ѡ�е�tab/������ʽ
// Default/DefaultR ������tab/������ʽ

// tabs������Ϣ
// tabName tab����
// maxTabs ������tab����
// style tab����ʽ
// frameClass iframe����ʽ
// scrolling iframe�Ƿ��й�����
// widthTabs tab�������
function tabDefine( tabName, maxTabs, style, frameClass, scrolling, menus )
{
	this.tabName = tabName;
	this.maxTabs = maxTabs;
	this.selectedTab = -1;
	this.style = style;
	this.frameClass = frameClass;
	this.scrolling = scrolling;
	
	this.menus = menus;
	this.subTab = new Array();
	
	// ������
	this.prepareIframe = _tabs_prepareIframe;
	this.appendTab = _tabs_appendTab;
	
	// ��ť
	this.menuClick = _tabs_menuClick;
	this.checkMenuItem = _tabs_checkMenuItem;
	
	// ����ҳ��
	this.loadPage = _tabs_loadPage;
	
}


// ����grid������Ϣ
function addTabDefine( tab )
{
	var	i;
	var	num = tabFrames.length;
	for( i=0; i < num; i++ ){
		if( tabFrames[i].tabName == tab.tabName ){
			tabFrames[i] = tab;
			return;
		}
	}
	
	tabFrames[i] = tab;
}

// �������ƻ�ȡgrid������Ϣ
function getTabDefine( tabName )
{
	var	i;
	for( i=0; i<tabFrames.length; i++ ){
		if( tabFrames[i].tabName == tabName ){
			return tabFrames[i];
		}
	}
	
	return null;
}

// �����ӱ�ǩ
function _tabs_appendTab( item )
{
	// ��tab�ĸ���
	var	num = this.subTab.length;
	
	// �ж��Ƿ��ظ���caption��tabValue��xurl��func
	for( var ii=0; ii<num; ii++ ){
		var t = this.subTab[ii];
		if( t.caption == item.caption &&
			t.tabValue == item.tabValue &&
			t.xurl == item.xurl &&
			t.func == item.func )
		{
			// ��ȫ��ͬ
			return;
		}
	}
	
	this.subTab[num] = item
	item.appendTab( num );
}


// ִ�а�ť����
function _tabs_menuClick( menu )
{
	// ��ֹ��ť
	clickFlag = 1;
	checkAllMenuItem( );
	clickFlag = 0;
	
	// ִ�в���
	menu.fireEvent(null, this.tabName, 0);
	
	// �ָ���ť
	checkAllMenuItem( );
	
	// ��ֹ����ִ�У�����image�ύ
	return false;
}


// ��鰴ť����Ч��
function _tabs_checkMenuItem( )
{
	// ��ť��Ϣ
  	var	menus = this.menus;
  	if( menus == null ){
  		return;
  	}
	
  	if(clickFlag == 0){
  		var status = false;
  	}
  	else{
  		var status = true;
  	}
  	
	// �ָ�/��ֹ ���а�ť
	for( var i=0; i<menus.length; i++ ){
		menus[i].setStatus( status );
	}
}


// ��tab����Ϣ
function subTabDefine( tabName, caption, xurl, func, tabValue, scrolling, styleClass, icon, hint )
{
	// ȱʡ����
	if( styleClass == null || styleClass == 'N/A' ){
		styleClass = tabName;
	}
	
	if( icon == null || icon == 'N/A' ){
		icon = "/script/tabs/tab-icon.gif";
	}
	
	if( caption == null || caption == '' ){
		caption = tabValue;
		if( caption == null || caption == '' ){
			caption = 'TAB��ǩ';
		}
	}
	
	if( tabValue == null || tabValue == '' ){
		tabValue = caption;
	}
	
	if( xurl == null ){
		xurl == '';
	}
	
	if( func == null ){
		func = '';
	}
	
	// ��ʾ��Ϣ
	if( hint == null || hint == '' ){
		hint = xurl;
		if( hint == null || hint == '' ){
			hint = func;
		}
	}
	
	this.tabName = tabName;
	this.caption = caption;
	this.xurl = xurl;
	this.func = func;
	this.tabValue = tabValue;
	this.scrolling = scrolling;
	this.styleClass = styleClass;
	this.icon = icon;
	this.hint = hint;
	this.clickedNumber = -1;
	
	// ����HTML
	this.getHtml = _tab_getHtml;
	this.appendTab = _tab_appendTab;
}

// ������tab��Ϣ
function addSubTab( tabName, caption, xurl, func, tabValue, scrolling, styleClass, icon, hint )
{
	// ������tab
	var item = new subTabDefine( tabName, caption, xurl, func, tabValue, scrolling, styleClass, icon, hint );
	
	// ����tab
	var	tab = getTabDefine( tabName );
	tab.appendTab( item );
}

// ����TAB��HTML��Ϣ
function _tab_appendTab( index )
{
	var tabs = document.getElementById( this.tabName );
	var row = tabs.rows[0];
	var cells = row.cells;
	var tab = row.insertCell( cells.length - 1 );
	
	tab.innerHTML = this.getHtml( index );
}

function _tab_getHtml( index )
{
	var str = "<table cellspacing='0' cellpadding='0' border='0' height='100%' width='100%'>\n";
	str += "<tr>\n";
	
	str += "<td nowrap id='" + this.tabName + "_td_" + index + "'";
	str += " align='center'";
	str += " class='" + this.styleClass + "_Default" + "'";
	str += " unselectable='on'";
	str += " onmouseover=" + '"' + "activeTab('" + this.tabName + "', " + index + ");" + '"';
	str += " onmouseout=" + '"' + "defaultTab('" + this.tabName + "', " + index + ");" + '"';
	str += " onclick=" + '"' + "clickTab('" + this.tabName + "', " + index + ");" + '"';
	str += ">";
	
	// ͼ��
	if( this.icon != '' ){
		str += "<img";
		str += " src='" + _browse.contextPath + this.icon + "'";
		str += " alt='" + this.hint + "'>";
	}
	
	// ����
	str += "<span title='" + this.hint + "'>" + this.caption + '</span></td>\n';
	
	// �ָ��
	str += "<td nowrap";
	str += " id='" + this.tabName + "_td_" + index + "R'";
	str += " class='" + this.styleClass + "_Default" + "R'";
	str += " width='6px' unselectable='on'>��</td>\n";
	
	str += "</tr>\n";
	str += "</table>";
	
	return str;
}


// ѡ��һ��tab
function activeTab( tabName, idx ){
	var	tab = getTabDefine( tabName );
	if( tab.selectedTab != idx ){
		var btn1 = tabName + '_td_' + idx;
		var btn2 = btn1 + 'R';
		var btnObj1=document.getElementById(btn1);
		var btnObj2=document.getElementById(btn2);
		if(btnObj1 && btnObj2){
			btnObj1.className = tab.style + '_Active';
			btnObj2.className = tab.style + '_ActiveR';
		}
	}
}

// ����tab����ʽ
function defaultTab( tabName, idx ){
	var	tab = getTabDefine( tabName );
	if( tab.selectedTab != idx ){
		var btn1 = tabName + '_td_' + idx;
		var btn2 = btn1 + 'R';
		document.getElementById(btn1).className = tab.style + '_Default';
		document.getElementById(btn2).className = tab.style + '_DefaultR';
	}
}

// ѡ��tab����ʽ
function selectedTab( tabName, idx ){
	var	tab = getTabDefine( tabName );
	var btn1 = tabName + '_td_' + idx;
	var btn2 = btn1 + 'R';
	document.getElementById(btn1).className = tab.style + '_Selected';
	document.getElementById(btn2).className = tab.style + '_SelectedR';
}

// ȡ��ǰ���ڴ����tab�ĵ������
function isClicked()
{
	if( currentTabsName == null ){
		return 0;
	}
	
	// ����tab�Ķ��壬���û�иı䣬������
	var	tab = getTabDefine( currentTabsName );
	if( tab == null || tab.selectedTab < 0 ){
		return 0;
	}
	
	// ȡxurl��func
	var	subTab = tab.subTab[tab.selectedTab];
	if( subTab == null ){
		return 0;
	}
	
	// ���ص���Ĵ���
	return subTab.clickedNumber;
}


// ѡ��һ��tabʱ
function clickTab( tabName, idx )
{
	// ���õ�ǰ���ڴ����tabs
	currentTabsName = tabName;
	
	// ����tab�Ķ���
	var	tab = getTabDefine( tabName );
	
	// �ж�idx�Ƿ���Ч
	if( idx == null || idx < 0 || idx >= tab.subTab.length ) idx = 0;
	
	// ���û�иı䣬������
	if( tab.selectedTab == idx || tab.subTab.length == 0 ) return;
	
	// ���õ�ǰѡ�е�tabIndex
	var	oldIdx = tab.selectedTab;
	tab.selectedTab = idx;
	
	// �ָ�ԭ���Ѿ�ѡ�е�tab����ʽ
	if( oldIdx >= 0 ){
		defaultTab( tabName, oldIdx );
	}
	
	// �����Ѿ�ѡ�е�tab��ʽ
	selectedTab( tabName, idx );
	
	// ȡxurl��func
	var	subTab = tab.subTab[idx];
	var func = subTab.func;
	
	// ��ǩ������Ĵ���
	subTab.clickedNumber = subTab.clickedNumber + 1;
	
	// ����:���û��Ļص������п���ʹ��
	var	caption = subTab.tabValue;
	
	// onclick����
	if( func != null && func != '' ){
  		eval( func.replace(/`/g, "'") );
  	}
	
	// ִ�ж���
	tab.loadPage( idx );
}

//����tab̫������
function adjust_tab(){
	var tabItem = document.getElementById("tabsAction");
	if(tabItem){
		var tabCon = document.getElementById("tab_con");
		tabItem.style.marginLeft = '10px';
		var next = document.getElementById("tab_next");
		var count = 1;
		var marginLeftWidth = count*400;
	//	next.innerHTML = "����";
		if(tabCon.clientWidth >= tabItem.clientWidth){
			next.style.display = 'none';
		}else{
			next.style.display = 'block';
			next.onclick = function(){
				marginLeftWidth = count*400;
				if(marginLeftWidth < tabItem.clientWidth || (tabItem.clientWidth-marginLeftWidth) > 400){
					tabItem.style.marginLeft = '-'+marginLeftWidth+'px';
				}else{
					tabItem.style.marginLeft = '10px';
					count = 0;
				}
				count = count+1;
			}
		}
	}
	
}

// ����ҳ��
function _tabs_loadPage( idx )
{
	var	subTab = this.subTab[idx];
	
	// ����iframe
	this.prepareIframe( idx );
	
	// ����
	var frameObject = document.getElementById( this.tabName + '_frameX' );
	
	// ������תҳ��:nextTabUrl���û��Ļص���������
	if( nextTabUrl == null || nextTabUrl == '' ){
		nextTabUrl = subTab.xurl.replace(/`/g, "'");
	}
	
	// ��ת
	if( nextTabUrl.charAt(0) == "/" ){
		if( nextTabUrl.indexOf(rootPath + "/") != 0 ){
			nextTabUrl = rootPath + nextTabUrl;
		}
	}
	else{
		nextTabUrl = rootPath + menuPath + nextTabUrl;
	}
	
	// alert( nextTabUrl );
	navigateTo( frameObject, nextTabUrl );
	
	// ���ù�����
	if( subTab.scrolling != null && subTab.scrolling != '' ){
		if( subTab.scrolling == 'no' || subTab.scrolling == 'false' ){
			frameObject.document.body.style.overflow = 'hidden';
		}
	}
}

// ����IFRAME
function _tabs_prepareIframe( index )
{
	var str = "<iframe id='" + this.tabName + "_frameX'";
	if( this.frameClass != '' ){
		str += " class='" + this.frameClass + "'";
	}
	
	var	subTab = this.subTab[index];
	if( subTab.scrolling != '' ){
		str += " scrolling='" + subTab.scrolling + "'";
	}
	else if( this.scrolling != '' ){
		str += " scrolling='" + this.scrolling + "'";
	}
	
	str += " frameborder='0' width='100%' height='0'></iframe>";
	
	// ��������
	var td = document.getElementById( 'td_' + this.tabName );
	td.innerHTML = str;
}


// LINK�Ĵ�����
function _processLink( index )
{
	var obj = window.event.srcElement;
	while( obj.type != 'link' ){
		obj = obj.parentNode;
	}
	
	// ȡonclick
	var strClick = '';
	var clickFunc = obj._onclick;
	if( clickFunc != null && clickFunc != '' ){
		var ptr = clickFunc.indexOf( "(" );
		if( ptr > 0 ){
			// ǰ�沿��
			strClick += clickFunc.substring(0, ptr+1);
			
			// ���沿��
			clickFunc = clickFunc.substring( ptr+1 );
			
			// ���Ų���
			ptr = clickFunc.indexOf( ")" );
			var val1 = clickFunc.substring(0, ptr).trim( 0 );
			if( val1 == '' ){
				strClick += index;
				strClick += clickFunc.substring(ptr);
			}
			else{
				strClick += clickFunc;
			}
		}
		else{
			strClick = clickFunc + "( index )";
		}
		
		eval( strClick );
	}
	
	// href
	var href = obj.href;
	var target = obj.target;
	if( href != null && href != '' ){
		navigateTo( window, href, target );
	}
	
	return false;
}

// ƽ̨��EDIT
function _edit_TabKey()
{
	var code, tmp;
	event.returnValue = false;
	var sel =event.srcElement.document.selection.createRange();
	var r = event.srcElement.createTextRange();

	switch( event.keyCode ){
		case( 8 ):
			if( !(sel.getClientRects().length > 1) ){
				event.returnValue = true;
				return;
			}
			
			code = sel.text;
			tmp = sel.duplicate();
			tmp.moveToPoint( r.getBoundingClientRect().left, sel.getClientRects()[0].top-_getWindowOffsetTop() );
			sel.setEndPoint( "startToStart", tmp );
			sel.text = sel.text.replace(/^\t/gm, "");
			code = code.replace(/^\t/gm, "").replace(/\r\n/g, "\r");
			r.findText(code);
			r.select();
			break;
			
		case (9):
			if (sel.getClientRects().length > 1){
				code = sel.text;
				tmp = sel.duplicate();
				tmp.moveToPoint(r.getBoundingClientRect().left, sel.getClientRects()[0].top-_getWindowOffsetTop());
				sel.setEndPoint("startToStart", tmp);
				sel.text = "\t"+sel.text.replace(/\r\n/g, "\r\t");
				code = code.replace(/\r\n/g, "\r\t");
				r.findText(code);
				r.select();
			}
			else{
				sel.text = "\t";
				sel.select();
			}
			
			break;
			
		case (13):
			tmp = sel.duplicate();
			tmp.moveToPoint(r.getBoundingClientRect().left, sel.getClientRects()[0].top-_getWindowOffsetTop());
			tmp.setEndPoint("endToEnd", sel);
			
			// ����ո�
			var tabs="";
			var t = tmp.text;
			for( var ii=0; ii<t.length; ii++ ){
				var c = t.charAt( ii );
				if( c == ' ' || c == '\t' ){
					tabs += c;
				}
				else{
					break;
				}
			}
			
			sel.text = "\r\n" + tabs;
			sel.select();
			break;
			
		default:
			event.returnValue = true;
			break;
	}
}


// ����richedit
function prepareRichedit( fieldName, type )
{
	xsEditor.emoticonPath=rootPath + "/script/richedit/images/emoticons/"
	xsEditor.emoticonList="emoticon-smile.gif,emoticon-wink.gif,emoticon-laugh.gif,emoticon-sad.gif,emoticon-ambivalent.gif,emoticon-tongue-in-cheek.gif,emoticon-surprised.gif,emoticon-unsure.gif,icon-error.gif,icon-info.gif,icon-warning.gif,emoticon-8.gif,emoticon-b.gif,emoticon-c.gif,emoticon-cat.gif,emoticon-d.gif,emoticon-e.gif,emoticon-f.gif,emoticon-g.gif,emoticon-h.gif,emoticon-i.gif,emoticon-k.gif,emoticon-l.gif,emoticon-u.gif,emoticon-n.gif,emoticon-y.gif,emoticon-s.gif,emoticon-star.gif,emoticon-t.gif,emoticon-m.gif,emoticon-p.gif,wiki-ftp.gif,wiki-mailto.gif,wiki-news.gif,wiki-wiki.gif,asp.gif,avi.gif,bmp.gif,chm.gif,doc.gif,gif.gif,gz.gif,html.gif,jpeg.gif,mov.gif,mp3.gif,mpeg.gif,pdf.gif,png.gif,ppt.gif,txt.gif,xls.gif,xml.gif,xsl.gif" 
	xsEditor.replaceTags="<strong>,<b>"
	xsEditor.path = rootPath + "/script/richedit/";
	
	xsEditor.showInsertImage="false"
	xsEditor.showEmoticon="false"
	// xsEditor.showMode="false"
	xsEditor.showPreview="false"
	xsEditor.showPrint="false"
	xsEditor.showCancel="false"
	xsEditor.showSave="false"
	
	var	obj = document.getElementById(fieldName);
	if( obj == null || obj.value == "" ){
		return;
	}
	
	if( type == 'text' ){
		xsEditor.insertText( obj.value );
	}
	else{
		xsEditor.insertHtml( obj.value );
	}
}


// ����title
function setPageTitle( caption )
{
	if( openWindowType == 'subframe' ){
		parent.setPageTitle( caption );
	}
	else{
		var	obj = document.getElementById( 'freeze-title' );
		if( obj != null ){
			obj.innerHTML = caption;
		}
	}
}

function getPageTitle()
{
	if( openWindowType == 'subframe' ){
		return parent.getPageTitle( );
	}
	else{
		var	obj = document.getElementById( 'freeze-title' );
		if( obj != null ){
			return obj.innerText;
		}
		else{
			return '';
		}
	}
}


// ҳ��ı���
function _preparePageTitle( caption, icon, styleClass, bgcolor, forecolor )
{
	//console.log("caption = " + caption);
	caption = caption.replace(/\s>\s/g, "<span class='ptan'></span>");
	if( openWindowType == 'subframe' ){
		//parent.setPageTitle( "��ǰλ�ã� " + caption );
		var str_caption = "<table style='' cellspacing='0' cellpadding='0'><tr><td width=15 style='background:url(/script/menu-images/pos.png) left 75% no-repeat;'></td><td><span style='font-weight:bold;'>����λ��</span> " + caption + "</td></tr></table>";
		parent.setPageTitle(str_caption );
	}
	else if( document.getElementById('freeze-title') == null ){
		// ��ʽ
		if( styleClass == null || styleClass == '' ){
			styleClass = 'hide';
		}
		
		// ͼ��
		if( icon == null || icon == '' ){
			icon = "/script/title/title-icon.gif";
		}
		else{
			if( icon.charAt(0) != '/' ){
				icon = menuPath + icon;
			}
		}
		
		if( icon.indexOf(_browse.contextPath+'/') != 0 ){
			icon = _browse.contextPath + icon;
		}
		
		// ���ɱ��
		var str = "<table width='95%'  align='center' cellpadding='0' cellspacing='0' style='table-layout:fixed;margin-top:3px; margin-bottom:3px;'>\n";
		str += "<tr class='" + styleClass + "'";
	   
		// ����ɫ
		if( bgcolor != null && bgcolor != '' ){
			str += " bgcolor='" + bgcolor + "'";
		}
		
		str += ">\n";
		   
		// ͼ��
	 //	str += "<td width='36' align='right' valign='middle' style='border-bottom: 1px; border-left: 1px; border-top: 1px; border-right: 1px;'>";
	//	str += "<img src='" + icon + "' width='7' height='7' hspace='8'  border='0' onclick='_keyboard.fireEvent(" + '"CTRL F12"' + ")'>";
	//	str += "</td>\n";
		
		// ����
		str += "<td height='25' align='left' valign='middle' nowrap id='freeze-title'";
		
		// �������ɫ
		if( forecolor != null && forecolor != '' ){
			str += " style='line-height:100%;color:" + forecolor + ";'";
		}
		
		str += ">\n";
		
		//alert(caption);
		// ����
	//	caption=caption.replace(/(^\s*)|(\s*$)/g,"");
		str = str + "<table cellspacing='0' cellpadding='0'><tr><td><td width=15 style='background:url(/script/menu-images/pos.png) left 75% no-repeat;'></td><td><span style='font-weight:bold;'>����λ��</span> " + caption + "</td></tr></table>";
		//str += caption;
		//alert(caption);
		//str += "������" + caption;
	
		// ����
		str += "</td></tr></table>";
		document.write( str );
	}

	_showProcessHintWindow( '���ڼ���ҳ�� ... ' );
}



// ������ҳ�������Ϣ
function _fakeProgress( barName, v )
{
	var el = document.getElementById( barName );
	if( el == null || el.style.display == 'none' ){
		return;
	}
	
	if( v == null ){
		v = 0;
	}
	
	if (v >= 100) {
		el.childNodes[1].style.width = v + "%"; 
		el.childNodes[0].innerText = '����ҳ��ɹ���' + v + "%"; 
	}
	else {
		el.childNodes[1].style.width = v + "%"; 
		el.childNodes[0].innerText = '���ڼ���ҳ�棺' + v + "%"; 
		
		if( v < 50 ){
			var ti = 200;
		}
		else if( v < 70 ){
			var ti = 140;
		}
		else if( v < 80 ){
			var ti = 100;
		}
		else if( v < 90 ){
			var ti = 70;
		}
		else if( v < 95 ){
			var ti = 50;
		}
		else{
			var ti = 100;
		}
		
		var t = (6000 * ti) / ((100 - v) * (100 - v));
		window.setTimeout(function(){_fakeProgress(barName, v+1)}, t); 
	} 
}

function _prepareProgressBar( name, height, width )
{
	if( height == null || height < 26 ){
		height = 26;
		var top = 0;
	}
	else{
		var top = (height - 26) / 2;
	}
	
	if( width == null ){
		width = '100%';
	}
	
	var str = '<span name="progressBar" style="height:' + height + ';width:100%;background-color:#FFFFFF;text-align:center;">';
	str += '<div id="' + name + '" name="' + name + '" style="BORDER: #CCCCCC 1px inset; WIDTH:' + width + ';text-align:left; HEIGHT: 26px; margin-top:' + top + '">';
	str += '<div style="FONT-SIZE: 16px; WIDTH: 100%; COLOR: red; TEXT-ALIGN: center; POSITION: absolute; line-height:150%;">0%</div>';
	str += '<DIV style="BACKGROUND: #EEEEFF; WIDTH: 0%; HEIGHT: 24px">';
	str += '<DIV style="FILTER: Alpha(Opacity=0, FinishOpacity=80, Style=1, StartX=0, StartY=0, FinishX=100, FinishY=0); BACKGROUND:#7777FF; WIDTH: 100%; HEIGHT: 100%"></DIV></DIV>';
	str += '</div></span>';
	return str;
}

function _createProgressBar( name, height, width )
{
	document.write( _prepareProgressBar(name, height, width) );
	document.write( '<script type=text/javascript>_fakeProgress("' + name + '");</scr' + 'ipt>' );
}

// ����ҳ��ʱ��ʾ�Ľ�����
function _showProgress( )
{
	var progressLayer = document.all.innerWindow;
	progressLayer.innerHTML = _prepareProgressBar( 'progress' );
	progressLayer.style.height = 26;
	progressLayer.style.width = '60%';
	progressLayer.style.display = '';
	setInnerWindowPosition( );
}


// freeze:iframe


// ��ʾ��ʾ��Ϣ
document.write("<div id=hintWindowLayer style='position: absolute; z-index: 9999; width: 150; height: 70; display: none'></div>");
document.write("<IFRAME id='hintWindowIFrame' name='hintWindowIFrame' frameborder='0' style='position: absolute; z-index: 9998; width: 150; height: 70; display: none'></IFRAME>");
document.write("<IFRAME id='optionContainerFrame' name='optionContainerFrame' frameborder='0' style='position: absolute; z-index: 9998; display: none'></IFRAME>");


function _showProcessHintWindow( hint )
{
	// ��ʾ��Ϣ
	if( hint == null || hint == '' ){
		hint = "����ִ��������ȴ�������";
	}
	var dt = new Date();

	hint = '<div style="border:1px solid #A7DAED;background:#fff;">'+
			'<div style="width:100%;text-align:center;padding:2px 0px;filter:alpha(opacity=70);"><img src="/script/menu-images/onload.gif" width="80" height="80"/></div>'+
			'<div style="height:16px;width:100%;text-align:center;color:#336699;">'+ hint + '</div>'+
			'<div style="width:100%;text-align:center;color:#336699;"><span id="_HintTableCaptionMinute">00</span>:<span id="_HintTableCaptionSecond">00</span></div></div>';
	// ���
	var bHintLayer = document.all.hintWindowLayer;
	bHintLayer.innerHTML = hint;
	
	// ��ʾ����������
	
	var o = bHintLayer.style;
	if(!o)return;
	o.display = "";
 	
    var cw = 150, ch = 70;
    
    if( openWindowType == 'modal' ){
    	var dw = window.dialogWidth;
	    var dh = window.dialogHeight;
	    if( dw == null || dh == null ){
	    	return;
	    }
	    
	    var ptr = dw.indexOf('px');
	    if( ptr > 0 ){
	    	dw = dw.substring( 0, ptr );
	    }
	    
	    ptr = dh.indexOf('px');
	    if( ptr > 0 ){
	    	dh = dh.substring( 0, ptr );
	    }
    }
    else{
	    var dw = document.body.clientWidth;
	    var dh = document.body.clientHeight;
		if(dh==0){
			dh = 700;
		}
		if(dw ==0 ){
			dw = 1000;
		}
	}
    
    o.top = (dh - ch)/2;
    o.left = (dw - cw)/2;
    var hintWindowIFrame = document.getElementById("hintWindowIFrame");
    hintWindowIFrame.style.top = o.top;
    hintWindowIFrame.style.left = o.left;
    hintWindowIFrame.style.height = bHintLayer.offsetHeight;
    hintWindowIFrame.style.width = bHintLayer.offsetWidth;
    var hintWindowIFrameStatus = hintWindowIFrame.style.display;
    hintWindowIFrame.style.display = "block";
    var promited = false;
    // ���û�����ؾ�����ʾ������ֱ�Ӻ���
    if (hintWindowIFrameStatus != "block"){
	  	window._checkWaitTime = setInterval( function(){
	    	var secondWithoutTransfer = (new Date - dt)/1000;
	    	var mins = Math.floor(secondWithoutTransfer / 60);
	    	var secs = Math.ceil(secondWithoutTransfer % 60);
	    	mins = mins < 10 ? "0" + mins : mins;
	    	secs = secs < 10 ? "0" + secs : secs;
	    	document.getElementById("_HintTableCaptionMinute").innerHTML = mins;
	    	document.getElementById("_HintTableCaptionSecond").innerHTML = secs;
	    	if (secondWithoutTransfer >= 60 && !promited){
	    		// document.getElementById("_HintTableCaption").innerHTML = "ִ�й��̽����������Լ����ȴ�Ҳ���Է��ػ����˳�";
	    		alert("ִ�й��̽����������Լ����ȴ�Ҳ���Է��ػ����˳�");
	    		promited = true;
	    	}
	    } , 1000);    
    }
}

function _hideProcessHintWindow()
{
	var bHintLayer = document.all.hintWindowLayer;
	
	var o = bHintLayer.style;
	if(!o)return;
	o.display = "none";
	document.getElementById("hintWindowIFrame").style.display = "none";
	clearInterval(window._checkWaitTime);
	if (document.getElementById("_HintTableCaptionMinute")){
        document.getElementById("_HintTableCaptionMinute").innerHTML = "00";
        document.getElementById("_HintTableCaptionSecond").innerHTML = "00";
	}
}


// ȡ����������DIV
function _getScrollDiv()
{
	var objs = document.body.childNodes;
	for( var ii=0; ii<objs.length; ii++ ){
		if( objs[ii].className == 'body-div' ){
			return objs[ii];
		}
	}
	
	return null;
}


//-->
