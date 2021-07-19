
Event.observe(window, 'load', function(){
	var objectId = getParameter("ObjectId");
	needCheck('Enabled');
	var objectId = getParameter("ObjectId");
	if(objectId != 0) needCheck('AnonymFtp');
	needCheck('PassiveMode');
	$('ObjectId').value = objectId;
	var TargetType = $('TargetTypeAss').innerHTML;
	if(TargetType != ''){
		$('TargetType').value = TargetType;
		changeTargetType(TargetType); 
	}
});
function needCheck(sName){
	var objectId = getParameter("ObjectId");
	if(objectId == 0){
		$(sName).checked = true;
	}else if($(sName).value == 1){
		$(sName).checked = true;
	}
}
function validField(obj){
	for (var field in obj){
		var domObj = $(field);
		if(domObj.value.trim() == ''){
			Ext.Msg.alert(obj[field]);
			domObj.focus();
			domObj.select(); 
			return false;
		}
	}
	return true;
}    

function checkParams(successCallBack){
/*
	if(!$('Enabled').checked){
		(successCallBack || Prototype.emptyFunction)();
	}
*/
	var targetType = $('TargetType').value;
	var checkEmptyStrObj = null;
	var checkValidObj = null;
	var url = '';
	if(targetType == "FILE"){            
		checkEmptyStrObj = {
			DataPath :    wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_1 || '存放路径不能为空'
		};
		checkValidObj = {
			Direction :$F('DataPath')
		};
		url = "../include/validate_dir_file.jsp";
	}else{
		checkEmptyStrObj = {
				TargetServer : wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_2 || '服务器地址不能为空!',
				TargetPort :   wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_3 || '服务器端口不能为空!'
			};
		if(targetType == 'SFTP'){
			Object.extend(checkEmptyStrObj, {
				LoginUser :  wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_5 || '用户名不能为空',
				LoginPassword : wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_6 || '密码不能为空'                    
			});
		}
		Object.extend(checkEmptyStrObj, {
			DataPath :     wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_4 || '存放目录不能为空'                  
		});
		checkValidObj = {
			Type : targetType,
			Server:$F('TargetServer'),
			Port:$F('TargetPort'),
			User:$F('LoginUser'),
			Password:$F('LoginPassword'),
			DataPath:$F('DataPath'),
			Anonymous:$F('AnonymFtp'),
			PassiveMode:$F('PassiveMode')
		};

		url = "../include/validate_dir_ftp.jsp";
	}
	if(!validField(checkEmptyStrObj)){
		return false;
	}
	if(targetType != 'FILE'){
		if(!/^(\d)+$/.test($F('TargetPort'))){
			Ext.Msg.alert(wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_7 || '服务器端口不是数字!');
			$('TargetPort').focus();
			$('TargetPort').select();
			return false;
		}
	}
	
	FloatPanel.disableCommand("saveDistribution",true);
	ProcessBar.start(wcm.LANG.PUBLISHDISTRUBUTION_VALID_44 || '连接服务器');
	
	var request = new Ajax.Request(url, {
		contentType:'application/x-www-form-urlencoded',
		asynchronous:true, 
		method:'post',
		parameters:$toQueryStr(checkValidObj),
		onSuccess : function(){
			if(request.transport.responseText.trim() != ''){
				var regExp = /\[CDATA\[(.+)\]\]/m;
				var match = regExp.exec(request.transport.responseText);
				Ext.Msg.alert(match[1]);
				return false;
			}
			(successCallBack || Prototype.emptyFunction)();
			return true;                
		},
		onFailure : function(){
			ProcessBar.close();
			FloatPanel.disableCommand("saveDistribution",false);
			Ext.Msg.alert(wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_8 || '与服务器交互时出现错误!');
			return false;
		},
		onComplete : function(){
			ProcessBar.close();
			FloatPanel.disableCommand("saveDistribution",false);
		}
	});       
}

//保存站点分发
function saveDistribution(){
	checkParams(function(){
		beforeSave();
		var AnonymFtp = $('AnonymFtp');
		var PassiveMode = $('PassiveMode');
		var Enabled = $('Enabled');
		AnonymFtp.value = AnonymFtp.checked ? 1 : 0;
		PassiveMode.value = PassiveMode.checked ? 1 : 0;
		Enabled.value = Enabled.checked ? 1 : 0;

		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm6_distribution', 'save', 'distributionForm', true, function(oTransport, oJson){
			notifyFPCallback(oTransport);
			FloatPanel.close();
		});   
		return false;
	});
	return false;
}

function beforeSave(){
	var fields = {
		SFTP : ['TargetServer', 'TargetPort', 'LoginUser', 'LoginPassword', 'DataPath'],
		FTP : ['TargetServer', 'TargetPort', 'LoginUser', 'LoginPassword', 'DataPath'],
		FILE : ['DataPath']
	};
	var type = $('TargetType').value;
	var fs = fields[type] || [];
	var doms = Form.getElements('distributionId');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].tagName != 'INPUT') continue;
		var sType = doms[i].type.toUpperCase();
		if(sType != 'TEXT' && sType != "PASSWORD") continue;
		if(!fs.include(doms[i].name)){
			doms[i].value = '';
		}
	}
}

//显示隐藏用户名/密码信息
function toggleUserInfo(hidden){
	if(hidden){
		Element.hide("userInfo");
	}else{
		Element.show("userInfo");
	}
}
//根据TargetType显示隐藏相关信息
function changeTargetType(TargetType){
	switch(TargetType.toUpperCase()){
		case 'SFTP':
			Element.hide('checkInfo');
			Element.show('userRed');
			Element.show('passwordRed');
			Element.show('userInfo');
			Element.show('forFtp');
			Element.update('pathDesc', wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_9 || '存放目录:');
			$('TargetPort').value = $('SFTPport').value;
			break;
		case 'FILE':
			Element.hide('forFtp');
			Element.update('pathDesc', wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_10 || '存放路径:');
			break;
		case 'FTP':                
			toggleUserInfo($('AnonymFtp').checked);
			Element.hide('userRed');
			Element.hide('passwordRed');
			Element.show('forFtp');  
			Element.show('checkInfo');
			Element.update('pathDesc', wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_9 || '存放目录:');
			$('TargetPort').value = $('ftpport').value;
			break;
	}
}

LockerUtil.register2(getParameter("ObjectId"), 955, true, 'saveDistribution');
