<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>查询范围定义</title>
<style type="text/css">
#totalTableDiv .leftTitle{
	background: url(/module/layout/layout-weiqiang/images_new/r_list_l.jpg) no-repeat !important;
}
#totalTableDiv .secTitle{
	background: #006699 !important;
}
#totalTableDiv .rightTitle{
	background: url(/module/layout/layout-weiqiang/images_new/r_list_r.jpg) no-repeat !important;
}
</style>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/connectConditionPluginJoin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/generateTotalTable.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/page/update-sys_svr_service.js"></SCRIPT>
</head>
<% 
	String svrId = request.getParameter("record:sys_svr_service_id");
%>
<freeze:body>
	<freeze:title caption="修改"/>
	<form action="preview.jsp" method="post" target="pageList_frameX" style="margin:0;padding:0">
		<input type="hidden"  id="record:query_sql" name="record:query_sql" />
		<input type="hidden"  id="record:columnsEnArray" name="record:columnsEnArray" />
		<input type="hidden"  id="record:columnsCnArray" name="record:columnsCnArray" />
	    <input  type="hidden" id="record:connStr"  name="record:connStr" />
	    <input  type="hidden"  id="record:sysParams"  name="record:sysParams" />
		<div id="stepsContainerDiv"></div>
		<div id="step1DIV">
			<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">数据表查询条件</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
			    <tr class="framerow">
		          <td style="padding:0px;border:2px solid #006699;">
		          	<div class="odd2" id="div1" style="height:100%;width:100%;"></div></td>
		        </tr>
		    </table>	
		    <div id="div2" style="margin-top:10px;"></div>
	        <p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='toStep2Button' class="menu" value='下一步' />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack0' class="menu" value=' 返 回 '
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center></p>
        </div>
        
        <div id="step2DIV" style="display:none"></div>
	    <div id="step2ButtonDiv" style="display:none">
	    	<select id="tempSelect" name="tempSelect" style="display:none"></select>
	    	<p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="preStep1Button" class="menu" value="上一步" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='toStep3Button' class="menu" value='下一步' />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="goBack1" class="menu" value=" 返 回 "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
	    	<input type='button' id='showQctConnectionStr' class="menu" value="获取连接条件" style="display:none;"/> 
	     </center></p>
	    </div>
        
        <div id="step3DIV" style="display:none">
        	<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">数据表查询条件</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
			    <tr class="framerow">
		          <td style="padding:0px;border:2px solid #006699;"><div class="odd2" id="columnsContainerDiv" style="height:100%;width:100%;"></div></td>
		        </tr>
		    </table>	
		</div>
        <div id="step3ButtonDiv" style="display:none">
        	<p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preStep2Button' class="menu" value="上一步" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preViewButton' class="menu" value="下一步" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack2' class="menu" value=" 返 回 "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center></p>
        </div>
        
        <div id="step4DIV" style="display:none">
        	<div id="totalTableDiv"></div>
        	<IFRAME id="pageList_frameX" name="pageList_frameX" frameBorder=0 width="95%" align="center" height="300"></IFRAME>
        </div>
        <div id="step4ButtonDiv" style="display:none">
        	<p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preStep3Button' class="menu" value="上一步" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='saveButton' class="menu" value=" 保 存 " />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack2' class="menu" value=" 返 回 "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center></p>
        </div>
	</form>
</freeze:body>
<script type="text/javascript">
  window.rootPath = "<%=request.getContextPath()%>";
  	//jufeng 调整step导航部分居总显示
	var s  = Math.round ( $('#step1DIV').width()*0.05/2 )
	var s2 = s+'px'
	$('#stepsContainerDiv').css('margin-left',s2);
  var qcObj = null;
  var qcObjTemp = new Array;
  var qcObjTempOld = new Array;
  var qcObjInit = new Array;
  
  var ajaxXml;
  
  var doUpdate = function(connObj, obj2, dataObj){
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
		
	  	function doInitQuery(){
			$.get("<%=request.getContextPath()%>/txn50202004.ajax?select-key:sys_svr_service_id=<%=svrId%>", function(xml){
				ajaxXml = xml;
				//设置已选的数据表
			    fillXmlToSelect(xml, "tbl-info", "selected_table", "table_no", "table_name_cn", {ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"});
			    document.getElementById("selected_table").oncontentchange();
			    var step1Params = xml.selectNodes("//record");		 
			    if(step1Params.length > 0){
				    for(var i = 0; i < step1Params.length; i++){
				    	
				    	var step1Param = step1Params[i].selectSingleNode("params").text;
				    	//alert(step1Param);
				    	if(step1Param){
					    	var jsonObj = eval("(" + step1Param + ")");
				    		addToArray(connObj.connJSONArray, jsonObj);
				    		
				    		insertParamRow(connObj, jsonObj.paramText);
				    	}
				    }
				    
				    if(connObj.connJSONArray.length > 0){
				    	var con1 = document.getElementById(connObj.id + "Condition");
				    	con1.options.length = 0;
				    	con1.options.add(createOption('AND','并且'));
				    	con1.options.add(createOption('OR','或者'));
				    	con1.disabled = false;
				    }
			    }
			    
			    step1Params = null;
			
			//获取初始化的系统参数	 jufeng 2012-07-03	 	  
			    var step2Params = xml.selectNodes("//config-param");
			    if(step2Params.length > 0){
				    for(var i = 0; i < step2Params.length; i++){
				    	var step2Param = step2Params[i].selectSingleNode("sysParams").text;
				    	//alert(step2Param);
			    		if(step2Param){
				    		eval(" qcObjInit\.push(" + step2Param + ") ");
			    		}
			    		
				    }
			    }
			    step2Params = null;
			   
			    fillXmlToSelect(xml, "col-info", "selected_"+obj2.selectPrefix, "column_no", "column_name_cn", {tblid:"table_no", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn", colByName:"column_byname"});
			    dataObj.doSelectQuery("/txn50202006.ajax", null, null, "source_" + dataObj.selectPrefix, "sys_id", "sys_name");
			    //将选择的表添加到字段步骤的表中
			    obj2.updateColumnTables("selected_"+dataObj.selectPrefix, "source_"+obj2.selectPrefix);
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
		
			//添加列
			var newTd0 = newTr.insertCell();
			var newTd1 = newTr.insertCell();
		
			//设置列内容和属性
			newTd0.innerHTML = paramStr; 
			newTd0.width = "90%";
			newTd0.align = "left";
			newTd1.innerHTML= "<table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type='button' id='deleteConnCondition_" + p.id + "' value=' 删除 ' class='menu' /> </td><td class='btn_right'></td></tr></table>";
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
	}
	

function insertParamRow2(p, paramStr){
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
			newTd1.innerHTML= "<table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type='button' id='deleteConnCondition_" + p.id + "' value=' 删除 ' class='menu' /></td><td class='btn_right'></td></tr></table>";
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
</script>
</freeze:html>
