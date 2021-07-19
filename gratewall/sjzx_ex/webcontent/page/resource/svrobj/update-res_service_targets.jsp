<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import = "com.gwssi.common.constant.FileConstant"%>
<%@ page import="com.gwssi.webservice.server.ParamAnalyzer"%>
<%@ page import="com.gwssi.common.constant.ShareConstants" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="800" height="380">
<head>
<style>
span.IPDiv{background:#ffffff;width:120;font-size:9pt;text-align:center;border:2 ridge threedshadow;border-right:inset threedhighlight;border-bottom:inset threedhighlight;
}
input.IPInput{width:24;font-size:9pt;text-align:center;border-width:0;
}
</style>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<title>修改对象信息</title>
</head>

<script language="javascript">
var service_targets_name_old;
var is_name_used = 1;
var namechecked = false;

var ipfieldsNUM = 0;

function newIPfield(){
	if(ipfieldsNUM >= 9)
	{
		alert("最多绑定10个IP地址！");
		return;
	}
	var tmpStr=[];           //创建一个数组
	for(var i=0;i<4;i++)     //通过循环-每隔3位，输出一个点
	tmpStr[i]="<input onkeyup=\"return ip_keyup()\" class=IPInput name=IPInput type=text size=3 maxlength=3 onkeydown='ip_keydown()'>"+(i==3?"":".");
	var htmlbutton = '<button  class="IPInput" onclick="delIPfield(this)" >删除</button>';
	document.getElementById("padcool").innerHTML+="<div><span class=IPDiv>"+tmpStr.join("")+"</span>"+htmlbutton+"</div>";//输出字符串
	ipfieldsNUM++;
}

function delIPfield(obj){
		var div = obj.parentNode; 
		div.parentNode.removeChild(div); 
		ipfieldsNUM--;
}

function setIPFields(ipString){
	var tmpStr=[];  
	var iparr = ipString.split(",");
	var html="";
	ipfieldsNUM = iparr.length-1;
	for(var i=0;i<iparr.length;i++) {
	    var ippart = iparr[i].split(".");
		for(var j=0;j<4;j++)     //通过循环-每隔3位，输出一个点
		{	
			tmpStr[j]="<input class=IPInput onkeyup='ip_keyup()' name=IPInput type=text size=3 maxlength=3 value="+ippart[j]+" onkeydown='ip_keydown()'>"+(j==3?"":".");
			}
			var htmlbutton = '<button  class="IPInput" onclick="delIPfield(this)" >删除</button>';
		html += "<div><span class=IPDiv>"+tmpStr.join("")+"</span>"+htmlbutton+"</div>";//输出字符串
	}
	
	document.getElementById("IPDiv").innerHTML = html;
}

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
	var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){
			document.getElementById("label_check").style.backgroundPositionY="top";
		document.getElementById("isip_bind").value = 'Y';
		document.getElementById("padcool").disabled = false;
		$(".IPInput").attr("disabled",false);
		}else{
			document.getElementById("isip_bind").value = 'N';
			document.getElementById("label_check").style.backgroundPositionY="bottom";
			document.getElementById("padcool").disabled = true;
			$(".IPInput").attr("disabled","disabled");
	}
}

function validate(){


    //IP不是用的烽火台标签，设置修改标志否则只改IP不能保存进数据库
	setModifyFlag( 'true' );//
    var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){

	var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;  
	var ips = document.getElementsByName("IPInput");
	var ip ="";
	var showIp="";
	for(var i=0;i<ips.length;i++){
		//alert(i+"="+ips[i].value);
		   if((i+1)%4==0){
		   ip +=ips[i].value+".";
		   if(ip!="....")
		   {
		     ip=ip.substring(0,ip.length-1)
			     var arr = reg.test(ip);  
				if(!arr){  
					alert("请输入正确的ip地址,比如 192.168.1.1");
					//for(var ii = 1;ii < 5; ii++)
					//	document.getElementById("ip"+ii).value = '';
					return false;
			}
			 showIp += ip+","; 
			}
			ip="";
		   }
		   else{
				ip +=ips[i].value+".";
			}
		
	}
		showIp=showIp.substring(0,showIp.length-1);
		if(showIp==''){
			alert("若勾选【绑定IP】项则需要至少填入一个有效ip地址");
			return false;
		}
     document.getElementById('record:is_bind_ip').value='Y';
     document.getElementById('record:ip').value=showIp;
	}else{
		alert("请输入至少一个ip地址");
		return;
		
	/*  document.getElementById('record:is_bind_ip').value='N';
     document.getElementById('record:ip').value=' '; */
	}
	
	return true;

}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

  service_targets_name_old=getFormFieldValue('record:service_targets_name');

  var is_bind_ip='<freeze:out value="${record.is_bind_ip}"/>';
   if(is_bind_ip=='Y'){
    $("#isip_bind").attr("checked","true");
    $("#label_check").css("background-position-y","top")
    document.getElementById("padcool").disabled = false;
  }
  else{
  document.getElementById("padcool").disabled = true;
  }
	
	var ipString = getFormFieldValue('record:ip');
	if(ipString!=null && ipString!="" )
	setIPFields(ipString);
	
	showIPRow();
	//手动设置宽度
	$(document.getElementById('record:show_order')).width(document.getElementById('record:service_targets_no').clientWidth);
}
function ip_keyup(){
	if(event.srcElement.value.length == 3){
		if(event.srcElement.nextSibling 
				&& event.srcElement.nextSibling.nextSibling 
				&& event.srcElement.nextSibling.nextSibling.tagName.toUpperCase() == 'INPUT' ){
			event.srcElement.nextSibling.nextSibling.focus();
		}
	}
}
function ip_keydown(){
	if(event.keyCode==39){
		event.keyCode=9;
	}else if(event.keyCode==8){
		if(event.srcElement.value.length == 0){
			if(event.srcElement.previousSibling 
					&& event.srcElement.previousSibling.previousSibling 
					&& event.srcElement.previousSibling.previousSibling.tagName.toUpperCase() == 'INPUT' ){
				event.srcElement.previousSibling.previousSibling.select();
			}
		}
	}
}
_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改对象信息"/>
<freeze:errors/>

