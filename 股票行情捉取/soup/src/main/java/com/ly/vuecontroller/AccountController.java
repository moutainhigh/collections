package com.ly.vuecontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.common.bean.BeanUtils;
import com.ly.common.util.UUIDUtils;
import com.ly.core.StockTradeCore;
import com.ly.dao.Page;
import com.ly.dao.impl.AccountDao;
import com.ly.dao.impl.AccountOptDao;
import com.ly.pojo.Account;
import com.ly.pojo.Account_Ji_Jing;
import com.ly.pojo.Account_Operation;

@RestController
@RequestMapping("pro")
public class AccountController {

	@Autowired
	private AccountDao buyDao;
	@Autowired
	private StockTradeCore stockTradeBus;
	

	@Autowired
	private AccountOptDao accountOptDao;
	

	
	//https://blog.51cto.com/liuximeng/1840083
	//https://www.cnblogs.com/sijizhen/p/10057294.html
	//https://fandayrockworld.iteye.com/blog/910347
	//https://blog.csdn.net/lixinyao5281/article/details/70332371
	@RequestMapping("/getAccountList")
	public Page getInfo(String pageNum,String pageSize){
		int pageNo = 0;
		int row = 10;
		
		if(!StringUtils.isEmpty(pageNum)){
			pageNo = Integer.valueOf(pageNum);
		}
		
		if(!StringUtils.isEmpty(pageSize)){
			row = Integer.valueOf(pageSize);
		}
		//left  join fetch t.options
		//https://www.cnblogs.com/nsw2018/p/6525759.html
		//https://08284008.iteye.com/blog/1456476
		List<Account> list = buyDao.find("select distinct  t from  Account  t left join  fetch t.options");
		int total  = (int) buyDao.findCount(Account.class);//https://blog.csdn.net/u014133399/article/details/49507109
		
		
		//必须这样，不然会让hibernate 产生一次update语句
		List<Account> list2 = new ArrayList<Account>();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Account ac  = list.get(i);
				String accountStr = ac.getAccount();
				accountStr = accountStr.substring(0,accountStr.length()-8)+"****";
				ac.setAccount(accountStr);
				
				
				String shAccount  = ac.getShAccount();
				String szAccount  = ac.getSzAccount();
				String tokenId = ac.getTokenId();
				
				tokenId = tokenId.substring(0,3) + "****" + tokenId.substring(tokenId.length()-3,tokenId.length());
				
				shAccount = shAccount.substring(0,3) + "****" + shAccount.substring(shAccount.length()-3,shAccount.length());
				szAccount = szAccount.substring(0,3) + "****" + szAccount.substring(szAccount.length()-3,szAccount.length());
				
				ac.setShAccount(shAccount);
				ac.setSzAccount(szAccount);
				ac.setTokenId(tokenId);
				
				ac.setAppName("sxho");
				
				list2.add(ac);
			}
	 		
		}
		Page<Account> pages = new Page<Account>();
		pages.setPageNo(pageNo/row+1);
		pages.setStart(pageNo);
		pages.setTotalCount(total);
		pages.setPageList(list2);
		return pages;
		
	} 
	
	
	
	
	
	
	
	//https://www.cnblogs.com/dongfangshenhua/p/7099970.html
	@RequestMapping("/addAccount")
	public Map addAccount(Account accWeb){
		Map<String,String> map = new HashMap<String,String>();
		if(accWeb!=null&&accWeb.getAccount()!=null&&StringUtils.isNotEmpty(accWeb.getAccount())&&accWeb.getName()!=null&&StringUtils.isNotEmpty(accWeb.getName())){
			//String uuid = DESUtils.getEncryptString(accWeb.getAccount());
			String uuid = "30089" +accWeb.getAccount()+"102358";
			try {
				List<Account> list  = buyDao.find("from Account t where t.aid = ? or t.account=?", uuid);
				if(list!=null&&list.size()>0){
					Account ac  = list.get(0);
					ac.setIsWeiTuo("1");
					BeanUtils.copyProperties(accWeb, ac);
					buyDao.update(ac);
					map.put("msg", "保存成功");
				}else{
					accWeb.setAid(uuid);
					//accWeb.setAppName(DESUtils.getEncryptString("PA18"));
					accWeb.setAppName("PA18");
					accWeb.setUpdateTime(new Date().toLocaleString());
					
					Account_Operation ac01 = new Account_Operation();
					//只买不卖,只卖不买,买卖同操,不操作
					ac01.setId(UUIDUtils.getUUID());
					ac01.setOperationType("只买不卖");
					ac01.setIsdefault("0");
					ac01.setSeqs("1");
					
					
					
					Account_Operation ac02 = new Account_Operation();
					//只买不卖,只卖不买,买卖同操,不操作
					ac02.setId(UUIDUtils.getUUID());
					ac02.setOperationType("只卖不买");
					ac02.setIsdefault("0");
					ac02.setSeqs("2");
					
					
					Account_Operation ac03 = new Account_Operation();
					//只买不卖,只卖不买,买卖同操,不操作
					ac03.setId(UUIDUtils.getUUID());
					ac03.setOperationType("买卖同操");
					ac03.setIsdefault("1");
					ac03.setSeqs("3");
					
					
					Account_Operation ac04 = new Account_Operation();
					//只买不卖,只卖不买,买卖同操,不操作
					ac04.setId(UUIDUtils.getUUID());
					ac04.setOperationType("不操作");
					ac04.setIsdefault("0");
					ac04.setSeqs("4");
					
					
					List<Account_Operation> set01 = new ArrayList<Account_Operation>();
					set01.add(ac01);
					set01.add(ac02);
					set01.add(ac03);
					set01.add(ac04);
					
					accWeb.setOptions(set01);
					
					
					
					Account_Ji_Jing ac05 = new Account_Ji_Jing();
					//只买不卖,只卖不买,买卖同操,不操作
					ac05.setId(UUIDUtils.getUUID());
					ac05.setOperationType("自动买");
					ac05.setIsdefault("1");
					ac05.setSeqs("1");
					
					Account_Ji_Jing ac06 = new Account_Ji_Jing();
					//只买不卖,只卖不买,买卖同操,不操作
					ac06.setId(UUIDUtils.getUUID());
					ac06.setOperationType("不自动买");
					ac06.setIsdefault("0");
					ac06.setSeqs("2");
					
					List<Account_Ji_Jing> listJiJing = new ArrayList<Account_Ji_Jing>();
					
					listJiJing.add(ac05);
					listJiJing.add(ac06);
					
					accWeb.setOptionsJiJing(listJiJing);
					
					
					buyDao.save(accWeb);
					map.put("msg", "保存成功");
				}
				
				
				
			} catch (Exception e) {
				map.put("msg", "保存或更新失败，请稍后重试，失败原因==>" + e.getMessage());
			}
		}else{
			map.put("msg", "保存或更新失败，账号名称,账号都不能为空");
		}
		return map;
	}
	
	
	@RequestMapping(value = "/updateGudongOpt")
	public Map<String,String > upateOperation(String account,String opt){
		Map<String, String> maps = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(account)&&StringUtils.isNotEmpty(opt)){
			try {
				accountOptDao.clearOpts(account);
				accountOptDao.addOpts(account,opt);
				maps.put("msg", "股东信息更新成功");
			} catch (Exception e) {
				e.printStackTrace();
				maps.put("msg", "股东信息更新失败,请稍后重试");
			}
		}else{
			maps.put("msg", "股东信息更新失败,相关信息不可同时为空");
		}
		return maps;
	}
	
	
	
	@RequestMapping(value = "/updateGudongJiJingOpt")
	public Map<String,String > updateGudongJiJingOpt(String account,String opt){
		Map<String, String> maps = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(account)&&StringUtils.isNotEmpty(opt)){
			try {
				accountOptDao.clearJiJingOpts(account);
				accountOptDao.addJiJingOpts(account,opt);
				maps.put("msg", "股东信息更新成功");
			} catch (Exception e) {
				e.printStackTrace();
				maps.put("msg", "股东信息更新失败,请稍后重试");
			}
		}else{
			maps.put("msg", "股东信息更新失败,相关信息不可同时为空");
		}
		return maps;
	}
	
	
	@RequestMapping("/updateAccount")
	public Map updateAccount(String aid,String tokenId){
		String szStockCode = "000001";
		String shStockCode = "601398";
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(aid)&&StringUtils.isNotEmpty(tokenId)){
			try {
				List<Account> list  = buyDao.find("from Account t where t.aid = ? ", aid);
				if(list!=null&&list.size()>0){
					Account ac  = list.get(0);
					ac.setIsWeiTuo("1");
					
					//ac.setAppName("AYLCH5");
					//ac.setTokenId(DESUtils.getEncryptString(tokenId));
					
					ac.setAppName("PA18");
					ac.setTokenId(tokenId);
					
					
					String szGuDongCode ="0"; 
					 szGuDongCode ="0137752409"; 
					String shGuDongCode ="0"; 
					 shGuDongCode ="A248845069"; 
					
					/* ArrayList list01 = (ArrayList) stockTradeBus.getGuDongAccount(ac, szStockCode);
					if (list01 != null && list01.size() > 0) {
						Map mpa =  (Map) list01.get(0);
						szGuDongCode = (String) mpa.get("stock_account");
					}
					
					ArrayList list02 = (ArrayList) stockTradeBus.getGuDongAccount(ac, shStockCode);
					if (list02 != null && list02.size() > 0) {
						Map mpa =  (Map) list02.get(0);
						shGuDongCode = (String) mpa.get("stock_account");
					}*/
					
					ac.setSzAccount(szGuDongCode);
					ac.setShAccount(shGuDongCode);
					
					ac.setUpdateTime(new Date().toLocaleString());
					
					buyDao.update(ac);
					map.put("msg", "保存成功");
				}
			} catch (Exception e) {
				map.put("msg", "更新失败，请稍后重试，失败原因==>" + e.getMessage());
			}
		}else{
			map.put("msg", "账号名称,账号都不能为空");
		}
		return map;
	}
	
	
	@RequestMapping("/delAccount")
	public Map delAccount(Account accWeb){
		Map<String,String> map = new HashMap<String,String>();
		if(accWeb!=null&&accWeb.getAid()!=null&&StringUtils.isNotEmpty(accWeb.getAid())){
			String aid = accWeb.getAid();
			try {
				accountOptDao.deleteAllById(aid);
				accountOptDao.deleteJiJingAllById(aid);
				buyDao.delete(Account.class, aid);
				map.put("msg", "删除成功");
			} catch (Exception e) {
				map.put("msg", "删除成功，失败原因==>" + e.getMessage());
			}
		}else{
			map.put("msg", "账号不能为空");
		}
		return map;
	}
	
	
	
}
