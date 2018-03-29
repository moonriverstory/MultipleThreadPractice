package WaitNotify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * ����wait()��notify()�Լ�notifyAll()ʱ��Ҫע���ϸ�ڣ����¡�
 * 1��ʹ��wait()��notify()��notifyAll()ʱ��Ҫ�ȶԵ��ö��������
 * 2������wait()�������߳�״̬��RUNNING��ΪWAITING��������ǰ�̷߳��õ�����ĵȴ����С�
 * 3��notify()��notifyAll()�������ú󣬵ȴ��߳����ɲ����wait()���أ���Ҫ����notify()��
 * notifAll()���߳��ͷ���֮�󣬵ȴ��̲߳��л����wait()���ء�
 * 4��notify()�������ȴ������е�һ���ȴ��̴߳ӵȴ��������Ƶ�ͬ�������У���notifyAll()
 * �������ǽ��ȴ����������е��߳�ȫ���Ƶ�ͬ�����У����ƶ����߳�״̬��WAITING��ΪBLOCKED��
 * 5����wait()�������ص�ǰ���ǻ���˵��ö��������
 * <p>
 * ��ʱ�ȴ�
 * // �Ե�ǰ�������
 * public synchronized Object get(long mills) throws InterruptedException {
 * long future = System.currentTimeMillis() + mills;
 * long remaining = mills;
 * // ����ʱ����0����result����ֵ������Ҫ��
 * while ((result == null) && remaining > 0) {
 * wait(remaining);
 * remaining = future - System.currentTimeMillis();
 * }
 * return result;
 * }
 */
public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {
        public void run() {
            // ������ӵ��lock��Monitor
            synchronized (lock) {
                // ������������ʱ������wait��ͬʱ�ͷ���lock����
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true. wait" + "@ "
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }
                // ��������ʱ����ɹ���
                System.out.println(Thread.currentThread() + " flag is false. running" + "@ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {
        public void run() {
            // ������ӵ��lock��Monitor
            synchronized (lock) {
                // ��ȡlock������Ȼ�����֪ͨ��֪ͨʱ�����ͷ�lock������
                // ֱ����ǰ�߳��ͷ���lock��WaitThread���ܴ�wait�����з���
                System.out.println(Thread.currentThread() + " hold lock. notify @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // �ٴμ���
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep" + "@ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}