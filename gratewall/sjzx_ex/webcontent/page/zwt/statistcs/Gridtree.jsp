<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.gwssi.dw.runmgr.etl.txn.TxnSysNoticeInfo"%>
<%@page import="java.util.Map"%>
<%@page import="cn.gwssi.common.context.Recordset"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="cn.gwssi.common.context.TxnContext"%>
<%@page import="com.gwssi.common.util.DateUtil "%>
<freeze:html width="650" height="350">
<head>



<%
 
	
	TxnContext context = (TxnContext) request
			.getAttribute("freeze-databus");
	String start_time = context.getRecord("select-key").getValue("created_time");
	if (StringUtils.isNotBlank(start_time)) {
		String[] times = DateUtil.getDateRegionByDatePicker(start_time,
				false);
		if (StringUtils.isNotBlank(times[0])) {
			context.getRecord("select-key").setValue(
					"startTime", times[0]);
			}
		if (StringUtils.isNotBlank(times[1])) {
			context.getRecord("select-key").setValue(
					"endTime", times[1]);
		}
	}
	/* if (context.getRecord("select-key").isEmpty()) {
		if (null == context.getRecord("select-key").getValue(
				"startTime")
				&& null == context.getRecord("select-key")
						.getValue("endTime")) {
			Calendar calendar = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String end_date = df.format(calendar.getTime());
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, -1);
			String start_date = df.format(calendar.getTime());
			context.getRecord("select-key").setValue(
					"startTime", start_date);
			context.getRecord("select-key").setValue(
					"endTime", end_date);
		}
	} */
	
	DataBus db =(DataBus)request.getAttribute("freeze-databus");
	DataBus bus= db.getRecord("select-key");
	String dateFrom=bus.getValue("startTime");
	String dateTo=bus.getValue("endTime");

    //System.out.println(db.getRecordset("record").size());
    //System.out.println(db.getRecordset("LeafNode").size());
 %>
  
 <script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/zwt/statistcs/stylesheets/jquery.treetable.theme.default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/zwt/statistcs/stylesheets/screen.css" media="screen" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/zwt/statistcs/stylesheets/jquery.treetable.css" />
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/page/zwt/statistcs/stylesheets/component.css" />
 --%> 
<title>�����ۺ�ͳ���б�</title>
</head>

<script type="text/javascript">
function showCollectBars(service_targets_id,service_targets_name){
	var page = new pageDefine( "/txn53000115.do", "�ɼ���������ͳ��", "_blank", "1000", "480");
	page.addValue( service_targets_id, "select-key:service_targets_id" );
	page.addValue( service_targets_name, "select-key:service_targets_name" );
	page.addParameter("select-key:startTime","select-key:start_time");
	page.addParameter("select-key:endTime","select-key:end_time");
	page.goPage();
}
function showShareBars(service_targets_id,service_targets_name){

	var page = new pageDefine( "/txn53000112.do", "�����������ͳ��", "_blank", "1000", "450");
	page.addValue( service_targets_id, "select-key:service_targets_id" );
	page.addValue( service_targets_name, "select-key:service_targets_name" );
	page.addParameter("select-key:startTime","select-key:start_time");
	page.addParameter("select-key:endTime","select-key:end_time");
	page.goPage();
}
function goS(service_targets_id,name,start_time,end_time){
				
		var page = new pageDefine( "/txn40200026.do", "�鿴��������ͳ��","_blank", "1000", "450");
		page.addValue( service_targets_id, "primary-key:service_id" );
		page.addValue( name, "primary-key:service_name" );
		page.addParameter("select-key:startTime","primary-key:start_time");
		page.addParameter("select-key:endTime","primary-key:end_time");
		//page.addValue( start_time, "primary-key:start_time" );
		//page.addValue( end_time, "primary-key:end_time" );
		page.goPage();
	}
function goC(collect_task_id, name){
	var page = new pageDefine( "/txn30101065.do", "�鿴��������ͳ��", "_blank","1000","450");
	page.addValue( name, "select-key:task_name" );
	page.addValue( collect_task_id, "select-key:collect_task_id" );
	page.addParameter("select-key:startTime","select-key:start_time");
	page.addParameter("select-key:endTime","select-key:end_time");
	page.goPage();
}
 
