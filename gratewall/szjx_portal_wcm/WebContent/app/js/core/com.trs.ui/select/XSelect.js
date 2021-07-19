Ext.ns('com.trs.ui');
/**
*下拉框
*/
com.trs.ui.XSelect = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		var sName = config['name'];
		var selectedValue = ',' + config["value"] + ','; 
		var aHtml = ['<select name="', sName, '" id="', sName, '" class="XSelect">'];
		var blank = config["blank"];
		if(blank != null){
			aHtml.push('<option value="', blank["value"], '">', blank["label"], '</option>');
		}
		items = this.getItems();
		for (var i = 0; i < items.length; i++){
			var bSelected = selectedValue.indexOf(',' + items[i]["value"] + ',') >= 0;
			var strSelected = bSelected ? ' selected="selected"' : '';
			aHtml.push('<option value="', items[i]["value"], '"', strSelected, '>', items[i]["label"], '</option>');
		}
		aHtml.push('</select>');			
		return aHtml.join("");			
	},
	getBox : function(){
		return $(this.initConfig['name']);
	},
	afterRender : function(){	
		com.trs.ui.XSelect.superclass.afterRender.apply(this, arguments);
		var config = this.initConfig;
		if(config['disabled']){
			$(config['name']).disabled = true;
		}
	},
	getValue : function(){
		var config = this.initConfig;
		var sValue = $(config['name']).value;
		var blank = config["blank"];
		if(blank && sValue == blank['value']) return "";
		return sValue.trim();
	},
	getItems : function(){
		var items = this.initConfig['items'];
		if(String.isString(items)){
			items = this.initConfig['items'] = com.trs.util.ItemParser.parse(items);
		}			
		return items;
	}
});