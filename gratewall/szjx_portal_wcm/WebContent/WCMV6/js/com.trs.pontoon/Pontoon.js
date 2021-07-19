var myActualTop = (top.actualTop||top);
if(self == myActualTop){
	$import('com.trs.pontoon.PontoonTop');
}
else if(myActualTop.com.trs.pontoon.Pontoon){
	Pontoon = myActualTop.Pontoon;
}
else{
	alert('top frame must import com.trs.pontoon.PontoonTop.js')
}