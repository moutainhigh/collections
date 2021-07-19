/**
*��д�ˣ�lifx
*htmlУ��
*$id$
*/

/**
 * ���ܣ� У���Ǹ��������ı����룬����������Ϣ����ȷ�����Զ���ʾ������Ϣ ����false���������뷵��true
 * ���룺 obj ���жϵ�Object,mc �Ƕ������ƣ�lx�Ƕ�������(1���֣�2�ַ�����3����),zcd�ܳ���,xswС��λ��
 * ����� �����У��û���� ����true, ���� ����false
 */
function checkInput(obj,mc,lx,wkbz,zcd,xsw)
{
    obj.value = obj.value;
    if (obj.disabled){
        return true;
    }
    //�ж�Ϊ�ձ�־
    if (obj == null || obj.value == null)
    {
        alert(mc+"����û���ҵ�,�����쳣!");
        return false;
    }
    if (wkbz && obj.value==""){
        return true;
    }else if(obj.value==""){
        alert(mc+"������Ϊ�գ�");
        obj.select();
        return false; //Ĭ��Ϊ0
    }

    //У������
    if (lx == 1)
    {
        if (isNaN(obj.value))
      {
         alert(mc+"������������!");
         obj.select();
         return false;
      }

    }else if(lx == 3){
      if (!checkDateBox(obj.value))
      {
          alert(mc+"��ȷ�����ڸ�ʽ(yyyy-mm-dd)!");
          obj.select();
          return false;
      }
    }

    //�жϳ���
    if (lx != 3)
    {
       if (getValueLength(obj.value) > zcd)
       {
           alert(mc+"���Ȳ��ܳ���"+zcd+"��������Χ!");
           obj.select();
           return false;
       }
       if (lx == 1)
       {
          if (obj.value.indexOf(".")==0)
          {
              alert(mc+"������������!");
              obj.select();
              return false;
          }
          var xsws = obj.value.indexOf(".")==-1 ? -1 : parseInt(obj.value.length)-(obj.value.indexOf(".")+1);
          if (xsw == 0 && xsws >= xsw)
          {
             alert(mc+"������������!");
             obj.select();
             return false;
          }else if (xsws == 0)
          {
             alert(mc+"δ��������!");
             obj.select();
             return false;
          }else if (xsws > xsw)
          {
            alert(mc+"С�����ܳ���"+xsw+"��С��λ!");
            obj.select();
            return false;
          }
       }
    }
    return true;
}

//**�ж��Ƿ������� yearObj�����ڶ��� ��monthObj ��ʱ����� dayObj �������
function isDate(yearObj,monthObj,dayObj) {
	if ((Math.abs(Math.abs(monthObj.value*2-15)-5)==2&&(dayObj.value==31))||
   	      (monthObj.value==2&&((dayObj.value>28&&parseInt(yearObj.value)%4!=0)||(dayObj.value>29&&parseInt(yearObj.value)%4==0)))){
	   alert("������д��ȷ�����ڣ�ע������ʹ�С�£�");
	   yearObj.focus()
	   return false;
	}
	return true;
}

//�ж��Ƿ�������Email��ʽ
function isEMail(obj){
	var secmail=obj.value;
	if (secmail.length != 0){
		if( secmail.length<8 || secmail.length>64 || !validateEmail(secmail) ) {
			alert("\����������ȷ�������ַ !!")
			obj.focus()
			return false;
		}
	}
	return true;
}

function isLoginNameAndPasswd(obj,mc,minlength){
	var str = obj.value;
	if (getValueLength(str) < minlength){
		alert(mc+" ����С��"+minlength+"λ����������ĸ�����ֺͺ��֣�");
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
			alert(mc+" ���ܰ����Ƿ��ַ�����������ĸ�����ֺͺ��֣�");
			return false;
		}
	}
	return true;
}

/**
*У�������ֻ��ѡ��һ�� ��������true,����������false
*/
function checkSelectOne(obj){
  if (obj.length == null)
  {
     if (!obj.checked)
     {
         alert("��ѡ��һ��!");
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
         alert("��ѡ��һ��!");
         return false;
     }
     if (counts > 1)
     {
         alert("��ֻ��ѡ��һ��!");
         return false;
     }
  }
  return true;
}


/**
*У�����������ѡ��һ�� ��������true,����������false
*/
function checkLeastSelectOne(obj){
  if (obj.length == null)
  {
     if (!obj.checked)
     {
         alert("������ѡ��һ��!");
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
         alert("������ѡ��һ��!");
         return false;
     }
  }
  return true;
}

