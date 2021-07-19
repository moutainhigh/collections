Ext.applyIf(Event, {
	KEY_BACKSPACE:	8,
	KEY_TAB:		9,
	KEY_RETURN:		13,
	KEY_ESC:		27,
	KEY_LEFT:		37,
	KEY_UP:			38,
	KEY_RIGHT:		39,
	KEY_DOWN:		40,
	KEY_DELETE:		46,
	KEY_PGUP:		33,
	KEY_PGDN:		34,
	KEY_HOME:		36,
	KEY_END:		35,
	KEY_F2:			113
});

Ext.applyIf(Array.prototype, {
	includeI : function(_object){
		if(Ext.isString(_object)){
			var matched = false;
			_object = _object.toLowerCase();
			try{
				this.each(function(_element){
					if(!Ext.isString(_element))return;
					matched = _element.toLowerCase()==_object;
					if(matched)throw $break;
				});
			}catch(err){
			}
			return matched;
		}
		return this.include(_object);
	}
});