/*------------------------------------------------------------------------
��Javascript+DOMʵ�ֵı������
���������������б�

����: ���� ����  ����
��λ: ����ʡͳ�ƾּ�������
E-mail: xy@nhome.org

2004.5.8  ��ʼ��˼��ѧϰ DOM��д����XML��ʽ�����ݣ����Ա����ʾ
2004.5.9  ����ָѧϰ DOM
2004.5.10 �������񣬼���дDOM���Ա����ʾ��¼��
2004.5.12 ���غ��޸� tooltip.js
2004.5.13 ���غ��޸� toolpane.js,��XW����
2004.5.17 ��JRȷ������ṹ��XML��ʽ����ʼд�ܿ�ģ�� StatsReports
2004.5.18 д���ܿ�ģ��ͽ�����������ʾ�ͱ���δ������ʾ.
2004.5.19 ����˱������ݵ�Ԫ����ʾ����
2004.5.20 ��XWд��menu-new.js�ӵ�ģ����,ʵ����menu����HYHȷ���������ݰ�XML��ʽ
2004.5.21 д�������ݰ�����Ԫ��ʾ,����NC�ı�����ʾ�Ĵ�����뵽ģ����,����˱�����������ʾ
2004.5.22 ����˱�������������ʾ
2004.5.23 ѧϰ��object�� filter ����,����˹���ʱ����������������.
2004.5.24 ��JR��VC������ı�ṹ��ʱ�����˶����ʱ����������,��ͼѰ�ҽ���취
2004.5.25 ����Ѱ�ҽ���취
2004.5.26 ����˹���ʱ¼�뵥Ԫ���ܵ������ͱ�����������.
2004.5.27 ��������˺���checkreport���������˹�ʽ�Ķ����ִ�����⡣
	  ��������Ϣ���ڣ�����˼��ؽ�����ʾ�������Ϣ��ʾ���⡣
2004.5.28 ���������¼������ݰ�������
2004.5.30 �������Ŀ���������ʾ��������Ŀ��¼������
	  �������Ŀ���XML���ݰ������ɺ����ݼ�������
	  �����¼�����ƶ��ͻس����Ŀ�������
2004.5.31 ����������ͱ�������ʾ�������
2004.6.1  ����˲��������¼������
2004.6.5  ����˲����������ݼ�������

2004.6.8  �������˹�ʽ�����������print����
	  ����˲����������Ŀ�����¶�����ʱ,�����������������������
2004.6.9  ����˱����ݵĶ�ȡ���ύ�ӿ� ͨ�� XMLHTTP��jspҳ�潻��XML����
2004.6.10 Fix�˼���BUG
2004.6.11 Fix ����ʱ������������BUG
	  ����˹�����Ҽ��ƶ�����
2004.6.14 Fix ¼���귭���������ҵ�����
2004.6.22 ������XW��д����ɫ�������
2004.6.23 ��������Ϣ���ڴ�ӡ����
2004.6.25 ���ֽ��IE5������
2004.6.26 �����tabpane.js��tooltip.js��IE5������
2004.6.28 ����˲�������IE5���е�����   // var se=doucment.createElement('select'); ��Ӧ���� this.parentNode.addChild(se);
2004.6.29  �������IE5�����ⲻ��refresh������
	   �ҵ��˳�������˳���ԭ����_zlAndbodyRemove()����������

2004.7.5  �ɹ��Ľ����IE5����ʾ�������������Բ�������
2004.7.8  ������HYH�ṩ����������ṹ��Ϣ�ĺ���
2004.7.12 �޸���pub���������ĵ�outset��ʾ
2004.7.13 �޸����������ͱ������ĵ�λ�ʹ����ѡ��ʾ
2004.7.19 ������NC��������ӡ�������޸�Ϊ�ڴ�ӡ����ֱ�����ɵ�DIV��������������������ʱ�����ϵ�ռ����Դ
2004.7.20 �޸�����Ŀ��Ͳ���������������ݲ��ܴ�ӡ����
2004.7.24  ������readOnly ����
2004.7.24  ������������չ���
2004.8.2  ������Ŀ101��ʾ����
2004.8.27 XML�м��밴�л��е��������ͺ�С��λ�Ŀ���
2004.8.27 ������������ʽʵ��cell���������ͺ�С��λ�Ŀ���
2004.8.28 ��������ʷ���ݵĵ��빦��

 ------------------------------------------------------------------------*/

function StatsReport(name,top,left,width,height,parent)   //���캯��
{

  window.moveTo(0,0)
  window.resizeTo(screen.availWidth,screen.availHeight)
 var pdiv,obj=document.createElement('Div');//����һ�����ƽڵ�
 if(parent){pdiv=parent;}else pdiv=document.body;//��ȡ����
  pdiv.appendChild(obj);
 obj.style.overflow = 'hidden';
 //obj.style.position='absolute';
  if(left) obj.style.left=left;
  if(top) obj.style.top=top;
  if(width) obj.style.width=width;
  if(!height)  height=500;
  //obj.style.height=height;
  obj.className='tab-pane';
  if(name){var s='window.'+name+'=obj';eval(s);}
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

  obj.stPacketUrl =null;    //��ṹ��Url
  obj.dataPacketUrl =null;    //��ṹ��Url

  obj.openDataUrl =null;       //���ݶ�ȡ��JSPҳ��ָ��
  obj.postDataUrl =null;       //�����ύ��JSPҳ��ָ��

  obj.stPacketNode =null;      //��ṹ���ڵ�
  obj.dataPacketNode = null ;  //�����ݰ��ڵ�
  obj.newDataPacketNode =null;  //�µ����ݰ�
  obj.dataQueryNode =null;
  obj.readOnly=false;      //����ֻ�������ģʽ
  obj.clearReportsData= SC_cleareportsrdata;   //�屨�����������

  obj.menuBar = new menu_bar(top-28,left,width);
  obj.popupMenu = new menu();

  obj.colorMenu = new menu();
  obj.colorMenu.add("��","js", "SetSkinColor(1);");
  obj.colorMenu.add("��","js","SetSkinColor(2);");
  obj.colorMenu.add("��","js","SetSkinColor(3);");
  obj.colorMenu.add("��","js","SetSkinColor(4);");

 /* obj.fontMenu = new menu();
  obj.fontMenu.add("��","js", "setBodyFontSize(1);");
  obj.fontMenu.add("��","js","setBodyFontSize(2);");
  obj.fontMenu.add("С","js","setBodyFontSize(3);");
 */
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

  obj.sendMenu.add("���ݱ���","js" ,"statsrptobj.postUnitData(1)");
  obj.sendMenu.add("��������ݱ���","js" ,"statsrptobj.postUnitData(0)");
  obj.sendMenu.add("�����������˳�","js","x=window.confirm('���ύ�����˳�');if(x==true) window.close();");

  obj.histroyMenu.add("��������","js" ,"statsrptobj.openUnitData('sy');");
  obj.histroyMenu.add("�ϼ�����","js" ,"statsrptobj.openUnitData('sj');");
  obj.histroyMenu.add("��������","js" ,"statsrptobj.openUnitData('sn');");

 // obj.dataMenu.add("�������ݰ�","js","statsrptobj.createDataPacket()");
//  obj.dataMenu.add("�������ݰ�","js","statsrptobj.dataPacketUrl=window.prompt();statsrptobj.getdataPacket()");
 // obj.dataMenu.add("�鿴���ݰ�","js" ,"alert(statsrptobj.newDataPacketNode.xml)");
  obj.dataMenu.add("�����Ϣ����","js","ClearInfo()");
  obj.dataMenu.seperate();
  obj.dataMenu.add("�������","js" ,"statsrptobj.clearReportsData();");
  obj.dataMenu.add("д���ݵ�����","js" ,'statsrptobj.createDataPacket();var file="c:zbdata.xml"; /*window.prompt("�ļ���","c:/zbdata.xml");*/ if(file!=null)  writeFile(file,statsrptobj.newDataPacketNode.xml)');
  obj.dataMenu.add("�ӱ��ض�����","js" ,"statsrptobj.dataPacketUrl='c:zbdata.xml';/*window.prompt('�ļ���','c:/zbdata.xml');*/if(statsrptobj.dataPacketUrl!=null) statsrptobj.getdataPacket()");
//  obj.dataMenu.seperate();
 // obj.dataMenu.add("дExcel��ʽ������","js" ,'statsrptobj.createExcelPacket();var file=window.prompt("�ļ���","c:/Exceldata.xml"); if(file!=null)  writeFile(file,statsrptobj.ExcelPacketNode.xml)');
//  obj.dataMenu.add("�����Ϣ����","js","ClearInfo()");
//obj.dataMenu.add("дbody","js" ,"writeBody()");
 // obj.dataMenu.add("���ݰ�����","js" ,"statsrptobj.openUnitData()");
 obj.printMenu.add("A4","js","statsrptobj.pageSize='a4';");
 obj.printMenu.add("A3","js","statsrptobj.pageSize='a3';");
 obj.printMenu.seperate();
 obj.printMenu.add("�����ӡ","js","statsrptobj.pageOrient='0';");
 obj.printMenu.add("�����ӡ","js","statsrptobj.pageOrient='1';;");
 obj.printMenu.seperate();
obj.gridMenu.add("������","js","statsrptobj.curReport._appendZlRow();");
 obj.gridMenu.add("ɾ����ǰ��","js", "statsrptobj.curReport._deleteZlRow(statsrptobj.curReport.focusRow);");

// obj.menuBar.add("��������",obj.fontMenu) ;
 obj.menuBar.add("����¼��",obj.popupMenu) ;
 obj.menuBar.add("�������",obj.checkMenu) ;
 obj.menuBar.add("�����Ϣ",obj.checkinfoMenu) ;
 obj.menuBar.add("��˲�����",obj.sendMenu) ;
 obj.menuBar.add("������",obj.gridMenu);
 obj.menuBar.add("������Ϣ",obj.dataMenu) ;
 obj.menuBar.add("�����ӡ",obj.printMenu) ;
// obj.menuBar.add("��ӡҳ������",obj.printSetupMenu) ;
 obj.menuBar.add("��ɫ����",obj.colorMenu) ;
 // obj.menuBar.add("��Ϣ����",obj.infoMenu) ;
 obj.menuBar.add("������ʷ����",obj.histroyMenu) ;

  obj.tabPane =null ;   //��ǩҳָ��

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
  obj.ExcelPacketNode;
////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////


 // obj.setdataPacket =SC_setdatapacket; //д�����ݰ��ڵ�
  obj.createExcelPacket =SC_createexcelpacket  //������ṹ�����ݰ�
  obj.createDataPacket =SC_createdatapacket  //���������ݰ�
  obj.createDataQuery =SC_createdataquery;  //���������ݰ���ȡ��XML�ڵ�
  obj.printViewReport =SC_printviewreport;
  obj.draw=SC_Draw;

  obj.CheckAllReport =SC_checkallreport;

  obj.createInfoDiv=SC_createinfodiv;     //������ʾ��Ϣ����

  //˽�з���
  obj._initAllReportView = SC_initallreportview;   //
  obj._initOneReportView = SC_initonereportview;

  obj._initAllReportData = SC_initallreportdata;   //
  obj._initOneReportData = SC_initonereportdata;
  obj._LoadOneReportData = SC_loadonereportdata
  obj.tabPane= new WebFXTabPane (obj);
  xyabc=xyChange(xyabc);
  if(xyabc!=xyChange(markabc)) return;
  tabpane = obj.tabPane;
  obj.onmousedown = function() {
	  if(event.button==2) this.popupMenu.show(event.clientX,event.clientY);
      else if(event.button==1) this.popupMenu.hide();
  }
  obj.createInfoDiv('info');
  statsrptobj= obj;
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
 var obj=document.createElement('Div');//����һ�����ƽڵ�
 obj.style.overflow = 'auto';
 this.appendChild(obj);
 obj.style.zIndex=1200;
 if(name){var s='window.'+name+'=obj';eval(s);}
 this.reportPtr['info'] =obj;
 this.addTabPage(obj,"��Ϣ����","����ִ�н������Ϣ����");   //����Ϣ������ӵ�tabpane��

}

