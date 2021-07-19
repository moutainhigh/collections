<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<title>查询系统日志列表</title>
</head>

<script language="javascript">
//TODO 按钮还要替换
function _menuClick(id)
{
	func_show_selectPic();
}
function func_show_selectPic()
{
	//var page = new pageDefine( "statistics-share_log_showPic1.jsp", "增加数据源");
	var page = new pageDefine( "/txn6020004.do", "查看图片");
	
	var chk_value =[];  
	$('input[name="query_index"]:checked').each(function(){  
	chk_value.push($(this).val());  
	});  
	
	page.addValue( chk_value, "select-key:query_index" );
	//page.addValue( id, "primary-key:interface_id" );
	//page.addParameter( "select-key:service_targets", "primary-key:interface_id" );
	page.addParameter( "select-key:service_targets", "select-key:service_targets_id" );
	//page.addParameter( "select-key:query_index", "select-key:query_index" );
	page.addParameter( "select-key:statistical_cycle", "select-key:statistical_cycle" );
	page.addParameter( "select-key:show_type", "select-key:show_type" );
	page.addParameter( "select-key:statistical_granularity", "select-key:statistical_granularity" );
	
    var win = window.frames('showPic');
  	document.getElementById("showPic").style.display = "block";
	page.goPage( null, win );
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	func_show_selectPic();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="服务对象统计"/>

<freeze:errors/>
<freeze:form action="/txn6020001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="service_targets" caption="服务对象" valueset="资源管理_服务对象名称" data="mix"  style="width:95%"/>
      <freeze:select property="show_type" caption="展现方式"  value="02" valueset="日志分析_展现方式" style="width:95%"/>
      <freeze:select property="statistical_cycle" caption="统计周期"  value="01" valueset="日志分析_统计周期"  style="width:95%"/>
      <freeze:select property="statistical_granularity" caption="统计粒度" value="00" valueset="日志分析_统计粒度"  style="width:95%"/>
     
           
          <tr> 
      <td width="15%" align="right" class="odd12" id="label:select-key:query_index">
      指标：
      </td>
      <td width="35%" class="odd12">
      <input type="checkbox" id="query_index0" value="共享数据量" name="query_index" checked="checked">共享数据量 
      <input type="checkbox" id="query_index1" value="调用次数" name="query_index" checked="checked">调用次数 
      </td>
      <td width="15%" class="odd12"></td>
       <td width="35%" class="odd12"></td>
      </tr>
  </freeze:block>
<br/>
  <div align="center">
 <iframe id='showPic' scrolling='no' frameborder='0' align="middle" width='95%' style="display:none;height:480px;"></iframe>
</div>
</freeze:form>
</freeze:body>
</freeze:html>
