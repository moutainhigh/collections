<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改通知管理信息</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存通知管理' );	// /txn315001001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn315001001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改通知管理信息"/>
<freeze:errors/>

<freeze:form action="/txn315001006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jbxx_pk" caption="通知编号-主键" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改通知管理信息" width="95%">
      
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jbxx_pk" caption="通知编号-主键" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="tzmc" caption="通知名称" datatype="string"  colspan="2" style="width:98%"/>
      <freeze:hidden property="fbrid" caption="发布人ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fbrmc" caption="发布人名称" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fbksid" caption="发布科室" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fbksmc" caption="发布名称" datatype="string"  style="width:95%"/>
      <freeze:hidden property="fbsj" caption="发布时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="tznr" caption="通知内容"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="tzzt" caption="通知状态" datatype="string"  style="width:95%"/>
      <freeze:hidden property="jsrids" caption="接收人ids"  maxlength="4000" style="width:98%"/>
      <freeze:hidden property="jsrmcs" caption="接收人名称"  maxlength="2000" style="width:98%"/>
      
      <tr>
      <td align="right" height="32">通知材料&nbsp;</td>
      <td>
      <%
        DataBus context1 = (DataBus) request.getAttribute("freeze-databus");
        Recordset fileList=null;
        try{
          fileList = context1.getRecordset("fjdb");
      %>
      <br>
      <%
          if(fileList!=null && fileList.size()>0){
            for(int i=0;i<fileList.size();i++){
              DataBus file = fileList.get(i);
              String file_id = file.getValue(FileConstant.file_id);
              String file_name = file.getValue(FileConstant.file_name);
      %>
      <a href="#" onclick="downFile('<%=file_id%>')" title="附件"><%=file_name %></a><br><br>
      <%}}
        }catch(Exception e){
	      System.out.println(e);
        }
      %>
      </td>
    </tr>
    <freeze:hidden property="fjmc" caption="通知材料" style="width:76%" />
    <freeze:hidden property="fj_pk" caption="通知材料id" style="width:90%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
