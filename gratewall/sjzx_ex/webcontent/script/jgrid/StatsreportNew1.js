/*------------------------------------------------------------------------
用Javascript+DOM实现的报表组件
表格组件开发更新列表

作者: 许勇 熊文  聂超
单位: 湖北省统计局计算中心
E-mail: xy@nhome.org

2004.5.8  开始构思，学习 DOM，写出读XML格式的数据，并以表格显示
2004.5.9  任务分割，学习 DOM
2004.5.10 布置任务，继续写DOM，以表格显示和录入
2004.5.12 下载和修改 tooltip.js
2004.5.13 下载和修改 toolpane.js,有XW参与
2004.5.17 和JR确定出表结构包XML格式，开始写总控模块 StatsReports
2004.5.18 写出总控模块和解决表的主栏显示和宾栏未处理显示.
2004.5.19 解决了表格的数据单元格显示问题
2004.5.20 将XW写的menu-new.js加到模块中,实现了menu，和HYH确定出表数据包XML格式
2004.5.21 写出读数据包到单元显示,并将NC的宾栏显示的代码加入到模块中,完成了宾栏的正常显示
2004.5.22 解决了表格的主栏正常显示
2004.5.23 学习了object的 filter 技术,解决了滚屏时主宾栏翻动的问题.
2004.5.24 读JR的VC定义出的表结构包时发现了读大表时很慢的问题,试图寻找解决办法
2004.5.25 继续寻找解决办法
2004.5.26 解决了滚屏时录入单元格跑到主栏和宾栏区的问题.
2004.5.27 加入了审核函数checkreport，解决了审核公式的读入和执行问题。
	  加入了信息窗口，解决了加载进度提示和审核信息显示问题。
2004.5.28 解决了数据录入后数据包的生成
2004.5.30 解决了项目表的主栏显示和新增项目的录入问题
	  解决了项目表的XML数据包的生成和数据加载问题
	  解决了录入光标移动和回车键的控制问题
2004.5.31 解决了主栏和宾兰的显示宽度问题
2004.6.1  解决了不定长表的录入问题
2004.6.5  解决了不定长表数据加载问题

2004.6.8  解决了审核公式带多个参数的print函数
	  解决了不定长表和项目表重新读数据时,清除主栏和数据栏表格的问题
2004.6.9  添加了表数据的读取和提交接口 通过 XMLHTTP与jsp页面交换XML数据
2004.6.10 Fix了几个BUG
2004.6.11 Fix 滚屏时公共栏不见的BUG
	  解决了光标左右键移动焦点
2004.6.14 Fix 录入光标翻屏出现紊乱的问题
2004.6.22 加入了XW编写的颜色搭配代码
2004.6.23 加入了信息窗口打印问题
2004.6.25 着手解决IE5的问题
2004.6.26 解决了tabpane.js和tooltip.js在IE5的问题
2004.6.28 解决了不长表在IE5运行的问题   // var se=doucment.createElement('select'); 后应马上 this.parentNode.addChild(se);
2004.6.29  继续解决IE5的问题不能refresh的问题
	   找到了程序加载退出的原因是_zlAndbodyRemove()函数有问题

2004.7.5  成功的解决了IE5下显示主栏和数据栏对不齐问题
2004.7.8  加入了HYH提供的三个读表结构信息的函数
2004.7.12 修改了pub区主栏区的的outset显示
2004.7.13 修改了主栏区和宾栏区的单位和代码可选显示
2004.7.19 加入了NC的两个打印函数，修改为在打印窗口直接生成的DIV，避免了在主窗口生成时而不断地占用资源
2004.7.20 修改了项目表和不定长表的主栏内容不能打印问题
2004.7.24  加入了readOnly 属性
2004.7.24  加入了数据清空功能
2004.8.2  加入项目101显示功能
2004.8.27 XML中加入按行或按列的输入类型和小数位的控制
2004.8.27 加入用正则表达式实现cell的输入类型和小数位的控制
2004.8.28 加入了历史数据的调入功能

 ------------------------------------------------------------------------*/

