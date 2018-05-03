package exception;

import java.util.concurrent.*;

public class CallableCatchWayDemo {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public static void main(String[] args) {

        Future<Integer> future = executorService.submit(new MyTask());

        try {

            System.out.println("myTask任务执行结果为" + future.get());

        }
//        catch (Exception e) {
//
//            System.out.println("catch all exception test！");
//
//        }
        catch (InterruptedException e) {

            System.out.println("任务被中断！");

        } catch (ExecutionException e) {

            System.out.println("任务内部抛出未受检异常！");

        } catch (CancellationException e) {

            System.out.println("任务被取消！");

        }

        executorService.shutdown();

    }


    private static final class MyTask implements Callable<Integer> {

        @Override

        public Integer call() throws Exception {

            Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                @Override

                public void uncaughtException(Thread t, Throwable e) {

                    System.out.println("unchecked exception happened:");

                    System.out.println(t.getId());

                    System.out.println(t.getName());

                    e.printStackTrace(System.out);

                }

            });

            int sum = 0;

            for (int i = 4; i >= 0; i--) {

                sum = sum + (12 / i);

            }

            return sum;

        }

    }
    /**


     任务内部抛出未受检异常！
     */


}
