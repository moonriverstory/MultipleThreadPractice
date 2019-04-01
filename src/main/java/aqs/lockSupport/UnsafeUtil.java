package aqs.lockSupport;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 虽然Unsafe这个类我们不能new，但是这个类里面有个 private static final Unsafe theUnsafe;属性，我们正是利用这点用反射获取这个Unsafe 的这个属性。
 */
public class UnsafeUtil {
    public static Unsafe unsafe = null;

    static {
        unsafe = UnsafeUtil.getInstance();
    }

    public static Unsafe getInstance() {
        Field f = null;
        sun.misc.Unsafe unsafe = null;
        try {
            //theUnsafe是unsafe 内部一个属性名
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            // 得到Unsafe类的实例
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return unsafe;
    }

}
