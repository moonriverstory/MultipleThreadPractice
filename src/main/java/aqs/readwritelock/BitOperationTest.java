package aqs.readwritelock;

public class BitOperationTest {
    public static void main(String[] args) {
        int a = 1;
        int b = -1;
        System.out.println("1>>2=" + (a >> 2));
        System.out.println("-1>>2=" + (b >> 2));
        System.out.println("1>>>2=" + (a >>> 2));
        System.out.println("-1>>>2=" + (b >>> 2));
        System.out.println("1<<6=" + (1 << 6));//2的6次方
    }
    /**
     1>>2=0
     -1>>2=-1
     1>>>2=0
     -1>>>2=1073741823
     1<<6=64
     */
}
