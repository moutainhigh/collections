
function ReportView(name,PacketUrl,top,left,width,height,parent)   //���캯��
{
 var pdiv,obj=document.createElement('Div');//����һ�����ƽڵ�
  if(parent){pdiv=parent;}else pdiv=document.body;//��ȡ����
  pdiv.appendChild(obj);
// obj.style.overflow = 'hidden';
 //obj.style.position='absolute';
  if(left) obj.style.left=left;
  if(top) obj.style.top=top;
  if(width) obj.style.width=width;
 // if(!height)  height=500;
  if(height) obj.style.height=height;
  obj.className='tab-pane';
  if(name){var s='window.'+name+'=obj';eval(s);}


  window.moveTo(0,0)
  window.resizeTo(screen.availWidth,screen.availHeight)
  //��������
  obj.name =name;
  obj.divHeight=height;
  obj.pageSize='a4';
  obj.pageOrient='0';
  obj.UserInfo= new Array();
  obj.curReport;


  obj.isLoadSY=0;
  obj.isLoadSJ=0;
  obj.isLoadSN=0;

  obj.stPacketUrl =PacketUrl;    
  obj.dataPacketUrl =null;    //��ṹ��Url

  obj.openDataUrl =null;       //���ݶ�ȡ��JSPҳ��ָ��
  obj.postDataUrl =null;       //�����ύ��JSPҳ��ָ��

  obj.stPacketNode =null;      //��ṹ���ڵ�
  obj.dataPacketNode = null ;  //�����ݰ��ڵ�
  obj.newDataPacketNode =null;  //�µ����ݰ�
  obj.dataQueryNode =null;
  obj.readOnly=false;      //����ֻ�������ģʽ
  obj.clearReportsData= SC_cleareportsrdata;   //�屨�����������

  obj.popupMenu = new menu();
  obj.popupMenu.add("��","js", "SetSkinColor(1);");
  obj.popupMenu.add("��","js","SetSkinColor(2);");
  obj.popupMenu.add("��","js","SetSkinColor(3);");
  obj.popupMenu.add("��","js","SetSkinColor(4);");

  obj.colorMenu = new menu();
  obj.colorMenu.add("��","js", "SetSkinColor(1);");
  obj.colorMenu.add("��","js","SetSkinColor(2);");
  obj.colorMenu.add("��","js","SetSkinColor(3);");
  obj.colorMenu.add("��","js","SetSkinColor(4);");

  obj.checkMenu = new menu();
  obj.checkinfoMenu = new menu();
  obj.printMenu = new menu();
  obj.printSetupMenu = new menu();
  obj.infoMenu = new menu();
  obj.dataMenu = new menu();
  obj.sendMenu = new menu();
  obj.histroyMenu = new menu();
  obj.gridMenu = new menu();
  obj.infoMenu.add("�鿴��Ϣ","js","tabpane.setSelectedIndex(0)");
  obj.infoMenu.add("�����Ϣ","js","ClearInfo()");
  obj.infoMenu.add("��ӡ��Ϣ","js","PrintInfo()");


  obj.histroyMenu.add("��������","js" ,"statsrptobj.openUnitData('sy');");
  obj.histroyMenu.add("�ϼ�����","js" ,"statsrptobj.openUnitData('sj');");
  obj.histroyMenu.add("��������","js" ,"statsrptobj.openUnitData('sn');");

  obj.dataMenu.add("�������ݰ�","js","statsrptobj.createDataPacket()");
//  obj.dataMenu.add("�������ݰ�","js","statsrptobj.dataPacketUrl=window.prompt();statsrptobj.getdataPacket()");
  obj.dataMenu.add("�鿴���ݰ�","js" ,"alert(statsrptobj.newDataPacketNode.xml)");
  obj.sendMenu.add("���ݱ���","js" ,"statsrptobj.postUnitData()");
  obj.sendMenu.add("�����������˳�","js","x=window.confirm('���ύ�����˳�');if(x==true) window.close();");
  obj.dataMenu.add("�������","js" ,"statsrptobj.clearReportsData();");
  obj.dataMenu.add("д���ݵ�����","js" ,"statsrptobj.createDataPacket();var file=window.prompt('�ļ���'); writeFile(file,statsrptobj.newDataPacketNode.xml)");
 // obj.dataMenu.add("�ӱ��ض�����","js" ,"statsrptobj.dataPacketUrl=window.prompt('�ļ���');statsrptobj.getdataPacket()");
//obj.dataMenu.add("дbody","js" ,"writeBody()");
 // obj.dataMenu.add("���ݰ�����","js" ,"statsrptobj.openUnitData()");
 obj.printSetupMenu.add("A4","js","statsrptobj.pageSize='a4';");
 obj.printSetupMenu.add("A3","js","statsrptobj.pageSize='a3';");
 obj.printSetupMenu.add("�����ӡ","js","statsrptobj.pageOrient='0';");
 obj.printSetupMenu.add("�����ӡ","js","statsrptobj.pageOrient='1';;");
 obj.gridMenu.add("������","js","statsrptobj.curReport._appendZlRow();");
 obj.gridMenu.add("ɾ����ǰ��","js", "statsrptobj.curReport._deleteZlRow(statsrptobj.curReport.focusRow);");
/*

 obj.menuBar = new menu_bar(top-28,left,width);
 obj.menuBar.add("��������",obj.fontMenu) ;
 obj.menuBar.add("����¼��",obj.popupMenu) ;
 obj.menuBar.add("�������",obj.checkMenu) ;
 obj.menuBar.add("�����Ϣ",obj.checkinfoMenu) ;
 obj.menuBar.add("��˲�����",obj.sendMenu) ;
 obj.menuBar.add("������",obj.gridMenu);
 obj.menuBar.add("������Ϣ",obj.dataMenu) ;
 obj.menuBar.add("�����ӡ",obj.printMenu) ;
 obj.menuBar.add("��ӡҳ������",obj.printSetupMenu) ;
 obj.menuBar.add("��ɫ����",obj.colorMenu) ;
 obj.menuBar.add("��Ϣ����",obj.infoMenu) ;
 obj.menuBar.add("������ʷ����",obj.histroyMenu) ;

*/
  obj.tabPane =null ;   //��ǩҳָ��
  obj.readOnly= true;

  obj.reportCount = 0;     //�����
  obj.reportCode =new Array() ;  //�����
  obj.reportType =new Array();  //������
  obj.reportPtr = new Array() ; //���ָ��
  obj.reportFirst = new Array() ; //���������С����������
//���з���

  obj.oncontextmenu=SC_oncontextmenu;   //�����Ĳ˵�
  obj.setCssStyle = SC_setcssstyle;

  obj.addTabPage = SC_addtabpage;
 // obj.addTabPageIdx = SC_addtabpageidx;
  obj.Refresh = SC_refresh;
  obj.switchReport =SC_switchreport; //(name)

//  obj.addReport = SC_addreport;

  obj.getstPacket =SC_getstpacket;     //����ṹ������ṹ���ڵ�
  obj.getdataPacket =SC_getdatapacket; //�������ݰ��������ݰ��ڵ�
  obj.openUnitData =SC_openunitdata;     //��ĳ��λ�����ݰ�
  obj.openUnitHistoryData =SC_openunithistorydata;     //��ĳ��λ�����ݰ�
  obj.LoadHistoryData = SC_loadhistorydata
  obj.postUnitData =SC_postunitdata;     //�ύ���ݰ�

 //////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// h y h //////////////////////////////////////////////////
  obj.openStPacketUrl =null;       //�ṹ��ȡ��JSPҳ��ָ��
  obj.createStPacketQuery =SC_createstpacketquery;  //������ṹ����ȡ��XML�ڵ�
  obj.openUnitStPacket =SC_openunitstpacket;     //��ĳ��λ�Ľṹ��
  obj.stPacketQueryNode =null;
////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////



  obj.createInfoDiv=SC_createinfodiv;     //������ʾ��Ϣ����

  //˽�з���
  obj._initAllReportView = SC_initallreportview;   //
  obj._initOneReportView = SC_initonereportview;

  obj.tabPane= new WebFXTabPane (obj);
  tabpane = obj.tabPane;
  obj.onmousedown = function() {
	  if(event.button==2) this.popupMenu.show(event.clientX,event.clientY);
      else if(event.button==1) this.popupMenu.hide();
  }
  obj.createInfoDiv('info');
  statsrptobj= obj;
  obj.getstPacket();

  return obj;
}
function SC_setcssstyle (csstext)
{
this.style.cssText=csstext;



}
function SC_oncontextmenu()
{
	var popupmenu=this.popupMenu;
	if(popupmenu) popupmenu.show(event.clientX,event.clientY);
    event.returnValue = false;
    event.cancelBubble=true;
}
function SC_createinfodiv(name)
{
/* var obj=document.createElement('Div');//����һ�����ƽڵ�
 obj.style.overflow = 'auto';
 this.appendChild(obj);
 obj.style.zIndex=1200;
 if(name){var s='window.'+name+'=obj';eval(s);}
 this.reportPtr['info'] =obj;
 this.addTabPage(obj,"��Ϣ����","����ִ�н������Ϣ����");   //����Ϣ������ӵ�tabpane��
*/
}


function AddInfo(info)
	{
/*	 var now =new Date();
	 var nowStr=DateTimeToStr(now)
	 window.info.innerHTML += '<br><font color=#0000d0>'+nowStr+' </font>';
	 window.info.innerHTML += info;
	 window.status =info;
     tabpane.setSelectedIndex(0);
     window.info.focus();
	*/}
function ClearInfo()
	{
	// window.info.innerHTML = '';

	}

function PrintInfo()
{
var odoc=window.info.innerHTML;
var pwin=window.open("","��Ϣ��ӡ");
pwin.document.write("<body>");
pwin.document.write(odoc);
var OLECMDID = 6; //6
var PROMPT = 1; // 2 DONTPROMPTUSER
var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
//pwin.document.body.insertAdjacentHTML('beforeEnd', WebBrowser);
pwin.document.write(WebBrowser);
pwin.document.write("</body>");
pwin.document.WebBrowser1.ExecWB(OLECMDID, PROMPT);
//pwin.document.WebBrowser1.outerHTML = "";
}

 //��tabpane�м��� tabpage
