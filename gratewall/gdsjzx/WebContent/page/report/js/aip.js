

 function login_onclick(i){ 
         
		if(i==4){
		    document.all.HWPostil1.Login("HWSEALDEMO",usertypevalue,65535,"111111","");
		}else{
			alert("�ǲ����û�����������ϵ������Ȩ!");
		}
		
 }
 function loadfile_onclick(){
		document.all.HWPostil1.LoadFile("");
		
 }
 function savefile_onclick(){
 			document.all.HWPostil1.SaveTo("","",0);
			
 }
 function closefile_onclick(){
 			document.all.HWPostil1.CloseDoc(1);
			
 }

function setpenwidth_onclick(){
 			document.all.HWPostil1.CurrPenWidth=-1;
			
 }
 function setcolor_onclick(){
 			document.all.HWPostil1.CurrPenColor=-1;
			
 }
 
 function setpressurelevel_onclick(){
 			document.all.HWPostil1.Pressurelevel=-1;
}
function undo_onclick(){
 			document.all.HWPostil1.Undo();
			
 }
 function redo_onclick(){
 			document.all.HWPostil1.Redo();
			
 }
 function undoall_onclick(){
 			document.all.HWPostil1.UndoAll();
			
 }
 function redoall_onclick(){
 			document.all.HWPostil1.RedoAll();
			
 }
function pagecount_onclick(){
			alert(document.all.HWPostil1.PageCount);
			
}
function pagemode_onclick(lModeType){
			document.all.HWPostil1.SetPageMode(lModeType,100);
			
} 	

function pageup_onclick(){ 
 			document.all.HWPostil1.CurrPage -= 1;
			
 }
 function pagedown_onclick(){
 		document.all.HWPostil1.CurrPage += 1;
		
 }

function showtoolbar_onclick(bShow){
			document.all.HWPostil1.ShowToolBar=bShow;
			
} 
function showmenu_onclick(bShow){
			document.all.HWPostil1.ShowDefMenu=bShow;
			
} 
function showbar_onclick(bShow){
			
			document.all.HWPostil1.ShowScrollBarButton=bShow;
			
} 
function ctrlmenu_onclick(){
			var values=0;
			document.all.HWPostil1.HideMenuItem(values);
			var filemenu=document.all.filemenu;
			var editmenu=document.all.editmenu;
			var viewmenu=document.all.viewmenu;
			var ctrlmenu=document.all.ctrlmenu;
			var usermenu=document.all.usermenu;
			var setmenu=document.all.setmenu;
			var closemenu=document.all.closemenu;
			var savemenu=document.all.savemenu;
			var printmenu=document.all.printmenu;
			if(filemenu.checked==true){
				values |= parseInt(filemenu.value);				
			}if(editmenu.checked==true){		
				values |= parseInt(editmenu.value);		
			}if(viewmenu.checked==true){
				values |= parseInt(viewmenu.value);
			}if(ctrlmenu.checked==true){
				values |= parseInt(ctrlmenu.value);
			}if(usermenu.checked==true){
				values |= parseInt(usermenu.value);
			}if(setmenu.checked==true){
				values |= parseInt(setmenu.value);
			}if(closemenu.checked==true){
				values |= parseInt(closemenu.value);
			}if(savemenu.checked==true){
				values |= parseInt(savemenu.value);
			}if(printmenu.checked==true){
				values |= parseInt(printmenu.value);
			} 
			document.all.HWPostil1.HideMenuItem(values);
				
}

function showfullscreen_onclick(){
			document.all.HWPostil1.ShowFullScreen = 1;
			
}
function searchtext_onclick(){
			document.all.HWPostil1.SearchText("",0,0);
			
}
function showview_onclick(){
			document.all.HWPostil1.ShowView |= 0x04;
			
}
function hideview_onclick(){
			document.all.HWPostil1.ShowView &=  0xfb;
			
}

function GetNowDateTime()
 {
    var nowTime = new Date();
    var NowDateTime = nowTime.getFullYear() + "��" + 
                        (nowTime.getMonth() + 1 )+ "��" + 
                        nowTime.getDate() + "��"  + 
                        " " +
                        nowTime.getHours() + ":" +
                        nowTime.getMinutes() + ":" +
                        nowTime.getSeconds() + "." +
                        nowTime.getMilliseconds();
   var NowDateTime = nowTime.toLocaleString();
   return NowDateTime;
 }
//���һ���¼���¼
var vLastEvent;
//���������ʾ�¼�
function AIP_Event_Flash(EventName)
{
	var vValue = "�¼�:"+EventName+"\t\t������:" +GetNowDateTime() + "\r\n"+document.all.commandtext.value;
	document.all.commandtext.value = vValue;
}

