<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>��ѯ��Χ����</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<style type="text/css">
#qctContainer, #qct2Container{
	width:100%;
}
#body-div{
	overflow:visible !important;
}
</style>
</head>
<script language="javascript">

</script>
<freeze:body>
	<div id="stepsContainerDiv"></div> 
    <div id="step1DIV">
			<table width="100%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="1"> 
			    <tr class="title-row">
			      <td>�����ֶ�ѡ��</td>
			    </tr>
			    <tr class="framerow">
		          <td>
		          	<table id="colTbl" width='100%' cellpadding='0' cellspacing='0' border="1" bordercolor="#cccccc" style="border-collapse:collapse">
						<tr height='30' >
						    <td width="1%" align="center" nowrap="nowrap">ȫѡ<input id="cAll" type="checkBox" /></td>
						    <td width="33%" align="center" nowrap="nowrap">�ֶ�����</td>
						    <td width="33%" align="center" nowrap="nowrap">�ֶ�����</td>
						    <td width="33%" align="center" nowrap="nowrap">�ֶα���</td>
						</tr>
                   		<tbody></tbody>
                    </table>
		          </td>
		        </tr>
		    </table>	
	        <p><center><input type='button' id='toStep2Button' class="menu" value='��һ��'/>
	        <input type='button' id='toSaveButton' class="menu" value=' �� �� '/>
	        <input type='button' id='goBack0' class="menu" value=' �� �� ' onclick="window.parent.goBack();"/></center></p>
    </div>
        
    <div id="step2DIV" style="display:none"></div>
    <div id="step2ButtonDiv" style="display:none">
    	<select id="tempSelect" name="tempSelect" style="display:none"></select>
    	<p><center><input type="button" id="preStep1Button" class="menu" value="��һ��" />
	    <input type='button' id='toStep3Button' class="menu" value='��һ��'/>
    	<input type="button" id="toSaveButton" class="menu" value=" �� �� " />
     <input type="button" id="goBack1" class="menu" value=" �� �� " onclick="window.parent.goBack();" /></center></p>
    </div>
        
        
    <div id="step3DIV" style="display:none"></div>
    <div id="step3ButtonDiv" style="display:none">
    	<p><center><input type='button' id='preStep2Button' class="menu" value="��һ��"/>
    	<input type="button" id="toSaveButton" class="menu" value=" �� �� " />
     <input type='button' id='goBack2' class="menu" value=" �� �� " onclick="window.parent.goBack();"/></center></p>
    </div>
</freeze:body>
<script language="javascript">
var onlyForUserParam = "������ֵ��";

