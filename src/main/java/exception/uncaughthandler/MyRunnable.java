package exception.uncaughthandler;

class MyRunnable implements Runnable {
    private  int ticket = 10000;
    private Object object;
    @Override
    public void run() {
        //demo exception
        System.out.println(3/0);
        System.out.println(3/2);
        System.out.println(4/0);

    }
}