function StatsReport(name,top,left,width,height,parent)   //构造函数
{

  window.moveTo(0,0)
  window.resizeTo(screen.availWidth,screen.availHeight)
 var pdiv,obj=document.createElement('Div');//创建一个控制节点
 if(parent){pdiv=parent;}else pdiv=document.body;//获取父块
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
  //共有属性
  obj.name =name;
  obj.divHeight=height;
  obj.pageSize='a4';
  obj.pageOrient='0';
  obj.UserInfo= new Array();
   obj.curReport;


  obj.isLoadSY=0;
  obj.isLoadSJ=0;
  obj.isLoadSN=0;

  obj.stPacketUrl =null;    //表结构包Url
  obj.dataPacketUrl =null;    //表结构包Url

  obj.openDataUrl =null;       //数据读取的JSP页面指针
  obj.postDataUrl =null;       //数据提交的JSP页面指针

  obj.stPacketNode =null;      //表结构包节点
  obj.dataPacketNode = null ;  //表数据包节点
  obj.newDataPacketNode =null;  //新的数据包
  obj.dataQueryNode =null;
  obj.readOnly=false;      //报表只读或浏览模式
  obj.clearReportsData= SC_cleareportsrdata;   //清报表的数据数据

  obj.menuBar = new menu_bar(top-28,left,width);
  obj.popupMenu = new menu();

  obj.colorMenu = new menu();
  obj.colorMenu.add("春","js", "SetSkinColor(1);");
  obj.colorMenu.add("夏","js","SetSkinColor(2);");
  obj.colorMenu.add("秋","js","SetSkinColor(3);");
  obj.colorMenu.add("冬","js","SetSkinColor(4);");

 /* obj.fontMenu = new menu();
  obj.fontMenu.add("大","js", "setBodyFontSize(1);");
  obj.fontMenu.add("中","js","setBodyFontSize(2);");
  obj.fontMenu.add("小","js","setBodyFontSize(3);");
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
  obj.infoMenu.add("查看信息","js","tabpane.setSelectedIndex(0)");
  obj.infoMenu.add("清除信息","js","ClearInfo()");
  obj.infoMenu.add("打印信息","js","PrintInfo()");

  obj.sendMenu.add("数据报送","js" ,"statsrptobj.postUnitData(1)");
  obj.sendMenu.add("不申核数据报送","js" ,"statsrptobj.postUnitData(0)");
  obj.sendMenu.add("不报送数据退出","js","x=window.confirm('不提交数据退出');if(x==true) window.close();");

  obj.histroyMenu.add("上月数据","js" ,"statsrptobj.openUnitData('sy');");
  obj.histroyMenu.add("上季数据","js" ,"statsrptobj.openUnitData('sj');");
  obj.histroyMenu.add("上年数据","js" ,"statsrptobj.openUnitData('sn');");

 // obj.dataMenu.add("生成数据包","js","statsrptobj.createDataPacket()");
//  obj.dataMenu.add("加载数据包","js","statsrptobj.dataPacketUrl=window.prompt();statsrptobj.getdataPacket()");
 // obj.dataMenu.add("查看数据包","js" ,"alert(statsrptobj.newDataPacketNode.xml)");
  obj.dataMenu.add("清除信息窗口","js","ClearInfo()");
  obj.dataMenu.seperate();
  obj.dataMenu.add("数据清空","js" ,"statsrptobj.clearReportsData();");
  obj.dataMenu.add("写数据到本地","js" ,'statsrptobj.createDataPacket();var file="c:zbdata.xml"; /*window.prompt("文件名","c:/zbdata.xml");*/ if(file!=null)  writeFile(file,statsrptobj.newDataPacketNode.xml)');
  obj.dataMenu.add("从本地读数据","js" ,"statsrptobj.dataPacketUrl='c:zbdata.xml';/*window.prompt('文件名','c:/zbdata.xml');*/if(statsrptobj.dataPacketUrl!=null) statsrptobj.getdataPacket()");
//  obj.dataMenu.seperate();
 // obj.dataMenu.add("写Excel格式到本地","js" ,'statsrptobj.createExcelPacket();var file=window.prompt("文件名","c:/Exceldata.xml"); if(file!=null)  writeFile(file,statsrptobj.ExcelPacketNode.xml)');
//  obj.dataMenu.add("清除信息窗口","js","ClearInfo()");
//obj.dataMenu.add("写body","js" ,"writeBody()");
 // obj.dataMenu.add("数据包加载","js" ,"statsrptobj.openUnitData()");
 obj.printMenu.add("A4","js","statsrptobj.pageSize='a4';");
 obj.printMenu.add("A3","js","statsrptobj.pageSize='a3';");
 obj.printMenu.seperate();
 obj.printMenu.add("纵向打印","js","statsrptobj.pageOrient='0';");
 obj.printMenu.add("横向打印","js","statsrptobj.pageOrient='1';;");
 obj.printMenu.seperate();
obj.gridMenu.add("新增行","js","statsrptobj.curReport._appendZlRow();");
 obj.gridMenu.add("删除当前行","js", "statsrptobj.curReport._deleteZlRow(statsrptobj.curReport.focusRow);");

// obj.menuBar.add("字体设置",obj.fontMenu) ;
 obj.menuBar.add("报表录入",obj.popupMenu) ;
 obj.menuBar.add("报表审核",obj.checkMenu) ;
 obj.menuBar.add("审核信息",obj.checkinfoMenu) ;
 obj.menuBar.add("审核并报送",obj.sendMenu) ;
 obj.menuBar.add("表格操作",obj.gridMenu);
 obj.menuBar.add("数据信息",obj.dataMenu) ;
 obj.menuBar.add("报表打印",obj.printMenu) ;
// obj.menuBar.add("打印页面设置",obj.printSetupMenu) ;
 obj.menuBar.add("颜色设置",obj.colorMenu) ;
 // obj.menuBar.add("信息窗口",obj.infoMenu) ;
 obj.menuBar.add("调入历史数据",obj.histroyMenu) ;

  obj.tabPane =null ;   //标签页指针

  obj.reportCount = 0;     //表个数
  obj.reportCode =new Array() ;  //表代码
  obj.reportType =new Array();  //表类型
  obj.reportPtr = new Array() ; //表的指针
  obj.reportFirst = new Array() ; //表的主宾栏小数定义优先
//共有方法

  obj.oncontextmenu=SC_oncontextmenu;   //上下文菜单
  obj.setCssStyle = SC_setcssstyle;

  obj.addTabPage = SC_addtabpage;
 // obj.addTabPageIdx = SC_addtabpageidx;
  obj.Refresh = SC_refresh;
  obj.switchReport =SC_switchreport; //(name)

//  obj.addReport = SC_addreport;

  obj.getstPacket =SC_getstpacket;     //读表结构包到表结构包节点
  obj.getdataPacket =SC_getdatapacket; //读表数据包到表数据包节点
  obj.openUnitData =SC_openunitdata;     //打开某单位的数据包
  obj.openUnitHistoryData =SC_openunithistorydata;     //打开某单位的数据包
  obj.LoadHistoryData = SC_loadhistorydata
  obj.postUnitData =SC_postunitdata;     //提交数据包

 //////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// h y h //////////////////////////////////////////////////
  obj.openStPacketUrl =null;       //结构读取的JSP页面指针
  obj.createStPacketQuery =SC_createstpacketquery;  //创建表结构包读取的XML节点
  obj.openUnitStPacket =SC_openunitstpacket;     //打开某单位的结构包
  obj.stPacketQueryNode =null;
  obj.ExcelPacketNode;
////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////


 // obj.setdataPacket =SC_setdatapacket; //写表数据包节点
  obj.createExcelPacket =SC_createexcelpacket  //创建表结构和数据包
  obj.createDataPacket =SC_createdatapacket  //创建表数据包
  obj.createDataQuery =SC_createdataquery;  //创建表数据包读取的XML节点
  obj.printViewReport =SC_printviewreport;
  obj.draw=SC_Draw;

  obj.CheckAllReport =SC_checkallreport;

  obj.createInfoDiv=SC_createinfodiv;     //创建提示信息窗口

  //私有方法
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
 var obj=document.createElement('Div');//创建一个控制节点
 obj.style.overflow = 'auto';
 this.appendChild(obj);
 obj.style.zIndex=1200;
 if(name){var s='window.'+name+'=obj';eval(s);}
 this.reportPtr['info'] =obj;
 this.addTabPage(obj,"信息窗口","所有执行结果的信息窗口");   //将信息窗口添加倒tabpane中

}

