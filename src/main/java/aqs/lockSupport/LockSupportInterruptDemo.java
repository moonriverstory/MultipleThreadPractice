package aqs.lockSupport;


import java.util.concurrent.locks.LockSupport;

public class LockSupportInterruptDemo {
    public static void main(String[] args) throws Exception {
        LockSupportInterruptThread thread = new LockSupportInterruptThread();
        thread.start();
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    static class LockSupportInterruptThread extends Thread {
        public void run() {
            System.out.println( Thread.currentThread().isInterrupted() );
            LockSupport.park();
            System.out.println( Thread.currentThread().isInterrupted() );
        }
    }
}

