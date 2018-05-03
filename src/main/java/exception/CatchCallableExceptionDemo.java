package exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

public class CatchCallableExceptionDemo {
    public static void main(String[] args) {

        try {
            ExceptionCallable task1 = new ExceptionCallable();
            NormalCallable task2 = new NormalCallable();
            NormalCallable task3 = new NormalCallable();
            ExecutorService executor = Executors.newFixedThreadPool(2);
            Future result1 = executor.submit(task1);
            Future result2 = executor.submit(task2);
            Future result3 = executor.submit(task3);

            //LockSupport.park(100000L);
            Thread.sleep(100L);

            try {
                if (result1.isDone()) {
                    System.out.println("1 Done ~");
                    System.out.println("Done " + result1.get());
                }
            } catch (Exception e) {
                System.out.println("1-捕获了" + e.getMessage());
            }

            if (result2.isDone()) {
                System.out.println("2 Done ~");
                System.out.println("Done " + result2.get());
            }
            if (result3.isDone()) {
                System.out.println("3 Done ~");
                System.out.println("Done " + result3.get());
            }
            executor.shutdown();

        } catch (Exception e) {
            System.out.println("捕获了" + e.getMessage());
        }

    }
    /**
     enter normal callable~
     enter callable~
     1 Done ~
     捕获了java.lang.ArithmeticException: / by zero
     */

    /**
     enter normal callable~
     enter callable~
     1 Done ~
     1-捕获了java.lang.ArithmeticException: / by zero
     2 Done ~
     Done ok
     */

}
