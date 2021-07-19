window.onload = function(){
		var rootPath = window.rootPath;
		var steps = new stepHelp({
	           id				: "steps",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
	           parentContainer	: "stepsContainerDiv", // �丸�ڵ��ID
	           textArray		: ["��ѯ��Χ����", "ѡ����ʾ��", "Ԥ��"]		 // �ַ�������
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
		  		var columns = document.getElementById("selected_"+ obj2.selectPrefix).options;//�����ѡ���ֶ����������
	  			var optionArray = getSelectedOptions("selected_" + obj.selectPrefix);//������еõ������������ѡ��
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
			  							scanConditions("delOne", true, true);//�ݹ���ã���ɾ�������������
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
	  	function createSQL(step){
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
						document.getElementById("step3DIV").style.display = "block";
						document.getElementById("step3ButtonDiv").style.display = "block";
						document.getElementById("step1DIV").style.display = "none";
						steps.setCurrentStep(1);
						document.getElementById("toStep2Button").disabled = false;
						obj2.updateColumnTables("selected_"+obj.selectPrefix, "source_"+obj2.selectPrefix);
					}
				});
	  		}
	  	}
	  	
	  	// ��������Ԥ����ť�¼�
	  	document.getElementById("preViewButton").onclick = function(){
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
  			_showProcessHintWindow( "����У��SQL��䣬���Ժ�....." );
	  		var sql = createSQL(2);
	  		$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
				var result = xml.selectSingleNode("/results/sql").text;
				if(result=="false"){
					_hideProcessHintWindow();
					alert("SQLУ��ʧ�ܣ����������");
					document.getElementById("toStep2Button").disabled = false;						
					return false;
				}else{
					_hideProcessHintWindow();
					var totalTable = new generateTotalTable({
			           id					: "totalTable",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
			           parentContainer		: "totalTableDiv", // �丸�ڵ��ID
			           columnSelect			: "selected_columnTable",
			           tableSelect			: "selected_table",
			           connectConditionTable: "conditionTbl_conn",
			           tableParamOnColumn	: ["tblid", "tblNameCn", "tblName"],  // ����Ϊ��ID(Value)����������(Text)����Ӣ����(��������)
			           sysParamOnTable		: ["ztid", "ztName", "ztNo"],   	  // ����Ϊ����ID(Value)������������(Text)������Ӣ����(��������)
			           columnParamOnColumn	: ["colId", "colNameCn", "colName"]	  // ����Ϊ�ֶ�ID(Value)���ֶ�������(Text)���ֶ�Ӣ����(��������)
			        });
			  		totalTable.write();
			  		document.getElementById("record:query_sql").value = sql;//����SQL
			  		document.getElementById("record:columnsCnArray").value = obj2.getSelectedDataValues("text");//�����ֶ�������
			  		document.getElementById("record:columnsEnArray").value = columnEnArray.toString();//�����ֶ�Ӣ����
			  		
					document.getElementById("step3DIV").style.display = "none";
					document.getElementById("step3ButtonDiv").style.display = "none";
					document.getElementById("step4DIV").style.display = "block";
					document.getElementById("step4ButtonDiv").style.display = "block";
					steps.setCurrentStep(2);
					document.getElementsByTagName("form")[0].submit();
					
				}
			});
	  		
	  	}
	  	
	  	// ����������һ����ť�¼�
	  	document.getElementById("preStep2Button").onclick = function(){
			document.getElementById("step3DIV").style.display = "none";
			document.getElementById("step3ButtonDiv").style.display = "none";
			document.getElementById("step1DIV").style.display = "block";
			steps.setCurrentStep(1);
	  	}
	  	
	  	var txnCode = "/txn50202003.ajax";//���״��룬Ĭ��Ϊ��ӣ����ajaxXml��Ϊ�գ���ִ���޸�
	  	// �����ĵı��水ť�¼�
	  	document.getElementById("saveButton").onclick = function(){
	  		var paramArray;
	  		if(ajaxXml){
		  		var id = ajaxXml.selectSingleNode("//record/sys_svr_service_id").text;
	  			var svr_name = ajaxXml.selectSingleNode("//record/svr_name").text;
		  		var code = ajaxXml.selectSingleNode("//record/svr_code").text;
		  		var max = ajaxXml.selectSingleNode("//record/max_records").text;
		  		var desc = ajaxXml.selectSingleNode("//record/svr_desc").text;
		  		paramArray = {svr_id:id, svr_name:svr_name, svr_code:code, max_records:max, svr_desc:desc};
		  		txnCode = "/txn50202002.ajax";
	  		}
	  		var paramArray = window.showModalDialog("fillName.jsp",paramArray,"dialogWidth:60;dialogHeight:25;scroll:yes;");
			if(paramArray){
				toAdd(paramArray);
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
	  		
	  		var sql = createSQL(2);
			sql = sql.replace(/\+/g, "%2B");
			_showProcessHintWindow( "���ڱ������ݣ����Ժ�....." );
	
			var tArray = obj.getSelectedDataValues("value");
			
			var cArray = obj2.getSelectedDataValues("value");
			var post = "record:svr_name=" + paramArray.svr_name
			         + "&record:svr_code=" + paramArray.svr_code
			         + "&record:max_records=" + paramArray.max_records
			         + "&record:svr_desc=" +  paramArray.svr_desc
			         + "&record:sys_svr_service_id="+paramArray.svr_id
			         + "&record:table_no="+tArray.toString()
			         + "&record:query_sql="+sql
			         + "&record:column_no="+cArray.toString()
			         + "&record:params="+connObj.connJSONArray.toJSONString()
			         + "&record:shared_columns_cn_array="+document.getElementById("record:columnsCnArray").value
			         + "&record:shared_columns_en_array="+document.getElementById("record:columnsEnArray").value
			         + "&record:svr_type=һ�����";
			         
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
					if(errCode != "000000"){
					    alert("�������" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
					    return;
					}
				    	alert("����ɹ���");
				    	window.location.href = rootPath+"/txn50202001.do?<%=request.getQueryString()%>";
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
	  	
	  	var obj = new dataSelectHTMLObj({
	  		rootPath:rootPath,					//z��Ŀ¼·��
	  		selectPrefix:"table",				//������id��׺
	  		text_srcTitle:"*ѡ�������",			//����
	  		text_selectSrcTitle:"*ѡ�����ݱ�",	//����
	  		tableContainer:"div1",				//���ĸ�div������һ
	  		txnCode:"/txn60210008.ajax",		//����������ı�ʱִ�еĽ���
	  		paramStr:"?record:sys_id=",			//����������ı�ʱִ�еĽ��ײ���ǰ׺
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
	  		doUpdate(connObj, obj2, obj);
	  	}else{
	  		obj.doSelectQuery("/txn50202006.ajax", null, null, "source_" + obj.selectPrefix, "sys_id", "sys_name");
	  	}
	  	
  	};