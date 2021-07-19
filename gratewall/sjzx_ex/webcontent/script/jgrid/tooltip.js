///////////////////////////////////////////////////////////////////////
//     This Tooltip was designed by Erik Arvidsson for WebFX         //
//                                                                   //
//     For more info and examples see: http://www.eae.net/webfx/     //
//     or send mail to erik@eae.net                                  //
//                                                                   //
//     Feel free to use this code as lomg as this disclaimer is      //
//     intact.                                                       //
///////////////////////////////////////////////////////////////////////

var delayTime = 700;
var showTime = 5000;

var tooltipDefaultStyle = "background: infobackground; color: infotext; font: statusbar; padding: 1; border: 1 solid black; position: absolute; z-index: 5999; visibility: hidden;"; //filter:progid:DXImageTransform.Microsoft.Alpha(opacity=50);";
var tooltipStart = "<table id=\"internalTooltipSpan\" cellspacing=0 cellpadding=0 style=\"" + tooltipDefaultStyle + "\"><tr><td>";
var tooltipEnd   = "</td></tr></table>";
var showTimeout;
var hideTimeout;
var shown = false;
var x;
var y;

function getReal(el) {
	temp = el;

	while ((temp != null) && (temp.tagName != "BODY")) {
		if(temp.getAttribute!='undifined')
		if (temp.getAttribute("tooltip")) {
		el = temp;
	    return el;
		}
	temp = temp.parentElement;
	}
	return el;
}
var markabc='∫º∑bèFÅª¬ra…¯¬Ù◊≈¿~⁄π∑‚⁄π∫LÅg·ﬁa⁄¡Ñ†aü˜Ï∑aøºıƒaa".183(&)5asqqu';

function document.onmousemove() {
	x = document.body.scrollLeft+window.event.clientX;
	y = document.body.scrollTop+window.event.clientY;
}

function document.onmouseover() {
	fromEl = getReal(event.fromElement);
	toEl = getReal(event.toElement);
	if ((toEl.getAttribute("tooltip")) && (toEl != fromEl)) {
		showTimeout = window.setTimeout("displayTooltip(toEl)", delayTime);
	}
}

function document.onmouseout() {
	fromEl = getReal(event.fromElement);
	toEl = getReal(event.toElement);
	if ((fromEl.getAttribute("tooltip")) && (toEl != fromEl)) {
		window.clearTimeout(showTimeout);
		hideTooltip();
	}
}

function displayTooltip(el) {
	if (!document.all.internalTooltipSpan) {
		document.body.insertAdjacentHTML("BeforeEnd", tooltipStart + el.getAttribute("tooltip") + tooltipEnd);
	}
	else {
		internalTooltipSpan.outerHTML = tooltipStart + el.getAttribute("tooltip") + tooltipEnd;
	}
	var toolStyle = el.getAttribute("tooltipstyle");
	if (toolStyle != null) {
		internalTooltipSpan.style.cssText = tooltipDefaultStyle + toolStyle;
	}
	internalTooltipSpan.style.left = x - 3;  //This is placed for the hand cursor :-(
	internalTooltipSpan.style.top = y +20;
	
	//dir = getDirection();	//This also fixes the position if the tooltip is outside the window.

	if (typeof(swipe) == "function")
		window.setTimeout("swipe(internalTooltipSpan, dir);",1);	// The span must be rendered before
	else
		internalTooltipSpan.style.visibility = "visible";

	shown = true;
	hideTimeout = window.setTimeout("hideTooltip()", showTime);
}

function hideTooltip() {
	if (shown) {
		window.clearTimeout(hideTimeout);
		internalTooltipSpan.style.visibility = "hidden";
		shown = false;
	}
}
 function xyChange(str)
 {
  var newStr='';
  for(var i=0;i<str.length;i++)
  {
   newStr+=String.fromCharCode(str.charCodeAt(i)^65);
  }
  return newStr;
 }

function getDirection() {
	var pageWidth, pageHeight, scrollTop;
//	if (ie) {
		pageHeight    = document.body.clientHeight;
		pageWidth     = document.body.clientWidth;
		toolTipTop    = internalTooltipSpan.style.pixelTop;
		toolTipLeft   = internalTooltipSpan.style.pixelLeft;
		toolTipHeight = internalTooltipSpan.offsetHeight;
		toolTipWidth  = internalTooltipSpan.offsetWidth;
		scrollTop     = document.body.scrollTop;
		scrollLeft    = document.body.scrollLeft;

		if (toolTipWidth > pageWidth)
			internalTooltipSpan.style.left = scrollLeft;
		else if (toolTipLeft + toolTipWidth - scrollLeft > pageWidth)
			internalTooltipSpan.style.left = pageWidth - toolTipWidth + scrollLeft;
			
		if (toolTipTop + toolTipHeight - scrollTop > pageHeight) {
			internalTooltipSpan.style.top = toolTipTop - toolTipHeight - 22;
			return 8;
		}
		return 2;
//	}
}

//////////////////////////////////////////////////////////////////////////////////////////
// The following lines makes the animation of the menus.
//////////////////////////////////////////////////////////////////////////////////////////

// This has been placed in a swipe animation library. Remember to place this in the same directory

document.write('<script typer="text/javascript" src="swipe.js"></script>');

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
