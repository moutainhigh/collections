String.prototype.setAttribute = function(param, paramValue){
	eval( "this." + param + "='" + paramValue + "'" );
}

String.prototype.getAttribute = function(param){
	return eval( "this." + param);
}

/**
 * 生成最终的结果列表
 */
var generateTotalTable = function(config){
	if (!config.id){
        alert("没有传入组件的ID！请在json中用参数id传递过来！");
        return;
    }
    
	if (!config.parentContainer){
		alert("没有定义父容器的ID！请在json中用parentContainer参数传递过来！");
		return;
	}
	
	if (!config.columnSelect){
		alert("没有定义字段下拉框的ID！请在json中用columnSelect参数传递过来！");
		return;
	}
	
	if (!config.tableSelect){
		alert("没有定义表下拉框的ID！请在json中用tableSelect参数传递过来！");
		return;
	}
	
	if (!config.tableParamOnColumn){
		alert("没有定义字段下拉框上的表参数列表！请在json中用tableParamOnColumn参数传递过来！");
		return;
	}
	
	if (!config.sysParamOnTable){
		alert("没有定义表下拉框上的主题参数列表！请在json中用sysParamOnTable参数传递过来！");
		return;
	}
	
	this.parentContainer = config.parentContainer;
	this.id = config.id;
	this.sysArray = new Array;
	this.columnSelectId		= config.columnSelect;
	this.tableSelectId		= config.tableSelect;
	// 是否需要连接条件
	this.needConnectTable = config.connectConditionTable ? true : false;
	this.connectConditionTable = config.connectConditionTable;
	// 是否需要查询条件
	this.needQueryTable = config.queryConditionTable ? true : false;
	this.queryConditionTable = config.queryConditionTable;
	// 是否需要比对条件
	this.needCompareTable = config.compareTable ? true : false;
	this.compareTable = config.compareTable;
	
	this.columnParamArray	= config.columnParamOnColumn;
	if ( this.columnParamArray.constructor != Array || this.columnParamArray.length < 1){
		alert("传入的columnParamOnColumn不是一个合法的数组");
		return;
	}
	this.columnParamLength	= this.columnParamArray.length;
	
	this.tableParamArray	= config.tableParamOnColumn;
	if ( this.tableParamArray.constructor != Array || this.tableParamArray.length < 1){
		alert("传入的tableParamOnColumn不是一个合法的数组");
		return;
	}
	this.tableParamLength	= this.tableParamArray.length;
	this.sysParamArray		= config.sysParamOnTable;
	if ( this.sysParamArray.constructor != Array || this.sysParamArray.length < 1){
		alert("传入的sysParamOnTable不是一个合法的数组");
		return;
	}
	this.sysParamLength	= this.sysParamArray.length;
	
	this.columnSelect = document.getElementById(this.columnSelectId);
	if ( !this.columnSelect ){
		alert("没有找到ID为：【" + columnSelect + "】的对象");
	}
	this.tableSelect = document.getElementById(this.tableSelectId);
	if ( !this.columnSelect ){
		alert("没有找到ID为：【" + tableSelect + "】的对象");
	}
	
	this.init();	
};

