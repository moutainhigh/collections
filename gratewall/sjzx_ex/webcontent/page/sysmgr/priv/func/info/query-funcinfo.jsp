<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html title="��ѯ������Ϣ�б�">
<freeze:include href="/script/gwssi-xtree.js"/>
<freeze:include href="/script/struts-coolmenus3.js"/>

<freeze:body>
<freeze:title caption="��ѯ������Ϣ�б�"/>
<freeze:errors/>

<script language="javascript">
function funcClick( )
{
	if( funcinfo.selectedNode.id == '@temp:add-root-node' ){
		var page = new pageDefine( "insert-funcinfo.jsp", "���ӹ�����Ϣ", "modal", 650, 300);
		var ret = page.addRecord( );
		if( ret == 1 ){
			_browse.refresh();
		}
	}
	else{
		var page = new pageDefine( "/txn980311.do", "���ܵĽ�����Ϣ" );
  		page.addValue(funcinfo.selectedNode.id, "select-key:funccode");
		var win = window.frames('txnlist');
		page.goPage( null, win );
	}
}

function func_record_addRecord()
{
	// ȡ�ϼ��ڵ�
	var	pid = null;
	if( funcinfo != null && funcinfo.selectedNode != null ){
		pid = funcinfo.selectedNode.id;
	}
	
	if( pid != null && pid != "" && pid != '@temp:add-root-node' ){
		pid = "record:parentcode=" + pid;
	}
	
    var page = new pageDefine( "insert-funcinfo.jsp", "���ӹ�����Ϣ", "modal", 650, 300);
	var ret = page.addRecord( pid );
	if( ret == 1 ){
		refreshPage();
	}
}

function func_record_updateRecord()
{
	// ȡ�ϼ��ڵ�
	if( funcinfo == null || funcinfo.selectedNode == null ){
		alert( 'û��ѡ����Ҫ�޸ĵļ�¼' );
		return;
	}
	
	var pid = funcinfo.selectedNode.id;
	if( pid == null || pid == "" || pid.indexOf('@temp:') == 0 ){
		alert( 'û��ѡ����Ҫ�޸ĵļ�¼' );
		return;
	}
	
	// �޸Ľڵ�
    var page = new pageDefine( "/txn980304.do", "�޸Ĺ�����Ϣ", "modal", 650, 300);
    page.addValue( pid, "primary-key:funccode" );
    page.updateRecord( );
}

function func_record_deleteRecord()
{
	// ȡ�ϼ��ڵ�
	if( funcinfo == null || funcinfo.selectedNode == null ){
		alert( 'û��ѡ����Ҫɾ���ļ�¼' );
		return;
	}
	
	var pid = funcinfo.selectedNode.id;
	if( pid == null || pid == "" || pid.indexOf('@temp:') == 0 ){
		alert( 'û��ѡ����Ҫɾ���ļ�¼' );
		return;
	}
	
	if( funcinfo.selectedNode.submenu != null ){
		alert( 'ѡ�еĹ����а����ӹ��ܣ����ܱ�ɾ��' );
		return;
	}
	
	var page = new pageDefine( "/txn980305.do", "ɾ��������Ϣ");
	
	// ����ִ�гɹ�ʱ���õĺ����򷵻ص�ҳ���ַ
	page.nextPage = "@refreshPage";
	
	// ������������÷���
	page.addValue( pid, 'primary-key:funccode' );
	page.deleteRecord("�Ƿ�ɾ��ѡ�еļ�¼");
}

function moveNode( iType )
{
	var srcId = funcinfo.selectedNode.id;
	var destId = funcinfo.menuNode.id;
	
	if( iType != 'sub' ){
		var p = funcinfo.menuNode.parentNode;
		if( p == null ){
			destId = '';
		}
		else{
			destId = p.id;
		}
	}
	
	var	page = new pageDefine( "/txn980307.do", '�ƶ����ܽڵ�' );
	
	// ����ִ�гɹ�ʱ���õĺ����򷵻ص�ҳ���ַ
	page.nextPage = "@refreshPage";
	
	// ������������÷���
	page.addValue( srcId, 'record:funccode' );		// �ƶ���ԭʼ�ڵ�
	page.addValue( destId, 'record:parentcode' );	// �ƶ���Ŀ��ڵ�
	// page.addValue( iType, 'record:move-type' );		// �ƶ���ʽ��post,pre,sub
	page.callService( '�ƶ����ܽڵ�' );
}

