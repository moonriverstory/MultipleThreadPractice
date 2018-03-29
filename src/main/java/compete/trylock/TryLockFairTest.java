package compete.trylock;

public class TryLockFairTest {
    public static void main(String[] args) {
        PrintQueue printQueue=new TryLockPrintQueue(false);
        Thread thread[]=new Thread[10];
        for (int i=0; i<10; i++){
            thread[i]=new Thread(new Job(printQueue),"Thread "+ i);
        }

        for (int i=0; i<10; i++){
            thread[i].start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     Thread 0: Going to print a document
     Thread 0:PrintQueue: Printing a Job during 1 seconds
     Thread 1: Going to print a document
     Thread 1:PrintQueue: Printing a Job during 7 seconds
     Thread 2: Going to print a document
     Thread 2:PrintQueue: Printing a Job during 7 seconds
     Thread 3: Going to print a document
     Thread 3:PrintQueue: Printing a Job during 6 seconds
     Thread 4: Going to print a document
     Thread 4:PrintQueue: Printing a Job during 8 seconds
     Thread 5: Going to print a document
     Thread 5:PrintQueue: Printing a Job during 9 seconds
     Thread 6: Going to print a document
     Thread 6:PrintQueue: Printing a Job during 6 seconds
     Thread 7: Going to print a document
     Thread 7:PrintQueue: Printing a Job during 0 seconds
     Thread 8: Going to print a document
     Thread 8:PrintQueue: Printing a Job during 0 seconds
     Exception in thread "Thread 8" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     Thread 9: Going to print a document
     Thread 9:PrintQueue: Printing a Job during 1 seconds
     Exception in thread "Thread 7" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     Thread 0:PrintQueue: Printing a Job during 4 seconds
     Exception in thread "Thread 9" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     Thread 0: The document has been printed
     Exception in thread "Thread 3" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     Exception in thread "Thread 6" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     Exception in thread "Thread 2" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     Exception in thread "Thread 1" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     Exception in thread "Thread 4" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     Exception in thread "Thread 5" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at compete.trylock.TryLockPrintQueue.printJob(TryLockPrintQueue.java:19)
     at compete.trylock.Job.run(Job.java:13)
     at java.lang.Thread.run(Thread.java:745)
     */
}