function SC_addtabpage(name,nameTag,nameTips)
{
  name.className='tab-page';
 name.style.height=this.divHeight;
 name.style.overflow = 'auto';

  var title = document.createElement( 'span' );
// var title = document.createElement( 'h2' );
  title.innerText =nameTag;
  title.className='tab';
  title.setAttribute("tooltip",nameTips);
  name.appendChild(title);
  name.style.backgroundColor =bgcolor;
 // name.style.padding='5px'

  this.tabPane.addTabPage( name ,nameTips);
 // name.focus();

}
function SC_refresh()
{
}
function SC_switchreport(name)
{
  if(name=='��Ϣ����'||name=='��Ŀ��Ϣ') return;
	this.curReport=this.reportPtr[name];
		 this.curReport.Refresh();
	      currptobj =this.curReport;
		 currptname =name;
	  // alert(currptname);

}

//�������ݰ��������ݰ��ڵ�
function SC_getdatapacket()
{
 AddInfo("��ʼ���ر����ݰ�.....");
 if(!this.dataPacketUrl){ return -1;}
try{
  //���� XML �ļ�
  var xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
  xmlDoc.async = false ;
  xmlDoc.load(this.dataPacketUrl);

  if(!xmlDoc)throw this.Name+Err_NoURL;
  if(xmlDoc.parseError.errorCode!=0||!xmlDoc.xml){throw this.Name+Err_NoURL;}
  var msg=xmlDoc.documentElement;
  var errorcode=msg.getAttribute("errorcode");
  if(errorcode!='0'&&errorcode!=null){throw this.Name+':'+msg.getAttribute("message");}
  if (xmlDoc){this.dataPacketNode=xmlDoc;}
  this._initAllReportData();

 }
  catch(e)
  {
	 StatsReport_err(e);
  }
 AddInfo("�������ݰ�.....�ɹ���");

}
/////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////// h y h ///////////////////////////////////////////////////
function SC_createstpacketquery(){
 var  a=this.stPacketQueryNode;
 if(a!=null && a.documentElement) a.removeChild(a.documentElement);
 this.stPacketQueryNode=new ActiveXObject('Microsoft.XMLDOM');
 a=this.stPacketQueryNode;
 var n = a.createProcessingInstruction("xml"," version=\"1.0\" encoding=\"gb2312\"");
  a.insertBefore(n,a.firstChild);
   el=a.createElement('query');
   a.appendChild(el);
   var root=el;
    el=a.createElement('frameid');
	 el.setAttribute('value',window.document.forms[0].frameid.value);
    root.appendChild(el);
    el=a.createElement('time');
	 el.setAttribute('value',window.document.forms[0].time.value);
    root.appendChild(el);
//    alert(this.stPacketQueryNode.xml);
    return root;
}

function SC_createdataquery(timetype){
 var  a=this.dataQueryNode;
 if(a!=null && a.documentElement) a.removeChild(a.documentElement);

 this.dataQueryNode=new ActiveXObject('Microsoft.XMLDOM');
 a=this.dataQueryNode;
 var n = a.createProcessingInstruction("xml"," version=\"1.0\" encoding=\"gb2312\"");
  a.insertBefore(n,a.firstChild);

   el=a.createElement('query');
   a.appendChild(el);
   var root=el;
    el=a.createElement('frameid');
	 el.setAttribute('value',window.document.forms[0].frameid.value);
    root.appendChild(el);
    el=a.createElement('userid');
	 el.setAttribute('value',window.document.forms[0].userid.value);
    root.appendChild(el);
    el=a.createElement('time');
	 el.setAttribute('value',window.document.forms[0].time.value);
    root.appendChild(el);
    el=a.createElement('itemid');
	 el.setAttribute('value',window.document.forms[0].itemid.value);
    root.appendChild(el);
    el=a.createElement('timeid');
	 el.setAttribute('value',window.document.forms[0].timeid.value);
    root.appendChild(el);
    el=a.createElement('timetype');
	 el.setAttribute('value',timetype);
    root.appendChild(el);
//    alert(this.dataQueryNode.xml);
    return root;
}

function SC_openunitstpacket()     //��ĳ��λ�Ľṹ��
{


  this.createStPacketQuery();
  if(this.openStPacketUrl==null)
	{
	  AddInfo("<font color=#f00000>û���ҵ�����ȡ����ִ��ҳ�棡</font>");
	  return ;
	}
  var httpob = new ActiveXObject("Microsoft.XMLHTTP");
  httpob.Open("post",this.openStPacketUrl, false);
  httpob.send(this.stPacketQueryNode);
  var xmlDoc=httpob.responseXML;
  if(!xmlDoc) {
	  AddInfo("<font color=#f00000>��ȡ����ִ��ʧ�ܣ�</font>");
	  return;
  }
  if(xmlDoc.parseError.errorCode!=0){
	  AddInfo("<font color=#f00000>��ȡ�����д���</font>");
	  return;
  }
  if (xmlDoc){
	  this.stPacketNode=xmlDoc;
      this._initAllReportView();
  }
}
///////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////

function SC_openunitdata(timetype)     //��ĳ��λ�����ݰ�
{
  if(timetype==null) timetype='dq';

  this.createDataQuery(timetype);
  if(this.openDataUrl==null)
	{
	  AddInfo("<font color=#f00000>û���ҵ�����ȡ����ִ��ҳ�棡</font>");
	  return ;
	}
  var httpob = new ActiveXObject("Microsoft.XMLHTTP");
  httpob.Open("post",this.openDataUrl, false);
  httpob.send(this.dataQueryNode);
  var xmlDoc=httpob.responseXML;
  if(!xmlDoc) {
	  AddInfo("<font color=#f00000>��ȡ����ִ��ʧ�ܣ�</font>");
	  return;
  }
  if(xmlDoc.parseError.errorCode!=0){
	  AddInfo("<font color=#f00000>��ȡ�����д���</font>");
	  return;
  }

  if (xmlDoc){
	  this.dataPacketNode=xmlDoc;
      this._initAllReportData();
  }


}

function SC_openunithistorydata(timetype)     //��ĳ��λ�����ݰ�
{
  if(timetype==null) timetype='dq';

  this.createDataQuery(timetype);
  if(this.openDataUrl==null)
	{
	  AddInfo("<font color=#f00000>û���ҵ�����ȡ����ִ��ҳ�棡</font>");
	  return ;
	}
  var httpob = new ActiveXObject("Microsoft.XMLHTTP");
  httpob.Open("post",this.openDataUrl, false);
  httpob.send(this.dataQueryNode);
  var xmlDoc=httpob.responseXML;
  if(!xmlDoc) {
	  AddInfo("<font color=#f00000>��ȡ����ִ��ʧ�ܣ�</font>");
	  return;
  }
  if(xmlDoc.parseError.errorCode!=0){
	  AddInfo("<font color=#f00000>��ȡ�����д���</font>");
	  return;
  }

  if (xmlDoc){
	  this.dataPacketNode=xmlDoc;
	//  alert(xmlDoc.xml);
      this.LoadHistoryData(timetype);
  }


}
function  SC_postunitdata()    //�ύ���ݰ�
{
   var err=this.CheckAllReport();
   if(err!=0)
	{
	   alert("�����"+err+"������,���ܱ�������!");
	   return;
	}
   
   this.createDataPacket();  //�������ݰ�
  if(this.postDataUrl==null)
	{
	  AddInfo("<font color=#f00000>û���ҵ������ύ��ִ��ҳ�棡</font>");
	  return ;
	}
  var httpob = new ActiveXObject("Microsoft.XMLHTTP");
  httpob.Open("post",this.postDataUrl, false);
  httpob.send(this.newDataPacketNode);
  var s=httpob.responseText;
  //AddInfo(s);
  alert(s);
  window.close();
}

function SC_loadhistorydata(timetype)
{
  var root=this.dataPacketNode.documentElement;
  if(root.tagName.toUpperCase()=="REPORTS")
   var Count = root.childNodes.length;

//  this.reportCount=0;
  for(var i=0;i< Count;i++)
	{
	var node=root.childNodes.item(i);   //�õ� <report> </report>�Խڵ�����
		if(node.tagName.toUpperCase()=='REPORT')
		{
		    if(node.getAttribute("code")=='no') {
			     AddInfo("û��"+timetype+"����");
			     break;

		}else{ this._LoadOneReportData(node.getAttribute("code"),node.getAttribute("type"),node,timetype);

		}
	}

	}

}




function SC_cleareportsrdata()  //������б��������
{
  var curReport;

  for(var i=0;i< this.reportCount;i++)
	{
	    curReport=this.reportPtr[this.reportCode[i]];

	   for (var row=0; row<curReport.RowNum; row++)
	    for (var col=0;col<curReport.ColNum ; col++)
	    {
			curReport.Cells[row+1][col+1].value='';
	    }


	}

}
//����ṹ������ṹ���ڵ�
function SC_getstpacket()
{
/* if( this.readOnly==true)
	{
	 this.menuBar.item[0].disabled =true;
	 this.menuBar.item[1].disabled =true;
	 this.menuBar.item[2].disabled =true;
	 this.menuBar.item[3].disabled =true;
	 this.menuBar.item[4].disabled =true;

	}
*/
 if(!this.stPacketUrl){AddInfo(this.Name+Err_NoURL);return -1;}
 try{
  //���� XML �ļ�
  var xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
  xmlDoc.async = false ;
  xmlDoc.load(this.stPacketUrl);

  if(!xmlDoc)throw this.Name+Err_NoURL;
  if(xmlDoc.parseError.errorCode!=0||!xmlDoc.xml){throw this.Name+Err_NoURL;}
  var msg=xmlDoc.documentElement;
  var errorcode=msg.getAttribute("errorcode");
  if(errorcode!='0'&&errorcode!=null){throw this.Name+':'+msg.getAttribute("message");}
  if (xmlDoc){this.stPacketNode=xmlDoc;}
  this._initAllReportView();
 }
  catch(e)
  {
	 StatsReport_err(e);
	//AddInfo(e);
  }

}
//��ʼ����XMLDOC ������Ľṹ�����������ű�

