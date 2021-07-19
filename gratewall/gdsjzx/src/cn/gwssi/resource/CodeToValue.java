package cn.gwssi.resource;

import java.util.List;
import java.util.Map;

import com.gwssi.optimus.core.cache.dictionary.DicData;
import com.gwssi.optimus.core.cache.dictionary.DictionaryManager;
import com.gwssi.optimus.core.exception.OptimusException;

import cn.gwssi.ToolService;

/**
 * 代码转换中文
 * @author zhixiong
 *
 */
public class CodeToValue {
	
	/**
	 * 根据代码翻译中文
	 * @param code
	 * @param flag 0代表从缓存中取/1 代表从库中取/2代表从缓存中取，当缓存不存在时再去从数据库中取，并更新缓存
	 * @param toolService
	 * @return
	 * @throws OptimusException
	 */
	public static Map codeToValue(String code,String flag,ToolService toolService) throws OptimusException{
		return codeToValue(code,flag,null,toolService);
	}
	
	/**
	 * 根据代码和代码表翻译中文
	 * @param code
	 * @param tableName
	 * @param flag 0代表从缓存中取/1 代表从库中取/2代表从缓存中取，当缓存不存在时再去从数据库中取，并更新缓存
	 * @param toolService
	 * @return
	 * @throws OptimusException
	 */
	public static Map codeToValue(String code,String flag,String tableName,ToolService toolService) throws OptimusException {
		if("0".equals(flag)){
			return null;
		}else if("0".equals(flag)){
			return toolService.codeToValue(code,tableName);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据代码翻译中文列表
	 * @param code
	 * @param flag 0代表从缓存中取/1 代表从库中取/2代表从缓存中取，当缓存不存在时再去从数据库中取，并更新缓存
	 * @param toolService
	 * @return
	 * @throws OptimusException
	 */
	public static List<Map> codeToValues(String code,String flag,ToolService toolService) throws OptimusException{
		return codeToValues(code,flag,null,toolService);
	}
	
	/**
	 * 根据代码和代码表翻译中文列表
	 * @param code
	 * @param tableName
	 * @param flag 0代表从缓存中取/1 代表从库中取/2代表从缓存中取，当缓存不存在时再去从数据库中取，并更新缓存
	 * @param toolService
	 * @return
	 * @throws OptimusException
	 */
	public static List<Map> codeToValues(String code,String flag,String tableName,ToolService toolService) throws OptimusException {
		if("0".equals(flag)){
			return null;
		}else if("1".equals(flag)){
			return toolService.codeToValues(code,tableName);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据代码和代码表翻译中文
	 * @param code
	 * @return
	 * @throws OptimusException
	 */
	public static String codeToValue(String tableCode,String code) throws OptimusException {
		DicData dicData = DictionaryManager.getData(tableCode.toUpperCase());
		String text = dicData.getText(code==null?"":code.trim());
		if(text==null){
			text = "";
		}
		return text;
	}
	
	/**
	 * 根据代码和代码表翻译中文列表
	 * @param code
	 * @return
	 * @throws OptimusException
	 */
	public static List<Map> codeToValueList(String tableCode) throws OptimusException {
		DicData dicData = DictionaryManager.getData(tableCode);
		List<Map> dataList = dicData.getDataList(); 
		return dataList;
	}
}
