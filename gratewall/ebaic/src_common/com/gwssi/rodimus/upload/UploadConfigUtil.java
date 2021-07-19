package com.gwssi.rodimus.upload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.expression.core.lang.UndefinedObjectException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.UploadException;
import com.gwssi.rodimus.expr.ExprUtil;
import com.gwssi.rodimus.upload.config.SysUploadListManager;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class UploadConfigUtil {
	
	public static final String CP_SETUP_1100 = "CP_SETUP_1100";
	
	public static Map<String, Object> prepareParams(OptimusRequest request) {
		String gid = ParamUtil.get("gid");
		Map<String, Object> ret = ParamUtil.prepareParams(gid);
		return ret;
	}
	/**
	 * 得到上传文件列表。
	 * 
	 * @param listCode
	 * @param gid
	 * @param paramMap
	 * @return
	 */
	public static List<Map<String, Object>> getList(String listCode,OptimusRequest request ) {
		Map<String, Object> paramMap = UploadConfigUtil.prepareParams(request);
		/**获取已经上传的文件信息**/
		List<Map<String, Object>> ret = getConfigList(listCode, paramMap);
		// 构造参数
		String gid = request.getParameter("gid");
		List<Map<String, Object>> fileList= UploadListUtil.getFileList(gid);
		/**已上传的文件和配置信息映射对应**/
		ret = UploadListUtil.mergeList(ret,fileList);
		return ret;
	}
	
	/**
	 * 获取配置列表（不包含数据）
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> getCatgoryList(String listCode, OptimusRequest request){
		Map<String, Object> params = UploadConfigUtil.prepareParams(request);
		if(StringUtil.isBlank(listCode)){
			throw new UploadException("上传列表Code不能为空。");
		}
		List<Map<String, Object>> list = SysUploadListManager.instance.getConfig(listCode);
		if(list==null || list.isEmpty()){
			throw new UploadException(String.format("上传列表%s不包含上传项。", listCode));
		}
		//String gid = StringUtil.safe2String(params.get("gid"));
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> row : list) {
			String trigerExpr = StringUtil.safe2String(row.get("trigerExpr"));
			String uploadId = StringUtil.safe2String(row.get("uploadId"));
			/**校验表达式**/
			if(StringUtil.isNotBlank(trigerExpr)){//表达式为空，视为真
				Object exprResult = ExprUtil.run(trigerExpr, params);
				if(exprResult==null){ // 结果为空，视为真
					throw new UploadException(String.format("上传列表%s.%s判别表达式执行结果为null。", listCode, uploadId));
				}
				if(!(exprResult instanceof Boolean)){
					throw new UploadException(String.format("上传列表%s.%s判别表达式执行结果不为布尔值。", listCode, uploadId));
				}
				if(Boolean.FALSE.equals(exprResult)){//表达式执行结果为FALSE
					continue;
				}
			}
			row.remove("dataSql");
			row.remove("dataParams");
			row.remove("trigerExpr");
			
			ret.add(row);
		}// end of list
		
		return ret ;
	}
	
	
	/**
	 * 查询上传配置列表。bg
	 * 
	 * @param gid
	 * @return
	 */
	public static List<Map<String, Object>> getConfigList(String listCode, Map<String,Object> params){
		if(StringUtil.isBlank(listCode)){
			throw new UploadException("上传列表Code不能为空。");
		}
		List<Map<String, Object>> list = SysUploadListManager.instance.getConfig(listCode);
		if(list==null || list.isEmpty()){
			throw new UploadException(String.format("上传列表%s不包含上传项。", listCode));
		}
		
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> row : list) {
			String trigerExpr = StringUtil.safe2String(row.get("trigerExpr"));
			String uploadId = StringUtil.safe2String(row.get("uploadId"));
			/**校验表达式**/
			if(StringUtil.isNotBlank(trigerExpr)){//表达式为空，视为真
				Object exprResult = null;
				try{
					exprResult = ExprUtil.run(trigerExpr, params);
				}catch(UndefinedObjectException e){
					e.printStackTrace();
					exprResult = Boolean.FALSE;
				}
				if(exprResult==null){ // 结果为空，视为真
					throw new UploadException(String.format("上传列表%s.%s判别表达式执行结果为null。", listCode, uploadId));
				}
				if(!(exprResult instanceof Boolean)){
					throw new UploadException(String.format("上传列表%s.%s判别表达式执行结果不为布尔值。", listCode, uploadId));
				}
				if(Boolean.FALSE.equals(exprResult)){//表达式执行结果为FALSE
					continue;
				}
			}
			/**根据配置查询文件要关联的数据**/
			String dataSql =StringUtil.safe2String(row.get("dataSql"));
			if(StringUtils.isBlank(dataSql)){
				row.remove("dataSql");
				row.remove("dataParams");
				row.remove("trigerExpr");
				ret.add(row);
				continue;
				//没有配置sql的不查
			}
			String dataParamNames = StringUtil.safe2String(row.get("dataParams"));
			/**
			 * dataSql中如果有要关联的数据
			 * 查询结果至少要包括refId（关联的数据所在表主键），refText（页面显示的标题文字）
			 * **/
			List<Map<String,Object>> dataList = getDataList(dataSql,dataParamNames,params);
			
			row.put("fileArray", dataList);
			row.remove("dataSql");
			row.remove("dataParams");
			row.remove("trigerExpr");
			
			ret.add(row);
		}// end of list
		
		return ret ;
	}
	/**
	 * 获取文件列表对应的数据。
	 * 
	 * @param dataSql 查询数据用的sql
	 * @param dataParams 用到的参数key值字符串,用逗号分割
	 * @param params 参数实际值
	 * @return
	 */
	public static List<Map<String, Object>> getDataList(String dataSql,String dataParamNames,Map<String,Object> params){
		String [] paramKeys = null;
		List<Object> paramList = null;
		if(StringUtils.isNotBlank(dataParamNames)){
			paramKeys = dataParamNames.split(",");
			paramList = new ArrayList<Object>();
			for (String key : paramKeys) {
				paramList.add(params.get(key));
			}
		}
		if(paramList==null){
			return DaoUtil.getInstance().queryForList(dataSql);
		}else{
			return  DaoUtil.getInstance().queryForList(dataSql, paramList);
		}
	}
}
