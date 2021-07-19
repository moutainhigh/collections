var ClassInfos = {
	servicesName : 'wcm6_ClassInfo',
	saveMethodName : 'saveClassInfo'
};

function saveData(){
	var params = {ObjectId:0,CName : $F('CName'),CDesc : $F('CDesc')};

	BasicDataHelper.call("wcm61_classinfo","saveClassInfo",params,true,function(transport, json){				
		notifyFPCallback($v(json, "CLASSINFO.CLASSINFOID"),$v(json, "CLASSINFO.CNAME"));
	});	
	return false;
}

Event.observe(window, 'load', function(){	
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('saveData', false);
	}, "ObjectForm");
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('saveData', true);
	}, "ObjectForm");
	ValidationHelper.initValidation(); 
});
