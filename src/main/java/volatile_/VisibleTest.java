package volatile_;

/**
 * volatile对线程可见性的作用
 */
public class VisibleTest {

    public static void main(String[] args) throws InterruptedException {
        VisibleTask task = new VisibleTask();
        Thread th = new Thread(task);
        th.start();
        Thread.sleep(10);
        task.running = false;
        System.out.println(task.i);
        Thread.sleep(10);
        System.out.println(task.i);
        System.out.println("The end");

    }
    /**
     running is not volatile

     4017530
     8871093
     The end

     cpu100%，线程死循环
     */
    /**
     running is volatile

     3367143
     3367143
     The end
     */
}
