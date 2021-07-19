/*
  language.js
  Copyright (c) 2002 Ma Bingyao. All rights reserved.
  Version 1.0.20021209
*/

lang = new Object();

lang["en"] = new Object();
lang["en"]["@@{color}"] = "Color";
lang["en"]["@@{palette}"] = "Palette";
lang["en"]["@@{16colors}"] = "16 colors(W3C)";
lang["en"]["@@{140colors}"] = "140 named colors";
lang["en"]["@@{216colors}"] = "216 safe colors";
lang["en"]["@@{syscolors}"] = "system colors";
lang["en"]["@@{custompalette}"] = "Custom Palette";
lang["en"]["@@{add}"] = "add";
lang["en"]["@@{remove}"] = "remove";
lang["en"]["@@{sort}"] = "sort";
lang["en"]["@@{reverse}"] = "reverse";
lang["en"]["@@{selectedcolor}"] = "Selected Color";
lang["en"]["@@{exact}"] = "Exact";
lang["en"]["@@{safe}"] = "Safe";
lang["en"]["@@{more}"] = "More Colors >>";
lang["en"]["@@{ok}"] = "ok";
lang["en"]["@@{cancel}"] = "cancel";
lang["en"]["@@{value}"] = "Value";
lang["en"]["@@{name}"] = "Name";
lang["en"]["@@{model}"] = " Model";
lang["en"]["@@{red}"] = "Red";
lang["en"]["@@{green}"] = "Green";
lang["en"]["@@{blue}"] = "Blue";
lang["en"]["@@{hue}"] = "Hue";
lang["en"]["@@{saturation}"] = "Saturation";
lang["en"]["@@{brightness}"] = "Brightness";
lang["en"]["@@{browserversionerror}"] = "This dialogbox is only available as of Microsoft Internet Explorer 5.0.\nPlease update your browser before you open this dialog.";
lang["en"]["@@{openerror}"] = "This page is a dialogbox, please use showModalDialog or showModelessDialog method to open it.";

lang["zh-cn"] = new Object();
lang["zh-cn"]["@@{color}"] = "颜色";
lang["zh-cn"]["@@{palette}"] = "调色板";
lang["zh-cn"]["@@{16colors}"] = "16色（W3C）";
lang["zh-cn"]["@@{140colors}"] = "140种命名色";
lang["zh-cn"]["@@{216colors}"] = "216种安全色";
lang["zh-cn"]["@@{syscolors}"] = "系统颜色";
lang["zh-cn"]["@@{custompalette}"] = "自定义调色板";
lang["zh-cn"]["@@{add}"] = "添加";
lang["zh-cn"]["@@{remove}"] = "删除";
lang["zh-cn"]["@@{sort}"] = "排序";
lang["zh-cn"]["@@{reverse}"] = "翻转";
lang["zh-cn"]["@@{selectedcolor}"] = "所选色";
lang["zh-cn"]["@@{exact}"] = "准确色";
lang["zh-cn"]["@@{safe}"] = "安全色";
lang["zh-cn"]["@@{more}"] = "更多的颜色 >>";
lang["zh-cn"]["@@{ok}"] = "确定";
lang["zh-cn"]["@@{cancel}"] = "取消";
lang["zh-cn"]["@@{value}"] = "颜色值";
lang["zh-cn"]["@@{name}"] = "名称";
lang["zh-cn"]["@@{model}"] = "颜色模型";
lang["zh-cn"]["@@{red}"] = "红色分量";
lang["zh-cn"]["@@{green}"] = "绿色分量";
lang["zh-cn"]["@@{blue}"] = "蓝色分量";
lang["zh-cn"]["@@{hue}"] = "色相";
lang["zh-cn"]["@@{saturation}"] = "饱和度";
lang["zh-cn"]["@@{brightness}"] = "亮度";
lang["zh-cn"]["@@{browserversionerror}"] = "此对话框只适用于 Microsoft Internet Explorer 5.0 及其更高版本浏览器。\n请升级您的浏览器后再打开此对话框。";
lang["zh-cn"]["@@{openerror}"] = "此页为对话框，请用showModalDialog或showModelessDialog方法打开。";

function getlocal(langcode, str) {
  return str.replace(str.match(/(@@{\w+})/g), lang[langcode][str.match(/(@@{\w+})/g)]);
}

function setlocal(langcode, items, attribute) {
  for(i = 0; i < items.length; i++) eval("items[i]." + attribute + " = getlocal(langcode, items[i]." + attribute + ");");
}

function setlanguage(langcode) {
  document.title = getlocal(langcode, document.title);
  setlocal(langcode, document.getElementsByTagName("legend"), "innerText");
  setlocal(langcode, document.getElementsByTagName("label"), "innerText");
  setlocal(langcode, document.getElementsByTagName("option"), "text");
  setlocal(langcode, document.getElementsByTagName("input"), "value");
}

var langcode = navigator.userLanguage;
if (langcode != "zh-cn") langcode = "en";
setlanguage(langcode);
