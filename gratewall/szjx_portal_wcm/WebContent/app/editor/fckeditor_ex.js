var myActualTop = parent.parent;
FCK.UnitStyle = false;
FCK.IsWordFile = false;
var editorCfg = myActualTop.editorCfg || {};
FCKToolbarFontSizeCombo.prototype.CreateItems = function( targetSpecialCombo ){
	targetSpecialCombo.FieldWidth = 70 ;
	var aSizes = FCKConfig.FontSizes.split(';') ;
	for ( var i = 0 ; i < aSizes.length ; i++ ){
		var aSizeParts = aSizes[i].split('/') ;
		this._Combo.AddItem( aSizeParts[0], 
			'<div style="font-size:12px"> ' + aSizeParts[1] + '</div>', aSizeParts[1] ) ;
	}
}
FCKToolbarFontsCombo.prototype.CreateItems = function( targetSpecialCombo ){
	var aFonts = FCKConfig.FontNames.split(';') ;
	for ( var i = 0 ; i < aFonts.length ; i++ ){
		this._Combo.AddItem( aFonts[i], 
			'<div style="font-size:12px;font-family:' + aFonts[i] + '">'
				+ aFonts[i] + '</div>' ) ;
	}
}
FCK_ContextMenu_GetListener0 = FCK_ContextMenu_GetListener;
var FCK_ContextMenu_GetListener = function(listenerName){
	var result = FCK_ContextMenu_GetListener0(listenerName);
	switch(listenerName){
		case 'Generic' :
			return {
				AddItems : function( menu, tag, tagName ){
					menu.AddItem( 'Cut', FCKLang.Cut, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					menu.AddItem( 'Copy', FCKLang.Copy, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					menu.AddItem( 'Paste', FCKLang.Paste, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					menu.AddSeparator() ;
					menu.AddItem( 'SelectAll', FCKLang.SelectAll,18, FCKCommands.GetCommand( 'SelectAll' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					if(!FCKConfig.SimpleEditor){
						menu.AddSeparator() ;
						menu.AddItem( 'PageBreak', FCKLang.PageBreak,43) ;
					}
					if(FCKConfig.SupportExtract&&FCKSelection.GetType()=='Text'){
						menu.AddSeparator() ;
						menu.AddItem( 'ExtractTitle', FCKLang.TOTITLE);
						menu.AddItem( 'ExtractAbstract', FCKLang.TOSUMMAY) ;
					}
				}
			};
		case 'Comment' :
			return {
				AddItems : function( menu, tag, tagName ){
					if ( tagName != 'SPAN' || !tag.getAttribute('_trscomment',2) )return;
					menu.AddSeparator() ;
					menu.AddItem( 'CommentDel', FCKLang.CommentDel) ;
					menu.AddItem( 'CommentAddIntoDoc', FCKLang.CommentAddIntoDoc) ;
					menu.AddSeparator() ;
					menu.AddItem( 'CommentSetBg1', FCKLang.CommentSetBg1) ;
					menu.AddItem( 'CommentSetBg2', FCKLang.CommentSetBg2) ;
					menu.AddItem( 'CommentSetBg3', FCKLang.CommentSetBg3) ;
					menu.AddItem( 'CommentSetBg4', FCKLang.CommentSetBg4) ;
					menu.AddItem( 'CommentSetBg5', FCKLang.CommentSetBg5) ;
					menu.AddItem( 'CommentSetBg6', FCKLang.CommentSetBg6) ;
				}
			} ;
		//增加图像右键菜单提取为附件及提取为图片库图片操作
		case 'Image':
			return {
				AddItems:function(menu,tag,tagName){
					if (tagName=='IMG'&&!tag.getAttribute('_fckfakelement')){
						menu.AddItem('Image',FCKLang.ImageProperties,37);
						var myActualTop = (top.actualTop||top);
						if(myActualTop && myActualTop.UserName){
							menu.AddSeparator();
							menu.AddItem('ImageToAppendix',FCKLang.ImageToAppendix,1);
							menu.AddItem('ImageToPhototLib',FCKLang.ImageToPhototLib,79);
						}
					}
				}
			};
	}
	return result;
}
FCKCommands.GetCommand0 = FCKCommands.GetCommand;
FCKCommands.GetCommand = function( commandName ){
	var oCommand = FCKCommands.LoadedCommands[commandName] ;
	if(oCommand)return oCommand ;
	
	switch(commandName ){
		case 'Link':
			oCommand = new FCKDialogCommand( 'Link', FCKLang.DlgLnkWindowTitle,
				'dialog/fck_link.html', 440, 180, FCK.GetNamedCommandState, 'CreateLink' ) ;
			break ;
		case 'Anchor':
			oCommand = new FCKDialogCommand( 'Anchor', FCKLang.DlgAnchorTitle,
				'dialog/fck_anchor.html', 370, 120 ) ; 
			break ;
		case 'BulletedList' :
			oCommand = new FCKDialogCommand( 'BulletedList', FCKLang.BulletedListProp,
				'dialog/fck_listprop.html', 370, 120 ) ;
			break ;
		case 'NumberedList' :
			oCommand = new FCKDialogCommand( 'NumberedList', FCKLang.NumberedListProp,
				'dialog/fck_listprop.html?OL', 370, 120 ) ;
			break ;
		case 'Find' :
			oCommand = new FCKDialogCommand( 'Find', FCKLang.DlgFindTitle,
				'dialog/fck_find.html', 440, 150 ) ; 
			break ;
		case 'Replace' : 
			oCommand = new FCKDialogCommand( 'Replace', FCKLang.DlgReplaceTitle,
				'dialog/fck_replace.html', 440, 150 ) ; 
			break ;
		case 'Image' : 
			oCommand = new FCKDialogCommand( 'Image', FCKLang.DlgImgTitle,
				'dialog/fck_image.html', 520, 200 ) ; 
			break ;
		case 'Flash' : 
			oCommand = new FCKDialogCommand( 'Flash', FCKLang.DlgFlashTitle,
				'dialog/fck_flash.html', 450, 270 ) ; 
			break ;
		case 'SpecialChar' : 
			oCommand = new FCKDialogCommand( 'SpecialChar', FCKLang.DlgSpecialCharTitle,
				'dialog/fck_specialchar.html', 560, 400 ) ; 
			break ;
		case 'Smiley' : 
			oCommand = new FCKDialogCommand( 'Smiley', FCKLang.DlgSmileyTitle,
				'dialog/fck_smiley.html', FCKConfig.SmileyWindowWidth, 
				FCKConfig.SmileyWindowHeight ) ; 
			break ;
		case 'Table' : 
			oCommand = new FCKDialogCommand( 'Table', FCKLang.DlgTableTitle,
				'dialog/fck_table.html', 450, 250 ) ; 
			break ;
		case 'UniversalKey' : 
			oCommand = new FCKDialogCommand( 'UniversalKey', FCKLang.UniversalKeyboard,
				'dialog/fck_universalkey.html', 415, 300 ) ;
			break ;
		case 'PasteWord' : 
			oCommand = new FCKPasteWordCommand() ; 
			break ;
		case 'InsertUnorderedList': 
			oCommand = new FCKUnOrderListCommand() ; 
			break ;
		case 'InsertOrderedList': 
			oCommand = new FCKOrderListCommand() ; 
			break ;
		case 'LineHeight':
			oCommand = new FCKLineHeightCommand();
			break;
		default :
			oCommand = FCKCommands.GetCommand0(commandName);
	}
	FCKCommands.LoadedCommands[commandName] = oCommand;
	return oCommand;
}
function FCKUnOrderListCommand(){}
	FCKUnOrderListCommand.prototype = new FCKNamedCommand( 'InsertUnorderedList' );
function FCKOrderListCommand(){}
	FCKOrderListCommand.prototype = new FCKNamedCommand( 'InsertOrderedList' );

//image
function getSrc(oImage){
	var imgsrc = oImage.getAttribute("uploadpic") || oImage.getAttribute("oldsrc");
	if(imgsrc == null){
		imgsrc = oImage.getAttribute("src");
		//对直接引用web上的图片需要先上传再处理
		if(imgsrc.indexOf("W0") >=0){
			var sGroup = imgsrc.split("/");
			imgsrc = sGroup[sGroup.length - 1];
			if(imgsrc.indexOf("=") > 0)
				imgsrc = imgsrc.split("=")[imgsrc.split("=").length - 1];
		}
	}
	return imgsrc;
}
var FCKImageCommand = function(){
	this.Name = 'ImageToAppendix' ;
}
FCKImageCommand.prototype.Execute = function(){
	var oImage = FCK.Selection.GetSelectedElement() ;
	if ( oImage && oImage.tagName != 'IMG') return;
	var imgsrc = getSrc(oImage);
	var alt = oImage.getAttribute("alt") || oImage.getAttribute("title");
	removeFocus();
	(top.actualTop||top).createImageAppendix(imgsrc,alt);
}
function removeFocus(){
	//修正焦点遮不住的问题
	var range = FCK.EditorDocument.body.createTextRange();
	range.collapse(false); 
	range.moveEnd('character'); 
	range.select();
}
FCKCommands.RegisterCommand( 'ImageToAppendix', new FCKImageCommand()) ;
//image to photoLib
var FCKImageToLibCommand = function(){
	this.Name = 'ImageToPhototLib' ;
}
FCKImageToLibCommand.prototype.Execute = function(){
	var oImage = FCK.Selection.GetSelectedElement() ;
	if ( oImage && oImage.tagName != 'IMG') return;
	oImage.setAttribute("fromphoto",1);
	var imgsrc = getSrc(oImage);
	removeFocus();
	(top.actualTop||top).uploadToPhotoLib(oImage,imgsrc);
}
FCKCommands.RegisterCommand( 'ImageToPhototLib', new FCKImageToLibCommand()) ;
// Page Breaks
FCKPageBreakCommand.prototype.Execute = function(){
	var e = FCK.EditorDocument.createElement('TRS_PAGE_SEPARATOR') ;
	var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__PageBreak', e ) ;
	oFakeImage = FCK.InsertElement( oFakeImage ) ;
}
var FCKDocumentProcessor_CreateFake = function( fakeClass, realElement ){
	var oSpan = FCK.CreateElement( 'SPAN' ) ;
	oSpan.setAttribute( '_fckfakelement', 'true', 0 ) ;
	oSpan.setAttribute( '_fckrealelement', FCKTempBin.AddElement( realElement ), 0 ) ;
	return oSpan ;
}
FCKPageBreaksProcessor.ProcessDocument = function( document ){
	var aPageSeps = document.getElementsByTagName( 'TRS_PAGE_SEPARATOR' ) ;
	var ePageSep ;
	var i = aPageSeps.length - 1 ;
	while ( i >= 0 && ( ePageSep = aPageSeps[i--])){
		var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__PageBreak',
			ePageSep.cloneNode(true) ) ;
		ePageSep.parentNode.insertBefore( oFakeImage, ePageSep ) ;
		ePageSep.parentNode.removeChild( ePageSep ) ;
	}
}
FCKToolbarSpecialCombo.prototype.RefreshState = function(){
	// Gets the actual state.
	var eState ;
	if ( FCK.EditMode == FCK_EDITMODE_SOURCE && ! this.SourceView )
		eState = FCK_TRISTATE_DISABLED ;
	else{
		var sValue = FCK.ToolbarSet.CurrentInstance.Commands
			.GetCommand( this.CommandName ).GetState() ;
		if ( sValue != FCK_TRISTATE_DISABLED ){
			eState = FCK_TRISTATE_ON ;
			if ( this.RefreshActiveItems ){
				this.RefreshActiveItems( this._Combo, sValue ) ;
			}
			else{
				if ( this._LastValue != sValue ){
					this._LastValue = sValue ;
					FCKToolbarSpecialCombo_RefreshActiveItems( this._Combo, sValue ) ;
				}
			}
		}
		else{
			eState = FCK_TRISTATE_DISABLED ;
		}
	}
	// If there are no state changes then do nothing and return.
	if ( eState == this.State ) return ;
	if ( eState == FCK_TRISTATE_DISABLED ){
		this._Combo.DeselectAll() ;
		this._Combo.SetLabel( '' ) ;
	}
	// Sets the actual state.
	this.State = eState ;
	// Updates the graphical state.
	this._Combo.SetEnabled( eState != FCK_TRISTATE_DISABLED ) ;
}
FCKToolbarFontSizeCombo.prototype.RefreshActiveItems = function( combo, value ){
	// Clear the actual selection.
	combo.DeselectAll() ;
	try{
		value = FCK.TRSExtend.getFontSize() ;
	}catch(err){}
	if(value){
		combo.SelectItem( value ) ;
		// Set the combo label to the first style in the collection.
		combo.SetLabelById( value ) ;
	}
	else
		combo.SetLabel('') ;
}
FCKFlashProcessor.RefreshView = function( placholderImage, originalEmbed ){
	if ( originalEmbed.width > 0 )
		placholderImage.style.width = FCKTools.ConvertHtmlSizeToStyle( originalEmbed.width ) ;
	else{
		placholderImage.style.width = '' ;
	}
	if ( originalEmbed.height > 0 )
		placholderImage.style.height = FCKTools.ConvertHtmlSizeToStyle( originalEmbed.height ) ;
	else{
		placholderImage.style.height = '' ;
	}
}
FCKFlashProcessor.ProcessDocument = function( document ){
	var aEmbeds = document.getElementsByTagName( 'EMBED' ) ;
	var oEmbed ;
	var i = aEmbeds.length - 1 ;
	while ( i >= 0 && ( oEmbed = aEmbeds[i--] ) ){
		// IE doesn't return the type attribute with oEmbed.type or oEmbed.getAttribute("type") 
		// But it turns out that after accessing it then it doesn't gets copied later
		var oType = oEmbed.attributes[ 'type' ] || {};
		// Check the extension and the type. Now it should be enough with just the type
		// Opera doesn't return oEmbed.src so oEmbed.src.EndsWith will fail
		if (!( (oEmbed.src && oEmbed.src.EndsWith( '.swf', true ))
				|| ( oType && oType.nodeValue == 'application/x-shockwave-flash' )
				|| (oEmbed.getAttribute( 'mediatype' )!=null)))continue;
		var oCloned = oEmbed.cloneNode( true ) ;
		if (FCKBrowserInfo.IsIE) {
			var aAttributes = [ 'scale', 'play', 'loop', 'menu', 'wmode',
			'quality', 'autostart', 'mediatype', 'uploadpic', 'ignore', 'flashvars', 'title'] ;
			for ( var iAtt = 0 ; iAtt < aAttributes.length ; iAtt++ ){
				var sAttrName = aAttributes[iAtt];
				var oAtt = oEmbed.getAttribute( sAttrName );
				if ( oAtt ) {
					oCloned.setAttribute( sAttrName, oAtt );
					if( oAtt != oCloned.getAttribute( sAttrName )){
						//wenyh@2011-09-15 fix the attributes obfuse in IE8+WinXP env
						oCloned[sAttrName] = oAtt;
					}
				}
			}
			// It magically gets lost after reading it in oType
			oCloned.setAttribute( 'type', oType.nodeValue || "") ;
		}

		var oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oCloned ) ;
		oImg.setAttribute( '_fckflash', 'true', 0 ) ;
		if( oEmbed.getAttribute("title") )
			oImg.title = oEmbed.getAttribute("title");
		FCKFlashProcessor.RefreshView( oImg, oEmbed ) ;

		oEmbed.parentNode.insertBefore( oImg, oEmbed ) ;
		oEmbed.parentNode.removeChild( oEmbed ) ;
	}
}
var FCKVideoProcessor = FCKDocumentProcessor.AppendNew() ;
FCKVideoProcessor.ProcessDocument = function( document ){
	var aEmbedDivs = document.getElementsByTagName( 'DIV' ) ;
	var oEmbedDiv ;
	var i = aEmbedDivs.length - 1 ;
	while ( i >= 0 && ( oEmbedDiv = aEmbedDivs[i--] ) ){
		if ( oEmbedDiv.getAttribute('__fckvideo',2)!='true' )continue;
		var oCloned = oEmbedDiv.cloneNode( true ) ;

		var oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oCloned ) ;
		//fjh@2007-11-15 去掉右键中的“多媒体属性”
		oImg.setAttribute( '_fckvideo', 'true', 0 ) ;
		//fjh@2007-11-15 重置,以显示缩略图和提示
		oImg.title = oEmbedDiv.title;
		oImg.src = oEmbedDiv.imgurl;
		FCKFlashProcessor.RefreshView( oImg, oEmbedDiv ) ;
		oEmbedDiv.parentNode.insertBefore( oImg, oEmbedDiv ) ;
		oEmbedDiv.parentNode.removeChild( oEmbedDiv ) ;
	}
}
FCKTableHandler.SplitCell = function(){
	// Check that just one cell is selected, otherwise return.
	var aCells = FCKTableHandler.GetSelectedCells() ;
	if ( aCells.length != 1 )
		return ;
	var aMap = this._CreateTableMap( aCells[0].parentNode.parentNode ) ;
	var iCellIndex = FCKTableHandler._GetCellIndexSpan( aMap,
			aCells[0].parentNode.rowIndex, aCells[0] ) ;
	var aCollCells = this._GetCollumnCells( aMap, iCellIndex ) ;
	for ( var i = 0 ; i < aCollCells.length ; i++ ){
		if ( aCollCells[i] == aCells[0] ){
			var oNewCell = this.InsertCell( aCells[0] ) ;
			oNewCell.innerHTML = '&nbsp;';
			if ( !isNaN( aCells[0].rowSpan ) && aCells[0].rowSpan > 1 )
				oNewCell.rowSpan = aCells[0].rowSpan ;
		}
		else{
			if ( isNaN( aCollCells[i].colSpan ) )
				aCollCells[i].colSpan = 2 ;
			else
				aCollCells[i].colSpan += 1 ;
		}
	}
}
FCKTableHandler.ClearRow = function( tr ){
	// Get the array of row's cells.
	var aCells = tr.cells ;
	// Replace the contents of each cell with "nothing".
	for ( var i = 0 ; i < aCells.length ; i++ ) {
		if ( FCKBrowserInfo.IsGecko )
			aCells[i].innerHTML = GECKO_BOGUS ;
		else
			aCells[i].innerHTML = '&nbsp;' ;
	}
}
FCKTableHandler.InsertColumn = function(){
	// Get the cell where the selection is placed in.
	var oCell = FCKSelection.MoveToAncestorNode('TD') 
		|| FCKSelection.MoveToAncestorNode('TH') ;
	if ( !oCell ) return ;
	// Get the cell's table.
	var oTable = FCKTools.GetElementAscensor( oCell, 'TABLE' ) ;
	// Get the index of the column to be created (based on the cell).
	var iIndex = oCell.cellIndex + 1 ;
	// Loop throw all rows available in the table.
	for ( var i = 0 ; i < oTable.rows.length ; i++ ){
		// Get the row.
		var oRow = oTable.rows[i] ;
		// If the row doens't have enought cells, ignore it.
		if ( oRow.cells.length < iIndex )continue ;
		oCell = oRow.cells[iIndex-1].cloneNode(false) ;
		if ( FCKBrowserInfo.IsGecko )
			oCell.innerHTML = GECKO_BOGUS ;
		else
			oCell.innerHTML = "&nbsp;" ;
		// Get the cell that is placed in the new cell place.
		var oBaseCell = oRow.cells[iIndex] ;
		// If the cell is available (we are not in the last cell of the row).
		if ( oBaseCell )
			oRow.insertBefore( oCell, oBaseCell ) ; // Insert the new cell just before of it.
		else
			oRow.appendChild( oCell ) ;  // Append the cell at the end of the row.
	}
}
FCKTableHandler.InsertCell = function( cell ){
	// Get the cell where the selection is placed in.
	var oCell = cell ? cell : FCKSelection.MoveToAncestorNode("TD") ;
	if ( !oCell ) return ;
	// Create the new cell element to be added.
	var oNewCell = FCK.EditorDocument.createElement("TD");
	if ( FCKBrowserInfo.IsGecko )
		oNewCell.innerHTML = GECKO_BOGUS ;
	else
		oNewCell.innerHTML = "&nbsp;" ;
	// If it is the last cell in the row.
	if ( oCell.cellIndex == oCell.parentNode.cells.length - 1 ){
		// Add the new cell at the end of the row.
		oCell.parentNode.appendChild( oNewCell ) ;
	}
	else{
		// Add the new cell before the next cell (after the active one).
		oCell.parentNode.insertBefore( oNewCell, oCell.nextSibling ) ;
	}
	return oNewCell ;
}
FCKStyleCommand.prototype.GetState = function(){
	if ( !FCK.EditorDocument )
		return FCK_TRISTATE_DISABLED ;
	var oSelection = FCK.EditorDocument.selection ;
	if ( FCKSelection.GetType() != 'Control' )return FCK_TRISTATE_OFF;
	var e = FCKSelection.GetSelectedElement() ;
	if ( !e )return FCK_TRISTATE_OFF;
	var sTagName = e.tagName;
	if(sTagName=='IMG'&&e.getAttribute( '_fckfakelement' ))return FCK_TRISTATE_DISABLED;
	return this.StylesLoader.StyleGroups[ sTagName ] ? FCK_TRISTATE_OFF : FCK_TRISTATE_DISABLED ;
}
FCKStyleCommand.prototype.Execute = function( styleName, styleComboItem ){
	FCKUndo.SaveUndoStep() ;
	try{
		if ( styleComboItem.Selected )
			styleComboItem.Style.RemoveFromSelection() ;
		else
			styleComboItem.Style.ApplyToSelection() ;
	}catch(err){
		alert(FCKLang.NOTSUPPORT);
	}
	FCKUndo.SaveUndoStep() ;
	FCK.Focus() ;
	FCK.Events.FireEvent( "OnSelectionChange" ) ;
}

FCKToolbarItems.GetItem0 = FCKToolbarItems.GetItem;
FCKToolbarItems.GetItem = function( itemName ){
	if(itemName==null)return null;
	var oItem = FCKToolbarItems.LoadedItems[ itemName ] ;
	if ( oItem )return oItem ;
	switch ( itemName ){
		case 'PasteWord' :
			oItem = new FCKToolbarButton( 'PasteWord', FCKLang.PasteWord, null, null,
				false, true, 11 ) ;
			break ;
		case 'Flash' : 
			oItem = new FCKToolbarButton( 'Flash', FCKLang.InsertFlashLbl, FCKLang.InsertFlash,
				null, false, true, 84 ) ; 
			break ;
		case 'LineHeight' ://--添加行距功能 2010-9-6 by HDG
			oItem=new FCKToolbarLineHeightCombo();
			break;
		case 'Find':
			oItem = new FCKToolbarButton('Find',FCKLang.Find,null,null,true,null,16);break;
		case 'Replace':
			oItem = new FCKToolbarButton('Replace',FCKLang.Replace,null,null,true,null,17);break;
		default : 
			oItem = FCKToolbarItems.GetItem0(itemName);
	}
	return oItem;
}

var FCKToolbarLineHeightCombo=function(A,B){
	this.CommandName='LineHeight';
	this.Label=this.GetLabel();
	this.Tooltip=A?A:this.Label;
	this.Style=B?B:2;
};
FCKToolbarLineHeightCombo.prototype=new FCKToolbarSpecialCombo;
FCKToolbarLineHeightCombo.prototype.GetLabel=function(){
	return FCKLang.LineHeight;
};
FCKToolbarLineHeightCombo.prototype.CreateItems=function(A){
	A.FieldWidth=70;
	var B=FCKConfig.LineHeights.split(';');
	for (var i=0;i<B.length;i++)
		this._Combo.AddItem(B[i],'<font style="line-height: '+B[i]+'">'+B[i]+'</font>');
}
var FCKLineHeightCommand=function(){
	this.Name='LineHeight';
}
FCKLineHeightCommand.prototype.Execute=function(A){
	if (A==null||A==''){
		FCK.ExecuteNamedCommand('LineHeight','100%');
	}else 
		FCK.ExecuteNamedCommand('LineHeight',A);
}
FCKLineHeightCommand.prototype.GetState=function(){
	return FCK.GetNamedCommandValue('LineHeight');
}


var FCKTableProcessor = FCKDocumentProcessor.AppendNew() ;
FCKTableProcessor.ProcessDocument = function( document ){
	//对表格边框不做任何处理
	return;
	var aEmbedDivs = document.getElementsByTagName( 'TABLE' ) ;
	var oEmbedDiv ;
	var i = aEmbedDivs.length - 1 ;
	while ( i >= 0 && ( oEmbedDiv = aEmbedDivs[i--] ) ){
		if (!(parseInt(oEmbedDiv.style.borderWidth) == 0
			|| oEmbedDiv.style.borderStyle=='none'))continue;
		oEmbedDiv.style.borderRight = oEmbedDiv.style.borderLeft =
		oEmbedDiv.style.borderTop = oEmbedDiv.style.borderBottom
			= oEmbedDiv.style.border
			= oEmbedDiv.border+'px solid '+(oEmbedDiv.borderColor||'black');
	}
}
FCK.TrsExtension = {};
FCK.TrsExtension.OnDoubleClickTable = function( oElement ){
	if ( oElement.tagName != 'TABLE')return;
	FCKCommands.GetCommand('Table').Execute() ;
}
FCK.TrsExtension.OnDoubleClickImg = function( oElement ){
	if ( oElement.tagName != 'IMG')return;
	var sFckFake = oElement.getAttribute( '_fckfakelement' );
	var sCommand = 'Image';
	if(!sFckFake){
		sCommand = 'Image';
	}
	else if(oElement.getAttribute( '_fckanchor' )){
		sCommand = 'Anchor';
	}
	else if(oElement.getAttribute( '_fckflash' )){
		sCommand = 'Flash';
	}
	else return;
	FCKCommands.GetCommand(sCommand).Execute() ;
}
FCK.TrsExtension.OnDoubleClickA = function( oElement ){
	if ( oElement.tagName != 'A')return;
	if(Element.hasClassName(oElement, 'FCK__AnchorC')
		|| !oElement.getAttribute( 'href',2 )){
		FCKCommands.GetCommand('Anchor').Execute() ;
	}
	else
		FCKCommands.GetCommand('Link').Execute() ;
}
FCK.RegisterDoubleClickHandler( FCK.TrsExtension.OnDoubleClickImg, 'IMG' ) ;
FCK.RegisterDoubleClickHandler( FCK.TrsExtension.OnDoubleClickA, 'A' ) ;
FCK.RegisterDoubleClickHandler( FCK.TrsExtension.OnDoubleClickTable, 'TABLE' ) ;
function FCKToolbarSet_Create( overhideLocation ){
	var oToolbarSet ;
	document.getElementById( 'xToolbarRow' ).style.display = '' ;
	oToolbarSet = new FCKToolbarSet( document ) ;
	oToolbarSet.CurrentInstance = FCK ;
	FCK.AttachToOnSelectionChange( oToolbarSet.RefreshItemsState ) ;
	return oToolbarSet ;
}

FCKToolbarButton.prototype.RefreshState = function(){
	var eState = FCK_TRISTATE_DISABLED;
	if ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ){
		// Gets the actual state.
		eState = FCK.ToolbarSet.CurrentInstance.Commands.GetCommand( this.CommandName ).GetState() ;
	}
	// If there are no state changes than do nothing and return.
	if ( eState == this._UIButton.State ) return ;
	// Sets the actual state.
	this._UIButton.ChangeState( eState ) ;
}
FCKTextColorCommand.prototype.GetState = function(){
	if ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ){
		return FCK_TRISTATE_OFF ;
	}
	else{
		return FCK_TRISTATE_DISABLED ;
	}
}
// Link Anchors
if ( FCKBrowserInfo.IsIE || FCKBrowserInfo.IsOpera ){
	FCKAnchorsProcessor.ProcessDocument = function( document ){
		var aLinks = document.getElementsByTagName( 'A' ) ;
		var oLink ;
		var i = aLinks.length - 1 ;
		while ( i >= 0 && ( oLink = aLinks[i--] ) ){
			if(oLink.name=='AnchorAddByWCM'){
				if(Element.hasClassName(oLink,'FCK__ContentLink'))continue;
				Element.addClassName(oLink,'FCK__ContentLink');
				continue;
			}
			// If it is anchor. Doesn't matter if it's also a link (even better: we show that it's both a link and an anchor)
			if ( oLink.name.length <= 0 )continue;
			//if the anchor has some content then we just add a temporary class
			if ( oLink.innerHTML !== '' ){
				if ( !FCKBrowserInfo.IsIE )continue;
				if(Element.hasClassName(oLink,'FCK__AnchorC'))continue;
				Element.addClassName(oLink,'FCK__AnchorC');
				oLink.style.cssText = "color: #000000;";
				continue;
			}
			var oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Anchor',
				oLink.cloneNode(true) ) ;
			oImg.setAttribute( '_fckanchor', 'true', 0 ) ;
			oLink.parentNode.insertBefore( oImg, oLink ) ;
			oLink.parentNode.removeChild( oLink ) ;
		}
	}
}

