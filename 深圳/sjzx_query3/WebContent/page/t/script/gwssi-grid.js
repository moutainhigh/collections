// 2006/07/12:对于支持多选时的grid，只有按下shift键，才能够多选
// grid 函数
// grid行样式
// oddstyle 奇数行样式 
// evenstyle 偶数行样式 
// activestyle 活动行样式 
// selectedstyle 选中行样式 
function gridStyle( oddstyle, evenstyle, activestyle, selectedstyle )
{
	this.oddstyle = oddstyle;
	this.evenstyle = evenstyle;
	this.activestyle = activestyle;
	this.selectedstyle = activestyle;
}


// grid定义信息
// gridName grid名称
// keyList 关键字列表
// checkflag 是否有checkbox
// multiselect 是否允许多项
// rowselect 是否支持行选择，boolean
// onselect 选中行时的动作，(checkflag=true)有效
// onshow 处理每一行的样式,原型onshow( grid, rowid, flag ),可以处理每一个域的样式，grid=grid的定义、rowid=行号，从1开始,flag=1:选中、2=活动、0=正常
// style grid行格式 -- gridStyle结构，包括四种样式
// menus grid菜单列表
function gridDefine( gridName, keyList, checkflag, 
		multiselect, rowselect, onselect, onshow, style, menus )
{
	this.gridName = gridName;
	this.index = 0;
	this.keyList = keyList;
	this.checkflag = checkflag;
	this.multiselect = multiselect;
	this.rowselect = rowselect;
	this.style = style;
	this.menus = menus;
	
	// 是否支持CTRL键
	this.supportCtrlKey = _gridSupportCtrlKey;
	
	// 允许排序的字段列表
	this.sortItems = new Array();	// 可以排序的字段列表
	this.prepareSortItem = grid_prepareSortItem;	// 生成字段的排序按钮
	this.prepareCellButton = grid_prepareCellButton;	// 生成单个字段的按钮
	
	// 选中行的动作
	if( onselect == null || onselect == '' ){
		this.onselect = null;
	}
	else{
		var ptr = onselect.indexOf( '(' );
		if( ptr < 0 ){
			onselect = onselect + '()';
		}
		
		this.onselect = onselect.replace(/`/g, "'");
	}
	
	// 格式化函数[onshow( grid, rowid, flag )]
	if( onshow == null || onshow == '' ){
		onshow = null;
	}
	else{
		var ptr = onshow.indexOf( '(' );
		if( ptr < 0 ){
			onshow = onshow + '(grid, rowid, flag)';
		}
		else{
			var p = onshow.substring( ptr+1 );
			p = trimSpace( p );
			if( p.substring(0,1) == ')' ){
				onshow = onshow.substring(0, ptr) + '(grid, rowid, flag)';
			}
		}
	}
	
	this.onshow = onshow;
	
	// 记录数量
	this.totalRecord = 0;
	
	// cell域选中的内容，在调用[getFieldValues]时设置这个值，在[_modifySelectedRecord]时清空这个值，格式：[cellName, valueList]
	this.fieldValues = new Array();
	
	// 函数
	this.menuClick = grid_menuClick;
	this.getMenuByName = grid_getMenuByName;
	this.setMenuVisible = grid_setMenuVisible;
	this.getMenuVisible = grid_getMenuVisible;
	this.setMenuCaption = grid_setMenuCaption;
	this.getMenuCaption = grid_getMenuCaption;
	this.checkMenuItem = grid_checkMenuItem;
	
	this.getCell = grid_getCell;
	this.getCellValue = grid_getCellValue;
	this.setCellValue = grid_setCellValue;
	this.getFieldValues = grid_getFieldValues;
	this.setFieldValues = grid_setFieldValues;
	this.getAllFieldValues = grid_getAllFieldValues;
	this.getSelectedRowNumber = grid_getSelectedRowNumber;
	
	// 初始化函数:生成页面时调用这个函数设置TABLE的样式
	this.initGridStyle = grid_initGridStyle;
	this.prepareTableRect = grid_prepareTableRect;
	this.prepareTableHeight = grid_prepareTableHeight;
	this.setRowid = grid_setRowid;		// 替换@rowid的值
	
	// 取边框颜色
	this.getBorderColor = grid_getBorderColor;
	
	// 取FORM的名称
	this.formName = null;
	this.getFormName = grid_getFormName;
	
	// 取table等对象
	this.table = null;
	this.getTable = grid_getTable;
	this.isChildNode = grid_isChildNode;
	
	this.getFrameDiv = grid_getFrameDiv;
	this.getSpaceDiv = grid_getSpaceDiv;
	this.getSelectedKeyBox = grid_getSelectedKeyBox;
	this.getFlagBoxs = grid_getFlagBoxs;
	
	// 取grid的第一行的编号，在重名grid中，取所有值或选中值时使用
	this.getFirstRowIndex = grid_getFirstRowIndex;
}

// grid定义列表
var	grids = new Array();

// 当前正在操作的grid
var	currentGrid = null;

// 增加grid定义信息
function addGridDefine( grid )
{
	var	index = 0;
	var	num = grids.length;
	for( var i=0; i < num; i++ ){
		if( grids[i].gridName == grid.gridName ){
			index = index + 1;
		}
	}
	
	grid.index = index;
	grids.push( grid );
}

// 根据名称获取grid定义信息
function getGridDefine( gridName, index )
{
	if( gridName == null || gridName == "" ){
		return currentGrid;
	}
	
	if( index == null ){
		index = 0;
	}
	
	for( var i=0; i<grids.length; i++ ){
		if( grids[i].gridName == gridName && grids[i].index == index ){
			return grids[i];
		}
	}
	
	return null;
}

// 判断是否为GRID的域
function isGridField( fieldName )
{
	var ptr = fieldName.indexOf( ':' );
	if( ptr > 0 ){
		var gn = fieldName.substring( 0, ptr );
		for( var i=0; i<grids.length; i++ ){
			if( grids[i].gridName == gn ){
				return true;
			}
		}
	}
	
	return false;
}

// 获取按钮或域的grid定义信息
function getGridDefineByElement( button )
{
	// td.tr.tbody.table.td.tr.tbody
	var table = button.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
	for( ii=0; ii<grids.length; ii++ ){
		// div.td.tr.tbody
		var t = grids[ii].getTable().parentNode.parentNode.parentNode.parentNode;
		if( t == table ){
			return grids[ii];
		}
	}
	
	return null;
}



// 执行按钮处理
function grid_menuClick( menu )
{
	// 当前处理的grid
	currentGrid = this;
	
	// 取已经选中的记录
	var selRows = new Array();
	var flagBoxs = this.getFlagBoxs();
    for( var i=0; i<flagBoxs.length; i++ ){
      if( flagBoxs[i].checked == true ){
        selRows.push( flagBoxs[i].value );
      }
    }
	
	// 设置标志禁止按钮
	clickFlag = 1;
	checkAllMenuItem( );
	
	// 设置需要加载的明细数据
	setLoadNode( this.gridName );
	
	// 调用函数
	menu.fireEvent( selRows, this.gridName, this.index );
  	
	// 如果错误需要恢复
	if( clickFlag == 0 ){
		setLoadNode('');
	}
	
	checkAllMenuItem( );
	
	// 禁止继续执行，避免image提交
	return false;
}


// 根据名称取菜单
function grid_getMenuByName( menuName )
{
	if( menuName == null || menuName == '' ){
		return null;
	}
	
  	var	menus = this.menus;
  	if( menus == null ){
		return null;
	}
	
	for( var i=0; i<menus.length; i++ ){
		if( menus[i].name == menuName ){
			var buttonName = this.gridName + "_" + menuName;
			var ms = document.getElementsByName( buttonName );
			if( ms.length <= this.index ){
				return null;
			}
			else{
				return ms[this.index];
			}
		}
	}
	
	return null;
}

// 设置菜单的visible
function grid_setMenuVisible( menuName, visible )
{
	var menu = this.getMenuByName( menuName );
	if( menu == null ){
		return;
	}
	
	// 设置是否可见
	if( visible ){
		menu.style.display = "";
	}
	else{
		menu.style.display = "none";
	}
}

function grid_getMenuVisible( menuName )
{
	var menu = this.getMenuByName( menuName );
	if( menu == null ){
		return;
	}
	
	if( menu.style.display == "none" ){
		return false;
	}
	else{
		return true;
	}
}

// 设置菜单的标题
function grid_setMenuCaption( menuName, caption )
{
	var menu = this.getMenuByName( menuName );
	if( menu == null ) return;
	
	menu.style.width = '';
	menu.value = caption;
	if( menu.clientWidth < 64 ) menu.style.width = '64px';
}

function grid_getMenuCaption( menuName )
{
	var menu = this.getMenuByName( menuName );
	if( menu == null ) return;
	
	return menu.value;
}


// 检查按钮的有效性
function grid_checkMenuItem( )
{
	var	i;
	var	buttonName;
	
	var	menu = this.menus;
	if( menu == null ) return;
	
	// 禁止所有按钮
	if( clickFlag == 1 ){
		for( i=0; i<menu.length; i++ ){
			menu[i].setStatus( true );
		}
		
    	return;
  	}
  	
  	// 取选中的记录数量
  	var selRowNumber = this.getSelectedRowNumber();
	
	// 检查按钮
	for( i=0; i<menu.length; i++ ){
		if( menu[i].validRule != "1" && menu[i].validRule != "2" ){
			menu[i].setStatus( false );
		}
		else if( menu[i].validRule == "1" ){
			if( selRowNumber == 1 ){
				menu[i].setStatus( false );
			}
			else{
				menu[i].setStatus( true );
			}
		}
		else{
			if( selRowNumber >= 1 ){
				menu[i].setStatus( false );
			}
			else{
				menu[i].setStatus( true );
			}
		}
	}
}


// 取单选方式下已经被选中的记录编号
function getSelectedRowid( gridName, index )
{
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return -1;
	}
	
	// 取checkbox
	var flagBoxs = grid.getFlagBoxs();
	if( flagBoxs.length == 0 ){
		return -1;
	}
	
	// 先判断选中行的样式
	var style = grid.style.selectedstyle;
	if( style != null ){
		var rows = grid.getTable().rows;
		for( var i=0; i<flagBoxs.length; i++ ){
			if( rows[i+1].className == style ){
				return i;
			}
		}
		
		return -1;
	}
	
	// 取选中的标志
	for( var i=0; i<flagBoxs.length; i++ ){
		if( flagBoxs[i].checked == true ){
			return i;
		}
	}
	
	return -1;
}

// 取选中的记录数量
function grid_getSelectedRowNumber( )
{
	var num = 0;
	
	// 取checkbox
	var flagBoxs = this.getFlagBoxs();
	if( flagBoxs.length == 0 ){
		return 0;
	}
	
	var	i;
	for( i=0; i<flagBoxs.length; i++ ){
		if( flagBoxs[i].checked == true ){
			num = num + 1;
		}
	}
	
	return num;
}

// 所有的记录数量
function getAllRowNumber( gridName, index )
{
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return 0;
	}
	
	// 取checkbox
	var flagBoxs = grid.getFlagBoxs();
	if( flagBoxs.length == 0 ){
		return 0;
	}
  	
  	return flagBoxs.length;
}



// 设置grid的行样式
// index table的序号
// rowid 从0开始
// flag=1:选中、2=活动、0=正常
function modifyRowStyle( gridName, index, rowid, flag )
{
	if( rowid < 0 ){
		return;
	}
	
	// 取定义信息
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return;
	}
	
	// 行信息
	rowid = rowid + 1;
	var	row = grid.getTable().rows[rowid];
	if( row == null ){
		return;
	}
	
	// 如果定义了取显示样式的函数，获取样式
	if( grid.onshow != null ){
		var	style = eval( grid.onshow.replace(/`/g, "'") );
		if( style != null && style != '' ){
			if( row.className != style ){
				row.className = style;
			}
			
			return;
		}
	}
	
	// 设置样式
	var	style = grid.style;
	if( style.selectedstyle == "" && flag == 1 ){
		flag = 0;
	}
	else if( style.activestyle == "" && flag == 2 ){
		flag = 0;
	}
	
	if( flag == 1 ){
		if( row.className != style.selectedstyle ){
			row.className = style.selectedstyle;
		}
	}
	else if( flag == 2 ){
		if( row.className != style.activestyle ){
			row.className = style.activestyle;
		}
	}
	else{
		if( rowid % 2 == 1 ){
			if( row.className != style.evenstyle ){
				row.className = style.evenstyle;
			}
		}
		else {
			if( row.className != style.oddstyle ){
				row.className = style.oddstyle;
			}
		}
	}
}


