// define for cb
function $v(json, xp, bCase){
	var rst = json, arrXp;
	if(!xp)return rst;
	try{
		xp = bCase ? xp.trim() : xp.trim().toUpperCase();
		arrXp = xp.split('.');
		for(var i=0; i<arrXp.length; i++){
			rst = rst[arrXp[i]];
		}
	}catch(err){
		return null;
	}
	return rst==null ? null : (rst['NODEVALUE'] || rst);
}
function init(_json) {
	var result = $v(_json, 'Report.IS_SUCCESS') || '';
	var bSuccess = result.trim().toLowerCase() == 'true';
	var sTitle = String.format('当前编辑的模板 - {0}', $v(_json, 'Report.TITLE'));
	if(bSuccess) {
		Element.update($('spTitle'), sTitle);
		$('spTitle').style.color = 'green';
		Element.hide('divErrInfo');

		$('imClue').src = WCMConstants.WCM6_PATH+'js/source/wcmlib/dialog/resource/2.gif';
	}else{
		var sErrInfo = $v(_json, 'Report.ERROR_INFO');
		Element.update($('spTitle'), sTitle);
		$('spTitle').style.color = 'red';
		$('txtErrInfo').value = sErrInfo || '';
		Element.show('divErrInfo');

		$('imClue').src = WCMConstants.WCM6_PATH+'js/source/wcmlib/dialog/resource/4.gif';
	}

}