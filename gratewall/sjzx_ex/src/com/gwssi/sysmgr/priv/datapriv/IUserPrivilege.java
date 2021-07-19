package com.gwssi.sysmgr.priv.datapriv;

import java.util.List;

/**
 *    用户的访问许可接口，开发人员通过getRule得到条件运算符in、not in，通过
 * getPrivilegeList或getPrivilegeSql得到条件范围
 * @author 周扬
 * */
public interface IUserPrivilege {
	/**
	 * 获取许可规则 in(包含) not in(不包含)
	 * @return 返回许可规则
	 */
	public String getRule();
	/**
	 * 获取数据许可列表
	 * @return 数据许可列表
	 */
	public List getPrivilegeList();
	/**
	 *    获取数据许可查询SQL字符串，该SQL语句返回一个只包含一个Code字段的数据集，
	 * SQL字串格式如下：
	 *    select code from table where userid='ss'
	 * @return
	 */
	public String getPrivilegeSql();
}
