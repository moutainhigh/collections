package com.gwssi.application.log.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gwssi.application.log.entity.OperationLogEntity;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.util.UuidGenerator;

public class LogUtil {
	/**
	 * 多数据源
	 * 获取日志库的数据源的key 该数据源在初始化的时候会自动加载
	 * @return 
	 */
	public static void logToOperationLog(OperationLogEntity entity) throws OptimusException{
		String pkOprationLog=UuidGenerator.getUUID();
		IPersistenceDAO dao = DAOManager.getPersistenceDAO("logDataSource");
		List listParam = new ArrayList();
		 
		//编写添加sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("insert into DC_SM_OPERATION_LOG (PK_OPERATION_LOG,CREATER_ID,CREATER_NAME,ORGAN_CODE,ORGAN_NAME,CLIENT_TYPE,CLIENT_IP,OPERATION_TIME,SYSTEM_CODE,FUNCTION_CODE,OPERATION_TYPE,OPERATION_STATE) ")
		.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");
		
		listParam.add(pkOprationLog);
		listParam.add(entity.getCreaterId());
		listParam.add(entity.getCreaterName());
		listParam.add(entity.getOrganCode());
		listParam.add(entity.getOrganName());
		listParam.add(entity.getClientType());
		listParam.add(entity.getClientIp());
		listParam.add(entity.getOperationTime());
		listParam.add(entity.getSystemCode());
		listParam.add(entity.getFunctionCode());
		listParam.add(entity.getOperationType());
		listParam.add(entity.getOperationState());
		
		
		dao.execute(sql.toString(),listParam);
		
		//执行sql
		/*
		try {
			dao.execute(sql.toString(),listParam);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i<listParam.size();i++){
				sb.append(listParam.get(i).toString());
				if(i!=listParam.size()-1)
					sb.append("|");
			}
			write(sb.toString(), true);
		}*/	
	}
	/*
	public static String getFileName(){
		Properties p = new Properties();
		String filePath = null;
		try {
			InputStream fis =FileName.class.getClassLoader().getResourceAsStream("fileName.properties");
			p.load(new InputStreamReader(fis,"UTF-8"));
			filePath = p.getProperty("fileName");
			return filePath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}
	
	public static void write(String message, boolean append) {
        String fileName = FileName.getFileName();
    		final File file = new File(fileName);
        final File parent = file.getParentFile();
        if (null != parent && !parent.exists()) {
            parent.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (final IOException ioe) {
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, append);
            fileWriter.write(message);
            fileWriter.write(System.lineSeparator());
            fileWriter.flush();
        } catch (IOException e) {
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
            }
        }

    }*/
}