function HWPostil1_NotifyBeforeAction(lActionType,lType,strName,plContinue) {
	AIP_Event_Flash("NotifyBeforeAction");
  if(1==lActionType){
    spnWorkingMsg.innerText  =  "�¼�����ӡ�ĵ�����ӡ��Ϊ:"+strName;
  }else if(2==lActionType){
  	spnWorkingMsg.innerText  =  "�¼���ɾ���˵�[ "+(lType+1)+" ]ҳ";
  }else if(3==lActionType){
  	spnWorkingMsg.innerText  =  "�¼���ɾ���ڵ�\t �ڵ�����Ϊ��"+lType+"\t�ڵ�����Ϊ:" + strName;
  }
}
function HWPostil1_NotifyChangeCurrUser() {
	AIP_Event_Flash("NotifyChangeCurrUser"); 
	spnWorkingMsg.innerText  =  "�����¼���NotifyChangeCurrUser"
}

function HWPostil1_NotifyChangePage() {
	AIP_Event_Flash("NotifyChangePage");
	spnWorkingMsg.innerText  =  "�����¼���NotifyChangePage"
}

function HWPostil1_NotifyChangePenColor() {
	AIP_Event_Flash("NotifyChangePenColor");
	spnWorkingMsg.innerText  =  "�����¼���NotifyChangePenColor"
}

function HWPostil1_NotifyChangePenWidth() {
	AIP_Event_Flash("NotifyChangePenWidth");
	spnWorkingMsg.innerText  =  "�����¼���NotifyChangePenWidth"
}

function HWPostil1_NotifyChangeValue(pcName,pcNewValue) {
	AIP_Event_Flash("NotifyChangeValue");
	spnWorkingMsg.innerText  =  "�����¼���NotifyChangeValue" + "\t����:pcName = "+pcName+"\tpcNewValue = "+pcNewValue;
}

function HWPostil1_NotifyClick(pcName) {
	AIP_Event_Flash("NotifyClick");
	spnWorkingMsg.innerText  =  "�����¼���NotifyClick" + "\t����:pcName = "+pcName;
}

function HWPostil1_NotifyCloseDoc() {
	AIP_Event_Flash("NotifyCloseDoc");
	spnWorkingMsg.innerText  =  "�����¼���NotifyCloseDoc";
}

function HWPostil1_NotifyDocOpened(lOpenResult) {
	AIP_Event_Flash("NotifyDocOpened");
	spnWorkingMsg.innerText  =  "�����¼���NotifyDocOpened" + "\t����:lOpenResult = "+lOpenResult;
}

function HWPostil1_NotifyFullScreen() {
	AIP_Event_Flash("NotifyFullScreen");
	spnWorkingMsg.innerText  =  "�����¼���NotifyFullScreen";
}

function HWPostil1_NotifyMenuMsg(lCmd) {
	AIP_Event_Flash("NotifyMenuMsg");
	spnWorkingMsg.innerText  =  "�����¼���NotifyMenuMsg" + "\t����:lCmd = "+lCmd;
}

function HWPostil1_NotifyModifyStatus() {
	AIP_Event_Flash("NotifyModifyStatus");
	spnWorkingMsg.innerText  =  "�����¼���NotifyModifyStatus";
}

function HWPostil1_NotifyPosChange(pcNoteName) {
	AIP_Event_Flash("NotifyPosChange");
	spnWorkingMsg.innerText  =  "�����¼���NotifyPosChange" + "\t����:pcNoteName = "+pcNoteName;
}

function HWPostil1_NotifyReset(pcName) {
	AIP_Event_Flash("NotifyReset");
	spnWorkingMsg.innerText  =  "�����¼���NotifyReset" + "\t����:pcName = "+pcName;
}

function HWPostil1_NotifySelect(pcName,lNoteType) {
	AIP_Event_Flash("NotifySelect");
	spnWorkingMsg.innerText  =  "�����¼���NotifySelect" + "\t����:pcName = "+pcName+"\tlNoteType = "+lNoteType;
}

function HWPostil1_NotifyStatusChanged(lStatus) {
	AIP_Event_Flash("NotifyStatusChanged");
	spnWorkingMsg.innerText  =  "�����¼���NotifyStatusChanged" + "\t����:lStatus = "+lStatus;
}

function HWPostil1_NotifySumbit(pcName) {
	AIP_Event_Flash("NotifySumbit");
	spnWorkingMsg.innerText  =  "�����¼���NotifySumbit" + "\t����:pcName = "+pcName;
}
	