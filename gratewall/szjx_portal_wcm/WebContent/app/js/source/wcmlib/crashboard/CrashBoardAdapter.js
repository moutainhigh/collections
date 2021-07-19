//CrashBoarder适配器

Ext.apply(wcm.CrashBoard.prototype, {
	getWin0 : function(){
		if(!window.$MsgCenter) return window;
		var acutalTop = $MsgCenter.getActualTop();
		if(acutalTop == window || !acutalTop.wcm.CrashBoard) return window;
		return acutalTop;
	},
	isOpenByCB : function(){
		return getParameter(wcm.CrashBoard["fromcb"]) == 1;
	}
});

(function(){
	if(getParameter(wcm.CrashBoard["fromcb"]) == 1) return;
	
	var execInit = function(){
		if(!window.init) return;
		var search = location.search;
		var params = search ? search.parseQuery() : {};
		init(params);
	};

	var initCfg = function(){
		if(!window.m_cbCfg) return;
		if(m_cbCfg.btns){
			var btns = m_cbCfg.btns;
			for (var i = 0; i < btns.length; i++){
				addBtn(btns[i]);
			}
		}
	};

	var bodyOnLoad = function(){
		initCfg();
		execInit();
	};

	Event.observe(window, 'load', bodyOnLoad, false);

	var addBtn = function(btn){
		var divBtns = ensureBtnContainer();
		var cb = wcm.CrashBoarder.get(window);
		var cpyBtn = Ext.applyIf({
			renderTo : divBtns,
			scope : cb,
			cmd : btn["cmd"] ? closeWin.createInterceptor(btn["cmd"], cb) : closeWin
		}, btn);
		var btn = new wcm.Button(cpyBtn);
		btn.render();
	};

	var m_btnsId = 'buttons_container';
	var ensureBtnContainer = function(){
		var divBtns = $(m_btnsId);
		if(!divBtns){
			divBtns = document.createElement('DIV');
			divBtns.id = m_btnsId;
			divBtns.align = 'center';
			document.body.appendChild(divBtns);
		}
		return divBtns;
	};

	var closeWin = function(){
		window.close();
	};
})();