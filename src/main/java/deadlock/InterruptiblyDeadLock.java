package deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lockInterruptibly()获得锁，如果发生死锁，调用线程interrupt来消除死锁。
 */
public class InterruptiblyDeadLock {
    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();

    public static void deathLock() {
        new Thread() {
            @Override
            public void run() {
                try {
                    lock1.lockInterruptibly();
                    System.out.println("thread1 get lock1...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        lock2.lockInterruptibly();
                        System.out.println("thread1 get lock1 and lock2...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock2.unlock();
                    }

                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } finally {
                    lock1.unlock();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    lock2.lockInterruptibly();
                    System.out.println("thread2 get lock2...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        lock1.lockInterruptibly();
                        System.out.println("thread2 get lock2 and lock1...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock1.unlock();
                    }

                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } finally {
                    lock2.unlock();
                }
            }
        }.start();
    }

    //基于JMX获取线程信息
    public static void checkDeadLock() throws InterruptedException {
        //获取Thread的MBean
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        //查找发生死锁的线程，返回线程id的数组
        long[] deadLockThreadIds = mbean.findDeadlockedThreads();
        System.out.println("find dead lock -- ");
        for(long id: deadLockThreadIds){
            System.out.println(id);
        }
        Thread.sleep(10L);
        if (deadLockThreadIds != null) {
            //获取发生死锁的线程信息
            ThreadInfo[] threadInfos = mbean.getThreadInfo(deadLockThreadIds);
            //获取JVM中所有的线程信息
            Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
            for (Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
                for (int i = 0; i < threadInfos.length; i++) {
                    Thread t = entry.getKey();
                    if (t.getId() == threadInfos[i].getThreadId()) {
                        //中断发生死锁的线程
                        t.interrupt();
                        //打印堆栈信息
                        for (StackTraceElement ste : entry.getValue()) {
                            System.err.println("t" + ste.toString().trim());
                        }
                    }

                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        deathLock();

        TimeUnit.SECONDS.sleep(2);

        checkDeadLock();
    }

    /**
     thread1 get lock1...
     thread2 get lock2...
     find dead lock --
     12
     11
     tsun.misc.Unsafe.park(Native Method)
     tjava.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
     tjava.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
     tjava.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:897)
     tjava.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
     tjava.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
     tdeadlock.InterruptiblyDeadLock$1.run(InterruptiblyDeadLock.java:27)
     tsun.misc.Unsafe.park(Native Method)
     tjava.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
     tjava.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
     tjava.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:897)
     tjava.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
     tjava.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
     tdeadlock.InterruptiblyDeadLock$2.run(InterruptiblyDeadLock.java:51)
     java.lang.InterruptedException
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:898)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
     at java.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
     at deadlock.InterruptiblyDeadLock$1.run(InterruptiblyDeadLock.java:27)
     java.lang.InterruptedException
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireInterruptibly(AbstractQueuedSynchronizer.java:898)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1222)
     at java.util.concurrent.locks.ReentrantLock.lockInterruptibly(ReentrantLock.java:335)
     at deadlock.InterruptiblyDeadLock$2.run(InterruptiblyDeadLock.java:51)
     Exception in thread "Thread-0" Exception in thread "Thread-1" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at deadlock.InterruptiblyDeadLock$1.run(InterruptiblyDeadLock.java:32)
     java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at deadlock.InterruptiblyDeadLock$2.run(InterruptiblyDeadLock.java:56)
     */
}
