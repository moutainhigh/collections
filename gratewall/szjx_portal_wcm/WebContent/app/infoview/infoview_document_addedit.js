var InfoDocHelper = {
	openeditor : function(info){
		var cb = wcm.CrashBoard.get({
			id : 'Trs_Simple_Editor',
			title: wcm.LANG['INFOVIEW_DOC_88'] || '格式文本编辑器',
			url : '../neweditor/infoview_editor.html',
			params : {
				html : info.html
			},
			width:'500px',
			height:'345px',
			callback : function(html, text){
				info.callback(html, text);
			},
			appendParamsToUrl : false
		});
		cb.show();
	},
	attachFile : function(info){
		var cb = wcm.CrashBoard.get({
			id : 'FileUploadDialog',
			title: wcm.LANG['INFOVIEW_DOC_89'] || '上传文件',
			url : 'file/file_upload.jsp',
			params : info.params,
			width:'420px',
			height:'160px',
			callback : function(args){
				info.callback(args);
			},
			btns : false
		});
		cb.show();
	},
	doValidation : function(rst){
		var iv = $('infoview');
		var frms = iv.getElementsByTagName('IFRAME');
		if(frms.length==0)return false;
		for(var i=0;i<frms.length;i++){
			oWin = frms[i].contentWindow;
			try{
				if(!oWin.exCenter)continue;
			}catch(err){continue;}
			if(oWin.exCenter._beforeSubmit(rst)===false)
				return false;
		}
	},
	collectMultiViewData : function(){
		var iv = $('infoview');
		var frms = iv.getElementsByTagName('IFRAME');
		if(frms.length==0)return null;
		var xmlHelper = frms[0].contentWindow.XmlDataHelper;
		var eleHelper = frms[0].contentWindow.EleHelper;
		var json = {}, rst = {}, oWin;
		for(var i=0;i<frms.length;i++){
			oWin = frms[i].contentWindow;
			try{
				if(!oWin.exCenter)continue;
			}catch(err){continue;}
			if(oWin.exCenter._beforeSubmit(rst)===false){
				validError(rst.msgs);
				return false;
			}
			Object.extend(json, oWin.EleHelper.jsonData());
		}
		var doc = xmlHelper.getDoc(), root = doc.documentElement;
		eleHelper.jsonIntoEle(doc, root, json);
		return doc.xml;
	}
}
var m_FieldHelper = {
	getDefaultValue : function(v, el){
		if(!v)return '';
		//添加对日期和时间控件的校验，假如是该控件的话，就用客户端的当前时间覆盖服务器端生成的时间
		if(el.getAttribute('data_type')=='datetime'){
			var sGetValidation ="";
			var sValidation = el.getAttribute('validation');
			if(sValidation){
				eval("sValidation = {"+sValidation+"}");
				if(sValidation.date_format){
					sGetValidation = sValidation.date_format;
				}
			}
			if(sGetValidation) {
				var sFormat = sGetValidation ||"yyyy-MM-dd HH:mm:ss";
				var currTime = new Date().format(sFormat);
				Ext.apply(m_sInitorValues,{CurrTime:currTime});
			}
		}
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
function validError(msgs){
	$('validMessageContainer').innerHTML = '<li>' + msgs.join('<br><li>');
	Element.show('dvValidError');
	Element.show('right-panel');
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
	var rst = {};
	if(InfoDocHelper.doValidation(rst)===false){
		validError(rst.msgs || []);
		return;
	}
	ProcessBar.start(wcm.LANG['INFOVIEW_DOC_91'] || '保存表单数据!');
	frmAction.DocTitle.value = $('_DocTitle') ? $('_DocTitle').value : '';
	frmAction.ObjectXML.value = InfoDocHelper.collectMultiViewData();
	frmAction.method = "post";
	frmAction.submit();
}
function unlockObj(_nObjId, _nObjType){
	var postData = {
		ObjId :  _nObjId,
		ObjType : _nObjType
	};
	new ajaxRequest({
		url : '../include/object_unlock.jsp',
		parameters : 'ObjId=' + _nObjId + '&ObjType=' + _nObjType
	});
}
function unlock(_nObjId, _nObjType){
	if(_nObjId>0) {
		unlockObj(_nObjId, _nObjType);
	}
}
Event.observe(window, 'load', function(){
	if(m_bFlowDocInFlow === true) {
		displayFlowDoc();
	}else if(m_bNewDocInFlow === true) {
		displayNewFlowDoc();
	}
	wcm.MultiView.draw('infoview', {
		InfoViewId : m_nInfoViewId,
		ViewMode : 4,
		FlowDocId : m_nFlowDocId
	});
});