<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<title>查看对象信息</title>
</head>

<script language="javascript">
var service_targets_name_old;
var is_name_used = 1;
var namechecked = false;
	
function checkNameUsed(){
	namechecked = false;
	var page = new pageDefine("/txn201007.ajax", "检查是否已经使用");	
	var service_targets_name=getFormFieldValue('record:service_targets_name');
	page.addValue(service_targets_name,"select-key:service_targets_name");
	page.callAjaxService('nameCheckCallback');
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used[0] = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used[0]>0){
  			alert("服务对象名称已存在，请重新起名");
  			setFormFieldValue('record:service_targets_name',service_targets_name_old);
  		}
		namechecked = true;
		
}


// 保 存
function func_record_saveAndExit()
{
	if(!validate()){
		return;
	}
    
	saveAndExit( '', '保存服务对象表' );	// /txn201001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn201001.do
}



function showIPRow(){
  if($("#label_isip_bind").css("background-position-y")=="bottom"){
  	$("#label_isip_bind").css("background-position-y","top");
    for(var i=1;i<=4;i++){
      $("#ip"+i).attr("disabled",false);
    }
   }else{
    for(var i=1;i<=4;i++){
      $("#ip"+i).attr("disabled",true);
      $("#label_isip_bind").css("background-position-y","bottom");
    }
  }
}

function validate(){


    
    var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){
		var ip1 = document.getElementById("ip1").value.trim();
		var ip2 = document.getElementById("ip2").value.trim();
		var ip3 = document.getElementById("ip3").value.trim();
		var ip4 = document.getElementById("ip4").value.trim();
		var ip = ip1 + '.' + ip2 + '.' + ip3 + '.' + ip4;
		if(ip&&ip.length < 7){
			alert("请填写完整IP");
			return false;
	    }
	var reg = /^([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])$/;  
    var arr = reg.test(ip);  
     if(!arr){  
     	alert("绑定IP错误.");
     	//for(var ii = 1;ii < 5; ii++)
		//	document.getElementById("ip"+ii).value = '';
         return false;
     }
     document.getElementById('record:is_bind_ip').value=1;
     document.getElementById('record:ip').value=ip;
	}else{
	 document.getElementById('record:is_bind_ip').value=0;
     document.getElementById('record:ip').value=' ';
	}
	
	return true;

}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

  service_targets_name_old=getFormFieldValue('record:service_targets_name');

  var is_bind_ip='<freeze:out value="${record.is_bind_ip}"/>';
  //var ip='<freeze:out value="${oper-data.ipaddress}"/>';
  var ip='<freeze:out value="${record.ip}"/>';
   if(is_bind_ip=='1'){
    $("#isip_bind").attr("checked","true");
    $("#label_isip_bind").css("background-position-y","top")
    var ips=ip.split(".");
    for(var i=0;i<ips.length;i++){
      $("#ip"+(i+1)).attr("disabled",false);
      $("#ip"+(i+1)).val(ips[i]);
    }
  }
  	$("#padcool").next().next().remove();
	$("#padcool").next().remove();
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看对象信息"/>
<freeze:errors/>

<freeze:form action="/txn20102001">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="service_targets_id" caption="服务对象ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改对象信息" width="95%">
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="service_targets_id" caption="服务对象ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string"  style="width:95%"/>
      
       <freeze:cell property="service_targets_type" caption="服务对象类型"  valueset="资源管理_服务对象类型" style="width:95%" notnull="true"/>
     <freeze:cell property="service_targets_name" caption="服务对象名称"     style="width:95%"/>
      <freeze:cell property="service_targets_no" caption="服务对象代码"    style="width:95%"/>
      <freeze:cell property="service_password" caption="服务口令"   style="width:95%"/>
      <freeze:cell property="service_status" caption="服务状态"  colspan="2" valueset="资源管理_一般服务状态" style="width:95%"  notnull="true"/>
      <freeze:textarea disabled="true" property="service_desc" caption="服务对象描述" colspan="2" rows="4" maxlength="2000" style="width:98%"/>


      <freeze:hidden property="is_bind_ip" caption="是否绑定IP" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:cell property="ip" caption="IP" datatype="string"  colspan="2" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
