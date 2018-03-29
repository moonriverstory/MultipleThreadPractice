package transferAmongThreads;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 利用PipedInputStream
 * <p>
 * Java中的Stream是单向的，所以在两个线程中分别建了一个input和output。这显然是一种很搓的方式，不过也算是一种通信方式吧
 */
public class MethodSix {
    private final PipedInputStream inputStream1;
    private final PipedOutputStream outputStream1;
    private final PipedInputStream inputStream2;
    private final PipedOutputStream outputStream2;
    private final byte[] MSG;

    public MethodSix() {
        inputStream1 = new PipedInputStream();
        outputStream1 = new PipedOutputStream();
        inputStream2 = new PipedInputStream();
        outputStream2 = new PipedOutputStream();
        MSG = "Go".getBytes();
        try {
            //连接inputStream管道和outputStream管道
            inputStream1.connect(outputStream2);
            inputStream2.connect(outputStream1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;
            private PipedInputStream in = inputStream1;
            private PipedOutputStream out = outputStream1;

            public void run() {
                try {
                    for (int i = 0; i < arr.length; i = i + 2) {
                        //打印数字
                        Helper.print(arr[i], arr[i + 1]);
                        //通知另一端执行
                        out.write(MSG);
                        byte[] inArr = new byte[2];
                        in.read(inArr);
                        //循环等待
                        while (true) {
                            //直到收到Go MSG，跳出等待while循环，继续执行for
                            if ("Go".equals(new String(inArr)))
                                break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
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
            private PipedInputStream in = inputStream2;
            private PipedOutputStream out = outputStream2;

            public void run() {

                try {
                    for (int i = 0; i < arr.length; i++) {
                        byte[] inArr = new byte[2];
                        //接收管道消息
                        in.read(inArr);
                        //循环等待
                        while (true) {
                            //收到Go MSG，跳出等待while循环，继续执行for
                            if ("Go".equals(new String(inArr)))
                                //break只跳出一层循环
                                break;
                        }
                        Helper.print(arr[i]);
                        out.write(MSG);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    try {
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
    }

    public static void main(String args[]) throws IOException {
        MethodSix six = new MethodSix();
        Helper.instance.run(six.newThreadOne());
        Helper.instance.run(six.newThreadTwo());
        Helper.instance.shutdown();
    }
    /**
     * 12A34B56C78D910E1112F1314G1516H1718I1920J2122K2324L2526M2728N2930O3132P3334Q3536R3738S3940T4142U4344V4546W4748X4950Y5152Z
     *
     * 运行结果正确，但是巨慢=。=
     *
     * 关闭流有bug
     */
}
