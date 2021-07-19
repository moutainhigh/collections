package com.gwssi.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;


/**
 * 读取所有的基础代码，缓存起来以便业务代码转换使用
 * @author lifx
 * 
 */
public class CodeConfig{
	
	
	private static CodeConfig codeConfig = null; 
	private Map mapCode = new HashMap();
	private static final String JC_DM_DM = "JC_DM_DM";
	private static final String JCSJFX_DM = "JCSJFX_DM";
	private static final String JCSJFX_MC = "JCSJFX_MC";
	private static final String SQLCODE = "901000-0001";
	private static final String SQL = "select GZ_DM_JCDM.JC_DM_DM,GZ_DM_JCDM_FX.JCSJFX_DM,GZ_DM_JCDM_FX.JCSJFX_MC from GZ_DM_JCDM,GZ_DM_JCDM_FX where GZ_DM_JCDM.JC_DM_ID = GZ_DM_JCDM_FX.JC_DM_ID order by GZ_DM_JCDM.JC_DM_DM,GZ_DM_JCDM_FX.JCSJFX_DM";
	private CodeConfig() {
		try{
			//获取数据库类型  区分
			//String sql = SQLConfig.get(SQLCODE);
			DBOperation operation = DBOperationFactory.createOperation();
			List list = operation.select(SQL);
			for(int i=0;list!=null&&i<list.size();i++){
				Map code = (Map)list.get(i);
				Map codeValue = null;
				String codeIndex = (String) code.get(JC_DM_DM);
				if(!mapCode.containsKey(codeIndex)){
					codeValue = new HashMap();
					mapCode.put(codeIndex, codeValue);
				}else{
					codeValue = (Map)mapCode.get(codeIndex);
				}
				codeValue.put(code.get(JCSJFX_DM), code.get(JCSJFX_MC));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized static CodeConfig getInstance(){
		if (codeConfig == null){
			codeConfig = new CodeConfig();
		}
		// reload();
		return codeConfig;
	}
	
	public static synchronized void reload(){
		codeConfig = new CodeConfig();
	}
	 
	public Map getCodeConfig(){
	    return mapCode;
	}
	
	/**
	 * 根据代码类型和具体代码值得到中文的含义
	 * @param key
	 * @return
	 */
	public static String get(String codeIndex,String code){
		CodeConfig codeConfig = getInstance();
		Map map = (Map) codeConfig.getCodeConfig().get(codeIndex);
		if(map==null||map.size()<=0){
			return nullToEmpty(code);
		}else{
			String codeValue = (String)map.get(code);
			if(codeValue==null||codeValue.length()<=0){
				return nullToEmpty(code);
			}else{
				return nullToEmpty(codeValue);
			}			
		}		
	}
	
	public static String nullToEmpty(String source){
		return (source==null?"":source);
	}
	
	public Map get(String codeIndex){
		Map map = (Map) codeConfig.getCodeConfig().get(codeIndex);
		return map;
	}

}
