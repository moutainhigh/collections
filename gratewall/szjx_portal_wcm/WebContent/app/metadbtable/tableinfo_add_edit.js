function saveData(){
	ProcessBar.start(wcm.LANG.METADBTABLE_38 || '执行保存操作');
	BasicDataHelper.call("wcm61_metadbtable", "saveDBTableInfo", $("objectContainer"), true, function(transport, json){
		ProcessBar.exit();
		notifyFPCallback();
		FloatPanel.close(true);
	});	
	return false;
}

Event.observe(window,"load",function(){
	ValidationHelper.bindValidations([
		{
			renderTo:'tableDesc',
			type:'string',
			max_len:'120',
			showid:'validTip',
			desc: wcm.LANG.METADBTABLE_10 || '元数据描述'
		},
		{
			renderTo:'anotherName',
			type:'string',
			required:'',
			max_len:'60',
			showid:'validTip',
			desc: wcm.LANG.METADBTABLE_8 || '元数据别名'
		},
		{
			renderTo:'tableName',
			type:'common_char',
			required:'',
			max_len:'29',
			showid:'validTip',
			desc: wcm.LANG.METADBTABLE_9 || '元数据名称'
		}
	]);

	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand("saveData", false);
	}, "ObjectForm");
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand("saveData", true);
	}, "ObjectForm");

	ValidationHelper.initValidation();
});

LockerUtil.register2(getParameter("objectId"), 1621427854, true, "saveData");