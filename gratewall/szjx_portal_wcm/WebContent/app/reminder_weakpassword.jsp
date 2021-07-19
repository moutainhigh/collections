<%
    boolean bRightTimeToReminder = false;
    String sLoginString = (String)session.getAttribute("login_now");
    if(sLoginString!=null && sLoginString.indexOf("true")>=0){
        session.setAttribute("login_now","false");
        bRightTimeToReminder = isWeakPassword(loginUser) && isRightTimeToReminder(loginUser);
    }
%>
<%!
    public boolean isRightTimeToReminder(User _oLoginUser) throws WCMException{
        String timeString = _oLoginUser.getAttributeValue("lastremindertime");
        //获取上次的系统提示时间
        CMyDateTime oLastReminderTime = getDateTime(timeString);
        //获取这次的登录时间
        CMyDateTime oLoginTime = _oLoginUser.getLoginTime();
        if(oLastReminderTime==null){
            _oLoginUser.setAttribute("lastremindertime",oLoginTime.toString());
            _oLoginUser.save();
            return false;
        }
        //获取系统设置
        String sSystemInterval=ConfigServer.getServer().getSysConfigValue("WEAK_PASSWORD_REMINDER_INTERVAL","0");
        float fSysteminterval;
        try{
            fSysteminterval = Float.parseFloat(sSystemInterval);
        }catch(Exception e){
            fSysteminterval=0;
        }
        if(fSysteminterval<=0){
            //System.out.println("系统不进行弱密码校验！");
            return false;
        }
        float fSystemInterval = fSysteminterval*Float.parseFloat(String.valueOf(CMyDateTime.ADAY_MILLIS));
        //登录时间间隔比较
        long fInterval = oLoginTime.compareTo(oLastReminderTime);
        if(fSystemInterval<Float.parseFloat(String.valueOf(fInterval))){
            _oLoginUser.setAttribute("lastremindertime",oLoginTime.toString());
            _oLoginUser.save();
            return true;
        }
        return false;
    }
    public CMyDateTime getDateTime(String _time)throws WCMException{
        if(_time==null)
            return null;
        CMyDateTime dtValue = new CMyDateTime();
        try {
            dtValue.setDateTimeWithString(_time);
            return dtValue;
        } catch (Exception e) {
            e.printStackTrace();
            return new CMyDateTime();
        }
    }
    public boolean isWeakPassword(User _oLoginUser){
        int level =_oLoginUser.getPasswordLev();
        if(level<3)
            return true;
        return false;
    }
%>