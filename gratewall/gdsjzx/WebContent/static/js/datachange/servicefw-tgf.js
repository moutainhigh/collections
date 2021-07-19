var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			},
			key: {
				checked: "checked"
			}
		},
		check: { 
            /* enable: true, 
            chkboxType:{ "Y":'s', "N":'s'},  */
			enable: true,
			chkStyle: "radio",
			radioType: "level"
        }
	};
	
	var update;
	var serviceobjectname;
	var tree;
	$(function(){
		update=$('#serviceUpdate', parent.document).val();
		var serviceid=$('#serviceidupdate', parent.document).val();
		if(update=="true"){
			changeArea();
			$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/dataservice/serviceDetail.do?serviceid='+serviceid);
			$("#formpanel_edit").formpanel('reload', "null", function(){
			$('#formpanel_edit .jazz-panel-content').loading('hide');
			var sname=$('div[name="servicename"]').hiddenfield('getValue');
			//var arrname=sname.split("/");
			var region=$('div[name="region"]').hiddenfield('getValue');
			var serviceorgname=$('div[name="serviceorgname"]').hiddenfield('getValue');
			var serviceurl=$('div[name="serviceurl"]').textareafield('getValue');
			
			if(serviceurl!=null && serviceurl.length>0){
				serviceurl=serviceurl.replace("http://","");
				
				var indexstr=serviceurl.split("/");
				$('#serviceclassify').val(indexstr[2]);
				var serviceurl=indexstr[0]+"/"+indexstr[1]+"/"+indexstr[indexstr.length-1];
				$('div[name="serviceurl"]').textareafield('setValue',serviceurl);
			}
			checkArea(region,"2");
			//回显
			$('#BelongArea').find('option[value="'+region+'"]').attr("selected","selected");
			$('#BelongOrg').find('option[value="'+serviceorgname+'"]').attr("selected","selected");
			});
		}else{
		 	$('div[name="createtype"]').hiddenfield('setValue', "tgf");
		 	$('div[name="defaulttime"]').textfield('setValue', "30000");
		}

		var funcUrl = rootPath+'/dataservice/serviceContentTree.do?serviceid='+serviceid+'&update='+update;
		var params = {
			url : funcUrl,
			callback : function(data, r, res) { 
				var data = data["data"];
				tree = $.fn.zTree.init($("#funcTree"), setting, data);
				tree.expandAll(true);
			}
		}
		//$.DataAdapter.submit(params);
		
		/*$('div[name="servicetype"]').on("comboxfielditemselect",function(event, ui){  
			if(ui.value==2){
				$('#dy').show();
			}else{
				$('#dy').hide();
			}
		});*/
		
	});