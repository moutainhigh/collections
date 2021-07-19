function queryConfig(sourceId, selectedId){
	var rootPath = window.rootPath;
	_showProcessHintWindow( "正在查询数据，请稍候....." );
	var right = document.getElementById(selectedId);
	var userObj = document.getElementById(sourceId);
	if(right.options[right.selectedIndex].cfgId){
		//alert( right.options[right.selectedIndex].cfgId +'  ---  ' + rootPath);
		window.pageList_frameX.location.href = rootPath+"/dw/runmgr/services/svrcfg/insert-sys_svr_config_steps.jsp?svrId=" + right.options[right.selectedIndex].value + "&svrName=" + right.options[right.selectedIndex].text + "&userId=" + userObj.options[userObj.selectedIndex].value + "&userName=" + userObj.options[userObj.selectedIndex].text + "&cfgId=" + right.options[right.selectedIndex].cfgId;
	}else{
		window.pageList_frameX.location.href = rootPath+"/dw/runmgr/services/svrcfg/insert-sys_svr_config_steps.jsp?svrId=" + right.options[right.selectedIndex].value + "&svrName=" + right.options[right.selectedIndex].text + "&userId=" + userObj.options[userObj.selectedIndex].value + "&userName=" + userObj.options[userObj.selectedIndex].text;
	}
	
	document.getElementById("div2").style.display = "none";
}
  	
