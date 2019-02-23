package aqs.reentrantLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockAQS {

    private Lock lock = new ReentrantLock(true);

    public void reentrant() {
        try {
            lock.lock();
            System.out.println("currentThread: " + Thread.currentThread().getName() + " thread id: " + Thread.currentThread().getId() + " lock~");
            try {
                lock.lock();
                System.out.println("currentThread: " + Thread.currentThread().getName() + " thread id: " + Thread.currentThread().getId() + " lock~");
            } finally {
                lock.unlock();
                System.out.println("currentThread: " + Thread.currentThread().getName() + " thread id: " + Thread.currentThread().getId() + " unlock~");
            }
        } finally {
            lock.unlock();
            System.out.println("currentThread: " + Thread.currentThread().getName() + " thread id: " + Thread.currentThread().getId() + " unlock~");
        }
    }

    public void holdingLock() {
        try {
            lock.lock();
            System.out.println("currentThread: " + Thread.currentThread().getName() + " thread id: " + Thread.currentThread().getId() + " lock~");
            try {
                //holding lock
                System.out.println("currentThread: " + Thread.currentThread().getName() + " thread id: " + Thread.currentThread().getId() + " waiting ... hold lock~");
                Thread.sleep(10 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
            System.out.println("currentThread: " + Thread.currentThread().getName() + " thread id: " + Thread.currentThread().getId() + " unlock~");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ReentrantLockAQS lockDemo = new ReentrantLockAQS();

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

        for (Thread thread : list) {
            thread.start();
        }

        Thread.sleep(3000);
        System.out.println("main over!");
    }
}
