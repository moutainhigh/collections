/**
 * A jQuery plugin boilerplate.
 * jquery.data2table.js
 * jufeng@gwssi.com.cn 2013.3.18
 */
var isIe = $.browser.msie;

;(function($) {
  // Change this to your plugin name.
  var pluginName = 'data2table';
  var isIe = $.browser.msie;
  
  /**
   * Plugin object constructor.
   * Implements the Revealing Module Pattern.
   */
  function Plugin(element, options) {
    // References to DOM and jQuery versions of element.
    var el = element;
    var $el = $(element);
 
    // Extend default options with those supplied by user.
    options = $.extend({}, $.fn[pluginName].defaults, options);
    var id = $el.attr("id");
    /**
     * Initialize plugin.
     */
    function init() {
      
      //bind options' array data , id=selData
     $el.data('selData', options.data);
    
     if(options.colNames){
     	 var names = options.colNames;
      	 var thead = document.createElement('THEAD');
      	
      	 var headTr = document.createElement('TR');
      	 thead.appendChild(headTr);
      	 
      	 element.appendChild(thead);
      	 if(options.colNum){
      	 	var colNum = document.createElement('TH');
      	 	headTr.appendChild(colNum);
      	 	if(isIe){
      	 		colNum.innerText='序号';
      	 	}else{
      	 		colNum.textContent='序号';
      	 	}
      	 }
      	 
      	 for(var j in names){
      	 	var col = document.createElement('TH');
      	 	headTr.appendChild(col);
      	 	if(isIe){
      	 		col.innerText=names[j];
      	 	}else{
      	 		col.textContent=names[j];
      	 	}
      	 	col.style.width='20%';
      	 }
     	 
     	 if(options.colOpt){
     	 	var col = document.createElement('TH');
      	 	headTr.appendChild(col);
      	 	if(isIe){
      	 		col.innerText='操作';
      	 	}else{
      	 		col.textContent='操作';
      	 	}
      	 	
     	 }

      }else{
      	 alert("colName must config");
      }
     
 	  if(options.colModel){
 	  	
 	  	var tbody = document.createElement('TBODY');
 	  	tbody.setAttribute('id','tbody_'+id);
      	element.appendChild(tbody);
 	  	
 	  	if(options.data){
 	  		var datas = options.data;
 	  		_addRows(datas);
 	  	}
 	  	
 	  }else{
 	  	 alert("colMode must config");
 	  }
 	  
      hook('onInit');
    }
    function _delRow(key){
    	element.deleteRow(key)
    }
    
    function _resetColNum(){
    	$('#tbody_'+id).find('.sn').each(function(index,value){
    		$(this).text( (index+1) );
    	})
    }
    
    function _addRows(datas){
    	var models = options.colModel;
    	var tbodyId = "tbody_"+id;
    	var tbody = document.getElementById(tbodyId);
    	var n = 0;
    	if(options.colNum){
    		n = $('#'+tbodyId).find('.sn').size();
    	}
    	
    	if(datas.length>0){
 	  			for(var j in datas){
 	  				var tr= document.createElement('TR');
 	  				var colNumTd = null;
 	  				if(options.colNum){
 	  					colNumTd= document.createElement('TD');
 	  					var num = parseInt(n,10) +1;
 	  					n++;
 	  					if(isIe){
			      	 		colNumTd.innerText=num;
			      	 	}else{
			      	 		colNumTd.textContent=num;
			      	 	}
			      	 	
 	  					tr.appendChild(colNumTd);
 	  					
 	  				}
 	  				
 	  				for(var i in models){
 	  						var td = document.createElement('TD');
 	  						var key = models[i].name;
 	  						var item = 'datas['+j.toString()+ ']';
 	  						var text  = '';
 	  						var exp = 'text = '+ item+'.'+key +' ;' ;
 	  						eval(exp);
 	  						
 	  						if(isIe){
 	  							td.innerText=text;
 	  						}else{
 	  							td.textContent=text;
 	  						}
 	  						tr.appendChild(td);
 	  				}
 	  				
 	  				if(options.colOpt){
 	  					var optTd = document.createElement('TD');
 	  					optTd.innerHTML="<a href=#>删除</a>";
 	  					tr.appendChild(optTd);
 	  					$(optTd).find('a').click(function(){
 	  						var size = $(this).parent().parent().prevAll().size();
 	  						_delRow(size+1);
 	  						if(options.colNum){
 	  							_resetColNum();
 	  						}
 	  					})
 	  				}
 	  				
 	  				tbody.appendChild(tr);
 	  				
 	  				if(colNumTd){
 	  					$(colNumTd).addClass('sn')
 	  				}
 	  			}
 	  		}
 	  		if(models){
 	  			var widths = new Array;
 	  			for(var j in models){
 	  				var width = "10%";	
 	  				if(models[j].width){
 	  					width = models[j].width+"%";
 	  				}
 	  				widths.push(width);
 	  			}
 	  			
 	  		}
    }
    
    /**
     * Get/set a plugin option.
     * Get usage: $('#el').demoplugin('option', 'key');
     * Set usage: $('#el').demoplugin('option', 'key', value);
     */
    function option (key, val) {
      if (val) {
        options[key] = val;
      } else {
        return options[key];
      }
    }
 
    /**
     * Destroy plugin.
     * Usage: $('#el').demoplugin('destroy');
     */
    function destroy() {
      // Iterate over each matching element.
    
      $el.each(function() {
        var el = this;
        var $el = $(this);
 
        // Add code to restore the element to its original state...
 
        hook('onDestroy');
        // Remove Plugin instance from the element.
        $el.removeData('plugin_' + pluginName);
        $el.removeData('selData');
        var id = $el.attr("id");
        $('#div_'+id).remove();
        $('#select_'+id).remove();
      });
    }
 
    /**
     * Callback hooks.
     * Usage: In the defaults object specify a callback function:
     * hookName: function() {}
     * Then somewhere in the plugin trigger the callback:
     * hook('hookName');
     */
    function hook(hookName) {
      if (options[hookName] !== undefined) {
        // Call the user defined function.
        // Scope is set to the jQuery element we are operating on.
        options[hookName].call(el);
      }
      
      
    }
 
 	
    // Initialize the plugin instance.
    init();
 	
    // Expose methods of Plugin we wish to be public.
    return {
      option: option,
      destroy: destroy,
      addRows:_addRows
    };
  }
 
  /**
   * Plugin definition.
   */
  $.fn[pluginName] = function(options) {
    // If the first parameter is a string, treat this as a call to
    // a public method.
    if (typeof arguments[0] === 'string') {
      var methodName = arguments[0];
      var args = Array.prototype.slice.call(arguments, 1);
      var returnVal;
      this.each(function() {
        // Check that the element has a plugin instance, and that
        // the requested public method exists.
        if ($.data(this, 'plugin_' + pluginName) && typeof $.data(this, 'plugin_' + pluginName)[methodName] === 'function') {
          // Call the method of the Plugin instance, and Pass it
          // the supplied arguments.
          returnVal = $.data(this, 'plugin_' + pluginName)[methodName].apply(this, args);
        } else {
          throw new Error('Method ' +  methodName + ' does not exist on jQuery.' + pluginName);
        }
      });
      if (returnVal !== undefined){
        // If the method returned a value, return the value.
        return returnVal;
      } else {
        // Otherwise, returning 'this' preserves chainability.
        return this;
      }
    // If the first parameter is an object (options), or was omitted,
    // instantiate a new instance of the plugin.
    } else if (typeof options === "object" || !options) {
      return this.each(function() {
        // Only allow the plugin to be instantiated once.
        if (!$.data(this, 'plugin_' + pluginName)) {
          // Pass options to Plugin constructor, and store Plugin
          // instance in the elements jQuery data object.
          $.data(this, 'plugin_' + pluginName, new Plugin(this, options));
        }
      });
    }
  };
 
  // Default plugin options.
  // Options can be overwritten when initializing plugin, by
  // passing an object literal, or after initialization:
  // $('#el').demoplugin('option', 'key', value);
  $.fn[pluginName].defaults = {
    onInit: function() {},
    onDestroy: function() {},
    colNum:true,
    filter:false,
    onDblclick:null,//node dblclick event,
    colOpt:true
  };
})(jQuery);