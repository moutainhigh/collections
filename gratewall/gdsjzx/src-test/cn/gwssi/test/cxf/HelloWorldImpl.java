package cn.gwssi.test.cxf;

import javax.jws.WebService;

@WebService(endpointInterface="cn.gwssi.cxf.HelloWorldDao")
public class HelloWorldImpl implements HelloWorldDao {

    @Override
    public void sayHello(String name) {
        System.out.println("hello," + name);
    }
}
