// ��������ȫ�ֱ���, ����Ϊ��ѯ���������Ƿ�ѡ�������ʾ���Ƿ�ѡ��������ʾ��
var qcObj; 
var choosedRegIndBas = false; 
var choosedRegBusEnt = false;

window.onload = function(){
		var rootPath = window.rootPath;
		var steps = new stepHelp({
	           id				: "steps",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
	           parentContainer	: "stepsContainerDiv", // �丸�ڵ��ID
	           textArray		: ["��ѯ��Χ����", "��ѯ�������", "ѡ����ʾ��", "Ԥ��"]		 // �ַ�������
	       });
		
	  	function updateRelationObj(type){
	  		connObj.setDataFromSelect("selected_table");
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
	  		var selectObj = document.getElementById("selected_" + obj.selectPrefix);
	  		if(action == "delOne"){//�������ǡ�ɾ��һ�����İ�ť��ִ�д˷�֧
	  			//��顰���������������Ƿ������˴˱�
	  			var connTblArray = connObj.getTableId();//��ñ����������õı�
		  		var qcTblArray = qcObj.getTableId();//��ò�ѯ���������õı�
		  		var columns = document.getElementById("selected_"+ obj2.selectPrefix).options;//�����ѡ���ֶ����������
	  			var optionArray = getSelectedOptions("selected_" + obj.selectPrefix);//�������ѡ�е�������ѡ��
	  			for(var i=0; i < optionArray.length; i++){
	  				if(existInArray(connTblArray, optionArray[i].value) != -1){
			  			if(del){
			  				if(!confirmed){
				  				if(!confirm("ϵͳ��⵽�˱��ڡ��������������б����ã��Ƿ�ͬʱɾ����ص�������")){
				  					return false;
				  				}
			  				}
			  				confirmed = true;//��Ϊtrue���������ġ���ѯ����������ʾ�ֶΡ��ظ���ʾ
			  				if(connObj.connJSONArray){
			  					for(var j = 0; j < connObj.connJSONArray.length; j++){
			  						var connJSONObj = connObj.connJSONArray[j];
			  						if(optionArray[i].value == connJSONObj.tableOneId || optionArray[i].value == connJSONObj.tableTwoId){
			  							connObj.deleteCondition(j);
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
			  		
			  		//��顰��ѯ���������Ƿ������˴˱�
			  		if(existInArray(qcTblArray, optionArray[i].value) != -1){
			  			if(del){
			  				if(!confirmed){
				  				if(!confirm("ϵͳ��⵽�˱��ڡ���ѯ�����������б����ã��Ƿ�ͬʱɾ����ص�������")){
				  					return false;
				  				}
			  				}
			  				confirmed = true;//��Ϊtrue���������ġ���ʾ�ֶΡ��ظ���ʾ
			  				if(qcObj.connJSONArray){
			  					for(var j = 0; j < qcObj.connJSONArray.length; j++){
			  						var connJSONObj = qcObj.connJSONArray[j];
			  						if(optionArray[i].value == connJSONObj.tableOneId ){
			  							qcObj.deleteCondition(j);
			  							scanConditions("delOne", true, true);//�ݹ���ã���ɾ�������������
			  						}
			  					}
			  				}
			  			}else{
			  				alert("ϵͳ��⵽�˱��ڡ���ѯ�������������б����ã���ֹɾ����");
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
				  					
				  				obj2.clickToLeft("tblid", true);
				  			}else{
				  				alert("ϵͳ��⵽��Ҫ��ʾ�˱���ֶΣ���ֹɾ����");
				  				return false;
				  			}
			  			}
			  		}
	  			}
		  		connTblArray = null;
		  		qcTblArray = null;
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
	  	
	  	/**
	  	 * ����SQL���
	  	 * @param loadColumn �Ƿ�����ֶε�SQL
	  	 * @return String 
	  	 */
	  	function createSQL(step, isNoNeedValue){
	  		var selectedTables = document.getElementById("selected_"+obj.selectPrefix).options;
			var sql = "";
			
			if(step == 1){
				sql = "SELECT " + selectedTables[0].tblName + ".* FROM ";
			
				for(var i = 0; i < selectedTables.length; i++){
					var tblName = selectedTables[i].tblName;
					sql += tblName;
					if( i == (selectedTables.length -1) )
						continue;
					else
						sql += ", ";
				}
				
				var paramStr = connObj.getconnConditionStr();
				if(paramStr.trim() != ""){
					sql += " WHERE "+ paramStr;
				}
			}else if (step == 2){
				sql = "SELECT " + selectedTables[0].tblName + ".* FROM ";
			
				for(var i = 0; i < selectedTables.length; i++){
					var tblName = selectedTables[i].tblName;
					sql += tblName;
					if( i == (selectedTables.length -1) )
						continue;
					else
						sql += ", ";
				}
				
				var paramStr = connObj.getconnConditionStr();
				if(paramStr.trim() != ""){
					sql += " WHERE "+ paramStr;
				}
				
				if(sql.indexOf("WHERE") > -1){
					if(qcObj.getQueryConditionStr().trim() != ""){
						if (isNoNeedValue){
							sql += " AND (" + qcObj.getQueryConditionStrWithoutValue() + ")";
						}else{
							sql += " AND (" + qcObj.getQueryConditionStr() + ")";
						}
					}
				}else{
					if(qcObj.getQueryConditionStr().trim() != ""){
						if (isNoNeedValue){
							sql += " WHERE (" + qcObj.getQueryConditionStrWithoutValue() + ")";
						}else{
							sql += " WHERE (" + qcObj.getQueryConditionStr() + ")";
						}
					}
				} 
			}else{
				var columnObj = document.getElementById("selected_"+obj2.selectPrefix).options;
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
				sql = "SELECT " + columnNameArray.toString() + " FROM ";
			
				for(var i = 0; i < selectedTables.length; i++){
					var tblName = selectedTables[i].tblName;
					sql += tblName;
					if( i == (selectedTables.length -1) )
						continue;
					else
						sql += ", ";
				}
				
				var paramStr = connObj.getconnConditionStr();
				if(paramStr.trim() != ""){
					sql += " WHERE (" + paramStr + ")";
				}
				
				if(sql.indexOf("WHERE") > -1){
					if(qcObj.getQueryConditionStr().trim() != ""){
						if (isNoNeedValue){
							sql += " AND (" + qcObj.getQueryConditionStrWithoutValue() + ")";
						}else{
							sql += " AND (" + qcObj.getQueryConditionStr() + ")";
						}
//						sql += " AND (" + isNoNeedValue ? qcObj.getQueryConditionStrWithoutValue() : qcObj.getQueryConditionStr() + ")";
					}
				}else{
					if(qcObj.getQueryConditionStr().trim() != ""){
						if (isNoNeedValue){
							sql += " WHERE (" + qcObj.getQueryConditionStrWithoutValue() + ")";
						}else{
							sql += " WHERE (" + qcObj.getQueryConditionStr() + ")";
						}
					}
				}
			}
			
			// �õ����ش���
			var reg_org = document.getElementById("reg_org").value;
			// �����������أ������ش��벻Ϊ�յ�ʱ��ע������reg_org == ""����ʡ��Ϊreg_org������reg_orgΪ0��ʱ���������ƣ�
			if (funcType && funcType=="download" && reg_org != ""){
				var sqlConn = sql.search(/\s+where\s+/i) >= 0 ? " AND " : " WHERE ";
				// ѡ���˸����ûѡ�������
				if (choosedRegIndBas && !choosedRegBusEnt){
					sql += sqlConn + " reg_indiv_base.reg_org like '" + reg_org + "%' ";
					// sql += sqlConn + " reg_indiv_base.dom_district = '" + reg_org + "' ";
				}else if (!choosedRegIndBas && choosedRegBusEnt){ // ѡ���������ûѡ������
					// sql += sqlConn + " reg_bus_ent.reg_org like '" + reg_org + "%' ";
					sql += sqlConn + " reg_bus_ent.dom_district = '" + reg_org + "' ";
				}
			}
			
			selectedTables = null;
			paramStr = null;
			return sql;
	  	}
	  	
	  	// ����һ����һ����ť�¼�
	  	document.getElementById("toStep2Button").onclick = function(){
	  		if(document.getElementById("selected_"+obj.selectPrefix).options.length == 0){
	  			alert("��ѡ�����ݱ�");
	  			return;
	  		}
	  		
	  		// ����������ع�����, �ж�û��ѡ�����������߻�������
	  		if (funcType && funcType == "download"){
	  			var selectedObj = document.getElementById("selected_"+obj.selectPrefix);
	  			var optionArray = selectedObj.options;
	  			choosedRegBusEnt = false;
	  			choosedRegIndBas = false;
	  			for (var iter = 0; iter < optionArray.length; iter ++){
	  				// ���ѡ��������
	  				if (optionArray[iter].tblName.toUpperCase() == "REG_BUS_ENT"){
	  					choosedRegBusEnt = true;
	  				}
	  				if (optionArray[iter].tblName.toUpperCase() == "REG_INDIV_BASE"){
	  					choosedRegIndBas = true;
	  				}
	  			}
	  			if (!choosedRegBusEnt && !choosedRegIndBas){
	  				alert("����������ѡ��һ������!");
	  				return false;
	  			}else if (choosedRegBusEnt && choosedRegIndBas){
	  				alert("������ͬʱѡ����������!");
	  				return false;
	  			}
	  			selectedObj = null;
	  		}
	  			
	  		if(connObj.validateConnCondition("selected_"+obj.selectPrefix)){
	  			document.getElementById("toStep2Button").disabled = true;
	  			_showProcessHintWindow( "����У����������������Ժ�....." );
	  			var sql = createSQL(1);
	  			$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
					var result = xml.selectSingleNode("/results/sql").text;
					if(result=="false"){
						_hideProcessHintWindow();
						alert("SQLУ��ʧ�ܣ����������");
						document.getElementById("toStep2Button").disabled = false;						
						return false;
					}else{
						_hideProcessHintWindow();
	  					qcObj.setDataFromSelect("selected_table");
						document.getElementById("step2DIV").style.display = "block";
						document.getElementById("step2ButtonDiv").style.display = "block";
						document.getElementById("step1DIV").style.display = "none";
						steps.setCurrentStep(1);
						document.getElementById("toStep2Button").disabled = false;
					}
				});
	  		}
	  	}
	  	
	  	// ���������һ����ť�¼�
	  	document.getElementById("toStep3Button").onclick = function(){
	  		var sql = createSQL(2);
	  		//������һ������һ����ť
			document.getElementById("toStep3Button").disabled = true;
	  		document.getElementById("preStep1Button").disabled = true;
  			_showProcessHintWindow( "����У����������������Ժ�....." );
	  		$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
				var result = xml.selectSingleNode("/results/sql").text;
				if(result=="false"){
					_hideProcessHintWindow();
					alert("SQLУ��ʧ�ܣ����������");
					//������һ������һ����ť
					document.getElementById("toStep3Button").disabled = false;	
	  				document.getElementById("preStep1Button").disabled = false;					
					return false;
				}else{
					_hideProcessHintWindow();
					obj2.updateColumnTables("selected_"+obj.selectPrefix, "source_"+obj2.selectPrefix);
					document.getElementById("step3DIV").style.display = "block";
					document.getElementById("step3ButtonDiv").style.display = "block";
					document.getElementById("step2DIV").style.display = "none";
					document.getElementById("step2ButtonDiv").style.display = "none";
					steps.setCurrentStep(2);
					//������һ������һ����ť
					document.getElementById("toStep3Button").disabled = false;
	  				document.getElementById("preStep1Button").disabled = false;
				}
			});
	  	}
	  	
	  	// ���������һ����ť�¼�
	  	document.getElementById("preStep1Button").onclick = function(){
			document.getElementById("step2DIV").style.display = "none";
			document.getElementById("step2ButtonDiv").style.display = "none";
			document.getElementById("step1DIV").style.display = "block";
			steps.setCurrentStep(0);
	  	}
	  	
	  	// ��������Ԥ����ť�¼�
	  	document.getElementById("preViewButton").onclick = function(){
	  		if(document.getElementById("selected_"+obj2.selectPrefix).options.length == 0){
	  			alert("��ѡ���ѯ�ֶΣ�");
	  			return;
	  		}
	  		var columnObj = document.getElementById("selected_"+obj2.selectPrefix).options;
	  		var columnEnArray
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
	  		
	  		//У��SQL
	  		var sql = createSQL(3);
	  		var dsql = createSQL(3, true);
  			_showProcessHintWindow( "����У����������������Ժ�....." );
  			
  			//������һ������һ����ť
			document.getElementById("preStep2Button").disabled = true;
			document.getElementById("preViewButton").disabled = true;
			// alert(sql);
	  		$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
				var result = xml.selectSingleNode("/results/sql").text;
				if(result=="false"){
					_hideProcessHintWindow();
					alert("SQLУ��ʧ�ܣ����������");
					//������һ������һ����ť
					document.getElementById("preViewButton").disabled = false;
	  				document.getElementById("preStep2Button").disabled = false;
					return false;
				}else{
					_hideProcessHintWindow();
					var totalTable = new generateTotalTable({
			           id					: "totalTable",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
			           parentContainer		: "totalTableDiv", // �丸�ڵ��ID
			           columnSelect			: "selected_columnTable",
			           tableSelect			: "selected_table",
			           connectConditionTable: "conditionTbl_conn",
			           queryConditionTable	: "conditionTbl_qct",
			           tableParamOnColumn	: ["tblid", "tblNameCn", "tblName"],  // ����Ϊ��ID(Value)����������(Text)����Ӣ����(��������)
			           sysParamOnTable		: ["ztid", "ztName", "ztNo"],   	  // ����Ϊ����ID(Value)������������(Text)������Ӣ����(��������)
			           columnParamOnColumn	: ["colId", "colNameCn", "colName"]	  // ����Ϊ�ֶ�ID(Value)���ֶ�������(Text)���ֶ�Ӣ����(��������)
			        });
			  		totalTable.write();
			  		// window.pageList_frameX.location.href = rootPath+"/dw/aic/gjcx/cxtjdy/showResults.jsp?colIds="+columnIdArray.toString()+"&sql="+sql;
			  		document.getElementById("record:query_sql").value = sql;//����SQL
			  		document.getElementById("record:query_dsql").value = dsql;//����dSQL
			  		document.getElementById("record:columnsCnArray").value = obj2.getSelectedDataValues("text");//�����ֶ�������
			  		document.getElementById("record:columnsEnArray").value = obj2.getMixedDataValues(['tblName','colName','colByName'],'.');//columnEnArray.toString();//�����ֶ�Ӣ����
					document.getElementById("step3DIV").style.display = "none";
					document.getElementById("step3ButtonDiv").style.display = "none";
					document.getElementById("step4DIV").style.display = "block";
					document.getElementById("step4ButtonDiv").style.display = "block";
					steps.setCurrentStep(3);
					//alert(document.getElementById('totalTableDiv').outerHTML);
					document.getElementById('sqlCondition').value=document.getElementById('totalTableDiv').outerHTML;
					//document.getElementsByTagName("form")[0].action=document.getElementsByTagName("form")[0].action+'?sqlCondition='+document.getElementById('totalTableDiv').outerHTML;
					document.getElementsByTagName("form")[0].submit();
					// �������ذ�ť
					/*if (document.getElementById("downloadButton")){
						document.getElementById("downloadButton").disabled = true;
					}*/
					
					//������һ������һ����ť
	  				document.getElementById("preStep2Button").disabled = false;
					document.getElementById("preViewButton").disabled = false;
				}
			});
	  		
	  	}
	  	
	  	// ����������һ����ť�¼�
	  	document.getElementById("preStep2Button").onclick = function(){
			document.getElementById("step3DIV").style.display = "none";
			document.getElementById("step3ButtonDiv").style.display = "none";
			document.getElementById("step2DIV").style.display = "block";
			document.getElementById("step2ButtonDiv").style.display = "block";
			steps.setCurrentStep(1);
	  	}
	  	
	  	var txnCode = "/txn60210003.ajax";//���״��룬���ajaxXml��Ϊ�գ���ִ���޸�
	  	// �����ĵı��水ť�¼�
	  	if (document.getElementById("saveButton")){
			document.getElementById("saveButton").onclick = function(){
		  		var paramArray;
		  		if(ajaxXml){
		  			var name = ajaxXml.selectSingleNode("//record/name").text;
			  		var id = ajaxXml.selectSingleNode("//record/sys_advanced_query_id").text;
			  		paramArray = {cxmc:name,cxid:id};
			  		txnCode = "/txn60210002.ajax";
		  		}
		  		
		  		var paramArray = window.showModalDialog(rootPath + "/dw/aic/gjcx/cxtjdy/confirmName.jsp",paramArray,"dialogWidth:35;dialogHeight:20;scroll:no;");
				if(paramArray){
					toAdd(paramArray);
				}
		  	}
		}
	  	
	  	var xmlObj = false;

        //����XMLHttpRequest����
	    function createXMLHttpRequest() {
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
	  	
	  	function toAdd(paramArray){
	  		
	  		var sql = createSQL(3);
	  		//alert(sql);
			_showProcessHintWindow( "���ڱ������ݣ����Ժ�....." );
	
			var tArray = obj.getSelectedDataValues("value");
			
			var cArray = obj2.getSelectedDataValues("value");
			var post = "record:name=" + paramArray.cxmc
			         + "&record:sys_advanced_query_id="+paramArray.cxid
			         + "&record:step1_param="+connObj.connJSONArray.toJSONString()
			         + "&record:step2_param="+qcObj.connJSONArray.toJSONString()
			         + "&record:table_no="+tArray.toString()
			         + "&record:query_sql="+sql.replace(/\+/g, "%2B")
			         + "&record:display_columns="+cArray.toString()
			         + "&record:display_columns_cn_array="+document.getElementById("record:columnsCnArray").value
			         + "&record:display_columns_en_array="+obj2.getMixedDataValues(['tblName','colName','colByName'],'.');//document.getElementById("record:columnsEnArray").value;
			         
			post=encodeURI(post); 
			post=encodeURI(post); 
			xmlObj = createXMLHttpRequest();
			var URL = rootPath + txnCode;
			xmlObj.open ('post',URL,false);
			xmlObj.onreadystatechange = handleRespose;
			xmlObj.setrequestheader("cache-control","no-cache"); 
			xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
			//xmlObj.setrequestheader('charset','GBK'); 
			xmlObj.send(post);
		}
		
		function handleRespose(){
			var xmlResults = xmlObj.responseXML;
			if (xmlObj.readyState == 4) { // �ж϶���״̬
				_hideProcessHintWindow();
				if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
					var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
					var query_id=_getXmlNodeValue( xmlResults, "/context/select-key/sys_advanced_query_id");
					if(errCode != "000000"){
					    alert("�������" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
					    return;
					}
					alert("����ɹ���");
					var url=rootPath+"/txn6025002.do?primary-key:sys_advanced_query_id="+query_id+"&select-key:queryType=0";
					//alert(url);
					window.location.href = url;
				} else { //ҳ�治����
					window.alert("���������ҳ�����쳣��");
				}
			}
		}
	  	
	  	// �����ĵ���һ����ť�¼�
	  	document.getElementById("preStep3Button").onclick = function(){
			document.getElementById("step4DIV").style.display = "none";
			document.getElementById("step4ButtonDiv").style.display = "none";
			document.getElementById("step3DIV").style.display = "block";
			document.getElementById("step3ButtonDiv").style.display = "block";
			steps.setCurrentStep(2);
	  	}
	  	
	  	var paramStr = "?record:queryflag=1&record:sys_id=";
	  	/*if ( funcType && funcType == "download" ){
	  		paramStr = "?record:queryflag=1&record:sys_id=";
	  	}*/
	  	
	  	var obj = new dataSelectHTMLObj({
	  		rootPath:rootPath,					//z��Ŀ¼·��
	  		selectPrefix:"table",				//������id��׺
	  		text_srcTitle:"*ѡ�������",			//����
	  		text_selectSrcTitle:"*ѡ�����ݱ�",	//����
	  		tableContainer:"div1",				//���ĸ�div������һ
	  		txnCode:"/txn60210008.ajax",		//����������ı�ʱִ�еĽ���
	  		paramStr:paramStr,			//����������ı�ʱִ�еĽ��ײ���ǰ׺
	  		fillObjId:"from_table",				//����������ı�ʱִ�еĽ������ݷ��غ����ĸ������������
	  		optionValue:"table_no",				//���������������value����ȡֵ
	  		optionText:"table_name_cn",			//�����������������ʾֵ
	  		oncontentchange:updateRelationObj,	//����ѡ�������ݸı��ִ�еĺ���
	  		beforecontentchange:scanConditions,	//����ѡ�������ݸı�ǰ��ִ�еĺ���
	  		addtionalParam:{ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"} //��ѡ������Ҫ��ӵĸ�������
	  	});
	  	
	  	var connObj = new connectCondition({
	  		id:"conn",
	  		parentContainer:"div2",
	  		getColumnSrcPrefix:rootPath+"/txn60210009.ajax?record:table_no="
	  	});
	  	
	  	qcObj = new queryCondition({
           id                  : "qct",
           parentContainer     : "step2DIV",
           getColumnSrcPrefix  : rootPath+"/txn60210009.ajax?record:table_no=",
           getCodeSrcPrefix    : rootPath+"/txn60210012.ajax?select-key:jc_dm_id="
        });
	       
	    
	  	var obj2 = new dataSelectHTMLObj({
	  		rootPath:rootPath,
	  		selectPrefix:"columnTable",
	  		text_srcTitle:"*ѡ�����ݱ�",
	  		text_selectSrcTitle:"*ѡ���ֶ�",
	  		tableContainer:"columnsContainerDiv",
	  		txnCode:"/txn60210009.ajax",
	  		paramStr:"?record:table_no=",
	  		fillObjId:"from_columnTable",
	  		optionValue:"column_no",
	  		optionText:"column_name_cn",
	  		addtionalParam:{tblid:"table_no", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn", colByName:"column_byname"}
	  	});
		
	  	if(doUpdate){
	  		doUpdate(connObj, qcObj, obj2, obj);
	  	}else{
	  		obj.doSelectQuery("/txn60210006.ajax", null, null, "source_" + obj.selectPrefix, "sys_id", "sys_name");
	  	}
	  	
  	};
