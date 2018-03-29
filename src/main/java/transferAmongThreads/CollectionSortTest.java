package transferAmongThreads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionSortTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("9");
        list.add("E");
        list.add("10");
        Collections.sort(list);
        System.out.print(list.toString());
    }
    /**
     * sort结果是[10, 9, E]，这是个bug，怎么解决？
     */
}
