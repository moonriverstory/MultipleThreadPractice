package aqs.lockSupport;

import java.util.concurrent.locks.LockSupport;

public class ParkInterruptTest {
    public static void main(String[] args) throws Exception {
        LockSupportInterruptThread thread = new LockSupportInterruptThread();
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    static class LockSupportInterruptThread extends Thread {
        public void run() {
            System.out.println(Thread.currentThread().isInterrupted());
            LockSupport.park();
            System.out.println(Thread.currentThread().isInterrupted());
            //System.out.println(Thread.interrupted() + " " + Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt();
            System.out.println("something after interrupt~ " + Thread.currentThread().isInterrupted());
        }
        /**
         false false
         true true
         */
        /**
         false false
         true false
         */
        /**
         false
         true
         something after interrupt~ true
         */
    }
}