//��ȡ������Ϣ
function getErroInfo(type,location,targets_id,targets_name,id,name){
	var page = new pageDefine( "/txn53000217.do", "������ϸ��Ϣ", "_blank","1000","550");
	if(location=="root"){
		page.addValue( targets_id, "select-key:service_targets_id" );
		page.addValue( targets_name, "select-key:service_targets_name" );
	}else if(location="leaf"){
		page.addValue( targets_id, "select-key:service_targets_id" );
		if(type=="S"){//����
			page.addValue( id, "select-key:service_id" );
		}else if(type=="C"){//�ɼ�
			page.addValue( id, "select-key:collect_task_id" );
		}
	}
	page.addValue( type, "select-key:type" );
	page.addValue( location, "select-key:location" );
	page.addParameter("select-key:startTime","select-key:startTime");
	page.addParameter("select-key:endTime","select-key:endTime");
	page.goPage();
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$('#t2').find('.pack-list li:eq(1)').hide();
}


function check(){
		var start = document.getElementById("select-key:startTime").value;	
		var end = document.getElementById("select-key:endTime").value;
		if(start!=""&& end!=""){
            if(start>end){
                alert("��ʱ�䷶Χ���������ڱ�����ڿ�ʼ���ڣ�");
                document.getElementById("select-key:endTime").select();
                return false;
            }		

		}else{
			alert("��ʱ�䷶Χ����ʼ���ںͽ������ڶ�����Ϊ�գ�");
			return false;
		}
		return true;
}
function submitForm(){
	if(!check()){
		return false;
	}
}
//�鿴�������
function func_record_viewFwdx(service_targets_id) {
	

	var page = new pageDefine( "/txn201009.do", "�鿴�������","modal","800","600" );
	page.addValue( service_targets_id, "primary-key:service_targets_id" );
	page.updateRecord();
}

//�鿴�����
function func_viewShareFw(service_id)
{
	var page = new pageDefine( "/txn40200014.do", "�鿴�����", "modal", "800", "600");
	page.addValue( service_id, "primary-key:service_id" );
	page.addValue( "true", "inner-flag:enableCopy");
	page.updateRecord();
}

//�鿴�ɼ�����
function func_viewCjFw(type,collect_task_id)
{	
	if(type=="WebService"){
		var page = new pageDefine( "/txn30101006.do", "�鿴�ɼ�����", "modal", "800", "600");
		page.addValue( collect_task_id, "primary-key:collect_task_id" );
		page.updateRecord();
	}else if(type=="FTP"){
		var page = new pageDefine( "/txn30101015.do", "�鿴�ɼ�����","modal", "800", "600" );
		page.addValue(collect_task_id, "primary-key:collect_task_id" );
		page.updateRecord();
	}
}
_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
<freeze:title caption="�����ۺ�ͳ���б�"/>
<freeze:errors/>
<gwssi:panel action="txn53000216" target="" parts="t1,t2" styleClass="wrapper">
   <gwssi:cell id="t1" name="�������" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" pop="true"  maxsize="10" />
   <gwssi:cell id="t2" name="��ʼʱ�� " key="created_time" data="created_time" date="true"/>
 </gwssi:panel>
<div style="text-align: center;">

<freeze:form action="/txn53000216" onsubmit="return submitForm();">
	<freeze:frame property="select-key" >
    	<freeze:hidden property="service_targets_id" caption="�������" />
    	<freeze:hidden property="service_targets_type" caption="�����������" />
     	<freeze:hidden property="created_time" caption="��ʼʱ��" />
     	<freeze:hidden property="startTime" caption="��ѯʱ�俪ʼ" />
     	<freeze:hidden property="endTime" caption="��ѯʱ�����" />
  	  </freeze:frame>

