(function($,factory){if(jazz.config.isUseRequireJS===true){define(["jquery"],factory)}else{factory($)}})(jQuery,function($){$.widget("jazz.component",{options:{name:"",content:null,items:null},_create:function(){this._attrToOptions();this.element.attr("vtype",this.options.vtype);var name=this.options.name;if(!name){this.options.name=name=this.options.id||this.getCompId()}this.element.attr("name",name);var $this=this;this.options.create=function(){$this.content_number=1;$this.items_number=1;$this._vtypetree()}},_init:function(){},_attrToOptions:function(){var c_a=jazz.attributeToOptions(this.element.get(0));for(var p in c_a){if(c_a[p]==="true"){c_a[p]=true}else{if(c_a[p]==="false"){c_a[p]=false}}}$.extend(this.options,c_a)},_createContent:function(obj){if(obj instanceof $&&this.content_number===1){$.each(obj.find("div[vtype]"),function(){$(this).parseComponent()});this.content_number++}},_createItems:function(obj){var items=this.options.items;if($.isArray(items)){if(obj instanceof $&&this.items_number===1){$.each(items,function(i,item){var vtype=item.vtype;if(vtype){var v=$("<div>").appendTo(obj);v[vtype](item)}});this.items_number++}}},_customopration:function(){if(arguments.length==0){return false}var cb=arguments[0];var params=[];if(cb){for(var i=1;i<arguments.length;i++){params.push(arguments[i])}if($.isFunction(cb)){return cb.apply(this,params)}else{if(cb.indexOf("(")!=-1){cb=cb.substr(0,cb.indexOf("("))}return eval(cb).apply(this,params)}}},_event:function(eventName,event,data){var _ename=eventName;var callback=this.options[_ename];if(!$.isFunction(callback)){var reg=/\(/;if(reg.test(callback)){callback=callback.split("(")[0]||null;if(callback==null){return false}}callback=eval(callback+"")}data=data||{};event=$.Event(event);event.type=(eventName===this.options.vtype?eventName:this.options.vtype+eventName).toLowerCase();event.currentClass=this;event.target=this.element[0];orig=event.originalEvent;if(orig){for(prop in orig){if(!(prop in event)){event[prop]=orig[prop]}}}this.element.trigger(event,data);return !($.isFunction(callback)&&callback.apply(this.element[0],[event].concat(data))===false||event.isDefaultPrevented())},_vtypetree:function(){if(this.options.createtype!="0"){jazz.vtypetree(this.element,"1")}},finish:function(){},getCompId:function(){var id=jazz.getId();return id},getParentComponent:function(){var nodeData=this.element.data("vtypetree")||{};return nodeData.parent||{}},getChildrenComponent:function(){var nodeData=this.element.data("vtypetree")||{};return nodeData.child||{}},getChildrenComponentByVtype:function(vtype){var element=null;var nodeData=this.element.data("vtypetree")||{};for(var childname in nodeData.child){var data=nodeData.child[childname].data("vtypetree");if(data.vtype==vtype){element=nodeData.child[childname];break}}return element}})});