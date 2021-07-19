Ext.ns('com.trs.ui');
/**
*多选框
*/
com.trs.ui.XCheckbox = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		var sName = config['name'];
		items = this.getItems();
		var selectedValue = ',' + config['value'] + ','; 
		var aHtml = ['<div class="XCheckbox" id="', sName, '-box">'];
		for (var i = 0; i < items.length; i++){
			var sId = sName + '_' + i;
			aHtml.push( 
				'<div class="checkbox-item">',
					'<input type="checkbox"',
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
	getBox : function(){
		var sName = this.initConfig['name'];
		return $(sName + "-box");
	},
	getValue : function(){
		var config = this.initConfig;
		var sName = config['name'];
		var doms = document.getElementsByName(sName);
		var selected = [];
		for (var i = 0; i < doms.length; i++){
			if(doms[i].checked) selected.push(doms[i].value);
		}
		return selected.join(",");
	},
	getItems : function(){
		var items = this.initConfig['items'];
		if(String.isString(items)){
			items = this.initConfig['items'] = com.trs.util.ItemParser.parse(items);
		}			
		return items;
	}
});