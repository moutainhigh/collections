package com.gwssi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gwssi.comselect.service.ComSelectService;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * 获取代码集工具类  公共提取 为Dao生成对应sql 
 * @author Yzh
 */
public class QueryCodeSql extends BaseService {

	public static String QueryCodeSql(String...params){
		String type = "";
		String param = "";
		
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();//参数准备
		
		if(params.length == 1){
			type = params[0];
			
			if("regcapcur".equals(type)){//币种
				 sql.append("select code as value, currency  as text from tcCurrency  ");
				 return sql.toString();
			}else if("industryphy".equals(type)){ //行业
				 sql.append("select code as value, code_desc  as text from C00014  ");
				 return sql.toString();
			}else if("regorg".equals(type)){//管辖区域
				 sql.append("select code as value, deptname  as text from tcDept  where depttype= '4'  ");
				 return sql.toString();
			}else if("caseFiauth".equals(type)){//立案机关
				 sql.append("select code as value, code_desc  as text from C00018 ");
				 return sql.toString();
			}else if("caseNo".equals(type)){//案件编号
				 sql.append("select code as value, code_desc  as text from C00110 ");
				 return sql.toString();
			}else if("caseState".equals(type)){//案件状态
				//目前案件状态没有新代码集  再改
				 sql.append("select code as value, code_desc  as text from C00110 ");
				 return sql.toString();
			}else if("infoOri".equals(type)){//案件状态
				//目前案件状态没有新代码集  再改
				 sql.append("select code as value, code_desc  as text from C00110 ");
				 return sql.toString();
			}else if("regDep".equals(type)){//案件状态
				//目前案件状态没有新代码集  再改
				 sql.append("select code as value, code_desc  as text from C00110 ");
				 return sql.toString();
			}else if("infoType".equals(type)){//案件状态
				//目前案件状态没有新代码集  再改
				 sql.append("select code as value, code_desc  as text from C00110 ");
				 return sql.toString();
			}
		}else{
			type = params[0];
			param = params[1];

			if("adminbrancode".equals(type)){//所属建管所
				 sql.append("select code as value, deptname  as text from tcDept  where depttype= '6'  and upperdept=? ");
				 str.add(param);
			}
		}
		
		return sql.toString();
	}
}
