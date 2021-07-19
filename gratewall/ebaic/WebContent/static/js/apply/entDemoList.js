define(['jquery', 'jazz', 'js/renderTemplate','js/util'], function($, jazz, tpl,util){
	
    var entDemo = {
    	/**
    	 * 模块初始化。
    	 */
    	_init: function(){
    		// 处理模板
	    	$("body").renderTemplate({templateName:'footer',insertType:'append'});
	    	$("body").renderTemplate({templateName:'header',insertType:'prepend'});
	    	
	    	// 绑定事件
	    	$('#entDemoGridpanel').gridpanel('option','datarender',this.entDemoGridpanel_datarender);
	    	
	    	$("[name='btnQuery']").on('click', this.query); 
	    	$("[name='btnReset']").on('click', this.reset); 
	    	
	    	$("[name='btnEntDemoAdd']").on('click', this.toEntDemoAdd); 
	    	
	    	// 导出函数
	    	util.exports('toEntDemoEdit',this.toEntDemoEdit);
	    	util.exports('toEntDemoDel',this.toEntDemoDel);
    	},
    	/**
    	 * 列表 数据渲染
    	 */
    	entDemoGridpanel_datarender : function(event,obj) {
    		var data = obj.data;
    		if(!data){
    			return ;
    		}
    		var row , entId ,entName ;
    		for(var i=0;i<data.length;i++){
    			row = data[i] ;
    			entId = row['entId'] || '';
    			entName = row['entName'] || '';
    			
    			// 操作列
    			var optHtml = '<a href="javascript:void(0);" onclick="ebaic.toEntDemoEdit(\''+entId+'\')">' + "编辑" + '</a>&nbsp;&nbsp;';
    			optHtml += '<a href="javascript:void(0);" onclick="ebaic.toEntDemoDel(\''+entId+'\',\''+entName+'\')">' + "删除" + '</a>&nbsp;&nbsp;';
    			row["opt"] = optHtml; 
    			
    		}
    		return data;
    	} ,
    	/**
    	 * 执行查询/刷新列表。
    	 */
    	query : function (){
    		var queryformName = 'entDemoQueryFormpanel';
    		var resultGridpanelId = 'entDemoGridpanel';
    		var dataurl = '../../../entdemo/getList.do';
    		util.queryForm(queryformName,resultGridpanelId, dataurl);
    	},
    	/**
    	 * 重置表单 
    	 */
    	reset : function (){  
    		var formId = 'entDemoQueryFormpanel' ;
    		util.resetForm(formId);
    	} ,
    	

    	toEntDemoAdd: function (){
    		util.openWindow("entDemoEditWindow","新增企业Demo",300,400);
    	},
    	/**
    	 * 编辑
    	 */
    	toEntDemoEdit: function (entId){
    		if(!entId){
    			jazz.info("企业编号为空。");
    			return ;
    		}
    		util.openWindow("entDemoEditWindow","编辑企业Demo",300,400);
    	},
    	
    	/**
    	 * 删除
    	 */
    	toEntDemoDel : function (entId,entName){
    		if(!entId){
    			jazz.info("企业编号为空，不能删除。");
    			return ;
    		}
    		if(!entName){
    			jazz.info("企业名称为空，不能删除。");
    			return ;
    		}
    		jazz.confirm("是否确认删除“"+entName+"”",function(){
    			alert('删除成功了？');
    		});
    	}
    	
    };
    entDemo._init();
    return entDemo;
});