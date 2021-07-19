//站点菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	
	reg({
		key:"addDelegate",
		desc:"我的委托",
		parent:"GRFW",
		order:2,
		cmd : function(event){
			wcm.CrashBoarder.get('my_workflow_delegate').show({
				title : '委托管理界面',
				src :  WCMConstants.WCM6_PATH +'flowdoc/my_delegate.jsp',
				width:'360px',
				height:'300px',
				top:'50px',
				left:'350px',
				maskable: true,
				btns : [
					{
						text : '关闭', 
						cmd : function(){
							this.close();
							return false;
						}
					}
				]
			});
		}
	});
})();