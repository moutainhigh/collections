var pBar, qcObj;
// ����dataSelect����ķ���
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
       textArray		: ["��ѯ��������", "Ԥ��"]
	});
	
	var obj = new dataSelectHTMLObj({
  		rootPath:rootPath,
  		selectPrefix:"table",
  		text_srcTitle:"*ѡ�����ݱ�",
  		text_selectSrcTitle:"*ѡ���ֶ�",
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
  		tempOption1.appendChild( document.createTextNode("��ҵ������") );
  		var tempOption2 = document.createElement("option");
  		tempOption2.value = "reg_indiv_base";
  		tempOption2.appendChild( document.createTextNode("�����") );
  		
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
  			alert("��ѡ���ֶΣ�");
  			return false;
  		}else{
  			columnNameArray = new Array();
  			columnIdArray = new Array();
  			for(var i = 0; i < columnObj.length; i++){
  				if(columnObj[i].colByName){//�ж��Ƿ���ڱ���
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
			// ������Ƿ��ǵǼ�������жϣ�������ǵǼ���������reg_org�����ж�
			// ����ǵǼ���������dom_district�����ж�
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
  			alert("��ѡ���ֶΣ�");
  			return false;
  		}else{
  			columnEnArray = new Array();
  			for(var i = 0; i < columnObj.length; i++){
  				if(columnObj[i].colByName){//�ж��Ƿ���ڱ���
  					addToArray(columnEnArray, columnObj[i].colByName);
  				}else{
  					addToArray(columnEnArray, columnObj[i].colName);
  				}
  			}
  		}
		var reg_org = $("input#reg_org").val();
		// �ύ��ѯ
		var sql = createSQL(true, reg_org);
//		alert(sql);
		// У��sql����Ƿ���ȷ
  		document.getElementById("record:query_sql").value = sql;//����SQL
  		document.getElementById("record:columnsCnArray").value = obj.getSelectedDataValues("text");//�����ֶ�������
  		document.getElementById("record:columnsEnArray").value = columnEnArray.toString();//�����ֶ�Ӣ����
  		
  		var selectedDataDisplay = "<font color='red'>���ݿ��</font>";
  		
  		var source_table = document.getElementById("source_table");
  		selectedDataDisplay += source_table.options[source_table.selectedIndex].text;
  		source_table = null;
  		selectedDataDisplay += "<br><font color='red'>��ѯ�ֶΣ�</font>" + document.getElementById("record:columnsCnArray").value;  		
  		selectedDataDisplay += "<br><font color='red'>��ѯ������</font>";
  		
  		var tdQueryCondition = document.getElementById("tdQueryCondition");
  		
  		var connTable = document.getElementById( "conditionTbl_qct" );				
		var rows = connTable.rows;
		if (rows.length > 0){
			for (var tempIndex = 0; tempIndex < rows.length; tempIndex ++){
				selectedDataDisplay += rows[tempIndex].cells[0].innerHTML;
			}
		}else{
			selectedDataDisplay += "(��)";
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
       id					: "pBar",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
       parentContainer		: "processBarDiv"  // �丸�ڵ��ID
    });
};

function ShowDialog(url){
	var iWidth=400; //���ڿ��
	var iHeight=200;//���ڸ߶�
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
	pBar.setInfo("(0%)���ڻ�ȡ���ؼ�¼����..");
	pBar.setPercent(0);
	try{
		var totalRecordSpanHtml = document.getElementById("pageList_frameX").contentWindow.document.getElementById("totalRecordSpan").innerHTML;
	}catch(e){
		try{
			var errorTD = document.getElementById("pageList_frameX").contentWindow.document.getElementById("errorTd");
			if (errorTD){
				pBar.setInfo("(0%)" + errorTD.innerHTML + ", ����ֹͣ!");
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
	
	// �ַ�����
	if (totalRecord == 0){
		pBar.hide();
		alert("��������Ϊ0�����������أ�");
		return false;
	}
	var has_purv   = $("input#has_purv").val();
	var max_result = $("input#max_result").val();
	if (has_purv == 0){
		pBar.hide();
		alert("��û�����ص�Ȩ�ޣ�");
		return false;
	}else if (parseInt(totalRecord) <= parseInt(max_result) || parseInt(max_result) == 0){
		// alert("ֱ�����أ�");
		var source_table = document.getElementById("source_table");
		var fileName = source_table.options[source_table.selectedIndex].text;
		fileName = encodeURI(encodeURI( fileName ));
		source_table = null;
		callDownloadServlet("&fileName=" + fileName, null, totalRecord);
	}else{
		// alert("�������أ�");
		pBar.hide();
		var sUrl = rootPath + "/dw/aic/download/query/inputDownloadCount.jsp?max_result="+ max_result +"&totalRecords="+totalRecord;
		ShowDialog(sUrl);
	}
};

// ���ú�̨�����ļ�servlet��ͳһ��ں���
// dataAddition: ���Ӳ���
function callDownloadServlet(dataAddition, isApply, totalRecord, processBar, sid){
	// ÿ��xls�ļ��ļ�¼����
	processBar = processBar || pBar;
	var fileSize = 10000;
	var totalPage = totalRecord%fileSize == 0 ? parseInt(totalRecord/fileSize) : parseInt(totalRecord/fileSize) + 1;
	genXlsFile(dataAddition, fileSize, 1, totalPage, sid, totalRecord, processBar, isApply);
}

function genXlsFile(dataAddition, fileSize, currPage, totalPage, sid, totalRecord, processBar, isApply){
	var percent = parseInt((currPage-1) * 80 / totalPage);
	processBar.setInfo("(" + percent + "%)�������� " + totalPage + " ��xls�ļ��еĵ� " + currPage + " ��..");
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
				window.alert("�����ļ������г����쳣�����Ժ�����");
			}			
		}
	};
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(data);
}

function genZipFile(dataAddition, sid, processBar, isApply, totalRecord){
	processBar.setPercent(80);
	processBar.setInfo("(80%)��������ZIP�ļ�");
	
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
				processBar.setInfo("(100%)���!");
				processBar.setPercent(100);
				if (!isApply){
					processBar.getParentContainer().innerHTML += "<a href='" + rootPath + "/download?filePath="+sid+".zip&displayName=" + display_name + "&condition=" 
					// + encodeURI(encodeURI(document.getElementById("tdQueryCondition").innerHTML))
					+ document.getElementById("tdQueryCondition").innerHTML
					+ (dataAddition ? dataAddition : "")
					+ "&totalRecord=" + totalRecord
					+ "' target='_blank'>�����������</a>"
				}else{
					processBar.getParentContainer().innerHTML += "���������Ѿ��ɹ�����!";
					window.frames["applyFrame"].document.getElementById("coverFrameText").innerHTML = "";
					window.frames["applyFrame"].document.getElementById("closeButtonDiv").style.display = "block";
				}
			}else{
				processBar.hide();
				window.alert("�����ļ������г����쳣�����Ժ�����");
			}			
		}
	};
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(data);
}
