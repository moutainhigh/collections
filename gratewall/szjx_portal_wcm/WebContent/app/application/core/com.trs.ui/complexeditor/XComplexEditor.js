Ext.ns('com.trs.ui');
/**
*复杂编辑器
*/
(function(){
	//private 
	var editorFrm = "-frm";
	var template = [
		'<div class="XComplexEditor">',
			'<textarea name="{0}" id="{0}" style="display:none;">{1}</textarea>',
			//'<textarea id="_editorValue_" style="display:none;">{1}</textarea>',
			'<iframe src="{2}" id="{0}-frm" frameborder="0" scrolling="auto" class="complexeditor-frame" width="100%" height="100%"></iframe>',
		'</div>'
	].join("");
	
	var disabledEditorUrl = XConstants.BASE_PATH + 'com.trs.ui/editor/blank.html';
	
	com.trs.ui.XComplexEditor = Ext.extend(com.trs.ui.BaseComponent, {
		disabledCls : 'XDisabledCls XDisabledEditor',
		//editorUrl : '/wcm/app/editor/editor.jsp',//XConstants.BASE_PATH + 'com.trs.ui/editor/core/editor.html',
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			var url = '/wcm/app/editor/editor.html?excludeToolbar=FitWindow&ChannelId='
			+ getParameter('channelid') + "&DocumentId="
			+ getParameter('ObjectId') + "&ChnlDocId="
			+ getParameter('ChnlDocId');
			if(config['params']) url += "&" + $toQueryStr(config['params']);
			return String.format(template, sName, config['value'], url);
		},
		initActions : function(){
			var frmName = this.initConfig['name'] + editorFrm,iframe = $(frmName),
				name = this.initConfig['name'];
			Event.observe(iframe.contentWindow,"load",function(){
				var t = setInterval(function(){
					var FCK = iframe.contentWindow.GetEditor();
					if(!FCK || !FCK.EditingArea)return;
					if(FCK)clearInterval(t);
					FCK.SetHTML($(name).value);
				},100);
			});
		},
		getContent : function(){
			var frmName = this.initConfig['name'] + editorFrm;
			try{
				var iframe = $(frmName);
				var FCK = iframe.contentWindow.GetEditor();
				var oWindow = iframe.contentWindow.GetTrueEditor();
				if(oWindow.OfficeActiveX){
					oWindow.OfficeActiveX.UploadLocals();
				}
				return FCK.QuickGetHtml(true, true);
			}catch(error){
				return "";
			}
		},
		getValue : function(){
			var sContent = this.getContent();
			if(sContent.length == 17 && sContent.toLowerCase() == "<div>&nbsp;</div>"){
				sContent = "";
			}
			$(this.initConfig['name']).value = sContent;
			return sContent
		}
	});
	
})();


// 动态设置编辑器的高度
Event.observe(window,"load",function(){
	document.getElementsByClassName = function(cls, p) {
		if(p && p.getElementsByClassName) return p.getElementsByClassName(cls);
		var arr = ($(p) || document.body).getElementsByTagName('*');
		var rst = [];
		var regExp = new RegExp("(^|\\s)" + cls + "(\\s|$)");
		for(var i=0,n=arr.length;i<n;i++){
			if (arr[i].className.match(regExp))
				rst.push(arr[i]);
		}
		return rst;
	}
	var XCEditors = document.getElementsByClassName("XComplexEditor");
		//XCEditor = XCEditors[0];
	if(XCEditors.length == 0)
		return;
	var clientH = document.documentElement.clientHeight || document.body.clientHeight;
	var btnH = $("buttons_container")?$("buttons_container").offsetHeight:50;
	var leftH =clientH - btnH -getOtherHeight();
	// 设置编辑器的高度
	var height = leftH - 30>200?leftH - 30:400 + "px";
	for (var i = 0; i < XCEditors.length; i++){
		XCEditors[i].style.height =height;
	}
	//XCEditor.style.height = leftH - 30>200?leftH - 30:400 + "px";
	
	//计算所有产生的剩余的高度
	function getOtherHeight(){
		var row = findItem(XCEditors[0],"","design-row","");
		var dom = XCEditors[0];
		while(dom.tagName !== "LI"){
			dom = dom.parentNode;
		}
		if(!row) return 0;
		return $("main").offsetHeight - dom.offsetHeight*XCEditors.length;
	}
	// 获取元素
	function findItem(t, attr,cls, aPAttr){
		aPAttr = aPAttr || [];
		while(t!=null&&t.tagName!='BODY'&& t.nodeType==1){
			for (var i = 0; i < aPAttr.length; i++){
				if(dom.getAttribute(aPAttr[i]) != null) return 0;
			}
			if(cls && Element.hasClassName(t, cls))return t;
			if(attr && t.getAttribute(attr, 2)!=null)return t;
			t = t.parentNode;
		}
		return null;
	}
});