function trans2Text(_sHtml){
	var oDiv = document.createElement('DIV');
	oDiv.innerText = _sHtml;
	var sText = oDiv.innerHTML;
	delete oDiv;
	return sText;
}