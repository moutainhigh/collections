//<!--
/* ***************** 按钮 ******************** */

// name 按钮名称
// menuId 按钮的唯一ID
// validRule 有效条件
// func 函数
// hotkey 热键
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
	// 设置状态
	this.setStatus = button_setStatus;
	
	// 函数
	this.fireEvent = button_fireEvent;
	
}

// 设置状态
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
			//查询
			btn_img = 'btn_query';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_addRecord'==this.name){
			//添加
			btn_img = 'btn_add';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_backupRecord'==this.name){
			//添加
			btn_img = 'btn_backup';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_prevRecord'==this.name){
			//同步
			btn_img = 'btn_prev';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_nextRecord'==this.name){
			//同步
			btn_img = 'btn_next';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_saveAndExit'==this.name || 'record_saveRecord'==this.name){
			//保存
			btn_img = 'btn_save';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_goBack'==this.name || 'record_goBackNoUpdate'==this.name){
			//返回
			btn_img = 'btn_cancel';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_syncRecord'==this.name){
			//同步
			btn_img = 'btn_sync';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_importRecord'==this.name){
			//倒入
			btn_img = 'btn_import';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_createTable'==this.name){
			//生成表结构
			btn_img = 'btn_gen';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_getFunction'==this.name){
			//获取数据
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
			//测试
			btn_img = 'btn_test';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_testRecord_old'==this.name){
			//共享服务测试(旧)
			btn_img = 'btn_testold';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('record_testRecord_new'==this.name){
			//共享服务测试(新)
			btn_img = 'btn_testnew';
			btnImg.onclick = function(){
				var src = window.event.srcElement;
				if(src.previousSibling){
					src.previousSibling.click();
				}
			}
		}else if('psw-inte'==this.name){
			//共享服务测试(新)
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

// 执行函数
function button_fireEvent( selRows, frameName, frameIndex )
{
	// 设置按钮名称
	var objs = document.getElementsByName('inner-flag:menu-name');
	if( objs != null ){
		for( var i=0; i<objs.length; i++ ){
			objs[i].value = this.name;
		}
	}
	
	// 表单名称
	currentFormName = _getFormName( frameName, frameIndex );
	
	// 执行操作
	if( this.func != null ) this.func( selRows, frameName, frameIndex, this.name );
}

// 按钮的click事件
function _menuClick( menuId )
{
	var menu = null;
	
	// 取GRID和MENU
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
	
	// 匹配到按钮
	if( menu != null ){
		return grid.menuClick( menu );
	}
	
	// 查找frame和MENU
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
	
	// 匹配到按钮
	if( menu != null ){
		return frame.menuClick( menu );
	}
	
	// 查找TABS和MENU
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
	
	// 匹配到按钮
	if( menu != null ){
		return tab.menuClick( menu );
	}
	
	return false;
}



// 检查所有按钮的有效性
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


// 判断是否按钮
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


// 滤镜
var __activeFilter = 'blur(add=1,direction=120,strength=2)';
var __disableFilter = 'Alpha(stype=0,opacity=40)';
var __selectedFilter = 'Alpha(stype=0,opacity=70)';

// 设置按钮的有效状态
// status = true 禁止
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
	// 判断按钮是否被禁止
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
		// 非图形按钮，返回
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
	// 设置滤镜
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

// 设置按钮样式
// flag=true 按钮正被选中（鼠标在按钮里）
// flag=false 按钮没有选中
function setButtonStyle(flag)
{
	obj = window.event.srcElement;
	if( obj == null ){
		return;
	}
	
	if( obj.tagName.toLowerCase() == 'input' ){
		// 非图形按钮，返回
		if( obj.type.toLowerCase() != 'image' ){
			return;
		}
	}
	else if( obj.tagName.toLowerCase() != 'img' ){
		return;
	}
	
	if( flag ){
		// 活动
		if( obj.active != null && obj.active != '' ){
			obj.src = obj.active;
		}
		else{
			obj.style.filter = __activeFilter;
		}
	}
	else{
		if( __selectedButton == obj ){
			// 选中
			if( obj.selected != null && obj.selected != '' ){
				obj.src = obj.selected;
			}
			else{
				obj.src = obj.normal;
				obj.style.filter = __selectedFilter;
			}
		}
		else{
			// 正常
			if( obj.active != null && obj.active != '' ){
				obj.src = obj.normal;
			}
			else{
				obj.style.filter = '';
			}
		}
	}
}

// 当前选中的按钮
var __selectedButton = null;

// 设置选中的按钮样式
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
		// 非图形按钮，返回
		if( obj.type.toLowerCase() != 'image' ){
			return;
		}
	}
	else if( obj.tagName.toLowerCase() != 'img' ){
		return;
	}
	
	// 是否已经选中了按钮或连接
	if( __selectedButton != null ){
		var selectedImage = __selectedButton.selected;
		if( selectedImage != null && selectedImage != '' ){
			__selectedButton.src = __selectedButton.normal;
		}
		else{
			__selectedButton.style.filter = '';
		}
	}
	
	// 处理当前的按钮或连接
	__selectedButton = obj;
	
	// 判断是否定义了活动图标
	var selectedImage = __selectedButton.selected;
	if( selectedImage != null && selectedImage != '' ){
		__selectedButton.src = selectedImage;
	}
	else{
		__selectedButton.src = __selectedButton.normal;
		__selectedButton.style.filter = __selectedFilter;
	}
}



