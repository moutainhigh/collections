Ext.ns('com.trs.ui');
/**
*单选按钮
*/
com.trs.ui.XRadio = Ext.extend(com.trs.ui.BaseComponent, {
	cancelSelectBtnName : '-cancel-btn',
	getHtml : function(){
		var config = this.initConfig;
		var sName = config['name'];
		var radioValidation = config['validation'] || "";
		radioValidation = eval('({' + radioValidation + '})');
		var bRequired = radioValidation["required"] == 1;
		items = this.getItems();
		var selectedValue = ',' + config['value'] + ','; 
		var aHtml = ['<div class="XRadio" id="', sName, '-box" tabIndex="1">'];
		for (var i = 0; i < items.length; i++){
			var sId = sName + '_' + i;
			aHtml.push( 
				'<div class="radio-item">',
					'<input type="radio"',
						' name="', sName, '"',
						' id="', sId,  '"',
						selectedValue.indexOf(',' + items[i]["value"] + ',') >= 0 ? ' checked="checked" class="default_option active_option"' : ' class="default_option"',
						' value="', items[i]["value"] + '"',
						(config['disabled'] ? 'disabled="disabled"' : ''),
					' />',
					'<label for="', sId, '">', items[i]["label"], '</label>',
				'</div>'
			);
		}
		if(!bRequired && !config['disabled']){
			aHtml.push(
				'<input type="radio" name="',sName,'" value="" id="',sName,'_none" style="display:none;">',
				'<div class="radio-item calcelselect">',
					'<a href="#"',
						' name="'+ sName + this.cancelSelectBtnName,'"',
						' id="'+ sName + this.cancelSelectBtnName, '"',
						'>',
						'取消选择',
					'</a>',
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
	getBox : function(){
		var sName = this.initConfig['name'];
		return $(sName + "-box");
	},
	getItems : function(){
		var items = this.initConfig['items'];
		if(String.isString(items)){
			items = this.initConfig['items'] = com.trs.util.ItemParser.parse(items);
		}			
		return items;
	},
	changeStyleAndCancelSelect : function(event){
		var name = this.initConfig['name'];
		event = event || window.event;
		var srcEl = Event.element(event);
		var currName = this.initConfig['name'];
		var cancelElId = currName + this.cancelSelectBtnName;
		if(srcEl.id != cancelElId && srcEl.name != name){
			return;
		}
		if(srcEl.id == cancelElId){
			var bCancel = true;
			//取消选择，循环将所有的单选钮的选中都取消
			var doms = document.getElementsByName(currName);
			for (var i = 0; i < doms.length; i++){
				if(doms[i].checked && bCancel){
					doms[i].checked = false;
				}
				if(doms[i].id == currName + '_none'){
					doms[i].checked = true;
				}
			}
		}else{
			var box = this.getBox();
			if(Element.hasClassName(box,'errorStyle')){
				Element.removeClassName(box,'errorStyle');
			}
		}
		//将当前选中的按钮处于激活状态
		var optionDoms = document.getElementsByName(currName);
		for(var k=0; k < optionDoms.length; k++){
			var optionDom = optionDoms[k];
			if(optionDom.checked && !Element.hasClassName(optionDom, "active_option")){
				Element.addClassName(optionDom, "active_option");
			}
			if(!optionDom.checked && Element.hasClassName(optionDom, "active_option")){
				Element.removeClassName(optionDom, 'active_option');
			}
		}
	},
	initActions : function(){
		var name = this.initConfig['name'];
		var boxElId = name + '-box';
		Event.observe(boxElId,'click',this.changeStyleAndCancelSelect.bind(this));
	}
});