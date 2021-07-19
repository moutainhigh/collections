//dialog适配器
var dialogInit = function(){
	if(!window.$MsgCenter) return;

	var acutalTop = $MsgCenter.getActualTop();

	if(acutalTop == window || !acutalTop.wcm.MessageBox) return;

	/**
	 * @class wcm.MessageBox
	*/
	wcm.MessageBox = acutalTop.wcm.MessageBox;

	/**
	 * @class wcm.FaultDialog
	*/
	wcm.FaultDialog = acutalTop.wcm.FaultDialog;

	/**
	 * @class wcm.ReportDialog
	*/
	wcm.ReportDialog = acutalTop.wcm.ReportDialog;

	//wcm.LANG = acutalTop.wcm.LANG;
};
dialogInit();