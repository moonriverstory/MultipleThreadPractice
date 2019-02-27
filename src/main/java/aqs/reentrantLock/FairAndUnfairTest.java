package aqs.reentrantLock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这个实验很好的展示了公平锁的特性：只要一个线程释放锁，则下一个被唤醒的一定是head.next（或者tail向前遍历最接近head的一个waitstatus不是取消状态的node），而新来的锁一定放在队尾
 * 非公平锁则不然，新来的线程直接参与竞争（这种行为叫做插队），插队失败才会加入队尾
 */
public class FairAndUnfairTest {

    public static void main(String[] args) {
        try {
            testLock("非公平锁", unfairLock);
            testLock("公平锁", fairLock);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Lock fairLock = new ReentrantLockMine(true);
    private static Lock unfairLock = new ReentrantLockMine(false);


    /**
     * 测试锁
     *
     * @param type
     * @param lock
     * @throws InterruptedException
     */
    private static void testLock(String type, Lock lock) throws InterruptedException {
        System.out.println(type);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(lock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        Thread.sleep(1000 * 5);
    }

    private static class Job implements Runnable {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    Thread.sleep(10);
                    System.out.println("获取锁的当前线程[" + Thread.currentThread().getName() + "], 同步队列中的线程" + ((ReentrantLockMine) lock).getQueuedThreads() + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 重新实现ReentrantLock类是为了重写getQueuedThreads方法，便于我们试验的观察
     */
    private static class ReentrantLockMine extends ReentrantLock {
        public ReentrantLockMine(boolean fair) {
            super(fair);
        }

        /**
         * 获取同步队列中的线程
         *
         * @return
         */
        @Override
        protected Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
            //倒序
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
    /**
     非公平锁
     获取锁的当前线程[0], 同步队列中的线程[1, 2, 3, 4]
     获取锁的当前线程[0], 同步队列中的线程[1, 2, 3, 4]
     获取锁的当前线程[1], 同步队列中的线程[2, 3, 4]
     获取锁的当前线程[1], 同步队列中的线程[2, 3, 4]
     获取锁的当前线程[2], 同步队列中的线程[3, 4]
     获取锁的当前线程[2], 同步队列中的线程[3, 4]
     获取锁的当前线程[3], 同步队列中的线程[4]
     获取锁的当前线程[3], 同步队列中的线程[4]
     获取锁的当前线程[4], 同步队列中的线程[]
     获取锁的当前线程[4], 同步队列中的线程[]
     公平锁
     获取锁的当前线程[0], 同步队列中的线程[1, 2, 3, 4]
     获取锁的当前线程[1], 同步队列中的线程[2, 3, 4, 0]
     获取锁的当前线程[2], 同步队列中的线程[3, 4, 0, 1]
     获取锁的当前线程[3], 同步队列中的线程[4, 0, 1, 2]
     获取锁的当前线程[4], 同步队列中的线程[0, 1, 2, 3]
     获取锁的当前线程[0], 同步队列中的线程[1, 2, 3, 4]
     获取锁的当前线程[1], 同步队列中的线程[2, 3, 4]
     获取锁的当前线程[2], 同步队列中的线程[3, 4]
     获取锁的当前线程[3], 同步队列中的线程[4]
     获取锁的当前线程[4], 同步队列中的线程[]
     */
}