package cn.gwssi.blog.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gwssi.optimus.core.persistence.datasource.DataSourceManager;

import cn.gwssi.blog.model.TPtSysLogBO;
import cn.gwssi.common.log.LogOperation;
import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.common.model.TPtFwrzxxxxBO;

public class BlogServer extends LogOperation{
	
	@Override
	public void execute(Object obj) {
		Connection con = null;
		PreparedStatement pstmt= null;
		try {
			int i = 0;
			StringBuffer sbf = new StringBuffer();
			if(obj instanceof TPtFwrzjbxxBO){//是否服务基本信息
				sbf.append("insert into T_PT_FWRZJBXX(fwrzjbId,callerName,callerTime,executeCase,executeWay,executeTime,callerParameter,executeResult,callerIP,executeType,callerenttime,calleer,fwmc)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				i = 1;
			}else if(obj instanceof TPtFwrzxxxxBO){//是否服务详细信息
				sbf.append("insert into T_PT_FWRZXXXX(fwrzxxId,fwrzjbId,detail,time,executeContent,startTime,endTime,obj,code)values(?,?,?,?,?,?,?,?,?)");
				i = 2;
			}else if(obj instanceof TPtSysLogBO){//数据中心操作日志
				sbf.append("insert into T_PT_SYS_LOG(logid,logtype,operatetime,ip,sourceplatform,userid,username,orgcode,orgname,starttime,endtime,content,function,url,errcode,errdesc,result,req,res,servicename,servicecontent,serviceobject,servicetype,servicestate,times,isfalg,runstate,countsum)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				i = 3;
			}else{
				return;
			}
			
			con = DataSourceManager.getConnection("defaultDataSource");
			//con.setAutoCommit(true);
			pstmt = con.prepareStatement(sbf.toString());
			switch (i) {
			case 1:
				TPtFwrzjbxxBO t = (TPtFwrzjbxxBO)obj;
				pstmt.setString(1, t.getFwrzjbid());
				pstmt.setString(2, t.getCallername());
				pstmt.setString(3, t.getCallertime());
				pstmt.setString(4, t.getExecutecase());
				pstmt.setString(5, t.getExecuteway());
				pstmt.setString(6, t.getExecutetime());
				pstmt.setString(7, t.getCallerparameter());
				pstmt.setString(8, t.getExecuteresult());
				pstmt.setString(9, t.getCallerip());
				pstmt.setString(10, t.getExecutetype());
				pstmt.setString(11, t.getCallerenttime());
				pstmt.setString(12, t.getCalleer());
				pstmt.setString(13, t.getFwmc());
				break;
			case 2:
				TPtFwrzxxxxBO t1 = (TPtFwrzxxxxBO)obj;
				pstmt.setString(1, t1.getFwrzxxid());
				pstmt.setString(2, t1.getFwrzjbid());
				pstmt.setString(3, t1.getDetail());
				pstmt.setString(4, t1.getTime());
				pstmt.setString(5, t1.getExecutecontent());
				
				pstmt.setString(6, t1.getStartTime());
				pstmt.setString(7, t1.getEndTime());
				pstmt.setString(8, t1.getObj());
				pstmt.setString(9, t1.getCode());
				break;
			case 3:
				TPtSysLogBO t2 = (TPtSysLogBO)obj;
				pstmt.setString(1, t2.getLogid());
				pstmt.setString(2, t2.getLogtype());
				pstmt.setString(3, t2.getOperatetime());
				pstmt.setString(4, t2.getIp());
				pstmt.setString(5, t2.getSourceplatform());
				pstmt.setString(6, t2.getUserid());
				pstmt.setString(7, t2.getUsername());
				pstmt.setString(8, t2.getOrgcode());
				pstmt.setString(9, t2.getOrgname());
				pstmt.setString(10, t2.getStarttime());
				pstmt.setString(11, t2.getEndtime());
				pstmt.setString(12, t2.getContent());
				pstmt.setString(13, t2.getFunction());
				pstmt.setString(14, t2.getUrl());
				pstmt.setString(15, t2.getErrcode());
				pstmt.setString(16, t2.getErrdesc());
				pstmt.setString(17, t2.getResult());
				pstmt.setString(18, t2.getReq());
				pstmt.setString(19, t2.getRes());
				pstmt.setString(20, t2.getServicename());
				pstmt.setString(21, t2.getServicecontent());
				pstmt.setString(22, t2.getServiceobject());
				pstmt.setString(23, t2.getServicetype());
				pstmt.setString(24, t2.getServicestate());
				pstmt.setBigDecimal(25, t2.getTimes());
				pstmt.setString(26, t2.getIsfalg());
				pstmt.setString(27, t2.getRunstate());
				pstmt.setBigDecimal(28, t2.getCountsum());
				break;
			default:
				break;
			}
			
			int backResult = pstmt.executeUpdate();
			//con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}