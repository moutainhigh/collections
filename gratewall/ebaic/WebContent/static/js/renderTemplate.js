(function (factory) {
    if (typeof define === "function" && define.amd) {
        // AMD模式
        define(["jquery", "etpl", "template" ], factory);
    } else {
        // 全局模式
        factory(jQuery);
    }
 }(function($, etpl, TEMPLATE) {
	 if(!engine){
		 var engine = new etpl.Engine();
		 window.engine = engine;
	 }
	
	window.engine.addFilter('max', function(source){
		if(source && source.indexOf('max=') != -1){
			var list = source.split(';')
			for(var i = 0,len = list.length; i < len; i++){
				var index = list[i].indexOf('max=');
				if(index != -1){
					return list[i].substring(index + 4);
				}
			}
		}else{
			return '99999';
		}
	});
	window.engine.addFilter('min', function(source){
		if(source && source.indexOf('min=') != -1){
			var list = source.split(';')
			for(var i = 0,len = list.length; i < len; i++){
				var index = list[i].indexOf('min=');
				if(index != -1){
					return list[i].substring(index + 4);
				}
			}
		}else{
			return '0';
		}
	});
	window.engine.addFilter('isAdd', function(source){
		if(source && source.indexOf('isAdd=') != -1){
			var list = source.split(';')
			for(var i = 0,len = list.length; i < len; i++){
				var index = list[i].indexOf('isAdd=');
				if(index != -1){
					return list[i].substring(index + 6);
				}
			}
		}else{
			return '0';
		}
	});
    var RenderTemplate = function(element, options, data) {
      this.element = element;
      this._init(options, data);
    };
    RenderTemplate.dafaults = {
        insertType : 'append',  //默认为append其他（prepend, after, before, wrap）
        templateName : '',
        wrapSelector : ''
    };
    RenderTemplate.prototype.template = TEMPLATE;
    RenderTemplate.prototype._init = function(options, data){
        this.options = $.extend(true, {}, RenderTemplate.dafaults, options);
        this.render(this.options.templateName, data);
        this.insert(this.options.insertType);
    };
    RenderTemplate.prototype.render = function(templateName, data){
        if(templateName == null || templateName == ''){
            alert('模板未指定');
            return;
        }
        var temp = this.template[templateName];
        if(temp == null || temp == ''){
            alert('模板指定错误');
            return;
        }
        var tempData = $.extend(true, {}, temp.data, data);
        
        var renderTemp;
        if(window.engine.targets && window.engine.targets[templateName]){
        	renderTemp = window.engine.getRenderer(templateName);
        }else{
        	try {
	                renderTemp = window.engine.compile(temp.tpl);
	                if ( !renderTemp ) {
	                    alert('模板生成错误');
	                    return;
	                }
	            }catch ( ex ) {
	            alert(ex.message);
	            return;
	        }
        }
        
        this.result = renderTemp(tempData);

    };
    RenderTemplate.prototype.insert = function(insertType){
        if(insertType == null){
            alert('未选择模板插入方式');
            return;
        }
        switch (insertType){
            case 'prepend':
                this.element.prepend(this.result);
                break;
            case 'after':
                this.element.after(this.result);
                break;
            case 'before':
                this.element.before(this.result);
                break;
            case 'wrap':
                this.wrap(this.result);
                break;
            default:
                this.element.append(this.result);
                break;
        }
    };
    RenderTemplate.prototype.wrap = function(content){
        if(!this.options.wrapSelector || this.options.wrapSelector == '' || !content || content == ''){
            this.element.wrap(content);
        }
        else{
            var temp = {
                    content : {},    //模板内容
                    before : [],    //要包裹的元素的同级前面元素
                    after : [],    //要包裹的元素的同级元素后面的元素
                    element :{}    //要包裹的元素
            };
            temp.content = $(content);
            if(temp.content.length == 0){
                alert("模板生成错误！");
                return;
            }
            while(temp.content.children().length > 0){    //循环所有元素查找要包裹的元素
                if(temp.content.children().length > 1){
                    var chs = temp.content.children();
                    var len = chs.length;
                    var flag = "before";
                    for(var i =0; i <len; i++){
                        if(chs.eq(i).is(this.options.wrapSelector) || chs.eq(i).find(this.options.wrapSelector).length > 0){
                            temp.element = chs.eq(i);    //要包裹的元素
                            flag = "after";
                        }else{
                            temp[flag].push(chs.eq(i));
                        }
                    }
                    if(flag == "before"){
                        alert("没有找到要插入的元素");
                        return;
                    }
                    break;
                }else{
                    temp = temp.children().eq(0);
                }
            }
            temp.content.find(temp.element).parent().empty().append(temp.element);  //清空要包裹的元素的同级元素
            this.element.wrap(temp.content);
            var that;
            this.element.parents().each(function(index, item){
                if($(item).is("." + temp.element.attr("class"))){
                    that = $(item);    //找到包裹后的要包裹的元素
                }
            })
            if(!that){
                alert("没有找到要插入的元素");
                return;
            }
            //循环插入同级元素
            for(var i = 0, len = temp.before.length; i < len; i++){
                that.before(temp.before[i]);
            }
            for(var i = 0, len = temp.after.length; i < len; i++){
                that.parent().append(temp.after[i]);
            }
        }
        
    }



    $.fn.renderTemplate = function(options, data) {
      this.each(function(){

        new RenderTemplate($(this), options, data);
        
      });
    };
  })

);

 
