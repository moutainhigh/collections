var PageContext = {
	m_arrNotifyTypes : ['email', 'message', 'sms'],
	loadPage : function(_sFlowContextInfo){
		var sInfo = _sFlowContextInfo;
		if(sInfo == null || !(sInfo.length > 0)) {
			return;
		}
		//else
		var json = com.trs.util.JSON.parseXml(com.trs.util.XML.loadXML(_sFlowContextInfo));
		this.initDisplay(json);
	},
	initDisplay : function(_json){
		//1.获取数据
		var data = _json;
		if(data == null) {
			return;
		}
		//else 显示
		//2.0 节点
		var sFirstNodeName = $v(data, 'FlowContextInfo.FirstNodeName');
		Element.update($('spFirstNodeName'), sFirstNodeName);
		//2.1 显示通知方式
		var notifyTypes = $v(data, 'FlowContextInfo.NotifyStyles');
		if(notifyTypes) {
			notifyTypes = (notifyTypes.toLowerCase()).split(',');
		}
		var allTypes = PageContext.m_arrNotifyTypes;
		for (var i = 0; i < allTypes.length; i++){
			$('chk_' + allTypes[i]).checked = (notifyTypes.include(allTypes[i]));
		}
		//2.2 显示处理人员
		var users = $a(data, 'FlowContextInfo.Users.User');
		if(users && users.length > 0) {
			var sValue = TempEvaler.evaluateTemplater('template_users', users, null);
			Element.update($('spUsers'), sValue);
			var arrSelectedUsersEle = document.getElementsByName("selectedUserIds");
			if(arrSelectedUsersEle.length==1){
				for (var i=0; i<arrSelectedUsersEle.length; i++){
					arrSelectedUsersEle[i].checked = true;
				}
			}
			Element.hide('sp_asRule');
		}else{
			var usersCreator = $v(data, 'FlowContextInfo.UsersCreator');
			if(usersCreator!=null && usersCreator!=''){
				Element.update($('spUsers'), '');
				Element.show('sp_asRule');
				if($('cb_asRule'))
				$('cb_asRule').checked = true;
			}
		}
	},
	getFlowContextInfo : function(){
		var arrNotifyTypes = [];
		var arrUsers = [];
		//通知方式
		var allTypes = PageContext.m_arrNotifyTypes;
		for (var i = 0; i < allTypes.length; i++){
			var chk = $('chk_' + allTypes[i]);
			if(!chk.checked) {
				continue;
			}
			//else
			arrNotifyTypes.push(chk.value);
		}
		//处理人员
		var arrSelectedUsersEle = document.getElementsByName("selectedUserIds");
		var arrSelectedUsers = [];
		for(var i=0;i<arrSelectedUsersEle.length;i++){
			if(arrSelectedUsersEle[i].checked){
				arrSelectedUsers.push(arrSelectedUsersEle[i].value);
			}
		}
		//alert(arrNotifyTypes + '\n' + arrUsers.length);
		var result = 'ToUserIds=' + arrSelectedUsers.join(',') + ';'
					+ 'NotifyTypes=' + arrNotifyTypes.join(',') + ';'
					+ 'PostDesc=' + $('txtComment').value.trim();

		return result;
	}
}
function validate(){
	var arrSelectedUsersEle = document.getElementsByName("selectedUserIds");
	var arrUsers = [];
	for(var i=0;i<arrSelectedUsersEle.length;i++){
		if(arrSelectedUsersEle[i].checked){
			arrUsers.push(arrSelectedUsersEle[i].value);
		}
	}
	if(!(arrUsers.length > 0) && $('cb_asRule') && !$('cb_asRule').checked) {
		alert(wcm.LANG['IFLOWCONTENT_8'] || '请指定下一节点的处理人员！(不指定处理人员，将导致流转异常或者流转提前结束)');
		return false;
	}
	if(!$('txtComment').disabled){
			var sLen = 0;
			if($('txtComment').value.trim() == ''){
				if (!confirm(wcm.LANG.workflow_process_init_4wcm52_1000 || '没有填写处理意见！仍要继续提交吗？')) {
					//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
					try{
						$('txtComment').select();
						$('txtComment').focus();
					}catch(ex){}
					return false;
				}
			}else if((sLen = $('txtComment').value.byteLength()) > 500){
				var sErrorInfo = String.format("处理意见限制为500个字符长度，当前为{0},每个汉字长度为2。",sLen);

				//Ext.Msg.alert(sErrorInfo);
				alert(sErrorInfo);
				//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
				try{
					$('txtComment').select();
					$('txtComment').focus();
				}catch(ex){
					
				}
				return false;
			}
		}

	return true;
}
function buildData(){
	var result = PageContext.getFlowContextInfo();
	//	alert(result);
	return result;
}
function reasignUsers(){
	var arrUsers = [];
	var arrSelectedUsersEle = document.getElementsByName("selectedUserIds");
	for(var i=0;i<arrSelectedUsersEle.length;i++){
		if(arrSelectedUsersEle[i].checked){
			arrUsers.push(arrSelectedUsersEle[i].value);
		}
	}
	var sUrl = WCMConstants.WCM6_PATH + 'include/select_index.jsp';
	var cb = parent.wcm.CrashBoard.get({
		id : 'select_user_or_group',
		title : wcm.LANG['IFLOWCONTENT_88'] || '选择用户/组织',
		reloadable : true,
		url : sUrl,
		width: '850px',
		height: '550px',
		params : {UserIds : arrUsers.join(','),FromProcess:1},
		maskable : true,
		callback : function(arOwners){
			if(arOwners == null){
				return;
			}
			if(arOwners.length < 2){
				Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_9'] || "返回值不正确！");
				return;
			}
			var arrBranchInfos = [];
			var arrUserIds = arOwners[0].split(',');
			var arrUserNames = arOwners[1].split(',');
			for (var i = 0; i < arOwners[0].length; i++){
				if(arrUserIds[i] == null || arrUserIds[i].trim() == '') {
					continue;
				}
				arrBranchInfos.push({
					USERID: arrUserIds[i],
					USERNAME: arrUserNames[i]
				});
			}
			if(arrBranchInfos.length>0){
				Element.hide('sp_asRule');
			}
			else{
				Element.show('sp_asRule');
			}
			var sValue = TempEvaler.evaluateTemplater('template_users', arrBranchInfos, null);
			Element.update($('spUsers'), sValue); 
			for (var i=0; i<arrSelectedUsersEle.length; i++){
				arrSelectedUsersEle[i].checked = true;
			}
			if(cb)
			cb.close();
			return;
		}
	});
	cb.show();
}