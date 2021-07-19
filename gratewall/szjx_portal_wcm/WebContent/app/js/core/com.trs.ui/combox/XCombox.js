Ext.ns('com.trs.ui');
/**
*可输入下拉框
*/
(function(){
	//private 
	var outerTemplate = [
		'<div class="XCombox">',
			'<input type="hidden" name="{0}" id="{0}" value="{1}" />',
			'<input type="text" name="{0}-text" id="{0}-text" value="{2}" /><span>',
				'<select name="{0}-sel" id="{0}-sel">{3}</select>',
			'</span>',
		'</div>'
	].join("");
	var innerTemplate = '<option value="{0}" _value="{1}">{0}</option>';

	var getLabelOrValue = function(aItems, sNameKey, sValueKey, sValue){
		for (var i = 0; i < aItems.length; i++){
			if(aItems[i][sNameKey] == sValue) return aItems[i][sValueKey];
		}
		return sValue;
	}

	com.trs.ui.XCombox = Ext.extend(com.trs.ui.BaseComponent, {
		getHtml : function(){
			var config = this.initConfig;
			var aHtml = [];
			var items = this.getItems();
			for (var i = 0; i < items.length; i++){
				aHtml.push(String.format(innerTemplate, $transHtml(items[i]["label"]), $transHtml(items[i]["value"])));
			}
			if(config["value"] != null){
				var sValue = config["value"] || "";
				var sLabel = getLabelOrValue(items, 'value', 'label', sValue) || "";
			}else{
				var sLabel = config["label"] || "";
				var sValue = getLabelOrValue(items, 'label', 'value', sLabel) || "";
			}
			return String.format(outerTemplate, config['name'], $transHtml(sValue), $transHtml(sLabel), aHtml.join(""));			
		},
		afterRender : function(){	
			com.trs.ui.XCombox.superclass.afterRender.apply(this, arguments);
			var config = this.initConfig;
			if(config['disabled']){
				$(config['name'] + "-text").disabled = true;
				$(config['name'] + "-sel").disabled = true;
			}
		},
		initActions : function(){
			com.trs.ui.XCombox.superclass.initActions.apply(this, arguments);
			var config = this.initConfig;
			var sName = config['name'];
			var sel = $(sName + '-sel');
			Event.observe(sel, 'click', function(){
				sel.value = $(sName + "-text").value;
			});
			Event.observe(sel, 'change', function(){
				var opt = sel.options[sel.selectedIndex];
				$(sName + "-text").value = opt.value;
				$(sName).value = opt.getAttribute("_value");
			});
			Event.observe(sName + "-text", 'change', function(){
				var sValue = $(sName + "-text").value;
				sel.value = sValue;
				if(sel.selectedIndex != -1){
					sValue = sel.options[sel.selectedIndex].getAttribute("_value");
				}
				$(sName).value = sValue;
			});		
		},
		getValue : function(){
			var config = this.initConfig;
			var sValue = $(config['name']).value;
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
})();