// 设置当前行的样式:flag=1进入行、0=离开
function modifyCurrentRowStyle( gridName, index, rowId, flag )
{
	if( rowId < 0 ){
		return;
	}
	
	// 取定义信息
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return;
	}
	
	// 行信息
	var	row = grid.getTable().rows[rowId+1];
	if( row == null ){
		return;
	}
	
	// 判断是否需要设置样式
	var	style = grid.style;
	if( grid.checkflag == "" || style.selectedstyle == "" ){
		if( style.activestyle != "" && flag == 1 ){
			if( row.className != style.activestyle ){
				row.className = style.activestyle;
			}
		}
		else{
			if( rowId % 2 == 0 ){
				if( row.className != style.evenstyle ){
	      			row.className = style.evenstyle;
	      		}
	    	}
	    	else {
	    		if( row.className != style.oddstyle ){
	      			row.className = style.oddstyle;
	      		}
	    	}
		}
		
		return;
	}
	
	var sflag = false;
	
	// 没有checkbox
	if( grid.checkflag != "" ){
		var flagBoxs = grid.getFlagBoxs();
		if( flagBoxs[rowId] != null && flagBoxs[rowId].checked == true ){
			sflag = true;
		}
	}
  	
	if( sflag == true ){
		modifyRowStyle(gridName, index, rowId, 1);
	}
	else if( flag == 1 ){
		modifyRowStyle(gridName, index, rowId, 2);
	}
	else{
		modifyRowStyle(gridName, index, rowId, 0);
	}
}

