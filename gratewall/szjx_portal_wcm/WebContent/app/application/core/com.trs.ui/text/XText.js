Ext.ns('com.trs.ui');
/**
*普通文本
*/
com.trs.ui.XText = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		return [
			'<span class="XText">',
				'<input type="text"', 
					' name="', config["name"], '"',
					' id="', config["name"], '"', 
					' value="', $transHtml(config["value"]), '"',
					' identityField="', config["identityField"], '"', 
					(config["disabled"] ? ' disabled="disabled" class="disableCls"' : ''),
					(config["validation"] ? (' validation="' + config["validation"] + '"') : ''),
				'/>',
			'</span>'
		].join("");
	}
});

/**
*密码文本
*/
com.trs.ui.XPassword = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		return [
			'<span class="XPassword">',
				'<input type="password"', 
					' name="', config["name"], '"',
					' id="', config["name"], '"', 
					' value="', $transHtml(config["value"]), '"',
					(config["disabled"] ? ' disabled="disabled" class="disableCls"' : ''),
					(config["validation"] ? (' validation="' + config["validation"] + '"') : ''),
				'/>',
			'</span>'
		].join("");
	}
});

/**
*多行文本
*/
com.trs.ui.XTextArea = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		return [
			'<span class="XTextArea">',
				'<textarea rows="6" cols="50"', 
					' name="', config["name"], '"',
					' id="', config["name"], '"', 
					(config["disabled"] ? ' disabled="disabled" class="disableCls"' : ''),
					(config["validation"] ? (' validation="' + config["validation"] + '"') : ''),
				'>', 
					config["value"]||"",
				'</textarea>',
			'</span>'
		].join("");
	},
	getValue : function(){
		var config = this.initConfig;
		var sName = config['name'];
		var dom = document.getElementById(sName);
		var reg = new RegExp("\r\n", "g");
		return dom.value.replace(reg, "");
	}
});
/**
*整数
*/
com.trs.ui.XInteger = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		return [
			'<input class="XInteger" type="text"',
        		' name="', config["name"], '"',
				' id="', config["id"] || config["name"], '"',
				' value="', config["value"], '"',
				(config["disabled"] ? ' disabled="disabled" class="disableCls"' : ''),
				(config["validation"] ? (' validation="' + config["validation"] + '"') : ''),
			'>'
		].join("");
	}
});
/**
*小数
*/
com.trs.ui.XFloat = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		return [
			'<input class="XFloat" type="text"',
        		' name="', config["name"], '"',
				' id="', config["name"], '"',
				' value="', config["value"], '"',
     			(config["disabled"] ? ' disabled="disabled" class="disableCls"' : ''),
				(config["validation"] ? (' validation="' + config["validation"] + '"') : ''),
			'>'
		].join("");
	}
});
/**
*双精度
*/
com.trs.ui.XDouble = Ext.extend(com.trs.ui.BaseComponent, {
	getHtml : function(){
		var config = this.initConfig;
		return [
			'<input class="XDouble" type="text"',
        		' name="', config["name"], '"',
				' id="', config["name"], '"',
				' value="', config["value"], '"',
    			(config["disabled"] ? ' disabled="disabled" class="disableCls"' : ''),
				(config["validation"] ? (' validation="' + config["validation"] + '"') : ''),
			'>'
		].join("");
	}
});
