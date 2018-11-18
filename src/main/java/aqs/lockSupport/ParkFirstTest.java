package aqs.lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * 先park,后unpark
 */
public class ParkFirstTest {
    public static void main(String[] args) throws Exception {

        ParkFirstThread thread = new ParkFirstThread(Thread.currentThread());
        thread.start();
        System.out.println("before park");

        // 等待获取许可
        LockSupport.park("Park");
        System.out.println("after park");
    }
}

class ParkFirstThread extends Thread {

    private Object object;

    public ParkFirstThread(Object object) {
        this.object = object;
    }

    public void run() {

        System.out.println("before unpark");
        // 获取blocker
        System.out.println("Blocker info: " + LockSupport.getBlocker((Thread) object));

        // 休眠,保证setBlocker(t, blocker)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 释放许可
        LockSupport.unpark((Thread) object);
        // 休眠500ms,保证先执行park中的setBlocker(t, null);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after unpark");
        System.out.println("Blocker info: " + LockSupport.getBlocker((Thread) object));

    }
}