<freeze:form action="/txn201002" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="service_targets_id" caption="服务对象ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改对象信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="service_targets_id" caption="服务对象ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string"  style="width:95%"/>
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%" />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="fjmc" caption="文件上传" />
      <freeze:hidden property="fj_fk" caption="文件上传id" style="width:90%" /> 
      <freeze:select property="service_targets_type" caption="服务对象类型" valueset="资源管理_服务对象类型" style="width:95%" notnull="true"/>
      <freeze:text property="service_targets_name" caption="服务对象名称"  onchange="javascript:checkNameUsed();" notnull="true"  datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="service_targets_no" caption="服务对象代码" readonly="true" datatype="string" maxlength="50" style="width:95%"/>
      <freeze:text property="service_password" caption="服务口令" maxlength="50"  notnull="true" style="width:95%"/>
      <freeze:select property="service_status" caption="服务状态" valueset="资源管理_一般服务状态" style="width:95%"  notnull="true"/>
      <%-- <freeze:select property="is_formal" caption="是否正式" value="N" valueset="资源管理_服务对象_是否正式环境" notnull="true" style="width:95%"/>
       --%><freeze:text property="show_order" notnull="true" datatype="int" numberformat="/^[1-9]\d*$/" caption="显示顺序" style="width:37%" />
      <freeze:textarea property="service_desc" caption="服务对象描述" colspan="2" rows="1" maxlength="2000" style="width:98%"/>


      <freeze:hidden property="is_bind_ip" caption="是否绑定IP" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="ip" caption="IP" datatype="string" maxlength="50" style="width:95%"/>
      </tr><tr>
      <td width="15%" align="right"><font color="red">*绑定IP</font>
      <input type="checkbox" style="margin-top:-1000px;" id="isip_bind" value="N" onclick="showIPRow();" />
	 	<label id="label_check" class="checkboxNew" for="isip_bind"></label>
	  </td>
	  <td id="padcool" colspan="3" width="35%">
	  
	  	<button class="IPInput" onclick="javascript:newIPfield();">增加IP地址</button>
	   <div id="IPDiv">  <span class="IPDiv">
	    <INPUT onkeyup="return ip_keyup()" onkeydown="ip_keydown()" class=IPInput name=IPInput maxLength=3 size=3>.<INPUT onkeyup="return ip_keyup()" onkeydown="ip_keydown()" class=IPInput name=IPInput maxLength=3 size=3>.<INPUT onkeyup="return ip_keyup()" onkeydown="ip_keydown()" class=IPInput name=IPInput maxLength=3 size=3>.<INPUT onkeyup="return ip_keyup()" onkeydown="ip_keydown()" class=IPInput name=IPInput maxLength=3 size=3>
	  	
	  	</span><button  class="IPInput" onclick="delIPfield(this)" >删除</button></div>
	<%
    DataBus context = (DataBus) request.getAttribute("freeze-databus");
    Recordset fileList=null;
    try{
     fileList = context.getRecordset("fjdb");

    if(fileList!=null && fileList.size()>0){
    	out.println("<tr><td height=\"32\" align=\"right\">文件名称：</td><td colspan=\"3\"><table >");
        for(int i=0;i<fileList.size();i++){
               DataBus file = fileList.get(i);
               String file_id = file.getValue(FileConstant.file_id);
               String file_name = file.getValue(FileConstant.file_name);
%>
<tr id='<%=file_id%>'>
 <td><a href="#" onclick="downFile('<%=file_id%>')" title="附件" ><%=file_name %></a></td>
 <td><a href="#" onclick="delChooseFile('<%=file_id%>','record:delIDs','<%=file_name%>','record:delNAMEs')"  title="删除" ><span class="delete">&nbsp;&nbsp;</span></a>
</tr>
<%  }
    out.println("</table></td></tr>"); 
   }
   }catch(Exception e){
	   System.out.println(e);
   }
%>   
  <!-- <span class="btn_add" onclick="addNewRowEx('record:fjmc1','&nbsp;上传文件：','80%')" title="增加">&nbsp;&nbsp;</span> -->
<freeze:file property="fjmc1" caption="交换说明文件" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="增加"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>

    <freeze:cell property="crename" caption="创建人：" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="创建时间：" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="最后修改人：" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="最后修改时间：" datatype="string"  style="width:95%"/>
      
     
   
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
