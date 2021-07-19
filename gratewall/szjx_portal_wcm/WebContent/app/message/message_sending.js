PageContext={};
Object.extend(PageContext, {
	loadPage : function(){
		$('txtUsers').value = PageContext.uname;
		$('txtGroups').value = PageContext.gname;
		$('hdUserIds').value = PageContext.uid;
		$('hdGroupIds').value = PageContext.gid;
		$('txtTitle').value = PageContext.raw_title;
		//$('txtContent').value = PageContext.raw_msg;
		window.m_editorCfg.html = PageContext.raw_msg;
		delete window.m_editorCfg.autoinit;
		InitEditor();
		//if($('txtUsers').value=="")
		//$('txtUsers').select();
	},
	sendMsg : function(){
		if(!validate()) {
			return false;
		}
		//else
		var sUserNames	= $('txtUsers').value.trim();
		if(sUserNames == '' || !this.isUserNamesChangedAfterSelect()) {
			this.doSendMsg();
			return false;
		}
		//else 需要先获取到user-ids信息 
		PageContext.retrieveUserIds(sUserNames, PageContext.doSendMsg);
		return false;
	},
	retrieveUserIds : function(_sUserNames, _fDoAfter){
		BasicDataHelper.call('wcm6_user', 'getUsersByNames', {usernames: _sUserNames}, true, function(_trans, _json){
			//var sUserId = $v(_json, 'users.user.userid');
			var arUsers = $a(_json, 'users.user');
			if(arUsers == null) {
				Ext.Msg.warn(String.format("无法获取用户[<b>{0}</b>]的任何信息！",_sUserNames ), function(){
					$('txtUsers').focus();
					$('txtUsers').select();
				});
				return false;
			}
			//else
			var arUserIds = [];
			for (var i = 0; i < arUsers.length; i++){
				var user = arUsers[i];
				arUserIds.push($v(user, 'userid'));
			}
			//alert(arUserIds.join(','));
			$('hdUserIds').value = arUserIds.join(',');
			if(_fDoAfter && typeof(_fDoAfter) == 'function') {
				_fDoAfter($('hdUserIds').value, _trans, _json);
			}
		}.bind(this), function(_trans, _json){
			var sExp = $v(_json, 'Fault.Message');
			
			if(sExp == null || sExp.trim() == '') {
				sExp = String.format("获取用户[<b>{0}</b>]的用户信息失败!",_sUserNames);
			}else{
				//wenyh@2009-09-23 truncate the ERR-xxx
				var nPos = sExp.indexOf("[ERR-");
				 if (nPos >= 0) {
                    nPos = sExp.indexOf(']', nPos + 1);
                    if (nPos > 0) sExp = sExp.substring(nPos + 1);
				 }
			}
			Ext.Msg.warn(sExp, function(){
				$('txtUsers').focus();
				$('txtUsers').select();				
			});			
		});				
	},
	doSendMsg : function(){
		BasicDataHelper.call('wcm6_message', 'sendMessage', $('fmData'), true, function(_trans, _json){
			Ext.Msg.$timeAlert(wcm.LANG['MESSAGE_45'] || '消息发送成功！', 3,  function(){
				var cbr = wcm.CrashBoarder.get("Send_Msg");
					//通知父窗口关闭，父窗口关闭时，子窗口自动关闭
				cbr.notify($v(_json, 'result'));
				cbr.close();
			});
		});
		return false;
	},
	/*
	*判断在选择用户后，是否又手动地进行做了修改
	*/
	isUserNamesChangedAfterSelect : function(){
		return ($('hdUserNames').value.trim().toLowerCase()
			 != $('txtUsers').value.trim().toLowerCase()); 
	}
});

