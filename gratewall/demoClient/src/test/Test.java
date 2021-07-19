package test;

import java.rmi.RemoteException;

import demo.HelloService;
import demo.HelloServiceProxy;

public class Test {

	public static void main(String[] args) throws RemoteException {
		HelloServiceProxy helloServiceProxy = new HelloServiceProxy();

		HelloService service = helloServiceProxy.getHelloService();

		String res = service.say("web service ");
		System.out.println(res);
	}
}
