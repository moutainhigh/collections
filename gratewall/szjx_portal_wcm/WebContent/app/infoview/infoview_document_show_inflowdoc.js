var m_FieldHelper = {
	getDefaultValue : function(v, el){
		if(!v)return '';
		var re = /^\$\$(.*)\$\$$/, arr;
		if(arr = re.exec(v)){
			return m_sInitorValues[arr[1]] || '';
		}
		return v;
	}
};
function displayFlowDoc(){
	$('frmFlowDoc').src = '../flowdoc/workflow_process_render_4wcm52.jsp?FlowDocId=' + m_nFlowDocId;
	Element.show('dvFlowDocPanel', 'right-panel');
}
function displayNewFlowDoc(){
	$('frmFlowDoc').src = '../flowdoc/workflow_process_init_4wcm52.jsp?FlowId=' + m_nFlowId;
	Element.show('dvFlowDocPanel', 'right-panel');
}
function doSubmit(){
	$('validMessageContainer').innerHTML = '';
	var frmAction = $('frmAction');
	//处理可能的表单流转
	if(m_bFlowDocInFlow === true) {
		try{
			var winFlowdoc = $('frmFlowDoc').contentWindow;
			//如果选择的是诸如“签收”、“返工”或者“拒绝”之类的操作，则不校验，但要保存
			if(!winFlowdoc.isSpecialOption()) {
				if(!winFlowdoc.validate(false)) {
					return;
				}
			}
			//else 组织一下要提交的数据
			var sPostData = winFlowdoc.buildPostXMLData();
			if(sPostData && sPostData.length > 0) {
				$('hdFlowDocPostXMLData').value = sPostData;
				//这是一个冗余数据，为了增强dowith页面的校验
				$('hdFlowDocId').value = m_nFlowDocId;
			}
			//注释掉的原因是在这几种特殊处理的时候，有必要保存下数据，但是不进行校验
			/*
			//如果选择的是诸如“签收”、“返工”或者“拒绝”之类
			//的操作，//则不需要校验/保存表单数据
			if(winFlowdoc.isSpecialOption()) {
				//指明参数后提交数据
				$('hdSkipDocSaving').value = 1;
				frmAction.method = "post";
				frmAction.submit();
				return;
			}*/
		}catch(err){
			//just skip it
			alert(err.message);
		}
	}else if(m_bNewDocInFlow) {
		try{
			var winFlowdoc = $('frmFlowDoc').contentWindow;
			//在不是诸如“签收”、“返工”或者“拒绝”操作的时候需要先先进行校验
			if(!winFlowdoc.validate(false)) {
				return;
			}
			//else 组织一下要提交的数据
			var sPostData = winFlowdoc.buildData();
			$('hdNewFlowDocMapData').value = sPostData;
		}catch(err){
			//just skip it
			alert(err.message);
		}
	}
	ProcessBar.start(wcm.LANG['INFOVIEW_DOC_91'] || '保存表单数据!');
	$('hdSkipDocSaving').value = 1;
	frmAction.method = "post";
	frmAction.submit();
}
Event.observe(window, 'load', function(){
	if(m_bFlowDocInFlow === true) {
		displayFlowDoc();
	}else if(m_bNewDocInFlow === true) {
		displayNewFlowDoc();
	}
	wcm.MultiView.draw('infoview', {
		InfoViewId : m_nInfoViewId,
		ViewMode : 3
	}); 
});