function  SC_initallreportview()
{
/* if( this.readOnly==true)
	{
	 this.menuBar.item[0].disabled =true;
	 this.menuBar.item[1].disabled =true;
	 this.menuBar.item[2].disabled =true;

	}
 */ var root=this.stPacketNode.documentElement;
   if(root.tagName.toUpperCase()=="REPORTS")
   var Count = root.childNodes.length;
  AddInfo("��ʼ���ؽṹ��.....");

 this.reportCount=0;
  for(var i=0;i< Count;i++)
	{
	var node=root.childNodes.item(i);   //�õ� <report> </report>�Խڵ�����
		if(node.tagName.toUpperCase()=='REPORT')
		{

			this.reportCode[i]=  node.getAttribute("code");
		    this.reportType[i]=  node.getAttribute("type");
			this.reportFirst[i]= node.getAttribute("first");
		    this.reportPtr[this.reportCode[i]]=this._initOneReportView(this.reportCode[i],this.reportType[i],this.reportFirst[i],this.readOnly,node,i+1);
		   this.reportCount++;
		}

	}
 tabpane.setSelectedIndex(0);
}
//����Ļ�ϻ���
function SC_initonereportview(name,type,firist,readonly,node,idx)
{
 AddInfo("���ڼ��ر�<font color='#F00000'>"+name+"</font>�Ľṹ.....��ȴ�");
  var obj=document.createElement('Div');//����һ�����ƽڵ�
  obj.style.position = 'relative';
  obj.style.overflow = 'auto';

  this.appendChild(obj);
  obj.onscroll = DG_scrolldraw;
 if(name){var s='window.'+name+'=obj';eval(s);}
 obj.id = "report";

 // obj._appendCell = AD_appendcell;
  obj.name = name;
  obj.Cells = Array();    //������Ԫ
  obj.Cells_SY = Array();
  obj.Cells_SJ = Array();
  obj.Cells_SN = Array();
  obj.Cols = Array() ;      //������һ������
  obj.Rows = Array();       //������һ�е�����
  obj.RowNum=0;             //���������Ч����
  obj.ColNum=0 ;            //���������Ч����
  obj.RowCount ;             //������������,
  obj.focusRow;
  obj.ColLevelNum;
  obj.blBottomRow =0;
  obj.type =type;
  obj.readOnly=readonly;


  obj.zlXm = new Array();  //������������
  obj.zlDm = new Array();  //������������
  obj.zlDmIdx = new Array();   //����������������Ӧ�������
  obj.zlDw = new Array();  //����������λ
  obj.ComboBox =new Array();

  obj.blXm = new Array();
  obj.blDm = new Array();  //�����������
  obj.reportTitle  =null;   //���������
  obj.reportSubTitle  =null;//�������ͷ

  obj.pubCellWidth = new Array();  //���������沿�ֵĵ�Ԫ����
  obj.pubZlAttr =new Array();
  obj.pubBlAttr =new Array();
  obj.pubCells  = new Array();     //���������沿�ֵĵ�Ԫ��
  obj.zlCells  = new Array();
  obj.cellData  =new Array();

  obj.blCells ;                      //�����ĵ�Ԫ��
  obj.blattr =new Array();
  obj.bldec= new Array() ;
  obj.zlattr =new Array();
  obj.zldec= new Array() ;
  
  obj.checks="";
  obj.checksText ="";     //�������˱����˱��ʽ
  obj.publicsNode =null;     //���������沿�ֵĽڵ�
  obj.blsNode =null;         //�������ֵĽڵ�
  obj.zlsNode =null;          //�������ֵĽڵ�
  obj.rowsNode = null;
  obj.cellNode =null;
  obj.addsTitle ='' ; //������Ϣ����

  //obj.Rows = null;//���ñ��������
 // obj.RowsCount = 0;  //��ǰ���еļ�¼����
  //obj.StateCells = null;//���״̬��
  //obj.Cells = null;     //���еĵ�Ԫ��
  obj.Headerdiv = null;//������ͷ�Ŀ�
  obj.Header = null;//��ʾ��ͷ�ı�
  obj.Addsdiv =null;
  //obj.ColsCount=0;//�������
  //obj.Cols = null;//�����
  obj.parent = this; //���������
  obj.EditMode = null; //�༭״̬
  obj.Enabled = false; //�Ƿ����
  obj.Visible = true;  //�Ƿ�ɼ�
  obj.Bodydiv = null; //����Ŀ�
  obj.Body = null;    //���屾��,����Ҫ�Ĳ���
  obj.Table = null;  //�û�ֱ�Ӳ����Ĳ���
  obj.BlDiv =null;    //��ı�����
  obj.BlTab = null;   //��ı����� table
  obj.ZlTab = null;   //��������� table
  obj.ZlDiv =null ;   //���������
  obj.PubDiv =null;
  obj.PubTab =null;
  obj.first =first;
  obj.Id = '';        //�����Ψһ�ı�ʾ
 // obj.DataSet = null;//���ݼ�
 // obj.Fields = null;//�ֶμ�
  obj.Isinit = true;//����Ƿ�Ϊ��ʼ��
  obj.CurrentCell = null
  obj.EditErrCell = null;  //���浱ǰ�༭�������
  obj.Auto = false; //�Ƿ���ʾ���е���


  obj.HeadColor =bgcolor;//clSilver;    //�����ṹ��ɫ
  obj.isDisplayHead = true;   //�����Ƿ���ʾ���������

  obj.Show = DG_show;                //������ʾ������ʼ����ı�
    obj.Refresh = DG_refresh;          //��ṹˢ�£������ṹ���¹���
  obj.SetHeadColor =DG_setheadcolor;  //�����ṹ��ɫ
  obj.dispCellNode = function () { alert(this.cellNode.xml); }
//˽�з���
  obj._CreateBody = _DG_createbody;    //��������������
  obj._CreateHead = _DG_createhead;    //������ͷ����������
//  obj._CreateBl = _DG_createbl;    //�������������������
  obj._CreateZl = _DG_createzl;    //��������������������
  obj._CreatePub = _DG_createpub;    //��������������������
  obj._CreateAdds = _DG_createadds    //����������Ϣ

  obj._BodyShow = _DG_bodyshow;        //��ʾ����ľ�������
  obj._HeadShow = _DG_headshow;        //��ʾ��ͷ�ľ��������
 // obj._BlShow = _DG_blshow;        //��ʾ������ľ�������
  obj._ZlShow = _DG_zlshow;        //��ʾ�������ľ��������
  obj._PubShow = _DG_pubshow;        //��ʾ�������ֵľ��������
  obj._AddsShow =_DG_addsshow;
  obj._appendRow = _DG_appendrow;     //���һ������
  obj._deleteRow = _DG_deleterow;     //ɾ��һ������
  obj._appendCell = _DG_appendcell;    //���һ���յĵ�Ԫ��
  obj._appendZlCell = _DG_appendzlcell;    //���һ����Ŀ��������յĵ�Ԫ��
  obj._appendSeZlCell = _DG_appendsezlcell;    //���һ���������������յĵ�Ԫ��
  obj._appendZlRow = _DG_appendzlrow;     //���һ����������
  obj._deleteZlRow = _DG_deletezlrow;     //ɾ��һ����������

  obj._zlAndbodyRemove=_DG_zlandbodyremove;
  obj._ComputHIsView = _DG_computHisview;  //���㵱ǰ���Ƿ��ڿ�������
  obj._ComputVIsView = _DG_computVisview;  //���㵱ǰ��Ԫ���Ƿ��ڿ�������
  obj._CreateBody();
  obj._CreateZl();
 // obj._CreateHead();
  obj._CreatePub();
  obj._CreateHead();
 // obj._CreateBl();
  obj._CreateAdds();
// obj.show();
  var count = node.childNodes.length;    //�õ� <report> </report>�����ӽڵ����
 // alert(count);
  for(var i=0;i<count;i++)
	{
	  var subnode = node.childNodes.item(i);
	  switch (subnode.tagName.toUpperCase())
	  {
	   case 'TITLE' :
		    var str=[];
		str=subnode.text.split(',');
		    obj.reportTitle=str[0];
		//	alert(obj.reportTitle);

		   break;
	   case 'SUBTITLE':
		    var str=[];
		str=subnode.text.split(',');
		   obj.reportSubTitle=str[0];
	       break;
	   case 'PUBLICS':
		   obj.publicsNode=subnode;
	       break;
	   case 'BLS':
		   obj.blsNode=subnode;
	       obj.ColNum =subnode.childNodes.length;
	       break;
	   case 'ZLS':
		   obj.zlsNode=subnode;
	       if(type=='������' || type=='1')
		 obj.RowNum =subnode.childNodes.length;
		   else  {
			   obj.RowNum =0;
			    obj.RowCount= subnode.childNodes.length;
		   }
	       break;
	   case 'ROWS':
		   obj.rowsNode =subnode;
	       break;
	   case 'TEXTAREA':
			    var str=[];
			str=subnode.text.split(',');
		    obj.addsTitle=str[0];
			if(str[0]=='��')  obj.addsTitle='';
	        break;
       case 'CHECKS':
		    obj.checks=subnode.text.replace(/\n/gi,"<br>") ;
	        break;
       case 'JSCHECKS':
		    //�˴�������˹�ʽ
		obj.checksText=subnode.text.replace(/this/gi,name);
		   break;;

	  }
	}
  this.addTabPage(obj,name,'<center>'+obj.reportTitle+'</center>');   //������ӵ�tabpane��
//  this.addTabPage(obj,name,'<center>'+obj.reportTitle+'</center>');   //������ӵ�tabpane��
 obj.Show();
 return obj;
}
function CheckInfo(str)
{
 if(err==0) { checkWarningNum++;  }
 else if(err==1)  checkErrNum++;
 AddInfo(str);
}

