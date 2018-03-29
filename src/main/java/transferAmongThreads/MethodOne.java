package transferAmongThreads;

/**
 * 利用最基本的synchronized、notify、wait
 */
public class MethodOne {
    /**
     * 线程间锁对象
     */
    private final ThreadToGo threadToGo = new ThreadToGo();

    /**
     * 线程1，打印数字
     *
     * @return
     */
    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                try {
                    //循环打印
                    for (int i = 0; i < arr.length; i = i + 2) {
                        //竞争条件判断
                        synchronized (threadToGo) {
                            //判断，如果可执行线程id不是当前线程
                            while (threadToGo.value == 2)
                                //当前线程等待
                                threadToGo.wait();
                            Helper.print(arr[i], arr[i + 1]);
                            //设置可执行线程id为2
                            threadToGo.value = 2;
                            //通知等待线程唤醒
                            threadToGo.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Oops...");
                }
            }
        };
    }

    /**
     * 线程2，打印字母
     *
     * @return
     */
    public Runnable newThreadTwo() {
        final String[] inputArr = Helper.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                try {
                    for (int i = 0; i < arr.length; i++) {
                        synchronized (threadToGo) {
                            while (threadToGo.value == 1)
                                threadToGo.wait();
                            Helper.print(arr[i]);
                            threadToGo.value = 1;
                            threadToGo.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Oops...");
                }
            }
        };
    }

    /**
     * 锁对象class-有运行判断信号值
     */
    class ThreadToGo {
        //首先执行线程1
        int value = 1;
    }

    public static void main(String args[]) throws InterruptedException {
        MethodOne one = new MethodOne();
        Helper.instance.run(one.newThreadOne());
        Helper.instance.run(one.newThreadTwo());
        Helper.instance.shutdown();
    }
}
