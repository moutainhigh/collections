package cn.gwssi.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.gwssi.trs.model.AnJianTrsEntity;
import cn.gwssi.trs.model.AnnualTrsEntity;
import cn.gwssi.trs.model.CaseInfoTrsEntity;
import cn.gwssi.trs.model.TrsEntity;

import com.gwssi.optimus.core.exception.OptimusException;
import com.trs.client.TRSConnection;
import com.trs.client.TRSConstant;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

public class PageDataUtil {
	private static  Logger log=Logger.getLogger(PageDataUtil.class);
	/**
	 * 市场主体 检索
	 * 总页数=总记录数/页面大小 Pageresult--->int totalPageCount
	 * 后台： 总记录数 ----- Pageresult--->long totalCount 总记录数据 -----
	 * Pageresult--->List items;
	 * 前台： 页面大小 ---- Pageresult--->int pageSize; 当前页 ---- Pageresult--->int
	 * pageNo;
	 * @param pages
	 * @param pagesize
	 * @param queryKeyWord
	 * @param labelFlag
	 * @return
	 * @throws OptimusException
	 */
	public Map<String, Object> pagesData(int pages, int pagesize, String queryKeyWord, String labelFlag) throws OptimusException {
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
			rs = conn.executeSelect(Conts.SELTABREG, sel.toString(), "RELEVANCE", "", "", 0,TRSConstant.TCE_OFFSET, false);
			long t2=new Date().getTime();
			log.info("trs全文检索耗时："+(t2-t1));
			rs.setReadOptions(TRSConstant.TCE_OFFSET, Conts.SELREGSHOW, ";");
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
				TrsEntity entity = new TrsEntity();
				entity.setUniscid(rs.getString("uniscid","red"));
				entity.setEntNameColor(rs.getString("entname","red"));
				entity.setEntName(rs.getString("entname"));
				entity.setRegNo(rs.getString("regno","red"));
				entity.setEntState(rs.getString("entstate"));
				entity.setEstDate(rs.getString("estdate"));
				entity.setEntType(rs.getString("enttype"));
				entity.setDom(rs.getString("dom","red"));
				entity.setIndustryPhy(rs.getString("industryphy"));
				entity.setRegOrg(rs.getString("regorg"));
				entity.setOpState(rs.getString("opstate"));
				entity.setOpScope(rs.getStringWithCutsize("opscope", 90, "red"));
				//rs.getString("opscope","red")
				entity.setLeRep(rs.getString("lerep","red"));
				entity.setOpScoAndForm(rs.getString("opscoandform"));
				String str = rs.getStringWithCutsize("inv", 150, "red");
		        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
		        Matcher matcher = pattern.matcher(str);
		        while(matcher.find()){
		        	String oldStri=matcher.group();
		        	if(oldStri.length()==15 || oldStri.length()==18){
		        		str = str.replace(oldStri,oldStri.substring(0,6)+"****"+oldStri.substring(14,oldStri.length()));
		        	}
		        }
				entity.setInv(str);
				//rs.getString("inv","red")
				entity.setUrl(rs.getString("url"));
				entity.setIndustryCo(rs.getString("industryco","red"));
				entity.setBgsx(rs.getStringWithCutsize("bgsx", 150, "red"));
				//rs.getString("bgsx","red")
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
	
	/**
	 * 12315 检索
	 * 总页数=总记录数/页面大小 Pageresult--->int totalPageCount
	 * 后台： 总记录数 ----- Pageresult--->long totalCount 总记录数据 -----
	 * Pageresult--->List items;
	 * 前台： 页面大小 ---- Pageresult--->int pageSize; 当前页 ---- Pageresult--->int
	 * pageNo;
	 * @param pages
	 * @param pagesize
	 * @param queryKeyWord
	 * @param labelFlag
	 * @return
	 * @throws OptimusException
	 */
	public Map<String, Object> pagesAjData(int pages, int pagesize, String queryKeyWord, String labelFlag) throws OptimusException {
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
			rs = conn.executeSelect(Conts.SELANJIAN, sel.toString(), "RELEVANCE", "", "", 0,TRSConstant.TCE_OFFSET, false);
			long t2=new Date().getTime();
			log.info("trs全文检索耗时："+(t2-t1));
			rs.setReadOptions(TRSConstant.TCE_OFFSET, Conts.SELAJSHOW, ";");
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
				AnJianTrsEntity entity = new AnJianTrsEntity();
				//ajid,applidique,accregper,regtime,accsce,acctime,keyword,pname,paddr,ptel,
				//enttype,entaddr,enttel,regdep,maintel,mainaddr,maindept,invopt
				//,mdsename,atime,btime,url
				entity.setAjid(rs.getString("ajid","red"));
				entity.setApplidique(rs.getString("applidique","red"));
				entity.setAccregper(rs.getString("accregper","red"));
				entity.setRegtime(rs.getString("regtime","red"));
				entity.setAccsce(rs.getString("accsce","red"));
				entity.setAcctime(rs.getString("acctime","red"));
				entity.setKeyword(rs.getString("keyword","red"));
				entity.setPname(rs.getString("pname","red"));
				entity.setPaddr(rs.getString("paddr","red"));
				entity.setPtel(rs.getString("ptel","red"));
				entity.setEntaddr(rs.getString("entaddr","red"));
				entity.setEnttel(rs.getString("enttel","red"));
				entity.setRegdep(rs.getString("regdep","red"));
				entity.setInvopt(rs.getString("invopt","red"));
				entity.setMdsename(rs.getString("mdsename","red"));
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

	/**
	 * 年度报告 检索
	 * 总页数=总记录数/页面大小 Pageresult--->int totalPageCount
	 * 后台： 总记录数 ----- Pageresult--->long totalCount 总记录数据 -----
	 * Pageresult--->List items;
	 * 前台： 页面大小 ---- Pageresult--->int pageSize; 当前页 ---- Pageresult--->int
	 * pageNo;
	 * @param pages
	 * @param pagesize
	 * @param queryKeyWord
	 * @param labelFlag
	 * @return
	 * @throws OptimusException
	 */
	public Map<String, Object> pagesAnnualData(int pages, int pagesize, String queryKeyWord, String labelFlag) throws OptimusException {
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
				entity.setAnCheYears(rs.getString("anCheYears","red"));
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
	
	/**
	 * 案件信息 检索
	 * 总页数=总记录数/页面大小 Pageresult--->int totalPageCount
	 * 后台： 总记录数 ----- Pageresult--->long totalCount 总记录数据 -----
	 * Pageresult--->List items;
	 * 前台： 页面大小 ---- Pageresult--->int pageSize; 当前页 ---- Pageresult--->int
	 * pageNo;
	 * @param pages
	 * @param pagesize
	 * @param queryKeyWord
	 * @param labelFlag
	 * @return
	 * @throws OptimusException
	 */
	public Map<String, Object> pagesCaseData(int pages, int pagesize, String queryKeyWord, String labelFlag) throws OptimusException {
		int listbegin=0,listend = 0,pagescount = 0;
		TRSConnection conn = null;
		TRSResultSet rs = null;
		PageResult pageResult = null;
		// 总记录数
		long recordCount = 0;
		Map<String, Object> map;
		try {
			conn = TrsConnectionUtil.GetTrsConnection();
			StringBuffer sel = new StringBuffer("bi=include(");
			sel.append(queryKeyWord).append(",1)");
			long t1=new Date().getTime();
			rs = conn.executeSelect(Conts.SELCASEINFO, sel.toString(), "RELEVANCE", "", "", 0,TRSConstant.TCE_OFFSET, false);
			long t2=new Date().getTime();
			log.info("trs全文检索耗时："+(t2-t1));
			rs.setReadOptions(TRSConstant.TCE_OFFSET, Conts.CASEINFOSHOW, ";");
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
				CaseInfoTrsEntity entity = new CaseInfoTrsEntity();
				entity.setCaseid(rs.getString("caseid"));
				entity.setCasename(rs.getString("casename","red"));//案件名
				entity.setEntnameurl(rs.getString("casename"));//案件名
				entity.setCaseno(rs.getString("caseno","red"));//
				entity.setEntname(rs.getString("entname","red"));
				entity.setCasestate(rs.getString("casestate","red"));//--案件状态(0:销案1：已结案2：未结案）
				entity.setCasetype(rs.getString("casetype","red"));//--案件类型(1-般案件，2-简易案件，3-特殊案件)
				
				entity.setCasescedistrict(rs.getString("casescedistrict","red"));
				entity.setCasespot(rs.getString("casespot","red"));//案发地
				entity.setCasetime(rs.getString("casetime","red"));//--案发时间
				entity.setCasereason(rs.getStringWithCutsize("casereason",150,"red"));//案由
				entity.setCaseval(rs.getString("caseval","red"));//--案值
				entity.setAppprocedure(rs.getString("appprocedure","red"));//--适用程序
				entity.setCaseinternetsign(rs.getString("caseinternetsign","red"));//--是否利用网络
				entity.setCaseforsign(rs.getString("caseforsign","red"));//--是否涉外案件
				entity.setCasefiauth(rs.getString("casefiauth","red"));//--立案机关
				entity.setCasefidate(rs.getString("casefidate","red"));//--立案日期
				entity.setExedate(rs.getString("exedate","red"));//--执行日期
				entity.setExesort(rs.getString("exesort","red"));//--执行类别
				entity.setUnexereasort(rs.getString("unexereasort","red"));//--未执行原因类别
				entity.setCaseresult(rs.getString("caseresult","red"));//--案件结果(1：受理2：不予受理3：告知9：其它）
				entity.setCasedep(rs.getString("casedep","red"));//--办案机构
				entity.setClocaserea(rs.getString("clocaserea","red"));//销案理由
				entity.setClocasedate(rs.getString("clocasedate","red"));//--销案日期
				entity.setSourceflag(rs.getString("sourceflag","red"));
				entity.setUrl(rs.getString("url"));
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
