$package("com.trs.editor");

$import('com.trs.editor.Component');
$import('com.trs.editor.Commander');
$import('com.trs.editor.ImgUploader');
$import('com.trs.editor.ArticleSubmiter');
$import('com.trs.dialog.Dialog');
$import('com.trs.logger.Logger');
$import('com.trs.util.CommonHelper');

$importCSS("com.trs.editor.css.editor");

com.trs.editor.Editor={
	newDocument:function(id,content){
		com.trs.editor.Config.load();
		var textArea = document.getElementById('textArea_'+id);
		
		new Insertion.Before(textArea,'<div id="toolbar_'+id+'"></div><TABLE id="body_table_'+id+'" width=100% cellSpacing=0 cellPadding=0 border=0>\
				<TR>\
					<TD class=editor_topleft id=editor_topleft_'+id+'></TD>\
					<TD class=editor_top id=editor_top_'+id+'></TD>\
					<TD class=editor_topright id=editor_topright_'+id+'></TD>\
				</TR>\
				<TR>\
					<TD class=editor_left id=editor_left_'+id+'></TD>\
					<TD class=editor_center id=editor_center_'+id+'></TD>\
					<TD class=editor_right id=editor_right_'+id+'></TD>\
				</TR>\
				<TR>\
					<TD class=editor_bottomleft id=editor_bottomleft_'+id+'></TD>\
					<TD class=editor_bottom id=editor_bottom_'+id+'></TD>\
					<TD class=editor_bottomright id=editor_bottomright_'+id+'></TD>\
				</TR>\
			</TABLE>');
		com.trs.editor.Config.components['Source'].toElement($('editor_bottom_'+id));
		var body=$('editor_center_'+id);
		this.createToolBar(id);
		this.toolbar.style.width = '100%';
		this.toolbar.style.zIndex = 100;
		//hide textarea
		textArea.style.display = 'none';
		textArea.style.width='100%';
		textArea.style.height='100%';
		
		
		//create the iframe
		var iframe = document.createElement('iframe');
		iframe.setAttribute('id', 'iframe_'+id);
		body.appendChild(iframe);
		
		//style iframe
		iframe.style.width = '100%';
		iframe.style.height = '100%';
		iframe.style.border = 0;
		iframe.style.scrolling='yes';
		iframe.style.zIndex=100;
		iframe.style.padding='20px';
		
		var iframeDocument = iframe.contentWindow.document;
		this.editor=iframe;
		//create iframe page content
		var iframeContent;
		iframeContent  = '<html xmlns:v="urn:schemas-microsoft-com:vml">\n';
		iframeContent += '<head>\n';
		if(_IE)
		{
			iframeContent += '<style> v\\:* { BEHAVIOR: url(#default#VML) }</style>'
			iframeContent += '<style>html,body{background: #ffffff;margin:0px;padding:0px;font-size:12px;\
					overflow:auto;scrollbar-face-color:#ffffff;scrollbar-highlight-color:#c1c1bb;scrollbar-shadow-color:#c1c1bb;\
					scrollbar-3dlight-color:#ebebe4;scrollbar-arrow-color:#cacab7;scrollbar-track-color:#f4f4f0;\
					scrollbar-darkshadow-color:#ebebe4;word-wrap: break-word;font-family: "Courier New", Courier, "宋体";\
					border:0px;}\ntd{border:1px dotted #CCCCCC;}\n</style>\n';
		}
		else
		{
			iframeContent += '<style>html,body{background: #ffffff;margin:0px;padding:0px;font-size:12px;\
					overflow:auto;word-wrap: break-word;font-family: "Courier New", Courier, "宋体";\
					border:0px;}\ntd{border:1px dotted #CCCCCC;}\n</style>\n';
		}
		iframeContent += '</head>\n';
		iframeContent += '<body class="Editor_TextArea">\n';
		iframeContent += content;
		iframeContent += '</body>\n';
		iframeContent += '</html>';

		iframeDocument.open();
		iframeDocument.write(iframeContent);
		iframeDocument.close();

		setTimeout(this.initIframe.bind(this), 300);
		
		Event.observe(iframeDocument, 'mouseup', function(){
			com.trs.editor.Editor.editor.contentWindow.focus();
			var selectedRange=iframeDocument.selection;
			if(selectedRange)
			{
				var hideDiv=com.trs.editor.Editor.getIframe();
				if(hideDiv)
				{
					hideDiv.setAttribute('hold',true);;
					hideDiv.range=selectedRange.createRange();
					hideDiv.type=selectedRange.type;
					if($log().isDebugEnabled())
					{
						$log().debug("hideDiv.range="+hideDiv.range);
						$log().debug("hideDiv.range.text="+hideDiv.range.text);
						$log().debug("hideDiv.type="+hideDiv.type);
					}
				}
			}
		},false);

		// ge gfc add @ 2006-8-15 13:54
		this.isAbstractExtracted = false;

		/*Event.observe(iframeDocument, 'keyup', function(){
			com.trs.editor.Editor.editor.contentWindow.focus();
			var selectedRange=iframeDocument.selection;
			if(selectedRange)
			{
				var hideDiv=com.trs.editor.Editor.getIframe();
				if(hideDiv)
				{
					hideDiv.range = selectedRange.createRange();
					hideDiv.type=selectedRange.type;
					if($log().isDebugEnabled())
					{
						$log().debug("hideDiv.range="+hideDiv.range);
						$log().debug("hideDiv.range.text="+hideDiv.range.text);
						$log().debug("hideDiv.type="+hideDiv.type);
					}
				}
			}
		},false);*/
		//unload event so we don't loose the data
		Event.observe(window, 'unload', function(){
			try{
				//ge gfc comment @ 2006-8-22 16:30
				//com.trs.util.CommonHelper.copy(iframeDocument.body.innerHTML);

				$destroy(com.trs.editor.Config);
				$destroy(com.trs.editor.ImgUploader);
				$destroy(com.trs.editor.ArticleSubmiter);
				delete com.trs.editor.Config;
				delete com.trs.editor.ImgUploader;
				delete com.trs.editor.ArticleSubmiter;
				com.trs.editor.Config = null;
				com.trs.editor.ImgUploader = null;			
				com.trs.editor.ArticleSubmiter = null;
				
				$destroy(iframeDocument);
				delete iframeDocument;
				iframeDocument = null;

				$destroy(this);
			}catch(er){}
		}.bind(this),false);
		/*Event.observe(iframe, 'unload', function(){
		}.bind(iframe),false);*/
	},
	initIframe:function(){
		var iframeDocument = this.editor.contentWindow.document;
		//Event.observe(iframeDocument, 'keypress', this.editorEvents.bind(this),true);
		var caller=this;
		Event.observe(iframeDocument, 'keydown', function(evt){caller.editorEvents.call(caller,evt)},true);
		Event.observe(iframeDocument, 'keyup', function(evt){caller.editorEvents.call(caller,evt)},true);
		Event.observe(iframeDocument, 'mousedown', this.editorEvents.bind(this),true);
		if(_IE)
		{
			iframeDocument.body.contentEditable = true;
		}
		else
		{
			iframeDocument.body.style.height=parseInt(com.trs.editor.Editor.editor.style.height);
			iframeDocument.designMode = 'on';
		}
	},
	getIframe:function(){
		for(var i=0;i<com.trs.editor.Config.iframes.length;i++)
		{
			if(com.trs.editor.Config.iframes[i].style.display!='none')return com.trs.editor.Config.iframes[i];
		}
		return false;
	},
	hideIframes:function(){
		$log().debug('hideIframes');
		for(var i=0;i<com.trs.editor.Config.iframes.length;i++)
		{
			com.trs.editor.Config.iframes[i].style.display='none';
			com.trs.editor.Config.iframes[i].setAttribute('hold',false);
		}
	},
	createToolBar:function(id){
		var html='<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
		' <tr><td id=toolbar_left_'+id+'>';
		html+='</td><td class=toolbar_right id=toolbar_right_'+id+'></td></tr></table>';
		this.toolbar=$('toolbar_'+id);
		this.toolbar.innerHTML=html;
		com.trs.editor.Config.toolbar($('toolbar_left_'+id));
	},
	replaceToolBar:function(){
		var div=this.toolbar.getElementsByTagName('td')[0];
		var childs=div.childNodes;
		for(var i=childs.length-1;i>=0;i--)
		{
			var tmp=div.removeChild(childs[i]);
			delete tmp;
		}
		com.trs.editor.Config.toolbar(div);
	},
	HTMLView:function(){
		var editor = this.editor;
		//WYSIWYG view
		if (com.trs.editor.Config.htmlOn == true){
			var div=document.createElement("DIV");
			div.innerHTML=this.editor.contentWindow.document.body.innerHTML;
			var html = div.innerText;
			var len1=html.length;
			html=html.replace(/<\s*script[^>]*>.*<\s*\/script[^>]*>/ig,"").replace(/<\s*script[^>]*>/ig,"").replace(/<\s*\/script[^>]*>/ig,"");
			var len2=html.length;
			if(len2<len1)$errorMsg('&lt;SCRIPT&gt;&lt;/SCRIPT&gt;部分被删除.<br>原因:<font color=red>不能编辑含有SCRIPT的HTML代码</font>','',3);
			for(;editor.contentWindow.document.body.childNodes.length>0;){
				editor.contentWindow.document.body.removeChild(editor.contentWindow.document.body.childNodes[0]);
			}
			editor.contentWindow.document.body.innerHTML = html;
			this.toolbar.style.display='';
	//		this.toolbar.style.visibility='visible';
			com.trs.editor.Config.htmlOn = false;
			delete div;
		//HTML view
		}else{
			if(document.all)
			{
				var html = editor.contentWindow.document.body.innerHTML;
				for(;editor.contentWindow.document.body.childNodes.length>0;){
					editor.contentWindow.document.body.removeChild(editor.contentWindow.document.body.childNodes[0]);
				}
				editor.contentWindow.document.body.innerText = html;
			}
			else
			{
				var html = document.createTextNode(editor.contentWindow.document.body.innerHTML);
				for(;editor.contentWindow.document.body.childNodes.length>0;){
					editor.contentWindow.document.body.removeChild(editor.contentWindow.document.body.childNodes[0]);
				}
				editor.contentWindow.document.body.innerHTML = '';
				editor.contentWindow.document.body.appendChild(html);
			}
			this.toolbar.style.display='none';
	// 		this.toolbar.style.visibility='hidden';
			com.trs.editor.Config.htmlOn = true;
		}
		editor.contentWindow.focus();
	},
	editorEvents:function(evt){
		var keyCode = evt.keyCode ? evt.keyCode : evt.charCode;
		var keyCodeChar = String.fromCharCode(keyCode).toLowerCase();

		var editor = this.editor;
		//run if enter key is pressed
		if(document.all)
		{
			if (evt.type=='keypress' && keyCode==13){
				var selectedRange = editor.contentWindow.document.selection.createRange();
				var parentElement = selectedRange.parentElement();
				var tagName = parentElement.tagName;
		
				while((/^(a|abbr|acronym|b|bdo|big|cite|code|dfn|em|font|i|kbd|label|q|s|samp|select|small|span|strike|strong|sub|sup|textarea|tt|u|var)$/i.test(tagName)) && (tagName!='HTML')){
					parentElement = parentElement.parentElement;
					tagName = parentElement.tagName;
				}
		
				//Insert <div> instead of <p>
				if (parentElement.tagName == 'P'||parentElement.tagName=='BODY'||parentElement.tagName=='HTML'||parentElement.tagName=='TD'||parentElement.tagName=='THEAD'||parentElement.tagName=='TFOOT'){
					selectedRange.pasteHTML('<div width=100%></div>');
					selectedRange.select();
					return false;
				}
			}/*
			if(evt.type=='keydown' && keyCode==13){
				evt.returnValue=false;
				evt.cancelBubble=true;
			}
			if (evt.type=='keyup' && keyCode==13){
				evt.returnValue=false;
				evt.cancelBubble=true;

				var selectedRange = editor.contentWindow.document.selection.createRange();
				var parentElement = selectedRange.parentElement();
				selectedRange.pasteHTML('<br>');
				selectedRange.select();				
			}*/
		}
		else
		{
		//Keyboard shortcuts
		//TODO 测试在FF下是否可以正常运行
			if (evt.type=='keypress' && evt.ctrlKey){
				var kbShortcut;
				switch (keyCodeChar){
					case 'b': kbShortcut = 'bold'; break;
					case 'i': kbShortcut = 'italic'; break;
					case 'u': kbShortcut = 'underline'; break;
					case 's': kbShortcut = 'strikethrough'; break;
				}
				if (kbShortcut){
					com.trs.editor.Command.Basic(kbShortcut);
					evt.preventDefault();
					evt.stopPropagation();
				}
			}
		}
		return true;
	},
	getHtml:function(){
		var html=null;
		if (com.trs.editor.Config.htmlOn == true)
		{
			var div=document.createElement("DIV");
			div.innerHTML=this.editor.contentWindow.document.body.innerHTML;
			html=div.innerText;
			delete div;
		}
		else
		{
			html=this.editor.contentWindow.document.body.innerHTML;
		}
		html=html.replace(/<\s*script[^>]*>.*<\s*\/script[^>]*>/ig,"").replace(/<\s*script[^>]*>/ig,"").replace(/<\s*\/script[^>]*>/ig,"");
		return html;
	},
	getText:function(){
		var div=document.createElement("DIV");
		div.innerHTML=this.editor.contentWindow.document.body.innerHTML;
		var html=null;
		var text=null;
		if (com.trs.editor.Config.htmlOn == true)
		{
			html=div.innerText;
		}
		else
		{
			html=div.innerHTML;
		}
		html=html.replace(/<\s*script[^>]*>.*<\s*\/script[^>]*>/ig,"").replace(/<\s*script[^>]*>/ig,"").replace(/<\s*\/script[^>]*>/ig,"");
		div.innerHTML = html;
		text=div.innerText;
		delete div;
		return text;
	},
	getAbstract:function(n){
		var result = null;

		n=(n)?parseInt(n)-1:-1;//0 - n-1的字符长度为n.
		var html=null;
		if (com.trs.editor.Config.htmlOn == true)
		{
			var div=document.createElement("DIV");
			div.innerHTML=this.editor.contentWindow.document.body.innerHTML;
			html=div.innerText;
			delete div;
		}else
		{
			html=this.editor.contentWindow.document.body.innerHTML;
		}
		if (html.legnth <= n || html.stripTags().getLength() <= n*2){
			result = html;
		}else{
			html=html.replace(/<\s*script[^>]*>.*<\s*\/script[^>]*>/ig,"").replace(/<\s*script[^>]*>/ig,"").replace(/<\s*\/script[^>]*>/ig,"");
			if(n <= 0){ 
				result = html;
			}
			else {
				//匹配所有的HTML Tag
				var pattern='<[^>]*>';
				var reg = new RegExp(pattern,'ig');
				var lastIndex=n;
				var arr;
				//循环直到第n个字符在当前匹配式之间或者之前时跳出
				while ((arr = reg.exec(html)) != null)
				{
					//当下一个匹配式<*>(表示为Tag)的起始位置超过n，则表示第n个字符处在当前匹配式之前
					if(arr.index>=n)break;
					//此时两种情况，一种是第n个字符在当前匹配式之间,另一种是当前匹配式在第n个字符之前
					lastIndex=arr.lastIndex;
					//当下一个匹配式<*>(表示为Tag)的结束位置超过n，则表示第n个字符处在当前匹配式之间
					if(lastIndex>=n)break;
				}
				//为了不截断第n个字符附近的Tag，需要根据情况重新指定截断的位置
				//第n个字符在循环中跳出时的匹配式之间的时候，需要将n指定到该匹配式的结束处，以免截断
				if(lastIndex>n)n=lastIndex;

				// ge gfc add @ 2006-8-15 13:54
				this.isAbstractExtracted = true;
				var result = html.substring(0, n);
			}
		}

		var divAbstract  = null;
		for (var i = 0; i < 4; i++){
			divAbstract = document.createElement('DIV');
			if(divAbstract) {
				divAbstract.innerHTML = result;
				result = divAbstract.innerHTML;
				delete divAbstract;
				divAbstract = null;
			}
		}
		return result;
		
	}
};
ClassName(com.trs.editor.Editor,'editor.Editor');

function $recursionRegular(n)
{
	if(n==1)
	{
		return '[^<]*((<\s*\\1[^>]*' + '.*'                   + '</\s*\\1[^>]*>)|(<(?!/?\s*\\1)[^>]*>[^<]*))*[^<]*';
	}
	else
	{
		return '[^<]*((<\s*\\1[^>]*' + $recursionRegular(n-1) + '</\s*\\1[^>]*>)|(<(?!/?\s*\\1)[^>]*>[^<]*))*[^<]*';
	}
}