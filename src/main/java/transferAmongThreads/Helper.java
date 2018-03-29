package transferAmongThreads;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通用代码
 * Question:
 * 编写两个线程，一个线程打印1~52，另一个线程打印字母A~Z，打印顺序为12A34B56C……5152Z，要求使用线程间的通信。
 */
public enum Helper {

    instance;

    /**
     * 内部线程池，运行线程数量为2
     */
    private static final ExecutorService tPool = Executors.newFixedThreadPool(2);

    /**
     * 创建数字数组
     *
     * @param max
     * @return
     */
    public static String[] buildNoArr(int max) {
        String[] noArr = new String[max];
        for (int i = 0; i < max; i++) {
            noArr[i] = Integer.toString(i + 1);
        }
        return noArr;
    }

    /**
     * 创建字母数组
     *
     * @param max
     * @return
     */
    public static String[] buildCharArr(int max) {
        String[] charArr = new String[max];
        int tmp = 65;
        for (int i = 0; i < max; i++) {
            charArr[i] = String.valueOf((char) (tmp + i));
        }
        return charArr;
    }

    /**
     * 顺序打印输入的String
     *
     * @param input
     */
    public static void print(String... input) {
        if (input == null)
            return;
        for (String each : input) {
            System.out.print(each);
        }
    }

    /**
     * 提交线程池
     *
     * @param r
     */
    public void run(Runnable r) {
        tPool.submit(r);
    }

    /**
     * 关闭线程池，等待以提交的线程执行结束
     */
    public void shutdown() {
        tPool.shutdown();
    }


}


