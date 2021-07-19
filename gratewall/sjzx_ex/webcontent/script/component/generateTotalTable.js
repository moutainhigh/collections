String.prototype.setAttribute = function(param, paramValue){
	eval( "this." + param + "='" + paramValue + "'" );
}

String.prototype.getAttribute = function(param){
	return eval( "this." + param);
}

/**
 * �������յĽ���б�
 */
var generateTotalTable = function(config){
	if (!config.id){
        alert("û�д��������ID������json���ò���id���ݹ�����");
        return;
    }
    
	if (!config.parentContainer){
		alert("û�ж��常������ID������json����parentContainer�������ݹ�����");
		return;
	}
	
	if (!config.columnSelect){
		alert("û�ж����ֶ��������ID������json����columnSelect�������ݹ�����");
		return;
	}
	
	if (!config.tableSelect){
		alert("û�ж�����������ID������json����tableSelect�������ݹ�����");
		return;
	}
	
	if (!config.tableParamOnColumn){
		alert("û�ж����ֶ��������ϵı�����б�����json����tableParamOnColumn�������ݹ�����");
		return;
	}
	
	if (!config.sysParamOnTable){
		alert("û�ж�����������ϵ���������б�����json����sysParamOnTable�������ݹ�����");
		return;
	}
	
	this.parentContainer = config.parentContainer;
	this.id = config.id;
	this.sysArray = new Array;
	this.columnSelectId		= config.columnSelect;
	this.tableSelectId		= config.tableSelect;
	// �Ƿ���Ҫ��������
	this.needConnectTable = config.connectConditionTable ? true : false;
	this.connectConditionTable = config.connectConditionTable;
	// �Ƿ���Ҫ��ѯ����
	this.needQueryTable = config.queryConditionTable ? true : false;
	this.queryConditionTable = config.queryConditionTable;
	// �Ƿ���Ҫ�ȶ�����
	this.needCompareTable = config.compareTable ? true : false;
	this.compareTable = config.compareTable;
	
	this.columnParamArray	= config.columnParamOnColumn;
	if ( this.columnParamArray.constructor != Array || this.columnParamArray.length < 1){
		alert("�����columnParamOnColumn����һ���Ϸ�������");
		return;
	}
	this.columnParamLength	= this.columnParamArray.length;
	
	this.tableParamArray	= config.tableParamOnColumn;
	if ( this.tableParamArray.constructor != Array || this.tableParamArray.length < 1){
		alert("�����tableParamOnColumn����һ���Ϸ�������");
		return;
	}
	this.tableParamLength	= this.tableParamArray.length;
	this.sysParamArray		= config.sysParamOnTable;
	if ( this.sysParamArray.constructor != Array || this.sysParamArray.length < 1){
		alert("�����sysParamOnTable����һ���Ϸ�������");
		return;
	}
	this.sysParamLength	= this.sysParamArray.length;
	
	this.columnSelect = document.getElementById(this.columnSelectId);
	if ( !this.columnSelect ){
		alert("û���ҵ�IDΪ����" + columnSelect + "���Ķ���");
	}
	this.tableSelect = document.getElementById(this.tableSelectId);
	if ( !this.columnSelect ){
		alert("û���ҵ�IDΪ����" + tableSelect + "���Ķ���");
	}
	
	this.init();	
};

