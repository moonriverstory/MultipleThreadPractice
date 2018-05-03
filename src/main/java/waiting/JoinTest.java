package waiting;

public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runner("A"));
        Thread t2 = new Thread(new Runner("B"));
        Thread t3 = new Thread(new Runner("C"));
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();

    }

}

class Runner implements Runnable {
    public String name;

    Runner(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " running~");
        try {
            System.out.println(name + " sleep~");
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            System.out.println(name + "sleep error!");
        }

    }
}
