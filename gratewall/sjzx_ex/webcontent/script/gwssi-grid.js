// 2006/07/12:����֧�ֶ�ѡʱ��grid��ֻ�а���shift�������ܹ���ѡ
// grid ����
// grid����ʽ
// oddstyle ��������ʽ 
// evenstyle ż������ʽ 
// activestyle �����ʽ 
// selectedstyle ѡ������ʽ 
function gridStyle( oddstyle, evenstyle, activestyle, selectedstyle )
{
	this.oddstyle = oddstyle;
	this.evenstyle = evenstyle;
	this.activestyle = activestyle;
	this.selectedstyle = activestyle;
}


// grid������Ϣ
// gridName grid����
// keyList �ؼ����б�
// checkflag �Ƿ���checkbox
// multiselect �Ƿ��������
// rowselect �Ƿ�֧����ѡ��boolean
// onselect ѡ����ʱ�Ķ�����(checkflag=true)��Ч
// onshow ����ÿһ�е���ʽ,ԭ��onshow( grid, rowid, flag ),���Դ���ÿһ�������ʽ��grid=grid�Ķ��塢rowid=�кţ���1��ʼ,flag=1:ѡ�С�2=���0=����
// style grid�и�ʽ -- gridStyle�ṹ������������ʽ
// menus grid�˵��б�
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
	
	// �Ƿ�֧��CTRL��
	this.supportCtrlKey = _gridSupportCtrlKey;
	
	// ����������ֶ��б�
	this.sortItems = new Array();	// ����������ֶ��б�
	this.prepareSortItem = grid_prepareSortItem;	// �����ֶε�����ť
	this.prepareCellButton = grid_prepareCellButton;	// ���ɵ����ֶεİ�ť
	
	// ѡ���еĶ���
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
	
	// ��ʽ������[onshow( grid, rowid, flag )]
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
	
	// ��¼����
	this.totalRecord = 0;
	
	// cell��ѡ�е����ݣ��ڵ���[getFieldValues]ʱ�������ֵ����[_modifySelectedRecord]ʱ������ֵ����ʽ��[cellName, valueList]
	this.fieldValues = new Array();
	
	// ����
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
	
	// ��ʼ������:����ҳ��ʱ���������������TABLE����ʽ
	this.initGridStyle = grid_initGridStyle;
	this.prepareTableRect = grid_prepareTableRect;
	this.prepareTableHeight = grid_prepareTableHeight;
	this.setRowid = grid_setRowid;		// �滻@rowid��ֵ
	
	// ȡ�߿���ɫ
	this.getBorderColor = grid_getBorderColor;
	
	// ȡFORM������
	this.formName = null;
	this.getFormName = grid_getFormName;
	
	// ȡtable�ȶ���
	this.table = null;
	this.getTable = grid_getTable;
	this.isChildNode = grid_isChildNode;
	
	this.getFrameDiv = grid_getFrameDiv;
	this.getSpaceDiv = grid_getSpaceDiv;
	this.getSelectedKeyBox = grid_getSelectedKeyBox;
	this.getFlagBoxs = grid_getFlagBoxs;
	
	// ȡgrid�ĵ�һ�еı�ţ�������grid�У�ȡ����ֵ��ѡ��ֵʱʹ��
	this.getFirstRowIndex = grid_getFirstRowIndex;
}

// grid�����б�
var	grids = new Array();

// ��ǰ���ڲ�����grid
var	currentGrid = null;

// ����grid������Ϣ
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

// �������ƻ�ȡgrid������Ϣ
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

// �ж��Ƿ�ΪGRID����
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

// ��ȡ��ť�����grid������Ϣ
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



// ִ�а�ť����
function grid_menuClick( menu )
{
	// ��ǰ�����grid
	currentGrid = this;
	
	// ȡ�Ѿ�ѡ�еļ�¼
	var selRows = new Array();
	var flagBoxs = this.getFlagBoxs();
    for( var i=0; i<flagBoxs.length; i++ ){
      if( flagBoxs[i].checked == true ){
        selRows.push( flagBoxs[i].value );
      }
    }
	
	// ���ñ�־��ֹ��ť
	clickFlag = 1;
	checkAllMenuItem( );
	
	// ������Ҫ���ص���ϸ����
	setLoadNode( this.gridName );
	
	// ���ú���
	menu.fireEvent( selRows, this.gridName, this.index );
  	
	// ���������Ҫ�ָ�
	if( clickFlag == 0 ){
		setLoadNode('');
	}
	
	checkAllMenuItem( );
	
	// ��ֹ����ִ�У�����image�ύ
	return false;
}


// ��������ȡ�˵�
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

