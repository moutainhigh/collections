PageContext = {
	loadPage : function(){
		var eRuleName = $('txtRuleName');
		var eSelRuleType = $('selRuleType');
		if(PageContext.rule && PageContext.rule['rulename']) {
			eRuleName.value = PageContext.rule['rulename'];
			eSelRuleType.value = PageContext.rule['ruletype'];
		}else{
			eRuleName.value = '';
			//TODO 根据其他已有规则决定新创建的规则类型
			eSelRuleType.value = this.getAdaptedType();
		}
		try{
			eRuleName.select();
			eRuleName.focus();
		}catch(error){
		}
	},
	getAdaptedType : function(){
		var rules = PageContext.rules;
		
		if(rules == null || rules.length == 0) {
			return 0; // 默认为'签收前'
		}
		//else
		var nMaxType = 0;
		for (var i = 0; i < rules.length; i++){
			var nType = parseInt(rules[i]['ruletype']);
			if(nType > nMaxType) {
				nMaxType = nType;
			}
		}
		if(nMaxType >= 5 || nMaxType < 0) {
			return 0;
		}
		//else
		return (nMaxType + 1);
	}

}
//for cb
function init(_ruleInfo){
	PageContext.isNew = (_ruleInfo.rule == null);
	PageContext.rule = _ruleInfo.rule;
	PageContext.index = _ruleInfo.index;
	PageContext.rules = _ruleInfo.rules;
	PageContext.loadPage();

}
function submitData(){
	//判空和长度
	if(!ValidationHelper.doValid('divHeader')) {
		return;
	}
		var rules = PageContext.rules;
		var nCurrItemId = 0;
		if(PageContext.isNew == true) { //新建
			var aNewRule = new WorkflowRule({
				rulename: $('txtRuleName').value,
				flowruleid: Math.random() + '',
				ruletype : $('selRuleType').value //TODO
			});
			nCurrItemId = aNewRule['flowruleid'];
			rules.push(aNewRule);
		}else{
			PageContext.rule['rulename'] = $('txtRuleName').value;
			PageContext.rule['ruletype'] = $('selRuleType').value;
			nCurrItemId = PageContext.rule['flowruleid'];
			rules[PageContext.index - 1] = PageContext.rule;
		}
		var cbr = wcm.CrashBoarder.get("Rule_AddEdit");
		cbr.notify({'rules': rules, 'itemid': nCurrItemId});
		cbr.hide();
}
/*
function closeframe(){
	if (window.parent){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
	}
}
*/