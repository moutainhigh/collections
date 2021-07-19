PageContext = {
	temp_rules: null,
	loadRules : function(_json){
		PageContext.temp_rules = _json || PageContext.nodeContext.rules;
		var json = {RULES: com.trs.util.JSON.toUpperCase(PageContext.temp_rules)};
		//alert(Object.parseSource(json))
		var sValue = TempEvaler.evaluateTemplater('rules_template', json, {})
		Element.update($('divRuleSkimList'), sValue);		
	}

};

function compareLength(strValue,minLength,maxLength,msg)
{
	var totallength=0;

	for (var i=0;i<strValue.length;i++)
	{
		var intCode=strValue.charCodeAt(i);
		if (intCode>=0&&intCode<=128){
			totallength=totallength+1;	//非中文单个字符长度加 1
		}else{
			totallength=totallength+2;	//中文字符长度则加 2
		}
	} //end for

	if (totallength<minLength||totallength>maxLength){
		Ext.Msg.alert(msg);
		return false;
	}
	return true;
} //英文字符和中文字符长度的比较

function init(){
	var flowProp = window.top.getDialogArguments();
	PageContext.nodeContext = flowProp;
	if (flowProp != null){
		document.all.flowName.value = flowProp.flowname;
		document.all.flowDesc.value = flowProp.flowdesc;
		document.all.idXML.innerText = flowProp.toXML();
	}
	//gfc 
	PageContext.loadRules();
	ValidationHelper.initValidation();
}

function checkSpecialChars(_sCode) {
	var regExp = /[<>\[\]{}#*%$%&^!~\-`]/g;
	var sResult = _sCode.match(regExp)
	return TRSArray.distinct(sResult);
}

function doOK(){
	if(!ValidationHelper.doValid('divHeader')) {
		return;
	}
	var specialChars = checkSpecialChars($('flowName').value);
	if(specialChars){
		Ext.Msg.alert(String.format("您输入的工作流名称不能包含以下特殊字符 {0} " ,specialChars), function(){
			$('flowName').select();
			$('flowName').focus();
		});
		return false;
	}	
	var flowProp = window.top.getDialogArguments();
	if (flowProp != null){
		flowProp.flowname = window.document.all.flowName.value;
		flowProp.flowdesc = window.document.all.flowDesc.value;
	}
	//gfc rules
	var frmRuleDef = $('frmRuleDef');
	if(frmRuleDef.contentWindow && frmRuleDef.contentWindow.PageContext.rules) {
		flowProp.rules = Object.clone(frmRuleDef.contentWindow.PageContext.rules, true);
	}
	
	window.returnValue = true;
	window.close();
}
function doCancel(){
	window.returnValue = false;
	window.close();
}

//TRSCrashBoard.setMaskable(true);
function defineRules(){
	var DIALOG_RULES_DEFINE = 'Rules_Define';
	var m_eTempSelector = TRSDialogContainer.register(DIALOG_RULES_DEFINE, wcm.LANG['FLOW_11'] || '定义工作流规则', 'about:blank', '360px', '350px', true);
	m_eTempSelector.onClosed = function(){
		//m_eTempSelector.destroy();
	}
	
	TRSDialogContainer.display(DIALOG_RULES_DEFINE);	
}




var ex_define = null;
var ex_skim = null;
var m_bToDispRuleDefine = false;
function defineRules(){
	if($('lnkToggle').disabled == true) {
		return false;
	}
	$('lnkToggle').disabled = true;

	var frmRuleDef = $('frmRuleDef');
	var divContent = $('divContent');
	if(ex_define == null) {
		ex_define = function(){
				if (m_bToDispRuleDefine){
					$('lnkToggle').innerHTML = (wcm.LANG['FLOW_12'] || '定义工作流规则'),(wcm.LANG['FLOW_12'] || '返回属性页面');
					$('lnkToggle').disabled = false;
					$('frmRuleDef').style.height="406px";
					Element.hide('divContent');
					Element.hide('divRuleSkimList');
					Element.hide('divOptions');
					Element.show('frmRuleDef');
				}else{
					Element.hide('frmRuleDef');
					Element.show('divContent');
					Element.show('divOptions');
					Element.show('divRuleSkimList');
					ex_skim();
					window.setTimeout(function(){
						PageContext.loadRules(frmRuleDef.contentWindow.PageContext.rules);
					}, 10);

					//ge gfc add@2008-4-16 16:50 通知iframe窗口进行隐藏页面时需要做的操作
					if($('frmRuleDef').contentWindow['doBeforeHide']) {
						$('frmRuleDef').contentWindow.doBeforeHide();
					}
				}
		};
	}
	if(ex_skim == null) {
		ex_skim = function() {
				if(m_bToDispRuleDefine){
					Element.hide('divRuleSkimList');
					ex_define();
				}else{
					Element.show('divRuleSkimList');
					$('lnkToggle').innerHTML = wcm.LANG['FLOW_START_DEFINE'] || '开始规则定义';
					$('lnkToggle').disabled = false;
				}
			};
	}

	m_bToDispRuleDefine = !m_bToDispRuleDefine;
	if(m_bToDispRuleDefine) { //将要展现"规则定义"
		if(frmRuleDef && frmRuleDef.contentWindow && frmRuleDef.contentWindow.init) {
			var rules = Object.clone(PageContext.temp_rules || PageContext.nodeContext.obj.rules, true);
			frmRuleDef.contentWindow.init(rules);
		}
		window.setTimeout(function(){
			ex_skim();
		}, 100);
		
	}else{
		ex_define();
	}
}