<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改采集数据表信息</title>
</head>

<script language="javascript">



// 返 回
function func_record_goBack()
{
	goBack();	// /txn20201001.do
}
//修改采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn20202006.do", "查看采集数据项信息", "modal" );
	page.addValue(idx, "primary-key:collect_dataitem_id" );
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	page.updateRecord();
}

// 生成表
function func_record_createTable()
{
    var collect_table_id=getFormFieldValue('record:collect_table_id');
    var ids = getFormAllFieldValues("dataItem:dataitem_name_en");
   
    
    if(collect_table_id==null||collect_table_id==""){
	    alert("请先填写采集表信息!");
	    clickFlag=0;
    }else if(ids==null||ids.length==0){
        alert("请先填写数据项信息!");
	    clickFlag=0;
    }
    else{
        var key=getFormAllFieldValues("dataItem:is_key");
        var num=0;
	    if(ids!=null){
	    for(i=0;i<ids.length;i++){
	    if(key[i]=='是'){
	       num=num+1;
	      }
	     }
	    }
        if(num>1){
	        alert("只能有一个数据项是主键!");
		    clickFlag=0;
		    return false;
        }
    
        var page = new pageDefine( "/txn20201009.ajax", "生成采集数据表!");
        page.addParameter("record:table_name_en","primary-key:table_name_en");
	    page.callAjaxService('creatTableCheck');
	}
}
function creatTableCheck(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
		if(confirm("采集库已存在此数据表名称且数据表里已有数据，是否继续生成该数据表?")){
		   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
  		   page.addParameter("record:collect_table_id","record:collect_table_id");
  		   page.addValue(is_name_used,"record:name_nums");
  		   page.callAjaxService('creatTable');
		}
  			
  		}else if(is_name_used==-1){
	  		if(confirm("采集库已存在此数据表名称,但数据表里没有数据，是否继续生成该数据表?")){
			   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
	  		   page.addParameter("record:collect_table_id","record:collect_table_id");
	  		   page.addValue(is_name_used,"record:name_nums");
	  		   page.callAjaxService('creatTable');
			}
  		}else{
  		   var page = new pageDefine( "/txn20201008.ajax", "生成采集数据表!");
  		   page.addParameter("record:collect_table_id","record:collect_table_id");
  		   page.addValue(is_name_used,"record:name_nums");
  		   page.callAjaxService('creatTable');
  		}
}
function creatTable(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("生成表成功!");
		}
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids = getFormAllFieldValues("dataItem:collect_dataitem_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	 
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改采集数据表信息"/>
<freeze:errors/>

<freeze:form action="/txn20201002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改采集数据表信息" width="95%">
  	  <freeze:button name="record_createTable" caption="生成表" hotkey="SAVE" onclick="func_record_createTable();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="服务对象ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="服务对象名称"   show="name" valueset="资源管理_服务对象名称"  style="width:98%"/>
      <freeze:cell property="table_name_en" caption="表名称"  style="width:95%"/>
      <freeze:cell property="table_name_cn" caption="表中文名称"  style="width:95%"/>
      <freeze:cell property="table_type" caption="表类型"  show="name" valueset="资源管理_表类型"  style="width:95%"/>
      <freeze:cell property="created_time" caption="创建时间"  style="width:95%"/>
      <freeze:cell property="table_desc" caption="表描述" colspan="2"  style="width:98%"/>
      <freeze:hidden property="table_status" caption="表状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>
 <br>
   <freeze:grid property="dataItem" caption="采集数据项列表" keylist="collect_dataitem_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID"  />
      <freeze:hidden property="collect_table_id" caption="采集数据表ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:6%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="数据项名称" style="width:12%" />
      <freeze:cell property="dataitem_name_cn" caption="中文名称" style="width:12%" />
      <freeze:cell property="dataitem_type" caption="数据项类型"  show="name" valueset="资源管理_数据项类型" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="数据项长度" style="width:10%" />
      <freeze:cell property="is_key" caption="是否主键" valueset="资源管理_是否主键" style="width:10%" />
      <freeze:hidden property="is_code_table" caption="是否代码表" style="width:10%" />
      <freeze:cell property="code_table" caption="对应代码表" valueset="资源管理_对应代码表" style="width:12%" />
      <freeze:cell property="oper" caption="操作" align="center" style="width:12%" />
      <freeze:hidden property="dataitem_long_desc" caption="数据项描述" style="width:20%"  />
      <freeze:hidden property="is_markup" caption="有效标记" style="width:10%" />
      <freeze:hidden property="creator_id" caption="创建人ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="创建时间" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" style="width:12%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
