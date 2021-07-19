var PageContext = {};
function getHelper(_sServceFlag){
	return new com.trs.web2frame.BasicDataHelper();
}
//判断是否在废稿箱中的文档,其中包括随着删除栏目而删除的文档,其状态不会置为负值,所以通过chnlid来判断
function isDel(){
	var nDocChnlId = getParameter("DocChnlId");
	if(getParameter('FromRecycle')==1 || (nDocChnlId && nDocChnlId<=0) || $('statusInput').value <=0)return true;
	return false;
}

Object.extend(PageContext, {
	params : {
		ChannelId : Math.abs(getParameter("Channelid")),
		DocumentId : getParameter("DocumentId"),
		ChnlDocId : getParameter("ChnlDocId") || 0,
		FlowDocId : getParameter('FlowDocId') || 0
	}
})
Event.observe(window, 'load', function(){
	if(PageContext.params['FlowDocId']>0 || isDel()){
		$('trOptions').style.display = "none";
	}
	var sRightValue = $('rightvalue').value;
	//各个操作是否具有权限
	if(!wcm.AuthServer.checkRight(sRightValue,32)){//版本保存是否具有权限
		$('versionSave').style.display = 'none';
	}
	if(!wcm.AuthServer.checkRight(sRightValue,35)){//文档状态修改是否具有权限
		$('docstatus').style.display = 'none';
	}
	if(!wcm.AuthServer.checkRight(sRightValue,32)){//文档编辑是否具有权限
		$('edit').style.display = 'none';
	}
	Element.show('cmdboxs');

	Event.observe('document_top_nav', 'click', function(event){
		event = event || window.event;
		var eSpan = Event.element(event);
		var sFunc = eSpan.getAttribute("_function");
		if(sFunc == null) {
			return;
		}
		if(sFunc=='backup'){
			var oPostData = {
				docids: PageContext.params["DocumentId"],
				ExcludeTrashed: true
			};
			getHelper().call('wcm6_documentBak','backup', oPostData, true,
				function(_transport,_json){
					Ext.Msg.report(_json, wcm.LANG.DOCUMENT_PROCESS_194 || '文档版本保存结果');
				}
			);
		}
		else if(sFunc=='hide_comment'){
			var aSpans = document.getElementsByTagName("SPAN");
			for(var i=0;i<aSpans.length;i++){
				if(aSpans[i].getAttribute('_trscomment',2))
					aSpans[i].style.display = 'none';
			}
			$('comment').innerHTML = $('comment').title = wcm.LANG.DOCUMENT_PROCESS_195 || '显示注释';
			$('comment').setAttribute('_function','show_comment');
		}
		else if(sFunc=='show_comment'){
			var aSpans = document.getElementsByTagName("SPAN");
			for(var i=0;i<aSpans.length;i++){
				if(aSpans[i].getAttribute('_trscomment',2))
					aSpans[i].style.display = '';
			}
			$('comment').innerHTML = $('comment').title = wcm.LANG.DOCUMENT_PROCESS_196 || '隐藏注释';
			$('comment').setAttribute('_function','hide_comment');
		}
		else if(sFunc=='close'){
			try{
//				if(top.window.opener){
//					top.window.opener.$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', PageContext.params['DocumentId']);
//				}
			}catch(err){}
			top.window.opener = null;
			top.window.close();
		}
		else if(sFunc=='edit'){
			var params = {
				DocumentId : getParameter("DocumentId"),
				channelid : getParameter("ChannelId"),
				siteid : getParameter("SiteId"),
				FromEditor : 1
			};
			var iWidth = window.screen.availWidth - 12;
			var iHeight = window.screen.availHeight - 30;
			var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
			window.open(WCMConstants.WCM6_PATH + "document/document_addedit.jsp?" + $toQueryStr(params), "_blank" , sFeature);
		}
	});
});



PageContext.rightIndexs = {
	"view":30,
	"new":31,
	"quicknew":31,
	"import":31,
	"edit":32,
	"trash":33,
	"detail":34,
	"copy":34,
	"quote":34,
	"setright":54,
	"preview":38,
	"basicpublish":39,
	"detailpublish":39,
	"recallpublish":39,
	"restore":33,
	"backup":32,
	"export":34,
	"saveorder":32,
	"changestatus":35,
	"changesource":46
};

function getSrc(win){
	var oMsrc = win;
	var nIsFormRelation = getParameter("isFormRelation") || 0;
	while(nIsFormRelation>0){
		oMsrc = win.opener;
		nIsFormRelation--;
	}
	return oMsrc;
}
//basic listen
function refreshList(event){
	var obj = event.getObj();
	//刷新列表
	if(window.opener){
		try{
			var oMsrc = getSrc(window.opener);
			oMsrc.CMSObj.createFrom({
				objType : obj.getType(),
				objId : obj.getId()
			}, {ChnlDocId : getParameter("ChnlDocId") || 0}).aftersave();
		}catch(err){
		}
	}
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_DOCUMENT,
	afteredit : refreshList,
	aftersave : function(event){
		refreshList(event);
		location.reload();
	}
});
Ext.get('docstatus').on('click', function(event, target){
	$('oldsel').style.display = 'none';
	$('sel').style.display = '';
}, this);