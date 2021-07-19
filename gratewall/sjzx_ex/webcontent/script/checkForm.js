/**
*编写人：lifx
*html校验
*$id$
*/

/**
 * 功能： 校验是各种类型文本输入，如果输入的信息不正确，会自动提示错误信息 返回false。正常输入返回true
 * 输入： obj 欲判断的Object,mc 是对象名称，lx是对象类型(1数字，2字符串，3日期),zcd总长度,xsw小数位数
 * 输出： 如果是校验没问题 返回true, 否则 返回false
 */
function checkInput(obj,mc,lx,wkbz,zcd,xsw)
{
    obj.value = obj.value;
    if (obj.disabled){
        return true;
    }
    //判断为空标志
    if (obj == null || obj.value == null)
    {
        alert(mc+"对象没有找到,发生异常!");
        return false;
    }
    if (wkbz && obj.value==""){
        return true;
    }else if(obj.value==""){
        alert(mc+"不可以为空！");
        obj.select();
        return false; //默认为0
    }

    //校验类型
    if (lx == 1)
    {
        if (isNaN(obj.value))
      {
         alert(mc+"必须输入数字!");
         obj.select();
         return false;
      }

    }else if(lx == 3){
      if (!checkDateBox(obj.value))
      {
          alert(mc+"正确的日期格式(yyyy-mm-dd)!");
          obj.select();
          return false;
      }
    }

    //判断长度
    if (lx != 3)
    {
       if (getValueLength(obj.value) > zcd)
       {
           alert(mc+"长度不能超出"+zcd+"个字数范围!");
           obj.select();
           return false;
       }
       if (lx == 1)
       {
          if (obj.value.indexOf(".")==0)
          {
              alert(mc+"必须输入数字!");
              obj.select();
              return false;
          }
          var xsws = obj.value.indexOf(".")==-1 ? -1 : parseInt(obj.value.length)-(obj.value.indexOf(".")+1);
          if (xsw == 0 && xsws >= xsw)
          {
             alert(mc+"必须输入整数!");
             obj.select();
             return false;
          }else if (xsws == 0)
          {
             alert(mc+"未输入完整!");
             obj.select();
             return false;
          }else if (xsws > xsw)
          {
            alert(mc+"小数不能超出"+xsw+"个小数位!");
            obj.select();
            return false;
          }
       }
    }
    return true;
}

//**判断是否是日期 yearObj是日期对象 ，monthObj 是时间对象 dayObj 是天对象
function isDate(yearObj,monthObj,dayObj) {
	if ((Math.abs(Math.abs(monthObj.value*2-15)-5)==2&&(dayObj.value==31))||
   	      (monthObj.value==2&&((dayObj.value>28&&parseInt(yearObj.value)%4!=0)||(dayObj.value>29&&parseInt(yearObj.value)%4==0)))){
	   alert("请您填写正确的日期，注意闰年和大小月！");
	   yearObj.focus()
	   return false;
	}
	return true;
}

//判断是否是正规Email格式
function isEMail(obj){
	var secmail=obj.value;
	if (secmail.length != 0){
		if( secmail.length<8 || secmail.length>64 || !validateEmail(secmail) ) {
			alert("\请您输入正确的邮箱地址 !!")
			obj.focus()
			return false;
		}
	}
	return true;
}

function isLoginNameAndPasswd(obj,mc,minlength){
	var str = obj.value;
	if (getValueLength(str) < minlength){
		alert(mc+" 不能小于"+minlength+"位！仅可用字母、数字和汉字！");
		return false;
	}
	var len;
	var i;
	len = 0;
	for (i=0;i<str.length;i++){
		if (str.charCodeAt(i)<48 ||
		    (str.charCodeAt(i)>57 && str.charCodeAt(i)<65) ||
		    (str.charCodeAt(i)>90 && str.charCodeAt(i)<97) ||
		    (str.charCodeAt(i)>122 && str.charCodeAt(i)<'128')){
			alert(mc+" 不能包含非法字符，仅可用字母、数字和汉字！");
			return false;
		}
	}
	return true;
}

