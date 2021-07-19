// alert("这里是比对下载JS文件！");
window.onload = compareDownload;
window.uploadFileSize = 0;
var rootPath = window.rootPath;
var steps, tableSelect, tableConn, columnSelect, compare;
var uploadFileProcessBar, downloadProcessBar;
// 记录服务器端的路径
var uploadFilePath;
// 记录生成的表ID
var tableNo;
// 记录当前记录ID
var download_compare_id = "";

function compareDownload(){
	steps = initStepComponent();
	tableSelect = initTableSelect();
	tableConn = initConnectCondition();
	columnSelect = initResultColumns();
	uploadFileProcessBar = initUploadFileBar();
	downloadProcessBar = initDownloadBar();
	compare = initCompareCondition();
	tableSelect.doSelectQuery("/txn60210006.ajax", 
		null, null, "source_" + tableSelect.selectPrefix, "sys_id", "sys_name");
}

// 初始化当前步骤组件
function initStepComponent(){
	return new stepHelp({
       id				: "steps",     // 组件的默认ID，如果一个页面中有多个该组件，注意该ID不可重复
       parentContainer	: "stepsContainerDiv", // 其父节点的ID
       textArray		: ["导入比对文件", "选择参与比对数据表", "选择导出数据项", "确定比对条件", "下载预览"] // 字符串数组
	});
}

