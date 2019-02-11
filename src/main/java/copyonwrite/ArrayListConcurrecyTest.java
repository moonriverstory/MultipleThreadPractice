package copyonwrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayListConcurrecyTest {
    private static List<String> list = new ArrayList<String>(

    );

    static {
        String[] arr = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        list = Arrays.asList(arr);
    }

    private void modify() {
        new Thread(new Runnable() {
            public void run() {
                list.add(8, "a8");
                list.remove(9);
                list.set(6, "6666");
                System.out.println("----修改完成----");
            }
        }).start();
    }

    public void testModify() throws InterruptedException {
        Iterator<String> iterable = list.iterator();
        int i = 0;
        while (iterable.hasNext()) {
            if (i++ == 1) {
                modify();
            } else if (i == 4) {
                Thread.sleep(1000);
            }
            System.out.println("index: " + i + " value: " + iterable.next());
        }
        Thread.sleep(1000);
        System.out.println(list);
    }

    public static void main(String[] args) {
        ArrayListConcurrecyTest test = new ArrayListConcurrecyTest();
        try {
            test.testModify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 index: 1 value: 1
 index: 2 value: 2
 index: 3 value: 3
 Exception in thread "Thread-0" java.lang.UnsupportedOperationException
 at java.util.AbstractList.add(AbstractList.java:148)
 at copyonwrite.ArrayListConcurrecyTest$1.run(ArrayListConcurrecyTest.java:21)
 at java.lang.Thread.run(Thread.java:745)
 index: 4 value: 4
 index: 5 value: 5
 index: 6 value: 6
 index: 7 value: 7
 index: 8 value: 8
 index: 9 value: 9
 [1, 2, 3, 4, 5, 6, 7, 8, 9]
 */
