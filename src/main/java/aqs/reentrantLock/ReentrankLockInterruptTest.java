package aqs.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class ReentrankLockInterruptTest {
    private static volatile ReentrantLockInterruptThread thread = new ReentrantLockInterruptThread();

    public static void main(String[] args) {

        thread.start();
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    static class ReentrantLockInterruptThread extends Thread {
        private final ReentrantLock reLock = new ReentrantLock();

        public void run() {
            System.out.println(Thread.interrupted());
            reLock.lock();
//            try {
//                reLock.lockInterruptibly();
//                System.out.println(Thread.interrupted());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(Thread.interrupted());
        }
    }
}