// 初始化数据库表选择
function initTableSelect(){
	return new dataSelectHTMLObj({
  		rootPath:rootPath,					//z根目录路径
  		selectPrefix:"table",				//下拉框id后缀
  		text_srcTitle:"*选择主题库",			//标题
  		text_selectSrcTitle:"*选择数据表",	//标题
  		tableContainer:"tableSelectDiv",	//向哪个div填充组件一
  		txnCode:"/txn60210008.ajax",		//顶部下拉框改变时执行的交易
  		paramStr:"?record:sys_id=",			//顶部下拉框改变时执行的交易参数前缀
  		fillObjId:"from_table",				//顶部下拉框改变时执行的交易数据返回后向哪个对象填充数据
  		optionValue:"table_no",				//被填充下拉框对象的value属性取值
  		optionText:"table_name_cn",			//被填充下拉框对象的显示值
  		oncontentchange:updateRelationObj,	//当已选对象内容改变后执行的函数
  		beforecontentchange:scanConditions,	//当已选对象内容改变前是执行的函数
  		addtionalParam:{ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"} //已选对象需要添加的附加属性
  	});
}

// 初始化连接条件
function initConnectCondition(){
	return new connectCondition({
  		id:"conn",
  		parentContainer:"tableConnectConditionDiv",
  		getColumnSrcPrefix:rootPath+"/txn60210009.ajax?record:table_no="
  	});
}

// 初始化结果显示列
function initResultColumns(){
	return new dataSelectHTMLObj({
		rootPath:rootPath,
		selectPrefix:"columnTable",
		text_srcTitle:"*选择数据表",
		text_selectSrcTitle:"*选择字段",
		tableContainer:"columnsSelectDiv",
		txnCode:"/txn60210013.ajax",
		paramStr:"?record:table_no=",
		fillObjId:"from_columnTable",
		optionValue:"column_no",
		optionText:"column_name_cn",
		addtionalParam:{tblid:"table_no", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn", colByName:"column_byname"}
	});
}

// 初始化比较条件
function initCompareCondition(){
	return new comparePlugin({
		id:"compare",
  		parentContainer:"tableCompareDiv",
  		getColumnSrcPrefix:rootPath+"/txn60210013.ajax?record:table_no="
	});
}

// 初始化文件上传进度条
function initUploadFileBar(){
	return new processBar({
		id:"uploadFileBar",
		parentContainer:"uploadFileProcessBarDiv"
	});
}

// 初始化下载进度条
function initDownloadBar(){
	return new processBar({
		id:"downloadBar",
		parentContainer:"downloadProcessBarDiv"
	});
}

// 初始化显示结果列
function initTotalTable(){
	return new generateTotalTable({
       id					: "totalTable",     // 组件的默认ID，如果一个页面中有多个该组件，注意该ID不可重复
       parentContainer		: "totalTableDiv", // 其父节点的ID
       columnSelect			: "selected_columnTable",
       tableSelect			: "source_columnTable",
       connectConditionTable: "conditionTbl_conn",
       compareTable			: "conditionTbl_compare",
       tableParamOnColumn	: ["tblid", "tblNameCn", "tblName"],  // 依次为表ID(Value)，表中文名(Text)，表英文名(附加属性)
       sysParamOnTable		: ["ztid", "ztName", "ztNo"],   	  // 依次为主题ID(Value)，主题中文名(Text)，主题英文名(附加属性)
       columnParamOnColumn	: ["colId", "colNameCn", "colName"]	  // 依次为字段ID(Value)，字段中文名(Text)，字段英文名(附加属性)
    });
} 

function updateRelationObj(type){
	tableConn.setDataFromSelect("selected_table");
}

// 打开查询结果
function openResultWindow(isModify){
//	var page = new pageDefine(rootPath + "/dw/aic/download/compare/preview.jsp?table_no="
//		 + tableNo + "&download_compare_id=" + download_compare_id, "查看临时表结果", "modal", 800, 450);
//	 if (isModify){
//	 	page.addValue("1", "showButton");
//	 }
//	var showResultIFrame = window.frames["resultIFrame"];
//	page.goPage(showResultIFrame);
	
	var src= rootPath + "/dw/aic/download/compare/preview.jsp?table_no="
		 + tableNo + "&download_compare_id=" + download_compare_id;
	
//	if (isModify){
//	 	src += "showButton=1";
//	 }
	document.getElementById("resultIFrame").src = src;
	document.getElementById("resultIFrame").style.display = "block";
}

// 跳转到第N步
function goToStep(i){
	var step1ContainerDiv = document.getElementById("step1ContainerDiv");
	var step2ContainerDiv = document.getElementById("step2ContainerDiv");
	var step3ContainerDiv = document.getElementById("step3ContainerDiv");
	var step4ContainerDiv = document.getElementById("step4ContainerDiv");
	var step5ContainerDiv = document.getElementById("step5ContainerDiv");
	
	function hideContainerDiv(){
		if (step1ContainerDiv.currentStyle.display != "none" ){ 
			step1ContainerDiv.style.display = "none";
		}
		if (step2ContainerDiv.currentStyle.display != "none" ){ 
			step2ContainerDiv.style.display = "none";
		}
		if (step3ContainerDiv.currentStyle.display != "none" ){ 
			step3ContainerDiv.style.display = "none";
		}
		if (step4ContainerDiv.currentStyle.display != "none" ){ 
			step4ContainerDiv.style.display = "none";
		}
		if (step5ContainerDiv.currentStyle.display != "none" ){ 
			step5ContainerDiv.style.display = "none";
		}
	}
	
	hideContainerDiv();
	steps.setCurrentStep(i);
	
	switch(i){
		case 0 : step1ContainerDiv.style.display = "block";break;
		case 1 : step2ContainerDiv.style.display = "block";break;
		case 2 : step3ContainerDiv.style.display = "block";break;
		case 3 : step4ContainerDiv.style.display = "block";break;
		case 4 : step5ContainerDiv.style.display = "block";break;
	}
}

// "生成临时表"按钮的响应函数
function genTempTableHandler(){
	var uploadFileInput = document.getElementById("record:uploadFileInput").value;
	if (!uploadFileInput){
		alert("请选择上传文件!");
		return false;
	}
	
	// 如果不是以".xls"结尾
	if ( uploadFileInput.search( /\.(xlsx?)|(txt)$/i ) < 0){
		alert("你选择的文件不是合适的EXCEL或者文本文件!");
		return false;
	}
	
	var tableName = document.getElementById("record:table_name").value;
	if (tableName.search(/^\w{5,20}$/i) < 0){
		alert("你输入的表名不合法!");
		document.getElementById("genTempTable").disabled=true;
		return false;
	}
	
	document.getElementById("genTempTable").disabled=true;
	document.getElementById("record:table_name").readOnly = "readonly";
	document.getElementById("record:table_name").value = "DL" + 
		document.getElementById("record:table_name").value;
	document.getElementById("record:uploadFileInput").readOnly = "readonly";
	
	uploadFileProcessBar.show();
	uploadFileProcessBar.reset();
	uploadFileProcessBar.setInfo("(" + 0 + "%)正在上传文件");
	uploadFileProcessBar.setPercent(0);
	
	document.forms[0].submit();
}

// 校验表名是否存在
function checkTableNameIsExist(){
	// 如果已经readOnly了，则不用再往后台校验了
	if (event.srcElement.readOnly){
		return false;
	}
	
	var tableName = document.getElementById("record:table_name").value;
	
	//要求输入以字母开头，且长度在5~20之间
	if (tableName.search(/^\w{5,20}$/i) < 0){
		document.getElementById("genTempTable").disabled=true;
		return false;
	}
	
	tableName = "DL" + tableName;
	
	$.get(rootPath + "/testsql?tableName=" + tableName, 
		function(xml){
//			alert("返回xml:" + xml.xml);
			var isExist = $("results", xml).find("isExist").text();
			
			if ( isExist == "false" || isExist == false){
				document.getElementById("genTempTable").disabled=false;
			}else{
				document.getElementById("genTempTable").disabled=true;
			}
//			alert("isExist:" + isExist);
		}
	);
}

// 创建临时表
function createTempTable(filePath, tableName){
	var tableName = document.getElementById("record:table_name").value;
	if (!tableName){
		alert("请输入临时表名！");
		return false;
	}
	
	$.post(rootPath + "/downloadCompare", 
		{filePath:uploadFilePath, tableName:tableName.toUpperCase(), tableNo:tableNo},
		function(data){
			// alert("返回结果:" + data);
			uploadFileProcessBar.setPercent(100);
			uploadFileProcessBar.setInfo("(100%)已成功上传并加载数据进入临时表!");
//			document.getElementById("viewDataLink").style.display = "block";
//			document.getElementById("step1_next_button").disabled = false;
			openResultWindow(); // 弹出数据窗口
		}
	);
}


/**
 * 扫描要删除的表是否已经被引用
 * @param action: delOne | delAll
 * @param del   : 是否同时删除引用的条件
 * @param confirmed : 是否进行提示
 * @return boolean 是否存在引用
 */
function scanConditions(action, del, confirmed){
	//获得已选数据表下拉框对象
	var selectObj = document.getElementById("selected_" + tableSelect.selectPrefix);
	if(action == "delOne"){//如果点的是“删除一条”的按钮，执行此分支
		//检查“表连接条件”中是否引用了此表
		var connTblArray = tableConn.getTableId();//获得表连接中引用的表
		var compareArray = compare.getTableId(); // 获得比较条件引用的表
  		var columns = document.getElementById("selected_"+ columnSelect.selectPrefix).options;//获得已选的字段下拉框对象
		var optionArray = getSelectedOptions("selected_" + tableSelect.selectPrefix);//获得所有选中的下拉框选项
		for(var i=0; i < optionArray.length; i++){
			if(existInArray(connTblArray, optionArray[i].value) != -1){
	  			if(del){
	  				if(!confirmed){
		  				if(!confirm("系统检测到此表在【表连接条件】中被引用，是否同时删除相关的条件？")){
		  					return false;
		  				}
	  				}
	  				confirmed = true;//设为true，避免后面的“查询条件”或“显示字段”重复提示
	  				if(tableConn.connJSONArray){
	  					for(var j = 0; j < tableConn.connJSONArray.length; j++){
	  						var connJSONObj = tableConn.connJSONArray[j];
	  						if(optionArray[i].value == connJSONObj.tableOneId || optionArray[i].value == connJSONObj.tableTwoId){
	  							tableConn.deleteCondition(j);
	  							j--;
	  							//scanConditions("delOne", true, true);//递归调用，可删除所有相关条件
	  						}
	  					}
	  				}
	  			}else{
	  				alert("系统检测到此表在【表连接条件】中被引用，禁止删除！");
	  				return false;
	  			}
	  		}
	  		
	  		if(existInArray(compareArray, optionArray[i].value) != -1){
	  			if(del){
	  				if(!confirmed){
		  				if(!confirm("系统检测到此表在【比较条件】中被引用，是否同时删除相关的条件？")){
		  					return false;
		  				}
	  				}
	  				confirmed = true;//设为true，避免后面的“查询条件”或“显示字段”重复提示
	  				if(compare.connJSONArray){
	  					for(var j = 0; j < compare.connJSONArray.length; j++){
	  						var connJSONObj = compare.connJSONArray[j];
	  						if(optionArray[i].value == connJSONObj.tableOneId || optionArray[i].value == connJSONObj.tableTwoId){
	  							compare.deleteCondition(j);
	  							j--;
	  							//scanConditions("delOne", true, true);//递归调用，可删除所有相关条件
	  						}
	  					}
	  				}
	  			}else{
	  				alert("系统检测到此表在【表连接条件】中被引用，禁止删除！");
	  				return false;
	  			}
	  		}
	  		
	  		if(columns.length != 0){
	  			var exist = false;
	  			for(var j = 0; j < columns.length; j++){
	  				var colTblId = columns[j].tblid;
	  				if(colTblId == optionArray[i].value){
	  					exist = true;
	  					columns[j].selected = true;//设为选中，最后一起删除
	  					//break;
	  				}else{
	  					columns[j].selected = false;//如果已经选中，则设为不选，避免误删
	  				}
	  			}
	  			if(exist){
	  				if(del){
	  					if(!confirmed){
			  				if(!confirm("系统检测到需要显示此表的字段，是否同时删除相关的字段？")){
			  					return false;
			  				}
	  					}
		  					
		  				columnSelect.clickToLeft("tblid", true);
		  			}else{
		  				alert("系统检测到需要显示此表的字段，禁止删除！");
		  				return false;
		  			}
	  			}
	  		}
		}
  		connTblArray = null;
  		columns = null;
  		optionArray = null;
	}else if (action == "delAll"){//如果点的是“删除全部”的按钮，执行此分支
		if(selectObj.options.length != 0){
			for(var i = 0; i< selectObj.options.length; i++){
				selectObj.options[i].selected = true;
				if(!scanConditions("delOne", del, true)){//循环调用“删除一条”的分支
					//selectObj.options[i].selected = false;
					return false;
				}
				selectObj.options[i].selected = false;
			}
		}
	}
	selectObj = null;
	return true;
}

// 创建SQL语句
function createSQL(step, isNoNeedValue){
	var selectedTables = document.getElementById("selected_"+tableSelect.selectPrefix).options;
	var sql = "";
	
	if(step == 2){
		sql = "SELECT " + selectedTables[0].tblName + ".* FROM ";
	
		for(var i = 0; i < selectedTables.length; i++){
			var tblName = selectedTables[i].tblName;
			sql += tblName;
			if( i == (selectedTables.length -1) )
				continue;
			else
				sql += ", ";
		}
		
		var paramStr = tableConn.getconnConditionStr();
		if(paramStr.trim() != ""){
			sql += " WHERE "+ paramStr;
		}
	}else if (step == 3){
		var columnObj = document.getElementById("selected_"+columnSelect.selectPrefix).options;
  		var columnIdArray;
  		if(columnObj.length == 0){
  			alert("请选择字段！");
  			return false;
  		}
  		
		var columnNameArray = new Array();
		var resultColumnNameArray = new Array(); 
		for(var i = 0; i < columnObj.length; i++){
			// 不是临时表
			if (columnObj[i].tblid != tableNo){
				if(columnObj[i].colByName){//判断是否存在别名
  					columnNameArray.push(columnObj[i].tblName + "." +columnObj[i].colName + " AS " + columnObj[i].colByName);
  					resultColumnNameArray.push("TMP." + columnObj[i].colByName);
  				}else{
  					columnNameArray.push(columnObj[i].tblName + "." + columnObj[i].colName);
  					resultColumnNameArray.push("TMP." + columnObj[i].colName);
  				}
			}else{
				resultColumnNameArray.push(columnObj[i].tblName + "." + columnObj[i].colName);
			}
		}
		
		var subSql = "";
		if (columnNameArray.toString().search(/^\s*$/i) < 0){
			subSql = "SELECT " + columnNameArray.toString() + " FROM ";
		}else{
			subSql = "SELECT '1' as rs FROM ";
		}
	
		for(var i = 0; i < selectedTables.length; i++){
			var tblName = selectedTables[i].tblName;
			subSql += tblName;
			if( i == (selectedTables.length -1) )
				continue;
			else
				subSql += ", ";
		}
		
		var paramStr = tableConn.getconnConditionStr();
		if(paramStr.trim() != ""){
			subSql += " WHERE (" + paramStr + ")";
		}
//		alert("subSql:" + subSql);
		var tableName = document.getElementById("record:table_name").value;
		sql = "SELECT " + resultColumnNameArray.toString() + " FROM " + tableName + ",";
		sql = sql + "(" + subSql + ") TMP ";	
//		alert("sql:" + sql);	
	}else if (step == 4){
		var columnObj = document.getElementById("selected_"+columnSelect.selectPrefix).options;
  		var columnIdArray;
  		if(columnObj.length == 0){
  			alert("请选择字段！");
  			return false;
  		}
  		
		var columnNameArray = new Array();
		var codeColumnNameArray = new Array(); 
		var resultColumnNameArray = new Array(); 
		var resultColumnCnNameArray = new Array();
		for(var i = 0; i < columnObj.length; i++){
			// 不是临时表
			if (columnObj[i].tblid != tableNo){
				if(columnObj[i].colByName){//判断是否存在别名
  					columnNameArray.push(columnObj[i].tblName + "." +columnObj[i].colName + " AS " + columnObj[i].colByName);
  					codeColumnNameArray.push(columnObj[i].tblName + "." +columnObj[i].colName + "." + columnObj[i].colByName);
  					resultColumnNameArray.push("TMP." + columnObj[i].colByName);
  					resultColumnCnNameArray.push(columnObj[i].text);
  				}else{
  					columnNameArray.push(columnObj[i].tblName + "." + columnObj[i].colName);
  					codeColumnNameArray.push(columnObj[i].tblName + "." + columnObj[i].colName + ".");
  					resultColumnNameArray.push("TMP." + columnObj[i].colName);
  					resultColumnCnNameArray.push(columnObj[i].text);
  				}
			}
			else{
				codeColumnNameArray.push(columnObj[i].tblName + "." + columnObj[i].colName + ".");
//				resultColumnNameArray.push(columnObj[i].tblName + "." + columnObj[i].colName);
				resultColumnCnNameArray.push(columnObj[i].text);
			}
		}
//		alert("中文名:" + resultColumnCnNameArray.toString());
		
		document.getElementById("columnCnNameInput").value = resultColumnCnNameArray.toString();
		document.getElementById("columnNameInput").value = codeColumnNameArray.toString();
		
		// 添加比对条件处的查询
		var connJSONArray = compare.connJSONArray;
		for (var t = 0; t < compare.connJSONArray.length; t++){
			var cc = compare.connJSONArray[t];
			var columnOneName = cc.tableOneName + "." + cc.columnOneName;
			if (cc.columnOneByName){
				columnOneName += " AS " + cc.columnOneByName;
			}
			var columnTwoName = cc.tableTwoName + "." + cc.columnTwoName;
			if (cc.columnTwoByName){
				columnTwoName += " AS " + cc.columnTwoByName;
			}
			var hasFoundColumnOne = false;
			for (var index=0; index < columnNameArray.length && !hasFoundColumnOne; index ++){
				if (columnNameArray[index] == columnOneName){
					hasFoundColumnOne = true;
				}
			}
			
			if (!hasFoundColumnOne){
				columnNameArray.push(columnOneName);
			}
		}
		var subSql = "SELECT " + columnNameArray.toString() + " FROM ";
	
		for(var i = 0; i < selectedTables.length; i++){
			var tblName = selectedTables[i].tblName;
			subSql += tblName;
			if( i == (selectedTables.length -1) )
				continue;
			else
				subSql += ", ";
		}
		
		var paramStr = tableConn.getconnConditionStr();
		if(paramStr.trim() != ""){
			subSql += " WHERE (" + paramStr + ")";
		}
		var tableName = document.getElementById("record:table_name").value;
		sql = "SELECT " + tableName + ".* " + 
			(resultColumnNameArray.toString() ? ("," + resultColumnNameArray.toString()) : "") + 
			" FROM " + tableName + ",";
		sql = sql + "(" + subSql + ") TMP ";
		var compareStr = compare.getconnConditionStr();
		if (compareStr.trim()){
			sql += " WHERE (" + compareStr + ")";
		}	
	}else{
		alert("还没有配置呢!");
	}
	
	selectedTables = null;
	paramStr = null;
//	alert("sql:" + sql);
	return sql;
}

// 执行按钮的响应函数
function executeHander(){
	// 先隐藏下载组件
	document.getElementById("downloadContainerDiv").style.display = "none";
	document.getElementById("downloadProcessBarDiv").style.display = "none";
	document.getElementById("linkDiv").innerHTML = "";
	
	var sql = createSQL(4);
	var tableName = document.getElementById("record:table_name").value;
	sql = "create table " + tableName + "_LS as " + sql;
	_showProcessHintWindow( "正在执行比对，请稍候....." );
	document.getElementById("execute_button").disabled = true;
	$.post(rootPath + "/downloadExecute",
		{sql : sql, tableName : tableName},
		function(xml){
			_hideProcessHintWindow();
			var hasSuccess = $("results", xml).find("hasSuccess").text();
			if (hasSuccess == true || hasSuccess == "true"){
				alert("比对执行成功！比对记录总数为" + $("results", xml).find("totalCount").text());
				var downloadContainerDiv = document.getElementById("downloadContainerDiv");
				downloadContainerDiv.style.display = "block";
				downloadContainerDiv = null;
			}else{
				alert("比对执行失败！请稍候再试！");
			}
			document.getElementById("execute_button").disabled = false;
		}
	);
}

// 第一个"下一步"按钮的响应函数
function step1_next(){
	var remark = document.getElementById("record:remark").value;
	if (remark.search(/^\s*$/i) >= 0){
		alert("请输入备注！");
		return false;
	}else{
		var page = new pageDefine("/txn60500007.ajax");
		_showProcessHintWindow( "正在存储备注，请稍候....." );
		page.addValue(remark, "record:remark");
		page.addValue(download_compare_id, "record:download_compare_id");
		page.callAjaxService("doCallBack");
	}
}

function doCallBack(errCode, errDesc, xmlResults){
	if(errCode=='000000'){
		_hideProcessHintWindow();
		document.getElementById("step1_next_button").value = "下一步";
		var dataErrorButton = document.getElementById("dataErrorButton");
		if (dataErrorButton){
			dataErrorButton.style.display = "none";
		}
		dataErrorButton = null;
		goToStep(1);
	}else{
		alert(errDesc);
	}
}

function step2_next(){
	// 校验连接条件
	if(document.getElementById("selected_"+tableSelect.selectPrefix).options.length == 0){
		alert("请选择数据表！");
		return;
	}
	
	if(tableConn.validateConnCondition("selected_"+tableSelect.selectPrefix)){
		document.getElementById("step2_next_button").disabled = true;
		_showProcessHintWindow( "正在校验表连接条件，请稍候....." );
		var sql = createSQL(2);
//		alert(sql);
		$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
			var result = xml.selectSingleNode("/results/sql").text;
			if(result=="false"){
				_hideProcessHintWindow();
				alert("SQL校验失败！请检查后重试");
				document.getElementById("step2_next_button").disabled = false;						
				return false;
			}else{
				_hideProcessHintWindow();	
				columnSelect.updateColumnTables("selected_"+tableSelect.selectPrefix, 
					"source_"+columnSelect.selectPrefix);
				// 将临时表加入第三步的数据表下拉框
				var tableName = document.getElementById("record:table_name").value;
				var source = document.getElementById("source_"+columnSelect.selectPrefix);
				var tempTableOption = document.createElement("OPTION");
				tempTableOption.appendChild(document.createTextNode("比对表(" + tableName + ")"));
				tempTableOption.value = tableNo;
				tempTableOption.ztid = "";
				tempTableOption.tblName = tableName;
				tempTableOption.ztName = "临时表主题";
				tempTableOption.ztNo = "";
				source.options.appendChild(tempTableOption);
				document.getElementById("step2_next_button").disabled = false;
				goToStep(2);
			}
		});
	}
}

