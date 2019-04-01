package aqs.readwritelock;

//import aqs.reentrantLock.ReentrantLockFairDemo;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CannotReEnter {
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void reentrant() {
        try {
            rwlock.readLock().lock();
            rwlock.writeLock().lock();
            rwlock.writeLock().lock();


        } finally {

            rwlock.writeLock().unlock();
            rwlock.writeLock().unlock();
            rwlock.readLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

    //    ReentrantLockFairDemo lockDemo = new ReentrantLockFairDemo();

      //  lockDemo.reentrant();

//        int i = 0;
//        List<Thread> list = new ArrayList<>(30);
//        do {
//            Thread a = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    lockDemo.reentrant();
//                }
//            }, "lit A_" + i);
//
//            Thread b = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    lockDemo.work();
//                }
//            }, "lit B_" + i);
//
//
//            list.add(a);
//            list.add(b);
//        } while (i++ < 10);
//
//        for(Thread thread : list){
//            thread.start();
//        }

        Thread.sleep(3000);
        System.out.println("main over!");
    }
}
