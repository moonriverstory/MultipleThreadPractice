package interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * 处理interrupt通知
 */
public class DealInterrupt {
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("run...");
                    LockSupport.park();
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("interruptd...");
                        System.out.println("do something else after received interrupt message ~");
                        break;
                    }
                    Thread.yield();
                    System.out.println("call yield()...");
                }
            }
        };
        t1.start();
        Thread.sleep(2000L);
        t1.interrupt();
    }
    /**
     事实证明，thread接到中断通知后，还可以在这种场景下做接下来的处理 =。=

     run...
     call yield()...
     run...
     call yield()...
     run...
     call yield()...
     run...
     call yield()...
     run...
     interruptd...
     do something else after received interrupt message ~
     */

    /**
     如何在sleep()时，catch住了interrupt exception, 则后面的判断thread interrupted 语句就不生效了=。=

     run...
     java.lang.InterruptedException: sleep interrupted
     call yield()...
     run...
     at java.lang.Thread.sleep(Native Method)
     at interrupt.DealInterrupt$1.run(DealInterrupt.java:14)
     call yield()...
     run...
     call yield()...
     run...
     call yield()...
     run...
     */
    /**
     用park()模拟最好了，又不抛错，也能收到interrupt，还能判断interrupted，多好！

     run...
     interruptd...
     do something else after received interrupt message ~
     */
}