// ���ò˵���visible
function grid_setMenuVisible( menuName, visible )
{
	var menu = this.getMenuByName( menuName );
	if( menu == null ){
		return;
	}
	
	// �����Ƿ�ɼ�
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

// ���ò˵��ı���
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


// ��鰴ť����Ч��
function grid_checkMenuItem( )
{
	var	i;
	var	buttonName;
	
	var	menu = this.menus;
	if( menu == null ) return;
	
	// ��ֹ���а�ť
	if( clickFlag == 1 ){
		for( i=0; i<menu.length; i++ ){
			menu[i].setStatus( true );
		}
		
    	return;
  	}
  	
  	// ȡѡ�еļ�¼����
  	var selRowNumber = this.getSelectedRowNumber();
	
	// ��鰴ť
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


// ȡ��ѡ��ʽ���Ѿ���ѡ�еļ�¼���
function getSelectedRowid( gridName, index )
{
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return -1;
	}
	
	// ȡcheckbox
	var flagBoxs = grid.getFlagBoxs();
	if( flagBoxs.length == 0 ){
		return -1;
	}
	
	// ���ж�ѡ���е���ʽ
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
	
	// ȡѡ�еı�־
	for( var i=0; i<flagBoxs.length; i++ ){
		if( flagBoxs[i].checked == true ){
			return i;
		}
	}
	
	return -1;
}

// ȡѡ�еļ�¼����
function grid_getSelectedRowNumber( )
{
	var num = 0;
	
	// ȡcheckbox
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

// ���еļ�¼����
function getAllRowNumber( gridName, index )
{
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return 0;
	}
	
	// ȡcheckbox
	var flagBoxs = grid.getFlagBoxs();
	if( flagBoxs.length == 0 ){
		return 0;
	}
  	
  	return flagBoxs.length;
}



// ����grid������ʽ
// index table�����
// rowid ��0��ʼ
// flag=1:ѡ�С�2=���0=����
function modifyRowStyle( gridName, index, rowid, flag )
{
	if( rowid < 0 ){
		return;
	}
	
	// ȡ������Ϣ
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return;
	}
	
	// ����Ϣ
	rowid = rowid + 1;
	var	row = grid.getTable().rows[rowid];
	if( row == null ){
		return;
	}
	
	// ���������ȡ��ʾ��ʽ�ĺ�������ȡ��ʽ
	if( grid.onshow != null ){
		var	style = eval( grid.onshow.replace(/`/g, "'") );
		if( style != null && style != '' ){
			if( row.className != style ){
				row.className = style;
			}
			
			return;
		}
	}
	
	// ������ʽ
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


// ���õ�ǰ�е���ʽ:flag=1�����С�0=�뿪
function modifyCurrentRowStyle( gridName, index, rowId, flag )
{
	if( rowId < 0 ){
		return;
	}
	
	// ȡ������Ϣ
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return;
	}
	
	// ����Ϣ
	var	row = grid.getTable().rows[rowId+1];
	if( row == null ){
		return;
	}
	
	// �ж��Ƿ���Ҫ������ʽ
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
	
	// û��checkbox
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

// ��������ʽ
function grid_initGridStyle( )
{
	var	ii, jj;
	
	// ȡ���
	var	table = this.getTable();
	if( table == null ) return;
	table.width = '100%';
	
	// �ж��Ƿ��ӡ
	if( table.className == 'print' ) return;
	
	currentGrid = this;
	
	// ȡѡ�м�¼�ļ���
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
	
	// ���ñ��߿�
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
	table.style.tableLayout = "fixed";	//�̶����
	table.style.border = "1px solid RGB(207, 207, 254)";
	table.cellSpacing = 0;
	table.cellpadding = 0;
	// �Ƿ�����
	/*if( table.noWrap == 'true' ){
		table.style.tableLayout = 'fixed';
	}
	else{
		table.style.wordBreak = 'break-all';
	}*/
	
	// �߿����ɫ
	var borderColor = this.getBorderColor();
	
	// ����
	if( border > 0 ){
		var frameDiv = this.getFrameDiv();
		if( frameDiv != null ){
			if( frameDiv.style.height != null && frameDiv.style.height != '' ){
				frameDiv.style.borderBottom = border + 'px solid ' + borderColor;
			}
		}
		
		// ����
		var spDiv = this.getSpaceDiv();
		if( spDiv != null ){
			spDiv.style.display = 'none';
			spDiv.style.width = '100%';
			spDiv.style.fontSize = 1;
			spDiv.style.borderLeft = border + 'px solid ' + borderColor;
			spDiv.style.borderRight = border + 'px solid ' + borderColor;
		}
	}
	
	// ȡ������
	var rows = table.rows;
	var pageRow = rows.length - 1;
	
	//ȡ������
	var cols = table.rows[0].cells.length;
	//���и��л�ɫ
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
	
	// ����ƶ��Ķ���:onmouseover,onmouseout
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
	
	// ����ѡ�ж���:onclick
	var click = table._onclick;
	func = "selectRow('" + this.gridName + "', " + this.index + ", ";
	for( ii=0; ii<flagBoxs.length; ii++ ){
		// ȡ�еı��
		var row = flagBoxs[ii].parentNode.parentNode;
		rid = row.id.substring(4);
		// �ж���
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
		
		// ѡ��ť�Ķ���
		if( flagBoxs[ii].style.display != 'none' ){
			flagBoxs[ii].onclick = new Function(func + rid + ", " + this.rowselect + ")");
		}
	}
	
	// ������ʽ
	for( ii=0; ii<pageRow; ii++ ){
		// ���õ�ǰ�е���ʽ:flag=1�����С�0=�뿪
		modifyCurrentRowStyle( this.gridName, this.index, ii, 0 );
	}
	
	// ��ѡ��ʱ�Ķ���
	if( this.onselect != null && this.onselect != '' ){
		var flagBoxs = this.getFlagBoxs();
		for( ii=0; ii<flagBoxs.length; ii++ ){
			var	flag = flagBoxs[ii].checked;
			if( flag == true ){
				eval( this.onselect );
			}
		}
	}
	
	// ȫѡ�Ĳ���
	var selectAllBoxs = document.getElementsByName( this.gridName + ':_select-all' );
	if( selectAllBoxs.length > this.index ){
		selectAllBoxs[_gridIndex].onclick = function(){ selectAllRows(_gridName, _gridIndex); };
	}
	
	// �����Ⱥ͸߶�
	this.prepareTableRect()
	
	// ���ÿ���������ֶ�
	this.prepareSortItem();
	
	// ��ʼ����ť��״̬
	this.checkMenuItem();
	
	// ��ʾ���
	var f = this.getFrameDiv();
	if( f != null ){
		f.style.display = '';
	}
}

// ���¼���grid�Ŀ�Ⱥ͸߶�
function grid_prepareTableRect()
{
	// ȡ���
	var	table = this.getTable();
	
	// ���
	var startCell = 0;
	if( this.checkflag == 'true' ){
		startCell = 1;
	}
	
	// �����еĿ�Ⱥ��ܵĿ��
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
	
	// ����ÿ�еĿ��
	var remains = 0;	// ����Ĳ��֣����[remains>1]��������ǰ�Ŀ��
	if( totalWidth > 0 ){
		var lastWidth = 100;
		if( totalWidth < 99 || totalWidth > 101 ){
			table.width = (totalWidth < 110) ? '100%' : totalWidth + '%';
			
			// �����еĿ��
			var f = 100 / totalWidth;
			for( var ii=startCell; ii<frow.cells.length-1; ii++ ){
				var d = cellWidth[ii] * f;	// ��ȵı�������������
				var w = parseInt(d);
				
				// �������Ĳ���
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
	
	// �����߶�
	this.prepareTableHeight( totalWidth );
}

// �����߶�
function grid_prepareTableHeight( totalWidth )
{
	if( totalWidth == null || totalWidth <= 0 ){
		return;
	}
	
	// ȡ���
	var	table = this.getTable();
	
	// �ж��Ƿ��Ѿ���ʼ���������û�г�ʼ�����ȴ�
	if( table.clientHeight == 0 ){
		var g = this;
		setTimeout( function(){g.prepareTableHeight(totalWidth)}, 10 );
		return;
	}
	
	// �����Ⱥ͸߶�
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
			// ��λ����һ��ѡ�еļ�¼
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


// ���ÿ���������ֶ� he ��ť
function grid_prepareSortItem()
{
	// �ж��Ƿ�ִ���˲�ѯ
	var	table = this.getTable();
	if( table.rows.length <= 1 ){
		// ��û�в�ѯ
		return;
	}
	
	var startCol = (this.checkflag == 'false') ? 0 : 1;
	
	// ����������ֶ��б�
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
	
	// ȡ�����ֶε����� �� ����
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
	
	// ��ʽ��
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
	
	// �����ֶ�de����ť
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
	
	// ���������
	for( var ii=startCol; ii<ids.length; ii++ ){
		var itemName = ids[ii].id.substring( 3 );
		if( itemName == '@rowid' ){
			this.setRowid( ii );
			break;
		}
	}
}

// ���������
function grid_setRowid( columnId )
{
	var	table = this.getTable();
	var flag = this.getFlagBoxs();
	
	// ��ǰҳ �� ÿҳ�ļ�¼����
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

// �����ֶε�����ť
function grid_prepareCellButton( itemName, caption, sortedItem, sortedType )
{
	var str = caption;
	
	// �ж��Ƿ���������
	var flag = false;
	for( var ii=0; ii<this.sortItems.length; ii++ ){
		if( this.sortItems[ii] == itemName ){
			flag = true;
			break;
		}
	}
	
	// ����������
	if( flag == false ){
		return str;
	}
	
	// ͼ��
	str += '&nbsp;<img border="0" vspace="1" hspace="0" style="cursor:hand" src="';
	str += _browse.contextPath;
	str += '/script/img/';
	
	if( itemName == sortedItem ){
		str += (sortedType == 'asc') ? 'arrow_up.gif"' : 'arrow_down.gif"';
	}
	else{
		str += 'arrow_off.gif"';
	}
	
	// ����
	var onclick = "_sortTableByColumn('" + this.gridName + "', " + this.index + ", '" + itemName + "'); return false;";
	str += ' onclick="' + onclick + '"'
	
	return str + '>';
}

// �����ݱ�����ĺ���
function _sortTableByColumn( gridName, index, columnName )
{
	if( window.event.offsetY > 5 ){
		columnName += ' desc';
	}
	else{
		columnName += ' asc';
	}
	
	// �ύ����
	var	grid = getGridDefine(gridName, index);
	if( grid == null ){
		return;
	}
	
	// ��������ʽ
	var sortedItem = document.getElementsByName('attribute-node:' + gridName + '_sort-column');
	if( sortedItem.length > index ){
		sortedItem[index].value = columnName;
	}
	
	_formSubmit( null, '���ڶ����ݱ�ļ�¼������ȴ�... ...' );
}


// ȡ�߿���ɫ
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

// ����grid��ÿһ�е���ʽ
function setGridRowsStyle( gridName, index, rowNumber )
{
	for( var ii=0; ii<rowNumber; ii++ ){
		// ���õ�ǰ�е���ʽ:flag=1�����С�0=�뿪
		modifyCurrentRowStyle( gridName, index, ii, 0 );
	}
}


// ����ѡ�еļ�¼�ؼ���
// keyValue = null ʱ����ռ�¼
function _modifySelectedRecord( gridName, index, keyValue, flag )
{
	var	grid = getGridDefine(gridName, index);
	if( grid == null ) return;
	
	// ����ѡ�м�¼���ֶ�
	var	keyItem = grid.getSelectedKeyBox();
	if( keyItem == null ) return;
	
	// ���[fieldValues]
	grid.fieldValues.length = 0;
	
	// ��ռ�¼
	if( keyValue == null ){
		if( flag ){
			keyItem.value = '';
		}
	}
	// ����¼
	else if( grid.multiselect != "true" ){
		if( flag ){
			keyItem.value = keyValue;
		}
	}
	else{
		// ѡ�еļ�¼��Ϣ
		var	selectedKey = keyItem.value;
		var	keyList = selectedKey.split( ';' );
		
		// ���ӻ�ɾ��ѡ�еļ�¼
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
	
	// ����ѡ�еļ�¼��Ϣ
	page_setSelectedRecord( window, gridName, keyItem.value );
}


// ѡ����ʱ�Ķ���
// ���һ������:���ڲ�֧����ѡ��ʱ���������������ʱ�򣬵�ǰ�е�״̬�Ѿ������˱仯����Ҫ��ԭ
// ֧����ѡ��ʱ������������checkbox��״̬���������α仯�������������У�����һ�α仯
function selectRow( gridName, index, rowid, changeFlag )
{
	if( rowid < 0 ) return;
  	
  	// ȡ������Ϣ
	var	grid = getGridDefine( gridName, index );
	if( grid == null ) return;
	
	// û��chekcbox
	if( grid.checkflag == "" ) return;

	// checkbox
	var flagBoxs = grid.getFlagBoxs();
	if( flagBoxs[rowid] == null || flagBoxs[rowid].disabled == true ) return;
		
	// ��ǰform������
	currentFormName = grid.getFormName();
	currentGrid = grid;
	
	// ����ѡ��
	if( grid.multiselect == "true" ){
		// ���ڲ�֧����ѡ��ʱ���������������ʱ�򣬵�ǰ�е�״̬�Ѿ������˱仯����Ҫ��ԭ
		if( changeFlag == false ){
			if( flagBoxs[rowid].checked == true ){
				flagBoxs[rowid].checked = false;
			}
			else{
				flagBoxs[rowid].checked = true;
			}
		}
		
		if( window.event != null ){
			// �ж�SHIFT KEY
			if( window.event.shiftKey ){
				document.selection.empty();
				
				// ��ǰ���Ѿ�ѡ�񣬰������Ĳ���
				if( flagBoxs[rowid].checked == false ){
					if( grid.getSelectedRowNumber() == 1 ){
						// ȡѡ�е���
						var	selectedId = -1;
						for( var i=0; i<flagBoxs.length; i++ ){
							if( flagBoxs[i].checked == true ){
								selectedId = i;
								break;
							}
						}
						
						// ��������
						if( selectedId > rowid ){
							var startId = rowid + 1;
							var stopId = selectedId - 1;
						}
						else{
							var startId = selectedId + 1;
							var stopId = rowid - 1;
						}
						
						// �������䣬��������������
						for( var i = startId; i <= stopId; i ++ ){
							flagBoxs[i].checked = true;
							modifyRowStyle( gridName, index, i, 1 );
							_modifySelectedRecord( gridName, index, flagBoxs[i].value, true );
						}
					}
				} // flagBoxs[rowid].checked == false
			}
			else if( window.event.ctrlKey == false ){
				// ��ǰ���Ѿ�ѡ�񣬰������Ĳ���
				if( flagBoxs[rowid].checked == false && grid.supportCtrlKey ){
					// ��������������ѡ��
					for( var ii=0; ii<flagBoxs.length; ii++ ){
						if( ii != rowid && flagBoxs[ii].checked == true ){
							flagBoxs[ii].checked = false;
							modifyRowStyle( gridName, index, ii, 0 );
						}
					}
					
					// ��ձ���ѡ�м�¼��������
					_modifySelectedRecord( gridName, index, null, true );
				}
			}	// window.event.ctrlKey
		}
		
		// �����е�״̬
		if( flagBoxs[rowid].checked == true ){
			flagBoxs[rowid].checked = false;
		}
		else{
			flagBoxs[rowid].checked = true;
		}
		
		// �ı���ɫ
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
	
	// ����ѡ�еļ�¼
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
	
	// ���ð�ť��״̬
	checkAllMenuItem( );
	
	// ��ѡ��ʱ�Ķ���
	if( grid.onselect != null && grid.onselect != '' ){
		var	flag = flagBoxs[rowid].checked;
		eval( grid.onselect.replace(/`/g, "'") );
	}
	//�޸���ʽ
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


// ѡ��������
function selectAllRows( gridName, index )
{
	if( index == null ) index = 0;
	
	// ȡ������Ϣ
	var	grid = getGridDefine(gridName, index);
	if( grid == null || grid.checkflag == "" ) return;
	
	// û��chekcbox
	if( grid.checkflag == "" || grid.multiselect != "true" ) return;
	
	// ȡ��־
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



// ���б�ļ����и������ƻ�ȡ����
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

// ȡcell
// rowid = �кţ���1��ʼ����
// cellName = cell��property
function grid_getCell( rowid, cellName )
{
	// ȡ��
	var	table = this.getTable();
	if( table == null ){
		return null;
	}
	
	// ȡ��
	var	row = table.rows( "row_" + (rowid-1) );
	if( row == null ){
		return null;
	}
	
	// ȡ��
	var	cell = row.cells( "td_" + cellName );
	if( cell == null ){
		return null;
	}
	
	return cell;
}

// ȡcell������
// rowid = �кţ���1��ʼ����
// cellName = cell��property
function grid_getCellValue( rowid, cellName )
{
	// �ж��Ƿ��Ǳ�����
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
	
	// �ж��Ƿ���keylist
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
	
	// ȡ��
	var	cell = this.getCell( rowid, cellName );
	if( cell == null ){
		return null;
	}
	
	return cell.innerText;
}


// ���õ�Ԫ�������
// rowid = �кţ���1��ʼ����
// cellName = cell��property
function grid_setCellValue( rowid, cellName, cellValue )
{
	// �ж��Ƿ���keylist
	var	keys = this.keyList.split(',');
	for( i=0; i<keys.length; i++ ){
		if( keys[i] == cellName ){
			break;
		}
	}
	
	// key����
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
			
			// û���ҵ�
			if( flag == false ){
				value = value + '&' + cellName + '=' + cellValue;
			}
			
			// ɾ����һ���ַ�
			value = value.substring(1);
		}
		
		// ���ùؼ���
		flagBoxs[rowid-1].value = value;
	}
	
	// �����Ƿ�����
	var	fieldName = this.gridName + ':' + cellName;
	var	obj = document.getElementsByName( fieldName );
	if( obj.length > 0 ){
		form_setFieldValue( obj, rowid-1, cellValue );
	}
	else{
		// �������
		this.fieldValues.length = 0;
		
		// ȡ��
		var	cell = this.getCell( rowid, cellName );
		if( cell == null ){
			return;
		}
		
		// �޸�����
		cell.innerText = cellValue;
	}
}



