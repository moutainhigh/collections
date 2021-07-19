var ProcessBar = {
	init : function(_sTitle){
		$('processbar').contentWindow.init(_sTitle||'系统进度执行中,请稍候...');
	},
	addState : function(_sState){
		$('processbar').contentWindow.addState(_sState);
	},
	start : function(){
		ProcessBar.open();
		$('processbar').contentWindow.start();
	},
	next : function(){
		$('processbar').contentWindow.next(this.exit);
	},
	exit : function(){
		ProcessBar.close();
	},
	isProcessing : function(){
		return $('processbar').contentWindow.processing;
	},
	open : function(){
		//
		if(window.RotatingBar && RotatingBar.visible()) {
			//setTimeout(function(){
				if(window.showCoverAll)showCoverAll(997);
				var ePanel = $('processbar');
				ePanel.style.zIndex = 998;
				ePanel.style.display = '';				
			//}, 100);
			RotatingBar.stop();
			return;
		}
		
		if(window.showCoverAll)showCoverAll(997);
		var ePanel = $('processbar');
//		ePanel.parentNode.appendChild(ePanel);
		ePanel.style.position = 'absolute';
		ePanel.style.zIndex = 998;
		ePanel.style.display = '';
	},
	close : function(){
		this.elipse = (new Date().getTime() - $('processbar').contentWindow.startTime) / 1000;
		$('processbar').contentWindow.exit();
		var ePanel = $('processbar');
		ePanel.style.display = 'none';
		if(window.hideCoverAll)hideCoverAll();

		//ge gfc add @ 2007-5-31 20:09 隐藏系统加载提示
		if(!window.m_bMainPageLoaded) {
			window.m_bMainPageLoaded = true;
			setTimeout(function(){
				try{
					window.hidePageLoading();		
				}catch(err){
					//just skip it
				}
			}, 10);
		}
	}
};
Event.observe(window,'load',function(){
	if(!$('processbar')){
		var pbIframe = document.createElement('IFRAME');
		pbIframe.id = 'processbar';
		pbIframe.frameBorder = 0;
		pbIframe.src = com.trs.util.Common.BASE + '../include/processbar.html';
		pbIframe.style.cssText = "position:absolute;left:0;top:0;width:100%;height:100%;display:none";
		pbIframe.allowTransparency = true;
		document.body.appendChild(pbIframe);
	}
});
function Wcm_ProcessBar(){
	alert('不建议再使用,正准备移除.目前仍可用.')
	Object.extend(this,ProcessBar);
//	return ProcessBar;
}
var ModalProcessBar = Class.create();
Object.extend(ModalProcessBar.prototype,ProcessBar);
Object.extend(ModalProcessBar.prototype,{
	initialize : function(_sOperName){
		this.init();
		this.addState('操作"'+_sOperName+'"正在执行.');
		this.addState('执行完成.');
		this.start();
	}
});


function $beginSimplePB(_sClue, _nEmTime){
	if($('processbar') == null 
		|| $('processbar').contentWindow == null 
		|| $('processbar').contentWindow.init == null) {
		return;
	}

	ProcessBar.init('进度执行中，请稍候...');
	ProcessBar.addState(_sClue, _nEmTime || 1);
	ProcessBar.addState('完成!');
	ProcessBar.start();
}
function $endSimplePB(){
	if($('processbar') == null 
		|| $('processbar').contentWindow == null 
		|| $('processbar').contentWindow.init == null) {
		return;
	}

	ProcessBar.exit();
}