var myActualTop = (top.actualTop||top);
if(myActualTop==self){
	$import('com.trs.wcm.ProcessBarTop');
}
else if(myActualTop.ProcessBar){
	var ProcessBar		= myActualTop.ProcessBar;
	var $beginSimplePB	= myActualTop.$beginSimplePB;
	var $endSimplePB	= myActualTop.$endSimplePB;
}
else {
	alert("top frame must import com.trs.wcm.MessageCenter or com.trs.wcm.ProcessBar");
}

