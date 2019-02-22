package aqs.reentrantLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockAQSFair {

    private Lock lock = new ReentrantLock(true);

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

    public void holdingLock() {
        try {
            lock.lock();
            try {
                //holding lock like forever =.=
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ReentrantLockAQSFair lockDemo = new ReentrantLockAQSFair();

        //lockDemo.reentrant();

        int i = 0;
        List<Thread> list = new ArrayList<>(30);
        do {
            Thread a = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockDemo.holdingLock();
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
        } while (i++ < 1);

        for(Thread thread : list){
            thread.start();
        }

        Thread.sleep(3000);
        System.out.println("main over!");
    }
}