function step3_next(){
	if(document.getElementById("selected_"+columnSelect.selectPrefix).options.length == 0){
		alert("请选择查询字段！");
		return false;
	}
	
	document.getElementById("step3_next_button").disabled = true;	
	_showProcessHintWindow( "正在校验导出数据项，请稍候....." );
	var sql = createSQL(3);
//	alert(sql);
	$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
		var result = xml.selectSingleNode("/results/sql").text;
		if(result=="false"){
			_hideProcessHintWindow();
			alert("SQL校验失败！请检查后重试");
			document.getElementById("step3_next_button").disabled = false;						
			return false;
		}else{
			_hideProcessHintWindow();	
			var tableName = document.getElementById("record:table_name").value;
			compare.setTableTwoData("比对表(" + tableName + ")", tableNo, tableName);
			compare.setDataFromSelect("selected_" + tableSelect.selectPrefix);
			document.getElementById("step3_next_button").disabled = false;
			goToStep(3);
		}
	});
}

function step4_next(){
	if (compare.connJSONArray.length <= 0){
		alert("您没有选择任何比对条件！");
		return false;
	}
	document.getElementById("step4_next_button").disabled = true;	
	_showProcessHintWindow( "正在校验比较条件，请稍候....." );
	var sql = createSQL(4);
//	alert(sql);
	$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
		var result = xml.selectSingleNode("/results/sql").text;
		if(result=="false"){
			_hideProcessHintWindow();
			alert("SQL校验失败！请检查后重试");
			document.getElementById("step4_next_button").disabled = false;					
			return false;
		}else{
			_hideProcessHintWindow();	
			document.getElementById("step4_next_button").disabled = false;
			var totalTable = initTotalTable();
			totalTable.write();
			goToStep(4);
			var downloadContainerDiv = document.getElementById("downloadContainerDiv");
			downloadContainerDiv.style.display = "none";
			downloadContainerDiv = null;
		}
	});
}


