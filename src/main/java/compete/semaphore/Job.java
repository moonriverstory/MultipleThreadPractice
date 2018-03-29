package compete.semaphore;

public class Job implements Runnable {
    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n",
                Thread.currentThread().getName());
        printQueue.printJob();
        System.out.printf("%s: The document has been printed\n",
                Thread.currentThread().getName());
    }
}
