package cn.gwssi.test.task;

import java.util.concurrent.*;

/**
 * Created by flybean on 16-5-17.
 */
public class SyncWithTimeout {
    static int seq = 1;

    public Object blockingMethod() {

        System.out.println("Current Thread : " + Thread.currentThread().getName());
        int count = 0;
        while ((count++) < 5) {
            try {
                System.out.println("Sleep at " + (seq++));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //return null;
            }
        }

        return new String("Successed");
    }

    public void callSyncWithTimout(long timeout) {
        System.out.println("Main thread : " + Thread.currentThread().getName());
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<Object> task = new Callable<Object>() {
            public Object call() {
                return blockingMethod();
            }
        };
        Future<Object> future = executor.submit(task);

        try {
            Object result = future.get(timeout, TimeUnit.SECONDS);
            System.out.println("Result:" + result);
        } catch (TimeoutException ex) {
            System.out.println("Timeout");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        } catch (ExecutionException e) {
            System.out.println("ExecutionException");
        } finally {
            future.cancel(true);
            System.out.println("Canceled");
            return;
        }
    }

    public static void main(String[] args) {
        SyncWithTimeout sync = new SyncWithTimeout();
        sync.callSyncWithTimout(2000);
    }
}
