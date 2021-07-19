Ext.ns('com.trs.ui');
/**
*分类法
*/
(function(){
	//private 
	var selectChnlBtn = "-select-chnl";
	var selectDocBtn = "-select-doc";
	var template = [
		'<div class="XLink">',
			'<input type="text" name="{0}" id="{0}" value="{1}">', 
			'<button class="sel-chnl-btn" id="{0}-select-chnl">', '选择栏目', '</button>',
			'<button class="sel-doc-btn" id="{0}-select-doc">', '选择文档', '</button>',
		'</div>'
	].join("");

	function showDialog4wcm52Style(_url, _nWidth, _nHeight, myArgs){
		var nWidth = _nWidth , nHeight = _nHeight; 
		var nLeft	= (window.screen.availWidth - nWidth)/2;
		var nTop	= (window.screen.availHeight - nHeight)/2;
		if(Ext.isIE6){
			nWidth = nWidth + 20;
			nHeight = nHeight + 35;
		}

		var sFeatures	= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
							+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
							+ "center: Yes; scroll:No;help: No; resizable: No; status: No;";
		return showModalDialog(_url, myArgs, sFeatures);
	}


	com.trs.ui.XLink = Ext.extend(com.trs.ui.BaseComponent, {
		/**
		*name,value,desc,rootId,treeType
		*/
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			return String.format(template, config['name'], $transHtml(config['value']));
		},

		selectChnl : function(){
			//构造参数、获取返回值
			var myArgs = new Object();
			myArgs.myActualTop = window;
			var config = this.initConfig;
			var nChannelId = config["channelId"] || 0;
			var _arrChnlInfos = showDialog4wcm52Style(WCMConstants.WCM6_PATH + 'document/link_channel_select.html?DocChannelId='+ nChannelId + "&IsRadio=1", 400, 500, myArgs);

			var n = (_arrChnlInfos)?_arrChnlInfos.length:0;
			if(n<=0) return;

			$(config['name']).value = _arrChnlInfos[0]['PUBURL'];
		},

		selectDoc : function(){
			//构造参数、获取返回值
			var myArgs = new Object();
			myArgs.myActualTop = window;
			var config = this.initConfig;
			var nChannelId = config["channelId"] || 0;
			var _arrDocInfos = showDialog4wcm52Style(WCMConstants.WCM6_PATH + 'document/link_document_select.html?DocChannelId='+ nChannelId + "&IsRadio=1", 875, 450 ,myArgs);

			var n = (_arrDocInfos)?_arrDocInfos.length:0;
			if(n<=0) return;
			if(n > 1){
				if(!confirm(wcm.LANG.DOCUMENT_PROCESS_233 || "只有一个文档将被选中.确定吗？")){
					return;
				}
			}

			$(config['name']).value = _arrDocInfos[0]['PUBURL'];
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + selectChnlBtn, 'click', this.selectChnl.bind(this));
			Event.observe(this.initConfig['name'] + selectDocBtn, 'click', this.selectDoc.bind(this));
		}
	});
})();