package condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    Logger log = LoggerFactory.getLogger(ConditionTest.class);

    final ReentrantLock lock = new ReentrantLock(false);

    private final Condition condition = lock.newCondition();

    private void waitTest() {
        try {
            condition.await();
            log.info("without lock condition.await() correct!");
        } catch (InterruptedException e) {
            log.error("without lock condition.await() error!", e);
        }
    }

    /**
     * Exception in thread "main" java.lang.IllegalMonitorStateException
     * at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     * at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     * at java.util.concurrent.locks.AbstractQueuedSynchronizer.fullyRelease(AbstractQueuedSynchronizer.java:1723)
     * at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2036)
     * at condition.ConditionTest.waitTest(ConditionTest.java:14)
     * at condition.ConditionTest.main(ConditionTest.java:22)
     */

    private void waitInLockTest() {
        try {
            lock.tryLock();
            condition.await(10, TimeUnit.SECONDS);
            log.info("with lock condition.waitInLockTest() correct!");
        } catch (InterruptedException e) {
            log.error("without lock condition.waitInLockTest() error!", e);
        } finally {
            lock.unlock();
        }
    }

    private void notifyTest() {
        condition.signal();
        log.info("without lock condition.signal() call!");
    }

    private void notifyInLockTest() {
        try {
            lock.tryLock();
            condition.signal();
            log.info("with lock condition.notifyInLockTest() call!");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionTest test = new ConditionTest();
        //test.waitTest();
        //test.notifyTest();

        test.waitInLockTest();
        test.notifyInLockTest();

    }


}
