package transferAmongThreads;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 利用AtomicInteger
 */
public class MethodFour {
    //线程间利用AtomicInteger同步通信，初始化可执行线程id为1
    private AtomicInteger threadToGo = new AtomicInteger(1);

    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i = i + 2) {
                    while (threadToGo.get() == 2) {
                        //无限循环等待
                    }
                    Helper.print(arr[i], arr[i + 1]);
                    //设置可执行线程id为2
                    threadToGo.set(2);
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
                    //可执行线程id为1时，循环等待
                    while (threadToGo.get() == 1) {
                    }
                    Helper.print(arr[i]);
                    //执行完本次for循环，设置可执行线程id为1
                    threadToGo.set(1);
                }
            }
        };
    }

    public static void main(String args[]) throws InterruptedException {
        MethodFour four = new MethodFour();
        Helper.instance.run(four.newThreadOne());
        Helper.instance.run(four.newThreadTwo());
        Helper.instance.shutdown();
    }
}
