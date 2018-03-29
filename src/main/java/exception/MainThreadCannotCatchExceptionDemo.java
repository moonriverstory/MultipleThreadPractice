package exception;

public class MainThreadCannotCatchExceptionDemo {
    public static void main(String[] args) {

        try{
            MyRunnable runnable = new MyRunnable();
            Thread t1 = new Thread(runnable, "one");
            Thread t2 = new Thread(runnable, "two");
            Thread t3 = new Thread(runnable, "third");
            Thread t4 = new Thread(runnable, "four");
            t1.start();
            t2.start();
            t3.start();
            t4.start();
        }catch (Exception e){
            System.out.println("捕获了");
        }

    }
    /**
     Exception in thread "one" java.lang.ArithmeticException: / by zero
     at exception.MyRunnable.run(MyRunnable.java:8)
     at java.lang.Thread.run(Thread.java:745)
     Exception in thread "two" java.lang.ArithmeticException: / by zero
     at exception.MyRunnable.run(MyRunnable.java:8)
     at java.lang.Thread.run(Thread.java:745)
     Exception in thread "third" java.lang.ArithmeticException: / by zero
     at exception.MyRunnable.run(MyRunnable.java:8)
     at java.lang.Thread.run(Thread.java:745)
     Exception in thread "four" java.lang.ArithmeticException: / by zero
     at exception.MyRunnable.run(MyRunnable.java:8)
     at java.lang.Thread.run(Thread.java:745)
     */
}