//�ж��Ƿ���·�� obj���ж϶���,��obj������,dummy��ɶ���ˣ���
function isPath(obj,mc,dummy){   
   //�ж�Ϊ�ձ�־
   if (obj == null || obj.value == null)
   {
       alert(mc+"����û���ҵ�,�����쳣!");
       return false;
   }
   if (dummy)
   {
       if (obj.value.indexOf('  ') != -1 || obj.value.indexOf(':') != -1 || obj.value.indexOf('/') != -1 || 
           obj.value.indexOf('\\') != -1 || obj.value.indexOf('*') != -1 || obj.value.indexOf('?') != -1 || 
           obj.value.indexOf('"') != -1 || obj.value.indexOf('<') != -1 || obj.value.indexOf('>') != -1 || 
           obj.value.indexOf('|') != -1){
           alert(mc+"���������� �� TAB : / \\ * ? \" < > | �� ���ַ�!");
           return false; //Ĭ��Ϊ0
       }
   }else{
       if (obj.value.indexOf('  ') != -1 || obj.value.indexOf('\\') != -1 || obj.value.indexOf('*') != -1 || 
           obj.value.indexOf('?') != -1 || obj.value.indexOf('"') != -1 || obj.value.indexOf('<') != -1 || 
           obj.value.indexOf('>') != -1 || obj.value.indexOf('|') != -1){
           alert(mc+"���������� �� TAB \\ * ? \" < > | �� ���ַ�!");
           return false; //Ĭ��Ϊ0
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
*������name���� ���Ƶ�����ػ�����ʾ��
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

/**�������Obj ����ȫ��ѡ���������ѡ��״̬����ȡ������ѡ��
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
*���µ�ѡ�񴰿�
*/
function showOpenWindow(url){
	window.open(url,"showOpenWindow","width=500px,height=450px,scrollbars=yes,resizable=yes,top=100px,left=250px");
}

/*****
*��������ϴ����ļ�����
******/
function checkUpFile(varFilename){
    var regFilename= new RegExp("/|(\.swf$)|(\.asf$)|(\.asx$)|(\.rmvb$)|(\.rm$)|(\.mpg$)|(\.mpeg$)|(\.avi$)|(\.wav$)|(\.wmv$)|(\.wma$)|(\.jpg$)|(\.bmp$)|(\.gif$)|(\.txt$)|(\.doc$)|(\.mp3$)|(\.mid$)|/", "i");
    if ((varFilename != "") && (varFilename.search(regFilename)!= -1)){
        return true;
    }else {
       //alert("ֻ���ϴ�swf��asf��asx��rmvb��rm��mpg��mpeg��avi��wav��wmv��wma��jpg��bmp��gif��txt��doc��mp3��mid��ʽ���ļ���");
       return false;
    }
}
/*****
*��������ϴ���ͼƬ����
******/
function checkUpImg(varFilename){
    var regFilename= new RegExp("/|(\.png$)|(\.jpg$)|(\.bmp$)|(\.gif$)|/", "i");
    if ((varFilename != "") && (varFilename.search(regFilename)!= -1)){
       return true;
    }else {
       //alert("ֻ���ϴ�jpg��bmp��gif��png�ȸ�ʽ��ͼ���ļ���");
       return false;
    }
}
/*****
*��������ϴ����ļ�����
******/
function checkUpVideo(varFilename){
    var regFilename= RegExp("/|(\.swf$)|(\.asf$)|(\.asx$)|(\.rmvb$)|(\.rm$)|(\.mpg$)|(\.mpeg$)|(\.avi$)|(\.wav$)|(\.wmv$)|(\.wma$)|(\.mp3$)|(\.mid$)|/", "i");
    if ((varFilename != "") && (varFilename.search(regFilename)!= -1)){
        return true;
    }else {
       //alert("ֻ���ϴ�swf��asf��asx��rmvb��rm��mpg��mpeg��avi��wav��wmv��wma��jpg��bmp��gif��txt��doc��mp3��mid��ʽ���ļ���");
       return false;
    }
}

/**
*����Ƿ���0-9 A-Z��ĸ���
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
˵�������ֺ������ַ������к���
���ߣ�������
ʱ�䣺2007-02-07
*/
function func_split(srcstr, offsetfixed, splitunit){
	var str = srcstr;
	//�����ֽ��
	var splitstr = "";
	//����ʵ���ַ�����
	var len = 0;
	//�����ϴζ�ȡλ��
	var last = 0;
	//�����ִ���
	var splittimes = 1;
	for (var i = 0; i < str.length; i++){
	  //�ж��Ƿ���
	  if (str.charCodeAt(i) > 127){
	    len += 2;
	  }else{
	    len++;
	  }
	  if ((len / splitunit) >= splittimes && (i + 1) != str.length){
	    //�����ǰ���ȳ��Թ̶����ȵĽ�����ڲ�ִΣ���ʾ��Ҫ���б��β���ˣ�ͬʱλ�ò��ܴ����ٽ�λ��
	    splittimes++;
	    splitstr = splitstr + offsetfixed + str.substr(last, i - last) + "\n";
	    last = i;
	  }else{
	    //�������һ��
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