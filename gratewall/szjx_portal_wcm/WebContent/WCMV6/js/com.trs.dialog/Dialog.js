var myActualTop = (top.actualTop||top);
var useWCMDialog = true;
if(self==myActualTop){
	if(useWCMDialog){
		$import('com.trs.dialog.wcmdialog.WCMDialog');
	}else{
		$import('com.trs.dialog.DialogTop');
	}
	$import('com.trs.dialog.FaultDialog');
	$import('com.trs.dialog.ReportsDialog');
}
else if(myActualTop.$dialog){
	$dialog = myActualTop.$dialog;
	$package('com.trs.dialog');
	com.trs.dialog.imgPath = myActualTop.com.trs.dialog.imgPath;
	$dialogElement = myActualTop.$dialogElement;
	$custom = myActualTop.$custom;
	$errorMsg = myActualTop.$errorMsg;
	__getDialogIcon = myActualTop.__getDialogIcon;
	$timeAlert = myActualTop.$timeAlert;
	$alert = myActualTop.$alert;
	$wait = myActualTop.$wait;
	$success = myActualTop.$success;
	$confirm = myActualTop.$confirm;
	$fail = myActualTop.$fail;
	FaultDialog = myActualTop.FaultDialog;
	ReportsDialog = myActualTop.ReportsDialog;
	TransformableDialog = myActualTop.TransformableDialog;
	$button = myActualTop.$button;
	msgbox = myActualTop.msgbox;
}
else{
	alert('top frame must import com.trs.dialog.DialogTop.js')
}