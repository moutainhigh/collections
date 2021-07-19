package com.gwssi.sysmgr.txn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 将功能模块统计用的context转化为按照功能名称、部门ID组织的Map
 */
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;


public class ContextToMap
{
	public static Map execute(DataBus context) throws TxnException{		
		Map map = new LinkedHashMap();
		
		Recordset rs = context.getRecordset("record");
		for(int i = 0; i < rs.size(); i++){
			DataBus databus = rs.get(i);
			String funcName = databus.getValue("func_name");
			String orgCode = databus.getValue("sjjgid_fk");
			String count = databus.getValue("querytimes");
			
			Map o;
			if(!map.containsKey(funcName)){
				o = new HashMap();
				map.put(funcName, o);
			}else{
				o = (Map) map.get(funcName);
			}
			o.put(orgCode, count);
		}
		
		
		// 把总数计算好了存进去 总数的key用total
		int[][] totalArray = new int[map.keySet().size()][2];
		String[] funcNameArray = new String[map.keySet().size()];
		int i = 0;
		for( Iterator iter = map.keySet().iterator(); iter.hasNext(); i++){
			String funcName = (String)iter.next();
			Map data = (Map) map.get(funcName);
			int total = 0;
			for(Iterator iterator = data.keySet().iterator(); iterator.hasNext();){
				String orgCode = (String)iterator.next();
				int count = Integer.parseInt((String)data.get(orgCode));
				total += count;
			}
			data.put("total", String.valueOf(total));
			totalArray[i][0] = total;
			totalArray[i][1] = i;
			funcNameArray[i] = funcName;
		}
		
		// 对二维数组进行排序
		for(int n = totalArray.length - 1; n >= 0; n--){
			for(int m = 0; m < n; m++){
				if(totalArray[m][0] < totalArray[m+1][0]){
					int temp = totalArray[m][0];
					int tempIndex = totalArray[m][1];
					totalArray[m][0] = totalArray[m+1][0];
					totalArray[m][1] = totalArray[m+1][1];
					totalArray[m+1][0] = temp;
					totalArray[m+1][1] = tempIndex;
				}
			}
		}
		
		Map sortMap = new LinkedHashMap();
		for(int m = 0; m < totalArray.length; m++){
			String funcName = funcNameArray[totalArray[m][1]];
			sortMap.put(funcName, (Map) map.get(funcName));
		}
		
		return sortMap;
	}
	
	public static Map parse2Map(DataBus context) throws TxnException{
		Recordset rs = context.getRecordset("record");

		Map sortMap = new LinkedHashMap();
		for(int j = 0; j < rs.size(); j++){
			DataBus databus = rs.get(j);
			String orgCode = databus.getValue("sjjgid_fk");
			String count = databus.getValue("querytimes");
			String queryDate = databus.getValue("query_date");
			Map map = new HashMap();
			if(sortMap.containsKey(queryDate)){
				map = (HashMap)sortMap.get(queryDate);
				if(map.get(orgCode) == null){
					map.put(orgCode, count);
				}else{
					map.put(orgCode, ""+(Integer.parseInt(count)+Integer.parseInt((String)map.get(orgCode))));
				}
				if(map.get("total")==null){
					map.put("total", count);
				}else{
					map.put("total", ""+(Integer.parseInt(count)+Integer.parseInt((String)map.get("total"))));
				}
				sortMap.put(queryDate, map);
			}else{
				map.put(orgCode, count);
				if(map.get("total")==null){
					map.put("total", count);
				}else{
					map.put("total", ""+(Integer.parseInt(count)+Integer.parseInt((String)map.get("total"))));
				}
				sortMap.put(queryDate, map);
			}
		}
		
		System.out.println(sortMap);
		
		return sortMap;
	}
	public static void main(String[] args) throws TxnException
	{
		Recordset rs = new Recordset();
		DataBus db = new DataBus();
		db.setValue("sjjgid_fk", "ee1c6e35f0ad4b43a7f66af451ed45f9");
		db.setValue("querytimes", "1");
		db.setValue("query_date", "2009-08-26");
		rs.add(db);
		db = new DataBus();
		db.setValue("sjjgid_fk", "ee1c6e35f0ad4b43a7f66af451ed45f9");
		db.setValue("querytimes", "7");
		db.setValue("query_date", "2009-08-24");
		rs.add(db);
		db = new DataBus();
		db.setValue("sjjgid_fk", "ee1c6e35f0ad4b43a7f66af451ed45f9");
		db.setValue("querytimes", "111");
		db.setValue("query_date", "2009-08-20");
		rs.add(db);
		db = new DataBus();
		db.setValue("sjjgid_fk", "001007");
		db.setValue("querytimes", "4");
		db.setValue("query_date", "2009-03-24");
		rs.add(db);
		db = new DataBus();
		db.setValue("sjjgid_fk", "001007");
		db.setValue("querytimes", "4");
		db.setValue("query_date", "2009-03-24");
		rs.add(db);
		db = new DataBus();
		db.setValue("sjjgid_fk", "001007");
		db.setValue("querytimes", "2");
		db.setValue("query_date", "2009-03-19");
		rs.add(db);
		
		DataBus dd = new DataBus();
		dd.put("record", rs);
		parse2Map(dd);
	}
}
