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
	<title>视图详情</title>
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
	<freeze:title caption="视图详情" />
	<form id="updateForm">
		<input type="hidden" id="record:columnsEnArray" name="record:columnsEnArray" />
		<input type="hidden" id="record:columnsCnArray" name="record:columnsCnArray" />
		<div id="stepsContainerDiv" style="display:none"></div>
		<div id="step1DIV" style="display:none">
			<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr class="title-row">
			      <td>选择数据表</td>
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
			      <td>选择字段</td>
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
<script language="javascript">
window.rootPath = "<%=request.getContextPath()%>";
window.onload = function(){
		var rootPath = window.rootPath;
		var steps = new stepHelp({
	           id				: "steps",     // 组件的默认ID，如果一个页面中有多个该组件，注意该ID不可重复
	           parentContainer	: "stepsContainerDiv", // 其父节点的ID
	           textArray		: ["选择数据表", "选择字段", "视图详情"]		 // 字符串数组
	       });
		steps.setCurrentStep(0);
		
	  	var obj = new dataSelectHTMLObj({
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
	  		oncontentchange:updateRelationObj,	//当已选对象内容改变后执行的函数
	  		beforecontentchange:scanConditions,	//当已选对象内容改变前是执行的函数
	  		addtionalParam:{ztid:"sys_id", tblName:"table_name", ztName:"sys_name", ztNo:"sys_no"} //已选对象需要添加的附加属性
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
	  		text_srcTitle:"*选择数据表",
	  		text_selectSrcTitle:"*选择字段",
	  		tableContainer:"columnsContainerDiv",
	  		txnCode:"/txn60210009.ajax",
	  		paramStr:"?record:table_no=",
	  		fillObjId:"from_columnTable",
	  		optionValue:"column_no",
	  		optionText:"column_name_cn",
	  		addtionalParam:{tblid:"table_no", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn", colByName:"column_byname"}
	  	});
	  	/**
	  	 * 扫描要删除的表是否已经被引用
	  	 * @param action: delOne | delAll
	  	 * @param del   : 是否同时删除引用的条件
	  	 * @param confirmed : 是否进行提示
	  	 * @return boolean 是否存在引用
	  	 */
	  	function scanConditions(action, del, confirmed){
	  		//获得已选数据表下拉框对象
	  		var selectObj = document.getElementById("selected_" + obj.selectPrefix);
	  		if(action == "delOne"){//如果点的是“删除一条”的按钮，执行此分支
	  			//检查“表连接条件”中是否引用了此表
	  			var connTblArray = connObj.getTableId();//获得表连接中引用的表
		  		var columns = document.getElementById("selected_"+ obj2.selectPrefix).options;//获得已选的字段下拉框对象
	  			var optionArray = getSelectedOptions("selected_" + obj.selectPrefix);//获得所有得到焦点的下拉框选项
	  			for(var i=0; i < optionArray.length; i++){
	  				if(existInArray(connTblArray, optionArray[i].value) != -1){
			  			if(del){
			  				if(!confirmed){
				  				if(!confirm("系统检测到此表在【表连接条件】中被引用，是否同时删除相关的条件？")){
				  					return false;
				  				}
			  				}
			  				confirmed = true;//设为true，避免后面的“查询条件”或“显示字段”重复提示
			  				if(connObj.connJSONArray){
			  					for(var j = 0; j < connObj.connJSONArray.length; j++){
			  						var connJSONObj = connObj.connJSONArray[j];
			  						if(optionArray[i].value == connJSONObj.tableOneId || optionArray[j].value == connJSONObj.tableTwoId){
			  							connObj.deleteCondition(j);
			  							scanConditions("delOne", true, true);//递归调用，可删除所有相关条件
			  						}
			  					}
			  				}
			  			}else{
			  				alert("系统检测到此表在【表连接条件】中被引用，禁止删除！");
			  				return false;
			  			}
			  		}

			  		
			  		if(columns.length != 0){
			  			var exist = false;
			  			for(var j = 0; j < columns.length; j++){
			  				var colTblId = columns[j].tblid;
			  				if(colTblId == optionArray[i].value){
			  					exist = true;
			  					columns[j].selected = true;//设为选中，最后一起删除
			  					break;
			  				}else{
			  					columns[j].selected = false;//如果已经选中，则设为不选，避免误删
			  				}
			  			}
			  			if(exist){
			  				if(del){
				  				if(!confirm("系统检测到需要显示此表的字段，是否同时删除相关的字段？"))
				  					return false;
				  					
				  				obj2.clickToLeft("tblid", true);
				  			}else{
				  				alert("系统检测到需要显示此表的字段，禁止删除！");
				  				return false;
				  			}
			  			}
			  		}
	  			}
		  		connTblArray = null;
		  		columns = null;
		  		optionArray = null;
	  		}else if (action == "delAll"){//如果点的是“删除全部”的按钮，执行此分支
	  			if(selectObj.options.length != 0){
	  				for(var i = 0; i< selectObj.options.length; i++){
	  					selectObj.options[i].selected = true;
	  					if(!scanConditions("delOne", del, true)){//循环调用“删除一条”的分支
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
			
			if(loadColumn){//是否加载“显示字段”
				var columnObj = document.getElementById("selected_"+obj2.selectPrefix).options;
		  		var columnNameArray;
		  		var columnIdArray;
		  		if(columnObj.length == 0){
		  			alert("请选择字段！");
		  			return false;
		  		}else{
		  			columnNameArray = new Array();
		  			columnIdArray = new Array();
		  			for(var i = 0; i < columnObj.length; i++){
		  				if(columnObj[i].colByName){//判断是否存在别名
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
		  return xmlObj;
	    }	  	

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
			$.get("<%=request.getContextPath()%>/txn52102004.ajax?select-key:sys_db_view_id=<%=queryId%>", function(xml){
				ajaxXml = xml;
				//设置已选的数据表
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
				    	con1.options.add(createOption('AND','并且'));
				    	con1.options.add(createOption('OR','或者'));
				    	con1.disabled = false;
				    }
			    }
			    
			    step1Params = null;
			    
			    fillXmlToSelect(xml, "column-info", "selected_"+obj2.selectPrefix, "column_no", "column_name_cn", {tblid:"table_no", colName:"column_name", tblName:"table_name", tblNameCn:"table_name_cn"});
                
	  	        var columnObj = document.getElementById("selected_"+obj2.selectPrefix).options;
	  			var columnEnArray = new Array();
	  			for(var i = 0; i < columnObj.length; i++){
	  				if(columnObj[i].colByName){//判断是否存在别名
	  					addToArray(columnEnArray, columnObj[i].colByName);
	  				}else{
	  					addToArray(columnEnArray, columnObj[i].colName);
	  				}
	  			}
	  		  	var totalTable = new generateTotalTable({
	                id					: "totalTable",     // 组件的默认ID，如果一个页面中有多个该组件，注意该ID不可重复
	                parentContainer		: "totalTableDiv", // 其父节点的ID
	                columnSelect			: "selected_columnTable",
	                tableSelect			: "selected_table",
	                connectConditionTable: "conditionTbl_conn",
	                tableParamOnColumn	: ["tblid", "tblNameCn", "tblName"],  // 依次为表ID(Value)，表中文名(Text)，表英文名(附加属性)
	                sysParamOnTable		: ["ztid", "ztName", "ztNo"],   	  // 依次为主题ID(Value)，主题中文名(Text)，主题英文名(附加属性)
	                columnParamOnColumn	: ["colId", "colNameCn", "colName"]	  // 依次为字段ID(Value)，字段中文名(Text)，字段英文名(附加属性)
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
	  	//alert(document.getElementById("source_"+dataObj.selectPrefix).onchange);
	  	//document.getElementById("source_"+dataObj.selectPrefix).fireEvent("onchange");
	}
	doUpdate(connObj, obj2, obj);
};
</script>
</freeze:html>
