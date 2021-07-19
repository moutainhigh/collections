Object.extend(Element, {
	getParentElementByClassName : function(_element, _sClassName){
		var oParentNode = _element.parentNode;
		if( oParentNode == null 
			|| (oParentNode.tagName != null && oParentNode.tagName == 'BODY') ) return null;
		if(Element.hasClassName(oParentNode, _sClassName))return oParentNode;
		return Element.findParentElementByClassName(oParentNode, _sClassName);
	}
});
var filePathTemplate = ['<li class="content"><span>{0}</span>',
						'<a href="" onclick="opencompare(\'{0}\');return false;">',
						'<span>&nbsp;在线比较</span>',
						'</a>',
						'</li>'
						].join("");
function queryMessage(){
	if(bStop || sIsConfirm != '1' || needUpdateFixSize == 0){
		return;
	}
	Element.show('updateinfo');
	//获取当前处于运行状态的元素
	var activeEl = document.getElementsByClassName('active',$('allmessage'))[0] || document.getElementsByClassName('warning',$('allmessage'))[0];
	var activeKey = "";
	if(activeEl){
		activeKey = activeEl.getAttribute('key');
	}
	new ajaxRequest({
		url : './update_message.jsp?Key=' + activeKey,
		method : 'post',
		onSuccess : function(trans){
			var sResponse =  trans.responseText;
			eval("var result =" + sResponse );

			//初始化时间戳参数，便于下一次执行
			if(result.timestamp != '' && m_sTimeStamp == null){
				m_sTimeStamp = result.timestamp;
			}
			//出现异常的情况，中止发消息
			if(result.errorinfo != '' && result.updated != 'true'){
				doWithExceptionState(activeEl);
				return;
			}
			//如果获取到当前的activeKey有错误信息，则显示为警告，出错
			if(result.currerror != ''){
				Element.removeClassName(activeEl, 'active');
				Element.addClassName(activeEl, 'warning');
				var currErrorEl = document.getElementsByClassName('detailinfo',activeEl)[0];
				var info = result.currerror.replace(/\n/ig,'<br/>');
				currErrorEl.innerHTML = info;
			}
			//如果获取到当前的activeKey完成，则继续执行下一个元素
			if(result.updated == 'true'){
				if(Element.hasClassName(activeEl,'active')){
					Element.removeClassName(activeEl, 'active');
					Element.addClassName(activeEl, 'completed');
				}
				//将下一个元素设置为active状态
				var nextEl = getNextUpdateItemEl(activeEl);
				if(nextEl){
					Element.removeClassName(nextEl, 'notExecute');
					Element.addClassName(nextEl, 'active');
					setTimeout(queryMessage, 500);
					return;
				}
			}
			if(result.bCompleted == 'true'){
				//执行完成后的后续处理
				afterComplete();
			}else{
				setTimeout(queryMessage, 500);
			}
		}
	});
	return false;
}
function getNextUpdateItemEl(currItemEl){
	var nextEl = currItemEl.nextSibling;
	if(nextEl){
		while(!nextEl.tagName || nextEl.tagName.toUpperCase() != 'DIV'){
			nextEl = nextEl.nextSibling;
			if(!nextEl)break;
		}
	}
	return nextEl;
}
function doWithExceptionState(currActiveEl){
	$("errortip").innerHTML = '更新fix出现异常，更新终止，请处理目前已更新fix的手工更新部分和冲突文件，清除work目录，重启应用，解决更新异常问题后，再重新更新！';
	Element.show('errorInfo');
	//Element.show('noticeInfo');
	$('errorInfo').scrollIntoView(true);
	showConflictFiles();
	showManualworkinfo(); 
	if(Element.hasClassName(currActiveEl, 'active')){
		Element.removeClassName(currActiveEl, 'active');
	}else if(Element.hasClassName(currActiveEl, 'warning')){
		Element.removeClassName(currActiveEl, 'warning');
	}

	Element.addClassName(currActiveEl, 'error');
	//获取异常信息
	new ajaxRequest({
		url : './showloginfo.jsp?TimeStamp=' + m_sTimeStamp,
		method : 'post',
		onSuccess : function(trans){
			var sResponse =  trans.responseText;
			if(sResponse.trim() == '')return;
			var currErrorEl = document.getElementsByClassName('detailinfo',currActiveEl)[0];
			currErrorEl.innerHTML = sResponse;
			Element.show(currErrorEl);
		}
	});
}
function showDetailInfo(event){
	var event = window.event || event;
	var dom = Event.element(event);
	if(!Element.hasClassName(dom,'itemtitle')){
		return false;
	}
	//获取当前父元素
	var currItem = Element.getParentElementByClassName(dom,'updateItem');
	if(!currItem)
		return false;
	if(!Element.hasClassName(currItem,'warning')&&!Element.hasClassName(currItem,'error')){
		return false;
	}
	//获取其下面的详细信息元素
	var currErrorEl = document.getElementsByClassName('detailinfo',currItem)[0];
	if(!currErrorEl || currErrorEl.innerHTML == '')
		return false;
	Element.toggle(currErrorEl);
}
Event.observe(window, 'load', function(){
	Event.observe('execute_info', 'click', showDetailInfo);
	queryMessage();
});
function afterComplete(task){
	if(task){
		Element.removeClassName(task, 'active');
		Element.addClassName(task, 'completed');
	}
	//将页面中可能还处于非completed状态的元素一次设置为completed
	var taskList = $('allmessage').getElementsByTagName("DIV");
	for(var i=0;i<taskList.length;i++){
		var task = taskList[i];
		if(!Element.hasClassName(task,'notExecute')){
			continue;
		}
		Element.addClassName(task, 'active');
		setTimeout(function(){
			afterComplete(task);
		}, 500);
		return;
	}
	Element.show('processorbar');
	//Element.show('noticeInfo');
	$('processorbar').innerHTML = '更新fix执行完成，请处理手工更新部分和冲突文件，清除work目录，重启应用！';
	$('processorbar').scrollIntoView(true);
	showConflictFiles();
	showManualworkinfo(); 
	return;
}
function showConflictFiles(){
	new ajaxRequest({
		url : './getConflict_files.jsp',
		method : 'post',
		onSuccess : function(trans){
			var sResponse =  trans.responseText;
			eval("var fileInfo =" + sResponse);
			m_sTimeStamp = fileInfo.timestamp;
			var fileList = fileInfo.filelist;
			var fileListBox = $('filelist');
			var listContent = [];
			for(var k=0;k<fileList.length;k++){
				var filePath = fileList[k];
				listContent.push(String.format(filePathTemplate, filePath));
			}
			var fileListInfo = "";
			if(listContent.length == 0){
				fileListInfo = "本次更新没有冲突文件";
			}else{
				fileListInfo = listContent.join("");
			}
			$('filelist').innerHTML = '<ul>' + fileListInfo + '</ul>';
			Element.show('conflictfileBox');
		}
	});
}
function showManualworkinfo(){
	new ajaxRequest({
		url : './getmanualworkinfo.jsp',
		method : 'post',
		onSuccess : function(trans){
			var sResponse =  trans.responseText;
			var result = "";
			if(sResponse.trim() == ''){
				result = "没有需要手工更新的部分";
			}else{
				result = sResponse;
			}
			$('Manualworkinfo').innerHTML = result;
			Element.show('ManualworkinfoBox');
		}
	});
}
function opencompare(filepath){
	var comparePath = '../../update/util/compare.jsp?SrcFilePath=' + filepath + '&TargetFilePath=' + m_sTimeStamp;
	var oWin = window.open(comparePath,'comparefile');
	if(oWin)oWin.focus();
}
/*
function openerrorinfo(){
	var errorPath = './showloginfo.jsp?TimeStamp=' + m_sTimeStamp;
	var oWin = window.open(errorPath,'errordetalinfo');
	if(oWin)oWin.focus();
}*/