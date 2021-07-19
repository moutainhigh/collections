PageContext = {
	loadPage : function(_params){
		PageContext.params = _params;
		var flowDocIds = _params['ObjectIds'].split(',');
		//var arrDocs = _params['docs'].split(',');
		var sOption = _params['option'].split(',');
		var titles = _params['titles'].split(',');
		//校验参数
		if(flowDocIds == null || flowDocIds.length == 0) {
			Ext.Msg.alert(wcm.LANG['IFLOWCONTENT_10'] || '没有要操作的文档！');
			return;
		}
		var sAction = null;
		if(sOption == null 
			|| (sAction = this.getActionByOption(sOption)) == null) {
			Ext.Msg.alert(String.format("没有定义类型为[{0}]文档操作!",sOption));
			return;
		}
		PageContext.action = sAction;
		//输出文档标题，收集id参数
		var sTitles = '', arrIds = [];
		for (var i = 0; i < flowDocIds.length; i++){
			sTitles += '<li title=\'' + $trans2Html(titles[i]) + '\'>' + $trans2Html(titles[i]) + '</li>';
			arrIds.push(flowDocIds[i]);
		}
		if($('divDocs')){
			Element.update('divDocs', sTitles);
		}
		PageContext.ids = arrIds;
		//其他处理
		//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
		try{
			$('txtComment').select();
			$('txtComment').focus();
		}catch(ex){}
	},
	getActionByOption : function(_sType){
		if(this.m_hContentMap == null) {
			this.m_hContentMap = {
				'refuse' : 'refuse',
				'cease' : 'forceEnd',
				'rework' : 'backTo'
			}
		}

		var result = this.m_hContentMap[_sType];
		return result;		
	}
};


function init(_params){
	PageContext.loadPage(_params);
}

function doOK(){
	if(!$('txtComment').disabled){
		var sLen = 0;
		if($('txtComment').value.trim() == ''){
			if (!confirm(wcm.LANG['IFLOWCONTENT_13'] || '没有填写处理意见！仍要继续提交吗？')) {
				//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
				try{
					$('txtComment').select();
					$('txtComment').focus();
				}catch(ex){}
				return false;
			}
		}else if((sLen = $('txtComment').value.byteLength()) > 500){
			var sErrorInfo = '<span style="width:180px;overflow-y:auto;">' + String.format("处理意见限制为500个字符长度,当前为{0}字节。<br><br><b> 提示:</b>每个汉字长度为2",sLen) + '</span>';
			Ext.Msg.$fail(sErrorInfo, function(){
				//$dialog().hide();
				//兼容了该页面嵌入到别的页面的时候，控件不可见引起的问题
				try{
					$('txtComment').select();
					$('txtComment').focus();
				}catch(ex){}
			});
			return false;
		}
	}
	doSubmit();
	return false;

}
function doSubmit(){
	var oPostData = {
		PostDesc: $('txtComment').value
	};
	if(PageContext.params['option'] == 'cease') {
		oPostData['ContentType'] = PageContext.params['ctype'];
		oPostData['ContentId'] = PageContext.params['cid'];
	}else{
		oPostData['ObjectIds'] = PageContext.ids;
	}
	if(PageContext.action == 'backTo'){
		oPostData['NotifyTypes'] = 'email,message';
	}
	var cbr = wcm.CrashBoarder.get(window);
	cbr.hide();
	BasicDataHelper.call('wcm6_process', PageContext.action, oPostData, true, function(_trans, _json){ 
		cbr.notify(_trans);
		cbr.close();
	});
	return false;
}