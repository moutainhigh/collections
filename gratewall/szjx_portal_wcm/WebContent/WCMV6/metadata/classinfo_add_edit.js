var ClassInfos = {
	servicesName : 'wcm6_ClassInfo',
	saveMethodName : 'saveClassInfo'
};

function saveData(){
	$beginSimplePB("正在保存数据");
	ClassInfoMgr.save(getParameter("objectId") || 0, {
		name : $F('cName'),
		desc : $F('cDesc')
	}, null, function(transport, json){
		$endSimplePB();
		var $v = com.trs.util.JSON.value;
		var objectId = $v(json, "CLASSINFO.CLASSINFOID");
		var objectName = $v(json, "CLASSINFO.CNAME");
		var params = 'objectId=' + objectId + "&objectName=" + objectName + "&isChanged=true";
		FloatPanel.open('./metadata/classinfo_config.html?'+params, '分类法维护', 500, 300);
//		$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', [getParameter("objectId") || 0]);
//		FloatPanel.close(true);
	});
	return false;
}

FloatPanel.addCloseCommand();   
FloatPanel.addCommand('savebtn', '下一步', 'saveData', null);

ValidationHelper.addValidListener(function(){
	FloatPanel.disableCommand('savebtn', false);
}, "ObjectForm");
ValidationHelper.addInvalidListener(function(){
	FloatPanel.disableCommand('savebtn', true);
}, "ObjectForm");

Event.observe(window, 'load', function(){
	Form.focusFirstElement("ObjectForm");
	ValidationHelper.initValidation(); 
});
