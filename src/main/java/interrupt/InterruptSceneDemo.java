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
    private void demo5ConditionInterruptDemo() {
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
     2019-12-18 15:07:20,014 INFO  [Thread-0] [InterruptSceneDemo.java:174] : entrant lockInterruptibly!
     2019-12-18 15:07:21,017 INFO  [Thread-0] [InterruptSceneDemo.java:179] : looping ~
     2019-12-18 15:07:22,018 INFO  [Thread-0] [InterruptSceneDemo.java:179] : looping ~
     2019-12-18 15:07:23,019 INFO  [Thread-0] [InterruptSceneDemo.java:179] : looping ~
     */

    public static void main(String[] args) {
        InterruptSceneDemo demo = new InterruptSceneDemo();
        //demo.demo1SleepInterruptDemo();
        //demo.demo2SynchronizedSleepInterruptDemo();
        //demo.demo3ConditionInterruptDemo();
        //demo.demo4ParkInterruptDemo();
        demo.demo5ConditionInterruptDemo();
    }
}
