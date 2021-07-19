window.onload = function(){
		var rootPath = window.rootPath;
		var steps = new stepHelp({
	           id				: "steps",     // 组件的默认ID，如果一个页面中有多个该组件，注意该ID不可重复
	           parentContainer	: "stepsContainerDiv", // 其父节点的ID
	           textArray		: ["查询范围定义", "选择显示项", "预览"]		 // 字符串数组
	       });
		
	  	function updateRelationObj(type){
	  		connObj.setDataFromSelect("selected_table");
	  	}
	  	 
	  	
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
			  						if(optionArray[i].value == connJSONObj.tableOneId || optionArray[i].value == connJSONObj.tableTwoId){
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
			  					//break;
			  				}else{
			  					columns[j].selected = false;//如果已经选中，则设为不选，避免误删
			  				}
			  			}
			  			if(exist){
			  				if(del){
			  					if(!confirmed){
					  				if(!confirm("系统检测到需要显示此表的字段，是否同时删除相关的字段？")){
					  					return false;
					  				}
			  					}
				  					
				  				obj2.clickToLeft("tblid", true);
				  			}else{
				  				alert("系统检测到需要显示此表的字段，禁止删除！");
				  				return false;
				  			}
			  			}
			  		}
	  			}
		  		connTblArray = null;
		  		qcTblArray = null;
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
	  	
	  	/**
	  	 * 创建SQL语句
	  	 * @param loadColumn 是否添加字段到SQL
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
					sql += " WHERE (" + paramStr + ")";
				}
			}
			
			selectedTables = null;
			paramStr = null;
			return sql;
	  	}
	  	
	  	// 步骤一的下一步按钮事件
	  	document.getElementById("toStep2Button").onclick = function(){
	  		if(document.getElementById("selected_"+obj.selectPrefix).options.length == 0){
	  			alert("请选择数据表！");
	  			return;
	  		}
	  			
	  		if(connObj.validateConnCondition("selected_"+obj.selectPrefix)){
	  			document.getElementById("toStep2Button").disabled = true;
	  			_showProcessHintWindow( "正在校验表连接条件，请稍候....." );
	  			var sql = createSQL(1);
	  			$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
					var result = xml.selectSingleNode("/results/sql").text;
					if(result=="false"){
						_hideProcessHintWindow();
						alert("SQL校验失败！请检查后重试");
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
	  	
	  	// 步骤三的预览按钮事件
	  	document.getElementById("preViewButton").onclick = function(){
	  		var columnObj = document.getElementById("selected_"+obj2.selectPrefix).options;
	  		var columnEnArray
	  		if(columnObj.length == 0){
	  			alert("请选择字段！");
	  			return false;
	  		}else{
	  			columnEnArray = new Array();
	  			for(var i = 0; i < columnObj.length; i++){
	  				if(columnObj[i].colByName){//判断是否存在别名
	  					addToArray(columnEnArray, columnObj[i].colByName);
	  				}else{
	  					addToArray(columnEnArray, columnObj[i].colName);
	  				}
	  			}
	  		}
  			_showProcessHintWindow( "正在校验SQL语句，请稍候....." );
	  		var sql = createSQL(2);
	  		$.post(rootPath+"/testsql",{testsql:sql}, function(xml){
				var result = xml.selectSingleNode("/results/sql").text;
				if(result=="false"){
					_hideProcessHintWindow();
					alert("SQL校验失败！请检查后重试");
					document.getElementById("toStep2Button").disabled = false;						
					return false;
				}else{
					_hideProcessHintWindow();
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
			  		document.getElementById("record:query_sql").value = sql;//设置SQL
			  		document.getElementById("record:columnsCnArray").value = obj2.getSelectedDataValues("text");//设置字段中文名
			  		document.getElementById("record:columnsEnArray").value = columnEnArray.toString();//设置字段英文名
			  		
					document.getElementById("step3DIV").style.display = "none";
					document.getElementById("step3ButtonDiv").style.display = "none";
					document.getElementById("step4DIV").style.display = "block";
					document.getElementById("step4ButtonDiv").style.display = "block";
					steps.setCurrentStep(2);
					document.getElementsByTagName("form")[0].submit();
					
				}
			});
	  		
	  	}
	  	
	  	// 步骤三的上一步按钮事件
	  	document.getElementById("preStep2Button").onclick = function(){
			document.getElementById("step3DIV").style.display = "none";
			document.getElementById("step3ButtonDiv").style.display = "none";
			document.getElementById("step1DIV").style.display = "block";
			steps.setCurrentStep(1);
	  	}
	  	
	  	var txnCode = "/txn50202003.ajax";//交易代码，默认为添加，如果ajaxXml不为空，则执行修改
	  	// 步骤四的保存按钮事件
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
	  	
	  	function toAdd(paramArray){
	  		
	  		var sql = createSQL(2);
			sql = sql.replace(/\+/g, "%2B");
			_showProcessHintWindow( "正在保存数据，请稍候....." );
	
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
			         + "&record:svr_type=一般服务";
			         
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
			if (xmlObj.readyState == 4) { // 判断对象状态
				_hideProcessHintWindow();
				if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
					var errCode = _getXmlNodeValue( xmlResults, "/context/error-code" );
					if(errCode != "000000"){
					    alert("处理错误：" + _getXmlNodeValue( xmlResults, "/context/error-desc" ));
					    return;
					}
				    	alert("保存成功！");
				    	window.location.href = rootPath+"/txn50202001.do?<%=request.getQueryString()%>";
				} else { //页面不正常
					window.alert("您所请求的页面有异常。");
				}
			}
		}
	  	
	  	// 步骤四的上一步按钮事件
	  	document.getElementById("preStep3Button").onclick = function(){
			document.getElementById("step4DIV").style.display = "none";
			document.getElementById("step4ButtonDiv").style.display = "none";
			document.getElementById("step3DIV").style.display = "block";
			document.getElementById("step3ButtonDiv").style.display = "block";
			steps.setCurrentStep(2);
	  	}
	  	
	  	var obj = new dataSelectHTMLObj({
	  		rootPath:rootPath,					//z根目录路径
	  		selectPrefix:"table",				//下拉框id后缀
	  		text_srcTitle:"*选择主题库",			//标题
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
		
	  	if(doUpdate){
	  		doUpdate(connObj, obj2, obj);
	  	}else{
	  		obj.doSelectQuery("/txn50202006.ajax", null, null, "source_" + obj.selectPrefix, "sys_id", "sys_name");
	  	}
	  	
  	};