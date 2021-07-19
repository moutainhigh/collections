define(['require', 'jquery', 'entCommon'], function(require, $, entCommon){
var torch = {
		_init : function(){
		 require(['domReady'], function (domReady) {
		    domReady(function () {		    			    	
		    	$("#myOperationGrid").gridpanel('hideColumn', 'requisitionId');
		    	$("#myOperationGrid").gridpanel("option","datarender",torch.myOperation_datarender);
		    	$("#myOperationGrid").gridpanel("option","dataurl","../../../../req/getListForConfirm.do");
				$("#myOperationGrid").gridpanel("reload");	
			 });
		 });
	},

	
	/**
	 * 列表数据格式化
	 * @param event
	 * @param obj
	 */
	/*myOperation_datarender : function (event,obj){
	    var data = obj.data;
		var code = "";
		var label = "";
		var url = "";
		for (var i = 0; i < data.length; i++) {
			if(!data[i]){
				continue;
			}//
			var htm = "<div class='table-panel-tr'>"
				    + "<span class='index-span-index'></span>"
					+ "<div class='ent-name omit'><p style='width:160px;text-align:center'>"+data[i]['operationType']+"</p></div>"
					+ "<div class='ent-name omit'><p style='width:200px;text-align:center'>经办人 : "+data[i]['linkman']+"</p></div>"
					+ "<div class='ent-name omit'><p style='width:100px;text-align:center'>"+data[i]['appDate']+"</p></div>"
					+ "<div class='state' text='state'><p style='width:100px;text-align:center'>"+data[i]['state']+"</p></div><div class='ent-name omit'>";
			//按钮
			$.each(data[i]["opr"],function(index,val){
				code = val.code;
    			label = val.label;
    			url = val.url;
    			// 稍后通过class:setupInit绑定事件
    			htm += "<a  href='"+url+"' class='"+code+"' style='margin-left:20px;'>"+label+"</a>";	
    			
			});
			htm += "</div></div>";
			data[i]["custom"] = htm;
		}
		//flag = 1;
		return data;
	},*/
	myOperation_datarender : function(item, rowsdata) {
		var data = rowsdata.data;
		if (data.length == 0) {// 没有数据，可修改页面样式
			return;
		}
		var code = "";
		var label = "";
		var url = "";
		for ( var i = 0; i < data.length; i++) {
			if (!data[i]) {
				continue;
			}

			var htm = "<div class='table-panel-tr'>"
					+ "<span class='index-span-index'></span>"
					+ "<div class='table-ent-name'>"
					+ "<div class='ent-name omit' title='"
					+ data[i]['entName']
					+ "'>"
					+ data[i]['entName']
					+ "</div>"
					+ "<span class='app-date' >"
					+ data[i]['submitDate']
					+ "</span>"
					+ "</div>"
					/*+ "<div class='flag'><img src='../../../static/image/img/24.png' /></div>" */

					+ "<div class='flag'>"
					+ (data[i]['authToApplyUser'] == '1' ? "<img src='../../../static/image/img/24.png' />"
							: "") + "</div>"
					+ "<div class='state' text='state'>"
					+ data[i]['operationType'] + "</div>";
			// 按钮
			$.each(data[i]["opr"], function(index, val) {
				code = val.code;
				label = val.label;
				url = val.url;
				// 稍后通过class:setupInit绑定事件
				htm += "<a href='" + url + "' class='" + code
						+ "'>" + label + "</a>";

			});
			htm += "</div>";
			data[i]["custom"] = htm;

		}
		// 业务授权
		$('a.authOpr').live(
				'click',
				function() {
					var hrefUrl = $(this).attr("href");
					util.openWindow('authOpr', '业务提交确认授权', hrefUrl,
							710, 660);
					return false;
				}),
		// 业务终止按钮事件重新绑定
		$('a.stopOpr').live('click', function() {
			var hrefUrl = $(this).attr('href');
			/* alert(hreUrl); */
			util.openWindow('stopReq', '业务终止', hrefUrl, 593, 370);

			return false;
		});
		// 删除业务按钮事件重新设置
		$('a.deleteOpr').live('click', function() {
			var hrefUrl = $(this).attr('href');
			jazz.confirm("删除后不能恢复，确定要删除这条业务吗？", function() {
				$.ajax({
					url : hrefUrl,
					type : 'post',
					success : function(data) {
						jazz.info("删除成功", function() {
							window.location.reload();
						});

					},
					error : function(data) {
						jazz.error("删除失败");
					}
				});
			}, function() {

			});

			return false;
		});
		return data;
	},
	
	
	//去除中间空格方法
	powerTrim : function(str,a){
		var result;
		result = str.replace(/(^\s+)|(\s+$)/g,"");
		if(a.toLowerCase()=="g")
			result = str.replace(/\s/g,"");
		return result;
	}
	
};
torch._init();
return torch;

});
