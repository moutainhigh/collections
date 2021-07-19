package com.ly.vuecontroller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.common.bean.BeanUtils;
import com.ly.common.util.UUIDUtils;
import com.ly.dao.Page;
import com.ly.dao.impl.EmailRecevierDao;
import com.ly.dao.impl.EmailRecevierLogDao;
import com.ly.dao.impl.EmailSenderDao;
import com.ly.placeholder.DESUtils;
import com.ly.pojo.Account;
import com.ly.pojo.Email_Recevier;
import com.ly.pojo.Email_Recevier_Log;
import com.ly.pojo.Email_Sender;
import com.ly.vo.EmailReceverBean;


@RestController
@RequestMapping("pro")
public class EmailController {

	@Autowired
	private EmailRecevierDao emailRecevierDao;
	@Autowired
	private EmailSenderDao emailSenderDao;
	
	@Autowired
	private EmailRecevierLogDao emailRecevierLogDao;
	
	@RequestMapping("/getEmailRecevierList")
	public Page<EmailReceverBean> getInfo(String pageNum,String pageSize){
		int pageNo = 0;
		int row = 10;
		
		if(!StringUtils.isEmpty(pageNum)){
			pageNo = Integer.valueOf(pageNum);
		}
		
		if(!StringUtils.isEmpty(pageSize)){
			row = Integer.valueOf(pageSize);
		}
		
	/*	SELECT t.address, t.NAME, t.isSend, COUNT(t.msg) AS times,t.addTime FROM (
				SELECT t1.address, t1.NAME, t1.isSend, t1.addTime, t2.msg
				FROM Email_Recevier t1
				LEFT JOIN Email_Recevier_Log t2 ON t1.address = t2.address 
				) t 
				GROUP BY t.address
				ORDER BY t.addTime desc*/
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.id,t.address, t.NAME, t.isSend, COUNT(t.msg) AS times ");
		sb.append("FROM ( ");
		sb.append("SELECT t1.id,t1.address, t1.NAME, t1.isSend, t1.addTime, t2.msg ");
		sb.append("FROM Email_Recevier t1 ");
		sb.append("LEFT JOIN Email_Recevier_Log t2 ON t1.address = t2.address ");
		sb.append(") t ");
		sb.append("GROUP BY t.address ");
		sb.append("ORDER BY t.addTime desc");
		
		//NativeQuery query = emailRecevierDao.getCurrentSession().createNativeQuery(sb.toString());;
		
		List results = emailRecevierDao.getCurrentSession().createNativeQuery(sb.toString()).getResultList();;
		int total  = (int) emailRecevierDao.findCount(Email_Recevier.class);
		
		
		List<EmailReceverBean> returnList = new ArrayList();
		EmailReceverBean myBean = null;
		for (Object object : results) {
			Object [] obj = (Object[]) object;
			myBean = new EmailReceverBean();
			
			myBean.setId(obj[0].toString());
			
			String address = obj[1].toString();
			address = address.substring(0,3)+"****" + address.substring(address.indexOf("@"),address.length());
			
			myBean.setAddress(address);
			if(null!=obj[2]){
				myBean.setName(obj[2].toString());
			}
			myBean.setIsSend(obj[3].toString());
			
			myBean.setTimes(obj[4].toString());
			
			/*if(null!=obj[3]){
				myBean.setTimes(obj[3].toString());
			}*/
			returnList.add(myBean);
		}
		
		
		
		Page<EmailReceverBean> pages = new Page<EmailReceverBean>();
		pages.setPageNo(pageNo/row+1);
		pages.setStart(pageNo);
		pages.setTotalCount(total);
		pages.setPageList(returnList);
		return pages;
	} 
	
	
	@RequestMapping("/getEmailSenderList")
	public Page<Email_Sender> getEmail_Sender(String pageNum,String pageSize){
		int pageNo = 0;
		int row = 10;
		
		if(!StringUtils.isEmpty(pageNum)){
			pageNo = Integer.valueOf(pageNum);
		}
		
		if(!StringUtils.isEmpty(pageSize)){
			row = Integer.valueOf(pageSize);
		}
		List<Email_Sender> list = emailSenderDao.findByPage("from Email_Sender ", pageNo, row);
		int total  = (int) emailSenderDao.findCount(Email_Sender.class);
		
		List<Email_Sender> returnList = new ArrayList();
		
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Email_Sender ac  = list.get(i);
				String address = ac.getAddress();
				address = address.substring(0,3)+"****" + address.substring(address.indexOf("@"),address.length());
				ac.setAddress(address);
				
				
				returnList.add(ac);
			}
	 		
		}
		
		
		
		Page<Email_Sender> pages = new Page<Email_Sender>();
		pages.setPageNo(pageNo/row+1);
		pages.setStart(pageNo);
		pages.setTotalCount(total);
		pages.setPageList(returnList);
		return pages;
	} 
	
	
	
	
	@RequestMapping("/delEmailSender")
	public Map delEmailSender(Email_Recevier accWeb){
		Map<String,String> map = new HashMap<String,String>();
		if(accWeb!=null&&StringUtils.isNotEmpty(accWeb.getId())){
			try {
				String address = accWeb.getId();
				emailSenderDao.delete(Email_Sender.class, address);
			} catch (Exception e) {
				map.put("msg", "保存或更新失败，请稍后重试，失败原因==>" + e.getMessage());
			}
		}else{
			map.put("msg", "保存或更新失败，地址不能为空");
		}
		return map;
	}
	
	@RequestMapping("/addEmailSender")
	public Map addAccount(Email_Sender accWeb){
		Map<String,String> map = new HashMap<String,String>();
		
		Calendar calendar2 = Calendar.getInstance();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		calendar2.add(Calendar.DATE, 90);
		String three_days_after = sdf2.format(calendar2.getTime());
		System.out.println(three_days_after);
		
		
		if(accWeb!=null&&StringUtils.isNotEmpty(accWeb.getId())){
			try {
				if(accWeb.getId().equals("undefined")){
					accWeb.setId(UUIDUtils.getUUID());
					accWeb.setDate((new Date().toLocaleString()));
					accWeb.setEndDate(three_days_after);
					emailSenderDao.save(accWeb);
					map.put("msg", "保存成功");
				}else{
					String address = accWeb.getId();
					List<Email_Sender> list  = emailSenderDao.find("from Email_Sender t where t.id = ?", address);
					if(list!=null&&list.size()>0){
						Email_Sender ac  = list.get(0);
						//accWeb.setPwd(DESUtils.getEncryptString(accWeb.getPwd()));
						accWeb.setDate((new Date().toLocaleString()));
						
						
						accWeb.setAddress(ac.getAddress());
						
						accWeb.setEndDate(three_days_after);
						
						BeanUtils.copyProperties(accWeb, ac);
						emailSenderDao.update(ac);
						map.put("msg", "保存成功");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("msg", "保存或更新失败，请稍后重试，失败原因==>" + e.getMessage());
			}
		}else{
			map.put("msg", "保存或更新失败，地址不能为空");
		}
		return map;
	}
	
	

	@RequestMapping("/updateDefaultSender")
	public Map updateDefaulSender(Email_Sender accWeb){
		Map<String,String> map = new HashMap<String,String>();
		if(accWeb!=null&&StringUtils.isNotEmpty(accWeb.getId())){
			String address = accWeb.getId();
			accWeb.setPwd(accWeb.getPwd().trim());
			try {
				emailSenderDao.updateDefault(address);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			map.put("msg", "保存或更新失败，默认地址不能为空");
		}
		return map;
	}
	
	
	
	@RequestMapping("/updateBuySellSender")
	public Map updateBuySellSender(Email_Sender accWeb){
		Map<String,String> map = new HashMap<String,String>();
		if(accWeb!=null&&StringUtils.isNotEmpty(accWeb.getId())){
			String address = accWeb.getId();
			accWeb.setPwd(accWeb.getPwd().trim());
			try {
				emailSenderDao.updateBuySellSender(address);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			map.put("msg", "保存或更新失败，默认地址不能为空");
		}
		return map;
	}
	
	
	
	@RequestMapping("/addEmailRecevier")
	public Map addEmailRecevier(Email_Recevier accWeb){
		Map<String,String> map = new HashMap<String,String>();
		if(accWeb!=null&&StringUtils.isNotEmpty(accWeb.getId())){
			try {
				String address = accWeb.getId();
				List<Email_Recevier> list  = emailRecevierDao.find("from Email_Recevier t where t.id = ?", address);
				if(list!=null&&list.size()>0){
					Email_Recevier ac  = list.get(0);
					
					ac.setAddtime(new Date().toLocaleString());
					BeanUtils.copyProperties(accWeb, ac);
					
					
					emailRecevierDao.update(ac);
					map.put("msg", "保存成功");
				}else{
					accWeb.setIsSend("1");
					accWeb.setId(UUIDUtils.getUUID());
					emailRecevierDao.save(accWeb);
					map.put("msg", "保存成功");
				}
			} catch (Exception e) {
				map.put("msg", "保存或更新失败，请稍后重试，失败原因==>" + e.getMessage());
			}
		}else if(accWeb!=null&&StringUtils.isNotEmpty(accWeb.getAddress())){
			try {
				String address = accWeb.getAddress();
				List<Email_Recevier> list  = emailRecevierDao.find("from Email_Recevier t where t.address = ?", address);
				if(list!=null&&list.size()>0){
					Email_Recevier ac  = list.get(0);
					
					ac.setAddtime(new Date().toLocaleString());
					BeanUtils.copyProperties(accWeb, ac);
					
					emailRecevierDao.update(ac);
					map.put("msg", "保存成功");
				}else{
					accWeb.setIsSend("1");
					accWeb.setAddtime(new Date().toLocaleString());
					accWeb.setId(UUIDUtils.getUUID());
					emailRecevierDao.save(accWeb);
					map.put("msg", "保存成功");
				}
			} catch (Exception e) {
				map.put("msg", "保存或更新失败，请稍后重试，失败原因==>" + e.getMessage());
			}
		}else{
			map.put("msg", "保存或更新失败，地址不能为空");
		}
		return map;
	}
	
	
	@RequestMapping("/delEmailRecevier")
	public Map deleEmailRecevier(Email_Recevier accWeb){
		Map<String,String> map = new HashMap<String,String>();
		if(accWeb!=null&&StringUtils.isNotEmpty(accWeb.getId())){
			try {
				String address = accWeb.getId();
				emailRecevierDao.delete(Email_Recevier.class, address);
			} catch (Exception e) {
				map.put("msg", "保存或更新失败，请稍后重试，失败原因==>" + e.getMessage());
			}
		}else{
			map.put("msg", "保存或更新失败，地址不能为空");
		}
		return map;
	}
	
	
	//https://blog.csdn.net/gongzhufanlulu/article/details/51017945
	@RequestMapping("/getLogByAddress")
	public List getLogByAddress(String id) {
		List retList = new ArrayList();
		Map map = null;
		List<Email_Recevier> emailAddress  = emailRecevierDao.find("from Email_Recevier t where t.id = ?", id);
		if(emailAddress!=null&&emailAddress.size()>0){
			String address =  emailAddress.get(0).getAddress();
			String hql = "select msg from Email_Recevier_Log t where t.address = ?";
			Query query = emailRecevierLogDao.getCurrentSession().createQuery(hql);
			query.setParameter(0, address);

			List<String> str = query.list();
			for (String string : str) {
				map = new HashMap();
				map.put("msg", string);
				retList.add(map);
			}
			
			
		}
		return retList;
	}
	

	
}
