package com.gwssi.trs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;



import com.gwssi.trs.model.TrsListEntity;
import com.gwssi.util.FreemarkerUtil;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.trs.client.TRSConnection;
import com.trs.client.TRSConstant;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

public class PageDataUtil {
	/*
	 * 总页数=总记录数/页面大小 Pageresult--->int totalPageCount
	 * 
	 * 后台： 总记录数 ----- Pageresult--->long totalCount 总记录数据 -----
	 * Pageresult--->List items;
	 * 
	 * 前台： 页面大小 ---- Pageresult--->int pageSize; 当前页 ---- Pageresult--->int
	 * pageNo;
	 */
	public Map pagesDate(int pages, int pagesize, String queryKeyWord, String labelFlag) throws OptimusException {
		int listbegin = 0;
		int listend = 0;
		TRSConnection conn = null;
		TRSResultSet rs = null;
		int pagescount = 0;
		PageResult pageResult = null;
		// 总记录数
		long recordCount = 0;
		long timeuseing=0;
		try {
			conn = TrsConnectionUtil.GetTrsConnection();
/*			rs = conn.executeSelect("demo2", "", "RELEVANCE", "", "", 0,
					TRSConstant.TCE_OFFSET, false);*/
			//企业名称、注册号，企业类型，成立日期、住所、地址、行业门类、登记机关、企业状态、经营范围、企业类别、法定代表人、邮编、电话、经营范围及方式、属地监管所、年检机关、旧注册号、投资人、证件号
			//判断是否符合日期格式
			//StringBuffer sel = new StringBuffer(Conts.SELREGINFO+"+=(" + queryKeyWord + ")");
			//StringBuffer sel = new StringBuffer(Conts.SELREGINFO+"+=include(");
			StringBuffer sel = new StringBuffer("bi=include(");
			//sel.append(queryKeyWord).append(",1)) or (").append(Conts.SELREGINFO1).append("+=%").append(queryKeyWord).append("%)");
			sel.append(queryKeyWord).append(",1)");
			/*if(DateUtil.isValidDate(queryKeyWord,"yyyyMMdd")){//符合
				sel.append(" or estdate=");
				sel.append(queryKeyWord.replace("-", "."));trs全文检索耗时：9462
				trs全文检索耗时：11352
			}*/
			long t1=new Date().getTime();
			rs = conn.executeSelect(Conts.SELTABREG, sel.toString(), "", "", "", 0,TRSConstant.TCE_OFFSET, false);
			//rs = conn.executeSelect(Conts.SELTABREG, sel.toString(), "-enttype;", "", "", 0,TRSConstant.TCE_OFFSET, false);
			//rs = conn.executeSelect(Conts.SELTABREG, sel.toString(), "RELEVANCE", "", "", 0,TRSConstant.TCE_OFFSET, false);
			//rs.sortResult("-enttype;RELEVANCE", true);-enttype;RELEVANCE
			long t2=new Date().getTime();
			System.out.println("trs全文检索耗时："+(t2-t1));
			timeuseing=t2-t1;
			rs.setReadOptions(TRSConstant.TCE_OFFSET, Conts.SELREGSHOW, ";");
			recordCount = rs.getRecordCount(1);
			System.out.println("总记录数："+recordCount );
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
			int i = 0;
			List items = new ArrayList();
            Map root = new HashMap();  
            List<TrsListEntity> trse = new ArrayList<TrsListEntity>();
			for (int size = 0; size < pagesize && size + (pages - 1) * pagesize < recordCount; size++) {

				//entity.setUrl("pid"+rs.getString("pid")+"entype"+rs.getString("r"));
				//entity.setUrl("page/reg/regDetail.jsp?priPid="+rs.getString("pid")+"&enttype="+rs.getString("r")+"&flag=0");
			/*	entity.setUrl("page/reg/regDetail.jsp");*/


				
				//？号传值的内容
/*				Map<String,String> searchValue = new HashMap<String,String>();
				searchValue.put("priPid", rs.getString("pid"));
				searchValue.put("enttype", this.replaceJS(rs.getString("r")));
				searchValue.put("flag", "0");*/
				
				//判断企业类型
/*				String enttype= this.replaceJS(rs.getString("r")); 			
				String etype=this.backEntype(enttype); 
				searchValue.put("economicproperty", etype);
				entity.setSearchValue(searchValue);*/
				
				
				TrsListEntity trsentity = new TrsListEntity();
				trsentity.setPid_pripid(rs.getString("pid"));
				trsentity.setA_entName(rs.getString("a","red"));
				trsentity.setB_regno(rs.getString("b", "red"));
				trsentity.setC_uniscid(rs.getString("c"));
				trsentity.setD_regstate(rs.getString("d"));
				trsentity.setE_cn_industryphy(rs.getString("e_"));
				trsentity.setE_industryphy(rs.getString("e"));
				trsentity.setF_cn_industryco(rs.getString("f_"));
				trsentity.setF_industryco(rs.getString("f"));
				trsentity.setG_name(rs.getString("g"));
				trsentity.setH_estdate(rs.getString("h"));
				trsentity.setI_cn_regorg(rs.getString("i_"));
				trsentity.setI_regorg(rs.getString("i"));
				trsentity.setJ(rs.getString("j"));
				trsentity.setK_opscope(rs.getString("k"));
				trsentity.setL_dom(rs.getString("l"));
				trsentity.setM(rs.getString("m"));
				trsentity.setN(rs.getString("n"));
				trsentity.setO(rs.getString("o"));
				trsentity.setP(rs.getString("p"));
				trsentity.setQ(rs.getString("q"));
				trsentity.setR_cn_enttype(rs.getString("r_"));
				trsentity.setR_enttype( this.replaceJS(rs.getString("r")));
				
				trsentity.setUrl("page/reg/regDetail.jsp");//跳转路径
				
				Map<String,String> searchValue = new HashMap<String,String>();
				searchValue.put("priPid", rs.getString("pid"));
				searchValue.put("enttype", this.replaceJS(rs.getString("r")));
				searchValue.put("flag", "0");		
				//判断企业类型
				String enttype= this.replaceJS(rs.getString("r")); 			
				String etype=this.backEntype(enttype); 
				searchValue.put("economicproperty", etype);
				trsentity.setChangeType(etype);
				//trsentity.setSearchValue(searchValue);
				
				trse.add(trsentity);
				rs.moveNext();
			
			}
		root.put("trslist", trse);
		String	returnString = FreemarkerUtil.returnString("trs_list.ftl", root);
		pageResult.setItems(items);
		pageResult.setFreeMReturnString(returnString);
		} catch (TRSException e) {
			System.out.println("ErrorCode: " + e.getErrorCode());
			System.out.println("ErrorString: " + e.getErrorString());
			e.printStackTrace();
			throw new OptimusException("trs库出错!");
		} catch (IOException e) {
			System.out.println("getMessage: " + e.getMessage());
			e.printStackTrace();
			throw new OptimusException("trs库出错!");
		} catch (Exception e) {
			System.out.println("getMessage: " + e.getMessage());
			e.printStackTrace();
			throw new OptimusException("trs库出错!");
		} finally {
			TrsConnectionUtil.release(rs, conn);
		}
		// 显示数据部分
		int recordbegin = (pages - 1) * pagesize;// 起始记录
		int recordend = 0;
		recordend = recordbegin + pagesize;
		// 最后一页记录显示处理
		if (pages == pagescount && pagescount % pagesize != 0) {
			recordend = (int) (recordbegin + (pagescount % pagesize));
		}

		Map map = new HashMap();
		map.put("listend", listend);
		map.put("listbegin", listbegin);
		map.put("data", pageResult);
		map.put("recordCount", recordCount);
		map.put("useingtime", timeuseing);
		return map;
	}
	
