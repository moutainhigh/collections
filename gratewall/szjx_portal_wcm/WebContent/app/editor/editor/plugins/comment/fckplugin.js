// Register the related command.
var FCKCommentCommand = function(_sName,_sBgColorIndex){
	this.Name = _sName ;
	this.BgColorIndex = _sBgColorIndex || 1;
}
var FCKCommentCommandBgColors = ['#FFFFD7','#FFFFD7','#FFE3C0','#FFD7FF','#D7FFD7','#D7FFFF','#EED7FF'];
FCKCommentCommand.prototype.Execute = function(){
	switch(this.Name){
		case 'Comment':
			var oCookies = FCK.loadCookie();
			var sValue = sDefault = FCKLang.CommentSet1,sBgColor = FCKCommentCommandBgColors[1];
			if(oCookies['EditorCommentBgColor']){
				sBgColor = oCookies['EditorCommentBgColor'];
			}
			var selRange = GetWYSIWYGSelectionRange();
			var MSG_TOO_COMPLEX = 'MSG_TOO_COMPLEX';
			var MSG_INCLUDE_SEL_TEXT = FCKLang.CommentSet2;			
			if (IsDefined(selRange, "collapsed")) { // non-IE browsers
				if (!selRange.collapsed) {
					if (WY_isRangeComplex_(selRange)) {
						if(confirm(MSG_TOO_COMPLEX)) {//TODO
							selRange.collapse(false);
						}
					} else if (!confirm(MSG_INCLUDE_SEL_TEXT)) {
						selRange.collapse(false);
					} else {
					}
				}
			} else if (IsDefined(selRange, "htmlText")) { // IE browsers
				if (selRange.htmlText.length > 0 && !FCK.isBlankContent(selRange.htmlText)) {
					if (confirm(MSG_INCLUDE_SEL_TEXT)) {
						sValue = selRange.htmlText;
					}
				}
			}
			var oSpan = FCKComments.Add( sValue, sBgColor, sDefault );
			SelectNode(oSpan.getElementsByTagName("SPAN")[0]);
			break;
		case 'CommentDel':
			FCKComments.GetCommentSpan();
			FCKComments.AddIntoDoc(false);
			break;
		case 'CommentAddIntoDoc':
			FCKComments.GetCommentSpan();
			FCKComments.AddIntoDoc(true);
			break;
		case 'CommentSetBg':
			FCKComments.GetCommentSpan();
			FCKComments.SetBgColor(
				FCKCommentCommandBgColors[this.BgColorIndex] || FCKCommentCommandBgColors[1],true);
			break;
	}
}

FCKCommentCommand.prototype.GetState = function(){
	/*
	var eSelected = FCKSelection.GetSelectedElement() ;
	var bInComment = false;
	if(!eSelected){
		FCKSelection.MoveToAncestorNode('SPAN');
	}
	var eTmp = eSelected;
	while(eTmp!=null&&eTmp.tagName!='BODY'){
		if(eTmp.tagName=='SPAN'&&eTmp._fckComment){
			bInComment = true;
			break;
		}
		eTmp = eTmp.parentNode;
	}
	if(bInComment){
		return FCK_TRISTATE_DISABLED ;
	}
	*/
	return FCK_TRISTATE_OFF ;
}

FCKCommands.RegisterCommand( 'Comment', 
	new FCKCommentCommand('Comment')) ;
FCKCommands.RegisterCommand( 'CommentDel', 
	new FCKCommentCommand('CommentDel')) ;
FCKCommands.RegisterCommand( 'CommentAddIntoDoc', 
	new FCKCommentCommand('CommentAddIntoDoc')) ;
FCKCommands.RegisterCommand( 'CommentSetBg1', 
	new FCKCommentCommand('CommentSetBg',1)) ;
FCKCommands.RegisterCommand( 'CommentSetBg2', 
	new FCKCommentCommand('CommentSetBg',2)) ;
FCKCommands.RegisterCommand( 'CommentSetBg3', 
	new FCKCommentCommand('CommentSetBg',3)) ;
FCKCommands.RegisterCommand( 'CommentSetBg4', 
	new FCKCommentCommand('CommentSetBg',4)) ;
FCKCommands.RegisterCommand( 'CommentSetBg5', 
	new FCKCommentCommand('CommentSetBg',5)) ;
FCKCommands.RegisterCommand( 'CommentSetBg6', 
	new FCKCommentCommand('CommentSetBg',6)) ;

// Create the "Comment" toolbar button.
var oCommentItem = new FCKToolbarButton( 'Comment', FCKLang.CommentBtn ) ;
oCommentItem.IconPath = FCKPlugins.Items['comment'].Path + 'comment.gif' ;

FCKToolbarItems.RegisterItem( 'Comment', oCommentItem ) ;

