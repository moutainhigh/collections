function $(id){
	return document.getElementById(id)
}
function showDetail(){
	if ($('previousSpaceSet').getAttribute('disabled')){
		$('previousSpaceSet').removeAttribute('disabled');
		$('nextSpaceSet').removeAttribute('disabled');
	}
	var els = document.getElementsByTagName('p');
	for (var i=0;i<els.length ;i++ ){
		els[i].style.marginTop = '1em';
	}
}
function hideDetail(){
	var els = document.getElementsByTagName('p');
	for (var i=0;i<els.length ;i++ ){
		els[i].style.marginTop = 0;
	}
		$('previousSpaceSet').value = 1;
		$('previousSpaceSet').setAttribute('disabled','true');
		$('nextSpaceSet').value = 0;
		$('nextSpaceSet').setAttribute('disabled','true');
}
function previousSpaceHandle(a){
	var els = document.getElementsByTagName('p');
	for (var i=0;i<els.length ;i++ ){
		els[i].style.marginTop = a+'em';
	}
}
function nextSpaceHandle(a){
	var els = document.getElementsByTagName('p');
	for (var i=0;i<els.length ;i++ ){
		els[i].style.marginBottom = a+'em';
	}
}
function lineSpaceHandle(a){
	$('preview').style.lineHeight = a;
}
function lineIndentHandle(a){
	var els = document.getElementsByTagName('p');
	var b;
	switch (a)
	{
	case 'normal':
		b=0;
		break;
	case 'one':
		b=1;
		break;
	case 'two':
		b=2;
		break;
	case 'three':
		b=3;
		break;
	case 'four':
		b=4;
		break;
	}
	for (var i=0;i<els.length ;i++ ){
		els[i].style.textIndent= b+'em';
	}
}
function fontSizeHandle(a){
	$('preview').style.fontSize = a+'px';
}
