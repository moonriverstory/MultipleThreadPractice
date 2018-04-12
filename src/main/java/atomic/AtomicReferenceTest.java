package atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    // 原子性引用
    private static AtomicReference<Person> aRperson;

    public static void main(String[] args) throws InterruptedException {
        aRperson = new AtomicReference<Person>(new Person("Tom", 18));

        System.out.println("Atomic Person is " + aRperson.get().toString());

        Thread t1 = new Thread(new Task1());
        Thread t2 = new Thread(new Task2());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Now Atomic Person is " + aRperson.get().toString());
    }

    static class Task1 implements Runnable {
        public void run() {
            aRperson.getAndSet(new Person("Tom1", aRperson.get().getAge() + 1));

            System.out.println("Thread1 Atomic References "
                    + aRperson.get().toString());
        }
    }

    static class Task2 implements Runnable {
        public void run() {
            aRperson.getAndSet(new Person("Tom2", aRperson.get().getAge() + 2));

            System.out.println("Thread2 Atomic References "
                    + aRperson.get().toString());
        }
    }

    /**
     Atomic Person is [name: Tom, age: 18]
     Thread1 Atomic References [name: Tom1, age: 19]
     Thread2 Atomic References [name: Tom2, age: 21]
     Now Atomic Person is [name: Tom2, age: 21]

     right
     */

    /**
     Atomic Person is [name: Tom, age: 18]
     Thread1 Atomic References [name: Tom2, age: 20]
     Thread2 Atomic References [name: Tom2, age: 20]
     Now Atomic Person is [name: Tom2, age: 20]

     heheda， 这个也错了哦=。=
     */
    /**
     Atomic Person is [name: Tom, age: 18]
     Thread1 Atomic References [name: Tom1, age: 19]
     Thread2 Atomic References [name: Tom1, age: 19]
     Now Atomic Person is [name: Tom1, age: 19]

     这个也错了
     */
}
