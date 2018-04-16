package interrupt;

import java.util.concurrent.locks.LockSupport;

public class WaitInterruptTest {
    private final static Object lockObj = new Object();
    public static void main(String[] args) throws InterruptedException {
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                synchronized (lockObj){
                    try {
                        lockObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread " + Thread.currentThread().getName() + " awake! ");
                }

            }
        });
        t.start();
        System.out.println("Timing ~");
        Thread.sleep(3000L);
        t.interrupt();
    }
    /**
     事实证明wait可以相应中断，但是，wait()的interrupt()会报错，而park()的interrupt()不会报错=。=

     Timing ~
     java.lang.InterruptedException
     at java.lang.Object.wait(Native Method)
     at java.lang.Object.wait(Object.java:502)
     at interrupt.WaitInterruptTest$1.run(WaitInterruptTest.java:14)
     at java.lang.Thread.run(Thread.java:745)
     Thread Thread-0 awake!
     */
}
