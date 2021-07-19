Ext.ns('com.trs.ui');
/**
*文档选择树
*/
(function(){
	//private 
	var documentSelectBtn = "-select-btn";
	var documentText = "-text";
	var template = [
		'<div class="XDocSelect">',
			'<input type="hidden" name="{0}" id="{0}" value="{1}" />',
			'<div class="document-select" id="{0}-select-btn"></div>',
			'<div class="document-text" id="{0}-text">{2}</div>',
		'</div>'
	].join("");

	com.trs.ui.XDocSelect = Ext.extend(com.trs.ui.BaseComponent, {
		/**
		*name,value,desc,rootId,treeType
		*/
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			//TODO
			//desc,rootId可能需要根据value去获取,但为了提供性能，先由外界传入
			var sDocumentText = (config['desc']||"无") + "[id=" + (config['value']||0) + "]";
			if(!config['desc']){
				config['value'] = "";
				sDocumentText = "";	
			}
			return String.format(template, config['name'], config['value'], sDocumentText);
		},
		
		documentSelect : function(event){
			var config = this.initConfig;
			var sName = config['name'];
			var sDocId = document.getElementById(sName).value || 0;
			var sChnlId = config['chnlId'] || 0;
			var sSiteId = config['siteId'] || 0;
			var params = {
				documentSelectBtn : documentSelectBtn,
				documentText : documentText,
				sName : sName,
				sDocId : sDocId,
				sChnlId : sChnlId,
				sSiteId : sSiteId
			};
			event = window.event || event;
			var dom = Event.element(event);
			showDocSelect(params, dom);
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + documentSelectBtn, 'click', this.documentSelect.bind(this));
		}
	});
})();

function showDocSelect(params, dom){
	var docID = params['sDocId'];
	var channelID = params['sChnlId'];
	var webSiteID = params['sSiteId'];
	Element.addClassName(dom, "setrelation");
	var sUrl = WCMConstants.WCM6_PATH + 'document/document_select_index.jsp?selectType=radio&recIds='+docID+"&DocChannelId=" + channelID + "&SiteId=" + webSiteID;
	var result = ShowDialog4wcm52Style(sUrl, 800, 440);
	Element.removeClassName(dom, "setrelation");
	if(!result) return;
	//下面是对参数进行赋值
	var sName = params["sName"];
	var index = Math.max(result['ids'].length-1, 0);
	var recId = result['ids'][index];
	if(!recId) return;
	$(sName).value = recId;
	var selectorID = sName + params["documentText"];
	var selectorText = result['infos'][recId].docTitle+'[id=' + recId + ']';
	Element.update(selectorID, selectorText);
}

function ShowDialog4wcm52Style(_url, _nWidth, _nHeight, dialogArguments){
	var nWidth = _nWidth , nHeight = _nHeight; 
	var nLeft	= (window.screen.availWidth - nWidth)/2;
	var nTop	= (window.screen.availHeight - nHeight)/2;

	var sFeatures	= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
						+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
						+ "center: Yes; scroll:No;help: No; resizable: No; status: No;";
	try{
		var bResult = showModalDialog(_url, dialogArguments==null?window:dialogArguments, sFeatures);
		return bResult;
	}catch(e){
		alert((wcm.LANG.CHANNEL_104 || "您的IE插件已经将对话框拦截!\n")
				+ (wcm.LANG.CHANNEL_105 || "请将拦截去掉-->点击退出-->关闭IE,然后重新打开IE登录即可!\n")
				+ (wcm.LANG.CHANNEL_106 || "给您造成不便,TRS致以深深的歉意!"));		
	}
	return true;
}