function _DG_createbody()//��������DIV
{
   if(!this.Bodydiv){
    this.Bodydiv=document.createElement('div');
    this.Bodydiv.style.position='absolute';
	this.Bodydiv.style.zIndex =10;
    this.appendChild(this.Bodydiv);
   }
   if(!this.Body){
     this.Body = document.createElement('table');
 //     this.Body.style.top=16;
 		 this.Body.id = "body";
     this.Bodydiv.appendChild(this.Body);

   this.Body.style.position='relative';
//   this.Body.border='1';
   this.Body.cellSpacing="0";
   this.Body.cellPanding='0';

   //��IE5��ȥ�����¾��Ϳ��Ը߶ȶ�����
   this.Body.style.tableLayout='fixed';


//  this.Body.style.borderCollapse='collapse';//���ߵĸ�ʽ
//   this.Body.borderColorLight="#000000";
//   this.Body.borderColorDark="#000000";
   }
   if(this.Body.childNodes.length==0||this.Table==null){
	this.Table = document.createElement('TBody');
     this.Table.style.tableLayout='fixed';
  this.Body.appendChild(this.Table);
   // this.Body.setAttribute('Child',this.Table);
   }
}
function _DG_createzl()//����������DIV
{
  if(!this.Zldiv){
   this.Zldiv=document.createElement('div');
   this.appendChild(this.Zldiv);
  this.Zldiv.style.position='absolute';
  //this.Zldiv.style.overflow = 'auto';
  this.Zldiv.style.zIndex=30;
//   this.Bldiv.style.height=60;
  }
  this.ZlTab=document.createElement('table');
  this.ZlTab.id = "zl";
  this.Zldiv.appendChild(this.ZlTab);
 this.ZlTab.style.position='relative';
  this.ZlTab.style.top=0;
//  this.ZlTab.style.width=280;
//  this.ZlTab.border='1';
  this.ZlTab.cellSpacing="0";
  this.ZlTab.cellPanding='0';
//  this.ZlTab.style.borderCollapse = 'collapse';
 // this.ZlTab.borderColorLight=bgcolor;
 // this.ZlTab.borderColorDark="#000000";
  this.ZlTab.style.backgroundColor=this.HeadColor;
  this.ZlTab.style.tableLayout = 'fixed';

 //this.Bodydiv.style.Left =this.Zldiv.offsetWidth+2;
 //this.Bldiv.style.Left =this.Zldiv.offsetWidth+2;
}
function _DG_createpub()//������ͷDIV
{
  if(!this.Pubdiv){
   this.Pubdiv=document.createElement('div');
   this.appendChild(this.Pubdiv);
   this.Pubdiv.style.position='absolute';
   this.Pubdiv.style.top=0;
   this.Pubdiv.style.zIndex=100;
  }
  this.PubTab=document.createElement('table');
	this.PubTab.id = "pub";
  this.Pubdiv.appendChild(this.PubTab);
  this.PubTab.style.position='relative';
  this.PubTab.style.top=0;
//  this.Header.style.height=16;
//  this.PubTab.border='1';
  this.PubTab.cellSpacing="0";
  this.PubTab.cellPanding='0';
 // this.PubTab.style.borderCollapse = 'collapse';
  this.PubTab.style.backgroundColor=this.HeadColor;
  this.PubTab.style.tableLayout = 'fixed';

}

function _DG_createhead()//������ͷDIV
{
  if(!this.Headdiv){
   this.Headdiv=document.createElement('div');
   this.appendChild(this.Headdiv);
   this.Headdiv.style.position='absolute';
   this.Headdiv.style.top=0;
   this.Headdiv.style.zIndex=40;
  }
  this.Header=document.createElement('table');
  this.Header.id = "bl";
  this.Headdiv.appendChild(this.Header);
  this.Header.style.position='relative';
  this.Header.style.top=0;
//  this.Header.style.height=16;
//  this.Header.border='1';
 // this.Header.style.BorderStyle='inset';
  this.Header.cellSpacing="0";
  this.Header.cellPanding='0';
//  this.Header.style.borderCollapse = 'collapse';
//  this.Header.borderColorLight="#000000"
//  this.Header.borderColorDark="#000000";
  this.Header.style.backgroundColor=this.HeadColor;
  this.Header.style.tableLayout = 'fixed';

}
function _DG_createadds () //����������ϢDIV
{
  if(!this.Addsdiv){
   this.Addsdiv=document.createElement('div');
   this.appendChild(this.Addsdiv);
   this.Addsdiv.style.position='absolute';
  // this.Addsdiv.style.top=0;
   this.Addsdiv.style.zIndex=9940;

  }
 
}
function  _DG_addsshow()
{
/*	this.AddsTitle=document.createElement('span'); 
	this.AddsTitle.innerText= this.addsTitle;
	this.Addsdiv.appendChild(this.AddsTitle);
	var br=document.createElement('br');
	this.Addsdiv.appendChild(br);
	this.AddsArea = document.createElement('textarea');
	this.AddsArea.rows=5;
	this.AddsArea.cols=80;
  
	this.Addsdiv.appendChild(this.AddsArea);

	var br=document.createElement('br');
	this.Addsdiv.appendChild(br);
	this.CheckTitle = document.createElement('span');
	this.CheckTitle.innerText="��˽��";
	this.Addsdiv.appendChild(this.CheckTitle);
	this.CheckArea = document.createElement('div');
	this.CheckArea.style.width=660;
	this.CheckArea.style.height=180;
    this.CheckArea.style.overflow = 'auto';
    this.CheckArea.style.borderStyle='solid';
    this.CheckArea.style.borderWidth=1;
	this.CheckArea.style.ackground="#808080";
    this.CheckArea.parent=this.Addsdiv;
	
//	this.CheckArea.onclick=function()

//	{
//			 if(this.isMove){this.isMove=false;return;}
//			}
	this.CheckArea.onmousedown=function()
		{
		  if(!this.isMove) {
				this.setCapture();
				this.posx=event.x;
				this.posy=event.y;
			      this.style.cursor="move";
		        this.isMove = true;
				this.parent.DivOffTop=this.parent.offsetTop;
				this.parent.DivOffLeft =this.parent.offsetLeft;
         }
	   }
	this.CheckArea.onmouseup=function()
		{
		this.style.cursor='default';
	    this.isMove =false;
	     this.releaseCapture();
		};
	this.CheckArea.onmousemove=function()
		{
			if(this.isMove){
			this.style.cursor="move";
			this.parent.style.pixelLeft= this.parent.DivOffLeft+  event.x - this.posx;

			this.parent.style.pixelTop= this.parent.DivOffTop+  event.y - this.posy;
			//this.parent.Refresh();
			}
	     }
 //   this.CheckArea.onmouseover=function()	{this.isMove =false; }
	this.CheckArea.onmouseout=function()	{
		this.isMove =false;
		this.style.cursor='default';
	 //	this.parent.Refresh();
    	this.releaseCapture();
	 }

	this.Addsdiv.appendChild(this.CheckArea);

    if (this.addsTitle=='')
    {
		this.AddsTitle.style.visibility='hidden';
		this.AddsArea.style.visibility='hidden';
    }
  //  if (this.CheckArea=='')
    {
		this.CheckTitle.style.visibility='hidden';
		this.CheckArea.style.visibility='hidden';
    }
*/
}
function DG_setheadcolor(color)
{
  this.PubTab.style.backgroundColor=color;
  this.ZlTab.style.backgroundColor=color;
  this.Header.style.backgroundColor=color;
  this.Header.style.borderColorLight =color;
  for(var i=0;i<this.ColLevelNum;i++)
  {
	 for(var j=0;j<this.ColNum;j++)

	 this.blCells[i][j].borderColorLight=color;
	 this.blCells[i][j].borderColorDark=color;

  }

}
function DG_show()
{
/*  if(this.Auto||this.Columns.Auto)
	  this._GetDataSetInfo();
  this.Isinit = false;

*/
//  this._BodyShow();//��ʾ����

if(this.isDisplayHead)
  {
     this._PubShow();
	 this._ZlShow();
  //   this._PubShow();
	}
  this._HeadShow();//��ʾ����

  this._BodyShow();//��ʾ����
  this._AddsShow();
  $("td",this).addClass("cell");
  $("td",this.Body).css("text-align","center");
  this.Refresh();
}
function DG_refresh()
{
  //���������������ĵ�Ԫ��ĸ߶�һ��
 //  alert(this.name);
 //  this.style.visibility='visible';
  for( var row=0;row<this.RowNum;row++)
  {
  this.Table.rows(row).cells(0).style.pixelHeight=this.ZlTab.rows(row).cells(0).offsetHeight;
   }
  for( var col=0;col<this.ColNum;col++)
  {
   this.Table.rows(0).cells(col).pixelWidth=this.Header.rows(0).cells(col).offsetWidth;
   }
  if(this.isDisplayHead){
 // this.Zldiv.style.visibility='visible';
  //this.Pubdiv.style.visibility='visible';

  this.Pubdiv.style.top =0;
  this.Pubdiv.style.left=0;

  this.Pubdiv.style.height = this.Headdiv.offsetHeight;

  this.Zldiv.style.left =0 ;
  this.Zldiv.style.top=this.Headdiv.offsetHeight;
//  this.Zldiv.style.width=400;
 // this.Headdiv.style.width=800;
  this.Pubdiv.style.width= this.Zldiv.offsetWidth;

//  this.Pubdiv.style.height = this.Headdiv.offsetHeight;
 //  this.PubTab.style.width=this.Zldiv.offsetWidth;
  this.PubTab.style.height = this.Headdiv.offsetHeight;
   this.PubTab.style.visibility='visible';
 // this.Header.style.height =this.Headdiv.offsetHeight;
//  this.Headdiv.style.left =0;
  this.Headdiv.style.left =this.Zldiv.offsetWidth;

  this.Bodydiv.style.top = this.Headdiv.offsetHeight;
  this.Bodydiv.style.left =this.Zldiv.offsetWidth;
  this.Bodydiv.style.height =this.Zldiv.offsetHeight;
  this.Table.style.visibility='visible';
 // this.Addsdiv.style.top = this.Bodydiv.offsetHeight+this.Bodydiv.offsetTop+10;
 }
  else {
  this.Zldiv.style.visibility='hidden';
  this.Pubdiv.style.visibility='hidden';
  this.Bodydiv.style.top = this.Headdiv.offsetHeight;
  this.Bodydiv.style.left =0;
  this.Pubdiv.style.height =0;
  this.Headdiv.style.left =0;
  }

this.onscroll();
}

function DG_scrolldraw()
{
 this.Headdiv.style.pixelTop=this.scrollTop;//�ñ�ͷʼ�տɼ�
 this.Pubdiv.style.pixelTop=this.scrollTop;//�ñ�ͷʼ�տɼ�
 this.Pubdiv.style.pixelLeft=this.scrollLeft;//�ñ�ͷʼ�տɼ�
 this.Zldiv.style.pixelLeft=this.scrollLeft;//�ñ�ͷʼ�տɼ�
}
function _DG_bodyshow()//��ʾ��
{
 var RowData = new Array();
if(this.Table.childNodes.length>0)return;
switch(this.type) {
 case '������':
 case '1':
    for (var row=0 ;row <this.RowNum ;row++ ){
		this._appendRow(row);
    }
   for(var i=0;i<this.rowsNode.childNodes.length; i++)
	{
      var subnode= this.rowsNode.childNodes.item(i);
	  var Dm= subnode.getAttribute("dm");
	  var row =this.zlDmIdx[Dm];
	  var Data =subnode.getAttribute("data");
	  rowData=Data.split(',');
	    for (var col=0;col<rowData.length;col++)
		{
	      this.Cells[row+1][col+1].value=rowData[col];
		 }
    }

   break;
 }
 }
