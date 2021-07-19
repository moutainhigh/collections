function queryConfig(sourceId, selectedId){
	var rootPath = window.rootPath;
	_showProcessHintWindow( "���ڲ�ѯ���ݣ����Ժ�....." );
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
  	 * ɨ��Ҫɾ���ı��Ƿ��Ѿ�������
  	 * @param action: delOne | delAll
  	 * @param del   : �Ƿ�ͬʱɾ�����õ�����
  	 * @param confirmed : �Ƿ������ʾ
  	 * @return boolean �Ƿ��������
  	 */
  	function scanConditions(){
  		document.getElementById("step2DIV").style.display = "none";
  		return true;
  	}
  	
  	var obj = new dataSelectHTMLObj({
  		rootPath:rootPath,					//z��Ŀ¼·��
  		selectPrefix:"user",				//������id��׺
  		text_srcTitle:"*�������",		//����
  		text_selectSrcTitle:"*ѡ�������",	//����
  		tableContainer:"div1",				//���ĸ�div������һ
  		txnCode:"/txn50203011.ajax",		//����������ı�ʱִ�еĽ���, ��ѯδ���õķ����б�
  		paramStr:"?select-key:sys_svr_user_id=", //����������ı�ʱִ�еĽ��ײ���ǰ׺
  		fillObjId:"selected_user",			//����������ı�ʱִ�еĽ������ݷ��غ����ĸ������������
  		optionValue:"sys_svr_service_id",	//���������������value����ȡֵ
  		optionText:"svr_name",			//�����������������ʾֵ
  		oncontentchange:updateRelationObj,	//����ѡ�������ݸı��ִ�еĺ���
  		beforecontentchange:confirmDelete,	//����ѡ�������ݸı�ǰ��ִ�еĺ���
  		addtionalParam:{cfgId:"sys_svr_config_id"} //��ѡ������Ҫ��ӵĸ�������
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
  	
  	//��ѯ��������б�
  	//obj.doSelectQuery("/txn50201001.ajax?select-key:showall=true&select-key:state=0", null, null, "source_" + obj.selectPrefix, "sys_svr_user_id", "user_name");
  	
  	document.getElementById("source_" + obj.selectPrefix).onchange = function(){
  		var objSel  = document.getElementById("source_" + obj.selectPrefix);
  		var target = window.targetUserId;
  		//��ѯĳ�û��������ܵķ����б�
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
  			if(confirm("�Ƿ񱣴浱ǰ���ã�")){
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
			alert("��ѡ��Ҫɾ�����");
			return;
		}
  		if(!confirmed){
			if(!confirm("ȷ��ɾ��������")) return;
  		}
		var optionArray = getSelectedOptions("selected_" + p.selectPrefix);
		
			
		if(optionArray){
			var cfgIdStr = "";
			for(var i=0; i < optionArray.length; i++){
				if(optionArray[i].cfgId){
					cfgIdStr += "'"+optionArray[i].cfgId+"'";
				}
				//����������һ��ѡ�������һ��ѡ�����Ѿ����õķ�����Ӷ���
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
						alert("�������"+xml.selectSingleNode("//context/error-desc").text);
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