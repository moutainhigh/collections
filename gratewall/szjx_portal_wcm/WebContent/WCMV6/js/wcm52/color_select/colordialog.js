//中文
/*
  colordialog.js
  Copyright (c) 2002 Ma Bingyao. All rights reserved.
  Version 2.0.20021209
*/

var defaultMaxWidth = 546;
var defaultMinWidth = 266;
var defaultHeight = 440;

dialogWidth = "272px";
dialogHeight = "465px";


function colorDialog(Arguments) {

  var TimeoutID = null;

  function palette.onchange() {
    colorselector.palette = this.value;
  }

  function colorselector.onchange() {
    colorshower.value = this.value;
    customcolor.value = this.value;
    colorvalue.value = this.value;
    colorname.value = this.name;
    if (this.value != null && this.value.substr(0, 1) == "#") colorpicker.value = this.value;
  }

  function customcolor.onchange() {
    colorselector.value = this.value;
  }

  function addbutton.onclick() {
    customcolor.add(customcolor.value);
  }

  function removebutton.onclick() {
    customcolor.remove();
  }

  function sortbutton.onclick() {
    customcolor.sort();
  }

  function reversebutton.onclick() {
    customcolor.reverse();
  }

  function morebutton.onclick() {
    dialogWidth = parseInt(dialogWidth) - document.body.clientWidth + defaultMaxWidth + "px";
    this.disabled = true;
  }

  function okbutton.onclick() {
    Arguments.value = returnValue = (isgetname.checked && colorname.value) ? colorname.value : ((safecolorradio.checked) ? colorshower.safecolor : colorvalue.value);
    close();
  }

  function cancelbutton.onclick() {
    Arguments.value = returnValue = "";
    close();
  }

  function colorpicker.onchange() {
    red.value = this.red;
    green.value = this.green;
    blue.value = this.blue;
    hue.value = this.hue;
    saturation.value = parseInt(this.saturation * 100);
    brightness.value = parseInt(this.brightness * 100);
    if (TimeoutID != null) clearTimeout(TimeoutID);
    TimeoutID = setTimeout("colorselector.value = colorpicker.value", 1);
  }

  function colorvalue.onblur() {
    colorselector.value = this.value;
    this.value = colorselector.value;
  }

  function colorname.onblur() {
    colorselector.value = this.value;
    this.value = colorselector.name;
  }


  function red.onchange() {
    colorpicker.red = this.value;
  }

  function green.onchange() {
    colorpicker.green = this.value;
  }

  function blue.onchange() {
    colorpicker.blue = this.value;
  }

  function hue.onchange() {
    colorpicker.hue = this.value;
  }

  function saturation.onchange() {
    colorpicker.saturation = this.value / 100;
  }

  function brightness.onchange() {
    colorpicker.brightness = this.value / 100;
  }


  colorselector.value = Arguments.value;
  dialogWidth = parseInt(dialogWidth) - document.body.clientWidth + defaultMinWidth + "px";
  dialogHeight = parseInt(dialogHeight) - document.body.clientHeight + defaultHeight + "px";

}

function window.onload() {
  if (parseFloat(navigator.appVersion.match(/(MSIE [\d.]+)/g).toString().split(" ")[1]) < 5) {
    alert(lang[langcode]["@@{browserversionerror}"]);
    close();
  }
  if (typeof(dialogArguments) != "undefined") new colorDialog(dialogArguments);
  else alert(lang[langcode]["@@{openerror}"]);
}