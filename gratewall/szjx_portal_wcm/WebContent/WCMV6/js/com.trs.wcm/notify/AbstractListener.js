$package('com.trs.wcm.notify');
com.trs.wcm.notify.AbstractListener = Class.create('wcm.AbstractListener');
com.trs.wcm.notify.AbstractListener.prototype = {
	initialize : function(){
		this.nav = $MessageCenter.getNav();
		this.main = $MessageCenter.getMain();
		this.oap = $MessageCenter.getOAP();
	},
	doNotify : function(_params){
		this.params = _params;
		if(this.nav) {
			this.onNavFresh();
		}
		window.setTimeout(function(){
			if(this.main) {
				this.onMainFresh();
			}
		}.bind(this), 100);
		if(this.oap) {
			this.onOAPFresh();
		}
	},
	onNavFresh : function(){
		//To be realized
	},
	onMainFresh : function(){
		//To be realized
	},
	onOAPFresh : function(){
		//To be realized
	}
};

AttrModifyListener = Class.create('wcm.AttrModifyListener');
AttrModifyListener.prototype = Object.extend(com.trs.wcm.notify.AbstractListener.prototype, {
	onNavFresh : function(){
		//alert('nav refresh');
		this.nav.refreshNode(this.params['objectid']);
	},
	onMainFresh : function(){
		if(this.main.updateHostInfo) {
			this.main.updateHostInfo();
		}

		if(this.main.updateObjectInfo) {
			this.main.updateObjectInfo();
		}
	}
});