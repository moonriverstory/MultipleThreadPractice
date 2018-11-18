package aqs.lockSupport;


import java.util.concurrent.locks.LockSupport;

/**
 * 先unpark,后park
 */
public class UnparkFirstTest {
    public static void main(String[] args) throws Exception {
        UnparkFirstThread thread = new UnparkFirstThread( Thread.currentThread() );
        long start = System.nanoTime();
        thread.start();
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( "before park" );
        // 等待获取许可
        LockSupport.park( "Park" );
        System.out.println( "after park:" + (System.nanoTime() - start) );
    }
}

class UnparkFirstThread extends Thread {

    private Object object;

    public UnparkFirstThread(Object object) {
        this.object = object;
    }

    public void run() {
        System.out.println( "before unpark" );
        LockSupport.unpark( (Thread) object );
        System.out.println( "after unpark" );
    }
}