<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="650" height="350">
<style type="text/css">
.connectConditionSelect, .queryConditionSelect, .queryConditionInput{
    width:100%;
}

select.dataSelect {
    MARGIN: -3px -2px -2px -3px; 
    WIDTH: 204px; 
    CURSOR: hand; 
    HEIGHT: 204px;
    font:normal 12px Verdana;color:#333333;
}
tr.selectRow td{
    padding:1px;
}
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
<head>
	<title>��ͼ����</title>
	<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
	<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/connectConditionPluginJoin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/generateTotalTable.js"></SCRIPT>

	<%
			String queryId = request.getParameter("select-key:sys_db_view_id");
	%>

</head>
<freeze:body>
	<freeze:title caption="��ͼ����" />
	<form id="updateForm">
		<input type="hidden" id="record:columnsEnArray" name="record:columnsEnArray" />
		<input type="hidden" id="record:columnsCnArray" name="record:columnsCnArray" />
		<div id="stepsContainerDiv" style="display:none"></div>
		<div id="step1DIV" style="display:none">
			<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr class="title-row">
			      <td>ѡ�����ݱ�</td>
			    </tr>
			    <tr class="framerow">
		          <td><div id="div1" style="height:100%;width:100%;"></div></td>
		        </tr>
		    </table>	
		    <div id="div2" style="margin-top:10px;"></div>
	        <p><center></center></p>
        </div>        
        
        <div id="step2DIV" style="display:none">
        	<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr class="title-row">
			      <td>ѡ���ֶ�</td>
			    </tr>
			    <tr class="framerow">
		          <td><div id="columnsContainerDiv" style="height:100%;width:100%;"></div></td>
		        </tr>
		    </table>	
		</div>
        <div id="step2ButtonDiv" style="display:none">
        	<p><center></center></p>
        </div>
        <div id="totalTableDiv"></div>
        <div id="step3ButtonDiv">
        	<p><center>
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
	</form>
</freeze:body>
<script language="javascript">
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
	  		addtionalParam:{ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"} //��ѡ������Ҫ��ӵĸ�������
	  	});
	  	
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
			  					break;
			  				}else{
			  					columns[j].selected = false;//����Ѿ�ѡ�У�����Ϊ��ѡ��������ɾ
			  				}
			  			}
			  			if(exist){
			  				if(del){
				  				if(!confirm("ϵͳ��⵽��Ҫ��ʾ�˱���ֶΣ��Ƿ�ͬʱɾ����ص��ֶΣ�"))
				  					return false;
				  					
				  				obj2.clickToLeft("tblid", true);
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

    var ajaxXml;
    var doUpdate = function(connObj, obj2, dataObj){
	  	function getParamCN(v){
			switch(v){
				case 'AND':
					return '����';
					break;
				case 'OR':
					return '����';
					break;
				case '>':
					return '����';
					break;
				case '<':
					return 'С��';
					break;
				case '<>':
					return '������';
					break;
				case '=':
					return '����';
					break;
				case 'like':
					return '����';
					break;
				default:
					return '';
					break;			
			}
		}
	  	
	  	function doInitQuery(){
			$.get("<%=request.getContextPath()%>/txn52102004.ajax?select-key:sys_db_view_id=<%=queryId%>", function(xml){
				ajaxXml = xml;
				//������ѡ�����ݱ�
			    fillXmlToSelect(xml, "table-info", "selected_table", "table_no", "table_name_cn", {ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"});

			    var step1Params = xml.selectNodes("//record");
			    for(var i = 0; i < step1Params.length; i++){
			    	var step1Param = step1Params[i].selectSingleNode("params").text;
			    	var jsonObj = eval("(" + step1Param + ")");
			    	
			    	/**var paramArray = step1Param.split("~");
		    		var connStr = paramArray[1] + "~" +
		    					  paramArray[2] + "~" +
		    					  paramArray[3] + "~" +
		    					  paramArray[4] + "~" +
		    					  paramArray[6] + "~" +
		    					  paramArray[7] + "~" +
		    					  paramArray[9] + "~" +
		    					  paramArray[10] + "~" +
		    					  paramArray[11] + "~" +
		    					  paramArray[13] + "~" +
		    					  paramArray[14] + "~" +
		    					  paramArray[16] ;**/
		    		addToArray(connObj.connJSONArray, jsonObj);
		    		insertParamRow(connObj, jsonObj.paramText);
		    		
				    if(connObj.connJSONArray.length > 0){
				    	var con1 = document.getElementById(connObj.id + "Condition");
				    	con1.options.length = 0;
				    	con1.options.add(createOption('AND','����'));
				    	con1.options.add(createOption('OR','����'));
				    	con1.disabled = false;
				    }
			    }
			    
			    step1Params = null;
			    
			    fillXmlToSelect(xml, "column-info", "selected_"+obj2.selectPrefix, "column_no", "column_name_cn", {tblid:"table_no", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn"});
                
	  	        var columnObj = document.getElementById("selected_"+obj2.selectPrefix).options;
	  			var columnEnArray = new Array();
	  			for(var i = 0; i < columnObj.length; i++){
	  				if(columnObj[i].colByName){//�ж��Ƿ���ڱ���
	  					addToArray(columnEnArray, columnObj[i].colByName);
	  				}else{
	  					addToArray(columnEnArray, columnObj[i].colName);
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
	  		});
	  	};
	  	doInitQuery();
	  	
	  	function insertParamRow(p, paramStr){
	  		var objTable = document.getElementById("conditionTbl_" + p.id);
			objTable.border = 1;
			var newTr = objTable.insertRow();
			//newTr.id = "curRow_"+rowIndex;
			newTr.borderColor = "#CCCCCC";
			newTr.height = 30;
		
			//�����
			var newTd0 = newTr.insertCell();
			var newTd1 = newTr.insertCell();
		
			//���������ݺ�����
			newTd0.innerHTML = paramStr; 
			newTd0.width = "90%";
			newTd0.align = "left";
			newTd1.innerHTML= "<input type='button' id='deleteConnCondition_" + p.id + "' value=' ɾ�� ' class='menu' />";
			newTd1.width = "10%";
			newTd1.align = "center";
			
			document.getElementById(p.id + "hideRow").style.display = "block";
			
			var delButton = document.getElementsByName("deleteConnCondition_"+p.id);
		    var obj = delButton[delButton.length - 1];
		    if (obj){
		    	obj.onclick = function(){
		    		if(confirm("ȷ��ɾ����������")){
						p.deleteCondition(this.parentNode.parentNode.rowIndex);
		    		}
		    	};
		    }
	  	}
	  	//alert(document.getElementById("source_"+dataObj.selectPrefix).onchange);
	  	//document.getElementById("source_"+dataObj.selectPrefix).fireEvent("onchange");
	}
	doUpdate(connObj, obj2, obj);
};
</script>
</freeze:html>
