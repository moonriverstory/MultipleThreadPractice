package threadgroup;

public class ThreadGroupDemo {

    public static void main(String[] args) {
        Thread main = Thread.currentThread();
        ThreadGroup mainGroup = main.getThreadGroup();
        System.out.println(mainGroup);
        /**
         输出:
         java.lang.ThreadGroup[name=main,maxpri=10]

         主线程在一个叫做main的线程组中,最大优先级是10
         */

        mainGroup.list();//打印线程组的所有信息
        /**
         java.lang.ThreadGroup[name=main,maxpri=10]
         Thread[main,5,main]
         */

        //指定线程组是"myThread"
        Thread t1 = new Thread(mainGroup, "myThread");
        t1.start();//必须要调用start方法运行之后(活动的线程)才能添加到线程组中.
        mainGroup.list();
        /**
         java.lang.ThreadGroup[name=main,maxpri=10]
         Thread[main,5,main]
         Thread[myThread,5,main]
         */


        //通过线程组可以知道,这个线程组中有多少条线程是运行着的,有多少条线程,每条线程是什么都可以获取到.
        Thread[] arr = new Thread[mainGroup.activeCount()];
        main.enumerate(arr);//将线程组中活动的线程复制到指定数组中。
        for (Thread thread : arr) {
            System.out.println(thread.getName());//输出:main 和 myThread
        }


        ThreadGroup parent = mainGroup.getParent();//获得父线程组.
        parent.list();
        /**
         main线程的父线程组中有system和垃圾回收相关的线程..

         java.lang.ThreadGroup[name=system,maxpri=10]
         Thread[Reference Handler,10,system]
         Thread[Finalizer,8,system]
         Thread[Signal Dispatcher,9,system]
         Thread[Attach Listener,5,system]
         java.lang.ThreadGroup[name=main,maxpri=10]
         Thread[main,5,main]
         */

    }
}
