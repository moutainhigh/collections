var m_FieldHelper = {
	treeWin : function(){
		var frm = $('fields_tree');
		if(frm && frm.contentWindow)return frm.contentWindow;
	},
	_GetEle : function(el, trsTmpId, bWin){
		if(el && typeof el != 'string')return el;
		var cnt = $('infoview'), oWin;
		var ifrms = cnt.getElementsByTagName('IFRAME');
		for(var i=0,n=ifrms.length;i<n;i++){
			try{
				oWin = ifrms[i].contentWindow;
				el = oWin.$(el) || oWin.$(trsTmpId);
				if(el)return !bWin ? el : {el:el, win:oWin};
			}catch(err){}
		}
		return null;
	},
	xPath2Name : function(xp){
		if(xp==null)return'';
		var axp = xp.split('/');
		for(var i=0,n=axp.length;i<n;i++){
			axp[i] = axp[i].replace(/^[^:]*?:/,'');
		}
		return axp.join('_');
	},
	isPublishable : function(el, trsTmpId){
		el = this._GetEle(el, trsTmpId);
		if(!el)return;
		return el.getAttribute("trs_obj_publish",2)!=0;
	},
	isWriteable : function(el, trsTmpId){
		el = this._GetEle(el, trsTmpId);
		return el.getAttribute("trs_readonly_field", 2)!=1;
	},
	isBackWriteable : function(el, trsTmpId){
		el = this._GetEle(el, trsTmpId);
		return el.getAttribute("trs_backreadonly_field", 2)!=1;	
	},
	isFrontWriteable : function(el, trsTmpId){
		el = this._GetEle(el, trsTmpId);
		return el.getAttribute("trs_readonly_field", 2)!=1;	
	},
	renderPower : function(trsTmpId, type, enable){
		this.renderFrmPower(trsTmpId, type, enable);
		this.renderTreePower(trsTmpId, type, enable);
	},
	renderFrmAllPower : function(){
		var cnt = $('infoview'), oWin;
		var ifrms = cnt.getElementsByTagName('IFRAME');
		for(var i=0,n=ifrms.length;i<n;i++){
			try{
				oWin = ifrms[i].contentWindow;
				oWin.location.reload();
			}catch(err){}
		}
	},
	renderFrmPower : function(trsTmpId, type, enable){
		if(type=='write'){
			var o = this._GetEle(null, trsTmpId, true);
			var el = o.el;
			el.setAttribute("trs_readonly_field", enable?0:1);
			o.win._TransRule_.initPower(el);
		}
		else if(type=='publish'){
			var o = this._GetEle(null, trsTmpId, true);
			var el = o.el;
			el.setAttribute("trs_obj_publish", enable?1:0);
			o.win._TransRule_.initPower(el);
		}
	},
	renderTreePower : function(trsTmpId, type, enable){
		var oTreeWin = this.treeWin();
		if(!oTreeWin || !oTreeWin.PowerHelper)return;
		oTreeWin.PowerHelper.renderPower(trsTmpId, 0, type, enable);
	} 
};