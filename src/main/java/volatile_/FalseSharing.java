package volatile_;

import sun.misc.Contended;

/**
 * 伪共享优化
 */
public final class FalseSharing implements Runnable {
    public static int NUM_THREADS = 4; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;
    private static VolatileLong[] longs;

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        Thread.sleep(1000);
        System.out.println("starting....");
        if (args.length == 1) {
            NUM_THREADS = Integer.parseInt(args[0]);
        }

        longs = new VolatileLong[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }

        final long start = System.currentTimeMillis();
        runTest();
        System.out.println("duration = " + (System.currentTimeMillis() - start));
    }

    /**
     starting....
     duration = 17784
     */


    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }

    @Contended //JDK8 默认支持自动填充加上这个注解 并且加上虚拟机参数-XX:-RestrictContended
    public final static class VolatileLong {
        public volatile long value = 0L;

        //64位系统默认对象头12字节（开启压缩） 补充10个字节的无用对象让缓存行共享失效
        public long p1, p2, p3, p4, p5, p6,p7,p8,p9,p10; // 这行代码注释掉速度就慢不少
    }
}
