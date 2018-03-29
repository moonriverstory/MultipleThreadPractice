package threadgroup;

import java.util.concurrent.TimeUnit;

public class ThreadGroupTest {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("Searcher");
        SearchTask searchTask = new SearchTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(threadGroup, searchTask);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Number of Threads:%d\n", threadGroup.activeCount());
        System.out.printf("Information of the Thread Group\n");
        threadGroup.list();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (Thread thread : threads) {
            System.out.printf("Thread ====%s: %s\n", thread.getName(), thread.getState());
        }

        // Wait unit one of the threads fo ThreadGroup objects ends.
        SearchTask.waitFinish(threadGroup);

        threadGroup.interrupt();

    }
}
