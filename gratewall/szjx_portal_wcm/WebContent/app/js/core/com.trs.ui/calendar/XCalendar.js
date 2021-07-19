Ext.ns('com.trs.ui');
/**
*日期控件
*/
(function(){
	var template = [
		'<div class="XCalendar">',
			'<input type="text" name="{0}" elname="{2}" id="{0}" value="{1}" />',
			'<button type="button" id="{0}-btn"><b></b></button>',
		'</div>'
	].join("");
	
	com.trs.ui.XCalendar = Ext.extend(com.trs.ui.BaseComponent, {
		getHtml : function(){
			var config = this.initConfig;
			return String.format(template, config["name"], config["value"], config["elname"]);	
		},
		afterRender : function(){	
			com.trs.ui.XCalendar.superclass.afterRender.apply(this, arguments);
			var config = this.initConfig;
			if(config['disabled']){
				$(config['name']).disabled = true;
				$(config['name']+"-btn").disabled = true;
			}
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			var withTime = this.initConfig['withtime'];
			var dtFmt = this.initConfig['dtFmt'];
			if(this.initConfig['withTime'] != null) withTime = this.initConfig['withTime'];
			wcm.TRSCalendar.get({
				input : this.initConfig['name'],
				handler : this.initConfig['name'] + '-btn',
				withtime : withTime,
				dtFmt : dtFmt
			});
		}
	});
})();