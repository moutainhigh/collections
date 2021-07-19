Ext.ns("wcm.TypeField");

wcm.TypeField = function(config){
	this.KeyCache = {};
	wcm.TypeField.superclass.constructor.apply(this, arguments);
	Ext.apply(this, config);
	this.addEvents(
		/**
		*@event beforeload
		*before send request for load data.
		*/
		'beforechange',
		/**
		*@event change
		*
		*/
		'change'
	);
};

Ext.extend(wcm.TypeField, wcm.util.Observable, {
	addFieldKey : function(sFieldName, sFieldKey){
		this.KeyCache[sFieldName.toUpperCase()] = sFieldKey;
	},
	getFieldKey : function(sFieldName){
		return this.KeyCache[sFieldName.toUpperCase()];
	},
	setValue : function(sFieldName, sFieldValue){
		var args = {
			fieldKey : this.getFieldKey(sFieldName),
			fieldName : sFieldName,
			oldValue : $(sFieldName).value,
			newValue : sFieldValue
		};
		if(this.fireEvent('beforechange', args) === false){
			return false;
		}
		$(sFieldName).value = sFieldValue;
		this.fireEvent('change', args);
	}
});

$TypeField = new wcm.TypeField();

/**
*使用示例：设置监听某个字段改变后，相对应的字段需要相应改变
*args 里面有下面这些参数
*	args.fieldName; 监听的字段
*	args.fieldKey; 监听的字段key
*	args.newValue; 字段新值
*	args.oldValue; 字段旧值
*/

/*添加我们要监听改变的字段关键词*/
/*$TypeField.addFieldKey("selectedView","metaView");*/
/*对所有字段进行监听，根据上面追加的关键词，在内部处理进行过滤*/
/*
$TypeField.addListener('change', function(args){
	//如果不是我们要处理的字段，就返回
	if(args.fieldKey != 'metaView' || args.fieldName != 'selectedView') return;
	//否则，对联动改变的字段进行设值，这里是设为空。
	$TypeField.setValue("selectedField", "");
});
*/