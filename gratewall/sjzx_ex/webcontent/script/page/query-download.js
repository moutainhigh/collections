var pBar, qcObj;
// 覆盖dataSelect组件的方法
dataSelectHTMLObj.prototype.doSelectQuery = function(txnCode, param, dataBusprefix, selectObjId, valueColumn, textColumn, addtionalParam, checkDataSelect){
	var p = this;
	clearOptions(selectObjId, true);
	if(!param)
		param = '';
		
	if(!dataBusprefix)
		dataBusprefix = "record";
	
	$.get(this.rootPath + txnCode + param, function(xml){
		fillXmlToSelect(xml, dataBusprefix, selectObjId, valueColumn, textColumn, addtionalParam, checkDataSelect);
		fillXmlToSelect(xml, dataBusprefix, "qctColumnOne", "column_no", "column_name_cn", {type: "edit_type", codeValue: "demo", colName:"column_name"});
		var qctTableOne = document.getElementById("qctTableOne");
		qctTableOne.innerHTML = "";
		var temp = document.createElement("option");
		qctTableOne.appendChild( temp );
		var tableSelect = document.getElementById("source_table");
		var tableOption = tableSelect.options[tableSelect.selectedIndex];
		temp.value = tableOption.value;
		temp.tblName = tableOption.value;
		var text = tableOption.text;
		tableOption = null;
		tableSelect = null;
		temp.appendChild( document.createTextNode( text ) );
		temp = null;
		qctTableOne = null;
		qcObj.Clear();
		document.getElementById("qctColumnOne").onchange();
	});
}

