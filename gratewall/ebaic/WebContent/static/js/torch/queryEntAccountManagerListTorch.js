/**
  * 自定义的模块化开发
  */

define(['require', 'jquery', 'entCommon','util', 'jazz'], function(require, $, entCommon,util, jazz){
var torch = {
		/**
		 * 格式化列表数据
		 * @param event
		 * @param obj
		 */
		queryList_datarender : function (event,obj){
			$("#ent-accout-list").empty();
			var rows = obj.data;
			var code = "";
			var label = "";
			var url = "";
			//
			for (var i = 0; i < rows.length; i++) {
				var htm="";
				if(!rows[i]){
					continue;
				}
				var htm="<div class='card btnEntDemoAdd'>"
					+"<p class='name-info omit'>"+rows[i]['name']+"</p>"
					+"<div class='info-tab'><span class='triangle'></span>"
					+"<span>已"+rows[i]['accoutState']+"</span></div>"
					+"<p class='setup-president'>"+rows[i]['mobile']+"</p>"
					+"<input type='hidden' value='"+rows[i]['managerId']+"'>"
					+"<div class='info-setup'>"
					+"<img src='../../../static/image/img/icon/deteleGreen.png' name='del_button' id='del'>"
					+"<img src='../../../static/image/img/icon/edit.png' name='edit_button' id='edit' ></div>";
					htm += "</div></div>";
					
				//	rows[i]["custom"] = htm;
					$("#ent-accout-list").append(htm);
					/*rows[i].accoutState='<a href="javascript:void(0);"   title="state" >'+rows[i].accoutState+'</a>';
					  rows[i]["opr"]='<a href="javascript:void(0);"   title="state" >编辑</a>';*/
				//按钮
				$.each(rows[i]["opr"],function(index,val){
					code = val.code;
	    			label = val.label;
	    			url = val.url;
	    			// 稍后通过class:setupInit绑定事件
	    			htm += "<a  href='"+url+"' class='"+code+"'>"+label+"</a> &nbsp;&nbsp;";	
	    			
				});
				rows[i]["opr"] = htm;
			}
			return rows;
			//账户状态切换按钮初始化
			
		},
		query_fromNames : [ 'queryList_Form'],
		/**
		 * 列表数据加载
		 */
		queryEntAccountManagerListSure : function(){
			$("div[name='queryList_Grid']").gridpanel("option","datarender",torch.queryList_datarender);
			$("div[name='queryList_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=queryEntAccountManagerList&wid=queryEntAccountManagerList");
			$("div[name='queryList_Grid']").gridpanel("query", [ "queryList_Form"]);
			
			//
//			var allRows = $("div[name='queryList_Grid']").gridpanel("getAllData");
		},
		queryEntAccountManagerListReset : function(){
			for( x in torch.query_fromNames){
				$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
			} 
		},
		/**
		 * 添加企业管理员账号
		 */
		add_button:function(){
			//util.openWindow("addManager","添加管理员","addAccountManager.html",1008,900);
			window.location.href="addAccountManager.html";
			
		},
		/**
		 * 返回企业登录主页
		 */
		back_button:function(){
			window.location.href = "../../../page/apply/ent_account/home.html";
		},
		/**
		 * 编辑数据
		 */
		editButton:function(managerId){
			/*var datas = $("div[name='queryList_Grid']").gridpanel('getSelectedRowData');
			var managerId = "";
			if(datas.length==0){
				jazz.warn("请选中一条要编辑的数据!");
				return;
			}else{
				managerId = datas[0].managerId;
			}*/
			var managerId=$(this).parent().prev().val();
			var url = "editEntAccountManagerInfo.html?managerId="+managerId ;
			if(screen.width>=1440){
				util.openWindow("editManager","编辑管理员",url,1010,660);
			}else{
				util.openWindow("editManager","编辑管理员",url,1010,530);
			}
		},
		/**
		 * 删除选中
		 */
		del_button:function(){
			//var datas = $("div[name='queryList_Grid']").gridpanel('getSelectedRowData');
			var managerIds=$(this).parent().prev().val();
			/*alert(managerIds);*/
			//var managerIds = "";
		/*	if(datas.length==0){
				jazz.warn("请选中要删除的数据!");
				return;
			}else{
				for(var i=0;i<datas.length-1;i++){
					managerIds = managerIds+datas[i].managerId+",";
				}
				managerIds = managerIds +datas[datas.length-1].managerId
			}*/
			//数据库删除数据
			var param ={
					url: '../../../apply/entManagerAccount/deleteManagers.do',
					params:{managerIds:managerIds},
					async:true,
					callback:function(data,param,res){
						var result = res.getAttr("result");
						if(result=="success"){
							jazz.confirm("是否删除?",function(){
								jazz.info("删除成功!");
								torch.queryEntAccountManagerListSure();
							});
						}else{
							jazz.error("删除失败。");
							return;
						}
					}
			};
			$.DataAdapter.submit(param,this);
		},
			
		/**
	     * 获取选择的数据 
	     */
	    getRequisitionIdarr : function (){
	    	var datas = $("div[name='queryList_Grid']").gridpanel('getSelectedRowData');
	    	var requisitionarr = [];
	    	for(var i=0;i<ii.length;i++){
	    		requisitionarr[i] = ii[i].requisitionId;
	    	}
	    	return requisitionarr;
	    },
			
			/*//动态传递参数的
			_openAddWindow:function(windowName,windowTitle,dataUrl,width,height){1
				if(width<800){
					width = 800;
				}
				if(height<780){
					height=780;
				}
				util.openWindow(windowName,windowTitle,dataUrl,width,height);
				
			},*/
			
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
	    	
	    	//列表数据初始化
	    	torch.queryEntAccountManagerListSure();
	    	//账户状态切换
//	    	torch.changeState();
	    	//回车提交查询
			$(document).keypress(function(e) {
				if (e.which == 13) {
					//回车查询
					torch.queryEntAccountManagerListSure();
				}
			});
			
			//绑定对像事件
			$("div[name='add_button']").on('click',torch.add_button);//绑写新增管理员
			$("div[name='queryEntAccountManagerList_return_button']").off('click').on('click',torch.back_button);//返回
			//
			/*$("img[name='edit_button']").off('click').on('click',torch.editButton);//绑写新增管理员
			$("img[name='del_button']").off('click').on('click',torch.del_button);//绑写删除该管理员*/
			$("img[name='edit_button']").live('click',torch.editButton);//绑写新增管理员
			$("img[name='del_button']").live('click',torch.del_button);//绑写删除该管理员
			$("div[name='queryEntAccountManagerList_query_button']").off('click').on('click',torch.queryEntAccountManagerListSure);
			$("div[name='queryEntAccountManagerList_reset_button']").off('click').on('click',torch.queryEntAccountManagerListReset);
		 });
		});
	 	$("div[name='queryList_Form']").css("margin","0 auto");
	 },
	 
	 back_button:function(){
			window.location.href = "../../../page/apply/ent_account/home.html";
	},
};
	torch._init();
//	torch.getRequisitionIdarr();
	return torch;

});