FCK.SetHTML0 = FCK.SetHTML;
FCK.SetHTML = function( html, resetIsDirty ){
	//对于word粘贴过来的数据，需要调整编辑器的展现形式
	var exg = /^<div class=\"TRS_PreAppend\"[^>]*>/img;
	if(exg.test(html)){
		if(!(window.Attributes && window.Attributes["isForWord"]=="1")) {
			FCK.RemoveEditorCss();
		}
		html = html.replace(/^<div class=\"TRS_PreAppend\"([^>]*?)width([\s:])([^>]*?)>/img,"<div class=\"TRS_PreAppend\"$1width-trs$2$3>");
		if(FCK.EditMode == FCK_EDITMODE_WYSIWYG){
			var ele = parent.frames[0].$("xEditingArea");
			var oCookie = FCK.loadCookie();
			if(ele && oCookie.RemovePageWidth != undefined && oCookie.RemovePageWidth == "0"){
				var widthExg = /^<div class=\"TRS_PreAppend\"[^>]*?temp:([^;"]*?)[;"]/img;
				var width = widthExg.exec(html);
				if(width && width[1])
					ele.style.width = width[1];							
			}
		}
	}
	if (FCK.EditMode != FCK_EDITMODE_WYSIWYG ){
		FCKEditingArea_Unload.call(FCK.EditorWindow);
		FCK.EditorWindow = null ;
		FCK.EditorDocument = null ;
		if(FCK.EditingArea){
			FCK.EditingArea.Window = null;
			FCK.EditingArea.Document = null;
		}
	}
	FCK.SetHTML0(html, resetIsDirty);
}
FCK.loadCookie = function(){
	var myCookies = document.cookie.split(";");
	var oCookieData = {};
	for(var i=0; i<myCookies.length; i++){
		var cookiePair = myCookies[i].split("=");
		if(cookiePair[0].trim()=='expires')continue;
		oCookieData[cookiePair[0].trim()] = unescape(cookiePair[1]);
	}
	return oCookieData;
}
FCK.clearCookie = function(_sCookieName){
	var myCookie = '';
	var sSaveValue = null;
	myCookie += _sCookieName+"=false";
	var expires = new Date();
	expires.setTime(expires.getTime() - 1);
	if(document.domain =="localhost")
		myCookie += "; path=/; expires=" + expires.toGMTString() + ";";
	else{
		myCookie += "; path=/; expires=" + expires.toGMTString() + ";domain=" + document.domain;
	}
	document.cookie = myCookie;
}
FCK.setCookie = function(_sCookieName,_sCookieValue){
	var myCookie = '';
	var sSaveValue = null;
	sSaveValue = escape(_sCookieValue);
	myCookie += _sCookieName+"="+sSaveValue+"";
	var expires = new Date();
	expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000 * 30));
	if(document.domain =="localhost")
		myCookie += "; path=/; expires=" + expires.toGMTString()+";";
	else
		myCookie += "; path=/; expires=" + expires.toGMTString()+";domain="+document.domain;
	document.cookie = myCookie;
}
FCK.isBlankContent = function(_sHtml){
	return _sHtml==null || _sHtml==''
		||(/^\s*(<(div|p)>)?(\s|\n|\r|&nbsp;|&nbsp)*(<\/\2>)?\s*$/i).test(_sHtml);
}
FCKTools.RemoveOuterTags = function( e ){
	try{
		e.insertAdjacentHTML( 'beforeBegin', e.innerHTML ) ;
		e.parentNode.removeChild( e ) ;
	}catch(err){
		for(var n=e.childNodes.length-1;n>=0;n--){
			e.parentNode.insertBefore(e.childNodes[n],e.nextSibling);
		}
		e.parentNode.removeChild( e ) ;
	}
}
function _FCK_KeystrokeHandler_OnKeystroke( keystroke, keystrokeValue ){
	if ( FCK.Status != FCK_STATUS_COMPLETE )
		return false ;
	if ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ){
		if ( keystrokeValue == 'Paste' ){
			return !FCK.Events.FireEvent( 'OnPaste' ) ;
		}else if ( keystrokeValue == 'TabInsert' ){
			FCK.InsertHtml( window.FCKTabHTML ) ;
			return true;
		}
		else if( keystrokeValue == 'Delete' ){
			FCKTableHandler.DeleteCellContents();
			return false;
		}
	}
	else{
		// In source mode, some actions must have their default behavior.
		if ( keystrokeValue.Equals( 'Paste', 'Undo', 'Redo', 'SelectAll', 'TabInsert', 'Delete' ) )
			return false ;
	}
	// The return value indicates if the default behavior of the keystroke must
	// be cancelled. Let's do that only if the Execute() call explicitelly returns "false".
	var oCommand = FCK.Commands.GetCommand( keystrokeValue ) ;
	return ( oCommand.Execute.apply( oCommand, FCKTools.ArgumentsToArray( arguments, 2 ) )
		!== false ) ;
}
FCKTableHandler.getTdObj = function(innerHtml, sel){
	// find text outside of tags
	var h = 0, k = 0;
	do{
		k = innerHtml.indexOf('<',h);
		if (k < 0){
			k = innerHtml.length; // the entire html
		}
		if (h < k){ // found some text, get the parent td
			text = FCKTools.HTMLDecode(innerHtml.substring(h, k));
			text = text.replace(/[\r\n]/, ''); // sometimes for some strange reason the text will have '\r\n' in it, then the sc.findText(text) will fail
			sc = sel.duplicate();
			if(text != '' && sc.findText(text)){
				return FCKTools.GetElementAscensor(sc.parentElement(), "TD"); 
			}
		}
		h = innerHtml.indexOf('>', k);
		if (h < 0){
			// we started with '<' but can't find '>', stop
			return null; // error
		}
		h++;
	}while (k < innerHtml.length);
	return null;
}
FCKTableHandler.DeleteCellContents = function() {
	// var oSel = Core.State.GetSelection();
	var oSel = FCK.EditorDocument.selection ;
	// var aCells = FCKTableHandler.GetSelectedCells() ;
	if(oSel.type != "Text")
		return false;
	oSel = oSel.createRange();
	var html = oSel.htmlText;
	// if html has table tag or has no td tag, ignore
	var htmlLowerCase = html.toLowerCase(); // used for finding tags
	if (htmlLowerCase.indexOf('<table') >= 0 || htmlLowerCase.indexOf('</table') >= 0 ||
			htmlLowerCase.indexOf('<td') < 0){
		return false;
	}
	var td1 = null, innerHtml1 = null, text = '';
	var tdTagBegin = 0, tdHtmlBegin = 0, tdHtmlEnd = 0, td1IsLastCell = false, tdTagNext = 0;
	tdTagBegin = htmlLowerCase.indexOf('<td');
	while (td1 == null && tdTagBegin >= 0){ // TODO 
		tdHtmlBegin = htmlLowerCase.indexOf('>', tdTagBegin) + 1;    
		tdHtmlEnd = htmlLowerCase.indexOf('</td', tdHtmlBegin);
		innerHtml1 = html.substring(tdHtmlBegin, tdHtmlEnd);
		td1 = this.getTdObj(innerHtml1, oSel);
		tdTagBegin = htmlLowerCase.indexOf('<td', tdHtmlEnd); 
	}
	if (td1 == null){
		return false; //error
	}
	if( tdTagBegin >= 0){
		tdTagNext = htmlLowerCase.indexOf('<td', tdTagBegin + 1);
	}else{
		tdTagNext = -1;
		td1IsLastCell = true;
	}
	var tr1 = FCKTools.GetElementAscensor(td1,"TR");
	var table = FCKTools.GetElementAscensor(tr1,"TABLE");
	var rows = table.rows;
	var r = 0, c = 0;
	// for each td tag found
	for (r = tr1.rowIndex; r < rows.length; r++){
		var start = (r == tr1.rowIndex) ? td1.cellIndex + 1 : 0;
		for (c = start; c < rows[r].cells.length && tdTagNext >= 0; c++){
			rows[r].cells[c].innerHTML = '';
			tdTagBegin = tdTagNext;
			tdTagNext = htmlLowerCase.indexOf('<td', tdTagBegin + 1);
		}
		if (tdTagNext < 0){
			break; // do this here since we don't want to increment r before exiting
		}
	}
	// delete the selected content in td1
	if ( td1IsLastCell ){//this case is bug 6392
		td1.innerHTML = td1.innerHTML.substring(innerHtml1.length, td1.innerHTML.length);
	}else{
		td1.innerHTML = td1.innerHTML.substring(0, td1.innerHTML.lastIndexOf(innerHtml1));
	}
	// take care of last cell
	if (c == rows[r].cells.length){
		r++;
		c = 0;
		if( r >= rows.length ){  // r was last row
			return true;
		}
	}
	tdHtmlBegin = htmlLowerCase.indexOf('>', tdTagBegin) + 1;    
	tdHtmlEnd = htmlLowerCase.indexOf('</td', tdHtmlBegin);
	// (tdHtmlEnd - tdHtmlBegin) is the length of the string to be deleted
	rows[r].cells[c].innerHTML = rows[r].cells[c].innerHTML.substring(tdHtmlEnd - tdHtmlBegin); 
	return true;
}
function GetWYSIWYGSelection() {
	if (FCK.EditorWindow && typeof FCK.EditorWindow.getSelection != "undefined") {
		return FCK.EditorWindow.getSelection();
	}
	if (FCK.EditorDocument && typeof FCK.EditorDocument.selection != "undefined") {
		return FCK.EditorDocument.selection;
	}
	return null;
}
function IsDefined(obj, field) {
	return typeof obj[field] != "undefined";
}
function GetWYSIWYGSelectionRange() {
	var selection = GetWYSIWYGSelection();
	var ua = navigator.userAgent.toLowerCase();
	var isIE9 = ua.indexOf("msie 9") > -1;
	if ((IsDefined(selection, "rangeCount") &&
			IsDefined(selection, "getRangeAt")) && !isIE9) {
		// Gecko
		if (selection.rangeCount == 0) {
			return null;
		}
		return selection.getRangeAt(selection.rangeCount - 1).cloneRange();
	} else if (IsDefined(selection, "createRange")) {
		// IE
		var range = selection.createRange();
		if (IsDefined(range, "parentElement")) {
			// It's a textRange.
			return range;
		} else if (IsDefined(range, "length")) {
			// It's a controlRange.
			// For now, just convert this into a textRange.
			// This means images in control mode will switch to selected images.
			var node = range(0);
			var bodyNode = FCK.EditorDocument.body;
			range = bodyNode.createTextRange();
			range.moveToElementText(node);
			return range;
		}
	}
	// If we get to here, we're probably in Safari, which doesn't completely
	// implement the selection object, so, return what we have.
	if (IsDefined(selection, "baseNode") && isSafari) {
		// Safari has bugs with the selection range, the offsets might be wrong, or out
		// of order, depending on how the user made them. Check here, and fix it.
		var result = {
			baseOffset: selection.baseOffset,
			baseNode: selection.baseNode,
			extentOffset: selection.extentOffset,
			extentNode: selection.extentNode,
			text: ("" + selection)
		};
		return result;
	}
	return selection;
}
function WY_isRangeComplex_(selRange) {
	var frag = selRange.cloneContents(); 
	for (var i = 0; i < frag.childNodes.length; i++) {
		if (!IsDefined(frag.childNodes[i], "tagName"))continue;
		var t = frag.childNodes[i].tagName.toLowerCase();
		if (t == "div" || t == "table" || t == "ul" || t == "ol" || t == "p")
			return true;
	}
	return false;
}
function SelectNode(node) {
	if (FCK.EditorDocument.createRange) {
		var range = FCK.EditorDocument.createRange();
		range.selectNodeContents(node);
		var selection = GetWYSIWYGSelection();
		selection.removeAllRanges();
		selection.addRange(range);
	} else {
		var range = FCK.EditorDocument.body.createTextRange();
		range.moveToElementText(node);
		range.select();
	}
	return false;
}
function RemoveHTMLMarkup(toStrip) {
	// Strip all html
	var strN = toStrip.replace(/(<([^>]+)>)/ig, "");
	// Replace carriage returns and line feeds
	strN = strN.replace(/\r\n/g, " ");
	strN = strN.replace(/\n/g, " ");
	strN = strN.replace(/\r/g, " ");
	strN = strN.replace(/&nbsp(;)?/g, " ");
	return strN;
}
var FCKFormatEditedHtml = {};
FCKFormatEditedHtml.Processers = [];
FCKFormatEditedHtml.AppendNew = function(_oProcesser){
	FCKFormatEditedHtml.Processers.push(_oProcesser);
}
FCK.FormatEditedHtml = function(_sHtml){
	for (var i = 0; i < FCKFormatEditedHtml.Processers.length; i++){
		var oProcesser = FCKFormatEditedHtml.Processers[i];
		try{
			_sHtml = oProcesser(_sHtml);
		}catch(err){
		}
	}
	return _sHtml;
}
var FCKBeforeStartEditor = {};
FCKBeforeStartEditor.Processers = [];
FCKBeforeStartEditor.AppendNew = function(_oProcesser){
	FCKBeforeStartEditor.Processers.push(_oProcesser);
}
FCK.BeforeStartEditor = function(_sHtml){
	for (var i = 0; i < FCKBeforeStartEditor.Processers.length; i++){
		var oProcesser = FCKBeforeStartEditor.Processers[i];
		try{
			_sHtml = oProcesser(_sHtml);
		}catch(err){}
	}
	return _sHtml;
}
FCK.GetLinkedFieldValue = function(){
	var sHTML = this.LinkedField.value ;
	sHTML = (FCK.BeforeStartEditor)?FCK.BeforeStartEditor(sHTML):sHTML;
	sHTML = (FCK.FormatEditedHtml)?FCK.FormatEditedHtml(sHTML):sHTML;
	return sHTML;
}
//设计/代码 切换
FCK.SwitchEditMode = function( noUndo ){
	var bIsWysiwyg = ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ) ;
	// Save the current IsDirty state, so we may restore it after the switch.
	var bIsDirty = FCK.IsDirty() ;
	var sHtml ;
	// Update the HTML in the view output to show.
	if ( bIsWysiwyg ){
		if ( !noUndo && FCKBrowserInfo.IsIE )
			FCKUndo.SaveUndoStep() ;
		sHtml = FCK.GetXHTML( FCKConfig.FormatSource ) ;
		if ( sHtml == null )
			return false ;
	}
	else{
		sHtml = this.EditingArea.Textarea.value ;
		sHtml = (FCK.FormatEditedHtml)?FCK.FormatEditedHtml(sHtml):sHtml;
	}
	FCK.EditMode = bIsWysiwyg ? FCK_EDITMODE_SOURCE : FCK_EDITMODE_WYSIWYG ;
	FCK.SetHTML( sHtml, !bIsDirty ) ;
	// Set the Focus.
	FCK.Focus() ;
	// Update the toolbar (Running it directly causes IE to fail).
	FCKTools.RunFunction( FCK.ToolbarSet.RefreshModeState, FCK.ToolbarSet ) ;
	return true ;
}

