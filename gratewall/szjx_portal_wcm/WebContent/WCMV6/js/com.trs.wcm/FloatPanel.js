var myActualTop = (top.actualTop||top);
if(myActualTop==self){
	$import('com.trs.wcm.FloatPanelTop');
}
else if(myActualTop.FloatPanel){
	var FloatPanel = myActualTop.FloatPanel;
}
else {
	alert("top frame must import com.trs.wcm.MessageCenter or com.trs.wcm.FloatPanel");
}