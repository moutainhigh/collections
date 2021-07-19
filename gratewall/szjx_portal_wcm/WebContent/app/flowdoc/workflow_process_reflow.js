PageContext = {
	m_arrNotifyTypes : ['email', 'message', 'sms'],
	loadPage : function(_params){
		PageContext.params = _params;
		if($('divDocs')){
			$('divDocs').innerHTML = _params.doctitle;
		}
	}
};
function init(_params){
	PageContext.loadPage(_params);
}
function doOK(){
	//组织提交的数据
	var oPostData = {
		ContentType: PageContext.params['ContentType'],
		objectid: PageContext.params['ContentId']
		
	}
	Object.extend(oPostData,getFlowContextInfo());
	Object.extend(oPostData, {
		reflow : true
	});
	if(oPostData.ToUserIds.length==0){
		Ext.Msg.warn((wcm.LANG['IFLOWCONTENT_28'] || '请指定下一节点的处理人员!'));
		return false;
	}
	var cbr = wcm.CrashBoarder.get(window);
	if(cbr){
		cbr.notify(oPostData);
		cbr.close();
	}
}

function getFlowContextInfo(){
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
	var eUsers = $('spUsers').getElementsByTagName('span');
	for (var i = 0; i < eUsers.length; i++){
		var nUserId = eUsers[i].getAttribute('_uid', 2);
		if(nUserId == null || nUserId==""|| isNaN(parseInt(nUserId))) {
			continue;
		}
		arrUsers.push(nUserId);
	}

	var result = {
		ToUserIds : arrUsers.join(','),
		NotifyTypes : arrNotifyTypes.join(','),
		PostDesc : $('txtComment').value.trim()
		
	}
	return result;
}
 
function reasignUsers(){
	var sUrl = WCMConstants.WCM6_PATH + 'include/select_index.jsp';
	
	var groupInfo = PageContext['GroupRange'];
	if(groupInfo != null && groupInfo['GroupId'] > 0) {
		sUrl = WCMConstants.WCM6_PATH + 'include/grouped_select_index.jsp?GroupIds=' + groupInfo['GroupId'];
	}
	var arrUsers = [];
	var eUsers = $('spUsers').getElementsByTagName('span');
	for (var i = 0; i < eUsers.length; i++){
		arrUsers.push(eUsers[i].getAttribute('_uid', 2));
	}
	wcm.CrashBoarder.get('select_user_or_group').show({
		title : wcm.LANG['IFLOWCONTENT_88'] || '选择用户/组织',
		reloadable : true,
		src : sUrl,
		width: '850px',
		height: '500px',
		params : {UserIds : arrUsers.join(','),FromProcess:1},
		maskable : true,
		callback : function(arOwners){
			if(arOwners == null){
				return;
			}

			if(arOwners.length < 2){
				Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_32'] || "返回值不正确！");
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
			var sValue = TempEvaler.evaluateTemplater('template_users', arrBranchInfos, null);
			Element.update($('spUsers'), sValue); 
			var cbr = wcm.CrashBoarder.get('select_user_or_group');
			cbr.close();
			return;
		}
	});
}