// 设置行样式
function grid_initGridStyle( )
{
	var	ii, jj;
	
	// 取框架
	var	table = this.getTable();
	if( table == null ) return;
	table.width = '100%';
	
	// 判断是否打印
	if( table.className == 'print' ) return;
	
	currentGrid = this;
	
	// 取选中记录的键字
	var flagBoxs = this.getFlagBoxs();
	if( this.checkflag != "" ){
		var keys = this.getSelectedKeyBox().value
		if( keys != '' ){
			var keyList = keys.split( ';' );
			for( ii=0; ii<flagBoxs.length; ii++ ){
				for( jj=0; jj<keyList.length; jj++ ){
					if( flagBoxs[ii].value == keyList[jj] ){
						flagBoxs[ii].checked = true;
						break;
					}
				}
			}
		}
	}
	
	// 设置表格边框
	var border = table.border;
	if( isNaN(parseInt(border))==true ){
		table.cellSpacing = 0;
		table.cellpadding = 0;
	}
	else{
		border = parseInt( border );
		if( border < 0 ){
			border = 0;
		}
		
		table.cellSpacing = 0;
		table.cellpadding = 0;
	}
	table.style.tableLayout = "fixed";	//固定宽度
	table.style.border = "1px solid RGB(207, 207, 254)";
	table.cellSpacing = 0;
	table.cellpadding = 0;
	// 是否折行
	/*if( table.noWrap == 'true' ){
		table.style.tableLayout = 'fixed';
	}
	else{
		table.style.wordBreak = 'break-all';
	}*/
	
	// 边框的颜色
	var borderColor = this.getBorderColor();
	
	// 底线
	if( border > 0 ){
		var frameDiv = this.getFrameDiv();
		if( frameDiv != null ){
			if( frameDiv.style.height != null && frameDiv.style.height != '' ){
				frameDiv.style.borderBottom = border + 'px solid ' + borderColor;
			}
		}
		
		// 边线
		var spDiv = this.getSpaceDiv();
		if( spDiv != null ){
			spDiv.style.display = 'none';
			spDiv.style.width = '100%';
			spDiv.style.fontSize = 1;
			spDiv.style.borderLeft = border + 'px solid ' + borderColor;
			spDiv.style.borderRight = border + 'px solid ' + borderColor;
		}
	}
	
	// 取行数量
	var rows = table.rows;
	var pageRow = rows.length - 1;
	
	//取列数量
	var cols = table.rows[0].cells.length;
	//隔行隔列换色
	//alert(table.getAttribute("id"));
	for(var rowii=1;rowii<=pageRow;rowii++){
		for(var coljj=1;coljj<cols;coljj++){
			var st = table.rows[rowii].cells[coljj].getElementsByTagName("span");
			if(st && st.length>0){
				table.rows[rowii].cells[coljj].title = st[0].innerHTML;
			}
		}
		
		/*for(var coljj=0;coljj<cols;coljj++){
			if(rowii % 2 == 0){
				if(this.rowselect){
					table.rows[rowii].cells[coljj].className="odd1";
					table.rows[rowii].cells[coljj].style.cursor="hand";
				}else{
					table.rows[rowii].cells[coljj].className="odd1";
				}
			}else{
				if(this.rowselect){
					table.rows[rowii].cells[coljj].className="odd2";
					table.rows[rowii].cells[coljj].style.cursor="hand";
				}else{
					table.rows[rowii].cells[coljj].className="odd2";
				}
			}
		}*/
		
		if(this.checkflag == "true" || this.checkflag==true){
			var rowCheck = document.getElementById("check_"+(rowii-1));
			if(rowCheck && rowCheck.checked){
				if(rowCheck.type=="radio"){
					var tmp1 = document.getElementById("label_"+(rowii-1));
					if(tmp1){
						tmp1.className = 'radioNew back_down';
					}
				}else if(rowCheck.type=="checkbox"){
					var tmp1 = document.getElementById("label_"+(rowii-1));
					if(tmp1){
						tmp1.className = 'checkboxNew back_down';
					}
				}
			}
		}
	}
	
	// 鼠标移动的动作:onmouseover,onmouseout
	var mouseOver = table._onmouseover;
	var mouseOut = table._onmouseout;
	var _gridName = this.gridName;
	var _gridIndex = this.index;
	
	var func = "modifyCurrentRowStyle('" + _gridName + "', " + _gridIndex + ", ";
	if( this.style.activestyle != null && this.style.activestyle != '' ){
		for( ii=0; ii<pageRow; ii++ ){
			if( mouseOver != null ){
				rows[ii+1].onmouseover = new Function(mouseOver + ';' + func + ii + ", 1)");
			}
			else{
				rows[ii+1].onmouseover = new Function(func + ii + ", 1)");
			}
			
			if( mouseOut != null ){
				rows[ii+1].onmouseout = new Function(mouseOut + ';' + func + ii + ", 0)");
			}
			else{
				rows[ii+1].onmouseout = new Function(func + ii + ", 0)");
			}
		}
	}
	
	// 设置选中动作:onclick
	var click = table._onclick;
	func = "selectRow('" + this.gridName + "', " + this.index + ", ";
	for( ii=0; ii<flagBoxs.length; ii++ ){
		// 取行的编号
		var row = flagBoxs[ii].parentNode.parentNode;
		rid = row.id.substring(4);
		// 行动作
		if( this.rowselect ){
			if( click != null ){
				row.onclick = new Function(func + rid + ", true);var rowIndex=" + rid + ";" + click);
			}
			else{
				row.onclick = new Function(func + rid + ", true)");
			}
		}else if( click != null ){
			row.onclick = new Function( "var rowIndex=" + rid + ";" + click );
		}
		
		// 选择按钮的动作
		if( flagBoxs[ii].style.display != 'none' ){
			flagBoxs[ii].onclick = new Function(func + rid + ", " + this.rowselect + ")");
		}
	}
	
	// 设置样式
	for( ii=0; ii<pageRow; ii++ ){
		// 设置当前行的样式:flag=1进入行、0=离开
		modifyCurrentRowStyle( this.gridName, this.index, ii, 0 );
	}
	
	// 行选择时的动作
	if( this.onselect != null && this.onselect != '' ){
		var flagBoxs = this.getFlagBoxs();
		for( ii=0; ii<flagBoxs.length; ii++ ){
			var	flag = flagBoxs[ii].checked;
			if( flag == true ){
				eval( this.onselect );
			}
		}
	}
	
	// 全选的操作
	var selectAllBoxs = document.getElementsByName( this.gridName + ':_select-all' );
	if( selectAllBoxs.length > this.index ){
		selectAllBoxs[_gridIndex].onclick = function(){ selectAllRows(_gridName, _gridIndex); };
	}
	
	// 计算宽度和高度
	this.prepareTableRect()
	
	// 设置可以排序的字段
	this.prepareSortItem();
	
	// 初始化按钮的状态
	this.checkMenuItem();
	
	// 显示表格
	var f = this.getFrameDiv();
	if( f != null ){
		f.style.display = '';
	}
}

// 重新计算grid的宽度和高度
function grid_prepareTableRect()
{
	// 取框架
	var	table = this.getTable();
	
	// 宽度
	var startCell = 0;
	if( this.checkflag == 'true' ){
		startCell = 1;
	}
	
	// 所有列的宽度和总的宽度
	var cellWidth = new Array();
	var totalWidth = 0;
	
	var frow = table.rows[0];
	for( var ii=startCell; ii<frow.cells.length; ii++ ){
		var w = frow.cells[ii].width;
		if( w.substring(w.length-1) == '%' ){
			w = w.substring(0, w.length-1);
			cellWidth[ii] = parseInt(w);
			totalWidth = totalWidth + parseInt(w);
		}
		else{
			totalWidth = 0;
			break;
		}
	}
	
	// 计算每列的宽度
	var remains = 0;	// 多余的部分，如果[remains>1]，调整当前的宽度
	if( totalWidth > 0 ){
		var lastWidth = 100;
		if( totalWidth < 99 || totalWidth > 101 ){
			table.width = (totalWidth < 110) ? '100%' : totalWidth + '%';
			
			// 设置列的宽度
			var f = 100 / totalWidth;
			for( var ii=startCell; ii<frow.cells.length-1; ii++ ){
				var d = cellWidth[ii] * f;	// 宽度的比例（浮点数）
				var w = parseInt(d);
				
				// 计算多余的部分
				remains += (d - w);
				if( remains >= 1 ){
					w ++;
					remains --;
				}
				
				lastWidth -= w;
				frow.cells[ii].width = w + '%';
			}
			
			frow.cells[frow.cells.length-1].width = lastWidth + '%';
		}
	}
	
	// 调整高度
	this.prepareTableHeight( totalWidth );
}

// 调整高度
function grid_prepareTableHeight( totalWidth )
{
	if( totalWidth == null || totalWidth <= 0 ){
		return;
	}
	
	// 取框架
	var	table = this.getTable();
	
	// 判断是否已经初始化，如果还没有初始化，等待
	if( table.clientHeight == 0 ){
		var g = this;
		setTimeout( function(){g.prepareTableHeight(totalWidth)}, 10 );
		return;
	}
	
	// 计算宽度和高度
	var frameDiv = this.getFrameDiv();
	var spaceDiv = this.getSpaceDiv();
	if( spaceDiv != null ){
		spaceDiv.style.width = totalWidth + '%';
		
		if( frameDiv.clientHeight > table.clientHeight ){
			var h = frameDiv.clientHeight - table.clientHeight;
			spaceDiv.style.height = h;
			spaceDiv.style.display = 'none';
			frameDiv.style.height = frameDiv.offsetHeight - h;
		}
		else{
			// 定位到第一条选中的记录
			var rh = table.rows[0].clientHeight;
			var flagBoxs = this.getFlagBoxs();
			for( var ii=1; ii<table.rows.length; ii++ ){
				rh = rh + table.rows[ii].clientHeight;
				if( flagBoxs[ii-1].checked == true ){
					if( rh > frameDiv.clientHeight ){
						frameDiv.scrollTop = rh - frameDiv.clientHeight + 24;
					}
					
					break;
				}
			}
		}
	}
	else if( totalWidth > 101 ){
		if( frameDiv.clientHeight > 0 ){
			var scrollHeight = frameDiv.offsetHeight - frameDiv.clientHeight;
			frameDiv.style.height = scrollHeight + frameDiv.offsetHeight;
		}
	}
}


// 设置可以排序的字段 he 按钮
function grid_prepareSortItem()
{
	// 判断是否执行了查询
	var	table = this.getTable();
	if( table.rows.length <= 1 ){
		// 还没有查询
		return;
	}
	
	var startCol = (this.checkflag == 'false') ? 0 : 1;
	
	// 允许排序的字段列表
	var sortColumn = table.sortColumn;
	if( sortColumn == '*' ){
		var ids = table.rows[1].cells;
		for( var ii=startCol; ii<ids.length; ii++ ){
			this.sortItems[ii-startCol] = ids[ii].id.substring( 3 );
		}
	}
	else if( sortColumn != null ){
		sortColumn = sortColumn.replaceAll( ',', ';' );
		sortColumn = sortColumn.replaceAll( ':', ';' );
		this.sortItems = sortColumn.split( ';' );
		for( var ii=0; ii<this.sortItems.length; ii++ ){
			this.sortItems[ii] = this.sortItems[ii].trim( );
		}
	}
	
	// 取排序字段的名称 和 类型
	var sortedType = 'asc';
	var sortedItem = document.getElementsByName('attribute-node:' + this.gridName + '_sort-column');
	if( sortedItem.length > this.index ){
		sortedItem = sortedItem[this.index].value.trim( 0 );
		var ptr = sortedItem.indexOf( '.' );
		if( ptr > 0 ){
			sortedItem = sortedItem.substring( ptr + 1 );
		}
	}
	else{
		sortedItem = null;
	}
	
	// 格式化
	if( sortedItem != null ){
		sortedItem = trimSpace( sortedItem, 0 );
		if( sortedItem == '' ){
			sortedItem = null;
		}
		else{
			var ptr = sortedItem.indexOf( ' ' );
			if( ptr > 0 ){
				sortedType = sortedItem.substring( ptr + 1 );
				sortedItem = sortedItem.substring( 0, ptr );
				sortedType = trimSpace( sortedType, 0 );
				if( sortedType != 'desc' ){
					sortedType = 'asc';
				}
			}
		}
	}
	
	// 设置字段de排序按钮
	var cells = table.rows[0].cells;
	var ids = table.rows[1].cells;
	for( var ii=startCol; ii<ids.length; ii++ ){
		var itemName = ids[ii].id.substring( 3 );
		var caption = cells[ii].innerText;
		var cellCaption = this.prepareCellButton( itemName, caption, sortedItem, sortedType );
		cells[ii].innerHTML = cellCaption;
		cells[ii].style.cursor = 'default';
		/* comment By WeiQiang 2007-12-17
		if( itemName == sortedItem ){
			cells[ii].bgColor = 'yellow';
			cells[ii].style.color = 'blue';
		} 
		*/   
	}
	
	// 设置序号列
	for( var ii=startCol; ii<ids.length; ii++ ){
		var itemName = ids[ii].id.substring( 3 );
		if( itemName == '@rowid' ){
			this.setRowid( ii );
			break;
		}
	}
}

