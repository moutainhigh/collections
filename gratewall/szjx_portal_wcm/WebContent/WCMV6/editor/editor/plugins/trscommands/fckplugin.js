var TRSFormatCommand = function(_sName){
	this.Name = _sName ;
}
TRSFormatCommand.prototype.Execute = function(){
	try{
		FormatHtml(this.Name);
	}catch(err){
//		alert(err.message);
	}
}

function FormatHtml(_sName){
	var sHtml = FCK.GetHTML(false);
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
	}
	if(sNewHtml!=sHtml){
		FCKUndo.SaveUndoStep() ;
		FCK.SetHTML(sNewHtml);
	}
}
TRSFormatCommand.outdentNode = function(_Node){
	pFirstChild = _Node;
	if(pFirstChild==null)
		return;
	if(pFirstChild.nodeType==3){
		var cNbsp = String.fromCharCode(160);
		var re = new RegExp('^(\\s|'+cNbsp+'|&nbsp;|&nbsp|　)*', 'ig');
		pFirstChild.nodeValue = pFirstChild.nodeValue.replace(re, '　　');
	}
	else if(pFirstChild.tagName=='SPAN' || pFirstChild.tagName=='DIV'){
		pFirstChild = pFirstChild.firstChild;
		TRSFormatCommand.outdentNode(pFirstChild);
	}
}
TRSFormatCommand.outdentParagraph = function(){
	var editorBody = FCK.EditorDocument.body;
	var bAppendBlank = true;
	var arrParas = editorBody.getElementsByTagName('P');
	if(arrParas.length>1){
		for (var i = 0, n = arrParas.length; i < n; i++){
			TRSFormatCommand.outdentNode(arrParas[i].firstChild);
			if(bAppendBlank){
				new Insertion.Bottom('<br>', arrParas[i]);
			}
		}
	}
	else{
		var editorBody = FCK.EditorDocument.body.innerHTML;
		//TODO
	}
}
/**
 * 段落增加缩进
 */
function outdentParagraph(_sHtml){
	var vReturn='';
	var vPattern='(<(p|h1|h2|h3|h4|h5|h6)(>|\\s+[^>]*>))(&nbsp;|&nbsp|　)*((\n|\r|.)*?(<\/\\2(>|\\s+[^>]*>))?)';
	var vReplace='$1　　$5';
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
	var vReturn2 = '';
	vMatchAll = new RegExp("(<br\\s*\/?>\\s*)+", 'img');
	var vTmp = null;
	lastIndex=0;

	while((vTmp=vMatchAll.exec(vReturn))!=null){
		var sTmpPreStr = vReturn.substring(lastIndex,vTmp.index);
		vReturn2+=sTmpPreStr;
		vReturn2+=vTmp[0]+"　　";
		lastIndex=vTmp.index+vTmp[0].length;
	}
	if(lastIndex<vReturn.length){
		vReturn2+=vReturn.substring(lastIndex);
	}
	if(!vReturn2.startsWith("　　")&&!vReturn2.startsWith("<")){
		vReturn2 = "　　" + vReturn2;
	}
	return vReturn2;
}

/**
 * 统一格式,_sHtml为传入的Html字符串,_sClassName为统一的样式
 */
