package cn.gwssi.etlschedule;

import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**ETL执行读取初始化参数
 * Created by Administrator on 2016/6/17.
 */

public class ETLinit extends BaseService {



    //获取ETL执行时，此时数据更新标记
    public String getMaxFlagByTableName(String tableName){
        IPersistenceDAO  dao=this.getPersistenceDAO();







        return null;
    }

}
