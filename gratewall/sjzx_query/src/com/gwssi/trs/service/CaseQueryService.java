package com.gwssi.trs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.trs.PageResult;
import com.trs.client.Date;

@Service("caseQueryService")
public class CaseQueryService extends BaseService {

	/**
	 * 全文检索详细 信息库
	 * 
	 * @return
	 */
	private static String getDetail_datasourcekey() {
		Properties properties = ConfigManager.getProperties("optimus");

		String key = properties.getProperty("regDetail.datasourcekey");

		return key;
	}
   
	/**
	 * 案件移送信息
	 */
	public Object queryanjianyisongxx(String casetranid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_case_cf_trans t where t.CASETRANID = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(casetranid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
    /**
     * 案件案源信息
     */
	public Object queryanyuanxx(String casesrcid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_CASE_CF_SRCINF t where t.casesrcid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(casesrcid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);
	} 
	
	
	
	 /**
     * 案件当事人信息
     */
	public Object querydangshirenxx(String casepartyid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_CASE_CF_PARTYINFO t where t.casepartyid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(casepartyid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	 /**
     * 案件违法行为及处罚信息
     */
	public Object queryweifaxx(String illegactid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_CASE_CF_IRREGPUNISHINFO t where t.illegactid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(illegactid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	 /*
     * 内资外资股权出质信息
     */
	public Object querynzwzgqchuzhixx(String illegactid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_guquan_chuzhi t where t.illegactid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(illegactid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	
	 /*
     * 内资外资注销信息
     */
	public Object querynzwzzhuxiaoxx(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_zhuxiaoxx t where t.pripid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	 /*
     * 内资外资吊销信息
     */
	public Object querynzwzdiaoxiaoxx(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_diaoxiaoxx t where t.pripid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	} 
	
	
	/*
     * 变更信息
     */
	public Object querybiangengxx(String altid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from E_GT_ALTER_RECODER t where t.altid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(altid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);
		// dao.queryByKey(arg0, arg1)

	}
	
	
	/*
	 * 内资外资许可信息
	 */
	public Object querynzwzxukexx(String licid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_xukexx t where t.licid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(licid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		if (list == null) {
			System.out.println("list  null------");
			return null;
		} else {
			System.out.println(list);
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
	}
	
	
	/*
     * 内资外资迁移信息
     */
	public Object querynzwzqianyixx(String id) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_qianru_qinachu_xx t where id = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(id);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 内资外资清算信息
     */
	public Object querynzwzqingsuanxx(String liid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from V_QY_NZWZ_QINGSUAN_CHENGYUANXX s where t.liid = ?");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(liid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 内资外资财务负责信息
     */
	public Object querynzwzcaiwufuzexx(String fpid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_caiwu_fuzexx t where fpid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(fpid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 内资外资联络信息
     */
	public Object querynzwzlianluoxx(String lmid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_nzwz_lianluo_yuanxx t where t.lmid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(lmid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	/*
     * 集团成员信息
     */
	public Object queryjituanchengyuanxx(String memid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_jituan_chengyuan_xx t where t.memid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(memid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 集团变更信息
     */
	public Object queryjituanbiangengxx(String altid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_jituan_biangeng_xx t where t.altid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(altid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 集团注销信息
     */
	public Object queryjituanzhuxiaoxx(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_jituan_zhuxiao_xx t where t.pripid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体经营信息
     */
	public Object querygetijinyinxx(String casepartyid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_gt_jinyin t where t.casepartyid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(casepartyid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体许可信息
     */
	public Object querygetixukexx(String licid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_geti_xuke_xx t where t.licid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(licid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体变更信息
     */
	public Object querygetibiangengxx(String altid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_gt_biangeng_xx t where t.altid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(altid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体注销信息
     */
	public Object querygetizhuxiaoxx(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_geti_zhuxiao_xx t where t.pripid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
	/*
     * 个体吊销信息
     */
	public Object querygetidiaoxiaoxx(String pripid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO(getDetail_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_qy_geti_diaoxiao_xx t where t.pripid = ? ");
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(pripid);
		List<Map> list = dao.queryForList(sql.toString(), paramlist);
		return list.get(0);

	}
	
	
}
