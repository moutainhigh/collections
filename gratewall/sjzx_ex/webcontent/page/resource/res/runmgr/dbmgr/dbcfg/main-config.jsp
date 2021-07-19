<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="650" height="350">
<head>
	<title>视图配置详细</title>
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
	    alert("处理错误："+errDesc);
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
		forQCondition.options.length = 0;//首先清空下拉框
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
		    con1.options.add(createOption('AND','并且'));
		    con1.options.add(createOption('OR','或者'));
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
		
			//添加列
			var newTd0 = newTr.insertCell();
			var newTd1 = newTr.insertCell();
		
			//设置列内容和属性
			newTd0.innerHTML = paramStr; 
			newTd0.width = "90%";
			newTd0.align = "left";
			newTd1.innerHTML= "<input type='button' id='deleteConnCondition_" + p.id + "' value=' 删除 ' class='menu' />";
			newTd1.width = "10%";
			newTd1.align = "center";
			
			document.getElementById(p.id + "hideRow").style.display = "block";
			
			var delButton = document.getElementsByName("deleteConnCondition_"+p.id);
		    var obj = delButton[delButton.length - 1];
		    if (obj){
		    	obj.onclick = function(){
		    		if(confirm("确认删除此条件吗？")){
						p.deleteCondition(this.parentNode.parentNode.rowIndex);
		    		}
		    	};
		    }
	  	}
	  	
	  	function getParamCN(v){
			switch(v){
				case 'AND':
					return '并且';
					break;
				case 'OR':
					return '或者';
					break;
				case '>':
					return '大于';
					break;
				case '<':
					return '小于';
					break;
				case '<>':
					return '不等于';
					break;
				case '=':
					return '等于';
					break;
				case 'like':
					return '包括';
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
//创建XMLHttpRequest对象
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





//保存成功后是否跳转到列表页面
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
	if (xmlObj.readyState == 4) { // 判断对象状态
		if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
            var selObj = document.getElementById("selected_" + obj.selectPrefix);
			if(errCode != "000000"){
			    alert("处理错误：" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
                selObj.options[selObj.selectedIndex].selected = false;
                selObj.options[selectedIndex].selected = true;
			    return;
			}		
		    alert("保存成功!");
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
		}else { //页面不正常
			alert("您所请求的页面有异常。");
		}
	}
}

function afterUpdate(){
	var xmlResults = xmlObj.responseXML;
	if (xmlObj.readyState == 4) { // 判断对象状态
		if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
			if(errCode != "000000"){
			    alert("处理错误：" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
                var selObj = document.getElementById("selected_" + obj.selectPrefix);
                selObj.options[selObj.selectedIndex].selected = false;
                selObj.options[selectedIndex].selected = true;
			    return;
			}		
		    alert("保存成功!");
		    saveFlag = true;
		    _hideProcessHintWindow();
    	    if(jump){
    	        goBack();
	 	    }	    
		}else { //页面不正常
			alert("您所请求的页面有异常。");
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
            alert('请输入【配置名称】！');
            return false;
        }else{
            if (reg.test(cfgName)==false){
		      alert("请正确填写【配置名称】！\r\n注意：只能由字母开头并且由字母、数字、_组成且长度不超过30位！");
		      return false;
	        }
        }
        /*
        var viewObj = document.getElementById("selected_table");
        for(var j=0;j<viewObj.options.length;j++){
            if(j!=selectedIndex&&cfgName==viewObj.options[j].config_name){
                alert('该【配置名称】已经被使用,请重新输入！');
                return false;
            }
        }
        */
        if(colNameArray.length==0){
            alert('请选择共享字段！');
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
		         alert("您选择的"+colNameArray[ind]+"是重名字段，请您为该字段填写【字段别名】！");
		         return false;
             }
           }           
        }
        
        str=',';
        for(var i=0;i<aliasArray.length;i++){
           if(aliasArray[i]!=null&&aliasArray[i]!=''&&aliasArray[i]!='null'){
             if (reg.test(aliasArray[i])==false){
		         alert("请正确填写【字段别名】!\r\n注意：只能由字母开头并且由字母、数字、_组成且长度不超过30位！");
		         return false;
	         }
	         if(colNameArray.toString().toUpperCase().indexOf(","+aliasArray[i].toUpperCase()+",")>=0){
	             alert("【字段别名】"+aliasArray[i]+"不能与【字段名】重复！");
	             return false;
	         }
             if(str.toUpperCase().indexOf(","+aliasArray[i].toUpperCase()+",")<0){
                 str = str+aliasArray[i]+',';
             }else{
		         alert("【字段别名】不能重复！");
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
	    if(user_type=='0'){ //中心库数据库用户
	      cols = cols + zxkuser + '.' + fullColNameArray[j];
	    }else if(user_type=='1'){ //共享库数据库用户
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
	if (xmlObj.readyState == 4) { // 判断对象状态
		if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
			if(errCode != "000000"){
			    alert("处理错误：" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
			    return;
			}		
		    alert("删除成功!");
    	    ajaxObj = null;
		    clickViewToLeft();
		    var grant_table = _getXmlNodeValue( xmlResults, "record:grant_table" );
		    var userObj = document.getElementById('source_table');
		    userObj.options[userObj.selectedIndex].grant_table = grant_table;
		    userObj.setAttribute("disabled", false);
			userObj.fireEvent("onchange");
			userObj.setAttribute("disabled", true);	        
		}else { //页面不正常
			alert("您所请求的页面有异常。");
		}
	}
}

function afterAllDelete(){
	var xmlResults = xmlObj.responseXML;
	if (xmlObj.readyState == 4) { // 判断对象状态
		if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
			if(errCode != "000000"){
			    alert("处理错误：" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
			    return;
			}		
		    alert("删除成功!");
    	    ajaxObj = null;
		    clickAllViewToLeft();  
		    var grant_table = _getXmlNodeValue( xmlResults, "record:grant_table" );
		    var userObj = document.getElementById('source_table');
		    userObj.options[userObj.selectedIndex].grant_table = grant_table;
		    userObj.setAttribute("disabled", false);
			userObj.fireEvent("onchange");
			userObj.setAttribute("disabled", true);	   
		}else { //页面不正常
			alert("您所请求的页面有异常。");
		}
	}
}
function clickAllViewToLeft(){
	var b = document.getElementById("from_" + obj.selectPrefix);
	var a = document.getElementById("selected_" + obj.selectPrefix);
	for(var i=a.options.length-1; i >= 0; i--){
		a.options[i].selected = false;//将选中属性设为false，避免过去后就是选中状态
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
		optionArray[i].selected = false;//将选中属性设为false，避免过去后就是选中状态
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
// 在删除时得到选择的视图中那些表的查询权限需要回收
function getRevokeTables(flag){
    var selectedTableId = "";
    var notSelectedTableId = "";
    var revokeTableId = "";
	var optionArray = document.getElementById("selected_" + obj.selectPrefix);
	for(var i=0; i < optionArray.length; i++){
		if(optionArray[i].selected){
		//得到选择的视图中包括的表id
		     if(optionArray[i].table_no){
		     	if(selectedTableId!=""){
		         	selectedTableId = selectedTableId+",";
		     	}
		     	selectedTableId = selectedTableId + optionArray[i].table_no;
		     }
		}else{
		//得到未选择的视图中包括的表id
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
		//如果没有选择视图，则不需收回任何表的访问权限
		if(selectedTableId==""){
		} else if(notSelectedTableId == ""){
	    	//如果选择所有视图，回收全部视图包括的表的访问权限，此时需要去除多个视图包括的重复表
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
	    	//回收选择视图包括的表的访问权限，如果未选择的视图包括该表，则不能回收
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


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	  	obj = new dataSelectHTMLObj({
	  		rootPath:rootPath,					//z根目录路径
	  		selectPrefix:"table",				//下拉框id后缀
	  		text_srcTitle:"*选择用户",			//标题
	  		text_selectSrcTitle:"*选择视图",	//标题
	  		tableContainer:"div1",				//向哪个div填充组件一
	  		txnCode:"/txn52103006.ajax",		//顶部下拉框改变时执行的交易
	  		paramStr:"?select-key:sys_db_user_id=",			//顶部下拉框改变时执行的交易参数前缀
	  		fillObjId:"selected_table",				//顶部下拉框改变时执行的交易数据返回后向哪个对象填充数据
	  		optionValue:"sys_db_view_id",				//被填充下拉框对象的value属性取值
	  		optionText:"view_name",			//被填充下拉框对象的显示值
	  		//oncontentchange:updateRelationObj,	//当已选对象内容改变后执行的函数
	  		//beforecontentchange:scanConditions,	//当已选对象内容改变前是执行的函数
	  		addtionalParam:{config_name:"config_name",sys_db_config_id:"sys_db_config_id",table_no:"table_no"} //已选对象需要添加的附加属性
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
			if(confirm("是否保存当前配置？")){
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
			_showProcessHintWindow( "正在查询，请稍候....." );		
			var page = new pageDefine( "txn52103004.ajax", "查询数据库视图配置" );
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
	
	        if(optionArray){//如果未选择任何项，则进行提示
    			if(!confirm("确认删除所选视图吗?")){
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
		        alert("请选择要删除的视图！");
	        }
	        */		
		};
	}
	
	
	//删除一个或多个项
	var bObj = document.getElementById("deleteOne_" + obj.selectPrefix);
	if (bObj){
		bObj.onclick = function(){
	        var optionArray = getSelectedOptions("selected_" + obj.selectPrefix);
	
	        if(optionArray){//如果未选择任何项，则进行提示
    			if(!confirm("确认删除所选视图吗?")){
    			    return;
    			}
    			func_delete(optionArray,'one');        
	        }else{
		        alert("请选择要删除的视图！");
	        }
		};
	}
	
	//删除所有项
	bObj = document.getElementById("deleteAll_" + obj.selectPrefix);
	if (bObj){
		bObj.onclick = function(){
			var a=document.getElementById("selected_"+obj.selectPrefix);
			if(a.options==null||a.options.length==0)
			    return;

    		if(!confirm("确认删除所选视图吗?")){
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
	<freeze:title caption="视图配置详细" />
	<freeze:errors />
	    <div id="step0DIV" style="display:none"><select name='selected_tables' id='selected_tables' multiple='true' class='dataSelect'></select></div> 
		<div id="step1DIV">
			<table width="95%" border="0" align="center" class="frame-body"
				cellpadding="0" cellspacing="1">
				<tr class="title-row">
					<td>
						视图配置
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
										*配置名称：
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
	        <input type='button' id='goBack00' class="menu" value=' 返 回 ' onclick="goBack();"/>
	    </div>
		
		<br>
		<div id="step2DIV" style="display:none">

			<table border="0" cellspacing="0" cellpadding="0" width="95%" align="center">
				<tr>
					<td>
						<table width='100%' cellspacing='0' cellpadding='0' border='0'>
							<tr class='title-row'>
								<td>
									&nbsp;选择字段
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
									字段名称
								</td>
								<td align="center" id='label:record:sys_name' width="30%">
									字段描述
								</td>
								<td align="center" id='label:record:sys_simple' width="40%">
									字段别名
								</td>
							</tr>
						</table>
				</td>
				</tr>
			</table>
			<p>
			<center>
				<input name="button" type="button" class="menu" id='toStepButton'
					onClick="toStep2();" value="下一步" />
				<input name="button" type="button" class="menu" id='goBack1'
					onClick="window.goBack();" value=" 返 回 " />
			</center>
			</p>
		</div>

		<div id="step3DIV" style="display:none" onclick="changeToSave();"></div>
		<div id="step3DIVButton" style="display:none">
			<p>
			<center>
				<input type='button' id='preStepButton' class="menu" value="上一步" />
				<input type='button' id='save' class="menu" value="保存" />
				<input type='button' id='goBack2' class="menu" value=" 返 回 "
					onclick="window.goBack();" />
			</center>
			</p>
		</div>
</freeze:body>
</freeze:html>
