package com.gwssi.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolTest {
	public static void main(String[] args) {  
      //  ExecutorService pool=Executors.newFixedThreadPool(5);//创建一个固定大小为5的线程池  
        ExecutorService pool=Executors.newCachedThreadPool(); //创建一个具有缓存功能的线程池，系统根据需要创建线程，这些线程将会被缓存在线程池中。
        for(int i=0;i<7;i++){  
            pool.submit(new MyThread());  
        }  
        pool.shutdown();  
    }  
}
class MyThread extends Thread{  
    @Override  
    public void run() {  
            System.out.println(Thread.currentThread().getName()+"正在执行。。。");  
    }  
}  