<%
/*
 * @Header 机构维护列表（无用）
 * @Revision
 * @Date 2007-03-01
 * @author adaFang
 * =====================================================
 *  深圳审计项目组
 * =====================================================
 */
%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>机构列表</title>
</head>
<freeze:body>
<freeze:title caption="机构列表"/>
<freeze:errors/>

<script language="javascript">

function func_record_addRecord(){
  var page = new pageDefine( "insert-jggl.jsp", "增加机构", "modal", 650,400);
  page.addRecord( );
}

function func_record_updateRecord(){
  var page = new pageDefine( "/txn806004.do", "修改机构", "modal", 650,400);
  page.addParameter("record:jgid_pk","primary-key:jgid_pk");
  page.updateRecord( );
}

function func_record_deleteRecord(){
  var page = new pageDefine( "/txn806005.do", "删除机构");
  page.addParameter("record:jgid_pk","primary-key:jgid_pk");
  page.deleteRecord("是否删除选中的记录");
}

</script>

<freeze:form action="/txn806001">
  <freeze:grid property="record" caption="机构列表" keylist="jgid_pk" width="100%" navbar="bottom" fixrow="false" >
    <freeze:button name="record_addRecord" caption="增加" txncode="806003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
    <freeze:button name="record_updateRecord" caption="修改" txncode="806004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
    <freeze:button name="record_deleteRecord" caption="删除" txncode="806005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
    <freeze:cell property="jgid_pk" caption="机构ID" style="width:10%"/>
    <freeze:cell property="sjjgid_fk" caption="上级机构ID" style="width:10%"/>
    <freeze:cell property="jgbh" caption="机构编号" style="width:10%"/>
    <freeze:cell property="jgmc" caption="机构名称" style="width:10%"/>
    <freeze:cell property="jgjc" caption="机构简称" style="width:10%"/>
    <freeze:cell property="jglx" caption="机构类型" style="width:10%"/>
    <freeze:cell property="jgfzr" caption="机构负责人" style="width:10%"/>
    <freeze:cell property="sfyx" caption="是否有效" style="width:10%"/>
    <freeze:cell property="plxh" caption="排列序号" style="width:10%"/>
    <freeze:cell property="bz" caption="备注" style="width:10%"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
