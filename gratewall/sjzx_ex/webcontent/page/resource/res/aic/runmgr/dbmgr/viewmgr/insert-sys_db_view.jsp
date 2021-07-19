<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��Χ����</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/connectConditionPluginJoin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/generateTotalTable.js"></SCRIPT>
<style>
#totalTableDiv .leftTitle{
	background:url(<%=request.getContextPath()%>/module/layout/layout-weiqiang/images_new/r_list_l.jpg) no-repeat !important;
}
#totalTableDiv .secTitle{
	background-color: rgb(0, 102, 153) !important;
}
#totalTableDiv .rightTitle{
	background:url(<%=request.getContextPath()%>/module/layout/layout-weiqiang/images_new/r_list_r.jpg) no-repeat !important;
}
</style>
</head>
<freeze:body>
	<freeze:title caption="������ͼ"/>
	<freeze:form action="/txn60210003">
	
		<input type="hidden" id="record:columnsEnArray" name="record:columnsEnArray" />
		<input type="hidden" id="record:columnsCnArray" name="record:columnsCnArray" />
		<div id="stepsContainerDiv"></div>
		<div id="step1DIV">
			<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">ѡ�����ݱ�</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
			    <tr class="framerow">
		          <td style="padding:0px;border:2px solid #006699;">
		          	<div class="odd2" id="div1" style="height:100%;width:100%;border:0px;"></div>
		          </td>
		        </tr>
		    </table>	
		    <div id="div2" style="margin-top:10px;"></div>
	        <p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='toStep2Button' class="menu" value='��һ��' />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack0' class="menu" value=' �� �� '
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center></p>
        </div>
        
        <div id="step2DIV" style="display:none">
            <table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">ѡ���ֶ�</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
			    <tr class="framerow">
		          <td style="padding:0px;border:2px solid #006699;">
		          	<div class="odd2" id="columnsContainerDiv" style="height:100%;width:100%;border:0px;"></div>
		          </td>
		        </tr>
		    </table>
		</div>
        <div id="step2ButtonDiv" style="display:none">
        	<p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="preStep1Button" class="menu" value="��һ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="toStep3Button" class="menu" value="��һ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="goBack1" class="menu" value=" �� �� "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center></p>
        </div>
        
        
        <div id="totalTableDiv" style="display:none"></div>
        <div id="step3ButtonDiv" style="display:none">
        	<p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preStep2Button' class="menu" value="��һ��" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='saveButton' class="menu" value=" �� �� " />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack2' class="menu" value=" �� �� "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center></p>
        </div>
	</freeze:form>
