package aqs.lockSupport;

import java.util.concurrent.locks.LockSupport;

public class OffsetGetTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadOffSet to = new ThreadOffSet();
        to.start();
        Thread.sleep(100L);
        System.out.println("parking, and get name: " + to.getName());
        to.interrupt();
    }

    /**
     thread enter, and park~
     parking, and get name: Thread-0
     thread unpark or interrupt~
     */

    static class ThreadOffSet extends Thread {
        @Override
        public void run() {
            System.out.println("thread enter, and park~");
            LockSupport.park();
            System.out.println("thread unpark or interrupt~");
        }
    }
}