<table  cellpadding="0" cellspacing="0" width="95%" align="center" style="border-collapse: collapse;">
<tr ><td>
    <div id="main" style="width:100%; background: #f2f2f2;">
    <div style="text-align:left;font-size:12px; height: 24px; line-height: 24px;">
          <%-- <table>
          <tr><td><div style="cursor:pointer;background:url('<%=request.getContextPath()%>/page/zwt/statistcs/typeicon/expand.png');width:20px;height:20px;" onclick="jQuery('#example-advanced').treetable('expandAll'); return false;" title='չ��ȫ��'></div>
          </td>
          <td><div style="cursor:pointer;background:url('<%=request.getContextPath()%>/page/zwt/statistcs/typeicon/collapse.png');width:20px;height:20px;" onclick="jQuery('#example-advanced').treetable('collapseAll'); return false;" title='�ر�ȫ��'></div>
          </td></tr>
          </table> --%>
          
          
     
          <a href="#" onclick="jQuery('#example-advanced').treetable('expandAll'); return false;">չ��ȫ��</a>
          <a href="#" onclick="jQuery('#example-advanced').treetable('collapseAll'); return false;">�ر�ȫ��</a>
      
         
         </div>
      <!-- <div id="freezediv" style="width:100%;height:340px;overflow:auto;">  -->
      <table id="example-advanced" style="align:center;font-size:12px; margin-top: 0px;" >
        <thead >        	
          <tr>
            <th rowspan="2" nowrap width="5%">���</th>
            <th rowspan="2" width="30%">�������</th>
            <th rowspan="2" nowrap width="">������ʽ</th>
            <th rowspan="2" nowrap width="">����״̬</th>
            <th colspan="3"  width="24%">�������</th>
            <th colspan="3"  width="24%">�ɼ�����</th>
            <th rowspan="2" nowrap width="5%">����ͼ</th>
          </tr>
          <tr>
          	<th nowrap width="8%">������(��)</th>
            <th nowrap width="7%">����(��)</th>
             <th nowrap width="9%">�������(��)</th>
            <th nowrap width="8%">������(��)</th>
            <th nowrap width="7%">����(��)</th>
             <th nowrap width="9%">�������(��)</th>
          </tr>
         
        </thead>
        <tbody>
        <%
        Recordset rootlist=db.getRecordset("record");
        Recordset leaflist=db.getRecordset("LeafNode");
        //����
        int s_amount=0,s_count=0,s_err=0,c_amount=0,c_count=0,c_err=0;
        String total="";
        String treeData="";
        if (!rootlist.isEmpty()) {
			int rootlen = rootlist.size();
			for (int i = 0; i < rootlen; i++) {
				DataBus rootTmp = rootlist.get(i);
				//�ۼ������Ϊ�ܼ�
				s_amount+=rootTmp.getValue("s_amount").equals("") ? 0 : Integer.parseInt(rootTmp.getValue("s_amount"));
				s_count+=rootTmp.getValue("s_count").equals("") ? 0 : Integer.parseInt(rootTmp.getValue("s_count"));
				s_err+=rootTmp.getValue("s_err").equals("") ? 0 : Integer.parseInt(rootTmp.getValue("s_err"));
				c_amount+=rootTmp.getValue("c_amount").equals("") ? 0 : Integer.parseInt(rootTmp.getValue("c_amount"));
				c_count+=rootTmp.getValue("c_count").equals("") ? 0 : Integer.parseInt(rootTmp.getValue("c_count"));
				c_err+=rootTmp.getValue("c_err").equals("") ? 0 : Integer.parseInt(rootTmp.getValue("c_err"));
						
				// String url =
				// "/txn53000112.do?select-key:service_targets_id="+rootTmp.get("SERVICE_TARGETS_ID")+"&select-key:service_targets_name="+rootTmp.get("SERVICE_TARGETS_NAME");
				// ���ڵ�
				treeData += ("<tr data-tt-id='"
						+ rootTmp.getValue("service_targets_id")
						+ "'><td>"
						+ (i + 1)
						+ "</td><td nowrap><span class='folder'>");
						
						String tmp="<a href='#' onclick='func_record_viewFwdx(\""+rootTmp.getValue("service_targets_id")+"\",\"\",\"\")'>"
								+rootTmp.getValue("service_targets_name")+"</a>";
								
						
				treeData +=	tmp
						+ "</span></td><td align='center'>-</td><td align='center'>"
						+ rootTmp.getValue("service_status")
						+ "</td><td style='text-align:right;' nowrap>"
						+ (rootTmp.getValue("s_amount").equals("") ? "-" : rootTmp
								.getValue("s_amount"))
						+ "</td><td style='text-align:right;' nowrap>"
						+ (rootTmp.getValue("s_count") == null ? "-" : rootTmp
								.getValue("s_count"))
						+ "</td><td style='text-align:right;' nowrap>";
				
				String tmp1="<a href='#' onclick='getErroInfo(\"S\",\"root\",\""+rootTmp.getValue("service_targets_id")+"\",\""+rootTmp.getValue("service_targets_name")+"\",\"\",\"\")'>"
					+rootTmp.getValue("s_err")+"</a>";
								
				treeData += (("0".equals(rootTmp.getValue("s_err")) ? "0" :tmp1)+"</td><td style='text-align:right;' nowrap>"
						+ (rootTmp.getValue("c_amount") == null ? "-" : rootTmp
								.getValue("c_amount"))
						+ "</td><td style='text-align:right;' nowrap>"
						+ (rootTmp.getValue("c_count") == null ? "-" : rootTmp
								.getValue("c_count"))
						+ "</td><td style='text-align:right;' nowrap>");
				
				String tmp4="<a href='#' onclick='getErroInfo(\"C\",\"root\",\""+rootTmp.getValue("service_targets_id")+"\",\""+rootTmp.getValue("service_targets_name")+"\",\"\",\"\")'>"
						+rootTmp.getValue("c_err")+"</a>";
				treeData +=	(("0".equals(rootTmp.getValue("c_err")) ? "0" :tmp4)	
						+ "</td><td nowrap>");
				if ("Y".equals(rootTmp.getValue("is_share"))) {
					treeData += ("<div class='detail' title='�鿴�������ͳ����Ϣ' onclick='showShareBars(\""
							+ rootTmp.getValue("service_targets_id")
							+ "\",\""
							+ rootTmp.getValue("service_targets_name") + "\")'></div>");
				}
				if ("Y".equals(rootTmp.getValue("is_collect"))) {
					treeData += ("<div class='view' title='�鿴�ɼ�����ͳ����Ϣ' onclick='showCollectBars(\""
							+ rootTmp.getValue("service_targets_id")
							+ "\",\""
							+ rootTmp.getValue("service_targets_name") + "\")'></div>");
				}
				treeData += "</td></tr>";
				// window.open(\""+url+"\",\"_blank\",\"scrollbars=0,resizable=1,width=700,heigth=500\");return
				// false;
				// Ҷ�ڵ�
				if (leaflist != null && leaflist.size() > 0) {
					int leaflen = leaflist.size();
					int num = 1;
					for (int j = 0; j < leaflen; j++) {
						DataBus leafTmp = leaflist.get(j);
						// System.out.println(leafTmp);
						if (rootTmp.get("service_targets_id").equals(
								leafTmp.get("service_targets_id"))) {
							String type = leafTmp.get("codename") == null ? ""
									: leafTmp.get("codename").toString();
							String iconPath = "";
							if ("WebService".equals(type)) {
								iconPath = "/page/zwt/statistcs/typeicon/webservice.png";
							} else if ("�ļ��ϴ�".equals(type)) {
								iconPath = "/page/zwt/statistcs/typeicon/file.png";
							} else if ("FTP".equals(type)) {
								iconPath = "/page/zwt/statistcs/typeicon/ftp.png";
							} else if ("���ݿ�".equals(type)) {
								iconPath = "/page/zwt/statistcs/typeicon/db.png";
							} else if ("JMS��Ϣ".equals(type)) {
								iconPath = "/page/zwt/statistcs/typeicon/jms.png";
							} else if ("SOCKET".equals(type)) {
								iconPath = "/page/zwt/statistcs/typeicon/socket.png";
							} else if ("ETL".equals(type)) {
								iconPath = "/page/zwt/statistcs/typeicon/etl.png";
							}
							String rwMc="";
							
							if ("S".equals(leafTmp.getValue("cors"))) {
							
							 rwMc="<a href='#' onclick='func_viewShareFw(\""+leafTmp.getValue("taskorservice_id")+"\",\"\",\"\")'>"
									+leafTmp.getValue("mc")+"</a>";
							}else{
								
								if ("WebService".equals(type)) {
									
									 rwMc="<a href='#' onclick='func_viewCjFw(\"WebService\",\""+leafTmp.getValue("taskorservice_id")+"\",\"\",\"\")'>"
												+leafTmp.getValue("mc")+"</a>";
								}else if ("FTP".equals(type)) {
									 rwMc="<a href='#' onclick='func_viewCjFw(\"FTP\",\""+leafTmp.getValue("taskorservice_id")+"\",\"\",\"\")'>"
												+leafTmp.getValue("mc")+"</a>";
								}
							}

							treeData += ("<tr data-tt-id='"
									+ leafTmp.getValue("taskorservice_id")
									+ "' data-tt-parent-id='"
									+ leafTmp.getValue("service_targets_id")
									+ "'><td nowrap>&nbsp;&nbsp;"
									+ (i + 1)
									+ "-"
									+ num
									+ "</td><td   title='"
									+ leafTmp.getValue("mc")
									+ "'  ><span class='file' style='width:84%' >"
									+ rwMc
									+ "</span></td><td align='center'><div style=\"background:url('"
									+ iconPath
									+ "')  no-repeat; width:20px;height:20px;\" title='"
									+ leafTmp.getValue("codename")
									+ "'></div></td><td align='center'>"
									+ leafTmp.getValue("status_cn")
									+ "</td><td style='text-align:right;' nowrap>"
									+ TxnSysNoticeInfo.getDefaultStr(leafTmp.getValue("s_amount")) 
									+ "</td><td style='text-align:right;' nowrap>"
									+ TxnSysNoticeInfo.getDefaultStr(leafTmp.getValue("s_count")) 
									+ "</td><td style='text-align:right;' nowrap>");
							
							String tmp2="<a href='#' onclick='getErroInfo(\"S\",\"leaf\",\""+leafTmp.getValue("service_targets_id")+"\",\"\",\""+leafTmp.getValue("taskorservice_id")+"\",\""+leafTmp.getValue("mc")+"\")'>"
									+leafTmp.getValue("s_err")+"</a>";
							if(StringUtils.isBlank(leafTmp.getValue("s_err"))){
								tmp2="-";
							}else if("0".equals(leafTmp.getValue("s_err"))){
								tmp2="0";
							}		
							treeData+=( tmp2+ "</td><td style='text-align:right;' nowrap>"
									+ TxnSysNoticeInfo.getDefaultStr(leafTmp.getValue("c_amount")) 
									+ "</td><td style='text-align:right;' nowrap>"
									+ TxnSysNoticeInfo.getDefaultStr(leafTmp.getValue("c_count")) 
									+ "</td><td style='text-align:right;' nowrap>");
							
							String tmp3="<a href='#' onclick='getErroInfo(\"C\",\"leaf\",\""+leafTmp.getValue("service_targets_id")+"\",\"\",\""+leafTmp.getValue("taskorservice_id")+"\",\""+leafTmp.getValue("mc")+"\")'>"
									+leafTmp.getValue("c_err")+"</a>";
							if(StringUtils.isBlank(leafTmp.getValue("c_err"))){
								tmp3="-";
							}else if("0".equals(leafTmp.getValue("c_err"))){
								tmp3="0";
							}
							treeData+=( tmp3+"</td><td>");
							if ("S".equals(leafTmp.getValue("cors"))) {
								treeData += "<div class='detail' onclick='goS(\""
										+ leafTmp.getValue("taskorservice_id")
										+ "\",\""
										+ leafTmp.getValue("mc")
										+ "\",\""
										+ dateFrom //��ʼʱ��
										+ "\",\""
										+ dateTo //����ʱ��
										+ "\")'></div></td></tr>";
							} else if ("C".equals(leafTmp.getValue("cors"))) {
								treeData += "<div class='view' onclick='goC(\""
										+ leafTmp.getValue("taskorservice_id")
										+ "\",\"" + leafTmp.getValue("mc")
										+ "\")'></div></td></tr>";
							}
							num++;
						}
					}
				}
			}
			//����ܼ���
			total += ("<tr data-tt-id='0'><td>-</td><td nowrap><span >�ܼ�</span></td>"
					+"<td align='center'>-</td><td align='center'>"
					+ "-</td><td style='text-align:right;' nowrap>"
					+ s_amount
					+ "</td><td style='text-align:right;' nowrap>"
					+ s_count
					+ "</td><td style='text-align:right;' nowrap>"
					+s_err
					+"</td><td style='text-align:right;' nowrap>"
					+ c_amount
					+ "</td><td style='text-align:right;' nowrap>"
					+ c_count
					+ "</td><td style='text-align:right;' nowrap>"
					+c_err
					+"</td><td nowrap>");
		}
        
        
        
        out.println(total+treeData);
        %>
         
        </tbody>
      </table>
      <!-- </div> -->
