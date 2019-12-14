package aqs.readwritelock;

import javax.sound.midi.Soundbank;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁降级
 */
public class ReadWriteLockDegradeTest {

    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    //读锁
    final Lock readLock = rwl.readLock();
    //写锁
    final Lock writeLock = rwl.writeLock();

    public void lockDegrade() {

        //锁降级从写锁获取到开始
        writeLock.lock();
        System.out.println("获取写锁");
        try {
            readLock.lock();
            System.out.println("获取读锁");
        } finally {
            writeLock.unlock();
            System.out.println("释放写锁");
        }//锁降级完成，写锁降级为读锁

        try {
            //略
            System.out.println("do something");
        } finally {
            //最终释放写锁
            readLock.unlock();
            System.out.println("释放读锁");
        }
    }

    public static void main(String[] args) {
        ReadWriteLockDegradeTest test = new ReadWriteLockDegradeTest();
        test.lockDegrade();
    }
}
