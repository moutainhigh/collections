package com.ye.dao;

import java.util.List;
import java.util.Map;

import com.bo.domain.Stock;

//excel  http://www.cnblogs.com/Charltsing/p/RTD.html
//https://www.nowapi.com/api/finance.stock_list

//http://www.zuidaima.com/share/3284639684004864.htm
//http://www.zuidaima.com/share/3493737675590656.htm
//http://www.zuidaima.com/share/3039330102692864.htm
//http://www.zuidaima.com/share/3155397967940608.htm
//https://www.cnblogs.com/han-1034683568/p/7196379.html

//http://blog.csdn.net/liuhaiabc/article/details/53899519
//http://blog.csdn.net/u014547764/article/details/50914424

//http://blog.csdn.net/column/details/dx-spring-0.html

//http://www.zuidaima.com/share/2479111514196992.htm

//http://www.zuidaima.com/share/2043055109475328.htm
//http://www.zuidaima.com/share/3155397967940608.htm

//http://362217990.iteye.com/blog/1769570

//http://www.zuidaima.com/share/2479111514196992.htm
public interface StockDao {
	public List<Stock> selectStockAllList();
	
	public List<Stock> getStockByCodeOrName(String param);
	public List<Stock> selectPubList();

	public List<Stock> selectAllRecentImpList(Map paramMap);

	public List<Stock> selectMostImpList(Map paramMap);

	public List<Stock> selectWaitImpList(Map paramMap);

	public List<Stock> selectLongHuList(Map paramMap);

	public List<Stock> selectNoNameStockList();

	public List<Stock> selectConsoleStockList(Map paramMap);

	public List<Stock> selectRecentImpAndPubList();

	public int save(Stock stock);

	public int update(Stock stock);

	public Stock getStockByStockCode(String code);

	public List<Stock> selectIf(int i);

	public List<Stock> imp(Map paramMap);
	public List<Stock> getZhangFuStock(Map paramMap);
	public List<Stock> getDieFuStock(Map paramMap);
	
}
