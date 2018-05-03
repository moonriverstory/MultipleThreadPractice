package interrupt;

import java.util.concurrent.*;

/**
 * runnable 调用thread.interrupt()方法中断
 * callable 调用future.cancel(true)方法中断
 */
public class CallableInterruptExceptionDemo implements Callable<Boolean> {
    private int i;

    public CallableInterruptExceptionDemo(int i) {
        this.i = i;
    }

    @Override
    public Boolean call() throws InterruptedException {
        switch (i) {
            case 1:
                while (true) {
                    System.out.println(Thread.currentThread().getName() + ";i:" + this.i); //第一个线程
                    Thread.sleep(200);
                }
            default:
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + ";i:" + this.i); //其他线程
        }

        return true;
    }


    public static void main(String[] args) {

        ExecutorService runnableService = Executors.newFixedThreadPool(3);

        Future<Boolean> r1 = runnableService.submit(new CallableInterruptExceptionDemo(1));
        Future<Boolean> r2 = runnableService.submit(new CallableInterruptExceptionDemo(2));
        Future<Boolean> r3 = runnableService.submit(new CallableInterruptExceptionDemo(3));

        try {
            boolean b1 = r1.get(); //r1先跑
            boolean b2 = r2.get(); //r2先跑
            boolean b3 = r3.get(); //r3先跑
            System.out.println(b1);
            System.out.println(b2);
            System.out.println(b3);
        } catch (InterruptedException e) {
            System.out.println("catch InterruptedException: " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("catch ExecutionException: " + e.getMessage());
        }
        r1.cancel(true);//r1是死循环，现在退出
        runnableService.shutdown();
    }
}
