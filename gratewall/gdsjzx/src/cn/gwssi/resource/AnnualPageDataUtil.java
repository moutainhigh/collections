package cn.gwssi.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.trs.model.AnJianTrsEntity;
import cn.gwssi.trs.model.AnnualTrsEntity;

import com.gwssi.optimus.core.exception.OptimusException;
import com.trs.client.TRSConnection;
import com.trs.client.TRSConstant;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

public class AnnualPageDataUtil {
	private static  Logger log=Logger.getLogger(AnnualPageDataUtil.class);
	/*
	 * 总页数=总记录数/页面大小 Pageresult--->int totalPageCount
	 * 后台： 总记录数 ----- Pageresult--->long totalCount 总记录数据 -----
	 * Pageresult--->List items;
	 * 前台： 页面大小 ---- Pageresult--->int pageSize; 当前页 ---- Pageresult--->int
	 * pageNo;
	 */
	public Map<String, Object> pagesDate(int pages, int pagesize, String queryKeyWord, String labelFlag) throws OptimusException {
		int listbegin=0,listend = 0,pagescount = 0;
		TRSConnection conn = null;
		TRSResultSet rs = null;
		PageResult pageResult = null;
		// 总记录数
		long recordCount = 0;
		Map<String, Object> map;
		try {
			/*StringBuffer sel = new StringBuffer(Conts.SELREGINFO+"+=(" + queryKeyWord + ")");
			  StringBuffer sel = new StringBuffer(Conts.SELREGINFO+"+=include(");sel.append(queryKeyWord).append(",1)) or (").append(Conts.SELREGINFO1).append("+=%").append(queryKeyWord).append("%)");
			  if(DateUtil.isValidDate(queryKeyWord,"yyyyMMdd")){
				sel.append(" or estdate=");
				sel.append(queryKeyWord.replace("-", "."));
			}
			rs = conn.executeSelect(Conts.SELTABREG, sel.toString(), "-enttype;RELEVANCE", "", "", 0,TRSConstant.TCE_OFFSET, false);
			rs.sortResult("-enttype;RELEVANCE", true);-enttype;RELEVANCE
			*/
			conn = TrsConnectionUtil.GetTrsConnection();
			StringBuffer sel = new StringBuffer("bi=include(");
			sel.append(queryKeyWord).append(",1)");
			long t1=new Date().getTime();
			rs = conn.executeSelect(Conts.SELANNUAL, sel.toString(), "RELEVANCE", "", "", 0,TRSConstant.TCE_OFFSET, false);
			long t2=new Date().getTime();
			log.info("trs全文检索耗时："+(t2-t1));
			rs.setReadOptions(TRSConstant.TCE_OFFSET, Conts.SELNDSHOW, ";");
			recordCount = rs.getRecordCount(1);
			if (pageResult == null) {
				// 总页数=总记录数/页大小
				pageResult = new PageResult(recordCount, pagesize);
				pageResult.setPageNo(pages);
			}
			rs.setPageSize(pagesize);
			rs.setPage(pages);
			pagescount = rs.getPageCount();

			if (pagescount < pages) {
				pages = pagescount;// 如果分页变量大总页数，则将分页变量设计为总页数
			}
			if (pages < 1) {
				pages = 1;// 如果分页变量小于１,则将分页变量设为１
			}

			if (pages <= pagesize / 2 + 1) {
				listbegin = 1;
				listend = pagesize;
			} else if (pages > pagesize / 2 + 1) {
				listbegin = pages - pagesize / 2;
				listend = pages + pagesize / 2 - 1;
			}
			// 对pageEnd 进行校验，并重新赋值
			if (listend > pagescount) {
				listend = pagescount;// 有问题
			}
			if (listend <= pagesize) {// 当不足pageNum数目时，要全部显示，所以pageStart要始终置为1
				listbegin = 1;
			}
			//int i = 0;
			List<Object> items = new ArrayList<Object>();
			for (int size = 0; size < pagesize && size + (pages - 1) * pagesize < recordCount; size++) {
				
			//entName,regNo,entType,tel,addr,email,busSt,department,pripid,inv,invName,invType,anCheYear
			//EntName   RegNo  EntType  Tel   Addr     Email  InvName	InvType	 AnCheYear	
				AnnualTrsEntity entity = new AnnualTrsEntity();
				entity.setEntName(rs.getString("entName","red"));
				entity.setRegNo(rs.getString("regNo","red"));
				entity.setEntType(rs.getString("entType","red"));
				entity.setTel(rs.getString("tel","red"));
				entity.setAddr(rs.getString("addr","red"));
				entity.setEmail(rs.getString("email","red"));
				entity.setBusSt(rs.getString("busSt","red"));
				//entity.setDepartment(rs.getString("department","red"));
				entity.setPripid(rs.getString("pripid","red"));
				entity.setInvName(rs.getString("invName","red"));
				entity.setAnCheYear(rs.getString("anCheYear","red"));
				entity.setUrl(rs.getString("url","red"));
				rs.moveNext();
				items.add(entity);
			}
			pageResult.setItems(items);
			
			// 显示数据部分
			int recordbegin = (pages - 1) * pagesize;// 起始记录
			int recordend = 0;
			recordend = recordbegin + pagesize;
			// 最后一页记录显示处理
			if (pages == pagescount && pagescount % pagesize != 0) {
				recordend = (int) (recordbegin + (pagescount % pagesize));
			}
			map = new HashMap<String, Object>();
			map.put("listend", listend);
			map.put("listbegin", listbegin);
			map.put("data", pageResult);
		} catch (TRSException e) {
			log.info("ErrorCode: " + e.getErrorCode());
			log.info("ErrorString: " + e.getErrorString());
			e.printStackTrace();
			throw new OptimusException("trs库出错!");
		} catch (IOException e) {
			log.info("getMessage: " + e.getMessage());
			e.printStackTrace();
			throw new OptimusException("trs库出错!");
		} catch (Exception e) {
			log.info("getMessage: " + e.getMessage());
			e.printStackTrace();
			throw new OptimusException("trs库出错!");
		} finally {
			TrsConnectionUtil.release(rs, conn);
		}
		return map;
	}

}
