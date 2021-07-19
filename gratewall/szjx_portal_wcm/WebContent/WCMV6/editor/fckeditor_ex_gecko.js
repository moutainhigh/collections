FCK.TRSExtend = {
	doFontSize : function(_sFontSize){
		// Gets the actual selection.
		var oSel = FCK.EditorWindow.getSelection() ;
		
		// Get the first available range.
		var oRange = oSel.getRangeAt(0) ;
		
		var sType = FCKSelection.GetType();
		if ( sType.toLowerCase()== 'text' ){
			//TODO not htmlText but text
			oRange.htmlText = new XMLSerializer().serializeToString(oRange.cloneContents());
			var sHtml = '<SPAN style="font-size:'+_sFontSize+'">'+oRange.htmlText + '</span>';
			FCK.InsertHtml(sHtml);
		}
		else if ( sType.toLowerCase()== 'control' ){
			var oElement = FCKSelection.GetSelectedElement();
			oElement.style.fontSize = _sFontSize;
		}
		else{
			var sHtml = '<span style="font-size:'+_sFontSize+'"></span>';
			FCK.InsertHtml(sHtml);
		}
	},
	getFontSize : function(){
		alert('must implement.');
		return '';
	}
};
