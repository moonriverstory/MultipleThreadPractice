package interrupt;

import condition.ConditionTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptSceneDemo {

    Logger log = LoggerFactory.getLogger(ConditionTest.class);

    private final static Object o = new Object();

    final ReentrantLock lock = new ReentrantLock(false);

    private final Condition condition = lock.newCondition();

    /**
     * sleep中断
     */
    private void demo1SleepInterruptDemo() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("thread sleep!");
                        Thread.sleep(10000);
                    }
                } catch (Exception e) {
                    System.out.println("thread exception!");
                    e.printStackTrace();
                }
                System.out.println("thread end!");
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    /**
     * synchronized中的sleep，可中断
     */
    private void demo2SynchronizedSleepInterruptDemo() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    System.out.println("entrant synchronized!");
                    try {
                        while (true) {
                            System.out.println("thread sleep!");
                            Thread.sleep(10000);
                        }
                    } catch (Exception e) {
                        System.out.println("thread exception!");
                        e.printStackTrace();
                    }
                    System.out.println("thread end!");
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
    /**
     entrant synchronized!
     thread sleep!
     thread exception!
     java.lang.InterruptedException: sleep interrupted
     at java.lang.Thread.sleep(Native Method)
     at interrupt.InterruptSceneDemo$2.run(InterruptSceneDemo.java:41)
     at java.lang.Thread.run(Thread.java:748)
     thread end!
     */

    /**
     * condition.await() interrupt，可中断
     */
    private void demo3ConditionInterruptDemo() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.tryLock();
                    log.info("entrant synchronized!");
                    while (true) {
                        log.info("condition.await() ~");
                        condition.await(10, TimeUnit.SECONDS);
                        log.info("condition.await() correct!");
                    }

                } catch (Exception e) {
                    log.error("condition.await() error!");
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println("thread end!");
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
    /**
     * 2019-12-18 11:10:06,400 INFO  [Thread-0] [InterruptSceneDemo.java:99] : entrant synchronized!
     * 2019-12-18 11:10:06,402 INFO  [Thread-0] [InterruptSceneDemo.java:101] : condition.await() ~
     * 2019-12-18 11:10:08,400 ERROR [Thread-0] [InterruptSceneDemo.java:107] : condition.await() error!
     * java.lang.InterruptedException
     * at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.reportInterruptAfterWait(AbstractQueuedSynchronizer.java:2014)
     * at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2173)
     * at interrupt.InterruptSceneDemo$3.run(InterruptSceneDemo.java:102)
     * at java.lang.Thread.run(Thread.java:748)
     * thread end!
     */

    /**
     * park可中断，并且没有抛出异常
     */
    private void demo4ParkInterruptDemo() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("entrant park!");
                    LockSupport.park();
                    log.info("park interrupted!");
                } catch (Exception e) {
                    log.error("park error!", e);
                }
                System.out.println("thread end!");
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
    /**
     * 2019-12-18 14:22:38,770 INFO  [Thread-0] [InterruptSceneDemo.java:142] : entrant park!
     * 2019-12-18 14:22:40,769 INFO  [Thread-0] [InterruptSceneDemo.java:144] : park interrupted!
     * thread end!
     */

    /**
     * 实验证明，正在运行的lockInterruptibly是不可中断的
     */
    private void demo5LockInterruptiblyRunningInterruptDemo() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();
                    log.info("entrant lockInterruptibly!");
                    long begin = System.currentTimeMillis();
                    while (true) {
                        if ((System.currentTimeMillis() - begin) > 1000) {
                            begin = System.currentTimeMillis();
                            log.info("looping ~");
                        }
                    }
                } catch (Exception e) {
                    log.error("lockInterruptibly error!", e);
                } finally {
                    lock.unlock();
                }
                System.out.println("thread end!");
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
    /**
     * 2019-12-18 15:07:20,014 INFO  [Thread-0] [InterruptSceneDemo.java:174] : entrant lockInterruptibly!
     * 2019-12-18 15:07:21,017 INFO  [Thread-0] [InterruptSceneDemo.java:179] : looping ~
     * 2019-12-18 15:07:22,018 INFO  [Thread-0] [InterruptSceneDemo.java:179] : looping ~
     * 2019-12-18 15:07:23,019 INFO  [Thread-0] [InterruptSceneDemo.java:179] : looping ~
     */

    /**
     * wait可中断，并且抛出InterruptedException错误~
     */
    private void demo6WaitInterruptDemo() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (o) {
                        log.info("entrant synchronized!");
                        log.info("wait!");
                        o.wait();
                    }
                    log.info("Thread awake!");
                } catch (Exception e) {
                    log.error("wait interruptibly error!", e);
                }
                System.out.println("thread end!");
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
    /**
     * 2019-12-19 10:58:00,766 INFO  [Thread-0] [InterruptSceneDemo.java:214] : entrant synchronized!
     * 2019-12-19 10:58:00,768 INFO  [Thread-0] [InterruptSceneDemo.java:215] : wait!
     * 2019-12-19 10:58:02,767 ERROR [Thread-0] [InterruptSceneDemo.java:220] : wait interruptibly error!
     * java.lang.InterruptedException
     * at java.lang.Object.wait(Native Method)
     * at java.lang.Object.wait(Object.java:502)
     * at interrupt.InterruptSceneDemo$6.run(InterruptSceneDemo.java:216)
     * at java.lang.Thread.run(Thread.java:748)
     * thread end!
     */

    /**
     * tryLock在获取锁是会检查中断标志位，如果有，则直接抛出中断错误~
     * 按源码来看，在等待获取锁的loop中，也是会检查中断标志位的，一但有中断标志位设置为true，则抛出中断异常~ ... 不过这种场景比较难造 =。=
     */
    private void demo7TrylockWaitingInterruptDemo() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("entrant lockInterruptibly!");
                    long begin = System.currentTimeMillis();
                    int count = 0;
                    while (true) {
                        if ((System.currentTimeMillis() - begin) > 1000) {
                            count++;
                            begin = System.currentTimeMillis();
                            log.info("looping ~" + count);
                            if (count >= 2) {
                                break;
                            }
                        }
                    }
                    lock.tryLock(3, TimeUnit.SECONDS);
                    log.info("entrant TrylockWaiting!");

                } catch (Exception e) {
                    log.error("TrylockWaiting error!", e);
                } finally {
                    lock.unlock();
                    log.info("TrylockWaiting unlock!");
                }
                System.out.println("thread end!");
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
    /**
     2019-12-19 11:09:51,756 INFO  [Thread-0] [InterruptSceneDemo.java:255] : entrant lockInterruptibly!
     2019-12-19 11:09:52,758 INFO  [Thread-0] [InterruptSceneDemo.java:262] : looping ~1
     2019-12-19 11:09:53,759 INFO  [Thread-0] [InterruptSceneDemo.java:262] : looping ~2
     2019-12-19 11:09:54,760 INFO  [Thread-0] [InterruptSceneDemo.java:262] : looping ~3
     2019-12-19 11:09:55,761 INFO  [Thread-0] [InterruptSceneDemo.java:262] : looping ~4
     2019-12-19 11:09:55,762 ERROR [Thread-0] [InterruptSceneDemo.java:272] : TrylockWaiting error!
     java.lang.InterruptedException
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.tryAcquireNanos(AbstractQueuedSynchronizer.java:1245)
     at java.util.concurrent.locks.ReentrantLock.tryLock(ReentrantLock.java:442)
     at interrupt.InterruptSceneDemo$7.run(InterruptSceneDemo.java:268)
     at java.lang.Thread.run(Thread.java:748)
     Exception in thread "Thread-0" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at interrupt.InterruptSceneDemo$7.run(InterruptSceneDemo.java:274)
     at java.lang.Thread.run(Thread.java:748)
     */
    /**
     2019-12-19 11:12:38,189 INFO  [Thread-0] [InterruptSceneDemo.java:255] : entrant lockInterruptibly!
     2019-12-19 11:12:39,192 INFO  [Thread-0] [InterruptSceneDemo.java:262] : looping ~1
     2019-12-19 11:12:39,192 INFO  [Thread-0] [InterruptSceneDemo.java:269] : entrant TrylockWaiting!
     2019-12-19 11:12:39,192 INFO  [Thread-0] [InterruptSceneDemo.java:275] : TrylockWaiting unlock!
     thread end!
     */

    /**
     * main test
     *
     * @param args
     */
    public static void main(String[] args) {
        InterruptSceneDemo demo = new InterruptSceneDemo();
        //demo.demo1SleepInterruptDemo();
        //demo.demo2SynchronizedSleepInterruptDemo();
        //demo.demo3ConditionInterruptDemo();
        //demo.demo4ParkInterruptDemo();
        //demo.demo5LockInterruptiblyRunningInterruptDemo();
        //demo.demo6WaitInterruptDemo();
        demo.demo7TrylockWaitingInterruptDemo();
    }
}