</freeze:body>
<script type="text/javascript">
window.rootPath = "<%=request.getContextPath()%>";
window.onload = function(){
		var rootPath = window.rootPath;
		var steps = new stepHelp({
	           id				: "steps",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
	           parentContainer	: "stepsContainerDiv", // �丸�ڵ��ID
	           textArray		: ["ѡ�����ݱ�", "ѡ���ֶ�", "��ͼ����"]		 // �ַ�������
	       });
		steps.setCurrentStep(0);
		
	  	var obj = new dataSelectHTMLObj({
	  		rootPath:rootPath,					//z��Ŀ¼·��
	  		selectPrefix:"table",				//������id��׺
	  		text_srcTitle:"*ѡ������",			//����
	  		text_selectSrcTitle:"*ѡ�����ݱ�",	//����
	  		tableContainer:"div1",				//���ĸ�div������һ
	  		txnCode:"/txn60210008.ajax",		//����������ı�ʱִ�еĽ���
	  		paramStr:"?record:sys_id=",			//����������ı�ʱִ�еĽ��ײ���ǰ׺
	  		fillObjId:"from_table",				//����������ı�ʱִ�еĽ������ݷ��غ����ĸ������������
	  		optionValue:"table_no",				//���������������value����ȡֵ
	  		optionText:"table_name_cn",			//�����������������ʾֵ
	  		oncontentchange:updateRelationObj,	//����ѡ�������ݸı��ִ�еĺ���
	  		beforecontentchange:scanConditions,	//����ѡ�������ݸı�ǰ��ִ�еĺ���
	  		addtionalParam:{ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"} //��ѡ������Ҫ���ӵĸ�������
	  	});
	  	
	  	obj.doSelectQuery("/txn60210006.ajax", null, null, "source_" + obj.selectPrefix, "sys_id", "sys_name");
	  	
	  	function updateRelationObj(type){
	  		connObj.setDataFromSelect("selected_table");
	  	}
	  	
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
			  						if(optionArray[i].value == connJSONObj.tableOneId || optionArray[j].value == connJSONObj.tableTwoId){
			  							connObj.deleteCondition(j);
			  							//scanConditions("delOne", true, true);//�ݹ���ã���ɾ�������������
			  							j--;
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
				  				    if(!confirm("ϵͳ��⵽��Ҫ��ʾ�˱����ֶΣ��Ƿ�ͬʱɾ����ص��ֶΣ�"))
				  					    return false;
				  				}	
				  				obj2.clickToLeft("tblid", true);
				  			}else{
				  				alert("ϵͳ��⵽��Ҫ��ʾ�˱����ֶΣ���ֹɾ����");
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
	  	
	  	function createSQL(loadColumn){
	  		var selectedTables = document.getElementById("selected_"+obj.selectPrefix).options;
			var sql = "";
			
			if(loadColumn){//�Ƿ���ء���ʾ�ֶΡ�
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
					sql += " WHERE "+ paramStr;
				}
				
				columnObj = null;
				columnNameArray = null;
				columnIdArray = null;
			}else{
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
			}
			
			selectedTables = null;
			paramStr = null;
			return sql;
	  	}
	  	
	  	document.getElementById("toStep2Button").onclick = function(){
	  		if(document.getElementById("selected_"+obj.selectPrefix).options.length == 0){
	  		    alert("��ѡ�����ݱ���");
	  		    return;
	  		}
	  		if(connObj.validateConnCondition("selected_"+obj.selectPrefix)){
	  			_showProcessHintWindow( "����У����������������Ժ�....." );
	  			var sql = createSQL();
	  			$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
					var result = xml.selectSingleNode("/results/sql").text;
					if(result=="false"){
					    _hideProcessHintWindow();
						alert("SQLУ��ʧ�ܣ����������");
						document.getElementById("toStep2Button").disabled = false;						
						return false;
					}else{
						obj2.updateColumnTables("selected_"+obj.selectPrefix, "source_"+obj2.selectPrefix);
						_hideProcessHintWindow();
						document.getElementById("step2DIV").style.display = "block";
						document.getElementById("step2ButtonDiv").style.display = "block";
						document.getElementById("step1DIV").style.display = "none";
						steps.setCurrentStep(1);
					}
				});
	  		}
	  	}
	  	
	  	document.getElementById("preStep1Button").onclick = function(){
			document.getElementById("step2DIV").style.display = "none";
			document.getElementById("step2ButtonDiv").style.display = "none";
			document.getElementById("step1DIV").style.display = "block";
			steps.setCurrentStep(0);
	  	}
	  	
	  	document.getElementById("toStep3Button").onclick = function(){
	  	    var columnObj = document.getElementById("selected_"+obj2.selectPrefix).options;
	  		if(columnObj.length == 0){
	  		    alert("��ѡ���ֶΣ�");
	  		    return;
	  		}else{
	  			var columnEnArray = new Array();
	  			for(var i = 0; i < columnObj.length; i++){
	  				if(columnObj[i].colByName){//�ж��Ƿ���ڱ���
	  					addToArray(columnEnArray, columnObj[i].colByName);
	  				}else{
	  					addToArray(columnEnArray, columnObj[i].colName);
	  				}
	  			}
	  		}	  	
	  		
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
	  		document.getElementById("record:columnsCnArray").value = obj2.getSelectedDataValues("text");//�����ֶ�������
	  		document.getElementById("record:columnsEnArray").value = columnEnArray.toString();//�����ֶ�Ӣ����
			document.getElementById("totalTableDiv").style.display = "block";
			document.getElementById("step3ButtonDiv").style.display = "block";
			document.getElementById("step2DIV").style.display = "none";
			document.getElementById("step2ButtonDiv").style.display = "none";
			steps.setCurrentStep(2);
	  	}
	  	
	  	document.getElementById("preStep2Button").onclick = function(){
			document.getElementById("totalTableDiv").style.display = "none";
			document.getElementById("step3ButtonDiv").style.display = "none";
			document.getElementById("step2DIV").style.display = "block";
			document.getElementById("step2ButtonDiv").style.display = "block";
			steps.setCurrentStep(1);
	  	}
	  	
	  	
	  	document.getElementById("saveButton").onclick = function(){
	  		
	  		var paramArray = window.showModalDialog("fillName.jsp",null,"dialogWidth:40;dialogHeight:20;scroll:no;");
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
	  		
//	  		var sql = createSQL(true);
			_showProcessHintWindow( "���ڱ������ݣ����Ժ�....." );
	
			var tArray = obj.getSelectedDataValues("value");
			
			var cArray = obj2.getSelectedDataValues("value");
			
			var post = "record:view_name=" + paramArray.name
			         + "&record:view_code="+ paramArray.code
			         + "&record:view_desc="+ paramArray.desc
			         + "&record:params="+connObj.connJSONArray.toJSONString()
			         + "&record:table_no="+tArray.toString()
			         + "&record:column_no="+cArray.toString()
			         + "&record:column_cn="+document.getElementById("record:columnsCnArray").value
			         + "&record:column_en="+document.getElementById("record:columnsEnArray").value;
			         
			post=encodeURI(post); 
			post=encodeURI(post); 
			xmlObj = createXMLHttpRequest();
			var URL = rootPath+'/txn52102003.ajax';
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
					    alert("��������" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
					    return;
					}
				    	alert("����ɹ���");
				    	goBackWithUpdate();
				} else { //ҳ�治����
					window.alert("���������ҳ�����쳣��");
				}
			}
		}
	  	
  	};
</script>
</freeze:html>