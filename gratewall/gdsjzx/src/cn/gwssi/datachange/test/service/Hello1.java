package cn.gwssi.datachange.test.service;


import javax.jws.WebMethod;
import javax.jws.WebService;  

@WebService  
public class Hello1 {  
	@WebMethod
    public String sayHello(String name) {  
        return ("Welcome, I am Server. Hello, " + name);  
    }  
  
    public int sum(int a, int b) {  
        return a + b;  
    }  
    
    
    
    
    
    
    
    
    
}  