window.onload = function(){
	var rootPath = window.rootPath;
	       
  	function updateRelationObj(type){
  		document.getElementById("div2").style.display = "block";
  		document.getElementById("step2DIV").style.display = "none";
  	}
  	
  	
  	/**
  	 * 扫描要删除的表是否已经被引用
  	 * @param action: delOne | delAll
  	 * @param del   : 是否同时删除引用的条件
  	 * @param confirmed : 是否进行提示
  	 * @return boolean 是否存在引用
  	 */
  	function scanConditions(){
  		document.getElementById("step2DIV").style.display = "none";
  		return true;
  	}
  	
  	var obj = new dataSelectHTMLObj({
  		rootPath:rootPath,					//z根目录路径
  		selectPrefix:"user",				//下拉框id后缀
  		text_srcTitle:"*服务对象",		//标题
  		text_selectSrcTitle:"*选择共享服务",	//标题
  		tableContainer:"div1",				//向哪个div填充组件一
  		txnCode:"/txn50203011.ajax",		//顶部下拉框改变时执行的交易, 查询未配置的服务列表
  		paramStr:"?select-key:sys_svr_user_id=", //顶部下拉框改变时执行的交易参数前缀
  		fillObjId:"selected_user",			//顶部下拉框改变时执行的交易数据返回后向哪个对象填充数据
  		optionValue:"sys_svr_service_id",	//被填充下拉框对象的value属性取值
  		optionText:"svr_name",			//被填充下拉框对象的显示值
  		oncontentchange:updateRelationObj,	//当已选对象内容改变后执行的函数
  		beforecontentchange:confirmDelete,	//当已选对象内容改变前是执行的函数
  		addtionalParam:{cfgId:"sys_svr_config_id"} //已选对象需要添加的附加属性
  	});
  	
  	var sourceObj = document.getElementById("source_"+obj.selectPrefix);
  	sourceObj.innerHTML = "";
  	sourceObj.setAttribute("name", "ource_"+obj.selectPrefix);
  	sourceObj.setAttribute("id", "ource_"+obj.selectPrefix);
  	sourceObj.options.add(createOption(window.targetUserId, window.targetUserName));
  	sourceObj.disabled = true;
  	
  	function confirmDelete(){
  		document.getElementById("div2").style.display = "block";
  		document.getElementById("step2DIV").style.display = "none";
  		return true;
  	}
  	
  	//查询服务对象列表
  	//obj.doSelectQuery("/txn50201001.ajax?select-key:showall=true&select-key:state=0", null, null, "source_" + obj.selectPrefix, "sys_svr_user_id", "user_name");
  	
  	document.getElementById("source_" + obj.selectPrefix).onchange = function(){
  		var objSel  = document.getElementById("source_" + obj.selectPrefix);
  		var target = window.targetUserId;
  		//查询某用户可以享受的服务列表
		obj.doSelectQuery("/txn50203008.ajax?select-key:sys_svr_user_id=" + target, null, null, "from_" + obj.selectPrefix, "sys_svr_service_id", "svr_name", null , null, function(){
			obj.queryDatas();
		});
  	};
  	
  	sourceObj.onchange();
  	
  	var preSelectedIdx = 0;
  	document.getElementById("selected_" + obj.selectPrefix).onchange = function(){
  		var right = document.getElementById("selected_" + obj.selectPrefix);
  		if(right.options.length == 0 || right.selectedIndex == -1){
  			return;
  		}
  		if(document.getElementById("step2DIV").style.display != "none"){
  			if(confirm("是否保存当前配置？")){
  				if(!window.pageList_frameX.toAdd(false)){
  					right.selectedIndex = preSelectedIdx;
  					return;
  				}
  			}
  		}
  		preSelectedIdx = right.selectedIndex
  		document.getElementById("preSelectedIdx").value = preSelectedIdx;
  		
  		var haveSelect = false;
  		for(var i = 0;i < right.options.length; i++){
  			if(right.options[i].selected && haveSelect){
  				return;
  			}
  			if(right.options[i].selected){
  				haveSelect = true;
  			}
  		}
  		queryConfig("source_" + obj.selectPrefix, "selected_" + obj.selectPrefix);
  	};
  	
  	dataSelectHTMLObj.prototype.clickToLeft = function(att, confirmed){
		var p = this;
		var b = document.getElementById("selected_" + p.selectPrefix);
  		if(b.selectedIndex == -1){
			alert("请选择要删除的项！");
			return;
		}
  		if(!confirmed){
			if(!confirm("确认删除此项吗？")) return;
  		}
		var optionArray = getSelectedOptions("selected_" + p.selectPrefix);
		
			
		if(optionArray){
			var cfgIdStr = "";
			for(var i=0; i < optionArray.length; i++){
				if(optionArray[i].cfgId){
					cfgIdStr += "'"+optionArray[i].cfgId+"'";
				}
				//如果不是最后一个选项，并且下一个选项是已经配置的服务，添加逗号
				if((i != (optionArray.length - 1)) && optionArray[i + 1].cfgId){
					cfgIdStr += ", ";
				}
			}
			if(cfgIdStr){
				var userObj = document.getElementById("source_" + p.selectPrefix);
				var userName = userObj.options[userObj.selectedIndex].text;
				var svrName = "";
				for(var jj = 0; jj < optionArray.length; jj++){
					svrName += optionArray[jj].text;
					if(jj != optionArray.length - 1){
						svrName += ",";
					}
				}
				$.get(rootPath+"/txn50203010.ajax?select-key:sys_svr_config_id=" + cfgIdStr +"&select-key:user_name="+userName+"&select-key:svr_name="+svrName, function(xml){
					var code = xml.selectSingleNode("//context/error-code").text;
					if(code != "000000"){
						alert("处理错误："+xml.selectSingleNode("//context/error-desc").text);
						return;
					}
					for(var i=0; i < optionArray.length; i++){
						document.getElementById("from_" + p.selectPrefix).options.appendChild(optionArray[i]);
					}
					userObj.setAttribute("disabled", false);
					userObj.fireEvent("onchange");
					userObj.setAttribute("disabled", true);
				});
			}else{
				for(var i=0; i < optionArray.length; i++){
					document.getElementById("from_" + p.selectPrefix).options.appendChild(optionArray[i]);
				}
			}
			if(b.oncontentchange)
				b.oncontentchange();
		}
	};
	
	
};