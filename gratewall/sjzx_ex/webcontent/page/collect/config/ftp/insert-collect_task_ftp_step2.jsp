<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>

<title>���Ӳɼ�������Ϣftp</title>
<link
		href="<%=request.getContextPath()%>/script/lib/skin-vista/ui.dynatree.css"
		rel="stylesheet" type="text/css">
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
	<script language="javascript"
		src="<%=request.getContextPath()%>/script/lib/jquery.dynatree.js"></script>
</head>
<script language='javascript' src='/script/uploadfile.js'></script>
<%


%>

<script language="javascript">


//ˢ��ҳ��
function func_refresh(){
	
	var page = new pageDefine( "/txn30101110.do", "ˢ��");
	page.addParameter("select-key:collect_task_id","select-key:collect_task_id");
	page.addParameter("select-key:task_name","select-key:task_name");
	page.addParameter("select-key:flag","select-key:flag");
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.goPage();
}
//���ص�һ������ҳ��
function func_record_prev(){
	
	var page = new pageDefine( "/txn30101104.do", "���òɼ���Ϣ");
	page.addParameter("select-key:collect_task_id","select-key:collect_task_id");
	page.addParameter("select-key:task_name","select-key:task_name");
	page.addParameter("select-key:flag","select-key:flag");
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.goPage();
}
//������Ϣ���������
function func_record_next()
{
	var first_line = document.getElementById("row_0");
	if (!first_line){
	  alert('������Ҫ���һ���ļ���Ϣ���ܽ�����һ��������!');
	  return;
	}
	
	
	
	var page = new pageDefine( "/txn30101102.do", "���òɼ���Ϣ");
	page.addParameter("select-key:collect_task_id","select-key:collect_task_id");
	page.addParameter("select-key:task_name","select-key:task_name");
	page.addParameter("select-keyd:flag","select-key:flag");
	page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
	page.goPage();
	
 	/* document.forms["CollectTaskUpdateForm"].setAttribute("action", "/txn30101102.do");
 	saveRecord( '', '����ɼ�����' ); */
 	
	
}

