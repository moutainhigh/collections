/**
 * @class wcm.CrashBoard
*/
(function(){
	var frmTemplate = '<iframe src="{0}" id="{1}" style="height:100%;width:100%;overflow:auto;" frameborder="0" onload="wcm.CrashBoard.contentLoaded(\'{2}\', this);"></iframe>';
	var htmlTemplate = [
        '<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
            '<tr><td><div id="{2}" style="overflow:visible;">{0}</div></td></tr>',
            '<tr><td class="buttons" id="{1}"></td></tr>',
        '</table>'
	].join("");

	wcm.CrashBoard = Ext.extend(wcm.Panel, {		
		baseCls : 'wcm-cbd',
		maskable : false,
		appendParamsToUrl : true,

		initComponent : function(){
			wcm.CrashBoard.superclass.initComponent.apply(this, arguments);
		},
		
		notify : function(params){
			if(this.callback){
				this.callback(params);
			}
		},

		onPosition : function(left, top){
			wcm.CrashBoard.superclass.onPosition.apply(this, arguments);
		},

		onResize : function(width, height){
			wcm.CrashBoard.superclass.onResize.apply(this, arguments);
			if(!height) return;			
			var dom = this.getElement(this.contentId);
			dom.style.height = height;
			/**
			*The iframe has the min height in the quirks mode of ie6, and the 100% is unavailable,
			*so fix it by absolute height.
			*/
			if(Ext.isIE6){
				var dom = this.getElement(this.frmId);
				if(dom) dom.style.height = height;
			}
		},
		getDragElId : function(){
			return this.getId();
		},
		onRender : function(){
			wcm.CrashBoard.superclass.onRender.apply(this, arguments);
			var id = this.getId();
			this.contentId = id + "-content";
			this.btnsId = id + "-buttons";
			var dom = this.getElement(this.bodyId);
			new Insertion.Bottom(dom, String.format(htmlTemplate, this.getContent(), this.btnsId, this.contentId));		

			if(this.btns){
				this.addBtns(this.btns);
				delete this.btns;
			}
		},

		afterRender : function(){
			var centered = this.left == undefined && this.top == undefined;
			wcm.CrashBoard.superclass.afterRender.apply(this, arguments);
			if(centered){
				this.center();
			}
		},

		getContentEl : function(){
			return this.getElement(this.getId() + "-content");
		},

		getContent : function(){
			var id = this.getId();
			if(this.src){
				if(this.appendParamsToUrl){
					var search = $toQueryStr(this.params || {});
					this.src = this.src +(this.src.indexOf("?") >= 0 ? "&" : "?") + search; 
				}
				this.frmId = id + "-iframe";
				return String.format(frmTemplate, this.buildSrc(), this.frmId, id);
			}
			return this.el ? $(this.el).innerHTML : this.html;
		},

		buildSrc : function(){
			var params = {};
			params[wcm.CrashBoard["fromcb"]] = 1;
			var search = $toQueryStr(params, this.params || {});
			return src = this.src +(this.src.indexOf("?") >= 0 ? "&" : "?") + search; 
		},

		resetBtns : function(config){
			this.clearBtns();
			this.addBtns(config["btns"]);
		},

		clearBtns : function(){
			var btnsDom = this.getElement(this.btnsId);
			var dom = Element.first(btnsDom);
			while(dom){
				this.remove(wcmXCom.get(dom));
				dom = Element.first(btnsDom);
			}
		},

		addBtns : function(btns){
			if(!btns) return;
			var btnsDom = this.getElement(this.btnsId);
			for (var i = 0; i < btns.length; i++){
				var cpyBtn = Ext.applyIf({
					renderTo : btnsDom,
					scope : this,
					cmd : btns[i]["cmd"] ? this.close.createInterceptor(btns[i]["cmd"], this) : this.close
				}, btns[i]);
				var btn = new wcm.Button(cpyBtn);
				btn.on('beforeclick', function(){
					return this.inited;
				}, this);
				this.add(btn).render();
			}
		}			
	});
	wcmXCom.reg('crashboard', wcm.Button);
})();


/**
*static method of wcm.CrashBoard
*/
Ext.apply(wcm.CrashBoard, {
	fromcb : '_fromcb_',
	frmIndentyAttr : '-cb-id-',
	contentLoaded : function(id, frm){
		if((frm.src == Ext.blankUrl)||(frm.src == "")) return;
		var cb = wcmXCom.get(id);
		if(!cb) return;
		frm[wcm.CrashBoard.frmIndentyAttr] = id;
		var win = null;
		try{
			win = frm.contentWindow;
			if(win && win.m_cbCfg){
				cb.resetBtns(win.m_cbCfg);
			}
		}catch(error){
			//alert(error.message);
		}
		try{
			if(win && win.init){
				win.init(cb.params);
			}
		}catch(error){
			//alert(error.message);
		}
		cb.inited = true;
	}
});


/**
 * @class wcm.CrashBoarder
*/
wcm.CrashBoarder = function(id){
	//id is window.
	var frm = null;
	if(id && !String.isString(id) && (frm = id.frameElement)){
		id = frm[wcm.CrashBoard.frmIndentyAttr];
	}
	this.id = id;
	this.cb = wcmXCom.get(id);
};


Ext.apply(wcm.CrashBoarder, {
	get : function(id){
		return new wcm.CrashBoarder(id);
	}
});


Ext.apply(wcm.CrashBoarder.prototype, {
	notify : function(params){
		if(this.cb){
			this.cb.notify(params);
		}
	},

	getCrashBoard : function(){
		return this.cb;
	},

	show : function(config){
		this.cb = wcmXCom.get(this.id);
		if(this.cb){
			this.cb.destroy();
			delete this.cb;
		}
		Ext.applyIf(config, {id : this.id});
		//this.cb = wcm.ComponentMgr.create(config, 'crashboard');
		this.cb = new wcm.CrashBoard(config);
		this.cb.show();
	},

	hide : function(){
		if(this.cb){
			this.cb.hide();
		}else{
			window.close();
		}
	},
	
	close : function(){
		if(this.cb){
			this.cb.close();
		}else{
			window.close();
		}
	}
});