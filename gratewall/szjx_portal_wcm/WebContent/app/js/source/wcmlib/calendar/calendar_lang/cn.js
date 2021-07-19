//中文
// ** I18N

// Calendar EN language
// Author: Mihai Bazon, <mishoo@infoiasi.ro>
// Encoding: any
// Translator : Niko <nikoused@gmail.com>
// Distributed under the same terms as the calendar itself.

// For translators: please use UTF-8 if possible.  We strongly believe that
// Unicode is the answer to a real internationalized world.  Also please
// include your contact information in the header, as can be seen above.

// full day names
Calendar._DN = new Array
("日",//星期日
 "一",//星期一
 "二",//星期二
 "三",//星期三
 "四",//星期四
 "五",//星期五
 "六",//星期六
 "日");//星期日

// Please note that the following array of short day names (and the same goes
// for short month names, _SMN) isn't absolutely necessary.  We give it here
// for exemplification on how one can customize the short day names, but if
// they are simply the first N letters of the full name you can simply say:
//
//   Calendar._SDN_len = N; // short day name length
//   Calendar._SMN_len = N; // short month name length
//
// If N = 3 then this is not needed either since we assume a value of 3 if not
// present, to be compatible with translation files that were written before
// this feature.

// short day names
/*
Calendar._SDN = new Array
("星期日",
 "星期一",
 "星期二",
 "星期三",
 "星期四",
 "星期五",
 "星期六",
 "星期日");*/

Calendar._SDN = new Array
("日",
 "一",
 "二",
 "三",
 "四",
 "五",
 "六",
 "日");

// full month names
Calendar._MN = new Array
("一月",
 "二月",
 "三月",
 "四月",
 "五月",
 "六月",
 "七月",
 "八月",
 "九月",
 "十月",
 "十一月",
 "十二月");

// short month names
Calendar._SMN = new Array
("一月",
 "二月",
 "三月",
 "四月",
 "五月",
 "六月",
 "七月",
 "八月",
 "九月",
 "十月",
 "十一月",
 "十二月");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "关于";

Calendar._TT["ABOUT"] =
"   DHTML 日起/时间选择控件\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this this ;-)
"For latest version visit: 最新版本请登陆http://www.dynarch.com/projects/calendar/察看\n" +
"遵循GNU LGPL.  细节参阅 http://gnu.org/licenses/lgpl.html" +
"\n\n" +
"日期选择:\n" +
"- 点击\xab(\xbb)按钮选择上(下)一年度.\n" +
"- 点击" + String.fromCharCode(0x2039) + "(" + String.fromCharCode(0x203a) + ")按钮选择上(下)个月份.\n" +
"- 长时间按着按钮将出现更多选择项.";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"时间选择:\n" +
"-在时间部分(分或者秒)上单击鼠标左键来增加当前时间部分(分或者秒)\n" +
"-在时间部分(分或者秒)上按住Shift键后单击鼠标左键来减少当前时间部分(分或者秒).";

Calendar._TT["PREV_YEAR"] = "上一年"; 
Calendar._TT["PREV_MONTH"]= "上个月";
Calendar._TT["GO_TODAY"] = "到今天";
Calendar._TT["NEXT_MONTH"] = "下个月";
Calendar._TT["NEXT_YEAR"] = "下一年";
Calendar._TT["SEL_DATE"] = "选择日期";
Calendar._TT["DRAG_TO_MOVE"] = "拖动";
Calendar._TT["PART_TODAY"] = " (今天)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "%s为这周的第一天";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "关闭";
Calendar._TT["TODAY"] = "今天";
Calendar._TT["TIME_PART"] = "(按着Shift键)单击或拖动改变值";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%Y-%m-%d";
Calendar._TT["TT_DATE_FORMAT"] = "%a, %b %e日";

Calendar._TT["WK"] = "周";
Calendar._TT["TIME"] = "时间:";

Calendar.LANG= {};
Calendar.LANG["NOT_FOUND_DATAEL"] = "没有找到存储日期值得元素，操作失败！";
Calendar.LANG["YEAR_AREA"] = "[年] 必须在 1900-2070 之间！";
Calendar.LANG["MONTH_AREA"] = "[月] 必须在 1-12 之间！";
Calendar.LANG["DAY_AREA"] = "[日] 必须在 1-31 之间！";
Calendar.LANG["SENCOND_MONTH_LARGEDAY"] = "闰年的二月最多只有29天！";
Calendar.LANG["MONTH_LARGEDAY"] = "月份最多只有30天！";
Calendar.LANG["HOUR_AREA"] = "[时] 必须在 0-23 之间！";
Calendar.LANG["MINUTE_AREA"] = "[分] 必须在 0-59 之间！";
Calendar.LANG["SECOND_AREA"] = "[秒] 必须在 0-59 之间！";
Calendar.LANG["DATA_FORMAT_ERROR"] = "您输入的日期不匹配指定的日期格式! \n 一个正确的日期格式应当为：";
Calendar.LANG["NOT_NULL"] = "时间不能为空!";