/* ***************** frame 和 block ******************* */
// Frame标签的信息
var	gframes = new Array();

// frame信息
function frameDefine( frameName, frameType, index, captionWidth, 
		fieldStype, labelStyle, onkeyup, onkeydown, menus )
{
	this.frameName = frameName;
	this.frameType = frameType;
	this.index = index;
	
	// 标题的宽度
	this.captionWidth = captionWidth;
	
	// 样式
	this.fieldStype = fieldStype;
	if( labelStyle == '' ){
		this.labelStyle = fieldStype;
	}
	else{
		this.labelStyle = labelStyle;
	}
	
	// 事件
	this.onkeyup = onkeyup;
	this.onkeydown = onkeydown;
	
	// 按钮列表
	this.menus = menus;
	
	// 函数
	this.checkMenuItem = frame_checkMenuItem;
	this.menuClick = frame_menuClick;
	
	// 初始化函数
	this.initFrameStyle = frame_initFrameStyle;
}

// 增加frame信息
function addFrameDefine( fmDefine )
{
	var	i;
	var	num = gframes.length;
	for( i=0; i < num; i++ ){
		if( gframes[i].frameName == fmDefine.frameName && gframes[i].index == fmDefine.index ){
			// 合并按钮
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
	
	// 保存
	gframes[i] = fmDefine;
	
	// 初始化
	gframes[i].initFrameStyle();
}


// 根据名称获取frame定义信息
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


// 执行按钮处理
function frame_menuClick( menu )
{
	// 禁止按钮
	clickFlag = 1;
	checkAllMenuItem( );
	clickFlag = 0;
	
	// 执行操作
	menu.fireEvent(null, this.frameName, this.index);
	
	// 恢复按钮
	checkAllMenuItem( );
	
	// 禁止继续执行，避免image提交
	return false;
}


// 检查按钮的有效性
function frame_checkMenuItem( )
{
	// 按钮信息
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
  	
	// 恢复/禁止 所有按钮
	for( var i=0; i<menus.length; i++ ){
		menus[i].setStatus( status );
	}
}


// 显示行
function setFrameRowVisible( frameName, rowid, flag )
{
	if( window.event != null && window.event.srcElement != null ){
		var frameObject = getFieldFrameObject( window.event.srcElement );
		if( frameObject != null && frameObject.name == frameName ){
			frameName = frameObject.id;
		}
	}
	
	// 取表
	var	table = document.getElementById( 'main_' + frameName );
	if( table == null ){
		return null;
	}
	
	// 取行
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


// 取域的FRAME
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


// 矩形block的开始部分
function frame_beginBlockHead( name, index, align, width, height, border, borderColor, styleClass )
{
	var str = "<table cellspacing='0' cellpadding='0'  frameType='block'";
	
	// 名称
	str += " name='" + name + "'";
	if( index > 0 ){
		str += " id='" + name + index + "'";
	}
	else{
		str += " id='" + name + "'";
	}
	
	// 宽度
	if( width != '' ){
		str += " width='" + width + "'";
	}
	
	if( height != '' ){
		str += " height='" + height + "'";
	}
	
	// 对齐
	if( align == '' ){
		str += " align='center'"
	}
	else{
		str += " align='" + align + "'";
	}
	
	border = '0';
	// 边框
	if( border != '' ){
		// 取线条的颜色
		if( borderColor == null || borderColor == '' ){
			css = _browse.getCssStyle( styleClass );
			if( css != null ){
				borderColor = css.backgroundColor;
			}
		}
		/*borderColor = '#006699';
		// 生成样式
		if( borderColor != null && borderColor != '' ){
			str += " style='border-left-style:solid; border-left-width:" + border + "px;";
			str += " border-right-style:solid; border-right-width:" + border + "px;'";
			str += " border-bottom-style:solid; border-bottom-width:" + border + "px;'";
			str += " bordercolor='" + borderColor + "'";
		}*/
		str += "style='margin-top:10px;'";
	}
	
	// 样式
	if( styleClass != '' ){
		str += " class='" + styleClass + "'";
	}
	
	document.write( str + '>' );
}


// 园角表单的开始
function frame_beginRoundBlockHead( name, index, align, width, borderColor, bgColor, styleClass )
{
	// 取背景色
	var css = _browse.getCssStyle( styleClass );
	if( css != null ){
		if( css.backgroundColor != null ){
			bgColor = css.backgroundColor;
		}
	}
	
	if( bgColor == null || bgColor == '' ){
		bgColor = '#FFFFFF';
	}
	
	// 取线条的颜色
	if( borderColor == null || borderColor == '' ){
		if( css != null ){
			borderColor = css.borderColor;
		}
		
		if( borderColor == null || borderColor == '' ){
			borderColor = '#000000';
		}
	}
	
	// 对齐
	var str = '<table name="roundTable" id="roundTable"'
	if( align == '' ){
		str += " align='center'"
	}
	else{
		str += " align='" + align + "'";
	}
	
	// 宽度
	if( width != '' ){
		str += " width='" + width + "'";
	}
	else{
		str += "width='100%'";
	}
	
	str += '><tr><td>';
	
	str += "<span style='width:100%'>";
	
	// 边框
	str += '<b class="roundtop">';
	str += '<b class="line1" style="background:' + borderColor + '"></b>';
	str += '<b class="line2" style="background:' + borderColor + '"><b class="line12" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line3" style="background:' + borderColor + '"><b class="line13" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line4" style="background:' + borderColor + '"><b class="line13" style="background:' + bgColor + '"></b></b>';
	str += '<b class="line5" style="background:' + borderColor + '"><b class="line15" style="background:' + bgColor + '"></b></b>';
	str += '</b>';
	
	// table
	str += "<table border='0' cellspacing='0' cellpadding='0' width='100%' frameType='block' bgcolor='" + bgColor + "'";
	
	// 名称
	str += " name='" + name + "'";
	if( index > 0 ){
		str += " id='" + name + index + "'";
	}
	else{
		str += " id='" + name + "'";
	}
	
	// 边框
	var border = 1;
	str += " style='border-left-style:solid; border-left-width:" + border + "px;";
	str += " border-right-style:solid; border-right-width:" + border + "px;'";
	str += " bordercolor='" + borderColor + "'";
	
	// 样式
	if( styleClass != '' ){
		str += " class='" + styleClass + "'";
	}
	
	// alert( str );
	document.write( str + '>' );
}


function frame_endRoundBlockHead( borderColor, bgColor, styleClass )
{
	// 取背景色
	var css = _browse.getCssStyle( styleClass );
	if( css != null ){
		if( css.backgroundColor != null ){
			bgColor = css.backgroundColor;
		}
	}
	
	if( bgColor == null || bgColor == '' ){
		bgColor = '#FFFFFF';
	}
	
	// 取线条的颜色
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


// 用于打印的block的开始部分
function frame_beginPrintBlockHead( name, index, align, width )
{
	var str = "<table cellspacing='0' cellpadding='0'";
	
	// 名称
	str += " name='" + name + "'";
	if( index > 0 ){
		str += " id='" + name + index + "'";
	}
	else{
		str += " id='" + name + "'";
	}
	
	// 宽度
	if( width != '' ){
		str += " width='" + width + "'";
	}
	
	// 对齐
	if( align == '' ){
		str += " align='center'"
	}
	else{
		str += " align='" + align + "'";
	}
	
	
	document.write( str + '>' );
}


// 标题
function frame_prepareBlockTitle( align, height, styleClass, bgColor, icon, caption )
{
	var str = "<tr valign='center'";
	
	// 对齐
	if( align != '' ){
		str += " align='" + align + "'";
	}
	
	// 样式
	if( styleClass != '' ){
		str += " class='" + styleClass + "'";
	}
	else if( bgColor != '' ){
		str += " bgcolor='" + bgColor + "'";
	}
	
	if( height != null && height != '' ){
		str += " style=' height:" + height + ";";
	}
	
	// 颜色
	str += ">\n<td><table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td style='display:none;' class='secTitle'>";
	
	// 图标
	if( icon != '' ){
		if( icon.charAt(0) == '/' ){
			icon = _browse.contextPath + icon;
		}
		
		str += "<img border='0' style='display:;' src='" + icon + "'>&nbsp;";
	}
	
	// 标题
	str += caption;
	
	// 结束
	str += "</td></tr></table></td>\n</tr>\n";
	
	document.write( str );
}


// 开始正文
// borderColor 分割线的颜色，none时没有分割线
function frame_beginBlockBody( name, index, borderColor, nowrap, styleClass )
{
	// 新的表格
	var str = "<table  width='100%' height='100%' cellpadding='0'";
	
	// name 和 id
	if( index > 0 ){
		var t = "'main_" + name + index + "'";
	}
	else{
		var t = "'main_" + name + "'";
	}
	
	str += " name=" + t + " id=" + t;
	
	borderColor = '#006699';
	
	// 边框
	if( borderColor != 'none' ){
		// 取线条的颜色
//		if( borderColor == null || borderColor == '' ){
//			css = _browse.getCssStyle( styleClass );
//			if( css != null ){
//				borderColor = css.backgroundColor;
//			}			
//			if( borderColor == null || borderColor == '' ){
//				borderColor = '#7f9db9';				
//			}		
//		}
		
		// 线条
		str += " cellspacing='0' ";
	}
	else{
		str += " cellspacing='0'";
	}
	
	// 样式
	if( nowrap == 'true' ){
		str += " style='table-layout:fixed; display:none;border:1px solid RGB(207,207,254);border-collapse:collapse;'";
	}
	else{
		str += " style='word-break:break-all; display:none;border:1px solid RGB(207,207,254);border-collapse:collapse;'";
	}
	
	// 结束
	str += ">\n";
	document.write( str );
}


// 开始正文
function frame_beginRoundBlockBody( name, index, borderColor, nowrap, styleClass )
{
	// 新的表格
	var str = "<table  width='95%' height='100%' align='center' cellpadding='2'";
	
	// name 和 id
	if( index > 0 ){
		var t = "'main_" + name + index + "'";
	}
	else{
		var t = "'main_" + name + "'";
	}
	
	str += " name=" + t + " id=" + t;
	
	// 取线条的颜色
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
	
	// 样式
	if( nowrap == 'true' ){
		str += " style='table-layout:fixed; display:none'";
	}
	else{
		str += " style='word-break:break-all; display:none'";
	}
	
	// 结束
	str += ">\n";
	document.write( str );
}


// 开始正文
function frame_beginPrintBlockBody( name, index, border, borderColor, nowrap, styleClass )
{
	// 新的表格
	var str = "<table cellpadding='0' cellspacing='0' width='100%' height='100%'";
	
	// 样式
	if( nowrap == 'true' ){
		str += " style='table-layout:fixed; display:none'";
	}
	else{
		str += " style='word-break:break-all; display:none'";
	}
	
	// 样式
	if( styleClass == '' ){
		styleClass = 'print';
	}
	
	str += " class='" + styleClass + "'"
	
	// name 和 id
	if( index > 0 ){
		var t = "'main_" + name + index + "'";
	}
	else{
		var t = "'main_" + name + "'";
	}
	
	str += " name=" + t + " id=" + t;
	
	// 边框
	if( border == '' ){
		str += " border='1'";
	}
	else{
		str += " border='" + border + "'";
	}
	
	// 边框颜色
	if( borderColor != 'none' ){
		// 取线条的颜色
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
	
	// 结束
	str += ">\n";
	document.write( str );
}

// 初始化BLOCK
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
	// 取对象
	if( this.index > 0 ){
		var frameId = "main_" + this.frameName + this.index;
	}
	else{
		var frameId = "main_" + this.frameName;
	}
	
	// 不处理frame
	if( this.frameType == 'frame' ){
		frameId = '_frame_' + frameId;
	}
	
	// 格式化
	var tables = document.getElementsByName( frameId );
	for( var ti=0; ti<tables.length; ti++ ){
		var table = tables[ti];
		
		// 判断是否已经处理:在处理完内容后，会删除加载条，并且把状态改成[_processed]
		if( table.status == '_processed' ){
			continue;
		}
		
		// 行
		var rows = table.rows;
		
		// 取列的数量
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
		
		// 计算宽度
		if( this.frameType == 'compare' ){
			labelWidth = 50 * this.captionWidth;
			
			if( columns == 3 ){
				fieldWidth = 25 * (1 - this.captionWidth);
			}
			else{
				fieldWidth = 50 * (1 - this.captionWidth);
			}
			
			// 设置第一行的样式
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
			
			// 第一个
			tds[0].width = labelWidth + '%';
			
			// 内容行
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
			
			// 修改属性
			var lineFlag = table.cellSpacing;
			for( var ii=0; ii<rows.length; ii++ ){
				var flag = false;
				var tds = rows[ii].cells;
				for( var jj=0; jj<tds.length; jj++ ){
					var p = tds[jj].colSpan;
					var p2 = parseInt(p/2);
					var p1 = p - 2*p2;
					
					// 设置宽度
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
					
					// 设置样式
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
		
		// 显示内容
		table.style.display = '';
		table.status = '_processed';
		
		//隔行隔列换色
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
	
	// 初始化按钮的状态
	this.checkMenuItem();
}

//lable的点击事件
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



//checkbox样式的替换
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


//全选checkbox的实现
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
//		console.log("页面没有全选设置");
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

// 外部域的标题内容，生成标题、样式和ID
// fieldName : 字段名称
// visible : 是否看见
// flag : 域的状态 disabled/readonly/notnull/normal
// caption : 标题
function _prepareFieldCaption( fieldName, index, visible, flag, caption )
{
	if( caption == null ){
		return;
	}
	
	// 取标题的对象
	var labelName = "label:" + fieldName;
	var labels = document.getElementsByName( labelName );
	if( index >= labels.length ){
		return;
	}
	
	// 设置标题的信息
	var label = labels[index];
	
	// 是否可见
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
	
	// 标题
	if( isGridField(fieldName) ){
		label.innerHTML = caption;
	}
	else if( flag == 'notnull' ){
		var ptr = caption.lastIndexOf( '>' );
		if( ptr > 0 ){
			label.innerHTML = caption.substring(0,ptr+1) + "*" + caption.substring(ptr+1) + "：";
		}
		else{
			label.innerHTML = "*" + caption + "：";
		}
	}
	else{
		label.innerHTML = caption + "：";
	}
}

// 设置域的标签
function _setFieldCaption( fieldName, index, obj )
{
	// 缺省的序号
	if( index == null || isNaN(parseInt(index)) || index < 0 ){
		index = 0;
	}
	
	// 标题
	var caption = getFormFieldCaption( fieldName, index );
	if( caption == null || caption == '' ){
		return;
	}
	
	// 其它属性
	var field = getSelectInfoByFieldName( fieldName );
	if( field != null ){
		var disabled = field.getDisabled( index );
		var readonly = field.getReadonly( index );
		var notnull = field.getNotnull( index );
		var visible = field.getVisible( index );
	}
	else{
		// 取对象
		if( obj == null ){
			var objs = document.getElementsByName( fieldName );
			if( objs.length <= index ){
				return;
			}
			
			obj = objs[index];
		}
		
		// 状态
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
	
	// 设置标题
	_prepareFieldCaption( fieldName, index, visible, flag, caption )
}


// 设置输入域是否允许输入汉字
function _setFieldImeMode( obj )
{
	// 取类型
	var datatype = obj.datatype;
	
	// 数字域不能输入汉字
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


// 初始化格式
function _initFieldStyle( fieldName, index, obj )
{
	if( obj == null ){
		// 取对象
		var objs = document.getElementsByName( fieldName );
		if( objs.length <= index ){
			return;
		}
		
		obj = objs[index];
	}
	
	// 日期
	if( obj._type == 'datebox' ){
		// 判断是否已经初始化
		if( obj._inited ) return;
		
		// 设置初始化标志
		obj._inited = true;
		
		// 设置TABLE的样式
		var tableObj = findAncestorWithName( obj, "TABLE" );
		if( tableObj != null ){
			tableObj.border = 0;
			tableObj.cellSpacing = 0;
			tableObj.cellPadding = 0;
			tableObj = null;
		}
		
		// td的宽度
		obj.parentNode.width = '99%';
		
		// 数据类型
		obj.datatype='date';
		obj.style.width = '100%';
		
		var notnull = false;
		if( obj.notnull == 'true' ){
			notnull = true;
		}
		
		// 设置字段长度、和格式
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
		
		// 格式化数据
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
				v = yyyy + '年' + mm + '月' + dd + '日';
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
				v = yyyy + '年' + mm + '月' + dd + '日';
			}
			else{
				v = yyyy + '-' + mm + '-' + dd + ' 00:00:00';
			}
		}
		
		obj.value = v;
		
		// 取长度
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
		
		// 校验信息
		if( notnull ){
			obj.minvalue = len;
			obj.maxvalue = len;
		}
		else{
			obj.minvalue = 0;
			obj.maxvalue = 0;
		}
		
		// 图标和方法
		var imgs = document.getElementsByName( 'img:' + fieldName );
		if( imgs.length > index ){
			var img = imgs[index];
			
			// td的宽度
			img.parentNode.width = '1%';
			
			// 样式
			img.border = 0;
			img.vspace = 0;
			img.hspace = 0;
			img.style.cursor = 'hand';
			
			// 标题
			if( obj.fieldCaption != null && obj.fieldCaption != '' ){
				img.alt = '选择日期：' + obj.fieldCaption;
			}
			else{
				img.alt = '选择日期';
			}
			
			// 事件
			img.onclick = function(){ calendar(document.getElementsByName(fieldName)[index], fmt); };
			
			// 设置图标
			var display = obj.style.display;
			var imgsrc = _browse.contextPath + "/script/calendar/image_calendar.gif";
			setTimeout( function(){img.src = imgsrc; img.style.display = display; img = null;}, 1 );
		}
		
		// 事件
		var func = function(){ document.getElementsByName('img:'+fieldName)[index].fireEvent('onclick'); };
		obj.ondblclick = func;
		
		// 如果不能手工输入
		if( _dateCanInput == false ){
			obj.contentEditable = false;
			obj.style.cursor = "hand";
			obj.onclick = func;
		}
	}	// end databox
	
	// 设置ime-mode
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


// data标签的处理函数
var	_loadDataFieldList = new Array();

// 页面内容
var	_loadPageContent = '<html><base target="_self"><head>' +
	'<meta http-equiv="Content-Language" content="zh-cn">' +
	'<meta http-equiv="Content-Type" content="text/html; charset=GBK">' +
	'<title>请等待</title>' +
	'</head>' +
	'<body style="margin:0;padding:0;">' +
	'<table border="0" width="100%" height="100%" cellspacing="0" cellpadding="0" style="border-style: solid; border-width: 1px" bordercolor="#FFFF00">' +
	'<tr height="20%" bgcolor="#FFFF00"><td><font color="blue">&nbsp;请等待 ... ...</font></td></tr>' +
	'<tr height="80%" bgcolor="#99CCFF"><td align=center><span name="hint" id="hint" style="font-size: 12pt">正在下载数据，请等待 ... ...</span></td></tr>' +
	'</table>' +
	'<form style="margin:0;padding:0;" name="load" method="post">' +
	'<input type="hidden" name="load-data-name" id="load-data-name">' +
	'<input type="hidden" name="load-data-property" id="load-data-property">' +
	'<input type="hidden" name="load-data-fields" id="load-data-fields">' +
	'<input type="hidden" name="load-data-txnCode" id="load-data-txnCode">' +
	'</form></body></html>';

// 定义实时下载域的信息
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


// 查找下载域信息
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


// 开始下载数据
function beginLoadData( name, param )
{
	// 查找域信息
	var	field = _getLoadFieldByName( name );
	if( field == null ){
		return;
	}
	
	// 取输入参数
	var	parameter = "";
	if( field.parameter != "" ){
		if( field.parameter.indexOf('(') > 0 ){
			parameter = eval( field.parameter );
		}
		else{
			parameter = eval( field.parameter + "()" );
		}
	}
	
	// 框架
	var bLoadDataLayer = getObjectById("innerWindow");
    bLoadDataLayer.style.width = 350;
    bLoadDataLayer.style.height = 80;
    
    // 内容
    bLoadDataLayer.innerHTML = "<iframe name=innerWindowIframe scrolling=no frameborder=0 width='100%' height='80'></iframe>";
    var	bLoadDataFrame = window.frames("innerWindowIframe");
	
	// 显示并设置座标
	if(!bLoadDataLayer)return;
	var o = bLoadDataLayer.style;
	o.display = "";
 	
    var cw = bLoadDataLayer.clientWidth, ch = bLoadDataLayer.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft;
    var dh = document.body.clientHeight, dt = document.body.scrollTop;
    
    o.top = dt + (dh - ch)/2;
    o.left = dl + (dw - cw)/2;
    
    // 页面内容
    bLoadDataFrame.document.write( _loadPageContent );
    
    // 提示信息
    if( field.hint != '' ){
    	bLoadDataFrame.document.getElementById("hint").innerText = field.hint;
    }
    
    // 开始下载数据
	bLoadDataFrame.document.getElementById("load-data-name").value = field.name;
	bLoadDataFrame.document.getElementById("load-data-property").value = field.property;
	bLoadDataFrame.document.getElementById("load-data-fields").value = field.fields;
	bLoadDataFrame.document.getElementById("load-data-txnCode").value = field.txnCode;
	
	// 准备参数
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


// 下载完成
function _onLoadDataComplete( name, errCode, errDesc, result )
{
	// 隐藏层
	var bLoadDataLayer = document.all.innerWindow;
	
	var o = bLoadDataLayer.style;
	if(!o)return;
	o.display = "none";
	
	// 恢复按钮
	clickFlag = 0;
	checkAllMenuItem();
	
	// 查找域信息
	var	field = _getLoadFieldByName( name );
	if( field == null ){
		return;
	}
	
	// 变量名称
	name = name.replace(/[-]/g, '_' );
	
	// 调用后处理
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





// TABS操作函数
// tab定义列表
var	tabFrames = new Array();

// 需要导航的下一个页面
var nextTabUrl = null;

// 当前正在操作的tabs名称
var	currentTabsName = null;

// 设置下一个导航页面地址
function setTabUrl( xurl )
{
	nextTabUrl = xurl;
}


// tab的六种样式
// Active/ActiveR 当前活动的tab/线条样式
// Selected/SelectedR 已经选中的tab/线条样式
// Default/DefaultR 正常的tab/线条样式

// tabs定义信息
// tabName tab名称
// maxTabs 最大的子tab数量
// style tab的样式
// frameClass iframe的样式
// scrolling iframe是否有滚动条
// widthTabs tab的最大宽度
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
	
	// 处理函数
	this.prepareIframe = _tabs_prepareIframe;
	this.appendTab = _tabs_appendTab;
	
	// 按钮
	this.menuClick = _tabs_menuClick;
	this.checkMenuItem = _tabs_checkMenuItem;
	
	// 加载页面
	this.loadPage = _tabs_loadPage;
	
}


// 增加grid定义信息
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

// 根据名称获取grid定义信息
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

// 增加子标签
function _tabs_appendTab( item )
{
	// 子tab的个数
	var	num = this.subTab.length;
	
	// 判断是否重复：caption、tabValue、xurl、func
	for( var ii=0; ii<num; ii++ ){
		var t = this.subTab[ii];
		if( t.caption == item.caption &&
			t.tabValue == item.tabValue &&
			t.xurl == item.xurl &&
			t.func == item.func )
		{
			// 完全相同
			return;
		}
	}
	
	this.subTab[num] = item
	item.appendTab( num );
}


// 执行按钮处理
function _tabs_menuClick( menu )
{
	// 禁止按钮
	clickFlag = 1;
	checkAllMenuItem( );
	clickFlag = 0;
	
	// 执行操作
	menu.fireEvent(null, this.tabName, 0);
	
	// 恢复按钮
	checkAllMenuItem( );
	
	// 禁止继续执行，避免image提交
	return false;
}


// 检查按钮的有效性
function _tabs_checkMenuItem( )
{
	// 按钮信息
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
  	
	// 恢复/禁止 所有按钮
	for( var i=0; i<menus.length; i++ ){
		menus[i].setStatus( status );
	}
}


// 子tab的信息
function subTabDefine( tabName, caption, xurl, func, tabValue, scrolling, styleClass, icon, hint )
{
	// 缺省数据
	if( styleClass == null || styleClass == 'N/A' ){
		styleClass = tabName;
	}
	
	if( icon == null || icon == 'N/A' ){
		icon = "/script/tabs/tab-icon.gif";
	}
	
	if( caption == null || caption == '' ){
		caption = tabValue;
		if( caption == null || caption == '' ){
			caption = 'TAB标签';
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
	
	// 提示信息
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
	
	// 生成HTML
	this.getHtml = _tab_getHtml;
	this.appendTab = _tab_appendTab;
}

// 增加子tab信息
function addSubTab( tabName, caption, xurl, func, tabValue, scrolling, styleClass, icon, hint )
{
	// 生成子tab
	var item = new subTabDefine( tabName, caption, xurl, func, tabValue, scrolling, styleClass, icon, hint );
	
	// 增加tab
	var	tab = getTabDefine( tabName );
	tab.appendTab( item );
}

// 生成TAB的HTML信息
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
	
	// 图标
	if( this.icon != '' ){
		str += "<img";
		str += " src='" + _browse.contextPath + this.icon + "'";
		str += " alt='" + this.hint + "'>";
	}
	
	// 标题
	str += "<span title='" + this.hint + "'>" + this.caption + '</span></td>\n';
	
	// 分割符
	str += "<td nowrap";
	str += " id='" + this.tabName + "_td_" + index + "R'";
	str += " class='" + this.styleClass + "_Default" + "R'";
	str += " width='6px' unselectable='on'>　</td>\n";
	
	str += "</tr>\n";
	str += "</table>";
	
	return str;
}


// 选中一个tab
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

// 正常tab的样式
function defaultTab( tabName, idx ){
	var	tab = getTabDefine( tabName );
	if( tab.selectedTab != idx ){
		var btn1 = tabName + '_td_' + idx;
		var btn2 = btn1 + 'R';
		document.getElementById(btn1).className = tab.style + '_Default';
		document.getElementById(btn2).className = tab.style + '_DefaultR';
	}
}

// 选中tab的样式
function selectedTab( tabName, idx ){
	var	tab = getTabDefine( tabName );
	var btn1 = tabName + '_td_' + idx;
	var btn2 = btn1 + 'R';
	document.getElementById(btn1).className = tab.style + '_Selected';
	document.getElementById(btn2).className = tab.style + '_SelectedR';
}

// 取当前正在处理的tab的点击次数
function isClicked()
{
	if( currentTabsName == null ){
		return 0;
	}
	
	// 查找tab的定义，如果没有改变，不处理
	var	tab = getTabDefine( currentTabsName );
	if( tab == null || tab.selectedTab < 0 ){
		return 0;
	}
	
	// 取xurl和func
	var	subTab = tab.subTab[tab.selectedTab];
	if( subTab == null ){
		return 0;
	}
	
	// 返回点击的次数
	return subTab.clickedNumber;
}


// 选择一个tab时
function clickTab( tabName, idx )
{
	// 设置当前正在处理的tabs
	currentTabsName = tabName;
	
	// 查找tab的定义
	var	tab = getTabDefine( tabName );
	
	// 判断idx是否有效
	if( idx == null || idx < 0 || idx >= tab.subTab.length ) idx = 0;
	
	// 如果没有改变，不处理
	if( tab.selectedTab == idx || tab.subTab.length == 0 ) return;
	
	// 设置当前选中的tabIndex
	var	oldIdx = tab.selectedTab;
	tab.selectedTab = idx;
	
	// 恢复原来已经选中的tab的样式
	if( oldIdx >= 0 ){
		defaultTab( tabName, oldIdx );
	}
	
	// 设置已经选中的tab样式
	selectedTab( tabName, idx );
	
	// 取xurl和func
	var	subTab = tab.subTab[idx];
	var func = subTab.func;
	
	// 标签被点击的次数
	subTab.clickedNumber = subTab.clickedNumber + 1;
	
	// 标题:在用户的回调函数中可能使用
	var	caption = subTab.tabValue;
	
	// onclick函数
	if( func != null && func != '' ){
  		eval( func.replace(/`/g, "'") );
  	}
	
	// 执行动作
	tab.loadPage( idx );
}

//调整tab太多的情况
function adjust_tab(){
	var tabItem = document.getElementById("tabsAction");
	if(tabItem){
		var tabCon = document.getElementById("tab_con");
		tabItem.style.marginLeft = '10px';
		var next = document.getElementById("tab_next");
		var count = 1;
		var marginLeftWidth = count*400;
	//	next.innerHTML = "更多";
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

// 加载页面
function _tabs_loadPage( idx )
{
	var	subTab = this.subTab[idx];
	
	// 生成iframe
	this.prepareIframe( idx );
	
	// 设置
	var frameObject = document.getElementById( this.tabName + '_frameX' );
	
	// 设置跳转页面:nextTabUrl由用户的回调函数设置
	if( nextTabUrl == null || nextTabUrl == '' ){
		nextTabUrl = subTab.xurl.replace(/`/g, "'");
	}
	
	// 跳转
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
	
	// 设置滚动条
	if( subTab.scrolling != null && subTab.scrolling != '' ){
		if( subTab.scrolling == 'no' || subTab.scrolling == 'false' ){
			frameObject.document.body.style.overflow = 'hidden';
		}
	}
}

// 生成IFRAME
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
	
	// 设置内容
	var td = document.getElementById( 'td_' + this.tabName );
	td.innerHTML = str;
}


// LINK的处理函数
function _processLink( index )
{
	var obj = window.event.srcElement;
	while( obj.type != 'link' ){
		obj = obj.parentNode;
	}
	
	// 取onclick
	var strClick = '';
	var clickFunc = obj._onclick;
	if( clickFunc != null && clickFunc != '' ){
		var ptr = clickFunc.indexOf( "(" );
		if( ptr > 0 ){
			// 前面部分
			strClick += clickFunc.substring(0, ptr+1);
			
			// 后面部分
			clickFunc = clickFunc.substring( ptr+1 );
			
			// 括号部分
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

// 平台的EDIT
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
			
			// 计算空格
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


// 生成richedit
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


// 设置title
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


// 页面的标题
function _preparePageTitle( caption, icon, styleClass, bgcolor, forecolor )
{
	//console.log("caption = " + caption);
	caption = caption.replace(/\s>\s/g, "<span class='ptan'></span>");
	if( openWindowType == 'subframe' ){
		//parent.setPageTitle( "当前位置： " + caption );
		var str_caption = "<table style='' cellspacing='0' cellpadding='0'><tr><td width=15 style='background:url(/script/menu-images/pos.png) left 75% no-repeat;'></td><td><span style='font-weight:bold;'>所在位置</span> " + caption + "</td></tr></table>";
		parent.setPageTitle(str_caption );
	}
	else if( document.getElementById('freeze-title') == null ){
		// 样式
		if( styleClass == null || styleClass == '' ){
			styleClass = 'hide';
		}
		
		// 图标
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
		
		// 生成表格
		var str = "<table width='95%'  align='center' cellpadding='0' cellspacing='0' style='table-layout:fixed;margin-top:3px; margin-bottom:3px;'>\n";
		str += "<tr class='" + styleClass + "'";
	   
		// 背景色
		if( bgcolor != null && bgcolor != '' ){
			str += " bgcolor='" + bgcolor + "'";
		}
		
		str += ">\n";
		   
		// 图标
	 //	str += "<td width='36' align='right' valign='middle' style='border-bottom: 1px; border-left: 1px; border-top: 1px; border-right: 1px;'>";
	//	str += "<img src='" + icon + "' width='7' height='7' hspace='8'  border='0' onclick='_keyboard.fireEvent(" + '"CTRL F12"' + ")'>";
	//	str += "</td>\n";
		
		// 标题
		str += "<td height='25' align='left' valign='middle' nowrap id='freeze-title'";
		
		// 字体的颜色
		if( forecolor != null && forecolor != '' ){
			str += " style='line-height:100%;color:" + forecolor + ";'";
		}
		
		str += ">\n";
		
		//alert(caption);
		// 标题
	//	caption=caption.replace(/(^\s*)|(\s*$)/g,"");
		str = str + "<table cellspacing='0' cellpadding='0'><tr><td><td width=15 style='background:url(/script/menu-images/pos.png) left 75% no-repeat;'></td><td><span style='font-weight:bold;'>所在位置</span> " + caption + "</td></tr></table>";
		//str += caption;
		//alert(caption);
		//str += "导航：" + caption;
	
		// 结束
		str += "</td></tr></table>";
		document.write( str );
	}

	_showProcessHintWindow( '正在加载页面 ... ' );
}



// 滚动条页面加载信息
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
		el.childNodes[0].innerText = '加载页面成功：' + v + "%"; 
	}
	else {
		el.childNodes[1].style.width = v + "%"; 
		el.childNodes[0].innerText = '正在加载页面：' + v + "%"; 
		
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

// 加载页面时显示的进度条
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


// 显示提示信息
document.write("<div id=hintWindowLayer style='position: absolute; z-index: 9999; width: 150; height: 70; display: none'></div>");
document.write("<IFRAME id='hintWindowIFrame' name='hintWindowIFrame' frameborder='0' style='position: absolute; z-index: 9998; width: 150; height: 70; display: none'></IFRAME>");
document.write("<IFRAME id='optionContainerFrame' name='optionContainerFrame' frameborder='0' style='position: absolute; z-index: 9998; display: none'></IFRAME>");


function _showProcessHintWindow( hint )
{
	// 提示信息
	if( hint == null || hint == '' ){
		hint = "正在执行请求，请等待处理结果";
	}
	var dt = new Date();

	hint = '<div style="border:1px solid #A7DAED;background:#fff;">'+
			'<div style="width:100%;text-align:center;padding:2px 0px;filter:alpha(opacity=70);"><img src="/script/menu-images/onload.gif" width="80" height="80"/></div>'+
			'<div style="height:16px;width:100%;text-align:center;color:#336699;">'+ hint + '</div>'+
			'<div style="width:100%;text-align:center;color:#336699;"><span id="_HintTableCaptionMinute">00</span>:<span id="_HintTableCaptionSecond">00</span></div></div>';
	// 框架
	var bHintLayer = document.all.hintWindowLayer;
	bHintLayer.innerHTML = hint;
	
	// 显示并设置座标
	
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
    // 如果没有隐藏就又显示，可以直接忽略
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
	    		// document.getElementById("_HintTableCaption").innerHTML = "执行过程较慢，您可以继续等待也可以返回或者退出";
	    		alert("执行过程较慢，您可以继续等待也可以返回或者退出");
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


// 取带滚动条的DIV
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
