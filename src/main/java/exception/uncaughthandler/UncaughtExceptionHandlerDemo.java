package exception.uncaughthandler;

public class UncaughtExceptionHandlerDemo {
    public static void main(String[] args) {

        MyRunnable runnable = new MyRunnable();
        Thread t1 = new Thread(runnable, "one");
        Thread t2 = new Thread(runnable, "two");
        Thread t3 = new Thread(runnable, "third");
        Thread t4 = new Thread(runnable, "four");
        t1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Caught exception in Thread: " + t.getName() + " , error: " + e.getMessage());
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
