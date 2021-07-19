package com.gwssi.query.webservice.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.audaque.webservice.service.SearchTeleWS;
import com.audaque.webservice.service.SearchTeleWSImplServiceLocator;
import com.gwssi.cxf.addrcode.impl.AddrInfoServiceImpl;

public class HouseServiceClient {// implements Runnable{
	private SearchTeleWSImplServiceLocator stw = new SearchTeleWSImplServiceLocator();
	private static Logger logger = Logger.getLogger(HouseServiceClient.class);

	// 根据楼栋编号进行查询
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getBuildingByCode(String buildingCode) {
		List list = new ArrayList();
		SearchTeleWS ws;
		String str = null;
		try {
			ws = stw.getSearchTeleWSImplPort();
			str = ws.building_Houses("1VU4F0JGL8DKCOJ5", buildingCode == null ? "" : buildingCode, 1, 10);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JSONArray array = JSONObject.parseArray(str);
		if (array != null && array.size() != 0) {
			for (int i = 0; i < array.size(); i++) {
				Map map = new HashMap();
				map.put("rownum", String.valueOf(i + 1));
				map.put("houseId", array.getJSONObject(i).get("HOUSE_CODE"));
				map.put("houseAdd", array.getJSONObject(i).get("DETAIL_HOUSE_FULL_ADDR"));
				list.add(map);
			}
		}
		return list;
	}

	// 根据楼栋名称，楼栋编码返回楼栋的基本信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getBuildingByParam(String buildingName, String buildingCode) {
		List list = new ArrayList();
		SearchTeleWS ws;
		String str = null;
		try {
			ws = stw.getSearchTeleWSImplPort();
			str = ws.searchTele_Building("1VU4F0JGL8DKCOJ5", buildingName == null ? "" : buildingName,
					buildingCode == null ? "" : buildingCode, 1, 10);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JSONArray array = JSONObject.parseArray(str);
		if (array != null && array.size() != 0) {
			for (int i = 0; i < array.size(); i++) {
				Map map = new HashMap();
				map.put("rownum", String.valueOf(i + 1));
				map.put("buildingCode", array.getJSONObject(i).get("BUILDING_CODE"));
				map.put("buildingAddr", array.getJSONObject(i).get("DETAIL_BUILDING_FULL_ADDR"));
				list.add(map);
			}
		}
		return list;
	}

	// 根据房屋名称，房屋编码返回房屋信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getHouseByParam(String houseName, String houseCode) {
		List list = new ArrayList();
		SearchTeleWS ws;
		String str = null;
		try {
			ws = stw.getSearchTeleWSImplPort();
			str = ws.searchTele_Houses("1VU4F0JGL8DKCOJ5", houseName == null ? "" : houseName,
					houseCode == null ? "" : houseCode, 1, 10);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JSONArray array = JSONObject.parseArray(str);
		if (array != null && array.size() != 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Object obj = jsonObject.get("rows");
				// obj为空，则跳出本次循环
				if (obj == null) {
					continue;
				}
				String jsonStr = JSONObject.toJSONString(obj);
				JSONArray parseArray = JSONObject.parseArray(jsonStr);
				for (int j = 0; j < parseArray.size(); j++) {
					Map map = new HashMap();
					map.put("rownum", String.valueOf(i + 1));
					map.put("houseId", parseArray.getJSONObject(j).get("HOUSE_CODE"));
					map.put("houseAdd", parseArray.getJSONObject(j).get("DETAIL_HOUSE_FULL_ADDR"));
					list.add(map);
				}
			}
		}
		return list;
	}

	// 根据接口4: 房屋名称(模糊查询),房屋编码查询
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getHouseByMoreAddress(String houseName, String houseCode) {
		List list = new ArrayList();
		SearchTeleWS ws;
		String str = null;
		try {
			ws = stw.getSearchTeleWSImplPort();
			str = ws.houses_MoreAddress("1VU4F0JGL8DKCOJ5", houseName == null ? "" : houseName,
					houseCode == null ? "" : houseCode, 1, 10);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JSONArray array = JSONObject.parseArray(str);
		if (array != null && array.size() != 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Object obj = jsonObject.get("rows");
				// obj为空，则跳出本次循环
				if (obj == null) {
					continue;
				}
				String jsonStr = JSONObject.toJSONString(obj);
				JSONArray parseArray = JSONObject.parseArray(jsonStr);
				for (int j = 0; j < parseArray.size(); j++) {
					Map map = new HashMap();
					map.put("rownum", String.valueOf(i + 1));
					map.put("houseId", parseArray.getJSONObject(j).get("HOUSE_CODE"));
					map.put("houseAdd", parseArray.getJSONObject(j).get("DETAIL_HOUSE_FULL_ADDR"));
					map.put("houseShortAdd", parseArray.getJSONObject(j).get("HOUSE_ADDR"));
					//map.put("realAddr", parseArray.getJSONObject(j).get("REAL_ADDR"));
					list.add(map);
				}
			}
		}
		logger.info("com.gwssi.query.webservice.client.HouseServiceClient.getHouseByMoreAddress(String, String) 查询出来的房屋数量======>"  + list.size());
		System.out.println(list);
		return list;
	}
	
	/*public static void main(String[] args) {
		 System.out.println(Thread.currentThread().getName());
     		for (int i = 0; i < 2000; i++) {
			   new Thread("" + i){
				   public void run(){
					   System.out.println("Thread: " + getName() + "  running");
					   HouseServiceClient c = new HouseServiceClient();
					   c.getHouseByMoreAddress("","4403060070043400005000379");
				   }
			   }.start();
		}
	}

	@Override
	public void run() {
		 for (int i = 0; i < 2000; i++) {  
	            System.out.println(Thread.currentThread().getName() + "运行  :  " + i);  
	            HouseServiceClient c = new HouseServiceClient();
			    c.getHouseByMoreAddress("","4403060070043400005000379");
	        }  
	         
	    }*/
	
	public static void main(String[] args) {
		HouseServiceClient c = new HouseServiceClient();
		   c.getHouseByMoreAddress("","4403060070043400005000379");
		
	}
	
}