generateTotalTable.prototype = {
	init : function(){
		var p = this;
		
		// 循环字段
		var columnSelectOptions = p.columnSelect.options;
		for (var tempIndex = 0; tempIndex < columnSelectOptions.length; tempIndex++){
			var tempColumnOption = columnSelectOptions[tempIndex];
			
			var columnId	= tempColumnOption.value;
			var tableId		= p.getTableId( columnId );
			var sysId		= p.getSysId( tableId );
			
			var columnItem = new String( columnId );
			var testTxt = "columnItem." + p.columnParamArray[1] + "=" + tempColumnOption.text;

			columnItem.setAttribute(p.columnParamArray[1], tempColumnOption.text);
			// 设置columnItem的附加参数
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
			// 设置tableItem的附加参数
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
			// 设置sysItem的附加参数(先不设置sysItem的附加参数，在循环table的时候加入)
			
			var sysArrayPosition = existInArray(this.sysArray, sysId);
			// sysArray包含当前column的sysid
			if ( sysArrayPosition != -1 ){
				var tableArray = this.sysArray[sysArrayPosition].tableArray ? this.sysArray[sysArrayPosition].tableArray : new Array;
				var tableArrayPosition = existInArray(tableArray, tableId);
				if ( tableArrayPosition != -1 ){ // tableArray包含当前column的tableid
					// 如果该字段所对应的表已经存在，则直接在该字段数组上添加一个数据
					var columnArray = tableArray[tableArrayPosition].columnArray ? tableArray[tableArrayPosition].columnArray : new Array;
					columnArray.push( columnItem );
					tableArray[tableArrayPosition].columnArray = columnArray;
					columnArray = null;
				}else{ // tableArray不包含当前column的tableid
					tableItem.columnArray = new Array;
					tableItem.columnArray.push( columnItem )
					tableArray.push( tableItem );
				}
				this.sysArray[sysArrayPosition].tableArray = tableArray;
				tableArrayPosition = null;
			}else{ // sysArray不包含当前column的sysid
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
		
		// 循环表
		var tableSelectOptions = p.tableSelect.options;
		for (var tempTableIndex = 0; tempTableIndex < tableSelectOptions.length; tempTableIndex++){
			var tempTableOption = tableSelectOptions[tempTableIndex];
			var tableId		= tempTableOption.value;
			var sysId		= p.getSysId( tableId );
			
			var tableItem = new String( tableId );
			tableItem.setAttribute(p.tableParamArray[1], tempTableOption.text);
			// 循环设置tableItem的附加属性
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
			// 循环设置sysItem的附加属性				
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
			// sysArray包含当前column的sysid(该表有字段已经被选为查询条件，或已经添加过该表)
			if ( sysArrayPosition != -1 ){
				var tableArray = this.sysArray[sysArrayPosition].tableArray ? this.sysArray[sysArrayPosition].tableArray : new Array;
				var tableArrayPosition = existInArray(tableArray, tableId);
				if ( tableArrayPosition != -1 ){ // tableArray包含当前column的tableid
					// 如果该表已经存在，则不用再次添加
				}else{ // tableArray不包含当前column的tableid
					tableArray.push( tableItem );
				}
				
				sysItem.tableArray = tableArray;
				this.sysArray[sysArrayPosition] = sysItem;
				// this.sysArray[sysArrayPosition].tableArray = tableArray;
				tableArrayPosition = null;
			}else{ // sysArray不包含当前column的sysid(该表没有字段被选为查询条件)
				sysItem.tableArray = new Array;
				sysItem.tableArray.push( tableItem );
//				// 循环设置sysItem的附加属性				
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
		firstCell.innerHTML = "<table cellspacing='0' cellpadding='0' width='100%' style='line-height:28px;'><tr><td class='leftTitle'></td><td class='secTitle'>已选择数据情况</td><td class='rightTitle'></td></tr></table>";
		firstCell = null;
		firstRow = null;
		divContainer.appendChild( tempSysTable );
		for (var i = 0 ; i < p.sysArray.length; i ++){
			//alert( "现在进入的主题是1：" + p.sysArray[i] );
			var tempRow = tempSysTable.insertRow();
			tempRow.className = 'grid-headrow';
			var tempCell = tempRow.insertCell();
			if (p.sysArray[i].getAttribute("ztName")) {
				tempCell.innerHTML = p.sysArray[i].getAttribute("ztName");
			}
			else{
				tempCell.innerHTML = "主题未定义";
			}
			//alert(p.sysArray[i].getAttribute("ztNo"));
			//不显示主题号
			/*if ( p.sysArray[i].getAttribute("ztNo") ){
				tempCell.innerHTML += "(" + p.sysArray[i].getAttribute("ztNo") + ")";
			}*/
			
			if ( p.sysArray[i].tableArray ){
				for ( var j = 0; j < p.sysArray[i].tableArray.length; j++ ){
					// alert( "现在进入主题【" + p.sysArray[i] + "】的下面的表：" + p.sysArray[i].tableArray[j] );
					tempRow = tempSysTable.insertRow();
					tempRow.className = 'framerow';
					tempCell = tempRow.insertCell();
					if(j%2 == 0){
						tempCell.className = 'even2';
					}else{
						tempCell.className = 'even1';
					}					
					//var cellHtml = "<font color='red'>" + p.sysArray[i].tableArray[j].getAttribute("tblNameCn") + "(" + p.sysArray[i].tableArray[j].getAttribute("tblName") + ")" + "</font>：";
				   var cellHtml = "<font color='red'>未选择表</font>";	
				   if(p.sysArray[i].tableArray[j].getAttribute("tblNameCn")) {
						cellHtml = "<font color='red'>" + p.sysArray[i].tableArray[j].getAttribute("tblNameCn")  + "</font>：";										
						if ( p.sysArray[i].tableArray[j].columnArray ){
							for (var k = 0; k < p.sysArray[i].tableArray[j].columnArray.length; k ++){
								// alert( "现在进入主题【" + p.sysArray[i] + "】的下面的表【" + p.sysArray[i].tableArray[j]+"】下面的字段：" + p.sysArray[i].tableArray[j].columnArray[k]);
								cellHtml += p.sysArray[i].tableArray[j].columnArray[k].getAttribute("colNameCn") + "(" + p.sysArray[i].tableArray[j].columnArray[k].getAttribute("colName") + ")";
								//cellHtml += p.sysArray[i].tableArray[j].columnArray[k].getAttribute("colNameCn") + "";
								if ( k != p.sysArray[i].tableArray[j].columnArray.length - 1){
									cellHtml += ", ";
								}
							}
						}else{
							cellHtml += "(该表下未选择字段)";
						}
					}
					tempCell.innerHTML = cellHtml;
				}				
			}
			tempRow = null;
			tempCell = null;
		}
		tempSysTable = null;
		
		// 开始写connectTable
		if ( p.needConnectTable ){
			var connTable = document.getElementById( p.connectConditionTable );
			if ( !connTable ){
				alert("没有找到ID为【" +  p.connectConditionTable  + "】的对象");
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
				tempConnectTableCell.innerHTML = "<table cellspacing='0' cellpadding='0' width='100%'><tr><td class='leftTitle'></td><td class='secTitle'>连接条件</td><td class='rightTitle'></td></tr></table>";
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
		// 开始写queryTable
		if ( p.needQueryTable ){
			var queryTable = document.getElementById( p.queryConditionTable );
			if ( !queryTable ){
				alert("没有找到ID为【" +  p.queryConditionTable  + "】的对象");
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
				tempConnectTableCell.innerHTML = "<table cellspacing='0' cellpadding='0' width='100%'><tr><td class='leftTitle'></td><td class='secTitle'>查询条件</td><td class='rightTitle'></td></tr></table>";
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
		
		// 开始写比较条件
		if ( p.needCompareTable ){
			var compareTable = document.getElementById( p.compareTable );
			if ( !compareTable ){
				alert("没有找到ID为【" +  p.compareTable  + "】的对象");
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
				tempConnectTableCell.innerHTML = "<table cellspacing='0' cellpadding='0' width='100%'><tr><td class='leftTitle'></td><td class='secTitle'>比较条件</td><td class='rightTitle'></td></tr></table>";
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
		alert("系统出现错误!");
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
		alert("系统出现错误!");
		return null;
	}
};