// 设置序号列
function grid_setRowid( columnId )
{
	var	table = this.getTable();
	var flag = this.getFlagBoxs();
	
	// 当前页 和 每页的记录数量
	var s = document.getElementById( this.gridName + '_curPage' );
	if( s == null ) return;
	var curPage = s.value;
	
	s = document.getElementById( 'attribute-node:' + this.gridName + '_page-row' );
	if( s == null ) return;
	var pageRow = s.value;
	
	var startRow = (parseInt(curPage) - 1) * parseInt(pageRow);
	
	var rows = table.rows;
	for( var ii=1; ii<=flag.length; ii++ ){
		var cell = rows[ii].cells[columnId];
		cell.firstChild.innerText = startRow + ii;
	}
}

// 生成字段的排序按钮
function grid_prepareCellButton( itemName, caption, sortedItem, sortedType )
{
	var str = caption;
	
	// 判断是否允许排序
	var flag = false;
	for( var ii=0; ii<this.sortItems.length; ii++ ){
		if( this.sortItems[ii] == itemName ){
			flag = true;
			break;
		}
	}
	
	// 不允许排序
	if( flag == false ){
		return str;
	}
	
	// 图标
	str += '&nbsp;<img border="0" vspace="1" hspace="0" style="cursor:hand" src="';
	str += _browse.contextPath;
	str += '/script/img/';
	
	if( itemName == sortedItem ){
		str += (sortedType == 'asc') ? 'arrow_up.gif"' : 'arrow_down.gif"';
	}
	else{
		str += 'arrow_off.gif"';
	}
	
	// 动作
	var onclick = "_sortTableByColumn('" + this.gridName + "', " + this.index + ", '" + itemName + "'); return false;";
	str += ' onclick="' + onclick + '"'
	
	return str + '>';
}

// 对数据表排序的函数
function _sortTableByColumn( gridName, index, columnName )
{
	if( window.event.offsetY > 5 ){
		columnName += ' desc';
	}
	else{
		columnName += ' asc';
	}
	
	// 提交事务
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return;
	}
	
	// 设置排序方式
	var sortedItem = document.getElementsByName('attribute-node:' + gridName + '_sort-column');
	if( sortedItem.length > index ){
		sortedItem[index].value = columnName;
	}
	
	_formSubmit( null, '正在对数据表的记录排序，请等待... ...' );
}


// 取边框颜色
function grid_getBorderColor()
{
	var table = this.getTable();
	var color = '';
	if(table){
		color = table.bgColor;
	}
	if( color != null && color != '' ){
		table.style.backgroundColor = color;
		return color;
	}
	
	color = getObjectStyleValue( table, 'backgroundColor' );
	if( color == null || color == '' ){
		color = '#000000';
	}
	
	return color;
}

// 设置grid的每一行的样式
function setGridRowsStyle( gridName, index, rowNumber )
{
	for( var ii=0; ii<rowNumber; ii++ ){
		// 设置当前行的样式:flag=1进入行、0=离开
		modifyCurrentRowStyle( gridName, index, ii, 0 );
	}
}


// 更新选中的记录关键字
// keyValue = null 时，清空记录
function _modifySelectedRecord( gridName, index, keyValue, flag )
{
	var	grid = getGridDefine(gridName, index);
	if( grid == null ) return;
	
	// 保存选中记录的字段
	var	keyItem = grid.getSelectedKeyBox();
	if( keyItem == null ) return;
	
	// 清空[fieldValues]
	grid.fieldValues.length = 0;
	
	// 清空记录
	if( keyValue == null ){
		if( flag ){
			keyItem.value = '';
		}
	}
	// 单记录
	else if( grid.multiselect != "true" ){
		if( flag ){
			keyItem.value = keyValue;
		}
	}
	else{
		// 选中的记录信息
		var	selectedKey = keyItem.value;
		var	keyList = selectedKey.split( ';' );
		
		// 增加或删除选中的记录
		selectedKey = "";
		for( ii=0; ii<keyList.length; ii++ ){
			if( keyList[ii] != keyValue ){
				if( selectedKey == "" ){
					selectedKey = keyList[ii];
				}
				else{
					selectedKey = selectedKey + ';' + keyList[ii];
				}
			}
		}
		
		if( flag ){
			if( selectedKey == "" ){
				selectedKey = keyValue;
			}
			else{
				selectedKey = selectedKey + ';' + keyValue;
			}
		}
		
		keyItem.value = selectedKey;
	}
	
	// 设置选中的记录信息
	page_setSelectedRecord( window, gridName, keyItem.value );
}