/** 
 * Copy from download_param.jsp
 */
var xmlObj;
/**
 * 下载文件
 * @param fileType:文件类型
 */
function downloadData(fileType){
	var tableName = document.getElementById("record:table_name").value;
	var dataAddition = "&fileName=" + encodeURI(encodeURI( tableName ))
		+ "&downloadCompare=1";
	
	var displayType = "name";
	var changeDataTypeRadio = document.getElementsByName("changeDataTypeRadio");
	if (changeDataTypeRadio[0].checked){
		displayType = changeDataTypeRadio[0].value;
	}else if (changeDataTypeRadio[1].checked){
		displayType = changeDataTypeRadio[1].value;
	}else if (changeDataTypeRadio[2].checked){
		displayType = changeDataTypeRadio[2].value;
	}
//	alert(displayType);
	dataAddition += "&displayType=" + displayType;
	
	document.getElementById("downloadProcessInfoDiv").style.display = "block";
	
	document.getElementById("linkDiv").innerHTML = "";
	downloadProcessBar.show();
	downloadProcessBar.reset();
	downloadProcessBar.setInfo("(" + 0 + "%)正在获取比对成功记录总数");
	downloadProcessBar.setPercent(0);
	
	var sql = "SELECT * FROM " + tableName + "_LS";
	document.getElementById("currentSqlInput").value = sql;
	var post = "testsql=" + encodeURIComponent(sql);
//	post=encodeURI(post);
//	post=encodeURI(post);
	xmlObj = createXMLHttpRequest();
	var URL = rootPath+'/getCount';
	xmlObj.open ('post',URL, true);
	xmlObj.onreadystatechange = function(){
		handleRespose(fileType, dataAddition);
	}
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(post);
}

