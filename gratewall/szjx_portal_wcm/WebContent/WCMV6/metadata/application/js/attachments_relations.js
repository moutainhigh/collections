/*---------------------------------Crash Board Relative Start-----------------------------------------**/
function onOk(){
    try{
		var targetWin = top.actualTop;
		if(!targetWin){
			alert("竟然没有top.actualTop");
			return;
		}
		targetWin.parent.notifyParentOnFinished(targetWin.document.FRAME_NAME, window[targetWin.ObjType]);
		targetWin.parent.notifyParent2CloseMe(targetWin.document.FRAME_NAME);
    }catch(error){
        //防止选择树iframe没有加载完成就单击确定按钮而导致脚本错误
		alert(error.message);
    }
}

function onCancel(){
    try{
		var targetWin = top.actualTop;
		if(!targetWin){
			alert("竟然没有top.actualTop");
			return;
		}
        targetWin.parent.notifyParent2CloseMe(targetWin.document.FRAME_NAME);
    }catch(error){
        //防止选择树iframe没有加载完成就单击确定按钮而导致脚本错误
		//alert(error.message);
    }
}
/*---------------------------------Crash Board Relative End-----------------------------------------**/