function page_queryDownload(){
	var rootPath = window.rootPath;
	var steps = new stepHelp({
       id				: "steps",
       parentContainer	: "stepsContainerDiv",
       textArray		: ["查询条件定义", "预览"]
	});
	
	var obj = new dataSelectHTMLObj({
  		rootPath:rootPath,
  		selectPrefix:"table",
  		text_srcTitle:"*选择数据表",
  		text_selectSrcTitle:"*选择字段",
  		tableContainer:"div1",
  		txnCode:"/txn30403007.ajax",
	  	paramStr:"?select-key:table_name=",
  		fillObjId:"from_table",
  		optionValue:"column_no",
  		optionText:"column_name_cn",
  		addtionalParam:{tblid:"table_name", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn", colByName:"column_byname"}
  	});
  	setTableData();
  	
  	var oldChangeEvent = document.getElementById("source_table").onchange;
  	document.getElementById("source_table").onchange = function(){
  		document.getElementById("selected_table").innerHTML = "";
  		if (oldChangeEvent){
  			oldChangeEvent();
  		}
  	}
  	
  	function setTableData(){
  		var source_table = document.getElementById("source_table");
  		var tempOption1  = document.createElement("option");
  		tempOption1.value = "reg_bus_ent";
  		tempOption1.appendChild( document.createTextNode("企业机构表") );
  		var tempOption2 = document.createElement("option");
  		tempOption2.value = "reg_indiv_base";
  		tempOption2.appendChild( document.createTextNode("个体表") );
  		
  		source_table.appendChild( tempOption1 );
  		source_table.appendChild( tempOption2 );
  		tempOption1 = null;
  		tempOption2 = null;
  		source_table = null;
  	}
  	
  	qcObj = new queryCondition({
       id                  : "qct",
       parentContainer     : "divQc",
       getColumnSrcPrefix  : rootPath+"/txn30403007.ajax?select-key:table_name=",
       getCodeSrcPrefix    : rootPath+"/txn60210012.ajax?select-key:jc_dm_id="
    });
  	
  	function createSQL(loadColumn, reg_org){
		var sql = "";
		
		var columnObj = document.getElementById("selected_table").options;
  		var columnNameArray;
  		var columnIdArray;
  		if(columnObj.length == 0){
  			alert("请选择字段！");
  			return false;
  		}else{
  			columnNameArray = new Array();
  			columnIdArray = new Array();
  			for(var i = 0; i < columnObj.length; i++){
  				if(columnObj[i].colByName){//判断是否存在别名
  					columnNameArray.push(columnObj[i].tblName + "." +columnObj[i].colName + " AS " + columnObj[i].colByName);
  				}else{
  					columnNameArray.push(columnObj[i].tblName + "." +columnObj[i].colName);
  				}
  				columnIdArray.push(columnObj[i].value);
  			}
  		}
  		
  		var source_table = document.getElementById("source_table");
		sql = "SELECT " + columnNameArray.toString() + " FROM " + source_table.options[source_table.selectedIndex].value;
		
		source_table = null;
		
		if(sql.indexOf("WHERE") > -1){
			sql += "(" + qcObj.getQueryConditionStr() + ")";
		}else{
			if(qcObj.getQueryConditionStr().trim() != "")
				sql += " WHERE (" + qcObj.getQueryConditionStr() + ")";
		}
		
		var source_table = document.getElementById("source_table");
  		var tableName = source_table.options[source_table.selectedIndex].value;
  		source_table = null;
		
		if(reg_org){
			// 添加了是否是登记主表的判断，如果不是登记主表，按照reg_org进行判断
			// 如果是登记主表，按照dom_district进行判断
			if ( tableName.toUpperCase() != "REG_BUS_ENT"){
				if (sql.indexOf("WHERE") > -1){
					sql += " AND reg_org like '" + reg_org + "%'";
				}else{
					sql += " WHERE reg_org like '" + reg_org + "%'";
				}
			}else{
				if (sql.indexOf("WHERE") > -1){
					sql += " AND dom_district like '" + reg_org + "%'";
				}else{
					sql += " WHERE dom_district like '" + reg_org + "%'";
				}
			}
			
		}
		
		columnObj = null;
		columnNameArray = null;
		columnIdArray = null;
		
		selectedTables = null;
		paramStr = null;
		return sql;
  	}
  	
  	document.getElementById("toPrevButton").onclick = function(){  		
  		var columnObj = document.getElementById("selected_"+obj.selectPrefix).options;
  		var columnEnArray;
  		if(columnObj.length == 0){
  			alert("请选择字段！");
  			return false;
  		}else{
  			columnEnArray = new Array();
  			for(var i = 0; i < columnObj.length; i++){
  				if(columnObj[i].colByName){//判断是否存在别名
  					addToArray(columnEnArray, columnObj[i].colByName);
  				}else{
  					addToArray(columnEnArray, columnObj[i].colName);
  				}
  			}
  		}
		var reg_org = $("input#reg_org").val();
		// 提交查询
		var sql = createSQL(true, reg_org);
//		alert(sql);
		// 校验sql语句是否正确
  		document.getElementById("record:query_sql").value = sql;//设置SQL
  		document.getElementById("record:columnsCnArray").value = obj.getSelectedDataValues("text");//设置字段中文名
  		document.getElementById("record:columnsEnArray").value = columnEnArray.toString();//设置字段英文名
  		
  		var selectedDataDisplay = "<font color='red'>数据库表：</font>";
  		
  		var source_table = document.getElementById("source_table");
  		selectedDataDisplay += source_table.options[source_table.selectedIndex].text;
  		source_table = null;
  		selectedDataDisplay += "<br><font color='red'>查询字段：</font>" + document.getElementById("record:columnsCnArray").value;  		
  		selectedDataDisplay += "<br><font color='red'>查询条件：</font>";
  		
  		var tdQueryCondition = document.getElementById("tdQueryCondition");
  		
  		var connTable = document.getElementById( "conditionTbl_qct" );				
		var rows = connTable.rows;
		if (rows.length > 0){
			for (var tempIndex = 0; tempIndex < rows.length; tempIndex ++){
				selectedDataDisplay += rows[tempIndex].cells[0].innerHTML;
			}
		}else{
			selectedDataDisplay += "(无)";
		}
		tdQueryCondition.innerHTML = selectedDataDisplay;
		connTable = null;
  		tdQueryCondition = null;
  		
		document.getElementById("step1DIV").style.display = "none";
  		document.getElementById("prevDIV").style.display = "block";
		steps.setCurrentStep(1);
		document.forms[0].submit();
  	}
  	
  	document.getElementById("toStep1Button").onclick = function(){
  		document.getElementById("pageList_frameX").src = "";
		pBar.hide();
  		document.getElementById("step1DIV").style.display = "block";
  		document.getElementById("prevDIV").style.display = "none";
  		steps.setCurrentStep(0);
  	}
  	
  	pBar = new processBar({
       id					: "pBar",     // 组件的默认ID，如果一个页面中有多个该组件，注意该ID不可重复
       parentContainer		: "processBarDiv"  // 其父节点的ID
    });
};

function ShowDialog(url){
	var iWidth=400; //窗口宽度
	var iHeight=200;//窗口高度
	var iTop=(document.body.clientHeight - iHeight)/2;
	var iLeft=(document.body.clientWidth - iWidth)/2;
	var applyFrameDiv = document.getElementById("applyFrameDiv"); 
	applyFrameDiv.style.paddingTop =iTop;
	applyFrameDiv.style.paddingLeft=iLeft;
	applyFrameDiv.style.display="block";
	document.getElementById("applyFrame").style.width=iWidth;
	document.getElementById("applyFrame").style.height=iHeight;
	var applyFrame = window.frames['applyFrame'];
	applyFrame.location.href=url;
};

function downloadData(){
	pBar.show();
	pBar.reset();
	document.getElementById("processBarDiv").style.display = "block";
	pBar.setInfo("(0%)正在获取下载记录总数..");
	pBar.setPercent(0);
	try{
		var totalRecordSpanHtml = document.getElementById("pageList_frameX").contentWindow.document.getElementById("totalRecordSpan").innerHTML;
	}catch(e){
		try{
			var errorTD = document.getElementById("pageList_frameX").contentWindow.document.getElementById("errorTd");
			if (errorTD){
				pBar.setInfo("(0%)" + errorTD.innerHTML + ", 下载停止!");
				return;
			}
		}catch(f){
			setTimeout(downloadData, 100);
			return;
		}
		setTimeout(downloadData, 100);
		return;
	}
	
	var totalRecord = parseInt(totalRecordSpanHtml);
	
	// 分发操作
	if (totalRecord == 0){
		pBar.hide();
		alert("数据总数为0条，无需下载！");
		return false;
	}
	var has_purv   = $("input#has_purv").val();
	var max_result = $("input#max_result").val();
	if (has_purv == 0){
		pBar.hide();
		alert("您没有下载的权限！");
		return false;
	}else if (parseInt(totalRecord) <= parseInt(max_result) || parseInt(max_result) == 0){
		// alert("直接下载！");
		var source_table = document.getElementById("source_table");
		var fileName = source_table.options[source_table.selectedIndex].text;
		fileName = encodeURI(encodeURI( fileName ));
		source_table = null;
		callDownloadServlet("&fileName=" + fileName, null, totalRecord);
	}else{
		// alert("申请下载！");
		pBar.hide();
		var sUrl = rootPath + "/dw/aic/download/query/inputDownloadCount.jsp?max_result="+ max_result +"&totalRecords="+totalRecord;
		ShowDialog(sUrl);
	}
};

// 调用后台下载文件servlet的统一入口函数
// dataAddition: 附加参数
function callDownloadServlet(dataAddition, isApply, totalRecord, processBar, sid){
	// 每个xls文件的记录条数
	processBar = processBar || pBar;
	var fileSize = 10000;
	var totalPage = totalRecord%fileSize == 0 ? parseInt(totalRecord/fileSize) : parseInt(totalRecord/fileSize) + 1;
	genXlsFile(dataAddition, fileSize, 1, totalPage, sid, totalRecord, processBar, isApply);
}

function genXlsFile(dataAddition, fileSize, currPage, totalPage, sid, totalRecord, processBar, isApply){
	var percent = parseInt((currPage-1) * 80 / totalPage);
	processBar.setInfo("(" + percent + "%)正在生成 " + totalPage + " 个xls文件中的第 " + currPage + " 个..");
	processBar.setPercent(percent);
	var sql = document.getElementById("record:query_sql").value;
	sql = encodeURI(encodeURI(sql));
	var columnCnName = document.getElementById("record:columnsCnArray").value;
	columnCnName = encodeURI(encodeURI(columnCnName));
	var columnName = document.getElementById("record:columnsEnArray").value;
	columnName = encodeURI(encodeURI(columnName));
	var data = "operate=gen&fileSize="+fileSize;
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
					genZipFile(dataAddition, sid, processBar, isApply, totalRecord);
					return;
				}else{
					genXlsFile(dataAddition, fileSize, currPage, totalPage, sid, totalRecord, processBar, isApply);
				}	
			}else{
				processBar.hide();
				window.alert("生成文件过程中出现异常，请稍候再试");
			}			
		}
	};
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(data);
}

