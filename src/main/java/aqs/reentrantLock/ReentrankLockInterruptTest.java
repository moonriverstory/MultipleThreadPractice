package aqs.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *ReentrantLock的锁中断测试分析
 */
public class ReentrankLockInterruptTest {

    private final static ReentrantLock reLock = new ReentrantLock();

    public static void main(String[] args) {
        final ReentrantLockInterruptThread thread = new ReentrantLockInterruptThread();
        thread.start();
        thread.interrupt();
    }
    /**
     true
     false
     */
    /**
     true
     java.lang.InterruptedException
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1220)
     at java.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
     at aqs.reentrantLock.ReentrankLockInterruptTest$ReentrantLockInterruptThread.run(ReentrankLockInterruptTest.java:32)
     false
     */


    static class ReentrantLockInterruptThread extends Thread {

        public void run() {
            System.out.println(Thread.currentThread().isInterrupted());
//            reLock.lock();
            try {
                reLock.lockInterruptibly();
                System.out.println(Thread.interrupted());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().isInterrupted());
        }
    }
}