// 生成比对成功文件
function genSuccessFiles(dataAddition, fileType){
	dataAddition += "&filePrefix=1";
	var sid = "";
	var fileSize = 10000;
	var totalRecords = document.getElementById("totalRecordsHidden").value; 
	totalRecords = parseInt(totalRecords);
	var successTotalPage = totalRecords%fileSize == 0 ? parseInt(totalRecords/fileSize) : parseInt(totalRecords/fileSize) + 1;

	if (fileType == "xls" || fileType == "txt" || fileType == "database"){
		genXlsFile(dataAddition, fileSize, 1, successTotalPage, sid, totalRecords, fileType, true);
	}else{
		alert("文件类型不正确!");
		return false;
	}
}

// 生成比对失败文件
function genFailedFiles(dataAddition, fileType, sid){
	if (dataAddition.indexOf("filePrefix=1")>=0){
		dataAddition = dataAddition.replace("filePrefix=1", "filePrefix=-1");
	}else if(dataAddition.indexOf("filePrefix=-1") < 0){
		dataAddition += "&filePrefix=-1";
	}
	var fileSize = 10000;
	var totalRecords = document.getElementById("totalRecordsFailedHidden").value; 
	totalRecords = parseInt(totalRecords);
	var failedTotalPage = totalRecords%fileSize == 0 ? parseInt(totalRecords/fileSize) : parseInt(totalRecords/fileSize) + 1;

	if (fileType == "xls" || fileType == "txt" || fileType == "database"){
		genXlsFile(dataAddition, fileSize, 1, failedTotalPage, sid, totalRecords, fileType, false);
	}else{
		alert("文件类型不正确!");
		return false;
	}
}