function _DG_appendrow(row)
{
var rowcolor;

 var aRow =  this.Table.insertRow();
     if(row%2)  rowcolor=clWhite;
     else   rowcolor='#F3F3F3';
	 aRow.style.backgroundColor=rowcolor;

	 this.Cells[row+1]= new Array();
	  for(var col=0;col<this.ColNum;col++){
		  var cell=aRow.insertCell();
		  cell.style.width=100;
	      //cell.style.height=this.ZlTab.rows(row).cells(0).offsetHeight;
	      cell.style.backgroundColor=rowcolor;
           var cellinput=this._appendCell(cell,row,col,"");
	      this.Cells[row+1][col+1]=cellinput;
	 }

}
function _DG_deleterow(currow)
{
  

   for (var row=currow; row<this.RowNum;row++ )
      {
 	    for(var col=0; col<this.ColNum ;col++)  
			this.Table.rows(row).cells(col).removeChild(this.Cells[row+1][col+1]);
	  }
  while ( this.Table.rows(currow).childNodes.length>0)
   {
			 this.Table.rows(currow).deleteCell(0);
   }

  this.Table.deleteRow(currow);


   for (var row=currow; row<this.RowNum-1;row++ )
      {
 	    for(var col=0; col <this.ColNum ;col++)  { 
			var color= this.Cells[row+1][col+1].style.backgroundColor;
	        this.Cells[row+1][col+1]=  this.Cells[row+2][col+1];
		    this.Cells[row+1][col+1].x =this.Cells[row+1][col+1].x-1;
		    this.Cells[row+1][col+1].style.backgroundColor=color;

			this.Table.rows(row).cells(col).appendChild(this.Cells[row+1][col+1]);
			this.Table.rows(row).cells(col).style.backgroundColor=color;
	    }
	  }


}
function _DG_zlandbodyremove()  //�����������Ŀ�����¶�����ʱ
{
}

function _DG_pubshow()//��ʾ��ͷ
{
if(this.PubTab.childNodes.length>0)return;
var headthead = document.createElement('thead');
this.PubTab.appendChild(headthead);
var headrow = document.createElement('tr');
//headrow.style.backgroundColor=this.;

headthead.appendChild(headrow);
var pubnode=this.publicsNode;
for(i=0;i<pubnode.childNodes.length-1;i++)
	{
	 var subnode=pubnode.childNodes.item(i);
     var cell = document.createElement('td');
    headrow.appendChild(cell);
	var noZl =subnode.getAttribute("zl");
	var noBl =subnode.getAttribute("bl")
    var attrname =subnode.getAttribute("attrname");
	var width=subnode.getAttribute("width");
	//this.pubZlAttr[attrname]=noZl;
	this.pubBlAttr[attrname]=noBl;

	if(width==null&& i==0) width=140;
	if(width==null&& i>0) width=70;
    if(noZl=="no") width=0;
    this.pubCellWidth[attrname]=width;
	//   cell.style.color = clWhite;
  //  cell.style.borderWidth='2pt';
   // cell.style.BorderStyle='soild';
    cell.style.width =width;
  //  cell.style.BorderStyle='soild';
   cell.innerText = subnode.getAttribute("name");
   //cell.style.borderStyle="outset";
   cell.align='center';
   cell.parent = this;
   cell.x = i;  //��Ԫ������������
   cell.isSize= false;
   cell.posx= 0;
   cell.cellOffWidth=0;
 //  cell.y = y;  //��Ԫ������������

       //Ϊ��Ԫ������µ�����
	//    cellbak.onclick=Cell_onclick;
		cell.onclick=function()
			{
			 if(this.isSize){this.isSize=false;return;}
			}
	   cell.onmousedown=function()
		{
		  if(!this.isSize) {
			//	this.style.borderStyle="inset";
				this.setCapture();
				this.posx=event.x;
			       this.style.cursor="move";
		this.isSize = true;
				this.cellOffWidth=this.offsetWidth;
 }
	   }
	cell.onmouseup=function()
		{
		//this.style.borderStyle="outset";
		this.style.cursor='default';
	this.isSize =false;
	  // this.releaseCapture();

		};
	cell.onmousemove=function()
		{
			if(this.isSize){
			this.style.cursor="move";
			this.style.pixelWidth= this.cellOffWidth+  event.x - this.posx;
			 this.parent.ZlTab.rows(0).cells(this.x).style.pixelWidth=this.style.pixelWidth;
	   this.parent.Headdiv.style.left =this.parent.Zldiv.offsetWidth;

	    this.parent.Bodydiv.style.left =this.parent.Zldiv.offsetWidth;
			/*for( var row=0;row<this.parent.RowNum;row++)
				{
			     this.parent.ZlTab.rows(row).cell(this.x).style.pixelWidth=this.style.pixelWidth;
				}*/
			this.parent.Refresh();
			//this.parent.Cells[0][0].value=this.parent.Cells[row][0].style.pixelHeight;
	//		this.parent.Cells[1][1].value=this.style.pixelWidth;
		//	this.parent.Cells[1][1].value=this.parent.RowNum;
			}
	     }
    cell.onmouseover=function()	{this.isSize=false; }
	 cell.onmouseout=function()	{
		this.isSize=false;
		this.style.cursor='default';
	this.releaseCapture();
	 }
 //  cell.set
   this.pubCells[i] =cell;
	}

}
function _DG_headshow()//��ʾ����
{
AddInfo("������"+ this.name +"����....");

if(this.Header.childNodes.length>0)return;
var headthead =  document.createElement('tbody'); //document.createElement('thead');
this.Header.appendChild(headthead);


var blsnode=this.blsNode;
var colCount=blsnode.childNodes.length;
var data=new Array();


for(var i=0;i<colCount;i++)
	{
 data[i]=new Array();
 var subnode=blsnode.childNodes.item(i);
 var inputStr= subnode.getAttribute("xm");

 this.blattr[i]=subnode.getAttribute("attr");
 this.bldec[i]=subnode.getAttribute("dec");

 var str=inputStr.split("/");
 pos=str.length-1;
 while(true)
	{
	 if(str[pos]=='') pos--;
	 else 
	 {
		 this.blXm[i]=str[pos]; break;
	 }
     
	}
 
// alert(this.blXm[i]);
 var part_num=0;
while (part_num <str.length)
 {
  //if(str[part_num]=="") str[part_num]=" ";
  data[i][part_num]=str[part_num];
  part_num++;
  }
  part_num--;
  this.blBottomRow =  part_num-1;
if(this.pubBlAttr['dm']!='no')
	   data[i][part_num++]=subnode.getAttribute("dm");
if(this.pubBlAttr['dw']!='no')
	data[i][part_num++]=subnode.getAttribute("dw");
var rowCount=part_num;
	}

var ColLevelNum= rowCount;
this.blCells=new Array();
for(var i=0;i<rowCount;i++)
{
    var headrow = document.createElement('tr');
    this.blCells[i]=new Array();
	headthead.appendChild(headrow);
	for(var j=0;j<colCount;j++)
	{
	 var cell = document.createElement('td');
     headrow.appendChild(cell);
	 cell.align='center';
	 this.blCells[i][j]=cell;
	// this.blCells[i][j].borderStyle="outset";
	 this.blCells[i][j].borderColorLight=bgcolor;
    //    this.blCells[i][j].setExpression('borderColorLight',bgcolor);
  //this.Body.borderColorDark="#000000";
	 this.blCells[i][j].style.width=100;

   cell.parent = this;
   cell.y = j;  //��Ԫ������������
   cell.isSize= false;
   cell.posx= 0;
   cell.cellOffWidth=0;

//  cell.y = y;  //��Ԫ������������

       //Ϊ��Ԫ������µ�����
	//    cellbak.onclick=Cell_onclick;
		cell.onclick=function()
			{
			 if(this.isSize){this.isSize=false;return;}
			}
	   cell.onmousedown=function()
		{
		  if(!this.isSize) {
		//		this.style.borderStyle="inset";
				this.setCapture();
				this.posx=event.x;
			       this.style.cursor="move";
		this.isSize = true;
				this.cellOffWidth=this.offsetWidth;
 }
	   }
	cell.onmouseup=function()
		{
	//	this.style.borderStyle="outset";
		this.style.cursor='default';
	//	this.style.pixelWidth= cellOffWidth+  event.x - posx;
	this.isSize =false;
	  this.releaseCapture();
	//	this.parent.Refresh();
		};
	cell.onmousemove=function()
		{
			if(this.isSize){
			this.style.cursor="move";
			this.style.pixelWidth= this.cellOffWidth+  event.x - this.posx;
		    this.parent.blCells[0][this.y].style.pixelWidth=this.style.pixelWidth;
			this.parent.Table.rows(0).cells(this.y).style.pixelWidth=this.style.pixelWidth;
			//this.parent.Cells[0][this.y].parentElement.style.pixelWidth=this.style.pixelWidth;
//			for ((var row=0;row<this.parent.RowNum; row++)
//			{
	//				this.parent.Cells[0][this.y].style.pixelWidth=this.style.pixelWidth-4;
//			}
      //    this.parent.Pubdiv.style.height = this.parent.Headdiv.offsetHeight;
      //    this.parent.Zldiv.style.top=this.parent.Headdiv.offsetHeight;
      //    this.parent.Bodydiv.style.top = this.parent.Headdiv.offsetHeight;
      //    this.parent.Bodydiv.style.height =this.parent.Zldiv.offsetHeight;
			this.parent.Refresh();
			}
	     }
    cell.onmouseover=function()	{this.isSize=false; }
	 cell.onmouseout=function()	{
		this.isSize=false;
		this.style.cursor='default';
	 //	this.parent.Refresh();
	this.releaseCapture();
	//	this.parent.Refresh();
	 }





//  this.Header.borderColorLight="#000000"
     }
}

 var bl_data=new Array();
 for(var i=0;i<rowCount;i++)
	{
     bl_data[i]=new Array();
	 for (var j=0;j<colCount;j++ )
     {
      bl_data[i][j]=data[j][i];
      this.blCells[i][j].innerText=bl_data[i][j];
//	  this.blCells[i][j].borderStyle="outset";
     }
 }
 var mRowCount;
 var mColCount;

 rowCount =rowCount-1 ; //������λ�ϲ�

 for (var i=0;i<rowCount;i++)
 {
 for (var j=0;j<colCount;j++)
 {

 if(i<rowCount-1 && bl_data[i][j]==bl_data[i+1][j])
	 {
      this.blCells[i][j].style.borderBottomStyle="none"; //ȥ����Ԫ����
      //this.blCells[i+1][j].style.borderTopStyle="none";
      this.blCells[i+1][j].innerText=" ";
	 }
   if(j<colCount-1 && bl_data[i][j]==bl_data[i][j+1])
	 {
     this.blCells[i][j].style.borderRightStyle="none";
     //this.blCells[i][j+1].style.borderLeftStyle="none";
      this.blCells[i][j+1].innerText=" ";

	 }
	if(bl_data[i][j]=="")
	 {
      this.blCells[i][j].style.borderTopStyle="none";
      this.blCells[i][j].innerText=" ";
	 }

  }
 }
}

