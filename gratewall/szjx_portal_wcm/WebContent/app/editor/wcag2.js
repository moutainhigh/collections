if(!window.myAcExt){
	var myAcExt = {version : '2.2'};
}
function $(el) {
	if (typeof el == 'string')
		el = document.getElementById(el) || document.getElementsByName(el)[0];
	return el;
}
myAcExt.apply = function(d, s) {
	if(!d || !s)return d;
	for (p in s)d[p] = s[p];
	return d;
}
Array.isArray = function(arr){
	return arr!=null && typeof arr=='object' && arr.length!=null && arr.splice;
}
String.scriptFragment = '(?:<script.*?>)((\n|\r|.)+?)(?:<\/script>)';
myAcExt.apply(String.prototype, {
	trim : function(){
		return this.replace(/^\s*/, "").replace(/\s*$/, "");
	},
	startsWith : function(s){
		return this.indexOf(s)==0;
	},
	stripScripts : function(){
		return this.replace(new RegExp(String.scriptFragment, 'img'), '');
	}
});
var ua = navigator.userAgent.toLowerCase();
myAcExt.isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
function easyOuterHTML0(ele){
	var _emptyTags = {
		"IMG" : true,
		"BR" : true,
		"INPUT" : true,
		"META" : true,
		"LINK" : true,
		"PARAM" : true,
		"HR" : true
	};
	var _moreTags = {
		"A" : true,
		"OBJECT" : true,
		"EMBED" : true,
		"UL" : true,
		"OL" : true
	};
	try{
		var outerHTMl = ele.outerHTML;		
	}catch(err){
		//TODO
	}
	if(outerHTMl!=null){
		var html = ele.outerHTML;
		if(_moreTags[ele.tagName])return html;
		var idx = html.indexOf('>');
		return ele.outerHTML.substring(0, idx+1).trim();
	}
	var str = "<" + ele.tagName;
	try{
		var attrs = ele.attributes;
		for (var i = 0; i < attrs.length; i++)
			str += " " + attrs[i].name + "=\"" + attrs[i].value + "\"";		
	}catch(err){
		//TODO
	}
	if (_emptyTags[ele.tagName] || !_moreTags[ele.tagName])
		return str + ">";
	return str + ">" + ele.innerHTML + "</" + ele.tagName + ">";
}
function easyOuterHTML(ele){
	var html = easyOuterHTML0(ele);
	return html.trim();
}
var WCAG2Validator = {
	wellForm: {
		type : FCKLang.DlgWcagW3cStd || 'W3C规范',
		warning : FCKLang.DlgWcagFormatErr || 'HTML格式不规范',
		fn : function(content){
			return true;
		}
	},
	doctype: {
		type : FCKLang.DlgWcagW3cStd || 'W3C规范',
		warning : FCKLang.DlgWcagNoDoctype || '页面没有指定DOCTYPE',
		fn : function(content){
			return content.trim().indexOf("<!DOCTYPE ")!=-1;
		}
	},
	htmlLang: {
		type : FCKLang.DlgWcagMultiLingual || '多语言',
		warning : FCKLang.DlgWcagAttrNull || 'html节点上没有指定lang/xml:lang属性或属性值为空',
		fn : function(content, doc){
			return !!(doc.documentElement.getAttribute("lang") || doc.documentElement.getAttribute("xml:lang"));
		}
	},
	htmlCharset: {
		type : FCKLang.DlgWcagW3cStd || 'W3C规范',
		warning : 'The character encoding was not specified. ',
		fn : function(content, doc){
			var arr = doc.getElementsByTagName("META");
			for(var i=0;i<arr.length;i++){
				var t1 = arr[i].getAttribute('http-equiv', 2) || '';
				var t2 = arr[i].getAttribute('content', 2) || '';
				if(t1.toLowerCase()=='content-type'
					&& t2.toLowerCase().indexOf('charset')!=-1)
					return true;
			}
			return false;
		}
	},
	htmlTitle: {
		type : FCKLang.DlgWcagTitle || '标题',
		warning : FCKLang.DlgWcagNoTitle || '页面没有包含Title节点或者Title无内容',
		fn : function(content, doc, body){
			var elTitle = doc.getElementsByTagName("Head")[0].getElementsByTagName("title")[0];
			return elTitle && elTitle.innerHTML.trim()!='';
		}
	},
	htmlH1: {
		type : FCKLang.DlgWcagTitle || '标题',
		warning : {
			'0' : (FCKLang.DlgWcagNoH1WithTitle || "页面正文中没有和Title相对应的H1节点"),
			'-1' : (FCKLang.DlgWcagH1Gt2 || "页面正文中的H1节点数超过2个,不建议"),
			'-2' : "The text content of each h1 element should match all or part of the title content. "
		},
		fn : function(content, doc, body){
			var h1s= body.getElementsByTagName("H1");
			if(h1s.length==0)return 0;
			if(h1s.length>2)return -1;
			if(doc.title.indexOf((h1s[0].innerText || h1s[0].textContent || '').trim())==-1)return -2;
			return true;
		}
	},
	htmlH1_H6: {
		type : FCKLang.DlgWcagTitleOrCat || '标题/类目',
		warning : FCKLang.DlgWcagFrameErr || '不是使用H1-H6来定义页面的框架结构',
		fn : function(content, doc, body){
			var cnt = 0;
			for(var i=1;i<=6;i++){
				cnt += body.getElementsByTagName("H"+i);
			}
			if(cnt==0)return false;
			return true;
		}
	},
	altAttr: {
		type : FCKLang.DlgWcagPic || '图片',
		warning : FCKLang.DlgWcagPicAttNull || 'IMG,AREA,INPUT(type=image)的节点没有指定alt属性或属性值为空',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("IMG");
			var rst = [], img;
			for(var i=0;i<arr.length;i++){
				img = arr[i];
				if((img.className || '').indexOf('FCK_')!=-1)continue;
				if(img.getAttribute('_fckflash'))continue;
				if(img.getAttribute('alt'))continue;
				var link = img.parentNode;
				if(link && link.tagName=='A'){//in link
					if((link.innerText || link.textContent || '').trim()!='')continue;
					if(link.getAttribute('title'))continue;
					rst.push(img);
				}else{
					rst.push(img);
				}
				/*
				var lstDis = img.style.display;
				img.style.display = '';
				if(img.offsetWidth>=20 || img.offsetHeight>=20){
					rst.push(img);
				}
				img.style.display = lstDis;
				*/
			}
			arr = body.getElementsByTagName("AREA");
			for(var i=0;i<arr.length;i++){
				img = arr[i];
				if(img.getAttribute('alt'))continue;
				rst.push(img);
			}
			arr = body.getElementsByTagName("INPUT");
			for(var i=0;i<arr.length;i++){
				var ele = arr[i];
				if(ele.type!='image' || ele.getAttribute('alt'))continue;
				rst.push(ele);
			}
			return rst;
		}
	},
	biTag: {
		type : FCKLang.DlgWcagW3cStd || 'W3C规范',
		warning : FCKLang.DlgWcagBOrIErr || 'B,I节点为非WCAG2的规范节点,建议替换为strong,em',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("B"), rst = [];
			for(var i=0;i<arr.length;i++){
				rst.push(arr[i]);
			}
			arr = body.getElementsByTagName("I");
			for(var i=0;i<arr.length;i++){
				rst.push(arr[i]);
			}
			return rst;
		}
	},
	dataTable: {
		type : FCKLang.DlgWcagDataForm || '数据表格',
		warning : FCKLang.DlgWcagNoSummary || '描述数据的表格中没有指定summary属性且不包含Caption节点',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("TABLE");
			var rst = [], tb;
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(tb.getAttribute('summary'))continue;
				if(tb.getElementsByTagName('Caption').length>0)continue;
				rst.push(tb);
			}
			return rst;
		}
	},
	frameTitle: {
		type : 'Frames',
		warning : FCKLang.DlgWcagFrameNoTitle || 'Frame/Iframe节点没有指定title属性',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("FRAME");
			var rst = [], tb;
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(tb.getAttribute('title'))continue;
				rst.push(tb);
			}
			arr = body.getElementsByTagName("IFRAME");
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(tb.getAttribute('title'))continue;
				rst.push(tb);
			}
			return rst;
		}
	},
	objectBody: {
		type : 'Objects',
		warning : FCKLang.DlgWcagNoContent || 'Object/Applet节点中不包含内容',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("Object");
			var rst = [], tb;
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(((myAcExt.isIE?tb.innerText:tb.textContent)||'').trim()!='')continue;
				rst.push(tb);
			}
			arr = body.getElementsByTagName("Applet");
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(((myAcExt.isIE?tb.innerText:tb.textContent)||'').trim()!='')continue;
				rst.push(tb);
			}
			return rst;
		}
	},
	embed: {
		type : 'Objects',
		warning : FCKLang.DlgWcagEmbedNoAlt || 'Embed节点没有指定alt属性,以及Embed节点中没有包含NoEmbed节点且下一个节点不是NoEmbed节点',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("Embed");
			var rst = [], tb;
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(tb.getAttribute('alt')|| tb.getAttribute('title'))continue;
				var noembed = tb.getElementsByTagName('NoEmbed')[0] || tb.nextSibling;
				if(noembed && noembed.tagName=='NOEMBED' && (noembed.innerText || noembed.textContent))continue;
				rst.push(tb);
			}
			arr = body.getElementsByTagName("IMG");
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(!tb.getAttribute('_fckflash'))continue;
				var tb2 = FCK.GetRealElement( tb ) ;
				if(tb2.getAttribute('alt')|| tb.getAttribute('title'))continue;
				var noembed = tb2.getElementsByTagName('NoEmbed')[0] || tb2.nextSibling;
				if(noembed && noembed.tagName=='NOEMBED' && (noembed.innerText || noembed.textContent))continue;
				tb.actualEle = tb2;
				rst.push(tb);
			}
			return rst;
		}
	},
	formSubmit: {
		type : FCKLang.DlgWcagForm || '表单',
		warning : FCKLang.DlgWcagFormNoSubmit || 'Form表单中没有表示提交的按钮(input type=submit/image 或button type=submit)',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("Form");
			var rst = [], tb, eles;
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				eles = tb.getElementsByTagName("Input");
				var v = false;
				for(var j=0;j<eles.length;j++){
					if(eles[j].type=='submit' || eles[j].type=='image'){
						v = true;
						break;
					}
				}
				eles = tb.getElementsByTagName("Button");
				for(var j=0;j<eles.length;j++){
					if(eles[j].type=='submit'){
						v = true;
						break;
					}
				}
				if(!v)rst.push(tb);
			}
			return rst;
		}
	},
	formElements: {
		type : FCKLang.DlgWcagFormControl || '表单控件',
		warning : [FCKLang.DlgWcagReasons || '可能原因:',
				FCKLang.DlgWcagReason1 || '1,Input(type=image)控件没有指定alt/title属性;',
				FCKLang.DlgWcagReason2 || '2,Input(type=button/submit/reset)控件没有指定value属性;',
				FCKLang.DlgWcagReason3 || '3,Input(type=text/radio/checkbox/password)/Select/Textarea控件没有指定title属性且没有相关的label节点',
				FCKLang.DlgWcagReason4 || '4,Button控件没有指定文本内容且没有title属性'/*,
				'FieldSet控件没有包含legend节点'*/
			].join('\n'),
		fn : function(content, doc, body){
			var rst = [], tb = body, eles, labels, hsLabels = {};
			labels = tb.getElementsByTagName("Label");
			for(var j=0;j<labels.length;j++){
				hsLabels[labels[j].getAttribute('for')] = true;
			}
			eles = tb.getElementsByTagName("Input");
			for(var j=0;j<eles.length;j++){
				if(hsLabels[eles[j].id])continue;
				if(eles[j].type=='submit' || eles[j].type=='button' || eles[j].type=='button'){
					if(eles[j].getAttribute('value'))continue;
				}
				else if(eles[j].type=='image'){
					if(eles[j].getAttribute('alt') || eles[j].getAttribute('title'))continue;
				}
				else if(eles[j].getAttribute('title'))continue;
				if(eles[j].type=='hidden')continue;
				rst.push(eles[j]);
			}
			eles = tb.getElementsByTagName("SELECT");
			for(var j=0;j<eles.length;j++){
				if(hsLabels[eles[j].id])continue;
				if(eles[j].getAttribute('title'))continue;
				rst.push(eles[j]);
			}
			eles = tb.getElementsByTagName("TEXTAREA");
			for(var j=0;j<eles.length;j++){
				if(hsLabels[eles[j].id])continue;
				if(eles[j].getAttribute('title'))continue;
				rst.push(eles[j]);
			}
			eles = tb.getElementsByTagName("BUTTON");
			for(var j=0;j<eles.length;j++){
				if(eles[j].innerHTML.trim()!='')continue;
				if(eles[j].getAttribute('title'))continue;
				rst.push(eles[j]);
			}
			/*
			eles = tb.getElementsByTagName("FieldSet");
			for(var j=0;j<eles.length;j++){
				if(eles[j].getElementsByTagName('Legend').length>0)continue;
				rst.push(eles[j]);
			}*/
			return rst;
		}
	},
	lists: {
		type : FCKLang.DlgWcagMenuOrNav || '菜单/导航条',
		warning : FCKLang.DlgWcagMenuOrNavAttErr || '每个表示菜单/导航条的OL/UL控件应该对应着一个H2-H6的标签',
		fn : function(content, doc, body){
			var tmpArr = [], rst = [], arr = body.getElementsByTagName("ul");
			for(var i=0;i<arr.length;i++)
				tmpArr.push(arr[i]);
			arr = body.getElementsByTagName("ol");
			for(var i=0;i<arr.length;i++)
				tmpArr.push(arr[i]);
			for(var i=0;i<tmpArr.length;i++){
				var listEl = tmpArr[i], arrLinks = listEl.getElementsByTagName("A");
				var navi = listEl.getAttribute('isNavi')=='1' || arrLinks.length==listEl.getElementsByTagName("LI").length;
				if(!navi)continue;
				var tmpEl = listEl.previousSibling;
				while(tmpEl!=null){
					if(tmpEl.nodeType == 1){					
						if(tmpEl.tagName.match(/^H[2-6]$/))break;
						if(tmpEl.tagName=='UL' || tmpEl.tagName=='OL'){
							tmpEl = null;
							break;
						}
					}
					tmpEl = tmpEl.previousSibling;
				}
				if(tmpEl!=null)continue;
				rst.push(listEl);
			}
			return rst;
		}
	},
	onmouseover: {
		type : 'Events',
		warning : FCKLang.DlgWcagOnmouseoverWithoutOnfocus || '控件指定了onmouseover事件的行为,但是没有与之相同行为的onfocus事件相对应',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName('*'), rst = [];
			for(var i=0;i<arr.length;i++){
				if(!arr[i].getAttribute('onmouseover'))continue;
				if(arr[i].getAttribute('onfocus'))continue;
				rst.push(arr[i]);
			}
			return rst;
		}
	},
	onmouseout: {
		type : 'Events',
		warning : FCKLang.DlgWcagOnmouseoutWithoutOnfocus || '控件指定了onmouseout事件的行为,但是没有与之相同行为的onblur事件相对应',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName('*'), rst = [];
			for(var i=0;i<arr.length;i++){
				if(!arr[i].getAttribute('onmouseout'))continue;
				if(arr[i].getAttribute('onblur'))continue;
				rst.push(arr[i]);
			}
			return rst;
		}
	},
	onclick: {
		type : 'Events',
		warning : FCKLang.DlgWcagOnclickNotFocus || '控件指定了onclick事件的行为,但是该控件不可选中(focus)',
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName('*'), rst = [];
			for(var i=0;i<arr.length;i++){
				if(!arr[i].getAttribute('onclick') && !arr[i].getAttribute('onfocus') && !arr[i].getAttribute('onblur'))continue;
				if(arr[i].tagName=='A' || arr[i].tagName=='BUTTON' || arr[i].tagName=='INPUT' || arr[i].tabIndex>0)continue;
				rst.push(arr[i]);
			}
			return rst;
		}
	}
};