/**
*校验对象是只能选择一个 成立返回true,不成立返回false
*/
function checkSelectOne(obj){
  if (obj.length == null)
  {
     if (!obj.checked)
     {
         alert("请选择一项!");
         return false;
     }
  }else{
     var counts = 0;
     for (i = 0 ; i < obj.length ; i++)
     {
         if (obj[i].checked)
         {
            counts++;
         }
     }
     if (counts < 1)
     {
         alert("请选择一项!");
         return false;
     }
     if (counts > 1)
     {
         alert("您只能选择一项!");
         return false;
     }
  }
  return true;
}


/**
*校验对象是至少选择一个 成立返回true,不成立返回false
*/
function checkLeastSelectOne(obj){
  if (obj.length == null)
  {
     if (!obj.checked)
     {
         alert("请至少选择一项!");
         return false;
     }
  }else{
     var counts = 0;
     for (i = 0 ; i < obj.length ; i++)
     {
         if (obj[i].checked)
         {
            counts++;
         }
     }
     if (counts < 1)
     {
         alert("请至少选择一项!");
         return false;
     }
  }
  return true;
}

//判断是否是路径 obj是判断对象,是obj的名称,dummy是啥忘了：（
function isPath(obj,mc,dummy){   
   //判断为空标志
   if (obj == null || obj.value == null)
   {
       alert(mc+"对象没有找到,发生异常!");
       return false;
   }
   if (dummy)
   {
       if (obj.value.indexOf('  ') != -1 || obj.value.indexOf(':') != -1 || obj.value.indexOf('/') != -1 || 
           obj.value.indexOf('\\') != -1 || obj.value.indexOf('*') != -1 || obj.value.indexOf('?') != -1 || 
           obj.value.indexOf('"') != -1 || obj.value.indexOf('<') != -1 || obj.value.indexOf('>') != -1 || 
           obj.value.indexOf('|') != -1){
           alert(mc+"不可以输入 “ TAB : / \\ * ? \" < > | ” 的字符!");
           return false; //默认为0
       }
   }else{
       if (obj.value.indexOf('  ') != -1 || obj.value.indexOf('\\') != -1 || obj.value.indexOf('*') != -1 || 
           obj.value.indexOf('?') != -1 || obj.value.indexOf('"') != -1 || obj.value.indexOf('<') != -1 || 
           obj.value.indexOf('>') != -1 || obj.value.indexOf('|') != -1){
           alert(mc+"不可以输入 “ TAB \\ * ? \" < > | ” 的字符!");
           return false; //默认为0
       }
   }
   return true;
}

function checkDateBox(srcStr)
{
    if (srcStr == '')
    {
        return true;
    }
    if (srcStr.length != 10) 
    {
        return false;
    }
    var year = srcStr.substring(0,4);
    var month = srcStr.substring(5,7);
    var day = srcStr.substring(8,10);
    var d_t1 = srcStr.substring(4,5);
    var d_t2 = srcStr.substring(7,8);    

    if (isNaN(day) || isNaN(month) || isNaN(year) || d_t1 != "-" || d_t2 != "-")
    {
        return false;
    }
    if (day > 31 || day < 1 || month > 12 )
    {
        return false;
    }
    if (month == 4 || month == 6 || month == 9 || month == 11)
    {
        if (day > 30)
        {
            return false;
        }    
    }
    if (month == 2)
    {
        if(year % 4 !=0 || (year % 400 != 0 && year % 100 == 0))
        {
            if(day > 28)
            {
                return false;
            }
        }
        else
        { 
            if( day > 29)
            {
                return false;
            }
        }
    }

    return true;    
}



function getValueLength(s)
{
    var sLength = 0;
    for (i = 0; i < s.length; i++)
    { 
        var c = s.charAt(i);
        if (c.charCodeAt(0) < 256)
            sLength += 1;
        else
            sLength += 2
    }
    return sLength;
}


function validateEmail(emailStr){
	var re=/^[\w-]+(\.*[\w-]+)*@([0-9a-z]+(([0-9a-z]*)|([0-9a-z-]*[0-9a-z]))+\.)+[a-z]{2,3}$/i;
	if(re.test(emailStr))
		return true;
	else
		return false;
}

function validateWebPath(strPath){
	var re=/^[0-9a-z]+(([0-9a-z]*)|([0-9a-z-]*[0-9a-z]))$/i;
	if(re.test(strPath))
		return true;
	else
		return false;
}


/**
*将传入name对象 控制点击隐藏还是显示。
*/
function showTree(name){
	if (name == null){
		return;
	}
	if (name.style.display==""){
		name.style.display="none";
	}else{
		name.style.display="";
	}
}

