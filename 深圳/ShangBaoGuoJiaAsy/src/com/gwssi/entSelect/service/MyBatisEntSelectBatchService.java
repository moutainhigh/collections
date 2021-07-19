package com.gwssi.entSelect.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.entSelect.dao.AltInfoDao;
import com.gwssi.entSelect.dao.EntLogDao;
import com.gwssi.entSelect.pojo.AltInfoBo;
import com.gwssi.entSelect.pojo.EntErrorLog;
import com.gwssi.entSelect.util.util;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;


@Service
public class MyBatisEntSelectBatchService extends BaseService {
	
	@Autowired
	private AltInfoDao altInfoDao;
	
	
	@Autowired
	LogService logService;
	
	
	public String queryTotal(String queryParams) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		String sql = "	select count(1) as count from v_guo_jia_ju_shangbao t"; 
		
		if(StringUtils.isNotEmpty(queryParams)) {
			sql= sql + queryParams;
		}
		
		// sql = "	select count(1) as count  from v_guo_jia_ju_shangbao t where t.main_tb_id = '2e8c23e89ab54c91b529c2d8437e2f31' ";    
		return dao.queryForList(sql, null).get(0).get("count").toString();
	}
	
	public List<Map> queryBiangengDetail(int pageSize,int pageIndex,String queryParams) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		String sql = "	select * from v_guo_jia_ju_shangbao t ";    

		if(StringUtils.isNotEmpty(queryParams)) {
			sql = sql + queryParams;
		}
		//sql="	select * from v_guo_jia_ju_shangbao t  where t.main_tb_id = '2e8c23e89ab54c91b529c2d8437e2f31' ";
		List retList =  dao.pageQueryForList(sql, null, pageSize,pageIndex)  ;
		return util.typechage(retList);
	}

	
	/*变更详细信息(第二种需要查询额外表)*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryBiangengDetail2(String altitem, String regino,String main_tb_id,String idParent,String pripidParent,String logDate) throws OptimusException{		
		String altitemcode = altitem;
		String id = main_tb_id;		
		IPersistenceDAO dao = getPersistenceDAO("dc_dc");;
		StringBuffer sql = new StringBuffer();
		
		
		List ids  = new ArrayList();
		List params = new ArrayList();
		
		if("03".equals(altitemcode)|| "B7".equals(altitemcode)){ //负责人变更--首席代表变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where (t.licflag = 'fddbr' or t.licflag = 'jyz') and t.regino = ?");
			params.add(regino);
		}else if("30".equals(altitemcode)||"D2".equals(altitemcode) || "D9".equals(altitemcode)|| "11".equals(altitemcode)){ //投资人变更
			//sql.append("select t.inv||' '||to_char(t.subconam,'fm999999999990.099999')||'（万元）'||to_char(t.conprop,'fm99990.009999')||'%' as content, t.bgtype,case EXCAFFSIGN  when '1' then '是' when '2' then '否' end as zshhr, case RESPONWAY when '1' then '普通合伙人' when '2' then '有限合伙人' end as  RESPONWAY  from dc_ra_mer_invest_ext t where 1=1 and t.regino = ? order by t.id");
			
			sql.append("select t.inv || ' ' || to_char(t.subconam, 'fm999999999990.099999') ||'（万元）' || to_char(t.conprop, 'fm99990.009999') || '%' as content,t.bgtype,case EXCAFFSIGN\n" +
					"         when '1' then\n" + 
					"          '是'\n" + 
					"         when '2' then\n" + 
					"          '否'\n" + 
					"       end as zshhr,\n" + 
					"       case RESPONWAY\n" + 
					"         when '1' then\n" + 
					"          '普通合伙人'\n" + 
					"         when '2' then\n" + 
					"          '有限合伙人'\n" + 
					"       end as RESPONWAY,\n" + 
					"       case when p.persname is not null then '委派代表:'||P.Persname else null end as persname\n" + 
					"  from dc_ra_mer_invest_ext t left join dc_ra_mer_persons_ext p  on t.id = p.id  and t.bgtype = p.bgtype \n" + 
					" where 1 = 1 \n " + 
					"   and t.regino = ? \n" + 
					" order by t.id ");

			
			params.add(regino);
		}
		/*else if("30".equals(altitemcode)||"D2".equals(altitemcode) || "D9".equals(altitemcode) || "11".equals(altitemcode)){ //投资人变更
			sql.append("select t.inv||' '||to_char(t.subconam,'fm999999999990.099999')||'（万元）'||to_char(t.conprop,'fm99990.009999')||'%' as content, t.bgtype from dc_ra_mer_invest_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}*/else if("46".equals(altitemcode) || "A3".equals(altitemcode) || "48".equals(altitemcode) || "B8".equals(altitemcode) || "47".equals(altitemcode) || "49".equals(altitemcode) || "70".equals(altitemcode) || "C7".equals(altitemcode)){ //主要人员变更
			sql.append("select wm_concat(t.persname ||'('||(func_getcode('C00005', a.POST)) ||')') as content, t.bgtype from dc_ra_mer_member_ext a left join dc_ra_mer_persons_ext t on t.id = a.id where t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("A8".equals(altitemcode) || "D6".equals(altitemcode) || "D7".equals(altitemcode) || "42".equals(altitemcode) || "15".equals(altitemcode) || "19".equals(altitemcode)){ //隶属企业名称变更
			sql.append("select t.entname as content, t.bgtype from dc_ra_mer_subent_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("A9".equals(altitemcode) || "B2".equals(altitemcode)){ //隶属企业地址变更
			sql.append("select t.dom as content, t.bgtype from dc_ra_mer_subent_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("B9".equals(altitemcode)){ //经营者表更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'jyz' and t.regino = ?");
			params.add(regino);
		}else if("B8".equals(altitemcode)){ //代表表更
			sql.append("select t.persname||'('||t.POST||')' as content, t.bgtype from V_OUTER_QY_ZHUYAORENYUANBIAO t where t.licflag in ('DSZ','QTDS','ZJL','JSCY','QT') and t.regino = ?");
			params.add(regino);
		}else if("C3".equals(altitemcode)){ //投资人姓名变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'fddbr' and t.regino = ?");
			params.add(regino);
		}else if("C9".equals(altitemcode)){ //出资计划变更
			sql.append("select t.curactconam as content, t.bgtype from dc_ra_mer_invplan_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("D1".equals(altitemcode)){ //审批项目变更
			sql.append("select t.licname as content, t.bgtype from dc_ra_mer_license_ext t where 1=1 and t.regino = ?");
			params.add(regino);
		}else if("E3".equals(altitemcode) || "E7".equals(altitemcode) || "73".equals(altitemcode)){ //清算组成员变更
			sql.append("select wm_concat(t.persname) as content, t.bgtype from dc_ra_mer_clmember_ext t where t.licflag in ('qsz','qszcy') and t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("E9".equals(altitemcode)){ //合伙人变更
			sql.append("select wm_concat(t.inv) as content, t.bgtype from dc_ra_mer_invest_ext t where 1=1 and t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("F4".equals(altitemcode)){ //执行事务合伙人/委派代表变更
			sql.append("select t.persname as content, t.bgtype from V_OUTER_QY_ZHUYAORENYUANBIAO t where t.licflag = 'hhrxx' and t.regino = ?");
			params.add(regino);
		}else if("43".equals(altitemcode)){ //指定联系人变更
			sql.append("select t.persname as content, t.bgtype from dc_ra_mer_persons_ext t where t.licflag = 'gslly' and t.regino = ?");
			params.add(regino);
		}else if("44".equals(altitemcode)){ //前置许可信息变更
			sql.append("select wm_concat(t.licname||'('||t.licno||')') as content, t.bgtype from dc_ra_mer_license_ext t where t.regino = ? group by t.bgtype");
			params.add(regino);
		}else if("72".equals(altitemcode)){ //分公司备案变更
			sql.append("select t.brname as content, t.bgtype from dc_ra_mer_branches_ext t where t.regino = ?");
			params.add(regino);
		}else{
			return null;
		}
		List<Map> resultList =  dao.queryForList(sql.toString(), params);
		
		
		if("E9".equals(altitemcode)) {
			String getZhiShiAndWpdbCount ="select count(1) as count  from v_qy_chuzi_xinxi2 t where t.pripid= ? and t.regino = ? and zshhr = '是'";
			List paramsZhiShiAndWpdbCountParam = new ArrayList();
			paramsZhiShiAndWpdbCountParam.add(id);
			paramsZhiShiAndWpdbCountParam.add(regino);
			
			String count =  dao.queryForList(getZhiShiAndWpdbCount, paramsZhiShiAndWpdbCountParam).get(0).get("count").toString();
			int nums = Integer.valueOf(count);
			if(nums>0) {
				 String sql2  ="select inv,subconam,conprop,zshhr from v_qy_chuzi_xinxi2 t where t.pripid= ? and t.regino = ? ";
					ids.add(id);
					ids.add(regino);
					List<Map> list =  dao.queryForList(sql2, ids);
					Map addMap = null;
					
					addMap = new HashMap(); 
					
				if(resultList!=null&&resultList.size()>0) {
					try {
						addMap  = resultList.get(1);
					} catch (Exception e) {
						addMap  = resultList.get(0);
					}
					
				
					String contents = (String) addMap.get("content");
					String [] tempCon = contents.split(",");
					//System.out.println("=========> " +list.size());
					Map ret = null;
					StringBuffer retSb = new StringBuffer();
					for (int i = 0; i < tempCon.length; i++) {
						for (int j = 0; j < list.size(); j++) {
							ret = (Map) list.get(j);
							//System.out.println(ret);
							String inv = (String) ret.get("inv");
							BigDecimal conprop = (BigDecimal) ret.get("conprop");
							BigDecimal subconam = (BigDecimal) ret.get("subconam");
							String  zshhr = (String) ret.get("zshhr");
							
							if(inv.equals(tempCon[i])) {
								//System.out.println(zshhr);
								retSb.append(tempCon[i] +"  "+subconam +"（万元）" + conprop+"% <br/>");
							}
							
						}
					}
					Map mapReturn = null;
					try {
						 mapReturn = resultList.get(1);
					} catch (Exception e) {
						 mapReturn = resultList.get(0);
						 
						 EntErrorLog logInfo = new EntErrorLog();
						 
						 logInfo.setId(idParent);
						 logInfo.setPripid(pripidParent);
						 logInfo.setMaintbid(main_tb_id);
						 logInfo.setRegino(regino);
						 
						 logInfo.setItemcode(altitemcode);
						 logInfo.setMsg(e.getMessage());
						 logInfo.setLogDetail(mapReturn.toString());
						 logInfo.setDate(logDate);
						 
						
						 logService.logMsg(logInfo);
						 
						e.getStackTrace();
					}
					
					mapReturn.put("content", retSb);
					resultList.add(mapReturn);
				}
			}
			
			
		}
		return util.typechage(resultList);
		//return resultList;
		
	}

	
	
	public void insert(int pageSize,int pageNo,String queryParams) throws OptimusException, ParseException {
		 List<AltInfoBo> upList = new ArrayList();
		 AltInfoBo alt =  null;
		 System.out.println("====> pageSize"  +pageSize   + "  ,=====>" + pageNo );
			List<Map> firtBianGenLei = this.queryBiangengDetail(pageSize,pageNo,queryParams);
			for (int j = 0; j < firtBianGenLei.size(); j++) {
				Map firtMap = firtBianGenLei.get(j);
				//System.out.println("一类变更同步列表============>" +firtMap);
				
				String describe =  (String) firtMap.get("describe");
				String altbe =  (String) firtMap.get("altbe");
				String altaf = (String) firtMap.get("altaf");
				
				String altitemCnPatent = (String) firtMap.get("altitem");
				String altitemcodeParent = (String) firtMap.get("altitemcode");
				
				
				String regino = (String) firtMap.get("regino");
				String main_tb_id = (String) firtMap.get("mainTbId");
				String idParent = (String) firtMap.get("id");
				String pripidParent = (String) firtMap.get("pripid");
				String dateParent =  (String) firtMap.get("altdate");
				
				
				if(altbe!=null&&altbe.length()>=1300) {
					altbe = altbe.substring(0,1300);
				}
				if(altaf!=null&&altaf.length()>=1300) {
					altaf = altaf.substring(0,1300);
				}
				
				if(describe!=null) {
					if(!describe.equals("2")) {
						alt = new AltInfoBo();
						alt.setAltid(idParent);
						alt.setPripid(pripidParent);
						alt.setAltitem(altitemcodeParent);
						alt.setAltitem_cn(altitemCnPatent);
						alt.setAltbe(altbe);
						alt.setAltaf(altaf);
						alt.setAltdate(dateParent);
						upList.add(alt);
					} 
					
					if(describe.equals("2")) {
						try {
							///二类变更
							List<Map> list = this.queryBiangengDetail2(altitemcodeParent,regino,main_tb_id,idParent,pripidParent,dateParent);
							StringBuffer altBefSb = new StringBuffer();
							StringBuffer altAftSb = new StringBuffer();
							
							String altBef = "";
							String  altAft= "";
							if(list!=null&&list.size()>0) {
								for (int k = 0; k < list.size();k++) {
									Map map = list.get(k);
									//System.out.println("二类变更同步列表============>" +map);
									try {
										String bgtype = (String)map.get("bgtype");
										String contents = (String) map.get("content");
										
										
										if("1".equals(bgtype)) {
											altBefSb.append(contents);
										}
										
										if("2".equals(bgtype)) {
											altAftSb.append(contents);
										}
										
										
										altBef = altBefSb.toString();
										altAft = altAftSb.toString();
										
										if(altBef!=null&&altBef.length()>=1300) {
											altBef = altBef.substring(0,1300);
										}
										if(altAft!=null&&altAft.length()>=1300) {
											altAft = altAft.substring(0,1300);
										}
										
										
										if(null==contents) {
											altBef = "";
											altAft = "";
										}
										
										
									} catch (Exception e) {
										
										 EntErrorLog logInfo = new EntErrorLog();
										 
										 logInfo.setId(idParent);
										 logInfo.setPripid(pripidParent);
										 logInfo.setMaintbid(main_tb_id);
										 logInfo.setRegino(regino);
										 
										 logInfo.setItemcode(altitemcodeParent);
										 logInfo.setMsg(e.getMessage());
										 logInfo.setLogDetail(map.get("bgtype").toString());
										 logInfo.setDate(dateParent);
										 
										 logService.logMsg(logInfo);
										 
										 
										e.getStackTrace();
									}
								}
								
								
								alt = new AltInfoBo();
								alt.setAltid(idParent);
								alt.setPripid(pripidParent);
								alt.setAltitem(altitemcodeParent);
								alt.setAltitem_cn(altitemCnPatent);
								alt.setAltbe(altBef);
								alt.setAltaf(altAft);
								alt.setAltdate(dateParent);
								upList.add(alt);
							}
						} catch (Exception e) {
							
							 EntErrorLog logInfo = new EntErrorLog();
							 
							 logInfo.setId(idParent);
							 logInfo.setPripid(pripidParent);
							 logInfo.setMaintbid(main_tb_id);
							 logInfo.setRegino(regino);
							 
							 logInfo.setItemcode(altitemcodeParent);
							 logInfo.setMsg(e.getMessage());
							 logInfo.setLogDetail("");
							 logInfo.setDate(dateParent);
							 
							 logService.logMsg(logInfo);
							 
							
							e.printStackTrace();
						}
						
						
					}
				}
				
			}
		
			
			/*altInfoDao.insertBatch(upList);	*/
			
			if (upList != null && upList.size() > 0) {
				this.batchCommitToDB(1000, upList);
			}
			
	}
	
	
	
	private <T> void batchCommitToDB(int commitCountEveryTime, List<T> list) {
		int commitCount = (int) Math.ceil(list.size() / (double) commitCountEveryTime);
		List<T> tempList = new ArrayList<T>(commitCountEveryTime);
		int start, stop;
		Long startTime = System.currentTimeMillis();
		for (int i = 0; i < commitCount; i++) {
			tempList.clear();
			start = i * commitCountEveryTime;
			stop = Math.min(i * commitCountEveryTime + commitCountEveryTime - 1, list.size() - 1);
			for (int j = start; j <= stop; j++) {
				tempList.add(list.get(j));
			}
			altInfoDao.insertBatch(tempList);	
		}
	}
}