function SC_printviewreport(reportname,top,left,width,height,papertype,method,parent)
{
 //判断纸张来源及打印为纵向或横向
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
 //var pix_800_height=978; 800*600下,A4幅面相当于像素点数
 /*1024*768 分辨率下210mm相当于330px,297mm相当于978Px.这个转换数值是经过试验得来.但是看起来好像
 不成比率。原因是该显示多少页的算法不对。

 实际上,A4纸210mm相当于640Px 297mm相当于1125Px*/


//var pix_800_width=978;
//var pix_800_height=640; //800*600下,A4幅面相当于像素点数 横打数据

 width=pix_800_width;
 height=pix_800_height;
 var curPtr=this.reportPtr[reportname];
 var blsnode=curPtr.blsNode;
 var blsCount=blsnode.childNodes.length;
 var zlsnode=curPtr.zlsNode;
 var zlsCount=curPtr.RowNum;//zlsnode.childNodes.length;
 var bls_loop_id=0;
  var pdiv,obj=pwin.document.createElement('Div');//创建一个控制节点
  if(parent){pdiv=parent;}else pdiv=pwin.document.body;//获取父块
  obj.style.position='relative';
  obj.style.visibility = 'hidden';
  //if(left) obj.style.left=left;
 // obj.style.top=top;
 // if(width) obj.style.width=width;
 // if(height) obj.style.hight=height;  //此处加入高度位移量
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
  var headrow  = obj.BigTab.insertRow();  //表头部分
  obj.titleTab = headrow.insertCell();
  obj.titleTab.colspan='2';

  var headrow  = obj.BigTab.insertRow();
  obj.pubTab = headrow.insertCell();      //公共部分
  obj.headTab = headrow.insertCell();      //宾栏部分
  var headrow  = obj.BigTab.insertRow();
  obj.zlTab = headrow.insertCell();        //主栏部分
  obj.bodyTab = headrow.insertCell();      //数据部分
   obj.parent=pdiv;
  //创建表头区

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


      //创建宾栏部分
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

 rowCount =rowCount-1 ; //除掉单位合并

 for (var i=0;i<rowCount;i++)
 {
 for (var j=0;j<blsCount;j++)
 {

 if(i<rowCount-1 && bl_data[i][j]==bl_data[i+1][j])
	 {
      blCells[i][j].style.borderBottomStyle="none"; //去除单元格线
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

   //创建公共部分
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




 //主栏部分
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

   //数据部分
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

/**************************************************调整对齐*/
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
//PUB栏与主栏对齐
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

//数据栏与宾栏对齐



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

//在此加入浏览器版本验证
pwin.print();
alert("打印完后按一键关闭窗口");
pwin.close();

}
function SC_Draw(reportname,top,left,width,height,pwin,H_page,V_page)
{
 //var pdiv,allObj=pwin.document.createElement('Div');//创建一个控制节点
 pdiv=pwin.document.body;//获取父块
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
 var lastBl=blsCount%loop_bl_id;//修正横向页码数
 var loopBl=loop_bl_id;
 var loop_zl_id=Math.floor(zlsCount/V_page);
 V_page=Math.floor(zlsCount/loop_zl_id);
 if(zlsCount%loop_zl_id!=0)V_page=V_page+1;
 var lastZl=zlsCount%loop_zl_id;//修正纵向页码数
 var loopZl=loop_zl_id;
 var pages;
 var vpages;

for(vpages=0;vpages<V_page;vpages++)
	{

    if(lastZl!=0)         //如果有最后一页
		{
  if(vpages==V_page-1)loop_zl_id=lastZl;
  if(vpages!=V_page-1)loop_zl_id=loopZl;
	}

	for(pages=0;pages<H_page;pages++)
	{
  if (lastBl!=0)         //如果有最后一页
  {
  if(pages==H_page-1)loop_bl_id=lastBl;
  if(pages!=H_page-1)loop_bl_id=loopBl; //判断是否是最后一页
   }
  var obj=pwin.document.createElement('Div');//创建一个控制节点
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
  var headrow  = obj.BigTab.insertRow();  //表头部分
  obj.titleTab = headrow.insertCell();
  obj.titleTab.colspan='2';
  var headrow  = obj.BigTab.insertRow();
  obj.pubTab = headrow.insertCell();      //公共部分
  obj.headTab = headrow.insertCell();      //宾栏部分
  var headrow  = obj.BigTab.insertRow();
  obj.zlTab = headrow.insertCell();        //主栏部分
  obj.bodyTab = headrow.insertCell();      //数据部分
  obj.parent=pdiv;
  //创建表头区

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
		cell.innerText= curPtr.reportTitle+"   第"+ppages+"页  共"+V_page*H_page+"页";
	var headrow  = tab_bt.insertRow();
		cell=headrow.insertCell();
		//cell.align='center';
		cell.innerText= curPtr.reportSubTitle;

      //创建宾栏部分
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

 rowCount =rowCount-1 ; //除掉单位合并

 for (var i=0;i<rowCount;i++)
 {
 for (var j=0;j<loop_bl_id;j++)
 {

 if(i<rowCount-1 && bl_data[i][j]==bl_data[i+1][j])
	 {
      blCells[i][j].style.borderBottomStyle="none"; //去除单元格线
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
  //创建公共部分
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
 //主栏部分
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
   //数据部分
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

/**************************************************调整对齐*/
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
//PUB栏与主栏对齐
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

//数据栏与栏对齐
//divTop=parseInt(obj.style.top)+parseInt(tab_pub.offsetHeight)+parseInt(tab_zl.offsetHeight);
var pageObj=pwin.document.createElement('Div');
pageObj.className="PageNext";
pdiv.appendChild(pageObj);
	}//每页循环完成,横向循环完成

	}//每页循环完成,纵向循环完成

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
var pwin=window.open("","信息打印");
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

 //向tabpane中加入 tabpage
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
/* //向tabpane中加入 tabpage
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
  if(name=='信息窗口'||name=='项目信息') return;
	this.curReport=this.reportPtr[name];
		 this.curReport.Refresh();
	      currptobj =this.curReport;
		 currptname =name;
	  // alert(currptname);

}

//读表数据包到表数据包节点
function SC_getdatapacket()
{
 AddInfo("开始加载表数据包.....");
//alert(this.dataPacketUrl);
 if(!this.dataPacketUrl){ return -1;}
try{
  //读出 XML 文件
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
 AddInfo("加载数据包.....成功！");

}
function SC_checkallreport()
{
    allerr=0;
	for ( var i=0;i<this.reportCount;i++ )
	{
	  var curReport=this.reportPtr[this.reportCode[i]];
	  if(curReport.type=='项目101表'||curReport.type=='4' || curReport.type=='101表'||curReport.type=='5')
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

	  if(curReport.zlCells[row][2].value=='*'||curReport.zlCells[row][1].value=='-'||curReport.zlCells[row][1].value=='－') continue;
	  if(curReport.type=='项目表'||curReport.type=='3')
		    if(curReport.zlCells[row][0].value=='' ||curReport.zlCells[row][0].value==null) continue;
       el=a.createElement('zl');
       zls.appendChild(el);
	   if(curReport.type=='项目表'||curReport.type=='3')
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
	AddInfo("数据包已生成！");
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

function SC_openunitstpacket()     //打开某单位的结构包
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
	  AddInfo("<font color=#f00000>没有找到数据取出的执行页面！</font>");
	  return ;
	}
  var httpob = new ActiveXObject("Microsoft.XMLHTTP");
  httpob.Open("post",this.openStPacketUrl, false);
  httpob.send(this.stPacketQueryNode);
  var xmlDoc=httpob.responseXML;
  if(!xmlDoc) {
	  AddInfo("<font color=#f00000>读取数据执行失败！</font>");
	  return;
  }
  if(xmlDoc.parseError.errorCode!=0){
	  AddInfo("<font color=#f00000>读取数据有错误！</font>");
	  return;
  }
  if (xmlDoc){
	  this.stPacketNode=xmlDoc;
      this._initAllReportView();
  }
}
///////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////

function SC_openunitdata(timetype)     //打开某单位的数据包
{
  if(timetype==null) timetype='dq';

  this.createDataQuery(timetype);
  if(this.openDataUrl==null)
	{
	  AddInfo("<font color=#f00000>没有找到数据取出的执行页面！</font>");
	  return ;
	}
  var httpob = new ActiveXObject("Microsoft.XMLHTTP");
  httpob.Open("post",this.openDataUrl, false);
  httpob.send(this.dataQueryNode);
  var xmlDoc=httpob.responseXML;
  if(!xmlDoc) {
	  AddInfo("<font color=#f00000>读取数据执行失败！</font>");
	  return;
  }
  if(xmlDoc.parseError.errorCode!=0){
	  AddInfo("<font color=#f00000>读取数据有错误！</font>");
	  return;
  }

  if (xmlDoc){
	  this.dataPacketNode=xmlDoc;
      this._initAllReportData();
  }


}

function SC_openunithistorydata(timetype)     //打开某单位的数据包
{
  if(timetype==null) timetype='dq';

  this.createDataQuery(timetype);
  if(this.openDataUrl==null)
	{
	  AddInfo("<font color=#f00000>没有找到数据取出的执行页面！</font>");
	  return ;
	}
  var httpob = new ActiveXObject("Microsoft.XMLHTTP");
  httpob.Open("post",this.openDataUrl, false);
  httpob.send(this.dataQueryNode);
  var xmlDoc=httpob.responseXML;
  if(!xmlDoc) {
	  AddInfo("<font color=#f00000>读取数据执行失败！</font>");
	  return;
  }
  if(xmlDoc.parseError.errorCode!=0){
	  AddInfo("<font color=#f00000>读取数据有错误！</font>");
	  return;
  }

  if (xmlDoc){
	  this.dataPacketNode=xmlDoc;
	//  alert(xmlDoc.xml);
      this.LoadHistoryData(timetype);
  }


}
function  SC_postunitdata(isCheck)    //提交数据包
{
  if(isCheck!=0) {
   var err=this.CheckAllReport();
   if(err!=0)
	{
	  var errinfo="审核有"+err+"个错误,不能报送数据!";
	  alert(errinfo);
	  AddInfo(errinfo);
	 
	   return;
	}
  }
   this.createDataPacket();  //生成数据包
  if(this.postDataUrl==null)
	{
	  AddInfo("<font color=#f00000>没有找到数据提交的执行页面！</font>");
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
	var node=root.childNodes.item(i);   //得到 <report> </report>对节点内容
		if(node.tagName.toUpperCase()=='REPORT')
		{
		    if(node.getAttribute("code")=='no') {
			     AddInfo("没有"+timetype+"数据");
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
 if(type=='项目101表' ||type=='101表')
	{
      return;
	}

    curReport=this.reportPtr[name];

	var count = node.childNodes.length;    //得到 <report> </report>对中子节点个数
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
	   case '定长表':
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
   case '不定长表':
   case '2':

//	  curReport._zlAndbodyRemove();  //清空主栏和数据栏的表格
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
   case '项目表':
   case '3':
   curReport._zlAndbodyRemove();   //清空主栏和数据栏的表格
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
 AddInfo("加载表<font color='#F00000'>"+name+"</font>的历史数据.....，成功！");

}




//开始分析XMLDOC 读出表的数据包并填入多张表中

function  SC_initallreportdata()
{
  var root=this.dataPacketNode.documentElement;
  if(root.tagName.toUpperCase()=="REPORTS")
   var Count = root.childNodes.length;

//  this.reportCount=0;
  for(var i=0;i< Count;i++)
	{
	var node=root.childNodes.item(i);   //得到 <report> </report>对节点内容
		if(node.tagName.toUpperCase()=='REPORT')
		{
		    if(node.getAttribute("code")=='no') {
			     AddInfo("填写新数据");
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
 if(type=='项目101表')
	{
	 //建项目101表
       if(this.reportPtr[name]==null) {
	    obj=document.createElement('Div');//创建一个控制节点
	    obj.style.overflow = 'auto';
	    this.appendChild(obj);
	   if(name){var s='window.'+name+'=obj';eval(s);}
	   this.reportPtr[name] =obj;
	   this.addTabPage(obj,"项目信息",name);   //将项目101表窗口添加倒tabpane中
	       rptType=type;
	   }
	}
	else  if(type!='101表'){
    curReport=this.reportPtr[name];
    rptType=curReport.type;


    curReport.cellNode=node;
     firstload =false;
	}
	else rptType =type;
 AddInfo("正在加载表<font color='#F00000'>"+name+"</font>的数据.....，请等待");


	var count = node.childNodes.length;    //得到 <report> </report>对中子节点个数
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
	   case '定长表':
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
   case '不定长表':
   case '2':

	  curReport._zlAndbodyRemove();  //清空主栏和数据栏的表格
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
   case '项目表':
   case '3':
   curReport._zlAndbodyRemove();   //清空主栏和数据栏的表格
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
	case '项目101表':
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
     cell.innerText="代码";
	 var cell =headrow.insertCell();
     cell.innerText="名称";
	 var cell =headrow.insertCell();
     cell.innerText="值";

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
	 if(name=='项目名称')  { this.xmName=value; /*alert(this.xmName); */}
    }

	for( var i=0;i<this.reportCode.length;i++)
	{
	  if(this.reportType[i]=='项目表' || this.reportType[i]=='3')
		{
	      var cur=this.reportPtr[this.reportCode[i]];
	      cur.zlCells[0][0].innerText=this.xmName;
		}
	}
	
	break;
	case '101表':
	case '5':

    for(var i=0;i<RowNum; i++)
	{
      var subnode= zlsNode.childNodes.item(i);
      var id=subnode.getAttribute("id");
	 if(id!="") this.UserInfo[id]=subnode.getAttribute("code");
    }
	break;

 }
 AddInfo("加载表<font color='#F00000'>"+name+"</font>的数据.....，成功！");

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

function SC_cleareportsrdata()  //清除所有报表的数据
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
//读表结构包到表结构包节点
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
  //读出 XML 文件
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
//开始分析XMLDOC 读出表的结构包并画出多张表

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
  AddInfo("开始加载结构包.....");

 this.reportCount=0;
  for(var i=0;i< Count;i++)
	{
	var node=root.childNodes.item(i);   //得到 <report> </report>对节点内容
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
 this.checkMenu.add("审核所有报表","js","statsrptobj.CheckAllReport();");
 this.printMenu.add("打印所有报表");
 tabpane.setSelectedIndex(1);
}
//在屏幕上画表
function SC_initonereportview(name,type,firist,readonly,node,idx)
{
 AddInfo("正在加载表<font color='#F00000'>"+name+"</font>的结构.....请等待");
  var obj=document.createElement('Div');//创建一个控制节点
  obj.style.position = 'relative';
  obj.style.overflow = 'auto';

  this.appendChild(obj);
  obj.onscroll = DG_scrolldraw;
 if(name){var s='window.'+name+'=obj';eval(s);}

 // obj._appendCell = AD_appendcell;
  obj.name = name;
  obj.Cells = Array();    //定义表格单元
  obj.Cells_SY = Array();
  obj.Cells_SJ = Array();
  obj.Cells_SN = Array();
  obj.Cols = Array() ;      //定义表格一列数据
  obj.Rows = Array();       //定义表格一行的数据
  obj.RowNum=0;             //定义表格的有效行数
  obj.ColNum=0 ;            //定义表格的有效列数
  obj.RowCount ;             //定义表格总行数,
  obj.focusRow;
  obj.ColLevelNum;
  obj.blBottomRow =0;
  obj.type =type;
  obj.readOnly=readonly;


  obj.zlXm = new Array();  //定义主栏名称
  obj.zlDm = new Array();  //定义主栏代码
  obj.zlDmIdx = new Array();   //定义主栏代码所对应的行序号
  obj.zlDmRawIdx = new Array();
  obj.zlDw = new Array();  //定义主栏单位
  obj.ComboBox =new Array();

  obj.blXm = new Array();
  obj.blDm = new Array();  //定义宾栏代码
  obj.reportTitle  =null;   //定义表名称
  obj.reportSubTitle  =null;//定义表子头

  obj.pubCellWidth = new Array();  //主宾栏交叉部分的单元格宽度
  obj.pubZlAttr =new Array();
  obj.pubBlAttr =new Array();
  obj.pubCells  = new Array();     //主宾栏交叉部分的单元格
  obj.zlCells  = new Array();

  obj.blCells ;                      //宾兰的单元格
  obj.blattr =new Array();
  obj.bldec= new Array() ;
  obj.zlattr =new Array();
  obj.zldec= new Array() ;

  obj.zlRawattr =new Array();
  obj.zlRawdec= new Array() ;

  obj.checks="";
  obj.checksText ="";     //定义表审核表的审核表达式
  obj.publicsNode =null;     //主宾栏交叉部分的节点
  obj.blsNode =null;         //宾栏部分的节点
  obj.zlsNode =null;          //主栏部分的节点
  obj.cellNode =null;
  obj.addsTitle ='' ; //附加信息名称

  //obj.Rows = null;//调用表的所有行
 // obj.RowsCount = 0;  //当前表中的记录总数
  //obj.StateCells = null;//表格状态条
  //obj.Cells = null;     //所有的单元格
  obj.Headerdiv = null;//包含表头的块
  obj.Header = null;//表示表头的表
  obj.Addsdiv =null;
  //obj.ColsCount=0;//表的列数
  //obj.Cols = null;//表的列
  obj.parent = this; //对象的容器
  obj.EditMode = null; //编辑状态
  obj.Enabled = false; //是否可用
  obj.Visible = true;  //是否可见
  obj.Bodydiv = null; //表体的块
  obj.Body = null;    //表体本身,最重要的部分
  obj.Table = null;  //用户直接操作的部分
  obj.BlDiv =null;    //表的宾栏块
  obj.BlTab = null;   //表的宾栏块 table
  obj.ZlTab = null;   //表的主栏块 table
  obj.ZlDiv =null ;   //表的主栏块
  obj.PubDiv =null;
  obj.PubTab =null;
  obj.first =first;
  obj.Id = '';        //对象的唯一的标示
 // obj.DataSet = null;//数据集
 // obj.Fields = null;//字段集
  obj.Isinit = true;//标记是否为初始化
  obj.CurrentCell = null
  obj.EditErrCell = null;  //保存当前编辑错误的项
  obj.Auto = false; //是否显示多有的项


  obj.HeadColor =bgcolor;//clSilver;    //定义表结构颜色
  obj.isDisplayHead = true;   //定义是否显示表的主宾栏

//公有方法
//  obj.SetDataSet = DG_setdataset;    //设置数据库
  obj.Show = DG_show;                //构造显示整个初始化后的表
//  obj.DeleteRow = DG_deleterow;      //删除一个指定的行
    obj.Refresh = DG_refresh;          //表结构刷新，整个结构重新构造
//	obj.onresize=DG_refresh;
 // obj.AppendRow = DG_appendrow;      //添加一个空行
 // obj.AppendRows = DG_appendrows;      //添加多个空行
 // obj.HandleMessage = DG_handlemessage;//扑捉消息
 // obj.UpdateRow = DG_updaterow;        //更新行
 // obj.Clear = DG_clear;  //清除表格中的所有内容
 // obj.ClearRows = DG_clearrows; //清除表格中的所有的行
  obj.SetHeadColor =DG_setheadcolor;  //定义表结构颜色
  obj.CheckReport = DG_checkreport;
  obj.CheckReportInfo = DG_checkreportinfo;
  obj.PrintReport = DG_printreport;
  obj.dispCellNode = function () { alert(this.cellNode.xml); }
//私有方法
  obj._CreateBody = _DG_createbody;    //创建表及其容器块
  obj._CreateHead = _DG_createhead;    //创建表头及其容器块
//  obj._CreateBl = _DG_createbl;    //创建表宾栏及其容器块
  obj._CreateZl = _DG_createzl;    //创建表主栏及其容器块
  obj._CreatePub = _DG_createpub;    //创建表公共部分其容器块
  obj._CreateAdds = _DG_createadds    //创建附加信息

  obj._BodyShow = _DG_bodyshow;        //显示表体的具体内容
  obj._HeadShow = _DG_headshow;        //显示表头的具体的内容
 // obj._BlShow = _DG_blshow;        //显示表宾栏的具体内容
  obj._ZlShow = _DG_zlshow;        //显示表主栏的具体的内容
  obj._PubShow = _DG_pubshow;        //显示表公共部分的具体的内容
  obj._AddsShow =_DG_addsshow;
  obj._appendRow = _DG_appendrow;     //添加一个空行
  obj._deleteRow = _DG_deleterow;     //删除一个空行
  obj._appendCell = _DG_appendcell;    //添加一个空的单元格
  obj._appendZlCell = _DG_appendzlcell;    //添加一个项目表的主栏空的单元格
  obj._appendSeZlCell = _DG_appendsezlcell;    //添加一个不定长表主栏空的单元格
  obj._appendZlRow = _DG_appendzlrow;     //添加一个空主栏行
  obj._deleteZlRow = _DG_deletezlrow;     //删除一个空主栏行

  obj._zlAndbodyRemove=_DG_zlandbodyremove;
//  obj._ArrayRemove = _arrayremove;  //用于数组处理，从数组中删掉指定的元素
//  obj._GetDataSetInfo = _DG_getdatasetinfo; //获得数据库信息
//  obj._ValidFieldName = _DG_validfieldname; //校验字段
  obj._ComputHIsView = _DG_computHisview;  //计算当前行是否在可视区域
  obj._ComputVIsView = _DG_computVisview;  //计算当前单元格是否在可视区域
//  obj.onpropertychange = _DG_onpropertychange;
 // obj._CreateRow = DG_N_Row;
 // obj.StateCells = new Array();
 // obj.Rows = new Array();
 // obj.Captions = new Array();
//  obj.Cols = new Columns(pdiv);
 // obj.Columns = obj.Cols;
//  obj.Columns.Auto = true; //是否显示多有的项
 // obj.Cols.Parent = obj;         //取得父亲节点
//  obj.OnCellClick;
 // obj.OnCellKeyDown;
 // obj.OnCellFocus;
 // obj.OnCellBlur;
 // obj.OnCellDblClick;
 // obj.OnCustomCell;
  //构造基本的块，开始调用私有方法
  obj._CreateBody();
  obj._CreateZl();
 // obj._CreateHead();
  obj._CreatePub();
  obj._CreateHead();
 // obj._CreateBl();
  obj._CreateAdds();
// obj.show();
  var count = node.childNodes.length;    //得到 <report> </report>对中子节点个数
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
	       if(type=='定长表' || type=='1')
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
			if(str[0]=='无')  obj.addsTitle='';
	        break;
       case 'CHECKS':
		    obj.checks=subnode.text.replace(/\n/gi,"<br>") ;
	        break;
       case 'JSCHECKS':
		    //此处加入审核公式
		obj.checksText=subnode.text.replace(/this/gi,name);
		   break;;

	  }
	}
  this.addTabPage(obj,name,obj.reportTitle+'<br>'+obj.reportSubTitle);   //将表添加倒tabpane中
 // this.popupMenu.add(obj.reportTitle,"js","tabpane.setSelectedIndex("+idx+")");
 // this.checkMenu.add(obj.reportTitle,"js",name+".CheckReport()");
 // this.printMenu.add(obj.reportTitle,"js",name+".PrintReport()");
  this.popupMenu.add("填报:"+name,"js","tabpane.setSelectedIndex("+idx+")");
  this.checkMenu.add("审核:"+name,"js",name+".CheckReport()");
  this.checkinfoMenu.add("查看:"+name,"js",name+".CheckReportInfo()");
   this.printMenu.add("打印:"+name,"js",name+".PrintReport()");
  //this.popMenu.add(obj.reportTitle);
  obj.Show();
 // obj.Refresh();
 AddInfo("加载表<font color='#F00000'>"+name+"</font>的结构.....成功！");
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
	 //读入上月数据
	 statsrptobj.openUnitHistoryData('sy');
	 statsrptobj.isLoadSY=1;
	}
 if(checkStr.indexOf("_SJ")!=-1 && statsrptobj.isLoadSJ==0 )
	{
	 //读入上季数据
	 statsrptobj.openUnitHistoryData('sj');
	 statsrptobj.isLoadSJ=1;
	}
 if(checkStr.indexOf("_SJ")!=-1 && statsrptobj.isLoadSN==0 )
	{
	 //读入上年数据
	 statsrptobj.openUnitHistoryData('sn');
	 statsrptobj.isLoadSN=1;
	}
 ClearInfo();
 eval(checkStr);
 }
 //alert(checkStr);
// alert(curindex);
 if(checkWarningNum) AddInfo("表"+this.name+"审核有<font color =#f00000><b>"+checkWarningNum+"</b></font>个警告性错误！请检查");
 if(checkErrNum==0) AddInfo("<font color =#f00000>表"+this.name+"审核通过!</font>");
 else  AddInfo("表"+this.name+"审核有<font color =#f00000><b>"+checkErrNum+"</b></font>个错误,审核未通过!");

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
var pwin=window.open("",this.name+"审核信息打印");
pwin.document.write("<body>");
pwin.document.write("<font color=red> 审核公式:</font><br>");
pwin.document.write(this.checks);
pwin.document.write("<br><font color=red> 审核结果:</font><br>")
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

function _DG_createbody()//创建表体DIV
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

   //在IE5中去掉后下句后就可以高度对齐了
   this.Body.style.tableLayout='fixed';


//  this.Body.style.borderCollapse='collapse';//表线的格式
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
function _DG_createzl()//创建表主栏DIV
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
function _DG_createpub()//创建表头DIV
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

function _DG_createhead()//创建表头DIV
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
function _DG_createadds () //创建附加信息DIV
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
	this.CheckTitle.innerText="审核结果";
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
//  this._BodyShow();//显示表体

if(this.isDisplayHead)
  {
     this._PubShow();
	 this._ZlShow();
  //   this._PubShow();
	}
  this._HeadShow();//显示宾栏

  this._BodyShow();//显示表体
  this._AddsShow();
//  this.Refresh();
//   this.Body.refreah();
//   this.PubTab.refresh();
 //  this.ZlTab.refresh();
 //  this.Header.refresh();
}
function DG_refresh()
{
  //是主栏和数据栏的单元格的高度一致
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
 this.Headdiv.style.pixelTop=this.scrollTop;//让表头始终可见
 this.Pubdiv.style.pixelTop=this.scrollTop;//让表头始终可见
 this.Pubdiv.style.pixelLeft=this.scrollLeft;//让表头始终可见
 this.Zldiv.style.pixelLeft=this.scrollLeft;//让表头始终可见
}
function _DG_bodyshow()//显示表
{
AddInfo("创建表："+ this.name +"单元格....");

if(this.Table.childNodes.length>0)return;
switch(this.type) {
 case '定长表':
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
function _DG_zlandbodyremove()  //不定长表和项目表重新读数据时
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

function _DG_pubshow()//显示表头
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
   cell.x = i;  //单元格行坐标属性
   cell.isSize= false;
   cell.posx= 0;
   cell.cellOffWidth=0;
 //  cell.y = y;  //单元格列坐标属性

       //为单元格添加新的属性
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
function _DG_headshow()//显示宾栏
{
AddInfo("创建表："+ this.name +"宾栏....");

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
   cell.y = j;  //单元格行坐标属性
   cell.isSize= false;
   cell.posx= 0;
   cell.cellOffWidth=0;

//  cell.y = y;  //单元格列坐标属性

       //为单元格添加新的属性
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

 rowCount =rowCount-1 ; //除掉单位合并

 for (var i=0;i<rowCount;i++)
 {
 for (var j=0;j<colCount;j++)
 {

 if(i<rowCount-1 && bl_data[i][j]==bl_data[i+1][j])
	 {
      this.blCells[i][j].style.borderBottomStyle="none"; //去除单元格线
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

function _DG_zlshow()//显示表主栏内容
{
AddInfo("创建表："+ this.name +"主栏....");
//alert("Ok");
if(this.ZlTab.childNodes.length>0) return;
//var headthead = this.ZlTab.createTHead();

switch(this.type) {
 case '定长表':
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
 case '不定长表':
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
 case '项目表':
 case '3':
	 this._appendZlRow();
	  break;
 }
}
function _DG_appendzlrow()
{
switch(this.type) {
 case '定长表':
 case '1':
	   break;
 case '不定长表':
 case '2':
      var row=this.RowNum;
	   this.RowNum++;
     //添加主栏
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
 case '项目表':
 case '3':
       var row=this.RowNum;
	   this.RowNum++;
     //添加主栏
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
 case '定长表':
 case '1':
	   break;
 case '不定长表':
 case '2':
 case '项目表':
 case '3':
	 x=window.confirm('要删除第 '+currow+' 行吗？');if(x==false) return;
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
		//*****************所有类型相同的处理***********//
	   cellbak = document.createElement('input');//添加一个指定元素
	     cell.appendChild(cellbak);
  //  cellbak.style.borderStyle ='solid';
	   cellbak.style.borderWidth =0;
	    //  setCellStyle1(cellbak);
	   //控制单元格的格式
       cellbak.parent = this;
	   cellbak.x = x;  //单元格行坐标属性
	   cellbak.y = y;  //单元格列坐标属性
	   cellbak.value =value;
	//   cellbak.w=cell.style.width-4;
      if(this.readOnly==true) cellbak.readOnly=true;
	   cellbak.Brother=null;
	   cellbak.selectedIdx =0;
	   if(y==0)
		   cellbak.size = 18;   //单元格输入宽度
	   else cellbak.size =8

       //为单元格添加新的属性
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

   this.style.width = 0;  //隐藏text
   if(!this.parent.ComboBox[this.y]){  //第一次生成 select
	 this.parent.ComboBox[this.y] =document.createElement('select');  //添加行

		 //在IE5下必须马上appendChild   Fix  2004.6.28
		 this.parentNode.appendChild(this.parent.ComboBox[this.y]);
	 this.parent.ComboBox[this.y].style.position='absolute'

		 var oOption = document.createElement("option");  //添空行
	 this.parent.ComboBox[this.y].add(oOption);
	     oOption.innerText = " ";   //空值
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

	  this.parent.ComboBox[this.y].onblur = _Combo_blur;    //设置失去焦点事件处理
	  this.parent.ComboBox[this.y].onchange= _Combo_onchange;//属性变化的事件
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
    this.Brother.style.visibility = 'visible'; //显示edit
    this.style.visibility = 'hidden'; //隐藏combobox
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
		alert(this.options[this.selectedIndex].value+ ' 已存在主栏中！');
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
function Cell_onsezlcellfocus()//当编辑单元格获的焦点
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
function Cell_onsezlcellblur()//当编辑单元格失去焦点
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
		case 38://当按动向上箭头
			if(this.x>0)
		    {
		     this.parent.zlCells[this.x-1][this.y].focus();

			}
		    break;
		case 40://当按动向下箭头
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
		case 33://当按动向上翻动页面
			this.parent.doScroll('scrollbarPageUp');
		    break;
		case 34://当按动向上翻动页面
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
		//*****************所有类型相同的处理***********//
	   cellbak = document.createElement('input');//添加一个指定元素
     //  cellbak.style.borderStyle ='solid';
	   cellbak.style.borderWidth =0;
	    //  setCellStyle1(cellbak);
	   //控制单元格的格式
       cellbak.parent = this;
	   cellbak.x = x;  //单元格行坐标属性
	   cellbak.y = y;  //单元格列坐标属性
	   cellbak.value =value;
	   if(y==0)
		   cellbak.size = 18;   //单元格输入宽度
	   else cellbak.size =8

       //为单元格添加新的属性
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
function Cell_onzlcellfocus()//当编辑单元格获的焦点
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
function Cell_onzlcellblur()//当编辑单元格失去焦点
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
		case 38://当按动向上箭头
			if(this.x>0)
		    {
		 this.parent.zlCells[this.x-1][this.y].focus();

			}
		    break;
		case 40://当按动向下箭头
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
		case 33://当按动向上翻动页面
			this.parent.doScroll('scrollbarPageUp');
		    break;
		case 34://当按动向上翻动页面
			this.parent.doScroll('scrollbarPageDown');
		    break;
		case 8:
			 if(this.y==2)
			 this.parent.Cells[this.x+1][1].focus();
		     break;

	}
}


function _DG_computHisview(row) {  //判断所给出的行是否在视区之内
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
   this.Headdiv.style.pixelTop=this.scrollTop;//让表头始终可见
   return isInView;
}

function _DG_computVisview(objcell) { //判断所给出的单元格是否在视区之内
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
		//*****************所有类型相同的处理***********//
	   cellbak = document.createElement('input');//添加一个指定元素
     //  cellbak.style.borderStyle ='solid';
	   cellbak.style.borderWidth =0;
	    //  setCellStyle1(cellbak);
	   //控制单元格的格式
      cellbak.parent = this;
	   cellbak.x = x+1;  //单元格行坐标属性
	   cellbak.y = y+1;  //单元格列坐标属性
	   cellbak.value =value;
	   cellbak.size = 10;   //单元格输入宽度

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
	   else  	 { //字符
		                cellbak.pattern =null
				        cellbak.decpattern= null
	        }


	   if(this.ZlTab.rows(x).cells(1).innerText=='－'||this.ZlTab.rows(x).cells(1).innerText=='-'||this.ZlTab.rows(x).cells(2).innerText=='*')
	    {cellbak.value='-';
	     cellbak.readOnly = true;
		}
	   else
	    cellbak.readOnly = false;

	if(this.readOnly==true) cellbak.readOnly=true;

       //为单元格添加新的属性
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
function Cell_oncellfocus()//当编辑单元格获的焦点
{
  this.style.borderStyle ='solid';
  this.style.borderWidth='1pt';
  this.style.color='#0000DF';
  this.parent._ComputHIsView(this.x);
  this.parent._ComputVIsView(this);
  this.parent.focusRow = this.x-1;
 // this.parent.Cells[1][1].value=this.x+':'+this.y;
}
function Cell_oncellblur()//当编辑单元格失去焦点
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
		case 38://当按动向上箭头
			if(this.x>1)
		    {
		 this.parent.Cells[this.x-1][this.y].focus();

			}
		    break;
		case 40://当按动向下箭头
			if(this.x< this.parent.RowNum)
		    {
			 this.parent.Cells[this.x+1][this.y].focus();
		    }
			else 
		    {
	         if(this.parent.readOnly==true|| this.parent.type=='定长表'||this.parent.type=='1') break;
			 this.parent._appendZlRow();
			 this.parent.Refresh();
	          this.parent.Cells[this.x+1][this.y].focus();

			}
		    break;
		case 13: //回车
		      if(this.y<this.parent.ColNum)
			   this.parent.Cells[this.x][this.y+1].focus();
	       else if(this.x< this.parent.RowNum)
			    this.parent.Cells[this.x+1][1].focus();

	       break;
		case 33://当按动向上翻动页面
			this.parent.doScroll('scrollbarPageUp');
		    break;
		case 34://当按动向上翻动页面
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
//	if(str=='') value='空值';
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
  body[0].style.fontFamily='宋体';
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
 infotx="已写入数据文件"+filename+"到本地!";
 alert(infotx);
 }
 else alert("写入本地失败!");
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
