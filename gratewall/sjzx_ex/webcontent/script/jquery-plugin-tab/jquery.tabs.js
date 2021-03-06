
$(function () {
	$.tabs = function (obj) {
		return (this instanceof $.tabs) ? this.init.apply(this, arguments) : new $.tabs(obj);
	};
	$.tabs.prototype = {init:function (obj) {
		var that = this; 
		$.extend(this, {selectedClass:"dom_tabs_selected", tabsSelector:">dt a", panelsSelector:">dd", click:$.noop, selected:0}, obj || {});
		this.ui = $(obj.selector);
		this.tabs = this.ui.find(this.tabsSelector);
		this.panels = this.ui.find(this.panelsSelector);
		this.ui.find("tabs").removeClass("tabs");
		this.ui.addClass("tabs");
		this.select(this.selected);
		this.tabs.live("click", function () {
			var index = that.tabs.index(this);
			that._switch.call(that, index);
			that.click.call(this, index, that);
		});
	}, _switch:function (index) {
		this.tabs.removeClass(this.selectedClass).eq(index).addClass(this.selectedClass);
		this.panels.hide().eq(index).show();
	}, select:function (index, callback) {
		index = ~~index;
		this._switch(index);
		callback && callback.call(this.tabs[index], index, this);
	}, remove:function (index, callback) {
		index = ~~index;
		this.tabs.eq(index).remove();
		this.panels.eq(index).remove();
		callback && callback.call(this.tabs[index], index, this);
	}};
	
});

