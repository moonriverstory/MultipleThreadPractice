package compete.trylock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockPrintQueue implements PrintQueue {
    private Lock queueLock ;

    private TryLockPrintQueue(){}

    public TryLockPrintQueue(boolean fair){
        queueLock = new ReentrantLock(fair);
    }

    public void printJob(Object document) {
        queueLock.tryLock();
        try {
            Long duration = (long) (Math.random() * 10000);
            System.out.printf("%s:PrintQueue: Printing a Job during %d seconds\n",
                    Thread.currentThread().getName(), (duration / 1000));
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
        queueLock.tryLock();
        try {
            Long duration = (long) (Math.random() * 10000);
            System.out.printf("%s:PrintQueue: Printing a Job during %d seconds\n",
                    Thread.currentThread().getName(), (duration / 1000));
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }
}
