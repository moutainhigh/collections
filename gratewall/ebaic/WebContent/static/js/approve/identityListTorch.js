define(['require', 'jquery', 'approvecommon','util'], function(require, $, approvecommon,util){
var torch = {
		queryGrid_datarender : function (event,obj){
	},

	query_fromNames : [ 'queryGrid_Form'],


	queryIdentitySure : function(){
					$("div[name='queryGrid_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=queryIdentity&wid=queryIdentity");
					$("div[name='queryGrid_Grid']").gridpanel("query", [ "queryGrid_Form"]);
//					$("div[name='queryGrid_Grid']").gridpanel("option", "datarender",function(obj,resultData){
//						var dataArrs = resultData.data;
//						if(dataArrs){
//							for(var i=0;i<dataArrs.length;i++){
//								if(dataArrs[i].state=='0'){
//									dataArrs[i]["operate"]="<div name='identity_button' class='littleBt'>审查</div>";
//								}
//							}
//						}
//					});
			},
	queryIdentityReset : function(){
				for( x in torch.query_fromNames){
					$("div[name='"+torch.query_fromNames[x]+"']").formpanel("reset");
				} 
			},
	 _init : function(){

	 require(['domReady'], function (domReady) {
	    domReady(function () {
			$("div[name='_Form']").gridpanel("option","datarender",torch.queryGrid_datarender);
			$("div[name='_Form']").gridpanel("reload");
			$("div[name='queryIdentity_query_button']").off('click').on('click',torch.queryIdentitySure);
			$("div[name='queryIdentity_reset_button']").off('click').on('click',torch.queryIdentityReset);
			$("div[name='identity_button']").off('click').on('click',torch.identityApprove);
			$("div[name='newIdentity']").on('click',torch.newIdentity);
		 });
		});
	 },
	 
	 newIdentity:function(){
		 window.location.href="../../../page/approve/sysidentify/sysmgr_identify.html";
	 },
	 identityApprove:function(){
 		var rowData = $("div[name='queryGrid_Grid']").gridpanel('getSelectedRowData');
 		if(rowData.length==0){
 			jazz.warn("请选择需要进行身份审查的数据!");
 			return false;
 		}
 		var state = rowData[0].state;
 		if(state!=0){
 			jazz.warn("请选择身份待审查的数据!");
 			return false;
 		}
 		var name = rowData[0].name||'';
 		var cerType = rowData[0].cerType||'';
 		var cerNo = rowData[0].cerNo||'';
 		var personSign = rowData[0].personSign||'';
 		//util.openWindow("censor_identity", "现场身份认证", "../../../page/approve/process/liveIdentity_check.html?name="+util.encodeURI(name)+"&cerType="+cerType+"&cerNo="+cerNo ,800 , 600);
 		window.location.href="../../../page/approve/process/liveIdentity_check.html?name="+util.encodeURI(name)+"&cerType="+cerType+"&cerNo="+cerNo+"&personSign="+personSign;
	 },
};
torch._init();
return torch;

});
