package com.gwssi.dw.runmgr.services;

public class CommonMapObject implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long	serialVersionUID	= -3009687897512343977L;
	private String[]  key; 
    private String[]  value; 

    public CommonMapObject() { 
    }

	public CommonMapObject(int i)
	{
		key = new String[i];
		value=new String[i];
	}

	public String[] getKey()
	{
		return key;
	}

	public void setKey(String[] key)
	{
		this.key = key;
	}

	public String[] getValue()
	{
		return value;
	}

	public void setValue(String[] value)
	{
		this.value = value;
	} 

}
