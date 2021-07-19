<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>查询范围定义</title>
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
			      <td>共享字段选择</td>
			    </tr>
			    <tr class="framerow">
		          <td>
		          	<table id="colTbl" width='100%' cellpadding='0' cellspacing='0' border="1" bordercolor="#cccccc" style="border-collapse:collapse">
						<tr height='30' >
						    <td width="1%" align="center" nowrap="nowrap">全选<input id="cAll" type="checkBox" /></td>
						    <td width="33%" align="center" nowrap="nowrap">字段名称</td>
						    <td width="33%" align="center" nowrap="nowrap">字段描述</td>
						    <td width="33%" align="center" nowrap="nowrap">字段别名</td>
						</tr>
                   		<tbody></tbody>
                    </table>
		          </td>
		        </tr>
		    </table>	
	        <p><center><input type='button' id='toStep2Button' class="menu" value='下一步'/>
	        <input type='button' id='toSaveButton' class="menu" value=' 保 存 '/>
	        <input type='button' id='goBack0' class="menu" value=' 返 回 ' onclick="window.parent.goBack();"/></center></p>
    </div>
        
    <div id="step2DIV" style="display:none"></div>
    <div id="step2ButtonDiv" style="display:none">
    	<select id="tempSelect" name="tempSelect" style="display:none"></select>
    	<p><center><input type="button" id="preStep1Button" class="menu" value="上一步" />
	    <input type='button' id='toStep3Button' class="menu" value='下一步'/>
    	<input type="button" id="toSaveButton" class="menu" value=" 保 存 " />
     <input type="button" id="goBack1" class="menu" value=" 返 回 " onclick="window.parent.goBack();" /></center></p>
    </div>
        
        
    <div id="step3DIV" style="display:none"></div>
    <div id="step3ButtonDiv" style="display:none">
    	<p><center><input type='button' id='preStep2Button' class="menu" value="上一步"/>
    	<input type="button" id="toSaveButton" class="menu" value=" 保 存 " />
     <input type='button' id='goBack2' class="menu" value=" 返 回 " onclick="window.parent.goBack();"/></center></p>
    </div>
</freeze:body>
<script language="javascript">
var onlyForUserParam = "（参数值）";