/*
 **关于文件保护 START**
  *caller : SetHTML
*/
FCKConfig.ProtectedSource.Protect = function(A) {
	function _Replace(protectedSource) {
		var B = FCKTempBin.AddElement(protectedSource);
		return '<!--{PS..' + B + '}-->';
	};
	for (var i = 0; i < this.RegexEntries.length; i++) {
		A = A.replace(this.RegexEntries[i], _Replace);
	};
	return A;
};
FCKConfig.ProtectedSource.Revert = function( html, clearBin ){
	function _Replace( m, opener, index ){
		var protectedValue = clearBin ? FCKTempBin.RemoveElement( index )
								: FCKTempBin.Elements[ index ] ;
		if(!protectedValue) return '';
		if(protectedValue==m)return protectedValue;
		// There could be protected source inside another one.
		if(FCKConfig.RemoveScript && protectedValue.match(/<script[\s\S]*?<\/script>/gi)
			&& protectedValue.indexOf('fck_')==-1){
			return '';
		}
		return FCKConfig.ProtectedSource.Revert( protectedValue, clearBin ) ;
	}
	return html.replace( /(<|&lt;)!--\{PS..(\d+)\}--(>|&gt;)/g, _Replace ) ;
}
var FCKTempBin = {
	Elements: [],
	AddElement: function(A) {
		var B = this.Elements.length;
		this.Elements[B] = A;
		return B;
	},
	RemoveElement: function(A) {
		var e = this.Elements[A];
		this.Elements[A] = null;
		return e;
	},
	Reset: function() {
		var i = 0;
		while (i < this.Elements.length) this.Elements[i++] = null;
		this.Elements.length = 0;
	}
};
/*
 **END
*/