function unionStyle(_sHtml){
	var vReturn = _sHtml;
//	var vMatchAll=new RegExp('<div class='+_sClassName+'>((\n|\r|.)*?)</div>','img');
//	vReturn=_sHtml.replace(vMatchAll,'$1');
	vReturn=clearFontStyleTags(vReturn);
	vReturn=clearTagStyleFontAbout(vReturn);
	vReturn=replaceBr(vReturn);
//	vReturn='<div class="'+_sClassName+'">'+vReturn+'</div>';
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
 * 去除空行
 */
function clearBlankLine(_sHtml){
	var vReturn=_sHtml;
	var vPatterns=[
		'<p(>|\\s+[^>]*>)(&nbsp|&nbsp;|\\s|　|<br\\s*(\/)?>)*<\/p(>|\\s+[^>]*>)',
		'(<br\\s*(\/)?>){2,}',
		'(<p(>|\\s+[^>]*>))((&nbsp|&nbsp;|\\s)*<br\\s*(\/)?>)*((.|\n|\r)*?<\/p(>|\\s+[^>]*>))'
	];
	var vReplaces=[
		'',
		'<br>',
		'$1$6'
	];
	for(var i=0;i<vPatterns.length;i++){
		var vRegExp = new RegExp(vPatterns[i], 'ig');
		vReturn=vReturn.replace(vRegExp,vReplaces[i]);
	}
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

TRSFormatCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF;
}

FCKCommands.RegisterCommand( 'ClearBlank', new TRSFormatCommand('ClearBlank')) ;
var oClearBlankItem = new FCKToolbarButton( 'ClearBlank', FCKLang.ClearBlankBtn) ;
oClearBlankItem.IconPath = FCKPlugins.Items['trscommands'].Path + 'clear_blank.gif' ;
FCKToolbarItems.RegisterItem( 'ClearBlank', oClearBlankItem ) ;

FCKCommands.RegisterCommand( 'OutdentPara', new TRSFormatCommand('OutdentPara')) ;
var oOutdentParaItem = new FCKToolbarButton( 'OutdentPara', FCKLang.OutdentParaBtn) ;
oOutdentParaItem.IconPath = FCKPlugins.Items['trscommands'].Path + 'outdent_para.gif' ;
FCKToolbarItems.RegisterItem( 'OutdentPara', oOutdentParaItem ) ;

FCKCommands.RegisterCommand( 'Db2Sb', new TRSFormatCommand('Db2Sb')) ;
var oDb2SbItem = new FCKToolbarButton( 'Db2Sb', FCKLang.Db2SbBtn) ;
oDb2SbItem.IconPath = FCKPlugins.Items['trscommands'].Path + 'db2sb.gif' ;
FCKToolbarItems.RegisterItem( 'Db2Sb', oDb2SbItem ) ;

FCKCommands.RegisterCommand( 'UnionStyle', new TRSFormatCommand('UnionStyle')) ;
var oDb2SbItem = new FCKToolbarButton( 'UnionStyle', FCKLang.UnionStyleBtn) ;
oDb2SbItem.IconPath = FCKPlugins.Items['trscommands'].Path + 'union_style.gif' ;
FCKToolbarItems.RegisterItem( 'UnionStyle', oDb2SbItem ) ;

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
}
/**
 * 段落增加缩进
 */
function outdentParagraph(_sHtml){
	var vReturn='';
	var vPattern='(<p(>|\\s+[^>]*>))(&nbsp;|&nbsp|　)*((\n|\r|.)*?(<\/p(>|\\s+[^>]*>))?)';
	var vReplace='$1　　$5';
	vReturn = _sHtml.replace(new RegExp(vPattern, 'img'), vReplace);
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
		if(sTmpPreStr.startsWith("<p")||sTmpPreStr.startsWith("<P")){
			vReturn2 += sTmpPreStr;
		}else{
			vReturn2 += sTmpPreStr.replace(/^(&nbsp;|&nbsp|　)*/ig, '　　');
		}
//		vReturn2+=sTmpPreStr;
		vReturn2+=vTmp[0];//+"　　";
		lastIndex=vTmp.index+vTmp[0].length;
	}
	if(lastIndex<vReturn.length){
		var sTmpPreStr = vReturn.substring(lastIndex);
		if(sTmpPreStr.startsWith("<p")||sTmpPreStr.startsWith("<P")){
			vReturn2 += sTmpPreStr;
		}else{
			vReturn2 += sTmpPreStr.replace(/^(&nbsp;|&nbsp|　)*/ig, '　　');
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