package atomic.basicUseage;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo{
        private static AtomicInteger atomicInteger = new AtomicInteger(1);

        public static void main(String[] args) {
            System.out.println(atomicInteger.getAndIncrement());
            System.out.println(atomicInteger.get());
        }
}
