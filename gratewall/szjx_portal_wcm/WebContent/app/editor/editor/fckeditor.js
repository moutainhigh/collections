Ext.ns('PageContext');
/**
* 统一格式,_sHtml为传入的Html字符串,_sClassName为统一的样式
*/
function unionStyle(_sHtml, _sClassName, _bFromPaste){
	var vReturn = _sHtml;
	//去掉word粘贴的特殊标记
	var exg = /^<div [^>]*class=\"?TRS_PreAppend\"?[^>]*>/img;
	if(exg.test(vReturn)){
		vReturn = vReturn.replace( /\s+class\s*=\s*(["'])TRS_PreAppend\1/img, ' ' ) ;
		FCK.AddEditorCss();
	}
	// 如果是粘贴过程触发的统一格式，需要在顶级元素追加
	if(!_bFromPaste){
		var vMatchAll = new RegExp('^<div class=\"?'+_sClassName+'\"?>((\n|\r|.)*)<\/div>$','img');
		vReturn = vReturn.replace(vMatchAll,'$1');
	}
	//抽取里面的table元素，因为table不需要进行样式处理，否则table将变形
	var oTablesHtml = {};
	var vReturn = ExtractTable(vReturn, oTablesHtml);
	vReturn = CleanCustomeWord(vReturn);
	vReturn = vReturn.replace(/<style(\s.*>|>)(.|\n|\r)*?<\/style>/gi, '');
	vReturn = clearFontStyleTags(vReturn);
	vReturn = clearTagStyleFontAbout(vReturn);
	vReturn = removeNoStyleTag(vReturn , "span");// 删除span元素
	//vReturn = replaceBr(vReturn);
	//将table里面的内容，还原回去
	vReturn = RestoreTable(vReturn, oTablesHtml);
	//清除空行
	vReturn = clearBlankLine(vReturn);
	if(!_bFromPaste){
		vReturn = '<div class="'+_sClassName+'">' + vReturn + '</div>';
	}

	//去掉word粘贴的特殊标记
	vReturn = vReturn.replace( /\s+class\s*=\s*(["'])TRS_PreAppend\1/img, ' ' ) ;
	FCK.UnitStyle = true;
	FCK.IsWordFile = false;
	return vReturn;
}

function CleanCustomeWord( sHtml){
	var html = sHtml ;
	// reomve text-indent,margin styles
	html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
	html = html.replace( /\s*face=[^ >]*/gi, '' ) ;
	html = html.replace( /([\s";])FONT-FAMILY:[^;"]*;?/gi, '$1' ) ;
	html = html.replace( /<(U|I|STRIKE)>&nbsp;<\/\1>/g, '&nbsp;' ) ;
	html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, function(_a0, _a1, _a2, _a3){
		if(_a2.toLowerCase().indexOf('fck_')!=-1){
			return '<' + _a1 + ' class=' + _a2 + _a3;
		}
		return '<' + _a1 + _a3;
	});//"<$1$3") ;

	html = html.replace( new RegExp('([\\s";\'])text-indent[^:]*:[^;"\']*;?', 'gi'), '$1' ) ;
	html =  html.replace( new RegExp('([\\s";\'])margin[^:]*:[^;"\']*;?', 'gi'), '$1' ) ;
	html = html.replace(/<o:p>\s*<\/o:p>/g, '') ;
	html = html.replace(/<o:p>.*?<\/o:p>/g, '&nbsp;') ;
	html = html.replace( /(["'])\s*PAGE-BREAK-BEFORE: [^\s;\']+;?"/gi, "$1" ) ;
	html = html.replace( /\s*FONT-VARIANT: [^\s;\']+;?"/gi, "\"" ) ;
	html = html.replace( /\s*tab-stops:[^;"\']*;?/gi, '' ) ;
	html = html.replace( /\s*tab-stops:[^"\']*/gi, '' ) ;

	html = html.replace( /([\s";'])mso-[a-z\-]+:[^;'"]*/img, '$1' ) ;
	html = html.replace( /\s+class\s*=\s*(["'])MsoNormal\1/img, ' ' ) ;

	html = html.replace( /<SPAN\s*[^>]*>\s*&nbsp;\s*<\/SPAN>/img, '&nbsp;' ) ;
	html = html.replace(/<\\?\?xml[^>]*>/gi, '' ) ;
	html = html.replace(/<\/?\w+:[^>]*>/gi, '' ) ;
	html = html.replace(/<\!--.*?-->/g, '' ) ;
	//HTML链接中可能存在链入本页面的锚点
	html = html.replace(/(<\w+[^>]*\s)href="([^"]*)(#.+)"/ig,
		function(a0,a1,a2,a3){
			if(a2.indexOf('fckeditor')!=-1 && a2.indexOf("?InstanceName=TRS_Editor")!=-1){
				return a1+'href="'+a3+'"';
			}
			return a0;
		});
	html = html.replace( /<H(\d)([^>]*)>/gi, '<h$1>' ) ;		
	return html ;
}

/**
*将sHtml里面的标记为table的outerHTML，提取出来，并保存到oTablesHtml对象中
*同时将sHtml里面的table替换成指定的标记，该标记为oTablesHtml对象的属性；
*/
function ExtractTable(sHtml, oTablesHtml){	
	//将table标记转成小写
	var regexp = /(<\/?)table([^>]*>)/ig;
	sHtml = sHtml.replace(regexp, "$1table$2");
	
	//被替换后的唯一标识前缀
	var guidPrefix = '$trs-table-';	
	//被替换后的唯一标识后缀，一直递增的序列
	var id = 0;
	var suffix = '$';	

	//返回后的HTML内容数组
	var aHtml = [];

	var index = 0;
	while(true){
		var pos = GetNextTagRange(sHtml, index, 'table');
		if(pos.s == -1){
			aHtml.push(sHtml.substring(index));
			return aHtml.join("");
		}
		//记录完整的table片段到oTablesHtml对象中
		var propertyName = guidPrefix + (++id) + suffix;
		oTablesHtml[propertyName] = sHtml.substring(pos.s, pos.e + 1);

		//组装返回的Html结果
		aHtml.push(sHtml.substring(index, pos.s), propertyName);

		//继续设置下一个偏移
		index = pos.e + 1;
	}

	return "";
}

/**
*从sHtml的nIndex位置开始，获取到html标记为sTagName的完整内容的位置范围{s:m,e:n}
*如果没有找到匹配的内容，返回{s:-1,e:-1}
*/
function GetNextTagRange(sHtml, nIndex, sTagName){
	var st = "<" + sTagName;
	var et = "</" + sTagName + ">";
	if(sHtml.indexOf(st, nIndex) < 0) return {s:-1, e:-1};

	var stack = [];
	var lastIndex;
	var index = nIndex;
	while(true){
		var sp = sHtml.indexOf(st, index);
		var ep = sHtml.indexOf(et, index);		
		if(sp == -1){
			lastIndex = stack.pop();
			index = ep + et.length;
		}else if(sp < ep){
			stack.push(sp);
			index = sp + st.length;
		}else{
			lastIndex = stack.pop();
			index = ep + et.length;
		}
		if(stack.length <= 0) return {s:lastIndex, e:index}
	}
	return {s:-1, e:-1};
}

/**
*将sHtml里面的特殊标记（为oTablesHtml对象属性名）转换成oTablesHtml对象属性值
*/
function RestoreTable(sHtml, oTablesHtml){
	return sHtml.replace(/\$trs-table-\d+\$/g, function($0){
		return oTablesHtml[$0] || 0;
	});
}

function removeNoStyleTag(_sContent, _sTagName){
	// 去除三次，避免嵌套
	var vMatchAll = new RegExp('<'+_sTagName+'>(.*?)<\/'+_sTagName+'>','img');
	_sContent = _sContent.replace(vMatchAll, '$1');
	_sContent = _sContent.replace(vMatchAll, '$1');
	_sContent = _sContent.replace(vMatchAll, '$1');

	return _sContent;	
}
/**
* 统一格式中替换1个或者1个以上的br为2个br
*/
function replaceBr(_sHtml){
	var vReturn=_sHtml;
	var vPatterns=[
		'(<br(\/)?>)+'
	];
	var vReplaces=[
		'<br><br>'
	];
	for(var i=0;i<vPatterns.length;i++){
		var vRegExp = new RegExp(vPatterns[i], 'ig');
		vReturn=vReturn.replace(vRegExp,vReplaces[i]);
	}
	return vReturn;
}
/**
* 统一格式中需要去除HTML置标中的style下的与font相关的属性
*/
function clearTagStyleFontAbout(_sHtml){
	var html = _sHtml;
	html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
	html = html.replace( /\s*face=[^ >]*/gi, '' ) ;
	html = html.replace( /\s*color="[^"]*"/gi, '' ) ;
	html = html.replace( /\s*color=[^ >]*/gi, '' ) ;
	var aFontStyles=[
		'font', 'color', 'font-family', 'font-size', 
		'font-size-adjust', 'font-stretch', 'font-style', 
		'font-weight', 'text-indent','text-decoration', 'text-underline-position',
		'text-shadow', 'font-variant', 'text-transform',
		'line-height', 'letter-spacing', 'word-spacing'
	];
	for(var i=0;i<aFontStyles.length;i++){
		html = html.replace( new RegExp('([\\s";\'])'+ aFontStyles[i] +':[^;"]*;?', 'gi'), '$1' ) ;
	}
	//需要排除margin相关属性
	html =  html.replace( new RegExp('([\\s";\'])margin[^:]*:[^;"]*;?', 'gi'), '$1' ) ;
	return html;
}
/**
* 统一格式中需要去除与font相关的HTML置标(如font,h1等)
*/
function clearFontStyleTags(_sHtml){
	var vReturn=_sHtml;
	var vPattern='</?(?:font|strong|b|i|u|em|sub|sup|strike)(?:>|\\s+[^>]*>)';
	var vRegExp = new RegExp(vPattern, 'img');
	vReturn = vReturn.replace(vRegExp, "");
	vPattern = '<(/?)h[1-6](?:>|\\s+[^>]*>)';
	var vRegExp = new RegExp(vPattern, 'img');
	vReturn = vReturn.replace(vRegExp, "<$1p>");
	return vReturn;
}

/**
* 转换全角为半角
*调整原有实现逻辑，对所有全角字符做处理
*/
function parseDb2Sb(_sHtml){
	//兼容在对"<,>,&"进行全传半时，由于转义而导致无法达到效果
	/*
	*首先由于设计界面不会出现（转义前）&amp;，无论是“＆”还是“＆ａｍｐ；”都可以得到相应的“&”和“&amp;”
	*/
	_sHtml = _sHtml.replace(/＆/img,"&amp;");
	var result = '';
	for (i=0 ; i<_sHtml.length; i++){
		code = _sHtml.charCodeAt(i);             //获取当前字符的unicode编码
		if (code >= 65281 && code <= 65373){   //unicode编码范围是所有的英文字母以及各种字符
			result += String.fromCharCode(_sHtml.charCodeAt(i) - 65248);    //把全角字符的unicode编码转换为对应半角字符的unicode码
		}else{
			result += _sHtml.charAt(i);                                     //原字符返回
		}
	}
	 return result;
}

/**
* 转换半角为全角
*/
function parseSb2db(_sHtml){
	//对所有的tag标签及内容不过滤
	var reExpression = /<\s*\/?\w+[^>]*?>/im;
	var pFragments;
	var nLastIndex = 0;
	var result = "";
	while(true){
		//表达式继续解析
		pFragments = reExpression.exec(_sHtml);
		//没有匹配的表达式时跳出循环
		if(pFragments==null){
			result += doSb2db(_sHtml);
			break;
		}
		//转化标签前的文本
		var temp = _sHtml.substring(0,pFragments.index);
		if(temp && temp.indexOf("&nbsp;") >= 0){
			var sub1 = temp.substring(0,temp.indexOf("&nbsp;"));
			temp = doSb2dbWithNbsp(temp);
		}else{
			temp = doSb2db(temp);
		}
		result += temp;
		//标签直接追加
		result += _sHtml.substring(pFragments.index,pFragments.lastIndex);
		//调整原串
		_sHtml = _sHtml.substring(pFragments.lastIndex);
	}
	return result;
}

function doSb2dbWithNbsp(_sHtml){
	var result = "";
	var index = _sHtml.indexOf("&nbsp;");
	var sub = _sHtml.substring(0,index);

	result += doSb2db(sub) + "&nbsp;";
	_sHtml = _sHtml.substring(index + "&nbsp;".length);
	if(_sHtml.indexOf("&nbsp;") >= 0){
		result += doSb2dbWithNbsp(_sHtml);
	}else{
		result += doSb2db(_sHtml);
	}
	return result;
}

function doSb2db(_sHtml){
	//兼容在对"<,>,&"进行半传全时，由于转义而导致无法达到效果
	/*
	*由于设计界面不会出现（转义前）&lt;
	*/
	_sHtml = _sHtml.replace(/&lt;/img,"<");
	_sHtml = _sHtml.replace(/&gt;/img,">");
	/*
	*由于设计界面不会出现（转义前）&lt;
	*/
	_sHtml = _sHtml.replace(/&amp;/img,"&");
	var result = '';
	for (i=0 ; i<_sHtml.length; i++){
		code = _sHtml.charCodeAt(i);             //获取当前字符的unicode编码
		if (code >= 33 && code <= 126){   //unicode编码范围是所有的英文字母以及各种字符
			result += String.fromCharCode(_sHtml.charCodeAt(i) + 65248);    //把半角字符的unicode编码转换为对应全角角字符的unicode码
		}else if (code == 32){                                      //空格
			result += String.fromCharCode(_sHtml.charCodeAt(i) + 12288 - 32);//全角空格
		}else{
			result += _sHtml.charAt(i);                                     //原字符返回
		}
	}
	return result;

}

function ClearWordStyle(){
	var sHtml = FCK_GetHTML();
	var sNewHtml = CleanWord(sHtml, false, false, false) ;
	if(sNewHtml!='' && sNewHtml!=sHtml){
		FCKUndo.SaveUndoStep() ;
		FCK.SetHTML(sNewHtml);
	}
}
function ClearClasses(){
	var sHtml = FCK_GetHTML();
	var sNewHtml = unionStyle(sHtml, 'Custom_UnionStyle') ;
	if(sNewHtml!='' && sNewHtml!=sHtml){
		FCKUndo.SaveUndoStep() ;
		FCK.SetHTML(sNewHtml);
	}
}
function FCK_GetHTML(){
	if(FCK.EditMode == 0){
		return FCK.EditorDocument.body.innerHTML;
	}
	else{
		return FCK.GetHTML();
	}
}
function CleanWord( sHtml, bIgnoreFont, bRemoveStyles, bRemoveEmptyBlock){
	var oCookie = FCK.loadCookie();
	var html = sHtml ;
	bRemoveEmptyBlock = false;
	if(oCookie.RemoveEmptyBlock){
		bRemoveEmptyBlock = oCookie.RemoveEmptyBlock == "1" ? true : false;
	}
	bIgnoreFont = false;
	if(oCookie.IgnoreFont){
		bIgnoreFont = oCookie.IgnoreFont == "1" ? true : false;
	}
	bRemoveStyles = false;
	if(oCookie.RemoveStyles){
		bRemoveStyles = oCookie.RemoveStyles == "1" ? true : false;
	}

	var bIgnoreBlock = false;
	if(oCookie.IgnoreBlock){
		bIgnoreBlock = oCookie.IgnoreBlock == "1" ? true : false;
	}
	// reomve text-indent,margin styles
	if ( bIgnoreFont ){
		html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
		html = html.replace( /\s*face=[^ >]*/gi, '' ) ;
		html = html.replace( /([\s";])FONT-FAMILY:[^;"]*?;?/gi, '$1' ) ;
		html = html.replace( /<(U|I|STRIKE)>&nbsp;<\/\1>/g, '&nbsp;' ) ;
			html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, function(_a0, _a1, _a2, _a3){
		if(_a2.toLowerCase().indexOf('fck_')!=-1){
			return '<' + _a1 + ' class=' + _a2 + _a3;
		}
		return '<' + _a1 + _a3;
	});//"<$1$3") ;

	}
	if(bIgnoreBlock){
		html = html.replace( new RegExp('([\\s";\'])text-indent[^:]*:[^;"\']*;?', 'gi'), '$1' ) ;
		html =  html.replace( new RegExp('([\\s";\'])margin[^:]*:[^;"\']*;?', 'gi'), '$1' ) ;
		html = html.replace(/<o:p>\s*<\/o:p>/g, '') ;
		html = html.replace(/<o:p>.*?<\/o:p>/g, '&nbsp;') ;
		html = html.replace( /(["';])\s*TEXT-ALIGN:[^\1]+?\1/gi, "$1" ) ;
		html = html.replace( /(["'])\s*PAGE-BREAK-BEFORE: [^\s;\']+;?"/gi, "$1" ) ;
		html = html.replace( /\s*FONT-VARIANT: [^\s;\']+;?"/gi, "\"" ) ;
		html = html.replace( /\s*tab-stops:[^;"\']*;?/gi, '' ) ;
		html = html.replace( /\s*tab-stops:[^"\']*/gi, '' ) ;
	}
	html = html.replace( /([\s";'])mso-[a-z\-]+:[^;'"]*/img, '$1' ) ;
	if ( bRemoveStyles ){
		html = html.replace(/<(\w[^>]*) style="([^\"]*)"([^>]*)/gmi, "<$1$3" ) ;
		html = html.replace(/<(\w[^>]*) style='([^\']*)'([^>]*)/gmi, "<$1$3" ) ;
		html =  html.replace( /\s*style="\s*"/gi, '' ) ;
		html = html.replace( /<(\w+)[^>]*\sstyle="[^"]*DISPLAY\s?:\s?none(.*?)<\/\1>/ig, '' ) ;
	}
	html = html.replace( /<SPAN\s*[^>]*>\s*&nbsp;\s*<\/SPAN>/img, '&nbsp;' ) ;
	html = html.replace(/<\\?\?xml[^>]*>/gi, '' ) ;
	html = html.replace(/<\/?\w+:[^>]*>/gi, '' ) ;
	html = html.replace(/<\!--.*?-->/g, '' ) ;
	//HTML链接中可能存在链入本页面的锚点
	html = html.replace(/(<\w+[^>]*\s)href="([^"]*)(#.+)"/ig,
		function(a0,a1,a2,a3){
			if(a2.indexOf('fckeditor')!=-1 && a2.indexOf("?InstanceName=TRS_Editor")!=-1){
				return a1+'href="'+a3+'"';
			}
			return a0;
		});
	if ( FCKConfig.CleanWordKeepsStructure ){
		html = html.replace( /<H(\d)([^>]*)>/gi, '<h$1>' ) ;
	}
	else{
		html = html.replace( /<H1([^>]*)>/gi, '<div$1><b><font size="6">' ) ;
		html = html.replace( /<H2([^>]*)>/gi, '<div$1><b><font size="5">' ) ;
		html = html.replace( /<H3([^>]*)>/gi, '<div$1><b><font size="4">' ) ;
		html = html.replace( /<H4([^>]*)>/gi, '<div$1><b><font size="3">' ) ;
		html = html.replace( /<H5([^>]*)>/gi, '<div$1><b><font size="2">' ) ;
		html = html.replace( /<H6([^>]*)>/gi, '<div$1><b><font size="1">' ) ;
		html = html.replace( /<\/H\d>/gi, '<\/font><\/b><\/div>' ) ;
		if(bRemoveEmptyBlock){
			html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
			html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
			html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
		}
	}

	if(bRemoveEmptyBlock){
		html = clearBlankLine(html);
	}
	html = html.replace( /<SPAN\s*[^>]*><\/SPAN>/gi, '' ) ;			
	return html ;
}

window.PasteAsPlainText = function PasteAsPlainText(){
	if (FCK.EditMode != FCK_EDITMODE_WYSIWYG )return;
	var sTemplate = FCK.GetClipboardHTML2();
	var reExpression = /<img[^>]*?>|<v:imagedata[^>]*?>/img;
	var pFragments, sTmpValue, sResultText = '';
	var nLastIndex = 0;
	try{
//		FCK.Events.FireEvent( 'OnSelectionChange' ) ;
		var eTmpDiv = document.createElement('DIV');
		eTmpDiv.style.display = 'none';
		document.body.appendChild(eTmpDiv);
		while(true){
			//表达式继续解析
			pFragments = reExpression.exec(sTemplate);
			//没有匹配的表达式时跳出循环
			if(pFragments==null)break;
			//得到上次匹配式的最后一位到当前匹配式中最前一位之间的串
			sTmpValue = sTemplate.substring(nLastIndex, pFragments.index);
			eTmpDiv.innerHTML = sTmpValue;
			if(navigator.userAgent.toUpperCase().indexOf("MSIE 8") >= 0){
				eTmpDiv.innerHTML = sTmpValue.replace( /\n/g, '<BR>' );
			}
			sResultText += eTmpDiv.innerText.replace( /&/g, '&amp;' ).replace( /</g, '&lt;' )
				.replace( />/g, '&gt;' ).replace( /\n/g, '<BR>' ).replace( /\s/g, '&nbsp;');
			sResultText += pFragments[0];
			//计算新的匹配式的最后一位并记录下来
			nLastIndex = pFragments.index + pFragments[0].length;
		}
		sTmpValue = sTemplate.substring(nLastIndex);
		eTmpDiv.innerHTML = sTmpValue;
		if(navigator.userAgent.toUpperCase().indexOf("MSIE 8") >= 0){
			eTmpDiv.innerHTML = sTmpValue.replace( /\n/g, '<BR>' );
		}
		sResultText	+= eTmpDiv.innerText.replace( /&/g, '&amp;' ).replace( /</g, '&lt;' )
				.replace( />/g, '&gt;' ).replace( /\n/g, '<BR>' ).replace( /\s/g, '&nbsp;');
		document.body.removeChild(eTmpDiv);
//		FCKCommands.GetCommand('SelectAll').Execute();
		FCKSelection.Collapse(false);

		re = new RegExp("v:imagedata","gi");  // 创建正则表达式对象.
		sResultText = sResultText.replace(re,"img");

		re = new RegExp("o:title=\"\"","gi");  // 创建正则表达式对象.
		sResultText = sResultText.replace(re,"");
		sResultText = doWithPageBreak(sResultText);//对复制的分页符做特殊处理
		FCK.InsertHtml(sResultText);
	}catch(err){
//		alert(getErrorStack(err));
	// Get the data available in the clipboard in text format.
		var sText = clipboardData.getData("Text") ;

		if ( sText && sText.length > 0 )
		{
			// Replace the carriage returns with <BR>
			sText = FCKTools.HTMLEncode( sText ).replace( /\n/g, '<BR>' ) ;

			// Insert the resulting data in the editor.
			FCK.InsertHtml( sText ) ;
		}
	}
}

/**
* 去除空行
*/
window.clearBlankLine = function clearBlankLine(_sHtml){
	var vReturn=_sHtml;
	var vPatterns=[
		'<(p|div|center)(>|\\s+[^>]*>)(<(strong|em|u|span)>)*(<\/(strong|em|u|span)>)*(&nbsp|&nbsp;|\\s|　|<br\\s*(\/)?>)*(<\/(strong|em|u|span)>)*<\/(p|div|center)(>|\\s+[^>]*>)',
		'(<br\\s*(\/)?>((\\s|&nbsp;|&nbsp|　)*)){2,}',
		'(<\/p\\s*>|<\/div\\s*>)<br\\s*(\/)?>((\\s|&nbsp;|&nbsp|　)*)',
		'(<p(>|\\s+[^>]*>))((&nbsp|&nbsp;|\\s)*<br\\s*(\/)?>)*((.|\n|\r)*?<\/p(>|\\s+[^>]*>))'
	];
	var vReplaces=[
		'',
		'<br>$3',
		'$1',
		'$1$6'
	];
	for(var i=0;i<vPatterns.length;i++){
		var vRegExp = new RegExp(vPatterns[i], 'img');
		vReturn=vReturn.replace(vRegExp,vReplaces[i]);
	}
	vReturn = vReturn.replace( /<SPAN\s*[^>]*><\/SPAN>/gi, '' ) ;	
	return vReturn;
}
/**
* 将格式不规格的代码替换成<p>节点包含的内容
* <br>也使用<p>来代替
*/
function wrapWithPTag(_sHtml){
	var lastIndex = 0;
	var oResultInfo = {
		lastIsEndPTag : true,
		strResult : '',
		lastTagName : 'p'
	}
	var vMatchAll = /<(\/)?([^\s>]*)(|\s+[^>]*?)>/ig;
	var sTmpPreStr, sCurrTagName, sCurrTagHtml, bIsEndTag;
	var vTmp = null;
	while((vTmp=vMatchAll.exec(_sHtml))!=null){
		sTmpPreStr = _sHtml.substring(lastIndex, vTmp.index);
		sCurrTagName = vTmp[2].toLowerCase();
		sCurrTagHtml = vTmp[0];
		bIsEndTag = vTmp[1]=='/';
		_doWrapWithPTag(sCurrTagName, bIsEndTag, sCurrTagHtml, sTmpPreStr, oResultInfo);
		lastIndex = vTmp.index + vTmp[0].length;
	}
	//最后一段,没有任何标记
	sTmpPreStr = _sHtml.substring(lastIndex);
	sCurrTagName = '_end_';
	sCurrTagHtml = '';
	bIsEndTag = true;
	_doWrapWithPTag(sCurrTagName, bIsEndTag, sCurrTagHtml, sTmpPreStr, oResultInfo);
	_sHtml = oResultInfo['strResult'];
	return _sHtml;
}

function _doWrapWithPTag(sCurrTagName, bIsEndTag, sCurrTagHtml, sTmpPreStr, _oInfo){
	var lastIsEndPTag = _oInfo['lastIsEndPTag'];
	var strResult = _oInfo['strResult'];
	var lastTagName = _oInfo['lastTagName'];
	if(sCurrTagName=='br'||sCurrTagName=='_end_'){
		//空<br>换成<p>&nbsp;</p>
		sTmpPreStr = (sCurrTagName=='br' && sTmpPreStr.trim()=='')?
		'&nbsp;' : sTmpPreStr;
		if(sCurrTagName=='_end_' && sTmpPreStr.trim()==''){
			strResult += sTmpPreStr;
		}
		else if(lastTagName=='p' && !lastIsEndPTag){
			strResult += sTmpPreStr+'</p>';
		}else{// \/p或者其他节点
			strResult += '<p>'+sTmpPreStr+'</p>';
		}
		//标记为/p为最后一个节点
		lastIsEndPTag = true;
		sCurrTagName = 'p';
		sCurrTagHtml = '';
	}else if(sCurrTagName=='p'){
		if(!bIsEndTag && lastIsEndPTag && sTmpPreStr.trim()==''){
			//当之前的为非<p>且此时为<p>时且中间的串为空串,此时不追加<p></p>
			strResult += sTmpPreStr;
		}else{
			if(lastTagName=='p' && !lastIsEndPTag){
				strResult += sTmpPreStr;
			}else{
				strResult += '<p>'+sTmpPreStr;
			}
			if(!bIsEndTag){
				strResult += '</p>';
			}
		}
		lastIsEndPTag = bIsEndTag;
	}
	else if(sCurrTagName.match(/(h\d|table|div|td|address|center)/)){
		if(lastTagName=='p' && !lastIsEndPTag){
			strResult += sTmpPreStr + '</p>';
		}else if(lastTagName=='p' && lastIsEndPTag){
			//防止引入多余的<p></p>
			if(sTmpPreStr.trim()!=''){
				strResult += '<p>' + sTmpPreStr + '</p>';
			}else{
				strResult += sTmpPreStr;
			}
		}else{
			strResult += sTmpPreStr;
		}
		lastIsEndPTag = true;
	}else if(sCurrTagName=='style'){
		strResult += sTmpPreStr;
	}else{
		if(lastTagName=='p' && lastIsEndPTag){
			strResult += '<p>';
			lastIsEndPTag = false;
		}
		//其余控件保持上一个有效控件的TagName
		sCurrTagName = lastTagName;
		strResult += sTmpPreStr;
	}
	strResult += sCurrTagHtml;
	//记录上一个标记
	lastTagName = sCurrTagName;
	_oInfo['lastIsEndPTag'] = lastIsEndPTag;
	_oInfo['strResult'] = strResult;
	_oInfo['lastTagName'] = lastTagName;
}
//新的首行缩进
function outdentParagraph(_sHtml){
	_sHtml = _sHtml.replace( new RegExp('([\\s";\'])text-indent[^:]*:[^;"\']*;?', 'gi'), '$1' ) ;
	_sHtml = wrapWithPTag(_sHtml);
	//对于编辑器默认附加的样式，需要排除
	var vMatchAll = new RegExp('^<div\\s+[^>]*?>(\n|\r|.)*<\/div>$','img');
	var bMatch = false;
	var sPreStyle = "";
	if(_sHtml.match(vMatchAll)){
		bMatch = true;
		sPreStyle =  _sHtml.replace(/^(<div\s+[^>]*?>)[\s\S]*/img,'$1');
	}
	if(bMatch){
		_sHtml = _sHtml.replace(/^<div\s+[^>]*?>([\s\S]*)<\/div>$/img,'$1');
	}
	var regReplace = /(<(p|div)(|\s+[^>]*)>)((\n|\r|.)*?<\/(p|div)>)/img;
	_sHtml =  _sHtml.replace(regReplace, function(){
		return arguments[1] + '　　' + 
		arguments[4].replace(/^(<(strong|em|i)>)(&nbsp;|&nbsp|　||\s)+/ig, '$1')
		.replace(/^(&nbsp;|&nbsp|　||\s)+/g, '');
	});
	//还原前缀样式
	if(bMatch){
		_sHtml = sPreStyle + _sHtml + "</div>";
	}
	return _sHtml;
}//end

Ext.apply(PageContext, {
	actionSource : function(event, item){
		var bIsWysiwyg = ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ) ;
		if(!bIsWysiwyg)return;
		if(bIsWysiwyg)FCK.SwitchEditMode(true) ;
		Element.addClassName( 'editor_btn_source', 'toolbar_current_btn');
		Element.removeClassName( 'editor_btn_design', 'toolbar_current_btn');
		Element.hide('btns_ctn', 'spt');
	},
	actionDesign : function(event, item){
		var bIsWysiwyg = ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ) ;
		if(bIsWysiwyg)return;
		if(!bIsWysiwyg)FCK.SwitchEditMode(true) ;
		Element.addClassName( 'editor_btn_design', 'toolbar_current_btn');
		Element.removeClassName( 'editor_btn_source', 'toolbar_current_btn');
		Element.show('btns_ctn', 'spt');
	},
	actionClearstyle : function(event, item){
		ClearWordStyle();
	},
	actionClearclasses : function(event, item){
		ClearClasses();
	},
	_replaceHtml : function(sHtml, sNewHtml){
		if(sNewHtml!='' && sNewHtml!=sHtml){
			FCKUndo.SaveUndoStep() ;
			// 是否会引起性能问题？？
			FCK.EditorDocument.body.innerHTML = sNewHtml;
			//FCK.SetHTML(sNewHtml);
		}
	},
	actionClearblank : function(event, item){
		var sHtml = FCK_GetHTML();
		var sNewHtml = clearBlankLine(sHtml);
		PageContext._replaceHtml(sHtml, sNewHtml);
	},
	actionOutdentpara : function(event, item){
		var sHtml = FCK_GetHTML();
		var sNewHtml = outdentParagraph(sHtml);
		PageContext._replaceHtml(sHtml, sNewHtml);
	},
	actionReplacenbsps : function(event, item){
		var sHtml = FCK_GetHTML();
		var sNewHtml = replaceNbsps(sHtml);
		PageContext._replaceHtml(sHtml, sNewHtml);
	},
	actionDb2sb : function(event, item){
		var sHtml = FCK_GetHTML();
		var sNewHtml = parseDb2Sb(sHtml);
		PageContext._replaceHtml(sHtml, sNewHtml);
	},
	actionSb2db : function(event, item){
		var sHtml = FCK_GetHTML();
		var sNewHtml = parseSb2db(sHtml);
		PageContext._replaceHtml(sHtml, sNewHtml);
	},
	actionPasteasplain : function(event, item){
		PasteAsPlainText();
	},
	actionFullscreen : function(event, item){
		try{
			item = item.getElementsByTagName('DIV')[0];
			if(!item)return;
			var bFull = (item.innerHTML.trim()== FCKLang.TIP12);
			item.innerHTML = (bFull? FCKLang.TIP13:FCKLang.TIP12);
			item.title = (bFull? FCKLang.TIP13:FCKLang.TIP12);
			if(myActualTop.FullOpenEditor)myActualTop.FullOpenEditor(bFull);
		}catch(ex){}
	},
/*,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,一键排版	by@sj*/
	actionKey4format : function(){
		var elDiv = document.getElementById("xEditingArea").childNodes;
		var doc = elDiv[0].contentWindow.document.body;
		var args = {
		"attribute"		:window.Attributes,
		/*
		*必传：当前编辑器document节点
		*/
		"doc"			:doc

		/*
		*预留接口：获取当前编辑器显示内容方法
		*可以不传
		*/
		//GetHtml		:function(){},

		/*
		*预留接口：设置当前编辑器显示内容方法
		*可以不传
		*/
		//SetHtml		:function(sHtml){},

		/*
		*预留接口：当前编辑器区域的html代码
		*可以不传
		*/
		//"html"		:document.getElementById("mytext").innerHTML
		};

		/*
		*根据不同浏览器设置打开一键排版的高度和宽度
		*/
		var height = "240px";
		var width = "710px";
		//for chrome
		if (window.MessageEvent && !document.getBoxObjectFor){
			if(window.chrome) {
				height = "260px";
				width = "846px";
			}
		}
		//for ff
		if (FCKBrowserInfo.IsGecko){
			height = "230px";
			width = "790px";
		}
		html=window.showModalDialog("plugins/key4format/dialogs.html",args,"dialogHeight=" + height + ";dialogWidth=" + width);
		if(html){
			//doc.innerHTML=html;
				var sTemp = html;
				FCK.SetFormatStyle = function(style){
					try{
						for(var i=0; i<FCK.EditorDocument.styleSheets.length; i++){
							if(FCK.EditorDocument.styleSheets[i].title == "__formatstyle__"){
								FCK.EditorDocument.styleSheets[i].cssText = style;
								break;
							}
						}
					}catch(ex){
					}
				}
				//不采用多次搜索，以确保多个样式同时存在时（表格边框）样式不会丢失
				var oReOuter = /<style[^>]*>([\s\S]*?)<\/style>/im;
				var sFormatStyle = oReOuter.exec(sTemp);
				if(sFormatStyle != null){
					FCK.sFormatStyle = sFormatStyle[0].replace(oReOuter,"$1");
					/*兼容编辑器不同系统对应字体库不同导致的粘贴时字体不生效问题*/
					var regFontSize4Fangsong = /仿宋|仿宋_GB2312/;
					var regFontSize4Kaiti = /楷体|楷体_GB2312/;
					FCK.sFormatStyle = FCK.sFormatStyle.replace(regFontSize4Fangsong,"仿宋,仿宋_GB2312");
					FCK.sFormatStyle = FCK.sFormatStyle.replace(regFontSize4Kaiti,"楷体,楷体_GB2312");
				}
				sTemp = sTemp.replace(oReOuter,'');
				FCKTools.RunFunction( function(){
					FCK.SetFormatStyle(FCK.sFormatStyle);
				});
				if(sFormatStyle[0]) {
					//sTemp = sFormatStyle[0] + sTemp;
					FCK.saveStyle = sFormatStyle[0];
				}
				if(FCK.SetHTML) {
					FCK.SetHTML(sTemp);
				}else {
					doc.innerHTML=html;
				}
		}
	},
/*........................................................................*/
	actionSetpageproperties : function(event, item){
		//2011-10-09 by CC 添加底部标签：页面属性设置
		// 弹出对话框
		FCKDialog.OpenDialog('DocumentProps','设置页面属性','plugins/documentprops/fck_documentprops.html',650, 400 );
	}
});
Event.observe(window, 'load', function(){
	function findActionItem(target){
		while(target!=null && target.tagName!='BODY'){
			if(target.getAttribute('_action', 2))return target;
			target = target.parentNode;
		}
		return null;
	}
	Ext.get('btn_container').on('click', function(event, target){
		var actionItem = findActionItem(target);
		if(actionItem==null)return;
		var action = actionItem.getAttribute('_action', 2) || '';
		PageContext['action' + action.camelize()](event, actionItem);
	});
});
FCKConfig.ProtectedSource.RegexEntries=[/<!--[\s\S]*?-->/g,/<script[\s\S]*?<\/script>/gi,/<noscript[\s\S]*?<\/noscript>/gi];