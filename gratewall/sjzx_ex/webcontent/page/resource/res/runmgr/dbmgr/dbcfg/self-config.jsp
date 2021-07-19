<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="650" height="350">
<head>
	<title>灵活配置详细</title>
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
	<SCRIPT language="javaScript" type="text/javascript"
		src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
</head>

<script language="javascript">
var user_id = window.parent.document.getElementById("select-key:sys_db_user_id").value;
var login_name = window.parent.document.getElementById("select-key:login_name").value;
var user_name = window.parent.document.getElementById("select-key:user_name").value;
var user_type = window.parent.document.getElementById("select-key:user_type").value;
var grant_table = window.parent.document.getElementById("select-key:grant_table").value;
var configId = "<%=request.getParameter("configId")%>";
var update = "<%=request.getParameter("update")%>";
var rootPath = "<%=request.getContextPath()%>";

var zxkuser = "<%=com.gwssi.dw.runmgr.db.DbConstant.ZXK_USER%>";
var gxkuser = "<%=com.gwssi.dw.runmgr.db.DbConstant.GXK_USER%>";
//修改时保存配置数据
var ajaxXml = null;
var obj = null;

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

function initUserSelected(){
   var userSelecteObj = document.getElementById("userSelected");
   if(userSelecteObj){
		var option1 = document.createElement("option");
		option1.value = user_id;
		var text = document.createTextNode(user_name);     
        option1.appendChild(text);
		userSelecteObj.appendChild(option1);		  
   }
}

function initSqlText(){
   document.getElementById("sqlTable").style.width = document.getElementById("div1").childNodes[0].offsetWidth;
   if(update!=null&&update!=''&&update!='null'){
       var sql = _getXmlNodeValue(ajaxXml, "config-inf:query_sql");
	   document.getElementById("sqlText").value=sql;
   }
}
function initConfigName(){
   if(update!=null&&update!=''&&update!='null'){
       var config_id = _getXmlNodeValue(ajaxXml, "config-inf:sys_db_config_id");
       var config_name = _getXmlNodeValue(ajaxXml, "config-inf:config_name");
       document.getElementById("record:sys_db_config_id").value=config_id;
       document.getElementById("record:config_name").value=config_name;
	   document.getElementById("record:config_name").readOnly=true; 
   }else{
       document.getElementById("record:config_name").select();	
   }
}
function saveConfig(){
    var config_name = document.getElementById("record:config_name").value;
    if(config_name==null||config_name==''){
        alert("【配置名称】不能为空");        
	    document.getElementById("record:config_name").select();
	    return;
    }
    var reg = new RegExp("^[a-zA-Z][a-zA-Z0-9#$_]{0,29}$");
    if (reg.test(config_name)==false){
		alert("请正确填写【配置名称】!\r\n注意：只能由字母或字母和数字组成！");
	    document.getElementById("record:config_name").select();
	    return;
	}
	
    var selectedObj = document.getElementById("selected_"+obj.selectPrefix).options;
	if(selectedObj==null||selectedObj.length==0){
	    alert("请选择要授权的表！");
	    return;
	}	
    var sql = document.getElementById("sqlText").value;
    if(sql==null||sql==''){
	    alert("sql语句不能为空！");
	    return;    
    }
    reg = new RegExp("^\\s*[select|SELECT]");
    if(reg.test(sql)==false){
	    alert("sql语句必须为查询sql语句！");
	    return;    
    }
    reg = new RegExp(";$");
    if(reg.test(sql)==true){
	    alert("sql语句不需要;结束！");
	    return;         
    }
    if(!confirm("是否保存当前配置？")){
        return;
    }
	$.post(rootPath+"/testsql",{testsql:sql}, function(xml){

		var result = xml.selectSingleNode("/results/sql").text;
		if(result=="false"){
			alert("SQL校验失败！请检查后重试");						
			return;
		}else{   
            if(update!=null&&update!=''&&update!='null'){
                _submitSave(config_name,sql,selectedObj);
            }else{
            
			    $.get("<%=request.getContextPath()%>/txn52103013.ajax?select-key:sys_db_user_id="+user_id+"&select-key:config_name="+config_name, function(xml){
				    var errCode = _getXmlNodeValue( xml, "/context/error-code" );
				    if(errCode != "000000"){
		    		    alert("处理错误：" + _getXmlNodeValue( xml, "/context/error-desc" ));
	        		    document.getElementById("record:config_name").select();	        		    
				    }else{
   		    		    _submitSave(config_name,sql,selectedObj);
				    }	    
			    }); 
			}
		}
	});    
    
   
}
function _submitSave(config_name,sql,selectedObj){
    		var configId = document.getElementById("record:sys_db_config_id").value;
    		var tableNos = '';
    		var tableNames = '';
    		var transferSql = sql;
    		for(var i=0;selectedObj&&i<selectedObj.length;i++){
       		 	if(tableNos!=''){
           			tableNos+=",";
           			tableNames+=",";
        		}
        		tableNos+=selectedObj[i].value;
        		tableNames+=selectedObj[i].text;
        		var val = selectedObj[i].tblName;
        		//alert("sql-before:" + sql);
        		var regExp = new RegExp(val+"((\\s+)|\\,)|" + val + "$", "gi");
        		transferSql = transferSql.replace(regExp,function(a,b,c){
           			if(user_type=='0')
               			return zxkuser + "." + a;
           			else if(user_type=='1')
               			return gxkuser + "." + a;
        		});
        		//alert("sql-after :" + transferSql);
    		}

    		var txnCode = "txn52103014.ajax";
   
    		var post = "record:config_name="+config_name;
    		if( configId!=null&&configId != ""){
    		    var old_table = _getXmlNodeValue(ajaxXml, "config-inf:permit_column");
        		txnCode = "txn52103016.ajax";
       	 		post = post 
       	 		 + "&record:sys_db_config_id="+configId
       	 		 + "&record:old_table="+old_table;
    		}
    		post = post
    		 + "&record:sys_db_user_id="+user_id
    		 + "&record:user_type="+user_type
    		 + "&record:login_name="+login_name
    		 + "&record:grant_table="+grant_table
    		 + "&record:permit_column="+tableNos
    		 + "&record:alias_column="+tableNames
    		 + "&record:query_sql="+sql
    		 + "&record:transfer_sql="+transferSql;
    		 
    		post=encodeURI(post); 
			post=encodeURI(post);
    		createXMLHttpRequest();
			var URL = "<%=request.getContextPath()%>/"+txnCode;
			xmlObj.open ('post',URL,false);
			xmlObj.onreadystatechange = afterSave;
			xmlObj.setrequestheader("cache-control","no-cache"); 
			xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
			xmlObj.send(post);
}

