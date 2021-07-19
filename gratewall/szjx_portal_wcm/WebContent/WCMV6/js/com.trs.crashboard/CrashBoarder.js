$import('com.trs.logger.Logger');
$import('com.trs.drag.SimpleDragger');
$package('com.trs.crashboard');

//TODO
if((top.actualTop||top).TRSCrashBoard){
	setTRSCrashBoard();
}else{
	Event.observe((top.actualTop||top), 'load', setTRSCrashBoard);
}
function setTRSCrashBoard(){
	with(window){//保证top.actualTop||top加载完毕，否则肯能出错
		sSrcPath = '/wcm/WCMV6/js/com.trs.crashboard/src/';
		(top.actualTop||top).TRSDialogContainer.DefaultFrameUrl = sSrcPath + 'cb_blank.html';

		TRSCrashBoard = (top.actualTop||top).TRSCrashBoard;
		TRSDialogContainer = (top.actualTop||top).TRSDialogContainer;
		TRSDialog = (top.actualTop||top).TRSDialog;

		notifyParentOnFinished = (top.actualTop||top).notifyParentOnFinished;

		notifyParent2CloseMe = (top.actualTop||top).notifyParent2CloseMe;
		$MOZ = (top.actualTop||top).$MOZ;
	}

}