package com.gwssi.dw.runmgr.webservices.localtax.in;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gwssi.common.util.ResourceBundleUtil;
import com.gwssi.dw.runmgr.webservices.common.ProcessResult;
import com.gwssi.dw.runmgr.webservices.localtax.LocalTaxWSException;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GSDJInfo;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GsWebServiceFactory;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GsWebServicePort_PortType;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.ReturnMultiGSData;



public class TaxRegUnLockProcess extends Process
{
	private static Log log =  LogFactory.getLog(TaxRegUnLockProcess.class);
	
	public List resultList;
	public TaxRegUnLockProcess(Process next){
		resultList = new ArrayList();
		this.setProcess(next);
	}
	
	public void getData(ProcessCondition con,ProcessResult result) throws LocalTaxWSException
	{
		log.info("TaxRegUnLockProcess-getData start");
		try{
			con = getCondition(con,result);
						
			GsWebServicePort_PortType services = GsWebServiceFactory.createGsWebService();
			ReturnMultiGSData taxData = services.getJIESXX_QUERY(con.getStartDate(), con.getEndDate(), con
					.getStartNum(), con.getEndNum());
			if(taxData!=null&&result.returnCorrect(taxData.getFHDM())){
				if(result.hasData(taxData.getFHDM())){
					GSDJInfo[] GSDJ_INFO_ARRAY = taxData.getGSDJ_INFO_ARRAY();
					for(int i=0;i<GSDJ_INFO_ARRAY.length;i++){
						resultList.add(GSDJ_INFO_ARRAY[i]);
					}
				}

				String zts = (taxData.getZTS()==null||"".equals(taxData.getZTS()))?"0":taxData.getZTS();
				if (Integer.parseInt(zts)>=Integer.parseInt(con.getEndNum())) {
					con.setFlag(ProcessCondition.FLAG_OUTNUM);
					getData(con, result);
				}
				
				if(ProcessCondition.FLAG_OUTTIME.equals(con.getFlag())){
					getData(con,result);
				}
			}

		}catch(Exception e){
			log.error("采集解锁数据：",e);
			throw new LocalTaxWSException("采集地税登记解锁数据失败！",e);
		}
		log.info("TaxRegUnLockProcess-getData end");
	}

	public void processData(ProcessResult result) throws LocalTaxWSException
	{
		log.info("TaxRegUnLockProcess-processData start");
		try{
			for (int i = 0; i < resultList.size(); i++) {
				GSDJInfo info = (GSDJInfo)resultList.get(i);
				result.getResultSql().add(info.toJIESSql());
				result.getResultSql().add(info.toDeleteJIASSql());
			}
			result.getCollectionLog().addLogDetail("getJIESXX_QUERY", "查询时间段内解锁信息", resultList.size());
		}catch(Exception e){
			log.error("处理解锁数据生成sql：",e);
			throw new LocalTaxWSException("保存地税解锁数据失败！",e);
		}
		log.info("TaxRegUnLockProcess-processData end");
	}

}