function WCAG2Helper(){}
WCAG2Helper.prototype = {
	_prepare : function(content){
		content = content.stripScripts();
		content = content.replace(/ (onresize|onload|onunload)=/ig, ' $1_1');
		return content;
	},
	_newDoc : function(){
		var iframe = document.createElement('Iframe');
		iframe.style.display = '';
		iframe.style.height = '1px';
		iframe.style.width = '1px';
		iframe.style.position = 'absolute';
		iframe.style.top = '-100px';
		document.body.appendChild(iframe);
		return iframe.contentWindow.document;
	},
	validDoc : function(doc){
		var valids = {
			lists : true,
			altAttr : true,
			dataTable : false,
			biTag : false,
			frameTitle : true, 
			objectBody : true, 
			embed : true, 
			formSubmit : true,
			formElements : true,
			onmouseover : true,
			onmouseout : true,
			onclick : true
		};
		var body = doc.body;
		return this.getAlertions(body.innerHTML, doc, body, valids);
	},
	validHtmlContent : function(content){
		var valids = {
			lists : true,
			altAttr : true,
			dataTable : true,
			biTag : false,
			frameTitle : true, 
			objectBody : true, 
			embed : true, 
			formSubmit : true,
			formElements : true,
			onmouseover : true,
			onmouseout : true,
			onclick : true
		};
		content = this._prepare(content);
		var doc = this._newDoc();
		doc.write('<!doctype html public "-//w3c//dtd html 4.0 transitional//en">');
		doc.write('<html><head><title> new document </title></head><body>');
		doc.write(content);
		doc.write('</body></html>');
		doc.close();
		var body = doc.body;
		return this.getAlertions(content, doc, body, valids);
	},
	validTemplate : function(content){
		var valids = {
			doctype : true,
			htmlLang : true,
			htmlCharset : true,
			htmlTitle : true,
			htmlH1 : true,
			htmlH1_H6 : true,
			lists : true,
			altAttr : true,
			dataTable : false,
			biTag : false,
			frameTitle : true, 
			objectBody : true, 
			embed : true, 
			formSubmit : true,
			formElements : true,
			onmouseover : true,
			onmouseout : true,
			onclick : true
		};
		content = this._prepare(content);
		var doc = this._newDoc();
		doc.write(content);
		doc.close();
		var body = doc.body;
		return this.getAlertions(content, doc, body, valids);
	},
	getAlertions : function(content, doc, body, valids){
		var alertions = [];
		for(var name in valids){
			if(!valids[name])continue;
			var item = WCAG2Validator[name];
			var rst = item.fn(content, doc, body);
			if(rst===true)continue;
			if(Array.isArray(rst)){
				if(rst.length==0)continue;
				var tmp = [];
				for(var i=0;i<rst.length;i++){
					if(rst[i].id && rst[i].id.match(/accessibility_\d*$/g))rst[i].id = '';
					if(!rst[i].id)rst[i].id = getAccessibilityId();
					tmp.push(easyOuterHTML(rst[i].actualEle || rst[i]));
					rst[i].actualEle = null;
				}
				alertions.push({
					klass : item.type,
					warning : item.warning,
					eles : rst,
					htmls : tmp
				});
				continue;
			}
			if(rst===false)
				alertions.push({
					klass : item.type,
					warning : item.warning
				});
			else
				alertions.push({
					klass : item.type,
					warning : item.warning[rst]
				});
		}
		if(alertions.length==0)return true;
		return alertions;
	}
}
var wcmWCAG2Helper = new WCAG2Helper();
function validInHtmlContent2(s){
	var alertions = wcmWCAG2Helper.validHtmlContent(s);
	if(alertions==true)return;
	var rst = [];
	for(var i=0;i<alertions.length;i++){
		if(alertions[i].htmls){
			rst.push(alertions[i].klass + ":" + alertions[i].warning + '\n\t' + alertions[i].htmls.join('\n\t'));
		}
		else rst.push(alertions[i].klass + ":" + alertions[i].warning);
	}
	for(var i=0;i<rst.length;i++){
		rst[i] = (i+1)+','+rst[i];
	}
	alert(rst.join('\n'));
}
function validInTemplate2(s){
	var alertions = wcmWCAG2Helper.validTemplate(s);
	if(alertions==true)return;
	var rst = [];
	for(var i=0;i<alertions.length;i++){
		if(alertions[i].htmls){
			rst.push(alertions[i].klass + ":" + alertions[i].warning + '\n\t' + alertions[i].htmls.join('\n\t'));
		}
		else rst.push(alertions[i].klass + ":" + alertions[i].warning);
	}
	for(var i=0;i<rst.length;i++){
		rst[i] = (i+1)+','+rst[i];
	}
	alert(rst.join('\n'));
}
function getAccessibilityId(){
	if(!window.__AccessibilityId){
		window.__AccessibilityId = 1;
	}
	return 'accessibility_' + window.__AccessibilityId++;
}
function focusAccessibility(id){
	var ele = FCK.EditorDocument.getElementById(id);
	FCK.Selection.SelectNode(ele);
	ele.scrollIntoView(true);
}
var m_CmdMapping = {
	TABLE : 'TableProp',
	IMG : 'Image',
	UL : 'BulletedList',
	OL : 'NumberedList',
	A : 'Link',
	FLASH : 'Flash'
};
function renderAccessibility(id){
	var ele = FCK.EditorDocument.getElementById(id);
	FCK.Selection.SelectNode(ele);
	ele.scrollIntoView(true);
	var tagName = ele.tagName;
	if(tagName=='IMG' && ele.getAttribute('_fckflash'))tagName = 'FLASH';
	var cmdName = m_CmdMapping[tagName];
	if(!cmdName){
		alert(FCKLang.DlgWcagDIY || '无法自动完成,请参考指导手动处理.');
		return;
	}
	FCK.Commands.GetCommand(cmdName).Execute() ;
	FCK.Commands.GetCommand('Accessibility').Execute() ;
}
function closeAccessibility(){
	var cnt = getAccessibility();
	cnt.style.display = 'none';
}
function doAccessibility(alertions){
	var cnt = getAccessibility();
	cnt.style.display = 'none';
	if(alertions==true){
		alert(FCKLang.DlgWcagResultOk || '无障碍检查结果:符合WCAG2规范.');
		return;
	}
	var html = [];
	html.push('<a class="accessibility_close" href="#" onclick="closeAccessibility();return false;">'+ (FCKLang.DlgWcagClose || '关闭') + '</a><div class="accessibility_title">' + (FCKLang.DlgWcagResult || '无障碍检查结果') + '</div>');
	html.push('<ul>');
	for(var i=0;i<alertions.length;i++){
		var id = getAccessibilityId();
		html.push('<li>');
		html.push('<span class="accessibility_class">', alertions[i].klass, '</span>');
		html.push('<span class="accessibility_warning">', alertions[i].warning, '</span>');
		html.push('<span class="accessibility_num">(',
			alertions[i].htmls ? alertions[i].htmls.length : 1, ')</span>');
		if(!alertions[i].htmls)continue;
		html.push('<a class="accessibility_detail" href="#" onclick="Element.toggle(\'', id, '\')">' + (FCKLang.DlgWcagDetail || '详细') + '</a>');
		html.push('<table border="1" bordercolor="gray" style="display:none;" id="', id, '">');
		for(var j=0;j<alertions[i].htmls.length;j++){
			var tmpHtml = alertions[i].htmls[j].trim();
			var tt = tmpHtml.replace(/</g, '&lt;').replace(/>/g, '&gt;');
			var eleId = alertions[i].eles[j].id;
			html.push('<tr>');
			html.push('<td width="60" align="center">',
				'<a href="#" onclick="renderAccessibility(\'', eleId, '\');return false;">' + (FCKLang.DlgWcagDo || '处理') + '</a></td>');
			if(tmpHtml.match(/^<img /ig)){
				html.push('<td style="height:50px;overflow:hidden;" onclick="focusAccessibility(\'', eleId, '\');return false;"  title="', tt.replace(/\"/g, '&#34;'), '">');
			}else{
				html.push('<td onclick="focusAccessibility(\'', eleId, '\');return false;"  title="', tt.replace(/\"/g, '&#34;'), '">');
			}
			tmpHtml = tmpHtml.replace(/<EMBED .*>/gi,'<img src="css/images/fck_flashlogo.gif" style="width:80px;height:80px;border: #a9a9a9 1px solid;">');
			html.push(tmpHtml);
			html.push('</td></tr>');
		}
		html.push('</table>');
	}
	html.push('</ul>');
	cnt.innerHTML = html.join('');
	cnt.style.display = '';
	//cnt.style.zIndex = 990;
}
function getAccessibility(){
	var cnt = $('accessibility_cnt');
	if(!cnt){
		cnt = document.createElement('DIV');
		cnt.id = 'accessibility_cnt';
		cnt.className = 'accessibility';
		cnt.style.display = 'none';
		document.body.appendChild(cnt);
	}
	return cnt;
}

//Accessibility
(function(){
	FCKLang.AccessibilityBtn = FCKLang.AccessibilityBtn || "无障碍检查";
	//AdInTrsCommand
	var AccessibilityCommand = function(){
	}
	AccessibilityCommand.prototype.Execute = function(){
		if(!window.wcmWCAG2Helper)return;
		var alertions = wcmWCAG2Helper.validDoc(FCK.EditorDocument);
		doAccessibility(alertions);
	}
	AccessibilityCommand.prototype.GetState = function(){
		return FCK_TRISTATE_OFF;
	}
	FCKCommands.RegisterCommand( 'Accessibility', new AccessibilityCommand()) ;
	var oAccessibilityItem = new FCKToolbarButton( 'Accessibility', FCKLang.AccessibilityBtn, 
		null, null, null, null, null ) ;
	FCKToolbarItems.RegisterItem( 'Accessibility', oAccessibilityItem ) ;
})();
