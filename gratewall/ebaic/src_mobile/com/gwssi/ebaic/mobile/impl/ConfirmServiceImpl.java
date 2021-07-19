/**
 * 
 */
package com.gwssi.ebaic.mobile.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.req.service.ReqService;
import com.gwssi.ebaic.mobile.api.ConfirmService;
import com.gwssi.rodimus.exception.RpcException;

/**
 * @author LXB
 *
 */
@Service(value="confirmServiceImpl")
public class ConfirmServiceImpl implements ConfirmService {

	@Autowired
	ReqService reqService;
	
	public List<Map<String, Object>> queryReqListByPerson(String cerType,
			String cerNo) {
		List<Map<String, Object>> list = null;
		try {
			list=reqService.queryReqListByPerson(cerType,cerNo);
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}
		if(list==null){
			list = new ArrayList<Map<String, Object>>();
		}
		return list;
	}

	
	public List<Map<String, Object>> queryReqListByCp(String licNo) {
		List<Map<String, Object>> list = null;
		try {
			list = reqService.queryReqListByCp(licNo);
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}
		if(list==null){
			list = new ArrayList<Map<String, Object>>();
		}
		return list;
	}

	
	public String personConfirm(String cerType, String cerNo, String gid,
			String withAuth) {
		try {
			String result=reqService.personConfirm(cerType, cerNo,gid,withAuth);
			return result;
		} catch (Exception e) {
			throw new RpcException("执行（自然人）业务确认方法错误。");
		}
	}

	
	public String cpConfirm(String licType, String licNo, String gid,
			String withAuth) {
		try {
			String result = reqService.cpConfirm(licType, licNo, gid, withAuth);
			return result;
		} catch (Exception e) {
			throw new RpcException("执行（非自然人）业务确认方法错误。");
		}
	}

	
	public String personBackToAppUser(String cerType, String cerNo, String gid,
			String reason) {
		try {
			String result =reqService.personBackToAppUser(cerType,cerNo, gid,reason);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RpcException("执行（自然人）退回申请人方法错误。");
		}
		
	}

	
	public String cpBackToAppUser(String licType,String licNo, String gid, String reason) {
		try {
			String result = reqService.cpBackToAppUser(licNo,gid, reason);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RpcException("执行（非自然人）退回申请人方法错误。");
		}
		
	}

}
