<%!
    public String[] common ={"trsadmin","12345678","123456789"};
    public int[] scores={10,30};
    public int minchar=8;
    public int checkPasswordLevel(String sPassword){
        int value =checkValue(sPassword);
        if(value<=scores[0]){
            return 2;
        }else if(value>scores[0] && value<=scores[1]){
            return 3;
        }
        return 4;
    }
    public int checkValue(String sPassword){
        int num=0;
        int charNum = countCharNum(sPassword);
        int passwordLength = sPassword.length();
        //从密码长度上获取其分值
        if(passwordLength<minchar){
            num-=300;
        }else if(passwordLength>minchar && passwordLength<=minchar+2){
            num+=6;
        }else if(passwordLength>minchar+2 && passwordLength<=minchar+4){
            num+=10;
        }else if(passwordLength>=minchar+5){
            num+=14;
        }
        // 从密码的字符组合上获取其分值
        // 有多少种字符,(防止只出现一种字符)
            if(charNum>1){num+=2*charNum;}
            // 有小写字母
            if(sPassword.matches("[a-z]")){num+=2;}
            // 有大写字母
            if(sPassword.matches("[A-Z]")){num+=5;}
            // 有数字
            if(sPassword.matches("\\d+")){num+=4;}
            // 一个特殊字符
            if(sPassword.matches(".[!,@,#,$,%,^,&,*,?,_,~]")){num+=5;}
            // 两个以上特殊符号
            if(sPassword.matches("(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])")){num+=18;}
            // 字母大小写
            if(sPassword.matches("([a-z].*[A-Z])|([A-Z].*[a-z])")){num+=6;}
            // 有字母和数字
            if(sPassword.matches("([a-zA-Z])")&& sPassword.matches("([0-9])")){num+=6;}
            // 字母、数字和符号
            if(sPassword.matches("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])")){num+=10;}
            for(int i=0;i<common.length;i++){
                if(sPassword.toLowerCase().equals(common[i])){
                    num=-200;
                }
            }
        return num;
    }
    /*
        获取密码的字符数
    */
    public int countCharNum(String sPassword){
        java.util.ArrayList list = new java.util.ArrayList();
        for (int i = 0; i < sPassword.length(); i++){
			if(list.contains(String.valueOf(sPassword.charAt(i))))continue;
			list.add(String.valueOf(sPassword.charAt(i)));
        }
        return list.size();
    }
%>