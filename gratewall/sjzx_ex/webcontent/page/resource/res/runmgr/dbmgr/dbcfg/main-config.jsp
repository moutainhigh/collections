<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="650" height="350">
<head>
	<title>��ͼ������ϸ</title>
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
</head>

<script language="javascript">
var dbUserName = "<%=request.getParameter("record:login_name")%>";

var ajaxObj = null;
var obj = null;
var qcObj = null;
var rootPath = "<%=request.getContextPath()%>";
var zxkuser = "<%=com.gwssi.dw.runmgr.db.DbConstant.ZXK_USER%>";
var gxkuser = "<%=com.gwssi.dw.runmgr.db.DbConstant.GXK_USER%>";
var changed = false;
var saveFlag= false;
var selectedIndex = -1;
function changeToSave(){
	changed = true;
}

var sysIndex = -1;
var userIndex = -1;


function showView(errCode, errDesc, xmlResults){
	if(errCode != '000000'){
	    alert("�������"+errDesc);
	    return;
	}else{
	    _hideProcessHintWindow();
	    var config_name = _getXmlNodeValue(xmlResults, "config-inf:config_name");
	    if(config_name!=null&&config_name!=''){
	        document.getElementById("record:config_name").value=config_name;
	        document.getElementById("record:config_name").readOnly=true;
	    }else{
	        document.getElementById("record:config_name").value="";
	        document.getElementById("record:config_name").readOnly=false;
	    }	        
	    ajaxObj = xmlResults;
	    changed = false;
	    saveFlag = false;
	    initColumn();
	    //initQueryCondition();
	    document.getElementById("step3DIV").innerHTML = "";
		document.getElementById("step3DIV").style.display = "none";
		document.getElementById("step3DIVButton").style.display = "none";
	    document.getElementById("step2DIV").style.display = "block";
	}
}

function initColumn(){
	if(ajaxObj != null){
		var objTable = document.getElementById("colTbl");
	    clearTblRows(objTable);
		document.getElementById("cAll").checked = false;
		var colNos = _getXmlNodeValues(ajaxObj,"column-info:column_no");
		var colNames = _getXmlNodeValues(ajaxObj,"column-info:column_name");
		var colNameCns = _getXmlNodeValues(ajaxObj,"column-info:column_name_cn");
		var permitCol = _getXmlNodeValue(ajaxObj,"config-inf:permit_column");
		var aliasCol = _getXmlNodeValue(ajaxObj,"config-inf:alias_column");
		var permitCols = permitCol.split(",");
		var aliasCols = aliasCol.split(",");
		var j=0;
		for(var i = 0; i < colNos.length; i++){
		  if(colNos[i]!=null&&colNos[i]!=''){
			var newTr = objTable.insertRow();
			newTr.className = "framerow";
			var newTd0 = newTr.insertCell();
			var newTd1 = newTr.insertCell();
			var newTd2 = newTr.insertCell();
			var newTd21 = newTr.insertCell();
			if(permitCol!=null&&permitCol.indexOf(colNos[i])>=0){
					newTd0.innerHTML = "<input type='checkBox' name='permitColumn' checked='checked' onclick='onCheck(this,"+i+");' value='"+colNos[i]+"'/>";
					newTd21.innerHTML = "<input type='text' id='permitAliasColumn"+i+"' name='permitAliasColumn' value='"+aliasCols[j]+"'/>";
					j++;			
			}else{
					newTd0.innerHTML = "<input type='checkBox' name='permitColumn' onclick='onCheck(this,"+i+");' value='"+colNos[i]+"'/>";
				    newTd21.innerHTML = "<input type='text' id='permitAliasColumn"+i+"' name='permitAliasColumn' readOnly=true/>";			
			}
			newTd1.innerHTML = colNames[i];
			newTd2.innerHTML = colNameCns[i];
		  }
		}
	}
}