function queryCondition2(config){
	this.addEvent = queryCondition2addEvent;
	return this;
}
// ����¼�
queryCondition2addEvent = function(){
    var p = this;
    // ѡ���ʱ
    var tableOne = document.getElementById(this.id + "TableOne");
    if (tableOne){
        tableOne.onchange = function(){
            p.getColumns(this.options[this.selectedIndex].value, p.id + "ColumnOne");
        };
    }
    tableOne = null;
    
    // ѡ���ֶ�ʱ
    var ColumnOne = document.getElementById(p.id + "ColumnOne");
    
    if (ColumnOne){
        ColumnOne.onchange = function(){
            var columnType = this.options[this.selectedIndex].type;
            var codeValue = this.options[this.selectedIndex].codeValue;
            // alert(columnType + ", " + codeValue);
            var paramValueInput = document.getElementById(p.id + "ParamValueInput");
            var paramValueSelect = document.getElementById(p.id + "ParamValueSelect");
            var paramValueDateSelect = document.getElementById(p.id + "ParamValueDateSelect");
            
            paramValueInput.value = "";
            paramValueInput.disabled = "true";
            paramValueSelect.innerHTML = "";
            paramValueSelect.disabled = "true";
            paramValueDateSelect.value = "";
            paramValueDateSelect.disabled = "true";
            
            
            var RelationString = document.getElementById(p.id + "RelationString");
            var RelationNumber = document.getElementById(p.id + "RelationNumber");
            var RelationCode = document.getElementById(p.id + "RelationCode");
            var RelationDate = document.getElementById(p.id + "RelationDate");
            /**
             * columnType:
             * 1. �ַ���
             * 2. ����
             * 3. ����
             * codeValue:
             * ��columnTypeΪ1��codeValue��ֵʱ��Ϊ���
             */
            if (columnType == 1){ // �ַ���
            	if (codeValue){//���
            		paramValueInput.style.display = "none";
            		paramValueSelect.style.display = "block";
            		paramValueDateSelect.style.display = "none";
            		RelationString.style.display = "none";
            		RelationCode.style.display = "block";
            		RelationDate.style.display = "none";
            		RelationNumber.style.display = "none";
            		
            		paramValueSelect.innerHTML = "";
            		var option = document.createElement("option");
            		paramValueSelect.appendChild(option);
            		option.text = onlyForUserParam;
            		option.value = onlyForUserParam;
            		option = null;
            	}else{// ��ͨ�ַ���
            		paramValueInput.style.display = "block";
            		paramValueInput.value = onlyForUserParam;
                    paramValueSelect.style.display = "none";
                    paramValueDateSelect.style.display = "none";
                    RelationString.style.display = "block";
                    RelationCode.style.display = "none";
                    RelationDate.style.display = "none";
                    RelationNumber.style.display = "none";
            	}
            }else if (columnType == 2){ // ����
            	paramValueInput.style.display = "none";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "block";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "block";
                RelationNumber.style.display = "none";
           		paramValueDateSelect.value = onlyForUserParam;
            }else if (columnType == 3){ // ����
                paramValueInput.style.display = "block";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "none";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "none";
                RelationNumber.style.display = "block";
           		paramValueInput.value = onlyForUserParam;
            }else{// ��������
            	paramValueInput.style.display = "none";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "none";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "none";
                RelationNumber.style.display = "none";
            }
            paramValueInput = null;
            paramValueSelect = null;
            paramValueDateSelect = null;
            RelationString = null;
            RelationCode = null;
            RelationNumber = null;
            RelationDate = null;
        };
    }
    ColumnOne = null;
    
    // ����¼�
    var addButton = document.getElementById(this.id + "AddButton");
    if (addButton){
        addButton.onclick = function(){
        	p.addQueryCondition();
        }
    }
};
document.getElementById("body-div").onresize = function(){
	//��ʾ����frame���ڳߴ�
    testResizeFrame();
};

var svrId = "<%= request.getParameter("svrId") %>";
var svrName = "<%= request.getParameter("svrName") %>";
var userId = "<%= request.getParameter("userId")%>";
var userName = "<%= request.getParameter("userName")%>";

var steps = new stepHelp({
      id				: "steps",     // �����Ĭ��ID�����һ��ҳ�����ж���������ע���ID�����ظ�
      parentContainer	: "stepsContainerDiv", // �丸�ڵ��ID
      textArray		: ["ѡ�����ֶ�", "����ϵͳ����", "���ýӿڲ���"]		 // �ַ�������
   });
  
   var qcObj;    //ϵͳ�������
   var qcObj2;   //�û��������
   var svrParams;//����ı����Ӳ���
   var configId; //����id������������õķ�����д�ֵ
