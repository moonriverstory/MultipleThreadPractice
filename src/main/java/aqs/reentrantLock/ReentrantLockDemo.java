package aqs.reentrantLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private Lock lock = new ReentrantLock();

    private void workOn() {
        System.out.println(Thread.currentThread().getName() + ":work");
    }

    private void workOff() {
        System.out.println(Thread.currentThread().getName() + ":home");
    }


    public void work() {
        try {
            lock.lock();
            workOn();
            System.out.println(Thread.currentThread().getName()
                    + " working!!!!");
            Thread.sleep(100);
            workOff();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final ReentrantLockDemo lockDemo = new ReentrantLockDemo();


        int i = 0;
        List<Thread> list = new ArrayList<>(30);
        do {
            Thread a = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockDemo.work();
                }
            }, "lit A_" + i);

            Thread b = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockDemo.work();
                }
            }, "lit B_" + i);


            list.add(a);
            list.add(b);
        } while (i++ < 10);

        for(Thread thread : list){
            thread.start();
        }

        Thread.sleep(3000);
        System.out.println("main over!");
    }
}