function prepareTree()
{
	// �������һ���ڵ�
	funcinfo.addNode( '', '@temp:add-root-node', '�����¹���' );
}

// ˢ��ҳ��
function refreshPage()
{
	if( funcinfo != null && funcinfo.selectedNode != null ){
		var page = new pageDefine( menuPath + "query-funcinfo.jsp", "ˢ���б�", "window", 500, 250 );
	    
	    // ���õ�ǰѡ�еĽڵ�
		var selectId = funcinfo.selectedNode.id;
		if( selectId != null && selectId != '' ){
			page.addValue( selectId, 'funcinfo:selected-node' );
		}
		
		page.goPage();
	}
	else{
		_browse.refresh();
	}
}

function exportData()
{
	// ȡ�ϼ��ڵ�
	if( funcinfo == null || funcinfo.selectedNode == null ){
		alert( 'û��ѡ����Ҫ�޸ĵļ�¼' );
		return;
	}
	
	var pid = funcinfo.selectedNode.id;
	if( pid == null || pid == "" || pid.indexOf('@temp:') == 0 ){
		alert( 'û��ѡ����Ҫ�޸ĵļ�¼' );
		return;
	}
	
	// �޸Ľڵ�
    var page = new pageDefine( "/txn980309.do", "��������", "modal", 750, 550);
    page.addValue( pid, "select-key:funccode" );
    page.goPage( );
}

function func_exportDocument()
{
	// ȡ�ϼ��ڵ�
	if( funcinfo == null || funcinfo.selectedNode == null ){
		alert( 'û��ѡ����Ҫ�޸ĵļ�¼' );
		return;
	}
	
	var pid = funcinfo.selectedNode.id;
	if( pid == null || pid == "" || pid.indexOf('@temp:') == 0 ){
		alert( 'û��ѡ����Ҫ�޸ĵļ�¼' );
		return;
	}
	
	// �޸Ľڵ�
    var page = new pageDefine( "/txn980308.do", "�����ĵ�", "modal", 750, 550 );
    page.addValue( pid, "select-key:funccode" );
    page.goPage( );
}
</script>

  <br>
  <table border="0" cellpadding="0" cellspacing="0" width="95%" height="92%" align="center" style='table-layout:fixed;'>
	<tr height="1"><td width="30%"></td><td width="70%"></td></tr>
	<tr height="26" valign="top"><td width="100%" colspan="2">
    <freeze:button name="record_addRecord" value="�����ӹ���" onclick="func_record_addRecord();" style="color:blue" />
    <freeze:button name="record_updateRecord" value="�޸Ĺ���" onclick="func_record_updateRecord();" style="color:blue"/>
    <freeze:button name="record_deleteRecord" value="ɾ������" onclick="func_record_deleteRecord();" style="color:blue"/>
	<freeze:button name="moveNode" value="�ƶ��ڵ�" onclick="funcinfo.openMoveNodeWindow('moveNode','sub')" style="color:blue"/>
	<!-- 
	<freeze:button name="exportData" txncode="980309" value="��������" onclick="exportData()" styleClass="menu"/>
	<freeze:button name="exportDocument" txncode="980308" value="�����ĵ�" onclick="func_exportDocument()" styleClass="menu"/>
	-->
	</td></tr>
	<tr><td height="1" bgcolor="3366FF" width="100%" colspan="2"></td></tr>
	<tr height="99%"><td>
	<freeze:xtree name='funcinfo' txnCode='980301' width="100%" height="100%" 
		bgcolor="F7F7F7" aspect="list" nodeicon="scrollend.gif" nodename="record" 
		pidname="parentcode" idname="funccode" textname="{funcname}" 
		onclick="funcClick" precreate="prepareTree"/>
	</td>
	<td valign="top">
	<iframe name='txnlist' scrolling=no frameborder=0 width=100% height=100%></iframe>
	</td>
	</tr>
  </table>
</freeze:body>
</freeze:html>
