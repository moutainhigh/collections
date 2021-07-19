<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template master-detail-3/frame-detail-update.jsp --%>
<freeze:html width="800" height="400">
<head>
<link rel="StyleSheet"
		href="<%=request.getContextPath()%>/script/dtree/dtree.css"
		type="text/css" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/script/dtree/dtree.js"></script>
	
</head>


<script language="javascript">
function __userInitPage()
{
	$('#menu').height($('body').height()-60);
	$('.dtree').height($('#wrapper').height());
}

_browse.execute( '__userInitPage()' );


</script>
<style>
.block{

border:1px solid #cfcffe;
}



</style>


<freeze:body style="overflow-y: hidden">
	<freeze:title caption="" />
	<freeze:errors />
	<table width="99%" >
	<tr>
	<td width="25%" valign="top">
<div id="menutop" style="height:400px;overflow-y:scroll">
<div id="menu" >
 <div class="dtree"
			style="padding-left:5px;width: 90%; float: left;font-size:12px;margin-left: 5px; border: solid 1px; height: 90%;border-color: rgb(207,207,254);">

			<p>
				<a href="javascript: d.openAll();">չ��</a> |
				<a href="javascript: d.closeAll();">����</a>
			</p>

			<script type="text/javascript">
		

		d = new dTree('d');
		var value;
		d.add(1,-1,'ʹ��˵��');
		d.add(10,1,'��Դ����');
	
		d.add(11,10,'����������','/page/help/pageRes/fwdxgl.htm','����������','content');
	    d.add(12,10,'����Դ����','/page/help/pageRes/sjygl.htm','���ݿ����','content');
	    d.add(13,10,'�ɼ���Դ����','/page/help/pageRes/cjzygl.htm','�ɼ���Դ����','content');
	    d.add(14,10,'������Դ�鿴','/page/help/pageRes/gxzyck.htm','������Դ�鿴','content');
	    d.add(15,10,'��׼�淶����','/page/help/pageRes/bzgfgl.htm','��׼�淶����','content');
	    d.add(16,10,'����ʱ�����','/page/help/pageRes/fwsjgl.htm','����ʱ�����','content');
	    d.add(20,1,'�������');
	    d.add(21,20,'�ӿڹ���','/page/help/pageRes/jkgl.htm','�ӿڹ���','content');
	    d.add(22,20,'��������','/page/help/pageRes/fwpz.htm','��������','content');
	    d.add(30,1,'�ɼ�����');
	    d.add(31,30,'�ɼ�����','/page/help/pageRes/cjrw.htm','�ɼ�����','content');
	     d.add(40,1,'���м��');
	      d.add(41,40,'ʵʱ���','/page/help/pageRes/yxjk.htm','ʵʱ���','content');
	      d.add(42,40,'���ָ�����','/page/help/pageRes/yxjk.htm','���ָ�����','content');
	      d.add(43,40,'���鷢�������','/page/help/pageRes/yxjk.htm','���鷢�������','content');
	      d.add(50,1,'��־����');
	      d.add(51,50,'��־��ѯ','/page/help/pageRes/rzcx.htm','��־��ѯ','content');
	      d.add(52,50,'��־ͳ��','/page/help/pageRes/rztj.htm','��־ͳ��','content');
	      d.add(53,50,'ʹ�ñ���','/page/help/pageRes/sybg.htm','ʹ�ñ���','content');
	       d.add(60,1,'ϵͳ����');
	        d.add(61,60,'ϵͳ�����û�','/page/help/pageRes/xtyhzx.htm','ϵͳ�����û�','content');
	         d.add(62,60,'Ȩ�޹���','/page/help/pageRes/qxgl.htm','Ȩ�޹���','content');
	          d.add(63,60,'����Ȩ��','/page/help/pageRes/gnqx.htm','����Ȩ��','content');
	           d.add(64,60,'֪ͨ����','/page/help/pageRes/tzgl.htm','֪ͨ����','content');
		document.write(d);
		value=11;
		d.openTo(value,true);
	</script>

		</div>
</div>	
</div>
</td>
	<td width="81%">
	<div class="block">
		
		<iframe src="/page/help/pageRes/fwdxgl.htm"  width="100%" frameborder="0" height="450px" name="content" scrolling="Yes"></iframe>
		
	</div>
	</td>
</tr>
</table>
<script>
        document.getElementsByName(value)[0].click();
</script>
</freeze:body>
</freeze:html>
