

/* copy对象 */
Object.prototype._clone = function()
{
	if(typeof(this) != "object"){
		return this;
	}
	
	var cloneDepth = ((arguments.length >= 1)?((isNaN(parseInt(arguments[0])))?(null):parseInt(arguments[0])):null);
	if (cloneDepth){
		cloneDepth=((cloneDepth <= 0)?(null):(cloneDepth));
	}
	
	var cloneObject = null;
	var thisConstructor = this.constructor;
	var thisConstructorPrototype = thisConstructor.prototype;
	
	if (thisConstructor == Array){
		cloneObject = new Array();
	} 
	else if(thisConstructor == Object){
		cloneObject = new Object();
	} 
	else {
		try{
			cloneObject = new thisConstructor;
		} 
		catch(exception) {
			cloneObject = new Object();
			cloneObject.constructor = thisConstructor;
			cloneObject.prototype = thisConstructorPrototype;
		}
	}
	
	var propertyName = "";
	var newObject=null;
	
	for (propertyName in this){
		newObject = this[propertyName];
		if (!thisConstructorPrototype[propertyName]){
			if (typeof(newObject)=="object"){
				if (newObject === null){
					cloneObject[propertyName] = null;
				} 
				else {
					if(cloneDepth){
						if(cloneDepth == 1){
							cloneObject[propertyName] = null;
						} 
						else {
							cloneObject[propertyName] = newObject._clone(--cloneDepth);
						}
					} 
					else {
						cloneObject[propertyName] = newObject._clone();
					}
				}
			} 
			else {
				cloneObject[propertyName] = newObject;
			}
		}
	}

	return cloneObject;
};



	this.cookieEnabled = false;
	// check cookie
	this.cookieEnabled = (this.getCookie("JSESSIONID") != null);
	
	
	this.cssRules = new Array();
	this.addRule = fz_css_addRule;
	this.createCssStyle = fz_createCssStyle;
function fz_css_addRule( selector, style )
{
	var rl = new Array( );
	rl[0] = selector;
	rl[1] = style;
	
	this.cssRules[this.cssRules.length] = rl;
}

function fz_createCssStyle()
{
	if(this.DOMable) {
		if(this.browserType == "IE") {
			document.createStyleSheet();
			var newStyleSheet = document.styleSheets(document.styleSheets.length-1);
		}
		else if(this.browserType == "NN") {
			var newStyleSheet = document.getElementById('mtmsheet');
			if(newStyleSheet) {
				newStyleSheet.disabled = false;
			}
		}
	}
	else {
		var outputHTML = '<style type="text/css">\n';
	}
	
	for(i = 0; i < this.cssRules.length; i++) {
		if(this.DOMable && this.browserType == "IE") {
			newStyleSheet.addRule(this.cssRules[i][0], this.cssRules[i][1]);
		}
		else if(this.DOMable && this.browserType == "NN" && newStyleSheet) {
			newStyleSheet.sheet.insertRule((this.cssRules[i][0] + " { " + this.cssRules[i][1] + " } "), newStyleSheet.sheet.cssRules.length);
		}
		else {
			outputHTML += this.cssRules[i][0] + ' {\n' + this.cssRules[i][1] + '\n}\n';
		}
	}
	
	if(!this.DOMable) {
		document.writeln(outputHTML + '</style>');
	}
}


	this.disableStyleSheets = fz_disableStyleSheets;

// 禁止样式单
function fz_disableStyleSheets()
{
	if( _browse.browserType == "IE" ) {
		for(i = 0; i < document.styleSheets.length; i++) {
			document.styleSheets(i).disabled = true;
		}
	}
	else if( _browse.browserType == "NN" ) {
		var myCollection = document.getElementsByTagName('style');
		for(i = 0; i < myCollection.length; i++) {
			myCollection.item(i).disabled = true;
		}
		
		var myCollection = document.getElementsByTagName('link');
		for(i = 0; i < myCollection.length; i++) {
			if(myCollection.item(i).getAttribute('type') == "text/css") {
				myCollection.item(i).disabled = true;
			}
		}
	}
}
