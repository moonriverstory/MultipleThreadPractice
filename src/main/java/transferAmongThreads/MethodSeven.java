package transferAmongThreads;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 利用BlockingQueue
 */
public class MethodSeven {
    //共享一个queue，根据peek和poll的不同来实现
    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i = i + 2) {
                    Helper.print(arr[i], arr[i + 1]);
                    queue.offer("TwoToGo");
                    while (!"OneToGo".equals(queue.peek())) {
                    }
                    queue.poll();
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
                    while (!"TwoToGo".equals(queue.peek())) {
                    }
                    queue.poll();
                    Helper.print(arr[i]);
                    queue.offer("OneToGo");
                }
            }
        };
    }

    //两个queue，利用take()会自动阻塞来实现
    private final LinkedBlockingQueue<String> queue1 = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<String> queue2 = new LinkedBlockingQueue<>();

    public Runnable newThreadThree() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i = i + 2) {
                    Helper.print(arr[i], arr[i + 1]);
                    try {
                        queue2.put("TwoToGo");
                        //这里的take()，相当于wait()方法喽，阻塞当前线程
                        queue1.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public Runnable newThreadFour() {
        final String[] inputArr = Helper.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    try {
                        queue2.take();
                        Helper.print(arr[i]);
                        queue1.put("OneToGo");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public static void main(String args[]) throws InterruptedException {
        MethodSeven seven = new MethodSeven();
        Helper.instance.run(seven.newThreadOne());
        Helper.instance.run(seven.newThreadTwo());
        Thread.sleep(2000);
        System.out.println("");
        Helper.instance.run(seven.newThreadThree());
        Helper.instance.run(seven.newThreadFour());
        Helper.instance.shutdown();
    }
    /**
     * 总结一下这种实现方式：
     *  核心都不是资源竞争，而是线程之间的通信和协同操作
     */
}