</div>
</td></tr></table>
    
    <%-- <script src="<%=request.getContextPath()%>/page/zwt/statistcs/vendor/jquery-ui.js"></script>
     --%><script src="<%=request.getContextPath()%>/page/zwt/statistcs/javascripts/src/jquery.treetable.js"></script>
    <%-- <script src="<%=request.getContextPath()%>/page/zwt/statistcs/javascripts/src/jquery.ba-throttle-debounce.min.js"></script>
    <script src="<%=request.getContextPath()%>/page/zwt/statistcs/javascripts/src/jquery.stickyheader.js"></script>
     --%> 
     <script>
      
      $("#example-advanced").treetable({ expandable: true ,column : 1});

      // Highlight selected row
      $("#example-advanced tbody").on("mousedown", "tr", function() {
        $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
      });

      // Drag & Drop Example Code
      /* $("#example-advanced .file, #example-advanced .folder").draggable({
        helper: "clone",
        opacity: .75,
        refreshPositions: true, // Performance?
        revert: "invalid",
        revertDuration: 300,
        scroll: true
      }); 

      $("#example-advanced .folder").each(function() {
        $(this).parents("#example-advanced tr").droppable({
          accept: ".file, .folder",
          drop: function(e, ui) {
            var droppedEl = ui.draggable.parents("tr");
            $("#example-advanced").treetable("move", droppedEl.data("ttId"), $(this).data("ttId"));
          },
          hoverClass: "accept",
          over: function(e, ui) {
            var droppedEl = ui.draggable.parents("tr");
            if(this != droppedEl[0] && !$(this).is(".expanded")) {
              $("#example-advanced").treetable("expandNode", $(this).data("ttId"));
            }
          }
        });
      });
      */
      $("form#reveal").submit(function() {
        var nodeId = $("#revealNodeId").val()

        try {
          $("#example-advanced").treetable("reveal", nodeId);
        }
        catch(error) {
          alert(error.message);
        }

        return false;
      });
      
/*       $(function(){
    	  $('span.indenter:first a')[0].click();
      }) */
      
      function fixupTitleRow() { 
			var tab=document.getElementById("example-advanced");
    	  	var div=document.getElementById("freezediv");
    	  	tab.rows[0].style.zIndex="1"; 
    	  	tab.rows[0].style.position="relative";
    	  	tab.rows[1].style.zIndex="1"; 
    	  	tab.rows[1].style.position="relative"; 
	    	  div.onscroll = function(){ 
		    	  var tr = tab.rows[0]; 
		    	  tr.style.top = this.scrollTop-(this.scrollTop==0?1:2); 
		    	  tr.style.left=-1; 
		    	  var tr1 = tab.rows[1]; 
		    	  tr1.style.top = this.scrollTop-(this.scrollTop==0?1:2); 
		    	  tr1.style.left=-1; 
	    	  } 
    	  
   	  } 

   	  window.onload = function(){ 
			//fixupTitleRow(); 
   	  } 
      
      
    </script>

  

</freeze:form>


</div>
</freeze:body>
</freeze:html>
