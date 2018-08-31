package blockingQueue;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 同步队列的使用例子
 */
public class SynchronousQueueExample {

    static class SynchronousQueueProducer implements Runnable {

        protected BlockingQueue<String> blockingQueue;
        final Random random = new Random();

        public SynchronousQueueProducer(BlockingQueue<String> queue) {
            this.blockingQueue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String data = UUID.randomUUID().toString();
                    System.out.println("Put: " + data);
                    blockingQueue.put(data);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static class SynchronousQueueConsumer implements Runnable {

        protected BlockingQueue<String> blockingQueue;

        public SynchronousQueueConsumer(BlockingQueue<String> queue) {
            this.blockingQueue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String data = blockingQueue.take();
                    System.out.println(Thread.currentThread().getName()
                            + " take(): " + data);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        final BlockingQueue<String> synchronousQueue = new SynchronousQueue<String>();

        SynchronousQueueProducer queueProducer = new SynchronousQueueProducer(
                synchronousQueue);
        new Thread(queueProducer).start();

        SynchronousQueueConsumer queueConsumer1 = new SynchronousQueueConsumer(
                synchronousQueue);
        new Thread(queueConsumer1).start();

        SynchronousQueueConsumer queueConsumer2 = new SynchronousQueueConsumer(
                synchronousQueue);
        new Thread(queueConsumer2).start();

    }
    /**
     Put: 12c53f05-b4d8-410c-b441-82de463ddff6
     Thread-2 take(): 12c53f05-b4d8-410c-b441-82de463ddff6
     Put: 1a7e62dc-9d09-4d25-abf5-804ddf319f06
     Thread-1 take(): 1a7e62dc-9d09-4d25-abf5-804ddf319f06
     Put: 77c3d32f-9ed0-4097-8df5-2cfcebf4512c
     Thread-2 take(): 77c3d32f-9ed0-4097-8df5-2cfcebf4512c
     Put: 5210ca78-c82b-4641-b371-19c8644ca90c
     Thread-1 take(): 5210ca78-c82b-4641-b371-19c8644ca90c
     Put: 748f70f6-86dc-4f48-a9fe-fac924ffccc9
     Thread-2 take(): 748f70f6-86dc-4f48-a9fe-fac924ffccc9
     Put: 4f7f1d4f-378a-4214-bef7-f86f179ad738
     Thread-1 take(): 4f7f1d4f-378a-4214-bef7-f86f179ad738
     Put: ff07d6c0-4dd9-4030-886a-05737d0af33d
     Thread-2 take(): ff07d6c0-4dd9-4030-886a-05737d0af33d
     Put: c5aed11e-76e7-4df2-a894-7a0541b8be17
     Thread-1 take(): c5aed11e-76e7-4df2-a894-7a0541b8be17
     Put: 7c9f265f-7701-449e-92e4-fe1b7966d410
     Thread-2 take(): 7c9f265f-7701-449e-92e4-fe1b7966d410
     Put: e7fffb51-6ee8-4f5d-b62e-a0aa4e727674
     Thread-1 take(): e7fffb51-6ee8-4f5d-b62e-a0aa4e727674
     Put: 52adf752-3831-42a9-ae41-a887a0113e9b
     Thread-2 take(): 52adf752-3831-42a9-ae41-a887a0113e9b
     Put: 7c350faf-f4b4-4ba3-9a76-90a507399991
     Thread-1 take(): 7c350faf-f4b4-4ba3-9a76-90a507399991
     Put: b6baffd7-eb05-4f45-95ee-3636e0f02412
     Thread-2 take(): b6baffd7-eb05-4f45-95ee-3636e0f02412
     Put: 106c7db0-26d0-44a5-b537-25a456a45692
     Thread-1 take(): 106c7db0-26d0-44a5-b537-25a456a45692
     Put: fffe53b7-5852-4845-9ba8-71c21d824fd6
     Thread-2 take(): fffe53b7-5852-4845-9ba8-71c21d824fd6
     Put: c668c73f-a92e-4f7d-8fed-09f3e4f9c851
     Thread-1 take(): c668c73f-a92e-4f7d-8fed-09f3e4f9c851
     Put: 745dd04c-d03c-4684-bf59-15ddd746b1a2
     Thread-2 take(): 745dd04c-d03c-4684-bf59-15ddd746b1a2
     Put: 8bb85a86-588c-423a-ba1a-5e0fc5dd9a83
     Thread-1 take(): 8bb85a86-588c-423a-ba1a-5e0fc5dd9a83
     Put: 15efeed1-c714-4db6-9f48-32f41e438f0f
     Thread-2 take(): 15efeed1-c714-4db6-9f48-32f41e438f0f
     Put: f7e5b3fb-6082-49fc-892f-825f330eee4c
     Thread-1 take(): f7e5b3fb-6082-49fc-892f-825f330eee4c
     Put: 19ed0f04-6549-4af4-a1fb-283c1977711a
     Thread-2 take(): 19ed0f04-6549-4af4-a1fb-283c1977711a
     Put: 03846bdb-5cce-45a8-b37f-5d4b3a577579
     Thread-1 take(): 03846bdb-5cce-45a8-b37f-5d4b3a577579
     Put: 154c1918-17f7-4fa4-9d08-e4afd97dc6a5
     Thread-2 take(): 154c1918-17f7-4fa4-9d08-e4afd97dc6a5
     Put: 8cda7a5e-24ad-464f-a20a-ecb3d7a0da94
     Thread-1 take(): 8cda7a5e-24ad-464f-a20a-ecb3d7a0da94
...
     */
}