function _DG_zlshow()//��ʾ����������
{
AddInfo("������"+ this.name +"����....");
//alert("Ok");
if(this.ZlTab.childNodes.length>0) return;
//var headthead = this.ZlTab.createTHead();

switch(this.type) {
 case '������':
 case '1':
var zlsnode=this.zlsNode;
for(i=0;i<this.RowNum;i++)
	{
	 var subnode=zlsnode.childNodes.item(i);
     var headrow = this.ZlTab.insertRow();
	  this.zlCells[i] =new Array();
      var cell =headrow.insertCell();
 //     cell.style.color = clWhite;
//      cell.style.border='none';
       var str =[];
	   var zlStr='';
	   str=subnode.getAttribute("xm").split('/');
	   for( var c=0;c<str.length-2;c++)
		    zlStr+='  ';
       zlStr+=str[str.length-2];
	   if( str.length==1)
		   cell.innerText=str[0];
       else cell.innerText =zlStr;
	  cell.style.width=this.pubCellWidth['xm'];
	   cell.setAttribute("tooltip",cell.innerText);
    //  cell.style.borderStyle="outset";
	  cell.noWrap=true;
	 this.zlCells[i][0]=cell;
	 this.zlCells[i][0].value=cell.innerText;
      var cell =headrow.insertCell();
 //     cell.style.color = clWhite;
  //    cell.style.border='none';
	    cell.style.width=this.pubCellWidth['dm'];
     cell.innerText = subnode.getAttribute("dm");
	 cell.setAttribute("tooltip",cell.innerText);
    // cell.style.borderStyle="outset";

	 this.zlDm[i]= subnode.getAttribute("dm");
     this.zlDmIdx[this.zlDm[i]] =i;
     this.zlCells[i][1]=cell;
	 this.zlCells[i][1].value=cell.innerText;
      var cell =headrow.insertCell();
 //    cell.style.color = clWhite;
    // cell.style.border='none';
     cell.style.width=this.pubCellWidth['dw'];
     cell.innerText = subnode.getAttribute("dw");
   //  cell.style.borderStyle="outset";
	 cell.setAttribute("tooltip",cell.innerText);
     this.zlCells[i][2]=cell;
	 this.zlCells[i][2].value=cell.innerText;

     this.zlattr[i]=subnode.getAttribute("attr");
     this.zldec[i]=subnode.getAttribute("dec");
	}
     break;
 case '��������':
 case '2':
  var zlsnode=this.zlsNode;
  for(i=0;i<this.RowCount;i++)
	{
	 var subnode=zlsnode.childNodes.item(i);
	   var str=[];
	   var zlStr='';
	   str=subnode.getAttribute("xm").split('/');
	   for( var c=0;c<str.length-2;c++)
		    zlStr+='  ';
       zlStr+=str[str.length-2];
	   if( str.length==1)
			    this.zlXm[i]=str[0];
       else  this.zlXm[i] =zlStr;

	 this.zlDm[i]= subnode.getAttribute("dm");
     this.zlDmIdx[this.zlDm[i]] =i;
     this.zlDw[i] = subnode.getAttribute("dw");
     this.zlattr[i]=subnode.getAttribute("attr");
     this.zldec[i]=subnode.getAttribute("dec");
	}
	 this._appendZlRow();
	  break;
 case '��Ŀ��':
 case '3':
	 this._appendZlRow();
	  break;
 }
}
function _DG_appendzlrow()
{
switch(this.type) {
 case '������':
 case '1':
	   break;
 case '��������':
 case '2':
      var row=this.RowNum;
	   this.RowNum++;
     //�������
	   this.zlCells[row]= new Array();
	    var headrow = this.ZlTab.insertRow();
		for(var col=0;col<3;col++)
	   {
	var cell =headrow.insertCell();
  //       if(row%2)  rowcolor=clWhite;
  //        else   rowcolor='#F3F3F3';
	     cell.style.backgroundColor=bgcolor;
	     if(col==0) cell.style.width=this.pubCellWidth['xm'];
		if (col==1) cell.style.width=this.pubCellWidth['dm'];
		if (col==2) cell.style.width=this.pubCellWidth['dw'];
	     var cellinput=this._appendSeZlCell(cell,row,col,"");
	     this.zlCells[row][col] =cellinput;
		 }

		 this._appendRow(row);
//		 this.Refresh();
	  break;
 case '��Ŀ��':
 case '3':
       var row=this.RowNum;
	   this.RowNum++;
     //�������
	   this.zlCells[row]= new Array();
	    var headrow = this.ZlTab.insertRow();
		for(var col=0;col<3;col++)
	   {
	var cell =headrow.insertCell();
	 if(row%2)  rowcolor=clWhite;
	  else   rowcolor='#F3F3F3';
	     cell.style.backgroundColor=rowcolor;
	    if (col==0) cell.style.width=this.pubCellWidth['xm'];
		if (col==1) cell.style.width=this.pubCellWidth['dm'];
		if (col==2) cell.style.width=this.pubCellWidth['dw'];

	     var cellinput=this._appendZlCell(cell,row,col,"");
	     this.zlCells[row][col] =cellinput;
		 }

	//	 this.zlCell[row][0].focus();
		 this._appendRow(row);
	//	this.Refresh();
	//	 this.zlCell[row][0].focus();
	  break;
 }

}
function _DG_deletezlrow(currow )
{
//alert(currow);
alert(currow);
switch(this.type) {
 case '������':
 case '1':
	   break;
 case '��������':
 case '2':
 case '��Ŀ��':
 case '3':
	 
    if( this.RowNum==1|| currow<0 || currow>this.RowNum-1) return ;

   for (var row=currow; row<this.RowNum;row++ )
      {
 	    for(var col=0; col<3 ;col++)  
			this.ZlTab.rows(row).cells(col).removeChild(this.zlCells[row][col]);
	  }

  while (this.ZlTab.rows(currow).childNodes.length>0)
   {
			this.ZlTab.rows(currow).deleteCell(0);
   }
    this.ZlTab.deleteRow(currow);


   for (var row=currow; row<this.RowNum-1;row++ )
      {
 	    for(var col=0; col <3 ;col++)  { 
			var color  = this.zlCells[row][col].style.backgroundColor;
	       this.zlCells[row][col]=  this.zlCells[row+1][col];
		   this.zlCells[row][col].x =this.zlCells[row][col].x-1;
		   this.zlCells[row][col].style.backgroundColor=color;

			this.ZlTab.rows(row).cells(col).appendChild(this.zlCells[row][col]);
			this.ZlTab.rows(row).cells(col).style.backgroundColor=color;
		}
	  }

   

   for (var row=currow; row<this.RowNum;row++ )
      {
 	    for(var col=0; col<this.ColNum ;col++)  
			this.Table.rows(row).cells(col).removeChild(this.Cells[row+1][col+1]);
	  }
  while ( this.Table.rows(currow).childNodes.length>0)
   {
			 this.Table.rows(currow).deleteCell(0);
   }

  this.Table.deleteRow(currow);


   for (var row=currow; row<this.RowNum-1;row++ )
      {
 	    for(var col=0; col <this.ColNum ;col++)  { 
			var color= this.Cells[row+1][col+1].style.backgroundColor;
	        this.Cells[row+1][col+1]=  this.Cells[row+2][col+1];
		    this.Cells[row+1][col+1].x =this.Cells[row+1][col+1].x-1;
		    this.Cells[row+1][col+1].style.backgroundColor=color;

			this.Table.rows(row).cells(col).appendChild(this.Cells[row+1][col+1]);
			this.Table.rows(row).cells(col).style.backgroundColor=color;
	    }
	  }



    this.RowNum--;
	break;
 }

}
function _DG_appendsezlcell(cell,x,y,value)
{
	var cellbak;
		//*****************����������ͬ�Ĵ���***********//
	   cellbak = document.createElement('input');//���һ��ָ��Ԫ��
	     cell.appendChild(cellbak);
  //  cellbak.style.borderStyle ='solid';
	   cellbak.style.borderWidth =0;
	    //  setCellStyle1(cellbak);
	   //���Ƶ�Ԫ��ĸ�ʽ
       cellbak.parent = this;
	   cellbak.x = x;  //��Ԫ������������
	   cellbak.y = y;  //��Ԫ������������
	   cellbak.value =value;
	//   cellbak.w=cell.style.width-4;
      if(this.readOnly==true) cellbak.readOnly=true;
	   cellbak.Brother=null;
	   cellbak.selectedIdx =0;
	   if(y==0)
		   cellbak.size = 18;   //��Ԫ��������
	   else cellbak.size =8

       //Ϊ��Ԫ������µ�����
	    cellbak.onclick=Cell_onsezlclick;
	    cellbak.onfocus = Cell_onsezlcellfocus;
	    cellbak.onblur =Cell_onsezlcellblur;
	    cellbak.onkeydown =Cell_onsezlcellkeydown;
	 //  cellbak.onchange = DG_onchange;
     if(x%2)  rowcolor=clWhite;
     else   rowcolor='#F3F3F3';
	 cellbak.style.backgroundColor=rowcolor;
	// cellbak.style.width=cell.style.width-1;
	// cellbak.setAttribute("tooltip",value);
	return cellbak;
}
function Cell_onsezlclick()
{
if(this.y==2) return;
 if(this.parent.readOnly==true) return;

   this.style.width = 0;  //����text
   if(!this.parent.ComboBox[this.y]){  //��һ������ select
	 this.parent.ComboBox[this.y] =document.createElement('select');  //�����

		 //��IE5�±�������appendChild   Fix  2004.6.28
		 this.parentNode.appendChild(this.parent.ComboBox[this.y]);
	 this.parent.ComboBox[this.y].style.position='absolute'

		 var oOption = document.createElement("option");  //�����
	 this.parent.ComboBox[this.y].add(oOption);
	     oOption.innerText = " ";   //��ֵ
	  oOption.value = " ";

		 for(var i=0;i<this.parent.RowCount;i++){
		  var oOption = document.createElement("OPTION");
	      this.parent.ComboBox[this.y].add(oOption);
			  if(this.y==0){
		oOption.innerText = this.parent.zlXm[i];
	       oOption.value = this.parent.zlXm[i];
			  }
			  if(this.y==1){
		oOption.innerText = this.parent.zlDm[i];
		oOption.value = this.parent.zlDm[i];
			  }
		 }

	  this.parent.ComboBox[this.y].onblur = _Combo_blur;    //����ʧȥ�����¼�����
	  this.parent.ComboBox[this.y].onchange= _Combo_onchange;//���Ա仯���¼�
	   this.parent.ComboBox[this.y].parent= this.parent;//

		 if(this.y==0)
		   this.parent.ComboBox[this.y].w=this.parent.pubCellWidth['xm']-4;
		  else
		    this.parent.ComboBox[this.y].w=this.parent.pubCellWidth['dm']-4;
		  }
		else
	  this.parentNode.appendChild(this.parent.ComboBox[this.y]);

		this.parent.ComboBox[this.y].selectedIndex=this.selectedIdx;

	this.Brother = this.parent.ComboBox[this.y];
	this.parent.ComboBox[this.y].Brother = this;
	var comstyle = this.parent.ComboBox[this.y].style;
	comstyle.visibility = 'visible';
	this.parent.ComboBox[this.y].w=this.parent.ZlTab.rows(this.x).cells(this.y).offsetWidth-4;
	comstyle.pixelWidth = this.parent.ComboBox[this.y].w;
	comstyle.top = this.parentNode.offsetTop;
	comstyle.left = this.parentNode.offsetLeft;
	this.parent.ComboBox[this.y].focus();

}
function _Combo_blur() {
	if(!this.Brother)return;
    this.style.width = 0;
    this.Brother.style.width =this.w;
    this.Brother.style.visibility = 'visible'; //��ʾedit
    this.style.visibility = 'hidden'; //����combobox
    this.Brother.parentNode.removeChild(this);
    this.Brother = null;
}
function _Combo_onchange() {
 var p=this.parent;
 if(this.selectedIndex==0) return;
 for (var row=0; row<p.RowNum; row++)
 {
 	// alert(p.zlCells[0][0].value);
	if( p.zlCells[row][this.Brother.y].value== this.options[this.selectedIndex].value) {
		alert(this.options[this.selectedIndex].value+ ' �Ѵ��������У�');
		return;
	 }

}
 if(this.Brother.y==0) {
 p.zlCells[this.Brother.x][this.Brother.y].innerText=this.options[this.selectedIndex].value;
 p.zlCells[this.Brother.x][this.Brother.y+1].innerText=p.zlDm[this.selectedIndex-1];
 p.zlCells[this.Brother.x][this.Brother.y+2].innerText=p.zlDw[this.selectedIndex-1];
 p.zlCells[this.Brother.x][this.Brother.y].selectedIdx=this.selectedIndex;
 p.zlCells[this.Brother.x][this.Brother.y+1].selectedIdx=this.selectedIndex;
}
if(this.Brother.y==1) {
 p.zlCells[this.Brother.x][this.Brother.y-1].innerText=p.zlXm[this.selectedIndex-1];
 p.zlCells[this.Brother.x][this.Brother.y].innerText=this.options[this.selectedIndex].value;
 p.zlCells[this.Brother.x][this.Brother.y+1].innerText=p.zlDw[this.selectedIndex-1];
 p.zlCells[this.Brother.x][this.Brother.y].selectedIdx=this.selectedIndex;
 p.zlCells[this.Brother.x][this.Brother.y-1].selectedIdx=this.selectedIndex;
}
}
function Cell_onsezlcellfocus()//���༭��Ԫ���Ľ���
{
// if(this.y<2) return;
  this.onclick();
  this.style.borderStyle ='solid';
  this.style.borderWidth='1pt';
  //this.style.BorderTopStyle='inset';
//  this.style.backgroundColor='#00003F';
  this.style.color='#0000DF';
 this.parent._ComputHIsView(this.x+1);
 this.parent._ComputVIsView(this);
}
function Cell_onsezlcellblur()//���༭��Ԫ��ʧȥ����
{
// if(this.y<2) return;
  this.style.borderWidth=0;
//  this.style.backgroundColor='#FFFFFF';
 this.style.color='#000000';
 this.setAttribute("tooltip",this.value)
}
 function Cell_onsezlcellkeydown()
{
// if(this.y<2) return;
	switch(event.keyCode)
	{
		case 38://���������ϼ�ͷ
			if(this.x>0)
		    {
		     this.parent.zlCells[this.x-1][this.y].focus();

			}
		    break;
		case 40://���������¼�ͷ
			if(this.x< this.parent.RowNum-1)
		    {
			 this.parent.zlCells[this.x+1][this.y].focus();
		    }
			else
		    {
			  if(this.parent.readOnly==true) break;

			 this.parent._appendZlRow();
			 this.parent.Refresh();
			 this.parent.zlCells[this.x+1][this.y].focus();

			}
		    break;
		case 13:
			 if(this.y<2)
			 this.parent.zlCells[this.x][this.y+1].focus();
	     else
			 this.parent.Cells[this.x+1][1].focus();
			 break;
		case 33://���������Ϸ���ҳ��
			this.parent.doScroll('scrollbarPageUp');
		    break;
		case 34://���������Ϸ���ҳ��
			this.parent.doScroll('scrollbarPageDown');
		    break;
		case 8:
			 if(this.y==2)
			 this.parent.Cells[this.x+1][1].focus();
		     break;

	}
}


