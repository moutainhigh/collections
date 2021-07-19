Object.extend(PageContext, {
	loadPage : function(){
		$('txtUsers').value = PageContext.uname;
		$('txtGroups').value = PageContext.gname;
		$('hdUserIds').value = PageContext.uid;
		$('hdGroupIds').value = PageContext.gid;
		$('txtTitle').value = PageContext.raw_title;
		//$('txtContent').value = PageContext.raw_msg;
		this.loadEditor(PageContext.raw_msg);
	},
	loadEditor : function(_sVal){
		if(PageContext.SimpleFCKeditor == null) {
			var editor = new FCKeditor( 'TRS_Editor' ) ;
			editor.BasePath	= '../simpleeditor/';
			editor.Height = '100%' ;
			editor.ToolbarSet = 'Title';
			editor.Value = _sVal;
			PageContext.SimpleFCKeditor = editor;
			$('editorContainer').innerHTML = PageContext.SimpleFCKeditor.CreateHtml();		
		}
		else{
			FCKeditorAPI.editorWindow.FCK.SetHTML(_sVal);
		}
	},
	sendMsg : function(){
		if(!validate()) {
			return;
		}
		//else
		var sUserNames	= $('txtUsers').value.trim();
		if(sUserNames == '' || !this.isUserNamesChangedAfterSelect()) {
			this.doSendMsg();
			return;
		}
		//else 需要先获取到user-ids信息
		PageContext.retrieveUserIds(sUserNames, PageContext.doSendMsg);
	},
	retrieveUserIds : function(_sUserNames, _fDoAfter){
		BasicDataHelper.call('wcm6_user', 'getUsersByNames', {usernames: _sUserNames}, true, function(_trans, _json){
			//var sUserId = $v(_json, 'users.user.userid');
			var arUsers = $a(_json, 'users.user');
			if(arUsers == null) {
				$fail('无法获取用户用户[<b>' + _sUserNames + '</b>]的任何信息！', function(){
					$dialog().hide();
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
				sExp = '获取用户[<b>' + _sUserNames + '</b>]的用户信息失败！';
			}
			$fail(sExp, function(){
				$dialog().hide();
				$('txtUsers').focus();
				$('txtUsers').select();				
			});			
		});				
	},
	doSendMsg : function(){
		BasicDataHelper.call('wcm6_message', 'sendMessage', $('fmData'), true, function(_trans, _json){
			closeframe();
			$timeAlert('消息发送成功！', 3, null, null, 2);
		});		
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
	var sUserNames = eUsers.value.trim();
	var sGroupNames = eGroups.value.trim();
	if(sUserNames == '' && sGroupNames == '') {
		$alert('没有指定接收用户或组织！');
		return false;
	}
	var eTitle = $('txtTitle');
	if(eTitle.value.trim() == '') {
		$alert('请填写标题！', function(){
			$dialog().hide();
			eTitle.focus();
			eTitle.select();
		});
		return false;
	}
	if(eTitle.value.getLength() > 200) {
		$alert('标题最大允许长度为200个字符！', function(){
			$dialog().hide();
			eTitle.focus();
			eTitle.select();
		});
		return false;
	}

	//var eContent = $('txtContent');
	var sContent = FCKeditorAPI.editorWindow.FCK.GetHTML(true);
	if(sContent.trim() == '') {
		$alert('请填写正文！', function(){
			$dialog().hide();
			//eContent.focus();
			//eContent.select();
		});
		return false;
	}
	if(sContent.trim().getLength() > 1000) {
		$alert('正文最大允许长度为1000个字符！', function(){
			$dialog().hide();
			//eContent.focus();
			//eContent.select();
		});
		return false;
	}
	$('hdContent').value = sContent;
	
	var sendTypes = getSendTypes();
	if(sendTypes.length == 0) {
		$alert('请至少选择一种发送类型！');
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

function closeframe(){
	if (window.parent){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
	}
}

Event.observe(window, 'unload', function(){
	//$destroy(PageContext);
} , false);


function setOwner(){
	if(PageContext.isUserNamesChangedAfterSelect()) {//如果已经手动地修改了usernames，则需要从服务器端重新获取uids
		if($('txtUsers').value.trim() == '') {
			renderOwnerSeting('', PageContext.gid);
			return;
		}//*/
		PageContext.retrieveUserIds($('txtUsers').value.trim(), function(_sUserIds){
			renderOwnerSeting(_sUserIds, PageContext.gid);
		});
		return;
	}
	//else 从上一次选取的结果中取
	renderOwnerSeting(PageContext.uid, PageContext.gid);
}
function renderOwnerSeting(_sUserIds, _sGroupIds){
	var oTRSAction = new CTRSAction('../../../console/include/select_index.jsp');
	oTRSAction.setParameter('UserIds', _sUserIds);
	oTRSAction.setParameter('GroupIds', _sGroupIds);
	var arOwners = oTRSAction.doDialogAction(800, 600);
	if(arOwners == null){
		return;
	}

	if(arOwners.length < 4){
		CTRSAction_alert('返回值不正确！');
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
}

TRSCrashBoard.setMaskable(true);

//button initialize...
Event.observe(window, 'load', function(){
	new $WCMButton({
		ButtonType	: $ButtonType.MESSAGESEND,	
		Container	: 'ButtonContainer',
		Action		: 'PageContext.sendMsg()'
	}).loadButton();
	new $WCMButton({
		ButtonType	: $ButtonType.CLOSE,	
		Container	: 'ButtonContainer',
		Action		: 'closeframe()'
	}).loadButton();
});