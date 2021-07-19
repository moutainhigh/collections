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
	if(ele.outerHTML!=null){
		var html = ele.outerHTML;
		if(_moreTags[ele.tagName])return html;
		var idx = html.indexOf('>');
		return ele.outerHTML.substring(0, idx+1).trim();
	}
	var attrs = ele.attributes;
	var str = "<" + ele.tagName;
	for (var i = 0; i < attrs.length; i++)
		str += " " + attrs[i].name + "=\"" + attrs[i].value + "\"";
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
		type : wcm.LANG.TEMPLATE_57||'W3C规范',
		warning : wcm.LANG.TEMPLATE_64||'HTML格式不规范',
		example : wcm.LANG.TEMPLATE_64||'HTML格式不规范',
		fn : function(content){
			return true;
		}
	},
	doctype: {
		type : wcm.LANG.TEMPLATE_57||'W3C规范',
		warning : wcm.LANG.TEMPLATE_65||'页面没有指定DOCTYPE',
		example : [
			wcm.LANG.TEMPLATE_66||'在页面的开始部分指定一个标准的文档类型声明，例如：<br>',
			'&nbsp;&nbsp;&lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN  http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;',
			'<br><a href="http://www.w3.org/QA/2002/04/valid-dtd-list.html" target="_blank">'+(wcm.LANG.TEMPLATE_67||'查看')+'</a>'+(wcm.LANG.TEMPLATE_68||'全部W3C推荐的文档类型声明.')
		].join('<br>'),
		fn : function(content){
			return content.trim().indexOf("<!DOCTYPE ")!=-1;
		}
	},
	htmlLang: {
		type : wcm.LANG.TEMPLATE_69||'多语言',
		warning : wcm.LANG.TEMPLATE_70||'html节点上没有指定lang/xml:lang属性或属性值为空',
		example : [
			wcm.LANG.TEMPLATE_71||'在HTML节点上添加lang属性和值，例如：<br>',
			'&nbsp;&nbsp;&lt;html <span style="color:blue">lang="cn"</span>&gt;'
		].join('<br>'),
		fn : function(content, doc){
			return !!(doc.documentElement.getAttribute("lang") || doc.documentElement.getAttribute("xml:lang"));
		}
	},
	htmlCharset: {
		type : wcm.LANG.TEMPLATE_57||'W3C规范',
		warning : wcm.LANG.TEMPLATE_72||'页面的Charset属性没有指定',
		example : [
			wcm.LANG.TEMPLATE_73||'在Head节点中添加描述charset的meta节点，例如：<br>',
			'&nbsp;&nbsp;&lt;META content="text/html; charset=UTF-8" http-equiv=Content-Type&gt;'
		].join('<br>'),
		fn : function(content, doc){
			var arr = doc.getElementsByTagName("META");
			for(var i=0;i<arr.length;i++){
				var t1 = arr[i].httpEquiv || '';
				var t2 = arr[i].getAttribute('content', 2) || '';
				if(t1.toLowerCase()=='content-type'
					&& t2.toLowerCase().indexOf('charset')!=-1)
					return true;
			}
			return false;
		}
	},
	htmlTitle: {
		type : wcm.LANG.TEMPLATE_74||'标题',
		warning : wcm.LANG.TEMPLATE_75||'页面没有包含Title节点或者Title无内容',
		example : [
			wcm.LANG.TEMPLATE_76||'在页面Head部分添加Title节点，例如：<br>',
			wcm.LANG.TEMPLATE_77||'&nbsp;&nbsp;&lt;TITLE&gt;描述这个页面的说明&lt;/TITLE&gt;'
		].join('<br>'),
		fn : function(content, doc, body){
			var elTitle = doc.getElementsByTagName("Head")[0].getElementsByTagName("title")[0];
			return elTitle && elTitle.innerHTML.trim()!='';
		}
	},
	htmlH1: {
		type : wcm.LANG.TEMPLATE_74||'标题',
		warning : {
			'0' : wcm.LANG.TEMPLATE_78||"页面正文中没有和Title相对应的H1节点",
			'-1' : wcm.LANG.TEMPLATE_79||"页面正文中的H1节点数超过2个,不建议",
			'-2' : wcm.LANG.TEMPLATE_80||"页面正文中的H1节点的内容应该完全匹配或包含于页面标题（Title）的内容"
		},
		example : {
				'0' : [
					wcm.LANG.TEMPLATE_81||'在页面Body部分添加隐藏的H1节点与Title对应，内容保持一致，例如：<br>',
					'&nbsp;&nbsp&lt;h1 style="display:none" class="nav_hide"&gt;'+(wcm.LANG.TEMPLATE_82||'描述这个页面的说明')+'&lt;/h1&gt;'
				].join('<br>'),
				'-1' : wcm.LANG.TEMPLATE_83||'将页面正文中的部分H1节点调整为H2-H6节点',
				'-2' : wcm.LANG.TEMPLATE_84||'调整H1节点/Title使得H1和Title的内容相等'
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
		type : wcm.LANG.TEMPLATE_85||'标题/类目',
		warning : wcm.LANG.TEMPLATE_86||'不是使用H1-H6来定义页面的框架结构',
		example : [
			wcm.LANG.TEMPLATE_87||'使用页面标签将页面划分为 标题, 段落, 列表, 引用等，例如：<br>',
			wcm.LANG.TEMPLATE_88||'&nbsp;&nbsp;使用 &lt;h1&gt; 标签来区分顶层头信息而不是简单的加大加粗这些文字，再使用CSS格式化这些标签的内容。'
		].join('<br>'),
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
		type : wcm.LANG.TEMPLATE_89||'图片',
		warning : {
			fail : wcm.LANG.TEMPLATE_90||'IMG,AREA,INPUT(type=image)的节点没有指定alt属性',
			warn : wcm.LANG.TEMPLATE_91||'IMG节点的alt属性为空'
		},
		example : {
			fail : [
				wcm.LANG.TEMPLATE_92||'为页面中表示内容的图片指定alt属性，例如：<br>',
				'&nbsp;&nbsp;&lt;IMG src="sqgk.jpg" <span style="color:blue">alt="'+(wcm.LANG.TEMPLATE_93||'图片说明')+'"</span>&gt;',
				wcm.LANG.TEMPLATE_94||'<br>为页面中不用于表示内容的图片指定alt属性，设置其说明为空，例如：<br>',
				'&nbsp;&nbsp;&lt;IMG src="dot3.jpg" width=9 height=9 <span style="color:blue">alt=""</span> OLDSRC="dot3.jpg" OLDID="172" RELATED="1"&gt;'
			].join('<br>'),
			warn : [
				wcm.LANG.TEMPLATE_95||'请确定当前alt属性为空的图片不用于表示内容，否则需要为其指定说明，例如：<br>',
				'&nbsp;&nbsp;&lt;IMG src="sqgk.jpg" <span style="color:blue">alt="'+(wcm.LANG.TEMPLATE_93||'图片说明')+'"</span>&gt;'
			].join('<br>')
		},
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("IMG");
			var rst = [], img, rstwarning = [];
			for(var i=0;i<arr.length;i++){
				img = arr[i];
				if(img.getAttribute('_fckflash'))continue;
				if(img.getAttribute('alt'))continue;
				if(!myAcExt.isIE && img.getAttribute('alt', 2)===''){
					rstwarning.push(img);
					continue;
				}
				if(myAcExt.isIE && img.outerHTML.indexOf(' alt')!=-1){
					rstwarning.push(img);
					continue;
				}
				var link = img.parentNode;
				if(link && link.tagName=='A'){//in link
					if((link.innerText || link.textContent || '').trim()!='')continue;
					if(link.getAttribute('title'))continue;
					rstwarning.push(img);
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
			return {
				fail : rst,
				warn : rstwarning
			};
		}
	},
	biTag: {
		type : wcm.LANG.TEMPLATE_57||'W3C规范',
		warning : wcm.LANG.TEMPLATE_96||'B,I节点为非WCAG2的规范节点,建议替换为strong,em',
		example : [
			wcm.LANG.TEMPLATE_97||'使用strong,em节点加粗/斜字体，避免使用B,I节点，例如：<br>',
			'&nbsp;&nbsp;<strong>'+(wcm.LANG.TEMPLATE_98||'加粗')+'</strong>(&lt;strong&gt;'+(wcm.LANG.TEMPLATE_98||'加粗')+'&lt;/strong&gt;)，<em>'+(wcm.LANG.TEMPLATE_99||'加斜')+'</em>(&lt;em&gt;'+(wcm.LANG.TEMPLATE_99||'加斜')+'&lt;/em&gt;)'
		].join('<br>'),
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
		type : wcm.LANG.TEMPLATE_100||'数据表格',
		warning : wcm.LANG.TEMPLATE_101||'描述数据的表格中没有指定summary属性且不包含Caption节点',
		example : [
			wcm.LANG.TEMPLATE_101||'描述数据的表格中没有指定summary属性且不包含Caption节点'
		].join('<br>'),
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
		warning : wcm.LANG.TEMPLATE_102||'Frame/Iframe节点没有指定title属性',
		example : [
			wcm.LANG.TEMPLATE_103||'为页面中的Iframe/Frame节点指定title属性，例如：<br>',
			'&nbsp;&nbsp;&lt;IFRAME height=100 src="" width=500 <span style="color:blue">title="'+(wcm.LANG.TEMPLATE_104||'页面说明')+'"</span>&gt;&lt;/IFRAME&gt;'
		].join('<br>'),
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
		warning : wcm.LANG.TEMPLATE_105||'Object/Applet节点中不包含内容',
		example : [
			wcm.LANG.TEMPLATE_106||'为页面中的Object/Applet节点添加内容，例如：<br>',
			'&nbsp;&nbsp;&lt;OBJECT codeBase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" classid=clsid:D27CDB6E-AE6D-11cf-96B8-444553540000 width="400" height="100"&gt;',
			'&nbsp;&nbsp;&nbsp;&nbsp;&lt;PARAM NAME="movie" VALUE="head.swf"&gt;&lt;PARAM NAME="quality" VALUE="high"&gt;',
			'&nbsp;&nbsp;&nbsp;&nbsp;&lt;embed src="head.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="400" height="100" alt="head.swf"&gt;&lt;/embed&gt;',
			'&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:blue">&lt;span class="nav_hide"&gt;head.swf&lt;/span&gt;</span>',
			'&nbsp;&nbsp;&lt;/OBJECT&gt;'
		].join('<br>'),
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("Object");
			var rst = [], tb;
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				var html = tb.altHtml||'';
				var html2 = html.replace(/<embed.*?<\/embed>/ig, '');
				if(((myAcExt.isIE?html2:tb.textContent)||'').trim()!='')continue;
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
		warning : wcm.LANG.TEMPLATE_107||'Embed节点没有指定alt属性,以及Embed节点中没有包含NoEmbed节点且下一个节点不是NoEmbed节点',
		example : [
			wcm.LANG.TEMPLATE_108||'为Embed节点指定alt属性，例如：<br>',
			'&nbsp;&nbsp;&lt;embed src="head.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="400" height="100" <span style="color:blue">alt="head.swf"</span>&gt;&lt;/embed&gt;',
		].join('<br>'),
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName("Embed");
			var rst = [], tb;
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(tb.getAttribute('alt'))continue;
				var noembed = tb.getElementsByTagName('NoEmbed')[0] || tb.nextSibling;
				if(noembed && noembed.tagName=='NOEMBED' && (noembed.innerText || noembed.textContent))continue;
				rst.push(tb);
			}
			arr = body.getElementsByTagName("IMG");
			for(var i=0;i<arr.length;i++){
				tb = arr[i];
				if(!tb.getAttribute('_fckflash'))continue;
				var tb2 = FCK.GetRealElement( tb ) ;
				if(tb2.getAttribute('alt'))continue;
				var noembed = tb2.getElementsByTagName('NoEmbed')[0] || tb2.nextSibling;
				if(noembed && noembed.tagName=='NOEMBED' && (noembed.innerText || noembed.textContent))continue;
				tb.actualEle = tb2;
				rst.push(tb);
			}
			return rst;
		}
	},
	formSubmit: {
		type : wcm.LANG.TEMPLATE_109||'表单',
		warning : wcm.LANG.TEMPLATE_110||'Form表单中没有表示提交的按钮(input type=submit/image 或button type=submit)',
		example : [
			wcm.LANG.TEMPLATE_111||'Form表单中增加表示提交的按钮，例如：<br>',
			'&nbsp;&nbsp;&lt;input type="submit" value="Ok"&gt;',
		].join('<br>'),
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
		type : wcm.LANG.TEMPLATE_112||'表单控件',
		warning : [wcm.LANG.TEMPLATE_113||'可能原因:',
				wcm.LANG.TEMPLATE_114||'1、Input(type=image)控件没有指定alt/title属性;',
				wcm.LANG.TEMPLATE_115||'2、Input(type=button/submit/reset)控件没有指定value属性;',
				wcm.LANG.TEMPLATE_116||'3、Input(type=text/radio/checkbox/password)/Select/Textarea控件没有指定title属性且没有相关的label节点',
				wcm.LANG.TEMPLATE_117||'4、Button控件没有指定文本内容且没有title属性'/*,
				'FieldSet控件没有包含legend节点'*/
			].join('\n'),
		example : [
			wcm.LANG.TEMPLATE_118||'1、为Input(type=image)控件指定alt属性，例如：<br>',
			'&nbsp;&nbsp;&lt;input type="image" src="search.gif" <span style="color:blue">alt="Do Search"</span>&gt;',
			wcm.LANG.TEMPLATE_119||'<br>2、为Input(type=button/submit/reset)控件指定value属性，例如：<br>',
			'&nbsp;&nbsp;&lt;input type="submit" <span style="color:blue">value="Ok"</span>&gt;',
			wcm.LANG.TEMPLATE_120||'<br>3、为Input(type=text/radio/checkbox/password)/Select/Textarea控件指定title属性，例如：<br>',
			'&nbsp;&nbsp;&lt;input type="text" <span style="color:blue">title="User Name"</span>&gt;',
			wcm.LANG.TEMPLATE_121||'<br>4、为Button控件指定文本内容，例如：<br>',
			'&nbsp;&nbsp;&lt;button&gt;<span style="color:blue">'+(wcm.LANG.TEMPLATE_122||'按钮内容')+'</span>&lt;/button&gt;',
		].join('<br>'),
		fn : function(content, doc, body){
			var rst = [], tb = body, eles, labels, hsLabels = {};
			labels = tb.getElementsByTagName("Label");
			for(var j=0;j<labels.length;j++){
				hsLabels[labels[j].htmlFor] = true;
			}
			eles = tb.getElementsByTagName("Input");
			for(var j=0;j<eles.length;j++){
				if(hsLabels[eles[j].id])continue;
				if(eles[j].type=='submit' || eles[j].type=='button' || eles[j].type=='reset'){
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
		type : wcm.LANG.TEMPLATE_123||'菜单/导航条',
		warning : wcm.LANG.TEMPLATE_124||'每个表示菜单/导航条的OL/UL控件应该对应着一个H2-H6的标签',
		example : [
			wcm.LANG.TEMPLATE_125||'表示菜单/导航条的OL/UL控件对应增加一个H2-H6的标签，例如：<br>',
			'&nbsp;&nbsp;&lt;h5&gt;'+(wcm.LANG.TEMPLATE_126||'更多下载选项')+'&lt;/h5&gt;',
			'&nbsp;&nbsp;&lt;ul&gt;',	
			'&nbsp;&nbsp;&nbsp;&nbsp;&lt;li&gt;&lt;a href="/releases/#latest"&gt;'+(wcm.LANG.TEMPLATE_127||'其他文件格式')+'&lt;/a&gt;&lt;/li&gt;',
			'&nbsp;&nbsp;&nbsp;&nbsp;&lt;li&gt;&lt;a href="/releases/#older"&gt;'+(wcm.LANG.TEMPLATE_128||'旧版本')+'&lt;/a&gt;&lt;/li&gt;',
			'&nbsp;&nbsp;&nbsp;&nbsp;&lt;li&gt;&lt;a href="/releases/#beta"&gt;Beta'+(wcm.LANG.TEMPLATE_129||'测试版本') +'&amp;amp; RC'+(wcm.LANG.TEMPLATE_130||'候选发行版本')+'&lt;/a&gt;&lt;/li&gt;',
			'&nbsp;&nbsp;&lt;/ul&gt;'
		].join('<br>'),
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
					if(tmpEl.tagName.match(/^H[2-6]$/))break;
					if(tmpEl.tagName=='UL' || tmpEl.tagName=='OL'){
						tmpEl = null;
						break;
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
		warning : wcm.LANG.TEMPLATE_131||'控件指定了onmouseover事件的行为,但是没有与之相同行为的onfocus事件相对应',
		example : [
			wcm.LANG.TEMPLATE_132||'为控件同时指定onfocus行为，并与onmouseover一致，例如：<br>',
			'&nbsp;&nbsp;&lt;div <span style="color:blue">onmouseover="fn1();" onfocus="fn1();"</span> onmouseout="fn2();" onblur="fn2();"&gt;'
		].join('<br>'),
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
		warning : wcm.LANG.TEMPLATE_131||'控件指定了onmouseout事件的行为,但是没有与之相同行为的onblur事件相对应',
		example : [
			wcm.LANG.TEMPLATE_132||'为控件同时指定onblur行为，并与onmouseout一致，例如：<br>',
			'&nbsp;&nbsp;&lt;div onmouseover="fn1();" onfocus="fn1();" <span style="color:blue">onmouseout="fn2();" onblur="fn2();"</span>&gt;'
		].join('<br>'),
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
		warning : wcm.LANG.TEMPLATE_134||'控件指定了onclick事件的行为,但是该控件不可选中(focus)',
		example : [
			wcm.LANG.TEMPLATE_133||'使用可通过键盘切换到焦点的控件替换该控件，例如：<br>',
			'&nbsp;&nbsp;&lt;div onclick="fn1();"&gt;',
			wcm.LANG.TEMPLATE_135||'<br>转换为<br>',
			'&nbsp;&nbsp;&lt;a href="#" onclick="fn1();return false;"&gt;'
		].join('<br>'),
		fn : function(content, doc, body){
			var arr = body.getElementsByTagName('*'), rst = [];
			for(var i=0;i<arr.length;i++){
				if(!arr[i].getAttribute('onclick') && !arr[i].getAttribute('onfocus') && !arr[i].getAttribute('onblur'))continue;
				if(arr[i].tagName=='A' || arr[i].tagName=='SELECT' || arr[i].tagName=='TEXTAREA' || arr[i].tagName=='BUTTON' || arr[i].tagName=='INPUT' || arr[i].tabIndex>0)continue;
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
	_arrAlertions : function(rst, alertions, item, n2){
		if(rst.length==0)return;
		var tmp = [];
		for(var i=0;i<rst.length;i++){
			//if(rst[i].id && rst[i].id.match(/accessibility_\d*$/g))rst[i].id = '';
			//if(!rst[i].id)rst[i].id = getAccessibilityId();
			tmp.push(easyOuterHTML(rst[i].actualEle || rst[i]));
			rst[i].actualEle = null;
		}
		alertions.push({
			klass : item.type,
			warning : n2 ? item.warning[n2] : item.warning,
			eles : rst,
			htmls : tmp,
			example : n2 ? item.example[n2] : item.example,
			level : n2 || 'fail'
		});
	},
	getAlertions : function(content, doc, body, valids){
		var alertions = [];
		for(var name in valids){
			if(!valids[name])continue;
			var item = WCAG2Validator[name];
			var rst = item.fn(content, doc, body);
			if(rst===true)continue;
			if(typeof rst == 'object' && !Array.isArray(rst)){
				for(var n2 in rst){
					this._arrAlertions(rst[n2], alertions, item, n2);
				}
				continue;
			}
			if(Array.isArray(rst)){
				this._arrAlertions(rst, alertions, item);
				continue;
			}
			if(rst===false)
				alertions.push({
					klass : item.type,
					warning : item.warning,
					example : item.example
				});
			else
				alertions.push({
					klass : item.type,
					warning : item.warning[rst],
					example : item.example[rst]
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
}
function renderAccessibility(id, j){
	var alertion = hashAlertions[id];
	var cnt = getAccessibilityExample();
	cnt.style.display = 'none';
	if(!alertion)return;
	var html = [];
	html.push('<a class="accessibility_close" href="#" onclick="closeAccessibilityExample();return false;">'+(wcm.LANG.CLOSE||'关闭')+'</a><div class="accessibility_title">'+(wcm.LANG.TEMPLATE_56||'无障碍处理建议')+'</div>');
	html.push('<p>');
	html.push(alertion.example);
	html.push('</p>');
	cnt.innerHTML = html.join('');
	cnt.style.display = '';
	cnt.style.zIndex = 992;
}

function closeAccessibilityExample(){
	var cnt = getAccessibilityExample();
	cnt.style.display = 'none';
}
function closeAccessibility(){
	var cnt = getAccessibility();
	cnt.style.display = 'none';
}
var hashAlertions = {};
function doAccessibility(alertions){
	var cnt = getAccessibility();
	cnt.style.display = 'none';
	if(alertions==true){
		alert(wcm.LANG.TEMPLATE_58||'无障碍检查结果:符合WCAG2规范.');
		return;
	}
	hashAlertions = {};
	var html = [];
	html.push('<a class="accessibility_close" href="#" onclick="closeAccessibility();return false;">'+(wcm.LANG.CLOSE||'关闭')+'</a><div class="accessibility_title">'+(wcm.LANG.TEMPLATE_59||'无障碍检查结果')+'</div>');
	html.push('<ul>');
	for(var i=0;i<alertions.length;i++){
		var id = getAccessibilityId();
		hashAlertions[id] = alertions[i];
		html.push('<li>');
		html.push('<span class="accessibility_level">', alertions[i].level=='warn'?(wcm.LANG.TEMPLATE_60||'警告'):(wcm.LANG.TEMPLATE_61||'错误'), '</span>');
		html.push('<span class="accessibility_class">', alertions[i].klass, '</span>');
		html.push('<span class="accessibility_warning">', alertions[i].warning, '</span>');
		html.push('<span class="accessibility_num">(',
			alertions[i].htmls ? alertions[i].htmls.length : 1, ')</span>');
		if(!alertions[i].htmls){
			html.push('<a class="accessibility_detail" href="#" onclick="renderAccessibility(\'', id, '\');">'+(wcm.LANG.TEMPLATE_62||'建议')+'</a>');
			continue;
		}
		html.push('<a class="accessibility_detail" href="#" onclick="Element.toggle(\'', id, '\')">'+(wcm.LANG.TEMPLATE_63||'详细')+'</a>');
		html.push('<table border="1" bordercolor="gray" style="display:none;" id="', id, '">');
		for(var j=0;j<alertions[i].htmls.length;j++){
			var tmpHtml = alertions[i].htmls[j].trim();
			var tt = tmpHtml.replace(/</g, '&lt;').replace(/>/g, '&gt;');
			var eleId = alertions[i].eles[j].id;
			html.push('<tr>');
			html.push('<td width="40" align="center">',
				'<a href="#" onclick="renderAccessibility(\'', id, '\', ', j, ');return false;">'+(wcm.LANG.TEMPLATE_62||'建议')+'</a></td>');
			if(tmpHtml.match(/^<img /ig)){
				html.push('<td style="height:50px;overflow:hidden;" onclick="focusAccessibility(\'', eleId, '\');return false;"  title="', tt.replace(/\"/g, '&#34;'), '">');
			}else{
				html.push('<td onclick="focusAccessibility(\'', id, '\', ', j, ');return false;"  title="', tt.replace(/\"/g, '&#34;'), '">');
			}
			html.push(tt);
			html.push('</td></tr>');
		}
		html.push('</table>');
	}
	html.push('</ul>');
	cnt.innerHTML = html.join('');
	cnt.style.display = '';
	cnt.style.zIndex = 990;
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
function getAccessibilityExample(){
	var cnt = $('accessibility_example');
	if(!cnt){
		cnt = document.createElement('DIV');
		cnt.id = 'accessibility_example';
		cnt.className = 'accessibility_example';
		cnt.style.display = 'none';
		document.body.appendChild(cnt);
	}
	return cnt;
}
function fireAccessibility(s){
	if(!window.wcmWCAG2Helper)return;
	var alertions = wcmWCAG2Helper.validTemplate(s);
	doAccessibility(alertions);
}