// 选择行时的动作
// 最后一个参数:对于不支持行选择时，进入这个函数的时候，当前行的状态已经发生了变化，需要复原
// 支持行选择时，如果点击的是checkbox，状态发生了三次变化；如果点击的是行，发生一次变化
function selectRow( gridName, index, rowid, changeFlag )
{
	if( rowid < 0 ) return;
  	
  	// 取定义信息
	var	grid = getGridDefine( gridName, index );
	if( grid == null ) return;
	
	// 没有chekcbox
	if( grid.checkflag == "" ) return;

	// checkbox
	var flagBoxs = grid.getFlagBoxs();
	if( flagBoxs[rowid] == null || flagBoxs[rowid].disabled == true ) return;
		
	// 当前form的名称
	currentFormName = grid.getFormName();
	currentGrid = grid;
	
	// 多行选择
	if( grid.multiselect == "true" ){
		// 对于不支持行选择时，进入这个函数的时候，当前行的状态已经发生了变化，需要复原
		if( changeFlag == false ){
			if( flagBoxs[rowid].checked == true ){
				flagBoxs[rowid].checked = false;
			}
			else{
				flagBoxs[rowid].checked = true;
			}
		}
		
		if( window.event != null ){
			// 判断SHIFT KEY
			if( window.event.shiftKey ){
				document.selection.empty();
				
				// 当前行已经选择，按正常的操作
				if( flagBoxs[rowid].checked == false ){
					if( grid.getSelectedRowNumber() == 1 ){
						// 取选中的行
						var	selectedId = -1;
						for( var i=0; i<flagBoxs.length; i++ ){
							if( flagBoxs[i].checked == true ){
								selectedId = i;
								break;
							}
						}
						
						// 计算区间
						if( selectedId > rowid ){
							var startId = rowid + 1;
							var stopId = selectedId - 1;
						}
						else{
							var startId = selectedId + 1;
							var stopId = rowid - 1;
						}
						
						// 设置区间，包括设置隐藏域
						for( var i = startId; i <= stopId; i ++ ){
							flagBoxs[i].checked = true;
							modifyRowStyle( gridName, index, i, 1 );
							_modifySelectedRecord( gridName, index, flagBoxs[i].value, true );
						}
					}
				} // flagBoxs[rowid].checked == false
			}
			else if( window.event.ctrlKey == false ){
				// 当前行已经选择，按正常的操作
				if( flagBoxs[rowid].checked == false && grid.supportCtrlKey ){
					// 撤销所有其它的选择
					for( var ii=0; ii<flagBoxs.length; ii++ ){
						if( ii != rowid && flagBoxs[ii].checked == true ){
							flagBoxs[ii].checked = false;
							modifyRowStyle( gridName, index, ii, 0 );
						}
					}
					
					// 清空保存选中记录的隐藏域
					_modifySelectedRecord( gridName, index, null, true );
				}
			}	// window.event.ctrlKey
		}
		
		// 设置行的状态
		if( flagBoxs[rowid].checked == true ){
			flagBoxs[rowid].checked = false;
		}
		else{
			flagBoxs[rowid].checked = true;
		}
		
		// 改变颜色
		if( flagBoxs[rowid].checked == false ){
			modifyRowStyle( gridName, index, rowid, 2 );
		}
		else{
			modifyRowStyle( gridName, index, rowid, 1 );
		}
	} // end if( grid.multiselect == "true" )
	else{
		for(var iii=0;iii<flagBoxs.length;iii++){
			if(flagBoxs[iii].checked == true){
				modifyRowStyle( gridName, index, iii, 0 );
			}
		}
		flagBoxs[rowid].checked = true;
		modifyRowStyle( gridName, index, rowid, 1 );
	}
	
	// 设置选中的记录
	if( flagBoxs[rowid].checked == true ){
		if(flagBoxs[rowid].type=='radio'){
			for(var iii=0;iii<flagBoxs.length;iii++){
				var tmp_1 = document.getElementById('label_'+iii);
				if(tmp_1)
					tmp_1.style.backgroundPositionY='bottom';
			}
			var tmp_2 = document.getElementById('label_'+rowid);
			if(tmp_2)
				tmp_2.style.backgroundPositionY='top';
		}
		_modifySelectedRecord( gridName, index, flagBoxs[rowid].value, true );
	}
	else{
		_modifySelectedRecord( gridName, index, flagBoxs[rowid].value, false );
	}
	
	// 设置按钮的状态
	checkAllMenuItem( );
	
	// 行选择时的动作
	if( grid.onselect != null && grid.onselect != '' ){
		var	flag = flagBoxs[rowid].checked;
		eval( grid.onselect.replace(/`/g, "'") );
	}
	//修改样式
	var obj=document.getElementById('check_'+rowid);
	if(obj){ 
	  var lab=document.getElementById("label_"+rowid);
	  if(lab){
		     if(obj.checked==false){
		        delClass(lab,"back_down");
		        addClass(lab,"back_up");
			 }else{
			    delClass(lab,"back_up");
				addClass(lab,"back_down");
		     }
	   }
	}
}

function addClass(elm,newClass){   
    var classes = elm.className.split(' ');
    var classIndex=hasClass(elm,newClass);
    if(classIndex==-1)classes.push(newClass);
    elm.className = classes.join(' '); 
}

function hasClass(elm,className){
    var classes = elm.className.split(' ');
    for(var a in classes){
        if(classes[a]==className)return a;
    }
    return -1;
}

function delClass(elm,className){
    var classes = elm.className.split(' ');
    var classIndex=hasClass(elm,className);
    if(classIndex!=-1)classes.splice(classIndex,1);
    elm.className=classes.join(' '); 
}


// 选择所有行
function selectAllRows( gridName, index )
{
	if( index == null ) index = 0;
	
	// 取定义信息
	var	grid = getGridDefine(gridName, index);
	if( grid == null || grid.checkflag == "" ) return;
	
	// 没有chekcbox
	if( grid.checkflag == "" || grid.multiselect != "true" ) return;
	
	// 取标志
	var selectAllBoxs = document.getElementsByName( grid.gridName + ':_select-all' );
	if( selectAllBoxs.length <= grid.index ) return;
	
	currentGrid = grid;
	
	var selectAllBox = selectAllBoxs[grid.index];
	var	flag = selectAllBox.checked;
	
	// checkbox
	var flagBoxs = grid.getFlagBoxs();
	if( flagBoxs.length == 0 ) return;
  	
	for( var i=0; i<flagBoxs.length; i++ ){
		if( flag == true ){
			flagBoxs[i].checked = true;
			_modifySelectedRecord( gridName, index, flagBoxs[i].value, true );
			modifyRowStyle( gridName, index, i, 1 );
		}
		else{
			flagBoxs[i].checked = false;
			_modifySelectedRecord( gridName, index, flagBoxs[i].value, false );
			modifyRowStyle( gridName, index, i, 0 );
		}
	}
	
	checkAllMenuItem( );
	var cbs = document.getElementsByName("record:_flag");
	for(var i = 0; i < cbs.length; i++){
	    var cbs_obj=cbs[i];
		/*if(cbs_obj.checked==false){
	        $("#label_"+i).removeClass("back_down");
			$("#label_"+i).addClass("back_up");
		  }else{
			$("#label_"+i).removeClass("back_up");
			$("#label_"+i).addClass("back_down");
		}*/
		var lab=document.getElementById('label_'+i);
		if(lab){
		     if(cbs_obj.checked==false){
		        delClass(lab,"back_down");
		        addClass(lab,"back_up");
			 }else{
			    delClass(lab,"back_up");
				addClass(lab,"back_down");
		     }
	   }
	}
}



// 从列表的键字中根据名称获取内容
function getKeyValue( selKey, name )
{
	if( selKey == null ){
		return '';
	}
	
	var	keys = selKey.split( '&' );
	if( keys.length == 1 ){
		return keys[0];
	}
	
	var	ii;
	name = name + '=';
	for( ii=0; ii<keys.length; ii++ ){
		if( keys[ii].indexOf(name) == 0 ){
			return keys[ii].substring( name.length );
		}
	}
	
	return '';
}

// 取cell
// rowid = 行号：从1开始计数
// cellName = cell的property
function grid_getCell( rowid, cellName )
{
	// 取表
	var	table = this.getTable();
	if( table == null ){
		return null;
	}
	
	// 取行
	var	row = table.rows( "row_" + (rowid-1) );
	if( row == null ){
		return null;
	}
	
	// 取列
	var	cell = row.cells( "td_" + cellName );
	if( cell == null ){
		return null;
	}
	
	return cell;
}

// 取cell的内容
// rowid = 行号：从1开始计数
// cellName = cell的property
function grid_getCellValue( rowid, cellName )
{
	// 判断是否是保留字
	if( cellName == '@key' ){
		var flagBoxs = this.getFlagBoxs();
		if( flagBoxs.length < rowid ){
			return null;
		}
		
		return flagBoxs[rowid-1].value;
	}
	
	if( cellName == '@index' ){
		return rowid - 1;
	}
	
	// 判断是否是keylist
	var	keys = this.keyList.split(',');
	for( i=0; i<keys.length; i++ ){
		if( keys[i] == cellName ){
			var flagBoxs = this.getFlagBoxs();
			if( flagBoxs.length < rowid ){
				return null;
			}
			
			var	value = flagBoxs[rowid-1].value;
			return getKeyValue( value, cellName );
		}
	}
	
	// 取列
	var	cell = this.getCell( rowid, cellName );
	if( cell == null ){
		return null;
	}
	
	return cell.innerText;
}


// 设置单元格的内容
// rowid = 行号：从1开始计数
// cellName = cell的property
function grid_setCellValue( rowid, cellName, cellValue )
{
	// 判断是否是keylist
	var	keys = this.keyList.split(',');
	for( i=0; i<keys.length; i++ ){
		if( keys[i] == cellName ){
			break;
		}
	}
	
	// key处理
	if( i<keys.length ){
		var flagBoxs = this.getFlagBoxs();
		if( flagBoxs.length < rowid ){
			return;
		}
		
		var	value = flagBoxs[rowid-1].value;
		if( keys.length == 1 ){
			value = cellValue;
		}
		else if( value == null || value == '' ){
			value = cellName + '=' + cellValue;
		}
		else{
			var	flag = false;
			var	vs = value.split('&');
			
			value = '';
			var	name = cellName + '=';
			for( var ii=0; ii<vs.length; ii++ ){
				if( vs[ii].indexOf(name) == 0 ){
					flag = true;
					value = value + '&' + cellName + '=' + cellValue;
				}
				else{
					value = value + '&' + vs[ii];
				}
			}
			
			// 没有找到
			if( flag == false ){
				value = value + '&' + cellName + '=' + cellValue;
			}
			
			// 删除第一个字符
			value = value.substring(1);
		}
		
		// 设置关键字
		flagBoxs[rowid-1].value = value;
	}
	
	// 查找是否是域
	var	fieldName = this.gridName + ':' + cellName;
	var	obj = document.getElementsByName( fieldName );
	if( obj.length > 0 ){
		form_setFieldValue( obj, rowid-1, cellValue );
	}
	else{
		// 清空数据
		this.fieldValues.length = 0;
		
		// 取列
		var	cell = this.getCell( rowid, cellName );
		if( cell == null ){
			return;
		}
		
		// 修改内容
		cell.innerText = cellValue;
	}
}



// 取已经选择的行的指定域的数组
// 对于非主键的数据，如果在程序中需要使用，可以通过配置hidden域的方式保存数据，并通过这个函数获取已经选中的记录
// 如果没有隐藏域，从cell中取数据
function grid_getFieldValues( cellName )
{
	// 判断是否已经取值
	for( var ii=0; ii<this.fieldValues.length; ii++ ){
		if( this.fieldValues[ii][0] == cellName ){
			return this.fieldValues[ii][1];
		}
	}
	
	// 结果
	var num = 0;
	var selectedRows = new Array();
	
	// 是否有TABLE
	var	table = this.getTable();
	if( table == null ){
		return selectedRows;
	}
	
	// 取checkbox
	var flagBoxs = this.getFlagBoxs();
	
	// 取需要取值的域
	var	fieldName = this.gridName + ':' + cellName;
	var	fields = document.getElementsByName( fieldName );
	
	// 从输入域取数据
	if( fields.length > 0 ){
		var startId = this.getFirstRowIndex();
		
		// 获取域的定义信息
		var	fieldDefine = getSelectInfoByFieldName( fieldName );
		if( fieldDefine != null ){
			for( var i=0; i<flagBoxs.length; i++ ){
				if( flagBoxs[i].checked == true ){
					selectedRows[num++] = fieldDefine.getValue( startId+i );
				}
			}
		}
		else{
			for( var i=0; i<flagBoxs.length; i++ ){
				if( flagBoxs[i].checked == true ){
					selectedRows[num++] = fields[startId+i].value;
				}
			}
		}
		
		return selectedRows;
	}
	
	// 从cell取数据
    for( var i=0; i<flagBoxs.length; i++ ){
	    if( flagBoxs[i].checked == true ){
	    	// 没有找到hidden域，从cell取
	    	var value = this.getCellValue( i+1, cellName );
	    	if( value == null ){
	    		selectedRows[num++] = "";
	    	}
	    	else{
	    		selectedRows[num++] = trimSpace( value );
	    	}
	    }
	    
		// 保存到[fieldValues]
		this.fieldValues.push( [cellName, selectedRows] );
    }
	
	return selectedRows;
}

function grid_setFieldValues( cellName, cellValue )
{
	// 是否有TABLE
	var	table = this.getTable();
	if( table == null ) return;
	
	// 设置内容
	var flagBoxs = this.getFlagBoxs();
	var	fieldName = this.gridName + ':' + cellName;
	var startId = this.getFirstRowIndex();
	for( var i=0; i<flagBoxs.length; i++ ){
		if( flagBoxs[i].checked == true ){
			setFormFieldValue( fieldName, startId+i, cellValue );
		}
	}
}

// 取所有的记录
function grid_getAllFieldValues( cellName )
{
	var allRows = new Array();
	
	// 是否有TABLE
	var	table = this.getTable();
	if( table == null ){
		return allRows;
	}
	
	// 取checkbox
    var flagBoxs = this.getFlagBoxs();
	
	// 取需要取值的域
	var	fieldName = this.gridName + ':' + cellName;
	var	fields = document.getElementsByName( fieldName );
	
	// 从输入域取数据
	if( fields.length > 0 ){
		var startId = this.getFirstRowIndex();
		
		// 获取域的定义信息
		var	fieldDefine = getSelectInfoByFieldName( fieldName );
		if( fieldDefine != null ){
			for( var i=0; i<flagBoxs.length; i++ ){
				allRows[i] = fieldDefine.getValue( startId+i );
			}
		}
		else{
			for( var i=0; i<flagBoxs.length; i++ ){
				allRows[i] = fields[startId+i].value;
			}
		}
		
		return allRows;
	}
	
	// 没有找到hidden域，从cell取
	for( var i=0; i<flagBoxs.length; i++ ){
		allRows[i] = this.getCellValue( i+1, cellName );
		if( allRows[i] == null ){
			allRows[i] = "";
		}
		else{
			allRows[i] = trimSpace( allRows[i] );
		}
	}
    
	return allRows;
}


// 取FORM的名称
function grid_getFormName()
{
	if( this.formName == null ){
		// 查找名称
		this.formName = _getFormName( this.gridName, this.index );
	}
	
	return this.formName;
}

// 取对象
function grid_getTable()
{
	if( this.table != null ){
		return this.table;
	}
	
	var	tables = document.getElementsByName( this.gridName );
	if( tables.length <= this.index ){
		return null;
	}
	else{
		this.table = tables[this.index];
		return this.table;
	}
}

function grid_isChildNode( obj )
{
	var table = this.getTable();
	
	// 判断是否子节点
	obj = obj.parentNode;
	while( obj != null ){
		if( obj == table ){
			return true;
		}
		
		obj = obj.parentNode;
	}
	
	return false;
}

function grid_getFrameDiv()
{
	var divs = document.getElementsByName( 'div_' + this.gridName )
	if( divs.length <= this.index ){
		return null;
	}
	else{
		return divs[this.index];
	}
}

function grid_getSpaceDiv()
{
	var divs = document.getElementsByName( 'sp_' + this.gridName )
	if( divs.length <= this.index ){
		return null;
	}
	else{
		return divs[this.index];
	}
}

// 取_selected-key
function grid_getSelectedKeyBox()
{
	var	fieldName = "attribute-node:" + this.gridName + "_selected-key";
	var keyBoxs = document.getElementsByName( fieldName );
	if( keyBoxs.length <= this.index ){
		return null;
	}
	
	return keyBoxs[this.index];
}

// 取grid的checkbox数组
function grid_getFlagBoxs()
{
	var flagBoxs = document.getElementsByName( this.gridName + ":_flag" );
	if( flagBoxs.length == 0 ){
		return new Array();
	}
	
	var	tables = document.getElementsByName( this.gridName );
	if( tables.length <= this.index ){
		return new Array();
	}
	
	if( tables.length == 1 ){
		return flagBoxs;
	}
	
	// 过滤
	var flags = new Array();
	var table = tables[this.index];
	for( var ii=0; ii<flagBoxs.length; ii++ ){
		if( flagBoxs[ii].parentNode.parentNode.parentNode.parentNode == table ){
			flags.push( flagBoxs[ii] );
		}
	}
	
	return flags;
}

// 取grid的第一行在同名grid中的行编号：同名的grid可能有很多个
function grid_getFirstRowIndex()
{
	var flagBoxs = document.getElementsByName( this.gridName + ":_flag" );
	if( flagBoxs.length == 0 ){
		return 0;
	}
	
	var	tables = document.getElementsByName( this.gridName );
	if( tables.length <= this.index ){
		return 0;
	}
	
	if( tables.length == 1 ){
		return 0;
	}
	
	// 过滤
	var flags = new Array();
	var table = tables[this.index];
	for( var ii=0; ii<flagBoxs.length; ii++ ){
		if( flagBoxs[ii].parentNode.parentNode.parentNode.parentNode == table ){
			return ii;
		}
	}
	
	return 0;
}


// 生成正文的开始部分
function grid_beginBody( frameName, height, selectedKey, sortedColumn )
{
	// 框架
	var str = "<tr><td>\n";
	str += "<div id='div_" + frameName + "' style='display:none;";
	
	// 高度
	if( height != null && height != '' ){
		str += "height:" + height;
	}
	
	str += "' class='pages'>\n";
	document.write( str );
	
	// 选中字段
	fz_addHiddenField( 'attribute-node:' + frameName + '_selected-key', selectedKey.trim() );
	
	// 排序字段
	fz_addHiddenField( 'attribute-node:' + frameName + '_sort-column', sortedColumn.trim() );
}


// 生成页面的导航条
function prepareNavigateBar( valign, gridName, pageRow, currentRow, totalRow, index )
{
 
	// 取框架
	var frame = document.getElementById( 'frame_' + gridName );
	if( frame == null ){
		return;
	}
	
	// 取index
	if( index == null ){
		var ii;
		for( ii=grids.length-1; ii>=0; ii-- ){
			if( grids[ii].gridName == gridName ){
				index = grids[ii].index;
				
			}
		}
		
		if( index == null ){
			return;
		}
	}
	
	// 初始化
	if( frame.clientWidth == 0 ){
		setTimeout( function(){prepareNavigateBar(valign, gridName, pageRow, currentRow, totalRow, index);}, 10 );
		return;
	}
	
	// 取配置信息
	var grid = getGridDefine( gridName, index );
	if( grid == null ){
		return;
	}
	
	// 记录数量
	grid.totalRecord = totalRow;
	
	// 计算宽带，根据宽带调整导航条
	if( frame.clientWidth >= 610 ){
		var navType = 1;
	}
	else if( frame.clientWidth >= 561 ){
		var navType = 2;
	}
	else if( frame.clientWidth >= 526 ){
		var navType = 3;
	}
	else if( frame.clientWidth >= 472 ){
		var navType = 4;
	}
	else{
		var navType = 5;
	}
	
	// 计算页信息
	var	currentPage;
	if( currentRow > 0 && pageRow == 1 ){
		currentPage = currentRow;
	}
	else if( currentRow > 0 && pageRow > 0 ){
		currentPage = Math.floor( currentRow / pageRow ) + 1;
	}
	else{
		currentPage = 1;
	}
	
	// 总页数
	var	totalPage;
	if( pageRow > 0 ){
		totalPage = Math.floor( (totalRow + pageRow - 1) / pageRow );
	}
	else{
		totalPage = 0;
	}
	
	// 左边记录数量信息
	var result = '<table width="100%"';
	if( valign == 'top' ){
		result = result + ' style="border-top-style:solid; border-top-width:1px"';
	}
	else{
		result = result + ' style="border-bottom-style:solid; border-bottom-width:1px"';
	}
	
	// 线条颜色
	var	table = grid.getTable();
	var bgColor = grid.getBorderColor();
	result = result + ' bordercolor="' + bgColor + '">\n<tr>\n<td valign=center>\n';
	
	// 当前页
	/*if( navType == 3 || navType == 5 ){
		result = result + '第';
	}
	else{
		result = result + '当前第';
	}*/
	
	result = result + '<input id="' + gridName + '_curPage"' +
		' name="' + gridName + '_curPage" value="'+currentPage+'" type="hidden" />\n';
	
	// 总共页
	result = result + ' <input id="' + gridName + '_totalPage"' +
		' name="' + gridName + '_totalPage" value="'+totalPage+'" type="hidden" />\n';
	
	// 共记录数量
	/*if( navType == 3 || navType == 5 ){
		result = result + ' 记录数';
	}
	else{
		result = result + ' 记录总数';
	}*/
	result = result + '<div style="float:left;display:inline; height: 35px; width:60px; text-align:center;">'
	+'<span style="display:inline-block;width:100%; background: #e7e7f6;color: #888;font-size:16px;" id="' + gridName 
	+ '_totalRow"' + ' name="' + gridName + '_curRow">' + totalRow 
	+ '</span><span style="white-space:nowrap;color:#888;">记录总数</span></div>\n';
	result += "<div style='display:inline; height: 35px; text-align:center;float:left;'>&nbsp;&nbsp;";
	result += '<div title="第一页" onclick="'+prepageGoRecoed(gridName, index, '1', totalRow)+'" class="goFirst">&nbsp;</div>';
	result += '<div title="上一页" onclick="'+prepageGoRecoed(gridName, index, currentPage - 1, totalRow)+'" class="goPre">&nbsp;</div>';
	if(totalPage < 6){
		var clsName = 'goPage';
		for(var ii=1; ii<=totalPage; ii++){
			clsName = (ii==currentPage ? clsName+' nowP' : clsName);
			result += '<div title="第'+ ii +'页" onclick="'+prepageGoRecoed(gridName, index, ii, totalRow)+'" class="'+clsName+'">'+ii+'</div>';
			clsName = 'goPage';
		}
	}else{
		var clsName = 'goPage';
		for(var ii=-4; ii<5; ii++){
			if((currentPage+ii)<=0 || (currentPage+ii)>totalPage)
				continue;
			clsName = (ii==0 ? clsName+' nowP' : clsName);
			result += '<div title="第'+ (currentPage+ii) +'页" onclick="'+prepageGoRecoed(gridName, index, (currentPage+ii), totalRow)
			+'" class="'+clsName+'">'+(currentPage+ii)+'</div>';
			clsName = 'goPage';
		}
	}
	result += '<div title="下一页" onclick="'+prepageGoRecoed(gridName, index, currentPage + 1, totalRow)+'" class="goNext">&nbsp;</div>';
	result += '<div title="最后一页" onclick="'+prepageGoRecoed(gridName, index, totalPage, totalRow)+'" class="goLast">&nbsp;</div></div></div>';
	// 保存记录总数
	result = result + '<input type="hidden" id="attribute-node:' + gridName + '_record-number"' + 
			' name="attribute-node:' + gridName + '_record-number" value="">\n';
			
	
	//每页显示多少条
	//var func=' onchange="prepageGoRecoed('+gridName+', '+index+', '+currentPage+' , '+totalRow+')";';
	//alert(func);
    /*result = result + '每页显示<select id="customPageRowSeleted" onchange="' + prepageGoRecoed(gridName, index, 'go', totalRow) + '">\n';
    
	if(pageRow==10){
		result+='<option value=10 selected>10</option><option value=20>20</option><option value=30>30</option><option value=40>40</option><option value=50>50</option></select>条\n';
	}else if(pageRow==20){
		result+='<option value=10 >10</option><option value=20 selected>20</option><option value=30>30</option><option value=40>40</option><option value=50>50</option></select>条\n';
	}else if(pageRow==30){
		result+='<option value=10 selected>10</option><option value=20>20</option><option value=30 selected>30</option><option value=40>40</option><option value=50>50</option></select>条\n';
	}else if(pageRow==40){
		result+='<option value=10 selected>10</option><option value=20>20</option><option value=30>30</option><option value=40 selected>40</option><option value=50>50</option></select>条\n';
	}else if(pageRow==50){
		result+='<option value=10 selected>10</option><option value=20>20</option><option value=30>30</option><option value=40>40</option><option value=50 selected>50</option></select>条\n';
	}else{
	   //update dc2 zhongxiaoqi
	   result+='<option value='+pageRow+' selected>'+pageRow+'</option><option value=10>10</option><option value=20>20</option><option value=30>30</option><option value=40>40</option><option value=50>50</option></select>条\n'; 
	}*/
	 
	// 选择页
	/*if(totalPage<=0 && totalPage > 0 ){
		result = result + ' 转到 <select style="color:blue" id="' + gridName + '_selectPage"' + 
			' name="' + gridName + '_selectPage" onchange="' + prepageGoRecoed(gridName, index, 'go', totalRow) + '">\n';
	 	
	 	var	ii;
	 	for( ii=1; ii<=totalPage; ii++ ){
	 		if( ii == currentPage ){
	  			result = result + '<option value="' + ii +'" selected>' + ii + '</option>\n';
	  		}
	  		else{
	  			result = result + '<option value="' + ii +'">' + ii + '</option>\n';
	  		}
	  	}
	 	
		result = result + '</select> 页\n';
	}
	else{
		result = result + ' 转到 <input type="text" style="color:blue" id="' + gridName + '_selectPage"' + 
			' name="' + gridName + '_selectPage" value="' + currentPage + '" onkeydown="if(event.keyCode == 13){' +
			prepageGoRecoed(gridName, index, 'go', totalRow) + '}" size="3" style="border: 1px solid #7f9db9"> 页\n';
	}*/
	
	
	// Comment By WeiQiang
	// 每页的记录数量
	/*if( navType > 3 ){
		result = result + 
			'<input type="hidden" style="color:blue" id="attribute-node:' + gridName + '_page-row"' +
			' name="attribute-node:' + gridName + '_page-row" value="' + pageRow + '">';
	}
	else{
		
		if( navType == 1 ){
			result = result + ' 每页显示：';
		}
		else{
			result = result + ' 每页：';
		}
		
		result = result + 
			'<input type="text" style="color:blue" id="attribute-node:' + gridName + '_page-row"' +
			' name="attribute-node:' + gridName + '_page-row" value="' + pageRow + '"' +
			' onkeydown="if(event.keyCode == 13){' + prepageGoRecoed(gridName, index, 'go', totalRow) + '}" size="3" style="border: 1px solid #7f9db9">';
		
		if( navType == 1 ){
			result = result + ' 条信息';
		}
		else{
			result = result + ' 条';
		}
		
		
	}*/
	// Comment end, WeiQiang
	
	
	// 每页的记录数量
	// Copy By WeiQiang
	
	if( navType > 3 ){
		result = result + 
			'<input type="hidden" style="color:blue" id="attribute-node:' + gridName + '_page-row"' +
			' name="attribute-node:' + gridName + '_page-row" value="' + pageRow + '">';
	}
	else{
		
		if( navType == 1 ){
			result = result + '';
		}
		else{
			result = result + '';
		}
	
		result = result + 
			'<input type="text" style="color:blue; display:none" id="attribute-node:' + gridName + '_page-row"' +
			' name="attribute-node:' + gridName + '_page-row" value="' + pageRow + '"' +
			' onkeydown="if(event.keyCode == 13){' + prepageGoRecoed(gridName, index, 'go', totalRow) + '}" size="3" style="border: 1px solid #7f9db9">';
		
		if( navType == 1 ){
			result = result + '';
		}
		else{
			result = result + '';
		}		
	}
	// Copy end, WeiQiang
	
	// 结束
	//result = result + '</td>\n';
	
	// 按钮第一页
	/*result = result + '<td align="right">\n' + 
		'<button style="WIDTH:25px" class="goFirst" onclick="' + prepageGoRecoed(gridName, index, '1', totalRow) +
		'" class="menu" title="第一页"';
	if( currentPage <= 1 ){
	 	result = result + ' disabled';
	}
	
	result = result + '></button>&nbsp;\n';
	*/
	// 上一页
	/*result = result + '<button class="goPre" style="WIDTH:25px" onclick="' + prepageGoRecoed(gridName, index, currentPage - 1, totalRow) +
		'" class="menu" title="上一页"';
	if( currentPage <= 1 ){
	 	result = result + ' disabled';
	}
	
	result = result + '></button>&nbsp;\n';
	*/
	// 下一页
	/*result = result + '<button class="goNext" style="WIDTH:25px" onclick="' + prepageGoRecoed(gridName, index, currentPage + 1, totalRow) +
		'" class="menu" title="下一页"';
	if( currentPage >= totalPage && totalPage >= 0 ){
	 	result = result + ' disabled';
	}
	
	result = result + '></button>&nbsp;\n';
	*/
	// 最后一页
	/*result = result + '<button class="goLast" style="WIDTH:25px" onclick="' + prepageGoRecoed(gridName, index, totalPage, totalRow) +
		'" class="menu" title="最后一页"';
	if( currentPage >= totalPage ){
	 	result = result + ' disabled';
	}
	
	result = result + '></button>\n';
	*/
	// 隐藏数据:当前第一行
	result = result + '<input type="hidden" id="attribute-node:' + gridName + '_start-row\"' +
		' name="attribute-node:' + gridName + '_start-row\"' +
		' value="' + currentRow + '">\n';
	
	// 导航条结束
	result = result + '</td></tr></table>\n';
	
	// 插入导航条
	var myRow = frame.rows( 'navbar' );
	var myCell = myRow.insertCell();
	myCell.innerHTML = result;
	var divs = document.getElementById('navbar').getElementsByTagName('div');
	if(divs){
		for(var ii=0; ii<divs.length; ii++){
			var div = divs[ii];
			if(div.className.indexOf('goPage')>-1 && div.className.indexOf('nowP')==-1 ){
				div.onmouseover = function(){
					this.className = 'goPage nowP';
				}
				div.onmouseout = function(){
					this.className = 'goPage';
				}
			}
		}
	}
	
	//alert("totalRow:" + totalRow + ", pageRow:" + pageRow);
	// 添加了Grid没有数据时候的处理
	/** 风格1：隐藏Grid，并且在Grid处显示提示div（多个Grid被隐藏的时候就会只有一条提示信息）
	if (totalRow.toString() == "0"){
		if (pageRow.toString() != "0" && pageRow.toString() != "-1"){
			// alert("没有查询到数据，请更改条件后重新查询！");
			var hint = document.createElement("div");
			frame.parentNode.appendChild(hint);
			var hintWindowLayer = document.getElementById("hintWindowLayer");
			hint.innerHTML = "提示：没有相关数据！";
			hint.style.textAlign = "center";
			hint.style.position = "absolute";
			hint.style.width = frame.offsetWidth;
			hint.style.top = frame.offsetTop + frame.offsetHeight/2 - 20;
			hint.style.left = frame.offsetLeft;
			hint.style.fontSize = "12pt";
			hint.style.display = "block";
			// alert(hint.outerHTML);
			hint = null;
			hintWindowLayer = null;
		}		
		if (pageRow.toString() != "-1"){
			frame.style.display = "none";
		}
	}
	* **/
	/** 风格2：隐藏Grid，并且在每个Grid前面提示没有数据。（不知道是什么数据被隐藏了）
	if (totalRow.toString() == "0"){
		if (pageRow.toString() != "0" && pageRow.toString() != "-1"){
			// alert("没有查询到数据，请更改条件后重新查询！");
			var hint = document.createElement("div");
			hint.innerHTML = "提示：没有相关数据！";
			hint.style.textAlign = "center";
			hint.style.width = "100%";
			hint.style.fontSize = "12pt";
			hint.style.display = "block";
			frame.parentNode.insertBefore(hint, frame);
			hint = null;
		}		
		if (pageRow.toString() != "-1"){
			frame.style.display = "none";
		}
	}
	*/
	/** 风格3：显示Grid，但是不显示分页帮助条，并显示没有找到相关数据的提示信息 
	*/
	if (totalRow.toString() == "0"){
		var isShowNoDataSign = false;
		if (pageRow.toString() == "0"){
			// 进入查询页面但是尚未查询的情况，直接隐藏
			frame.style.display = "none";
			// 如果找到提示信息，显示提示信息(等待到页面加载完成后执行)
			_browse.execute(function(){
				var queryHelpDiv = document.getElementById("queryHelpDiv");
				if (queryHelpDiv){
					queryHelpDiv.style.display = "block";
				}
				queryHelpDiv = null;
			});
		}else if (pageRow.toString() == "-1"){
			// 不分页显示的时候，如果记录数为0，显示提示信息(此时没有总记录数，需要先获取)
			var totalRecord = grid.getFlagBoxs().length;
			if (totalRecord == 0){
				isShowNoDataSign = true;
			}
		}else{
			// 隐藏翻页条，并显示提示信息
			myCell.style.display = "none";
			isShowNoDataSign = true;
		}
		if (isShowNoDataSign){
			var myInfoCell = myRow.parentNode.insertRow().insertCell();
            myInfoCell.innerHTML = "<span id='noDataList' style='width:100%; text-align:center; color:#3333FF; font-size:12pt;'>提示：没有相关数据！</span>";
		}
		
	}
}


function prepageGoRecoed( gridName, index, cmd, totalRow )
{
	return "grid_goRecord('" + gridName + "', " + index + ", " + totalRow + ", '" + cmd + "'); return false;";
}

// 取属性的对象
function getAttributeObject( gridName, index, attrName )
{
	var objs = document.getElementsByName( 'attribute-node:' + gridName + '_' + attrName );
	if( objs.length > index ){
		return objs[index];
	}
	else{
		return null;
	}
}

// 设置属性的对象
function setAttributeObject( gridName, index, attrName, ss )
{
	var objs = document.getElementsByName( 'attribute-node:' + gridName + '_' + attrName );
	if( objs.length > index ){
		objs[index].value=ss;
	}
}

// 页面跳转
function grid_goRecord(gridName, index, totalRow, flag)
{
  if( flag == null || flag == '' ){
  	flag = 'go';
  }
  
  var pageRowField = getAttributeObject(gridName, index, 'page-row');
  var startRowField = getAttributeObject(gridName, index, 'start-row');
  
  var pageRow=parseInt(pageRowField.value);
  //DC2-jufeng-20120724 add pageRow selected
  /*var ss = document.getElementById('customPageRowSeleted')
  var pageRow2 = parseInt( ss.options[ss.selectedIndex].text );
  if(pageRow2){
 	 pageRow = pageRow2
  }*/
  if( pageRow > 500 ){
    pageRow = 500;
  }
  else if( pageRow < 1 ){
    pageRow = 10;
  }
  
  if( flag == 'go' ){
  	var objs = document.getElementsByName( gridName + '_selectPage' );
  	var pageno = parseInt(objs[index].value);
  }
  else{
  	var pageno = parseInt(flag);
  }
  
  var startRow = (pageno-1) * pageRow + 1;
  pageRowField.value = pageRow;
  startRowField.value = startRow;
  
  var action =  pageRowField.form.action;
  action += (action.indexOf('?') > 0) ? '&' : '?';
  
  var param = 'attribute-node:' + gridName + '_page-row=' + pageRow
		+ '&attribute-node:' + gridName + '_start-row=' + startRow
		+ '&attribute-node:' + gridName + '_record-number=' + totalRow;
	
  //alert("111  \n"+pageRow2 +'\n 222 \n'+param)
  
  pageRowField = startRowField = null;
  // modified By WeiQiang 2009-02-12
  // fixed the bug that when change grid's page and the open type is new-window, window will be close
  // alert( action+param );
  var nextPage = action+param ;
  // goWindowBack( action+param );
	
	// 缺省导航到上一个页面
	if( nextPage == null ){
		nextPage = _prePageName;
	}
	
	// 取缓存数据标志
	var addr = _addHrefParameter( nextPage, 'inner-flag:back-flag', 'true' );
 	// alert("全路径:" + addr);
 	
 	// 打开窗口的类型
	if( openWindowType == 'subframe' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'subframe' );
	}else if ( openWindowType == 'new-window' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'new-window' );
	}else{
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'window' );
	}
	
	// 生成全路径
	addr = _browse.resolveURL( addr );
	// alert("全路径:" + addr);
 	// 转换编码
 	addr = page_setSubmitData( window, addr );
	window.location = addr;
  
}