function SC_printviewreport(reportname,top,left,width,height,papertype,method,parent)
{
 //�ж�ֽ����Դ����ӡΪ��������
var pwin=window.open("","print");
pwin.document.write("<body>");
pwin.document.write("<style media=print>");
pwin.document.write(".Noprint{display:none;}");
pwin.document.write(".PageNext{page-break-after: always;}");
pwin.document.write("</style>");
pwin.document.write("</body>");

 if (papertype=='a4')
 {
    if(method=='0')
	 {
 var pix_800_width=600;
 var pix_800_height=900;
	    }
    if(method=='1')
	 {
 var pix_800_width=900;
 var pix_800_height=600;
	    }
 }

 if (papertype=='a3')
 {
    if(method=='0')
	 {
 var pix_800_width=970;
 var pix_800_height=1300;
	 }
    if(method=='1')
	 {
 var pix_800_width=1300;
 var pix_800_height=970;
	 }

 }

 //var pix_800_width=640;
 //var pix_800_height=978; 800*600��,A4�����൱�����ص���
 /*1024*768 �ֱ�����210mm�൱��330px,297mm�൱��978Px.���ת����ֵ�Ǿ����������.���ǿ���������
 ���ɱ��ʡ�ԭ���Ǹ���ʾ����ҳ���㷨���ԡ�

 ʵ����,A4ֽ210mm�൱��640Px 297mm�൱��1125Px*/


//var pix_800_width=978;
//var pix_800_height=640; //800*600��,A4�����൱�����ص��� �������

 width=pix_800_width;
 height=pix_800_height;
 var curPtr=this.reportPtr[reportname];
 var blsnode=curPtr.blsNode;
 var blsCount=blsnode.childNodes.length;
 var zlsnode=curPtr.zlsNode;
 var zlsCount=curPtr.RowNum;//zlsnode.childNodes.length;
 var bls_loop_id=0;
  var pdiv,obj=pwin.document.createElement('Div');//����һ�����ƽڵ�
  if(parent){pdiv=parent;}else pdiv=pwin.document.body;//��ȡ����
  obj.style.position='relative';
  obj.style.visibility = 'hidden';
  //if(left) obj.style.left=left;
 // obj.style.top=top;
 // if(width) obj.style.width=width;
 // if(height) obj.style.hight=height;  //�˴�����߶�λ����
  pdiv.appendChild(obj);
   if(name){var s='window.'+name+'_print=obj';eval(s);}
  obj.BigTab=pwin.document.createElement('table');
  obj.BigTab.style.position='relative';
//  this.Header.style.height=16;
  obj.BigTab.border='0';
  obj.BigTab.cellSpacing="0";
  obj.BigTab.cellPanding='0';
  obj.BigTab.style.width=width;
  //obj.BigTab.style.tableLayout = 'fixed';
  obj.appendChild(obj.BigTab);
  var headrow  = obj.BigTab.insertRow();  //��ͷ����
  obj.titleTab = headrow.insertCell();
  obj.titleTab.colspan='2';

  var headrow  = obj.BigTab.insertRow();
  obj.pubTab = headrow.insertCell();      //��������
  obj.headTab = headrow.insertCell();      //��������
  var headrow  = obj.BigTab.insertRow();
  obj.zlTab = headrow.insertCell();        //��������
  obj.bodyTab = headrow.insertCell();      //���ݲ���
   obj.parent=pdiv;
  //������ͷ��

   var tab_bt =pwin.document.createElement('table');
      // tab_bt.style.tableLayout = 'fixed';
	   tab_bt.style.position='relative';
       tab_bt.border=0;
	   tab_bt.style.width="100%"
       tab_bt.cellSpacing="0";
       tab_bt.cellPanding='0';
       obj.titleTab.appendChild(tab_bt);
	var headrow  = tab_bt.insertRow();
		cell=headrow.insertCell();
		cell.align='center';
		cell.innerText= curPtr.reportTitle;
	var headrow  = tab_bt.insertRow();
		cell=headrow.insertCell();
		//cell.align='center';
		cell.innerText= curPtr.reportSubTitle;


      //������������
   var tab_bl =pwin.document.createElement('table');
   //    tab_bl.style.tableLayout = 'fixed';
	   tab_bl.style.position='relative';
       tab_bl.border='1';
       tab_bl.cellSpacing="0";
       tab_bl.cellPanding='0';
	   tab_bl.style.width='100%';
       obj.headTab.appendChild(tab_bl);
      var colCount=blsnode.childNodes.length;
      var data=new Array();
      for(var i=0;i<blsCount;i++)
	  {
       data[i]=new Array();
       var subnode=blsnode.childNodes.item(i);
       var inputStr= subnode.getAttribute("xm");
       var str=inputStr.split("/");
       var part_num=0;
       while (part_num <str.length)
       {
	 data[i][part_num]=str[part_num];
	 part_num++;
	}
       part_num--;
       data[i][part_num++]=subnode.getAttribute("dm");
       data[i][part_num++]=subnode.getAttribute("dw");
       var rowCount=part_num;
	    }

       var blCells = new Array();
       for(var i=0;i<rowCount;i++)
       {
	var headrow  = tab_bl.insertRow();
	 blCells[i]=new Array();
	     for(var j=0;j<blsCount;j++)
	     {
	      var cell=headrow.insertCell();
	      cell.align='center';
		  cell.noWrap=true;
		  blCells[i][j]=cell;
	      //blCells[i][j].borderStyle="outset";
	      blCells[i][j].borderColorLight="#FFFFFF";//skinFaceColor;
	      //this.blCells[i][j].style.width=100;

	 }
       }

       var bl_data=new Array();
       for(var i=0;i<rowCount;i++)
	   {
	 bl_data[i]=new Array();
	     for (var j=0;j<blsCount;j++ )
	 {
	   bl_data[i][j]=data[j][i];
	   blCells[i][j].innerText=bl_data[i][j];
	  }
       }

 rowCount =rowCount-1 ; //������λ�ϲ�

 for (var i=0;i<rowCount;i++)
 {
 for (var j=0;j<blsCount;j++)
 {

 if(i<rowCount-1 && bl_data[i][j]==bl_data[i+1][j])
	 {
      blCells[i][j].style.borderBottomStyle="none"; //ȥ����Ԫ����
      blCells[i+1][j].style.borderTopStyle="none";
      blCells[i+1][j].innerText=" ";
	 }
   if(j<blsCount-1 && bl_data[i][j]==bl_data[i][j+1])
	 {
     blCells[i][j].style.bordeRightStyle="none";
     blCells[i][j+1].style.borderLeftStyle="none";
     blCells[i][j+1].innerText=" ";

	 }
	if(bl_data[i][j]=="")
	 {
      blCells[i][j].style.borderTopStyle="none";
      blCells[i][j].innerText=" ";
	 }

  }
 }

   //������������
   var tab_pub =pwin.document.createElement('table');
     //  tab_pub.style.tableLayout = 'fixed';
	   tab_pub.style.position='relative';
       tab_pub.border='1';
	   tab_pub.style.width='100%';
	   tab_pub.style.height=tab_bl.offsetHeight;
       tab_pub.cellSpacing="0";
       tab_pub.cellPanding='0';
       obj.pubTab.appendChild(tab_pub);
	var headrow  = tab_pub.insertRow();
	    var pubnode= curPtr.publicsNode;
	var pub_Data=new Array();
		 for(i=0;i<pubnode.childNodes.length-1;i++)
	     {
		  var cell=headrow.insertCell();
		  var subnode=pubnode.childNodes.item(i);
	  cell.innerText =  subnode.getAttribute("name");
		  pub_Data[i]=subnode.getAttribute("name");
		  cell.align ='center';
		  cell.noWrap=true;
		 }




 //��������
  var tab_zl =pwin.document.createElement('table');
     //  tab_zl.style.position='relative';
     //  tab_zl.style.tableLayout = 'fixed';
	   tab_zl.border='1';
	   tab_zl.style.width='100%';
       tab_zl.cellSpacing="0";
       tab_zl.cellPanding='0';
       obj.zlTab.appendChild(tab_zl);
  var zl_Data=new Array();
  for(var zi=0;zi<curPtr.RowNum;zi++)
	{
      var headrow = tab_zl.insertRow();
      var cell =headrow.insertCell();
      cell.innerText=curPtr.zlCells[zi][0].value;
	  cell.noWrap=true;
	  var cell =headrow.insertCell();
     cell.innerText=curPtr.zlCells[zi][1].value;
	 cell.noWrap=true;
	 var cell =headrow.insertCell();
     cell.innerText=curPtr.zlCells[zi][2].value;
	 cell.noWrap=true;
	}

   //���ݲ���
  var tab_data =pwin.document.createElement('table');

       obj.bodyTab.appendChild(tab_data);
    //   tab_data.style.tableLayout = 'fixed';
	   tab_data.border='1';
       tab_data.style.width='100%';
//	   tab_data.style.pixelHeight=tab_zl.offsetHeigth;;
	   tab_data.cellSpacing="0";
       tab_data.cellPanding='0';

  for(var row=0;row<curPtr.RowNum;row++)
	{
     var headrow = tab_data.insertRow();
      for(var col=0;col<curPtr.ColNum;col++)
		{
	 var cell =headrow.insertCell();
	 if(curPtr.Cells[row+1][col+1].value=='')
			 cell.innerText='  ';
		 else
			 cell.innerText=curPtr.Cells[row+1][col+1].value;
		}
	}

/**************************************************��������*/
tab_pub.style.height=tab_bl.offsetHeight;
//tab_pub.style.width=tab_zl.offsetWidth;
tab_pub.border='1';
tab_pub.cellSpacing='0';
tab_pub.cellPanding='0';
for(var i=0;i<3;i++)
	{
tab_pub.rows(0).cells(i).style.width=tab_zl.rows(0).cells(i).offsetWidth;
tab_zl.rows(0).cells(i).style.width=tab_pub.rows(0).cells(i).style.width;
	}
/*obj.pubTab.style.width=wl+'mm';
tab_pub.style.width=wl+'mm';
tab_zl.style.width=wl+'mm';
obj.headTab.style.width=wl+'mm';
//PUB������������
*/

//tab_data.style.position='relative';
for(var i=0;i<colCount;i++)
	if(curPtr.RowNum>0){
tab_data.rows(0).cells(i).style.width=tab_bl.rows(0).cells(i).offsetWidth;
tab_bl.rows(0).cells(i).style.width=tab_data.rows(0).cells(i).style.width;
	}

for(var i=0;i<curPtr.RowNum;i++)
	{
    tab_data.rows(i).cells(0).style.pixelHeight=tab_zl.rows(i).cells(0).offsetHeight;
    }

//���������������



var H_page=Math.floor(tab_bl.offsetWidth/(pix_800_width-tab_pub.offsetWidth))+1;
width=(tab_bl.offsetWidth/H_page)+tab_pub.offsetWidth;

var V_page=Math.floor(tab_zl.offsetHeight/(pix_800_height-tab_pub.offsetHeight))+1;
height=(tab_zl.offsetHeight/V_page)+tab_pub.offsetHeight;


obj.innerHTML=" ";
/*pdiv.removeChild(obj);
//obj.removeNode(true);

  while(obj.childNodes.length>0){
   var cn = obj.childNodes[0];
   obj.removeChild(cn);
   cn.removeNode(true);
  }
 */
/*var pwin=window.open("","print");
pwin.document.write("<body>");
pwin.document.write("<style media=print>");
pwin.document.write(".Noprint{display:none;}");
pwin.document.write(".PageNext{page-break-after: always;}");
pwin.document.write("</style>");
pwin.document.write("</body>");
*/var drawResult=this.draw(reportname,top,left,width,height,pwin,H_page,V_page);
//var odoc=drawResult.innerHTML;
/*var pwin=window.open("","print");
pwin.document.write("<style media=print>");
pwin.document.write(".Noprint{display:none;}");
pwin.document.write(".PageNext{page-break-after: always;}");
pwin.document.write("</style>");
pwin.document.write(odoc);
*/
//pwin.document.print();

//�ڴ˼���������汾��֤
pwin.print();
alert("��ӡ���һ���رմ���");
pwin.close();

}
function SC_Draw(reportname,top,left,width,height,pwin,H_page,V_page)
{
 //var pdiv,allObj=pwin.document.createElement('Div');//����һ�����ƽڵ�
 pdiv=pwin.document.body;//��ȡ����
// if(reportname){var s='window.'+reportname+'_draw=obj';eval(s);}
// pdiv.appendChild(allObj);
 var curPtr=this.reportPtr[reportname];
 var blsnode=curPtr.blsNode;
 var blsCount=blsnode.childNodes.length;
 var zlsnode=curPtr.zlsNode;
 var zlsCount=curPtr.RowNum;//zlsnode.childNodes.length;
 var loop_bl_id=Math.floor(blsCount/H_page);
 H_page=Math.floor(blsCount/loop_bl_id);
 if(blsCount%loop_bl_id!=0)H_page=H_page+1;
 var lastBl=blsCount%loop_bl_id;//��������ҳ����
 var loopBl=loop_bl_id;
 var loop_zl_id=Math.floor(zlsCount/V_page);
 V_page=Math.floor(zlsCount/loop_zl_id);
 if(zlsCount%loop_zl_id!=0)V_page=V_page+1;
 var lastZl=zlsCount%loop_zl_id;//��������ҳ����
 var loopZl=loop_zl_id;
 var pages;
 var vpages;

for(vpages=0;vpages<V_page;vpages++)
	{

    if(lastZl!=0)         //��������һҳ
		{
  if(vpages==V_page-1)loop_zl_id=lastZl;
  if(vpages!=V_page-1)loop_zl_id=loopZl;
	}

	for(pages=0;pages<H_page;pages++)
	{
  if (lastBl!=0)         //��������һҳ
  {
  if(pages==H_page-1)loop_bl_id=lastBl;
  if(pages!=H_page-1)loop_bl_id=loopBl; //�ж��Ƿ������һҳ
   }
  var obj=pwin.document.createElement('Div');//����һ�����ƽڵ�
  obj.style.position='relative';
  var ppages=vpages*H_page+pages+1
  if(left) obj.style.left=left;
  if(width) obj.style.width=width;
  if(height) obj.style.hight=height;
  pdiv.appendChild(obj);
  if(reportname){var s='window.'+reportname+pages+'=obj';eval(s);}
  obj.BigTab=pwin.document.createElement('table');
  obj.BigTab.style.position='relative';

  obj.BigTab.border='0';
  obj.BigTab.cellSpacing="0";
  obj.BigTab.cellPanding='0';
  obj.BigTab.style.width='100%';
  //obj.BigTab.style.tableLayout = 'fixed';
  obj.appendChild(obj.BigTab);
  var headrow  = obj.BigTab.insertRow();  //��ͷ����
  obj.titleTab = headrow.insertCell();
  obj.titleTab.colspan='2';
  var headrow  = obj.BigTab.insertRow();
  obj.pubTab = headrow.insertCell();      //��������
  obj.headTab = headrow.insertCell();      //��������
  var headrow  = obj.BigTab.insertRow();
  obj.zlTab = headrow.insertCell();        //��������
  obj.bodyTab = headrow.insertCell();      //���ݲ���
  obj.parent=pdiv;
  //������ͷ��

   var tab_bt =pwin.document.createElement('table');
       tab_bt.style.position='relative';
       tab_bt.border=0;
	   tab_bt.style.width="100%"
	//   tab_bt.style.tableLayout = 'fixed';
       tab_bt.cellSpacing="0";
       tab_bt.cellPanding='0';
       obj.titleTab.appendChild(tab_bt);
	var headrow  = tab_bt.insertRow();
		cell=headrow.insertCell();
		cell.align='center';
		cell.innerText= curPtr.reportTitle+"   ��"+ppages+"ҳ  ��"+V_page*H_page+"ҳ";
	var headrow  = tab_bt.insertRow();
		cell=headrow.insertCell();
		//cell.align='center';
		cell.innerText= curPtr.reportSubTitle;

      //������������
    var tab_bl =pwin.document.createElement('table');

	   tab_bl.style.position='relative';
       tab_bl.border='1';
       tab_bl.cellSpacing="0";
       tab_bl.cellPanding='0';
	   tab_bl.style.width='100%';
//	   tab_bl.style.tableLayout = 'fixed';
       obj.headTab.appendChild(tab_bl);
      var colCount=blsnode.childNodes.length;

      var data=new Array();

	  for(var i=0;i<loop_bl_id;i++)
	  {
       data[i]=new Array();
       var subnode=blsnode.childNodes.item(i+pages*loopBl);
       if(subnode==null)break;
	   var inputStr= subnode.getAttribute("xm");
       var str=inputStr.split("/");
       var part_num=0;
       while (part_num <str.length)
       {
	 data[i][part_num]=str[part_num];
	 part_num++;
	}
       part_num--;
       data[i][part_num++]=subnode.getAttribute("dm");
       data[i][part_num++]=subnode.getAttribute("dw");
       var rowCount=part_num;
	    }
       var blCells = new Array();
       for(var i=0;i<rowCount;i++)
       {
		var headrow  = tab_bl.insertRow();
	 blCells[i]=new Array();
	     for(var j=0;j<loop_bl_id;j++)
	     {
	      var cell=headrow.insertCell();
	      cell.align='center';
		  cell.noWrap=true;
		  blCells[i][j]=cell;
	      //blCells[i][j].borderStyle="outset";
	      blCells[i][j].borderColorLight="#FFFFFF";//skinFaceColor;
	      //this.blCells[i][j].style.width=100;
		   }
       }

       var bl_data=new Array();
       for(var i=0;i<rowCount;i++)
	   {
	 if(subnode==null)break;
		 bl_data[i]=new Array();
	     for (var j=0;j<loop_bl_id;j++ )
	 {
	 bl_data[i][j]=data[j][i];
	 blCells[i][j].innerText=bl_data[i][j];
		  }
       }

 rowCount =rowCount-1 ; //������λ�ϲ�

 for (var i=0;i<rowCount;i++)
 {
 for (var j=0;j<loop_bl_id;j++)
 {

 if(i<rowCount-1 && bl_data[i][j]==bl_data[i+1][j])
	 {
      blCells[i][j].style.borderBottomStyle="none"; //ȥ����Ԫ����
      blCells[i+1][j].style.borderTopStyle="none";
      blCells[i+1][j].innerText=" ";
	 }
   if(j<loop_bl_id-1 && bl_data[i][j]==bl_data[i][j+1])
	 {
     blCells[i][j].style.bordeRightStyle="none";
     blCells[i][j+1].style.borderLeftStyle="none";
     blCells[i][j+1].innerText=" ";

	 }
	if(bl_data[i][j]=="")
	 {
      blCells[i][j].style.borderTopStyle="none";
      blCells[i][j].innerText=" ";
	 }

  }
 }
  //������������
   var tab_pub =pwin.document.createElement('table');
  /*     tab_pub.style.position='relative';
       tab_pub.border='1';
	   tab_pub.style.width='100%';
	   tab_pub.style.height=tab_bl.offsetHeight;
       tab_pub.cellSpacing="0";
       tab_pub.cellPanding='0';*/
       obj.pubTab.appendChild(tab_pub);
//       tab_pub.style.tableLayout = 'fixed';
		var headrow  = tab_pub.insertRow();
	    var pubnode= curPtr.publicsNode;
	 for(i=0;i<pubnode.childNodes.length-1;i++)
	     {
		  var cell=headrow.insertCell();
		 var subnode=pubnode.childNodes.item(i);
	  cell.innerText =  subnode.getAttribute("name");
		  cell.align ='center';
		  cell.noWrap=true;
		 }
 //��������
  var tab_zl =pwin.document.createElement('table');
     //  tab_zl.style.position='relative';
       tab_zl.border='1';
	   tab_zl.style.width='100%';
       tab_zl.cellSpacing="0";
       tab_zl.cellPanding='0';
//       tab_zl.style.tableLayout = 'fixed';
	   obj.zlTab.appendChild(tab_zl);

  for(var zi=0;zi<loop_zl_id;zi++)
	{

	 var headrow = tab_zl.insertRow();
      var cell =headrow.insertCell();
      cell.innerText=curPtr.zlCells[zi+vpages*loopZl][0].value;
	  cell.noWrap=true;
	  var cell =headrow.insertCell();
     cell.innerText=curPtr.zlCells[zi+vpages*loopZl][1].value;
	 cell.noWrap=true;
	 var cell =headrow.insertCell();
    cell.innerText=curPtr.zlCells[zi+vpages*loopZl][2].value;
	cell.noWrap=true;
	}
   //���ݲ���
  var tab_data =pwin.document.createElement('table');

       obj.bodyTab.appendChild(tab_data);
	   tab_data.border='1';
       tab_data.cellSpacing='0';
       tab_data.cellPanding='0';


//       tab_data.style.tableLayout = 'fixed';
  for(var row=0;row<loop_zl_id;row++)
	{
     var headrow = tab_data.insertRow();
      for(var col=0;col<loop_bl_id;col++)
		{
	 var cell =headrow.insertCell();
	 if(curPtr.Cells[row+1+vpages*loopZl][col+1+pages*loopBl].value=='')
			 cell.innerText='  ';
		 else
			 cell.innerText=curPtr.Cells[row+1+vpages*loopZl][col+1+pages*loopBl].value;
		}
	}

/**************************************************��������*/
tab_pub.style.height=tab_bl.offsetHeight;
//tab_pub.style.width=tab_zl.offsetWidth;
tab_pub.border='1';
tab_pub.cellSpacing='0';
tab_pub.cellPanding='0';
for(var i=0;i<3;i++)
	{
tab_pub.rows(0).cells(i).style.width=tab_zl.rows(0).cells(i).offsetWidth;
tab_zl.rows(0).cells(i).style.width=tab_pub.rows(0).cells(i).style.width;
	}
/*obj.pubTab.style.width=wl+'mm';
tab_pub.style.width=wl+'mm';
tab_zl.style.width=wl+'mm';
obj.headTab.style.width=wl+'mm';
//PUB������������
*/

//tab_data.style.position='relative';
for(var i=0;i<loop_bl_id;i++)
	{
tab_data.rows(0).cells(i).style.width=tab_bl.rows(0).cells(i).offsetWidth;
tab_bl.rows(0).cells(i).style.width=tab_data.rows(0).cells(i).style.width;
	}

for(var i=0;i<loop_zl_id;i++)
	{
    tab_data.rows(i).cells(0).style.pixelHeight=tab_zl.rows(i).cells(0).offsetHeight;
    }

//��������������
//divTop=parseInt(obj.style.top)+parseInt(tab_pub.offsetHeight)+parseInt(tab_zl.offsetHeight);
var pageObj=pwin.document.createElement('Div');
pageObj.className="PageNext";
pdiv.appendChild(pageObj);
	}//ÿҳѭ�����,����ѭ�����

	}//ÿҳѭ�����,����ѭ�����

//return allObj;
}

