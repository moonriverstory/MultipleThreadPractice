package aqs.reentrantLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockAQSNonFair {
    private Lock lock = new ReentrantLock(false);

    public void reentrant() {
        try {
            lock.lock();
            try {
                lock.lock();
            } finally {
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

    public void work() {
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ReentrantLockAQSNonFair lockDemo = new ReentrantLockAQSNonFair();

        lockDemo.reentrant();

        int i = 0;
        List<Thread> list = new ArrayList<>(30);
        do {
            Thread a = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockDemo.work();
                    try {
                        //holding lock
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lockDemo.reentrant();
                }
            }, "lit A_" + i);

            Thread b = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockDemo.reentrant();
                }
            }, "lit B_" + i);

            list.add(a);
            list.add(b);
        } while (i++ < 2);

        for(Thread thread : list){
            thread.start();
        }

        Thread.sleep(3000);
        System.out.println("main over!");
    }
}
