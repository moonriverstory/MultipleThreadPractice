package transferAmongThreads;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 利用CyclicBarrier
 * <p>
 * 这个例子有bug，不太好
 */
public class MethodFive {
    /**
     * CyclicBarrier可以实现让一组线程在全部到达Barrier时(执行await())，再一起同时执行(构造函数传入设置好的action方法)，并且所有线程释放后，还能复用它
     */
    private final CyclicBarrier barrier;
    private final List<String> list;

    public MethodFive() {
        list = Collections.synchronizedList(new ArrayList<String>());
        //设置2个参与者，运行各自的程序，到栅栏时，执行newBarrierAction。如此循环
        barrier = new CyclicBarrier(2, newBarrierAction());
    }

    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0, j = 0; i < arr.length; i = i + 2, j++) {
                    try {
                        list.add(arr[i]);
                        list.add(arr[i + 1]);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public Runnable newThreadTwo() {
        final String[] inputArr = Helper.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    try {
                        list.add(arr[i]);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private Runnable newBarrierAction() {
        return new Runnable() {
            @Override
            public void run() {
                /**
                 * 问题出在Collections.sort String 有bug =。=
                 */
                Collections.sort(list);
                for (String c: list) {
                    System.out.print(c);
                }
                list.clear();
            }
            /**
             * 去掉sort()问题更多
             *
             * 12AB3456CD78910EF11121314GH15161718IJ19202122KL23242526MN27282930OP31323334QR35363738ST39404142UV43444546WX47484950YZ5152
             */
        };
    }

    public static void main(String args[]) {
        MethodFive five = new MethodFive();
        Helper.instance.run(five.newThreadOne());
        Helper.instance.run(five.newThreadTwo());
        Helper.instance.shutdown();
    }
    /**
     * 这个结果里10和9是反的
     *
     * 12A34B56C78D109E1112F1314G1516H1718I1920J2122K2324L2526M2728N2930O3132P3334Q3536R3738S3940T4142U4344V4546W4748X4950Y5152Z
     */
}
