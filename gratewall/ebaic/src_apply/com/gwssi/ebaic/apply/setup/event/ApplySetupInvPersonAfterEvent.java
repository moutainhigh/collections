package com.gwssi.ebaic.apply.setup.event;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.ebaic.torch.event.OnEventListener;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.domain.TorchRequest;
import com.gwssi.torch.domain.edit.EditConfigBo;
import com.gwssi.torch.domain.query.QueryConfigBo;
import com.gwssi.torch.util.ThreadLocalManager;
import com.gwssi.torch.util.UUIDUtil;
import com.gwssi.torch.web.TorchController;

@Service("applySetupInvPersonAfterEvent")
public class ApplySetupInvPersonAfterEvent implements OnEventListener{

	
	public void execEdit(TorchController controller,
			Map<String, String> params, EditConfigBo editConfigBo, Object result) {
		
		TorchRequest tReq = (TorchRequest)ThreadLocalManager.get(ThreadLocalManager.TORCH_REQUEST);
		Map<String,String>  tReqmap = tReq.getParams();
		if(tReqmap == null || tReqmap.isEmpty()){
			throw new EBaicException("数据异常，请联系管理员");
		}

		String gid= tReqmap.get("gid");
		String invId = tReqmap.get("investorId");
		
		if(StringUtils.isEmpty(gid)){
			throw new EBaicException("GID为空，数据异常，请联系管理员");
		}
		if(StringUtils.isEmpty(invId)){
			throw new EBaicException("股东ID为空，数据异常，请联系管理员");
		}
		String tReqJson = tReqmap.get("stagespay");
		if(StringUtils.isEmpty(tReqJson)){
			throw new EBaicException("股东出资信息为空，数据异常，请联系管理员");
		}
		
		/**
		 * chaiyoubing 2016-07-13  new add method checkInvestorPayTime  
		 *   检查出资时间：大于等于名称核准通过日期，小于等于名称核准通过日期+营业期限。
		 */
		String stagespay = tReqmap.get("stagespay");//conDate
		JSONArray array =JSONArray.parseArray (stagespay);
		Iterator<Object> iterator = array.iterator();
		JSONObject condateObject = null;
		String payTime = null;
		while(iterator.hasNext()){
			condateObject =(JSONObject)iterator.next();
			payTime = condateObject.getString("conDate");
			try {
				checkInvestorPayTime(gid,payTime);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		
		delStagespay(invId);
		
		List<List<Object>> paramInsert = new ArrayList<List<Object>>();
		JSONArray ja = JSONArray.parseArray(tReqJson);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		double totals = 0d;
		int stagesNo = 0;//缴费期次
		if(ja!=null && ja.size()!=0){
			for (int i = 0; i < ja.size(); i++) {
				stagesNo++;
				List<Object> param = new ArrayList<Object>();
				JSONObject jo = ja.getJSONObject(i);
				String stagespayId = jo.getString("stagespayId");
				if(StringUtils.isEmpty(stagespayId)){
					stagespayId = UUIDUtil.getUUID();
				}
				String curActConAm = jo.getString("curActConAm");
				//累计出资金额
				BigDecimal decimal=new BigDecimal(curActConAm);
				totals+=decimal.doubleValue();
				String conForm = jo.getString("conForm");					
				Date date = null ;
				try {
					date = sdf.parse(jo.getString("conDate"));	
				} catch (ParseException e) {
					throw new EBaicException("解析数据异常，请联系管理员:"+e.getMessage(),e);
				} 
				Calendar conDate = Calendar.getInstance();
				conDate.setTime(date);
				param.add(stagespayId);
				param.add(curActConAm);
				param.add(conForm);
				param.add(conDate);
				param.add(gid);
				param.add(invId);
				param.add(now);
				param.add(stagesNo);
				paramInsert.add(param);
			}
		}
		
		//IPersistenceDAO dao = DAOManager.getPersistenceDAO();
		String sql = " insert into be_wk_stagespay(STAGESPAY_ID,CUR_ACT_CON_AM,CON_FORM,CON_DATE,GID,INVESTOR_ID,TIMESTAMP,STAGES_NO,STAGES_SIGN) values (?,?,?,?,?,?,?,?,'1') ";
		//dao.executeBatch(sql, paramInsert);
		DaoUtil.getInstance().executeBatch(sql, paramInsert);
		changeSaveState(invId,totals);
		
		//更新时间戳
		SubmitUtil.updateTimeStamp("be_wk_requisition", "gid", gid);
	}
	
	/**
	 * 删除投资人所有投资记录
	 * 
	 */
	public void delStagespay(String invId){
	
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from be_wk_stagespay where investor_id=? ");
		List<String>param = new ArrayList<String>();
		param.add(invId);
		DaoUtil.getInstance().execute(sql.toString(), param);
		
	}
	
	/**
	 * 更改股东信息保存状态
	 * @param gid
	 */
	public void changeSaveState(String invId,double subConAm){
		StringBuffer sql = new StringBuffer();
		sql.append("  update be_wk_investor i set i.modify_sign = '2',i.sub_con_am=? where i.investor_id = ? ");
		List<Object>param = new ArrayList<Object>();
		param.add(subConAm);
		param.add(invId);
		try {
			DaoUtil.getInstance().execute(sql.toString(), param);
		} catch (Exception e) {
			throw new EBaicException("执行更改股东信息保存状态异常，请联系管理员");
		}
	}
	
	/**
	 * 将json字符串转成实体List
	 * @param invStr json字符串
	 * @return
	 */
	public List<List<Object>> parseJson2List(String invStr,String gid,String invId) throws ParseException {
		List<List<Object>> paramInsert = new ArrayList<List<Object>>();
		if(StringUtils.isEmpty(invStr)){
			return paramInsert;
		}
		JSONArray ja = JSONArray.parseArray(invStr);
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		
		if(ja!=null && ja.size()!=0){
			
			for (int i = 0; i < ja.size(); i++) {
				List<Object> param = new ArrayList<Object>();
				JSONObject jo = ja.getJSONObject(i);
				String stagespayId = jo.getString("stagespayId");
				if(StringUtils.isEmpty(stagespayId)){
					stagespayId = UUIDUtil.getUUID();
				}
				String curActConAm = jo.getString("curActConAm");
				String conForm = jo.getString("conForm");					
				Date date = sdf.parse(jo.getString("conDate"));				
				Calendar conDate = Calendar.getInstance();
				conDate.setTime(date);
				param.add(stagespayId);
				param.add(curActConAm);
				param.add(conForm);
				param.add(conDate);
				param.add(gid);
				param.add(invId);
				param.add(now);
				paramInsert.add(param);
			}
		}
		
		return paramInsert;
		
	}
	/**
	 * 
	 * 检查出资时间：大于等于名称核准通过日期，小于等于名称核准通过日期+营业期限。
	 * 
	 * sql查询名称核准通过日期
	 * select c.check_date from nm_rs_name n left join nm_rs_transact t 
	 * on t.name_id = n.name_id
	 * left join nm_rs_check c on c.name_id = n.name_id
	 * where c.check_result = '4'--核准通过
	 * and t.busi_type_co = '10'
	 * and c.name_id = (select r.name_id from be_wk_requisition r where r.gid = '440fccc1c94e494ba0575d669babeeb8')
	 * @param gid
	 * @throws ParseException 
	 */
	public void checkInvestorPayTime(String gid,String payTime) throws ParseException{
		//0、准备变量
		StringBuffer sql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		
		//1、查询营业期限
		sql.append(" select r.trade_term from be_wk_ent r where r.gid = ?");
		params.add(gid);
		String tradeTerm = DaoUtil.getInstance().queryForOneString(sql.toString(), params);
		
		//2、查询名称核准通过日期
		sql.delete(0, sql.length());
		params.clear();
		params.add(tradeTerm);
		params.add(gid);
		sql.append(" select to_char(c.check_date,'yyyy-mm-dd') as date1,to_char(add_months(check_date, 12*?),'yyyy-MM-dd') date2 from nm_rs_name n left join nm_rs_transact t  ")
		   .append(" on t.name_id = n.name_id left join nm_rs_check c on c.name_id = n.name_id ")
		   .append(" where c.check_result = '4' ")
		   .append(" and t.busi_type_co = '10' ")
		   .append(" and c.name_id = (select r.name_id from be_wk_requisition r where r.gid = ?) ");
		Map<String, Object> row = DaoUtil.getInstance().queryForRow(sql.toString(), params);
		if(row==null){
			throw new EBaicException("通过该笔业务未查询到名称核准通过日期。");
		}
		String checkDate = (String)row.get("date1");
		String checkAndTradeTerm =(String)row.get("date2");//名称核准通过日期加上营业期限年长的日期
		
		/**
		 * 3、校验时间
		 * 如果  营业期限是长期，即tradeTerm 为空，那么出资时间只需大于名称核准时间即可；
		 * 如果  营业期限非长期，即tradeTerm不为空，那么payTime>checkDate&&payTime<checkDate+tradeTerm
		 */
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date payTimeTemp = sdf.parse(payTime);
		Date checkDateTemp = sdf.parse(checkDate);
		Date checkAndTradeTemp = null;
		if(StringUtil.isNotBlank(checkAndTradeTerm)){
			checkAndTradeTemp = sdf.parse(checkAndTradeTerm);
		}
		
		if(payTimeTemp.before(checkDateTemp)){
			String msg = "出资时间应该晚于名称核准通过日期（"+checkDate+"）。";
			throw new EBaicException(msg);
		}
		
		if(StringUtil.isNotBlank(tradeTerm)){//非长期
			if(payTimeTemp.after(checkAndTradeTemp)){//出资时间大于名称核准通过日期加上营业期限年长的日期。
//				String msg = "出资时间应该早于名称核准营业期限日期（"+checkAndTradeTemp+"）。";
				String msg = "出资时间应早于企业营业期限日期。";
				throw new EBaicException(msg);
			}
		}
		
	}
	
	public void execQuery(TorchController controller,
			Map<String, String> params, QueryConfigBo editConfigBo,
			Object result) {
		// TODO Auto-generated method stub
		
	}

	

}