function checkInfo()
{
    checkErrNum++;
	var a=arguments
	var len =a.length;
	var info =a[0];
	for (var i=0;i<len-1 ; i++)
	{
      var newstr ='{'+ i+'}';
	  while(1)
		{
	     if(info.indexOf(newstr)==-1) break;
		 info=info.replace(newstr,eval(a[i+1]));
		}
	}
	AddInfo(info);
}
function print()
{
  //  checkErrNum++;
	var a=arguments
	var len =a.length;
	var info =a[0];
	for (var i=0;i<len-1 ; i++)
	{
      var newstr ='{'+ i+'}';
	  while(1)
		{
	     if(info.indexOf(newstr)==-1) break;
		 info=info.replace(newstr,eval(a[i+1]));
		}
	}
	AddInfo(info);
}
function AddInfo(info)
	{
	 var now =new Date();
	 var nowStr=DateTimeToStr(now)
	 window.info.innerHTML += '<br><font color=#0000d0>'+nowStr+' </font>';
	 window.info.innerHTML += info;
	 window.status =info;
     tabpane.setSelectedIndex(0);
     window.info.focus();
	}
function ClearInfo()
	{
	 window.info.innerHTML = '';

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

  this.tabPane.addTabPage( name );
 // name.focus();

}
/* //��tabpane�м��� tabpage
function SC_addtabpageidx(name,nameTag,nameTips,idx)
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

  this.tabPane.addTabPageIdx( name ,idx);
 // name.focus();

}*/
function SC_refresh()
{
/* var curReport=null;
	for( var i=0;i<this.reportCode.length;i++)
	{
       var  name =this.reportCode[i];
	curReport=this.reportPtr[name];
		curReport.Refresh();
	curReport.Cells[0][0].focus();
	}
*/
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
//alert(this.dataPacketUrl);
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
function SC_checkallreport()
{
    allerr=0;
	for ( var i=0;i<this.reportCount;i++ )
	{
	  var curReport=this.reportPtr[this.reportCode[i]];
	  if(curReport.type=='��Ŀ101��'||curReport.type=='4' || curReport.type=='101��'||curReport.type=='5')
		   continue;
	  allerr+= curReport.CheckReport();

	}
	return allerr;
}
function SC_createexcelpacket()
{
 this.ExcelPacketNode=new ActiveXObject('Microsoft.XMLDOM');
 var a=this.ExcelPacketNode;
 var n = a.createProcessingInstruction("xml"," version=\"1.0\" encoding=\"gb2312\"");
  a.insertBefore(n,a.firstChild);
  var root=a.createElement('all');
   a.appendChild(root);
   root.appendChild(this.stPacketNode.documentElement);
  this.createDataPacket();
  root.appendChild(this.newDataPacketNode.documentElement);
 alert(a.xml);
 return root;
}
function SC_createdatapacket()
{
 var  a=this.newDataPacketNode;
 if(a!=null && a.documentElement) a.removeChild(a.documentElement);

 this.newDataPacketNode=new ActiveXObject('Microsoft.XMLDOM');
 a=this.newDataPacketNode;
 var n = a.createProcessingInstruction("xml"," version=\"1.0\" encoding=\"gb2312\"");
  a.insertBefore(n,a.firstChild);

   el=a.createElement('reports');
   a.appendChild(el);
   var root=el;
    el=a.createElement('unit');
    root.appendChild(el);
    el=a.createElement('datelist');
    root.appendChild(el);
	//alert(this.reportCount);
	for ( var i=0;i<this.reportCount;i++ )
	{
     el=a.createElement('report');
	  var curReport=this.reportPtr[this.reportCode[i]];
	  curReport.cellNode=el;

	 el.setAttribute('code',this.reportCode[i]);
	  el.setAttribute('type',this.reportType[i]);
     root.appendChild(el);
     var rpt =el;
     el=a.createElement('zls');
     rpt.appendChild(el);
	 var zls= el;
	   for (var row=0; row<curReport.RowNum; row++)
	   {

	  if(curReport.zlCells[row][2].value=='*'||curReport.zlCells[row][1].value=='-'||curReport.zlCells[row][1].value=='��') continue;
	  if(curReport.type=='��Ŀ��'||curReport.type=='3')
		    if(curReport.zlCells[row][0].value=='' ||curReport.zlCells[row][0].value==null) continue;
       el=a.createElement('zl');
       zls.appendChild(el);
	   if(curReport.type=='��Ŀ��'||curReport.type=='3')
		  {
	   el.setAttribute('xm',curReport.zlCells[row][0].value);
	   el.setAttribute('dm',curReport.zlCells[row][1].value);
	   el.setAttribute('dw',curReport.zlCells[row][2].value);
		  }
	   else {
		   el.setAttribute('dm',curReport.zlDm[row]);
		   el.setAttribute('dw',curReport.zlDw[row]);
	   }
	   var data=null;
	    for (var col=0;col<curReport.ColNum ; col++)
	    {
		    if(col==0) data=curReport.Cells[row+1][col+1].value;
			else data+=curReport.Cells[row+1][col+1].value;
			if(col<curReport.ColNum-1) data+=',';
	    }
	    el.setAttribute('data',data);

	   }
      if(curReport.addTitle!=''){
	   e1=a.createElement('textarea');
	   rpt.appendChild(e1);
	   e1.setAttribute('name',curReport.addsTitle);
	   e1.setAttribute('value',curReport.AddsArea.innerText);
	  }
      if(curReport.CheckTitle!=''){
	   e1=a.createElement('checkresult');
	   rpt.appendChild(e1);
	   e1.setAttribute('name',curReport.CheckTitle.innerText);
	   e1.setAttribute('value',curReport.CheckArea.innerHTML);
	  }
	}
	AddInfo("���ݰ������ɣ�");
//	alert(a.xml);
	return root;
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
 if( this.readOnly==true)
	{
	 this.menuBar.item[0].disabled =true;
	 this.menuBar.item[1].disabled =true;
	 this.menuBar.item[2].disabled =true;
	 this.menuBar.item[3].disabled =true;
	 this.menuBar.item[4].disabled =true;

	}


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
function  SC_postunitdata(isCheck)    //�ύ���ݰ�
{
  if(isCheck!=0) {
   var err=this.CheckAllReport();
   if(err!=0)
	{
	  var errinfo="�����"+err+"������,���ܱ�������!";
	  alert(errinfo);
	  AddInfo(errinfo);
	 
	   return;
	}
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
function  SC_loadonereportdata(name,type,node,timetype)
{
 var zlsNode;
 var RowNum;
 var curReport;
 var obj;
 var rowData =new Array();
 if(type=='��Ŀ101��' ||type=='101��')
	{
      return;
	}

    curReport=this.reportPtr[name];

	var count = node.childNodes.length;    //�õ� <report> </report>�����ӽڵ����
  for(var i=0;i<count;i++)
	{
	  var subnode = node.childNodes.item(i);
	  switch (subnode.tagName.toUpperCase())
	  {
	   case 'ZLS':
		   zlsNode=subnode;
	       RowNum =subnode.childNodes.length;
		  // alert(name+":"+timetype+":"+RowNum);
	       break;
	  case 'ROWS':
		   zlsNode=subnode;
	       RowNum =subnode.childNodes.length;
		   break;

	  }
	}
   switch(type)
	{
	   case '������':
	   case '1':
   for(var i=0;i<RowNum; i++)
	{
      var subnode= zlsNode.childNodes.item(i);
	  var Dm= subnode.getAttribute("dm");
	  var row =curReport.zlDmIdx[Dm];
	     if (timetype=='sy')
	        curReport.Cells_SY[row+1]=  new Array();
	     else if (timetype=='sj')
	        curReport.Cells_SJ[row+1]=  new Array();
	     else if (timetype=='sn')
	        curReport.Cells_SN[row+1]=  new Array();

	  var Data =subnode.getAttribute("data");
	  rowData=Data.split(',');
	    for (var col=0;col<rowData.length;col++)
		{
	     if (timetype=='sy')
	        curReport.Cells_SY[row+1][col+1]=rowData[col];
	     else if (timetype=='sj')
	        curReport.Cells_SJ[row+1][col+1]=rowData[col];
	     else if (timetype=='sn')
	        curReport.Cells_SN[row+1][col+1]=rowData[col];
		
		 }
   }
   break;
   case '��������':
   case '2':

//	  curReport._zlAndbodyRemove();  //����������������ı��
       var row=0;
	   for (var i=0;i<RowNum; i++ )
	   {
	      var subnode= zlsNode.childNodes.item(i);
	      var dmvalue=subnode.getAttribute("dm");
	     if(dmvalue==null||dmvalue=='') continue ;
		 if( curReport.zlDmIdx[dmvalue])
		  var pos = curReport.zlDmIdx[dmvalue];
         else continue ;
		 if (timetype=='sy')
	        curReport.Cells_SY[pos]=  new Array();
	     else if (timetype=='sj')
	        curReport.Cells_SJ[pos]=  new Array();
	     else if (timetype=='sn')
	        curReport.Cells_SN[pos]=  new Array();

			var Data =subnode.getAttribute("data");
	      rowData=Data.split(',');
		 for (var col=0;col<rowData.length;col++)
		   {
		     if (timetype=='sy')
	           curReport.Cells_SY[pos][col+1]=rowData[col];
	         else if (timetype=='sj')
	           curReport.Cells_SJ[pos][col+1]=rowData[col];
	         else if (timetype=='sn')
	           curReport.Cells_SN[pos][col+1]=rowData[col];
       // curReport.Cells[pos][col+1].value=rowData[col];
		   }
       }
	   break;
   case '��Ŀ��':
   case '3':
   curReport._zlAndbodyRemove();   //����������������ı��
   var row=0;
   for(var i=0;i<RowNum; i++)
	{
      var subnode= zlsNode.childNodes.item(i);
	  var xmvalue=subnode.getAttribute("xm");
      if(xmvalue==null||xmvalue=='') continue ;
	  else row++;
		 if (timetype=='sy')
	        curReport.Cells_SY[row]=  new Array();
	     else if (timetype=='sj')
	        curReport.Cells_SJ[row]=  new Array();
	     else if (timetype=='sn')
	        curReport.Cells_SN[row]=  new Array();
	  var Data =subnode.getAttribute("data");
	  rowData=Data.split(',');
	    for (var col=0;col<rowData.length;col++)
		{
		     if (timetype=='sy')
	           curReport.Cells_SY[row][col+1]=rowData[col];
	         else if (timetype=='sj')
	           curReport.Cells_SJ[row][col+1]=rowData[col];
	         else if (timetype=='sn')
	           curReport.Cells_SN[row][col+1]=rowData[col];
	    // curReport.Cells[row][col+1].value=rowData[col];
		}
   }
    break;
 }
 AddInfo("���ر�<font color='#F00000'>"+name+"</font>����ʷ����.....���ɹ���");

}




//��ʼ����XMLDOC ����������ݰ���������ű���

function  SC_initallreportdata()
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
			     AddInfo("��д������");
			     break;

		}else{ this._initOneReportData(node.getAttribute("code"),node.getAttribute("type"),node);

		}
	}

	}

     tabpane.setSelectedIndex(1);


}
function  SC_initonereportdata(name,type,node)
{
 var zlsNode;
 var RowNum;
 var rptType;
 var curReport;
 var obj;
var firstload =true;
 var rowData =new Array();
 if(type=='��Ŀ101��')
	{
	 //����Ŀ101��
       if(this.reportPtr[name]==null) {
	    obj=document.createElement('Div');//����һ�����ƽڵ�
	    obj.style.overflow = 'auto';
	    this.appendChild(obj);
	   if(name){var s='window.'+name+'=obj';eval(s);}
	   this.reportPtr[name] =obj;
	   this.addTabPage(obj,"��Ŀ��Ϣ",name);   //����Ŀ101������ӵ�tabpane��
	       rptType=type;
	   }
	}
	else  if(type!='101��'){
    curReport=this.reportPtr[name];
    rptType=curReport.type;


    curReport.cellNode=node;
     firstload =false;
	}
	else rptType =type;
 AddInfo("���ڼ��ر�<font color='#F00000'>"+name+"</font>������.....����ȴ�");


	var count = node.childNodes.length;    //�õ� <report> </report>�����ӽڵ����
  for(var i=0;i<count;i++)
	{
	  var subnode = node.childNodes.item(i);
	  switch (subnode.tagName.toUpperCase())
	  {
	   case 'ZLS':
		   zlsNode=subnode;
	       RowNum =subnode.childNodes.length;
	       break;
	  case 'ROWS':
		   zlsNode=subnode;
	       RowNum =subnode.childNodes.length;
		   break;
	  case 'TEXTAREA':
		     curReport.addsTitle= subnode.getAttribute("name");
	         curReport.AddsArea.innerText = subnode.getAttribute("value");
        break;
	  case 'CHECKRESULT':
		     curReport.CheckTitle.style.visibility='visible';
		     curReport.CheckArea.style.visibility='visible';
		     curReport.CheckTitle.innerText= subnode.getAttribute("name");
	         curReport.CheckArea.innerHTML = subnode.getAttribute("value");
        break;

	  }
	}
   switch(rptType)
	{
	   case '������':
	   case '1':
   for(var i=0;i<RowNum; i++)
	{
      var subnode= zlsNode.childNodes.item(i);
	  var Dm= subnode.getAttribute("dm");
	  var row =curReport.zlDmIdx[Dm];
	  var Data =subnode.getAttribute("data");
	  rowData=Data.split(',');
	    for (var col=0;col<rowData.length;col++)
		{
	     curReport.Cells[row+1][col+1].value=rowData[col];
		 }
   }
   break;
   case '��������':
   case '2':

	  curReport._zlAndbodyRemove();  //����������������ı��
       var row=0;
	   for (var i=0;i<RowNum; i++ )
	   {
	      var subnode= zlsNode.childNodes.item(i);
	      var dmvalue=subnode.getAttribute("dm");
	  if(dmvalue==null||dmvalue=='') continue ;
	      else row++;
		  var pos = curReport.zlDmRawIdx[dmvalue];
          curReport.zlDmIdx[dmvalue]=row-1;
		curReport.zlCells[row-1][0].value= curReport.zlXm[pos];
	      curReport.zlCells[row-1][1].value= curReport.zlDm[pos];
	      curReport.zlCells[row-1][2].value= curReport.zlDw[pos];
	    
		  curReport.zlDmIdx[dmvalue]=row-1;
          curReport.zlattr[row-1]= curReport.zlRawattr[pos];
		  curReport.zldec[row-1]=curReport.zlRawdec[pos];

	var Data =subnode.getAttribute("data");
	      rowData=Data.split(',');
		 for (var col=0;col<rowData.length;col++)
		  {
	     curReport.Cells[row][col+1].value=rowData[col];
		   }
	    if(i==RowNum-1 ) continue;
			curReport._appendZlRow();
       }
	   break;
   case '��Ŀ��':
   case '3':
   curReport._zlAndbodyRemove();   //����������������ı��
   var row=0;
   for(var i=0;i<RowNum; i++)
	{
      var subnode= zlsNode.childNodes.item(i);
	  var xmvalue=subnode.getAttribute("xm");
      if(xmvalue==null||xmvalue=='') continue ;
	  else row++;
	  curReport.zlCells[row-1][0].value= subnode.getAttribute("xm");
	  curReport.zlCells[row-1][1].value= subnode.getAttribute("dm");
	  curReport.zlCells[row-1][2].value= subnode.getAttribute("dw");
	  var Data =subnode.getAttribute("data");
	  rowData=Data.split(',');
	    for (var col=0;col<rowData.length;col++)
		{
	     curReport.Cells[row][col+1].value=rowData[col];
		 }
	    if(i==RowNum-1) continue;
			curReport._appendZlRow();
   }
    break;
	case '��Ŀ101��':
	case '4':
	if(firstload == false ) break;
  //   alert(RowNum);
	/* var tabname=name+'_table';
	 var s='window.'+tabname+'=tabobj';eval(s);
	 if(tabobj!=null) {
		 obj.removeChild(tabobj);
	     tabobj=null;
	 }*/
     var tab_data =document.createElement('table');

       obj.appendChild(tab_data);
	   tab_data.border='1';
       tab_data.cellSpacing='0';
       tab_data.cellPanding='0';
	  // tab_dat.id =tabname;


     var headrow = tab_data.insertRow();
	 var cell =headrow.insertCell();
     cell.innerText="����";
	 var cell =headrow.insertCell();
     cell.innerText="����";
	 var cell =headrow.insertCell();
     cell.innerText="ֵ";

    for(var i=0;i<RowNum; i++)
	{
      var subnode= zlsNode.childNodes.item(i);

     var headrow = tab_data.insertRow();
	 var cell =headrow.insertCell();
	 var id=subnode.getAttribute("id");
     cell.innerText=id;
	 
	 var cell =headrow.insertCell();
	 var name= subnode.getAttribute("name");
     cell.innerText=name;
	 var cell =headrow.insertCell();
	 var value =subnode.getAttribute("value");
     cell.innerText=value;
	
	 if(id!="") this.UserInfo[id]=subnode.getAttribute("code");
	 if(name=='��Ŀ����')  { this.xmName=value; /*alert(this.xmName); */}
    }

	for( var i=0;i<this.reportCode.length;i++)
	{
	  if(this.reportType[i]=='��Ŀ��' || this.reportType[i]=='3')
		{
	      var cur=this.reportPtr[this.reportCode[i]];
	      cur.zlCells[0][0].innerText=this.xmName;
		}
	}
	
	break;
	case '101��':
	case '5':

    for(var i=0;i<RowNum; i++)
	{
      var subnode= zlsNode.childNodes.item(i);
      var id=subnode.getAttribute("id");
	 if(id!="") this.UserInfo[id]=subnode.getAttribute("code");
    }
	break;

 }
 AddInfo("���ر�<font color='#F00000'>"+name+"</font>������.....���ɹ���");

}
function Math.user(id)
{
  return  statsrptobj.UserInfo[id];
}
function Math.debug(str)
{
 if(err==0) { checkWarningNum++;  }
 else if(err==1)  checkErrNum++;
 AddInfo(str+':'+errInfo[err]);
}
function Math.dec(number,decnum)
{
	return Math.round(number*Math.pow(10,decnum))/Math.pow(10,decnum);
}