function validate(){
	var eUsers = $('txtUsers');
	var eGroups = $('txtGroups');
	var eRoles = $('everyoneRole');
	var sUserNames = eUsers.value.trim();
	var sGroupNames = eGroups.value.trim();
	if(sUserNames == '' && sGroupNames == '' && !eRoles.checked) {
		Ext.Msg.$alert(wcm.LANG['MESSAGE_46'] || '没有指定接收用户,组织或所有用户！');
		return false;
	}
	var eTitle = $('txtTitle');
	if(eTitle.value.trim() == '') {
		Ext.Msg.$alert(wcm.LANG['MESSAGE_47'] || '请填写标题！', function(){
			eTitle.focus();
			eTitle.select();
		});
		return false;
	}
	if(eTitle.value.byteLength() > 200) {
		Ext.Msg.$alert(wcm.LANG['MESSAGE_48'] || '标题最大允许长度为200个字符！', function(){
			eTitle.focus();
			eTitle.select();
		});
		return false;
	}

	var sContent = GetText();
	if(sContent.trim() == '') {
		Ext.Msg.$alert(wcm.LANG['MESSAGE_49'] || '请填写正文！', function(){
			//eContent.focus();
			//eContent.select();
		});
		return false;
	}
	sContent = GetHtml();
	if(sContent.trim().byteLength() > 1000) {
		Ext.Msg.$alert(wcm.LANG['MESSAGE_50'] || '正文最大允许长度为1000个字符！', function(){
			//eContent.focus();
			//eContent.select();
		});
		return false;
	}
	var mHtml = /(<[^>]+?)(\son[a-zA-Z]+\s*=\s*(\w+\([^\)]*?\)|\'[^\']*?\'|\"[^\"]*?\"))([^>]*>)/gi;
	sContent = sContent.replace(mHtml, "$1$4");
	$('hdContent').value = sContent;
	
	var sendTypes = getSendTypes();
	if(sendTypes.length == 0) {
		Ext.Msg.$alert(wcm.LANG['MESSAGE_51'] || '请至少选择一种发送类型！');
		return false;
	}
	$('hdSendTypes').value = sendTypes;

	return true;
}

function getSendTypes(){
	var result = [];
	var chks = document.getElementsByName('SendingType');
	for (var i = 0; i < chks.length; i++){
		var chk = chks[i];
		if(chk.checked) {
			result.push(chk.value);
		}
	}
	return result;//.join(',');
}

// define for cb
function init(_args) {
	PageContext.uname = _args.uname || '';
	PageContext.gname = _args.gname || '';
	PageContext.uid = _args.uid || '';
	PageContext.gid = _args.gid || '';
	PageContext.raw_title = _args.raw_title || '';
	PageContext.raw_msg = _args.raw_msg || '';

	PageContext.loadPage();
}

Event.observe(window, 'unload', function(){
	//$destroy(PageContext);
} , false);


function setOwner(userOrGroup){
	if(PageContext.isUserNamesChangedAfterSelect()) {//如果已经手动地修改了usernames，则需要从服务器端重新获取uids
		if($('txtUsers').value.trim() == '') {
			renderOwnerSeting('', PageContext.gid, 0);
			return;
		}//*/
		PageContext.retrieveUserIds($('txtUsers').value.trim(), function(_sUserIds){
			renderOwnerSeting(_sUserIds, PageContext.gid);
		});
		return;
	}
	//else 从上一次选取的结果中取
	renderOwnerSeting(PageContext.uid, PageContext.gid, userOrGroup);
}
function renderOwnerSeting(_sUserIds, _sGroupIds, _userOrGroup){

	var sUrl = WCMConstants.WCM6_PATH + 'include/select_index.jsp';
	wcm.CrashBoarder.get('select_user_or_group').show({
		title : wcm.LANG['MESSAGE_52'] || '选择用户/组织',
		reloadable : true,
		src : sUrl,
		width: '970px',
		height: '470px',
		params : {UserIds : _sUserIds, GroupIds : _sGroupIds, UserOrGroup : _userOrGroup, ForSendMsg:1},
		maskable : true,
		callback : function(arOwners){
			if(arOwners == null){
				return;
			}
			if(arOwners.length < 4){
				alert(wcm.LANG['MESSAGE_53'] || '返回值不正确！');
				return;
			}
			var regexp = /,/g;
			PageContext.uname = arOwners[1] || '';
			PageContext.uid = arOwners[0] || '';
			PageContext.gname = arOwners[3] || '';
			PageContext.gid = arOwners[2] || '';
			PageContext.uname = PageContext.uname.replace(regexp, ',');
			PageContext.gname = PageContext.gname.replace(regexp, ',');
			$('hdUserIds').value = PageContext.uid;
			$('hdGroupIds').value = PageContext.gid;
			$('hdUserNames').value = PageContext.uname;
			$('txtUsers').value = PageContext.uname;
			$('txtGroups').value = PageContext.gname;
			var cbr = wcm.CrashBoarder.get('select_user_or_group');
			cbr.close();
			return;
		}
	});
}  


function toggleSelect(){
	var isEveryOneChecked = $('everyoneRole').checked;
	$('hdRoleIds').value = isEveryOneChecked ? $('everyoneRole').value : "";
	toggleElement('User', isEveryOneChecked);
	toggleElement('Group', isEveryOneChecked);
}

function toggleElement(sName, hidden){
	var sNames = sName + "s";
	if(hidden){
		var dom = $('select' + sNames);
		dom.style.display = 'none';

		dom = $('txt' + sNames);
		dom.setAttribute("_value", dom.value);
		dom.value = "";
		dom.disabled = true;

		dom = $('hd'+sName+'Ids');
		dom.setAttribute("_value", dom.value);
		dom.value = "";
	}else{
		var dom = $('select' + sNames);
		dom.style.display = '';

		dom = $('txt' + sNames);		
		dom.value = dom.getAttribute("_value") || "";
		dom.disabled = false;

		dom = $('hd'+sName+'Ids');
		dom.value = dom.getAttribute("_value") || "";
	}
}