function initQueryCondition(){
	if(ajaxObj != null){
        var forQCondition = document.getElementById("selected_tables");
		forQCondition.options.length = 0;//�������������
		var tblNos = _getXmlNodeValues(ajaxObj,"table-info:table_no");
		var tblNames = _getXmlNodeValues(ajaxObj,"table-info:table_name");
		var tblCNs = _getXmlNodeValues(ajaxObj,"table-info:table_name_cn");
		for(var i = 0; i < tblNos.length; i++){
		    if(tblNos[i]!=null&&tblNos[i]!='')		    	
		        forQCondition.options.add(createOption(tblNos[i], tblCNs[i], {tblName:tblNames[i]}));
		}
		document.getElementById('step3DIV').innerHTML = qcObj.queryConditionHtml;
		qcObj.addEvent();
		qcObj.Clear();
	    qcObj.setDataFromSelect("selected_tables");	
	    var step2Params = _getXmlNodeValues(ajaxObj,"config-param:params");
		for(var i = 0; step2Params!=null&&i < step2Params.length; i++){
		    if(step2Params[i]!=null&step2Params[i]!=''){
			    var jsonObj = eval("(" + step2Params[i] + ")");
			    					  
			    addToArray(qcObj.connJSONArray, jsonObj);		    		
			    insertParamRow(qcObj, jsonObj.paramText);
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
		step2Params = null;	
	}    
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

function clearTblRows(obj){
	for(var i=obj.rows.length-1;i>=1;i--)
  		obj.deleteRow(i);
}

function onCheck(cb,i){
    var alias = document.getElementById('permitAliasColumn'+i);

    if(cb.checked){
       alias.readOnly=false;
    }else{
       alias.value="";
       alias.readOnly=true;
    }
    changeToSave();
}

function checkAll(str1,str2){ 
    var f = document.getElementById(str1);
    var m = document.getElementsByName(str2);
    var alias = document.getElementsByName('permitAliasColumn');
    var l = m.length;
    if(f.checked){
	    for ( var i=0; i< l; i++)
	    {
	        m[i].checked = true;
	        alias[i].readOnly=false;
	    }
    }else{
    	for ( var i=0; i< l; i++)
	    {
	        m[i].checked = false;
	        alias[i].value='';
	        alias[i].readOnly=true;
	    }
    }
    changeToSave();
}

	var colArray = null;
	var colNameArray = null;
	var fullColNameArray = null;
	var colNameCNArray = null;
	var aliasArray = null;


var xmlObj = null;
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
}





//����ɹ����Ƿ���ת���б�ҳ��
var jump = false;
function saveConfig(jumpFlag){
	jump = jumpFlag;
    var txnCode = "txn52103003.ajax";
    var configId = _getXmlNodeValue(ajaxObj,"config-inf:sys_db_config_id");
    var table_ids = _getXmlNodeValues(ajaxObj,"table-info:table_no");
    var sql = createSql();
    var cfgObj = document.getElementById('record:config_name');
    var userObj = document.getElementById('source_table');
    var viewObj = document.getElementById('selected_table');
    var post = "record:config_name="+cfgObj.value;
    if( configId!=null&&configId != ""){
    	txnCode = "txn52103002.ajax";
    	post = post + "&record:sys_db_config_id="+configId;
    }
    post = post
    		 + "&record:sysParams="+qcObj.connJSONArray.toJSONString()
    		 + "&record:sys_db_view_id="+viewObj.options[selectedIndex].value
    		 + "&record:sys_db_user_id="+userObj.options[userObj.selectedIndex].value
    		 + "&record:user_type="+userObj.options[userObj.selectedIndex].user_type
    		 + "&record:login_name="+userObj.options[userObj.selectedIndex].login_name
    		 + "&record:grant_table="+userObj.options[userObj.selectedIndex].grant_table
    		 + "&record:table_no="+table_ids.toString()
    		 + "&record:query_sql="+sql
    		 + "&record:permit_column="+colArray.toString()
    		 + "&record:alias_column="+aliasArray.toString();
    
    
    post=encodeURI(post); 
	post=encodeURI(post);
    createXMLHttpRequest();
	var URL = "<%=request.getContextPath()%>/"+txnCode;
	xmlObj.open ('post',URL,false);
	if( configId != ""){
		xmlObj.onreadystatechange = afterUpdate;
	}else{
		xmlObj.onreadystatechange = afterAdd;
	}
	xmlObj.setrequestheader("cache-control","no-cache"); 
	xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	xmlObj.send(post);
    
}

function afterAdd(){
	var xmlResults = xmlObj.responseXML;
	if (xmlObj.readyState == 4) { // �ж϶���״̬
		if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
            var selObj = document.getElementById("selected_" + obj.selectPrefix);
			if(errCode != "000000"){
			    alert("�������" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
                selObj.options[selObj.selectedIndex].selected = false;
                selObj.options[selectedIndex].selected = true;
			    return;
			}		
		    alert("����ɹ�!");
		    saveFlag = true;
		    _hideProcessHintWindow();
    	    if(jump){
    	        goBack();
	 	    }else{
	 	        var grant_table = _getXmlNodeValue( xmlResults, "record:grant_table" );
	 	        var config_id = _getXmlNodeValue( xmlResults, "record:sys_db_config_id" );
	 	        var config_name = _getXmlNodeValue( xmlResults, "record:config_name" );
	 	        var table_no = _getXmlNodeValue( xmlResults, "record:table_no" );
	 	        var userObj = document.getElementById('source_table');
	 	        userObj.options[userObj.selectedIndex].grant_table = grant_table;
	 	        selObj.options[selectedIndex].sys_db_config_id = config_id;
	 	        selObj.options[selectedIndex].config_name = config_name;
	 	        selObj.options[selectedIndex].table_no = table_no;
	 	    }	    
		}else { //ҳ�治����
			alert("���������ҳ�����쳣��");
		}
	}
}

function afterUpdate(){
	var xmlResults = xmlObj.responseXML;
	if (xmlObj.readyState == 4) { // �ж϶���״̬
		if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
			if(errCode != "000000"){
			    alert("�������" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
                var selObj = document.getElementById("selected_" + obj.selectPrefix);
                selObj.options[selObj.selectedIndex].selected = false;
                selObj.options[selectedIndex].selected = true;
			    return;
			}		
		    alert("����ɹ�!");
		    saveFlag = true;
		    _hideProcessHintWindow();
    	    if(jump){
    	        goBack();
	 	    }	    
		}else { //ҳ�治����
			alert("���������ҳ�����쳣��");
		}
	}
}

function initArray(){
    colArray = null;
    aliasArray = null;
    colNameArray = null;
    colNameCNArray = null;
    fullColNameArray = null; 
    colArray = new Array();
    aliasArray = new Array();
    colNameArray = new Array();
    colNameCNArray = new Array();
    fullColNameArray = new Array();   
}
function validateValue(){
    initArray();
    var colNames = _getXmlNodeValues(ajaxObj,"column-info:column_name");
    var colNameCNs = _getXmlNodeValues(ajaxObj,"column-info:column_name_cn");
    var tabNames = _getXmlNodeValues(ajaxObj,"column-info:table_name");
	var m = document.getElementsByName("permitColumn");	
	var alias = document.getElementsByName("permitAliasColumn");
	
    for ( var i=0; i< m.length; i++)
    {
        if(m[i].checked){
        	colArray.push(m[i].value);
        	aliasArray.push(alias[i].value);
        	colNameArray.push(colNames[i]);
        	colNameCNArray.push(colNameCNs[i]);
        	fullColNameArray.push(tabNames[i]+"."+colNames[i]);
        }
    }
    
    var cfgName = document.getElementById("record:config_name").value;
    var colNames = _getXmlNodeValues(ajaxObj,"column-info:column_name");
        
        var reg = new RegExp("^[a-zA-Z][a-zA-Z0-9#$_]{0,29}$");
        if(cfgName==null||cfgName==''){
            alert('�����롾�������ơ���');
            return false;
        }else{
            if (reg.test(cfgName)==false){
		      alert("����ȷ��д���������ơ���\r\nע�⣺ֻ������ĸ��ͷ��������ĸ�����֡�_����ҳ��Ȳ�����30λ��");
		      return false;
	        }
        }
        /*
        var viewObj = document.getElementById("selected_table");
        for(var j=0;j<viewObj.options.length;j++){
            if(j!=selectedIndex&&cfgName==viewObj.options[j].config_name){
                alert('�á��������ơ��Ѿ���ʹ��,���������룡');
                return false;
            }
        }
        */
        if(colNameArray.length==0){
            alert('��ѡ�����ֶΣ�');
            return false;
        }
        
        var str=',';
        for(var ind=0;ind<colNameArray.length;ind++){
           if(colNameArray[ind]!=null&&colNameArray[ind]!=''&&colNameArray[ind]!='null'){
             if(str.toUpperCase().indexOf(","+colNameArray[ind].toUpperCase()+",")<0){
                 if(aliasArray[ind]==null||aliasArray[ind]==''){
                    str = str+colNameArray[ind]+',';
                 }
             }else if(aliasArray[ind]==null||aliasArray[ind]==''){
		         alert("��ѡ���"+colNameArray[ind]+"�������ֶΣ�����Ϊ���ֶ���д���ֶα�������");
		         return false;
             }
           }           
        }
        
        str=',';
        for(var i=0;i<aliasArray.length;i++){
           if(aliasArray[i]!=null&&aliasArray[i]!=''&&aliasArray[i]!='null'){
             if (reg.test(aliasArray[i])==false){
		         alert("����ȷ��д���ֶα�����!\r\nע�⣺ֻ������ĸ��ͷ��������ĸ�����֡�_����ҳ��Ȳ�����30λ��");
		         return false;
	         }
	         if(colNameArray.toString().toUpperCase().indexOf(","+aliasArray[i].toUpperCase()+",")>=0){
	             alert("���ֶα�����"+aliasArray[i]+"�����롾�ֶ������ظ���");
	             return false;
	         }
             if(str.toUpperCase().indexOf(","+aliasArray[i].toUpperCase()+",")<0){
                 str = str+aliasArray[i]+',';
             }else{
		         alert("���ֶα����������ظ���");
		         return false;
             }
           }           
        }
        return true;
}

function createSql(){
	var tblNames = _getXmlNodeValues(ajaxObj,"table-info:table_name");
	var userObj = document.getElementById('source_table');
	var user_type = userObj.options[userObj.selectedIndex].user_type;
	var connArray = qcObj.connJSONArray;
	var sql = '';
	
	var cols = '';
	for(var j=0;j<aliasArray.length;j++){
	    if(j>0){
	      cols = cols +", ";
	    }
	    if(user_type=='0'){ //���Ŀ����ݿ��û�
	      cols = cols + zxkuser + '.' + fullColNameArray[j];
	    }else if(user_type=='1'){ //��������ݿ��û�
	      cols = cols + gxkuser + '.' + fullColNameArray[j];
	    }
	    if(aliasArray[j]!=null&&aliasArray[j]!=''){
	        cols = cols + " as " +aliasArray[j];
	    }
	}
	var tablesFullName = '';
	for(var n=0;n<tblNames.length;n++){
	    if(n!=0)
	        tablesFullName+=',';
	    if(user_type=='0'){
	      tablesFullName = tablesFullName + zxkuser + '.' + tblNames[n];
	    }else if(user_type=='1'){
	      tablesFullName = tablesFullName + gxkuser + '.' + tblNames[n];
	    }
	}
	
	sql = 'select '+cols+' from '+tablesFullName;
	
    var paramStr = "";
	
	if(connArray && connArray.length > 0){
		
		for(var i = 0; i < connArray.length; i++){
			var jsonObj = connArray[i];
			if(user_type=='0'){
			    paramStr += jsonObj.condition +" "
					 +  jsonObj.preParen + zxkuser + '.'
					 +  jsonObj.tableOneName +"."
					 +  jsonObj.columnOneName + " "
					 +  jsonObj.relation + " "
					 +  jsonObj.paramValue
					 +  jsonObj.postParen +" ";
			}else if(user_type=='1'){
			    paramStr += jsonObj.condition +" "
					 +  jsonObj.preParen + gxkuser + '.'
					 +  jsonObj.tableOneName +"."
					 +  jsonObj.columnOneName + " "
					 +  jsonObj.relation + " "
					 +  jsonObj.paramValue
					 +  jsonObj.postParen +" ";
	        }
		}
	}
	    
	if(paramStr!=null&&paramStr!=''){
	   sql = sql + ' where ('+paramStr+') ';
	}
	var params = _getXmlNodeValues(ajaxObj,"record:params");
	var paramCondition = '';
	for(var i=0;i<params.length;i++){
	    var jsonObj = eval("(" + params[i] + ")");
	    if(user_type=='0'){
		    paramCondition +=   jsonObj.condition
		    			    + " " + jsonObj.preParen	
		    			    + " " + zxkuser + '.' + jsonObj.tableOneName
		    			    + "." + jsonObj.columnOneName;
		    if(jsonObj.relation!='='){
		        paramCondition += '=';
		    }else{
		        paramCondition += jsonObj.relation;
		    }			    
		    paramCondition +=    " " + zxkuser + '.' + jsonObj.tableTwoName
		    			    + "." + jsonObj.columnTwoName;	 
		    if(jsonObj.relation=='=%2B'){
		        paramCondition += '(%2B)';
		    }	    
		    paramCondition +=    " " + jsonObj.postParen;	
	    }else if(user_type=='1'){
		    paramCondition +=   jsonObj.condition
		    			    + " " + jsonObj.preParen	
		    			    + " " + gxkuser + '.' + jsonObj.tableOneName
		    			    + "." + jsonObj.columnOneName;
		    if(jsonObj.relation!='='){
		        paramCondition += '=';
		    }else{
		        paramCondition += jsonObj.relation;
		    }			    
		    paramCondition +=    " " + gxkuser + '.' + jsonObj.tableTwoName
		    			    + "." + jsonObj.columnTwoName;	 
		    if(jsonObj.relation=='=%2B'){
		        paramCondition += '(%2B)';
		    }	    
		    paramCondition +=    " " + jsonObj.postParen;
	    }   
	}
	if(paramCondition!=null&&paramCondition!=''){
	    if(sql.indexOf("where")>-1){
	        sql = sql + " and (" +paramCondition +")";
	    }else{
	        sql = sql + " where (" +paramCondition +")";
	    }
	}
	return sql;
}

function func_delete(optionArray,flag){
	var configIds = "";
	var configNames = "";
	var table_no = "";
	for(var i=0; i < optionArray.length; i++){
		if(optionArray[i].sys_db_config_id!=null&&optionArray[i].sys_db_config_id!=""){
			if(configIds!=""){
				configIds = configIds + ",";
				configNames = configNames + ",";
				table_no = table_no + ",";
			}
			configIds = configIds + optionArray[i].sys_db_config_id;
			configNames = configNames + optionArray[i].config_name;
			table_no = table_no + optionArray[i].table_no;
		}			        
	}
	
    if( configIds != ""){   			
    	var cfgObj = document.getElementById('record:config_name');
    	var userObj = document.getElementById('source_table');
    	var txnCode = "txn52103005.ajax";
    	var post = "record:config_name="+configNames
                               + "&record:sys_db_config_id="+configIds
                               + "&record:sys_db_user_id="+userObj.options[userObj.selectedIndex].value
    		                   + "&record:login_name="+userObj.options[userObj.selectedIndex].login_name
    		                   + "&record:user_type="+userObj.options[userObj.selectedIndex].user_type
    		                   + "&record:grant_table="+userObj.options[userObj.selectedIndex].grant_table
    		                   + "&record:old_table="+table_no;
    	                   
    	post=encodeURI(post); 
		post=encodeURI(post);
   		createXMLHttpRequest();
		var URL = "<%=request.getContextPath()%>/"+txnCode;
		xmlObj.open ('post',URL,false);
		if(flag=='one'){
			xmlObj.onreadystatechange = afterDelete;
		}else if(flag=='all'){
		    xmlObj.onreadystatechange = afterAllDelete;
		} 
		xmlObj.setrequestheader("cache-control","no-cache"); 
		xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
		xmlObj.send(post);
					
    }else{		
        if(flag=='one'){
			clickViewToLeft();  
		}else if(flag=='all'){
		    clickAllViewToLeft();
		}      
	} 
}

function afterDelete(){
	var xmlResults = xmlObj.responseXML;
	if (xmlObj.readyState == 4) { // �ж϶���״̬
		if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
			if(errCode != "000000"){
			    alert("�������" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
			    return;
			}		
		    alert("ɾ���ɹ�!");
    	    ajaxObj = null;
		    clickViewToLeft();
		    var grant_table = _getXmlNodeValue( xmlResults, "record:grant_table" );
		    var userObj = document.getElementById('source_table');
		    userObj.options[userObj.selectedIndex].grant_table = grant_table;
		    userObj.setAttribute("disabled", false);
			userObj.fireEvent("onchange");
			userObj.setAttribute("disabled", true);	        
		}else { //ҳ�治����
			alert("���������ҳ�����쳣��");
		}
	}
}

function afterAllDelete(){
	var xmlResults = xmlObj.responseXML;
	if (xmlObj.readyState == 4) { // �ж϶���״̬
		if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
			if(errCode != "000000"){
			    alert("�������" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
			    return;
			}		
		    alert("ɾ���ɹ�!");
    	    ajaxObj = null;
		    clickAllViewToLeft();  
		    var grant_table = _getXmlNodeValue( xmlResults, "record:grant_table" );
		    var userObj = document.getElementById('source_table');
		    userObj.options[userObj.selectedIndex].grant_table = grant_table;
		    userObj.setAttribute("disabled", false);
			userObj.fireEvent("onchange");
			userObj.setAttribute("disabled", true);	   
		}else { //ҳ�治����
			alert("���������ҳ�����쳣��");
		}
	}
}
function clickAllViewToLeft(){
	var b = document.getElementById("from_" + obj.selectPrefix);
	var a = document.getElementById("selected_" + obj.selectPrefix);
	for(var i=a.options.length-1; i >= 0; i--){
		a.options[i].selected = false;//��ѡ��������Ϊfalse�������ȥ�����ѡ��״̬
		b.options.appendChild(a.options[i]);			        
	}
	selectedIndex = -1;
	changed = false;
	document.getElementById("record:config_name").value="";
	document.getElementById("record:config_name").readOnly=false;
	document.getElementById("step3DIV").style.display = "none";
	document.getElementById("step3DIVButton").style.display = "none";
	document.getElementById("step2DIV").style.display = "none";
	document.getElementById("div3").style.display = "block";
}

function clickViewToLeft(){
	var b = document.getElementById("from_" + obj.selectPrefix);
	var optionArray = getSelectedOptions("selected_" + obj.selectPrefix);
	for(var i=0; i < optionArray.length; i++){
		optionArray[i].selected = false;//��ѡ��������Ϊfalse�������ȥ�����ѡ��״̬
		b.options.appendChild(optionArray[i]);			        
	}
	selectedIndex = -1;
	changed = false;
	document.getElementById("record:config_name").value="";
	document.getElementById("record:config_name").readOnly=false;
	document.getElementById("step3DIV").style.display = "none";
	document.getElementById("step3DIVButton").style.display = "none";
	document.getElementById("step2DIV").style.display = "none";
	document.getElementById("div3").style.display = "block";
}
/*
// ��ɾ��ʱ�õ�ѡ�����ͼ����Щ��Ĳ�ѯȨ����Ҫ����
function getRevokeTables(flag){
    var selectedTableId = "";
    var notSelectedTableId = "";
    var revokeTableId = "";
	var optionArray = document.getElementById("selected_" + obj.selectPrefix);
	for(var i=0; i < optionArray.length; i++){
		if(optionArray[i].selected){
		//�õ�ѡ�����ͼ�а����ı�id
		     if(optionArray[i].table_no){
		     	if(selectedTableId!=""){
		         	selectedTableId = selectedTableId+",";
		     	}
		     	selectedTableId = selectedTableId + optionArray[i].table_no;
		     }
		}else{
		//�õ�δѡ�����ͼ�а����ı�id
		     if(optionArray[i].table_no){
		     	if(notSelectedTableId!=""){
		         	notSelectedTableId = notSelectedTableId+",";
		     	}
		     	notSelectedTableId = notSelectedTableId + optionArray[i].table_no;
		     }		     
		}			        
	}
	if(flag){
	    var tmpArray = (selectedTableId+","+notSelectedTableId).split(",");
	    for(var j=0;tmpArray!=null&&j<tmpArray.length;j++){
	        if(revokeTableId.indexOf(tmpArray[j])==-1){
	        	if(revokeTableId!=""){
	            	revokeTableId = revokeTableId + ",";
	        	}
	            revokeTableId = revokeTableId + tmpArray[j];
	        }
	    }	
	}else{
		//���û��ѡ����ͼ�������ջ��κα�ķ���Ȩ��
		if(selectedTableId==""){
		} else if(notSelectedTableId == ""){
	    	//���ѡ��������ͼ������ȫ����ͼ�����ı�ķ���Ȩ�ޣ���ʱ��Ҫȥ�������ͼ�������ظ���
	    	var tmpArray = selectedTableId.split(",");
	    	for(var j=0;tmpArray!=null&&j<tmpArray.length;j++){
	        	if(revokeTableId.indexOf(tmpArray[j])==-1){
	        		if(revokeTableId!=""){
	            		revokeTableId = revokeTableId + ",";
	        		}
	            	revokeTableId = revokeTableId + tmpArray[j];
	        	}
	    	}
		} else {	
	    	//����ѡ����ͼ�����ı�ķ���Ȩ�ޣ����δѡ�����ͼ�����ñ����ܻ���
	    	var selArray = selectedTableId.split(",");
	    	var notSelArray = notSelectedTableId.split(",");
	    	for(var m=0;selArray!=null&&m<selArray.length;m++){
	        	if(notSelectedTableId.indexOf(selArray[m])==-1&&revokeTableId.indexOf(selArray[m])==-1){
		    		if(revokeTableId!=""){
	            		revokeTableId = revokeTableId + ",";
	        		}
	            	revokeTableId = revokeTableId + selArray[m];
	        	}
	    	}	    
		}
	}
	return revokeTableId;
}
*/


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	  	obj = new dataSelectHTMLObj({
	  		rootPath:rootPath,					//z��Ŀ¼·��
	  		selectPrefix:"table",				//������id��׺
	  		text_srcTitle:"*ѡ���û�",			//����
	  		text_selectSrcTitle:"*ѡ����ͼ",	//����
	  		tableContainer:"div1",				//���ĸ�div������һ
	  		txnCode:"/txn52103006.ajax",		//����������ı�ʱִ�еĽ���
	  		paramStr:"?select-key:sys_db_user_id=",			//����������ı�ʱִ�еĽ��ײ���ǰ׺
	  		fillObjId:"selected_table",				//����������ı�ʱִ�еĽ������ݷ��غ����ĸ������������
	  		optionValue:"sys_db_view_id",				//���������������value����ȡֵ
	  		optionText:"view_name",			//�����������������ʾֵ
	  		//oncontentchange:updateRelationObj,	//����ѡ�������ݸı��ִ�еĺ���
	  		//beforecontentchange:scanConditions,	//����ѡ�������ݸı�ǰ��ִ�еĺ���
	  		addtionalParam:{config_name:"config_name",sys_db_config_id:"sys_db_config_id",table_no:"table_no"} //��ѡ������Ҫ��ӵĸ�������
	  	});	
	
	  	qcObj = new queryCondition({
           id                  : "qct",
           parentContainer     : "step3Div",
           getColumnSrcPrefix  : rootPath+"/txn60210009.ajax?record:table_no=",
           getCodeSrcPrefix    : rootPath+"/txn60210012.ajax?select-key:jc_dm_id="
        });	  	  		
    
	function queryViewInfo(){
		var selObj = document.getElementById("selected_" + obj.selectPrefix);
		
		if(changed){
			if(confirm("�Ƿ񱣴浱ǰ���ã�")){
                if(!validateValue()){
                    selObj.options[selObj.selectedIndex].selected = false;
                    selObj.options[selectedIndex].selected = true;
                    return;
                }
				saveConfig(false);
			}else{
			    saveFlag = true;
			}
		}else{
		    saveFlag = true;
		}
		if(saveFlag||selectedIndex==-1){
			selectedIndex = selObj.selectedIndex;
	    	
			var viewId = selObj.options[selObj.selectedIndex].value;
			selObj = document.getElementById("source_" + obj.selectPrefix);
			var userId = selObj.options[selObj.selectedIndex].value;
			_showProcessHintWindow( "���ڲ�ѯ�����Ժ�....." );		
			var page = new pageDefine( "txn52103004.ajax", "��ѯ���ݿ���ͼ����" );
			page.addValue(viewId, "select-key:sys_db_view_id");
			page.addValue(userId, "select-key:sys_db_user_id");
			page.callAjaxService("showView");
		}
	}



		  	
	var sourceObj = document.getElementById("source_" + obj.selectPrefix);
	if (sourceObj){
		sourceObj.onchange = function(){		
	        document.getElementById("record:config_name").value="";
	        document.getElementById("record:config_name").readOnly=false;
		    document.getElementById("step3DIV").style.display = "none";
		    document.getElementById("step3DIVButton").style.display = "none";
		    document.getElementById("step2DIV").style.display = "none";
	  	    obj.queryDatas();
	  	    obj.doSelectQuery("/txn52103010.ajax", "?select-key:sys_db_user_id="+sourceObj.value, null, "from_" + obj.selectPrefix, "sys_db_view_id", "view_name");
		    if(dbUserName&&dbUserName!=''&&dbUserName!='null'){
		        sourceObj.disabled = true;
		    }
		};
	}
	
	var selectedObj = document.getElementById("selected_" + obj.selectPrefix);
	if (selectedObj){
		selectedObj.onchange = function(){
  			var right = document.getElementById("selected_" + obj.selectPrefix);
  			if(right.options.length == 0 || right.selectedIndex == -1){
  				return;
  			}	
  			document.getElementById("div3").style.display = "none";
            queryViewInfo();
		};
		
		selectedObj.ondblclick = function(){
		    /*
	        var optionArray = getSelectedOptions("selected_" + obj.selectPrefix);
	
	        if(optionArray){//���δѡ���κ���������ʾ
    			if(!confirm("ȷ��ɾ����ѡ��ͼ��?")){
    			    return;
    			}
    			//var configId = _getXmlNodeValue(ajaxObj,"config-inf:sys_db_config_id");
    			var configIds = "";
    			var configNames = "";
				for(var i=0; i < optionArray.length; i++){
					if(optionArray[i].sys_db_config_id!=null&&optionArray[i].sys_db_config_id!=""){
					    if(configIds!=""){
					         configIds = configIds + ",";
					         configNames = configNames + ",";
					    }
					    configIds = configIds + optionArray[i].sys_db_config_id;
					    configNames = configNames + optionArray[i].config_name;
					}			        
				}
    			if( configIds != ""){
    			    var tableNos = getRevokeTables();    			
    				var cfgObj = document.getElementById('record:config_name');
    				var userObj = document.getElementById('source_table');
    			    var txnCode = "txn52103005.ajax";
    				var post = "record:config_name="+configNames
                               + "&record:sys_db_config_id="+configIds
                               + "&record:sys_db_user_id="+userObj.options[userObj.selectedIndex].value
    		                   + "&record:login_name="+userObj.options[userObj.selectedIndex].login_name
    		                   + "&record:user_type="+userObj.options[userObj.selectedIndex].user_type
    		                   + "&record:tables="+tableNos;
    		                   
    				post=encodeURI(post); 
					post=encodeURI(post);
   					createXMLHttpRequest();
					var URL = "%=request.getContextPath()%>/"+txnCode;
					xmlObj.open ('post',URL,false);
					xmlObj.onreadystatechange = afterDelete;
					xmlObj.setrequestheader("cache-control","no-cache"); 
					xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
					xmlObj.send(post);
    			}else{
		        	clickViewToLeft();       
		        }       
	        }else{
		        alert("��ѡ��Ҫɾ������ͼ��");
	        }
	        */		
		};
	}
	
	
	//ɾ��һ��������
	var bObj = document.getElementById("deleteOne_" + obj.selectPrefix);
	if (bObj){
		bObj.onclick = function(){
	        var optionArray = getSelectedOptions("selected_" + obj.selectPrefix);
	
	        if(optionArray){//���δѡ���κ���������ʾ
    			if(!confirm("ȷ��ɾ����ѡ��ͼ��?")){
    			    return;
    			}
    			func_delete(optionArray,'one');        
	        }else{
		        alert("��ѡ��Ҫɾ������ͼ��");
	        }
		};
	}
	
	//ɾ��������
	bObj = document.getElementById("deleteAll_" + obj.selectPrefix);
	if (bObj){
		bObj.onclick = function(){
			var a=document.getElementById("selected_"+obj.selectPrefix);
			if(a.options==null||a.options.length==0)
			    return;

    		if(!confirm("ȷ��ɾ����ѡ��ͼ��?")){
    			return;
    		}
    		func_delete(a.options,'all');        		 
		};
	}
	
	document.getElementById("toStepButton").onclick = function(){
	    if(!validateValue()){
            return;
        }
        initQueryCondition();
		document.getElementById("step3DIV").style.display = "block";
		document.getElementById("step3DIVButton").style.display = "block";
		document.getElementById("step2DIV").style.display = "none";
	}
	document.getElementById("preStepButton").onclick = function(){
		document.getElementById("step2DIV").style.display = "block";
		document.getElementById("step3DIV").style.display = "none";
		document.getElementById("step3DIVButton").style.display = "none";
	}
	document.getElementById("save").onclick = function(){
        if(!validateValue()){
            return;
        }
	    saveConfig(true);
	}
	
	if(dbUserName&&dbUserName!=''&&dbUserName!='null'){
	    obj.doSelectQuery("/txn52101011.ajax", "?select-key:showall=1&select-key:login_name="+dbUserName, null, "source_" + obj.selectPrefix, "sys_db_user_id", "user_name",{user_type:"user_type",login_name:"login_name",grant_table:"grant_table"});
	    //document.getElementById("source_"+obj.selectPrefix).disabled = true;
	}
	else{
	   obj.doSelectQuery("/txn52101011.ajax", "?select-key:showall=1", null, "source_" + obj.selectPrefix, "sys_db_user_id", "user_name",{user_type:"user_type",login_name:"login_name",grant_table:"grant_table"});
    }
    
    //obj.doSelectQuery("/txn52101001.ajax", "?select-key:showall=1", null, "source_" + obj.selectPrefix, "sys_db_user_id", "user_name",{user_type:"user_type",login_name:"login_name"});
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:title caption="��ͼ������ϸ" />
	<freeze:errors />
	    <div id="step0DIV" style="display:none"><select name='selected_tables' id='selected_tables' multiple='true' class='dataSelect'></select></div> 
		<div id="step1DIV">
			<table width="95%" border="0" align="center" class="frame-body"
				cellpadding="0" cellspacing="1">
				<tr class="title-row">
					<td>
						��ͼ����
					</td>
				</tr>
				<tr class="framerow">
					<td>
						<div id="div1" style="height:100%;width:100%;">
						</div>
					</td>
				</tr>
				<tr class="framerow">
					<td>
						<div id="div2" style="height:100%;width:100%;">
							<table cellpadding='0' style='table-layout:fixed;'
								cellspacing='0' border='0' align='center' width='75%'>
								<tr height='30'>
									<td align='right' width='100' nowrap='nowrap'>
										*�������ƣ�
									</td>
									<td align='left' width='200'>
										<input type="text" id="record:config_name"
											name="record:config_name" value=""/>
									</td>
									<td></td>
									<td width='200'></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>	
		<div id="div3" style="margin-top:10px;" align="center">
	        <input type='button' id='goBack00' class="menu" value=' �� �� ' onclick="goBack();"/>
	    </div>
		
		<br>
		<div id="step2DIV" style="display:none">

			<table border="0" cellspacing="0" cellpadding="0" width="95%" align="center">
				<tr>
					<td>
						<table width='100%' cellspacing='0' cellpadding='0' border='0'>
							<tr class='title-row'>
								<td>
									&nbsp;ѡ���ֶ�
								</td>
								<td align="right">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table id="colTbl" border='0'  cellspacing="1" class="frame-body"
							cellpadding="0">
							<tr height="22" class="grid-headrow">
								<td width=22>
									<input id="cAll" type="checkBox"
										onclick="checkAll('cAll','permitColumn')" />
								</td>
								<td align="center" id='label:record:sys_no' width="30%">
									�ֶ�����
								</td>
								<td align="center" id='label:record:sys_name' width="30%">
									�ֶ�����
								</td>
								<td align="center" id='label:record:sys_simple' width="40%">
									�ֶα���
								</td>
							</tr>
						</table>
				</td>
				</tr>
			</table>
			<p>
			<center>
				<input name="button" type="button" class="menu" id='toStepButton'
					onClick="toStep2();" value="��һ��" />
				<input name="button" type="button" class="menu" id='goBack1'
					onClick="window.goBack();" value=" �� �� " />
			</center>
			</p>
		</div>

		<div id="step3DIV" style="display:none" onclick="changeToSave();"></div>
		<div id="step3DIVButton" style="display:none">
			<p>
			<center>
				<input type='button' id='preStepButton' class="menu" value="��һ��" />
				<input type='button' id='save' class="menu" value="����" />
				<input type='button' id='goBack2' class="menu" value=" �� �� "
					onclick="window.goBack();" />
			</center>
			</p>
		</div>
</freeze:body>
</freeze:html>
