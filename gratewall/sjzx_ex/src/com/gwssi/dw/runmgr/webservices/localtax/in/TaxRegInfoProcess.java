package com.gwssi.dw.runmgr.webservices.localtax.in;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gwssi.common.util.ResourceBundleUtil;
import com.gwssi.dw.runmgr.webservices.common.ProcessResult;
import com.gwssi.dw.runmgr.webservices.localtax.LocalTaxWSException;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GsWebServiceFactory;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GsWebServicePort_PortType;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.ReturnMultiGSData;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.ReturnMultiTaxData;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.SWDJInfo;



public class TaxRegInfoProcess extends Process
{
	private static Log log =  LogFactory.getLog(TaxRegInfoProcess.class);
	public List resultList;
	public TaxRegInfoProcess(Process next){
		resultList = new ArrayList();
		this.setProcess(next);
	}
	
	public void getData(ProcessCondition con,ProcessResult result) throws LocalTaxWSException
	{
		log.info("TaxRegInfoProcess-getData start");
		try{
			con = getCondition(con,result);
			
			GsWebServicePort_PortType services = GsWebServiceFactory.createGsWebService();
			log.debug("开始记录数"+con.getStartNum());
			log.debug("结束记录数"+con.getEndNum());
			ReturnMultiTaxData taxData = services.getSWDJ_QUERY(con.getStartDate(), con.getEndDate(), con
					.getStartNum(), con.getEndNum());

			if(taxData!=null&&result.returnCorrect(taxData.getFHDM())){
				if(result.hasData(taxData.getFHDM())){
					SWDJInfo[] SWDJ_INFO_ARRAY = taxData.getSWDJ_INFO_ARRAY();
					log.debug("记录数"+SWDJ_INFO_ARRAY.length);
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
			
		}catch(LocalTaxWSException e){
			log.error("采集登记数据：",e);
			throw e;
		}catch(Exception ex){
			log.error("采集登记数据：",ex);
			throw new LocalTaxWSException("采集地税登记数据失败！",ex);
		}
		log.info("TaxRegInfoProcess-getData end");
	}

	public void processData(ProcessResult result) throws LocalTaxWSException
	{
		log.info("TaxRegInfoProcess-processData start");
		try{
			for (int i = 0; i < resultList.size(); i++) {
				SWDJInfo info = (SWDJInfo)resultList.get(i);
				result.getResultSql().add(info.toSWDJSql());
			}
			result.getCollectionLog().addLogDetail("getSWDJ_QUERY", "查询时间段内税务登记信息", resultList.size());
		}catch(Exception e){
			log.error("处理登记数据生成sql：",e);
			throw new LocalTaxWSException("保存地税登记信息数据失败！",e);
		}
		log.info("TaxRegInfoProcess-processData end");
	}
	
	
	public static void main(String arg[]){
		GsWebServicePort_PortType services;
		try {
			services = GsWebServiceFactory.createGsWebService();
			ReturnMultiTaxData taxData = services.getSWDJ_QUERY("20081105", "20081106", "1","2000");	
			System.out.println(taxData.getFHDM());
			System.out.println(taxData.getZTS());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
