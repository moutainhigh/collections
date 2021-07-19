package com.gwssi.dw.dq.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

/**
 * 模板查询
 * @author zhengziguo
 *
 */
public class TemplatesDao
{
	private String connType = "2";

	public TemplatesDao(String connType)
	{
		super();
		this.connType = connType;
	}

    /**
     * 计算列表的长度
     * @return
     * @throws DBException
     */
    public List templateCount() throws DBException{
    	List count;
    	String sql = "select count(*) as count from DQ_TEMPLATE";
    	count=DbUtils.select(sql, this.connType);
		return count;
    }
	/**
	 * 模板删除
	 * @param templateId
	 * @return
	 * @throws DBException
	 * @throws SQLException
	 */
	public boolean delTemplates(String templateId) throws DBException,
			SQLException
	{
		String sql = "delete from DQ_TEMPLATE where TEMPLATE_ID='" +
				templateId +
				"'";
		int n = DbUtils.execute(sql, this.connType);
		if (n > 0)
			return true;
		else
			return false;
	}
	/**
	 * 用rownum分页
	 * @param propertyName
	 * @param pager
	 * @param pageIndex
	 * @return
	 * @throws DBException
	 */
	public List searchTemplate(String propertyName ,String pager , String pageIndex) throws DBException{
		List dataList;
		int pagerInt = Integer.parseInt(pager);
		int pageIndexInt = Integer.parseInt(pageIndex);
		String sql = "select TEMPLATE_NAME as TEMPLATENAME,TEMPLATE_ID as TEMPLATEID,TARGET_MEMO as TEMPLATEDESC "
	                + "from DQ_TEMPLATE" 
	                + " where  TEMPLATE_NAME like '%"
		            + propertyName+"%' order by TEMPLATE_NAME"; 
		String sql1 = "select * from ( "
			+ " select row_.*, rownum rownum_ "
			+  "        from ("+sql+") row_ "
			+  " where rownum <= "+(pageIndexInt*pagerInt)+") "
			+  "where rownum_ >"+(pagerInt*(pageIndexInt-1));
		dataList=DbUtils.select(sql1, this.connType);
		return dataList;
	}
	
	/**
	 * 模板查询列表
	 * @param pager 
	 * @param pageIndex
	 * @return
	 * @throws DBException
	 */
	public List searchTemplate(String pager , String pageIndex) throws DBException{
		List dataList;
		int pagerInt = Integer.parseInt(pager);
		int pageIndexInt = Integer.parseInt(pageIndex);
		
		String sql = "select TEMPLATE_NAME as TEMPLATENAME,TEMPLATE_ID as TEMPLATEID,TARGET_MEMO as TEMPLATEDESC "
	                + "from DQ_TEMPLATE" 
	                + " order by TEMPLATE_NAME ";
		String sql1 = "select * from ( "
				+ " select row_.*, rownum rownum_ "
				+  "        from ("+sql+") row_ "
				+  " where rownum <=  "
				+(pageIndexInt*pagerInt)
				+ ") "+" where rownum_ > "
				+(pagerInt*(pageIndexInt-1));
		dataList=DbUtils.select(sql1, this.connType);
		return dataList;
	}
	/**
	 * 计算List的长度
	 * @return
	 * @throws DBException
	 */
	public List searchTemplateCont() throws DBException{
		List dataList;
		String sql = "select TEMPLATE_NAME as TEMPLATENAME,TEMPLATE_ID as TEMPLATEID,TARGET_MEMO as TEMPLATEDESC "
	                + "from DQ_TEMPLATE" 
	                + " order by TEMPLATE_NAME ";
		sql = "select count(*) as count from ("+sql+")";
		dataList=DbUtils.select(sql, this.connType);
		return dataList;
	}
	/**
	 * 模板的模糊查询
	 * @param propertyName 模板名称
	 * @return
	 * @throws DBException
	 */
	public List searchTemplateCont(String propertyName) throws DBException{
		List dataList;
		String sql = "select TEMPLATE_NAME as TEMPLATENAME,TEMPLATE_ID as TEMPLATEID,TARGET_MEMO as TEMPLATEDESC "
	                + "from DQ_TEMPLATE" 
	                + " where  TEMPLATE_NAME like '%"
		            + propertyName+"%' order by TEMPLATE_NAME"; 
		sql = "select count(*) as count from ("+sql+")";
		dataList=DbUtils.select(sql, this.connType);
		return dataList;
	}
	/**
	 * 模板的数据组装
	 * @param propertyName
	 * @param pager
	 * @param pageIndex
	 * @return
	 * @throws DBException
	 */
	public Map searchTemp(String propertyName,String pager,String pageIndex) throws DBException{
		Map map = new HashMap();
		List dataList;
		List countList ;
		if(propertyName == null){
			dataList = searchTemplate(pager,pageIndex);
			countList = searchTemplateCont();
		}else{
			dataList = searchTemplate(propertyName,pager,pageIndex);
			countList = searchTemplateCont(propertyName);
		}
		Map countMap = (Map)countList.get(0);
		int count = Integer.parseInt(String.valueOf(countMap.get("COUNT")));
		map.put("totalCount", String.valueOf(count));
		map.put("records", dataList);
		return map;
	}
	
}