// 生成报告
function genReport(dataAddition, sid){
	var successNumber = document.getElementById("totalRecordsHidden").value;
	var failedNumber = document.getElementById("totalRecordsFailedHidden").value;
	var compareCondition = document.getElementById("totalTableDiv").innerText;	
	var remark = document.getElementById("record:remark").value;
	
	var post = "operate=REPORT&sid="+sid+"&successNumber="+successNumber
		+ "&failedNumber="+failedNumber+"&compareCondition="+encodeURIComponent(compareCondition)
		+ "&totalRecord="+window.uploadFileSize + "&remark=" + encodeURIComponent(remark);
//	post=encodeURI(post);
//	post=encodeURI(post);
	xmlObj = createXMLHttpRequest();
	var URL = rootPath+'/download';
	xmlObj.open ('post',URL, true);
	xmlObj.onreadystatechange = function(){
		if (xmlObj.readyState == 4) { // 判断对象状态
			if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
				xmlResults = xmlObj.responseXML;
				genZipFile(dataAddition, sid);
			}
		}
	}
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(post);	
}

//创建XMLHttpRequest对象
function createXMLHttpRequest() {
	var xmlObj;
	try { 
		xmlObj = new ActiveXObject("Msxml2.XMLHTTP"); 
	} catch (e) { 
		try { 
		  	xmlObj = new ActiveXObject("Microsoft.XMLHTTP"); 
		} catch (E) { 
		  	xmlObj = false; 
		} 
	}
	if (!xmlObj && typeof XMLHttpRequest!='undefined'){ 
		xmlObj = new XMLHttpRequest(); 
	} 
	if (!xmlObj){
		alert("Get Ajax object failed");
	}
	return xmlObj;
}