function _DG_appendzlcell(cell,x,y,value)
{
	var cellbak;
		//*****************����������ͬ�Ĵ���***********//
	   cellbak = document.createElement('input');//���һ��ָ��Ԫ��
     //  cellbak.style.borderStyle ='solid';
	   cellbak.style.borderWidth =0;
	    //  setCellStyle1(cellbak);
	   //���Ƶ�Ԫ��ĸ�ʽ
       cellbak.parent = this;
	   cellbak.x = x;  //��Ԫ������������
	   cellbak.y = y;  //��Ԫ������������
	   cellbak.value =value;
	   if(y==0)
		   cellbak.size = 18;   //��Ԫ��������
	   else cellbak.size =8

       //Ϊ��Ԫ������µ�����
	//    cellbak.onclick=Cell_onclick;
	    cellbak.onfocus = Cell_onzlcellfocus;
	    cellbak.onblur =Cell_onzlcellblur;
	    cellbak.onkeydown =Cell_onzlcellkeydown;
	 //  cellbak.onchange = DG_onchange;
     if(x%2)  rowcolor=clWhite;
     else   rowcolor='#F3F3F3';
	 cellbak.style.backgroundColor=rowcolor;
	// cellbak.style.width=cell.style.width-1;
	 cell.appendChild(cellbak);
     if(this.readOnly==true) cellbak.readOnly=true;
	 cellbak.setAttribute("tooltip",value);
	return cellbak;
}
function Cell_onzlcellfocus()//���༭��Ԫ���Ľ���
{
  this.style.borderStyle ='solid';
  this.style.borderWidth='1pt';
  //this.style.BorderTopStyle='inset';
//  this.style.backgroundColor='#00003F';
  this.style.color='#0000DF';
 // this.style.backgroundColor ='#0000DF';;
  //this.style.visibility='visible';
 this.parent._ComputHIsView(this.x+1);
 this.parent._ComputVIsView(this);
 this.parent.focusRow = this.x;
// alert(this.y);
}
function Cell_onzlcellblur()//���༭��Ԫ��ʧȥ����
{
  this.style.borderWidth=0;
//  this.style.backgroundColor='#FFFFFF';
 this.style.color='#000000';
 this.setAttribute("tooltip",this.value)
}
 function Cell_onzlcellkeydown()
{
	switch(event.keyCode)
	{
		case 38://���������ϼ�ͷ
			if(this.x>0)
		    {
		 this.parent.zlCells[this.x-1][this.y].focus();

			}
		    break;
		case 40://���������¼�ͷ
			if(this.x< this.parent.RowNum-1)
		    {
			 this.parent.zlCells[this.x+1][this.y].focus();
		    }
			else
		    {
	      if(this.parent.readOnly==true) break;
			 this.parent._appendZlRow();
			 this.parent.Refresh();
			 this.parent.zlCells[this.x+1][this.y].focus();

			}
		    break;
		case 13:
			 if(this.y<2)
			 this.parent.zlCells[this.x][this.y+1].focus();
	     else
			 this.parent.Cells[this.x+1][1].focus();
			 break;
		case 33://���������Ϸ���ҳ��
			this.parent.doScroll('scrollbarPageUp');
		    break;
		case 34://���������Ϸ���ҳ��
			this.parent.doScroll('scrollbarPageDown');
		    break;
		case 8:
			 if(this.y==2)
			 this.parent.Cells[this.x+1][1].focus();
		     break;

	}
}


function _DG_computHisview(row) {  //�ж������������Ƿ�������֮��
   var isInView = 0;
   var hViewTop = this.scrollTop;
   var hViewBottom = this.scrollTop+this.Bodydiv.offsetHeight;
   var hRowTop =this.ZlTab.rows(row-1).cells(1).offsetTop;
   if(hRowTop>hViewBottom) this.scrollTop=hRowTop+this.Bodydiv.offsetHeight;
   if(hRowTop<hViewTop)   {
	   if(row>1)
		    this.scrollTop= this.ZlTab.rows(row-1).cells(1).offsetTop-1;
	else this.scrollTop =0;
   }
   this.Headdiv.style.pixelTop=this.scrollTop;//�ñ�ͷʼ�տɼ�
   return isInView;
}

