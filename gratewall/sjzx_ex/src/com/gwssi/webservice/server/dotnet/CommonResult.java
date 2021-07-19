package com.gwssi.webservice.server.dotnet;

import com.gwssi.dw.runmgr.services.common.Constants;

public class CommonResult implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= 6593162716906849070L;

	private String FHDM;

    private String ZTS;

    private String KSJLS;

    private String JSJLS;
    
    private CommonMapObject[] commonObject;
    
    public CommonResult(){
    	FHDM=Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
    	ZTS="0";
    	KSJLS="0";
    	JSJLS="0";
    }
    

	public String getFHDM()
	{
		return FHDM;
	}

	public void setFHDM(String fhdm)
	{
		FHDM = fhdm;
	}

	public String getJSJLS()
	{
		return JSJLS;
	}

	public void setJSJLS(String jsjls)
	{
		JSJLS = jsjls;
	}

	public String getKSJLS()
	{
		return KSJLS;
	}

	public void setKSJLS(String ksjls)
	{
		KSJLS = ksjls;
	}

	public CommonMapObject[] getCommonObject()
	{
		return commonObject;
	}


	public void setCommonObject(CommonMapObject[] commonObject)
	{
		this.commonObject = commonObject;
	}


	public String getZTS()
	{
		return ZTS;
	}

	public void setZTS(String zts)
	{
		ZTS = zts;
	}
   
}