function SC_cleareportsrdata()  //������б��������
{
  for(var i=0;i< this.reportCount;i++)
	{
	  var  curReport=this.reportPtr[this.reportCode[i]];
	 // curReport.CheckTitle.innerText="";
	  curReport.CheckArea.innerHTML ="";

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
 if( this.readOnly==true)
	{
	 this.menuBar.item[0].disabled =true;
	 this.menuBar.item[1].disabled =true;
	 this.menuBar.item[2].disabled =true;
	 this.menuBar.item[3].disabled =true;
	 this.menuBar.item[4].disabled =true;

	}

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
 if( this.readOnly==true)
	{
	 this.menuBar.item[0].disabled =true;
	 this.menuBar.item[1].disabled =true;
	 this.menuBar.item[2].disabled =true;

	}
  var root=this.stPacketNode.documentElement;
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
 this.checkMenu.seperate();
 this.printMenu.seperate();
 this.checkMenu.add("������б���","js","statsrptobj.CheckAllReport();");
 this.printMenu.add("��ӡ���б���");
 tabpane.setSelectedIndex(1);
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
  obj.zlDmRawIdx = new Array();
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

  obj.blCells ;                      //�����ĵ�Ԫ��
  obj.blattr =new Array();
  obj.bldec= new Array() ;
  obj.zlattr =new Array();
  obj.zldec= new Array() ;

  obj.zlRawattr =new Array();
  obj.zlRawdec= new Array() ;

  obj.checks="";
  obj.checksText ="";     //�������˱����˱��ʽ
  obj.publicsNode =null;     //���������沿�ֵĽڵ�
  obj.blsNode =null;         //�������ֵĽڵ�
  obj.zlsNode =null;          //�������ֵĽڵ�
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

//���з���
//  obj.SetDataSet = DG_setdataset;    //�������ݿ�
  obj.Show = DG_show;                //������ʾ������ʼ����ı�
//  obj.DeleteRow = DG_deleterow;      //ɾ��һ��ָ������
    obj.Refresh = DG_refresh;          //��ṹˢ�£������ṹ���¹���
//	obj.onresize=DG_refresh;
 // obj.AppendRow = DG_appendrow;      //���һ������
 // obj.AppendRows = DG_appendrows;      //��Ӷ������
 // obj.HandleMessage = DG_handlemessage;//��׽��Ϣ
 // obj.UpdateRow = DG_updaterow;        //������
 // obj.Clear = DG_clear;  //�������е���������
 // obj.ClearRows = DG_clearrows; //�������е����е���
  obj.SetHeadColor =DG_setheadcolor;  //�����ṹ��ɫ
  obj.CheckReport = DG_checkreport;
  obj.CheckReportInfo = DG_checkreportinfo;
  obj.PrintReport = DG_printreport;
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
//  obj._ArrayRemove = _arrayremove;  //�������鴦����������ɾ��ָ����Ԫ��
//  obj._GetDataSetInfo = _DG_getdatasetinfo; //������ݿ���Ϣ
//  obj._ValidFieldName = _DG_validfieldname; //У���ֶ�
  obj._ComputHIsView = _DG_computHisview;  //���㵱ǰ���Ƿ��ڿ�������
  obj._ComputVIsView = _DG_computVisview;  //���㵱ǰ��Ԫ���Ƿ��ڿ�������
//  obj.onpropertychange = _DG_onpropertychange;
 // obj._CreateRow = DG_N_Row;
 // obj.StateCells = new Array();
 // obj.Rows = new Array();
 // obj.Captions = new Array();
//  obj.Cols = new Columns(pdiv);
 // obj.Columns = obj.Cols;
//  obj.Columns.Auto = true; //�Ƿ���ʾ���е���
 // obj.Cols.Parent = obj;         //ȡ�ø��׽ڵ�
//  obj.OnCellClick;
 // obj.OnCellKeyDown;
 // obj.OnCellFocus;
 // obj.OnCellBlur;
 // obj.OnCellDblClick;
 // obj.OnCustomCell;
  //��������Ŀ飬��ʼ����˽�з���
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
  this.addTabPage(obj,name,obj.reportTitle+'<br>'+obj.reportSubTitle);   //������ӵ�tabpane��
 // this.popupMenu.add(obj.reportTitle,"js","tabpane.setSelectedIndex("+idx+")");
 // this.checkMenu.add(obj.reportTitle,"js",name+".CheckReport()");
 // this.printMenu.add(obj.reportTitle,"js",name+".PrintReport()");
  this.popupMenu.add("�:"+name,"js","tabpane.setSelectedIndex("+idx+")");
  this.checkMenu.add("���:"+name,"js",name+".CheckReport()");
  this.checkinfoMenu.add("�鿴:"+name,"js",name+".CheckReportInfo()");
   this.printMenu.add("��ӡ:"+name,"js",name+".PrintReport()");
  //this.popMenu.add(obj.reportTitle);
  obj.Show();
 // obj.Refresh();
 AddInfo("���ر�<font color='#F00000'>"+name+"</font>�Ľṹ.....�ɹ���");
 return obj;
}
function  DG_checkreport( )
{
 var selectedIndex=tabpane.getSelectedIndex();
 if(selectedIndex==null) selectedIndex=0;
 err=1;
 checkErrNum=0;
 checkWarningNum=0;
 var checkStr = this.checksText;
 if(checkStr)  {
 if(checkStr.indexOf("_SY")!=-1 && statsrptobj.isLoadSY==0 )
	{
	 //������������
	 statsrptobj.openUnitHistoryData('sy');
	 statsrptobj.isLoadSY=1;
	}
 if(checkStr.indexOf("_SJ")!=-1 && statsrptobj.isLoadSJ==0 )
	{
	 //�����ϼ�����
	 statsrptobj.openUnitHistoryData('sj');
	 statsrptobj.isLoadSJ=1;
	}
 if(checkStr.indexOf("_SJ")!=-1 && statsrptobj.isLoadSN==0 )
	{
	 //������������
	 statsrptobj.openUnitHistoryData('sn');
	 statsrptobj.isLoadSN=1;
	}
 ClearInfo();
 eval(checkStr);
 }
 //alert(checkStr);
// alert(curindex);
 if(checkWarningNum) AddInfo("��"+this.name+"�����<font color =#f00000><b>"+checkWarningNum+"</b></font>�������Դ�������");
 if(checkErrNum==0) AddInfo("<font color =#f00000>��"+this.name+"���ͨ��!</font>");
 else  AddInfo("��"+this.name+"�����<font color =#f00000><b>"+checkErrNum+"</b></font>������,���δͨ��!");

 this.CheckTitle.style.visibility='visible';
 this.CheckArea.style.visibility='visible';
 this.CheckArea.innerHTML= window.info.innerHTML;
 //this.CheckTitle.style.visibility='visible';
// this.CheckArea.style.visibility='visible';
// this.CheckArea.style.visibility='visible';
/*  if (this.addsTitle=='')
    {
		this.AddsTitle.style.visibility='hidden';
		this.Addsdiv.style.visibility='hidden';
    }*/
tabpane.setSelectedIndex(selectedIndex);
 return checkErrNum;
}

function CheckInfo(str)
{
 if(err==0) { checkWarningNum++;  }
 else if(err==1)  checkErrNum++;
 AddInfo(str);
}
function DG_checkreportinfo( )
{
var checks=this.checks;
var odoc=this.CheckArea.innerHTML;
var pwin=window.open("",this.name+"�����Ϣ��ӡ");
pwin.document.write("<body>");
pwin.document.write("<font color=red> ��˹�ʽ:</font><br>");
pwin.document.write(this.checks);
pwin.document.write("<br><font color=red> ��˽��:</font><br>")
pwin.document.write(odoc);
var OLECMDID = 6; //6
var PROMPT = 1; // 2 DONTPROMPTUSER
var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
//pwin.document.body.insertAdjacentHTML('beforeEnd', WebBrowser);
pwin.document.write(WebBrowser);
pwin.document.write("</body>");
//pwin.document.WebBrowser1.ExecWB(OLECMDID, PROMPT);
}
function  DG_printreport( )
{
 statsrptobj.printViewReport(this.name,0,10,600,600,statsrptobj.pageSize,statsrptobj.pageOrient);
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
     this.Bodydiv.appendChild(this.Body);

   this.Body.style.position='relative';
   this.Body.border='1';
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
  this.Zldiv.appendChild(this.ZlTab);
 this.ZlTab.style.position='relative';
  this.ZlTab.style.top=0;
//  this.ZlTab.style.width=280;
  this.ZlTab.border='1';
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
  this.Pubdiv.appendChild(this.PubTab);
  this.PubTab.style.position='relative';
  this.PubTab.style.top=0;
//  this.Header.style.height=16;
  this.PubTab.border='1';
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
  this.Headdiv.appendChild(this.Header);
  this.Header.style.position='relative';
  this.Header.style.top=0;
//  this.Header.style.height=16;
  this.Header.border='1';
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
	this.AddsTitle=document.createElement('span'); 
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
//  this.Refresh();
//   this.Body.refreah();
//   this.PubTab.refresh();
 //  this.ZlTab.refresh();
 //  this.Header.refresh();
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
  this.Addsdiv.style.top = this.Bodydiv.offsetHeight+this.Bodydiv.offsetTop+10;
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
AddInfo("������"+ this.name +"��Ԫ��....");

if(this.Table.childNodes.length>0)return;
switch(this.type) {
 case '������':
 case '1':

    for (var row=0 ;row <this.RowNum ;row++ ){
		this._appendRow(row);
    }
	break;
 }
 }
function _DG_appendrow(row)
{
var rowcolor;

 var aRow =  this.Table.insertRow();
 /*var aRow = document.createElement('tr');
	 this.Table.appendChild(aRow);*/
     if(row%2)  rowcolor=clWhite;
     else   rowcolor='#F3F3F3';
	 aRow.style.backgroundColor=rowcolor;

	 this.Cells[row+1]= new Array();
	  for(var col=0;col<this.ColNum;col++){
		  var cell=aRow.insertCell();
	     /*
		 var cell = document.createElement('td');
	      aRow.appendChild(cell);
          */
		 // cell.style.width=this.blCells[row][col].offsetWidth;
		  cell.style.width=100;
	      cell.style.height=this.ZlTab.rows(row).cells(0).offsetHeight;
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


	/*
  for(var col=0; col <this.ColNum ;col++)  
	this.Table.rows(currow).cells(col).removeChild(this.Cells[currow+1][col+1]);
   for (var row=currow; row<this.RowNum-1;row++ )
      {
 	    for(var col=0; col <this.ColNum ;col++)   {
			var parent1 = this.Table.rows(row).cells(col);
			var parent2 = this.Table.rows(row+1).cells(col);
			var color= this.Cells[row+1][col+1].style.backgroundColor;
	        parent2.removeChild(this.Cells[row+2][col+1]);
	        this.Cells[row+1][col+1]=  this.Cells[row+2][col+1];
		    this.Cells[row+1][col+1].x =this.Cells[row+1][col+1].x-1;
		    this.Cells[row+1][col+1].style.backgroundColor=color;
	        parent1.appendChild(this.Cells[row+1][col+1]);
			this.Table.rows(row).cells(col).style.backgroundColor=color;

  		}
	 
      }	
     this.Table.deleteRow(this.RowNum-1);

   */
}
function _DG_zlandbodyremove()  //�����������Ŀ�����¶�����ʱ
{
/*for (i=0; i < this.Table.rows.length; i++) {
	for (j=0; j < this.Table.rows(i).cells.length; j++) {
	    this.Table.rows(i).cells(j).removeChild(this.Cells[i+1][j+1]);
		}
}
for (i=0; i < this.ZlTab.rows.length; i++) {
	for (j=0; j < this.ZlTab.rows(i).cells.length; j++) {
	    this.ZlTab.rows(i).cells(j).removeChild(this.zlCells[i][j]);
		}
}

  while(this.Table.childNodes.length>0){
       while(this.Table.rows(0).childNodes.length>0){
	       this.Table.rows(0).deleteCell(0);
	   }

	  this.Table.deleteRow(0);
  }
  while(this.ZlTab.childNodes.length>0){
        while(this.ZlTab.rows(0).childNodes.length>0){
	       this.ZlTab.rows(0).deleteCell(0);
	   }

	  this.ZlTable.deleteRow(0) 
	}

 this.RowNum=0;
 //this._appendZlRow();
*/}

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
      this.blCells[i+1][j].style.borderTopStyle="none";
      this.blCells[i+1][j].innerText=" ";
	 }
   if(j<colCount-1 && bl_data[i][j]==bl_data[i][j+1])
	 {
     this.blCells[i][j].style.bordeRightStyle="none";
     this.blCells[i][j+1].style.borderLeftStyle="none";
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
    // this.zlDmIdx[this.zlDm[i]] =i;
     this.zlDmRawIdx[this.zlDm[i]] =i;
     this.zlDw[i] = subnode.getAttribute("dw");
     this.zlRawattr[i]=subnode.getAttribute("attr");
     this.zlRawdec[i]=subnode.getAttribute("dec");
	}
	 this.RowNum=0;
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
if(currow=="undefined") return ;
switch(this.type) {
 case '������':
 case '1':
	   break;
 case '��������':
 case '2':
 case '��Ŀ��':
 case '3':
	 x=window.confirm('Ҫɾ���� '+currow+' ����');if(x==false) return;
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
 p.zlDmIdx[p.zlDm[this.selectedIndex-1]]=this.Brother.x;
 p.zlattr[this.Brother.x]= p.zlRawattr[this.selectedIndex-1];
 p.zldec[this.Brother.x]=p.zlRawdec[this.selectedIndex-1];
 
 p.zlCells[this.Brother.x][this.Brother.y].selectedIdx=this.selectedIndex;
 p.zlCells[this.Brother.x][this.Brother.y+1].selectedIdx=this.selectedIndex;
}
if(this.Brother.y==1) {
 p.zlCells[this.Brother.x][this.Brother.y-1].innerText=p.zlXm[this.selectedIndex-1];
 p.zlCells[this.Brother.x][this.Brother.y].innerText=this.options[this.selectedIndex].value;
 p.zlCells[this.Brother.x][this.Brother.y+1].innerText=p.zlDw[this.selectedIndex-1];
 p.zlDmIdx[p.zlDm[this.selectedIndex-1]]=this.Brother.x;
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
     //  cellbak.style.borderStyle ='solid';
	   cellbak.style.borderWidth =0;
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


	   if(this.ZlTab.rows(x).cells(1).innerText=='��'||this.ZlTab.rows(x).cells(1).innerText=='-'||this.ZlTab.rows(x).cells(2).innerText=='*')
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
  this.style.borderStyle ='solid';
  this.style.borderWidth='1pt';
  this.style.color='#0000DF';
  this.parent._ComputHIsView(this.x);
  this.parent._ComputVIsView(this);
  this.parent.focusRow = this.x-1;
 // this.parent.Cells[1][1].value=this.x+':'+this.y;
}
function Cell_oncellblur()//���༭��Ԫ��ʧȥ����
{
  this.style.borderWidth=0;
//  this.style.backgroundColor='#FFFFFF';
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
//	if(str=='') value='��ֵ';
	if(str=='') value=0;
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
if(fid) {
 fid.Write(str);
 fid.Close();
 infotx="��д�������ļ�"+filename+"������!";
 alert(infotx);
 }
 else alert("д�뱾��ʧ��!");
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
