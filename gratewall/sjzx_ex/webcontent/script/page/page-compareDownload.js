// alert("�����Ǳȶ�����JS�ļ���");
window.onload = compareDownload;
window.uploadFileSize = 0;
var rootPath = window.rootPath;
var steps, tableSelect, tableConn, columnSelect, compare;
var uploadFileProcessBar, downloadProcessBar;
// ��¼�������˵�·��
var uploadFilePath;
// ��¼���ɵı�ID
var tableNo;
// ��¼��ǰ��¼ID
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

// ��ʼ����ǰ�������
function initStepComponent(){
	return new stepHelp({
       id				: "steps",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
       parentContainer	: "stepsContainerDiv", // �丸�ڵ��ID
       textArray		: ["����ȶ��ļ�", "ѡ�����ȶ����ݱ�", "ѡ�񵼳�������", "ȷ���ȶ�����", "����Ԥ��"] // �ַ�������
	});
}

// ��ʼ�����ݿ��ѡ��
function initTableSelect(){
	return new dataSelectHTMLObj({
  		rootPath:rootPath,					//z��Ŀ¼·��
  		selectPrefix:"table",				//������id��׺
  		text_srcTitle:"*ѡ�������",			//����
  		text_selectSrcTitle:"*ѡ�����ݱ�",	//����
  		tableContainer:"tableSelectDiv",	//���ĸ�div������һ
  		txnCode:"/txn60210008.ajax",		//����������ı�ʱִ�еĽ���
  		paramStr:"?record:sys_id=",			//����������ı�ʱִ�еĽ��ײ���ǰ׺
  		fillObjId:"from_table",				//����������ı�ʱִ�еĽ������ݷ��غ����ĸ������������
  		optionValue:"table_no",				//���������������value����ȡֵ
  		optionText:"table_name_cn",			//�����������������ʾֵ
  		oncontentchange:updateRelationObj,	//����ѡ�������ݸı��ִ�еĺ���
  		beforecontentchange:scanConditions,	//����ѡ�������ݸı�ǰ��ִ�еĺ���
  		addtionalParam:{ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"} //��ѡ������Ҫ��ӵĸ�������
  	});
}

// ��ʼ����������
function initConnectCondition(){
	return new connectCondition({
  		id:"conn",
  		parentContainer:"tableConnectConditionDiv",
  		getColumnSrcPrefix:rootPath+"/txn60210009.ajax?record:table_no="
  	});
}

// ��ʼ�������ʾ��
function initResultColumns(){
	return new dataSelectHTMLObj({
		rootPath:rootPath,
		selectPrefix:"columnTable",
		text_srcTitle:"*ѡ�����ݱ�",
		text_selectSrcTitle:"*ѡ���ֶ�",
		tableContainer:"columnsSelectDiv",
		txnCode:"/txn60210013.ajax",
		paramStr:"?record:table_no=",
		fillObjId:"from_columnTable",
		optionValue:"column_no",
		optionText:"column_name_cn",
		addtionalParam:{tblid:"table_no", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn", colByName:"column_byname"}
	});
}

// ��ʼ���Ƚ�����
function initCompareCondition(){
	return new comparePlugin({
		id:"compare",
  		parentContainer:"tableCompareDiv",
  		getColumnSrcPrefix:rootPath+"/txn60210013.ajax?record:table_no="
	});
}

// ��ʼ���ļ��ϴ�������
function initUploadFileBar(){
	return new processBar({
		id:"uploadFileBar",
		parentContainer:"uploadFileProcessBarDiv"
	});
}

// ��ʼ�����ؽ�����
function initDownloadBar(){
	return new processBar({
		id:"downloadBar",
		parentContainer:"downloadProcessBarDiv"
	});
}

// ��ʼ����ʾ�����
function initTotalTable(){
	return new generateTotalTable({
       id					: "totalTable",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
       parentContainer		: "totalTableDiv", // �丸�ڵ��ID
       columnSelect			: "selected_columnTable",
       tableSelect			: "source_columnTable",
       connectConditionTable: "conditionTbl_conn",
       compareTable			: "conditionTbl_compare",
       tableParamOnColumn	: ["tblid", "tblNameCn", "tblName"],  // ����Ϊ��ID(Value)����������(Text)����Ӣ����(��������)
       sysParamOnTable		: ["ztid", "ztName", "ztNo"],   	  // ����Ϊ����ID(Value)������������(Text)������Ӣ����(��������)
       columnParamOnColumn	: ["colId", "colNameCn", "colName"]	  // ����Ϊ�ֶ�ID(Value)���ֶ�������(Text)���ֶ�Ӣ����(��������)
    });
} 

function updateRelationObj(type){
	tableConn.setDataFromSelect("selected_table");
}

// �򿪲�ѯ���
function openResultWindow(isModify){
//	var page = new pageDefine(rootPath + "/dw/aic/download/compare/preview.jsp?table_no="
//		 + tableNo + "&download_compare_id=" + download_compare_id, "�鿴��ʱ����", "modal", 800, 450);
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

// ��ת����N��
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

// "������ʱ��"��ť����Ӧ����
function genTempTableHandler(){
	var uploadFileInput = document.getElementById("record:uploadFileInput").value;
	if (!uploadFileInput){
		alert("��ѡ���ϴ��ļ�!");
		return false;
	}
	
	// ���������".xls"��β
	if ( uploadFileInput.search( /\.(xlsx?)|(txt)$/i ) < 0){
		alert("��ѡ����ļ����Ǻ��ʵ�EXCEL�����ı��ļ�!");
		return false;
	}
	
	var tableName = document.getElementById("record:table_name").value;
	if (tableName.search(/^\w{5,20}$/i) < 0){
		alert("������ı������Ϸ�!");
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
	uploadFileProcessBar.setInfo("(" + 0 + "%)�����ϴ��ļ�");
	uploadFileProcessBar.setPercent(0);
	
	document.forms[0].submit();
}

// У������Ƿ����
function checkTableNameIsExist(){
	// ����Ѿ�readOnly�ˣ�����������̨У����
	if (event.srcElement.readOnly){
		return false;
	}
	
	var tableName = document.getElementById("record:table_name").value;
	
	//Ҫ����������ĸ��ͷ���ҳ�����5~20֮��
	if (tableName.search(/^\w{5,20}$/i) < 0){
		document.getElementById("genTempTable").disabled=true;
		return false;
	}
	
	tableName = "DL" + tableName;
	
	$.get(rootPath + "/testsql?tableName=" + tableName, 
		function(xml){
//			alert("����xml:" + xml.xml);
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

// ������ʱ��
function createTempTable(filePath, tableName){
	var tableName = document.getElementById("record:table_name").value;
	if (!tableName){
		alert("��������ʱ������");
		return false;
	}
	
	$.post(rootPath + "/downloadCompare", 
		{filePath:uploadFilePath, tableName:tableName.toUpperCase(), tableNo:tableNo},
		function(data){
			// alert("���ؽ��:" + data);
			uploadFileProcessBar.setPercent(100);
			uploadFileProcessBar.setInfo("(100%)�ѳɹ��ϴ����������ݽ�����ʱ��!");
//			document.getElementById("viewDataLink").style.display = "block";
//			document.getElementById("step1_next_button").disabled = false;
			openResultWindow(); // �������ݴ���
		}
	);
}


/**
 * ɨ��Ҫɾ���ı��Ƿ��Ѿ�������
 * @param action: delOne | delAll
 * @param del   : �Ƿ�ͬʱɾ�����õ�����
 * @param confirmed : �Ƿ������ʾ
 * @return boolean �Ƿ��������
 */
function scanConditions(action, del, confirmed){
	//�����ѡ���ݱ����������
	var selectObj = document.getElementById("selected_" + tableSelect.selectPrefix);
	if(action == "delOne"){//�������ǡ�ɾ��һ�����İ�ť��ִ�д˷�֧
		//��顰���������������Ƿ������˴˱�
		var connTblArray = tableConn.getTableId();//��ñ����������õı�
		var compareArray = compare.getTableId(); // ��ñȽ��������õı�
  		var columns = document.getElementById("selected_"+ columnSelect.selectPrefix).options;//�����ѡ���ֶ����������
		var optionArray = getSelectedOptions("selected_" + tableSelect.selectPrefix);//�������ѡ�е�������ѡ��
		for(var i=0; i < optionArray.length; i++){
			if(existInArray(connTblArray, optionArray[i].value) != -1){
	  			if(del){
	  				if(!confirmed){
		  				if(!confirm("ϵͳ��⵽�˱��ڡ��������������б����ã��Ƿ�ͬʱɾ����ص�������")){
		  					return false;
		  				}
	  				}
	  				confirmed = true;//��Ϊtrue���������ġ���ѯ����������ʾ�ֶΡ��ظ���ʾ
	  				if(tableConn.connJSONArray){
	  					for(var j = 0; j < tableConn.connJSONArray.length; j++){
	  						var connJSONObj = tableConn.connJSONArray[j];
	  						if(optionArray[i].value == connJSONObj.tableOneId || optionArray[i].value == connJSONObj.tableTwoId){
	  							tableConn.deleteCondition(j);
	  							j--;
	  							//scanConditions("delOne", true, true);//�ݹ���ã���ɾ�������������
	  						}
	  					}
	  				}
	  			}else{
	  				alert("ϵͳ��⵽�˱��ڡ��������������б����ã���ֹɾ����");
	  				return false;
	  			}
	  		}
	  		
	  		if(existInArray(compareArray, optionArray[i].value) != -1){
	  			if(del){
	  				if(!confirmed){
		  				if(!confirm("ϵͳ��⵽�˱��ڡ��Ƚ��������б����ã��Ƿ�ͬʱɾ����ص�������")){
		  					return false;
		  				}
	  				}
	  				confirmed = true;//��Ϊtrue���������ġ���ѯ����������ʾ�ֶΡ��ظ���ʾ
	  				if(compare.connJSONArray){
	  					for(var j = 0; j < compare.connJSONArray.length; j++){
	  						var connJSONObj = compare.connJSONArray[j];
	  						if(optionArray[i].value == connJSONObj.tableOneId || optionArray[i].value == connJSONObj.tableTwoId){
	  							compare.deleteCondition(j);
	  							j--;
	  							//scanConditions("delOne", true, true);//�ݹ���ã���ɾ�������������
	  						}
	  					}
	  				}
	  			}else{
	  				alert("ϵͳ��⵽�˱��ڡ��������������б����ã���ֹɾ����");
	  				return false;
	  			}
	  		}
	  		
	  		if(columns.length != 0){
	  			var exist = false;
	  			for(var j = 0; j < columns.length; j++){
	  				var colTblId = columns[j].tblid;
	  				if(colTblId == optionArray[i].value){
	  					exist = true;
	  					columns[j].selected = true;//��Ϊѡ�У����һ��ɾ��
	  					//break;
	  				}else{
	  					columns[j].selected = false;//����Ѿ�ѡ�У�����Ϊ��ѡ��������ɾ
	  				}
	  			}
	  			if(exist){
	  				if(del){
	  					if(!confirmed){
			  				if(!confirm("ϵͳ��⵽��Ҫ��ʾ�˱���ֶΣ��Ƿ�ͬʱɾ����ص��ֶΣ�")){
			  					return false;
			  				}
	  					}
		  					
		  				columnSelect.clickToLeft("tblid", true);
		  			}else{
		  				alert("ϵͳ��⵽��Ҫ��ʾ�˱���ֶΣ���ֹɾ����");
		  				return false;
		  			}
	  			}
	  		}
		}
  		connTblArray = null;
  		columns = null;
  		optionArray = null;
	}else if (action == "delAll"){//�������ǡ�ɾ��ȫ�����İ�ť��ִ�д˷�֧
		if(selectObj.options.length != 0){
			for(var i = 0; i< selectObj.options.length; i++){
				selectObj.options[i].selected = true;
				if(!scanConditions("delOne", del, true)){//ѭ�����á�ɾ��һ�����ķ�֧
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

// ����SQL���
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
  			alert("��ѡ���ֶΣ�");
  			return false;
  		}
  		
		var columnNameArray = new Array();
		var resultColumnNameArray = new Array(); 
		for(var i = 0; i < columnObj.length; i++){
			// ������ʱ��
			if (columnObj[i].tblid != tableNo){
				if(columnObj[i].colByName){//�ж��Ƿ���ڱ���
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
  			alert("��ѡ���ֶΣ�");
  			return false;
  		}
  		
		var columnNameArray = new Array();
		var codeColumnNameArray = new Array(); 
		var resultColumnNameArray = new Array(); 
		var resultColumnCnNameArray = new Array();
		for(var i = 0; i < columnObj.length; i++){
			// ������ʱ��
			if (columnObj[i].tblid != tableNo){
				if(columnObj[i].colByName){//�ж��Ƿ���ڱ���
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
//		alert("������:" + resultColumnCnNameArray.toString());
		
		document.getElementById("columnCnNameInput").value = resultColumnCnNameArray.toString();
		document.getElementById("columnNameInput").value = codeColumnNameArray.toString();
		
		// ��ӱȶ��������Ĳ�ѯ
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
		alert("��û��������!");
	}
	
	selectedTables = null;
	paramStr = null;
//	alert("sql:" + sql);
	return sql;
}

// ִ�а�ť����Ӧ����
function executeHander(){
	// �������������
	document.getElementById("downloadContainerDiv").style.display = "none";
	document.getElementById("downloadProcessBarDiv").style.display = "none";
	document.getElementById("linkDiv").innerHTML = "";
	
	var sql = createSQL(4);
	var tableName = document.getElementById("record:table_name").value;
	sql = "create table " + tableName + "_LS as " + sql;
	_showProcessHintWindow( "����ִ�бȶԣ����Ժ�....." );
	document.getElementById("execute_button").disabled = true;
	$.post(rootPath + "/downloadExecute",
		{sql : sql, tableName : tableName},
		function(xml){
			_hideProcessHintWindow();
			var hasSuccess = $("results", xml).find("hasSuccess").text();
			if (hasSuccess == true || hasSuccess == "true"){
				alert("�ȶ�ִ�гɹ����ȶԼ�¼����Ϊ" + $("results", xml).find("totalCount").text());
				var downloadContainerDiv = document.getElementById("downloadContainerDiv");
				downloadContainerDiv.style.display = "block";
				downloadContainerDiv = null;
			}else{
				alert("�ȶ�ִ��ʧ�ܣ����Ժ����ԣ�");
			}
			document.getElementById("execute_button").disabled = false;
		}
	);
}

// ��һ��"��һ��"��ť����Ӧ����
function step1_next(){
	var remark = document.getElementById("record:remark").value;
	if (remark.search(/^\s*$/i) >= 0){
		alert("�����뱸ע��");
		return false;
	}else{
		var page = new pageDefine("/txn60500007.ajax");
		_showProcessHintWindow( "���ڴ洢��ע�����Ժ�....." );
		page.addValue(remark, "record:remark");
		page.addValue(download_compare_id, "record:download_compare_id");
		page.callAjaxService("doCallBack");
	}
}

function doCallBack(errCode, errDesc, xmlResults){
	if(errCode=='000000'){
		_hideProcessHintWindow();
		document.getElementById("step1_next_button").value = "��һ��";
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
	// У����������
	if(document.getElementById("selected_"+tableSelect.selectPrefix).options.length == 0){
		alert("��ѡ�����ݱ�");
		return;
	}
	
	if(tableConn.validateConnCondition("selected_"+tableSelect.selectPrefix)){
		document.getElementById("step2_next_button").disabled = true;
		_showProcessHintWindow( "����У����������������Ժ�....." );
		var sql = createSQL(2);
//		alert(sql);
		$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
			var result = xml.selectSingleNode("/results/sql").text;
			if(result=="false"){
				_hideProcessHintWindow();
				alert("SQLУ��ʧ�ܣ����������");
				document.getElementById("step2_next_button").disabled = false;						
				return false;
			}else{
				_hideProcessHintWindow();	
				columnSelect.updateColumnTables("selected_"+tableSelect.selectPrefix, 
					"source_"+columnSelect.selectPrefix);
				// ����ʱ���������������ݱ�������
				var tableName = document.getElementById("record:table_name").value;
				var source = document.getElementById("source_"+columnSelect.selectPrefix);
				var tempTableOption = document.createElement("OPTION");
				tempTableOption.appendChild(document.createTextNode("�ȶԱ�(" + tableName + ")"));
				tempTableOption.value = tableNo;
				tempTableOption.ztid = "";
				tempTableOption.tblName = tableName;
				tempTableOption.ztName = "��ʱ������";
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
		alert("��ѡ���ѯ�ֶΣ�");
		return false;
	}
	
	document.getElementById("step3_next_button").disabled = true;	
	_showProcessHintWindow( "����У�鵼����������Ժ�....." );
	var sql = createSQL(3);
//	alert(sql);
	$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
		var result = xml.selectSingleNode("/results/sql").text;
		if(result=="false"){
			_hideProcessHintWindow();
			alert("SQLУ��ʧ�ܣ����������");
			document.getElementById("step3_next_button").disabled = false;						
			return false;
		}else{
			_hideProcessHintWindow();	
			var tableName = document.getElementById("record:table_name").value;
			compare.setTableTwoData("�ȶԱ�(" + tableName + ")", tableNo, tableName);
			compare.setDataFromSelect("selected_" + tableSelect.selectPrefix);
			document.getElementById("step3_next_button").disabled = false;
			goToStep(3);
		}
	});
}

function step4_next(){
	if (compare.connJSONArray.length <= 0){
		alert("��û��ѡ���καȶ�������");
		return false;
	}
	document.getElementById("step4_next_button").disabled = true;	
	_showProcessHintWindow( "����У��Ƚ����������Ժ�....." );
	var sql = createSQL(4);
//	alert(sql);
	$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
		var result = xml.selectSingleNode("/results/sql").text;
		if(result=="false"){
			_hideProcessHintWindow();
			alert("SQLУ��ʧ�ܣ����������");
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
 * �����ļ�
 * @param fileType:�ļ�����
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
	downloadProcessBar.setInfo("(" + 0 + "%)���ڻ�ȡ�ȶԳɹ���¼����");
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

// ���ɱȶԳɹ��ļ�
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
		alert("�ļ����Ͳ���ȷ!");
		return false;
	}
}

// ���ɱȶ�ʧ���ļ�
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
		alert("�ļ����Ͳ���ȷ!");
		return false;
	}
}

// ���ɱ���
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
		if (xmlObj.readyState == 4) { // �ж϶���״̬
			if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
				xmlResults = xmlObj.responseXML;
				genZipFile(dataAddition, sid);
			}
		}
	}
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(post);	
}

//����XMLHttpRequest����
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
	if (xmlObj.readyState == 4) { // �ж϶���״̬
		if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
			var xmlResults = xmlObj.responseXML;
			var totalRecords = xmlResults.selectSingleNode("//results/totalRecord");
			totalRecords = parseInt(totalRecords.text);
			document.getElementById("totalRecordsHidden").value = totalRecords;
			
			downloadProcessBar.setInfo("(" + 5 + "%)���ڻ�ȡ�ȶ�ʧ�ܼ�¼����");
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
				if (xmlObj.readyState == 4) { // �ж϶���״̬
					if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
						xmlResults = xmlObj.responseXML;
						var failedTotalRecords = xmlResults.selectSingleNode("//results/totalRecord");
						failedTotalRecords = parseInt(failedTotalRecords.text);
						document.getElementById("totalRecordsFailedHidden").value = failedTotalRecords;
						
						if (document.getElementById("totalRecordsFailedHidden").value == "0"
							&& document.getElementById("totalRecordsHidden").value == "0"){
							downloadProcessBar.setPercent(100);
							downloadProcessBar.setInfo("(" + 100 + "%)�ܼ�¼��Ϊ0���������أ�");
						}
						genSuccessFiles(dataAddition, fileType);
					}
				}
			}
			xmlObj.setrequestheader("cache-control","no-cache"); 
			xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
			xmlObj.send(post);			
		} else { //ҳ�治����
			alert("��ȡ��¼����ʱ�����쳣");
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
	
	downloadProcessBar.setInfo("(" + percent + "%)�������� " + totalPage + " ��" + fileType + (bSuccess ? "�ɹ�" : "ʧ��") + "�ļ��еĵ� " + currPage + " ��..");
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
				window.alert("�����ļ������г����쳣�����Ժ�����");
			}			
		}
	};
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(data);
}

function genZipFile(dataAddition, sid){
	downloadProcessBar.setPercent(80);
	downloadProcessBar.setInfo("(80%)��������ZIP�ļ�");
	
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
				downloadProcessBar.setInfo("(100%)���!");
				downloadProcessBar.setPercent(100);
				document.getElementById("linkDiv").innerHTML = "<a href='" + rootPath + "/download?filePath="+sid+".zip&displayName=" + display_name
					+ "' target='_blank'>�����������</a>";
			}else{
				downloadProcessBar.hide();
				window.alert("�����ļ������г����쳣�����Ժ�����");
			}			
		}
	};
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(data);
}

// ���ذ�ť�Ļص�
function goBackHandler(){
	goBack();
};

function dataErrorHandler(){
	var error = window.confirm("�ò�������ɾ���ղ��ϴ�������, ��ȷ������������");
	if (error){
		_showProcessHintWindow( "����ɾ����ʱ�����ʱ�����ݣ����Ժ�....." );
		var page = new pageDefine("/txn60500005.ajax");
		page.addValue(download_compare_id , "primary-key:download_compare_id");
		page.callAjaxService("doDeteleDataCallBack");
	}
}

function doDeteleDataCallBack(errCode, errDesc, xmlResults){
	if(errCode=='000000'){
		_hideProcessHintWindow();
		alert("��ʱ�����ʱ������ɾ���ɹ����������ϴ��ļ���");
		
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
		document.getElementById("step1_next_button").value = "��һ��";
		document.getElementById("step1_next_button").disabled = true;
	}else{
		alert(errDesc);
		_hideProcessHintWindow();
	}
}