define(['jquery', 'jazz','util' ], function($, jazz,util){
 var torch ={};
	torch = {
		/**
		 * 模块初始化
		 */
			_init: function(){
				$_this = this;
				require(['domReady'], function (domReady) {
					    domReady(function () {
                               //初始化列表数据
				    		$("#save_button").off('click').on('click',$_this.queryCFList);
						    $("#reset_button").off('click').on('click',torch.resetEditCondition);
						    $_this.loadingData();
						    $_this.queryList();
					    });
				});
			},
			
			loadingData:function(){
				$("#select").comboxfield('addOption', '快速查询', '快速查询');
				$("#select").comboxfield('addOption', '商事主体综合查询', '商事主体综合查询');
				$("#select").comboxfield('addOption', '重复地址查询', '重复地址查询');
				$("#select").comboxfield('addOption', '总局黑名单', '总局黑名单');
				$("#select").comboxfield('addOption', '年报查询', '年报查询');
				$("#select").comboxfield('addOption', '一人公司查询', '一人公司查询');
				$("#select").comboxfield('addOption', '失信被执行人查询', '失信被执行人查询');
				$("#select").comboxfield('addOption', '异常名录查询', '异常名录查询');
				$("#select").comboxfield('addOption', '投资人', '投资人');
				$("#select").comboxfield('addOption', '主要人员信息', '主要人员信息');
				
			},
			queryList:function(){
				$("#select").comboxfield("option", "itemselect", function(event, ui){  
					
					var query = $("#select").comboxfield('getValue');
					$.ajax({
						url:"../../app.do",
						type:"post",
						data:{"name":query},
						dataType:"json",
						success:function(data){
							//console.log(data.data[0].data);
							var name = data.data[0].data;
							var gridUrl ="../../list.do?name="+name;
							$("#CFQueryListGrid").gridpanel("option",'dataurl',gridUrl);
				    		//$("#CFQueryListGrid").gridpanel('query', ['CFQueryListPanel'],null);
							$("#CFQueryListGrid").gridpanel("reload");
						}
						
					});
				});
			},
			edit_fromNames : [ 'CFQueryListPanel']
		};
		torch._init();
		return torch;
});
