package aqs.lockSupport;

import sun.misc.Unsafe;

import java.util.Date;

public class UnsafeParkTest {

    private static final Unsafe unsafe = UnsafeUtil.getInstance();

    public void falseParkTime() {
        System.out.println(new Date(System.currentTimeMillis()));
        //相对时间后面的参数单位是纳秒
        unsafe.park(false, 3*1000*1000*1000L);
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println("SUCCESS!!!");
    }
    /**
     Fri Mar 29 17:45:37 CST 2019
     Fri Mar 29 17:45:40 CST 2019
     SUCCESS!!!
     */

    public void trueParkTime() {
        long time = System.currentTimeMillis() + 3*1000L;
        //绝对时间后面的参数单位是毫秒
        System.out.println(new Date(System.currentTimeMillis()));
        unsafe.park(true, time);
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println("SUCCESS!!!");
    }
    /**
     Fri Mar 29 17:48:52 CST 2019
     Fri Mar 29 17:48:55 CST 2019
     SUCCESS!!!
     */


    public static void main(String[] args) {
        UnsafeParkTest upt = new UnsafeParkTest();
        //upt.falseParkTime();
        upt.trueParkTime();
    }
}