//������ļ�
function addNewFile(){
		
	/* var page = new pageDefine( "/txn30101109.do", "���òɼ���Ϣ","modal");
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:task_name","record:task_name");
	page.addParameter("record:flag","record:flag");
	page.addParameter("record:service_targets_id","record:service_targets_id");
	page.goPage(); */
	var service_targets_id=getFormFieldValue('select-key:service_targets_id');
	var collect_task_id=getFormFieldValue('select-key:collect_task_id');
	var task_name=getFormFieldValue('select-key:task_name');
	var flag=getFormFieldValue('select-key:flag');
	var href='/txn30101109.do?record:service_targets_id='+service_targets_id
	+'&record:collect_task_id='+collect_task_id
	+'&record:task_name='+task_name
	+'&record:flag='+flag;
	window.showModalDialog(href, window, "dialogHeight:450px;dialogWidth:800px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
	
}


//�����ݿ�ɾ��ָ�����ļ���Ϣ
function delFileInfo(ftp_task_id,file_name_cn,file_name_en){
	
		if(confirm("ȷ��ɾ���ļ�"+file_name_en+"("+file_name_cn+")?")){
			var page = new pageDefine( "/txn30101108.do", "���òɼ���Ϣ");
			page.addParameter("select-key:collect_task_id","select-key:collect_task_id");
			page.addParameter("select-key:task_name","select-key:task_name");
			page.addParameter("select-key:flag","select-key:flag");
			page.addParameter("select-key:service_targets_id","select-key:service_targets_id");
			page.addValue(ftp_task_id,"select-key:ftp_task_id");
			page.goPage();
			/* document.forms["CollectTaskUpdateForm"].setAttribute("action", "/txn30101108.do");
		 	saveRecord( '', '����ɼ�����' ); */
		}
	
	
}

//�޸�ָ�����ļ���Ϣ
function updateFileInfo(ftp_task_id){
	/* var page = new pageDefine( "/txn30101109.do", "���òɼ���Ϣ","modal");
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:task_name","record:task_name");
	page.addParameter("record:flag","record:flag");
	page.addParameter("record:service_targets_id","record:service_targets_id");
	page.addValue(ftp_task_id,"record:ftp_task_id");
	page.goPage(); */
	var service_targets_id=getFormFieldValue('select-key:service_targets_id');
	var collect_task_id=getFormFieldValue('select-key:collect_task_id');
	var task_name=getFormFieldValue('select-key:task_name');
	var flag=getFormFieldValue('select-key:flag');
	var href='/txn30101109.do?record:service_targets_id='+service_targets_id
	+'&record:collect_task_id='+collect_task_id
	+'&record:task_name='+task_name
	+'&record:flag='+flag
	+'&record:ftp_task_id='+ftp_task_id;
	window.showModalDialog(href, window, "dialogHeight:450px;dialogWidth:800px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
	
}
//�ɼ���ѡ���ȡֵ
function getParameter(){	
	
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
	service_targets_id=getFormFieldValue('select-key:service_targets_id');
    var parameter = 'input-data:service_targets_id='+service_targets_id;
    //console.log(parameter);
    //return 'input-data:service_targets_id=e405cb2e140241b599755d4e25130755';
	return parameter;
}

/* //��ʼ����
function initTree(){
	var page = new pageDefine("/txn30101109.ajax", "��ѯĳ���û��ķ�����");
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:service_targets_id","record:service_targets_id");
	page.addParameter("record:task_name","record:task_name");
	page.callAjaxService("callBack()");
}

function callBack(errCode, errDesc, xmlResults){
	 if(errCode!='000000'){
		alert("��ѯĳ���û��ķ�����ʱ��������")
		return
	 }
		
	
	 var file_name_en = _getXmlNodeValues(xmlResults,'treeinfo:file_name_en');//Ӣ����
	 var file_name_cn = _getXmlNodeValues(xmlResults,'treeinfo:file_name_cn');//������
	 var collect_table = _getXmlNodeValues(xmlResults,'treeinfo:collect_table');//�ɼ���
	 var collect_mode = _getXmlNodeValues(xmlResults,'treeinfo:collect_mode');//�ɼ���ʽ
	 var ftp_task_id = _getXmlNodeValues(xmlResults,'treeinfo:ftp_task_id');//����
	 var file_description = _getXmlNodeValues(xmlResults,'treeinfo:file_description');//�ļ�����
	
	 //�����ӽڵ�����
	 var s = new Array;
	 var firstKey;
	 var len=ftp_task_id.length;
	 if(len>0){
		 
		 firstKey=ftp_task_id[0];
		 for(var i=0;i<len;i++){
			 s.push({'title':"("+file_name_en[i]+")"+file_name_cn[i],
				 'tooltip':"("+file_name_en[i]+")"+file_name_cn[i],
				 'file_description':file_description[i],
				 'file_name_cn':file_name_cn[i],
				 'file_name_en':file_name_en[i],
			 	 'collect_table':collect_table[i],
			 	 'collect_mode':collect_mode[i],
				 'key':ftp_task_id[i]});
		 }
	 }
	 
	 var task_name=getFormFieldValue('record:task_name');
	 //���ø��ڵ���Ϣ
     var r1 =  {"title": task_name, "isFolder": true, "children": s,"key": "root" }
     var root = new Array;
     root.push(r1);

     obj = $("#tree").dynatree({
           children: root,
           minExpandLevel: 2,
		   onActivate: function(node) {
			  // alert(node.data.key);
				if( node.data.key ){
					if(node.data.key=="root" ){
						addNewFile();//������ڵ㣬�൱��Ҫ�����ļ�
					}else{
						//����ӽڵ㣬��ʾ��Ӧ���ļ���Ϣ���޸�
						//alert(node.data.key);
			  			setFormFieldValue("fileinfo:file_name_en",0,node.data.file_name_en);
			  			setFormFieldValue("fileinfo:file_name_cn",0,node.data.file_name_cn);
			  			setFormFieldValue("fileinfo:file_description",0,node.data.file_description);
			  			setFormFieldValue("fileinfo:collect_table",0,node.data.collect_table);
			  			setFormFieldValue("fileinfo:collect_mode",0,node.data.collect_mode);
			  			setFormFieldValue("fileinfo:ftp_task_id",0,node.data.key);	
					}
					
					
				}
			}
		});
	
	if(firstKey){
	   	var fnode = $("#tree").dynatree("getTree").getNodeByKey(firstKey);
	   	//fnode.activate();
	}else if(len==0){
		$("#tree").dynatree("getTree").getNodeByKey("root").activate();
	}
	
}
 */
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
/* 	var collect_task_id = getFormFieldValue("record:collect_task_id");
	 var service_targets_id = getFormFieldValue("record:service_targets_id");
	 
	alert("collect_task_id="+collect_task_id+"--service_targets_id="+service_targets_id);  */
	//addSelectInputField( new SelectInputField( 'fileinfo', 'collect_table', 'browsebox', '��Դ����_��������Ӧ�ɼ���', 'name', 'collect_table', '', '', '', 'getParameter();',new Array() ) );
	var ids = getFormAllFieldValues("treeinfo:ftp_task_id");
	var name_cn = getFormAllFieldValues("treeinfo:file_name_cn");
	var name_en = getFormAllFieldValues("treeinfo:file_name_en");
	
	for(var i=0; i<ids.length; i++){
	   var htm='';
	   htm='<a href="#" title="�޸�" onclick="updateFileInfo(\''+ids[i]+'\');"><div class="edit"></div></a>';
	   htm+='<a href="#" title="ɾ��" onclick="delFileInfo(\''+ids[i]+'\',\''+name_cn[i]+'\',\''+name_en[i]+'\');"><div class="delete"></div></a>';
	   document.getElementsByName("span_treeinfo:oper")[i].innerHTML +=htm;
	}
	
	//initTree();
	
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="���Ӳɼ�������Ϣ"/>
<freeze:errors/>
<freeze:form action="/txn30101101" >

  <freeze:block property="select-key" width="95%">
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
      <freeze:hidden property="task_name" caption="�ɼ���������" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="�������ID" datatype="string" maxlength="32"  style="width:80%"/>
	  <freeze:hidden property="flag" caption="������������Ǳ༭"  />						      
      
  </freeze:block>

<div style="width: 95%;margin-left:20px;">
		<table style="width: 80%;align:left;">
			<tr>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								��һ�������û�����Ϣ
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="color: white; background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								�ڶ����������ļ���Ϣ
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/blue_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>
				<td width="30%">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_l.png') left 50% no-repeat;"
								width="2" height="25" valign="middle"></td>
							<td height="25"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_c.png') left 50% repeat-x;">
								<div
									style="background: url(<%=request.getContextPath()%>/ images/ xzcjbg/ icon_bg .                                                                     png ) left 50% no-repeat; width: 20px; display: inline;"></div>
								�����������ù�����Ϣ
							</td>
							<td width="5" height="25" valign="middle"
								style="background: url('<%=request.getContextPath()%>/images/xzcjbg/gray_bg_r.png') left 50% no-repeat;"></td>
						</tr>
					</table>
				</td>

			</tr>
		</table>
	</div>

<freeze:grid property="treeinfo" caption="�ɼ������ļ������б�" keylist="ftp_task_id" width="95%"  multiselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���Ӳɼ��ļ�"  enablerule="0" hotkey="ADD" align="right" onclick="addNewFile();"/>
     <freeze:hidden property="ftp_task_id" caption="����ID" datatype="string" maxlength="32"  style="width:80%"/>
      
      <freeze:cell property="@rowid" caption="���"  style="width:5%" align="center" />
     <freeze:cell property="file_name_en" caption="�ļ�Ӣ������" datatype="string"  style="width:20%"/>
      <freeze:cell property="file_name_cn" caption="�ļ���������" datatype="string"  style="width:15%"/>
      <freeze:cell property="file_description" caption="�ļ�����"  style="width:30%"/>
      <freeze:cell property="collect_table" caption="�ɼ���" align="center"  valueset="��Դ����_�ɼ���" show="name" style="width:20%"/>
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ" align="center" show="name" valueset="��Դ����_�ɼ���ʽ"  style="width:8%"/>
       <freeze:cell property="oper" caption="����" style="width:7%; text-align: center;" />

  </freeze:grid>	
	<table align='center' cellpadding=0 cellspacing=0 width="95%">
			<tr>
				<td align="center" height="50">
					<div class="btn_prev" onclick="func_record_prev();"></div>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="btn_next" onclick="func_record_next();"></div>
				</td>
			</tr>
	</table>
	
	
	
 </freeze:form>
<!--  <input type="button" value="ˢ��" onclick="func_refresh()"></input> -->
</freeze:body>
</freeze:html>
