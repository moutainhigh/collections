package com.ye.dao;

import java.util.List;
import java.util.Map;

import com.bo.domain.SMSStock;

public interface SMSStockDao {

	public List<SMSStock> selectAllSMSStockList();

	public List<SMSStock> getNoNameSMSStock();

	public List<SMSStock> getSMSStockByCodeOrName(String param);

	public int save(SMSStock stock);

	public int update(SMSStock stock);

	public SMSStock getSMSStockByStockCode(String code);

	public List<SMSStock> getZhangFuStock(Map map);

	public List<SMSStock> getDieFuStock(Map map);

}