var actualTop = top.actualTop||top;

// The object used for all Comment operations.
var FCKComments = new Object() ;
var WY_commentSpan_,WY_newCommentColor_;
// Add a new Comment at the actual selection.
FCKComments.Add = function( _sComment, _sBgColor, _sDefault )
{
	var oSpan = FCK.CreateElement( 'SPAN' ) ;
	try{
		this.SetupSpan( oSpan, _sComment, _sBgColor ) ;
	}catch(err){
		try{
			var eDiv = FCK.EditorDocument.createElement('DIV');
			eDiv.innerHTML = _sComment;
			this.SetupSpan( oSpan, eDiv.innerText, _sBgColor ) ;
		}catch(err2){
			this.SetupSpan( oSpan, _sDefault, _sBgColor ) ;
		}
	}
	return oSpan;
}

FCKComments.SetupSpan = function( span, _sComment, _sBgColor )
{
	span.innerHTML = '<span contentEditable="true">'+_sComment+'<\/span> -'+(actualTop.UserName||'')+' '+new Date().toString(0);

	span.style.backgroundColor = _sBgColor ;

	if ( FCKBrowserInfo.IsGecko )
		span.style.cursor = 'default' ;

	span.setAttribute('_trscomment','true');
	span.id = 'fckcomment_'+(actualTop.UserName||'');
	span.className = 'fck_comment';
	span.contentEditable = false ;
	// To avoid it to be resized.
	span.onresizestart = function()
	{
		FCK.EditorWindow.event.returnValue = false ;
		return false ;
	}
}
FCKComments.GetCommentSpan = function(){
	var eSelected = FCKSelection.GetSelectedElement() ;
	WY_commentSpan_ = null;
	if ( eSelected.tagName == 'SPAN' && eSelected.getAttribute('_trscomment',2) ){
		WY_commentSpan_ = eSelected ;
	}
	return WY_commentSpan_;
}
/**
 * Either removes or incorporates the current comment.
 *
 * @param {Boolean} addToDoc If true, inserts the comment the comment as text. 
 * false, just deletes it.
 */
FCKComments.AddIntoDoc = function(addToDoc) {
	try {
		if (!WY_commentSpan_) {
			return;
		}
		var ask = addToDoc ? FCKLang.CommentSet3 : FCKLang.CommentDel;
		if (!confirm(ask + " " + FCKLang.CommentSet4)) {
			return;
		}
		var textToAdd = null;
		if (addToDoc) {
			textToAdd = RemoveHTMLMarkup(WY_commentSpan_.innerHTML);
			var newNode = FCK.EditorDocument.createTextNode(textToAdd);
			WY_commentSpan_.parentNode.replaceChild(newNode, WY_commentSpan_);
		} else {
			WY_commentSpan_.parentNode.removeChild(WY_commentSpan_);
		}

	} catch (ex) {
	}
}
/**
 * Changes the color of the current comment or all the user's comments.
 *
 * @param {Boolean} all true to change all the user's comments, false just this.
 * @param {Boolean} save true to save this color as the new default for this 
 * user.
 */
FCKComments.SetBgColor = function(_sBgColor,_bAll) {
	var sSpanId = 'fckcomment_'+(actualTop.UserName||'');
	if (!_bAll){
		if (WY_commentSpan_) {
			WY_commentSpan_.style.backgroundColor = _sBgColor;
		}
	}
	else{
		var spans = FCK.EditorDocument.getElementsByTagName("span");
		for (var index = 0; index < spans.length; index++) {
			var oSpan = spans[index];
			if (oSpan.getAttribute('_trscomment',2)&&oSpan.id == sSpanId) {
				oSpan.style.backgroundColor = _sBgColor;
			}
		}
	}
	FCK.setCookie('EditorCommentBgColor',_sBgColor);
}
FCKComments.FormatHTML = function(_sHTML){
	return _sHTML.replace(/<TRS_COMMENT([^>]*)>((.|\n|\r)*?)<\/TRS_COMMENT>/ig,'<SPAN class="fck_comment" _trscomment="true" contentEditable="false" onresizestart="return false"$1>$2<\/SPAN>');
}
FCKFormatEditedHtml.AppendNew(FCKComments.FormatHTML);
// We must process the SPAN tags to replace then with the real resulting value of the comment.
FCKXHtml.TagProcessors['span'] = function( node, htmlNode ){
	if ( htmlNode.getAttribute('_trscomment',2) ){
		node = FCKXHtml.XML.createElement('TRS_COMMENT') ;
		FCKXHtml._AppendAttribute( node, 'id', htmlNode.id ) ;
		FCKXHtml._AppendAttribute( node, 'style', 'background-color:'+htmlNode.style.backgroundColor ) ;
		node = FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;
	}
	else
		FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;

	return node ;
}
