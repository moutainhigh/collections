package cn.gwssi.etlschedule.module;

public class Area{
	//…Ë÷√≤Œ ˝
	private String areaCode=null;
	private String dataSource=null;
	private String sourceFlag=null;
	private String dbUser=null;
	private String dbPassword=null;
	
	public Area(String areaCode,String dataSource,String sourceFlag,String dbUser,String dbPassword){
		this.areaCode=areaCode;
		this.dataSource=dataSource;
		this.sourceFlag=sourceFlag;
		this.dbUser=dbUser;
		this.dbPassword=dbPassword;
	}
	
	public String getAreaCode(){
		return this.areaCode;
	}
	
	public String getDataSource(){
		return this.dataSource;
	}
	
	public String getSourceFlag(){
		return this.sourceFlag;
	}
	
	public String getDbUser(){
		return this.dbUser;
	}
	
	public String getDbPassword(){
		return this.dbPassword;
	}
}