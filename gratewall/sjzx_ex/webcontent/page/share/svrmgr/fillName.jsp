<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html>
<head>
	<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
	
</head> 
<freeze:body>
	  <freeze:title caption="新建" />
	  <freeze:block property="record" columns="1" bodyline="true" caption="输入服务信息" border="1" width="95%" align="center" captionWidth="0.2">
		<freeze:button name="record_saveAndExit" caption="确 定" hotkey="SAVE_CLOSE" onclick="passValue(true);"/>
        <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="passValue(false);"/>
        <freeze:hidden property="sys_svr_service_id" caption="服务ID" style="width:95%" />
        <freeze:text property="svr_code" caption="服务代码" style="width:65%;color=#666666;" readonly="true" />
        <freeze:text property="svr_name" caption="<font color='red'>服务名称*</font>" style="width:65%" onblur="checkExist();" onkeyup="releaseBtn(this);"/>
        <freeze:text property="max_records" caption="最大记录数" value="500" style="width:65%" />
        <freeze:textarea property="svr_desc" caption="服务描述" style="width:65%" rows="4" />
	  </freeze:block>
</freeze:body>
<script language="javascript">
		
		var param = window.dialogArguments;
	
		function init(){
			if(param){
				document.getElementById("record:sys_svr_service_id").value = param.svr_id;
				document.getElementById("record:svr_code").value = param.svr_code;
				document.getElementById("record:svr_name").value = param.svr_name;
				document.getElementById("record:max_records").value = param.max_records;
				document.getElementById("record:svr_desc").value = param.svr_desc;
			}else{
				$.get("<%=request.getContextPath()%>/txn50202015.ajax", function(xml){
					var svr_code = xml.selectSingleNode("/context/record/svr_order").text;
				    if(svr_code == "")
				    	svr_code = 0;
				    var num = new Number(svr_code);
					var newNum = "service"+(num + 1);
				    document.getElementById("record:svr_code").value = newNum;
				});
			}
		}
		
		init();
		
		function checkExist(){
			var fwmc = document.getElementById("record:svr_name").value;
			if(fwmc.trim() == ""){
				return;
			}
			var regExp = /[~\\\?'"_\^\*\+]/;
			if(regExp.test(fwmc)){
				alert("【名称】中含有不合法的字符【" + fwmc.match(regExp) + "】，请重新输入！");
				return;
			}
			$.get("<%=request.getContextPath()%>/txn50202017.ajax?select-key:svr_name="+fwmc, function(xml){
				var errCode = _getXmlNodeValue( xml, "/context/error-code" );
				if(errCode != "000000"){
				    alert("处理错误：" + _getXmlNodeValue( xml, "/context/error-desc" ));
				    return false;
				}
				var existId = _getXmlNodeValue( xml, "/context/record/sys_svr_service_id" );
				if(existId){
					if(!param){
						alert("此服务名称已存在，请更换！");
						document.getElementById("record_record_saveAndExit").disabled = true;
						return false;
					}else{
						if(existId != param.svr_id){
							alert("此服务名称已存在，请更换！");
							document.getElementById("record_record_saveAndExit").disabled = true;
							return false;
						}
					}
				}else{
					document.getElementById("record_record_saveAndExit").disabled = false;
				}
				
				
				return true;
			});
		}
		
		//释放确定按钮
		function releaseBtn(obj){
			document.getElementById("record_record_saveAndExit").disabled = false;
		}
		
		
		function trim(str){
			return str.replace(/(^\s*)|(\s*$)/g,"");
		}
		
		function _validate(){
			var fwmc = document.getElementById("record:svr_name").value;
		    if(trim(fwmc) == ""){
		    	alert("请填写【名称】！");
		    	document.getElementById("record:svr_name").focus();
		    	return false;
		    }
		    var regExp = /[~\\\?'"_\^\*\+]/;
			if(regExp.test(fwmc)){
				alert("【名称】中含有不合法的字符【" + fwmc.match(regExp) + "】，请重新输入！");
				return false;
			}
			var zdjls = document.getElementById("record:max_records").value;
		    if(trim(zdjls) == ""){
		    	alert("请填写【最大记录数】！");
		    	document.getElementById("record:max_records").focus();
		    	return false;
		    }
		    reg = new RegExp("^[0-9]*[1-9][0-9]*$");
			if (reg.test(zdjls)==false){
				alert("请正确设置【最大记录数】！");
			    document.getElementById("record:max_records").select();
				return false;
			}
			zdjls = parseInt(zdjls);
			if(zdjls < -1){
			    alert("请正确设置【最大记录数】！");
			    document.getElementById("record:max_records").select();
			    return false;
			}
			if(zdjls > 2000){
			    alert("【最大记录数】不能超过2000条！");
			    document.getElementById("record:max_records").select();
			    return false;
			}
			return true;
		}
		
		function passValue(clickConfirm){
			if(clickConfirm){
				if(!_validate()){
					return;
				}
				var paramJSON = {svr_id:document.getElementById("record:sys_svr_service_id").value, 
								 svr_code:document.getElementById("record:svr_code").value, 
								 svr_name:document.getElementById("record:svr_name").value, 
								 max_records:document.getElementById("record:max_records").value, 
								 svr_desc:document.getElementById("record:svr_desc").value};
				window.returnValue = paramJSON;
				window.close();
			}else{
				window.close();
			}
		}
		
		function __userInitPage()
		{
			document.getElementById("record:svr_name").focus();
			window.onunload = null;
		}
		_browse.execute( '__userInitPage()' );
	</script>
</freeze:html>