/**将传入的Obj 对象全部选择，如果是已选择状态，则取消所有选择。
*/
function checkSelected(parentObj,obj){
	var checkValue = parentObj.checked;
	if (obj != null){
		if (obj.length == null){
		   obj.checked = checkValue;
		}else{
			for (i = 0 ; i < obj.length ; i++){
			   obj[i].checked = checkValue;
			} 
		}
	}
}

/**
*打开新的选择窗口
*/
function showOpenWindow(url){
	window.open(url,"showOpenWindow","width=500px,height=450px,scrollbars=yes,resizable=yes,top=100px,left=250px");
}

/*****
*检查允许上传的文件类型
******/
function checkUpFile(varFilename){
    var regFilename= new RegExp("/|(\.swf$)|(\.asf$)|(\.asx$)|(\.rmvb$)|(\.rm$)|(\.mpg$)|(\.mpeg$)|(\.avi$)|(\.wav$)|(\.wmv$)|(\.wma$)|(\.jpg$)|(\.bmp$)|(\.gif$)|(\.txt$)|(\.doc$)|(\.mp3$)|(\.mid$)|/", "i");
    if ((varFilename != "") && (varFilename.search(regFilename)!= -1)){
        return true;
    }else {
       //alert("只能上传swf、asf、asx、rmvb、rm、mpg、mpeg、avi、wav、wmv、wma、jpg、bmp、gif、txt、doc、mp3、mid格式的文件！");
       return false;
    }
}
/*****
*检查允许上传的图片类型
******/
function checkUpImg(varFilename){
    var regFilename= new RegExp("/|(\.png$)|(\.jpg$)|(\.bmp$)|(\.gif$)|/", "i");
    if ((varFilename != "") && (varFilename.search(regFilename)!= -1)){
       return true;
    }else {
       //alert("只能上传jpg、bmp、gif、png等格式的图象文件！");
       return false;
    }
}
/*****
*检查允许上传的文件类型
******/
function checkUpVideo(varFilename){
    var regFilename= RegExp("/|(\.swf$)|(\.asf$)|(\.asx$)|(\.rmvb$)|(\.rm$)|(\.mpg$)|(\.mpeg$)|(\.avi$)|(\.wav$)|(\.wmv$)|(\.wma$)|(\.mp3$)|(\.mid$)|/", "i");
    if ((varFilename != "") && (varFilename.search(regFilename)!= -1)){
        return true;
    }else {
       //alert("只能上传swf、asf、asx、rmvb、rm、mpg、mpeg、avi、wav、wmv、wma、jpg、bmp、gif、txt、doc、mp3、mid格式的文件！");
       return false;
    }
}

/**
*检查是否以0-9 A-Z字母组合
*/
function isWeb(obj){
    var webpath=obj.value;
	if (webpath.length != 0){
		if( webpath.length<32 && !validateWebPath(webpath) ) {
			obj.focus();
			return false;
		}
	}
	return true;
}

/*
说明：区分汉字与字符的折行函数
作者：杨文彦
时间：2007-02-07
*/
function func_split(srcstr, offsetfixed, splitunit){
	var str = srcstr;
	//保存拆分结果
	var splitstr = "";
	//保存实际字符长度
	var len = 0;
	//保存上次读取位置
	var last = 0;
	//计算拆分次数
	var splittimes = 1;
	for (var i = 0; i < str.length; i++){
	  //判断是否汉字
	  if (str.charCodeAt(i) > 127){
	    len += 2;
	  }else{
	    len++;
	  }
	  if ((len / splitunit) >= splittimes && (i + 1) != str.length){
	    //如果当前长度除以固定长度的结果大于拆分次，表示需要进行本次拆分了，同时位置不能处于临界位置
	    splittimes++;
	    splitstr = splitstr + offsetfixed + str.substr(last, i - last) + "\n";
	    last = i;
	  }else{
	    //处理最后一次
	    if ((i + 1) == str.length){
	      if((len / splitunit) <= splittimes){
	        splitstr = splitstr + offsetfixed + str.substr(last, i - last + 1);
	      }else{
	        splitstr = splitstr + offsetfixed + str.substr(last, i - last) + "\n" + offsetfixed + str.substr(str.length - 1, 1);
	      }
	    }
	  }
	}
	return splitstr;
}