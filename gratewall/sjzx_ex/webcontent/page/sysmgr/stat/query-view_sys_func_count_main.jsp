<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="300">
<freeze:include href="/script/gwssi-xtree.js"/>
  <head>
    <title>����ͳ����״չʾ�б�</title>
  </head>
<freeze:body>
<freeze:title caption="����ͳ����״չʾ�б�"/>
<freeze:errors/>
<script language="javascript">
	function funclick(){
		var treeNode = areacode.lookupNode(areacode.menu.selectedNode.id);  
		var nodeId = treeNode.id;
		if(nodeId == 100001){//�򵥲�ѯ>����Ǽ���Ϣ
			var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100002){//�򵥲�ѯ>�߹���Ա��Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=2", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100003){//�򵥲�ѯ>���ŵǼǲ�ѯ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=3", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100004){//�򵥲�ѯ>��ҵ�����Ϣ
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000041){//�򵥲�ѯ>��ҵ�����Ϣ>������Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=4", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000042){//�򵥲�ѯ>��ҵ�����Ϣ>���վ�Ӫ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=5", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100005){//�򵥲�ѯ>�̱�����Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=6", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100006){//�򵥲�ѯ>����������Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=7", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100007){//�򵥲�ѯ>��ͬ�����Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=8", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100008){//�򵥲�ѯ>ʳƷ��ȫ��Ϣ
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000081){//�򵥲�ѯ>ʳƷ��ȫ��Ϣ>�г���̬
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=11", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000082){//�򵥲�ѯ>ʳƷ��ȫ��Ϣ>ʳƷ����
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=9", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000083){//�򵥲�ѯ>ʳƷ��ȫ��Ϣ>ʳƷ��ҵ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=10", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000084){//�򵥲�ѯ>ʳƷ��ȫ��Ϣ>ר������
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=12", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000085){//�򵥲�ѯ>ʳƷ��ȫ��Ϣ>ͻ���¼�
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=13", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100009){//�򵥲�ѯ>��Ʒ�������
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000091){//�򵥲�ѯ>��Ʒ�������>��ҵ��Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=14", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000092){//�򵥲�ѯ>��Ʒ�������>��Ʒ��Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=15", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000093){//�򵥲�ѯ>��Ʒ�������>�����Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=16", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000094){//�򵥲�ѯ>��Ʒ�������>ֹͣ����
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=17", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000095){//�򵥲�ѯ>��Ʒ�������>�����г�
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=18", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000096){//�򵥲�ѯ>��Ʒ�������>�����г�
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=19", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100010){//�򵥲�ѯ>���߾ٱ���Ϣ
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000101){//�򵥲�ѯ>���߾ٱ���Ϣ>������Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=20", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000102){//�򵥲�ѯ>���߾ٱ���Ϣ>�ٱ���Ϣ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=21", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100011){//�򵥲�ѯ>�������Ϣ
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000111){//�򵥲�ѯ>�������Ϣ>ר�����
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=24", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000112){//�򵥲�ѯ>�������Ϣ>Υ�����
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=23", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000113){//�򵥲�ѯ>�������Ϣ>������ѯ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=25", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000114){//�򵥲�ѯ>�������Ϣ>����ý��
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=22", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000115){//�򵥲�ѯ>�������Ϣ>Ͷ�߾ٱ�
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=26", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000116){//�򵥲�ѯ>�������Ϣ>��������
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=27", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000117){//�򵥲�ѯ>�������Ϣ>���ŷ���
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=28", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000118){//�򵥲�ѯ>�������Ϣ>��ҵ����
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=29", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 200000){//ȫ�ļ���
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 300000){//�߼���ѯ
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 300001){//�߼���ѯ > ���ϲ�ѯ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=30", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 300002){//�߼���ѯ > �Զ����ѯ
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=31", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400000){//ͳ�Ʊ���
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "������Ϣ" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 400001){//ͳ�Ʊ���>����Ǽ�
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=32", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400002){//ͳ�Ʊ���>��ҵ���
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=33", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400003){//ͳ�Ʊ���>��������
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=34", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400004){//ͳ�Ʊ���>���߾ٱ�
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=35", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400005){//ͳ�Ʊ���>��Ʒ���
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=36", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400006){//ͳ�Ʊ���>������
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=37", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400007){//ͳ�Ʊ���>����
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=39", "������Ϣ" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}
	}
	
</script>
  <table border="0" cellpadding="0" cellspacing="0" width="95%" height="95%" align="center" style='table-layout:fixed;'>
	<tr height="99%">
	<td width="25%">
      <freeze:xtree name="areacode" property="$HOME/sysmgr/stat/frame-detail_tree.xml" aspect="tree" root="����ʹ��ͳ��"  nodename="node" idname="id" textname="{name}" memoname="condition" width="100%" height="100%" border="1" bordercolor="#aadadb" onclick="funclick()"/>
	</td>
	<td valign="top" valign="top" align="center" >
	<iframe name='tree_view' scrolling=no frameborder=0 width=95% height=100% style="display:block"></iframe>
	</td>
	</tr>
  </table>
</freeze:body>
</freeze:html>