function doInitQuery(){
	$.get("<%= request.getContextPath()%>/txn50203004.ajax?select-key:sys_svr_user_id=" + userId + "&select-key:sys_svr_service_id=" + svrId, function(xml){
		//document.getElementById("test").innerText = xml.xml;
		//���÷���ı����Ӳ���
		var params = xml.selectNodes("//record");
		svrParams = new Array();
		for(var i = 0; i < params.length; i++){
			var param = params[i].selectSingleNode("params").text;//��÷���ı����Ӳ���
			if(param.trim() != ""){
				svrParams.push(eval("(" + param + ")"));
			}
		}
		configId = xml.selectSingleNode("//config-inf/sys_svr_config_id").text;//�������id
		
		var cols = xml.selectNodes("//col-info");//���������ʾ�ֶ�
		
		var permitCol = xml.selectSingleNode("//config-inf/permit_column").text;//������Ѿ����õķ������ù����ֶ�
		var permitCols = null;
		if(permitCol){
			permitCols = permitCol.split(",");
		}
		
		var aliasStr = xml.selectSingleNode("//config-inf/column_alias").text;//������Ѿ����õķ������ù����ֶα���
		var aliasJSON = null;
		if(aliasStr){
			aliasJSON = eval("(" + aliasStr + ")");
		}
		var objTable = document.getElementById("colTbl");
		for(var i = 0; i < cols.length; i++){
	    	var colNo = cols[i].selectSingleNode("column_no").text;
	    	var colName = cols[i].selectSingleNode("column_name").text;
	    	var colNameCn = cols[i].selectSingleNode("column_name_cn").text;
	    	var tblName = cols[i].selectSingleNode("table_name").text;
	    	var tblNameCn = cols[i].selectSingleNode("table_name_cn").text;
	    	//����׷���ֶ���Ϣ
	    	var newTr = objTable.insertRow();
			var newTd0 = newTr.insertCell();
			var newTd1 = newTr.insertCell();
			var newTd2 = newTr.insertCell();
			var newTd3 = newTr.insertCell();
			
			if(permitCols){
				for(var j = 0; j < permitCols.length; j++){
					if(colNo == permitCols[j]){
						newTd0.innerHTML = "<input type='checkBox' name='permitColumn' colName='"+colName+"' colNameCn='"+colNameCn+"' tblName='"+tblName+"' tblNameCn='"+tblNameCn+"' value='"+colNo+"' checked='checked'/>";
						break;
					}
					else{
						newTd0.innerHTML = "<input type='checkBox' name='permitColumn' colName='"+colName+"' colNameCn='"+colNameCn+"' tblName='"+tblName+"' tblNameCn='"+tblNameCn+"' value='"+colNo+"' />";
					}
				}
				//��ӱ���
				if(aliasJSON){
					var alia = eval("aliasJSON." + colName) == null ? "" : eval("aliasJSON." + colName);
					newTd3.innerHTML = "<input type='text' name='colAlias' value='"+ alia +"'/>";
				}
			}else{
				newTd0.innerHTML = "<input type='checkBox' name='permitColumn' colName='"+colName+"' colNameCn='"+colNameCn+"' tblName='"+tblName+"' tblNameCn='"+tblNameCn+"' value='"+colNo+"' />";
				newTd3.innerHTML = "<input type='text' name='colAlias' />";
			}
			newTd1.innerHTML = colName;
			newTd2.innerHTML = colNameCn;
	    }
	    forQCondition = null;
	    
	    var forQCondition = document.getElementById("tempSelect");
	    
	    forQCondition.options.length = 0;//�������������
	    var tbls = xml.selectNodes("//tbl-info");//������еı�
	    
	    for(var i = 0; i < tbls.length; i++){
	    	var tblNo = tbls[i].selectSingleNode("table_no").text;
	    	var tblName = tbls[i].selectSingleNode("table_name").text;
	    	var tblNameCn = tbls[i].selectSingleNode("table_name_cn").text;
	    	forQCondition.options.add(createOption(tblNo, tblNameCn, {tblName:tblName}));
	    }
	    
	    qcObj = new queryCondition({
           id                  : "qct",
           parentContainer     : "step2DIV",
           getColumnSrcPrefix  : rootPath+"/txn60210009.ajax?record:table_no=",
           getCodeSrcPrefix    : rootPath+"/txn60210012.ajax?select-key:jc_dm_id="
        });
        qcObj.setDataFromSelect("tempSelect");
        
        var qq = new queryCondition({
           id                  : "qct2",
           parentContainer     : "step3DIV",
           getColumnSrcPrefix  : rootPath+"/txn60210009.ajax?record:table_no=",
           getCodeSrcPrefix    : rootPath+"/txn60210012.ajax?select-key:jc_dm_id=",
           compCond_String     : [{text:'����', value:'=', prefix:'\'', postfix:'\''},
                                  {text:'������', value:'<>', prefix:'\'', postfix:'\''},
                                  {text:'��ʼ��', value:'like', prefix:'\'', postfix:'%\''},
                                  {text:'������', value:'like', prefix:'\'%', postfix:'\''},
                                  {text:'����', value:'like', prefix:'\'%', postfix:'%\''},
                                  {text:'IN', value:'IN', prefix:'(\'', postfix:'\')'}]
        });
        
        qcObj2 = queryCondition2.call(qq);
        qcObj2.addEvent();
        
        qcObj2.setDataFromSelect("tempSelect");
	    var configParams = xml.selectNodes("//config-param");//����������ò���
	   
	    if(configParams){
	    	for(var i = 0; i < configParams.length; i++){
	    		var configParam = configParams[i].selectSingleNode("params").text;
	    		if(configParam){
	    			var configParamJSON = eval("(" + configParam + ")");
	    			if(configParamJSON.paramType == '0'){
	    				qcObj.connJSONArray.push(configParamJSON);
	    				insertParamRow(qcObj, configParamJSON.paramText);
	    			}else{
	    				qcObj2.connJSONArray.push(configParamJSON);
	    				insertParamRow(qcObj2, configParamJSON.paramText);
	    			}
	    		}
	    	}
	    }
	    
	    var objTable = document.getElementById("conditionTbl_" + qcObj.id);
	    if(objTable.rows.length > 0){
	    	var con1 = document.getElementById(qcObj.id + "Condition");
	    	con1.options.length = 0;
	    	con1.options.add(createOption('AND','����'));
	    	con1.options.add(createOption('OR','����'));
	    	con1.disabled = false;
	    }
	    
	    var objTable = document.getElementById("conditionTbl_" + qcObj2.id);
	    if(objTable.rows.length > 0){
	    	var con1 = document.getElementById(qcObj2.id + "Condition");
	    	con1.options.length = 0;
	    	con1.options.add(createOption('AND','����'));
	    	con1.options.add(createOption('OR','����'));
	    	con1.disabled = false;
	    }
	    //��ʾ����frame���ڳߴ�
	    testResizeFrame();
	    window.parent.document.getElementById("step2DIV").style.display = "block";
	    window.parent._hideProcessHintWindow();
	});
}

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
		newTd1.innerHTML= "<a href='javascript:void(0)' id='deleteConnCondition_" + p.id + "'>ɾ��</a>";
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

	// ����һ����һ����ť�¼�
 	document.getElementById("toStep2Button").onclick = function(){
 		//���ȼ���Ƿ���ͬ�����ֶ�
		if(!validateSelectedColumns()){
			return;
		}
		document.getElementById("step2DIV").style.display = "block";
		document.getElementById("step2ButtonDiv").style.display = "block";
		document.getElementById("step1DIV").style.display = "none";
		steps.setCurrentStep(1);
		//��ʾ����frame���ڳߴ�
	    testResizeFrame();
 	}

 	// ���������һ����ť�¼�
 	document.getElementById("preStep1Button").onclick = function(){
		document.getElementById("step2DIV").style.display = "none";
		document.getElementById("step2ButtonDiv").style.display = "none";
		document.getElementById("step1DIV").style.display = "block";
		steps.setCurrentStep(0);
		//��ʾ����frame���ڳߴ�
	    testResizeFrame();
 	}

 	// ���������һ����ť�¼�
 	document.getElementById("toStep3Button").onclick = function(){
		document.getElementById("step3DIV").style.display = "block";
		document.getElementById("step3ButtonDiv").style.display = "block";
		document.getElementById("step2DIV").style.display = "none";
		document.getElementById("step2ButtonDiv").style.display = "none";
		steps.setCurrentStep(2);
		//��ʾ����frame���ڳߴ�
	    testResizeFrame();
 	}
  	
 	// ����������һ����ť�¼�
 	document.getElementById("preStep2Button").onclick = function(){
		document.getElementById("step3DIV").style.display = "none";
		document.getElementById("step3ButtonDiv").style.display = "none";
		document.getElementById("step2DIV").style.display = "block";
		document.getElementById("step2ButtonDiv").style.display = "block";
		steps.setCurrentStep(1);
		//��ʾ����frame���ڳߴ�
	    testResizeFrame();
 	}
 	
 	var xmlObj = false;

      //����XMLHttpRequest����
   function createXMLHttpRequest() {
     try { 
	  	xmlObj = new ActiveXObject("Msxml2.XMLHTTP"); 
	  } catch (e) { 
		  try { 
		   	xmlObj = new ActiveXObject("Microsoft.XMLHTTP"); 
		  } catch (e) { 
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
 	
var jump = true;
function toAdd(doJump){
	if(!doJump){
		jump = false;
	}
	//_showProcessHintWindow( "���ڱ������ݣ����Ժ�....." );
    //alert("svrName = "+svrName+", userName = "+userName);
	//���ȼ���Ƿ���ͬ�����ֶ�
	if(!validateSelectedColumns()){
		return false;
	}
	
	//ѭ���ֶ���Ϣ
    //ѭ��ʵ��ѡ�е��ֶ�����
	var cbArray = new Array();
	var colNameArray = new Array();
	var colCnNameArray = new Array();
	var aliasJSONStr = "{";
    for ( var i=0; i< colArray.length; i++)
    {
       	cbArray.push(colArray[i].value);
       	if(colArray[i].alias){//���ָ���˱������򱣳ֱ���ΪӢ����
       		colNameArray.push(colArray[i].alias);
       	}else{
       		colNameArray.push(colArray[i].colName);
       	}
       	colCnNameArray.push(colArray[i].colNameCn);
       	aliasJSONStr += colArray[i].colName + ":'" + (colArray[i].alias == null ? "" : colArray[i].alias) + "'";
       	
       	if(i != (colArray.length - 1)){
       		aliasJSONStr += ",";
       	}
    }

    aliasJSONStr += "}";
	var sql = createSQL();
    var post = "record:sys_svr_config_id=" +       configId
   		 	 + "&record:sysParams=" +              qcObj.connJSONArray.toJSONString()
   			 + "&record:userParams=" + 			   qcObj2.connJSONArray.toJSONString()
   		 	 + "&record:sys_svr_service_id=" +     svrId
   			 + "&record:sys_svr_user_id=" +        userId
   			 + "&record:permit_column=" +          cbArray.toString()
   			 + "&record:permit_column_en_array=" + colNameArray.toString()
   		 	 + "&record:permit_column_cn_array=" + colCnNameArray.toString()
   			 + "&record:query_sql=" +              sql
   			 + "&record:column_alias="+            aliasJSONStr.toString()
   			 + "&select-key:svr_name="+            svrName
   			 + "&select-key:user_name="+           userName;
   		 
   	
   	var txnCode = "/txn50203003.ajax";//Ĭ��Ϊ�½�
   	if(configId){//���������ID����ִ�и���
   		txnCode = "/txn50203002.ajax"
   	}
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

	return true;
}

function handleRespose(){
	var xmlResults = xmlObj.responseXML;
	if (xmlObj.readyState == 4) { // �ж϶���״̬
		_hideProcessHintWindow();
		if (xmlObj.status != 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
			window.alert("���������ҳ�����쳣��");
			var rightObj = window.parent.document.getElementById("selected_user");
			var preClicked = window.parent.document.getElementById("preSelectedIdx").value;
			rightObj.selectedIndex = preClicked;
			rightObj.options[preClicked].click();
			rightObj.options[preClicked].selected = true;
			rightObj.options[rightObj.selectedIndex].selected = false;
		}
		var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
		if(errCode != "000000"){
		    alert("�������" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
		    var rightObj = window.parent.document.getElementById("selected_user");
		    var preClicked = window.parent.document.getElementById("preSelectedIdx").value;
			rightObj.selectedIndex = preClicked;
		    return;
		}
    	alert("����ɹ���");
    	if(jump){
    		window.parent.goBack();//
    	}else{
	    	window.parent.queryConfig("source_user", "selected_user");
	    	var newId = _getXmlNodeValue( xmlResults, "/context/record/sys_svr_config_id" );
	    	var preClicked = window.parent.document.getElementById("preSelectedIdx").value;
	    	window.parent.document.getElementById("selected_user").options[preClicked].cfgId = newId;
		}
	}
}

/**
 * ��ù����ֶ�����
 * @return Ԫ��Ϊcheckbox��������
 */
function getPermitColumns(){
	var m = document.getElementsByName("permitColumn");
	var alias = document.getElementsByName("colAlias");
	var checkedColArray = new Array();//����ѡ�е��ֶ�
	for ( var i=0; i< m.length; i++){
		if(m[i].checked){
        	m[i].alias = alias[i].value.trim();//Ϊѡ�е�checkbox��һ�������ԡ�alias���������Ӧ�ı���
        	checkedColArray.push(m[i]);//��ѡ�е��ֶ���ӵ�������
        }
    }
    return checkedColArray;
}

var colArray = null;
function validateSelectedColumns(){
	colArray = getPermitColumns();
	if(colArray.length == 0){
		alert("��ѡ�����ֶΣ�");
		return false;
	}
	for(var i = 0; i < colArray.length; i++){
		var colName = "";
		if(colArray[i].alias){//�ж��Ƿ��û�ָ���˱���������ʹ���ֶ���
			if(colArray[i].colName == colArray[i].alias){
				alert("������"+ colArray[i].alias +"�����ֶ�����ͬ��");
	      		return false;
			}
			colName = colArray[i].alias;
			var reg = new RegExp("^[a-zA-Z][a-zA-Z0-9#$_]{0,29}$");
			if(!reg.test(colName)){
				alert("����ȷ��д���ֶα�������\r\nע�⣺ֻ������ĸ��ͷ��������ĸ�����֡�_����ҳ��Ȳ�����30λ��");
	      		return false;
			}
		}else{
			colName = colArray[i].colName;
		}
		
		for(var j = 0; j < colArray.length; j++){
			if(i == j){//��Ҫ�Լ����Լ��Ƚ�
				continue;
			}
			
			if((colArray[j].alias && (colName == colArray[j].alias)) || colName == colArray[j].colName){
				alert("�ֶ�����"+colName+"���ظ�����������ԣ�");
				return false;
			}
		}
	}
	return true;
}

	function createSQL(){
		var sql = "SELECT ";
		var colStr = "";
		for ( var i=0; i< colArray.length; i++)
	    {
	    	colStr += colArray[i].tblName + "." + colArray[i].colName;
	    	if(colArray[i].alias){
	    		colStr += " AS " + colArray[i].alias;
	    	}
	    	
	    	if(i != colArray.length - 1){
	    		colStr += ", ";
	    	}
	    }
	    sql += colStr + " FROM ";
	    var tblObj = document.getElementById("tempSelect").options;
	    var tblStr = "";
	    for( var i=0; i< tblObj.length; i++){
	    	tblStr += tblObj[i].tblName;
	    	if(i != tblObj.length - 1){
	    		tblStr += ", ";
	    	}else{
	    		tblStr += " ";
	    	}
	    }
	    sql += tblStr;
	    
	    //��ӷ������õı����Ӳ���
	    var tblConnStr = "";
	    if(svrParams.length > 0){
	    	tblConnStr += " WHERE ( ";
		    for(var i = 0; i < svrParams.length; i++){
		    	tblConnStr += " " + svrParams[i].condition
		    			    + " " + svrParams[i].tableOneName
		    			    + "." + svrParams[i].columnOneName;
		    			    if(svrParams[i].relation != '='){
		    			    	tblConnStr += '=';
		    			    }else{
		    			    	tblConnStr += " " + svrParams[i].relation
		    			    }
		    			    tblConnStr += " " + svrParams[i].tableTwoName
		    			    + "." + svrParams[i].columnTwoName;
		    			    if(svrParams[i].relation == '=%2B'){
							 	tblConnStr += '(%2B)';
							}
		    			    + " " + svrParams[i].postParen;
		    }
	   	 	tblConnStr += " ) ";
	    }
	    sql += tblConnStr;
	    
	    //������õ�ϵͳ����
	    var sysParam = qcObj.getQueryConditionStr();
	    if(sysParam.trim() != ""){
	    	if(sql.indexOf("WHERE") > -1){
	    		sql += " AND (" + sysParam + " )";
	    	}else{
	    		sql += " WHERE (" + sysParam + " )";
	    	}
	    }
	    
	    //������õ��û�����
	    var userParam = qcObj2.getQueryConditionStr();
	    if(userParam.trim() != ""){
	    	if(sql.indexOf("WHERE") > -1){
	    		sql += " AND (" + userParam + " )";
	    	}else{
	    		sql += " WHERE (" + userParam + " )";
	    	}
	    }
	    
	    return sql;
	}
	
 	var saveButtons = document.getElementsByName("toSaveButton");
 	for(var i = 0; i < saveButtons.length; i++){
 		saveButtons[i].onclick = function() {
 			toAdd(true);
 		}
 	}
 	
 	document.getElementById("cAll").onclick = function(){ 
	    var f = document.getElementById("cAll");
	    var m = document.getElementsByName("permitColumn");
	    var l = m.length;
	    if(f.checked){
		    for ( var i=0; i< l; i++)
		    {
		        m[i].checked = true;
		    }
	    }else{
	    	for ( var i=0; i< l; i++)
		    {
		        m[i].checked = false;
		    }
	    }
	}
	doInitQuery();
</script>
</freeze:html>
