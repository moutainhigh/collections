<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@ page import="java.util.ArrayList, com.gwssi.common.util.DateUtil"%>
<%-- template single/single-table-query.jsp --%>

<freeze:html height="450">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<%
	DataBus db = (DataBus) request.getAttribute("freeze-databus");
	Recordset rs = db.getRecordset("record");
	Recordset rs1 = db.getRecordset("svrInfo");
	Recordset rsCollcet = db.getRecordset("collcet_task");
	//������������Ϣ
	DataBus SvrTarget = rs1.get(0);
	String targetName = SvrTarget.getString("service_targets_name");
	String crtime = SvrTarget.getString("created_time");
	String targetType = SvrTarget.getString("service_targets_type");
	String targetIP = SvrTarget.getString("ip");
	String targetId = SvrTarget.getString("service_targets_id");
	if(crtime.indexOf(" ") > -1){
		crtime = crtime.substring(0, crtime.indexOf(" "));
	}
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/homepage/preview/css/main.css">
<style type="text/css">
.body-div{background: #FFF;}
h1, h2{color: #246; margin-bottom: 8px;}
h2{padding: 0; margin-top: 5px; margin-bottom: 5px; width: 130px; cursor: pointer;}
.hs{margin: 0 4px; padding: 0;}
div{line-height: 1.5;}
.p_desc{text-indent: 2em; color: #333; line-height: 1.5;}
ul{padding: 0; margin-top: 0px; margin-bottom: 4px; margin-left: 1em; }
ul li{list-style: none; line-height: 1.5;}
.num{color: #006699; font-size: 13px; font-weight: bold;}
.table_a{color: #006699; text-decoration:underline;}
.hide{display:none;}
.right{text-align:left;}
ul.nav li{cursor: pointer; white-space: nowrap;}
.closeBtn{ text-align:center; float:right; width:40px; margin-top:2px; line-height:23px; cursor: pointer; height:22px; }
.closeBtn_over{background: #e2e2e2; color: #333;}
.line_x{background:url("/css/homepage/images/line_x.png") left 23px repeat-x;}
</style>
<script type='text/javascript' src='<%=request.getContextPath()%>/script/uploadfile.js' ></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>

<title><%=targetName %>��������ͳ��</title>
</head>

<script type="text/javascript">
var datas = new Array;
function getCollectTable(){
	var page = new pageDefine("/txn201015.ajax", "���ݽӿ�ID��ȡ���ݱ��б�");
	page.addValue('<%=targetId %>', "select-key:service_targets_id");
	page.callAjaxService('callback');
	}
	//�������ݱ�����

	function callback(errCode, errDesc, xmlResults) {
	  if (errCode != '000000') {
	    alert('�������['+errCode+']==>'+errDesc);
	    return;
	  }
	  
	  var datas = _getXmlNodeValues(xmlResults, "record:service_targets_name");
	  var data_id = _getXmlNodeValues(xmlResults, "record:service_targets_id");
	  var str = "";
	  
	  for(var i=0; i<datas.length; i++){
		  str += "<a href='javascript:;' class='num' onclick='goT(\"" + data_id[i] + "\")'>\""+ datas[i] +"\"</a>��";
	  }
	  str = str.replace(/��$/g, "");
	  if(datas.length > 0){
			$('#collectinfo').html("<%=targetName%>���й��̾��ṩ<span class=\"num\">"+<%=rsCollcet.size()%>+
					"</span>�����ݽӿڷ���<span class=\"num\">"+datas.length+"</span>�����ݱ����ݣ�\n�ֱ�Ϊ "+str+"��");
		}
}
	function getTargetStatics(){
		var page = new pageDefine("/txn201016.ajax", "���ݽӿ�ID��ȡ���ݱ��б�");
		page.addValue('<%=targetId %>', "select-key:service_targets_id");
		page.callAjaxService('callback2');
	}
	
	function callback2(errCode, errDesc, xmlResults) {
		  if (errCode != '000000') {
		    alert('�������['+errCode+']==>'+errDesc);
		    return;
		  }
		  var min_date = _getXmlNodeValues(xmlResults, "record:min_date");
		  var amount = _getXmlNodeValues(xmlResults, "record:amount");
		  var avg_times = _getXmlNodeValues(xmlResults, "record:avg_times");
		  var avg_time = _getXmlNodeValues(xmlResults, "record:avg_time");
		  var error_num = _getXmlNodeValues(xmlResults, "record:error_num");
		  var error_r = _getXmlNodeValues(xmlResults, "record:error_r");
		  
		  if(min_date.length > 0){
			  var d = min_date[2];
			  if(d.indexOf(' ') > -1){
				  d = d.substr(0, d.indexOf(' '));
			  }
			 
			  if(d){
				  d = d.replace(/-0?/, "��").replace(/-0?/, "��") + "��";
			  }
			  
			  $("#min_date").html(d);
			}
		  if(amount.length > 0){
				$("#s_amount").html(amount[0]);
				$("#c_amount").html(amount[1]);
			}
		  if(avg_times.length > 0){
				$("#avg_times").html(Math.floor((parseInt(avg_times[0]) + parseInt(avg_times[1]))/2));
			}
		  if(avg_time.length > 0){
			  var num = (parseFloat(avg_time[0]) + parseFloat(avg_time[1]))/2;
				$("#avg_time").html(num.toFixed(2));
			}
		  if(error_num.length > 0){
				$("#error_num").html(parseInt(error_num[0]) + parseInt(error_num[1]));
			}
		  if(error_r.length > 0){
			  var num = (parseFloat(error_r[0]) + parseFloat(error_r[1]))/2;
				$("#error_r").html(num.toFixed(2));
			}
	}
	function goT(collect_table_id){
		if(typeof(collect_table_id) != "undefined"){
			var page = new pageDefine( "/txn20201006.do", "�鿴�ɼ�����Ϣ", "_blank");
			page.addValue( collect_table_id, "primary-key:collect_table_id" );
			page.updateRecord();
		}
	}
	// ����������ӣ�ҳ�������ɺ���û���ʼ������
	function __userInitPage() {
		<% if(rsCollcet.size() > 0){
			out.println("getCollectTable()");
		} %>
		getTargetStatics();
		var ctime = '<%=crtime %>';
		ctime = ctime.replace(/-0?/, "��").replace(/-0?/, "��") + "��";
		$('#crtime').html(ctime);
		var d = new Date();
		d = d.getFullYear() + "��" + (d.getMonth()+1) + "��" + d.getDate() + "��";
		$('#today').html(d);
		
		$('h2').bind("click", function(){
			$(this).next("div").toggle();
		})
		$('.left .nav li').bind('click', function(){
			$('.right').html($('#hs_'+$(this).index()).html());
		})
		$('.all-sort-list .item:first').addClass('hover');
		$('.all-sort-list .item:first').children('.item-list').css('display','block');
	}

	function goS(service_targets_id,service_name){
		if(typeof(service_targets_id)==='undefined'){
			var page = new pageDefine( "/txn53000112.do", "�����������ͳ��", "modal", 1000, 450);
			page.addValue( '<%=targetId %>', "select-key:service_targets_id" );
			page.addValue( '<%=targetName %>', "select-key:service_targets_name" );
		}else{
			var page = new pageDefine( "/txn40200025.do", "�鿴�������", "modal");
			page.addValue( service_targets_id, "primary-key:service_id" );
			page.addValue( service_name, "primary-key:service_name" );
		}
		page.goPage();
	}
	
	function goC(collect_task_id, service_targets_name){
		if(typeof(service_targets_name) != 'undefined' && typeof(collect_task_id) != 'undefined'){
			var page = new pageDefine( "/txn30101018.do", "�鿴��������ͳ��", "modal");
			page.addValue( service_targets_name, "select-key:service_targets_name" );
			page.addValue( collect_task_id, "primary-key:collect_task_id" );
		
		}else if(typeof(service_targets_id)==='undefined'){
			var page = new pageDefine( "/txn53000115.do", "�ɼ���������ͳ��", "modal", 1000, 450);
			page.addValue( '<%=targetId %>', "select-key:service_targets_id" );
			page.addValue( '<%=targetName %>', "select-key:service_targets_name" );
		}
		page.goPage();
	}
	_browse.execute('__userInitPage()');
</script>

<freeze:body >
<freeze:errors />
<table  cellpadding="0" cellspacing="0" width="98%" align="center" style="margin-top: 3px; border-collapse: collapse;">
<tr ><td class="line_x" colspan="2" >
	<div style="float:left;padding-top:3px;height:24px;"><h1 style="font-size: 16px"><%=targetName %></h1></div> 
	<div class="closeBtn" style="" onclick="javascript:parent.closeIframe()">�ر�</div><div style="clear:both;"></div>
</td></tr>
<%-- <tr><td colspan="2" style="" >
<div class="p_desc" style="width:100%;font-size: 13px;">���û�������<span id="crtime" class="num"></span>���ṩ���ݹ���<%if(rsCollcet.hasNext()){out.println("���ɼ��ӿ�");} %>���񡣽�ֹ����ǰ,�й��̾���<%=targetName %>�ṩ<a href="#shareService"><span class="num"><%=rs.size() %></span></a>�����ݹ������
	</div>
	</td></tr> --%>
<tr>
<td colspan="2" style="">
<div class="line_x" style="height:3px; "></div>
<div class="wrap">
		<div class="all-sort-list">
			<div class="item bo" >
				<h3 id='data'><span>��</span>�������</h3>
				<div class="item-list clearfix">
					<div class="subitem" >
						<div>
							<div id="hs_0" class="hs"><div>
							<!--  <dl class="fore1"><div class="p_desc" style="width:100%;font-size: 13px;">���û�������<span id="crtime" class="num"></span>���ṩ���ݹ���<%if(rsCollcet.size()>0){out.println("���ɼ��ӿ�");} %>���񡣽�ֹ����ǰ,�й��̾���<%=targetName %>�ṩ--><!-- <a href="#shareService"> --><!--  <span class="num"><%=rs.size() %></span>--><!-- </a> --><!--  �����ݹ������-->
							<!--</div></dl>-->
							<d1 class="fore1" style="font-size:13px;color:#333">����ʱ�䣺<span id="crtime" class="num"></span><br></d1>
							<d1 class="fore2" style="font-size:13px;color:#333">������������<span class="num"><%=rs.size() %></span><br></d1>
							<d1 class="fore3" style="font-size:13px;color:#333">�ɼ����������<span class="num"><%=rsCollcet.size()%></span><br></d1>
							<!--  <dl class="fore2"> -->
							<!-- <div class="p_desc" style="width:100%;font-size: 13px;" id="collectinfo"></div> -->
							<!-- </dl> -->
							
							<dl class="fore3" style="font-size:13px;color:#333">��IP��<%=(null == targetIP) ? "��" : targetIP %></dl>
							<dl class="fore4" style="font-size:13px;color:#333">����������<%
						    Recordset fileList=null;
						    try{
						     fileList = db.getRecordset("fjdb");
						    if(fileList!=null && fileList.size()>0){
						        for(int i=0;i<fileList.size();i++){
						               DataBus file = fileList.get(i);
						               String file_id = file.getValue(FileConstant.file_id);
						               String file_name = file.getValue(FileConstant.file_name);
						%><a href="<%=request.getContextPath()%>/txn604050209.do?primary-key:ysbh_pk=<%=file_id%>"  title="����" ><%=null==file_name ? "" : file_name %></a>
						</dl><%  }
						   }
						   }catch(Exception e){
							   System.out.println(e);
						   }
						%>   
							<dl class="fore5" style="font-size:13px;color:#333">��������ʱ�䣺<span class='num' id="min_date"></span></dl>
							<dl class="fore6" style="font-size:13px;color:#333">��ֹ����ǰ��<span class='num' id="today"></span>����������������£�</dl>
							<ul style="font-size:13px;color:#333"><li>��������Ϊ��<span class="num" id="s_amount"></span>������</li>
							<% if(rsCollcet.size() > 0){ %>
								<li>�ɼ�����Ϊ��<span class="num" id="c_amount"></span>������</li>
							<% } %>
							<li>ƽ��ÿ����Ӧ����Ϊ��<span class="num" id='avg_times'></span>���Σ�</li>
							<li>ƽ����Ӧʱ��Ϊ��<span class="num" id='avg_time'></span>���룩��</li>
							<li>��������� <span class="num" id='error_num'></span>���Σ� </li>
							<li>��������� <span class="num" id='error_r'></span>% </li>
							</ul>
						
							</div></div>
							
							</div>
					</div>
					
				</div>
			</div>
			
			<div class="item">
				<h3 id='dataStatics'><span>��</span>����ͳ��</h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<div>
						<div id="hs_2" class="hs">
						<dl class="fore1">
						<dt>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="goS()"><%=targetName %>�����������ͳ��</a>
						</dt>
						<%if(rsCollcet.size() > 0){%>
						<dt>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="goC()"><%=targetName %>�ɼ���������ͳ��</a>
						</dt>
						<%} %>
						<dt><br /><br /><br /><br /></dt></dl></div>
						</div>
					</div>
				</div>
			</div>
			<div class="item">
				<h3 id='shareService'><span>��</span>�������</h3>
				<div class="item-list clearfix">
					
					<div class="subitem">
						<div>
							<%
							if(rs.size() > 0){
								out.println("<div id='hs_3' class='hs'><ul>");
								int i = 1;
								while (rs.hasNext()) {
									DataBus data = (DataBus) rs.next();
									String service_id = data.getValue("service_id");
									String service_no = data.getValue("service_no");
									String service_name = data.getValue("service_name");
									//String url = "/txn40200025.do?primary-key:service_id="+ service_id;
									out.println("<dt><li>"+i+") <a href='javascript:;' onclick='goS(\""+service_id+"\",\""+service_name+"\")' >" + service_name + "</a></li></dt>");
									i++;
								}
								out.println("</ul></div>");
							}
							
							%></div>
					</div>
					
				</div>
			</div>
			<%if(rsCollcet.size() > 0){%>
			<div class="item">
				<h3 id='collectTask'><span>��</span>�ɼ�����</h3>
				<div class="item-list clearfix">
					
					<div class="subitem">
						<div><%
								out.println("<div id='hs_4' class='hs'><ul>");
									String service_targets_name = "";
									int i = 1;
									while (rsCollcet.hasNext()) {
										DataBus data = (DataBus) rsCollcet.next();
										String collect_task_id = data.getValue("collect_task_id");
										String collect_type = data.getValue("collect_type");
										String service_name = data.getValue("task_name");
										service_targets_name = data.getValue("service_targets_name");
										String url = "#";
										//url = "/txn30101018.do?primary-key:collect_task_id="+ collect_task_id+"&record:service_targets_name="+service_targets_name;
										out.println("<dt><li>" + i + ") <a href='javascript:;' onclick='goC(\""+collect_task_id+"\", \""+service_targets_name+"\")' >" + service_name + "</a></li></dt>");
										i++;		
									}
									out.println("</ul></div>");
							%>
							</div>
					</div>
				</div>
			</div>
		<%} %>
		</div>
	</div>
<script type="text/javascript">
		$('.all-sort-list > .item').hover(function(){
			$('.all-sort-list .item').removeClass('hover'); //�Ȱ�ѡ��״̬ȥ��
			$('.all-sort-list .item').children('.item-list').css('display','none');
			
			var eq = $('.all-sort-list > .item').index(this),	//��ȡ��ǰ�����ǵڼ���Ԫ��
				h = $('.all-sort-list').offset().top,	//��ȡ��ǰ�����˵����봰�ڶ�������
				s = $(window).scrollTop(),		//��ȡ�����������˶��ٸ߶�
				i = $(this).offset().top,	//��ǰԪ�ػ������봰�ڶ�������
				item = $(this).children('.item-list').height(),	//�����˵��������������ĸ߶�
				sort = $('.all-sort-list').height();//��������б������ĸ߶�
			
			if ( item < sort ){	//�������ĸ߶�С�ڸ���ĸ߶�
				if ( eq == 0 ){
					$(this).children('.item-list').css('top', (i-h));
				} else {
					$(this).children('.item-list').css('top', (i-h)+1);
				}
			} else {
				if ( s > h ) {	//�ж��������ʾλ�ã���������ĸ߶ȴ������з����б������ĸ߶�
					if ( i-s > 0 ){	//�� �����жϵ�ǰ����������λ�� �Ƿ���һ�볬������һ���ڴ�������ʾ��Bug,
						$(this).children('.item-list').css('top', (s-h)+2 );
					} else {
						$(this).children('.item-list').css('top', (s-h)-(-(i-s))+2 );
					}
				} else {
					$(this).children('.item-list').css('top', 3 );
				}
			}	

			$(this).addClass('hover');
			$(this).children('.item-list').css('display','block');
		},function(){
			$(this).removeClass('hover');
			$(this).children('.item-list').css('display','none');
		});

		$('.item > .item-list > .close').click(function(){
			$(this).parent().parent().removeClass('hover');
			$(this).parent().hide();
		});
		$('.closeBtn').bind('mouseover', function(){
			$(this).addClass("closeBtn_over");
		}).bind('mouseout', function(){
			$(this).removeClass("closeBtn_over");
		})
	</script>
</td></tr>
</table>
	<form action="/txn53000112.do" target="sw" id="allpost" method="post">
	<input type="hidden" name="select-key:service_targets_id" id="select-key:service_targets_id" />
	<input type="hidden" name="select-key:service_targets_name" id="select-key:service_targets_name" />
	<input type="hidden" name="select-key:collect_task_id" id="select-key:collect_task_id" />
	<input type="hidden" name="primary-key:service_id" id="primary-key:service_id" />
	</form>
	<br />
</freeze:body>
</freeze:html>