function handleRespose(fileType, dataAddition){
	if (xmlObj.readyState == 4) { // 判断对象状态
		if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
			var xmlResults = xmlObj.responseXML;
			var totalRecords = xmlResults.selectSingleNode("//results/totalRecord");
			totalRecords = parseInt(totalRecords.text);
			document.getElementById("totalRecordsHidden").value = totalRecords;
			
			downloadProcessBar.setInfo("(" + 5 + "%)正在获取比对失败记录总数");
			downloadProcessBar.setPercent(5);
			
			var tableName = document.getElementById("record:table_name").value;
			
			var tempTableColumns = document.getElementById("tempTableColumnsInput").value;
			var sql = "SELECT " + tempTableColumns +" FROM " + tableName
				+ " minus "
				+ "SELECT " + tempTableColumns + " FROM " + tableName + "_LS";
			document.getElementById("failedSqlInput").value = sql;
			var post = "testsql=" + encodeURIComponent(sql);
//			post=encodeURI(post);
//			post=encodeURI(post);
			xmlObj = createXMLHttpRequest();
			var URL = rootPath+'/getCount';
			xmlObj.open ('post',URL, true);
			xmlObj.onreadystatechange = function(){
				if (xmlObj.readyState == 4) { // 判断对象状态
					if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
						xmlResults = xmlObj.responseXML;
						var failedTotalRecords = xmlResults.selectSingleNode("//results/totalRecord");
						failedTotalRecords = parseInt(failedTotalRecords.text);
						document.getElementById("totalRecordsFailedHidden").value = failedTotalRecords;
						
						if (document.getElementById("totalRecordsFailedHidden").value == "0"
							&& document.getElementById("totalRecordsHidden").value == "0"){
							downloadProcessBar.setPercent(100);
							downloadProcessBar.setInfo("(" + 100 + "%)总记录数为0，无需下载！");
						}
						genSuccessFiles(dataAddition, fileType);
					}
				}
			}
			xmlObj.setrequestheader("cache-control","no-cache"); 
			xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
			xmlObj.send(post);			
		} else { //页面不正常
			alert("获取记录总数时出现异常");
			downloadProcessBar.hide();
		}
	}
}