function queryCondition2(config){
	this.addEvent = queryCondition2addEvent;
	return this;
}
// 添加事件
queryCondition2addEvent = function(){
    var p = this;
    // 选择表时
    var tableOne = document.getElementById(this.id + "TableOne");
    if (tableOne){
        tableOne.onchange = function(){
            p.getColumns(this.options[this.selectedIndex].value, p.id + "ColumnOne");
        };
    }
    tableOne = null;
    
    // 选择字段时
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
             * 1. 字符串
             * 2. 日期
             * 3. 数字
             * codeValue:
             * 当columnType为1且codeValue有值时，为码表
             */
            if (columnType == 1){ // 字符串
            	if (codeValue){//码表
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
            	}else{// 普通字符串
            		paramValueInput.style.display = "block";
            		paramValueInput.value = onlyForUserParam;
                    paramValueSelect.style.display = "none";
                    paramValueDateSelect.style.display = "none";
                    RelationString.style.display = "block";
                    RelationCode.style.display = "none";
                    RelationDate.style.display = "none";
                    RelationNumber.style.display = "none";
            	}
            }else if (columnType == 2){ // 日期
            	paramValueInput.style.display = "none";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "block";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "block";
                RelationNumber.style.display = "none";
           		paramValueDateSelect.value = onlyForUserParam;
            }else if (columnType == 3){ // 数字
                paramValueInput.style.display = "block";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "none";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "none";
                RelationNumber.style.display = "block";
           		paramValueInput.value = onlyForUserParam;
            }else{// 不让输入
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
    
    // 添加事件
    var addButton = document.getElementById(this.id + "AddButton");
    if (addButton){
        addButton.onclick = function(){
        	p.addQueryCondition();
        }
    }
};
document.getElementById("body-div").onresize = function(){
	//显示设置frame窗口尺寸
    testResizeFrame();
};

var svrId = "<%= request.getParameter("svrId") %>";
var svrName = "<%= request.getParameter("svrName") %>";
var userId = "<%= request.getParameter("userId")%>";
var userName = "<%= request.getParameter("userName")%>";

var steps = new stepHelp({
      id				: "steps",     // 组件的默认ID，如果一个页面中有多个该组件，注意该ID不可重复
      parentContainer	: "stepsContainerDiv", // 其父节点的ID
      textArray		: ["选择共享字段", "配置系统参数", "配置接口参数"]		 // 字符串数组
   });
  
   var qcObj;    //系统参数组件
   var qcObj2;   //用户参数组件
   var svrParams;//服务的表连接参数
   var configId; //配置id，如果是已配置的服务会有此值
function doInitQuery(){
	$.get("<%= request.getContextPath()%>/txn50203004.ajax?select-key:sys_svr_user_id=" + userId + "&select-key:sys_svr_service_id=" + svrId, function(xml){
		//document.getElementById("test").innerText = xml.xml;
		//设置服务的表连接参数
		var params = xml.selectNodes("//record");
		svrParams = new Array();
		for(var i = 0; i < params.length; i++){
			var param = params[i].selectSingleNode("params").text;//获得服务的表连接参数
			if(param.trim() != ""){
				svrParams.push(eval("(" + param + ")"));
			}
		}
		configId = xml.selectSingleNode("//config-inf/sys_svr_config_id").text;//获得配置id
		
		var cols = xml.selectNodes("//col-info");//获得所有显示字段
		
		var permitCol = xml.selectSingleNode("//config-inf/permit_column").text;//如果是已经配置的服务，则获得共享字段
		var permitCols = null;
		if(permitCol){
			permitCols = permitCol.split(",");
		}
		
		var aliasStr = xml.selectSingleNode("//config-inf/column_alias").text;//如果是已经配置的服务，则获得共享字段别名
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
	    	//向表格追加字段信息
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
				//添加别名
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
	    
	    forQCondition.options.length = 0;//首先清空下拉框
	    var tbls = xml.selectNodes("//tbl-info");//获得所有的表
	    
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
           compCond_String     : [{text:'等于', value:'=', prefix:'\'', postfix:'\''},
                                  {text:'不等于', value:'<>', prefix:'\'', postfix:'\''},
                                  {text:'开始于', value:'like', prefix:'\'', postfix:'%\''},
                                  {text:'结束于', value:'like', prefix:'\'%', postfix:'\''},
                                  {text:'包含', value:'like', prefix:'\'%', postfix:'%\''},
                                  {text:'IN', value:'IN', prefix:'(\'', postfix:'\')'}]
        });
        
        qcObj2 = queryCondition2.call(qq);
        qcObj2.addEvent();
        
        qcObj2.setDataFromSelect("tempSelect");
	    var configParams = xml.selectNodes("//config-param");//获得所有配置参数
	   
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
	    	con1.options.add(createOption('AND','并且'));
	    	con1.options.add(createOption('OR','或者'));
	    	con1.disabled = false;
	    }
	    
	    var objTable = document.getElementById("conditionTbl_" + qcObj2.id);
	    if(objTable.rows.length > 0){
	    	var con1 = document.getElementById(qcObj2.id + "Condition");
	    	con1.options.length = 0;
	    	con1.options.add(createOption('AND','并且'));
	    	con1.options.add(createOption('OR','或者'));
	    	con1.disabled = false;
	    }
	    //显示设置frame窗口尺寸
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
	
		//添加列
		var newTd0 = newTr.insertCell();
		var newTd1 = newTr.insertCell();
	
		//设置列内容和属性
		newTd0.innerHTML = paramStr; 
		newTd0.width = "90%";
		newTd0.align = "left";
		newTd1.innerHTML= "<a href='javascript:void(0)' id='deleteConnCondition_" + p.id + "'>删除</a>";
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

	// 步骤一的下一步按钮事件
 	document.getElementById("toStep2Button").onclick = function(){
 		//首先检查是否有同名的字段
		if(!validateSelectedColumns()){
			return;
		}
		document.getElementById("step2DIV").style.display = "block";
		document.getElementById("step2ButtonDiv").style.display = "block";
		document.getElementById("step1DIV").style.display = "none";
		steps.setCurrentStep(1);
		//显示设置frame窗口尺寸
	    testResizeFrame();
 	}

 	// 步骤二的上一步按钮事件
 	document.getElementById("preStep1Button").onclick = function(){
		document.getElementById("step2DIV").style.display = "none";
		document.getElementById("step2ButtonDiv").style.display = "none";
		document.getElementById("step1DIV").style.display = "block";
		steps.setCurrentStep(0);
		//显示设置frame窗口尺寸
	    testResizeFrame();
 	}

 	// 步骤二的下一步按钮事件
 	document.getElementById("toStep3Button").onclick = function(){
		document.getElementById("step3DIV").style.display = "block";
		document.getElementById("step3ButtonDiv").style.display = "block";
		document.getElementById("step2DIV").style.display = "none";
		document.getElementById("step2ButtonDiv").style.display = "none";
		steps.setCurrentStep(2);
		//显示设置frame窗口尺寸
	    testResizeFrame();
 	}
  	
 	// 步骤三的上一步按钮事件
 	document.getElementById("preStep2Button").onclick = function(){
		document.getElementById("step3DIV").style.display = "none";
		document.getElementById("step3ButtonDiv").style.display = "none";
		document.getElementById("step2DIV").style.display = "block";
		document.getElementById("step2ButtonDiv").style.display = "block";
		steps.setCurrentStep(1);
		//显示设置frame窗口尺寸
	    testResizeFrame();
 	}
 	
 	var xmlObj = false;

      //创建XMLHttpRequest对象
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
	//_showProcessHintWindow( "正在保存数据，请稍候....." );
    //alert("svrName = "+svrName+", userName = "+userName);
	//首先检查是否有同名的字段
	if(!validateSelectedColumns()){
		return false;
	}
	
	//循环字段信息
    //循环实际选中的字段数组
	var cbArray = new Array();
	var colNameArray = new Array();
	var colCnNameArray = new Array();
	var aliasJSONStr = "{";
    for ( var i=0; i< colArray.length; i++)
    {
       	cbArray.push(colArray[i].value);
       	if(colArray[i].alias){//如果指定了别名，则保持别名为英文名
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
   		 
   	
   	var txnCode = "/txn50203003.ajax";//默认为新建
   	if(configId){//如果有配置ID，则执行更新
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
	if (xmlObj.readyState == 4) { // 判断对象状态
		_hideProcessHintWindow();
		if (xmlObj.status != 200) { // 信息已经成功返回，开始处理信息
			window.alert("您所请求的页面有异常。");
			var rightObj = window.parent.document.getElementById("selected_user");
			var preClicked = window.parent.document.getElementById("preSelectedIdx").value;
			rightObj.selectedIndex = preClicked;
			rightObj.options[preClicked].click();
			rightObj.options[preClicked].selected = true;
			rightObj.options[rightObj.selectedIndex].selected = false;
		}
		var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
		if(errCode != "000000"){
		    alert("处理错误：" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
		    var rightObj = window.parent.document.getElementById("selected_user");
		    var preClicked = window.parent.document.getElementById("preSelectedIdx").value;
			rightObj.selectedIndex = preClicked;
		    return;
		}
    	alert("保存成功！");
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
 * 获得共享字段数组
 * @return 元素为checkbox对象数组
 */
function getPermitColumns(){
	var m = document.getElementsByName("permitColumn");
	var alias = document.getElementsByName("colAlias");
	var checkedColArray = new Array();//保存选中的字段
	for ( var i=0; i< m.length; i++){
		if(m[i].checked){
        	m[i].alias = alias[i].value.trim();//为选中的checkbox赋一个新属性“alias”，保存对应的别名
        	checkedColArray.push(m[i]);//将选中的字段添加到数组中
        }
    }
    return checkedColArray;
}

var colArray = null;
function validateSelectedColumns(){
	colArray = getPermitColumns();
	if(colArray.length == 0){
		alert("请选择共享字段！");
		return false;
	}
	for(var i = 0; i < colArray.length; i++){
		var colName = "";
		if(colArray[i].alias){//判断是否用户指定了别名，否则使用字段名
			if(colArray[i].colName == colArray[i].alias){
				alert("别名【"+ colArray[i].alias +"】与字段名相同！");
	      		return false;
			}
			colName = colArray[i].alias;
			var reg = new RegExp("^[a-zA-Z][a-zA-Z0-9#$_]{0,29}$");
			if(!reg.test(colName)){
				alert("请正确填写【字段别名】！\r\n注意：只能由字母开头并且由字母、数字、_组成且长度不超过30位！");
	      		return false;
			}
		}else{
			colName = colArray[i].colName;
		}
		
		for(var j = 0; j < colArray.length; j++){
			if(i == j){//不要自己和自己比较
				continue;
			}
			
			if((colArray[j].alias && (colName == colArray[j].alias)) || colName == colArray[j].colName){
				alert("字段名【"+colName+"】重复，请检查后重试！");
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
	    
	    //添加服务配置的表连接参数
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
	    
	    //获得配置的系统参数
	    var sysParam = qcObj.getQueryConditionStr();
	    if(sysParam.trim() != ""){
	    	if(sql.indexOf("WHERE") > -1){
	    		sql += " AND (" + sysParam + " )";
	    	}else{
	    		sql += " WHERE (" + sysParam + " )";
	    	}
	    }
	    
	    //获得配置的用户参数
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
