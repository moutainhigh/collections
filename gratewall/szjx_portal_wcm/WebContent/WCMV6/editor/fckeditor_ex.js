	function ExecuteCommand( commandName ){
		var bIsWysiwyg = ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ) ;
		switch(commandName.toLowerCase()){
			case 'source':
				if(bIsWysiwyg){
					FCK.SwitchEditMode(true) ;
				}
				break;
			case 'design':
				if(!bIsWysiwyg){
					FCK.SwitchEditMode(true) ;
				}
				break;
			default:
				if(bIsWysiwyg){
					// Execute the command.
					FCKCommands.GetCommand( commandName ).Execute() ;
				}
		}
	}
	function SwitchMode(_sMode){
		var eSource = $('editor_btn_source');
		var eDesign = $('editor_btn_design');
		if(_sMode=='Source'){
			Element.addClassName(eSource,'toolbar_current_btn');
			Element.removeClassName(eDesign,'toolbar_current_btn');
		}
		else if(_sMode=='Design'){
			Element.addClassName(eDesign,'toolbar_current_btn');
			Element.removeClassName(eSource,'toolbar_current_btn');
		}
	}
	function ToggleToolbar(_sName){
		if(_sName=='WCM6'){
			$('xToolbarRow').className = 'editor_toolbar';
			$('xToolbar').className = 'TB_ToolbarSet xToolbar';
			var oParentBlank = (top.actualTop||top).$('props_blank');
			if(oParentBlank){
//				oParentBlank.className = 'props_blank1';
			}
		}
		else if(_sName=='WCM6_ADV'){
			$('xToolbarRow').className = 'editor_toolbar_adv';
			$('xToolbar').className = 'TB_ToolbarSet xToolbar_adv';
			var oParentBlank = (top.actualTop||top).$('props_blank');
			if(oParentBlank){
//				oParentBlank.className = 'props_blank2';
			}
		}
	}
	FCKToolbarFontSizeCombo.prototype.CreateItems = function( targetSpecialCombo )
	{
		targetSpecialCombo.FieldWidth = 70 ;
		
		var aSizes = FCKConfig.FontSizes.split(';') ;
		
		for ( var i = 0 ; i < aSizes.length ; i++ )
		{
			var aSizeParts = aSizes[i].split('/') ;
			this._Combo.AddItem( aSizeParts[0], '<div style="font-size:12px">'+aSizeParts[1]+'</div>', aSizeParts[1] ) ;
	//		this._Combo.AddItem( aSizeParts[0], '<div style="font-size:'+aSizeParts[0]+'">'+aSizeParts[1]+'</div>', aSizeParts[1] ) ;
		}
	}
	FCKFontSizeCommand.prototype.Execute = function( fontSize )
	{
	//	if ( typeof( fontSize ) == 'string' ) fontSize = parseInt(fontSize) ;

		if ( fontSize == null || fontSize == '' )
		{
			// TODO: Remove font size attribute (Now it works with size 3. Will it work forever?)
			FCK.ExecuteNamedCommand( 'FontSize', '12pt' ) ;
		}
		else
			FCK.ExecuteNamedCommand( 'FontSize', fontSize ) ;
	}
	FCKToolbarFontsCombo.prototype.CreateItems = function( targetSpecialCombo )
	{
		var aFonts = FCKConfig.FontNames.split(';') ;
		
		for ( var i = 0 ; i < aFonts.length ; i++ )
			this._Combo.AddItem( aFonts[i], '<div style="font-size:12px;font-family:'+aFonts[i]+'">'+ aFonts[i] + '</div>' ) ;
		delete aFonts;
	}
	FCK.ExecuteNamedCommand = function( commandName, commandParameter, noRedirect )
	{
		if(!FCK.EditorDocument||!FCK.EditorDocument.body)return;
		FCKUndo.SaveUndoStep() ;
		if( commandName=='FontSize' ){
			FCK.TRSExtend.doFontSize(commandParameter);
			FCK.Events.FireEvent( 'OnSelectionChange' ) ;
		}
		else if ( !noRedirect && FCK.RedirectNamedCommands[ commandName ] != null )
			FCK.ExecuteRedirectedNamedCommand( commandName, commandParameter ) ;
		else
		{
			FCK.Focus() ;
			FCK.EditorDocument.execCommand( commandName, false, commandParameter ) ; 
			FCK.Events.FireEvent( 'OnSelectionChange' ) ;
		}
		
		FCKUndo.SaveUndoStep() ;
	}
	function FCK_ContextMenu_GetListener( listenerName )
	{
		switch ( listenerName )
		{
			case 'Generic' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					menu.AddSeparator() ;
					menu.AddItem( 'SelectAll'	, FCKLang.SelectAll ,18, FCKCommands.GetCommand( 'SelectAll' ).GetState() == FCK_TRISTATE_DISABLED ) ;
					if(!FCKConfig.SimpleEditor){
						menu.AddSeparator() ;
						menu.AddItem( 'PageBreak'	, FCKLang.PageBreak ,43) ;
					}
					if(FCKConfig.SupportExtract&&FCKSelection.GetType()=='Text'){
						menu.AddSeparator() ;
						menu.AddItem( 'ExtractTitle', '提取为标题') ;
						menu.AddItem( 'ExtractAbstract', '提取为摘要') ;
					}
				}} ;

			case 'Table' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					var bIsTable	= ( tagName == 'TABLE' ) ;
					var bIsCell		= ( !bIsTable && FCKSelection.HasAncestorNode( 'TABLE' ) ) ;
					
					if ( bIsCell )
					{
						menu.AddSeparator() ;
						var oItem = menu.AddItem( 'Cell'	, FCKLang.CellCM ) ;
						oItem.AddItem( 'TableInsertCell'	, FCKLang.InsertCell, 58 ) ;
						oItem.AddItem( 'TableDeleteCells'	, FCKLang.DeleteCells, 59 ) ;
						oItem.AddItem( 'TableMergeCells'	, FCKLang.MergeCells, 60 ) ;
						oItem.AddItem( 'TableSplitCell'		, FCKLang.SplitCell, 61 ) ;
						oItem.AddSeparator() ;
						oItem.AddItem( 'TableCellProp'		, FCKLang.CellProperties, 57 ) ;

						menu.AddSeparator() ;
						oItem = menu.AddItem( 'Row'			, FCKLang.RowCM ) ;
						oItem.AddItem( 'TableInsertRow'		, FCKLang.InsertRow, 62 ) ;
						oItem.AddItem( 'TableDeleteRows'	, FCKLang.DeleteRows, 63 ) ;
						
						menu.AddSeparator() ;
						oItem = menu.AddItem( 'Column'		, FCKLang.ColumnCM ) ;
						oItem.AddItem( 'TableInsertColumn'	, FCKLang.InsertColumn, 64 ) ;
						oItem.AddItem( 'TableDeleteColumns'	, FCKLang.DeleteColumns, 65 ) ;
					}

					if ( bIsTable || bIsCell )
					{
						menu.AddSeparator() ;
						menu.AddItem( 'TableDelete'			, FCKLang.TableDelete ) ;
						menu.AddItem( 'TableProp'			, FCKLang.TableProperties, 39 ) ;
					}
				}} ;

			case 'Link' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					var bInsideLink = ( tagName == 'A' || FCKSelection.HasAncestorNode( 'A' ) ) ;

					if ( bInsideLink || FCK.GetNamedCommandState( 'Unlink' ) != FCK_TRISTATE_DISABLED )
					{
						// Go up to the anchor to test its properties
						var oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;
						var bIsAnchor = ( oLink && oLink.name.length > 0 && oLink.href.length == 0 ) ;
						// If it isn't a link then don't add the Link context menu
						if ( bIsAnchor )
							return ;

						menu.AddSeparator() ;
						if ( bInsideLink )
							menu.AddItem( 'Link', FCKLang.EditLink		, 34 ) ;
						menu.AddItem( 'Unlink'	, FCKLang.RemoveLink	, 35 ) ;
					}
				}} ;
			case 'Image' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					if ( tagName == 'IMG' && !tag.getAttribute( '_fckfakelement' ) )
					{
						menu.AddSeparator() ;
						menu.AddItem( 'Image', FCKLang.ImageProperties, 37 ) ;
					}
				}} ;

			case 'Anchor' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					// Go up to the anchor to test its properties
					var oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;
					var bIsAnchor = ( oLink && oLink.name.length > 0 ) ;

					if ( bIsAnchor || ( tagName == 'IMG' && tag.getAttribute( '_fckanchor' ) ) )
					{
						menu.AddSeparator() ;
						menu.AddItem( 'Anchor', FCKLang.AnchorProp, 36 ) ;
					}
				}} ;

			case 'Flash' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					if ( tagName == 'IMG' && tag.getAttribute( '_fckflash' ) )
					{
						menu.AddSeparator() ;
						menu.AddItem( 'Flash', FCKLang.FlashProperties, 38 ) ;
					}
				}} ;
			case 'BulletedList' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					if ( FCKSelection.HasAncestorNode('UL') )
					{
						menu.AddSeparator() ;
						menu.AddItem( 'BulletedList', FCKLang.BulletedListProp, 27 ) ;
					}
				}} ;
			case 'NumberedList' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					if ( FCKSelection.HasAncestorNode('OL') )
					{
						menu.AddSeparator() ;
						menu.AddItem( 'NumberedList', FCKLang.NumberedListProp, 26 ) ;
					}
				}} ;
			case 'Comment' :
				return {
				AddItems : function( menu, tag, tagName )
				{
					if ( tagName == 'SPAN' && tag.getAttribute('_trscomment',2) )
					{
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
				}} ;
		}
		return null ;
	}
	FCKCommands.GetCommand = function( commandName )
	{
		var oCommand = FCKCommands.LoadedCommands[ commandName ] ;
		
		if ( oCommand )
			return oCommand ;

		switch ( commandName )
		{
			case 'DocProps'		: oCommand = new FCKDialogCommand( 'DocProps'	, FCKLang.DocProps				, 'dialog/fck_docprops.html'	, 400, 390, FCKCommands.GetFullPageState ) ; break ;
			case 'Templates'	: oCommand = new FCKDialogCommand( 'Templates'	, FCKLang.DlgTemplatesTitle		, 'dialog/fck_template.html'	, 440, 400 ) ; break ;
			case 'Link'			: oCommand = new FCKDialogCommand( 'Link'		, FCKLang.DlgLnkWindowTitle		, 'dialog/fck_link.html'		, 440, 180, FCK.GetNamedCommandState, 'CreateLink' ) ; break ;
			case 'Unlink'		: oCommand = new FCKUnlinkCommand() ; break ;
			case 'Anchor'		: oCommand = new FCKDialogCommand( 'Anchor'		, FCKLang.DlgAnchorTitle		, 'dialog/fck_anchor.html'		, 370, 120 ) ; break ;
			case 'BulletedList'	: oCommand = new FCKDialogCommand( 'BulletedList', FCKLang.BulletedListProp		, 'dialog/fck_listprop.html'	, 370, 120 ) ; break ;
			case 'NumberedList'	: oCommand = new FCKDialogCommand( 'NumberedList', FCKLang.NumberedListProp		, 'dialog/fck_listprop.html?OL'	, 370, 120 ) ; break ;
			case 'About'		: oCommand = new FCKDialogCommand( 'About'		, FCKLang.About					, 'dialog/fck_about.html'		, 400, 330 ) ; break ;

			case 'Find'			: oCommand = new FCKDialogCommand( 'Find'		, FCKLang.DlgFindTitle			, 'dialog/fck_find.html'		, 440, 150 ) ; break ;
			case 'Replace'		: oCommand = new FCKDialogCommand( 'Replace'	, FCKLang.DlgReplaceTitle		, 'dialog/fck_replace.html'		, 440, 150 ) ; break ;

			case 'Image'		: oCommand = new FCKDialogCommand( 'Image'		, FCKLang.DlgImgTitle			, 'dialog/fck_image.html'		, 500, 180 ) ; break ;
			case 'Flash'		: oCommand = new FCKDialogCommand( 'Flash'		, FCKLang.DlgFlashTitle			, 'dialog/fck_flash.html'		, 450, 140 ) ; break ;
			case 'SpecialChar'	: oCommand = new FCKDialogCommand( 'SpecialChar', FCKLang.DlgSpecialCharTitle	, 'dialog/fck_specialchar.html'	, 560, 400 ) ; break ;
			case 'Smiley'		: oCommand = new FCKDialogCommand( 'Smiley'		, FCKLang.DlgSmileyTitle		, 'dialog/fck_smiley.html'		, FCKConfig.SmileyWindowWidth, FCKConfig.SmileyWindowHeight ) ; break ;
			case 'Table'		: oCommand = new FCKDialogCommand( 'Table'		, FCKLang.DlgTableTitle			, 'dialog/fck_table.html'		, 540, 160 ) ; break ;
			case 'TableProp'	: oCommand = new FCKDialogCommand( 'Table'		, FCKLang.DlgTableTitle			, 'dialog/fck_table.html?Parent', 540, 160 ) ; break ;
			case 'TableCellProp': oCommand = new FCKDialogCommand( 'TableCell'	, FCKLang.DlgCellTitle			, 'dialog/fck_tablecell.html'	, 540, 180 ) ; break ;
			case 'UniversalKey'	: oCommand = new FCKDialogCommand( 'UniversalKey', FCKLang.UniversalKeyboard	, 'dialog/fck_universalkey.html', 415, 300 ) ; break ;

			case 'Style'		: oCommand = new FCKStyleCommand() ; break ;

			case 'FontName'		: oCommand = new FCKFontNameCommand() ; break ;
			case 'FontSize'		: oCommand = new FCKFontSizeCommand() ; break ;
			case 'FontFormat'	: oCommand = new FCKFormatBlockCommand() ; break ;

			case 'Source'		: oCommand = new FCKSourceCommand() ; break ;
			case 'Preview'		: oCommand = new FCKPreviewCommand() ; break ;
			case 'Save'			: oCommand = new FCKSaveCommand() ; break ;
			case 'NewPage'		: oCommand = new FCKNewPageCommand() ; break ;
			case 'PageBreak'	: oCommand = new FCKPageBreakCommand() ; break ;

			case 'TextColor'	: oCommand = new FCKTextColorCommand('ForeColor') ; break ;
			case 'BGColor'		: oCommand = new FCKTextColorCommand('BackColor') ; break ;

			case 'PasteText'	: oCommand = new FCKPastePlainTextCommand() ; break ;
			case 'PasteWord'	: oCommand = new FCKPasteWordCommand() ; break ;

			case 'TableInsertRow'		: oCommand = new FCKTableCommand('TableInsertRow') ; break ;
			case 'TableDeleteRows'		: oCommand = new FCKTableCommand('TableDeleteRows') ; break ;
			case 'TableInsertColumn'	: oCommand = new FCKTableCommand('TableInsertColumn') ; break ;
			case 'TableDeleteColumns'	: oCommand = new FCKTableCommand('TableDeleteColumns') ; break ;
			case 'TableInsertCell'		: oCommand = new FCKTableCommand('TableInsertCell') ; break ;
			case 'TableDeleteCells'		: oCommand = new FCKTableCommand('TableDeleteCells') ; break ;
			case 'TableMergeCells'		: oCommand = new FCKTableCommand('TableMergeCells') ; break ;
			case 'TableSplitCell'		: oCommand = new FCKTableCommand('TableSplitCell') ; break ;
			case 'TableDelete'			: oCommand = new FCKTableCommand('TableDelete') ; break ;

			case 'Form'			: oCommand = new FCKDialogCommand( 'Form'		, FCKLang.Form			, 'dialog/fck_form.html'		, 380, 230 ) ; break ;
			case 'Checkbox'		: oCommand = new FCKDialogCommand( 'Checkbox'	, FCKLang.Checkbox		, 'dialog/fck_checkbox.html'	, 380, 230 ) ; break ;
			case 'Radio'		: oCommand = new FCKDialogCommand( 'Radio'		, FCKLang.RadioButton	, 'dialog/fck_radiobutton.html'	, 380, 230 ) ; break ;
			case 'TextField'	: oCommand = new FCKDialogCommand( 'TextField'	, FCKLang.TextField		, 'dialog/fck_textfield.html'	, 380, 230 ) ; break ;
			case 'Textarea'		: oCommand = new FCKDialogCommand( 'Textarea'	, FCKLang.Textarea		, 'dialog/fck_textarea.html'	, 380, 230 ) ; break ;
			case 'HiddenField'	: oCommand = new FCKDialogCommand( 'HiddenField', FCKLang.HiddenField	, 'dialog/fck_hiddenfield.html'	, 380, 230 ) ; break ;
			case 'Button'		: oCommand = new FCKDialogCommand( 'Button'		, FCKLang.Button		, 'dialog/fck_button.html'		, 380, 230 ) ; break ;
			case 'Select'		: oCommand = new FCKDialogCommand( 'Select'		, FCKLang.SelectionField, 'dialog/fck_select.html'		, 400, 380 ) ; break ;
			case 'ImageButton'	: oCommand = new FCKDialogCommand( 'ImageButton', FCKLang.ImageButton	, 'dialog/fck_image.html?ImageButton', 450, 400 ) ; break ;

			case 'SpellCheck'	: oCommand = new FCKSpellCheckCommand() ; break ;
			case 'FitWindow'	: oCommand = new FCKFitWindow() ; break ;

			case 'Undo'	: oCommand = new FCKUndoCommand() ; break ;
			case 'Redo'	: oCommand = new FCKRedoCommand() ; break ;

			case 'SelectAll' : oCommand = new FCKSelectAllCommand() ; break ;

			// Generic Undefined command (usually used when a command is under development).
			case 'Undefined'	: oCommand = new FCKUndefinedCommand() ; break ;
			
			// By default we assume that it is a named command.
			default:
				if ( FCKRegexLib.NamedCommands.test( commandName ) )
					oCommand = new FCKNamedCommand( commandName ) ;
				else
				{
					alert( FCKLang.UnknownCommand.replace( /%1/g, commandName ) ) ;
					return null ;
				}
		}
		
		FCKCommands.LoadedCommands[ commandName ] = oCommand ;
		
		return oCommand ;
	}
	// Page Breaks
	FCKPageBreakCommand.prototype.Execute = function()
	{
	//	var e = FCK.EditorDocument.createElement( 'CENTER' ) ;
	//	e.style.pageBreakAfter = 'always' ;

		// Tidy was removing the empty CENTER tags, so the following solution has 
		// been found. It also validates correctly as XHTML 1.0 Strict.
		var e = FCK.EditorDocument.createElement('TRS_PAGE_SEPARATOR') ;
		var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__PageBreak', e ) ;
		oFakeImage	= FCK.InsertElement( oFakeImage ) ;
	}

	var FCKDocumentProcessor_CreateFake = function( fakeClass, realElement )
	{
		var oSpan = FCK.CreateElement( 'SPAN' ) ;
		oSpan.setAttribute( '_fckfakelement', 'true', 0 ) ;
		oSpan.setAttribute( '_fckrealelement', FCKTempBin.AddElement( realElement ), 0 ) ;
		return oSpan ;
	}

	FCKPageBreaksProcessor.ProcessDocument = function( document )
	{
		var aPageSeps = document.getElementsByTagName( 'TRS_PAGE_SEPARATOR' ) ;

		var ePageSep ;
		var i = aPageSeps.length - 1 ;
		while ( i >= 0 && ( ePageSep = aPageSeps[i--]))
		{
			var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__PageBreak', ePageSep.cloneNode(true) ) ;
			ePageSep.parentNode.insertBefore( oFakeImage, ePageSep ) ;
			ePageSep.parentNode.removeChild( ePageSep ) ;
		}
	}
	FCKToolbarSpecialCombo.prototype.RefreshState = function()
	{
		// Gets the actual state.
		var eState ;
		if ( FCK.EditMode == FCK_EDITMODE_SOURCE && ! this.SourceView )
			eState = FCK_TRISTATE_DISABLED ;
		else
		{
			var sValue = FCK.ToolbarSet.CurrentInstance.Commands.GetCommand( this.CommandName ).GetState() ;

	//		FCKDebug.Output( 'RefreshState of Special Combo "' + this.TypeOf + '" - State: ' + sValue ) ;

			if ( sValue != FCK_TRISTATE_DISABLED )
			{
				eState = FCK_TRISTATE_ON ;
				
				if ( this.RefreshActiveItems ){
					this.RefreshActiveItems( this._Combo, sValue ) ;
				}
				else
				{
					if ( this._LastValue != sValue )
					{
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
		
		if ( eState == FCK_TRISTATE_DISABLED )
		{
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
	FCKFlashProcessor.RefreshView = function( placholderImage, originalEmbed )
	{
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
	FCKFlashProcessor.ProcessDocument = function( document )
	{
		/*
		Sample code:
		This is some <embed src="/UserFiles/Flash/Yellow_Runners.swf"></embed><strong>sample text</strong>. You are&nbsp;<a name="fred"></a> using <a href="http://www.fckeditor.net/">FCKeditor</a>.
		*/

		var aEmbeds = document.getElementsByTagName( 'EMBED' ) ;

		var oEmbed ;
		var i = aEmbeds.length - 1 ;
		while ( i >= 0 && ( oEmbed = aEmbeds[i--] ) )
		{
			// IE doesn't return the type attribute with oEmbed.type or oEmbed.getAttribute("type") 
			// But it turns out that after accessing it then it doesn't gets copied later
			var oType = oEmbed.attributes[ 'type' ] ;

			// Check the extension and the type. Now it should be enough with just the type
			// Opera doesn't return oEmbed.src so oEmbed.src.EndsWith will fail
			if ( (oEmbed.src && oEmbed.src.EndsWith( '.swf', true )) || ( oType && oType.nodeValue == 'application/x-shockwave-flash' ) || (oEmbed.getAttribute( 'mediatype' )!=null))
			{
				var oCloned = oEmbed.cloneNode( true ) ;
				
				// On IE, some properties are not getting clonned properly, so we
				// must fix it. Thanks to Alfonso Martinez.
				if ( FCKBrowserInfo.IsIE )
				{
					var aAttributes = [ 'scale', 'play', 'loop', 'menu', 'wmode', 'quality', 'autostart', 'mediatype', 'uploadpic', 'ignore'] ;
					for ( var iAtt = 0 ; iAtt < aAttributes.length ; iAtt++ )
					{
						var oAtt = oEmbed.getAttribute( aAttributes[iAtt] ) ;
						if ( oAtt ) oCloned.setAttribute( aAttributes[iAtt], oAtt ) ;
					}
					// It magically gets lost after reading it in oType
					oCloned.setAttribute( 'type', oType.nodeValue ) ;
				}
			
				var oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oCloned ) ;
				oImg.setAttribute( '_fckflash', 'true', 0 ) ;
				
				FCKFlashProcessor.RefreshView( oImg, oEmbed ) ;

				oEmbed.parentNode.insertBefore( oImg, oEmbed ) ;
				oEmbed.parentNode.removeChild( oEmbed ) ;

	//			oEmbed.setAttribute( '_fcktemp', 'true', 0) ;
	//			oEmbed.style.display = 'none' ;
	//			oEmbed.hidden = true ;
			}
		}
	}
	var FCKVideoProcessor = FCKDocumentProcessor.AppendNew() ;
	FCKVideoProcessor.ProcessDocument = function( document )
	{
		var aEmbedDivs = document.getElementsByTagName( 'DIV' ) ;

		var oEmbedDiv ;
		var i = aEmbedDivs.length - 1 ;
		while ( i >= 0 && ( oEmbedDiv = aEmbedDivs[i--] ) )
		{
			if ( oEmbedDiv.getAttribute('__fckvideo',2)=='true' )
			{
				var oCloned = oEmbedDiv.cloneNode( true ) ;
			
				var oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oCloned ) ;
	
				//fjh@2007-11-15 去掉右键中的“多媒体属性”
				oImg.setAttribute( '_fckvideo', 'true', 0 ) ;
				
				//fjh@2007-11-15 重置，以显示缩略图和提示
				oImg.title = oEmbedDiv.title;
				oImg.src = oEmbedDiv.imgurl;
				
				FCKFlashProcessor.RefreshView( oImg, oEmbedDiv ) ;
				oEmbedDiv.parentNode.insertBefore( oImg, oEmbedDiv ) ;
				oEmbedDiv.parentNode.removeChild( oEmbedDiv ) ;
			}
		}
	}
	FCKTableHandler.SplitCell = function()
	{
		// Check that just one cell is selected, otherwise return.
		var aCells = FCKTableHandler.GetSelectedCells() ;
		if ( aCells.length != 1 )
			return ;
		
		var aMap = this._CreateTableMap( aCells[0].parentNode.parentNode ) ;
		var iCellIndex = FCKTableHandler._GetCellIndexSpan( aMap, aCells[0].parentNode.rowIndex , aCells[0] ) ;
			
		var aCollCells = this._GetCollumnCells( aMap, iCellIndex ) ;
		
		for ( var i = 0 ; i < aCollCells.length ; i++ )
		{
			if ( aCollCells[i] == aCells[0] )
			{
				var oNewCell = this.InsertCell( aCells[0] ) ;
				oNewCell.innerHTML = '&nbsp;';
				if ( !isNaN( aCells[0].rowSpan ) && aCells[0].rowSpan > 1 )
					oNewCell.rowSpan = aCells[0].rowSpan ;
			}
			else
			{
				if ( isNaN( aCollCells[i].colSpan ) )
					aCollCells[i].colSpan = 2 ;
				else
					aCollCells[i].colSpan += 1 ;
			}
		}
	}
	FCKTableHandler.ClearRow = function( tr )
	{
		// Get the array of row's cells.
		var aCells = tr.cells ;

		// Replace the contents of each cell with "nothing".
		for ( var i = 0 ; i < aCells.length ; i++ ) 
		{
			if ( FCKBrowserInfo.IsGecko )
				aCells[i].innerHTML = GECKO_BOGUS ;
			else
				aCells[i].innerHTML = '&nbsp;' ;
		}
	}
	FCKTableHandler.InsertColumn = function()
	{
		// Get the cell where the selection is placed in.
		var oCell = FCKSelection.MoveToAncestorNode('TD') || FCKSelection.MoveToAncestorNode('TH') ;


		if ( !oCell ) return ;
		
		// Get the cell's table.
		var oTable = FCKTools.GetElementAscensor( oCell, 'TABLE' ) ;

		// Get the index of the column to be created (based on the cell).
		var iIndex = oCell.cellIndex + 1 ;

		// Loop throw all rows available in the table.
		for ( var i = 0 ; i < oTable.rows.length ; i++ )
		{
			// Get the row.
			var oRow = oTable.rows[i] ;
		
			// If the row doens't have enought cells, ignore it.
			if ( oRow.cells.length < iIndex )
				continue ;
			
			oCell = oRow.cells[iIndex-1].cloneNode(false) ;
			
			if ( FCKBrowserInfo.IsGecko )
				oCell.innerHTML = GECKO_BOGUS ;
			else
				oCell.innerHTML = "&nbsp;" ;
			
			// Get the cell that is placed in the new cell place.
			var oBaseCell = oRow.cells[iIndex] ;

			// If the cell is available (we are not in the last cell of the row).
			if ( oBaseCell )
				oRow.insertBefore( oCell, oBaseCell ) ;	// Insert the new cell just before of it.
			else
				oRow.appendChild( oCell ) ;				// Append the cell at the end of the row.
		}
	}
	FCKTableHandler.InsertCell = function( cell )
	{
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
		if ( oCell.cellIndex == oCell.parentNode.cells.length - 1 )
		{
			// Add the new cell at the end of the row.
			oCell.parentNode.appendChild( oNewCell ) ;
		}
		else
		{
			// Add the new cell before the next cell (after the active one).
			oCell.parentNode.insertBefore( oNewCell, oCell.nextSibling ) ;
		}
		
		return oNewCell ;
	}
	FCKStyleCommand.prototype.GetState = function()
	{
		if ( !FCK.EditorDocument )
			return FCK_TRISTATE_DISABLED ;

		var oSelection = FCK.EditorDocument.selection ;
		
		if ( FCKSelection.GetType() == 'Control' )
		{
			var e = FCKSelection.GetSelectedElement() ;
			if ( e ){
				var sTagName = e.tagName;
				if(sTagName=='IMG'&&e.getAttribute( '_fckfakelement' ))return FCK_TRISTATE_DISABLED;
				return this.StylesLoader.StyleGroups[ sTagName ] ? FCK_TRISTATE_OFF : FCK_TRISTATE_DISABLED ;
			}
		}

		return FCK_TRISTATE_OFF ;
	}
	FCKStyleCommand.prototype.Execute = function( styleName, styleComboItem )
	{
		FCKUndo.SaveUndoStep() ;
		try{
			if ( styleComboItem.Selected )
				styleComboItem.Style.RemoveFromSelection() ;
			else
				styleComboItem.Style.ApplyToSelection() ;
		}catch(err){
			alert('暂不支持多个单元格同时设置或者取消样式.');
		}
		FCKUndo.SaveUndoStep() ;

		FCK.Focus() ;
		
		FCK.Events.FireEvent( "OnSelectionChange" ) ;
	}
	FCKToolbarItems.GetItem = function( itemName )
	{
		var oItem = FCKToolbarItems.LoadedItems[ itemName ] ;

		if ( oItem )
			return oItem ;

		switch ( itemName )
		{
			case 'Source'			: oItem = new FCKToolbarButton( 'Source'	, FCKLang.Source, null, FCK_TOOLBARITEM_ICONTEXT, true, true, 1 ) ; break ;
			case 'DocProps'			: oItem = new FCKToolbarButton( 'DocProps'	, FCKLang.DocProps, null, null, null, null, 2 ) ; break ;
			case 'Save'				: oItem = new FCKToolbarButton( 'Save'		, FCKLang.Save, null, null, true, null, 3 ) ; break ;
			case 'NewPage'			: oItem = new FCKToolbarButton( 'NewPage'	, FCKLang.NewPage, null, null, true, null, 4  ) ; break ;
			case 'Preview'			: oItem = new FCKToolbarButton( 'Preview'	, FCKLang.Preview, null, null, true, null, 5  ) ; break ;
			case 'Templates'		: oItem = new FCKToolbarButton( 'Templates'	, FCKLang.Templates, null, null, null, null, 6 ) ; break ;
			case 'About'			: oItem = new FCKToolbarButton( 'About'		, FCKLang.About, null, null, true, null, 47  ) ; break ;

			case 'Cut'				: oItem = new FCKToolbarButton( 'Cut'		, FCKLang.Cut, null, null, false, true, 7 ) ; break ;
			case 'Copy'				: oItem = new FCKToolbarButton( 'Copy'		, FCKLang.Copy, null, null, false, true, 8 ) ; break ;
			case 'Paste'			: oItem = new FCKToolbarButton( 'Paste'		, FCKLang.Paste, null, null, false, true, 9 ) ; break ;
			case 'PasteText'		: oItem = new FCKToolbarButton( 'PasteText'	, FCKLang.PasteText, null, null, false, true, 10 ) ; break ;
			case 'PasteWord'		: oItem = new FCKToolbarButton( 'PasteWord'	, FCKLang.PasteWord, null, null, false, true, 11 ) ; break ;
			case 'Print'			: oItem = new FCKToolbarButton( 'Print'		, FCKLang.Print, null, null, false, true, 12 ) ; break ;
			case 'SpellCheck'		: oItem = new FCKToolbarButton( 'SpellCheck', FCKLang.SpellCheck, null, null, null, null, 13 ) ; break ;
			case 'Undo'				: oItem = new FCKToolbarButton( 'Undo'		, FCKLang.Undo, null, null, false, true, 14 ) ; break ;
			case 'Redo'				: oItem = new FCKToolbarButton( 'Redo'		, FCKLang.Redo, null, null, false, true, 15 ) ; break ;
			case 'SelectAll'		: oItem = new FCKToolbarButton( 'SelectAll'	, FCKLang.SelectAll, null, null, null, null, 18 ) ; break ;
			case 'RemoveFormat'		: oItem = new FCKToolbarButton( 'RemoveFormat', FCKLang.RemoveFormat, null, null, false, true, 19 ) ; break ;
			case 'FitWindow'		: oItem = new FCKToolbarButton( 'FitWindow'	, FCKLang.FitWindow, null, null, true, true, 66 ) ; break ;

			case 'Bold'				: oItem = new FCKToolbarButton( 'Bold'		, FCKLang.Bold, null, null, false, true, 20 ) ; break ;
			case 'Italic'			: oItem = new FCKToolbarButton( 'Italic'	, FCKLang.Italic, null, null, false, true, 21 ) ; break ;
			case 'Underline'		: oItem = new FCKToolbarButton( 'Underline'	, FCKLang.Underline, null, null, false, true, 22 ) ; break ;
			case 'StrikeThrough'	: oItem = new FCKToolbarButton( 'StrikeThrough'	, FCKLang.StrikeThrough, null, null, false, true, 23 ) ; break ;
			case 'Subscript'		: oItem = new FCKToolbarButton( 'Subscript'		, FCKLang.Subscript, null, null, false, true, 24 ) ; break ;
			case 'Superscript'		: oItem = new FCKToolbarButton( 'Superscript'	, FCKLang.Superscript, null, null, false, true, 25 ) ; break ;

			case 'OrderedList'		: oItem = new FCKToolbarButton( 'InsertOrderedList'		, FCKLang.NumberedListLbl, FCKLang.NumberedList, null, false, true, 26 ) ; break ;
			case 'UnorderedList'	: oItem = new FCKToolbarButton( 'InsertUnorderedList'	, FCKLang.BulletedListLbl, FCKLang.BulletedList, null, false, true, 27 ) ; break ;
			case 'Outdent'			: oItem = new FCKToolbarButton( 'Outdent'	, FCKLang.DecreaseIndent, null, null, false, true, 28 ) ; break ;
			case 'Indent'			: oItem = new FCKToolbarButton( 'Indent'	, FCKLang.IncreaseIndent, null, null, false, true, 29 ) ; break ;

			case 'Link'				: oItem = new FCKToolbarButton( 'Link'		, FCKLang.InsertLinkLbl, FCKLang.InsertLink, null, false, true, 34 ) ; break ;
			case 'Unlink'			: oItem = new FCKToolbarButton( 'Unlink'	, FCKLang.RemoveLink, null, null, false, true, 35 ) ; break ;
			case 'Anchor'			: oItem = new FCKToolbarButton( 'Anchor'	, FCKLang.Anchor, null, null, null, null, 36 ) ; break ;

			case 'Image'			: oItem = new FCKToolbarButton( 'Image'			, FCKLang.InsertImageLbl, FCKLang.InsertImage, null, false, true, 37 ) ; break ;
			case 'Flash'			: 
				oItem = new FCKToolbarButton( 'Flash'			, FCKLang.InsertFlashLbl, FCKLang.InsertFlash, null, false, true, null ) ; 
				break ;
			case 'Table'			: oItem = new FCKToolbarButton( 'Table'			, FCKLang.InsertTableLbl, FCKLang.InsertTable, null, false, true, 39 ) ; break ;
			case 'SpecialChar'		: oItem = new FCKToolbarButton( 'SpecialChar'	, FCKLang.InsertSpecialCharLbl, FCKLang.InsertSpecialChar, null, false, true, 42 ) ; break ;
			case 'Smiley'			: oItem = new FCKToolbarButton( 'Smiley'		, FCKLang.InsertSmileyLbl, FCKLang.InsertSmiley, null, false, true, 41 ) ; break ;
			case 'PageBreak'		: oItem = new FCKToolbarButton( 'PageBreak'		, FCKLang.PageBreakLbl, FCKLang.PageBreak, null, false, true, 43 ) ; break ;
			case 'UniversalKey'		: oItem = new FCKToolbarButton( 'UniversalKey'	, FCKLang.UniversalKeyboard , null, null, false, true, 44) ; break ;

			case 'Rule'				: oItem = new FCKToolbarButton( 'InsertHorizontalRule', FCKLang.InsertLineLbl, FCKLang.InsertLine, null, false, true, 40 ) ; break ;

			case 'JustifyLeft'		: oItem = new FCKToolbarButton( 'JustifyLeft'	, FCKLang.LeftJustify, null, null, false, true, 30 ) ; break ;
			case 'JustifyCenter'	: oItem = new FCKToolbarButton( 'JustifyCenter'	, FCKLang.CenterJustify, null, null, false, true, 31 ) ; break ;
			case 'JustifyRight'		: oItem = new FCKToolbarButton( 'JustifyRight'	, FCKLang.RightJustify, null, null, false, true, 32 ) ; break ;
			case 'JustifyFull'		: oItem = new FCKToolbarButton( 'JustifyFull'	, FCKLang.BlockJustify, null, null, false, true, 33 ) ; break ;

			case 'Style'			: oItem = new FCKToolbarStyleCombo() ; oItem.PanelWidth = 200 ; break ;
			case 'FontName'			: oItem = new FCKToolbarFontsCombo() ; break ;
			case 'FontSize'			: oItem = new FCKToolbarFontSizeCombo() ; break ;
			case 'FontFormat'		: oItem = new FCKToolbarFontFormatCombo() ; break ;

			case 'TextColor'		: oItem = new FCKToolbarPanelButton( 'TextColor', FCKLang.TextColor, null, null, 45 ) ; break ;
			case 'BGColor'			: oItem = new FCKToolbarPanelButton( 'BGColor'	, FCKLang.BGColor, null, null, 46 ) ; break ;

			case 'Find'				: oItem = new FCKToolbarButton( 'Find'		, FCKLang.Find, null, null, null, null, 16 ) ; break ;
			case 'Replace'			: oItem = new FCKToolbarButton( 'Replace'	, FCKLang.Replace, null, null, null, null, 17 ) ; break ;

			case 'Form'				: oItem = new FCKToolbarButton( 'Form'			, FCKLang.Form, null, null, null, null, 48 ) ; break ;
			case 'Checkbox'			: oItem = new FCKToolbarButton( 'Checkbox'		, FCKLang.Checkbox, null, null, null, null, 49 ) ; break ;
			case 'Radio'			: oItem = new FCKToolbarButton( 'Radio'			, FCKLang.RadioButton, null, null, null, null, 50 ) ; break ;
			case 'TextField'		: oItem = new FCKToolbarButton( 'TextField'		, FCKLang.TextField, null, null, null, null, 51 ) ; break ;
			case 'Textarea'			: oItem = new FCKToolbarButton( 'Textarea'		, FCKLang.Textarea, null, null, null, null, 52 ) ; break ;
			case 'HiddenField'		: oItem = new FCKToolbarButton( 'HiddenField'	, FCKLang.HiddenField, null, null, null, null, 56 ) ; break ;
			case 'Button'			: oItem = new FCKToolbarButton( 'Button'		, FCKLang.Button, null, null, null, null, 54 ) ; break ;
			case 'Select'			: oItem = new FCKToolbarButton( 'Select'		, FCKLang.SelectionField, null, null, null, null, 53 ) ; break ;
			case 'ImageButton'		: oItem = new FCKToolbarButton( 'ImageButton'	, FCKLang.ImageButton, null, null, null, null, 55 ) ; break ;
			case null:
			case window.undefined:
				return null;
			default:
				alert( FCKLang.UnknownToolbarItem.replace( /%1/g, itemName ) ) ;
				return null ;
		}

		FCKToolbarItems.LoadedItems[ itemName ] = oItem ;

		return oItem ;
	}

	var FCKTableProcessor = FCKDocumentProcessor.AppendNew() ;
	FCKTableProcessor.ProcessDocument = function( document )
	{
		var aEmbedDivs = document.getElementsByTagName( 'TABLE' ) ;

		var oEmbedDiv ;
		var i = aEmbedDivs.length - 1 ;
		while ( i >= 0 && ( oEmbedDiv = aEmbedDivs[i--] ) )
		{
			if ( parseInt(oEmbedDiv.style.borderWidth) == 0 || oEmbedDiv.style.borderStyle=='none')
			{
				oEmbedDiv.style.borderRight = oEmbedDiv.style.borderLeft =
					oEmbedDiv.style.borderTop = oEmbedDiv.style.borderBottom = oEmbedDiv.style.border = oEmbedDiv.border+'px solid '+(oEmbedDiv.borderColor||'black');
			}
		}
	}
	/*
	var FCKPProcessor = FCKDocumentProcessor.AppendNew() ;
	FCKPProcessor.ProcessDocument = function( document )
	{
		var aEmbedDivs = document.getElementsByTagName( 'P' ) ;

		var oEmbedDiv ;
		var i = aEmbedDivs.length - 1 ;
		while ( i >= 0 && ( oEmbedDiv = aEmbedDivs[i--] ) )
		{
			if ( oEmbedDiv.style.margin == 'none' || oEmbedDiv.style.margin == ''){
				oEmbedDiv.style.margin = '0';
			}
		}
	}
	function Doc_OnKeyUp(){
		var e = FCK.EditorWindow.event ;
		switch ( e.keyCode )
		{
			case 13 :	// ENTER
				if ( !FCKConfig.UseBROnCarriageReturn && !(e.ctrlKey || e.altKey || e.shiftKey) ){
					FCKPProcessor.ProcessDocument(FCK.EditorDocument);
				}
				break ;
		}
		
		return true ;
	}
	*/
	// Open the Placeholder dialog on double click.
	FCK.TrsExtension = {};
	FCK.TrsExtension.OnDoubleClick = function( oElement )
	{
		var oSelectedElement = FCK.Selection.GetSelectedElement() ;
		if(oElement.tagName!='TD'&&oSelectedElement!=oElement)return;
		var sTagName = oElement.tagName;
		if ( sTagName == 'IMG'){
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
			else{
				return;
			}
			FCKCommands.GetCommand(sCommand).Execute() ;
		}
		else if( sTagName == 'A' ){
			if(Element.hasClassName(oElement,'FCK__AnchorC')||!oElement.getAttribute( 'href',2 )){
				FCKCommands.GetCommand('Anchor').Execute() ;
			}
			else
				FCKCommands.GetCommand('Link').Execute() ;
		}
		else if( sTagName == 'TABLE' ){
			FCKCommands.GetCommand('Table').Execute() ;
		}
//		else if( sTagName == 'TD' ){
//			FCKCommands.GetCommand('TableCellProp').Execute() ;
//		}
	}

	FCK.RegisterDoubleClickHandler( FCK.TrsExtension.OnDoubleClick, 'IMG' ) ;
	FCK.RegisterDoubleClickHandler( FCK.TrsExtension.OnDoubleClick, 'A' ) ;
	FCK.RegisterDoubleClickHandler( FCK.TrsExtension.OnDoubleClick, 'TABLE' ) ;
//	FCK.RegisterDoubleClickHandler( FCK.TrsExtension.OnDoubleClick, 'TD' ) ;
	function FCKToolbarSet_Create( overhideLocation )
	{
		var oToolbarSet ;
			
		var isSSL = location.href.indexOf("https://")!=-1;
		var sLocation = overhideLocation || FCKConfig.ToolbarLocation ;
		switch ( sLocation )
		{
			case 'In' :
					document.getElementById( 'xToolbarRow' ).style.display = '' ;
					oToolbarSet = new FCKToolbarSet( document ) ;
				break ;
				
	//		case 'OutTop' :
				// Not supported.
				
			default :
				FCK.Events.AttachEvent( 'OnBlur', FCK_OnBlur ) ;
				FCK.Events.AttachEvent( 'OnFocus', FCK_OnFocus ) ;

				var eToolbarTarget ;
				
				// Out:[TargetWindow]([TargetId])
				var oOutMatch = sLocation.match( /^Out:(.+)\((\w+)\)$/ ) ;
				if ( oOutMatch )
				{
					eToolbarTarget = eval( 'parent.' + oOutMatch[1] ).document.getElementById( oOutMatch[2] ) ;
				}
				else
				{
					// Out:[TargetId]
					oOutMatch = sLocation.match( /^Out:(\w+)$/ ) ;
					if ( oOutMatch )
						eToolbarTarget = parent.document.getElementById( oOutMatch[1] ) ;
				}
				
				if ( !eToolbarTarget )
				{
					alert( 'Invalid value for "ToolbarLocation"' ) ;
					return this._Init( 'In' ) ;
				}
				
				// If it is a shared toolbar, it may be already available in the target element.
				oToolbarSet = eToolbarTarget.__FCKToolbarSet ;
				if ( oToolbarSet )
					break ;

				// Create the IFRAME that will hold the toolbar inside the target element.
				var eToolbarIFrame = FCKTools.GetElementDocument( eToolbarTarget ).createElement( 'iframe' ) ;
				if(isSSL){
					eToolbarIFrame.src = 'javascript:void(0)';
				}
				eToolbarIFrame.frameBorder = 0 ;
				eToolbarIFrame.width = '100%' ;
				eToolbarIFrame.height = '10' ;
				eToolbarIFrame.allowTransparency = true ;
				eToolbarTarget.appendChild( eToolbarIFrame ) ;
				eToolbarIFrame.unselectable = 'on' ;
				
				// Write the basic HTML for the toolbar (copy from the editor main page).
				var eTargetDocument = eToolbarIFrame.contentWindow.document ;
				eTargetDocument.open() ;
				eTargetDocument.write( '<html><head><script type="text/javascript"> window.onload = window.onresize = function() { window.frameElement.height = document.body.scrollHeight ; } </script></head><body style="overflow: hidden" class="xToolbarBody">' + document.getElementById( 'xToolbarSpace' ).innerHTML + '</body></html>' ) ;
				eTargetDocument.close() ;
				
				eTargetDocument.oncontextmenu = FCKTools.CancelEvent ;
				eToolbarIFrame.contentWindow.onunload = function(){
					eTargetDocument.oncontextmenu = null ;
					eToolbarIFrame.contentWindow.onload = eToolbarIFrame.contentWindow.onresize
						= eToolbarIFrame.contentWindow.onunload = null ;
				}
				// Load external resources (must be done here, otherwise Firefox will not
				// have the document DOM ready to be used right away.
				FCKTools.AppendStyleSheet( eTargetDocument, FCKConfig.SkinPath + 'fck_editor.css' ) ;
				
				oToolbarSet = eToolbarTarget.__FCKToolbarSet = new FCKToolbarSet( eTargetDocument ) ;
				oToolbarSet._IFrame = eToolbarIFrame ;

				if ( FCK.IECleanup )
					FCK.IECleanup.AddItem( eToolbarTarget, FCKToolbarSet_Target_Cleanup ) ;
		}
		
		oToolbarSet.CurrentInstance = FCK ;

		FCK.AttachToOnSelectionChange( oToolbarSet.RefreshItemsState ) ;

		return oToolbarSet ;
	}

	window.onerror = function(message, url, line){
		//if($logger.level>=-1&&$logger.level<5){
			var s = "错误信息：" + message + "\n文件："+ url +"\n出错位置：第" + line + "行\n";
			if(_IE){
				s = message+"["+line+"@"+url+"]";
				var a = arguments.caller;			
				var stack = [];
				stack.push(a.callee);
				while(true){
					a = a.caller;
					if (!a||stack.include(a.callee)) {
						break;
					}
					stack.push(a.callee);
				}
				stack = stack.join('\n------------------------\n');
				if((top.actualTop||top).getParameter('isdebug')==1){
					alert( s + stack );
				}
				else{
					//TODO logger
				}
			}
			else{
				try{
					throwerror();
				}catch(err){
					if((top.actualTop||top).getParameter('isdebug')==1){
						alert(s+err.stack);
					}
					else{
						//TODO logger
					}
				}
			}
		//}
		return true;
	}


FCKToolbarButton.prototype.RefreshState = function()
{
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

FCKTextColorCommand.prototype.GetState = function()
{
	if ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ){
		return FCK_TRISTATE_OFF ;
	}
	else{
		return FCK_TRISTATE_DISABLED ;
	}
}

// Link Anchors
if ( FCKBrowserInfo.IsIE || FCKBrowserInfo.IsOpera )
{
	FCKAnchorsProcessor.ProcessDocument = function( document )
	{
		var aLinks = document.getElementsByTagName( 'A' ) ;

		var oLink ;
		var i = aLinks.length - 1 ;
		while ( i >= 0 && ( oLink = aLinks[i--] ) )
		{
			if(oLink.name=='AnchorAddByWCM'){
				if(!Element.hasClassName(oLink,'FCK__ContentLink')){
					Element.addClassName(oLink,'FCK__ContentLink');
				}
			}
			// If it is anchor. Doesn't matter if it's also a link (even better: we show that it's both a link and an anchor)
			else if ( oLink.name.length > 0 )
			{
				//if the anchor has some content then we just add a temporary class
				if ( oLink.innerHTML !== '' )
				{
					if ( FCKBrowserInfo.IsIE ){
						if(!Element.hasClassName(oLink,'FCK__AnchorC')){
							Element.addClassName(oLink,'FCK__AnchorC');
						}
					}
				}
				else
				{
					var oImg = FCKDocumentProcessor_CreateFakeImage( 'FCK__Anchor', oLink.cloneNode(true) ) ;
					oImg.setAttribute( '_fckanchor', 'true', 0 ) ;

					oLink.parentNode.insertBefore( oImg, oLink ) ;
					oLink.parentNode.removeChild( oLink ) ;
				}
			}

		}
	}
}
FCK.SetHTML = function( html, resetIsDirty )
{
	this.EditingArea.Mode = FCK.EditMode ;

	if ( FCK.EditMode == FCK_EDITMODE_WYSIWYG )
	{
		html = FCKConfig.ProtectedSource.Protect( html ) ;

		// Fix for invalid self-closing tags (see #152).
		html = html.replace( FCKRegexLib.InvalidSelfCloseTags, '$1></$2>' ) ;

		html = FCK.ProtectEvents( html ) ;
		html = FCK.ProtectUrls( html ) ;
		html = FCK.ProtectTags( html ) ;

		// Firefox can't handle correctly the editing of the STRONG and EM tags.
		// We must replace them with B and I.
		if ( FCKBrowserInfo.IsGecko )
		{
			html = html.replace( FCKRegexLib.StrongOpener, '<b$1' ) ;
			html = html.replace( FCKRegexLib.StrongCloser, '<\/b>' ) ;
			html = html.replace( FCKRegexLib.EmOpener, '<i$1' ) ;
			html = html.replace( FCKRegexLib.EmCloser, '<\/i>' ) ;
		}

		this._ForceResetIsDirty = ( resetIsDirty === true ) ;

		var sHtml = '' ;

		if ( FCKConfig.FullPage )
		{
			// The HTML must be fixed if the <head> is not available.
			if ( !FCKRegexLib.HeadOpener.test( html ) )
			{
				// Check if the <html> is available.
				if ( !FCKRegexLib.HtmlOpener.test( html ) )
					html = '<html dir="' + FCKConfig.ContentLangDirection + '">' + html + '</html>' ;

				// Add the <head>.
				html = html.replace( FCKRegexLib.HtmlOpener, '$&<head></head>' ) ;
			}

			// Save the DOCTYPE.
			FCK.DocTypeDeclaration = html.match( FCKRegexLib.DocTypeTag ) ;

			if ( FCKBrowserInfo.IsIE )
				sHtml = FCK._GetBehaviorsStyle() ;
			else if ( FCKConfig.ShowBorders )
				sHtml = '<link href="' + FCKConfig.FullBasePath + 'css/fck_showtableborders_gecko.css" rel="stylesheet" type="text/css" _fcktemp="true" />' ;

			sHtml += '<link href="' + FCKConfig.FullBasePath + 'css/fck_internal.css' + '" rel="stylesheet" type="text/css" _fcktemp="true" />' ;

			// Attention: do not change it before testing it well (sample07)!
			// This is tricky... if the head ends with <meta ... content type>,
			// Firefox will break. But, it works if we include the temporary
			// links as the last elements in the HEAD.
			sHtml = html.replace( FCKRegexLib.HeadCloser, sHtml + '$&' ) ;

			// Insert the base tag (FCKConfig.BaseHref), if not exists in the source.
			// The base must be the first tag in the HEAD, to get relative
			// links on styles, for example.
			if ( FCK.TempBaseTag.length > 0 && !FCKRegexLib.HasBaseTag.test( html ) )
				sHtml = sHtml.replace( FCKRegexLib.HeadOpener, '$&' + FCK.TempBaseTag ) ;
		}
		else
		{
			sHtml =
				FCKConfig.DocType +
				'<html dir="' + FCKConfig.ContentLangDirection + '"' ;

			// On IE, if you are use a DOCTYPE differenft of HTML 4 (like
			// XHTML), you must force the vertical scroll to show, otherwise
			// the horizontal one may appear when the page needs vertical scrolling.
			if ( FCKBrowserInfo.IsIE && !FCKRegexLib.Html4DocType.test( FCKConfig.DocType ) )
				sHtml += ' style="overflow-y: scroll"' ;

			sHtml +=
				'><head><title></title>' +
				_FCK_GetEditorAreaStyleTags() +
				'<link href="' + FCKConfig.FullBasePath + 'css/fck_internal.css' + '" rel="stylesheet" type="text/css" _fcktemp="true" />' ;

			if ( FCKBrowserInfo.IsIE )
				sHtml += FCK._GetBehaviorsStyle() ;
			else if ( FCKConfig.ShowBorders )
				sHtml += '<link href="' + FCKConfig.FullBasePath + 'css/fck_showtableborders_gecko.css" rel="stylesheet" type="text/css" _fcktemp="true" />' ;

			sHtml += FCK.TempBaseTag ;

			// Add ID and Class to the body
			var sBodyTag = '<body' ;
			if ( FCKConfig.BodyId && FCKConfig.BodyId.length > 0 )
				sBodyTag += ' id="' + FCKConfig.BodyId + '"' ;
			if ( FCKConfig.BodyClass && FCKConfig.BodyClass.length > 0 )
				sBodyTag += ' class="' + FCKConfig.BodyClass + '"' ;
			sHtml += '</head>' + sBodyTag + '>' ;

			if ( FCKBrowserInfo.IsGecko && ( html.length == 0 || FCKRegexLib.EmptyParagraph.test( html ) ) )
				sHtml += GECKO_BOGUS ;
			else
				sHtml += html ;

			sHtml += '</body></html>' ;
		}

		this.EditingArea.OnLoad = _FCK_EditingArea_OnLoad ;
		this.EditingArea.Start( sHtml ) ;
	}
	else
	{
		FCKEditingArea_Unload.call(this.EditorWindow);
		// Remove the references to the following elements, as the editing area
		// IFRAME will be removed.
		FCK.EditorWindow	= null ;
		FCK.EditorDocument	= null ;
		if(FCK.EditingArea){
			FCK.EditingArea.Window = null;
			FCK.EditingArea.Document = null;
		}

		this.EditingArea.OnLoad = null ;
		this.EditingArea.Start( html ) ;

		// Enables the context menu in the textarea.
		this.EditingArea.Textarea._FCKShowContextMenu = true ;

		// Removes the enter key handler.
		FCK.EnterKeyHandler = null ;

		if ( resetIsDirty )
			this.ResetIsDirty() ;

		// Listen for keystroke events.
		FCK.KeystrokeHandler.AttachToElement( this.EditingArea.Textarea ) ;

		this.EditingArea.Textarea.focus() ;

		FCK.Events.FireEvent( 'OnAfterSetHTML' ) ;
	}

	if ( FCKBrowserInfo.IsGecko )
		window.onresize() ;
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
	myCookie += "; path=/; expires=" + expires.toGMTString() + ";domain=" + document.domain;
	document.cookie = myCookie;
}
FCK.setCookie = function(_sCookieName,_sCookieValue){
	var myCookie = '';
	var sSaveValue = null;
	sSaveValue = escape(_sCookieValue);
	myCookie += _sCookieName+"="+sSaveValue+"";
	var expires = new Date();
	expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000 * 30));
	myCookie += "; path=/; expires=" + expires.toGMTString()+";domain="+document.domain;
	document.cookie = myCookie;
}
FCK.isBlankContent = function(_sHtml){
	return _sHtml==null||_sHtml==''||(/^\s*(<(div|p)>)?(\s|\n|\r|&nbsp;|&nbsp)*(<\/\2>)?\s*$/i).test(_sHtml);
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

function _FCK_KeystrokeHandler_OnKeystroke( keystroke, keystrokeValue )
{
	if ( FCK.Status != FCK_STATUS_COMPLETE )
		return false ;

	if ( FCK.EditMode == FCK_EDITMODE_WYSIWYG )
	{
		if ( keystrokeValue == 'Paste' )
			return !FCK.Events.FireEvent( 'OnPaste' ) ;
		else if ( keystrokeValue == 'TabInsert' ){
			FCK.InsertHtml( window.FCKTabHTML ) ;
			return true;
		}
		else if( keystrokeValue == 'Delete' ){
			FCKTableHandler.DeleteCellContents();
			return false;
//			return true;
		}
	}
	else
	{
		// In source mode, some actions must have their default behavior.
		if ( keystrokeValue.Equals( 'Paste', 'Undo', 'Redo', 'SelectAll', 'TabInsert', 'Delete' ) )
			return false ;
	}

	// The return value indicates if the default behavior of the keystroke must
	// be cancelled. Let's do that only if the Execute() call explicitelly returns "false".
	var oCommand = FCK.Commands.GetCommand( keystrokeValue ) ;
	return ( oCommand.Execute.apply( oCommand, FCKTools.ArgumentsToArray( arguments, 2 ) ) !== false ) ;
}
FCKTableHandler.getTdObj = function(innerHtml, sel)
{
	// find text outside of tags
	var h = 0;
	var k = 0;
	
	do
	{
		k = innerHtml.indexOf('<',h);
		if (k < 0)
		{
			k = innerHtml.length;	// the entire html
		}
			
		if (h < k)	// found some text, get the parent td
		{
			text = FCKTools.HTMLDecode(innerHtml.substring(h, k));
			text = text.replace(/[\r\n]/, '');	// sometimes for some strange reason the text will have '\r\n' in it, then the sc.findText(text) will fail
			sc = sel.duplicate();
			if(text != '' && sc.findText(text))
			{
				return FCKTools.GetElementAscensor(sc.parentElement(), "TD");	
			}
		}
		
		h = innerHtml.indexOf('>', k);
		if (h < 0)
		{
			// we started with '<' but can't find '>', stop
			return null;	// error
		}
		
		h++;
	}  
	while (k < innerHtml.length);
	
	return null;
} 

FCKTableHandler.DeleteCellContents = function() 
{
//	var oSel = Core.State.GetSelection();
	var oSel = FCK.EditorDocument.selection ;
//	var aCells = FCKTableHandler.GetSelectedCells() ;

	if(oSel.type != "Text")
		return false;
	oSel = oSel.createRange();
	var html = oSel.htmlText;

	// if html has table tag or has no td tag, ignore
	var htmlLowerCase = html.toLowerCase();		// used for finding tags
	if (htmlLowerCase.indexOf('<table') >= 0 || htmlLowerCase.indexOf('</table') >= 0 ||
		htmlLowerCase.indexOf('<td') < 0)
	{
		return false;
	}

	var td1 = null;
	var innerHtml1 = null;
	var text = '';
	var tdTagBegin = 0;
	var tdHtmlBegin = 0;
	var tdHtmlEnd = 0;
	var td1IsLastCell = false;
	var tdTagNext = 0;

	tdTagBegin = htmlLowerCase.indexOf('<td');
		
	while (td1 == null && tdTagBegin >= 0)	// TODO 
	{
		tdHtmlBegin = htmlLowerCase.indexOf('>', tdTagBegin) + 1;    
		tdHtmlEnd = htmlLowerCase.indexOf('</td', tdHtmlBegin);
		
		innerHtml1 = html.substring(tdHtmlBegin, tdHtmlEnd);
		td1 = this.getTdObj(innerHtml1, oSel);
		
		tdTagBegin = htmlLowerCase.indexOf('<td', tdHtmlEnd);	
	}

	if (td1 == null)
	{
		return false;	//error
	}

	if( tdTagBegin >= 0)
	{
		tdTagNext = htmlLowerCase.indexOf('<td', tdTagBegin + 1);
	}
	else
	{
		tdTagNext = -1;
		td1IsLastCell = true;
	}

	var tr1 = FCKTools.GetElementAscensor(td1,"TR");
	var table = FCKTools.GetElementAscensor(tr1,"TABLE");
	var rows = table.rows;

	var r = 0;	
	var c = 0;
	// for each td tag found
	for (r = tr1.rowIndex; r < rows.length; r++)
	{
		var start = (r == tr1.rowIndex) ? td1.cellIndex + 1 : 0;
		
		for (c = start; c < rows[r].cells.length && tdTagNext >= 0; c++)
		{
			rows[r].cells[c].innerHTML = '';
			
			tdTagBegin = tdTagNext;
			tdTagNext = htmlLowerCase.indexOf('<td', tdTagBegin + 1);
		}	
		
		if (tdTagNext < 0)
		{
			break;		// do this here since we don't want to increment r before exiting
		}
	}

	// delete the selected content in td1

	if ( td1IsLastCell )                           //this case is bug 6392
	{
		td1.innerHTML = td1.innerHTML.substring(innerHtml1.length, td1.innerHTML.length);
	}
	else
	{
		td1.innerHTML = td1.innerHTML.substring(0, td1.innerHTML.lastIndexOf(innerHtml1));
	}
	// take care of last cell
	if (c == rows[r].cells.length)
	{
		r++;
		c = 0;
		if( r >= rows.length )  // r was last row
		{
			return true;
		}
	}
	
	tdHtmlBegin = htmlLowerCase.indexOf('>', tdTagBegin) + 1;    
	tdHtmlEnd = htmlLowerCase.indexOf('</td', tdHtmlBegin);
	// (tdHtmlEnd - tdHtmlBegin) is the length of the string to be deleted
	rows[r].cells[c].innerHTML = rows[r].cells[c].innerHTML.substring(tdHtmlEnd - tdHtmlBegin);	

	return true;
}

/**
 * This returns the selection *object* for the current editor.  This object can
 * be used to get a selection range, and may have methods that allow
 * manipulation of the selection, depending on the platform.  Don't cache this,
 * it may change in response to user action.
 */
function GetWYSIWYGSelection() {
  // Opera supports both methods, so order the preferred one first.
  // Safari, Gecko, Opera
  if (typeof FCK.getSelection != "undefined") {
    return FCK.getSelection();
  }

  // IE, Opera
  if (FCK.EditorDocument && typeof FCK.EditorDocument.selection != "undefined") {
    return FCK.EditorDocument.selection;
  }

  return null;
}
function IsDefined(obj, field) {
  return typeof obj[field] != "undefined";
}
/**
 * This returns the actual selection range object. It's very different depending
 * on what platform you're on.  In safari, it will actually be a JS object cache
 * of the selection range, and may be fixed up due to bugs.
 */
function GetWYSIWYGSelectionRange() {
  // Function to store range of current selection
  var selection = GetWYSIWYGSelection();

  if (IsDefined(selection, "rangeCount") &&
      IsDefined(selection, "getRangeAt")) {
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

    /*
    This is almost right. I think the real test is: if they're in the same node, if the
    range is a different length than the text, then you have to figure out which way they
    dragged (extentOffset is always the endpoint, but it might be at the start of the word -
    if baseOffset is bigger than extentOffset, that's a right to left drag, so drop the selection to the
    end of the sel, and walk by words backwards, otherwise drop to the start and walk forwards),
    then drop the selection to the endpoint that's towards where the click was (the baseOffset),
    then walk one word at a time (it was a double click, rememeber?) with modify, until the
    new endpoint of the range is at or past (depending in the direction!) the extentOffset.
    if (selection.type == "Range") {
      if (selection.baseOffset == selection.extentOffset && selection.baseNode == selection.extentNode) {
        //if you doubleclick, safari does not form the selection correctly. Fix it here, as best we can.
        //Also, toString doesn't seem to work directly, for some reason, but the cast does.
        //result.extentOffset = result.baseOffset + (("" + selection).length);
        selection.collapseToStart();
        rng.modify("extend", "right", "word");
        selection = GetWYSIWYGSelection();
        result = {
          baseOffset: selection.baseOffset,
          baseNode: selection.baseNode,
          extentOffset: selection.extentOffset,
          extentNode: selection.extentNode};
      }
    }
    */

    return result;
  }

  return selection;
}
  
/**
 * Checks to see if the currently selected range spans block-level elements.
 *
 * @param {Object} selRange The current DOM selection range.
 *
 * @returns true if the range spans block-level elements, false otherwise.
 * @type Boolean
 */
function WY_isRangeComplex_(selRange) {
  var frag = selRange.cloneContents(); 
  for (var i = 0; i < frag.childNodes.length; i++) {
    if (IsDefined(frag.childNodes[i], "tagName")) {
      var t = frag.childNodes[i].tagName.toLowerCase();
      if (t == "div" || t == "table" || t == "ul" || t == "ol" || t == "p") {
        return true;
      }
    }
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

FCK.SwitchEditMode = function( noUndo ){
	var bIsWysiwyg = ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ) ;

	// Save the current IsDirty state, so we may restore it after the switch.
	var bIsDirty = FCK.IsDirty() ;

	var sHtml ;

	// Update the HTML in the view output to show.
	if ( bIsWysiwyg )
	{
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

FCKConfig.ProtectedSource.Revert = function( html, clearBin )
{
	function _Replace( m, opener, index )
	{
		var protectedValue = clearBin ? FCKTempBin.RemoveElement( index ) : FCKTempBin.Elements[ index ] ;
		if(!protectedValue) return '';
		if(protectedValue==m)return protectedValue;
		// There could be protected source inside another one.
		if(FCKConfig.RemoveScript && protectedValue.match(/<script[\s\S]*?<\/script>/gi) && protectedValue.indexOf('fck_')==-1){
			return '';
		}
		return FCKConfig.ProtectedSource.Revert( protectedValue, clearBin ) ;
	}
	return html.replace( /(<|&lt;)!--\{PS..(\d+)\}--(>|&gt;)/g, _Replace ) ;
}

function FCKIECleanup_Cleanup()
{
	if ( !this._FCKCleanupObj )
		return ;
	var aItems = this._FCKCleanupObj.Items ;
	
	while ( aItems.length > 0 )
	{

		// It is important to remove from the end to the beginning (pop()),
		// because of the order things get created in the editor. In the code,
		// elements in deeper position in the DOM are placed at the end of the
		// cleanup function, so we must cleanup then first, otherwise IE could
		// throw some crazy memory errors (IE bug).
		var oItem = aItems.pop() ;
		try{
			if ( oItem )
				oItem[1].call( oItem[0] ) ;
		}catch(err){
			//Just Skip it.
		}
	}
	this._FCKCleanupObj.Items = null;

	this._FCKCleanupObj = null ;
	Event.unloadCache();
	if(window.FCK_Cleanup)window.FCK_Cleanup.call(FCK);
	if ( window.CollectGarbage )
		window.CollectGarbage() ;
}

function FCK_Cleanup()
{
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
function FCKEditingArea_Cleanup()
{
	this.TargetElement = null ;
	this.IFrame = null ;
	this.Document = null ;
	this.Textarea = null ;
	this._FCKToolbarPanelButton = null;
	if ( this.Window )
	{
		this.Window._FCKEditingArea = null ;
		this.Window = null ;
	}
}

function FCKMenuBlock_Cleanup()
{
	this._Window = null ;
	this._ItemsTable = null ;
}


function FCKMenuItem_Cleanup()
{
	this.MainElement = null ;
}


function FCKPanel_Cleanup()
{
	this._Popup = null ;
	this._Window = null ;
	this.Document = null ;
	this.MainNode = null ;
}


function FCKSpecialCombo_Cleanup()
{
	this._LabelEl = null ;
	this._OuterTable = null ;
	this._ItemsHolderEl = null ;
	if ( this.Items )
	{
		for ( var key in this.Items ){
			var eDiv = this.Items[key];
			if(eDiv){
				eDiv.onmouseover = eDiv.onmouseout = eDiv.onclick = null;
			}
			this.Items[key] = null ;
		}
		this.Items = null;
	}
	if ( this._Fields )
	{
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

FCKSpecialCombo.prototype.Create = function( targetElement )
{
	var oDoc = FCKTools.GetElementDocument( targetElement ) ;
	var eOuterTable = this._OuterTable = targetElement.appendChild( oDoc.createElement( 'TABLE' ) ) ;
	eOuterTable.cellPadding = 0 ;
	eOuterTable.cellSpacing = 0 ;

	eOuterTable.insertRow(-1) ;

	var sClass ;
	var bShowLabel ;

	switch ( this.Style )
	{
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

	if ( this.Caption && this.Caption.length > 0 && bShowLabel )
	{
		var oCaptionCell = eOuterTable.rows[0].insertCell(-1) ;
		oCaptionCell.innerHTML = this.Caption ;
		oCaptionCell.className = 'SC_FieldCaption' ;
	}
	if(!this._Fields)this._Fields = [];
	// Create the main DIV element.
	var oField = FCKTools.AppendElement( eOuterTable.rows[0].insertCell(-1), 'div' ) ;
	if ( bShowLabel )
	{
		oField.className = 'SC_Field' ;
		oField.style.width = this.FieldWidth + 'px' ;
		oField.innerHTML = '<table width="100%" cellpadding="0" cellspacing="0" style="TABLE-LAYOUT: fixed;"><tbody><tr><td class="SC_FieldLabel"><label>&nbsp;</label></td><td class="SC_FieldButton">&nbsp;</td></tr></tbody></table>' ;

		this._LabelEl = oField.getElementsByTagName('label')[0] ;		// Memory Leak
		this._LabelEl.innerHTML = this.Label ;
	}
	else
	{
		oField.className = 'TB_Button_Off' ;
		//oField.innerHTML = '<span className="SC_FieldCaption">' + this.Caption + '<table cellpadding="0" cellspacing="0" style="TABLE-LAYOUT: fixed;"><tbody><tr><td class="SC_FieldButton" style="border-left: none;">&nbsp;</td></tr></tbody></table>' ;
		//oField.innerHTML = '<table cellpadding="0" cellspacing="0" style="TABLE-LAYOUT: fixed;"><tbody><tr><td class="SC_FieldButton" style="border-left: none;">&nbsp;</td></tr></tbody></table>' ;

		// Gets the correct CSS class to use for the specified style (param).
		oField.innerHTML = '<table title="' + this.Tooltip + '" class="' + sClass + '" cellspacing="0" cellpadding="0" border="0">' +
				'<tr>' +
					//'<td class="TB_Icon"><img src="' + FCKConfig.SkinPath + 'toolbar/' + this.Command.Name.toLowerCase() + '.gif" width="21" height="21"></td>' +
					'<td><img class="TB_Button_Padding" src="' + FCK_SPACER_PATH + '" /></td>' +
					'<td class="TB_Text">' + this.Caption + '</td>' +
					'<td><img class="TB_Button_Padding" src="' + FCK_SPACER_PATH + '" /></td>' +
					'<td class="TB_ButtonArrow"><img src="' + FCKConfig.SkinPath + 'images/toolbar.buttonarrow.gif" width="5" height="3"></td>' +
					'<td><img class="TB_Button_Padding" src="' + FCK_SPACER_PATH + '" /></td>' +
				'</tr>' +
			'</table>' ;
	}


	// Events Handlers

	oField.SpecialCombo = this ;

	oField.onmouseover	= FCKSpecialCombo_OnMouseOver ;
	oField.onmouseout	= FCKSpecialCombo_OnMouseOut ;
	oField.onclick		= FCKSpecialCombo_OnClick ;
	this._Fields.push(oField);
	FCKTools.DisableSelection( this._Panel.Document.body ) ;
}


function FCKToolbar_Cleanup()
{
	this.MainElement = null ;
	this.RowElement = null ;
	this.Items = null;
}


function FCKToolbarButtonUI_Cleanup()
{
//	this.Icon = null;
	this._ToolbarButton = null;
	if ( this.MainElement )
	{
		this.MainElement._FCKButton = null ;
		this.MainElement.onclick = this.MainElement.onmouseover = this.MainElement.onmouseout = null;
		this.MainElement = null ;
	}
}

function FCKTextColorCommand_Cleanup()
{
	if(this._Items){
		for ( var i=0,n=this._Items.length;i<n;i++ ){
			var oItem = this._Items[i];
			if(oItem){
				oItem.Command = null ;
				oItem.onmouseover = oItem.onmouseout = oItem.onclick = null;
			}
		}
		this._Items = null;
	}
}

FCKTextColorCommand.prototype._CreatePanelBody = function( targetDocument, targetDiv )
{
	if ( FCK.IECleanup )
		FCK.IECleanup.AddItem( this, FCKTextColorCommand_Cleanup ) ;
	function CreateSelectionDiv()
	{
		var oDiv = targetDocument.createElement( "DIV" ) ;
		oDiv.className		= 'ColorDeselected' ;
		oDiv.onmouseover	= FCKTextColorCommand_OnMouseOver ;
		oDiv.onmouseout		= FCKTextColorCommand_OnMouseOut ;

		return oDiv ;
	}

	// Create the Table that will hold all colors.
	var oTable = targetDiv.appendChild( targetDocument.createElement( "TABLE" ) ) ;
	oTable.className = 'ForceBaseFont' ;		// Firefox 1.5 Bug.
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
	oDiv.innerHTML =
		'<table cellspacing="0" cellpadding="0" width="100%" border="0">\
			<tr>\
				<td><div class="ColorBoxBorder"><div class="ColorBox" style="background-color: #000000"></div></div></td>\
				<td nowrap width="100%" align="center">' + FCKLang.ColorAutomatic + '</td>\
			</tr>\
		</table>' ;

	oDiv.Command = this ;
	oDiv.onclick = FCKTextColorCommand_AutoOnClick ;
	this._Items.push(oDiv);
	// Create an array of colors based on the configuration file.
	var aColors = FCKConfig.FontColors.toString().split(',') ;

	// Create the colors table based on the array.
	var iCounter = 0 ;
	while ( iCounter < aColors.length )
	{
		var oRow = oTable.insertRow(-1) ;

		for ( var i = 0 ; i < 8 && iCounter < aColors.length ; i++, iCounter++ )
		{
			oDiv = oRow.insertCell(-1).appendChild( CreateSelectionDiv() ) ;
			oDiv.Color = aColors[iCounter] ;
			oDiv.innerHTML = '<div class="ColorBoxBorder"><div class="ColorBox" style="background-color: #' + aColors[iCounter] + '"></div></div>' ;

			oDiv.Command = this ;
			oDiv.onclick = FCKTextColorCommand_OnClick ;
			this._Items.push(oDiv);
		}
	}

	// Create the Row and the Cell for the "More Colors..." button.
	oCell = oTable.insertRow(-1).insertCell(-1) ;
	oCell.colSpan = 8 ;

	oDiv = oCell.appendChild( CreateSelectionDiv() ) ;
	oDiv.innerHTML = '<table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td nowrap align="center">' + FCKLang.ColorMoreColors + '</td></tr></table>' ;

	oDiv.Command = this ;
	oDiv.onclick = FCKTextColorCommand_MoreOnClick ;
	this._Items.push(oDiv);
}

function FCKToolbarSet_Target_Cleanup()
{
	this.__FCKToolbarSet = null ;
}


function FCKToolbarSet_Cleanup()
{
	var targetDocument = this._Document;
	var eExpandHandle	= targetDocument.getElementById( 'xExpandHandle' ) ;
	var eCollapseHandle	= targetDocument.getElementById( 'xCollapseHandle' ) ;
	eExpandHandle.onclick = eCollapseHandle.onclick = null;
	this._Document = null;
	this._TargetElement = null ;
	this._IFrame = null ;
	this.CurrentInstance = null;
}

function FCKEditingArea_Unload(){
	try{
		Event.stopAllObserving(this);
	}catch(err){
		//Just Skip it
	}
	try{
		Event.stopAllObserving(this.document);
	}catch(err){
		//Just Skip it
	}
	try{
		Event.stopAllObserving(this.document.body);
	}catch(err){
		//Just Skip it
	}
	if(this._FCKEditingArea){
		this._FCKEditingArea.Window = null;
		this._FCKEditingArea.Document = null;
		this._FCKEditingArea = null;
	}
	if(this.document){
		this.document.detachEvent( 'onmouseup', Doc_OnMouseUp ) ;
		if(this.document.body)this.document.body.detachEvent( 'onpaste', Doc_OnPaste ) ;
		this.document.detachEvent("onkeydown", Doc_OnKeyDown ) ;
		this.document.detachEvent("onkeydown", Doc_OnKeyDown ) ;
		this.document.detachEvent("ondblclick", Doc_OnDblClick ) ;
		this.document.detachEvent("onselectionchange", Doc_OnSelectionChange ) ;
	}
	this._FCKEditingArea = null;
}
/*
FCK.GetText = function(_bFormat,_bGetAll){
	var sHtml = FCK.GetHTML(_bFormat,false);
	//comment去除
	sHtml = sHtml.replace(/<TRS_COMMENT([^>]*)>((.|\n|\r)*?)<\/TRS_COMMENT>/ig,'');
	sHtml = sHtml.replace(/(<\/(p|li)>|<\/(p|li) [^>]*>)/ig,'$1<br>');
	var eTmpDiv = document.createElement('DIV');
	try{
		eTmpDiv.innerHTML = sHtml;
		return eTmpDiv.innerText;
	}catch(err){
	}

	return sHtml;
}
*/
FCK.QuickGetHtml = function(_bFormat){
	var sResult = '';
	if(FCK.EditorDocument!=null){
		sResult = FCK.EditorDocument.body.innerHTML;
		if(sResult.indexOf('_fckfakelement')!=-1 || sResult.indexOf('_trscomment')!=-1){
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
			sResult = sResult.replace(new RegExp('<script([^>]*)>(\n|\r|.)*?</'+'script>', 'img'), function(_a0, _a1){
				if(_a1.indexOf(' fck_adintrs=')!=-1 || _a1.indexOf(' fck_trsvideo=')!=-1){
					return _a0;
				}
				return '';
			});
		}catch(err){
			//Just Skip it.
		}
	}
	//*/
	sResult = replaceNbsps(sResult);
	return sResult;
}
FCK.GetText = FCK.QuickGetText = function(){
	if(FCK.EditorDocument!=null){
		return FCK.EditorDocument.body.innerText;
	}
	else if(FCK.EditingArea.Textarea){
		try{
			var sHtml = FCK.EditingArea.Textarea.value;
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
}
var FCKExtractCommand = function(_sType){
	this.type = _sType;
}
if(FCKConfig.SupportExtract){
	FCKCommands.RegisterCommand( 'ExtractTitle', 
		new FCKExtractCommand('Title')) ;
	FCKCommands.RegisterCommand( 'ExtractAbstract', 
		new FCKExtractCommand('Abstract')) ;
}

FCKXHtmlEntities.Initialize = function()
{
	FCKXHtmlEntities.Entities = {
//		' ' : 'nbsp'
	}
	FCKXHtmlEntities.EntitiesRegex = /./g;
}

// Retrieves a entity (internal format) for a given character.
function FCKXHtml_GetEntity( character )
{
	// We cannot simply place the entities in the text, because the XML parser
	// will translate & to &amp;. So we use a temporary marker which is replaced
	// in the end of the processing.
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

FCKXHtml.TagProcessors['p'] = function( node, htmlNode ){
	if( FCKBrowserInfo.IsIE && htmlNode.innerHTML==''){
//		if(htmlNode.innerHTML.toUpperCase().indexOf('<BR')==-1)
			return false;
	}
	if ( FCKBrowserInfo.IsIE && htmlNode.outerHTML.toUpperCase().indexOf('</P>')==-1){
		htmlNode.innerHTML = htmlNode.innerHTML;
//		return false ;
	}
	return FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;
}

FCK.FixBody = function(){
	//Do nothing
}

FCKXHtml._AppendChildNodes = function( xmlNode, htmlNode, isBlockElement )
{
//	var oNode = htmlNode.firstChild ;

//	while ( oNode )
//	{
//		this._AppendNode( xmlNode, oNode ) ;
//		oNode = oNode.nextSibling ;
//		if(oNode!=null&&oNode.parentNode!=htmlNode){
//			oNode = null;
//		}
//	}

	var arrChildNodes = htmlNode.childNodes;
	for (var i = 0; i < arrChildNodes.length; i++){
		var oNode = arrChildNodes[i];
		this._AppendNode( xmlNode, oNode ) ;
	}

	// Trim block elements. This is also needed to avoid Firefox leaving extra
	// BRs at the end of them.
	if ( isBlockElement )
		FCKDomTools.TrimNode( xmlNode, true ) ;

	// If the resulting node is empty.
	if ( xmlNode.childNodes.length == 0 )
	{
		if ( isBlockElement && FCKConfig.FillEmptyBlocks )
		{
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
	}

	return xmlNode ;
}
function replaceNbsps(html){
	return html.replace(/&nbsp;&nbsp;/ig, '　');
}
