package com.gwssi.query.webservice.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.audaque.webservice.service.SearchTeleWS;
import com.audaque.webservice.service.SearchTeleWSImplServiceLocator;

public class HouseServiceClient {
	private SearchTeleWSImplServiceLocator stw = new SearchTeleWSImplServiceLocator();

	// 根据楼栋编号进行查询
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getBuildingByCode(String buildingCode) {
		List list = new ArrayList();
		SearchTeleWS ws;
		String str = null;
		try {
			ws = stw.getSearchTeleWSImplPort();
			str = ws.building_Houses(buildingCode == null ? "" : buildingCode);
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
				map.put("houseAdd",
						array.getJSONObject(i).get("DETAIL_HOUSE_FULL_ADDR"));
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
			str = ws.searchTele_Building(buildingName == null ? ""
					: buildingName, buildingCode == null ? "" : buildingCode);
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
				map.put("buildingCode",
						array.getJSONObject(i).get("BUILDING_CODE"));
				map.put("buildingAddr",
						array.getJSONObject(i).get("DETAIL_BUILDING_FULL_ADDR"));
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
			str = ws.searchTele_Houses(houseName == null ? "" : houseName,
					houseCode == null ? "" : houseCode);
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
				map.put("houseAdd",
						array.getJSONObject(i).get("DETAIL_HOUSE_FULL_ADDR"));
				list.add(map);
			}
		}
		return list;
	}
}