generateTotalTable.prototype = {
	init : function(){
		var p = this;
		
		// ѭ���ֶ�
		var columnSelectOptions = p.columnSelect.options;
		for (var tempIndex = 0; tempIndex < columnSelectOptions.length; tempIndex++){
			var tempColumnOption = columnSelectOptions[tempIndex];
			
			var columnId	= tempColumnOption.value;
			var tableId		= p.getTableId( columnId );
			var sysId		= p.getSysId( tableId );
			
			var columnItem = new String( columnId );
			var testTxt = "columnItem." + p.columnParamArray[1] + "=" + tempColumnOption.text;

			columnItem.setAttribute(p.columnParamArray[1], tempColumnOption.text);
			// ����columnItem�ĸ��Ӳ���
			for (var columnParamIndex = 2; columnParamIndex < p.columnParamLength; columnParamIndex++){
				var columnParam = p.columnParamArray[columnParamIndex];
				var columnParamValue = tempColumnOption.getAttribute( columnParam );
				if ( columnParamValue ){
					columnItem.setAttribute(columnParam, columnParamValue);
				}				
				columnParam = null;
				columnParamValue = null;
			}
			
			var tableItem = new String( tableId );
			// ����tableItem�ĸ��Ӳ���
			for (var tableParamIndex = 1; tableParamIndex < p.tableParamLength; tableParamIndex++){
				var tableParam = p.tableParamArray[tableParamIndex];
				var tableParamValue = tempColumnOption.getAttribute( tableParam );
				if ( tableParamValue ){
					tableItem.setAttribute(tableParam, tableParamValue);
				}				
				tableParam = null;
				tableParamValue = null;
			}
			
			var sysItem = new String( sysId );
			// ����sysItem�ĸ��Ӳ���(�Ȳ�����sysItem�ĸ��Ӳ�������ѭ��table��ʱ�����)
			
			var sysArrayPosition = existInArray(this.sysArray, sysId);
			// sysArray������ǰcolumn��sysid
			if ( sysArrayPosition != -1 ){
				var tableArray = this.sysArray[sysArrayPosition].tableArray ? this.sysArray[sysArrayPosition].tableArray : new Array;
				var tableArrayPosition = existInArray(tableArray, tableId);
				if ( tableArrayPosition != -1 ){ // tableArray������ǰcolumn��tableid
					// ������ֶ�����Ӧ�ı��Ѿ����ڣ���ֱ���ڸ��ֶ����������һ������
					var columnArray = tableArray[tableArrayPosition].columnArray ? tableArray[tableArrayPosition].columnArray : new Array;
					columnArray.push( columnItem );
					tableArray[tableArrayPosition].columnArray = columnArray;
					columnArray = null;
				}else{ // tableArray��������ǰcolumn��tableid
					tableItem.columnArray = new Array;
					tableItem.columnArray.push( columnItem )
					tableArray.push( tableItem );
				}
				this.sysArray[sysArrayPosition].tableArray = tableArray;
				tableArrayPosition = null;
			}else{ // sysArray��������ǰcolumn��sysid
				tableItem.columnArray = new Array;
				tableItem.columnArray.push( columnItem );
				sysItem.tableArray = new Array;
				sysItem.tableArray.push( tableItem );
				this.sysArray.push( sysItem );
			}
			
			sysArrayPosition = null;
			
			columnId	= null;
			tableId		= null;
			sysId		= null;
			columnItem	= null;
			tableItem	= null;
			sysItem		= null;
		}
		
		// ѭ����
		var tableSelectOptions = p.tableSelect.options;
		for (var tempTableIndex = 0; tempTableIndex < tableSelectOptions.length; tempTableIndex++){
			var tempTableOption = tableSelectOptions[tempTableIndex];
			var tableId		= tempTableOption.value;
			var sysId		= p.getSysId( tableId );
			
			var tableItem = new String( tableId );
			tableItem.setAttribute(p.tableParamArray[1], tempTableOption.text);
			// ѭ������tableItem�ĸ�������
			for (var tableParamIndex = 2; tableParamIndex < p.tableParamLength; tableParamIndex++){
				var tableParam = p.tableParamArray[tableParamIndex];
				var tableParamValue = tempTableOption.getAttribute( tableParam );
				if ( tableParamValue ){
					tableItem.setAttribute(tableParam, tableParamValue);
				}
				tableParam = null;
				tableParamValue = null;
			}
			
			var sysItem = new String( sysId );
			// ѭ������sysItem�ĸ�������				
			for (var sysParamIndex = 1; sysParamIndex < p.tableParamLength; sysParamIndex++){
				var sysParam = p.sysParamArray[sysParamIndex];
				var sysParamValue = tempTableOption.getAttribute( sysParam );
				if (sysParamValue){
					sysItem.setAttribute(sysParam, sysParamValue);
				}				
				sysParam = null;
				sysParamValue = null;
			}
			
			var sysArrayPosition = existInArray(this.sysArray, sysId);
			// sysArray������ǰcolumn��sysid(�ñ����ֶ��Ѿ���ѡΪ��ѯ���������Ѿ���ӹ��ñ�)
			if ( sysArrayPosition != -1 ){
				var tableArray = this.sysArray[sysArrayPosition].tableArray ? this.sysArray[sysArrayPosition].tableArray : new Array;
				var tableArrayPosition = existInArray(tableArray, tableId);
				if ( tableArrayPosition != -1 ){ // tableArray������ǰcolumn��tableid
					// ����ñ��Ѿ����ڣ������ٴ����
				}else{ // tableArray��������ǰcolumn��tableid
					tableArray.push( tableItem );
				}
				
				sysItem.tableArray = tableArray;
				this.sysArray[sysArrayPosition] = sysItem;
				// this.sysArray[sysArrayPosition].tableArray = tableArray;
				tableArrayPosition = null;
			}else{ // sysArray��������ǰcolumn��sysid(�ñ�û���ֶα�ѡΪ��ѯ����)
				sysItem.tableArray = new Array;
				sysItem.tableArray.push( tableItem );
//				// ѭ������sysItem�ĸ�������				
//				for (var sysParamIndex = 1; sysParamIndex < p.tableParamLength; sysParamIndex++){
//					var sysParam = p.sysParamArray[sysParamIndex];
//					var sysParamValue = tempTableOption.getAttribute( sysParam );
//					sysItem.setAttribute(sysParam, sysParamValue);
//					sysParam = null;
//					sysParamValue = null;
//				}
				this.sysArray.push( sysItem );
			}
			sysArrayPosition = null;
		}
		
	},
	
	write : function(){
		var p = this;
		var divContainer = document.getElementById(p.parentContainer);
		divContainer.innerHTML = "";
		var tempSysTable = document.createElement("table");
		tempSysTable.className = 'frame-body';
		tempSysTable.border = "0";
		tempSysTable.cellSpacing = "0";
		tempSysTable.cellPadding = "0";
		tempSysTable.width = "95%";
		tempSysTable.align = "center";
		var firstRow = tempSysTable.insertRow();
		firstRow.className = 'title-row';
		var firstCell = firstRow.insertCell();
		firstCell.innerHTML = "<table cellspacing='0' cellpadding='0' width='100%' style='line-height:28px;'><tr><td class='leftTitle'></td><td class='secTitle'>��ѡ���������</td><td class='rightTitle'></td></tr></table>";
		firstCell = null;
		firstRow = null;
		divContainer.appendChild( tempSysTable );
		for (var i = 0 ; i < p.sysArray.length; i ++){
			//alert( "���ڽ����������1��" + p.sysArray[i] );
			var tempRow = tempSysTable.insertRow();
			tempRow.className = 'grid-headrow';
			var tempCell = tempRow.insertCell();
			if (p.sysArray[i].getAttribute("ztName")) {
				tempCell.innerHTML = p.sysArray[i].getAttribute("ztName");
			}
			else{
				tempCell.innerHTML = "����δ����";
			}
			//alert(p.sysArray[i].getAttribute("ztNo"));
			//����ʾ�����
			/*if ( p.sysArray[i].getAttribute("ztNo") ){
				tempCell.innerHTML += "(" + p.sysArray[i].getAttribute("ztNo") + ")";
			}*/
			
			if ( p.sysArray[i].tableArray ){
				for ( var j = 0; j < p.sysArray[i].tableArray.length; j++ ){
					// alert( "���ڽ������⡾" + p.sysArray[i] + "��������ı�" + p.sysArray[i].tableArray[j] );
					tempRow = tempSysTable.insertRow();
					tempRow.className = 'framerow';
					tempCell = tempRow.insertCell();
					if(j%2 == 0){
						tempCell.className = 'even2';
					}else{
						tempCell.className = 'even1';
					}					
					//var cellHtml = "<font color='red'>" + p.sysArray[i].tableArray[j].getAttribute("tblNameCn") + "(" + p.sysArray[i].tableArray[j].getAttribute("tblName") + ")" + "</font>��";
				   var cellHtml = "<font color='red'>δѡ���</font>";	
				   if(p.sysArray[i].tableArray[j].getAttribute("tblNameCn")) {
						cellHtml = "<font color='red'>" + p.sysArray[i].tableArray[j].getAttribute("tblNameCn")  + "</font>��";										
						if ( p.sysArray[i].tableArray[j].columnArray ){
							for (var k = 0; k < p.sysArray[i].tableArray[j].columnArray.length; k ++){
								// alert( "���ڽ������⡾" + p.sysArray[i] + "��������ı�" + p.sysArray[i].tableArray[j]+"��������ֶΣ�" + p.sysArray[i].tableArray[j].columnArray[k]);
								cellHtml += p.sysArray[i].tableArray[j].columnArray[k].getAttribute("colNameCn") + "(" + p.sysArray[i].tableArray[j].columnArray[k].getAttribute("colName") + ")";
								//cellHtml += p.sysArray[i].tableArray[j].columnArray[k].getAttribute("colNameCn") + "";
								if ( k != p.sysArray[i].tableArray[j].columnArray.length - 1){
									cellHtml += ", ";
								}
							}
						}else{
							cellHtml += "(�ñ���δѡ���ֶ�)";
						}
					}
					tempCell.innerHTML = cellHtml;
				}				
			}
			tempRow = null;
			tempCell = null;
		}
		tempSysTable = null;
		
		// ��ʼдconnectTable
		if ( p.needConnectTable ){
			var connTable = document.getElementById( p.connectConditionTable );
			if ( !connTable ){
				alert("û���ҵ�IDΪ��" +  p.connectConditionTable  + "���Ķ���");
				return;
			}
			
			var rows = connTable.rows;
			if (rows.length > 0){
				var tempConnectTable = document.createElement("table");
				tempConnectTable.className = 'frame-body';
				tempConnectTable.border = "0";
				tempConnectTable.cellSpacing = "0";
				tempConnectTable.cellPadding = "0";
				tempConnectTable.width = "95%";
				tempConnectTable.align = "center";
				var tempConnectTableRow = tempConnectTable.insertRow();
				tempConnectTableRow.className = 'title-row';
				var tempConnectTableCell = tempConnectTableRow.insertCell();
				tempConnectTableCell.innerHTML = "<table cellspacing='0' cellpadding='0' width='100%'><tr><td class='leftTitle'></td><td class='secTitle'>��������</td><td class='rightTitle'></td></tr></table>";
				tempConnectTableCell = null;
				tempConnectTableRow = null;
				
				for (var tempIndex = 0; tempIndex < rows.length; tempIndex ++){
					if(tempIndex%2 == 0){
						tempConnectTable.insertRow().appendChild( rows[tempIndex].cells[0].cloneNode(true) ).className = "even2";
					}else{
						tempConnectTable.insertRow().appendChild( rows[tempIndex].cells[0].cloneNode(true) ).className = "even1";
					}						
				}
				divContainer.appendChild( tempConnectTable );
				tempConnectTable = null;
			}
			
			connTable = null;
		}
		// ��ʼдqueryTable
		if ( p.needQueryTable ){
			var queryTable = document.getElementById( p.queryConditionTable );
			if ( !queryTable ){
				alert("û���ҵ�IDΪ��" +  p.queryConditionTable  + "���Ķ���");
				return;
			}
			
			var rows = queryTable.rows;
			if (rows.length > 0){
				var tempConnectTable = document.createElement("table");
				tempConnectTable.className = 'frame-body';
				tempConnectTable.border = "0";
				tempConnectTable.cellSpacing = "0";
				tempConnectTable.cellPadding = "0";
				tempConnectTable.width = "95%";
				tempConnectTable.align = "center";
				var tempConnectTableRow = tempConnectTable.insertRow();
				tempConnectTableRow.className = 'title-row';
				var tempConnectTableCell = tempConnectTableRow.insertCell();
				tempConnectTableCell.innerHTML = "<table cellspacing='0' cellpadding='0' width='100%'><tr><td class='leftTitle'></td><td class='secTitle'>��ѯ����</td><td class='rightTitle'></td></tr></table>";
				tempConnectTableCell = null;
				tempConnectTableRow = null;
				
				for (var tempIndex = 0; tempIndex < rows.length; tempIndex ++){
					if(tempIndex%2 == 0){
						tempConnectTable.insertRow().appendChild( rows[tempIndex].cells[0].cloneNode(true) ).className = "even2";
					}else{
						tempConnectTable.insertRow().appendChild( rows[tempIndex].cells[0].cloneNode(true) ).className = "even1";
					}	
				}
				divContainer.appendChild( tempConnectTable );
				tempConnectTable = null;
			}
			
			queryTable = null;
		}
		
		// ��ʼд�Ƚ�����
		if ( p.needCompareTable ){
			var compareTable = document.getElementById( p.compareTable );
			if ( !compareTable ){
				alert("û���ҵ�IDΪ��" +  p.compareTable  + "���Ķ���");
				return;
			}
			
			var rows = compareTable.rows;
			if (rows.length > 0){
				var tempConnectTable = document.createElement("table");
				tempConnectTable.className = 'frame-body';
				tempConnectTable.border = "0";
				tempConnectTable.cellSpacing = "0";
				tempConnectTable.cellPadding = "0";
				tempConnectTable.width = "95%";
				tempConnectTable.align = "center";
				var tempConnectTableRow = tempConnectTable.insertRow();
				tempConnectTableRow.className = 'title-row';
				var tempConnectTableCell = tempConnectTableRow.insertCell();
				tempConnectTableCell.innerHTML = "<table cellspacing='0' cellpadding='0' width='100%'><tr><td class='leftTitle'></td><td class='secTitle'>�Ƚ�����</td><td class='rightTitle'></td></tr></table>";
				tempConnectTableCell = null;
				tempConnectTableRow = null;
				
				for (var tempIndex = 0; tempIndex < rows.length; tempIndex ++){
					if(tempIndex%2 == 0){
						tempConnectTable.insertRow().appendChild( rows[tempIndex].cells[0].cloneNode(true) ).className = "even2";
					}else{
						tempConnectTable.insertRow().appendChild( rows[tempIndex].cells[0].cloneNode(true) ).className = "even1";
					}
				}
				divContainer.appendChild( tempConnectTable );
				tempConnectTable = null;
			}
			
			queryTable = null;
		}
		
		divContainer = null;
	},
	
	getTableId : function( columnId ){
		var p = this;
		var options = p.columnSelect.options;
		for ( var i = 0; i < options.length; i++ ){
			if(options[i].value == columnId){
				return options[i].getAttribute(p.tableParamArray[0]);
			}
		}
		alert("ϵͳ���ִ���!");
		return null;
	},
	
	getSysId : function( tableId ){
		var p = this;
		var options = p.tableSelect.options;
		for ( var i = 0; i < options.length; i++ ){
			if(options[i].value == tableId){
				return options[i].getAttribute(p.sysParamArray[0]);
			}
		}
		alert("ϵͳ���ִ���!");
		return null;
	}
};