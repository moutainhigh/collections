function FormatHtml(_sName){
	try{
		var sHtml = FCK_GetHTML();
		var sNewHtml = '';
		switch(_sName){
			case 'OutdentPara':
				sNewHtml = outdentParagraph(sHtml);
				break;
			case 'ClearBlank':
				sNewHtml = clearBlankLine(sHtml);
				break;
			case 'Db2Sb':
				sNewHtml = parseDb2Sb(sHtml);
				break;
			case 'UnionStyle':
				sNewHtml = unionStyle(sHtml);
				break;
			case 'ReplaceNbsps':
				sNewHtml = replaceNbsps(sHtml);
				break;
			default:
				var sFunctionName = (_sName.charAt(0)+'').toLowerCase()+_sName.substring(1);
				if(window[sFunctionName]){
					sNewHtml = window[sFunctionName](sHtml);
					break;
				}
		}
		if(sNewHtml!='' && sNewHtml!=sHtml){
			FCKUndo.SaveUndoStep() ;
			FCK.SetHTML(sNewHtml);
		}
	}catch(ex){
		alert(ex.message);
	}
}
/**
 * 统一格式,_sHtml为传入的Html字符串,_sClassName为统一的样式
 */
function unionStyle(_sHtml, _sClassName){
	var vReturn = _sHtml;
	var vMatchAll = new RegExp('^<div class=\"?'+_sClassName+'\"?>((\n|\r|.)*)<\/div>$','img');
	vReturn = vReturn.replace(vMatchAll,'$1');
	vReturn = CleanWord( vReturn, true, true, false);
	vReturn = vReturn.replace(/<style(\s.*>|>)(.|\n|\r)*?<\/style>/gi, '');
	vReturn = clearFontStyleTags(vReturn);
	vReturn = clearTagStyleFontAbout(vReturn);
	//vReturn = replaceBr(vReturn);
	vReturn = '<div class="'+_sClassName+'">' + vReturn + '</div>';
	return vReturn;
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
		'font-weight', 'text-decoration', 'text-underline-position',
		'text-shadow', 'font-variant', 'text-transform',
		'line-height', 'letter-spacing', 'word-spacing'
	];
	for(var i=0;i<aFontStyles.length;i++){
		html = html.replace( new RegExp('([\\s";])'+ aFontStyles[i] +':[^;"]*;?', 'gi'), '$1' ) ;
	}
	return html;

	var vReturn='';
	var vFontStyles='font|color|font-family|font-size|font-size-adjust|font-stretch|font-style|font-weight|text-decoration|text-underline-position|text-shadow|font-variant|text-transform|line-height|letter-spacing|word-spacing';
	var vPattern='<[^>]* style=["\']?([^;\'"]*;)*(('+vFontStyles+'):[^;\'"]*)+[^>"\']*["\']?(>|\\s+[^>]*>)';
	var vPattern2='('+vFontStyles+'):[^;\'"]*';
	var vMatchAll = new RegExp(vPattern, 'ig');
	var vReplaceReg = new RegExp(vPattern2, 'ig');
	var vTmp=null;
	var lastIndex=0;
	while((vTmp=vMatchAll.exec(_sHtml))!=null){
		vReturn+=_sHtml.substring(lastIndex,vTmp.index);
		vReturn+=vTmp[0].replace(vReplaceReg,'');
		lastIndex=vTmp.index+vTmp[0].length;
	}
	if(lastIndex<_sHtml.length){
		vReturn+=_sHtml.substring(lastIndex);
	}
	return vReturn;
}
/**
 * 统一格式中需要去除与font相关的HTML置标(如font,h1等)
 */
function clearFontStyleTags(_sHtml){
	var vReturn=_sHtml;
	var vPattern='<(font|h1|h2|h3|h4|h5|h6|strong|b|i|u|em|sub|sup|strike)(>|\\s+[^>]*>)((\n|\r|.)*?)</\\1(>|\\s+[^>]*>)';
	var vRegExp = new RegExp(vPattern, 'img');
	var vReplace='<p>$3</p>';
	vReturn=vReturn.replace(vRegExp,function(_a0, _a1, _a2, _a3){
		if(_a1.match(/h[1-6]/g)){
			return "<p>" + _a3 + "</p>";
		}
		return _a3;
	});
	return vReturn;
}

/**
 * 转换全角为半角
 */