function FCKIECleanup_Cleanup(){
	if ( !this._FCKCleanupObj )
		return ;
	var aItems = this._FCKCleanupObj.Items ;
	while ( aItems.length > 0 ){
		var oItem = aItems.pop() ;
		try{
			if ( oItem )oItem[1].call( oItem[0] ) ;
		}catch(err){
		//Just Skip it.
		}
	}
	this._FCKCleanupObj.Items = null;
	this._FCKCleanupObj = null ;
	if(window.FCK_Cleanup)window.FCK_Cleanup.call(FCK);
	if ( window.CollectGarbage )
		window.CollectGarbage() ;
}
function FCK_Cleanup(){
	try{
		FCKEditingArea_Unload.call(this.EditorWindow);
		if(this.EditingArea){
			this.EditingArea.Window = null;
			this.EditingArea.Document = null;
			this.EditingArea = null;
		}
		this.EditorWindow = null ;
		this.EditorDocument = null ;
	}catch(err){
		//Just Skip it.
	}
}
function FCKEditingArea_Cleanup(){
	this.TargetElement = null ;
	this.IFrame = null ;
	this.Document = null ;
	this.Textarea = null ;
	this._FCKToolbarPanelButton = null;
	if ( this.Window ){
		this.Window._FCKEditingArea = null ;
		this.Window = null ;
	}
}
function FCKMenuBlock_Cleanup(){
	this._Window = null ;
	this._ItemsTable = null ;
}
function FCKMenuItem_Cleanup(){
	this.MainElement = null ;
}
function FCKPanel_Cleanup(){
	this._Popup = null ;
	this._Window = null ;
	this.Document = null ;
	this.MainNode = null ;
}
function FCKSpecialCombo_Cleanup(){
	this._LabelEl = null ;
	this._OuterTable = null ;
	this._ItemsHolderEl = null ;
	if ( this.Items ){
		for ( var key in this.Items ){
			var eDiv = this.Items[key];
			if(eDiv){
				eDiv.onmouseover = eDiv.onmouseout = eDiv.onclick = null;
			}
			this.Items[key] = null ;
		}
		this.Items = null;
	}
	if ( this._Fields ){
		for ( var i=0,n=this._Fields.length;i<n;i++ ){
			var oField = this._Fields[i];
			if(oField){
				oField.SpecialCombo = null ;
				oField.onmouseover = oField.onmouseout = oField.onclick = null;
			}
		}
		this._Fields = null;
	}
	this._PanelBox = null ;
	this._Panel = null;
}
FCKSpecialCombo.prototype.Create = function( targetElement ){
	var oDoc = FCKTools.GetElementDocument( targetElement ) ;
	var eOuterTable = this._OuterTable
		= targetElement.appendChild( oDoc.createElement( 'TABLE' ) ) ;
	eOuterTable.cellPadding = 0 ;
	eOuterTable.cellSpacing = 0 ;
	eOuterTable.insertRow(-1) ;
	var sClass ,bShowLabel ;
	switch ( this.Style ){
		case FCK_TOOLBARITEM_ONLYICON :
			sClass = 'TB_ButtonType_Icon' ;
			bShowLabel = false;
			break ;
		case FCK_TOOLBARITEM_ONLYTEXT :
			sClass = 'TB_ButtonType_Text' ;
			bShowLabel = false;
			break ;
		case FCK_TOOLBARITEM_ICONTEXT :
			bShowLabel = true;
			break ;
	}
	if ( this.Caption && this.Caption.length > 0 && bShowLabel ){
		var oCaptionCell = eOuterTable.rows[0].insertCell(-1) ;
		oCaptionCell.innerHTML = this.Caption ;
		oCaptionCell.className = 'SC_FieldCaption' ;
	}
	if(!this._Fields)this._Fields = [];
	// Create the main DIV element.
	var oField = FCKTools.AppendElement( eOuterTable.rows[0].insertCell(-1), 'div' ) ;
	if ( bShowLabel ){
		oField.className = 'SC_Field' ;
		oField.style.width = this.FieldWidth + 'px' ;
		oField.innerHTML = ['<table width="100%" cellpadding="0" cellspacing="0" style="TABLE-LAYOUT: fixed;">',
			'<tbody><tr><td class="SC_FieldLabel"><label>&nbsp;</label></td>',
			'<td class="SC_FieldButton">&nbsp;</td></tr></tbody></table>'].join('') ;
		this._LabelEl = oField.getElementsByTagName('label')[0] ; // Memory Leak
		this._LabelEl.innerHTML = this.Label ;
	}
	else{
		oField.className = 'TB_Button_Off' ;
		oField.innerHTML = ['<table title="', this.Tooltip,
			'" class="', sClass, '" cellspacing="0" cellpadding="0" border="0">',
			'<tr>',
			'<td><img class="TB_Button_Padding" src="', FCK_SPACER_PATH, '" /></td>',
			'<td class="TB_Text">', this.Caption, '</td>',
			'<td><img class="TB_Button_Padding" src="', FCK_SPACER_PATH, '" /></td>',
			'<td class="TB_ButtonArrow">', '<img src="', FCKConfig.SkinPath,
			'images/toolbar.buttonarrow.gif" width="5" height="3"></td>',
			'<td><img class="TB_Button_Padding" src="', FCK_SPACER_PATH, '" /></td>',
			'</tr>',
		'</table>'].join('') ;
	}
	// Events Handlers
	oField.SpecialCombo = this ;
	oField.onmouseover = FCKSpecialCombo_OnMouseOver ;
	oField.onmouseout = FCKSpecialCombo_OnMouseOut ;
	oField.onclick = FCKSpecialCombo_OnClick ;
	this._Fields.push(oField);
	FCKTools.DisableSelection( this._Panel.Document.body ) ;
}
function FCKToolbar_Cleanup(){
	this.MainElement = null ;
	this.RowElement = null ;
	this.Items = null;
}
function FCKToolbarButtonUI_Cleanup(){
	// this.Icon = null;
	this._ToolbarButton = null;
	if ( this.MainElement ){
		this.MainElement._FCKButton = null ;
		this.MainElement.onclick = this.MainElement.onmouseover = this.MainElement.onmouseout = null;
		this.MainElement = null ;
	}
}
function FCKTextColorCommand_Cleanup(){
	if(!this._Items)return;
	for ( var i=0,n=this._Items.length;i<n;i++ ){
		var oItem = this._Items[i];
		if(!oItem)continue;
		oItem.Command = null ;
		oItem.onmouseover = oItem.onmouseout = oItem.onclick = null;
	}
	this._Items = null;
}
FCKTextColorCommand.prototype._CreatePanelBody = function( targetDocument, targetDiv ){
	if ( FCK.IECleanup )
		FCK.IECleanup.AddItem( this, FCKTextColorCommand_Cleanup ) ;
	function CreateSelectionDiv(){
		var oDiv = targetDocument.createElement( "DIV" ) ;
		oDiv.className = 'ColorDeselected' ;
		oDiv.onmouseover = FCKTextColorCommand_OnMouseOver ;
		oDiv.onmouseout = FCKTextColorCommand_OnMouseOut ;
		return oDiv ;
	}
	// Create the Table that will hold all colors.
	var oTable = targetDiv.appendChild( targetDocument.createElement( "TABLE" ) ) ;
	oTable.className = 'ForceBaseFont' ; // Firefox 1.5 Bug.
	oTable.style.tableLayout = 'fixed' ;
	oTable.cellPadding = 0 ;
	oTable.cellSpacing = 0 ;
	oTable.border = 0 ;
	oTable.width = 150 ;
	var oCell = oTable.insertRow(-1).insertCell(-1) ;
	oCell.colSpan = 8 ;
	this._Items = [];
	// Create the Button for the "Automatic" color selection.
	var oDiv = oCell.appendChild( CreateSelectionDiv() ) ;
	oDiv.innerHTML =['<table cellspacing="0" cellpadding="0" width="100%" border="0">',
		'<tr>',
		'<td><div class="ColorBoxBorder">',
		'<div class="ColorBox" style="background-color: #000000"></div></div></td>',
		'<td nowrap width="100%" align="center">', FCKLang.ColorAutomatic, '</td>',
		'</tr>',
		'</table>'].join('') ;
	oDiv.Command = this ;
	oDiv.onclick = FCKTextColorCommand_AutoOnClick ;
	this._Items.push(oDiv);
	// Create an array of colors based on the configuration file.
	var aColors = FCKConfig.FontColors.toString().split(',') ;
	// Create the colors table based on the array.
	var iCounter = 0 ;
	while ( iCounter < aColors.length ){
		var oRow = oTable.insertRow(-1) ;
		for ( var i = 0 ; i < 8 && iCounter < aColors.length ; i++, iCounter++ ){
			oDiv = oRow.insertCell(-1).appendChild( CreateSelectionDiv() ) ;
			oDiv.Color = aColors[iCounter] ;
			oDiv.innerHTML = ['<div class="ColorBoxBorder">',
				'<div class="ColorBox" style="background-color: #',
				aColors[iCounter], '"></div></div>'].join('');
			oDiv.Command = this ;
			oDiv.onclick = FCKTextColorCommand_OnClick ;
			this._Items.push(oDiv);
		}
	}
	// Create the Row and the Cell for the "More Colors..." button.
	oCell = oTable.insertRow(-1).insertCell(-1) ;
	oCell.colSpan = 8 ;
	oDiv = oCell.appendChild( CreateSelectionDiv() ) ;
	oDiv.innerHTML = ['<table width="100%" cellpadding="0" cellspacing="0" border="0">',
		'<tr><td nowrap align="center">',
		FCKLang.ColorMoreColors, '</td></tr></table>'].join('');
	oDiv.Command = this ;
	oDiv.onclick = FCKTextColorCommand_MoreOnClick ;
	this._Items.push(oDiv);
}
function FCKToolbarSet_Target_Cleanup(){
	this.__FCKToolbarSet = null ;
}
function FCKToolbarSet_Cleanup(){
	var targetDocument = this._Document;
	var eExpandHandle = targetDocument.getElementById( 'xExpandHandle' ) ;
	var eCollapseHandle = targetDocument.getElementById( 'xCollapseHandle' ) ;
	eExpandHandle.onclick = eCollapseHandle.onclick = null;
	this._Document = null;
	this._TargetElement = null ;
	this._IFrame = null ;
	this.CurrentInstance = null;
}
function FCKEditingArea_Unload(){
	try{
		Event.stopAllObserving(this);
	}catch(err){}
	try{
		Event.stopAllObserving(this.document);
	}catch(err){}
	try{
		Event.stopAllObserving(this.document.body);
	}catch(err){}
	if(this._FCKEditingArea){
		this._FCKEditingArea.Window = null;
		this._FCKEditingArea.Document = null;
		this._FCKEditingArea = null;
	}
	if(this.document&&Ext.isIE){
		this.document.detachEvent( 'onmouseup', Doc_OnMouseUp ) ;
		if(this.document.body)this.document.body.detachEvent( 'onpaste', Doc_OnPaste ) ;
		this.document.detachEvent("onkeydown", Doc_OnKeyDown ) ;
		this.document.detachEvent("ondblclick", Doc_OnDblClick ) ;
		this.document.detachEvent("onselectionchange", Doc_OnSelectionChange ) ;
	}
	this._FCKEditingArea = null;
}
FCK.QuickGetHtml = function(_bFormat, _bReplaceNbsps){
	var sResult = '';
	if(FCK.EditorDocument!=null){
		sResult = FCK.EditorDocument.body.innerHTML;
		if(sResult.indexOf('_fckfakelement')!=-1 || sResult.indexOf('_trscomment')!=-1
		|| sResult.indexOf('FCK__AnchorC')!=-1){
			sResult = FCK.GetHTML(_bFormat, true);
		}
		else{
			if(FCK.GetHtmlWithCustomStyle){
				sResult = FCK.GetHtmlWithCustomStyle(sResult);
			}
		}
	}
	else if(FCK.EditingArea.Textarea){
		if(FCK.GetHtmlWithCustomStyle){
			sResult = FCK.GetHtmlWithCustomStyle(FCK.EditingArea.Textarea.value);
		}
		else{
		sResult = FCK.EditingArea.Textarea.value;
		}
	}
	/**/
	if(FCKConfig.RemoveScript){
		try{
			sResult = sResult.replace(new RegExp('<script([^>]*)>(\n|\r|.)*?</'+'script>', 'img'),
				function(_a0, _a1){
					if(_a1.indexOf(' fck_adintrs=')!=-1 || _a1.indexOf(' fck_trsvideo=')!=-1){
						return _a0;
					}
					return '';
				}
			);
		}catch(err){
			//Just Skip it.
		}
	}
	//*/
	if(_bReplaceNbsps || _bReplaceNbsps == undefined)
		sResult = replaceNbsps(sResult);
	return sResult;
}
if(!Ext.isIE){
    HTMLElement.prototype.__defineGetter__ ( "innerText", function (){ 
		var anyString = ""; 
		var childS = this.childNodes; 
		for(var i=0; i<childS.length; i++) 
		{ 
		if(childS[i].nodeType==1){
			if(childS[i].tagName=="BR")
				anyString += '\n';
			else if(childS[i].tagName=="STYLE")
				continue;
			else
				anyString += childS[i].innerText;
		}else if(childS[i].nodeType==3) 
		anyString += childS[i].nodeValue; 
		} 
		return anyString; 
	}); 
}
FCK.GetText = FCK.QuickGetText = function(){
	if(FCK.EditorDocument!=null){
		if(Ext.isIE){
			return FCK.EditorDocument.body.innerText;
		}
		var sHtml = FCK.EditorDocument.body.innerHTML;
		var element = document.createElement("div");
		element.innerHTML =  sHtml;
		var text= FCK.EditorDocument.body.innerText;
		sHtml = element.innerText.replace(/&nbsp;/ig," ");
		sHtml = element.innerText.replace(/<style[^>]*>(.|\n|\r)*?<\/style>/ig, "");
		return sHtml;
	}
	if(FCK.EditingArea.Textarea){
		var sHtml = FCK.EditingArea.Textarea.value;
		try{
			//comment去除
			sHtml = sHtml.replace(/<TRS_COMMENT([^>]*)>((.|\n|\r)*?)<\/TRS_COMMENT>/ig,'');
			var eTmpDiv = document.createElement('DIV');
			eTmpDiv.style.display = 'none';
			document.body.appendChild(eTmpDiv);
			eTmpDiv.innerHTML = sHtml;
			sText = eTmpDiv.innerText;
			document.body.removeChild(eTmpDiv);
			return sText;
		}catch(err){
			return sHtml;
		}
	}
	return '';
}
var FCKExtractCommand = function(_sType){
	this.type = _sType;
}
if(FCKConfig.SupportExtract){
	FCKCommands.RegisterCommand( 'ExtractTitle', new FCKExtractCommand('Title')) ;
	FCKCommands.RegisterCommand( 'ExtractAbstract', new FCKExtractCommand('Abstract')) ;
}
FCKXHtmlEntities.Initialize = function(){
	FCKXHtmlEntities.Entities = {
		// ' ' : 'nbsp'
	}
	FCKXHtmlEntities.EntitiesRegex = /./g;
}
// Retrieves a entity (internal format) for a given character.
function FCKXHtml_GetEntity( character ){
	var sEntity = FCKXHtmlEntities.Entities[ character ];
	if(sEntity != null){
		return '#?-:' + sEntity + ';' ;
	}
	else if(sEntity == null && character.charCodeAt(0) == 160){
		sEntity = 'nbsp';
		return '#?-:' + sEntity + ';' ;
	}
	return character;
}
//FCKXHtml.TagProcessors['p'] = function( node, htmlNode ){
//	if( FCKBrowserInfo.IsIE ){
//		if(htmlNode.innerHTML=='')htmlNode.innerHTML = "&nbsp;";
//		else if(htmlNode.innerText=='')htmlNode.innerHTML = "&nbsp;";
//	}
//	if ( FCKBrowserInfo.IsIE && htmlNode.outerHTML.toUpperCase().indexOf('</P>')==-1){
//		htmlNode.innerHTML = htmlNode.innerHTML;
//	// return false ;
//	}
//	return FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;
//}
FCK.FixBody = function(){
	//Do nothing
}
FCKXHtml._AppendChildNodes = function( xmlNode, htmlNode, isBlockElement ){
	var child=htmlNode.firstChild;
	while (child){
		this._AppendNode(xmlNode,child);
		child=child.nextSibling;
	}
	if ( isBlockElement && htmlNode.tagName && htmlNode.tagName.toLowerCase()!='pre'){
		FCKDomTools.TrimNode( xmlNode, true ) ;
		if (FCKConfig.FillEmptyBlocks){
			var temp = xmlNode.lastChild;
			if (temp && temp.nodeType==1 && temp.nodeName=='br') 
				this._AppendEntity(xmlNode,this._NbspEntity);
		}
	}
	if ( xmlNode.childNodes.length != 0 )return xmlNode;
	if ( isBlockElement && FCKConfig.FillEmptyBlocks ){
		this._AppendEntity( xmlNode, this._NbspEntity ) ;
		return xmlNode ;
	}
	var sNodeName = xmlNode.nodeName ;
	// Some inline elements are required to have something inside (span, strong, etc...).
	if ( FCKListsLib.InlineChildReqElements[ sNodeName ] )
		return null ;
	// We can't use short representation of empty elements that are not marked
	// as empty in th XHTML DTD.
	if ( !FCKListsLib.EmptyElements[ sNodeName ] )
		xmlNode.appendChild( this.XML.createTextNode('') ) ;
	return xmlNode ;
}
function replaceNbsps(html){
	return html.replace(/&nbsp;&nbsp;|&nbsp; |  | &nbsp;/ig, '　');
}
FCKXHtml._AppendNode = function( xmlNode, htmlNode ){
	try{
	if (FCKBrowserInfo.IsGecko && htmlNode.tagName && htmlNode.tagName.toLowerCase()=='br'&& htmlNode.parentNode.tagName.toLowerCase()=='pre'){
		var temp='\r';
		if (htmlNode==htmlNode.parentNode.firstChild) 
			temp+='\r';
		return FCKXHtml._AppendNode(xmlNode,this.XML.createTextNode(temp));
	};
	if ( !htmlNode )
		return false ;
	switch ( htmlNode.nodeType ){
		// Element Node.
		case 1 :
			if ( htmlNode.getAttribute('_fckfakelement') )
				return FCKXHtml._AppendNode( xmlNode, FCK.GetRealElement( htmlNode ) ) ;
			if ( FCKBrowserInfo.IsGecko && (htmlNode.hasAttribute('_moz_editor_bogus_node')|| htmlNode.getAttribute('type')=='_moz'))
				return false ;
			if ( htmlNode.getAttribute('_fcktemp') )
				return false ;
			var sNodeName = htmlNode.tagName.toLowerCase()  ;
			if ( FCKBrowserInfo.IsIE ){
				// IE doens't include the scope name in the nodeName. So, add the namespace.
			if ( htmlNode.scopeName && htmlNode.scopeName != 'HTML' && htmlNode.scopeName != 'FCK' )
				sNodeName = htmlNode.scopeName.toLowerCase() + ':' + sNodeName ;
			}else{
				if ( sNodeName.StartsWith( 'fck:' ) )
					sNodeName = sNodeName.Remove( 0,4 ) ;
			}
			if ( !FCKRegexLib.ElementName.test( sNodeName ) )
				return false ;
			// Remove the <br> if it is a bogus node.
			if ( sNodeName == 'br' && htmlNode.getAttribute( 'type', 2 ) == '_moz' )
				return false ;

			if (htmlNode._fckxhtmljob && htmlNode._fckxhtmljob==FCKXHtml.CurrentJobNum) return false;
			var oNode=this.XML.createElement(sNodeName);
			FCKXHtml._AppendAttributes( xmlNode, htmlNode, oNode, sNodeName);
			htmlNode._fckxhtmljob=FCKXHtml.CurrentJobNum;

			var oTagProcessor = FCKXHtml.TagProcessors[ sNodeName ] ;
			if ( oTagProcessor )
				oNode = oTagProcessor( oNode, htmlNode, xmlNode ) ;
			else
				oNode = this._AppendChildNodes( oNode, htmlNode,
					Boolean( FCKListsLib.NonEmptyBlockElements[ sNodeName ] ) ) ;
			if ( !oNode )
				return false ;
			xmlNode.appendChild( oNode ) ;
			break ;
		// Text Node.
		case 3 :
			return this._AppendTextNode( xmlNode, htmlNode.nodeValue.ReplaceNewLineChars(' ') ) ;
		// Comment
		case 8 :
			if ( FCKBrowserInfo.IsIE && !htmlNode.innerHTML )
				break ;
			try { xmlNode.appendChild( this.XML.createComment( htmlNode.nodeValue ) ) ; }
			catch (e) { /* Do nothing... probably this is a wrong format comment. */ }
			break ;
		// Unknown Node type.
		default :
			xmlNode.appendChild( this.XML.createComment( 
				"Element not supported - Type: " + htmlNode.nodeType + 
				" Name: " + htmlNode.nodeName ) ) ;
			break ;
	}
	return true ;
	}catch(ex){}
}
FCK.TRSExtend = {};
FCKIcon.prototype.CreateIconElement = function( document )
{
	var eIcon, eIconImage ;

	if ( this.Position )		// It is using an icons strip image.
	{
		var sPos = '-' + ( ( this.Position - 1 ) * this.Size ) + 'px' ;

		if ( FCKBrowserInfo.IsIE )
		{
			// <div class="TB_Button_Image"><img src="strip.gif" style="top:-16px"></div>

			eIcon = document.createElement( 'DIV' ) ;

			eIconImage = eIcon.appendChild( document.createElement( 'div' ) ) ;
			//eIconImage.src = FCK_SPACER_PATH ;
			eIconImage.className = "editor_toolbar_button";
			eIconImage.style.backgroundPosition	= '0px ' + sPos ;
			//eIconImage.src = this.Path ;
			//eIconImage.style.top = sPos ;
		}
		else
		{
			// <img class="TB_Button_Image" src="spacer.gif" style="background-position: 0px -16px;background-image: url(strip.gif);">

			eIcon = document.createElement( 'IMG' ) ;
			eIcon.src = FCK_SPACER_PATH ;
			eIcon.style.backgroundPosition	= '0px ' + sPos ;
			eIcon.style.backgroundImage		= 'url(' + this.Path + ')' ;
		}
		
		eIcon.className = 'TB_Button_Image' ;
	}
	else					// It is using a single icon image.
	{
		if ( FCKBrowserInfo.IsIE )
		{
			// IE makes the button 1px higher if using the <img> directly, so we
			// are changing to the <div> system to clip the image correctly.
			eIcon = document.createElement( 'DIV' ) ;

			eIconImage = eIcon.appendChild( document.createElement( 'IMG' ) ) ;
			eIconImage.src = this.Path ? this.Path : FCK_SPACER_PATH ;
		}
		else
		{
			// This is not working well with IE. See notes above.
			// <img class="TB_Button_Image" src="smiley.gif">
			eIcon = document.createElement( 'IMG' ) ;
			eIcon.src = this.Path ? this.Path : FCK_SPACER_PATH ;
		}
		/*
		eIcon = doc.createElement( 'DIV' ) ; 
		eIconImage = eIcon.appendChild( doc.createElement( 'DIV' ) ) ; 
		var nEndPos = this.Path.lastIndexOf(".");
		eIconImage.className = "editor_tb_"+this.Path.substring(0, nEndPos);
		*/
	}

	return eIcon ;
}
function just4IE(){
if(!Ext.isIE)return;

FCKUnOrderListCommand.prototype.Execute0 = FCKUnOrderListCommand.prototype.Execute;
FCKOrderListCommand.prototype.Execute0 = FCKOrderListCommand.prototype.Execute;
FCKOrderListCommand.prototype.Execute = FCKUnOrderListCommand.prototype.Execute = function(){
	this.Execute0.apply(this, arguments);
	var dom = FCKSelection.GetParentElement() ;
	if(dom && (dom.tagName == 'OL' || dom.tagName == 'UL')) dom.removeAttribute("type");
};

FCKFontSizeCommand.prototype.Execute = function( fontSize ){
	fontSize = fontSize || '12pt';
	FCK.ExecuteNamedCommand( 'FontSize', fontSize ) ;
}
FCK.ExecuteNamedCommand = function( commandName, commandParameter, noRedirect ){
	if(!FCK.EditorDocument||!FCK.EditorDocument.body)return;
	FCKUndo.SaveUndoStep() ;
	if( commandName=='FontSize' ){
		FCK.TRSExtend.doFontSize(commandParameter);
		FCK.Events.FireEvent( 'OnSelectionChange' ) ;
	}// 添加行距命令 2010-9-6 by hdg
	else if(commandName=='LineHeight'){
		FCK.TRSExtend.doLineHeight(commandParameter);
		FCK.Events.FireEvent( 'OnSelectionChange' ) ;
	}
	else if ( !noRedirect && FCK.RedirectNamedCommands[ commandName ] != null ){
		FCK.ExecuteRedirectedNamedCommand( commandName, commandParameter ) ;
	}
	else{
		FCK.Focus() ;
		FCK.EditorDocument.execCommand( commandName, false, commandParameter ) ; 
		FCK.Events.FireEvent( 'OnSelectionChange' ) ;
	}
	FCKUndo.SaveUndoStep() ;
}
window.isHTMLElement = function isHTMLElement(_oTextRange, _oParentElement){
	if(_oParentElement == null || _oParentElement.tagName == "BODY")
		return false;
	try{
		var sRangeHTML = _oTextRange.htmlText;
		//TextRange会产生多余的\r\n,需要排除 
		var reg = /\r\n/g;
		var bContainsInvalidChar = (sRangeHTML.search(reg)>0);
		if(bContainsInvalidChar){
			sRangeHTML = sRangeHTML.replace(reg, ''); 
		}
		var sParentHTML = _oParentElement.outerHTML;
		if(bContainsInvalidChar && sParentHTML.search(reg)>=0){
			sParentHTML = sParentHTML.replace(reg, '');
		}
		if(sParentHTML == sRangeHTML){
			return true;
		}
		sParentHTML = _oParentElement.innerHTML;
		if(bContainsInvalidChar && sParentHTML.search(reg)>=0){
			sParentHTML = sParentHTML.replace(reg, '');
		}
		if(sParentHTML == sRangeHTML){
			return true;
		}
	}catch(err){
	}
	return false; 
}
Ext.apply(FCK.TRSExtend, {
	_doFontSize : function(oFont,_sFontSize,_bSet){
		if(_bSet){
			oFont.style.fontSize = _sFontSize;
		}
		var nCount = oFont.childNodes.length, objTemp,sTagTemp;
		for(var i=0; i<nCount; i++){
			objTemp = oFont.childNodes[i];
			sTagTemp = objTemp.tagName;
			if(sTagTemp == "TD" || sTagTemp == "FONT"){
				FCK.TRSExtend._doFontSize(objTemp,_sFontSize,true);
			}
			else if(objTemp.nodeType==1){
				if(objTemp.style.fontSize){
					objTemp.style.fontSize = _sFontSize;
				}
				FCK.TRSExtend._doFontSize(objTemp,_sFontSize,objTemp.style.fontSize!=null);
			}
		}
	},
	_getFontSize : function(oFont){
		var nCount = oFont.childNodes.length, objTemp;
		var sFontSize = Element.getStyle(oFont,'fontSize');
		for(var i=0; i<nCount; i++){
			objTemp = oFont.childNodes[i];
			if(objTemp.nodeType==1){
				var sTmpSize = FCK.TRSExtend._getFontSize(objTemp,sFontSize);
				if(sTmpSize!=''&&sTmpSize!=sFontSize){
					return null;
				}
			}
		}
		return sFontSize;
	},
	getFontSize : function(){
		// Gets the actual selection.
		var oSel = FCK.EditorDocument.selection ;
		var oRange = oSel.createRange() ;
		var sRangeHTML = oRange.htmlText;
		var sCount = oRange.text.length;
		if ( oSel.type.toLowerCase()== 'text' ){
			var eParent = oRange.parentElement() ;
			if(eParent != null){
				var sFontSize = Element.getStyle(eParent,'fontSize');
			}
			var oFont = document.createElement("SPAN") ;
			oFont.innerHTML = sRangeHTML ;
			oFont.style.fontSize = sFontSize;
			var sPasteHTML = "";
			sFontSize = FCK.TRSExtend._getFontSize(oFont);
			delete oFont;
			return sFontSize;
		}
		else if ( oSel.type.toLowerCase()== 'control' ){
			var oElement = FCKSelection.GetSelectedElement();
			return FCK.TRSExtend._getFontSize(oElement);
		}
		else{
			var eParent = oRange.parentElement() ;
			if(eParent != null && eParent.innerHTML.length==0 && eParent.tagName != "BODY"){
				return Element.getStyle(eParent,'fontSize');
			}
			return '';
		}
	},
	doFontSize : function(_sFontSize){
		var oSel = FCK.EditorDocument.selection ;
		var oRange = oSel.createRange() ;
		if (true|| oSel.type.toLowerCase() != 'none' || oRange.htmlText!=''){
			if(oRange.text==''){
				oRange.pasteHTML('&nbsp;') ;
				oRange.collapse(true) ; 
				oRange.moveStart('character',-1);
				oRange.select();
			}
			FCK.EditorDocument.execCommand( 'FontSize', false, 1 ) ; 
			var eFontNews = FCK.EditorDocument.getElementsByTagName('font');
			for(var i=0;i<eFontNews.length;i++){
				var eFont = eFontNews[i];
				if(eFont.getAttribute('size')){
					eFont.removeAttribute('size');
					FCK.TRSExtend._doFontSize(eFont,_sFontSize,true);
				}
			}
		}else{
			var eParent = oRange.parentElement() ;
			if(eParent != null && eParent.innerHTML.length==0 && eParent.tagName != "BODY"){
				eParent.style.fontSize = _sFontSize;
			}else{
				oRange.pasteHTML('<span style="font-size:'+_sFontSize+'">&nbsp;</span>') ;
				oRange.collapse(true) ; 
				oRange.moveStart('character',-1);
				oRange.select();
			}
		}
	}
});
//添加行距设置2010-9-6 by hdg
Ext.apply(FCK.TRSExtend, {
	_doLineHeight : function(oFont,_sLineHeight,_bSet){
		if(_bSet){
			oFont.style.lineHeight = _sLineHeight;
		}
		var nCount = oFont.childNodes.length, objTemp ,sTagTemp;
		for(var i=0; i<nCount; i++){
			objTemp = oFont.childNodes[i];
			sTagTemp = objTemp.tagName;
			if(sTagTemp == "TD" || sTagTemp == "FONT"){
				FCK.TRSExtend._doLineHeight(objTemp,_sLineHeight,true);
			}
			else if(objTemp.nodeType==1){
				if(objTemp.style.lineHeight){
					objTemp.style.lineHeight = _sLineHeight;
				}
				FCK.TRSExtend._doLineHeight(objTemp,_sLineHeight,objTemp.style.lineHeight!=null);
			}
		}
	},
	doLineHeight : function(_sLineHeight){
		var oSel = FCK.EditorDocument.selection ;
		var oRange = oSel.createRange() ;
		if (true|| oSel.type.toLowerCase() != 'none' || oRange.htmlText!=''){
			if(oRange.text==''){
				oRange.pasteHTML('&nbsp;') ;
				oRange.collapse(true) ;
				oRange.moveStart('character',-1);
				oRange.select();
			}
			FCK.EditorDocument.execCommand( 'FontSize', false, 0) ;
			var eFontNews = FCK.EditorDocument.getElementsByTagName('font');
			for(var i=0;i<eFontNews.length;i++){
				var eFont = eFontNews[i];
					if(eFont.getAttribute('size')){
						//删除execCommand添加的size属性
						eFont.removeAttribute('size');
						FCK.TRSExtend._doLineHeight(eFont,_sLineHeight,true);
					}
			 }
		}
	}
});

var nTitleLength = editorCfg.autoTitleLength || 30;
var bRepeatDetect = false, bPasteTitle = false, bPasteBody = false;
window.AutoPaste = function AutoPaste(_bNotInterval){
	if(!FCKConfig.AutoDetectPaste)return;
	if(!_bNotInterval){
		FCK.autopasteInterval = setInterval(function(){AutoPaste(true)},500);
	}
	try{
		var sClipText = "";
		var oCookie = FCK.loadCookie();
		if(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0"){
			sClipText = clipboardData.getData("Text")||'';
		}else{
			if(window.OfficeActiveX){
				sClipText = getPainText(window.OfficeActiveX.GetWordContent());
			}
		}
		if(sClipText=='')return;
		if(sClipText == FCKConfig.lastClipText)return;
		var eTitle = myActualTop.document.getElementById('DocTitle');
		if(!eTitle){
			bPasteTitle = true;
		}
		if(eTitle && sClipText.length<=nTitleLength && !/<.*?>|\n/.test(sClipText)){
			if((!bRepeatDetect&&bPasteTitle)||eTitle.value!=''){
				return;
			}
			FCKConfig.lastClipText = sClipText;
			eTitle.focus();
			eTitle.value = sClipText;
			bPasteTitle = true;
			eTitle.select();
			return;
		}
		var sCurrHtml = FCK.EditorDocument.body.innerHTML;
		var bLinkDocument = (myActualTop.$("DocType").value==30);
		var bFileDocument = (myActualTop.$("DocType").value==40);
		/*增加链接型文档和文件型文档的判断*/
		if((!bRepeatDetect&&bPasteBody)
			|| sCurrHtml.length>20 || !FCK.isBlankContent(sCurrHtml) || bLinkDocument || bFileDocument){
			return;
		}
		FCKConfig.lastClipText = sClipText;
		bPasteBody = true;
		FCK.EditorDocument.body.focus();
		FCK.Paste(true);
	}catch(err){}
	if(!bRepeatDetect && bPasteTitle && bPasteBody){
		clearInterval(FCK.autopasteInterval);
	}
}

function getPageWidth(result){
	if(result && result.indexOf("cm") > 0){
		return (parseFloat(result.substring(0,result.length -2))*28.35 + 28);
	}else if(result && result.indexOf("pt") > 0){
		return parseFloat(result.substring(0,result.length -2));
	}
}
FCK.PasteFromWord = function(bExcel){
	//word粘贴时，先获取当前编辑器文本内容，如果有，则不需要过滤页面设置的样式
	var tempContent = FCK.QuickGetText();
	if(tempContent.trim() == ""){
		FCK.IsWordFile = true;
		if(!(window.Attributes && window.Attributes["isForWord"]=="1")) {
			FCK.RemoveEditorCss();
		}
	}
	var oCookie = FCK.loadCookie();
	try{
		var html = "";
		if(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0"){
			if(window.OfficeActiveX){
				window.OfficeActiveX.FormatWordClip();
			}
			html = FCK.GetClipboardHTML2();
		}else{
			if(window.OfficeActiveX) {
				var reWordVersion = /SourceURL:.*\.(.*)./ig;
				var reImgTag = /<img/ig;
				var sTempHtmlValue = window.OfficeActiveX.GetWordContent();
				if(/*reWordVersion.exec( sTempHtmlValue )[1]=="docx" || */!(reImgTag.test( sTempHtmlValue ))) {
					//var re = /<v:shape\s[\s\S]*width:(.*?)\;[\s\S]*height:(.*?)\;[\s\S]*src=\"(.*?)\"[\s\S]*<\/v:shape>/ig;
					re = new RegExp('<v:shape\\s[\\s\\S]*?width:(.*?)\\;[\\s\\S]*?height:(.*?)\\;[\\s\\S]*?src=\\"(.*?)\\"[\\s\\S]*?<\\/v:shape>',"gi");
					sTempHtmlValue = sTempHtmlValue.replace(re,'<img width="$1" height="$2" src="$3"/>');
					//暂时兼容某些尚未知晓的特殊情况，当v:shape标签转换失败的时候，仍然将imagedata进行转化
					//得到原始大小的图片
					re = new RegExp("v:imagedata","gi");
					sTempHtmlValue = sTempHtmlValue.replace(re,"img");
				}
				var fina = pickWordContent(sTempHtmlValue,!bExcel);
				if(bExcel) {
					html = fina[0] + fina[1];
					html = cleanExcelCode(html);
				}else{
					//调整载入页宽问题
					html = fina[1] || "";
					var result = pickPageStyle(sTempHtmlValue);
					if(result.length > 0){
						var ele = parent.frames[0].$("xEditingArea");
						if(ele && oCookie.RemovePageWidth != undefined && oCookie.RemovePageWidth == "0"){
							if(tempContent.trim() == "")
								ele.style.width = (getPageWidth(result[0]) + 28 +"pt");
						}
					}
				} 
			}
		}
	}catch(err){}
	if(!bExcel)
		html = CleanWord(html);
		html = paste2defaultformat(html,true);
	FCK.InsertHtml( html ) ;

	//excel
	if(bExcel && !(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0")){
		var sHtml = FCK.GetHTML(false,false);
		if(sHtml.indexOf('class="TRS_PreExcel TRS_PreAppend"') < 0){
			FCKUndo.SaveUndoStep() ;
			var styleContent = pickWordContent(window.OfficeActiveX.GetWordContent(),false)[0];
			var sTemp = (styleContent == null ? "" : styleContent) + "\n<div class='TRS_PreExcel TRS_PreAppend'>";
			FCK.SetHTML(sTemp + sHtml + "</div>");
		}

	}

	if(!bExcel && !(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0")){
		// 顶级元素追加统一的样式
		var sHtml = FCK.GetHTML(false,false);
		if(sHtml.indexOf('class="TRS_PreAppend"') < 0){
			FCKUndo.SaveUndoStep() ;
			var styleContent = pickWordContent(window.OfficeActiveX.GetWordContent(),true)[0];
			
			//调整行间距 +增加默认排版word粘贴不开启状态下的条件
			var sTemp = sHtml;
			if(!(window.Attributes && window.Attributes["isForWord"]=="1")){
				sTemp = (styleContent == null ? "" : styleContent) + "\n<div class='TRS_PreAppend' style=\"";
				if(oCookie.KeepLineHeight){		
					sTemp += "layout-grid:15.6pt;";
				}
				sTemp += "OVERFLOW-X: hidden;WORD-BREAK: break-all;";
				if(result.length > 0 && oCookie.RemovePageWidth != undefined && oCookie.RemovePageWidth == "0"){
					if(result[3] != ""){
						sTemp += "margin:" + result[3];
					}
					sTemp += (" temp:" +  (getPageWidth(result[0]) + 28) +"pt;");
					sTemp += " WIDTH-TRS:" + (getPageWidth(result[0]) - getPageWidth(result[1]) - getPageWidth(result[2]) + "pt\"");
				}
				sTemp += "\">" + sHtml + "</div>";		
				
				if(tempContent.trim() != ""){
					sTemp = sHtml;
				}
			}

			var oReOuter = /<style[^>]*>([\s\S]*?)<\/style>/img;		
			var sWordStyle = oReOuter.exec(sTemp);
			if(sWordStyle != null){
				FCK.WordStyle = sWordStyle[0].replace(oReOuter,"$1");
				/*兼容编辑器不同系统对应字体库不同导致的粘贴时字体不生效问题*/
				var regFontSize4Fangsong = /仿宋|仿宋_GB2312/;
				var regFontSize4Kaiti = /楷体|楷体_GB2312/;
				FCK.WordStyle = FCK.WordStyle.replace(regFontSize4Fangsong,"仿宋,仿宋_GB2312");
				FCK.WordStyle = FCK.WordStyle.replace(regFontSize4Kaiti,"楷体,楷体_GB2312");
			}
			sTemp = sTemp.replace(oReOuter,'');
			FCKTools.RunFunction( function(){
/*,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,简单编辑其中没有支持SetWordStyle属性，简单解决方法是在这里重写*/
				FCK.SetWordStyle = function(style){
					try{
						for(var i=0; i<FCK.EditorDocument.styleSheets.length; i++){
							if(FCK.EditorDocument.styleSheets[i].title == "__wordstyle__"){
								FCK.EditorDocument.styleSheets[i].cssText = style;
								break;
							}
						}
					}catch(ex){
					}
				}
/*......................................................by@sj*/
				FCK.SetWordStyle(FCK.WordStyle);
			});
			FCK.SetHTML(sTemp);
		}	
	}
}
FCK.GetClipboardHTML2 = function(){
	var oDiv = document.getElementById( '___FCKHiddenDiv' ) ;
	if ( !oDiv ){
		oDiv = document.createElement( 'DIV' ) ;
		oDiv.id = '___FCKHiddenDiv' ;
		var oDivStyle = oDiv.style ;
		oDiv.unselectable = 'on';
		oDivStyle.position = 'absolute' ;
		oDivStyle.overflow = 'hidden' ;
		oDivStyle.width = oDivStyle.height = 1 ;
		oDivStyle.top = oDivStyle.left = -100 ;
		document.body.appendChild( oDiv ) ;
	}
	try{
		oDiv.style.display = '';
		oDiv.style.visibility = 'visible';
		oDiv.innerHTML = '' ;
		oDiv.contentEditable = true;
		oDiv.focus();
//		document.execCommand( 'Paste' ) ;
//		document.body.focus();
	}catch(ex){}
	var sData = oDiv.innerHTML ;
	if(sData == ''){
		oDiv.innerHTML = '' ;
		var oTextRange = document.body.createTextRange() ;
		oTextRange.moveToElementText( oDiv ) ;
		oTextRange.execCommand( 'Paste' ) ;
		sData = oDiv.innerHTML ;
		oDiv.innerHTML = '' ;
	}
	return sData ;
}

var m_sServerName = null;
var m_sAppName = null;
var m_sProtocal = null;
function initServerName(){
	m_sProtocal = window.location.protocol;
	m_sServerName = window.location.host;
	m_sAppName = window.location.pathname.split('/')[1];
}
//Office ActiveXObject 控件对象,用于在客户端处理Office文档
window.OfficeActiveX = {
	m_OfficeActiveX : null,
	Init : function(){//初始化ActiveX控件
		document.write("<OBJECT id=\"WORD_CLIENT\" CLASSID=" +
				"\"clsid:D6641A7A-B6F8-4FC7-A382-624DDBAEF96F\" " +
				"codeBase=\"WCMOffice.cab#Version=1,0,0,29\" style=\"display:none\"></OBJECT>");
		this.m_OfficeActiveX = $("WORD_CLIENT");
		if(this.m_OfficeActiveX == null){
			alert(FCKLang.SETUPWRONG);
			FCK.setCookie('EditorEnableWordClient','-1');
			return;
		}
		if(m_sServerName == null)
			initServerName();
		if(m_sServerName == "")return;
		var sSOAP_URL = null;
		if(FCKConfig.SingleSOAPApp){
			sSOAP_URL = m_sProtocal + "//"+ m_sServerName +"/soap/servlet/rpcrouter";
		}else{
			sSOAP_URL = m_sProtocal + "//"+ m_sServerName +"/"+m_sAppName+"/services";
		}
		var sWCM_URL = m_sProtocal + "//"+ m_sServerName;
		try{
			this.m_OfficeActiveX.SetSOAPURL(sSOAP_URL, "urn:FileService", "sendFileBase64", sWCM_URL); 
		}catch(e){
			alert(FCKLang.WRONGTIP1 +sSOAP_URL + FCKLang.WRONGTIP2);
			FCK.setCookie('EditorEnableWordClient','-1');
		}
	},
	IsEnableWordClient : function(){
		if(this._bIsEnableWordClient!=null)return this._bIsEnableWordClient;
		var oCookie = FCK.loadCookie();
		var sEditorEnableWordClient = oCookie['EditorEnableWordClient'];
		var bRetVal = false;
		if(sEditorEnableWordClient == '0'){
			FCK.setCookie('EditorEnableWordClient','0');
			bRetVal = false;
		}
		else if(sEditorEnableWordClient == '1'){
			FCK.setCookie('EditorEnableWordClient','1');
			bRetVal = true;
		}
		else{
			var sConfirm = FCKLang.LONGTEXT1;
			if(sEditorEnableWordClient == '-1'){
				sConfirm = FCKLang.LONGTEXT2;
			}
			if(confirm(sConfirm)){
				FCK.setCookie('EditorEnableWordClient','1');
				bRetVal = true;
			}
			else{
				FCK.setCookie('EditorEnableWordClient','0');
				bRetVal = false;
			}
		}
		this._bIsEnableWordClient = bRetVal;
		return bRetVal;
	},
	isUsed : function(){
		return (this.m_OfficeActiveX != null);
	},
	FormatWordClip : function(){//格式化word代码,转换图片为本地文件名
		if(!this.isUsed()) return;
		try{
			//格式化word源码,去除多余的格式信息
			this.m_OfficeActiveX.SetOfficeFilter(true);
			this.m_OfficeActiveX.PasteContent(false); 
		}catch(e){
			//Just Skip it.
		}
	},
	GetWordContent : function(){
		if(!this.isUsed()) return;
		try{
			//格式化word源码,去除多余的格式信息
			return this.m_OfficeActiveX.GetClipData();
		}catch(e){
			//Just Skip it.
		}
	},
	UploadLocals : function(){
		//根据图片的本地文件名获得uploadpic的文件名,并添加这个属性到各IMG对象中
		if(!this.isUsed()) return;
		//上传Img中的图片
		this._UploadLocalFile("IMG", "src");
		//上传A中的本地文件
		this._UploadLocalFile("A", "href");
		//上传Flash,音频,视频
		this._UploadLocalFile("EMBED", "src");
	},
	_ExtractFileName : function(_sFileName){
		if(_sFileName.length>13 && _sFileName.substring(0, 2)=="U0"){
			return _sFileName;
		}
		var nStartPos = _sFileName.indexOf("<FILENAME>");
		var nEndPos = _sFileName.indexOf("</FILENAME>");
		if(nStartPos<0 || nEndPos<0 || nEndPos<nStartPos){
			//alert("WCM SOAP服务配置有误!\n SOAP Return Info=["+this.m_OfficeActiveX.GetMyMsg()+"]");
			//使用GetErrorInfo()得到的信息对可能的问题调试更有帮助
			alert(FCKLang.LONGTEXT3 + "\n FileName="+_sFileName+" \nSOAP Return Info=["+this.m_OfficeActiveX.GetErrorInfo()+"]");
			return;
		}
		return _sFileName.substring(nStartPos + "<FILENAME>".length, nEndPos);
	},
	_IsWebPicFile : function(_element, _sSrc){
		var sFlag = m_sProtocal + "//" + m_sServerName +  "/webpic/W0";
		if(_sSrc && _sSrc.indexOf(sFlag) == 0){
			return true;
		}
		sFlag = "/webpic/W0";
		if(_sSrc && _sSrc.indexOf(sFlag) == 0){
			return true;
		}
		return false;
	},
	_VerifyAnchor : function(_element){
		if(!_element) return false;
		if(_element.tagName != "A") return true;
		if(_element.className == "b" && _element.onfocus == "h()"
			&& _element.onclick == "return false") return false;
	},
	_UploadLocalFile : function(_sTagName,_sAttributeName){//上传指定元素的本地文件
		//记录已经处理过的文件
		var oHasDowith = {};
		if ( FCK.EditMode == FCK_EDITMODE_SOURCE ){
			try{
				var sHtml = FCK.EditingArea.Textarea.value ;
				var regElements = new RegExp("<"+_sTagName.trim()+"\\s[^>]*" +
					_sAttributeName.trim()+"\\s*=[^>]*>",'ig');
				sHtml = sHtml.replace(regElements,function(_a0){
					var regUploadPic = new RegExp("\\sUploadPic\\s*=(\"|\')(.*?)\\1",'ig');
					var sUploadPic = (regUploadPic.exec(_a0)||[])[2];
					if(sUploadPic) return _a0;
					var regAttr = new RegExp("\\s"+_sAttributeName.trim()+"\\s*=(\"|\')(.*?)\\1",'ig');
					_a0 = _a0.replace(regAttr,function(_a0,_a1,_a2){
						var strLocalFile = _a2;
						if(OfficeActiveX._IsWebPicFile(null, strLocalFile))
							strUploadPicName = strLocalFile.substring(strLocalFile.lastIndexOf("/")+1);
						else 
							strUploadPicName = oHasDowith[strLocalFile] || sUploadPic; 
						//仅当IMG的UploadPic属性值为空时进行如下处理
						if(strUploadPicName == null || strUploadPicName.length <= 0){  
							var sSrc = strLocalFile;
							if(!(sSrc == null || sSrc.length<=0 
									|| !sSrc.match(/^(file:\/{2,})/ig))){
								sSrc = decodeURIComponent(sSrc.replace(/^(file:\/{2,})/ig,'')
									.replace(/\//g,'\\'));
								var sUploadedFile = OfficeActiveX.m_OfficeActiveX.UploadFile(sSrc);
								if(!sUploadedFile && FCKConfig.ServerUrl){
									var sSOAP_URL = null;
									if(FCKConfig.SingleSOAPApp){
									sSOAP_URL = config.ServerUrl +"/soap/servlet/rpcrouter";
									}else{
										sSOAP_URL = config.ServerUrl +"/"+m_sAppName+"/services";
									}
									OfficeActiveX.m_OfficeActiveX.SetSOAPURL(sSOAP_URL,
										"urn:FileService", "sendFileBase64", "");
									sUploadedFile = OfficeActiveX.m_OfficeActiveX.UploadFile(sSrc);
								}
								strUploadPicName = OfficeActiveX._ExtractFileName(sUploadedFile);
							}
						}
						//如果获取上传文件名出错,则get错误信息
						if(strUploadPicName == "" || strUploadPicName == null)
							return _a0;
						//对处理后的文件名进行判断
						if(strUploadPicName.indexOf("U0") != 0)
							return _a0;
						//记录已经处理过的记录
						oHasDowith[strLocalFile] = strUploadPicName;  
						//将得到的上传文件名添加到各自IMG对象的UploadPic属性中
						return " " + _sAttributeName + "=\"" + strLocalFile +
							"\" UploadPic=\"" + strUploadPicName + "\"";  
					});
					return _a0;
				});
				FCK.EditingArea.Textarea.value = sHtml;
			}catch(err){
				//TODO logger
				//Just Skip it now.
			}
			return;
		}
		if(!FCK.EditorDocument)return;
		//校验数据有效性
		if(_sTagName == null || _sTagName.length<=0 
			|| _sAttributeName==null || _sAttributeName.length<=0) return;
		//获取指定的元素集合
		var arrElements = FCK.EditorDocument.getElementsByTagName(_sTagName.toUpperCase());
		//遍历所有IMG对象
		for(var i=0;i<arrElements.length;i++) {
			var strUploadPicName = null;
			var element = arrElements[i];
			if(!element || !this._VerifyAnchor(element)) continue;

			var strLocalFile = element.getAttribute(_sAttributeName,2);
			if(this._IsWebPicFile(arrElements[i], strLocalFile))
				strUploadPicName = strLocalFile.substring(strLocalFile.lastIndexOf("/")+1);
			else 
				strUploadPicName = oHasDowith[strLocalFile] || element.getAttribute('UploadPic',2); 
			//仅当IMG的UploadPic属性值为空时进行如下处理
			if(strUploadPicName == null || strUploadPicName.length <= 0){  
				//根据本地文件名获得上传时的文件名  
				try{
					var sSrc = strLocalFile;
					if(sSrc == null || sSrc.length<=0)continue;
					if(!sSrc.match(/^(file:\/{2,})/ig))continue;
					sSrc = decodeURIComponent(sSrc.replace(/^(file:\/{2,})/ig,'')
						.replace(/\//g,'\\'));
					var sUploadedFile = this.m_OfficeActiveX.UploadFile(sSrc);
					if(!sUploadedFile && FCKConfig.ServerUrl){
						var sSOAP_URL = null;
						if(FCKConfig.SingleSOAPApp){
							sSOAP_URL = config.ServerUrl +"/soap/servlet/rpcrouter";
						}else{
							sSOAP_URL = config.ServerUrl +"/"+m_sAppName+"/services";
						}
						this.m_OfficeActiveX.SetSOAPURL(sSOAP_URL, "urn:FileService", "sendFileBase64", "");
						sUploadedFile = this.m_OfficeActiveX.UploadFile(sSrc);
					}
					strUploadPicName = this._ExtractFileName(sUploadedFile);
				}catch(e){
					alert(e+ FCKLang.LONGTEXT4+location.port+FCKLang.LONGTEXT5);
					continue;
				}
				//如果获取上传文件名出错,则get错误信息
				if(strUploadPicName == "" || strUploadPicName == null)
					continue;
				//对处理后的文件名进行判断
				if(strUploadPicName.indexOf("U0") != 0)
					continue
				//记录已经处理过的记录
				oHasDowith[strLocalFile] = strUploadPicName;  
				//将得到的上传文件名添加到各自IMG对象的UploadPic属性中
				element.setAttribute("UploadPic", strUploadPicName);  
			}
		}
	}
}
Event.observe(window, 'unload', function(){
	clearTimeout(myActualTop.autosave);
	if(FCK.autopasteInterval){
		clearInterval(FCK.autopasteInterval);
		FCK.autopasteInterval = false;
	}
});
window.cleanExcelCode = function cleanExcelCode(_sHtml){
	var html = _sHtml; 
	var nStartPos = html.indexOf("<x:ClientData");
	if(nStartPos >= 0){
		var nEndPos = html.indexOf("</x:ClientData>", nStartPos);
		if(nEndPos>0){
			html = html.substring(0, nStartPos) + 
				html.substring(nEndPos + "</x:ClientData>".length); 
		}
	}
	// Remove all Col tags
	html = html.replace(/<COL\s.[^>]*>/gi, "" );
	html = html.replace(/<\/?COLGROUP[^>]*>/gi, "" );

	//对td元素，需要去掉不换行问题
	html = html.replace(/([\s;{])?white-space:nowrap;?/img,"$1");
	return html;
}
FCK.GetClipboardHTML = function(){
	return clipboardData.getData("Text");
}
FCK._CheckIsPastingEnabled = function( returnContents ){
	// The following seams to be the only reliable way to check is script
	// pasting operations are enabled in the secutiry settings of IE6 and IE7.
	// It adds a little bit of overhead to the check, but so far that's the
	// only way, mainly because of IE7.
	FCK._PasteIsEnabled = false ;
	document.body.attachEvent( 'onpaste', FCK_CheckPasting_Listener ) ;
	// The execCommand in GetClipboardHTML will fire the "onpaste", only if the
	// security settings are enabled.
	var oReturn = FCK.GetClipboardHTML2() ;
	document.body.detachEvent( 'onpaste', FCK_CheckPasting_Listener ) ;
	if ( FCK._PasteIsEnabled ){
		if ( !returnContents )
			oReturn = true ;
	}
	else
		oReturn = false ;
	delete FCK._PasteIsEnabled ;
	return oReturn ;
}
FCK.Paste = function(bFromAutoPaste){
	if ( FCK._PasteIsRunning )
		return true ;
	if ( FCKConfig.ForcePasteAsPlainText ){
		FCK.PasteAsPlainText() ;
		return false ;
	}
	var oCookie = FCK.loadCookie();
	// 如果启用了粘贴的内容需要统一规范和格式
	/*
	*注释掉统一样式功能
	if(FCKConfig.PasteToUnionStyle){
		pasteToUnionStyle();
		return false;
	}
	*/

	var sHTML = "";
	//新增剪切板模式配置项，默认用控件抽取剪切板内容
	if(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0"){
		sHTML = FCK._CheckIsPastingEnabled( true ) ;
	}else{
		if(window.OfficeActiveX) {
			sHTML = window.OfficeActiveX.GetWordContent();
		}
	}

	var eDiv = document.getElementById('auto_message');
	if (FCKConfig.AutoDetectPasteFromWord && sHTML.length > 0 ){
		var re = /<\w[^>]*(( class="?MsoNormal"?))/gi ;
		var re2 = /<v:imagedata /gi;
		//var re3 = /<html\s+[\s\S]*?[\s\S]+xmlns:w=(['"])urn:schemas-microsoft-com:office:word\1/gi;   //较慢的匹配
		var re3 = /<html\s+[^>]*?xmlns:w=(['"]?)urn:schemas-microsoft-com:office:word\1/gi;
		var reColGroup = /<COLGROUP>/gi;
		//var reExcel = /<html\s+[\s\S]*?[\s\S]+xmlns:x=(['"])urn:schemas-microsoft-com:office:excel\1/gi;   //较慢的匹配
		var reExcel = /<html\s+[^>]*?xmlns:x=(['"]?)urn:schemas-microsoft-com:office:excel\1/gi;
		if ( re2.test( sHTML ) || re3.test( sHTML )){
			if(eDiv){
				Element.show('auto_message');
				eDiv.style.cursor = "pointer";
				if(eDiv.innerHTML == ""){
					eDiv.innerHTML = '<span title="对Word粘贴过来的内容进行过滤设置，点击进入设置窗口"><font color="#6666FF">粘贴设置</font></span>';
				Event.observe(eDiv, 'click', function(){
					FCKDialog.OpenDialog("FCKDialog_WordPasteConfig","Word粘贴格式设置","wordPasteConfig.html","400px","240px");
				})
				}
			}
			FCK.PasteFromWord(false) ;
			return false ;
		}else{
			if(eDiv){
				Element.hide('auto_message');
			}
			if(reColGroup.test(sHTML) || reExcel.test(sHTML)){
				FCK.PasteFromWord(true) ;
				return false;
			}
		}
	}
	if(eDiv){
		Element.hide('auto_message');
	}
	FCK._PasteIsRunning = true ;
	if(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0"){
		FCK.EditorWindow.focus() ;
		FCK.ExecuteNamedCommand( 'Paste' ) ;
	}else{
		var bFromWeb = /\sSourceURL:/img;
		if(bFromWeb.test(sHTML)){
			//粘贴web网页时的处理
			if(!FCK.FilterWebPic && !bFromAutoPaste){
				var ua = navigator.userAgent.toLowerCase();
				var isIE6 = ua.indexOf("msie 6") > -1;
				if(isIE6){
					FCK.EditorWindow.focus() ;
					FCK.ExecuteNamedCommand( 'Paste' ) ;
					delete FCK._PasteIsRunning ;
					return false;//2011
				}
			}
			sHTML = pickWebContent(sHTML);
		}else{
			var regContent = /<!--StartFragment-->[\s\S]*?<!--EndFragment-->/img;
			var sRealContent = regContent.exec(sHTML);
			if(sRealContent && sRealContent[0] != null){
				sHTML = sRealContent[0].replace(/<!--[\s\S]*?-->/img, ""); 
			}else{
				FCK.EditorWindow.focus() ;
				sHTML = sHTML.replace(/\t/img,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
				//sHTML = sHTML.replace( /\n/g, '<BR>' );
				//sHTML = sHTML.replace( / /g, '&nbsp;' );//转义以保证多个空格的体现,20111117,注释掉保持粘贴原样式
				var BHangJu = false;
				if(top && top.getEditorCss){
					var sCssText = top.getEditorCss();
					if(sCssText != null && sCssText != ""){
						BHangJu = getHangJu(sCssText);
					}
				}
				sHTML = wrapBRWithPTag(sHTML,BHangJu);
			}
		}
		//去掉冗余行
		sHTML = sHTML.replace(/<p [^>]*?>[\s]*?<\/p>/img,"");
		sHTML = sHTML.replace(/<p [^>]*?><span [^>]*?>[?\s]*?<\/p>/img,"");
		sHTML = sHTML.replace(/<p [^>]*?><span [^>]*?>[?\s]*?<\/span><\/p>/img,"");
		sHTML = doWithPageBreak(sHTML);	//对复制过来的分页符做处理，创建新的realELement
		sHTML = paste2defaultformat(sHTML,false);
		FCK.InsertHtml(sHTML);
	}	
	delete FCK._PasteIsRunning ;
	return false ;
}
/*校验上级标签是否包含参数tagName*/
function isValidParentTagName(elementP,tagName){
	var element = elementP;
	while( element.tagName.toUpperCase() != "BODY" ){
		element = element.parentNode;
		if(element.tagName.toUpperCase() == tagName){
			return true;
		}
	}
	return false;
}
function paste2defaultformat(html,isWord){
	var Attributes = window.Attributes;
	if(!Attributes) {
		return html;
	}
	if(Attributes["isForWord"]=="0" && isWord) {
		return html;
	}
	$("docbox").innerHTML = html;
	html = $("docbox").innerHTML;
	var bv = /<!--([^a]|a)*?-->/gi;
	html = html.replace(bv, "");
	bv = /<br\s*\/?>\s*/gi;
	html = html.replace(bv, "<br>");
	if (Attributes["d_keepall"] && Attributes["d_keepall"]!="1") {//!$("d_keepall").checked
		var qh = ",P,BR,TABLE,TBODY,TH,TR,TD,IMG,APPLET,OBJECT,EMBED,PARAM,SPAN,";
		if (Attributes["d_keepul"]=="1") {//$("d_keepul").checked
			qh += "UL,OL,LI,";
		}
		if (Attributes["d_keepa"]=="1") {//$("d_keepa").checked
			qh += "A,";
		}
		bv = /<\/?([a-zA-Z]+)(\s[^>]*)?>/gi;
		html = html.replace(bv,
		function(wu, $1) {
			if (qh.indexOf("," + $1.toUpperCase() + ",") > -1) {
				return wu;
			} else {
				return "";
			}
		});
	}	
	bv = /^[\s\r\n]*[^<\s\r\n]+/gi;
	if (bv.test(html) && FCK.GetText().trim()=="") {
		html = "<p>" + html;
	}
	//<br>2<p>
	if (Attributes["d_addbr2p"]=="1") {//$("d_addbr2p").checked
		bv = /<br>/gi;
		html = html.replace(bv, "</p><p>");
	}
	//去除多余空格
	if (Attributes["d_delspace"]=="1") {//$("d_delspace").checked
		bv = /(\s|&nbsp\;)+<\/p>/gi;
		html = html.replace(bv, "</p>");
		bv = /(\s|&nbsp\;)+<br>/gi;
		html = html.replace(bv, "<br>");
		//@空行不显示
		bv = /><\/p>/gi;
		html = html.replace(bv, "/>&nbsp\;</p>");
	}
	//去除多余行
	if (Attributes["d_delline"]=="1") {//$("d_delline").checked
		bv = /<p(\s[^>]*)?>(&nbsp\;|\s)*<\/p>/gi;
		html = html.replace(bv, "");
		bv = /(<br>)+/gi;
		html = html.replace(bv, "<br>");
	}
	//去除所有属性
	if (Attributes["d_delallattr"]=="1") {//$("d_delallattr").checked
		bv = /(<[a-zA-Z]+)(\s[^>]*)>/gi;
		html = html.replace(bv,
		function(wu, $1, $2) {
			var r = "";
			var m = $2.match(/(oldsrc|src|href|type|name|value|rowsspan|colspan|classid|codebase)=(\'[^\']+\'|\"[^\"]+\"|[^\s]+)/gi);
			if (m) {
				for (var i = 0; i < m.length; i++) {
					r += " " + m[i];
				}
			}
			return $1 + r + ">";
		});
	} else {
	//去除样式属性
		if (Attributes["d_delstyle"]=="1") {//$("d_delstyle").checked
			bv = /(<[^>]+?)(\sstyle\s*=\s*(\'[^\']*?\'|\"[^\"]*?\"))([^>]*>)/gi;
			html = html.replace(bv, "$1$4");
			bv = /(<[^>]+?)(\sclass\s*=\s*(\'[^\']*?\'|\"[^\"]*?\"|\w+))([^>]*>)/gi;
			html = html.replace(bv, "$1$4");
		}
		//去除事件属性
		if (Attributes["d_delon"]=="1") {//$("d_delon").checked
			bv = /(<[^>]+?)(\son[a-zA-Z]+\s*=\s*(\w+\([^\)]*?\)|\'[^\']*?\'|\"[^\"]*?\"))([^>]*)>/gi;
			html = html.replace(bv, "$1$4");
		}
	}
	//首行缩进2字节
	if (Attributes["d_addindent"]=="1") {//$("d_addindent").checked
		bv = /(<p(\s[^>]*)?>)(&nbsp\;|\s)*/gi;
		html = html.replace(bv, "$1");
		//@空行不显示
		bv = /><\/p>/gi;
		html = html.replace(bv, "/>&nbsp\;</p>");
	}
	//单线表格
	if (Attributes["d_addtablebc"]=="1") {//$("d_addtablebc").checked
		bv = /<table(\s[^>]*)?>/gi;
		html = html.replace(bv, "<table style=\"BORDER-COLLAPSE:collapse\" border=1 bordercolor=\"#000000\" cellpadding=2 cellspacing=0>");
	}
	//..............................html2DOM.....................
	$("docbox").innerHTML = html;
	//.......................................................
	var bz = $("docbox").getElementsByTagName("P");
	for (var i = 0; i < bz.length; i++) {
		var R = bz[i];
		/*
		//段前
		if (Attributes["d_inpmargintop"]=="1" && isWord) {//$("d_inpmargintop").checked
			R.style.marginTop = Attributes["v_inpmargintop"];//wr
		}
		//断后
		if (Attributes["d_inpmarginbottom"]=="1" && isWord) {//$("d_inpmarginbottom").checked
			R.style.marginBottom = Attributes["v_inpmarginbottom"];//wv
		}
		*/
		//首行缩进
		if (Attributes["d_addindent"]=="1") {//$("d_addindent").checked
			//R.style.textIndent = "2em";
			if(R.style.textIndent == "2em") {
				R.style.textIndent = "";
			}
			/*修改设置首行缩进后无法通过删除去掉缩进样式的bug --by@sj--2012-05-28*/
			//-table标签中的P元素不应该设置首行缩进-by@sj--2012-07-09
			if(R.tagName == "P" && !isValidParentTagName(R,"TD")){//R.parentNode.tagName.toUpperCase()!="TD"
				//避免多重设置导致缩进多次2个空格
				var sHtml = R.innerHTML;
				//去掉可能存在段首的缩进空格
				var RexSpace = /^(&nbsp\;|\s|　)*(.*?)$/gi;
				sHtml = sHtml.replace(RexSpace, "$2");
				R.innerHTML = '　　' + sHtml;
			}
		}
		//两端对齐
		if (Attributes["d_addjustify"]=="1") {//$("d_addjustify").checked
			//R.style.textAlign = "justify";
			//R.style.textJustify = "inter-ideograph";
			if(R.style.textAlign == "justify") {
				R.style.textAlign = "";
			}
			R.align = "justify";
		}
		//行距
		/*
		if (Attributes["d_inplineheight"]=="1" && isWord) {//$("d_inplineheight").checked
			R.style.lineHeight = Attributes["v_inplineheight"];//$("v_inplineheight").value
		}
		*/
	}
	//.......................................................
	//文字的字体和尺寸
	/*
	var pP = Attributes["d_selfontname"];//$("d_selfontname").value;
	var jg = Attributes["d_selfontsize"];//$("d_selfontsize").value;
	if ((pP && pP!="--系统字体--") || (jg && jg!="--系统字号--")) {
		var bz = $("docbox").getElementsByTagName("*");
		for (var i = 0; i < bz.length; i++) {
			if ("|P|DIV|TD|TH|SPAN|FONT|UL|LI|A|".indexOf("|" + bz[i].tagName + "|") >= 0) {
				if (pP && pP!="--系统字体--") {
					bz[i].style.fontFamily = pP;//获取到标签内部文字字体的属性
				}
				if (jg && jg!="--系统字号--") {
					bz[i].style.fontSize = jg;//获取到标签内部文字大小的属性
				}
			}
		}
	}
	*/
	html = $("docbox").innerHTML;
	return html;
}

function getHangJu(oStyle){
	var result =false;
	var exg = /.TRS_Editor {([^}]*)?}/img;
	oStyle = exg.exec(oStyle);
	var temp = null;
	if(oStyle && oStyle[1]){
		temp = /MARGIN-TOP:([^;}]*)?/ig.exec(oStyle);
		var top = kick(temp[1].trim());
		temp = /MARGIN-BOTTOM:([^;}]*)?/ig.exec(oStyle);
		var bottom = kick(temp[1].trim());
		if(top != "0" || bottom != "0")
			result = true;
	}
	return result;
}

function kick(temp){
	if(temp == null || temp == "") return "";
	return temp.substring(0,temp.length -2);
}
//获取word纯文本
function getPainText(sContent){
	//提取正文内容
	var regContent = /<!--StartFragment-->[\s\S]*?<!--EndFragment-->/img;
	var sRealContent = regContent.exec(sContent);
	//非word格式的呃，直接返回
	if(sRealContent != null){
		//去除注释
		sRealContent = sRealContent[0].replace(/<!--[\s\S]*?-->/img, ""); 
		//提取纯文本
		var parentEle = document.createElement("DIV");
		parentEle.style.display = 'none';
		parentEle.innerHTML = sRealContent;
		sRealContent = parentEle.innerText;
	}else{
		sRealContent = sContent;
	}
	//sRealContent = sRealContent.replace(/\n/g, '<br/>');
	return sRealContent;
}

//提取word页面设置信息
window.pickPageStyle = function pickPageStyle(sContent){
	var result = [];
	try{
		var sStyle = getStyle(sContent,true);
		var pageReg = /\s@page[\s\S]*?{([\s\S]*?)}/img;
		sStyle = pageReg.exec(sStyle);
		var margin = "";
		if(sStyle != null){
			sStyle = sStyle[1];
			var preIndex = sStyle.indexOf("size:");
			var proIndex = sStyle.indexOf(' ');
			result[0] = sStyle.substring(preIndex + 5,proIndex);
			preIndex = sStyle.indexOf("margin:");
			sStyle = sStyle.substring(preIndex + 7);
			margin = sStyle;
			proIndex = sStyle.indexOf(';');
			sStyle = sStyle.substring(0,proIndex);
			result[1] = sStyle.split(' ')[1];
			result[2] = sStyle.split(' ')[3];		
			result[3] = margin;
		}
	}catch(ex){
	}
	return result;
}

function getStyle(sContent,bWord){
	//先提取页面的所有style标记
	var regStyle = /<style>[\s\S]*<\/style>/img;
	var sStyle = regStyle.exec(sContent);
	//遍历各个style,只提取含有MSO的文件
	if(sStyle != null){
		var reExpression = /<style>[\s\S]*?<\/style>/img;
		var pFragments;
		var nLastIndex = 0;
		while(true){
			//表达式继续解析
			pFragments = reExpression.exec(sStyle[0]);
			//没有匹配的表达式时跳出循环
			if(pFragments==null)break;
			if(pFragments[0].toLowerCase().indexOf("msonormal") >=0){
				sStyle = pFragments[0];
				break;
			}
			//得到上次匹配式的最后一位到当前匹配式中最前一位之间的串
			var sTmpValue = sStyle[0].substring(nLastIndex, pFragments.index);
			nLastIndex = pFragments.index + pFragments[0].length;
		}
	}else{
		sStyle = "";
	}
	if((typeof(sStyle)).toLowerCase() != "string" && sStyle[0] != null){
		if(!(bWord && navigator.userAgent.toUpperCase().indexOf("MSIE 8") >= 0)){
			sStyle = sStyle[0];
		}
	}
	//清理MSO的style文件
	//sStyle = sStyle.replace( /([\s;{])mso-[a-z\-]+:[^;}]*?(;|})/img, '$1' ) ;
	//兼容mso-值中存在分号的特殊情况
	sStyle = sStyle.replace(/([\s;{])mso-[a-z\-]+:([^;}]*?(;|}))/img,function($0,$1,$2){
		if($2.indexOf("\\;") != -1) {
			return $0;
		}else{
			return $1;
		}
	} ) ;
	//去除注释
	sStyle = sStyle.replace(/<!--([\s\S]*?)-->/img, "$1"); 
	sStyle = sStyle.replace(/\/\*[\s\S]*?\*\//img, ""); 
	//去掉空样式
	sStyle = sStyle.replace(/[.#@]?[a-z]+[^\s]*\s*{\s*}/img, ""); 

	//增加样式作用域控制
	sStyle = sStyle.replace(/\s([.#@]?[a-z][a-z0-9\s]*?){/img, " .TRS_PreAppend $1 {");
	//style样式空白区处理
	sStyle = sStyle.replace(/([;{}])\s*/img,"$1");
	sStyle = sStyle.replace(/\s*{/img,"{");
	sStyle = sStyle.replace(/}/img,"}\n");
	sStyle = sStyle.replace(/(<style[^>]*>)\s*/img,"$1");
	return sStyle;
}

//获取word正文，以style + 内容的方式返回
window.pickWordContent = function pickWordContent(sContent,bWord){
	var sStyle = getStyle(sContent,bWord);

	//过滤冗余style定义
	sStyle = sStyle.replace(/@[\s\S]*?{[\s\S]*?}/img,"");
	//提取正文内容
	var sRealContent = "";
	var regContent = "";
	if(bWord){
		regContent = /<!--StartFragment-->[\s\S]*?<!--EndFragment-->/img;
	}else{
		regContent = /<table[^>]*?>[\s\S]*?<\/table>/img;
	}
	sRealContent = regContent.exec(sContent);
	if(sRealContent == null) return "";
	//去除注释
	if(sRealContent[0] != null){
		sRealContent = sRealContent[0].replace(/<!--[\s\S]*?-->/img, ""); 
	}
	//去除vml格式的图片和备注
	sRealContent = sRealContent.replace(/<!\[if !(vml|supportAnnotations)\]>([\s\S]*?)<!\[endif\]>/img,"$2".toUpperCase());

	//去除浮动
	var oCookie = FCK.loadCookie();
	if(!oCookie.RemovePosition){
		sRealContent = sRealContent.replace(/[;\s]position:\s*absolute\s*;/img,"");
	}
	//去掉冗余行
	sRealContent = sRealContent.replace(/<p [^>]*?>[\s?]*?<\/p>/img,"");
	sRealContent = sRealContent.replace(/<p [^>]*?><span [^>]*?>[\s?]*?<\/span><\/p>/img,"");
	var result = [sStyle,sRealContent];
	return result;
}

function pickWebContent(sContent){
	var sImgPre = "";
	//查看是否有base设置
	var bBase = false;
	var sBaseUrl = /<\s*base\s+href=(['"]?)(.*)?\1?\s*\/>/img;
	sBaseUrl = sBaseUrl.exec(sContent);
	if(sBaseUrl && sBaseUrl[2] != null){
		sImgPre = sBaseUrl[2];
		bBase = true;
	}else{
		//修正相对路径的图片，先保存好
		var sSourceURL = /\sSourceURL:\s*[^\s]*?\s/img;
		sSourceURL = sSourceURL.exec(sContent);
		if(sSourceURL[0]){
			sSourceURL = sSourceURL[0].replace(/SourceURL:\s*([^\s]*?)/img,"$1");
			var index = sSourceURL.lastIndexOf("/");
			if(index > 0){
				sImgPre = sSourceURL.substring(0,index);
			}	
		}
	}
	//依次遍历，对所有的img元素的src属性做过滤处理
	var reExpression = /<\s*IMG\s*[^>]*?>/im;
	var pFragments;
	var nLastIndex = 0;
	//调整后的输出串
	var result;
	while(true){
		//表达式继续解析
		pFragments = reExpression.exec(sContent);
		//没有匹配的表达式时跳出循环
		if(pFragments==null){
			result += sContent;
			break;
		}
		//获取匹配前的html串
		var temp = sContent.substring(0,pFragments.index);
		result += temp;
		//获取img元素
		var imgEle = sContent.substring(pFragments.index,pFragments.lastIndex);
		var rel =/\s+SRC\s*=\s*([\'\"])([^\1]*?)\1/img;
		var src = rel.exec(imgEle);
		if(src && src[2] != null){
			if(!src[2].match(/^(http|https|ftp):/ig)){
				imgEle = imgEle.replace(rel," src=$1" + formatRealteSrc(sImgPre,src[2],bBase) +"$1");
			}else{
				imgEle = imgEle.replace(rel," src=$1" + getLocalPath(src[2]) +"$1");
			}
		}
		//粘贴网页中图片，去掉IMG标签中的_fcksavedurl属性；防止粘贴wcm发出的站点图片在wcm编辑器中src被_fcksavedurl属性替换
		var fcksavedurl = /_fcksavedurl=\"[^\"]+\"/img;
		imgEle = imgEle.replace(fcksavedurl,'');
		/*by@sj-2012/1/10*/

		//调整后的img元素
		result += imgEle;
		//调整原串
		sContent = sContent.substring(pFragments.lastIndex);
	}

	//提取正文
	var regContent = /<!--StartFragment-->[\s\S]*?<!--EndFragment-->/img;
	var sRealContent = regContent.exec(result);
	//去除注释
	if(sRealContent[0] != null){
		sRealContent = sRealContent[0].replace(/<!--[\s\S]*?-->/img, ""); 
	}

	return sRealContent;
}

function formatRealteSrc(sImgPre,src,bBase){
	//trim()处理
	src = src.replace(/[(^\s+)(\s+$)]/img,"");
	if(/^.\//ig.test(src)){
		return sImgPre + src.substring(1);
	}
	if(/^\//ig.test(src)){
		if(bBase) return sImgPre + src;
		var basePath = /[^\/]\/[^\/]/ig;
		basePath = basePath.exec(sImgPre);
		if(basePath && basePath[0] != null){
			var index = sImgPre.indexOf(basePath[0]);
			return sImgPre.substring(0,index + 1) + src;
		}
	}
	return sImgPre + "/" + src;
}

function getLocalPath(src){
	//调用控件，将图片路径转为本地缓冲路径
	var result = src;
	if(window.OfficeActiveX) {
		var ocx = window.OfficeActiveX.m_OfficeActiveX;
		src = ocx.GetLocalCachedFileName(src);
		if(src && src != ""){
			result = "file:///" + src;
		}
	}
	return result
}

FCKXml.prototype.LoadUrl = function( urlToCall ){
	this.Error = false ;
	var oXmlHttp = FCKTools.CreateXmlObject( 'XmlHttp' ) ;
	if ( !oXmlHttp ){
		this.Error = true ;
		return ;
	}
	oXmlHttp.open( "GET", urlToCall, false ) ;
	oXmlHttp.send( null ) ;
	if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ){
		var sContentType = oXmlHttp.getResponseHeader('Content-Type');
		if(sContentType.indexOf('/xml')!=-1){
			this.DOMDocument = oXmlHttp.responseXML ;
		}
		else{
			this.DOMDocument = FCKTools.CreateXmlObject( 'DOMDocument' ) ;
			this.DOMDocument.async = false ;
			this.DOMDocument.resolveExternals = false ;
			this.DOMDocument.loadXML( oXmlHttp.responseText ) ;
		}
	}
	else if ( oXmlHttp.status == 0 && oXmlHttp.readyState == 4 ){
		this.DOMDocument = FCKTools.CreateXmlObject( 'DOMDocument' ) ;
		this.DOMDocument.async = false ;
		this.DOMDocument.resolveExternals = false ;
		this.DOMDocument.loadXML( oXmlHttp.responseText ) ;
	}
	else{
		this.DOMDocument = null ;
	}
	if ( this.DOMDocument == null || this.DOMDocument.firstChild == null ){
		this.Error = true ;
		if (window.confirm( 'Error loading "' + urlToCall + 
				'"\r\nDo you want to see more info?' ) ){
			alert( 'URL requested: "' + urlToCall + '"\r\n' +
			'Server response:\r\nStatus: ' + oXmlHttp.status + '\r\n' +
			'Response text:\r\n' + oXmlHttp.responseText ) ;
		}
	}
}
FCK.InsertHtml = function( html ){
	html = FCKConfig.ProtectedSource.Protect( html ) ;
	html = FCK.ProtectEvents( html ) ;
	html = FCK.ProtectUrls( html ) ;
	html = FCK.ProtectTags( html ) ;
	FCK.EditorWindow.focus() ;
	FCKUndo.SaveUndoStep() ;
	var oSel = FCK.EditorDocument.selection ;
	// Deletes the actual selection contents.
	if ( oSel.type.toLowerCase() == 'control' )
		oSel.clear() ;
	try{
		oSel.createRange().pasteHTML( html ) ;
	}catch(err){
	}
	FCKDocumentProcessor.Process( FCK.EditorDocument ) ;
}
FCK.InsertHtmlAndSelect = function( html, _nCount){
	html = FCKConfig.ProtectedSource.Protect( html ) ;
	html = FCK.ProtectEvents( html ) ;
	html = FCK.ProtectUrls( html ) ;
	html = FCK.ProtectTags( html ) ;
	FCK.EditorWindow.focus() ;
	FCKUndo.SaveUndoStep() ;
	var oSel = FCK.EditorDocument.selection ;
	if ( oSel.type.toLowerCase() == 'control' )
		oSel.clear() ;
	html = '<span id="__fakeFCKRemove__">&nbsp;</span>' + html ;
	try{
		var oRange = oSel.createRange();
		oRange.pasteHTML( html ) ;
		oRange.moveStart('character',-1*_nCount);
		oRange.select();
	}catch(err){}
	FCK.EditorDocument.getElementById('__fakeFCKRemove__').removeNode( true ) ;
	FCKDocumentProcessor.Process( FCK.EditorDocument ) ;
}
FCKStyleCommand.prototype.Execute = function( styleName, styleComboItem ){
	FCKUndo.SaveUndoStep() ;
	if ( styleComboItem.Selected )
		styleComboItem.Style.RemoveFromSelection() ;
	else{
		styleComboItem.Style.ApplyToSelection() ;
	}
	FCKUndo.SaveUndoStep() ;
	FCK.Focus() ;
	FCK.Events.FireEvent( "OnSelectionChange" ) ;
}
/*,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,20111130修改样式H1\H2等嵌套的问题，通过处理createRange修改嵌套*/
FCKStyleDef.prototype.RemoveFromSelection=function(){
	if (FCKSelection.GetType()=='Control') {
		this._RemoveMe(FCK.ToolbarSet.CurrentInstance.Selection.GetSelectedElement());
	}
	else { 
		this._RemoveMe(FCK.ToolbarSet.CurrentInstance.Selection.GetParentElement());
	}
}
FCKStyleDef.prototype.ApplyToSelection=function(){
	var A=FCK.ToolbarSet.CurrentInstance.EditorDocument.selection;
	if (A.type=='Text'){
		var B=A.createRange();
		//var reg = /<[^>]+?\sclass\s*=\s*\"(H1|H2|H3|H1Centered|Code)\"[^>]*>(.*)<\/.*>/g;
		var reg = /<div class=(H1|H2|H3|H1Centered)>(.*)<\/div>/ig;
		var reg2 = /<span class=Code>(.*)<\/span>/ig;
		var p = B.htmlText;
		p = p.replace(reg,'$2');
		p = p.replace(reg2,'$1');
		var e=document.createElement(this.Element);
		e.innerHTML=p;
		this._AddAttributes(e);
		this._RemoveDuplicates(e);
		B.pasteHTML(e.outerHTML);
	}else if (A.type=='Control'){
		var C=FCK.ToolbarSet.CurrentInstance.Selection.GetSelectedElement();
		if (C.tagName==this.Element){
			this._AddAttributes(C);
		}
	}
}
//
FCKStyleDef.prototype._RemoveDuplicates=function(A){
	for (var i=0;i<A.children.length;i++){
		var B=A.children[i];
		this._RemoveDuplicates(B);
		if (this.IsEqual(B)){ 
			FCKTools.RemoveOuterTags(B);
		}
	}
}
/*............................................................................................by@sj*/

FCKExtractCommand.prototype.Execute = function(){
	var oSel = FCK.EditorDocument.selection;
	var oRange = oSel.createRange() ;
	switch(this.type){
		case 'Title':
			var eTitle = myActualTop.$('DocTitle');
			if(eTitle)eTitle.value = oRange.text;
			break;
		case 'Abstract':
			var eAbstract = myActualTop.$('DocAbstract');
			var sHtml = '';
			sHtml = oRange.htmlText;
			if(sHtml){
				sHtml = sHtml.replace(/\s+_fckxhtmljob="\d+"/ig,'');
			}
			if(eAbstract)eAbstract.value = sHtml||oRange.text;
			break;
	}
}//end


FCKXHtml._AppendAttributes=function(A,B,C,D){
    var E=B.attributes;
    for (var n=0;n<E.length;n++){
		try{//fix ie8
			var F=E[n];
		}catch(error){
			continue;
		}
        if (F.specified){
            var G=F.nodeName.toLowerCase();
            var H;
            if (G.StartsWith('_fck')) continue;
            else if (G=='style') H=B.style.cssText.replace(FCKRegexLib.StyleProperties,FCKTools.ToLowerCase);
            else if (G=='class'||G.indexOf('on')==0) H=F.nodeValue;
            else if (D=='body'&&G=='contenteditable') continue;
            else if (F.nodeValue===true) H=G;
            else{
                try{
                    H=B.getAttribute(G,2);
                }catch (e) {}
            };
            this._AppendAttribute(C,G,H||F.nodeValue);
        }
    }
};

FCKXHtml._AppendAttribute=function(A,B,C){
	try{
		if (C==undefined||C==null) C='';
		else if (C.replace){
			if (FCKConfig.ForceSimpleAmpersand)			C=C.replace(/&/g,'___FCKAmp___');
			//C=C.replace(FCKXHtmlEntities.EntitiesRegex,FCKXHtml_GetEntity);
		};
		
		var D=this.XML.createAttribute(B);
		D.value=C;
		A.attributes.setNamedItem(D);
	}catch (e){}
};

FCKStyleDef.prototype._AddAttributes=function(A){
	for (var a in this.Attributes){
		switch (a.toLowerCase()){
			case 'style':
				A.style.cssText=this.Attributes[a];
				break;
			case 'class':
				var sClass = Ext.isIE8 ? 'class' : 'className';				
				A.setAttribute(sClass,this.Attributes[a],0);
				A.className = this.Attributes[a];
				break;
			case 'src':
				A.setAttribute('_fcksavedurl',this.Attributes[a],0);
			default:
				A.setAttribute(a,this.Attributes[a],0);
		}
	}
};//end

FCKStyleDef.prototype.IsEqual=function(e){
	if (e.tagName!=this.Element) return false;
	for (var a in this.Attributes){
		switch (a.toLowerCase()){
			case 'style':
				if (e.style.cssText.toLowerCase()!=this.Attributes[a].toLowerCase())
					return false;
				break;
			case 'class':
				if(e.className == this.Attributes[a]) return true;
				if (e.getAttribute('className',0)!=this.Attributes[a]) 
					return false;
				break;
			default:
				if (e.getAttribute(a,0)!=this.Attributes[a]) 
					return false;
		}
	};
	return true;
};

FCKEditingArea.prototype.Start=function(A,B){
	var C=this.TargetElement;
	var D=FCKTools.GetElementDocument(C);
	while(C.childNodes.length>0) 
		C.removeChild(C.childNodes[0]);
	if (this.Mode==0){
		var E=this.IFrame=D.createElement('iframe');
		E.src='javascript:void(0)';
		E.frameBorder=0;E.width=E.height='100%';
		C.appendChild(E);
		if (FCKBrowserInfo.IsIE) 
			A=A.replace(/(<base[^>]*?)\s*\/?>(?!\s*<\/base>)/gi,'$1></base>');
		else if (!B){if (FCKBrowserInfo.IsGecko) 
			A=A.replace(/(<body[^>]*>)\s*(<\/body>)/i,'$1'+GECKO_BOGUS+'$2');
		var F=A.match(FCKRegexLib.BodyContents);
		if (F){A=F[1]+'&nbsp;'+F[3];this._BodyHTML=F[2];}
		else this._BodyHTML=A;};this.Window=E.contentWindow;
		var G=this.Document=this.Window.document;G.open();G.write(A);G.close();
		if (FCKBrowserInfo.IsGecko10&&!B){this.Start(A,true);return;};
		this.Window._FCKEditingArea=this;
		if (FCKBrowserInfo.IsGecko10) 
			this.Window.setTimeout(FCKEditingArea_CompleteStart,500);
		else FCKEditingArea_CompleteStart.call(this.Window);}
		else{var H=this.Textarea=D.createElement('textarea');H.className='SourceField';H.dir='ltr';H.style.height= Ext.isIE6 ? '100%' :'97%';H.style.border='none';C.appendChild(H);H.value=A;FCKTools.RunFunction(this.OnLoad);}
};


//修复当在编辑器空白情况下,第一次插入表格的时候,不能撤销
FCKUndo.SaveUndoStep=function(){
	if (FCK.EditMode!=0) return;
	FCKUndo.SavedData=FCKUndo.SavedData.slice(0,FCKUndo.CurrentIndex+1);
	var A=FCK.EditorDocument.body.innerHTML;
	if (FCKUndo.CurrentIndex>0&&A==FCKUndo.SavedData[FCKUndo.CurrentIndex][0]) 
		return;
	if (FCKUndo.CurrentIndex+1>=FCKConfig.MaxUndoLevels) 
		FCKUndo.SavedData.shift();
	else FCKUndo.CurrentIndex++;
	var B;
	if (FCK.EditorDocument.selection.type=='Text') 
		B=FCK.EditorDocument.selection.createRange().getBookmark();
	FCKUndo.SavedData[FCKUndo.CurrentIndex]=[A,B];
	FCK.Events.FireEvent("OnSelectionChange");
};

FCK._IsFunctionKey = function( keyCode )
{
	// keys that are captured but do not change editor contents
	if ( keyCode >= 16 && keyCode <= 20 )
		// shift, ctrl, alt, pause, capslock
		return true ;
	if ( keyCode == 27 || ( keyCode >= 33 && keyCode <= 40 ) )
		// esc, page up, page down, end, home, left, up, right, down
		return true ;
	if ( keyCode == 45 )
		// insert, no effect on FCKeditor, yet
		return true ;
	return false ;
};

window.Doc_OnKeyDown = function(evt)
{
	if (! evt)
		evt = FCK.EditorWindow.event ;
	if ( FCK.EditorWindow )
	{
		var e = FCK.EditorWindow.event;
		if ( !FCK._IsFunctionKey(evt.keyCode) // do not capture function key presses, like arrow keys or shift/alt/ctrl
				&& !(evt.ctrlKey || evt.metaKey) // do not capture Ctrl hotkeys, as they have their snapshot capture logic
				&& !( e.keyCode >=16 && e.keyCode <= 18 ) )
			Doc_OnKeyDownUndo() ;
	}
	return true ;
}







}//end function just4IE
just4IE();

//Gecko only
function just4Gecko(){
	if(Ext.isIE)return;
	FCKDialog.Show0 = FCKDialog.Show;
	FCKDialog.Show = function( dialogInfo, dialogName, pageUrl, dialogWidth, dialogHeight, parentWindow, resizable ){
		if(!window.showModalDialog){
			return FCKDialog.Show0.apply(FCKDialog, arguments);
		}
		if ( !parentWindow )
			parentWindow = window ;
		var iTop  = (FCKConfig.ScreenHeight - dialogHeight) / 2 ;
		var iLeft = (FCKConfig.ScreenWidth  - dialogWidth)  / 2 ;

		var sOptions = 'help:no;scroll:no;status:no;' +
			';resizable:'  + ( resizable ? 'yes' : 'no' ) +
			';dialogWidth:' + dialogWidth + 'px' +
			';dialogHeight:' + dialogHeight + 'px' +
			';dialogTop:' + iTop + 'px' +
			';dialogLeft:' + iLeft + 'px' ;

		FCKFocusManager.Lock() ;

		var oReturn = 'B' ;

		try
		{
			oReturn = parentWindow.showModalDialog( pageUrl, dialogInfo, sOptions ) ;
		}
		catch( e ) {}

		if ( 'B' === oReturn )
			alert( FCKLang.DialogBlocked ) ;

		FCKFocusManager.Unlock() ;
	}
	//---添加行距功能2010-9-6 by HDG（FF）
	FCK.ExecuteNamedCommand = function( commandName, commandParameter, noRedirect ){
		if(!FCK.EditorDocument||!FCK.EditorDocument.body)return;
		FCKUndo.SaveUndoStep() ;
		if(commandName=='LineHeight'){
			FCK.TRSExtend.doLineHeight(commandParameter);
			FCK.Events.FireEvent( 'OnSelectionChange' ) ;
		}
		else if ( !noRedirect && FCK.RedirectNamedCommands[ commandName ] != null ){
			FCK.ExecuteRedirectedNamedCommand( commandName, commandParameter ) ;
		}
		else{
			FCK.Focus() ;
			FCK.EditorDocument.execCommand( commandName, false, commandParameter ) ; 
			FCK.Events.FireEvent( 'OnSelectionChange' ) ;
		}
		FCKUndo.SaveUndoStep() ;
	}
	FCK.TRSExtend ={};
	//添加行距设置2010-9-6 by hdg
	Ext.apply(FCK.TRSExtend, {
		_doLineHeight : function(oFont,_sLineHeight,_bSet){
			if(_bSet){
				oFont.style.lineHeight = _sLineHeight;
			}
			var nCount = oFont.childNodes.length, objTemp ,sTagTemp;
			for(var i=0; i<nCount; i++){
				objTemp = oFont.childNodes[i];
				sTagTemp = objTemp.tagName;
				if(sTagTemp == "TD" || sTagTemp == "FONT"){
					FCK.TRSExtend._doLineHeight(objTemp,_sLineHeight,true);
				}
				else if(objTemp.nodeType==1){
					if(objTemp.style.lineHeight){
						objTemp.style.lineHeight = _sLineHeight;
					}
					FCK.TRSExtend._doLineHeight(objTemp,_sLineHeight,objTemp.style.lineHeight!=null);
				}
			}
		},
		doLineHeight : function(_sLineHeight){
			var oSel = FCK.EditorWindow.getSelection();
			var oRange = FCK.EditorWindow.getSelection() ;
			if (true|| oSel.type.toLowerCase() != 'none' || oRange.htmlText!=''){
				if(oRange.text==''){
					oRange.pasteHTML('&nbsp;') ;
					oRange.collapse(true) ;
					oRange.moveStart('character',-1);
					oRange.select();
				}
				FCK.EditorDocument.execCommand( 'FontSize', false, 0) ;
				var eFontNews = FCK.EditorDocument.getElementsByTagName('font');
				for(var i=0;i<eFontNews.length;i++){
					var eFont = eFontNews[i];
						if(eFont.getAttribute('size')){
							//删除execCommand添加的size属性
							eFont.removeAttribute('size');
							FCK.TRSExtend._doLineHeight(eFont,_sLineHeight,true);
						}
				 }
			}
		}
	});
}
just4Gecko();

//重写编辑器点击按钮触发方法，传入当前window参数，处理元数据解决全屏问题-by@sj-
FCKToolbarButton.prototype.Click = function() {
	var A = this._ToolbarButton || this;
	FCK.ToolbarSet.CurrentInstance.Commands.GetCommand(A.CommandName).Execute(window);
};
FCKFitWindow.prototype.Execute=function(win){
	this.IsMaximized = !this.IsMaximized;
	if(myActualTop.FullOpenEditor)myActualTop.FullOpenEditor(this.IsMaximized,win);
	FCKToolbarItems.GetItem('FitWindow').RefreshState() ;
}

//调整自定义样式逻辑，默认改为Body上加，保存时处理
FCKEditingArea.prototype.MakeEditable=function(){
	var A=this.Document;
	if(FCKConfig.AutoAppendStyle){
		A.body.className = "TRS_Editor";
	}
	if (FCKBrowserInfo.IsIE){
		A.body.contentEditable=true;}
	else{
		try{A.body.spellcheck=(this.FFSpellChecker!==false);if (this._BodyHTML){A.body.innerHTML=this._BodyHTML;this._BodyHTML=null;};A.designMode='on';try{A.execCommand('styleWithCSS',false,FCKConfig.GeckoUseSPAN);}catch (e){A.execCommand('useCSS',false,!FCKConfig.GeckoUseSPAN);};A.execCommand('enableObjectResizing',false,!FCKConfig.DisableObjectResizing);A.execCommand('enableInlineTableEditing',false,!FCKConfig.DisableFFTableHandles);}catch (e) {}
	}
};

//调整分页标题为非模态对话框
FCKDialog.Show=function(A,B,C,D,E,F,G){
	if (!F) F=window;var H='help:no;scroll:no;status:no;resizable:'+(G?'yes':'no')+';dialogWidth:'+D+'px;dialogHeight:'+E+'px';FCKFocusManager.Lock();
	var I='B';try{
		if(B == "FCKDialog_InsertSepTitle") I=F.showModelessDialog(C,A,H);
		else I=F.showModalDialog(C,A,H);
	}catch(e) {};if ('B'===I) alert(FCKLang.DialogBlocked);FCKFocusManager.Unlock();
};

//重新设置系统配置项
var oCookies = FCK.loadCookie();
setConfig(oCookies,"AutoDetectPaste");
setConfig(oCookies,"ForcePasteAsPlainText");
setConfig(oCookies,"PasteToUnionStyle");
setConfig(oCookies,"AutoAppendStyle");
setConfig(oCookies,"IgnoreFont");
setConfig(oCookies,"IgnoreBlock");
setConfig(oCookies,"RemoveStyles");
setConfig(oCookies,"RemoveEmpty");
setConfig(oCookies,"RemovePosition");
setConfig(oCookies,"KeepLineHeight");
setConfig(oCookies,"RemovePageWidth");

function setConfig(oCookies,item){
	if(!oCookies[item]) return;
	if(oCookies[item] == "1") FCKConfig[item] = true
	if(oCookies[item] == "0") FCKConfig[item] = false;
}

setDeConfig(oCookies,"ClipBoardMode");

function setDeConfig(oCookies,item){
	if(!oCookies[item]) return;
	if(oCookies[item] == "1") FCKConfig[item] = false
	if(oCookies[item] == "0") FCKConfig[item] = true;
}

FCKEditingArea.prototype.MakeEditable=function(){
	var A=this.Document;
	if (FCKBrowserInfo.IsIE){
		A.body.contentEditable=true;
		A.body.className = "TRS_Editor innerFrameBody";
	}else{
		try{A.body.spellcheck=(this.FFSpellChecker!==false);
			if (this._BodyHTML){
				A.body.innerHTML=this._BodyHTML;this._BodyHTML=null;
			};
			A.designMode='on';
			try{
				A.execCommand('styleWithCSS',false,FCKConfig.GeckoUseSPAN);
			}catch (e){
				A.execCommand('useCSS',false,!FCKConfig.GeckoUseSPAN);};
				A.execCommand('enableObjectResizing',false,!FCKConfig.DisableObjectResizing);
				A.execCommand('enableInlineTableEditing',false,!FCKConfig.DisableFFTableHandles);
		}catch (e) {}
	}
	//为“自定义样式”设置容器
	if(FCKConfig.AutoAppendStyle){
		A.body.className = "TRS_Editor";
	}
};

function pasteToUnionStyle(){
	// 判断是否来自于Office系列内容，如果是需要处理一下
	var oCookie = FCK.loadCookie();
	var html ="";
	try{
		if(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0"){
			html = FCK.GetClipboardHTML2();
		}else{
			if(window.OfficeActiveX) {
				html = window.OfficeActiveX.GetWordContent();
			}
		}
	}catch(err){}

	var re = /<\w[^>]*(( class="?MsoNormal"?)|(="mso-))/gi ;
	var re2 = /<v:imagedata /gi;
	var reColGroup = /<COLGROUP>/gi;
	var reExcel = /<\w[^>]* class="?xl"?/gi ;
	if ( re.test( html ) || reColGroup.test(html) || reExcel.test(html) || re2.test(html)){
		
		var reColGroup = /<COLGROUP>/gi;
		var reExcel = /<\w[^>]* class="?xl"?/gi ;
		if(reColGroup.test(html) || reExcel.test( html )){//为Excel
			html = cleanExcelCode(html);
		}
		else{
			//判断是否为Word
			var re = /<\w[^>]* class="?MsoNormal"?/gi ;
			var re2 = /<v:imagedata /gi;
			if ( re.test( html ) || re2.test( html ) ){
				if(oCookie.ClipBoardMode && oCookie.ClipBoardMode == "0"){
					if(window.OfficeActiveX){
						window.OfficeActiveX.FormatWordClip();
					}
					html = FCK.GetClipboardHTML2();
				}else{
					if(window.OfficeActiveX) {
						var rel = pickWordContent(window.OfficeActiveX.GetWordContent(),true);
						html = rel[0] + rel[1];
					}
				}
			}
		}		
	}
	
	// 清除垃圾代码、统一格式、采用一个固定的样式控制
	var sUnionClassName = FCKConfig.UnionStyleName || 'Custom_UnionStyle';
	html = unionStyle(html , sUnionClassName, true) ;	

	// 插入数据
	FCK.InsertHtml( html ) ;

	// 顶级元素追加统一的样式
	var sHtml = FCK_GetHTML();
	var vMatchAll = new RegExp('^<div class=\"?'+sUnionClassName+'\"?>((\n|\r|.)*)<\/div>$','img');
	if(sHtml.search(vMatchAll) < 0){
		FCKUndo.SaveUndoStep() ;
		FCK.SetHTML("<div class=\""+sUnionClassName+"\">" + sHtml + "</div>");
	}	
}
FCKContextMenu.prototype.AttachToElement=function(A){
	if (FCKBrowserInfo.IsIE) {
		A.attachEvent("oncontextmenu",function(){return false;});
		FCKTools.AddEventListenerEx(A,'contextmenu',FCKContextMenu_AttachedElement_OnContextMenu,this);
	}
	else A._FCKContextMenu=this;};


function Doc_OnPaste(){
	var A=FCK.EditorDocument.body;
	A.detachEvent('onpaste',Doc_OnPaste);
	var B=FCK.PasteTest(!FCKConfig.ForcePasteAsPlainText&&!FCKConfig.AutoDetectPasteFromWord);
	A.attachEvent('onpaste',Doc_OnPaste);
	return B;
};

FCK.PasteTest = function(A){
	if (FCK.Status!=2||!FCK.Events.FireEvent('OnPaste')) 
		return false;
	return A || FCK.Paste();
}

function wrapBRWithPTag(_sHtml,BHangJu){
	var sHTMLGroup = _sHtml.split("\n");
	if(sHTMLGroup.length <= 1) return _sHtml;
	var sResult = "";
	if(sHTMLGroup[0].trim() == "") sResult += "";
	else sResult += "<p>" + sHTMLGroup[0];	
	var temp = "";
	for(var i=1;i < sHTMLGroup.length; i++){
		temp = sHTMLGroup[i];
		if(sHTMLGroup[i-1].trim() != "" && temp.trim() != ""){
			if(i == sHTMLGroup.length - 1) sResult += ("<br>" + temp + "</p>");
			else sResult += ("<br>" + temp);
		}else if(sHTMLGroup[i-1].trim() != "" && temp.trim() == ""){
			sResult += ("</p>");
		}else if(sHTMLGroup[i-1].trim() == "" && temp.trim() != ""){
			if(i == sHTMLGroup.length - 1) sResult += ("<p>" + temp + "</p>");
			else sResult += ("<p>" + temp);
		}else if(sHTMLGroup[i-1].trim() == "" && temp.trim() == ""){
			sResult += ("<br/>");
		}
	}
	return sResult;

}

FCK.RemoveEditorCss = function(){
	try{
		FCKConfig.EditorAreaCSS = [
			FCKConfig.BasePath + 'css/fck_editorarea.css',
			FCKConfig.BasePath + 'css/fck_editorarea_standard.css'
		];
	}catch(ex){
	}
}

FCK.AddEditorCss = function(){
	try{
		FCKConfig.EditorAreaCSS = [
			FCKConfig.BasePath + 'css/fck_editorarea.css',
			FCKConfig.BasePath + 'css/fck_editorarea_standard.css',
			FCKConfig.BasePath + 'css/editor.css'
		];
	}catch(ex){
	}
}
function doWithPageBreak(sHtml){
	var reg = /<img[^>]*class="?FCK__PageBreak"?[^>]*>/img;
	var reg2= /<img[^>]*class="?FCK__PageBreak"?[^>]*>/im;
	while(reg.exec(sHtml)){
		var e = FCK.EditorDocument.createElement('TRS_PAGE_SEPARATOR') ;
		var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__PageBreak', e ) ;

		sHtml = sHtml.replace(reg2,oFakeImage.outerHTML);
	}
	return sHtml;
}
