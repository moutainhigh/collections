<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script>
function nameLength(_groupName){
	var content=_groupName;
	var iLen=0;
	for (var i = 0; i < content.length; i++) {
		iLen += /[^\x00-\xff]/g.test(content.charAt(i)) ? 1 : 0.5;
	};
	iLen=Math.ceil(iLen);
	return iLen;
}
<!--
	var m_cbCfg = {
		btns : [
			{//绘制确定按钮
				text: '确定',
				cmd : function(){
					var groupName = $("#groupName").val();
					if(groupName == $.trim(m_params.SCMGroupName) && $.trim(groupName).length > 0){
						var params={
							triggerClose : 1,
							param1 : "value1"
						};
						this.close(params);
						return;
					}
					if(groupName == null || $.trim(groupName).length == 0){
						alert("请填写分组的名称!");
						$("#groupName").select();
						return false;
					}
					var groupNameLength = nameLength($.trim(groupName));
					if(groupNameLength > 10){
						alert("分组名不能超过10个字!");
						$("#groupName").select();
						return false;
					}
					groupName = $.trim(groupName);
					var flag = true;
					$.ajax({
						async:false,
						type:"post",
						data:{GroupName:groupName},
						dataType:"text",
						url:"query_groupbyname_dowith.jsp",
						success:function(data){
							if($.trim(data) == 1){
								alert("分组名已经存在，请重新输入分组名！");
								$("#groupName").select();
								flag = false;
							}else if($.trim(data) == 2){
								alert("查询分组出现异常，请重试！");
								flag = false
							}
						},
						error:function(){
							alert("请求数据失败，请重试！");
						}
					});
					if(!flag){
						return false;
					}
					var paramsback={
						GroupId:m_params.SCMGroupId,
						GroupName:groupName
					}
					this.notify(paramsback);
				}
			},
			{//绘制取消按钮
				text: '取消',
				cmd : function(){
					var params={
						triggerClose : 1,//为1是触发关闭事件,为0不触发关闭事件
						param1 : "value1"
					};
					this.close(params);
				}
			}
		]
	};
//-->
</script>
<script language="javascript">
	var m_params = null;
	function g_init(params){
		m_params = params;
		if(params.SCMGroupName != null && $.trim(params.SCMGroupName).length != 0){
			$("#groupName").val(params.SCMGroupName);
		}
		$("#groupName").select();
	}

</script>
</head>
<body>
	<center>
		<div style="height:18px;"></div>
			<span style="font-size:14px;">分组名称：</span><input type="text" name="groupName" id="groupName" style="border:2px solid #ccc;height:22px;line-height:22px;fint-size:15px;width:180px;"/>
	</center>
</body>
</html>