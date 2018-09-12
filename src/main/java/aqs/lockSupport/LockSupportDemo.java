package aqs.lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的park(),unPark()与Thread的wait(),notify()区别
 */
public class LockSupportDemo {
    public static void main(String[] args) throws Exception {
        DemoLockSupport lockSupport = new DemoLockSupport();
        lockSupport.start();
        DemoThread thread = new DemoThread();
        thread.start();

    }
}

class DemoLockSupport extends Thread {
    public void run() {
        System.out.println( "TestLockSupport.run()" );
        LockSupport.park(  );
    }
}

class DemoThread extends Thread {
    public void run() {
        System.out.println( "TestThread.run()" );
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