function parseDb2Sb(_sHtml){
	var vReturn='';
	var vPattern='([０-９Ａ-Ｚａ-ｚ])';
	var vMatchAll = new RegExp(vPattern, 'img');
	var vTmp=null;
	var lastIndex=0;
	while((vTmp=vMatchAll.exec(_sHtml))!=null){
		vReturn+=_sHtml.substring(lastIndex,vTmp.index);
		vReturn+=DbChar2SbChar(vTmp[0]);
		lastIndex=vTmp.index+vTmp[0].length;
	}
	if(lastIndex<_sHtml.length){
		vReturn+=_sHtml.substring(lastIndex);
	}
	return vReturn;
}
/**
 * 转换全角字符为半角字符
 */
function DbChar2SbChar(_cChar) {
    return String.fromCharCode(_cChar.charCodeAt(0)-65248); 
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
	var sNewHtml = unionStyle(sHtml , 'Custom_UnionStyle') ;
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
// This function will be called from the PasteFromWord dialog (fck_paste.html)
// Input: oNode a DOM node that contains the raw paste from the clipboard
// bIgnoreFont, bRemoveStyles booleans according to the values set in the dialog
// Output: the cleaned string
function CleanWord( sHtml, bIgnoreFont, bRemoveStyles, bRemoveEmptyBlock)
{
//	return sHtml;
	var html = sHtml ;
	bRemoveEmptyBlock = bRemoveEmptyBlock!=false;
	bIgnoreFont = bIgnoreFont || true;
	bRemoveStyles = bRemoveStyles || false;
	html = html.replace(/<o:p>\s*<\/o:p>/g, '') ;
	html = html.replace(/<o:p>.*?<\/o:p>/g, '&nbsp;') ;

	// Remove mso-xxx styles.
	html = html.replace( /\s*mso-[^:]+:[^;"]+;?/gi, '' ) ;

	// Remove margin styles.
	html = html.replace( /\s*MARGIN: 0cm 0cm 0pt\s*;/gi, '' ) ;
	html = html.replace( /\s*MARGIN: 0cm 0cm 0pt\s*"/gi, "\"" ) ;

	html = html.replace( /\s*TEXT-INDENT: 0cm\s*;/gi, '' ) ;
	html = html.replace( /\s*TEXT-INDENT: 0cm\s*"/gi, "\"" ) ;

	html = html.replace( /\s*TEXT-ALIGN: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*PAGE-BREAK-BEFORE: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*FONT-VARIANT: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*tab-stops:[^;"]*;?/gi, '' ) ;
	html = html.replace( /\s*tab-stops:[^"]*/gi, '' ) ;

	// Remove FONT face attributes.
	if ( bIgnoreFont )
	{
		html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
		html = html.replace( /\s*face=[^ >]*/gi, '' ) ;

		html = html.replace( /([\s";])FONT-FAMILY:[^;"]*;?/gi, '$1' ) ;
	}

	// Remove Class attributes
	//html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, "<$1$3") ;
	html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, function(_a0, _a1, _a2, _a3){
		if(_a2.toLowerCase().indexOf('fck_')!=-1){
			return '<' + _a1 + ' class=' + _a2 + _a3;
		}
		return '<' + _a1 + _a3;
	});//"<$1$3") ;

	html = html.replace( /([\s";])mso-[a-z\-]+:[^;"]*;?/gi, '$1' ) ;
	// Remove styles.
	if ( bRemoveStyles )
		html = html.replace( /<(\w[^>]*) style="([^\"]*)"([^>]*)/gi, "<$1$3" ) ;

	// Remove empty styles.
	html =  html.replace( /\s*style="\s*"/gi, '' ) ;

	html = html.replace( /<SPAN\s*[^>]*>\s*&nbsp;\s*<\/SPAN>/gi, '&nbsp;' ) ;

	html = html.replace( /<SPAN\s*[^>]*><\/SPAN>/gi, '' ) ;

	// Remove Lang attributes
	html = html.replace(/<(\w[^>]*) lang=([^ |>]*)([^>]*)/gi, "<$1$3") ;

//	html = html.replace( /<SPAN\s*>(.*?)<\/SPAN>/gi, '$1' ) ;

//	html = html.replace( /<FONT\s*>(.*?)<\/FONT>/gi, '$1' ) ;

	// Remove XML elements and declarations
	html = html.replace(/<\\?\?xml[^>]*>/gi, '' ) ;

	// Remove Tags with XML namespace declarations: <o:p><\/o:p>
	html = html.replace(/<\/?\w+:[^>]*>/gi, '' ) ;

	// Remove comments [SF BUG-1481861].
	html = html.replace(/<\!--.*-->/g, '' ) ;

	html = html.replace( /<(U|I|STRIKE)>&nbsp;<\/\1>/g, '&nbsp;' ) ;

	html = html.replace( /<H\d>\s*<\/H\d>/gi, '' ) ;

	// Remove "display:none" tags.
	html = html.replace( /<(\w+)[^>]*\sstyle="[^"]*DISPLAY\s?:\s?none(.*?)<\/\1>/ig, '' ) ;
	//HTML链接中可能存在链入本页面的锚点
	html = html.replace(/(<\w+[^>]*\s)href="([^"]*)(#.+)"/ig,
	function(a0,a1,a2,a3){
		if(a2.indexOf("/editor/editor/fckeditor.html?InstanceName=TRS_Editor&amp;Toolbar=WCM6")!=-1){
			return a1+'href="'+a3+'"';
		}
		return a0;
	});
	if ( FCKConfig.CleanWordKeepsStructure )
	{
		// The original <Hn> tag send from Word is something like this: <Hn style="margin-top:0px;margin-bottom:0px">
		html = html.replace( /<H(\d)([^>]*)>/gi, '<h$1>' ) ;

		// Word likes to insert extra <font> tags, when using MSIE. (Wierd).
//		html = html.replace( /<(H\d)><FONT[^>]*>(.*?)<\/FONT><\/\1>/gi, '<$1>$2<\/$1>' );
//		html = html.replace( /<(H\d)><EM>(.*?)<\/EM><\/\1>/gi, '<$1>$2<\/$1>' );
	}
	else
	{
		html = html.replace( /<H1([^>]*)>/gi, '<div$1><b><font size="6">' ) ;
		html = html.replace( /<H2([^>]*)>/gi, '<div$1><b><font size="5">' ) ;
		html = html.replace( /<H3([^>]*)>/gi, '<div$1><b><font size="4">' ) ;
		html = html.replace( /<H4([^>]*)>/gi, '<div$1><b><font size="3">' ) ;
		html = html.replace( /<H5([^>]*)>/gi, '<div$1><b><font size="2">' ) ;
		html = html.replace( /<H6([^>]*)>/gi, '<div$1><b><font size="1">' ) ;

		html = html.replace( /<\/H\d>/gi, '<\/font><\/b><\/div>' ) ;

		// Transform <P> to <DIV>
//		var re = new RegExp( '(<P)([^>]*>.*?)(<\/P>)', 'gi' ) ;	// Different because of a IE 5.0 error
//		html = html.replace( re, '<div$2<\/div>' ) ;

		// Remove empty tags (three times, just to be sure).
		// This also removes any empty anchor
		if(bRemoveEmptyBlock){
			html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
			html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
			html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
		}
	}
	return html ;
}
function FullScreenEditor(_elControl){
	try{
		var bFull = (_elControl.innerText=='全屏编辑');
		_elControl.innerHTML = '<div class="btn_inner">' + (bFull?'退出全屏':'全屏编辑') + '</div>';
		parent.parent.FullOpenEditor(bFull);
	}catch(ex){}
}
function PasteAsPlainText()
{	
	var sTemplate = FCK.GetClipboardHTML();
	var reExpression = /<img[^>]*?>/img;
	var pFragments, sTmpValue, sResultText = '';
	var nLastIndex = 0;
	try{
		FCK.Events.FireEvent( 'OnSelectionChange' ) ;
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
			sResultText += eTmpDiv.innerText.replace( /&/g, '&amp;' ).replace( /</g, '&lt;' )
				.replace( />/g, '&gt;' ).replace( /\n/g, '<BR>' ).replace( /\s/g, '&nbsp;');
			sResultText += pFragments[0];
			//计算新的匹配式的最后一位并记录下来
			nLastIndex = pFragments.index + pFragments[0].length;
		}
		sTmpValue = sTemplate.substring(nLastIndex);
		eTmpDiv.innerHTML = sTmpValue;
		sResultText	+= eTmpDiv.innerText.replace( /&/g, '&amp;' ).replace( /</g, '&lt;' )
				.replace( />/g, '&gt;' ).replace( /\n/g, '<BR>' ).replace( /\s/g, '&nbsp;');
		document.body.removeChild(eTmpDiv);
		FCKCommands.GetCommand('SelectAll').Execute();
		FCKSelection.Collapse(false);
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
 * 段落增加缩进
 */
function outdentParagraph(_sHtml){
	var vReturn='';
	var vPattern='(<(p|span)(>|\\s+[^>]*>))((\n|\r|.)*?<\/\\2(>|\\s+[^>]*>))';
//	var vReplace='$1　　$6';
	vReturn = _sHtml.replace(new RegExp(vPattern, 'img'), function(){
		return arguments[1] + arguments[4].replace(/^(&nbsp;|&nbsp|　||\s)+/g, '　　');
	});//vReplace);
	/*
	var vMatchAll = new RegExp(vPattern, 'img');
	var vMatchOne = new RegExp(vPattern, 'im');
	var vTmp=null;
	var lastIndex=0;
	while((vTmp=vMatchAll.exec(_sHtml))!=null){
		vReturn+=_sHtml.substring(lastIndex,vTmp.index);
		vReturn+=vTmp[0].replace(vMatchOne,vReplace);
		lastIndex=vTmp.index+vTmp[0].length;
	}
	if(lastIndex<_sHtml.length){
		vReturn+=_sHtml.substring(lastIndex);
	}
	*/
	var vReturn2 = '';
	vMatchAll = new RegExp("(<br\\s*\/?>\\s*)+", 'img');
	var vTmp = null;
	lastIndex=0;
	while((vTmp=vMatchAll.exec(vReturn))!=null){
		var sTmpPreStr = vReturn.substring(lastIndex,vTmp.index);
		if(sTmpPreStr.startsWith("<p")||sTmpPreStr.startsWith("<P")
			|| sTmpPreStr.substring(0,5).toLowerCase()=="<span"){
			vReturn2 += sTmpPreStr;
		}else{
			vReturn2 += sTmpPreStr.replace(/^(&nbsp;|&nbsp|　||\s)+/g, '　　');
		}
//		vReturn2+=sTmpPreStr;
		vReturn2+=vTmp[0];//+"　　";
		lastIndex=vTmp.index+vTmp[0].length;
	}
	if(lastIndex<vReturn.length){
		var sTmpPreStr = vReturn.substring(lastIndex);
		if(sTmpPreStr.startsWith("<p")||sTmpPreStr.startsWith("<P")
			|| sTmpPreStr.substring(0,5).toLowerCase()=="<span"){
			vReturn2 += sTmpPreStr;
		}else{
			vReturn2 += sTmpPreStr.replace(/^(&nbsp;|&nbsp|　||\s)+/g, '　　');
		}
	}
	return vReturn2;
}
/**
 * 去除空行
 */
function clearBlankLine(_sHtml){
	var vReturn=_sHtml;
	var vPatterns=[
		'<p(>|\\s+[^>]*>)(&nbsp|&nbsp;|\\s|　|<br\\s*(\/)?>)*<\/p(>|\\s+[^>]*>)',
		'(<br\\s*(\/)?>((\\s|&nbsp;|&nbsp|　)*)){2,}',
		'(<p(>|\\s+[^>]*>))((&nbsp|&nbsp;|\\s)*<br\\s*(\/)?>)*((.|\n|\r)*?<\/p(>|\\s+[^>]*>))'
	];
	var vReplaces=[
		'',
		'<br>$3',
		'$1$6'
	];
	for(var i=0;i<vPatterns.length;i++){
		var vRegExp = new RegExp(vPatterns[i], 'img');
		vReturn=vReturn.replace(vRegExp,vReplaces[i]);
	}
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
	//最后一段，没有任何标记
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
			//当之前的为非<p>且此时为<p>时且中间的串为空串，此时不追加<p></p>
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
	else if(sCurrTagName.match(/(h\d|table|div|td|address|center|img)/)){
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
	_sHtml = wrapWithPTag(_sHtml);
	var regReplace = /(<p(|\s+[^>]*)>)((\n|\r|.)*?<\/p>)/img;
	return _sHtml.replace(regReplace, function(){
		return arguments[1] + '　　' + 
			arguments[3].replace(/^(<(strong|em|i)>)(&nbsp;|&nbsp|　||\s)+/ig, '$1')
				.replace(/^(&nbsp;|&nbsp|　||\s)+/g, '');
	});
}
//华龙网定制
function huaLongWang(_sHtml){
	return outdentParagraph(_sHtml);
}