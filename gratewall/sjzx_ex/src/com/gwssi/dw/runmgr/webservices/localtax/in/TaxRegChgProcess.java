package com.gwssi.dw.runmgr.webservices.localtax.in;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gwssi.common.util.ResourceBundleUtil;
import com.gwssi.dw.runmgr.webservices.common.ProcessResult;
import com.gwssi.dw.runmgr.webservices.localtax.LocalTaxWSException;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GsWebServiceFactory;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GsWebServicePort_PortType;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.ReturnMultiTaxData;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.SWDJComparator;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.SWDJInfo;



public class TaxRegChgProcess extends Process
{
	private static Log log =  LogFactory.getLog(TaxRegChgProcess.class);
	
	public List resultList;
	public TaxRegChgProcess(Process next){
		resultList = new ArrayList();
		this.setProcess(next);
	}
	
	public void getData(ProcessCondition con,ProcessResult result) throws LocalTaxWSException
	{
		log.info("TaxRegChgProcess-getData start");
		try{
			con = getCondition(con,result);
			
			GsWebServicePort_PortType services = GsWebServiceFactory.createGsWebService();
			ReturnMultiTaxData taxData = services.getBG_QUERY(con.getStartDate(), con.getEndDate(), con
					.getStartNum(), con.getEndNum());
			if(taxData!=null&&result.returnCorrect(taxData.getFHDM())){
				if(result.hasData(taxData.getFHDM())){
					SWDJInfo[] SWDJ_INFO_ARRAY = taxData.getSWDJ_INFO_ARRAY();
					for(int i=0;i<SWDJ_INFO_ARRAY.length;i++){
						resultList.add(SWDJ_INFO_ARRAY[i]);
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
			log.error("采集变更数据：",e);
			throw new LocalTaxWSException("采集地税登记变更数据失败",e);
		}
		log.info("TaxRegChgProcess-getData end");
	}

	public void processData(ProcessResult result) throws LocalTaxWSException
	{
		log.info("TaxRegChgProcess-processData start");
		Collections.sort(resultList, new SWDJComparator());
		try{
			for (int i = 0; i < resultList.size(); i++) {
				SWDJInfo info = (SWDJInfo) resultList.get(i);
				result.getResultSql().add(info.toSWBGSql());
				String updateSql = info.toUpdateSWDJByBGSql();
				if (updateSql != null
						&& !"".equals(updateSql)) {
					result.getResultSql().add(updateSql);
				}
			}
			result.getCollectionLog().addLogDetail("getBG_QUERY", "查询时间段内变更信息", resultList.size());
		}catch(Exception e){
			log.error("处理变更数据生成sql",e);
			throw new LocalTaxWSException("保存地税变更数据失败！",e);
		}
		log.info("TaxRegChgProcess-processData end");
	}
	
}