	/**
	 * 企业类型
	 * 内资 2  外资 3   集团 4  个体 1
	 * @return
	 */
	private String backEntype(String enttype){
		if(StringUtils.isNotBlank(enttype)){
	  		if(enttype.startsWith("1")||enttype.startsWith("2")||enttype.startsWith("3")||enttype.startsWith("4")){//内资企业
	  			//searchValue.put("economicproperty", "2");
	  			return "2";
	  		}else if(enttype.startsWith("5")||enttype.startsWith("6")||enttype.startsWith("7")){//外资企业
	  			//searchValue.put("economicproperty", "3");
	  			return "3";
	  		}else if(enttype.startsWith("9500")){//个体
	  			//searchValue.put("economicproperty", "1");
	  			return "1";
	  		}else if(enttype.startsWith("8")){//集团
	  			//searchValue.put("economicproperty", "4");
	  			return "4";
	  		}else{
	  		/*	System.out.println("错误企业类型---"+enttype);
	  			return "9999999";*/
	  			return "2"; //暂时先写成2
	  		}
							
		}else{
			return null;
		}
	}
	public String replaceJS(String str){
		String url=str;
		if(url == null)
		{
		url="";
		} else{ url = url.replace("\n\r", "");
		url = url.replace("\r\n", "");//这才是正确的！
		url= url.replace("\t", "");
		  url =url.replace(" ", "");
		   
		    url=url.replace("\"", "\\"+"\"");//如果原文含有双引号，这一句最关键！！！！！！
		    url = url.replaceAll("\r\n", "");
		    url=url.replaceAll("\n", "");
		}
		return url;
	}

}
