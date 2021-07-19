Ext.ns('com.trs.ui');
/**
*其它表
*/
(function(){
	//private 
	var selectBtn = "-select-btn";
	var otherfieldText = "-text";
	var template = [
		'<div class="XOtherField">',
			'<input type="hidden" name="{0}" id="{0}" value="{1}" ignore="true"/>',
			'<span class="selectOtherTable" id="{0}-select-btn"></span>',
			'<span class="otherfield-text" id="{0}-text">{2}</span>',
		'</div>'
	].join("");
	/**
	*config:tableName : dbFieldName, anotherName, name,viewId
	*/
	com.trs.ui.XOtherField = Ext.extend(com.trs.ui.BaseComponent, {
		init : function(config){
			com.trs.ui.XOtherField.superclass.init.apply(this, arguments);
			com.trs.ui.XOtherFieldMgr.add(this);
		},
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];	
			var sValue = config['value'];
			//副表字段的值只起到判空的作用，不在此处存储实际值。为1表示不空。
			if(sValue){
				sValue = "1";
			}
			//fixme 可能需要注意转义处理
			return String.format(template, config['name'], sValue, config['desc']);
		},
		getTableName : function(){
			return this.initConfig['tableName'];
		},
		selectOtherTable : function(){
			var caller = this;
			var config = this.initConfig;
			var sTableName = config["tableName"];
			var aFieldNames = com.trs.ui.XOtherFieldMgr.getFieldProperties(sTableName, 'name');
			var sURL = WCMConstants.WCM6_PATH + "metaviewdata/metaviewdata_classic_list_select.html";
			new wcm.CrashBoard({				
				title : wcm.LANG.METAVIEWDATA_97 || "选择数据",
				src : sURL,
				top : (screen.availHeight - 450)/2 - 50 + "px",
				left : (screen.availWidth - 800)/2 + "px",
				width:'800px',
				height:'450px',
				border:false,
				params:{					
					ViewId:config['viewId'],
					TableName:sTableName,
					SelectFields: aFieldNames.join(","),
					CurrField:config['name']
				},
				callback : function(info){
					com.trs.ui.XOtherFieldMgr.setTableValue(sTableName, info["id"] || 0, $(config['name']).parentNode);
					var fields = info["values"];
					//fixme 这个地方还可以做优化调整
					if(fields){
						for(var i=0,len=fields.length;i<len;i++){
							var dom = $(fields[i]["name"] + otherfieldText);
							if(!dom) continue;
							Element.update(dom, fields[i]["value"]);
							if(fields[i]["value"]){
								$(fields[i]["name"]).value = "1";
							}
						}
					}else{
						for(var i = 0,len=aFieldNames.length; i<len; i++){
							$(aFieldNames[i]).value = "";
							Element.update(aFieldNames[i] + otherfieldText, "");
						}
					}
				}
			}).show();				
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + selectBtn, 'click', this.selectOtherTable.bind(this));
		}
	});
})();

com.trs.ui.XOtherFieldMgr = function(){
	//private
	var suffix = "id";
	var tableCache = {};//<tableName, [field1,field1...]>
	return {
		add : function(field){
			var sTableName = field.getTableName();
			if(!tableCache[sTableName]) tableCache[sTableName] = [];
			tableCache[sTableName].push(field);
		},
		setTableValue : function(sTableName, sTableValue, container){
			var sName = sTableName + suffix;
			var dom = $(sName);
			if(!dom){
				//dom = document.createElement('<input type="hidden" name="' + sName + '" id="' + sName + '"/>');
				dom = document.createElement("input");
				dom.type = "hidden";
				dom.setAttribute("name",sName);
				dom.setAttribute("id",sName);
				container = container || document.body;
				container.appendChild(dom);
			}
			dom.value = sTableValue;
		},
		getTableValue : function(sTableName){
			var sName = sTableName + suffix;
			if(!$(sName)) return;
			return $(sName).value;
		},
		getFieldProperties : function (sTableName, sPropertyName){
			var fields = tableCache[sTableName] || [];	
			var rst = [];
			for(var i=0,len=fields.length;i<len;i++){
				rst.push(fields[i].getProperty(sPropertyName));
			}	
			return rst;
		}
	};
}();