<%@page import="com.gwssi.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<freeze:html>
<head>
<!-- <meta http-equiv="X-UA-Compatible" content="IE=9,IE=8,IE=EDGE" > -->
<title>��ѯ��־</title>

<script type="text/javascript" src="/script/lib/jquery171.js"></script>

<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>


<!-- 
<link rel="stylesheet" href="/script/lib/skin/query.form.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="/script/timepicker/css/jquery-ui.css" />
<script type="text/javascript" src="/script/lib/jquery171.js" charset="UTF-8" ></script>
<script type="text/javascript" src="/script/timepicker/js/jquery-ui.js" charset="UTF-8" ></script>
<script type="text/javascript" src="/script/timepicker/js/jquery-ui-slide.min.js" charset="UTF-8" ></script>
<script type="text/javascript" src="/script/timepicker/js/jquery-ui-timepicker-addon.js" charset="UTF-8" ></script>
<script type="text/javascript" src="/script/lib/jquery/jquery.qform.js" ></script>
 -->


</head>

<script>
	// ����������ӣ�ҳ�������ɺ���û���ʼ������
	function __userInitPage() {

		var ids = getFormAllFieldValues("record:tsid");
		
		var service_id = getFormAllFieldValues("record:service_id");
		var names = getFormAllFieldValues("record:tsname");
		var tablefroms = getFormAllFieldValues("record:tablefrom");
		
		var status = getFormAllFieldValues("record:status");
		
		var targetnames = getFormAllFieldValues("record:service_targets_name");

		for ( var i = 0; i < ids.length; i++) {
			var id = ids[i];
			var name=names[i];
			var tablefrom=tablefroms[i];
			document.getElementsByName("span_record:tsname")[i].innerHTML = '<a href="javascript:func_record_showdetail(\''
					+ id + '\',\''+tablefrom+'\');" title="'+name+'" >'+name+'</a>';
					
			var htm2 = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_viewConfig(\''
							+ i + '\');">' + targetnames[i] + '</a>';
			document.getElementsByName("span_record:service_targets_name")[i].innerHTML = htm2;
			
			if(status[i]!=null&&status[i]=="ʧ��"){
				var htm3 = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_viewFailDetail(\''
					+ i + '\');">'  + status[i] + '</a>';
				document.getElementsByName("span_record:status")[i].innerHTML = htm3; 
			
			}
			if(tablefroms[i]=='cj'){
				setFormFieldValue("record:fwlx", i,'�ɼ�');
			}else{
				setFormFieldValue("record:fwlx", i,'����');
			}

		}
		$('#t4').find('.pack-list li:eq(1)').hide();
		
	}
	
	//��ȡ������Ϣ
	function func_viewFailDetail(idx){
		var page = new pageDefine( "/txn53000217.do", "������ϸ��Ϣ", "_blank",1000,550);
		
		var targets_id = getFormFieldValue("record:service_targets_id", idx);
		var id= getFormFieldValue("record:service_id", idx);
		var startTime= getFormFieldValue("record:start_time", idx);
		var type;
		var tablefrom = getFormFieldValue("record:tablefrom", idx);
		
			page.addValue( targets_id, "select-key:service_targets_id" );
			if(tablefrom=="cj"){//�ɼ�
				page.addValue( id, "select-key:collect_task_id" );
			    type="C";
			}else{//����
				page.addValue( id, "select-key:service_id" );
				type="S";
			}
		
		page.addValue( type, "select-key:type" );
		page.addValue( "leaf", "select-key:location" );
		page.addValue(startTime,"select-key:startTime");
		page.addValue(startTime,"select-key:endTime");
		page.goPage();
	}
	function func_record_showdetail(tsid,tablefrom) {
		//alert(tsid+"--"+tablefrom);
		if(tablefrom=='cj'){
			var url="txn6011006.do?select-key:collect_joumal_id="+tsid;
		    var page = new pageDefine( url, "��ѯ�ɼ�������Ϣ","modal", 750, 300);
		    page.goPage();
		}else{
			
			var url = "txn6010006.do?select-key:log_id=" + tsid;
			var page = new pageDefine(url, "��ѯ���������Ϣ","modal");
			page.goPage();
		}
		
		 
		    
	}
	function func_record_guidang(){
		var typechoose = $('#t1_choose').val();
		if(typechoose){
			if(typechoose=='c'){//�鵵�ɼ���־
				var page = new pageDefine( "update-collect_archive.jsp", "�ɼ���־�鵵","modal");
				page.addRecord();
			}else if(typechoose=='s'){//�鵵������־
				var page = new pageDefine( "update-share_archive.jsp", "������־�鵵","modal");
				page.addRecord();
			}
		}else{
			alert("ֻ�ܹ鵵ͬһ���͵���־,����ѡ����־����Ϊ'�ɼ�����'��'�������'");
		}
	}
	
	
	//�鿴�������
	function func_viewConfig(idx) {
		
		var svrId = getFormFieldValue("record:service_targets_id", idx);
		var page = new pageDefine( "/txn201009.do", "�鿴�������","modal" );
		page.addValue( svrId, "primary-key:service_targets_id" );
		page.updateRecord();
	}
	
	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="��ѯ��־�б�"/>
<freeze:errors/>
<gwssi:panel action="txn6011020" target="" parts="t1,t2,t3,t4" styleClass="wrapper">
   <gwssi:cell id="t1" name="��־����" key="type" data="svrType" />
   <gwssi:cell id="t2" name="�������" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget" pop="true"  maxsize="10" />
   <gwssi:cell id="t3" name="��־״̬" key="status" data="svrStatus" />
   <gwssi:cell id="t4" name="��ʼʱ�� " key="created_time" data="created_time" dateTime="true" />
 </gwssi:panel>


  <freeze:form action="/txn6011020">
	  <freeze:frame property="select-key" >
     	<freeze:hidden property="type" caption="��־����" />
    	<freeze:hidden property="service_targets_id" caption="�������" />
    	<freeze:hidden property="service_targets_type" caption="�����������" />
     	<freeze:hidden property="created_time" caption="��ʼʱ��" />
     	<freeze:hidden property="status" caption="��־״̬" />
  	  </freeze:frame>
  <freeze:grid property="record" checkbox="false" caption="��ѯ��־�б�" keylist="tsid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_backupRecord" caption="�鵵" enablerule="0" hotkey="ADD" align="right" onclick="func_record_guidang();"/>
			<freeze:hidden property="tsid" caption="����" />
			<freeze:hidden property="service_id" caption="����ID" />
			<freeze:hidden property="service_targets_id" caption="�������ID" />
			<freeze:hidden property="tablefrom" caption="��Դ��" />
			<freeze:cell property="@rowid" caption="���" style="width:5%"  align="center"/>
			<freeze:cell property="service_targets_name" align="center" caption="�������"
				style="width:10%" />
			<freeze:cell property="fwlx" caption="��������" align="center" style="width:10%" />
			<freeze:cell property="tsname" caption="�������/�ɼ���������" align="center" style="width:20%" />
			<freeze:cell property="start_time" caption="����ʼʱ��" align="center"
				style="width:18%" />
			<freeze:cell property="consume_time" caption="��ʱ(��)" align="center"
				style="width:10%" />
			<freeze:cell property="record_amount" caption="������(��)" align="center"
				style="width:8%" />
			<freeze:cell property="status" caption="״̬" align="center"
				style="width:12%" />
			
  </freeze:grid>

</freeze:form>

</freeze:body>
</freeze:html>