function afterSave(){
	var xmlResults = xmlObj.responseXML;
	if (xmlObj.readyState == 4) { // 判断对象状态
		if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
			var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
			if(errCode != "000000"){
			    alert("处理错误：" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
			    return;
			}		
		    alert("保存成功!");
		    window.parent.location.reload();
		    //_hideProcessHintWindow();	    
		}else { //页面不正常
			alert("您所请求的页面有异常。");
		}
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	  	    obj = new dataSelectHTMLObj({
	  		rootPath:rootPath,					//z根目录路径
	  		selectPrefix:"table",				//下拉框id后缀
	  		text_srcTitle:"*选择主题",			//标题
	  		text_selectSrcTitle:"*选择数据表",	//标题
	  		tableContainer:"div1",				//向哪个div填充组件一
	  		txnCode:"/txn60210008.ajax",		//顶部下拉框改变时执行的交易
	  		paramStr:"?record:sys_id=",			//顶部下拉框改变时执行的交易参数前缀
	  		fillObjId:"from_table",				//顶部下拉框改变时执行的交易数据返回后向哪个对象填充数据
	  		optionValue:"table_no",				//被填充下拉框对象的value属性取值
	  		optionText:"table_name_cn",			//被填充下拉框对象的显示值
	  		//oncontentchange:updateRelationObj,	//当已选对象内容改变后执行的函数
	  		//beforecontentchange:scanConditions,	//当已选对象内容改变前是执行的函数
	  		addtionalParam:{ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"} //已选对象需要添加的附加属性
	  	});	
	  	
	  	function addInit(){
	  	    initUserSelected();
	  		obj.doSelectQuery("/txn60210006.ajax", null, null, "source_" + obj.selectPrefix, "sys_id", "sys_name");
	  		initSqlText();
	  		initConfigName();
	  	};
	  	
	  	function updateInit(){
			$.get("<%=request.getContextPath()%>/txn52103015.ajax?select-key:sys_db_config_id="+configId, function(xml){
	  	        initUserSelected();
				ajaxXml = xml;
				//设置已选的数据表
			    fillXmlToSelect(xml, "table-info", "selected_" + obj.selectPrefix, "table_no", "table_name_cn", {ztid:"sys_id", tblName:"table_name"});
			    obj.doSelectQuery("/txn60210006.ajax", null, null, "source_" + obj.selectPrefix, "sys_id", "sys_name");
	  		    initSqlText();
	  		    initConfigName();			
			});	  	
	  	};
	  	if(update!=null&&update!=''&&update!='null'){
	  	    updateInit();
	  	}else{
	  		addInit();
	  	}
	  	
	  	
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:title caption="灵活配置详细" />
	<freeze:errors />
		<div id="step1DIV">
			<table width="100%" border="0" align="center" class="frame-body"
				cellpadding="0" cellspacing="1">
				<tr class="title-row">
					<td>
						授权数据表
					</td>
				</tr>
				
				
				<tr class="framerow">
					<td>
						<div id="div2" style="height:100%;width:100%;">
							<table cellpadding='0' style='table-layout:fixed;'
								cellspacing='0' border='0' align='center' width='75%'>
								<tr height='30'>
									<td align='right' width='100' nowrap='nowrap'>
										选择用户：
									</td>
									<td align='left' width='200'>
										 <select id="userSelected" disabled>
						                 </select>
									</td>
									<td align='right' width='100' nowrap='nowrap'>
										*配置名称：
									</td>
									<td align='left' width='200'>
										<input type="text" id="record:config_name"
											name="record:config_name" value=""/>
										<input type="hidden" id="record:sys_db_config_id"
											name="record:sys_db_config_id" value=""/>
									</td>
								</tr>
							</table>
						</div>
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
						<div style="height:100%;width:100%;">
							<table id="sqlTable" cellpadding='0' style='table-layout:fixed;'
								cellspacing='0' border='0' align='center'>
								<tr>
									<td align='right' width='100' nowrap='nowrap'>
										*视图查询sql：
									</td>
									<td align='left' colspan="3">
										 <textarea id="sqlText" rows="5" cols="68"></textarea>
									</td>
								</tr>
							</table>
						</div>
					</td>		
				</tr>						
			</table>
		</div>		
		<div id="step3DIVButton">
			<p>
			<center>
				<input type='button' id='save' class="menu" value=" 保 存 " onclick="saveConfig();" />
			</center>
			</p>
		</div> 	
</freeze:body>
</freeze:html>