// ȡ�Ѿ�ѡ����е�ָ���������
// ���ڷ����������ݣ�����ڳ�������Ҫʹ�ã�����ͨ������hidden��ķ�ʽ�������ݣ���ͨ�����������ȡ�Ѿ�ѡ�еļ�¼
// ���û�������򣬴�cell��ȡ����
function grid_getFieldValues( cellName )
{
	// �ж��Ƿ��Ѿ�ȡֵ
	for( var ii=0; ii<this.fieldValues.length; ii++ ){
		if( this.fieldValues[ii][0] == cellName ){
			return this.fieldValues[ii][1];
		}
	}
	
	// ���
	var num = 0;
	var selectedRows = new Array();
	
	// �Ƿ���TABLE
	var	table = this.getTable();
	if( table == null ){
		return selectedRows;
	}
	
	// ȡcheckbox
	var flagBoxs = this.getFlagBoxs();
	
	// ȡ��Ҫȡֵ����
	var	fieldName = this.gridName + ':' + cellName;
	var	fields = document.getElementsByName( fieldName );
	
	// ��������ȡ����
	if( fields.length > 0 ){
		var startId = this.getFirstRowIndex();
		
		// ��ȡ��Ķ�����Ϣ
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
	
	// ��cellȡ����
    for( var i=0; i<flagBoxs.length; i++ ){
	    if( flagBoxs[i].checked == true ){
	    	// û���ҵ�hidden�򣬴�cellȡ
	    	var value = this.getCellValue( i+1, cellName );
	    	if( value == null ){
	    		selectedRows[num++] = "";
	    	}
	    	else{
	    		selectedRows[num++] = trimSpace( value );
	    	}
	    }
	    
		// ���浽[fieldValues]
		this.fieldValues.push( [cellName, selectedRows] );
    }
	
	return selectedRows;
}

function grid_setFieldValues( cellName, cellValue )
{
	// �Ƿ���TABLE
	var	table = this.getTable();
	if( table == null ) return;
	
	// ��������
	var flagBoxs = this.getFlagBoxs();
	var	fieldName = this.gridName + ':' + cellName;
	var startId = this.getFirstRowIndex();
	for( var i=0; i<flagBoxs.length; i++ ){
		if( flagBoxs[i].checked == true ){
			setFormFieldValue( fieldName, startId+i, cellValue );
		}
	}
}

// ȡ���еļ�¼
function grid_getAllFieldValues( cellName )
{
	var allRows = new Array();
	
	// �Ƿ���TABLE
	var	table = this.getTable();
	if( table == null ){
		return allRows;
	}
	
	// ȡcheckbox
    var flagBoxs = this.getFlagBoxs();
	
	// ȡ��Ҫȡֵ����
	var	fieldName = this.gridName + ':' + cellName;
	var	fields = document.getElementsByName( fieldName );
	
	// ��������ȡ����
	if( fields.length > 0 ){
		var startId = this.getFirstRowIndex();
		
		// ��ȡ��Ķ�����Ϣ
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
	
	// û���ҵ�hidden�򣬴�cellȡ
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


// ȡFORM������
function grid_getFormName()
{
	if( this.formName == null ){
		// ��������
		this.formName = _getFormName( this.gridName, this.index );
	}
	
	return this.formName;
}

// ȡ����
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
	
	// �ж��Ƿ��ӽڵ�
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

// ȡ_selected-key
function grid_getSelectedKeyBox()
{
	var	fieldName = "attribute-node:" + this.gridName + "_selected-key";
	var keyBoxs = document.getElementsByName( fieldName );
	if( keyBoxs.length <= this.index ){
		return null;
	}
	
	return keyBoxs[this.index];
}

// ȡgrid��checkbox����
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
	
	// ����
	var flags = new Array();
	var table = tables[this.index];
	for( var ii=0; ii<flagBoxs.length; ii++ ){
		if( flagBoxs[ii].parentNode.parentNode.parentNode.parentNode == table ){
			flags.push( flagBoxs[ii] );
		}
	}
	
	return flags;
}

// ȡgrid�ĵ�һ����ͬ��grid�е��б�ţ�ͬ����grid�����кܶ��
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
	
	// ����
	var flags = new Array();
	var table = tables[this.index];
	for( var ii=0; ii<flagBoxs.length; ii++ ){
		if( flagBoxs[ii].parentNode.parentNode.parentNode.parentNode == table ){
			return ii;
		}
	}
	
	return 0;
}


// �������ĵĿ�ʼ����
function grid_beginBody( frameName, height, selectedKey, sortedColumn )
{
	// ���
	var str = "<tr><td>\n";
	str += "<div id='div_" + frameName + "' style='display:none;";
	
	// �߶�
	if( height != null && height != '' ){
		str += "height:" + height;
	}
	
	str += "' class='pages'>\n";
	document.write( str );
	
	// ѡ���ֶ�
	fz_addHiddenField( 'attribute-node:' + frameName + '_selected-key', selectedKey.trim() );
	
	// �����ֶ�
	fz_addHiddenField( 'attribute-node:' + frameName + '_sort-column', sortedColumn.trim() );
}


// ����ҳ��ĵ�����
function prepareNavigateBar( valign, gridName, pageRow, currentRow, totalRow, index )
{
 
	// ȡ���
	var frame = document.getElementById( 'frame_' + gridName );
	if( frame == null ){
		return;
	}
	
	// ȡindex
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
	
	// ��ʼ��
	if( frame.clientWidth == 0 ){
		setTimeout( function(){prepareNavigateBar(valign, gridName, pageRow, currentRow, totalRow, index);}, 10 );
		return;
	}
	
	// ȡ������Ϣ
	var grid = getGridDefine( gridName, index );
	if( grid == null ){
		return;
	}
	
	// ��¼����
	grid.totalRecord = totalRow;
	
	// �����������ݿ������������
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
	
	// ����ҳ��Ϣ
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
	
	// ��ҳ��
	var	totalPage;
	if( pageRow > 0 ){
		totalPage = Math.floor( (totalRow + pageRow - 1) / pageRow );
	}
	else{
		totalPage = 0;
	}
	
	// ��߼�¼������Ϣ
	var result = '<table width="100%"';
	if( valign == 'top' ){
		result = result + ' style="border-top-style:solid; border-top-width:1px"';
	}
	else{
		result = result + ' style="border-bottom-style:solid; border-bottom-width:1px"';
	}
	
	// ������ɫ
	var	table = grid.getTable();
	var bgColor = grid.getBorderColor();
	result = result + ' bordercolor="' + bgColor + '">\n<tr>\n<td valign=center>\n';
	
	// ��ǰҳ
	/*if( navType == 3 || navType == 5 ){
		result = result + '��';
	}
	else{
		result = result + '��ǰ��';
	}*/
	
	result = result + '<input id="' + gridName + '_curPage"' +
		' name="' + gridName + '_curPage" value="'+currentPage+'" type="hidden" />\n';
	
	// �ܹ�ҳ
	result = result + ' <input id="' + gridName + '_totalPage"' +
		' name="' + gridName + '_totalPage" value="'+totalPage+'" type="hidden" />\n';
	
	// ����¼����
	/*if( navType == 3 || navType == 5 ){
		result = result + ' ��¼��';
	}
	else{
		result = result + ' ��¼����';
	}*/
	result = result + '<div style="float:left;display:inline; height: 35px; width:60px; text-align:center;">'
	+'<span style="display:inline-block;width:100%; background: #e7e7f6;color: #888;font-size:16px;" id="' + gridName 
	+ '_totalRow"' + ' name="' + gridName + '_curRow">' + totalRow 
	+ '</span><span style="white-space:nowrap;color:#888;">��¼����</span></div>\n';
	result += "<div style='display:inline; height: 35px; text-align:center;float:left;'>&nbsp;&nbsp;";
	result += '<div title="��һҳ" onclick="'+prepageGoRecoed(gridName, index, '1', totalRow)+'" class="goFirst">&nbsp;</div>';
	result += '<div title="��һҳ" onclick="'+prepageGoRecoed(gridName, index, currentPage - 1, totalRow)+'" class="goPre">&nbsp;</div>';
	if(totalPage < 6){
		var clsName = 'goPage';
		for(var ii=1; ii<=totalPage; ii++){
			clsName = (ii==currentPage ? clsName+' nowP' : clsName);
			result += '<div title="��'+ ii +'ҳ" onclick="'+prepageGoRecoed(gridName, index, ii, totalRow)+'" class="'+clsName+'">'+ii+'</div>';
			clsName = 'goPage';
		}
	}else{
		var clsName = 'goPage';
		for(var ii=-4; ii<5; ii++){
			if((currentPage+ii)<=0 || (currentPage+ii)>totalPage)
				continue;
			clsName = (ii==0 ? clsName+' nowP' : clsName);
			result += '<div title="��'+ (currentPage+ii) +'ҳ" onclick="'+prepageGoRecoed(gridName, index, (currentPage+ii), totalRow)
			+'" class="'+clsName+'">'+(currentPage+ii)+'</div>';
			clsName = 'goPage';
		}
	}
	result += '<div title="��һҳ" onclick="'+prepageGoRecoed(gridName, index, currentPage + 1, totalRow)+'" class="goNext">&nbsp;</div>';
	result += '<div title="���һҳ" onclick="'+prepageGoRecoed(gridName, index, totalPage, totalRow)+'" class="goLast">&nbsp;</div></div></div>';
	// �����¼����
	result = result + '<input type="hidden" id="attribute-node:' + gridName + '_record-number"' + 
			' name="attribute-node:' + gridName + '_record-number" value="">\n';
			
	
	//ÿҳ��ʾ������
	//var func=' onchange="prepageGoRecoed('+gridName+', '+index+', '+currentPage+' , '+totalRow+')";';
	//alert(func);
    /*result = result + 'ÿҳ��ʾ<select id="customPageRowSeleted" onchange="' + prepageGoRecoed(gridName, index, 'go', totalRow) + '">\n';
    
	if(pageRow==10){
		result+='<option value=10 selected>10</option><option value=20>20</option><option value=30>30</option><option value=40>40</option><option value=50>50</option></select>��\n';
	}else if(pageRow==20){
		result+='<option value=10 >10</option><option value=20 selected>20</option><option value=30>30</option><option value=40>40</option><option value=50>50</option></select>��\n';
	}else if(pageRow==30){
		result+='<option value=10 selected>10</option><option value=20>20</option><option value=30 selected>30</option><option value=40>40</option><option value=50>50</option></select>��\n';
	}else if(pageRow==40){
		result+='<option value=10 selected>10</option><option value=20>20</option><option value=30>30</option><option value=40 selected>40</option><option value=50>50</option></select>��\n';
	}else if(pageRow==50){
		result+='<option value=10 selected>10</option><option value=20>20</option><option value=30>30</option><option value=40>40</option><option value=50 selected>50</option></select>��\n';
	}else{
	   //update dc2 zhongxiaoqi
	   result+='<option value='+pageRow+' selected>'+pageRow+'</option><option value=10>10</option><option value=20>20</option><option value=30>30</option><option value=40>40</option><option value=50>50</option></select>��\n'; 
	}*/
	 
	// ѡ��ҳ
	/*if(totalPage<=0 && totalPage > 0 ){
		result = result + ' ת�� <select style="color:blue" id="' + gridName + '_selectPage"' + 
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
	 	
		result = result + '</select> ҳ\n';
	}
	else{
		result = result + ' ת�� <input type="text" style="color:blue" id="' + gridName + '_selectPage"' + 
			' name="' + gridName + '_selectPage" value="' + currentPage + '" onkeydown="if(event.keyCode == 13){' +
			prepageGoRecoed(gridName, index, 'go', totalRow) + '}" size="3" style="border: 1px solid #7f9db9"> ҳ\n';
	}*/
	
	
	// Comment By WeiQiang
	// ÿҳ�ļ�¼����
	/*if( navType > 3 ){
		result = result + 
			'<input type="hidden" style="color:blue" id="attribute-node:' + gridName + '_page-row"' +
			' name="attribute-node:' + gridName + '_page-row" value="' + pageRow + '">';
	}
	else{
		
		if( navType == 1 ){
			result = result + ' ÿҳ��ʾ��';
		}
		else{
			result = result + ' ÿҳ��';
		}
		
		result = result + 
			'<input type="text" style="color:blue" id="attribute-node:' + gridName + '_page-row"' +
			' name="attribute-node:' + gridName + '_page-row" value="' + pageRow + '"' +
			' onkeydown="if(event.keyCode == 13){' + prepageGoRecoed(gridName, index, 'go', totalRow) + '}" size="3" style="border: 1px solid #7f9db9">';
		
		if( navType == 1 ){
			result = result + ' ����Ϣ';
		}
		else{
			result = result + ' ��';
		}
		
		
	}*/
	// Comment end, WeiQiang
	
	
	// ÿҳ�ļ�¼����
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
	
	// ����
	//result = result + '</td>\n';
	
	// ��ť��һҳ
	/*result = result + '<td align="right">\n' + 
		'<button style="WIDTH:25px" class="goFirst" onclick="' + prepageGoRecoed(gridName, index, '1', totalRow) +
		'" class="menu" title="��һҳ"';
	if( currentPage <= 1 ){
	 	result = result + ' disabled';
	}
	
	result = result + '></button>&nbsp;\n';
	*/
	// ��һҳ
	/*result = result + '<button class="goPre" style="WIDTH:25px" onclick="' + prepageGoRecoed(gridName, index, currentPage - 1, totalRow) +
		'" class="menu" title="��һҳ"';
	if( currentPage <= 1 ){
	 	result = result + ' disabled';
	}
	
	result = result + '></button>&nbsp;\n';
	*/
	// ��һҳ
	/*result = result + '<button class="goNext" style="WIDTH:25px" onclick="' + prepageGoRecoed(gridName, index, currentPage + 1, totalRow) +
		'" class="menu" title="��һҳ"';
	if( currentPage >= totalPage && totalPage >= 0 ){
	 	result = result + ' disabled';
	}
	
	result = result + '></button>&nbsp;\n';
	*/
	// ���һҳ
	/*result = result + '<button class="goLast" style="WIDTH:25px" onclick="' + prepageGoRecoed(gridName, index, totalPage, totalRow) +
		'" class="menu" title="���һҳ"';
	if( currentPage >= totalPage ){
	 	result = result + ' disabled';
	}
	
	result = result + '></button>\n';
	*/
	// ��������:��ǰ��һ��
	result = result + '<input type="hidden" id="attribute-node:' + gridName + '_start-row\"' +
		' name="attribute-node:' + gridName + '_start-row\"' +
		' value="' + currentRow + '">\n';
	
	// ����������
	result = result + '</td></tr></table>\n';
	
	// ���뵼����
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
	// �����Gridû������ʱ��Ĵ���
	/** ���1������Grid��������Grid����ʾ��ʾdiv�����Grid�����ص�ʱ��ͻ�ֻ��һ����ʾ��Ϣ��
	if (totalRow.toString() == "0"){
		if (pageRow.toString() != "0" && pageRow.toString() != "-1"){
			// alert("û�в�ѯ�����ݣ���������������²�ѯ��");
			var hint = document.createElement("div");
			frame.parentNode.appendChild(hint);
			var hintWindowLayer = document.getElementById("hintWindowLayer");
			hint.innerHTML = "��ʾ��û��������ݣ�";
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
	/** ���2������Grid��������ÿ��Gridǰ����ʾû�����ݡ�����֪����ʲô���ݱ������ˣ�
	if (totalRow.toString() == "0"){
		if (pageRow.toString() != "0" && pageRow.toString() != "-1"){
			// alert("û�в�ѯ�����ݣ���������������²�ѯ��");
			var hint = document.createElement("div");
			hint.innerHTML = "��ʾ��û��������ݣ�";
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
	/** ���3����ʾGrid�����ǲ���ʾ��ҳ������������ʾû���ҵ�������ݵ���ʾ��Ϣ 
	*/
	if (totalRow.toString() == "0"){
		var isShowNoDataSign = false;
		if (pageRow.toString() == "0"){
			// �����ѯҳ�浫����δ��ѯ�������ֱ������
			frame.style.display = "none";
			// ����ҵ���ʾ��Ϣ����ʾ��ʾ��Ϣ(�ȴ���ҳ�������ɺ�ִ��)
			_browse.execute(function(){
				var queryHelpDiv = document.getElementById("queryHelpDiv");
				if (queryHelpDiv){
					queryHelpDiv.style.display = "block";
				}
				queryHelpDiv = null;
			});
		}else if (pageRow.toString() == "-1"){
			// ����ҳ��ʾ��ʱ�������¼��Ϊ0����ʾ��ʾ��Ϣ(��ʱû���ܼ�¼������Ҫ�Ȼ�ȡ)
			var totalRecord = grid.getFlagBoxs().length;
			if (totalRecord == 0){
				isShowNoDataSign = true;
			}
		}else{
			// ���ط�ҳ��������ʾ��ʾ��Ϣ
			myCell.style.display = "none";
			isShowNoDataSign = true;
		}
		if (isShowNoDataSign){
			var myInfoCell = myRow.parentNode.insertRow().insertCell();
            myInfoCell.innerHTML = "<span id='noDataList' style='width:100%; text-align:center; color:#3333FF; font-size:12pt;'>��ʾ��û��������ݣ�</span>";
		}
		
	}
}


function prepageGoRecoed( gridName, index, cmd, totalRow )
{
	return "grid_goRecord('" + gridName + "', " + index + ", " + totalRow + ", '" + cmd + "'); return false;";
}

// ȡ���ԵĶ���
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

// �������ԵĶ���
function setAttributeObject( gridName, index, attrName, ss )
{
	var objs = document.getElementsByName( 'attribute-node:' + gridName + '_' + attrName );
	if( objs.length > index ){
		objs[index].value=ss;
	}
}

// ҳ����ת
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
	
	// ȱʡ��������һ��ҳ��
	if( nextPage == null ){
		nextPage = _prePageName;
	}
	
	// ȡ�������ݱ�־
	var addr = _addHrefParameter( nextPage, 'inner-flag:back-flag', 'true' );
 	// alert("ȫ·��:" + addr);
 	
 	// �򿪴��ڵ�����
	if( openWindowType == 'subframe' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'subframe' );
	}else if ( openWindowType == 'new-window' ){
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'new-window' );
	}else{
		addr = _addHrefParameter( addr, 'inner-flag:open-type', 'window' );
	}
	
	// ����ȫ·��
	addr = _browse.resolveURL( addr );
	// alert("ȫ·��:" + addr);
 	// ת������
 	addr = page_setSubmitData( window, addr );
	window.location = addr;
  
}
