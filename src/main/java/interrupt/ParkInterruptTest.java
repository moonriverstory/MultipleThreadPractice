package interrupt;

import java.util.concurrent.locks.LockSupport;

public class ParkInterruptTest {

    public static void main(String[] args) throws InterruptedException {
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println("Thread " + Thread.currentThread().getName() + " awake! ");
            }
        });
        t.start();
        System.out.println("Timing ~");
        Thread.sleep(3000L);
        t.interrupt();
    }
    /**


     Timing ~
     Thread Thread-0 awake!
     */
}
