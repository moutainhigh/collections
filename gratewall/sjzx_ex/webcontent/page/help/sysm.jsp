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
				<a href="javascript: d.openAll();">展开</a> |
				<a href="javascript: d.closeAll();">收起</a>
			</p>

			<script type="text/javascript">
		

		d = new dTree('d');
		var value;
		d.add(1,-1,'使用说明');
		d.add(10,1,'资源管理');
	
		d.add(11,10,'服务对象管理','/page/help/pageRes/fwdxgl.htm','服务对象管理','content');
	    d.add(12,10,'数据源管理','/page/help/pageRes/sjygl.htm','数据库管理','content');
	    d.add(13,10,'采集资源管理','/page/help/pageRes/cjzygl.htm','采集资源管理','content');
	    d.add(14,10,'共享资源查看','/page/help/pageRes/gxzyck.htm','共享资源查看','content');
	    d.add(15,10,'标准规范管理','/page/help/pageRes/bzgfgl.htm','标准规范管理','content');
	    d.add(16,10,'服务时间管理','/page/help/pageRes/fwsjgl.htm','服务时间管理','content');
	    d.add(20,1,'共享服务');
	    d.add(21,20,'接口管理','/page/help/pageRes/jkgl.htm','接口管理','content');
	    d.add(22,20,'服务配置','/page/help/pageRes/fwpz.htm','服务配置','content');
	    d.add(30,1,'采集任务');
	    d.add(31,30,'采集任务','/page/help/pageRes/cjrw.htm','采集任务','content');
	     d.add(40,1,'运行监控');
	      d.add(41,40,'实时监控','/page/help/pageRes/yxjk.htm','实时监控','content');
	      d.add(42,40,'监控指标管理','/page/help/pageRes/yxjk.htm','监控指标管理','content');
	      d.add(43,40,'警情发现与管理','/page/help/pageRes/yxjk.htm','警情发现与管理','content');
	      d.add(50,1,'日志管理');
	      d.add(51,50,'日志查询','/page/help/pageRes/rzcx.htm','日志查询','content');
	      d.add(52,50,'日志统计','/page/help/pageRes/rztj.htm','日志统计','content');
	      d.add(53,50,'使用报告','/page/help/pageRes/sybg.htm','使用报告','content');
	       d.add(60,1,'系统管理');
	        d.add(61,60,'系统在线用户','/page/help/pageRes/xtyhzx.htm','系统在线用户','content');
	         d.add(62,60,'权限管理','/page/help/pageRes/qxgl.htm','权限管理','content');
	          d.add(63,60,'功能权限','/page/help/pageRes/gnqx.htm','功能权限','content');
	           d.add(64,60,'通知管理','/page/help/pageRes/tzgl.htm','通知管理','content');
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
