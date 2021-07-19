(function($){$.DataAdapter={submit:function(params,$this){var adapter=new $.dataAdapterObject();adapter.submit(params,$this)},query:function(params){var adapter=new $.dataAdapterObject();adapter.createAdapter();adapter.options.components=params.components;adapter.componentsData2();return adapter.dataArray},vtypeset:["gridpanel","formpanel","querypanel","comboxtreefield","comboxfield","icon","attachment","boxlist","tree"]};$.dataAdapterObject=function(){this.options={sourceobject:null,contextpath:"",name:null,vtype:null,components:null,url:null,type:"post",data:null,success:null,error:null,callback:null,params:null,pageparams:null,queryparams:null,showloading:null}};$.dataAdapterObject.prototype={createAdapter:function(){this.jazzData=new Object();this.dataArray=new Array();this.jazzData.data=this.dataArray},addAttr:function(name,value){if(!!name&&!!vtype){var temp={};temp.name=name;temp.vtype="attr";temp.data=value;this.dataArray[name]=value}},addForm:function(name,vtype,formData){this.dataArray.push(formData)},addGrid:function(name,vtype,gridData){var obj={};obj.name=name;obj.vtype=vtype;obj.data={};obj.data["rows"]=gridData||[];this.dataArray.push(obj)},addSelect:function(name,vtype,selectData){this.dataArray.push(selectData)},addTree:function(name,vtype,treeData){this.dataArray.push(treeData)},addIcon:function(name,vtype,iconData){var obj=new Object();this.dataArray.push(iconData);return obj},getJazzDataString:function(){return jazz.jsonToString(this.jazzData)},getSubmitJsonData:function(){return this.jazzData},ajaxSubmit:function(){var $this=this;var initRes={obj:{},setAttr:function(key,value){this.obj[key]=value},getAttr:function(key){return this.obj[key]}};var data=this.getJazzDataString();$.ajax({url:$this.options.url,type:$this.options.type,dataType:"json",data:{postData:data},async:true,contentType:"application/x-www-form-urlencoded; charset=utf-8",error:$this.options.error||function(responseObj){if(responseObj.responseText){var err=jazz.stringToJson(responseObj.responseText);if(err.exceptionMes){jazz.error('<font color="blue" >错误信息</font> : '+err.exceptionMes)}else{jazz.error("与服务器连接断开，请尝试重新登录或与管理员联系!")}}else{jazz.error("与服务器连接断开，请尝试重新登录或与管理员联系!")}return false},complete:function(){if(((jazz.config.submitShowLoading==true&&$this.options.showloading==null)||$this.options.showloading==true)&&$this.modality){$this.modality.remove()}if($.isFunction($this.options.complete)){$this.options.complete.apply(this)}},success:function(responseObj){if(responseObj.message=="noSessionRight"){return false}if(responseObj.exception){jazz.error('<font color="blue" >错误名称</font> : '+responseObj.exception+'<br><font color="blue" >错误信息</font> : '+responseObj.exceptionMes);return false}var callbackdata=null;var jsonData=responseObj.data;if(typeof(jsonData)=="object"){if(jsonData!=null&&$.isArray(jsonData)){var newJsonData=[];$.each(jsonData,function(i,jsonObj){var vtype=jsonObj.vtype;if(vtype=="attr"){initRes.setAttr(jsonObj.name,jsonObj.data)}newJsonData.push(jsonData[i])});callbackdata=newJsonData[0]}}else{callbackdata=jsonData}if($.isFunction($this.options.callback)){$this.options.callback.call(this,callbackdata,$this.options.sourceobject,initRes)}}})},componentsData:function(){var flag=true;var components=this.options.components;if($.isArray(components)){for(var i=0,len=components.length;i<len;i++){var compJson=$('div[name="'+components[i]+'"]');var vtype=compJson.attr("vtype");var name=compJson.attr("name");if(vtype=="formpanel"){$('div[name="'+components[i]+'"]').find("div[vtype]").each(function(index,element){var el=$(element);var vtype=el.attr("vtype"),$field=el.data(vtype);var options=$field.options;var rule=options.rule;if(rule&&vtype!="gridpanel"&&vtype!="hiddenfield"&&vtype!="checkboxfield"&&vtype!="radiofield"&&options.isrule){var msg=options.msg;var f=jazz.doTooltip($field,$field.getText(),rule,msg);if(f==false){flag=false;$field._validateStyle(f)}}})}if(!flag){return false}if($.inArray(vtype,$.DataAdapter.vtypeset)>=0){if(vtype=="formpanel"||vtype=="querypanel"){var data=compJson[vtype]("getValue");this.addForm(name,vtype,data)}else{if(vtype=="gridpanel"){var data=compJson[vtype]("getSelection");this.addGrid(name,vtype,data)}}}else{alert("解析的vtype类型错误！！！ ");return false}}}var qparams=this.options.queryparams;if(qparams){var $this=this;$.each(qparams,function(){$this.dataArray.push(this)})}var params=this.options.pageparams;if(params&&typeof(params)=="object"){for(var p in params){var param={};param.vtype="pagination";param.name=p;param.data=params[p];this.dataArray.push(param)}}var params=this.options.params;if(params&&typeof(params)=="object"){for(var p in params){var param={};param.vtype="attr";param.name=p;param.data=params[p];this.dataArray.push(param)}}return flag},componentsData2:function(){var params=this.options.params;if(params&&typeof(params)=="object"){for(var p in params){var param={};param.vtype="attr";param.name=p;param.data=params[p];this.dataArray.push(param)}}var components=this.options.components;if($.isArray(components)){for(var i=0,len=components.length;i<len;i++){var name=components[i];var compJson=$('div[name="'+name+'"]');var vtype=compJson.attr("vtype");if(vtype=="formpanel"||vtype=="querypanel"){var data=compJson[vtype]("getValue");this.addForm(this.options.name,vtype,data)}else{if(vtype=="textfield"||vtype=="comboxfield"||vtype=="datefield"||vtype=="comboxtreefield"||vtype=="checkboxfield"||vtype=="radiofield"||vtype=="autofield"||vtype=="numberfield"||vtype=="passwordfield"){var data=compJson[vtype]("getValue");var param={};param.vtype="attr";param.name=name;param.data=data;this.dataArray.push(param)}}}}},submit:function(params,$sourcethis){$.extend(this.options,params);if((jazz.config.submitShowLoading==true&&this.options.showloading==null)||this.options.showloading==true){this.modality=$('<div id="submit_"'+jazz.getRandom()+'"></div>').css({position:"fixed",top:"0px",left:"0px",width:jazz.windowWidth(),height:jazz.windowHeight(),"z-index":++jazz.config.zindex}).appendTo(document.body).loading({blank:true})}var flag=true;this.createAdapter();this.options.sourceobject=$sourcethis;flag=this.componentsData();if(String(flag)=="false"){if(((jazz.config.submitShowLoading==true&&this.options.showloading==null)||this.options.showloading==true)&&this.modality){this.modality.remove()}return false}this.ajaxSubmit()}};$(function(){var initRes={obj:{},setAttr:function(key,value){this.obj[key]=value},getAttr:function(key){return this.obj[key]}};var initPageData=$("#OptimusData").attr("data");if(typeof(initPageData)!="object"){if(initPageData){initPageData=jazz.stringToJson(initPageData)}}if(initPageData){var jsonData=initPageData.data;if($.isArray(jsonData)){$.each(jsonData,function(i,jsonObj){var vtype=jsonObj.vtype,name=jsonObj.name;if(vtype=="attr"){initRes.setAttr(name,jsonObj.data)}})}if($.isFunction(window.initData)){initData(initRes)}}})})(jQuery);