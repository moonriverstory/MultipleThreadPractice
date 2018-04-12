package synchronized_;

/**
 * 使用synchronized对同一个对象多次加锁
 */
public class RepeatLockOneObject {
    private Object lockObj = new Object();

    public void repeatLock(){
        int i = 0;
        synchronized (lockObj){
            i++;
            synchronized (lockObj){
                i++;
                synchronized (lockObj){
                    i++;
                }
            }
        }
        System.out.println(i);
    }

    public static void main(String[] args) {
        RepeatLockOneObject repeat = new RepeatLockOneObject();
        repeat.repeatLock();
    }
}
