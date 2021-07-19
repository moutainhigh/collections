<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>

<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%!
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

	/*
	*  通过传入年份，获取开始时间和结束时间
	*/
	public String[] getDateTimes(int _nStatYear)throws Exception{
		try{
			int nStatYear = _nStatYear;
			
			CMyDateTime startDateTime = new CMyDateTime();
			startDateTime.setDateTimeWithString(String.valueOf(nStatYear));
			String sStartTime = startDateTime.toString();

			CMyDateTime endDateTime = new CMyDateTime();
			endDateTime.setDateTimeWithString(nStatYear+"-12-31");
			String sEndTime = endDateTime.toString();
			CMyDateTime now = CMyDateTime.now();
			if(nStatYear == now.getYear()){
				sEndTime = now.toString();
			}
			return new String[]{sStartTime,sEndTime};
		}catch(Exception ex){
			throw new Exception(LocaleServer.getString("static_common.getTime.error","获取时间出现错误！"),ex);
		}
	}

	/** 
	* 获取图表统计数据
	*/
	public String getChartData(String _sAccountDesc, String _sData){
		String sAccountDesc = _sAccountDesc;
		if(CMyString.isEmpty(sAccountDesc)){
			return "";
		}
		return "{name: '" + sAccountDesc + "',data:"+_sData+"}";
	}

	/** 
	* 根据帐号Id获取帐号的描述信息：帐号名称（平台名称）
	*/
	public String getAccountDesc(int _nAccountId){
		Account oAccount = null;
		try{
			oAccount = Account.findById(_nAccountId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(oAccount == null){
			return "";
		}
		String sAccountName = oAccount.getAccountName();
		String sPaltform = oAccount.getPlatform();
		String sChineseName ="未知平台";
		try{
			Platform oPlatform = PlatformFactory.getPlatform(sPaltform);
			if(oPlatform != null ){
				sChineseName = oPlatform.getChineseName();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sAccountName + "("+sChineseName+")";
	}

	public List transXLabels(List _oXLabels){
		List newList = new ArrayList();
		if(_oXLabels == null){
			return newList;
		}
		for(int i=0; i< _oXLabels.size(); i++){
			String sTemp = (String)_oXLabels.get(i);
			if(sTemp == null)
				continue;
			sTemp = removeDoubleQuote(sTemp);
			if(sTemp.indexOf("-") > -1){
				try{
					CMyDateTime oTempDate = new CMyDateTime();
					oTempDate.setDateTimeWithString(sTemp);
					sTemp = String.valueOf(oTempDate.getMonth());
				}catch(Exception e){
					// 如果出异常，默认是1月
					sTemp = "1";
					e.printStackTrace();
				}
			}
			newList.add(sTemp + "月");
		}
		return newList;
	}

	private String removeDoubleQuote(String _str) {
        if (_str.startsWith("\"")) {
            // 去掉开头的双引号
            _str = _str.substring(1);
        }
        if (_str.endsWith("\"")) {
            // 去掉结尾的双引号
            _str = _str.substring(0, _str.length() - 1);
        }
        return _str;
    }
%>