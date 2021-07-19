/*wcm.ColorPicker*/
if(!window.wcm) wcm = {};
if(!wcm.LANG) wcm.LANG = {};
WCMLANG = wcm.LANG;
Ext.ns('wcm.ColorPicker');
Number.prototype.toColorPart = function() { 
	var digits = this.toString(16); 
	if (this < 16) return '0' + digits; 
	return digits; 
} 
wcm.ColorPicker = function(sid){
	var m_sColorPicker = (function(){
		var arrFontColors = '000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,808080,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF' ;
		var aColors = arrFontColors.toString().split(',') ;
		var iCounter = 0 ;
		var arr = ['<table border="0" cellPadding="0" cellspacing="0" style="table-layout:fixed" class="ForceBaseFont">'];
		arr.push('<tr><td colspan="8">',
			'<table cellspacing="0" cellpadding="0" width="100%" border="0" class="coloritem colorheader">',
			'<tr unselectable="on">',
			'<td unselectable="on"><div class="ColorBoxBorder" unselectable="on"><div class="ColorBox" style="background-color: #000000" unselectable="on"></div></div></td>',
			'<td nowrap width="100%" align="center" unselectable="on">','自动','</td>',
			'</tr>',
			'</table>',
			'</td></tr>'
		);
		while ( iCounter < aColors.length ){
			arr.push('<tr>');
			for ( var i = 0 ; i < 8 && iCounter < aColors.length ; i++, iCounter++ ){
				arr.push('<td unselectable="on">');
				arr.push('<div class="coloritem ColorBoxBorder" unselectable="on"><div class="ColorBox" style="background-color: #');
				arr.push(aColors[iCounter]);
				arr.push('" unselectable="on"></div></div>');
				arr.push('</td>');
			}
			arr.push('</tr>');
		}
		arr.push('</table>');
		return arr.join('');
	})();
	function getColorItem(target){
		while(target!=null && target.tagName!='BODY'){
			if(Element.hasClassName(target, 'coloritem'))return target;
			target = target.parentNode;
		}
		return null;
	}
	//colorpicker
	this.id = sid || 'colorpicker';
	var div = $(this.id);
	if(div==null){
		div = document.createElement('div');
		div.id = this.id;
		document.body.appendChild(div);
		div.className = 'colorpicker';
		Element.hide(div);
	}
	Element.update(this.id, m_sColorPicker);
	var caller = this;
	Ext.get(this.id).on('click', function(event, target){
		var coloritem = getColorItem(target);
		if(coloritem==null)return;
		var sColor = '';
		if(coloritem.tagName!='TABLE'){
			sColor = coloritem.getElementsByTagName('DIV')[0].style.backgroundColor;
			if (/rgb/i.test(sColor)){
				var arr=eval(sColor.replace("rgb","new Array"));   
				sColor="#"+Number(arr[0]).toColorPart()+Number(arr[1]).toColorPart()+Number(arr[2]).toColorPart();
			}
		}
		if(caller.doAfterClick)caller.doAfterClick(sColor);
	});
	var lastitem = null;
	Ext.get(this.id).on('mouseover', function(event, target){
		var coloritem = getColorItem(target);
		if(coloritem==null)return;
		if(coloritem==lastitem)return;
		if(lastitem){
			if(lastitem.tagName=='TABLE'){
				Element.removeClassName(lastitem, 'colorheader1');
				Element.addClassName(lastitem, 'colorheader');
			}else{
				Element.removeClassName(lastitem, 'ColorBoxBorder1');
				Element.addClassName(lastitem, 'ColorBoxBorder');
			}
		}
		if(coloritem.tagName=='TABLE'){
			Element.removeClassName(coloritem, 'colorheader');
			Element.addClassName(coloritem, 'colorheader1');
		}else{
			Element.removeClassName(coloritem, 'ColorBoxBorder');
			Element.addClassName(coloritem, 'ColorBoxBorder1');
		}
		lastitem = coloritem; 
	});
};