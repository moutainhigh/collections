Ext.ns('com.trs.ui');
/**
*单选按钮
*/
com.trs.ui.XRadio = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		var sName = config['name'];
		items = this.getItems();
		var selectedValue = ',' + config['value'] + ','; 
		var aHtml = ['<div class="XRadio" id="', sName, '-box">'];
		for (var i = 0; i < items.length; i++){
			var sId = sName + '_' + i;
			aHtml.push( 
				'<div class="radio-item">',
					'<input type="radio"',
						' name="', sName, '"',
						' id="', sId,  '"',
						selectedValue.indexOf(',' + items[i]["value"] + ',') >= 0 ? ' checked="checked"' : '',
						' value="', items[i]["value"] + '"',
						(config['disabled'] ? 'disabled="disabled"' : ''),
					' />',
					'<label for="', sId, '">', items[i]["label"], '</label>',
				'</div>'
			);
		}		
		aHtml.push('</div>');
		return aHtml.join("");			
	},
	getValue : function(){
		var config = this.initConfig;
		var sName = config['name'];
		var doms = document.getElementsByName(sName);
		for (var i = 0; i < doms.length; i++){
			if(doms[i].checked){
				return doms[i].value;
			}
		}
		return "";
	},
	getItems : function(){
		var items = this.initConfig['items'];
		if(String.isString(items)){
			items = this.initConfig['items'] = com.trs.util.ItemParser.parse(items);
		}			
		return items;
	}
});