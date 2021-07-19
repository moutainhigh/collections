package cn.gwssi.etlschedule;

import java.util.concurrent.*;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Administrator on 2016/6/20.
 */
@Component
public class ETLExcuterQueue   extends ApplicationObjectSupport implements InitializingBean {
    private static ExecutorService pool =null;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool=Executors.newFixedThreadPool(15);//
    }
    
    public static ExecutorService getPool() {
        return pool;
    }
}
