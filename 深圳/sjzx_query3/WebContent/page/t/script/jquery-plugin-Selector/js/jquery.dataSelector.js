/**
 * A jQuery plugin boilerplate.
 * jquery.dataSelect.js
 * jufeng@gwssi.com.cn 2013.3.13
 */

// fix IE option hidden problem
$.fn.showOption = function() {
    this.each(function() {
      var opt = $(this).find('option').show();
   	  $(this).replaceWith(opt);
    });
}
$.fn.hideOption = function() {
    this.each(function() {
		$(this).wrap('<span>').hide();
    });
}
//end

;(function($) {
  // Change this to your plugin name.
  var pluginName = 'dataSelector';
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
 
    /**
     * Initialize plugin.
     */
    function init() {
      
      //bind options' array data , id=selData
      $el.empty();
      $el.data('selData', options.data);
      var id = $el.attr("id");
      
      if(options.filter){
      	 var filter = document.createElement('input');
     	 element.appendChild(filter);
     	 $(filter).keyup(function(){
     	 	_filter($(this).val());
     	 	//not finished
     	 	//console.log("keyup"); 
     	 })
      }
      var sel = element.appendChild(document.createElement('select'));
      sel.setAttribute("id", id+"_id");
      var $select = $(sel);
      
      if(!options.multiple){
                 $select.removeAttr('multiple');
      }else{//ie6
      	if(isIe&&$.browser.version.substr(0,1)<7 ){
          setTimeout(function(){sel.multiple=true;},1000)
      	}else{
          $select.attr('multiple','multiple');
      	}
      }
      if (!options.className) {
	  	if (options.size > 1) {
	  		$select.attr('size', options.size).addClass("cssSelect");
	  	}
	  	else {
	  		$select.attr('size', options.size).addClass("cssSelect1");
	  	}
	  }else{
	  	$select.attr('size', options.size).addClass(options.className);
	  }
      
      if(options.data&&options.data.length>0){
      		for(var i=0 ; i<options.data.length; i++){
            	$select.append("<option class='"+id+options.data[i].key+"' id='"+options.data[i].key+"' value='"+options.data[i].key+"' title='"+options.data[i].title+"' index='"+i+"' >"+options.data[i].title+"</option>" );
            }
       }
       
      if(options.size>1){
      	_bindDblclick($select);
      	_bindClick($select);
      } else {
      	_bindOnChange($select);
      }
 	  
      hook('onInit');
    }
    
    function _bindDblclick($select){
    	if(options.onDblclick){
    		
	      	if(isIe){
	      		//ie need to bind dblclick event to select element
	      		$select.unbind('dblclick');
	      		$select.on('dblclick', function(event){
				    event.preventDefault();
				    options.onDblclick.call($(this),event, _getSelectedNodes()[0]);                         
				});
	      		
	      	}else{
	      		
	      		$select.children().unbind("dblclick");
	      		$select.children().on('dblclick', function(event){
				    event.preventDefault();
				    options.onDblclick.call($(this),event,$(this).attr("id"));                         
				});
	      	} 	
      } 
    }
    
    function _bindClick($select){
    	if(options.onClick){
	      	if(isIe){
	      		//ie need to bind dblclick event to select element
	      		$select.unbind('click');
	      		$select.on('click', function(event){
				    event.preventDefault();
				    options.onClick.call($(this),event, _getSelectedNodes()[0]);                         
				});
	      	}else{
	      		$select.children().unbind("click");
	      		$select.children().on('click', function(event){
				    event.preventDefault();
				    options.onClick.call($(this),event,$(this).attr("id"));                         
				});
	      	} 	
      } 
    }
    
    function _bindOnChange($select){
    	if(options.onChange){
    		if(isIe){
    			$select.unbind('change');
    			$select.on('change', function(event) {
    			 	event.preventDefault();
				    options.onChange.call($(this),event,_getSelectedNodes()[0]);   
  					//alert( this.value ); // or $(this).val()
				});
    		}else{
    			//$select.unbind('change');
    			$select.on('change', function(event) {
    			 	event.preventDefault();
				    options.onChange.call($(this),event,_getSelectedNodes()[0].id);   
  					//alert( this.value ); // or $(this).val()
				});
    		}
    	}
    }

    /**
     * get all selected nodes 
	 * return all selected nodes array
     */
    function _getSelectedNodeArray() {
      // Code goes here...
      var selData = $el.data('selData');
      var nodes = new Array;
      if(isIe){
    		$el.find('select').children().each(function(){
    			if( $(this).attr("selected") =="selected"){
    				nodes.push( selData[$(this).attr('index')])
    			}
    		})
      }else{
	      $el.find('option:selected').each(function(){
	      	nodes.push( selData[$(this).attr('index')])
	      });
      }
      return nodes ;
    }
    
    /*
     * get all selected nodes 
	 * return all selected nodes as jQuery Object
     */
    function _getSelectedNodes(){
    	if(isIe){
    		var indexs = new Array;
    		$el.find('select').children().each(function(){
    			if( $(this).attr("selected") =="selected"){
    				//push option's id in indexs array
    				indexs.push($(this).attr("id")); 
    			}
    		});
    		return indexs;
    	}else{
    		return  $el.find('select option:selected'); 
    	}
    }
	
	/*
     * get all selected nodes 
	 * return all selected nodes as jQuery Object
     */
    function _getSelectedObject(){
    	if(isIe){
    		var indexs = new Array;
    		$el.find('select').children().each(function(){
    			if( $(this).attr("selected") =="selected"){
    				//push option's id in indexs array
    				indexs.push($(this)); 
    			}
    		});
    		return indexs;
    	}else{
    		return  $el.find('select option:selected:visible'); 
    	}
    }
    
    /**
     * get all nodes 
	 * return all nodes array
     */
    function _getAllNodeArray() {
      	// Code goes here...
		var selData = $el.data('selData');
      	var nodes = new Array;
      	if(isIe){
     		$el.find('select').children().each(function(){
    		 	nodes.push( selData[$(this).attr('index')])
     	}) } else {
      		$el.find('option').each(function(){
      			nodes.push( selData[$(this).attr('index')])
      		});
      	}
      	return nodes ;
    }
    
	/*
    function _removeNodes(nodes){
    	if(isIe){
			var indexa = new Array;
			for(var j=0; j<nodes.length; j++){
				var e = document.getElementById(nodes[j]);
				indexa.push( $(e).attr('index') );
				e.parentNode.removeChild(e);
			}
			var removedNodes = _removeDataFromSelData(indexa);
			
			_resetOptionsIndex();
			
	    	return removedNodes;
			
    	}
    }*/
    
    function _removeSelectedNodes(){
    	if(isIe){
    		var nodes = _getSelectedObject();
			var indexa = new Array;
			
			for(var j=0; j<nodes.length; j++){
				var e = $el.find("."+nodes[j].attr('class'));
				indexa.push( e.attr('index') );
				e.remove();
			}
			var removedNodes = _removeDataFromSelData(indexa);
			
			_resetOptionsIndex();
			
	    	return removedNodes;
			
    	}else{
    		var $selectedNodes = _getSelectedNodes();
	    	var indexa = new Array;
	    	$selectedNodes.each(function(){
	    		indexa.push($(this).attr('index'));
	    	})
	    	
	    	var removedNodes = _removeDataFromSelData(indexa);
	
	    	$selectedNodes.remove();
	    	
	    	_resetOptionsIndex();
	    	
	    	return removedNodes;
    	}
    }
	
	function _removeNodes(key){
		var nodeId=$(this).attr('id')+key;
    	if(isIe){
			var indexa = new Array;
			var e = $("."+nodeId);
			indexa.push( $(e).attr('index') );
			if(e[0] && e[0].parentNode)
			e[0].parentNode.removeChild(e[0]);
			
			var removedNodes = _removeDataFromSelData(indexa);
			_resetOptionsIndex();
			
	    	return removedNodes;
			
    	}else{
    		var $selectedNodes = _getSelectedNodes();
	    	var indexa = new Array;
	    	$selectedNodes.each(function(){
	    		indexa.push($(this).attr('index'));
	    	})
	    	
	    	var removedNodes = _removeDataFromSelData(indexa);
	
	    	$selectedNodes.remove();
	    	
	    	_resetOptionsIndex();
	    	
	    	return removedNodes;
    	}
    }
    
    function _selectAllBlockNodes(){
    	if(isIe){
    		$el.find('select>option').each(function(){
    			$(this).attr("selected", "selected");
    		})
    	}else{
    		 $el.find('select option:visible').prop('selected', true);; 
    	}
    }
    
    function _removeDataFromSelData(indexa){
    	var selData = $el.data('selData');
    	var b = new Array;
    	var a = new Array;
    	if(indexa.length>0){
    		var filterStr = "-"+indexa.join("-")+"-";
    		for (var j=0;j<selData.length;j++){
    			if(filterStr.indexOf( '-'+j.toString()+'-' )<0 ){
    				b.push(selData[j]);
    			} else {
    				a.push(selData[j]);
    			}
    		}
    		$el.data('selData', b);
    	}
    	return a;
    }
    
    function _resetOptionsIndex(){
    	$el.find('option').each(function(index, value){
    		$(this).attr('index',index);
    	})
    }
    
    function _filter(val){
    	_showAllNodes();
    	if(val!=""){
    		var selData = $el.data('selData');
	    	var matcher = new RegExp( _escapeRegex(val), "i" );
			var b =  $.grep( selData, function(n,i) {
				return matcher.test( n.title );
			});
			
			if(b.length>0){
				$el.find('option').each(function(){
					var key = $(this).val();
					for(var j in b){
						if(key==b[j].key){
							$(this).show();
						}else{
							$(this).hide();
						}
					}
				})
			}else{
				$el.find('option').hide()
			}
    	}
    }
    
    function _escapeRegex( value ) {
		return value.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
	}
	
	function _hideSelectedNodes(){
		if(isIe){
			var nodes = _getSelectedNodes();
		
			for(var j in nodes){
				var e = document.getElementById(nodes[j]);
				$(e).hideOption();
			}
			
		}else{
			$selectedNode = _getSelectedNodes();
			$selectedNode.hide();
		}
	}
 
 
	function _showAllNodes(){
		if(isIe){
			$el.find('select span').showOption();
		}else{
			$el.find('option').show();
		}
	}
	
	function _addNodes(nodes){
		if(nodes.length>0){
			var selData = $el.data('selData');
			var id = $el.attr("id");
			var a = new Array;
			for(var j in selData){
				a.push(selData[j].key);
			}
			var filterStr = "-"+a.join("-")+"-";
			var $select = $el.find("select");
			var n = $select.find('option').size();
			for(var j=0;j<nodes.length;j++){
				if(filterStr.indexOf(nodes[j].key)<0){
					if(typeof(nodes[j]) != 'function'){
						selData.push(nodes[j]);
						$select.append("<option class='"+id+nodes[j].key+"' id='"+nodes[j].key+"' value='"+nodes[j].key+"' index='"+(n++)+"' >"+nodes[j].title+"</option>" );
					}
				}else{
					alert("key is "+nodes[j].title +" has already in nodes!");
				}
			} 
			$el.data('selData',selData);
			
			_bindDblclick($select);
		}
	}
	
	function _removeNodeById(id){
		var indexa = new Array;
		var e = $el.find('#'+id);
		//if(!e)
		//return;
		
		//alert($(e).text());
		indexa.push( $(e).attr('index') );
		e.remove();
		//e.parentNode.removeChild(e);
		var removedNodes = _removeDataFromSelData(indexa);
		_resetOptionsIndex();
	    return removedNodes;
		
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
      getSelectedNodes:_getSelectedNodeArray,
      hideSelectedNodes: _hideSelectedNodes,
      removeSelectedNodes:_removeSelectedNodes,
      removeNodes:_removeNodes,
      getAllNodes:_getAllNodeArray,
      showAllNodes:_showAllNodes,
      addNodes:_addNodes,
      removeNodeById:_removeNodeById,
      selectAllBlockNodes:_selectAllBlockNodes
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
    multiple:true,
    size:10,
    filter:false,
    onDblclick:null,//node dblclick event
    onChange:null,//node onchange event
    onClick:null //node onclick event
  };
})(jQuery);