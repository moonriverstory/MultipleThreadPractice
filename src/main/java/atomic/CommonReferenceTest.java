package atomic;

public class CommonReferenceTest {
    //普通引用
    private static Person person;

    public static void main(String[] args) throws InterruptedException {
        person = new Person("Tom", 18);

        System.out.println("Person is " + person.toString());

        Thread t1 = new Thread(new Task1());
        Thread t2 = new Thread(new Task2());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Now Person is " + person.toString());
    }

    static class Task1 implements Runnable {
        public void run() {
            person.setAge(person.getAge() + 1);
            person.setName("Tom1");

            System.out.println("Thread1 Values "
                    + person.toString());
        }
    }

    static class Task2 implements Runnable {
        public void run() {
            person.setAge(person.getAge() + 2);
            person.setName("Tom2");

            System.out.println("Thread2 Values "
                    + person.toString());
        }
    }
    /**
     一个对象的初始状态为 name=Tom, age = 18。
     在 线程1 中将 name 修改为 Tom1，age + 1。
     在 线程2 中将 name 修改为 Tom2，age + 2。
     我们认为只会产生两种结果：

     若 线程1 先执行，线程2 后执行，则中间状态为 name=Tom1, age = 19，结果状态为 name=Tom2, age = 21
     若 线程2 先执行，线程1 后执行，则中间状态为 name=Tom2, age = 20，结果状态为 name=Tom1, age = 21

     */

    /**
     right

     Person is [name: Tom, age: 18]
     Thread2 Values [name: Tom2, age: 20]
     Thread1 Values [name: Tom1, age: 21]
     Now Person is [name: Tom1, age: 21]
     */
    /**
     right

     Person is [name: Tom, age: 18]
     Thread1 Values [name: Tom1, age: 19]
     Thread2 Values [name: Tom2, age: 21]
     Now Person is [name: Tom2, age: 21]
     */
    /**
     wrong

     Person is [name: Tom, age: 18]
     Thread2 Values [name: Tom1, age: 21]
     Thread1 Values [name: Tom1, age: 21]
     Now Person is [name: Tom1, age: 21]

     尴尬不=。=
     */
}