function genZipFile(dataAddition, sid, processBar, isApply, totalRecord){
	processBar.setPercent(80);
	processBar.setInfo("(80%)正在生成ZIP文件");
	
	var data = "operate=zip&sid=" + sid + "&totalRecord=" + totalRecord;
	if (isApply){
		data += "&isApply=1";
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
				var display_name = xmlResults.selectSingleNode("//results/fileName").text;
//				display_name = encodeURI(encodeURI(display_name));
				processBar.setInfo("(100%)完成!");
				processBar.setPercent(100);
				if (!isApply){
					processBar.getParentContainer().innerHTML += "<a href='" + rootPath + "/download?filePath="+sid+".zip&displayName=" + display_name + "&condition=" 
					// + encodeURI(encodeURI(document.getElementById("tdQueryCondition").innerHTML))
					+ document.getElementById("tdQueryCondition").innerHTML
					+ (dataAddition ? dataAddition : "")
					+ "&totalRecord=" + totalRecord
					+ "' target='_blank'>点击这里下载</a>"
				}else{
					processBar.getParentContainer().innerHTML += "下载申请已经成功发送!";
					window.frames["applyFrame"].document.getElementById("coverFrameText").innerHTML = "";
					window.frames["applyFrame"].document.getElementById("closeButtonDiv").style.display = "block";
				}
			}else{
				processBar.hide();
				window.alert("生成文件过程中出现异常，请稍候再试");
			}			
		}
	};
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(data);
}