function genXlsFile(dataAddition, fileSize, currPage, totalPage, sid, totalRecords, fileType, bSuccess){	
	var percent = bSuccess ? (parseInt((currPage-1) * 50 / totalPage) + 10) : 
			(parseInt((currPage-1) * 20 / totalPage) + 60); 
    var per="";
	try{
	  per=parseInt(percent);
	}catch(e){
	  per=50;
	}
	
	downloadProcessBar.setInfo("(" + percent + "%)正在生成 " + totalPage + " 个" + fileType + (bSuccess ? "成功" : "失败") + "文件中的第 " + currPage + " 个..");
	downloadProcessBar.setPercent(percent);
	var sql = bSuccess ? document.getElementById("currentSqlInput").value : 
		document.getElementById("failedSqlInput").value;
		
	// sql = encodeURI(encodeURI(sql));
	sql = encodeURIComponent(sql);
	
	var columnCnName = bSuccess ? document.getElementById("columnCnNameInput").value : 
			document.getElementById("tempTableColumnsCnInput").value;
//	columnCnName = encodeURI(encodeURI(columnCnName));
	columnCnName = encodeURIComponent(columnCnName);
	var columnName = bSuccess ? document.getElementById("columnNameInput").value :
			document.getElementById("tempTableColumnsEnInput").value;
//	columnName = encodeURI(encodeURI(columnName));
	columnName = encodeURIComponent(columnName);
	var data = "operate=" + fileType + "&fileSize="+fileSize;
	data += "&record:query_sql=" + sql + "&record:columnsCnArray=" + columnCnName 
		+ "&record:columnsEnArray=" + columnName;
	data += "&currPage="+currPage;
	if (sid){
		data += "&sid="+sid;
	}
	if (dataAddition){
		data += dataAddition;
	}
	var xmlObj = new ActiveXObject("Microsoft.XMLHTTP");
	var URL = rootPath + '/download';
	xmlObj.open ('post',URL, true);
	xmlObj.onreadystatechange = function(){
		if (xmlObj.readyState == 4){
			if (xmlObj.status == 200){
				var xmlResults = xmlObj.responseXML;
				var sid = xmlResults.selectSingleNode("//results/sid").text;
				currPage = currPage + 1;
				if (currPage > totalPage){
					if (bSuccess){
						genFailedFiles(dataAddition, fileType, sid);
					}else{
						genReport(dataAddition, sid);
					}
					return;
				}else{
					genXlsFile(dataAddition, fileSize, currPage, totalPage, sid, totalRecords, fileType, bSuccess);
				}	
			}else{
				downloadProcessBar.hide();
				window.alert("生成文件过程中出现异常，请稍候再试");
			}			
		}
	};
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(data);
}

function genZipFile(dataAddition, sid){
	downloadProcessBar.setPercent(80);
	downloadProcessBar.setInfo("(80%)正在生成ZIP文件");
	
	var data = "operate=zip&sid=" + sid;
	if (dataAddition){
		data += dataAddition;
	}
	var xmlObj = new ActiveXObject("Microsoft.XMLHTTP");
	var URL = rootPath + '/download';
	xmlObj.open ('post',URL, true);
	xmlObj.onreadystatechange = function(){
		if (xmlObj.readyState == 4){
			if (xmlObj.status == 200){
				var xmlResults = xmlObj.responseXML;
				var sid = xmlResults.selectSingleNode("//results/sid").text;
				var display_name = xmlResults.selectSingleNode("//results/fileName").text;
				downloadProcessBar.setInfo("(100%)完成!");
				downloadProcessBar.setPercent(100);
				document.getElementById("linkDiv").innerHTML = "<a href='" + rootPath + "/download?filePath="+sid+".zip&displayName=" + display_name
					+ "' target='_blank'>点击这里下载</a>";
			}else{
				downloadProcessBar.hide();
				window.alert("生成文件过程中出现异常，请稍候再试");
			}			
		}
	};
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(data);
}

// 返回按钮的回调
function goBackHandler(){
	goBack();
};

function dataErrorHandler(){
	var error = window.confirm("该操作将会删除刚才上传的数据, 您确定数据有误吗？");
	if (error){
		_showProcessHintWindow( "正在删除临时表和临时表数据，请稍候....." );
		var page = new pageDefine("/txn60500005.ajax");
		page.addValue(download_compare_id , "primary-key:download_compare_id");
		page.callAjaxService("doDeteleDataCallBack");
	}
}

function doDeteleDataCallBack(errCode, errDesc, xmlResults){
	if(errCode=='000000'){
		_hideProcessHintWindow();
		alert("临时表和临时表数据删除成功，请重新上传文件！");
		
		document.getElementById("record:uploadFileInput").readOnly = false;
		var tableName = document.getElementById("record:table_name").value;
		if (tableName.indexOf("DL") == 0){
			tableName = tableName.substr(2);
			document.getElementById("record:table_name").value = tableName;
		}
		
		document.getElementById("genTempTable").disabled = false;
//		document.getElementById("viewDataLink").style.display = "none";
		
		uploadFileProcessBar.reset();
		uploadFileProcessBar.hide();
		document.getElementById("resultIFrame").style.display = "none";
		document.getElementById("dataErrorButton").style.display = "none";
		document.getElementById("step1_next_button").value = "下一步";
		document.getElementById("step1_next_button").disabled = true;
	}else{
		alert(errDesc);
		_hideProcessHintWindow();
	}
}