function _DG_computVisview(objcell) { //�ж��������ĵ�Ԫ���Ƿ�������֮��
   var isInView = 0;
   var vCellRight =objcell.parentNode.offsetLeft + objcell.parentNode.offsetWidth;
   var vCellLeft = objcell.parentNode.offsetLeft;
   var vViewRight = this.scrollLeft + this.Bodydiv.offsetWidth;
   var vViewLeft = this.scrollLeft;
   if(objcell.y == 1){
     this.scrollLeft = 0;
   }
   else{
	   if(vCellLeft>vViewRight) this.scrollLeft=vCellLeft;///+this.Bodydiv.offsetWidth;; //objcell.parentNode.offsetLeft;
	   if(vCellLeft<vViewLeft)  this.scrollLeft=objcell.parentNode.offsetLeft;
     if(vCellRight > vViewRight){
       this.scrollLeft = vCellLeft;
     }
   }
}
//
function _DG_appendcell(cell,x,y,value)
{
	var cellbak;
		//*****************����������ͬ�Ĵ���***********//
	   cellbak = document.createElement('input');//���һ��ָ��Ԫ��
	   cellbak.style.width="100%";
     //  cellbak.style.borderStyle ='solid';
	   cellbak.style.borderWidth =0;
	   $(cellbak).css("text-align","right");
	    //  setCellStyle1(cellbak);
	   //���Ƶ�Ԫ��ĸ�ʽ
      cellbak.parent = this;
	   cellbak.x = x+1;  //��Ԫ������������
	   cellbak.y = y+1;  //��Ԫ������������
	   cellbak.value =value;
	   cellbak.size = 10;   //��Ԫ��������

	 if( this.first=='zl')
	  {
		   cellattr=this.zlattr[x];
		   celldec=this.zldec[x];
	  }
	  else
	  {
		   cellattr=this.blattr[y];
		   celldec=this.bldec[y];
	  }
	   if(cellattr=='n') {
		   switch(celldec) {
			   case '0':
				        cellbak.pattern= /[^\d\-]|(\-)?\d+\-/g
				        cellbak.decpattern=null
			            break;
			   case '1':
					   cellbak.pattern = /[^\d\.\-]|(\-)?\d+(\-)|(\-)?\d*\.\d*\./g
					   cellbak.decpattern = /(\-)?\d+\.\d{1}./g
				       break;
			   case '2':
					   cellbak.pattern = /[^\d\.\-]|(\-)?\d+(\-)|(\-)?\d*\.\d*\./g
					   cellbak.decpattern = /(\-)?\d+\.\d{2}./g
				       break;
			   case '3':
					   cellbak.pattern = /[^\d\.\-]|(\-)?\d+(\-)|(\-)?\d*\.\d*\./g
					   cellbak.decpattern = /(\-)?\d+\.\d{3}./g
				       break;
			   case '4':
					   cellbak.pattern = /[^\d\.\-]|(\-)?\d+(\-)|(\-)?\d*\.\d*\./g
					   cellbak.decpattern = /(\-)?\d+\.\d{4}./g
				       break;
			   default:
	                    cellbak.pattern= /[^\d\-]|(\-)?\d*\-/g
				        cellbak.decpattern= null
			            break;


		   }
	   }
	   else  	 { //�ַ�
		                cellbak.pattern =null
				        cellbak.decpattern= null
	        }


	   if(this.ZlTab.rows(x).cells(1).innerText=='-'||this.ZlTab.rows(x).cells(2).innerText=='*')
	    {cellbak.value='-';
	     cellbak.readOnly = true;
		}
	   else
	    cellbak.readOnly = false;

	if(this.readOnly==true) cellbak.readOnly=true;

       //Ϊ��Ԫ������µ�����
	//    cellbak.onclick=Cell_onclick;
	    cellbak.onfocus = Cell_oncellfocus;
	    cellbak.onblur =Cell_oncellblur;
	    cellbak.onkeydown =Cell_oncellkeydown;
		cellbak.onkeyup =Cell_oncellkeyup;
        cellbak.onmouseover=Cell_oncellfocus;
	    cellbak.onmouseout=Cell_oncellblur;

	 //  cellbak.onchange = DG_onchange;
     if(x%2)  rowcolor=clWhite;
     else   rowcolor='#F3F3F3';
	 cellbak.style.backgroundColor=rowcolor;
  	  cell.appendChild(cellbak);
	// cellbak.style.width=cell.style.width-1;
	 cellbak.setAttribute("tooltip",value);
	return cellbak;
}
function Cell_oncellfocus()//���༭��Ԫ���Ľ���
{
 // this.style.borderStyle ='solid';
 // this.style.borderWidth='1pt';
 // this.style.color='#0000DF';
  this.style.color=skinFaceColor;
 // this.style.backgroundColor='#E6E6E6';
 this.parent._ComputHIsView(this.x);
  this.parent._ComputVIsView(this);
  this.parent.focusRow = this.x-1;
 // this.parent.Cells[1][1].value=this.x+':'+this.y;
}
function Cell_oncellblur()//���༭��Ԫ��ʧȥ����
{
  this.style.borderWidth=0;
  if((this.x+1)%2)  rowcolor=clWhite;
  else   rowcolor='#F3F3F3';
  this.style.backgroundColor=rowcolor;
  this.style.color='#000000';
 this.setAttribute("tooltip",this.value)
}
function Cell_oncellkeyup()
{
 if(this.pattern==null) return;

 this.value=this.value.replace(this.pattern,'');
// this.value=this.value.replace(this.pattern,this.value.substring(0,this.value.length-1));
if(this.decpattern==null) return;
//  this.value=this.value.replace(this.decpattern,this.value.substring(0,this.value.length-1));

var pos =this.value.indexOf('.');
 var decnum='';
 if(pos!=-1)  {
  decnum =this.value.substring(pos,pos+this.celldec+1);
  this.value=this.value.replace(this.decpattern,decnum);
 }
	if(this.parent.OnCellKeyUp){
	  this.parent.OnCellKeyUp(this,this.x,this.y,event.keyCode);

	}
}
 function Cell_oncellkeydown()
{
     /*     var  r=this.createTextRange();
	  r.moveToPoint(event.x,event.y);
		 r.moveStart("character",-event.srcElement.value.length)
	 var posy=r.text.length;
   */
	switch(event.keyCode)
	{
	    case 37:
			//    if(posy==0)
		       {
			   if(this.y>1)
		 this.parent.Cells[this.x][this.y-1].focus();
			   break;

		       }
			   break;
		case 38://���������ϼ�ͷ
			if(this.x>1)
		    {
		 this.parent.Cells[this.x-1][this.y].focus();

			}
		    break;
		case 40://���������¼�ͷ
			if(this.x< this.parent.RowNum)
		    {
			 this.parent.Cells[this.x+1][this.y].focus();
		    }
			else 
		    {
	         if(this.parent.readOnly==true|| this.parent.type=='������'||this.parent.type=='1') break;
			 this.parent._appendZlRow();
			 this.parent.Refresh();
	          this.parent.Cells[this.x+1][this.y].focus();

			}
		    break;
		case 13: //�س�
		      if(this.y<this.parent.ColNum)
			   this.parent.Cells[this.x][this.y+1].focus();
	       else if(this.x< this.parent.RowNum)
			    this.parent.Cells[this.x+1][1].focus();

	       break;
		case 33://���������Ϸ���ҳ��
			this.parent.doScroll('scrollbarPageUp');
		    break;
		case 34://���������Ϸ���ҳ��
			this.parent.doScroll('scrollbarPageDown');
		    break;
	}
	  if (event.shiftKey && (!event.shiftLeft) && event.altLeft) {
	    alert(xyabc);
       
      }
	if(this.parent.OnCellKeyDown){
	  this.parent.OnCellKeyDown(this,this.x,this.y,event.keyCode);

	}
}
function toNumber( str)
{
	value = parseFloat(str);
	 if(isNaN(value)) value=str;
	if(str=='') value='��ֵ';
	return value;
}
//-----------------------------------------------------------------------------------------------

function DateTimeToStr(d)
{
if(isNaN(d))return;
var y=d.getFullYear();var m=(d.getMonth()+1);var da=d.getDate();
var h=d.getHours();var mi=d.getMinutes();var sec=d.getSeconds();var ms=d.getMilliseconds();var s=y.toString()+'-';
if(m<10) s=s+'0';s=s+m+'-';if(da<10) s=s+'0';s=s+da+' ';
if(h||mi||sec||ms){if(h<10) s=s+'0';s=s+h+':';if(mi<10) s=s+'0';s=s+mi+':';if(sec<10) s=s+'0';s=s+sec+' ';}
return s;
}
function DateToStr(d)
{
if(isNaN(d))return;
var y=d.getFullYear();var m=(d.getMonth()+1);var da=d.getDate();
var s=y.toString()+'-';
if(m<10) s=s+'0';s=s+m+'-';if(da<10) s=s+'0';s=s+da;
return s;
}
function FormatFloat(value,mask)
{
	return BasicFormat(value,mask,'FormatNumber');
//	return FormatNumber(value,mask);
}
function FormatDate(varDate, bstrFormat, varDestLocale)
{
	return BasicFormat(varDate,bstrFormat,'FormatDate',varDestLocale);
}
function FormatTime(varTime, bstrFormat, varDestLocale)
{
	return BasicFormat(varTime,bstrFormat,'FormatTime',varDestLocale);
}
function BasicFormat(value,mask,action,param)
{
	var xmlDoc=new ActiveXObject("Microsoft.XMLDOM")
	var xslDoc=new ActiveXObject("Microsoft.XMLDOM")
	var v='<formats><format><value>'+value+'</value><mask>'+mask+'</mask></format></formats>';
//	xmlDoc.loadXML(s);
	 xmlDoc.loadXML(v);
	var x='<xsl:stylesheet xmlns:xsl="uri:xsl">';
	x+='<xsl:template match="/">';
	x+='<xsl:eval>'+action+'('+value+',"'+mask+'"';
	if(param)x+=','+param;
	x+=')</xsl:eval>';
	x+='</xsl:template></xsl:stylesheet>';
	xslDoc.loadXML(x);
	var s = xmlDoc.transformNode(xslDoc);
	return s;
}
function StatsReport_err(e)
{
  //if(window.Exception){alert(e);}else throw e;
  AddInfo(e);
}
function test()
{
var body=document.getElementsByTagName("body")
// sCode=script[0].innerHTML ;
 alert(body[0].innerHTML);
}

function setBodyFontSize(size){
var body=document.getElementsByTagName("body");
  body[0].style.fontFamily='����';
 if(size==1)
	 body[0].style.fontSize='20pt';
  else  if( size==2)
	 body[0].style.fontSize='10pt';
  else
	 body[0].style.fontSize='4pt';

}
function writeFile(filename,str)
{
fso = new ActiveXObject("Scripting.FileSystemObject");
fid=fso.CreateTextFile(filename, 2, true);
fid.Write(str);
fid.Close();
}
function readFile(filename)
{
fso = new ActiveXObject("Scripting.FileSystemObject");
fid=fso.OpenTextFile(filename,1);
var content=fid.ReadLine();
fid.Close();
return content;
}

function writeBody()
{
var body=document.getElementsByTagName("html")
 writeFile('d:\\body.html',body[0].innerHTML);
}
